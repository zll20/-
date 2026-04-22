<template>
	<view class="content">
		<view class="login-box" :style="{'backgroundImage': indexBgUrl?`url(${$base.url + indexBgUrl})`:''}">
			<view :style='{"width":"100%","padding":"0 60rpx","position":"relative","display":"block","height":"auto"}'>
				<image :style='{"width":"160rpx","margin":"0 auto 20rpx auto","borderRadius":"8rpx","display":"block","height":"160rpx"}' :src="indexLogoUrl?($base.url + indexLogoUrl):'http://codegen.caihongy.cn/20250814/2cb518a34dd04bd1b6c5e52e74d8f581.jpg'" mode="aspectFill"></image>
				<view v-if="loginType==1" :style='{"padding":"20rpx 0","margin":"0","borderColor":"#fff","background":"#a2a2a280","borderWidth":"0 0 2rpx","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="uni-form-item uni-column">
					<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">账号：</view>
					<input v-model="username" :style='{"border":"0px solid rgb(255, 170, 51)","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"60rpx"}' type="text" class="uni-input" name="" placeholder="请输入账号" />
				</view>
				<view v-if="loginType==1" :style='{"padding":"20rpx 0","margin":"0","borderColor":"#fff","background":"#a2a2a280","borderWidth":"0 0 2rpx","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="uni-form-item uni-column">
					<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">密码：</view>
					<input v-model="password" password :style='{"border":"0px solid rgb(255, 170, 51)","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"60rpx"}' type="password" class="uni-input" name="" placeholder="请输入密码" />
				</view>
				<view v-if="loginType==4" :style='{"padding":"20rpx 0","margin":"0","borderColor":"#fff","background":"#a2a2a280","borderWidth":"0 0 2rpx","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="uni-form-item uni-column">
					<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">手机号：</view>
					<input v-model="phone" :style='{"border":"0px solid rgb(255, 170, 51)","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"60rpx"}' type="text" class="uni-input" name="" placeholder="请输入手机号" />
				</view>
				<view v-if="loginType==4" class="code" :style='{"padding":"20rpx 10rpx 20rpx 0","margin":"0","borderColor":"#fff","background":"#a2a2a280","borderWidth":"0 0 2rpx","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">验证码：</view>
					<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0","color":"#333","flex":"1","background":"none","width":"100%","fontSize":"28rpx","height":"60rpx"}' type="text" v-model="phonecode" placeholder="请输入验证码" />
					<view v-if="isEndFlag" class="getCodeBt" :style='{"border":"0","padding":"0 20rpx","margin":"0px","alignItems":"center","background":"#FFD128","display":"flex","width":"auto","justifyContent":"center","height":"60rpx"}' @click="phoneClick()">
						发送验证码
					</view>
					<view v-if="!isEndFlag" class="getCodeBt" :style='{"border":"0","padding":"0 20rpx","margin":"0px","alignItems":"center","background":"#FFD128","display":"flex","width":"auto","justifyContent":"center","height":"60rpx"}'>
						{{emailText}}
					</view>
				</view>
				<view v-if="phoneroles.length>2&&loginType==4" :style='{"padding":"20rpx 0","margin":"0","borderColor":"#fff","background":"#a2a2a280","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">用户类型：</view>
					<picker @change="phonerolesChange" :value="phonerolesIndex" :range="phoneroles" range-key="roleName" :style='{"padding":"0 20rpx","lineHeight":"60rpx","fontSize":"28rpx","color":"#000","flex":"1"}'>
						<view class="uni-picker-type">{{phoneroles[phonerolesIndex].roleName}}</view>
					</picker>
				</view>
				<view v-if="roleNum>1&&loginType<=2" :style='{"padding":"20rpx 0","margin":"0","borderColor":"#fff","background":"#a2a2a280","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ffffff","color":"#000","borderRadius":"0","borderWidth":"0 0px 0 0","width":"auto","lineHeight":"60rpx","fontSize":"26rpx","borderStyle":"solid"}' class="label">用户类型：</view>
					<picker @change="optionsChange" :value="index" :range="options" :style='{"padding":"0 20rpx","lineHeight":"60rpx","fontSize":"28rpx","color":"#000","flex":"1"}'>
						<view class="uni-picker-type">{{options[index]}}</view>
					</picker>
				</view>
				

				
				<button v-if="loginType==1||loginType==3||loginType==4" class="btn-submit" @tap="onLoginTap" type="primary" :style='{"border":"0","padding":"0px","margin":"20rpx 0","color":"#000","borderRadius":"0px","background":"#fff","width":"100%","lineHeight":"88rpx","fontSize":"32rpx","height":"88rpx"}'>登录</button>
				<button v-if="loginType==2" class="btn-submit" @tap="onFaceLoginTap" type="primary" :style='{"border":"0","padding":"0px","margin":"20rpx 0","color":"#000","borderRadius":"0px","background":"#fff","width":"100%","lineHeight":"88rpx","fontSize":"32rpx","height":"88rpx"}'>人脸识别登录</button>
				<view class="links" :style='{"width":"100%","padding":"0","margin":"20rpx 0 0 0","flexWrap":"wrap","display":"flex","height":"auto"}'>
					<view class="link-highlight" @tap="onRegisterTap('xuesheng')" :style='{"padding":"0 10rpx 0 10rpx","margin":"0 0 20rpx 0","fontSize":"28rpx","color":"#000"}'>注册学生</view>
					<view @tap="onForgetTap" :style='{"padding":"0 10rpx 0 10rpx","margin":"0 0 20rpx 0","fontSize":"28rpx","color":"#000"}'>忘记密码？</view>
					<view v-if="loginType==1" @tap="changeLogin(4)" :style='{"padding":"0 10rpx 0 10rpx","margin":"0 0 20rpx 0","fontSize":"28rpx","color":"#000"}'>手机号登录</view>
					<view v-if="loginType==2||loginType==3||loginType==4" @tap="changeLogin(1)" :style='{"padding":"0 10rpx 0 10rpx","margin":"0 0 20rpx 0","fontSize":"28rpx","color":"#000"}'>用户密码登录</view>
				</view>
				
				<view class="idea1" :style='{"width":"100%","background":"red","display":"none","height":"80rpx"}'>idea1</view>
				<view class="idea2" :style='{"width":"100%","background":"red","display":"none","height":"80rpx"}'>idea2</view>
				<view class="idea3" :style='{"width":"100%","background":"red","display":"none","height":"80rpx"}'>idea3</view>
			</view>
		</view>
	</view>
