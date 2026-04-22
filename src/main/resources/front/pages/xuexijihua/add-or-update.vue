  

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
				<view :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#000","textAlign":"left"}' class="title">学习任务</view>
				<input :style='{"border":"2rpx solid #3D3D3D","padding":"0px 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.xuexirenwu" v-model="ruleForm.xuexirenwu" placeholder="学习任务"  type="text"></input>
			</view>
			<view :style='{"padding":"12rpx 0","margin":"0 0 10rpx","borderColor":"#ccc","alignItems":"flex-start","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#000","textAlign":"left"}' class="title">复习计划</view>
				<input :style='{"border":"2rpx solid #3D3D3D","padding":"0px 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.fuxijihua" v-model="ruleForm.fuxijihua" placeholder="复习计划"  type="text"></input>
			</view>
      
			
			<view :style='{"width":"100%","padding":"10rpx 0 10rpx 0","flexWrap":"wrap","justifyContent":"center","display":"flex","height":"auto"}' class="btn" >
				<button :style='{"border":"0","padding":"0px","margin":"0 0 20rpx 0","color":"#fff","borderRadius":"8rpx","background":"#FFD128","width":"100%","lineHeight":"80rpx","fontSize":"32rpx","height":"80rpx"}' @tap="onSubmitTap(null)" class="bg-red">提交</button>
			</view>
		</form>
		<w-picker  mode="date" step="1" :current="false" :hasSecond="false" @confirm="riqiConfirm" ref="riqi" themeColor="#333333"></w-picker>
	</view>
</view>
</template>

<script>
	import wPicker from "@/components/w-picker/w-picker.vue";
	import xiaEditor from '@/components/xia-editor/xia-editor';
	import multipleSelect from "@/components/momo-multipleSelect/momo-multipleSelect";
	export default {
		data() {
			return {
				cross:'',
				ruleForm: {
				xingming: '',
				xuehao: '',
				riqi: '',
				xuexishizhang: Number('2') ,
				xuexirenwu: '',
				fuxijihua: '',
				},
				// 登录用户信息
				user: {},
				ro:{
				   xingming : false,
				   xuehao : false,
				   riqi : false,
				   xuexishizhang : false,
				   xuexirenwu : false,
				   fuxijihua : false,
				},
				virtualPay: false,
				showType: 1,
			}
		},
		components: {
			wPicker,
			xiaEditor,
			multipleSelect,
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
					if(o=='xuexirenwu'){
						this.ruleForm.xuexirenwu = obj[o];
						this.ro.xuexirenwu = true;
						continue;
					}
					if(o=='fuxijihua'){
						this.ruleForm.fuxijihua = obj[o];
						this.ro.fuxijihua = true;
						continue;
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
				res = await this.$api.info(`xuexijihua`, this.ruleForm.id);
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
						let res = await this.$api.list(`xuexijihua`, params);
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
					await this.$api.update(`xuexijihua`, this.ruleForm);
				}else{
					oet = await this.$api.add(`xuexijihua`, this.ruleForm);
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
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
	}
</style>
