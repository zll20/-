<template>
<view class="content">
	<view :style='{"minHeight":"100%","width":"100%","padding":"0","position":"relative","background":"#F6F7F9","height":"auto"}'>
		<form :style='{"width":"100%","padding":"24rpx","background":"none","display":"block","height":"auto"}'>
			<view :style='{"padding":"12rpx 0 12rpx 0","margin":"0 0 10rpx 0","borderColor":"#cccccc","alignItems":"center","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
				<input :style='{"border":"2rpx solid #3D3D3D","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"#F7F7F7","fontSize":"28rpx","height":"80rpx"}' v-model="forum.title" placeholder="标题"></input>
			</view>
			<view :style='{"padding":"12rpx 0 12rpx 0","margin":"0 0 10rpx 0","borderColor":"#cccccc","alignItems":"center","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
				<picker :style='{"width":"100%","flex":"1","height":"auto"}' @change="setIsDoneTap" :value="index" :range="options">
					<view class="uni-picker-type" :style='{"border":"2rpx solid #3D3D3D","padding":"0 20rpx","color":"#666","borderRadius":"8rpx","width":"100%","lineHeight":"80rpx","fontSize":"28rpx"}'>{{options[index]}}</view>
				</picker>
			</view>
			<view :style='{"padding":"12rpx 0 12rpx 0","margin":"0 0 10rpx 0","borderColor":"#cccccc","alignItems":"center","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
				<picker :style='{"width":"100%","flex":"1","height":"auto"}' @change="setIsanonTap" :value="index2" :range="options2">
					<view class="uni-picker-type" :style='{"border":"2rpx solid #3D3D3D","padding":"0 20rpx","color":"#666","borderRadius":"8rpx","width":"100%","lineHeight":"80rpx","fontSize":"28rpx"}'>{{options2[index2]}}</view>
				</picker>
			</view>
			<view style="display: flex;align-items: center;padding: 10px 0;">
				<image @click="imgView(item,index)" v-for="(item,index) in picList" :key="index" :src="baseUrl + item" style="width: 50px;margin: 0 5px 0 0;" mode="widthFix"></image>
				<view style="padding: 0 10px;line-height: 20px;background: #ccc;color: #333;" @click="uploadImg">上传图片</view>
			</view>
			<view :style='{"padding":"12rpx 0 12rpx 0","margin":"0 0 10rpx 0","borderColor":"#cccccc","alignItems":"center","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
				<xia-editor :style='{"border":"2rpx solid #3D3D3D","minHeight":"300rpx","padding":"20rpx","margin":"0px","borderRadius":"8rpx","flex":"1","background":"#F7F7F7","height":"auto"}' v-model="forum.content" @editorChange="contentChange" placeholder="内容"></xia-editor>
			</view>
			<view :style='{"width":"100%","padding":"10rpx 0","flexWrap":"wrap","justifyContent":"center","display":"flex","height":"auto"}'>
				<button :style='{"border":"0","padding":"0px","margin":"0 0 20rpx 0","color":"#fff","borderRadius":"8rpx","background":"#FFD128","width":"100%","lineHeight":"80rpx","fontSize":"32rpx","height":"80rpx"}' @tap="onSubmitTap" class="bg-red margin-tb-sm">确认提交</button>
			</view>
		</form>
	</view>
</view>
</template>

<script>
	import xiaEditor from '@/components/xia-editor/xia-editor';
	export default {
		data() {
			return {
				forum: {
					content: '',
					id: '',
					title: '',
					isdone: '开放',
					parentid: 0,
					isanon: 0,
					cover: ''
				},
				picList: [],
				index: 0,
				options: ['开放', '关闭'],
				index2: 0,
				options2: ['实名', '匿名'],
				username: '',
				user: {},
			}
		},
		components: {
			xiaEditor
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			}
		},
		async onLoad(options) {
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			if(table == `xuesheng`){
				this.username = this.user.xuehao
			}
			if (options.id) {
				this.id = options.id;
				let res = await this.$api.info('forum',this.id);
				if(res.data.cover) {
					this.picList = res.data.cover.split(',')
				}
				this.forum = res.data
			}
			this.styleChange()
		},
		methods: {
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
			async uploadImg(){
				let _this = this;
				await this.$api.upload(function(res) {
					_this.picList.push('upload/' + res.file)
					_this.$forceUpdate();
				});
			},
			contentChange(e) {
				this.forum.content = e
			},
			styleChange() {
				this.$nextTick(()=>{
					// document.querySelectorAll('.my-publish-pv .uni-input-input').forEach(el=>{
					//   el.style.backgroundColor = this.myPublishForm.content.input.backgroundColor
					// })
				})
			},
			async onSubmitTap() {
				if(this.forum.title==''){
					this.$utils.msg('标题不能为空');
					return false
				}
				if(this.forum.content==''){
					this.$utils.msg('内容不能为空');
					return false
				}
				if(this.picList.length){
					this.forum.cover = this.picList.join(',')
				}else {
					this.$utils.msg('封面图不能为空');
					return false
				}
				this.forum.username = this.username;
				if (this.id) {
					await this.$api.update('forum',this.forum);
				} else {
					this.forum.toptime = this.$utils.getCurDateTime()
					await this.$api.save('forum',this.forum);
				}
				this.$utils.msgBack('操作成功');
			},
			setIsDoneTap(e) {
				// this.forum.isdone = e;
				this.index = e.target.value
				this.forum.isdone = this.options[this.index]
				this.$forceUpdate();
			},
			setIsanonTap(e){
				this.index2 = e.target.value
				this.forum.isanon = this.index2
				this.$forceUpdate();
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