</template>

<script>
	import menu from '@/utils/menu'
	export default {
		data() {
			return {
				username: '',
				password: '',
				loginType:1,
				phone: '',
				phonecode: '',
				phoneroles: [
					{
						tableName: '',
						roleName: '请选择登录用户类型'
					},
					{
						tableName: 'xuesheng',
						roleName: '学生'
					},
				],
				phonerolesIndex: 0,
				// 倒计时时间
				count: 60,
				// 倒计时定时器
				inter: null,
				// 倒计时是否结束
				isEndFlag: true,
				codes: [{
					num: 1,
					color: '#000',
					rotate: '10deg',
					size: '16px'
				}, {
					num: 2,
					color: '#000',
					rotate: '10deg',
					size: '16px'
				}, {
					num: 3,
					color: '#000',
					rotate: '10deg',
					size: '16px'
				}, {
					num: 4,
					color: '#000',
					rotate: '10deg',
					size: '16px'
				}],
				options: [
					'请选择登录用户类型',
				],
				optionsValues: [
					'',
					'xuesheng',
				],
				index: 0,
				roleNum:0,

				indexBgUrl: '',
				indexLogoUrl: '',
			}
		},
		computed: {
			emailText: function() {
				var time = this.count;
				if (null != time && "" != time) {
					time = parseInt(time) + "秒后重发";
				}
				return time;
			}
		},
		onLoad() {
			let options = ['请选择登录用户类型'];
			let menus = menu.list();
			this.menuList = menus;
			for(let i=0;i<this.menuList.length;i++){
				if(this.menuList[i].hasFrontLogin=='是'){
					options.push(this.menuList[i].roleName);
					this.roleNum++;
				}
			}
			if(this.roleNum==1) {
				this.index = 1;
			}	
			this.options = options;
			this.styleChange()
			
		},
		onShow() {
		},
		mounted() {
		},
		methods: {
			async phoneClick() {
				var roles = ''
				if(!this.phone){
					this.$utils.msg(`手机号不能为空`);
					return
				}
				if(this.phone&&(!this.$validate.isMobile(this.phone))){
					this.$utils.msg(`请输入正确的手机号格式`);
					return
				}
				if (this.phoneroles.length>2) {
					if (!this.phoneroles[this.phonerolesIndex].tableName) {
						this.$utils.msg("请选择登录用户类型");
						return false;
					}
					roles = this.phoneroles[this.phonerolesIndex].tableName
				}else {
					roles = this.phoneroles[1].tableName
				}
				var _this = this;
				let res = await this.$api.getPublic(`${roles}/sendsms/login?mobile=${this.phone}`);
				if(res.code==0) {
					this.isEndFlag = false;
					this.inter = setInterval(function() {
						_this.count = _this.count - 1;
						if (_this.count <= 0) {
							clearInterval(_this.inter);
							_this.isEndFlag = true;
							_this.count=60;
						}
					}, 1000);
					this.$utils.msg("发送成功！");
				}else {
					this.$utils.msg(res.msg);
				}
			},
			async phoneLogin() {
				var roles = ''
				var rolesName = ''
				if (this.phoneroles.length>2) {
					roles = this.phoneroles[this.phonerolesIndex].tableName
					rolesName = this.phoneroles[this.phonerolesIndex].roleName
				}else {
					roles = this.phoneroles[1].tableName
					rolesName = this.phoneroles[1].roleName
				}
				let res = await this.$api.postPublic(`${roles}/sms/login?mobile=${this.phone}&&smscode=${this.phonecode}`);
				uni.removeStorageSync("useridTag");
				uni.setStorageSync("appToken", res.token);
				uni.setStorageSync("nickname",res.username);
				uni.setStorageSync("nowTable", `${roles}`);
				res = await this.$api.session(`${roles}`);
				if(res.data.touxiang) {
					uni.setStorageSync('frontHeadportrait', res.data.touxiang);
				} else if(res.data.headportrait) {
					uni.setStorageSync('frontHeadportrait', res.data.headportrait);
				}
				if(roles== 'xuesheng') {
					uni.setStorageSync('appExamName', res.data.xingming);
				}
				uni.setStorageSync('userSession',JSON.stringify(res.data))
				// 保存用户id
				uni.setStorageSync("appUserid", res.data.id);
				if(res.data.vip) {
					uni.setStorageSync("vip", res.data.vip);
				}
				uni.setStorageSync("appRole", `${rolesName}`);
				this.$utils.tab('../index/index');
			},
			phonerolesChange(e){
				this.phonerolesIndex = e.target.value
			},
			async styleChange() {
				let rs = await this.$api.getPublic('config/info?name=appLoginBackgroundImg')
				this.indexBgUrl = rs.data?rs.data.value:''
				rs = await this.$api.getPublic('config/info?name=appLoginLogo')
				this.indexLogoUrl = rs.data?rs.data.value:''
			},
			onRegisterTap(tableName) {
				uni.setStorageSync("loginTable", tableName);
				this.$utils.jump('../register/register')
			},
			onForgetTap() {
				this.$utils.jump('../forget/forget')
			},
			async onLoginTap() {
				if(this.loginType==1) {
					if (!this.username) {
						this.$utils.msg('请输入用户名')
						return
					}
					if (!this.password) {
						this.$utils.msg('请输入用户密码')
						return
					}
					if (!this.optionsValues[this.index]) {
						this.$utils.msg('请选择登录用户类型')
						return
					}
				}
				if(this.loginType==4) {
					if (!this.phone) {
						this.$utils.msg('请输入手机号')
						return
					}
					if (!this.phonecode) {
						this.$utils.msg('请输入手机验证码')
						return
					}
					if (this.phoneroles.length>2&&!this.phoneroles[this.phonerolesIndex].tableName) {
						this.$utils.msg('请选择登录用户类型')
						return
					}
				}

				this.loginPost()

			},
			async loginPost() {
				let that = this
				if(this.loginType==4) {
					this.phoneLogin()
					return false
				}
				let res = await this.$api.login(`${this.optionsValues[this.index]}`, {
					username: this.username,
					password: this.password
				});
				uni.removeStorageSync("useridTag");
				uni.setStorageSync("appToken", res.token);
				uni.setStorageSync("nickname",this.username);
				uni.setStorageSync("nowTable", `${this.optionsValues[this.index]}`);
				res = await this.$api.session(`${this.optionsValues[this.index]}`);
				if(res.data.touxiang) {
					uni.setStorageSync('frontHeadportrait', res.data.touxiang);
				} else if(res.data.headportrait) {
					uni.setStorageSync('frontHeadportrait', res.data.headportrait);
				}
				if(that.optionsValues[that.index]== 'xuesheng') {
					uni.setStorageSync('appExamName', res.data.xingming);
				}
				uni.setStorageSync('userSession',JSON.stringify(res.data))
				// 保存用户id
				uni.setStorageSync("appUserid", res.data.id);
				if(res.data.vip) {
					uni.setStorageSync("vip", res.data.vip);
				}
				uni.setStorageSync("appRole", `${this.options[this.index]}`);
				this.$utils.tab('../index/index');
			},
			changeLogin(type){
				this.loginType = type
			},
			optionsChange(e) {
				this.index = e.target.value
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		height: 100%;
	}
	
	.content {
		height: 100%;
		box-sizing: border-box;
	}
	.login-box {
		padding: 20rpx;
		background: url(http://codegen.caihongy.cn/20250814/4a8e27888ea14787868acb75a407c14b.jpg) no-repeat top center / cover;
		display: flex;
		width: 100%;
		min-height: 100%;
		align-items: center;
		height: auto;
	}
</style>
