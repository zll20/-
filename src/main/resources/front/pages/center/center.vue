<template>
	<view class="content">
		<view :style='{"minHeight":"100%","width":"100%","padding":"0","position":"relative","background":"#F6F7F9","height":"auto"}'>
			<view v-if="user&&user.id" :style='{"minHeight":"280rpx","padding":"20rpx","margin":"0 0 20rpx 0","borderRadius":"0 0 30rpx 30rpx","background":"#fff","display":"flex","width":"100%","position":"relative","height":"auto"}' @tap="onPageTap('../user-info/user-info')" class="header" v-bind:class="{'status':isH5Plus}">
				<view :style='{"alignItems":"center","flexWrap":"wrap","display":"flex","height":"auto"}' v-if="tableName=='xuesheng'" class="userinfo">
					<image :style='{"width":"132rpx","padding":"0","margin":"0 20rpx 0 40rpx","borderRadius":"100%","height":"132rpx"}' :src="user.touxiang?baseUrl+user.touxiang:require('../../static/gen/upload.png')"></image>
					<view :style='{"margin":"0px 0 0","alignItems":"center","flexDirection":"column","display":"flex","width":"auto","justifyContent":"center","order":"2"}' class="info">
						<view :style='{"width":"100%","lineHeight":"1.5","fontSize":"28rpx","color":"#666","textAlign":"left"}'>{{user.xuehao}}<text v-if="user.vip&& user.vip=='是'">(VIP)</text></view>
					</view>
					
					<view :style='{"padding":"20rpx 40rpx 20rpx 40rpx","margin":"20rpx 5% 0 5%","borderRadius":"20rpx","flexWrap":"wrap","background":"url(http://codegen.caihongy.cn/20250814/99c884f099b4425ba22dac8e1a2c6850.jpg) no-repeat center top /  100% auto","display":"flex","width":"90%","order":"3"}' class="info">
						<view :style='{"width":"100%","lineHeight":"48rpx","fontSize":"28rpx","alignItems":"center","display":"flex"}'>
							<span :style='{"color":"#333","whiteSpace":"nowrap"}'>手机号：</span>
							<span :style='{"color":"#333","whiteSpace":"nowrap"}'>{{user.mobile}}</span>
						</view>
						<view :style='{"width":"100%","lineHeight":"48rpx","fontSize":"28rpx","alignItems":"center","display":"flex"}' v-if="user.jf||user.jf==0">
							<span :style='{"color":"#333","whiteSpace":"nowrap"}'>积分：</span>
							<span :style='{"color":"#333","whiteSpace":"nowrap"}'>{{user.jf}}</span>
						</view>
					</view>
					<view :style='{"width":"100%","margin":"30rpx 0 0","alignItems":"center","justifyContent":"center","display":"flex","order":"3"}'>
					  <view :style='{"padding":"0 20rpx 0 20rpx","margin":"0 40rpx 0 40rpx","borderRadius":"60rpx","color":"#fff","background":"#0036FF10","display":"flex"}'>
					    <text class="icon iconfont icon-shoucang10" :style='{"margin":"0 4rpx 0 0","lineHeight":"60rpx","fontSize":"28rpx","color":"#333"}'></text>
					    <text :style='{"color":"#333","lineHeight":"60rpx","fontSize":"28rpx"}'>收藏 {{user.storenum}}</text>
					  </view>
					</view>
				</view>
				<view :style='{"padding":"0","alignItems":"center","top":"20rpx","flexDirection":"column","display":"flex","width":"auto","position":"absolute","right":"40rpx","justifyContent":"center","height":"auto"}' class="setting">
					<text class="icon iconfont icon-qita6" :style='{"border":"0","padding":"0","color":"#000","borderRadius":"0","width":"auto","lineHeight":"1","fontSize":"52rpx"}'></text>
					<text :style='{"color":"#333","lineHeight":"2","fontSize":"32rpx"}'>设置</text>
				</view>
			</view>
			
			<view v-else :style='{"minHeight":"280rpx","padding":"20rpx","margin":"0 0 20rpx 0","borderRadius":"0 0 30rpx 30rpx","background":"#fff","display":"flex","width":"100%","position":"relative","height":"auto"}' @tap="loginClick" class="header" v-bind:class="{'status':isH5Plus}">
				<view :style='{"alignItems":"center","color":"#666","display":"flex","width":"100%","fontSize":"36rpx","justifyContent":"center","height":"100%"}'>登录/注册</view>
			</view>
			
			<view :style='{"width":"100%","background":"none","height":"auto"}' class="list">
				
				<view :style='{"width":"100%","margin":"0 0 40rpx","overflow":"hidden","borderRadius":"30rpx","background":"#fff","height":"auto"}'>
					<view :style='{"padding":"0 20rpx 0 40rpx","color":"#fff","textAlign":"left","background":"url(http://codegen.caihongy.cn/20250214/607fdecc1a9b45e7a35d80592775c303.png) center center/100% 100%","display":"flex","width":"100%","lineHeight":"100rpx","fontSize":"32rpx","fontWeight":"bold","justifyContent":"space-between"}'>
						<view :style='{"fontSize":"32rpx","lineHeight":"100rpx","color":"#333","fontWeight":"500"}'>我的服务</view>
					</view>
					<view class="me-menu-view">
						<block v-for="item in menuList" v-bind:key="item.roleName">
							<block v-if="role==item.roleName" v-bind:key="menu.menu" v-for="(menu,index) in item.backMenu">
								<block v-bind:key="child.tableName" v-for=" (child,sort) in menu.child">
									<view class="me-menu-item" v-if="hasBack(child.tableName)" @tap="onPageTap('../'+child.tableName+'/list?userid='+user.id+'&menuJump='+child.menuJump)" hover-class="hover">
										<view class="me-menu-icon" :class="child.appFrontIcon"></view>
										<view class="text">{{child.menu}}</view>
										<view class="icon iconfont icon-jinru"></view>
									</view>
								</block>
							</block>
						</block>


						<view v-if="user&&user.id" @tap="onPageTap('../forum-my/forum-my')" class="me-menu-item" hover-class="hover">
							<view class="cuIcon-scan me-menu-icon"></view>
							<view class="text">我的发贴</view>
							<view class="icon iconfont icon-jinru"></view>
						</view>
						<view v-if="user&&user.id" @tap="passwordShow()" class="me-menu-item" hover-class="hover">
							<view class="cuIcon-lock me-menu-icon"></view>
							<view class="text">修改密码</view>
							<view class="icon iconfont icon-jinru"></view>
						</view>
					</view>
				</view>
			</view>
			
			<!-- 推荐 -->
			<view class="recommend" :style='{"width":"100%","overflow":"hidden","borderRadius":"20rpx","background":"none","height":"auto","order":"5"}' v-if="recommendList.length">
				<view :style='{"padding":"0 0 0 40rpx","color":"#333","textAlign":"left","background":"url(http://codegen.caihongy.cn/20250214/607fdecc1a9b45e7a35d80592775c303.png) center center/100% 100%","width":"100%","lineHeight":"100rpx","fontSize":"32rpx","fontWeight":"500"}'>为你推荐</view>
				<view :style='{"padding":"20rpx ","flexWrap":"wrap","background":"#F6F7F9","display":"flex","width":"100%","justifyContent":"space-between","height":"auto"}'>
					<view v-for="(item,index) in recommendList" :key="index" :style='{"padding":" 20rpx 20rpx","margin":"0 0 20rpx","borderRadius":"20rpx","flexWrap":"wrap","background":"#fff","display":"flex","width":"49%","height":"auto"}' @click="recommendDetail(item.id)">
						<image v-if="preHttp(item[recommendPicture])" :style='{"width":"100%","margin":"0 0 10rpx 0","objectFit":"cover","borderRadius":"20rpx","display":"block","height":"220rpx"}' :src="item[recommendPicture]"></image>
						<image v-else :style='{"width":"100%","margin":"0 0 10rpx 0","objectFit":"cover","borderRadius":"20rpx","display":"block","height":"220rpx"}' :src="item[recommendPicture]?(baseUrl + item[recommendPicture].split(',')[0]):''"></image>
						<view :style='{"padding":"0 8rpx","whiteSpace":"nowrap","overflow":"hidden","color":"#000","textAlign":"center","width":"100%","lineHeight":"1.5","fontSize":"28rpx","textOverflow":"ellipsis","fontWeight":"500"}'>{{item[recommendTitle]}}</view>
						<view :style='{"width":"100%","padding":"0 8rpx","lineHeight":"2","fontSize":"24rpx","color":"#959595","textAlign":"center"}'>{{item.addtime}}</view>
					</view>
				</view>
			</view>
			<view style="width: 100%;height: 80px"></view>
		</view>
		<uni-popup class="popup-content" ref="passwordPopup" type="bottom">
			<view class="passwordForm">
				<view class="passwordInput">
					<input type="password" v-model="passwordForm.mima" placeholder="原密码" />
				</view>
				<view class="passwordInput">
					<input type="password" v-model="passwordForm.newmima" placeholder="新密码" />
				</view>
				<view class="passwordInput">
					<input type="password" v-model="passwordForm.newmima1" placeholder="确认密码" />
				</view>
				<view class="passwordBtnView">
					<button class="passwordBtn" @click="updatePassword">更新</button>
				</view>
			</view>
		</uni-popup>
	</view>
