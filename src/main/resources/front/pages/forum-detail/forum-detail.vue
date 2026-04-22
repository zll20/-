<template>
	<view :style='{"minHeight":"100vh","padding":"0 0 120rpx 0","flexWrap":"wrap","background":"#F6F7F9","display":"flex","width":"100%","position":"relative"}'>
		<swiper :style='{"width":"100%","borderRadius":"30rpx","background":"#fff","height":"360rpx"}' class="swiper" :indicator-dots='false' :autoplay='false' :circular='false' indicator-active-color='#000000' indicator-color='rgba(0, 0, 0, .3)' :duration='500' :interval='5000' :vertical='false'>
			<swiper-item :style='{"width":"100%","background":"#fff","height":"360rpx"}' v-for="(swiper,index) in swiperList" :key="index">
				<image :style='{"width":"100%","objectFit":"cover","display":"block","height":"360rpx"}' mode="aspectFill" v-if="swiper.substring(0,4)=='http'" :src="swiper" @tap="imgView2(swiper)"></image>
				<image :style='{"width":"100%","objectFit":"cover","display":"block","height":"360rpx"}' mode="aspectFill" v-else :src="baseUrl+swiper" @tap="imgView2(baseUrl+swiper)"></image>
			</swiper-item>
		</swiper>
		<view :style='{"padding":"20rpx","margin":"20rpx 0 0 0","borderRadius":"30rpx 30rpx 0 0","textAlign":"center","background":"#fff","width":"100%","fontSize":"36rpx","fontWeight":"700"}'>
			{{forum.title}}
		</view>
		<view :style='{"padding":"10rpx 0 10rpx 0","color":"#999","textAlign":"center","background":"#fff","width":"100%","fontSize":"20rpx","fontWeight":"700"}'>
			<span class="icon iconfont icon-touxiang18" :style='{"padding":"0 10rpx 0 0","fontSize":"24rpx"}'></span>
			<span :style='{"padding":"0 10rpx 0 0","fontSize":"24rpx"}'>发布人：</span>
			<span :style='{"padding":"0","fontSize":"24rpx"}'>{{forum.userid!=userid&&forum.isanon==1?'匿名':forum.username}}</span>
		</view>
		<view :style='{"border":"2rpx solid #eeeeee","padding":"30rpx","borderWidth":"0 0 2rpx 0","background":"#fff","letterSpacing":"4rpx","fontSize":"30rpx","lineHeight":"52rpx","order":"1"}'>
			<rich-text :nodes="forum.content"></rich-text>
		</view>
		<view :style='{"padding":"0 20rpx 0 20rpx","margin":"40rpx 0 40rpx 0","overflow":"hidden","borderRadius":"30rpx","background":"#fff","width":"100%","order":"3"}'>
			<view :style='{"width":"100%","margin":"20rpx 0","background":"#fff"}' v-for="comment in commentData" :key="comment.id">
				<view :style='{"padding":"20rpx 0","alignItems":"center","color":"#ccc","justifyContent":"flex-start","display":"flex"}'>
					<image class="image" :src="baseUrl+comment.avatarurl" mode="widthFix" :style='{"width":"60rpx","height":"60rpx"}'></image>
					<view :style='{"padding":"0 0 0 20rpx","flexDirection":"column","display":"flex","width":"100%","fontSize":"24rpx","lineHeight":"1","justifyContent":"center"}'>
						<view :style='{"color":"#000","lineHeight":"2","fontSize":"26rpx"}'>
							{{comment.username}}
						</view>
						<view :style='{"lineHeight":"1","fontSize":"24rpx"}'> {{comment.addtime}}</view>
					</view>
				</view>
				<view :style='{"border":"2rpx solid #D8D8D8","padding":"0 20rpx 20rpx 80rpx","fontSize":"28rpx","color":"#333","borderWidth":"0 0 2rpx"}'>
					<rich-text :style='{"color":"#000"}' :nodes="comment.content"></rich-text>
					<view :style='{"width":"100%","alignItems":"center","justifyContent":"flex-end","display":"flex"}'>
						<text @tap="onReplyTap(comment.id)" :style='{"color":"#FFD128","padding":"0 12rpx"}'>回复</text>
						<text @tap="delClick(comment.id)" v-if="userid==comment.userid" :style='{"color":"#B30000"}'>删除</text>
					</view>
				</view>
				<view :style='{"width":"100%","padding":"0 0 0 120rpx"}' v-if="comment.childs">
					<view :style='{"width":"100%"}' v-for="item in comment.childs" :key="item.id">
						<view :style='{"padding":"20rpx 0","alignItems":"center","color":"#ccc","justifyContent":"flex-start","display":"flex"}'>
							<image :style='{"width":"60rpx","height":"60rpx"}' :src="baseUrl+item.avatarurl" mode="widthFix"></image>
							<view :style='{"padding":"0 0 0 20rpx","flexDirection":"column","display":"flex","width":"100%","fontSize":"24rpx","lineHeight":"1","justifyContent":"center"}'>
								<view :style='{"color":"#000","lineHeight":"2","fontSize":"26rpx"}'>
									{{item.username}}
								</view>
								<view :style='{"lineHeight":"1","fontSize":"24rpx"}'>{{item.addtime}}</view>
							</view>
						</view>
						<view :style='{"border":"2rpx solid #D8D8D8","padding":"0 20rpx 20rpx 80rpx","fontSize":"28rpx","color":"#333","borderWidth":"0 0 2rpx"}'>
							<rich-text :style='{"color":"#000"}' :nodes="item.content"></rich-text>
							<view :style='{"width":"100%","alignItems":"center","justifyContent":"flex-end","display":"flex"}'>
								<text :style='{"color":"#B30000"}' @tap="delClick(item.id)" v-if="userid==item.userid">删除</text>
							</view>
						</view>
					</view>
				</view>
			</view>
			<view class="kong"></view>
			
			<view class="bottom-content">
				<button @tap="onReplyTap(forum.id)" class="bg-red margin-tb-sm">评论</button>
			</view>
		</view>
	</view>
