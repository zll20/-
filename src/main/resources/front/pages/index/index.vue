<template>
	<view class="content">
		<view :style='{"minHeight":"100%","width":"100%","flexWrap":"wrap","background":"#F6F7F9","display":"flex","height":"auto"}'>
			<!-- menu -->
			<view v-if="true" class="menu_view">
				<!-- 自定义导航入口：单词库、听力、文刊、互动学习 -->
				<view class="menu-item" @tap="onPageTap('danciku')">
					<view class="iconarr cuIcon-pic" :style="{'background':menuColor[0]}"></view>
					<view class="text">单词库</view>
				</view>
				<view class="menu-item" @tap="onPageTapCustom('tingli')">
					<view class="iconarr cuIcon-sound" :style="{'background':menuColor[1]}"></view>
					<view class="text">听力</view>
				</view>
				<view class="menu-item" @tap="onPageTapCustom('wenkan')">
					<view class="iconarr cuIcon-read" :style="{'background':menuColor[2]}"></view>
					<view class="text">文刊</view>
				</view>
				<block v-for="(item,index1) in menuList" v-bind:key="item.roleName">
					<block v-if="index1==0" v-bind:key="index" v-for=" (menu,index) in item.frontMenu">
						<block v-bind:key="sort" v-for=" (child,sort) in menu.child">
							<block v-bind:key="sort2" v-for=" (button,sort2) in child.buttons">
								<view class="menu-item" v-if="button=='查看' && child.tableName!='danciku' && child.tableName!='yifahuodingdan' && child.tableName!='yituikuandingdan' &&child.tableName!='yiquxiaodingdan' && child.tableName!='weizhifudingdan' && child.tableName!='yizhifudingdan' && child.tableName!='yiwanchengdingdan' " @tap="onPageTap2(child.tableName)">
									<view class="iconarr" :class="child.appFrontIcon" :style="{'background':menuColor[index1+3]}"></view>
									<view class="text">{{child.menu.split("列表")[0]}}</view>
								</view>
							</block>
						</block>
					</block>
				</block>
			</view>
			<!-- 商品推荐 -->
			<view class="listBox recommend" :style='{"width":"94%","margin":"20rpx 3% 20rpx 3%","borderRadius":"20rpx","background":"#fff","order":"4"}'>
				<view class="title" :style='{"width":"100%","padding":"0 0 0 40rpx","margin":"0","background":"url(http://codegen.caihongy.cn/20250214/607fdecc1a9b45e7a35d80592775c303.png) center center/100% 100%","justifyContent":"space-between","display":"flex"}'>
					<view :style='{"color":"#333","textAlign":"left","flex":"1","width":"100%","lineHeight":"100rpx","fontSize":"32rpx","fontWeight":"500"}'>单词库推荐</view>
					<view :style='{"margin":"0 20rpx 0 0","alignItems":"center","justifyContent":"center","display":"flex"}' @tap="onPageTap('danciku')">
						<text :style='{"color":"#666","fontSize":"28rpx","whiteSpace":"nowrap"}'>更多</text>
						<text class="icon iconfont icon-jinru" :style='{"color":"#666","fontSize":"28rpx"}'></text>
					</view>
				</view>
				<!-- 样式3 -->
				<view class="list-box style3" :style='{"width":"100%","padding":"0 24rpx 0 24rpx","margin":"24rpx 0 0 0","height":"auto"}'>
					<view @tap="onDetailTap('danciku',product.id)" v-for="(product,index) in dancikulist" :key="index" class="list-item" :style='{"boxShadow":"none","margin":"0 0 20rpx 0","borderRadius":"12rpx","flexWrap":"wrap","background":"#fff","display":"flex","width":"100%","position":"relative","justifyContent":"space-between","height":"auto"}'>
						<image :style='{"width":"30%","objectFit":"cover","borderRadius":"12rpx","display":"block","height":"240rpx"}' class="list-item-image" mode="aspectFill" v-if="product.fengmiantu.substring(0,4)=='http'" :src="product.fengmiantu"></image>
						<image :style='{"width":"30%","objectFit":"cover","borderRadius":"12rpx","display":"block","height":"240rpx"}' class="list-item-image" mode="aspectFill" v-else :src="product.fengmiantu?baseUrl+product.fengmiantu.split(',')[0]:''"></image>
						<view class="list-item-body" :style='{"alignContent":"center","padding":"0 20rpx","margin":"0","flexWrap":"wrap","display":"flex","width":"68%","height":"auto"}'>
							<view :style='{"padding":"0","margin":"0","overflow":"hidden","whiteSpace":"nowrap","color":"#333","width":"100%","lineHeight":"48rpx","fontSize":"28rpx","textOverflow":"ellipsis"}' class="list-item-title">{{product.danci}}</view>
							<view :style='{"padding":"0 10rpx 0 0","order":"5"}'>
								<text class="icon iconfont icon-shijian21" :style='{"margin":"0 4rpx 0 0","lineHeight":"50rpx","fontSize":"24rpx","color":"#666"}'></text>
								<text :style='{"color":"#666","lineHeight":"50rpx","fontSize":"24rpx"}'>{{product.addtime.split(' ')[0].replace(/\-/g,'-')}}</text>
							</view>
							<view :style='{"padding":"0 10rpx 0 0","display":"inline-block","order":"2"}'>
								<text class="icon iconfont icon-shoucang10" :style='{"margin":"0 4rpx 0 0","lineHeight":"50rpx","fontSize":"24rpx","color":"#666"}'></text>
								<text :style='{"color":"#666","lineHeight":"50rpx","fontSize":"24rpx"}'>{{product.storeupnum}}</text>
							</view>
						</view>
					</view>
				</view>
			</view>
			<!-- 商品推荐 -->
			
			<!-- 商品列表 -->
			<!-- 商品列表 -->
			<!-- 新闻资讯 -->
			<!-- 新闻资讯 -->
			<view v-if="scrollTop>200" @tap="scrollTopClick" :style='{"boxShadow":"0 8rpx 16rpx rgba(0,0,0,.3)","borderRadius":"50%","textAlign":"center","bottom":"20%","background":"#ff000030","width":"60rpx","lineHeight":"60rpx","position":"fixed","right":"20rpx","height":"60rpx","zIndex":"999"}'>
				<span class="icon iconfont icon-jiantou07" :style='{"color":"#fff"}'></span>
			</view>
		</view>
	</view>