</template>
<script>
	import menu from '@/utils/menu'
	export default {
		data() {
			return {
				isH5Plus: true,
				user: {},
				tableName:'',
				role: '',
				menuList: [],
				iconArr: [
					'cuIcon-same',
					'cuIcon-deliver',
					'cuIcon-evaluate',
					'cuIcon-shop',
					'cuIcon-ticket',
					'cuIcon-cascades',
					'cuIcon-discover',
					'cuIcon-question',
					'cuIcon-pic',
					'cuIcon-filter',
					'cuIcon-footprint',
					'cuIcon-pulldown',
					'cuIcon-pullup',
					'cuIcon-moreandroid',
					'cuIcon-refund',
					'cuIcon-qrcode',
					'cuIcon-remind',
					'cuIcon-profile',
					'cuIcon-home',
					'cuIcon-message',
					'cuIcon-link',
					'cuIcon-lock',
					'cuIcon-unlock',
					'cuIcon-vip',
					'cuIcon-weibo',
					'cuIcon-activity',
					'cuIcon-friendadd',
					'cuIcon-friendfamous',
					'cuIcon-friend',
					'cuIcon-goods',
					'cuIcon-selection'
				],
				recommendList: [],
				recommendTable: '',
				recommendTitle: '',
				recommendPicture: '',
				passwordForm: {
					mima: '',
					newmima: '',
					newmima1: '',
				},
			};
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			},
		},
		async onLoad(){
			let menus = menu.list();
			this.menuList = menus;
		},
		async onShow(){
            uni.removeStorageSync("useridTag");
			this.role = uni.getStorageSync("appRole");
			await this.getSession()
			this.getStoreup()
			this.tableName = uni.getStorageSync("nowTable");
			let menus = menu.list();
			this.menuList = menus;
			this.getRecommendList()
			this.$forceUpdate()
		},
		methods: {
			hasBack(tableName) {
				if(tableName == 'exampaper'||tableName == 'examquestion') {
					return false
				}
				if(tableName.indexOf('chapter')!=-1) {
					return false
				}
				return true
			},
			async getSession(){
				let table = uni.getStorageSync("nowTable");
				let res = await this.$api.session(table);
				this.user = res.data;
				this.$forceUpdate()
			},
			async getStoreup(){
				if(!this.user||!this.user.id){
					return false
				}
				let res = await this.$api.page('storeup',{userid: this.user.id,type: 1})
				this.user.storenum = Number(res.data.total)
				this.$forceUpdate()
			},
			loginClick(){
				uni.navigateTo({
					url: '../login/login'
				});
			},
			onPageTap(url) {
                uni.setStorageSync("useridTag",1);
				uni.navigateTo({
					url: url,
					fail: function() {
						uni.switchTab({
							url: url
						});
					}
				});
			},
			hasTranslate(){
				for(let x in this.menuList){
					if(this.menuList[x].roleName == this.role){
						for(let i in this.menuList[x].backMenu){
							if(this.menuList[x].backMenu[i].child.length) {
								for(let j in this.menuList[x].backMenu[i].child) {
									if(this.menuList[x].backMenu[i].child[j].tableName=='hasTranslate'){
										return true
									}
								}
							}
						}
					}
				}
				return false
			},
			
			preHttp(str) {
			    return str && str.substr(0,4)=='http';
			},
			async getRecommendList(){
				let params = {
					page: 1,
					limit: 6,
				}
				let res;
				if(uni.getStorageSync("appUserid")) {
					res = await this.$api.recommend2('danciku', params);
				} else {
					res = await this.$api.recommend('danciku', params);
				}
				this.recommendList = res.data.list
				this.recommendTable = 'danciku'
				this.recommendTitle = 'danci'
				this.recommendPicture = 'fengmiantu'
				this.$forceUpdate()
			},
			recommendDetail(id) {
				uni.navigateTo({
					url: `../${this.recommendTable}/detail?id=${id}`,
					fail: function() {
						uni.switchTab({
							url: `../${this.recommendTable}/detail?id=${id}`
						});
					}
				});
			},
			passwordShow() {
				this.passwordForm = {
					mima: '',
					newmima: '',
					newmima1: '',
				}
				this.$forceUpdate()
				this.$refs.passwordPopup.open()
			},
			async updatePassword() {
				if (this.passwordForm.mima == ''){
					this.$utils.msg('原密码不能为空')
					return false
				}
				if (this.passwordForm.newmima == ''){
					this.$utils.msg('新密码不能为空')
					return false
				}
				if (this.passwordForm.newmima1 == ''){
					this.$utils.msg('确认密码不能为空')
					return false
				}
				let password = ''
				if (this.user.mima) {
					password = this.user.mima;
				} else if (this.user.password) {
					password = this.user.password;
				}
				if (this.tableName == 'xuesheng') {
				}
				let newpassword = this.passwordForm.mima
				let res = await this.$api.encrypt('md5',this.passwordForm.mima)
				newpassword = res.data
				if(password != newpassword){
					this.$utils.msg('原密码不正确')
					return false
				}
				if (this.passwordForm.newmima != this.passwordForm.newmima1){
					this.$utils.msg('两次密码不一致')
					return false
				}
				if (this.passwordForm.mima == this.passwordForm.newmima){
					this.$utils.msg('新密码与原密码相同！')
					return false
				}
				this.user.password = this.passwordForm.newmima
				this.user.mima = this.passwordForm.newmima
				await this.$api.update(this.tableName,this.user)
				this.$utils.msg('修改密码成功,下次登录系统生效')
				this.$refs.passwordPopup.close()
				this.getSession()
			},
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		height: calc(100vh - 94px);
		box-sizing: border-box;
	}
	.passwordForm {
		width: 100%;
		padding: 10px 20px 60px;
		background: #fff;
	
		.passwordInput {
			background: #eee;
			margin: 0 0 10px;
			padding: 0 10px;
	
			input {
				height: 36px;
				line-height: 36px;
				font-size: 14px;
			}
		}
	
		.passwordBtnView {
			width: 100%;
			display: flex;
			align-items: center;
			justify-content: center;
			padding: 10px 0;
	
			.passwordBtn {
				padding: 0 20px;
				width: auto;
				height: 20px;
				line-height: 20px;
				font-size: 14px;
				color: #fff;
				background: #007aff;
				border: none;
				border-radius: 5px;
	
			}
		}
	}
	.me-menu-view {
		padding: 0;
		display: flex;
		width: 100%;
		flex-wrap: wrap;
		height: auto;
		.me-menu-item {
			padding: 0 0 0 40rpx;
			display: flex;
			width: 50%;
			border-color: #eeeeee;
			border-width: 0 0 2rpx 0;
			align-items: center;
			border-style: solid;
			height: auto;
			.me-menu-icon {
				color: #000;
				font-size: 60rpx;
				line-height: 1;
			}
			.text {
				padding: 0 20rpx;
				color: #000;
				flex: 1;
				width: 100%;
				font-size: 28rpx;
				line-height: 100rpx;
			}
			.icon {
				color: #999;
				display: none;
				width: 28rpx;
				font-size: 28rpx;
				line-height: 28rpx;
			}
		}
	}
</style>
