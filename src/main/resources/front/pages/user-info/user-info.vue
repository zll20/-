<template>
<view class="content">
	<view class="box" :style='{"minHeight":"100%","width":"100%","padding":"0","position":"relative","background":"#F6F7F9","height":"auto"}'>
		<view :style='{"width":"100%","padding":"20rpx","background":"none","display":"block","height":"auto"}'>
			<view :style='{"padding":"12rpx 0 12rpx 0","margin":"0 0 10rpx 0","borderColor":"#cccccc","alignItems":"center","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" class="">
				<view class="title" :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#333","textAlign":"left"}'>学号</view>
				<input :style='{"border":"2rpx solid #3D3D3D","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"10rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.xuehao"  v-model="ruleForm.xuehao" placeholder="学号"></input>
			</view>
			<view :style='{"padding":"12rpx 0 12rpx 0","margin":"0 0 10rpx 0","borderColor":"#cccccc","alignItems":"center","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" class="">
				<view class="title" :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#333","textAlign":"left"}'>姓名</view>
				<input :style='{"border":"2rpx solid #3D3D3D","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"10rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.xingming"  v-model="ruleForm.xingming" placeholder="姓名"></input>
			</view>
			<view v-if="tableName=='xuesheng'" :style='{"padding":"12rpx 0 12rpx 0","margin":"0 0 10rpx 0","borderColor":"#cccccc","alignItems":"center","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class=" select">
				<view :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#333","textAlign":"left"}' class="title">性别</view>
				<picker :style='{"border":"2rpx solid #3D3D3D","width":"100%","padding":"0 24rpx 0 24rpx","borderRadius":"8rpx","flex":"1","height":"auto"}' :disabled="ro.xingbie" @change="xueshengxingbieChange" :value="xueshengxingbieIndex" :range="xueshengxingbieOptions">
					<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"28rpx","color":"#333"}' class="uni-input picker-select-input">{{ruleForm.xingbie?ruleForm.xingbie:"请选择性别"}}</view>
				</picker>
			</view>
			<view :style='{"padding":"12rpx 0 12rpx 0","margin":"0 0 10rpx 0","borderColor":"#cccccc","alignItems":"center","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" @tap="xueshengtouxiangTap" class="">
				<view class="title" :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#333","textAlign":"left"}'>头像</view>
				<image :style='{"width":"80rpx","borderRadius":"100%","objectFit":"cover","display":"block","height":"80rpx"}' v-if="ruleForm.touxiang" style="margin: 0;" class="avator" :src="baseUrl+ruleForm.touxiang" mode=""></image>
				<image :style='{"width":"80rpx","borderRadius":"100%","objectFit":"cover","display":"block","height":"80rpx"}' v-else class="avator" style="margin: 0;" src="../../static/gen/upload.png" mode=""></image>
			</view>
			<view :style='{"padding":"12rpx 0 12rpx 0","margin":"0 0 10rpx 0","borderColor":"#cccccc","alignItems":"center","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" class="">
				<view class="title" :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#333","textAlign":"left"}'>邮箱</view>
				<input :style='{"border":"2rpx solid #3D3D3D","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"10rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.youxiang"  v-model="ruleForm.youxiang" placeholder="邮箱"></input>
			</view>
			<view :style='{"padding":"12rpx 0 12rpx 0","margin":"0 0 10rpx 0","borderColor":"#cccccc","alignItems":"center","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" class="">
				<view class="title" :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#333","textAlign":"left"}'>手机号</view>
				<input :style='{"border":"2rpx solid #3D3D3D","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"10rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.mobile"  v-model="ruleForm.mobile" placeholder="手机号"></input>
			</view>
			<view :style='{"padding":"12rpx 0 12rpx 0","margin":"0 0 10rpx 0","borderColor":"#cccccc","alignItems":"center","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" class="">
				<view class="title" :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#333","textAlign":"left"}'>密保问题</view>
				<input :style='{"border":"2rpx solid #3D3D3D","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"10rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.pquestion"  v-model="ruleForm.pquestion" placeholder="密保问题"></input>
			</view>
			<view :style='{"padding":"12rpx 0 12rpx 0","margin":"0 0 10rpx 0","borderColor":"#cccccc","alignItems":"center","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='xuesheng'" class="">
				<view class="title" :style='{"width":"100%","padding":"0 20rpx 0 0","lineHeight":"80rpx","fontSize":"28rpx","color":"#333","textAlign":"left"}'>密保答案</view>
				<input :style='{"border":"2rpx solid #3D3D3D","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"10rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.panswer"  v-model="ruleForm.panswer" placeholder="密保答案"></input>
			</view>
			<view :style='{"width":"100%","padding":"10rpx 0 10rpx 0","flexWrap":"wrap","justifyContent":"center","display":"flex","height":"auto"}' class="btn">
				<button @tap="update()" class="cu-btn lg" :style='{"border":"0","padding":"0px","margin":"0 0 20rpx 0","color":"#fff","borderRadius":"8rpx","background":"#FFD128","width":"100%","lineHeight":"80rpx","fontSize":"30rpx","height":"80rpx"}'>保存</button>
				<button @tap="logout()" class="cu-btn lg" :style='{"border":"0","padding":"0px","margin":"0 0 20rpx 0","color":"#fff","borderRadius":"8rpx","background":"#9C9C9C","width":"100%","lineHeight":"80rpx","fontSize":"30rpx","height":"80rpx"}'>退出登录</button>
			</view>
		</view>
	</view>
