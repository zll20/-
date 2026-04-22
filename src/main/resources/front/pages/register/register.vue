<template>
<view class="content">
	<view class="register-box" :style="{'backgroundImage': indexBgUrl?`url(${$base.url + indexBgUrl})`:''}">
		<view :style='{"width":"100%","padding":"0 60rpx","position":"relative","display":"block","height":"auto"}'>
			<image :style='{"width":"160rpx","margin":"0 auto 20rpx auto","borderRadius":"8rpx","display":"block","height":"160rpx"}' :src="indexLogoUrl?($base.url + indexLogoUrl):'http://codegen.caihongy.cn/20250814/f9973b923f0e40a9ad6ac1e302b50e9f.jpg'" mode="aspectFill"></image>
			<view :style='{"padding":"20rpx 0","margin":"0","borderColor":"#ffffff","background":"#a2a2a280","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" class="uni-form-item uni-column">
				<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">学号：</view>
				<input :style='{"border":"0px solid rgb(255, 170, 51)","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"60rpx"}' :disabled="ro.xuehao" v-model="ruleForm.xuehao"  type="text"  class="uni-input" name="" placeholder="学号" />
			</view>
			<view :style='{"padding":"20rpx 0","margin":"0","borderColor":"#ffffff","background":"#a2a2a280","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" class="uni-form-item uni-column">
				<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">密码：</view>
				<input :style='{"border":"0px solid rgb(255, 170, 51)","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"60rpx"}' :disabled="ro.mima" v-model="ruleForm.mima" type="password"  class="uni-input" name="" placeholder="密码" />
			</view>
			<view :style='{"padding":"20rpx 0","margin":"0","borderColor":"#ffffff","background":"#a2a2a280","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" class="uni-form-item uni-column">
				<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">确认密码：</view>
				<input :style='{"border":"0px solid rgb(255, 170, 51)","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"60rpx"}' v-model="ruleForm.mima2" type="password" class="uni-input" name="" placeholder="确认密码" />
			</view>
			<view :style='{"padding":"20rpx 0","margin":"0","borderColor":"#ffffff","background":"#a2a2a280","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" class="uni-form-item uni-column">
				<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">姓名：</view>
				<input :style='{"border":"0px solid rgb(255, 170, 51)","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"60rpx"}' :disabled="ro.xingming" v-model="ruleForm.xingming"  type="text"  class="uni-input" name="" placeholder="姓名" />
			</view>
			<view :style='{"padding":"20rpx 0","margin":"0","borderColor":"#ffffff","background":"#a2a2a280","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">性别：</view>
				<picker :style='{"width":"100%","padding":"0 20rpx","margin":"0","flex":"1","display":"flex","height":"auto"}' v-if="tableName=='xuesheng'" :disabled="ro.xingbie" @change="xueshengxingbieChange" :value="xueshengxingbieIndex" :range="xueshengxingbieOptions">
					<view>
						<view :style='{"lineHeight":"60rpx","fontSize":"28rpx","color":"#000","flex":"1"}' class="uni-input">{{ruleForm.xingbie?ruleForm.xingbie:"请选择性别"}}</view>
					</view>
				</picker>
			</view>
			<view :style='{"padding":"20rpx 0","margin":"0","borderColor":"#ffffff","background":"#a2a2a280","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" @tap="xueshengtouxiangTap" class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">头像：</view>
				<image :style='{"width":"100rpx","margin":"0 0 0 20rpx","borderRadius":"16rpx","display":"block","height":"100rpx"}' v-if="ruleForm.touxiang" class="avator" :src="baseUrl+ruleForm.touxiang" mode=""></image>
				<image :style='{"width":"100rpx","margin":"0 0 0 20rpx","borderRadius":"16rpx","display":"block","height":"100rpx"}' v-else class="avator" src="../../static/gen/upload.png" mode=""></image>
			</view>
			<view :style='{"padding":"20rpx 0","margin":"0","borderColor":"#ffffff","background":"#a2a2a280","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" class="uni-form-item uni-column">
				<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">邮箱：</view>
				<input :style='{"border":"0px solid rgb(255, 170, 51)","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"60rpx"}' :disabled="ro.youxiang" v-model="ruleForm.youxiang"  type="text"  class="uni-input" name="" placeholder="邮箱" />
			</view>
			<view :style='{"padding":"20rpx 0","margin":"0","borderColor":"#ffffff","background":"#a2a2a280","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" class="uni-form-item uni-column">
				<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">手机：</view>
				<input :style='{"border":"0px solid rgb(255, 170, 51)","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"60rpx"}' v-model="ruleForm.mobile" type="text" class="uni-input" name="" placeholder="手机号码" />
			</view>
			<view :style='{"padding":"20rpx 10rpx 20rpx 0","margin":"0","borderColor":"#ffffff","background":"#a2a2a280","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng' && true" class="uni-form-item uni-column">
				<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">验证码：</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0","color":"#333","flex":"1","background":"none","width":"100%","fontSize":"28rpx","height":"60rpx"}' v-model="smscode" type="text" class="uni-input" name="" placeholder="请输入短信验证码" />
				<button :style='{"border":"0","padding":"0 12rpx","margin":"0","color":"#333","background":"#FFD128","width":"auto","lineHeight":"60rpx","fontSize":"28rpx","height":"60rpx"}' type="primary" v-if="isEndFlag" @tap="sendsmscode" class="bg-red">发送验证码</button>
				<button :style='{"border":"0","padding":"0 12rpx","margin":"0","color":"#333","background":"#FFD128","width":"auto","lineHeight":"60rpx","fontSize":"28rpx","height":"60rpx"}' type="primary" v-if="!isEndFlag" disabled="disabled" class="bg-red">{{SecondToDate}}</button>
			</view>
			<view :style='{"padding":"20rpx 0","margin":"0","borderColor":"#ffffff","background":"#a2a2a280","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" class="uni-form-item uni-column">
				<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">密保问题：</view>
				<input :style='{"border":"0px solid rgb(255, 170, 51)","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"60rpx"}' :disabled="ro.pquestion" v-model="ruleForm.pquestion"  type="text"  class="uni-input" name="" placeholder="密保问题" />
			</view>
			<view :style='{"padding":"20rpx 0","margin":"0","borderColor":"#ffffff","background":"#a2a2a280","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" class="uni-form-item uni-column">
				<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">密保答案：</view>
				<input :style='{"border":"0px solid rgb(255, 170, 51)","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"60rpx"}' :disabled="ro.panswer" v-model="ruleForm.panswer"  type="text"  class="uni-input" name="" placeholder="密保答案" />
			</view>
			<button :style='{"border":"0","padding":"0px","margin":"20rpx 0","color":"#000","borderRadius":"0px","background":"#fff","width":"100%","lineHeight":"88rpx","fontSize":"32rpx","height":"88rpx"}' class="btn-submit" @tap="register" type="primary">注册</button>
			
			<view class="idea1" :style='{"width":"100%","background":"red","display":"none","height":"80rpx"}'>idea1</view>
			<view class="idea2" :style='{"width":"100%","background":"red","display":"none","height":"80rpx"}'>idea2</view>
			<view class="idea3" :style='{"width":"100%","background":"red","display":"none","height":"80rpx"}'>idea3</view>
		</view>
	</view>
