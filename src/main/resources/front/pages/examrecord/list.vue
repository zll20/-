<template>
	<mescroll-uni @init="mescrollInit" :up="upOption" :down="downOption" @down="downCallback" @up="upCallback" @scroll="scrollChange">
		<view class="cu-bar bg-white search" :style='{"width":"100%","padding":"20rpx 24rpx","background":"#fff","display":"flex","height":"auto"}'>
			<view class="search-form round" :style='{"margin":"0","flex":"1","position":"relative"}'>
				<text class="icon iconfont icon-fangdajing07" :style='{"color":"#FFD128","left":"0px","textAlign":"center","width":"80rpx","fontSize":"40rpx","lineHeight":"80rpx","position":"absolute","right":"0px"}'></text>
				<input :style='{"border":"2rpx solid #FFD128","padding":"12rpx 20rpx 12rpx 80rpx","color":"#000","borderRadius":"0","background":"#fff","width":"100%","lineHeight":"56rpx","fontSize":"28rpx","height":"80rpx"}' v-model="searchForm.papername" type="text" placeholder="试卷名称" ></input>
			</view>
			<button :style='{"border":"0","padding":"0px","margin":"0","color":"#fff","borderRadius":"0","background":"#FFD128","width":"136rpx","lineHeight":"80rpx","fontSize":"28rpx","height":"80rpx"}' @tap="search" class="cu-btn shadow-blur round">搜索</button>
		</view>
		<view @tap="onDetailTap(item)" class="cu-list menu-avatar" v-for="(item,index) in list" v-bind:key="index">
			<view class="cu-item">
				<view class="content" style="left: 20rpx;">
					<view class="text-grey">试卷名称：{{item.papername}}&nbsp;&nbsp;({{item.username}})</view>
				</view>
				<view class="action" style="width: auto;display:flex;flex-direction:column;" v-if="item.ismark>0">
					<view class="cu-tag round bg-red sm">分数：批阅中</view>
					<view class="cu-tag round bg-green sm" style="margin:4px 0 0">准确率：/</view>
					<view class="cu-tag round bg-red sm" style="margin:4px 0 0">错误率：/</view>
				</view>
				<view class="action" style="width: auto;display:flex;flex-direction:column;" v-else>
					<view class="cu-tag round bg-red sm">分数：{{item.myscore}}分</view>
					<view class="cu-tag round bg-green sm" style="margin:4px 0 0">准确率：{{(item.accuracy * 100).toFixed(2)}}%</view>
					<view class="cu-tag round bg-red sm" style="margin:4px 0 0">错误率：{{((1 - item.accuracy) * 100).toFixed(2)}}%</view>
				</view>
			</view>
		</view>
		<view v-if="scrollTop>200" @tap="scrollTopClick" :style='{"boxShadow":"0 8rpx 16rpx rgba(0,0,0,.3)","borderRadius":"50%","textAlign":"center","bottom":"20%","background":"#ff000030","width":"60rpx","lineHeight":"60rpx","position":"fixed","right":"20rpx","height":"60rpx","zIndex":"999"}'>
			<span class="icon iconfont icon-jiantou07" :style='{"color":"#fff"}'></span>
		</view>
	</mescroll-uni>

</template>

<script>
	export default {
		data() {
			return {
				list: [],
				mescroll: null, //mescroll实例对象
				downOption: {
					auto: false //是否在初始化后,自动执行下拉回调callback; 默认true
				},
				upOption: {
					noMoreSize: 5, //如果列表已无数据,可设置列表的总数量要大于半页才显示无更多数据;避免列表数据过少(比如只有一条数据),显示无更多数据会不好看; 默认5
					textNoMore: '~ 没有更多了 ~',
					onScroll: true,
					toTop: false
				},
				hasNext: true,
				searchForm: {},
				CustomBar: '0',
				scrollTop: 0
			}
		},
		onShow() {
			this.hasNext = true
			// 重新加载数据
			if (this.mescroll) this.mescroll.resetUpScroll()
		},
		onLoad(options) {
			this.hasNext = true
			this.userid = options.userid
			// 重新加载数据
			if (this.mescroll) this.mescroll.resetUpScroll()
		},
		methods: {
			scrollChange(e){
				this.scrollTop = e.scrollTop
			},
			scrollTopClick(){
				uni.pageScrollTo({
					scrollTop: 0
				})
			},
			// mescroll组件初始化的回调,可获取到mescroll对象
			mescrollInit(mescroll) {
				this.mescroll = mescroll;
			},
			/*下拉刷新的回调 */
			downCallback(mescroll) {
				this.hasNext = true
				// 重置分页参数页数为1
				mescroll.resetUpScroll()
			},
			/*上拉加载的回调: mescroll携带page的参数, 其中num:当前页 从1开始, size:每页数据条数,默认10 */
			async upCallback(mescroll) {
				let params = {
					page: mescroll.num,
					limit: mescroll.size,
					userid: this.userid
				}
				let res = await this.$api.groupby(params);
				// 如果是第一页数据置空
				if (mescroll.num == 1) this.list = [];
				this.list = this.list.concat(res.data.list);
				if (res.data.list.length == 0) this.hasNext = false;
				mescroll.endSuccess(mescroll.size, this.hasNext);
			},
			// 详情
			onDetailTap(item) {
				this.$utils.jump(`./detail?paperid=${item.paperid}&userid=${item.userid}`)
			},
			// 搜索
			async search(){
				this.mescroll.num = 1
				let params = {
					page: this.mescroll.num,
					limit: this.mescroll.size,
					userid: this.userid
				}
				if(this.searchForm.papername){
					params.papername = '%' + this.searchForm.papername + '%'
				}
				let res = await this.$api.groupby(params);
				// 如果是第一页数据置空
				if (this.mescroll.num == 1) this.list = [];
				this.list = this.list.concat(res.data.list);
				if (res.data.list.length == 0) this.hasNext = false;
				this.mescroll.endSuccess(this.mescroll.size, this.hasNext);
			}
		}
	}
</script>

<style>

</style>
