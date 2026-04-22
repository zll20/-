<template>
	<div class="home-content" :style="{'backgroundImage': indexBgUrl?`url(${$base.url + indexBgUrl})`:''}">
		<!-- title -->
		<div id="home-title" class="home-title animate__animated">
			<div class="titles" >
				<span>欢迎使用</span>
				{{this.$project.projectName}}
			</div>
		</div>
    <!-- statis -->
    <div class="statis-box">
      <!-- 原有单词学习总数卡片 -->
      <div id="statis1" class="statis1 animate__animated" v-if="isAuth('dancixuexi','首页总数')">
        <div class="left">
          <span class="icon iconfont icon-xiaoliang1"></span>
        </div>
        <div class="right">
          <div class="num">{{dancixuexiCount}}</div>
          <div class="name">单词学习总数</div>
        </div>
      </div>
      <!-- 新增总单词学习数量卡片 -->
      <div id="statis2" class="statis1 animate__animated">
        <div class="left">
          <span class="icon iconfont icon-shouye"></span>
        </div>
        <div class="right">
          <div class="num">{{totalWordCount}}</div>
          <div class="name">总单词学习数量</div>
        </div>
      </div>
    </div>
    <!-- statis -->

    <!-- echarts -->
    <div class="type2">
      <!-- 左下：学习时长扇形图 -->
      <div class="echarts1 animate__animated">
        <div id="moduleTimeChart" style="width: 100%;height: 100%"></div>
      </div>
      <!-- 右下：学生学习时长 TOP5 表格 -->
      <div class="echarts2 animate__animated">
        <table class="top5-table">
          <thead>
            <tr>
              <th>排名</th>
              <th>学生姓名</th>
              <th>账号</th>
              <th>总学习时长</th>
              <th>总单词量</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in top5List" :key="index">
              <td>{{index + 1}}</td>
              <td>{{item.xingming || '-'}}</td>
              <td>{{item.zhanghao}}</td>
              <td>{{formatTime(item.totalTime)}}</td>
              <td>{{item.totalWords}}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <!-- echarts -->
  </div>
