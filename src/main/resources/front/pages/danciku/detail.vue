
<template>
<view>
<mescroll-uni @init="mescrollInit" :up="upOption" :down="downOption" @down="downCallback" @up="upCallback" @scroll="scrollChange">
	<view class="content">
		<view class="container" :style='{"minHeight":"100%","width":"100%","padding":"0","position":"relative","background":"#F6F7F9","height":"auto"}'>
			<swiper :style='{"width":"100%","background":"#fff","height":"360rpx"}' class="swiper" :indicator-dots='true' :autoplay='false' :circular='true' indicator-active-color='#000000' indicator-color='rgba(0, 0, 0, .3)' :duration='500' :interval='5000' :vertical='false'>
				<swiper-item :style='{"width":"100%","background":"#fff","height":"360rpx"}' v-for="(swiper,index) in swiperList" :key="index">
					<img :style='{"width":"100%","objectFit":"cover","display":"block","height":"360rpx"}' v-if="swiper.substring(0,4)=='http'" :src="swiper" @tap="imgView(swiper)">
					<img :style='{"width":"100%","objectFit":"cover","display":"block","height":"360rpx"}' v-else :src="baseUrl+swiper" @tap="imgView(baseUrl+swiper)">
				</swiper-item>
			</swiper>
			<view :style='{"padding":"24rpx","borderRadius":"30rpx","flexWrap":"wrap","background":"#fff","display":"flex","width":"100%","height":"auto"}' class="detail-content">
				<view :style='{"margin":"0 0 0px 0","borderColor":"#cccccc","alignItems":"center","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","justifyContent":"space-between","height":"auto"}' class="detail-list-item price">
					<view :style='{"padding":"0 20rpx","background":"#FFD128","display":"flex"}' v-if="storeupFlag==1" @click="shoucang">
						<text class="icon iconfont icon-shoucang11" :style='{"margin":"0 8rpx 0 0","lineHeight":"50rpx","fontSize":"28rpx","color":"#fff"}'></text>
						<text :style='{"color":"#fff","lineHeight":"50rpx","fontSize":"28rpx"}'>已收藏</text>
					</view>
					<view :style='{"padding":"0 30rpx","background":"#FFD128","display":"flex"}' v-if="storeupFlag==0" @click="shoucang">
						<text class="icon iconfont icon-shoucang11" :style='{"margin":"0 8rpx 0 0","lineHeight":"50rpx","fontSize":"28rpx","color":"#fff"}'></text>
						<text :style='{"color":"#fff","lineHeight":"50rpx","fontSize":"28rpx"}'>收藏</text>
					</view>
				</view>

				<view :style='{"padding":"0","margin":"0 0 0px 0","borderColor":"#cccccc","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="detail-list-item title">
					<view :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"bold"}' class="lable">单词：</view>
					<view :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666"}' class="text" >{{detail.danci}}</view>
				</view>

				<view class="detail-list-item" :style='{"padding":"0","margin":"0 0 0px 0","borderColor":"#cccccc","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"bold"}'>级别：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666"}' >{{detail.jibie}}</view>
				</view>
				<view class="detail-list-item" :style='{"padding":"0","margin":"0 0 0px 0","borderColor":"#cccccc","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"bold"}'>单词分类：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666"}' >{{detail.dancifenlei}}</view>
				</view>
				<view class="detail-list-item" :style='{"padding":"0","margin":"0 0 0px 0","borderColor":"#cccccc","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"bold"}'>释义：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666"}' >{{detail.shiyi}}</view>
				</view>
				<view class="detail-list-item" :style='{"padding":"0","margin":"0 0 0px 0","borderColor":"#cccccc","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"bold"}'>例句：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666"}' >{{detail.liju}}</view>
				</view>
				<view class="detail-list-item" :style='{"padding":"0","margin":"0 0 0px 0","borderColor":"#cccccc","alignItems":"center","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"bold"}'>读音：</view>
					<image v-if="detail.duyin"
						src="/static/speaker.svg"
						hover-class="audio-btn-hover"
						:style='{"width":"36rpx","height":"36rpx","background":"none"}'
						@click="playAudio">
					</image>
					<view v-else :style='{"color":"#999","fontSize":"24rpx"}'>暂无读音</view>
				</view>

				<view class="detail-list-item" :style='{"padding":"0","margin":"0 0 0px 0","borderColor":"#cccccc","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"bold"}'>评论数：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666"}' >{{detail.discussnum}}</view>
				</view>

				<view class="time-content" :style='{"width":"100%","margin":"0 0 10rpx","height":"auto","order":"4"}'>
					<view class="comoment-header" :style='{"border":"2rpx solid #FFD128","width":"100%","boxShadow":"none","justifyContent":"space-between","display":"flex","height":"auto"}'>
						<view :style='{"padding":"0 24rpx","lineHeight":"80rpx","fontSize":"28rpx","color":"#FFD128"}'>评论</view>
						<view :style='{"padding":"0 20rpx","background":"#FFD128","display":"flex"}' @click="onCommentTap" class="btn-comment-content" style="display: flex;align-items: center;">
							<view :style='{"margin":"0 8rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#fff"}' class="cuIcon-add"></view>
							<view :style='{"color":"#fff","lineHeight":"80rpx","fontSize":"28rpx"}'>添加评论</view>
						</view>
					</view>
				
					<view :style='{"boxShadow":"none","padding":"24rpx","margin":"24rpx 0 0 0","borderColor":"#D8D8D8","borderWidth":"0 0 2rpx","width":"100%","position":"relative","borderStyle":"solid","height":"auto"}' v-for="(item,index) in commentList" v-bind:key="index" class="cu-item comment-item">
						<view :style='{"boxShadow":"none","position":"absolute","right":"20rpx","top":"0","background":"#fff"}' v-if="item.istop">
							<span class="icon iconfont icon-jiantou24" :style='{"color":"#000"}'></span>
						</view>
						<view :style='{"width":"100%","display":"flex","height":"auto"}'>
							<image :style='{"width":"60rpx","margin":"0 8rpx 0 0","borderRadius":"100%","display":"block","height":"60rpx"}' v-if="item.avatarurl" mode="aspectFill" :src="baseUrl+item.avatarurl"></image>
							<view :style='{"color":"#333","lineHeight":"60rpx","fontSize":"28rpx"}' class="text-grey">{{item.nickname}}</view>
						</view>
						<view :style='{"margin":"8rpx 0","lineHeight":"1.5","fontSize":"28rpx","color":"#666"}' class="text-gray text-content text-df">
							<rich-text :nodes="item.content"></rich-text>
						</view>
						<view :style='{"width":"100%","margin":"16rpx 0 0 0","alignItems":"center","justifyContent":"flex-end","display":"flex","height":"60rpx"}'>
							<view v-if="!comzanChange(item)&&!comcaiChange(item)" :style='{"alignItems":"center","display":"flex"}' @click="comzanClick(item)">
								<span class="icon iconfont icon-zan07" :style='{"fontSize":"28rpx"}'></span>
								<span :style='{"fontSize":"28rpx","display":"none"}'>赞</span>
								<span :style='{"fontSize":"28rpx"}'>({{item.thumbsupnum}})</span>
							</view>
							<view v-if="comzanChange(item)" :style='{"background":"none"}' @click="comzanClick(item)">
								<span class="icon iconfont icon-zan11" :style='{"color":"#ff0000","fontSize":"28rpx"}'></span>
								<span :style='{"color":"#ff0000","fontSize":"28rpx","display":"none"}'>已赞</span>
								<span :style='{"color":"#ff0000","fontSize":"28rpx"}'>({{item.thumbsupnum}})</span>
							</view>
							<view v-if="!comzanChange(item)&&!comcaiChange(item)" :style='{"alignItems":"center","display":"flex"}' @click="comcaiClick(item)">
								<span class="icon iconfont icon-cai01" :style='{"fontSize":"28rpx"}'></span>
								<span :style='{"fontSize":"28rpx","display":"none"}'>踩</span>
								<span :style='{"fontSize":"28rpx"}'>({{item.crazilynum}})</span>
							</view>
							<view v-if="comcaiChange(item)" :style='{"background":"none"}' @click="comcaiClick(item)">
								<span class="icon iconfont icon-cai16" :style='{"color":"#ff0000","fontSize":"28rpx"}'></span>
								<span :style='{"color":"#ff0000","fontSize":"28rpx","display":"none"}'>已踩</span>
								<span :style='{"color":"#ff0000","fontSize":"28rpx"}'>({{item.crazilynum}})</span>
							</view>
						</view>
						<view :style='{"color":"#999","lineHeight":"24rpx","fontSize":"24rpx"}' class="margin-top-sm text-gray text-df">{{item.addtime}}</view>
						<view v-if="item.reply" :style='{"margin":"8rpx 0","lineHeight":"1.5","fontSize":"28rpx","color":"#666"}' class="text-gray text-content text-df">
							回复:<rich-text :nodes="item.reply"></rich-text>
						</view>
						<view style="display: flex;display: flex;justify-content: flex-end;padding: 6rpx 0;" v-if="user&&user.id==item.userid">
							<view style="color: #999;font-size: 16rpx;" @click="delClick(item.id)">删除</view>
						</view>
					</view>
				</view>

				<view class="bottom-content bg-white tabbar border shop" :style='{"padding":"20rpx 10rpx","margin":"20rpx 0","borderRadius":"20rpx","flexWrap":"wrap","background":"#EDEDED","display":"flex","width":"100%","height":"auto","order":"1"}'>

				</view>
			</view>
		</view>
	</view>

	<!-- 导航按钮 - 圆形，底部居中 -->
	<view v-if="showNavigation" :style='{"position":"fixed","bottom":"100rpx","left":"0","right":"0","display":"flex","justifyContent":"center","alignItems":"center","zIndex":"9999","pointerEvents":"none"}' class="navigation-buttons">
		<!-- 上一个按钮 -->
		<button
			v-if="hasPrev"
			hover-class="nav-btn-hover"
			:style='{"width":"200rpx","height":"200rpx","borderRadius":"50%","background":"#FFD128","color":"#fff","fontSize":"28rpx","display":"flex","flexDirection":"column","alignItems":"center","justifyContent":"center","boxShadow":"0 8rpx 24rpx rgba(255,209,40,0.4)","border":"none","pointerEvents":"auto","margin":"0 40rpx"}'
			@click="prevWord">
			<text class="icon iconfont icon-zuo" :style='{"fontSize":"40rpx","margin":"0 0 8rpx 0"}'></text>
			<text style="fontSize:28rpx">上一个</text>
		</button>
		<view v-else :style='{"width":"200rpx","height":"200rpx","borderRadius":"50%","background":"#ccc","color":"#999","fontSize":"28rpx","display":"flex","flexDirection":"column","alignItems":"center","justifyContent":"center","boxShadow":"none","margin":"0 40rpx"}'>
			<text class="icon iconfont icon-zuo" :style='{"fontSize":"40rpx","margin":"0 0 8rpx 0"}'></text>
			<text style="fontSize:28rpx">上一个</text>
		</view>

		<!-- 下一个按钮 -->
		<button
			v-if="hasNext"
			hover-class="nav-btn-hover"
			:style='{"width":"200rpx","height":"200rpx","borderRadius":"50%","background":"#FFD128","color":"#fff","fontSize":"28rpx","display":"flex","flexDirection":"column","alignItems":"center","justifyContent":"center","boxShadow":"0 8rpx 24rpx rgba(255,209,40,0.4)","border":"none","pointerEvents":"auto","margin":"0 40rpx"}'
			@click="nextWord">
			<text class="icon iconfont icon-you" :style='{"fontSize":"40rpx","margin":"0 0 8rpx 0"}'></text>
			<text style="fontSize:28rpx">下一个</text>
		</button>
		<view v-else :style='{"width":"200rpx","height":"200rpx","borderRadius":"50%","background":"#ccc","color":"#999","fontSize":"28rpx","display":"flex","flexDirection":"column","alignItems":"center","justifyContent":"center","boxShadow":"none","margin":"0 40rpx"}'>
			<text class="icon iconfont icon-you" :style='{"fontSize":"40rpx","margin":"0 0 8rpx 0"}'></text>
			<text style="fontSize:28rpx">下一个</text>
		</view>
	</view>
