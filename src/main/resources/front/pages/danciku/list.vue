  <template>
<mescroll-uni @init="mescrollInit" :up="upOption" :down="downOption" @down="downCallback" @up="upCallback" @scroll="scrollChange">
	<view class="content">
		<view class="container" :style='{"minHeight":"100%","width":"100%","padding":"20rpx","position":"relative","background":"#F6F7F9","height":"auto"}'>
			<!-- 搜索栏 -->
			<view class="search-box" :style='{"padding":"20rpx","background":"#fff","borderRadius":"20rpx","margin":"0 0 20rpx 0","display":"flex","alignItems":"center"}'>
				<input
					:style='{"flex":1,"padding":"16rpx 24rpx","background":"#f5f5f5","borderRadius":"30rpx","fontSize":"28rpx"}'
					placeholder-style="color: #999"
					v-model="searchForm.danci"
					type="text"
					placeholder="搜索单词"
					@confirm="search"
				></input>
				<button
					:style='{"padding":"16rpx 32rpx","margin":"0 0 0 20rpx","background":"#FFD128","borderRadius":"30rpx","color":"#fff","fontSize":"28rpx"}'
					@click="search"
				>搜索</button>
			</view>

			<!-- 切换单词库按钮 -->
			<button
				:style='{"width":"100%","padding":"32rpx","background":"#FFD128","borderRadius":"20rpx","color":"#fff","fontSize":"32rpx","fontWeight":"bold","margin":"0 0 30rpx 0"}'
				@click="showLevelSelector = true"
			>
				<text class="icon iconfont icon-shaixuan" :style='{"margin":"0 10rpx 0 0","fontSize":"32rpx"}'></text>
				切换单词库
			</button>

			<!-- 级别选择弹窗 -->
			<view
				:style='{"top":"0","left":"0","background":"rgba(0, 0, 0, .5)","width":"100%","position":"fixed","height":"100%","zIndex":"999"}'
				v-if="showLevelSelector"
				@click="showLevelSelector = false"
			></view>
			<view
				:style='{"padding":"40rpx 30rpx","background":"#fff","borderRadius":"30rpx 30rpx 0 0","width":"100%","position":"fixed","bottom":"0","zIndex":"1000","maxHeight":"80vh","overflowY":"auto"}'
				:class="showLevelSelector ? 'level-selector-active' : 'level-selector'"
			>
				<view :style='{"fontSize":"36rpx","fontWeight":"bold","margin":"0 0 30rpx 0","textAlign":"center"}'>选择单词库</view>
				<view :style='{"display":"flex","flexWrap":"wrap","justifyContent":"space-between"}'>
					<view
						v-for="(item,index) in jibieOptions"
						:key="index"
						:style='{"width":"48%","padding":"32rpx 20rpx","margin":"0 0 20rpx 0","background":"#FFD128","borderRadius":"16rpx","color":"#fff","fontSize":"30rpx","textAlign":"center","fontWeight":"bold"}'
						@click="enterLevel(item)"
					>
						{{item}}
					</view>
				</view>
			</view>

			<!-- 搜索结果列表（可选显示） -->
			<view v-if="searchResultList.length > 0" :style='{"background":"#fff","borderRadius":"20rpx","padding":"20rpx"}'>
				<view :style='{"fontSize":"28rpx","color":"#666","margin":"0 0 20rpx 0"}'>搜索结果：</view>
				<view
					v-for="(item,index) in searchResultList"
					:key="index"
					:style='{"padding":"20rpx","margin":"0 0 10rpx 0","background":"#f5f5f5","borderRadius":"12rpx"}'
					@click="onDetailTap(item)"
				>
					<view :style='{"fontSize":"30rpx","fontWeight":"bold","color":"#333"}'>{{item.danci}}</view>
					<view :style='{"fontSize":"24rpx","color":"#666","margin":"8rpx 0 0 0"}'>{{item.shiyi}}</view>
				</view>
			</view>

			<!-- 提示信息 -->
			<view v-else :style='{"padding":"100rpx 40rpx","textAlign":"center","color":"#999","fontSize":"28rpx"}'>
				<view :style='{"fontSize":"100rpx","margin":"0 0 20rpx 0"}'>📚</view>
				<view>点击上方"切换单词库"按钮</view>
				<view :style='{"margin":"10rpx 0 0 0"}'>选择要学习的单词级别</view>
			</view>
		</view>
	</view>
</mescroll-uni>
</template>

<script>
	export default {
		data() {
			return {
				jibieOptions:['小学', '初中', '高中', '四级', '六级', '考研'],
				searchForm:{
					danci: '',
				},
				searchResultList: [],
				showLevelSelector: false,
				mescroll: null,
				downOption: {
					auto: false
				},
				upOption: {
					noMoreSize: 5,
					textNoMore: '~ 没有更多了 ~',
				},
			};
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			},
		},
		async onShow() {
			this.searchResultList = [];
		},
		methods: {
			// 进入某个级别的单词
			async enterLevel(jibie) {
				this.showLevelSelector = false;

				try {
					// 获取该级别的第一个单词
					let params = {
						page: 1,
						limit: 1,
						sort: 'id',
						order: 'asc',
						jibie: jibie
					};

					let res = await this.$api.list('danciku', params);

					if(res.data.list && res.data.list.length > 0) {
						// 跳转到该单词的详情页，并标记从列表页进入
						let firstWord = res.data.list[0];
						this.$utils.jump(`./detail?id=${firstWord.id}&fromList=1&jibie=${jibie}`);
					} else {
						this.$utils.msg(`暂无${jibie}级别的单词`);
					}
				} catch(err) {
					console.error('获取单词失败：', err);
					this.$utils.msg('加载失败，请重试');
				}
			},

			// 搜索单词
			async search(){
				if(!this.searchForm.danci) {
					this.$utils.msg('请输入搜索内容');
					return;
				}

				try {
					let params = {
						page: 1,
						limit: 20,
						danci: '%' + this.searchForm.danci + '%'
					};

					let res = await this.$api.list('danciku', params);
					this.searchResultList = res.data.list;

					if(this.searchResultList.length === 0) {
						this.$utils.msg('未找到相关单词');
					}
				} catch(err) {
					console.error('搜索失败：', err);
					this.$utils.msg('搜索失败，请重试');
				}
			},

			// 点击搜索结果进入详情
			onDetailTap(item) {
				this.$utils.jump(`./detail?id=${item.id}&fromList=1&jibie=${item.jibie}`);
			},

			// mescroll组件初始化的回调
			mescrollInit(mescroll) {
				this.mescroll = mescroll;
			},

			/*下拉刷新的回调 */
			downCallback(mescroll) {
				mescroll.endSuccess();
			},

			/*上拉加载的回调 */
			async upCallback(mescroll) {
				mescroll.endSuccess();
			},

			scrollChange(e){
				this.scrollTop = e.scrollTop;
			},
		}
	};
</script>

<style lang="scss" scoped>
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
	}

	.level-selector {
		transform: translateY(100%);
		transition: transform 0.3s ease;
	}
	
	.level-selector-active {
		transform: translateY(0);
		transition: transform 0.3s ease;
	}
</style>
