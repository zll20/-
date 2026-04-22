  

<template>
<view class="content">
	<view :style='{"minHeight":"100%","width":"100%","padding":"0","position":"relative","background":"#F6F7F9","height":"auto"}' v-if="showType==1">
		<form :style='{"width":"100%","padding":"24rpx","background":"none","display":"block","height":"auto"}' class="app-update-pv">












			<view :style='{"padding":"12rpx 0","margin":"0 0 10rpx","borderColor":"#ccc","alignItems":"flex-start","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#000","textAlign":"left"}' class="title">姓名</view>
				<input :style='{"border":"2rpx solid #3D3D3D","padding":"0px 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.xingming" v-model="ruleForm.xingming" placeholder="姓名"  type="text"></input>
			</view>
			<view :style='{"padding":"12rpx 0","margin":"0 0 10rpx","borderColor":"#ccc","alignItems":"flex-start","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#000","textAlign":"left"}' class="title">学号</view>
				<input :style='{"border":"2rpx solid #3D3D3D","padding":"0px 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.xuehao" v-model="ruleForm.xuehao" placeholder="学号"  type="text"></input>
			</view>
			<view :style='{"padding":"12rpx 0","margin":"0 0 10rpx","borderColor":"#ccc","alignItems":"flex-start","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#000","textAlign":"left"}' class="title">日期</view>
				<input :disabled="ro.riqi" :style='{"border":"2rpx solid #3D3D3D","padding":"0px 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' v-model="ruleForm.riqi" placeholder="日期" @tap="toggleTab('riqi')"></input>
			</view>
			<view :style='{"padding":"12rpx 0","margin":"0 0 10rpx","borderColor":"#ccc","alignItems":"flex-start","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#000","textAlign":"left"}' class="title">学习时长</view>
				<input :style='{"border":"2rpx solid #3D3D3D","padding":"0px 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.xuexishizhang" v-model="ruleForm.xuexishizhang" placeholder="学习时长" type="digit" @input="numberChange"></input>
			</view>
			<view :style='{"padding":"12rpx 0","margin":"0 0 10rpx","borderColor":"#ccc","alignItems":"flex-start","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#000","textAlign":"left"}' class="title">单词量</view>
				<input :style='{"border":"2rpx solid #3D3D3D","padding":"0px 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.danciliang" v-model="ruleForm.danciliang" placeholder="单词量" type="number" @input="numberChange"></input>
			</view>
			<view :style='{"padding":"12rpx 0","margin":"0 0 10rpx","borderColor":"#ccc","alignItems":"flex-start","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class=" select">
				<view :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#000","textAlign":"left"}' class="title">是否记住</view>
				<picker :disabled="ro.shifoujizhu" :style='{"border":"2rpx solid #3D3D3D","width":"100%","padding":"0 24rpx","borderRadius":"8rpx","flex":"1","height":"auto"}' @change="shifoujizhuChange" :value="shifoujizhuIndex" :range="shifoujizhuOptions">
					<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"28rpx","color":"#000"}' class="uni-input">{{ruleForm.shifoujizhu?ruleForm.shifoujizhu:"请选择是否记住"}}</view>
				</picker>
			</view>
      
			
			<view :style='{"width":"100%","padding":"10rpx 0 10rpx 0","flexWrap":"wrap","justifyContent":"center","display":"flex","height":"auto"}' class="btn" >
				<button :style='{"border":"0","padding":"0px","margin":"0 0 20rpx 0","color":"#fff","borderRadius":"8rpx","background":"#FFD128","width":"100%","lineHeight":"80rpx","fontSize":"32rpx","height":"80rpx"}' @tap="onSubmitTap(null)" class="bg-red">提交</button>
			</view>
		</form>
		<w-picker  mode="date" step="1" :current="false" :hasSecond="false" @confirm="riqiConfirm" ref="riqi" themeColor="#333333"></w-picker>
	</view>
	<view v-else-if="showType==2">
		<view style="width: 100%;display: flex;flex-wrap: wrap;padding: 10px;">
			<button style="margin: 0 10px 0 0" v-if="(userid&&isAuth('dancixuexi','学习分析'))||(!userid&&isAuthFront('dancixuexi','学习分析'))" size="mini" type="primary" @click="chartClick1()">
				学习分析
			</button>
			<button style="margin: 0 10px 0 0" v-if="(userid&&isAuth('dancixuexi','学习时长'))||(!userid&&isAuthFront('dancixuexi','学习时长'))" size="mini" type="primary" @click="chartClick2()">
				学习时长
			</button>
		</view>
		<view style="width: 100%;">
			<view v-if="chartType1" style="width: 100%;text-align: center;line-height: 2;font-size: 12px;color: #000">
				学习分析
			</view>
			<qiun-data-charts
				v-if="chartType1"
				style="width: 100%;height:"
				type="pie"
				:opts="opts1"
				:chartData="chartData1">
			</qiun-data-charts>
			<view v-if="chartType2" style="width: 100%;text-align: center;line-height: 2;font-size: 12px;color: #000">
				学习时长
			</view>
			<qiun-data-charts
				v-if="chartType2"
				style="width: 100%;height:"
				type="column"
				:opts="opts2"
				:chartData="chartData2">
			</qiun-data-charts>
		</view>
	</view>