</template>
<script>
import 'animate.css'
//2
import router from '@/router/router-static'
import * as echarts from 'echarts'
export default {
	data() {
		return {
			dancixuexiCount: 0,
			line: {"backgroundColor":"transparent","yAxis":{"axisLabel":{"borderType":"solid","rotate":0,"padding":0,"shadowOffsetX":0,"margin":15,"backgroundColor":"transparent","borderColor":"#000","shadowOffsetY":0,"color":"#333","shadowBlur":0,"show":true,"inside":false,"ellipsis":"...","overflow":"none","borderRadius":0,"borderWidth":0,"width":"","fontSize":12,"lineHeight":24,"shadowColor":"transparent","fontWeight":"normal","height":""},"axisTick":{"show":true,"length":5,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"inside":false},"splitLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#666","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"minInterval":1,"axisLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"splitArea":{"show":false,"areaStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"rgba(25,25,25,0.3)","opacity":1,"shadowBlur":10,"shadowColor":"rgba(0,0,0,.5)"}}},"xAxis":{"axisLabel":{"borderType":"solid","rotate":30,"padding":0,"shadowOffsetX":0,"margin":10,"backgroundColor":"transparent","borderColor":"#000","shadowOffsetY":0,"color":"#333","shadowBlur":0,"show":true,"inside":false,"ellipsis":"...","overflow":"truncate","borderRadius":0,"borderWidth":0,"width":120,"interval":0,"fontSize":12,"lineHeight":24,"shadowColor":"transparent","fontWeight":"normal","height":""},"axisTick":{"show":true,"length":5,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"inside":false},"splitLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":false},"axisLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"splitArea":{"show":false,"areaStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"rgba(25,25,25,.3)","opacity":1,"shadowBlur":10,"shadowColor":"rgba(0,0,0,.5)"}}},"color":["#FF7700","#dc3dfa","#76bd9e","#3b43f7","#73c0de","#89e6d8","#4495ac","#9a60b4","#ea7ccc"],"legend":{"padding":0,"itemGap":10,"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#666","shadowOffsetY":0,"orient":"horizontal","shadowBlur":0,"bottom":"auto","itemHeight":14,"show":true,"icon":"roundRect","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"inherit","shadowOffsetY":0,"color":"inherit","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"transparent"},"right":"auto","top":"auto","borderRadius":0,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"inherit","shadowBlur":0,"width":"auto","type":"inherit","opacity":1,"shadowColor":"transparent"},"left":"right","borderWidth":0,"width":"80%","itemWidth":20,"textStyle":{"textBorderWidth":0,"color":"inherit","textShadowColor":"transparent","ellipsis":"...","overflow":"none","fontSize":12,"lineHeight":24,"textShadowOffsetX":0,"position":"outside","textShadowOffsetY":0,"textBorderType":"solid","textBorderColor":"transparent","textShadowBlur":0},"shadowColor":"rgba(0,0,0,.3)","height":"auto"},"series":{"animationDuration":6000,"symbol":"emptyCircle","label":{"color":"#FA6B04","show":true,"position":"top"},"symbolSize":[5,5],"symbolOffset":[0,1],"animation":true},"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"title":{"borderType":"solid","padding":0,"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#666","shadowOffsetY":0,"shadowBlur":0,"bottom":"auto","show":true,"right":"auto","top":"auto","borderRadius":0,"left":"left","borderWidth":0,"textStyle":{"textBorderWidth":0,"color":"#333","textShadowColor":"transparent","fontSize":14,"lineHeight":24,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":600,"textBorderColor":"#666","textShadowBlur":0},"shadowColor":"transparent"}},
			bar: {"backgroundColor":"transparent","yAxis":{"axisLabel":{"borderType":"solid","rotate":0,"padding":0,"shadowOffsetX":0,"margin":12,"backgroundColor":"transparent","borderColor":"#666","shadowOffsetY":0,"color":"#333","shadowBlur":0,"show":true,"inside":false,"ellipsis":"...","overflow":"none","borderRadius":0,"borderWidth":0,"width":"","fontSize":12,"lineHeight":24,"shadowColor":"transparent","fontWeight":"normal","height":""},"axisTick":{"show":true,"length":5,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"inside":false},"splitLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#666","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"minInterval":1,"axisLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"splitArea":{"show":false,"areaStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"rgba(25,25,25,0.3)","opacity":1,"shadowBlur":10,"shadowColor":"rgba(0,0,0,.5)"}}},"xAxis":{"axisLabel":{"borderType":"solid","rotate":30,"padding":0,"shadowOffsetX":0,"margin":10,"backgroundColor":"transparent","borderColor":"#000","shadowOffsetY":0,"color":"#333","shadowBlur":0,"show":true,"inside":false,"ellipsis":"...","overflow":"truncate","borderRadius":0,"borderWidth":0,"width":120,"interval":0,"fontSize":12,"lineHeight":24,"shadowColor":"transparent","fontWeight":"normal","height":""},"axisTick":{"show":true,"length":5,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"inside":false},"splitLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":false},"minInterval":1,"axisLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"splitArea":{"show":false,"areaStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"rgba(25,25,25,.3)","opacity":1,"shadowBlur":10,"shadowColor":"rgba(0,0,0,.5)"}}},"color":["#FF7700","#FF004E","#FF8DB1","#FFEAD8","#E5E5E5","#89e6d8","#4495ac","#9a60b4","#ea7ccc"],"legend":{"padding":0,"itemGap":10,"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#666","shadowOffsetY":0,"orient":"horizontal","shadowBlur":0,"bottom":"auto","itemHeight":14,"show":true,"icon":"roundRect","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"inherit","shadowOffsetY":0,"color":"inherit","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"transparent"},"right":"auto","top":"auto","borderRadius":0,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"inherit","shadowBlur":0,"width":"auto","type":"inherit","opacity":1,"shadowColor":"transparent"},"left":"right","borderWidth":0,"width":"80%","itemWidth":20,"textStyle":{"textBorderWidth":0,"color":"inherit","textShadowColor":"transparent","ellipsis":"...","overflow":"none","fontSize":12,"lineHeight":12,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"transparent","textShadowBlur":0},"shadowColor":"rgba(0,0,0,.3)","height":"auto"},"grid":{"right":"20","top":"60","left":"20","bottom":"20","containLabel":true},"series":{"barWidth":"auto","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"#666","shadowOffsetY":0,"color":"","shadowBlur":0,"barBorderRadius":[5,5,15,15],"borderWidth":0,"opacity":1,"shadowColor":"#000"},"colorBy":"data","label":{"color":"#FA6B04","show":true,"position":"top"},"barCategoryGap":"30%"},"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"title":{"borderType":"solid","padding":0,"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#666","shadowOffsetY":0,"subtext":"","shadowBlur":0,"bottom":"auto","show":true,"right":"auto","subtextStyle":{"padding":[5,0,0,0],"borderColor":"red","color":"red","borderWidth":10},"top":"auto","borderRadius":0,"left":"left","borderWidth":0,"textStyle":{"textBorderWidth":0,"color":"#333","textShadowColor":"transparent","fontSize":14,"lineHeight":24,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":600,"textBorderColor":"#666","textShadowBlur":0},"shadowColor":"transparent"},"base":{"animate":false,"interval":2000}},
			pie: {"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"backgroundColor":"transparent","color":["#FF7700","#FF004E","#FF8DB1","#FFEAD8","#73c0de","#89e6d8","#4495ac","#9a60b4","#ea7ccc"],"title":{"borderType":"solid","padding":[5,0,0,0],"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#666","shadowOffsetY":0,"shadowBlur":0,"bottom":"auto","show":true,"right":"auto","top":"auto","borderRadius":0,"left":"left","borderWidth":0,"textStyle":{"textBorderWidth":0,"color":"#333","textShadowColor":"transparent","fontSize":14,"lineHeight":14,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":600,"textBorderColor":"#666","textShadowBlur":0},"shadowColor":"transparent"},"legend":{"padding":[5,0,0,0],"itemGap":10,"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#666","shadowOffsetY":0,"orient":"horizontal","shadowBlur":0,"bottom":"auto","itemHeight":4,"show":true,"icon":"roundRect","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"inherit","shadowOffsetY":0,"color":"inherit","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"transparent"},"right":0,"top":"auto","borderRadius":0,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"inherit","shadowBlur":0,"width":"auto","type":"inherit","opacity":1,"shadowColor":"transparent"},"left":"right","borderWidth":0,"width":"80%","itemWidth":4,"textStyle":{"textBorderWidth":0,"color":"inherit","textShadowColor":"transparent","ellipsis":"...","overflow":"none","fontSize":12,"lineHeight":12,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"transparent","textShadowBlur":0},"shadowColor":"rgba(0,0,0,.3)","height":"auto"},"series":{"itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"#666","shadowOffsetY":0,"color":"","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"#000"},"label":{"borderType":"solid","rotate":0,"padding":0,"textBorderWidth":0,"backgroundColor":"transparent","borderColor":"#666","color":"inherit","show":true,"textShadowColor":"transparent","distanceToLabelLine":5,"ellipsis":"...","overflow":"none","borderRadius":0,"borderWidth":0,"fontSize":12,"lineHeight":18,"textShadowOffsetX":0,"position":"outside","textShadowOffsetY":0,"textBorderType":"solid","textBorderColor":"#666","textShadowBlur":0},"labelLine":{"show":true,"length":10,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"#964afb","shadowBlur":0,"width":2,"type":"solid","opacity":1,"shadowColor":"#000"},"length2":14,"smooth":false}}},
			funnel: {"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"backgroundColor":"transparent","color":["#FF7700","#FF004E","#FF8DB1","#FFEAD8","#73c0de","#89e6d8","#4495ac","#9a60b4","#ea7ccc"],"title":{"borderType":"solid","padding":2,"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#ccc","shadowOffsetY":0,"shadowBlur":0,"bottom":"auto","show":true,"right":"auto","top":"auto","borderRadius":0,"left":"center","borderWidth":0,"textStyle":{"textBorderWidth":0,"color":"#666","textShadowColor":"transparent","fontSize":14,"lineHeight":12,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"#ccc","textShadowBlur":0},"shadowColor":"transparent"},"legend":{"padding":5,"itemGap":10,"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#ccc","shadowOffsetY":0,"orient":"vertical","shadowBlur":0,"bottom":"auto","itemHeight":4,"show":true,"icon":"roundRect","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"inherit","shadowOffsetY":0,"color":"inherit","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"transparent"},"top":"auto","borderRadius":0,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"inherit","shadowBlur":0,"width":"auto","type":"inherit","opacity":1,"shadowColor":"transparent"},"left":"left","borderWidth":0,"width":"auto","itemWidth":4,"textStyle":{"textBorderWidth":0,"color":"inherit","textShadowColor":"transparent","ellipsis":"...","overflow":"none","fontSize":12,"lineHeight":20,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"transparent","textShadowBlur":0},"shadowColor":"rgba(0,0,0,.3)","height":"auto"},"series":{"itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"#000","shadowOffsetY":0,"color":"","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"#000"},"label":{"borderType":"solid","rotate":0,"padding":0,"textBorderWidth":0,"backgroundColor":"transparent","borderColor":"#fff","color":"","show":true,"textShadowColor":"transparent","distanceToLabelLine":5,"ellipsis":"...","overflow":"none","borderRadius":0,"borderWidth":0,"fontSize":12,"lineHeight":18,"textShadowOffsetX":0,"position":"outside","textShadowOffsetY":0,"textBorderType":"solid","textBorderColor":"#fff","textShadowBlur":0},"labelLine":{"show":true,"length":10,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"#000"},"length2":14,"smooth":false}}},
			boardBase: {"funnelNum":8,"lineNum":12,"radarNum":8,"gaugeNum":8,"barNum":12,"pieNum":10},
			gauge: {"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"backgroundColor":"transparent","color":["#FF7700","#FF004E","#FF8DB1","#FFEAD8","#73c0de","#89e6d8","#4495ac","#9a60b4","#ea7ccc"],"title":{"top":"top","left":"left","textStyle":{"fontSize":14,"lineHeight":24,"color":"#333","fontWeight":600}},"series":{"pointer":{"offsetCenter":[0,"10%"],"icon":"path://M2.9,0.7L2.9,0.7c1.4,0,2.6,1.2,2.6,2.6v115c0,1.4-1.2,2.6-2.6,2.6l0,0c-1.4,0-2.6-1.2-2.6-2.6V3.3C0.3,1.9,1.4,0.7,2.9,0.7z","width":8,"length":"80%"},"axisLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"opacity":0.5,"shadowBlur":1,"shadowColor":"#000"},"roundCap":true},"anchor":{"show":true,"itemStyle":{"color":"inherit"},"size":18,"showAbove":true},"emphasis":{"disabled":false},"progress":{"show":true,"roundCap":true,"overlap":true},"splitNumber":25,"detail":{"formatter":"{value}","backgroundColor":"inherit","color":"#fff","borderRadius":3,"width":20,"fontSize":12,"height":10},"title":{"fontSize":14},"animation":true}},
			radar: {"backgroundColor":"transparent","radar":{"shape":"circle"},"color":["#365E77","#DF308C","#0CB906","#7690cb","#49ada0","#5BBAEC","#a68a28","#EE142F","#FFE9E9"],"legend":{"padding":5,"itemGap":5,"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#ccc","shadowOffsetY":0,"orient":"vertical","shadowBlur":0,"bottom":"auto","itemHeight":4,"show":true,"icon":"roundRect","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"inherit","shadowOffsetY":0,"color":"inherit","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"transparent"},"right":"auto","top":"auto","borderRadius":0,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"inherit","shadowBlur":0,"width":"auto","type":"inherit","opacity":1,"shadowColor":"transparent"},"left":"right","borderWidth":0,"width":"auto","itemWidth":4,"textStyle":{"textBorderWidth":0,"color":"inherit","textShadowColor":"transparent","ellipsis":"...","overflow":"none","fontSize":12,"lineHeight":24,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"transparent","textShadowBlur":0},"shadowColor":"rgba(0,0,0,.3)","height":"auto"},"series":{},"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#7987FD"}},"title":{"top":"top","left":"left","textStyle":{"textBorderWidth":0,"color":"#365E77","textShadowColor":"transparent","fontSize":14,"lineHeight":14,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":600,"textBorderColor":"#666","textShadowBlur":0}}},
			indexBgUrl: '',
			indexLogoUrl: '',
			totalWordCount: 0, // 总单词学习数量
			moduleTimeData: [], // 模块时长数据
			top5List: [], // 学习时长TOP5列表
		};
	},
	mounted(){
		this.init();
		this.getdancixuexiCount();
		// 新增：获取学习记录数据
		this.getTotalWordCount();
		this.getModuleTimeData();
		this.getTop5Data();
		if(this.isAuth('dancixuexi','首页总数')){
			this.dancixuexiChat1();
		}
		if(this.isAuth('dancixuexi','首页统计',2)){
			this.dancixuexiChat1();
		}
		if(this.isAuth('dancixuexi','首页统计',2)){
			this.dancixuexiChat2();
		}
		// 新增：初始化扇形图
		this.$nextTick(() => {
			this.initModuleTimeChart();
		});
	},
	methods:{
		handleScroll() {
			let arr = [
				{id:'home-title',css:'animate__'},
				{id:'statis1',css:'animate__'},
				{id:'dancixuexiChart1',css:'animate__'},
				{id:'dancixuexiChart2',css:'animate__'},
			]
			
			for (let i in arr) {
				let doc = document.getElementById(arr[i].id)
				if (doc) {
					let top = doc.offsetTop
					let win_top = window.innerHeight + window.pageYOffset
					// console.log(top,win_top)
					if (win_top > top && doc.classList.value.indexOf(arr[i].css) < 0) {
						// console.log(doc)
						doc.classList.add(arr[i].css)
					}
				}
			}
		},
		// 统计图动画
		myChartInterval(type, xAxisData, seriesData, myChart) {
			this.$nextTick(() => {
				setInterval(() => {
					let xAxis = xAxisData.shift()
					xAxisData.push(xAxis)
					let series = seriesData.shift()
					seriesData.push(series)
				
					if (type == 1) {
						myChart.setOption({
							xAxis: [{
								data: xAxisData
							}],
							series: [{
								data: seriesData
							}]
						});
					}
					if (type == 2) {
						myChart.setOption({
							yAxis: [{
								data: xAxisData
							}],
							series: [{
								data: seriesData
							}]
						});
					}
				}, $template2.back.board.bar.base.interval);
			})
		},
		changeStatQuery(arr) {
			if(arr.length==1) {
				if(arr[0] == 'users'&&this.$storage.get("sessionTable")=='users') {
					return true
				}
			}
			let role = this.$storage.get('role')
			for(let x in arr) {
				if(arr[x] == role) {
					return true
				}
			}
			return false
		},
		init(){
			if(this.$storage.get('Token')){
				this.$http({
					url: `${this.$storage.get('sessionTable')}/session`,
					method: "get"
				}).then(({ data }) => {
					if (data && data.code != 0) {
						router.push({ name: 'login' })
					}
				});
			}else{
				router.push({ name: 'login' })
			}
		},
		getdancixuexiCount() {
			this.$http({
				url: `dancixuexi/count`,
				method: "get"
			}).then(({
				data
			}) => {
				if (data && data.code == 0) {
					this.dancixuexiCount = data.data
				}
			})
		},
		dancixuexiChat1(e=null) {
			this.$nextTick(()=>{
				var dancixuexiChart1 = echarts.init(document.getElementById("dancixuexiChart1"),'macarons');
				let params = {
				}
				this.$http({
					url: "dancixuexi/group/shifoujizhu",
					method: "get",
					params
				}).then(({ data }) => {
					if (data && data.code === 0) {
						let res = data.data||[];
						// 统计图设置对了吗
						let xAxis = [];
						let yAxis = [];
						let pArray = []
						for(let i=0;i<res.length;i++){
							if(this.boardBase&&i==this.boardBase.pieNum){
								break;
							}
							xAxis.push(res[i].shifoujizhu);
							yAxis.push(parseFloat((res[i].total)));
							pArray.push({
								value: parseFloat((res[i].total)),
								name: res[i].shifoujizhu
							})
						}
						var option = {};
						let titleObj = this.pie.title
						titleObj.text = '学习分析'
						
						const legendObj = this.pie.legend
						let tooltipObj = {trigger: 'item',formatter: '{b} : {c} ({d}%)'}
						tooltipObj = Object.assign(tooltipObj , this.pie.tooltip?this.pie.tooltip:{})
						
						let seriesObj = {
							type: 'pie',
							radius: '55%',
							center: ['50%', '60%'],
							data: pArray,
							emphasis: {
								itemStyle: {
									shadowBlur: 10,
									shadowOffsetX: 0,
									shadowColor: 'rgba(0, 0, 0, 0.5)'
								}
							}
						}
						seriesObj = Object.assign(seriesObj , this.pie.series)
						const gridObj = this.pie.grid
						option = {
							backgroundColor: this.pie.backgroundColor,
							color: this.pie.color,
							title: titleObj,
							legend: legendObj,
							grid: gridObj,
							tooltip: tooltipObj,
							series: [seriesObj]
						};
						// 使用刚指定的配置项和数据显示图表。
						dancixuexiChart1.setOption(option);
				
						//根据窗口的大小变动图表
						window.onresize = function() {
							dancixuexiChart1.resize();
						};
					}else{
						this.$message({
							message: data.msg,
							type: "warning",
							duration: 1500,
						})
					}
				});
			})
		},

		dancixuexiChat2(e=null) {
			this.$nextTick(()=>{

				var dancixuexiChart2 = echarts.init(document.getElementById("dancixuexiChart2"),'macarons');
				let params = {
				}
				this.$http({
					url: `dancixuexi/value/riqi/xuexishizhang`,
					method: "get",
					params
				}).then(({ data }) => {
					if (data && data.code === 0) {
						let res = data.data||[];
						// 统计图设置对了吗
						let xAxis = [];
						let yAxis = [];
						let pArray = []
						for(let i=0;i<res.length;i++){
							if(this.boardBase&&i==this.boardBase.barNum){
								break;
							}
							xAxis.push(res[i].riqi);
							yAxis.push(parseFloat((res[i].total)));
							pArray.push({
								value: parseFloat((res[i].total)),
								name: res[i].riqi
							})
						}
						var option = {};
						let titleObj = this.bar.title
						titleObj.text = '学习时长'
						
						const legendObj = this.bar.legend
						let tooltipObj = {trigger: 'item',formatter: '{b} : {c}'}
						tooltipObj = Object.assign(tooltipObj , this.bar.tooltip?this.bar.tooltip:{})
				
						let xAxisObj = this.bar.xAxis
						xAxisObj.type = 'category'
						xAxisObj.data = xAxis
						
						let yAxisObj = this.bar.yAxis
						yAxisObj.type = 'value'
						let seriesObj = {
							data: yAxis,
							type: 'bar',
						}
						seriesObj = Object.assign(seriesObj , this.bar.series)
						const gridObj = this.bar.grid

						option = {
							backgroundColor: this.bar.backgroundColor,
							color: this.bar.color,
							title: titleObj,
							legend: legendObj,
							tooltip: tooltipObj,
							xAxis: xAxisObj,
							yAxis: yAxisObj,
							series: [seriesObj],
							grid: gridObj
						};
						// 使用刚指定的配置项和数据显示图表。
						dancixuexiChart2.setOption(option);
				
						//根据窗口的大小变动图表
						window.onresize = function() {
							dancixuexiChart2.resize();
						};
					}else{
						this.$message({
							message: data.msg,
							type: "warning",
							duration: 1500,
						})
					}
				});
			})
		},
		// 新增方法：获取总单词数
		getTotalWordCount() {
			this.$http({
				url: 'study/admin/study/totalWords',
				method: 'get'
			}).then(({ data }) => {
				if (data && data.code === 0 && data.data) {
					this.totalWordCount = data.data.totalWords || 0;
				}
			});
		},
		// 新增方法：获取模块时长数据
		getModuleTimeData() {
			this.$http({
				url: 'study/admin/study/moduleTime',
				method: 'get'
			}).then(({ data }) => {
				if (data && data.code === 0 && data.data) {
					this.moduleTimeData = data.data;
				}
			});
		},
		// 新增方法：获取TOP5数据
		getTop5Data() {
			this.$http({
				url: 'study/admin/study/top5',
				method: 'get'
			}).then(({ data }) => {
				if (data && data.code === 0 && data.data) {
					this.top5List = data.data;
				}
			});
		},
		// 新增方法：格式化时间（秒 → 小时/分钟/秒）
		formatTime(seconds) {
			if (!seconds) return '0秒';
			const hours = Math.floor(seconds / 3600);
			const minutes = Math.floor((seconds % 3600) / 60);
			const secs = seconds % 60;
			let timeStr = '';
			if (hours > 0) timeStr += hours + '小时';
			if (minutes > 0) timeStr += minutes + '分钟';
			if (secs > 0) timeStr += secs + '秒';
			return timeStr || '0秒';
		},
		// 新增方法：初始化学习时长扇形图
		initModuleTimeChart() {
			const chartDom = document.getElementById('moduleTimeChart');
			const myChart = echarts.init(chartDom, 'macarons');
			const option = {
				tooltip: {
					trigger: 'item',
					formatter: '{b}: {c} ({d}%)'
				},
				legend: {
					orient: 'vertical',
					left: 'left'
				},
				series: [
					{
						name: '模块时长',
						type: 'pie',
						radius: '55%',
						center: ['50%', '60%'],
						data: this.moduleTimeData.map(item => ({
							value: item.totalTime,
							name: this.getModuleName(item.module)
						})),
						emphasis: {
							itemStyle: {
								shadowBlur: 10,
								shadowOffsetX: 0,
								shadowColor: 'rgba(0, 0, 0, 0.5)'
							}
						}
					}
				]
			};
			myChart.setOption(option);
			window.addEventListener('resize', () => myChart.resize());
		},
		// 新增方法：模块名称映射
		getModuleName(module) {
			const moduleMap = {
				'danciku': '单词库',
				'tingli': '听力',
				'wenkan': '文刊'
			};
			return moduleMap[module] || module;
		},
	}
};
</script>
<style lang="scss" scoped>
	.home-content {
		padding: 0 30px 30px;
		background: url(http://codegen.caihongy.cn/20250826/9c566af26d2e4baca85799a2893fb657.png) no-repeat center top / cover;
		display: flex;
		width: 100%;
		min-height: 100vh;
		flex-wrap: wrap;
		.home-title {
			padding: 10px 0 0;
			box-shadow: none;
			margin: 10px 0;
			display: flex;
			width: 100%;
			justify-content: center;
			align-items: center;
			transition: 0.3s;
			.titles {
				padding: 0;
				color: #333;
				font-size: 30px;
				line-height: 60px;
				span {
				}
			}
		}
		.home-title:hover {
			transform: translate3d(0, 0px, 0);
		}
		.statis-box {
			margin: 0;
			background: none;
			display: flex;
			width: 100%;
			justify-content: center;
			align-items: center;
			flex-wrap: wrap;
			.statis1 {
				border: 0px solid #00000050;
				border-radius: 24px;
				padding: 25px 20px;
				margin: 10px;
				background: #fff;
				display: flex;
				width: calc(20% - 20px);
				justify-content: center;
				flex-wrap: wrap;
				transition: 0.3s;
				height: 228px;
				.left {
					background: none;
					display: flex;
					width: 100%;
					align-items: center;
					height: 40px;
					.iconfont {
						border-radius: 8px;
						padding: 0;
						box-shadow: 0px 3px 6px 1px #FFF0E8, inset 0px 3px 6px 1px #FFF39F;
						color: #fff;
						background:  linear-gradient( 180deg, #FFD500 0%, #FDAF01 50%, #FA6B04 100%);
						font-weight: 500;
						width: 66px;
						font-size: 48px;
						line-height: 66px;
						text-align: center;
						height: 66px;
					}
				}
				.right {
					display: flex;
					width: 100%;
					justify-content: center;
					flex-wrap: wrap;
					.num {
						margin: 5px 0;
						color: #333333;
						font-weight: 600;
						width: 100%;
						font-size: 36px;
						line-height: 24px;
						height: 24px;
						order: 3;
					}
					.name {
						margin: 20px 0 10px 0;
						color: #333333;
						width: 100%;
						font-size: 18px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis1:hover {
				box-shadow: 0px 4px 3px #8558ff50;
				transform: translate3d(0, 3px, 0);
				z-index: 1;
				background: rgba(255,255,255,1);
			}
			.statis2 {
				border: 0px solid #00000050;
				border-radius: 24px;
				padding: 25px 20px;
				margin: 10px;
				background: #fff;
				display: flex;
				width: calc(20% - 20px);
				justify-content: center;
				flex-wrap: wrap;
				transition: 0.3s;
				height: 228px;
				.left {
					background: none;
					display: flex;
					width: 100%;
					align-items: center;
					height: 40px;
					.iconfont {
						border-radius: 8px;
						padding: 0;
						box-shadow: 0px 3px 6px 1px #FFF0E8, inset 0px 3px 6px 1px #FFC1D3;
						color: #fff;
						background: linear-gradient( 180deg, #FFFFFF 0%, #FF8DB1 50%, #FF004E 100%);
						font-weight: 500;
						width: 66px;
						font-size: 48px;
						line-height: 66px;
						text-align: center;
						height: 66px;
					}
				}
				.right {
					display: flex;
					width: 100%;
					justify-content: center;
					flex-wrap: wrap;
					.num {
						margin: 5px 0;
						color: #333333;
						font-weight: 600;
						width: 100%;
						font-size: 36px;
						line-height: 24px;
						height: 24px;
						order: 3;
					}
					.name {
						margin: 20px 0 10px 0;
						color: #333333;
						width: 100%;
						font-size: 18px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis2:hover {
				box-shadow: 0px 4px 3px #8558ff50;
				transform: translate3d(0, 3px, 0);
				z-index: 1;
				background: rgba(255,255,255,1);
			}
			.statis3 {
				border: 0px solid #00000050;
				border-radius: 24px;
				padding: 25px 20px;
				margin: 10px;
				background: #fff;
				display: flex;
				width: calc(20% - 20px);
				justify-content: center;
				flex-wrap: wrap;
				transition: 0.3s;
				height: 228px;
				.left {
					background: none;
					display: flex;
					width: 100%;
					align-items: center;
					height: 40px;
					.iconfont {
						border-radius: 8px;
						padding: 0;
						box-shadow: 0px 3px 6px 1px #FFF0E8, inset 0px 3px 6px 1px #FFC1D3;
						color: #fff;
						background: linear-gradient( 180deg, #FFFFFF 0%, #FF8DB1 50%, #FF004E 100%);
						font-weight: 500;
						width: 66px;
						font-size: 48px;
						line-height: 66px;
						text-align: center;
						height: 66px;
					}
				}
				.right {
					display: flex;
					width: 100%;
					justify-content: center;
					flex-wrap: wrap;
					.num {
						margin: 5px 0;
						color: #333333;
						font-weight: 600;
						width: 100%;
						font-size: 36px;
						line-height: 24px;
						height: 24px;
						order: 3;
					}
					.name {
						margin: 5px 0;
						color: #333333;
						width: 100%;
						font-size: 18px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis3:hover {
				box-shadow: 0px 4px 3px #8558ff50;
				transform: translate3d(0, 3px, 0);
				z-index: 1;
				background: rgba(255,255,255,1);
			}
			.statis4 {
				border: 0px solid #00000050;
				border-radius: 24px;
				padding: 25px 20px;
				margin: 10px;
				background: #fff;
				display: flex;
				width: calc(20% - 20px);
				justify-content: center;
				flex-wrap: wrap;
				transition: 0.3s;
				height: 228px;
				.left {
					background: none;
					display: flex;
					width: 100%;
					align-items: center;
					height: 40px;
					.iconfont {
						border-radius: 8px;
						padding: 0;
						box-shadow: 0px 3px 6px 1px #FFF0E8, inset 0px 3px 6px 1px #FFF39F;
						color: #fff;
						background:  linear-gradient( 180deg, #FFD500 0%, #FDAF01 50%, #FA6B04 100%);
						font-weight: 500;
						width: 66px;
						font-size: 48px;
						line-height: 66px;
						text-align: center;
						height: 66px;
					}
				}
				.right {
					display: flex;
					width: 100%;
					justify-content: center;
					flex-wrap: wrap;
					.num {
						margin: 5px 0;
						color: #333333;
						font-weight: 600;
						width: 100%;
						font-size: 36px;
						line-height: 24px;
						height: 24px;
						order: 3;
					}
					.name {
						margin: 5px 0;
						color: #333333;
						width: 100%;
						font-size: 18px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis4:hover {
				box-shadow: 0px 4px 3px #8558ff50;
				transform: translate3d(0, 3px, 0);
				z-index: 1;
				background: rgba(255,255,255,1);
			}
			.statis5 {
				border: 0px solid #00000050;
				border-radius: 24px;
				padding: 25px 20px;
				margin: 10px;
				background: #fff;
				display: flex;
				width: calc(20% - 20px);
				justify-content: center;
				flex-wrap: wrap;
				transition: 0.3s;
				height: 228px;
				.left {
					background: none;
					display: flex;
					width: 100%;
					align-items: center;
					height: 40px;
					.iconfont {
						border-radius: 8px;
						padding: 0;
						box-shadow: 0px 3px 6px 1px #FFF0E8, inset 0px 3px 6px 1px #FFF39F;
						color: #fff;
						background:  linear-gradient( 180deg, #FFD500 0%, #FDAF01 50%, #FA6B04 100%);
						font-weight: 500;
						width: 66px;
						font-size: 48px;
						line-height: 66px;
						text-align: center;
						height: 66px;
					}
				}
				.right {
					display: flex;
					width: 100%;
					justify-content: center;
					flex-wrap: wrap;
					.num {
						margin: 5px 0;
						color: #333333;
						font-weight: 600;
						width: 100%;
						font-size: 36px;
						line-height: 24px;
						height: 24px;
						order: 3;
					}
					.name {
						margin: 5px 0;
						color: #333333;
						width: 100%;
						font-size: 18px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis5:hover {
				box-shadow: 0px 4px 3px #8558ff50;
				transform: translate3d(0, 3px, 0);
				z-index: 1;
				background: rgba(255,255,255,1);
			}
			.statis6 {
				border: 0px solid #00000050;
				border-radius: 24px;
				padding: 25px 20px;
				margin: 10px;
				background: #fff;
				display: flex;
				width: calc(20% - 20px);
				justify-content: center;
				flex-wrap: wrap;
				transition: 0.3s;
				height: 228px;
				.left {
					background: none;
					display: flex;
					width: 100%;
					align-items: center;
					height: 40px;
					.iconfont {
						border-radius: 8px;
						padding: 0;
						box-shadow: 0px 3px 6px 1px #FFF0E8, inset 0px 3px 6px 1px #FFC1D3;
						color: #fff;
						background: linear-gradient( 180deg, #FFFFFF 0%, #FF8DB1 50%, #FF004E 100%);
						font-weight: 500;
						width: 66px;
						font-size: 48px;
						line-height: 66px;
						text-align: center;
						height: 66px;
					}
				}
				.right {
					display: flex;
					width: 100%;
					justify-content: center;
					flex-wrap: wrap;
					.num {
						margin: 5px 0;
						color: #333333;
						font-weight: 600;
						width: 100%;
						font-size: 36px;
						line-height: 24px;
						height: 24px;
						order: 3;
					}
					.name {
						margin: 5px 0;
						color: #333333;
						width: 100%;
						font-size: 18px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis6:hover {
				box-shadow: 0px 4px 3px #8558ff50;
				transform: translate3d(0, 3px, 0);
				z-index: 1;
				background: rgba(255,255,255,1);
			}
			.statis7 {
				border: 0px solid #00000050;
				border-radius: 24px;
				padding: 25px 20px;
				margin: 10px;
				background: #fff;
				display: flex;
				width: calc(20% - 20px);
				justify-content: center;
				flex-wrap: wrap;
				transition: 0.3s;
				height: 228px;
				.left {
					background: none;
					display: flex;
					width: 100%;
					align-items: center;
					height: 40px;
					.iconfont {
						border-radius: 8px;
						padding: 0;
						box-shadow: 0px 3px 6px 1px #FFF0E8, inset 0px 3px 6px 1px #FFC1D3;
						color: #fff;
						background: linear-gradient( 180deg, #FFFFFF 0%, #FF8DB1 50%, #FF004E 100%);
						font-weight: 500;
						width: 66px;
						font-size: 48px;
						line-height: 66px;
						text-align: center;
						height: 66px;
					}
				}
				.right {
					display: flex;
					width: 100%;
					justify-content: center;
					flex-wrap: wrap;
					.num {
						margin: 5px 0;
						color: #333333;
						font-weight: 600;
						width: 100%;
						font-size: 36px;
						line-height: 24px;
						height: 24px;
						order: 3;
					}
					.name {
						margin: 5px 0;
						color: #333333;
						width: 100%;
						font-size: 18px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis7:hover {
				box-shadow: 0px 4px 3px #8558ff50;
				transform: translate3d(0, 3px, 0);
				z-index: 1;
				background: rgba(255,255,255,1);
			}
			.statis8 {
				border: 0px solid #00000050;
				border-radius: 24px;
				padding: 25px 20px;
				margin: 10px;
				background: #fff;
				display: flex;
				width: calc(20% - 20px);
				justify-content: center;
				flex-wrap: wrap;
				transition: 0.3s;
				height: 228px;
				.left {
					background: none;
					display: flex;
					width: 100%;
					align-items: center;
					height: 40px;
					.iconfont {
						border-radius: 8px;
						padding: 0;
						box-shadow: 0px 3px 6px 1px #FFF0E8, inset 0px 3px 6px 1px #FFF39F;
						color: #fff;
						background:  linear-gradient( 180deg, #FFD500 0%, #FDAF01 50%, #FA6B04 100%);
						font-weight: 500;
						width: 66px;
						font-size: 48px;
						line-height: 66px;
						text-align: center;
						height: 66px;
					}
				}
				.right {
					display: flex;
					width: 100%;
					justify-content: center;
					flex-wrap: wrap;
					.num {
						margin: 5px 0;
						color: #333333;
						font-weight: 600;
						width: 100%;
						font-size: 36px;
						line-height: 24px;
						height: 24px;
						order: 3;
					}
					.name {
						margin: 5px 0;
						color: #333333;
						width: 100%;
						font-size: 18px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis8:hover {
				box-shadow: 0px 4px 3px #8558ff50;
				transform: translate3d(0, 3px, 0);
				z-index: 1;
				background: rgba(255,255,255,1);
			}
			.statis9 {
				border: 0px solid #00000050;
				border-radius: 24px;
				padding: 25px 20px;
				margin: 10px;
				background: #fff;
				display: flex;
				width: calc(20% - 20px);
				justify-content: center;
				flex-wrap: wrap;
				transition: 0.3s;
				height: 228px;
				.left {
					background: none;
					display: flex;
					width: 100%;
					align-items: center;
					height: 40px;
					.iconfont {
						border-radius: 8px;
						padding: 0;
						box-shadow: 0px 3px 6px 1px #FFF0E8, inset 0px 3px 6px 1px #FFF39F;
						color: #fff;
						background:  linear-gradient( 180deg, #FFD500 0%, #FDAF01 50%, #FA6B04 100%);
						font-weight: 500;
						width: 66px;
						font-size: 48px;
						line-height: 66px;
						text-align: center;
						height: 66px;
					}
				}
				.right {
					display: flex;
					width: 100%;
					justify-content: center;
					flex-wrap: wrap;
					.num {
						margin: 5px 0;
						color: #333333;
						font-weight: 600;
						width: 100%;
						font-size: 36px;
						line-height: 24px;
						height: 24px;
						order: 3;
					}
					.name {
						margin: 5px 0;
						color: #333333;
						width: 100%;
						font-size: 18px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis9:hover {
				box-shadow: 0px 4px 3px #8558ff50;
				transform: translate3d(0, 3px, 0);
				z-index: 1;
				background: rgba(255,255,255,1);
			}
			.statis10 {
				border: 0px solid #00000050;
				border-radius: 24px;
				padding: 25px 20px;
				margin: 10px;
				background: #fff;
				display: flex;
				width: calc(20% - 20px);
				justify-content: center;
				flex-wrap: wrap;
				transition: 0.3s;
				height: 228px;
				.left {
					background: none;
					display: flex;
					width: 100%;
					align-items: center;
					height: 40px;
					.iconfont {
						border-radius: 8px;
						padding: 0;
						box-shadow: 0px 3px 6px 1px #FFF0E8, inset 0px 3px 6px 1px #FFC1D3;
						color: #fff;
						background: linear-gradient( 180deg, #FFFFFF 0%, #FF8DB1 50%, #FF004E 100%);
						font-weight: 500;
						width: 66px;
						font-size: 48px;
						line-height: 66px;
						text-align: center;
						height: 66px;
					}
				}
				.right {
					display: flex;
					width: 100%;
					justify-content: center;
					flex-wrap: wrap;
					.num {
						margin: 5px 0;
						color: #333333;
						font-weight: 600;
						width: 100%;
						font-size: 36px;
						line-height: 24px;
						height: 24px;
						order: 3;
					}
					.name {
						margin: 5px 0;
						color: #333333;
						width: 100%;
						font-size: 18px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis10:hover {
				box-shadow: 0px 4px 3px #8558ff50;
				transform: translate3d(0, 3px, 0);
				z-index: 1;
				background: rgba(255,255,255,1);
			}
		}
		// echarts2
		.type2 {
			padding: 0;
			align-content: flex-start;
			background: none;
			display: flex;
			width: 100%;
			justify-content: space-between;
			flex-wrap: wrap;
			height: auto;
			.echarts1 {
				border-radius: 24px;
				padding: 20px;
				box-shadow: 0px 3px 6px 1px #FFEAD8;
				margin: 10px;
				background: rgba(255,255,255,1);
				width: calc(50% - 20px);
				transition: 0.3s;
				height: 486px;
			}
			.echarts1:hover {
				box-shadow: 0px 3px 6px 1px #FFEAD8;
				transform: translate3d(0, 3px, 0);
				z-index: 1;
				background: rgba(255,255,255,1);
			}
			.echarts2 {
				border-radius: 24px;
				padding: 20px;
				box-shadow: 0px 3px 6px 1px #FFEAD8;
				margin: 10px;
				background: rgba(255,255,255,1);
				width: calc(50% - 20px);
				transition: 0.3s;
				height: 486px;
			}
			.echarts2:hover {
				box-shadow: 0px 3px 6px 1px #FFEAD8;
				transform: translate3d(0, 3px, 0);
				z-index: 1;
				background: rgba(255,255,255,1);
			}
		}
	}
	
	.echarts-flag-2 {
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
		padding: 10px 20px;
		background: rebeccapurple;
	
		&>div {
			width: 32%;
			height: 300px;
			margin: 10px 0;
			background: rgba(255,255,255,.1);
			border-radius: 8px;
			padding: 10px 20px;
		}
	}
	.animate__animated {
		animation-fill-mode: none;
	}
	.top5-table {
    width: 100%;
    border-collapse: collapse;
}
.top5-table th, .top5-table td {
    border: 1px solid #eee;
    padding: 10px;
    text-align: center;
}
.top5-table th {
    background-color: #f5f5f5;
}
