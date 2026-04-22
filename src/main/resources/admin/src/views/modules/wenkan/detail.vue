<template>
	<div class="detail">
		<el-form class="detail-form-content" ref="ruleForm" :model="ruleForm" label-width="100px">
			<el-form-item label="名称" prop="mingcheng">
				<el-input v-model="ruleForm.mingcheng" readonly></el-input>
			</el-form-item>
			<el-form-item label="级别" prop="jibie">
				<el-input v-model="ruleForm.jibie" readonly></el-input>
			</el-form-item>
			<el-form-item label="文刊内容" prop="wenkanneirong">
				<div class="wenkan-content" v-html="ruleForm.wenkanneirong"></div>
			</el-form-item>
			<el-form-item>
				<el-button class="back-btn" type="default" @click="back()">返回</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
export default {
	data() {
		return {
			id: '',
			ruleForm: {}
		}
	},
	methods: {
		init(id) {
			this.id = id
			this.getInfo()
		},
		getInfo() {
			this.$http({
				url: `wenkan/info/${this.id}`,
				method: 'get'
			}).then(({data}) => {
				if (data && data.code === 0) {
					this.ruleForm = data.data
				}
			})
		},
		back() {
			this.$parent.showFlag = true
			this.$parent.detailVisible = false
		}
	}
}
</script>

<style lang="scss" scoped>
.detail {
	padding: 20px;
	background: #fff;
	border-radius: 8px;

	.wenkan-content {
		font-size: 16px;
		line-height: 1.8;
		color: #333;
		min-height: 200px;
		padding: 10px;
		border: 1px solid #eee;
		border-radius: 4px;
	}

	.back-btn {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 24px;
		outline: none;
		color: #666;
		background: #f5f5f5;
		width: auto;
		font-size: 16px;
		height: 40px;
	}

	.back-btn:hover {
		opacity: 0.8;
	}
}
</style>