</view>
</template>

<script>
	import wPicker from "@/components/w-picker/w-picker.vue";
	import xiaEditor from '@/components/xia-editor/xia-editor';
	import multipleSelect from "@/components/momo-multipleSelect/momo-multipleSelect";
	import qiunDataCharts from '@/components/qiun-data-charts/components/qiun-data-charts/qiun-data-charts.vue';
	export default {
		data() {
			return {
				cross:'',
				ruleForm: {
				xingming: '',
				xuehao: '',
				riqi: '',
				xuexishizhang: Number('2') ,
				danciliang: Number('50') ,
				shifoujizhu: '',
				},
				shifoujizhuOptions: [],
				shifoujizhuIndex: 0,
				// 登录用户信息
				user: {},
				ro:{
				   xingming : false,
				   xuehao : false,
				   riqi : false,
				   xuexishizhang : false,
				   danciliang : false,
				   shifoujizhu : false,
				},
				virtualPay: false,
				showType: 1,
				userid: '',
				chartData1: {},
				opts1: {},
				chartType1: false,
				chartData2: {},
				opts2: {},
				chartType2: false,
			}
		},
		components: {
			wPicker,
			xiaEditor,
			multipleSelect,
			qiunDataCharts,
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			},
			sessionForm() {
				return uni.getStorageSync("appUserid")?JSON.parse(uni.getStorageSync('userSession')):{}
			},



		},
		async onLoad(options) {
			if(options.showType) {
				this.showType = 2
				if(options.userid&&options.userid!='') {
					this.userid = options.userid
				}
				return false
			}else {
				this.showType = 1
			}
			if(options.virtualPay){
				this.virtualPay = true
			}
			this.ruleForm.riqi = this.$utils.getCurDate();
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			
			// ss读取
			this.ruleForm.xingming = this.user.xingming
			this.ro.xingming = true;
			this.ruleForm.xuehao = this.user.xuehao
			this.ro.xuehao = true;

			// 跨表
			this.cross = options.cross;
			if(options.cross){
				var obj = uni.getStorageSync('crossObj');
				for (var o in obj){
					if(o=='xingming'){
						this.ruleForm.xingming = obj[o];
						this.ro.xingming = true;
						continue;
					}
					if(o=='xuehao'){
						this.ruleForm.xuehao = obj[o];
						this.ro.xuehao = true;
						continue;
					}
					if(o=='riqi'){
						this.ruleForm.riqi = obj[o];
						this.ro.riqi = true;
						continue;
					}
					if(o=='xuexishizhang'){
						this.ruleForm.xuexishizhang = obj[o];
						this.ro.xuexishizhang = true;
						continue;
					}
					if(o=='danciliang'){
						this.ruleForm.danciliang = obj[o];
						this.ro.danciliang = true;
						continue;
					}
					if(o=='shifoujizhu'){
						this.ruleForm.shifoujizhu = obj[o];
						for(let x in this.shifoujizhuOptions) {
							if(this.shifoujizhuOptions[x] == this.ruleForm.shifoujizhu) {
								this.shifoujizhuIndex = Number(x)
							}
						}
						this.ro.shifoujizhu = true;
						continue;
					}
				}
			}

			// 自定义下拉框值
			this.shifoujizhuOptions = "已记住,未记住".split(',')
			this.shifoujizhuOptions.unshift("请选择是否记住");
			if(this.ruleForm.shifoujizhu) {
				for(let x in this.shifoujizhuOptions) {
					if(this.shifoujizhuOptions[x] == this.ruleForm.shifoujizhu) {
						this.shifoujizhuIndex = Number(x)
					}
				}
			}

			// 如果有登录，获取登录后保存的userid
			this.ruleForm.userid = uni.getStorageSync("appUserid")
			if (options.refid) {
				// 如果上一级页面传递了refid，获取改refid数据信息
				this.ruleForm.refid = Number(options.refid);
				this.ruleForm.nickname = uni.getStorageSync("nickname");
			}
			// 如果是更新操作
			if (options.id) {
				this.ruleForm.id = options.id;
				// 获取信息
				res = await this.$api.info(`dancixuexi`, this.ruleForm.id);
				if(res.data.shifoujizhu) {
					for(let x in this.shifoujizhuOptions) {
						if(this.shifoujizhuOptions[x] == res.data.shifoujizhu) {
							this.shifoujizhuIndex = Number(x)
						}
					}
				}
				this.ruleForm = res.data;
			}
			this.$forceUpdate()
			if (uni.getStorageSync('raffleType') && uni.getStorageSync('raffleType') != null) {
				uni.removeStorageSync('raffleType')
				setTimeout(() => {
					this.onSubmitTap(null)
				}, 300)
			}
		},
		methods: {
			numberChange(e) {
				e = Number(e)
			},

			// 多级联动参数

			riqiChange(e) {
				this.ruleForm.riqi = e.target.value;
				this.$forceUpdate();
			},

			// 日长控件选择日期时间
			riqiConfirm(val) {
				this.ruleForm.riqi = val.result;
				this.$forceUpdate();
			},

			// 下拉变化
			shifoujizhuChange(e) {
				this.shifoujizhuIndex = e.target.value
				this.ruleForm.shifoujizhu = this.shifoujizhuOptions[this.shifoujizhuIndex]
			},


			getUUID () {
				return new Date().getTime();
			},
			async onSubmitTap(subMitType=null) {
				let that = this
				//跨表计算判断
				var obj;
				if(this.ruleForm.xuexishizhang&&(!this.$validate.isNumber(this.ruleForm.xuexishizhang))){
					this.$utils.msg(`学习时长应输入数字`);
					return
				}
				if(this.ruleForm.danciliang&&(!this.$validate.isIntNumer(this.ruleForm.danciliang))){
					this.$utils.msg(`单词量应输入整数`);
					return
				}
				//更新跨表属性
				var crossuserid;
				var crossrefid;
				var crossoptnum;
				if(this.cross){
					var statusColumnName = uni.getStorageSync('statusColumnName');
					var statusColumnValue = uni.getStorageSync('statusColumnValue');
					if(statusColumnName!='') {
						if(!obj) {
							obj = uni.getStorageSync('crossObj');
						}
						if(!statusColumnName.startsWith("[")) {
							for (var o in obj){
								if(o==statusColumnName){
									obj[o] = statusColumnValue;
								}
							}
							var table = uni.getStorageSync('crossTable');
							await this.$api.update(`${table}`, obj);
						} else {
								crossuserid=Number(uni.getStorageSync('appUserid'));
								crossrefid=obj['id'];
								crossoptnum=uni.getStorageSync('statusColumnName');
								crossoptnum=crossoptnum.replace(/\[/,"").replace(/\]/,"");
						}
					}
					if(crossrefid && crossuserid) {
						this.ruleForm.crossuserid=crossuserid;
						this.ruleForm.crossrefid=crossrefid;
						let params = {
							page: 1,
							limit:10,
							crossuserid:crossuserid,
							crossrefid:crossrefid,
						}
						let res = await this.$api.list(`dancixuexi`, params);
						if (res.data.total >= crossoptnum) {
							this.$utils.msg(uni.getStorageSync('tips'));
							uni.removeStorageSync('crossCleanType');
							return false;
						}
					}
					//跨表计算
				}
				let oet = {}
				if(this.ruleForm.id){
					await this.$api.update(`dancixuexi`, this.ruleForm);
				}else{
					oet = await this.$api.add(`dancixuexi`, this.ruleForm);
				}
				if(this.cross){
					uni.setStorageSync('crossCleanType',true);
				}
				that.$utils.msgBack('提交成功');
			},
			optionsChange(e) {
				this.index = e.target.value
			},
			bindDateChange(e) {
				this.date = e.target.value
			},
			getDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();
				if (type === 'start') {
					year = year - 60;
				} else if (type === 'end') {
					year = year + 2;
				}
				month = month > 9 ? month : '0' + month;;
				day = day > 9 ? day : '0' + day;
				return `${year}-${month}-${day}`;
			},
			toggleTab(str) {
				if(this.ro[str]){
					return false
				}
				this.$refs[str].show();
			},
			async chartClick1() {
				let res = await this.$api.getPublic('dancixuexi/group/shifoujizhu')
				res = res.data
				let xAxis = [];
				let yAxis = [];
				let pArray = []
				for(let i=0;i<res.length;i++){
					xAxis.push(res[i]['shifoujizhu']);
					yAxis.push(parseFloat((res[i].total)));
					pArray.push({
						value: parseFloat((res[i].total)),
						name: res[i]['shifoujizhu']
					})
				}
				this.opts1 = {
					color: ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"],
					padding: [5,5,5,5],
					enableScroll: false,
					extra: {
						pie: {
							activeOpacity: 0.5,
							activeRadius: 10,
							offsetAngle: 0,
							labelWidth: 15,
							border: false,
							borderWidth: 3,
							borderColor: "#FFFFFF"
						}
					}
				}
				this.chartData1 = {
					series: [
						{
							data: pArray
						}
					]
				};
				this.chartType1 = true
			},
			async chartClick2() {
				let res = await this.$api.getPublic('dancixuexi/value/riqi/xuexishizhang')
				res = res.data
				let xAxis = [];
				let yAxis = [];
				let pArray = []
				for(let i=0;i<res.length;i++){
					xAxis.push(res[i]['riqi']);
					yAxis.push(parseFloat((res[i].total)));
					pArray.push({
						value: parseFloat((res[i].total)),
						name: res[i]['riqi']
					})
				}
				this.opts2 = {
					color: ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"],
					padding: [15,15,10,5],
					enableScroll: false,
					xAxis: {
						disableGrid: true,
						rotateLabel: true,
						rotateAngle: 30
					},
					legend: {
						show: false
					},
					yAxis: {},
					extra: {
						column: {
							type: "group",
							width: 30,
							activeBgColor: "#000000",
							activeBgOpacity: 0.08
						}
					}
				}
				this.chartData2 = {
					categories: xAxis,
					series: [
						{
							data: yAxis
						}
					]
				};
				this.chartType2 = true
			},
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
	}
</style>