</template>
<script>
	import userReply from '@/components/forum-reply/forum-reply.vue'
	export default {
		components: {
			userReply
		},
		data() {
			return {
				commentData: [],
				forum: {},
				id: '',
				swiperList: [],
			}
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			},
			userid(){
				return uni.getStorageSync('appUserid')
			}
		},
		async onLoad(options) {
			this.id = options.id;
			this.init()
		},
		async onShow() {
			let forumComType = uni.getStorageSync('forumComType');
			if(forumComType){
				uni.removeStorageSync('forumComType')
				this.init()
			}
		},
		methods: {
			async init() {
				let res = await this.$api.forumDetail(this.id)
				this.swiperList = res.data.cover ? res.data.cover.split(",") : [];
				res.data.childs = this.changeContent(res.data.childs)
				this.commentData = res.data.childs;
				res.data.content = res.data.content.replace(/img src/gi,"img style=\"width:100%;\" src");
				this.forum = res.data;
			},
			imgView(url,index){
				let that = this
				uni.showActionSheet({
					itemList: ['预览','删除'],
					success(res) {
						if(res.tapIndex==0){
							let arr = []
							for(let x in that.picList){
								arr.push(that.baseUrl + that.picList[x])
							}
							uni.previewImage({
								current: that.baseUrl + url,
								urls: arr
							})
						}else if(res.tapIndex==1){
							that.picList.splice(index,1)
						}
					}
				})
			},
			imgView2(url){
				let arr = []
				for(let x in this.swiperList){
					arr.push(this.swiperList[x].substr(0,4)=='http'?this.swiperList[x]:this.baseUrl + this.swiperList[x])
				}
				uni.previewImage({
					current: url,
					urls: arr
				})
			},
			changeContent(childs){
				if(childs&&childs!=null&&childs.length){
					childs = change(childs)
				}
				return childs
				function change(childs){
					for(let x in childs){
						childs[x].content = childs[x].content.replace(/img src/gi,"img style=\"width:100%;\" src");
						if(childs[x].childs){
							childs[x].childs = change(childs[x].childs)
						}
					}
					return childs
				}
			},
			onReplyTap(pid) {
				this.$utils.jump(`../forum-reply/forum-reply?pid=${pid}`);
			},
			// 删除当前评论
			delClick(id) {
				let that = this
				uni.showModal({
					title: '提示',
					content: '是否删除此评论？',
					async success(res) {
						if (res.confirm) {
							await that.$api.del('forum', JSON.stringify([id]))
							that.$utils.msg('删除成功')
							setTimeout(async () => {
								let obj = await that.$api.forumDetail(that.id)
								obj.data.childs = that.changeContent(obj.data.childs)
								that.commentData = obj.data.childs;
							}, 1500)
						}
					}
				})
			},
		}
	}
</script>

<style scoped lang="scss">
	page {
		background: #FFFFFF;
	}

	.title {
		text-align: center;
		font-size: 40upx;
		font-weight: bold;
		margin: 20upx;
	}
	
	.username{
		text-align: center;
		font-size: 20upx;
		font-weight: bold;
		color: #888888;
		margin: 20upx;
	}
	
	.content {
		margin: 40upx;
		font-size: 30upx;
		line-height: 50upx;
		letter-spacing: 5upx;
	}

	.article-meta {
		padding: 20rpx 20rpx;
		display: flex;
		align-items: center;
		flex-direction: row;
		justify-content: flex-start;
		color: gray;
	}

	.article-header-image {
		width: 60rpx;
		height: 60rpx;
		flex-shrink: 0;
		/* border-radius: 50%; */
		overflow: hidden;
	}

	.article-header-image .image {
		width: 100%;
		height: 100%;
	}

	.article-header-content {
		display: flex;
		flex-direction: column;
		justify-content: center;
		padding-left: 10px;
		font-size: 24rpx;
		line-height: 1;
		width: 100%;
		/* height: 60rpx; */
	}

	.article-header-title {
		display: flex;
		justify-content: space-between;
		align-items: center;
		font-size: 26rpx;
		line-height: 2;
		color: #000;
	}

	.article-header-info {
		font-size: 24rpx;
		line-height: 1;
	}

	.article-top {
		display: flex;
		align-items: flex-end;
		font-size: 26rpx;
		line-height: 1;
		color: #999;
	}

	.article-top .image {
		width: 30rpx;
		margin-left: 5rpx;
	}

	.uni-article__comment {
		margin-bottom: 160upx;
	}

	.uni-article__comment-title {
		width: 100%;
		line-height: 3;
		font-size: 28rpx;
		color: #666;
		background: #f5f5f5;
		padding-left: 20rpx;
	}

	.uni-article__comment-centent {
		border-bottom: 1px #eee solid;
		padding: 0 20rpx;
		padding-left: 95rpx;
		padding-bottom: 20rpx;
		font-size: 14px;
		color: #333;
	}

	.btn-replay {
		color: red;
		width: 100%;
		display: flex;
		align-items: center;
		justify-content: flex-end;
	}
	
	.bottom-content{
		position: fixed;
		bottom: 0;
		padding: 20upx;
		width: 100%;
		left: 0;
	}

	.kong {
		width: 100%;
		height: 180rpx;
	}
</style>