</view>
</template>

<script>
	import multipleSelect from "@/components/momo-multipleSelect/momo-multipleSelect";
	export default {
		data() {
			return {
				xueshengxingbieOptions: [],
				xueshengxingbieIndex: 0,
				ruleForm: {
				xuehao: '',
				mima: '',
				xingming: '',
				xingbie: '',
				touxiang: '',
				youxiang: '',
				mobile: '',
				pquestion: '',
				panswer: '',
				},
				ro: {},
				smscode: "",
				// 倒计时定时器
				inter: null,
				// 倒计时
				count: 10,
				//倒计时结束标识
				isEndFlag: true,
				tableName:"",
				indexBgUrl: '',
				indexLogoUrl: '',
			}
		},
		components: {
			multipleSelect
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			},
			SecondToDate: function() {
				var time = this.count;
				time = parseInt(time) + "秒后重发";
				return time;
			}
		},
		async onLoad() {
			let res = [];
			let table = uni.getStorageSync("loginTable");
			this.tableName = table;

			if(this.tableName=='xuesheng'){
				// 自定义下拉框值
				this.xueshengxingbieOptions = "男,女".split(',');
				this.ruleForm.xingbie=this.xueshengxingbieOptions[0]
				this.ro = {
					xuehao: false,
					mima: false,
					xingming: false,
					xingbie: false,
					touxiang: false,
					youxiang: false,
					mobile: false,
					pquestion: false,
					panswer: false,
				}
			}
			
			this.styleChange()
		},
		methods: {
			// 发送邮箱验证码
			async sendsmscode(){
				if(!this.ruleForm.mobile){
					this.$utils.msg(`手机号码不能为空`);
					return
				}
				if(this.ruleForm.mobile&&(!this.$validate.isMobile(this.ruleForm.mobile))){
					this.$utils.msg(`请输入正常的手机格式`);
					return
				}
				if (this.count > 0) {
					this.isEndFlag = false;
					var _this = this;
					this.inter = setInterval(function() {
						_this.count = _this.count - 1;
						if (_this.count <= 0) {
							clearInterval(_this.inter);
							_this.count=60;
							_this.isEndFlag = true;
						}
					}, 1000);
				}
				let res = await this.$api.sendsms(`${this.tableName}`, this.ruleForm.mobile);
				if (res.code == 0) {
					this.$utils.msg(`发送成功`);
				} else {
					this.$utils.errMsg(`服务器异常，请稍后重试`);
					return;
				}
			},

			// 下拉变化
			xueshengxingbieChange(e) {
					this.xueshengxingbieIndex = e.target.value
					this.ruleForm.xingbie = this.xueshengxingbieOptions[this.xueshengxingbieIndex]
			},
			xueshengtouxiangTap() {
				if(this.ro.touxiang) {
					return false
				}
				let _this = this;
				this.$api.upload(function(res) {
					_this.ruleForm.touxiang = 'upload/' + res.file;
					_this.$forceUpdate();
				});
			},
			toggleTab(str,str1) {
				if(this.ro[str1]){
					return false
				}
				this.$refs[str + str1].show();
			},

			async styleChange() {
				let rs = await this.$api.getPublic('config/info?name=appRegisterBackgroudImg')
				this.indexBgUrl = rs.data?rs.data.value:''
				rs = await this.$api.getPublic('config/info?name=appRegLogo')
				this.indexLogoUrl = rs.data?rs.data.value:''
			},
			// 获取uuid
			getUUID () {
				return new Date().getTime();
			},
			// 注册
			async register() {
				
				if(`xuesheng` == this.tableName && (this.ruleForm.mima!=this.ruleForm.mima2)){
					this.$utils.msg(`两次密码输入不一致`);
					return
				}
				if(`xuesheng` == this.tableName && this.ruleForm.youxiang&&(!this.$validate.isEmail(this.ruleForm.youxiang))){
					this.$utils.msg(`邮箱应输入邮件格式`);
					return
				}
				if(`xuesheng` == this.tableName && this.ruleForm.mobile&&(!this.$validate.isMobile(this.ruleForm.mobile))){
					this.$utils.msg(`手机号应输入手机格式`);
					return
				}
				if(`xuesheng` == this.tableName && this.ruleForm.pquestion==''){
					this.$utils.msg(`密保问题不能为空`);
					return
				}
				if(`xuesheng` == this.tableName && this.ruleForm.panswer==''){
					this.$utils.msg(`密保答案不能为空`);
					return
				}
				await this.$api.registerSms(`${this.tableName}`, this.ruleForm, this.smscode);
				this.$utils.msgBack('注册成功');;
			}
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
	}
	.register-box{
		padding: 20rpx;
		background: url(http://codegen.caihongy.cn/20250814/4a8e27888ea14787868acb75a407c14b.jpg) no-repeat top center / cover;
		display: flex;
		width: 100%;
		min-height: 100%;
		align-items: center;
		height: auto;
	}
</style>