</view>
</template>

<script>

	import xiaEditor from '@/components/xia-editor/xia-editor';
	import multipleSelect from "@/components/momo-multipleSelect/momo-multipleSelect";
	export default {
		data() {
			return {
				ruleForm: {
				},
				tableName:"",
				ro: {},
				xueshengxingbieOptions: [],
				xueshengxingbieIndex: 0,
			}
		},
		components: {
			multipleSelect,
			xiaEditor
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			}
		},
		async onLoad() {
			let table = uni.getStorageSync("nowTable");
			let that = this
			let res = {}
			this.tableName = table;
			this.getSession()
			if(this.tableName=='xuesheng'){
				// 自定义下拉框值
				this.xueshengxingbieOptions = "男,女".split(',');
				this.xueshengxingbieOptions.forEach((item, index) => {
					if(item==this.ruleForm.xingbie) {
						this.xueshengxingbieIndex = index;
					}
				});
				this.ro = {
					xuehao: true,
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
			this.$forceUpdate()
		},
		methods: {
			async getSession(){
				let res = await this.$api.session(this.tableName)
				this.ruleForm = res.data;
				uni.setStorageSync('userSession',JSON.stringify(res.data))
			},
			// 下拉变化
			xueshengxingbieChange(e) {
				this.xueshengxingbieIndex = e.target.value
				this.ruleForm.xingbie = this.xueshengxingbieOptions[this.xueshengxingbieIndex]
			},
			xueshengtouxiangTap() {
				if(this.ro.touxiang){
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

			styleChange() {
				this.$nextTick(()=>{
					// document.querySelectorAll('. .uni-input-input').forEach(el=>{
					//   el.style.backgroundColor = this.userInfoForm.list.input.backgroundColor
					// })
				})
			},
			// 获取uuid
			getUUID () {
				return new Date().getTime();
			},
			logout() {
				uni.clearStorageSync()
				this.$utils.jump('../login/login');
			},
			// 注册
			async update() {
				if(`xuesheng` == this.tableName && this.ruleForm.youxiang&&(!this.$validate.isEmail(this.ruleForm.youxiang))){
					this.$utils.msg(`邮箱应输入邮件格式`);
					return
				}
				if(`xuesheng` == this.tableName && this.ruleForm.mobile&&(!this.$validate.isMobile(this.ruleForm.mobile))){
					this.$utils.msg(`手机号应输入手机格式`);
					return
				}
				let table = uni.getStorageSync("nowTable");
				await this.$api.update(table, this.ruleForm);
				this.$utils.msgBack('修改成功');;
				this.getSession()
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