</mescroll-uni>

</view>
</template>

<script>
	export default {
		data() {
			return {
				btnColor: ['#409eff','#67c23a','#909399','#e6a23c','#f56c6c','#356c6c','#351c6c','#f093a9','#a7c23a','#104eff','#10441f','#a21233','#503319'],
				id: '',
				userid: '',
				detail: {},
				swiperList: [],
				commentList: [],
				mescroll: null,
				downOption: {
					auto: false
				},
				upOption: {
					noMoreSize: 3,
					textNoMore: '~ 没有更多了 ~',
					onScroll: true,
					toTop: true
				},
				hasNext: true,
				hasPrev: true,
				showNavigation: false,
				currentJibie: '',
				user: {},
				storeupFlag: 0,
				count: 0,
				timer: null,
				title:'',
				scrollTop: 0,
			}
		},
		components: {
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			},
			username() {
				return uni.getStorageSync("nickname")
			},
		},
		async onLoad(options) {
			// #ifdef APP-PLUS
			let page = getCurrentPages()
			this.href = this.baseUrl + 'front/h5/#/' + page[page.length - 1].route
			// #endif
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			this.id = options.id;
			if(options.userid) {
				this.userid = options.userid;
			}
			console.log('URL参数：', options);
			if(options.fromList) {
				this.showNavigation = true;
				console.log('设置 showNavigation = true');
			} else {
				console.log('fromList 不存在，showNavigation 保持 false');
			}
			if(options.jibie) {
				this.currentJibie = options.jibie;
				console.log('设置 currentJibie =', this.currentJibie);
			}
			console.log('最终 showNavigation =', this.showNavigation);
			this.init();
			this.hasNext = true
			if (this.mescroll) this.mescroll.resetUpScroll()
		},
		// #ifdef MP-WEIXIN
		onShareAppMessage(){
			var obj = {
				title: this.title,
				imageUrl: this.swiperList[0]?this.baseUrl + this.swiperList[0]: ''
			}
			return obj
		},
		// #endif
		onUnload() {
			if(this.timer) {
				clearInterval(this.timer);
			}
		},
		async onShow(options) {
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			this.btnColor = this.btnColor.sort(()=> {
				return (0.5-Math.random());
			});
			this.getStoreup();
			let cleanType = uni.getStorageSync('discussdancikuCleanType')
			if(cleanType){
				uni.removeStorageSync('discussdancikuCleanType')
				this.mescroll.num = 1
				this.upCallback(this.mescroll)
				this.init(2);
			}
			let crossCleanType = uni.getStorageSync('crossCleanType')
			if(crossCleanType) {
				uni.removeStorageSync('crossCleanType')
				res = await this.$api.info('danciku', this.id);
				let reg=new RegExp('http://localhost:8081/code/upload','g')//g代表全部
				this.detail = res.data;
				this.title = this.detail.danci
			}
		},
		destroyed: function() {
			//window.clearInterval(this.inter);
		},
		methods: {
			testClick() {
				console.log('=== 导航按钮容器被点击 ===');
			},
			scrollChange(e){
				this.scrollTop = e.scrollTop
			},
			scrollTopClick(){
				uni.pageScrollTo({
					scrollTop: 0
				})
			},
			imgView(url){
				let arr = []
				for(let x in this.swiperList){
					arr.push(this.swiperList[x].substr(0,4)=='http'?this.swiperList[x]:this.baseUrl + this.swiperList[x])
				}
				uni.previewImage({
					current: url,
					urls: arr
				})
			},
			async prevWord(){
				console.log('=== prevWord 被调用 ===');

				try {
					let params = {};
					let jibie = this.currentJibie || this.detail.jibie;
					if(jibie) {
						params.jibie = jibie;
					}

					console.log('调用prev接口，参数：', params);

					uni.request({
						url: this.baseUrl + `danciku/prev/${this.id}`,
						data: params,
						method: 'GET',
						success: (res) => {
							console.log('prev接口返回：', res.data);
							if(res.data.code === 0 && res.data.data) {
								console.log('切换到上一个单词，新ID:', res.data.data.id);
								this.id = res.data.data.id;
								this.init();
								this.checkNavigation();
								uni.pageScrollTo({
									scrollTop: 0,
									duration: 300
								});
							} else {
								this.hasPrev = false;
							}
						},
						fail: (err) => {
							console.error('prev接口失败：', err);
							this.hasPrev = false;
						}
					});
				} catch(err) {
					console.error('prevWord错误：', err);
					this.hasPrev = false;
				}
			},
			async nextWord(){
				console.log('=== nextWord 被调用 ===');

				try {
					let params = {};
					let jibie = this.currentJibie || this.detail.jibie;
					if(jibie) {
						params.jibie = jibie;
					}

					console.log('调用next接口，参数：', params);

					uni.request({
						url: this.baseUrl + `danciku/next/${this.id}`,
						data: params,
						method: 'GET',
						success: (res) => {
							console.log('next接口返回：', res.data);
							if(res.data.code === 0 && res.data.data) {
								console.log('切换到下一个单词，新ID:', res.data.data.id);
								this.id = res.data.data.id;
								this.init();
								this.checkNavigation();
								uni.pageScrollTo({
									scrollTop: 0,
									duration: 300
								});
							} else {
								this.hasNext = false;
							}
						},
						fail: (err) => {
							console.error('next接口失败：', err);
							this.hasNext = false;
						}
					});
				} catch(err) {
					console.error('nextWord错误：', err);
					this.hasNext = false;
				}
			},
			playAudio(){
				if(!this.detail.duyin) {
					this.$utils.msg('暂无读音');
					return;
				}

				let audioUrl = this.detail.duyin;
				if(audioUrl.substring(0, 4) !== 'http') {
					audioUrl = this.baseUrl + audioUrl;
				}

				const innerAudioContext = uni.createInnerAudioContext();
				innerAudioContext.src = audioUrl;
				innerAudioContext.play();

				innerAudioContext.onPlay(() => {
					console.log('开始播放');
				});

				innerAudioContext.onError((res) => {
					console.log(res.errMsg);
					this.$utils.msg('播放失败');
				});
			},
			async checkNavigation(){
				console.log('=== checkNavigation 被调用 ===');
				try {
					let prevParams = {};
					let jibie = this.currentJibie || this.detail.jibie;
					if(jibie) {
						prevParams.jibie = jibie;
					}

					uni.request({
						url: this.baseUrl + `danciku/prev/${this.id}`,
						data: prevParams,
						method: 'GET',
						success: (res) => {
							console.log('checkNavigation prev:', res.data);
							this.hasPrev = res.data.code === 0;
						},
						fail: (err) => {
							console.error('checkNavigation prev 失败：', err);
							this.hasPrev = false;
						}
					});
				} catch(err) {
					this.hasPrev = false;
				}

				try {
					let nextParams = {};
					let jibie = this.currentJibie || this.detail.jibie;
					if(jibie) {
						nextParams.jibie = jibie;
					}

					uni.request({
						url: this.baseUrl + `danciku/next/${this.id}`,
						data: nextParams,
						method: 'GET',
						success: (res) => {
							console.log('checkNavigation next:', res.data);
							this.hasNext = res.data.code === 0;
						},
						fail: (err) => {
							console.error('checkNavigation next 失败：', err);
							this.hasNext = false;
						}
					});
				} catch(err) {
					this.hasNext = false;
				}
			},
			// 拨打电话
			callClick(row){
				uni.makePhoneCall({
					phoneNumber: row
				})
			},
			// 支付
			onPayTap(){
				let that = this
				if(!this.user){
					this.$utils.msg("请先登录")
					setTimeout(()=>{
						that.$utils.jump('../login/login')
					},1500)
					return false
				}
				uni.setStorageSync('paytable','danciku');
				uni.setStorageSync('payObject',this.detail);
				this.$utils.jump('../pay-confirm/pay-confirm?type=1')
			},
			onDetailTap(item) {
				uni.setStorageSync("useridTag",this.userid);
				this.$utils.jump(`./detail?id=${item.id}&userid=`+this.userid)
			},
			// 收藏
			async getStoreup() {
				if(!this.user){
					return false
				}
				let params = {
					page: 1,
					limit: 1,
					refid : this.id,
					tablename : 'danciku',
					userid: this.user.id,
					type: '1',
				}
				let res = await this.$api.list(`storeup`, params);
				this.storeupFlag = res.data.list.length;
			},
			async shoucang(){
				let that = this
				if(!this.user){
					this.$utils.msg("请先登录")
					setTimeout(()=>{
						that.$utils.jump('../login/login')
					},1500)
					return false
				}
				let _this = this;
				let params = {
					page: 1,
					limit: 1,
					refid : _this.detail.id,
					tablename : 'danciku',
					userid: _this.user.id,
					type: '1',
				}
				let res = await _this.$api.list(`storeup`, params);
				if (res.data.list.length == 1) {
					let storeupId = res.data.list[0].id;
					uni.showModal({
						title: '提示',
						content: '是否取消',
						success: async function(res) {
							if (res.confirm) {
								_this.detail.storeupnum--
								await _this.$api.update('danciku',_this.detail)
								await _this.$api.del('storeup', JSON.stringify([storeupId]));
								_this.$utils.msg('取消成功');
								_this.getStoreup();
							}
						}
					});
					return;
				}
				uni.showModal({
					title: '提示',
					content: '是否收藏',
					success: async function(res) {
						if (res.confirm) {
							await _this.$api.add('storeup', {
								userid: _this.user.id,
								name: _this.detail.danci,
								picture: _this.swiperList[0],
								refid: _this.detail.id,
								tablename: 'danciku',
								type: '1'
							});
							_this.detail.storeupnum++
							await _this.$api.update('danciku',_this.detail)
							_this.$utils.msg('收藏成功');
							_this.getStoreup();
						}
					}
				});
			},
			// 跨表
			async onAcrossTap(tableName,crossOptAudit,crossOptPay,statusColumnName,tips,statusColumnValue,type=1){
				let that = this
				if(!this.user){
					this.$utils.msg("请先登录")
					setTimeout(()=>{
						that.$utils.jump('../login/login')
					},1500)
					return false
				}
				uni.setStorageSync('crossTable','danciku');
				uni.setStorageSync(`crossObj`, this.detail);
				uni.setStorageSync(`statusColumnName`, statusColumnName);
				uni.setStorageSync(`statusColumnValue`, statusColumnValue);
				uni.setStorageSync(`tips`, tips);
				if(statusColumnName!=''&&!statusColumnName.startsWith("[")) {
					var obj = uni.getStorageSync('crossObj');
					for (var o in obj){
						if(o==statusColumnName && obj[o]==statusColumnValue){
							this.$utils.msg(tips);
							return
						}
					}
				}
				this.$utils.jump(`../${tableName}/add-or-update?cross=true`);
			},
			// 获取详情
			async init(type=1){
				if(this.timer) {
					clearInterval(this.timer);
				}
				let res = await this.$api.info('danciku', this.id);
				console.log('单词详情数据：', res.data);
				console.log('读音字段 duyin：', res.data.duyin);
				let reg=new RegExp('http://localhost:8081/code/upload','g')
				this.detail = res.data;

				this.title = this.detail.danci

				this.hasPrev = true;
				this.hasNext = true;
				this.checkNavigation();


				this.swiperList = this.detail.fengmiantu ? this.detail.fengmiantu.split(",") : [];
				


				if(type==2){
					this.detail.discussnum++
					await this.$api.update('danciku',this.detail)
				}
			},
			// mescroll组件初始化的回调,可获取到mescroll对象
			mescrollInit(mescroll) {
				this.mescroll = mescroll;
			},

			/*下拉刷新的回调 */
			downCallback(mescroll) {
				this.hasNext = true
				mescroll.resetUpScroll()
			},

			/*上拉加载的回调: mescroll携带page的参数, 其中num:当前页 从1开始, size:每页数据条数,默认10 */
			async upCallback(mescroll) {
				if(uni.getStorageSync("appUserid")){
					let res = await this.$api.list('discussdanciku', {
						page: mescroll.num,
						limit: 10,
						refid: Number(this.id),
						sort: 'istop',
						order: 'desc',
					});
					// 如果是第一页数据置空
					if (mescroll.num == 1) this.commentList = [];
					for(let x in res.data.list){
						if(res.data.list[x].content){
							res.data.list[x].content = res.data.list[x].content.replace(/img src/gi,"img style=\"width:100%;\" src")
						}
					}
					this.commentList = this.commentList.concat(res.data.list);
					if (res.data.list.length == 0) this.hasNext = false;
				}
				mescroll.endSuccess(mescroll.size, this.hasNext);

			},
			comzanChange(row){
				if(row.tuserids){
					let arr = String(row.tuserids).split(',')
					for(let x in arr){
						if(arr[x] == this.user.id){
							return true
						}
					}
				}
				return false
			},
			async comzanClick(row){
				if(!this.user){
					return false
				}
				if(!this.comzanChange(row)){
					row.thumbsupnum++
					if(row.tuserids){
						row.tuserids = row.tuserids + ',' + this.user.id
					}else {
						row.tuserids = this.user.id
					}
					await this.$api.update('discussdanciku',row)
					this.$utils.msg('点赞成功');
				}else {
					row.thumbsupnum--
					let arr = String(row.tuserids).split(',')
					for(let x in arr){
						if(arr[x] == this.user.id){
							arr.splice(x,1)
						}
					}
					row.tuserids = arr.join(',')
					await this.$api.update('discussdanciku',row)
					this.$utils.msg('取消成功');
				}
				this.$forceUpdate()
			},
			comcaiChange(row){
				if(row.cuserids){
					let arr = String(row.cuserids).split(',')
					for(let x in arr){
						if(arr[x] == this.user.id){
							return true
						}
					}
				}
				return false
			},
			async comcaiClick(row){
				if(!this.user){
					return false
				}
				if(!this.comcaiChange(row)){
					row.crazilynum++
					if(row.cuserids){
						row.cuserids = row.cuserids + ',' + this.user.id
					}else {
						row.cuserids = this.user.id
					}
					await this.$api.update('discussdanciku',row)
					this.$utils.msg('点踩成功');
				}else {
					row.crazilynum--
					let arr = String(row.cuserids).split(',')
					for(let x in arr){
						if(arr[x] == this.user.id){
							arr.splice(x,1)
						}
					}
					row.cuserids = arr.join(',')
					await this.$api.update('discussdanciku',row)
					this.$utils.msg('取消成功');
				}
				this.$forceUpdate()
			},



			onChatTap() {
				this.$utils.jump('../chat/chat')
			},
			// 下载
			download(url ){
				if(!url){
					return false
				}
				let _this = this;
				url=_this.$base.url +  url;
				uni.downloadFile({
					url: url,
					success: (res) => {
						if (res.statusCode === 200) {
							_this.$utils.msg('下载成功');
							// #ifdef H5
							 window.open(url);
							// #endif
							// #ifndef H5
							uni.saveFile({
								tempFilePath: res.tempFilePath, //临时路径
								success: function(obj) {
									uni.showToast({
										icon: 'success',
										mask: true,
										title: '下载成功' , 
										duration: 2000,
									});
									setTimeout(() => {
										console.log('obj.savedFilePath',obj.savedFilePath);
										var filePath = obj.savedFilePath;
										uni.openDocument({ //新开页面打开文档，支持格式：doc, xls, ppt, pdf, docx, xlsx, pptx。
											filePath: filePath,
											showMenu: true,
											success: function(res) {
												console.log('打开文档成功');
											}
										});
									}, 2000)
								}
							});
							// #endif
						}
					}
				});
			},
			//
			onCartTabTap() {
				this.$utils.tab('../shop-cart/shop-cart')
			},
			// 添加评论
			async onCommentTap() {
				let that = this
				if(!this.user){
					this.$utils.msg("请先登录")
					setTimeout(()=>{
						that.$utils.jump('../login/login')
					},1500)
					return false
				}
				let res = {}
				this.$utils.jump(`../discussdanciku/add-or-update?refid=${this.id}` )
			},
			delClick(id){
				let that = this
				uni.showModal({
					title: '提示',
					content: '是否删除此评论？',
					async success(res) {
						if(res.confirm){
							await that.$api.del('discussdanciku',JSON.stringify([id]))
							that.$utils.msg('删除成功')
							that.detail.discussnum--
							await that.$api.update('danciku',that.detail)
							setTimeout(()=>{
								that.mescroll.num = 1
								that.upCallback(that.mescroll)
							},1500)
						}
					}
				})
			},

		}
	}
