  

<template>
<view class="content">
	<view :style='{"minHeight":"100%","width":"100%","padding":"0","position":"relative","background":"#F6F7F9","height":"auto"}' v-if="showType==1">
		<form :style='{"width":"100%","padding":"24rpx","background":"none","display":"block","height":"auto"}' class="app-update-pv">


















			<view :style='{"padding":"12rpx 0","margin":"0 0 10rpx","borderColor":"#ccc","alignItems":"flex-start","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#000","textAlign":"left"}' class="title">单词</view>
				<input :style='{"border":"2rpx solid #3D3D3D","padding":"0px 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.danci" v-model="ruleForm.danci" placeholder="单词"  type="text"></input>
			</view>
			<view :style='{"padding":"12rpx 0","margin":"0 0 10rpx","borderColor":"#ccc","alignItems":"flex-start","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class=" select">
				<view :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#000","textAlign":"left"}' class="title">级别</view>
				<picker :disabled="ro.jibie" :style='{"border":"2rpx solid #3D3D3D","width":"100%","padding":"0 24rpx","borderRadius":"8rpx","flex":"1","height":"auto"}' @change="jibieChange" :value="jibieIndex" :range="jibieOptions">
					<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"28rpx","color":"#000"}' class="uni-input">{{ruleForm.jibie?ruleForm.jibie:"请选择级别"}}</view>
				</picker>
			</view>
			<view :style='{"padding":"12rpx 0","margin":"0 0 10rpx","borderColor":"#ccc","alignItems":"flex-start","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class=" select">
				<view :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#000","textAlign":"left"}' class="title">单词分类</view>
				<picker :disabled="ro.dancifenlei" :style='{"border":"2rpx solid #3D3D3D","width":"100%","padding":"0 24rpx","borderRadius":"8rpx","flex":"1","height":"auto"}' @change="dancifenleiChange" :value="dancifenleiIndex" :range="dancifenleiOptions">
					<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"28rpx","color":"#000"}' class="uni-input">{{ruleForm.dancifenlei?ruleForm.dancifenlei:"请选择单词分类"}}</view>
				</picker>
			</view>
			<view :style='{"padding":"12rpx 0","margin":"0 0 10rpx","borderColor":"#ccc","alignItems":"flex-start","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#000","textAlign":"left"}' class="title">释义</view>
				<input :style='{"border":"2rpx solid #3D3D3D","padding":"0px 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.shiyi" v-model="ruleForm.shiyi" placeholder="释义"  type="text"></input>
			</view>
			<view :style='{"padding":"12rpx 0","margin":"0 0 10rpx","borderColor":"#ccc","alignItems":"flex-start","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#000","textAlign":"left"}' class="title">例句</view>
				<input :style='{"border":"2rpx solid #3D3D3D","padding":"0px 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.liju" v-model="ruleForm.liju" placeholder="例句"  type="text"></input>
			</view>
			<view :style='{"padding":"12rpx 0","margin":"0 0 10rpx","borderColor":"#ccc","alignItems":"flex-start","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="" @tap="fengmiantuTap">
				<view :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#000","textAlign":"left"}' class="title">封面图</view>
				<image :style='{"width":"80rpx","borderRadius":"100%","objectFit":"cover","display":"block","height":"80rpx"}' class="avator" v-if="ruleForm.fengmiantu" :src="baseUrl+ruleForm.fengmiantu.split(',')[0]" mode="aspectFill"></image>
				<image :style='{"width":"80rpx","borderRadius":"100%","objectFit":"cover","display":"block","height":"80rpx"}' class="avator" v-else src="../../static/gen/upload.png" mode="aspectFill"></image>
			</view>
         
			
			<view :style='{"width":"100%","padding":"10rpx 0 10rpx 0","flexWrap":"wrap","justifyContent":"center","display":"flex","height":"auto"}' class="btn" >
				<button :style='{"border":"0","padding":"0px","margin":"0 0 20rpx 0","color":"#fff","borderRadius":"8rpx","background":"#FFD128","width":"100%","lineHeight":"80rpx","fontSize":"32rpx","height":"80rpx"}' @tap="onSubmitTap(null)" class="bg-red">提交</button>
			</view>
		</form>
		<w-picker  mode="dateTime" step="1" :current="false" :hasSecond="false" @confirm="clicktimeConfirm" ref="clicktime" themeColor="#333333"></w-picker>
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
				danci: '',
				jibie: '',
				dancifenlei: '',
				shiyi: '',
				liju: '',
				fengmiantu: '',
				discussnum: '',
				storeupnum: '',
				},
				jibieOptions: [],
				jibieIndex: 0,
				dancifenleiOptions: [],
				dancifenleiIndex: 0,
				// 登录用户信息
				user: {},
				ro:{
				   danci : false,
				   jibie : false,
				   dancifenlei : false,
				   shiyi : false,
				   liju : false,
				   fengmiantu : false,
				   clicktime : false,
				   discussnum : false,
				   storeupnum : false,
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
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			
			// ss读取

			// 跨表
			this.cross = options.cross;
			if(options.cross){
				var obj = uni.getStorageSync('crossObj');
				for (var o in obj){
					if(o=='danci'){
						this.ruleForm.danci = obj[o];
						this.ro.danci = true;
						continue;
					}
					if(o=='jibie'){
						this.ruleForm.jibie = obj[o];
						for(let x in this.jibieOptions) {
							if(this.jibieOptions[x] == this.ruleForm.jibie) {
								this.jibieIndex = Number(x)
							}
						}
						this.ro.jibie = true;
						continue;
					}
					if(o=='dancifenlei'){
						this.ruleForm.dancifenlei = obj[o];
						for(let x in this.dancifenleiOptions) {
							if(this.dancifenleiOptions[x] == this.ruleForm.dancifenlei) {
								this.dancifenleiIndex = Number(x)
							}
						}
						this.ro.dancifenlei = true;
						continue;
					}
					if(o=='shiyi'){
						this.ruleForm.shiyi = obj[o];
						this.ro.shiyi = true;
						continue;
					}
					if(o=='liju'){
						this.ruleForm.liju = obj[o];
						this.ro.liju = true;
						continue;
					}
					if(o=='fengmiantu'){
						this.ruleForm.fengmiantu = obj[o].split(",")[0];
						this.ro.fengmiantu = true;
						continue;
					}
					if(o=='clicktime'){
						this.ruleForm.clicktime = obj[o];
						this.ro.clicktime = true;
						continue;
					}
					if(o=='discussnum'){
						this.ruleForm.discussnum = obj[o];
						this.ro.discussnum = true;
						continue;
					}
					if(o=='storeupnum'){
						this.ruleForm.storeupnum = obj[o];
						this.ro.storeupnum = true;
						continue;
					}
				}
			}

			// 自定义下拉框值
			this.jibieOptions = "四级,六级".split(',')
			this.jibieOptions.unshift("请选择级别");
			if(this.ruleForm.jibie) {
				for(let x in this.jibieOptions) {
					if(this.jibieOptions[x] == this.ruleForm.jibie) {
						this.jibieIndex = Number(x)
					}
				}
			}
			var dancifenleiRefParams = {}
			// 下拉框
			res = await this.$api.option(`dancifenlei`,`dancifenlei`,dancifenleiRefParams);
			this.dancifenleiOptions = res.data;
			this.dancifenleiOptions.unshift("请选择单词分类");
			if(this.ruleForm.dancifenlei) {
				for(let x in this.dancifenleiOptions) {
					if(this.dancifenleiOptions[x] == this.ruleForm.dancifenlei) {
						this.dancifenleiIndex = Number(x)
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
				res = await this.$api.info(`danciku`, this.ruleForm.id);
				if(res.data.jibie) {
					for(let x in this.jibieOptions) {
						if(this.jibieOptions[x] == res.data.jibie) {
							this.jibieIndex = Number(x)
						}
					}
				}
				if(res.data.dancifenlei) {
					for(let x in this.dancifenleiOptions) {
						if(this.dancifenleiOptions[x] == res.data.dancifenlei) {
							this.dancifenleiIndex = Number(x)
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


			// 日长控件选择日期时间
			clicktimeConfirm(val) {
				this.ruleForm.clicktime = val.result;
				this.$forceUpdate();
			},

			// 下拉变化
			jibieChange(e) {
				this.jibieIndex = e.target.value
				this.ruleForm.jibie = this.jibieOptions[this.jibieIndex]
			},
			// 下拉变化
			dancifenleiChange(e) {
				this.dancifenleiIndex = e.target.value
				this.ruleForm.dancifenlei = this.dancifenleiOptions[this.dancifenleiIndex]
			},

			fengmiantuTap() {
				if(this.ro.fengmiantu){
					return false
				}
				let _this = this;
				this.$api.upload(function(res) {
					_this.ruleForm.fengmiantu = 'upload/' + res.file;
					_this.$forceUpdate();
				});
			},

			getUUID () {
				return new Date().getTime();
			},
			async onSubmitTap(subMitType=null) {
				let that = this
				//跨表计算判断
				var obj;
				if((!this.ruleForm.danci)){
					this.$utils.msg(`单词不能为空`);
					return
				}
				if((!this.ruleForm.jibie)){
					this.$utils.msg(`级别不能为空`);
					return
				}
				if(this.ruleForm.discussnum&&(!this.$validate.isIntNumer(this.ruleForm.discussnum))){
					this.$utils.msg(`评论数应输入整数`);
					return
				}
				if(this.ruleForm.storeupnum&&(!this.$validate.isIntNumer(this.ruleForm.storeupnum))){
					this.$utils.msg(`收藏数应输入整数`);
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
						let res = await this.$api.list(`danciku`, params);
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
					await this.$api.update(`danciku`, this.ruleForm);
				}else{
					oet = await this.$api.add(`danciku`, this.ruleForm);
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
