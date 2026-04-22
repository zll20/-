<template>
<view class="content">
	<view :style='{"minHeight":"100%","width":"100%","padding":"0","position":"relative","background":"#F6F7F9","height":"auto"}'>
		<form :style='{"width":"100%","padding":"24rpx","background":"none","display":"block","height":"auto"}'>
			<view :style='{"padding":"12rpx 0 12rpx 0","margin":"0 0 10rpx 0","borderColor":"#cccccc","alignItems":"center","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
				<xia-editor :style='{"border":"2rpx solid #3D3D3D","minHeight":"300rpx","padding":"20rpx","margin":"0px","borderRadius":"8rpx","flex":"1","background":"#F7F7F7","height":"auto"}' v-model="content" @editorChange="contentChange" placeholder="回复"></xia-editor>
			</view>
			<view :style='{"width":"100%","padding":"10rpx 0","flexWrap":"wrap","justifyContent":"center","display":"flex","height":"auto"}'>
				<button :style='{"border":"0","padding":"0px","margin":"0 0 20rpx 0","color":"#fff","borderRadius":"8rpx","background":"#FFD128","width":"100%","lineHeight":"80rpx","fontSize":"32rpx","height":"80rpx"}' @tap="onReplyTap" class="bg-red margin-tb-sm">提交回复</button>
			</view>
		</form>
	</view>
</view>
</template>

<script>
	import xiaEditor from '@/components/xia-editor/xia-editor'
	export default {
		data() {
			return {
				pid: '',
				content: '',
				username: '',
				avatarurl: '',
				user: {},
			}
		},
		components: {
			xiaEditor
		},
		async onLoad(options) {
			this.pid = options.pid;
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			if(table == `xuesheng`){
				this.username = this.user.xuehao
			}
		},
		methods: {
			contentChange(e) {
				this.content = e
			},
			async onReplyTap() {
				if(this.content==''){
					this.$utils.msg('内容不能为空');
					return false
				}
				this.avatarurl = uni.getStorageSync('frontHeadportrait')?uni.getStorageSync('frontHeadportrait'):'';
				
				await this.$api.save('forum',{
					parentid: this.pid,
					content: this.content,
					avatarurl: this.avatarurl,
					username: this.username,
					toptime: this.$utils.getCurDateTime()
				});
				this.$utils.msgBack('回复成功');
				uni.setStorageSync('forumComType', true);
			}
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
	}
</style>