</script>

<style lang="scss">
	page {
	  --animate-duration: 1s;
	  --animate-delay: 1s;
	  --animate-repeat: 1;
	}
	
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
		padding-bottom: 120rpx;
	}

	.nav-btn-hover {
		opacity: 0.9;
		transform: scale(0.95);
		box-shadow: 0 8rpx 24rpx rgba(255,209,40,0.4) !important;
	}

	.audio-btn-hover {
		filter: drop-shadow(0 4rpx 8rpx rgba(0,0,0,0.3));
		transform: scale(1.05);
	}

	.audio-btn-hover image {
		filter: drop-shadow(0 2rpx 6rpx rgba(0,0,0,0.3));
	}
	
	.navigation-buttons {

		button {
			&::after {
				border: none;
			}

			&:active {
				opacity: 0.9;
				transform: scale(0.95);
			}
		}
	}

	.seat-list {
		display: flex;
		align-items: center;
		flex-wrap: wrap;
		background: #FFFFFF;
		margin: 20upx;
		border-radius: 20upx;
		padding: 20upx;
		font-size: 30upx;
		.seat-item {
			width: 33.33%;
			display: flex;
			align-items: center;
			flex-direction: column;
			margin-bottom: 20upx;
	
			.seat-icon {
				width: 50upx;
				height: 50upx;
				margin-bottom: 10upx;
			}
		}
	}
	
	audio {
		display: flex;
		flex-direction: column;
	}
	
	.audio .uni-audio-default {
		width: inherit !important;
	}
	

</style>