</template>

<script>
    import menu from '@/utils/menu'
	import '@/assets/css/global-restaurant.css'
	import uniIcons from "@/components/uni-ui/lib/uni-icons/uni-icons.vue"
	export default {
		components: {
			uniIcons
		},
		data() {
			return {
				startX: 0,
				prevNumList4: '',
				numList4: 0,
				timerList4: null,
				flagList4: false,
				navigationActive: {"width":"16rpx","margin":"0 4rpx","borderRadius":"100%","background":"#FFD128","height":"16rpx"},
				navigationDefault: {"width":"16rpx","margin":"0 4rpx","borderRadius":"100%","background":"#FFD12850","height":"16rpx"},
				options2: {
					effect: 'flip',
					loop : true
				},
				options3: {
					effect: 'cube',
					loop : true,
					cubeEffect: {
						shadow: true,
						slideShadows: true,
						shadowOffset: 20,
						shadowScale: 0.94,
					}
				},
				rows: 2,
				column: 4,
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
				role : '',
				menuList: [],
				swiperMenuList:[],
				user: {},
				tableName:'',
				menuColor: '#409eff,#67c23a,#909399,#e6a23c,#f56c6c,#356c6c,#351c6c,#f093a9,#a7c23a,#104eff,#10441f,#a21233,#503319'.split(','),

				dancikulist: [],
				news: [],
				scrollTop: 0,
			}
		},
		onPageScroll(e) {
			this.scrollTop = e.scrollTop
		},
		watch: {
		},
		mounted() {
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			},
			username() {
				return uni.getStorageSync("nickname")
			},
		},
		async onLoad(){
			this.menuColor = this.menuColor.sort(()=> {
				return (0.5-Math.random());
			});
		},
		async onShow() {
			if (this.timerList4&&this.timerList4!=null) clearInterval(this.timerList4)
			this.swiperMenuList = []
			this.role = uni.getStorageSync("appRole");
			let table = uni.getStorageSync("nowTable");
			let res = {}
			if(table) {
				res = await this.$api.session(table);
				this.user = res.data;
				this.tableName = table;
			}
			let menus = menu.list();
			this.menuList = menus;
			this.menuList.forEach((item,key) => {
				if(key==0) {
					item.frontMenu.forEach((item2,key2) => {
						if(item2.child[0].buttons.indexOf("查看")>-1) {
							this.swiperMenuList.push(item2);
						}
					})
				}
			})
			// let res;
			// 推荐信息
			this.getRecommendList()
			this.getHomeList()
			this.getNewsList()
		},
		methods: {
			onPageTapCustom(type) {
				if(type === 'tingli') {
					uni.navigateTo({
						url: '/pages/tingli/list'
					});
				} else if(type === 'wenkan') {
					uni.showToast({
						title: '文刊功能开发中',
						icon: 'none'
					});
				}
			},
			scrollTopClick(){

			},
			uGetRect(selector, all) {
				return new Promise(resolve => {
					uni.createSelectorQuery()
					.in(this)
					[all ? 'selectAll' : 'select'](selector)
					.boundingClientRect(rect => {
						if (all && Array.isArray(rect) && rect.length) {
							resolve(rect);
						}
						if (!all && rect) {
							resolve(rect);
						}
					})
					.exec();
				});
			},
			cloneData(data) {
				return JSON.parse(JSON.stringify(data));
			},
			async getNewsList(){
				let res;
				let params = {
					page: 1,
					limit: 6,
					sort: 'id',
					order: 'desc',
				}
			},
			homeTabClick2(index,name){
				this['home' + name + 'Index2'] = index
				this.getHomeList()
			},
			async getHomeList(){
				let res;
				let params;
			},
			recommendTabClick2(index,name){
				this[name + 'Index2'] = index
				this.getRecommendList()
			},
			async getRecommendList(){
				let res;
				let params;
				// 推荐信息
				params = {
					page: 1,
					limit: 4,
				}
				if(uni.getStorageSync("appUserid")) {
					res = await this.$api.recommend2('danciku', params);
				} else {
					res = await this.$api.recommend('danciku', params);
				}
				this.dancikulist = res.data.list
				

			},
			autoPlayList4() {
				this.prevNumList4 = this.numList4
			
				this.numList4++
				if (this.numList4 == this.swiperList.length) this.numList4 = 0
			},
			touchStart(event) {
				this.startX = event.touches[0].clientX
				
				clearInterval(this.timerList4)
				this.flagList4 = true
			},
			touchMove(event) {
				const currentX = event.touches[0].clientX;
				const deltaX = currentX - this.startX;
				
				if (deltaX > 50) {
					// 向右滑动逻辑
					if (this.flagList4) {
						this.flagList4 = false
						
						this.prevNumList4 = this.numList4
						this.numList4++
						if (this.numList4 == this.swiperList.length) this.numList4 = 0
					}
					
				} else if (deltaX < -50) {
					// 向左滑动逻辑
					if (this.flagList4) {
						this.flagList4 = false
						
						this.prevNumList4 = this.numList4
						this.numList4--
						if (this.numList4 < 0) this.numList4 = this.swiperList.length - 1
					}
				}
			},
			touchEnd() {
				this.startX = 0
				this.timerList4 = setInterval(this.autoPlayList4, 3000)
				this.flagList4 = false
			},
			// 新闻详情
			onNewsDetailTap(id) {
				this.$utils.jump(`../news-detail/news-detail?id=${id}`)
			},
			// 推荐列表点击详情
			onDetailTap(tableName, id) {
				let url = `../${tableName}/detail?id=${id}`
				if(tableName=='forum') {
					url = `../forum-detail/forum-detail?id=${id}`
				}
				this.$utils.jump(url)
			},
			onPageTap(tableName){
				let url = `../${tableName}/list`
				if(tableName=='forum') {
					url = '../forum-index/forum-index'
				}
				uni.navigateTo({
					url: url,
					fail: function(){
						uni.switchTab({
							url: url
						});
					}
				});
			},
			onPageTap2(index) {
				let url = '../' + index + '/list'
				if(index=='forum'){
					url = '../forum-index/forum-index'
				}
				uni.setStorageSync("useridTag",0);
				uni.navigateTo({
					url: url,
					fail: function() {
						uni.switchTab({
							url: url
						});
					}
				});
			}
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
	}
	.list-swiper-4 .animate__animated {
		--animate-delay: 300ms;
	}

	.menu_view {
		border-radius: 20rpx;
		padding: 0 0 20rpx 0;
		margin: 20rpx 3% 20rpx 3%;
		background: #fff;
		display: flex;
		width: 94%;
		justify-content: flex-start;
		flex-wrap: wrap;
		height: auto;
		order: 3;
		.menu-item {
			padding: 12rpx 0 4rpx;
			margin: 10rpx 1% 0;
			width: 23%;
			height: auto;
			.iconarr {
				border-radius: 20rpx;
				padding: 0;
				margin: 0px auto;
				color: #fff;
				background: #FFD128;
				display: block;
				width: 100rpx;
				font-size: 64rpx;
				line-height: 100rpx;
				text-align: center;
				height: 100rpx;
			}
			.text {
				padding: 0;
				margin: 12rpx auto 0;
				color: #333;
				width: 100%;
				font-size: 28rpx;
				line-height: 28rpx;
				text-align: center;
			}
		}
	}
</style>
