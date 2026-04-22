<template>
	<div class="addEdit-block">
		<el-form
			class="add-update-preview"
			ref="ruleForm"
			:model="ruleForm"
			:rules="rules"
			label-width="140px"
		>
			<template>
				<el-form-item class="input" v-if="type!='info'" label="名称" prop="mingcheng">
					<el-input v-model="ruleForm.mingcheng" placeholder="文刊名称" clearable :readonly="ro.mingcheng"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="名称" prop="mingcheng">
					<el-input v-model="ruleForm.mingcheng" placeholder="文刊名称" readonly></el-input>
				</el-form-item>
				<el-form-item class="select" v-if="type!='info'" label="级别" prop="jibie">
					<el-select :disabled="ro.jibie" v-model="ruleForm.jibie" placeholder="请选择级别" filterable>
						<el-option
							v-for="(item,index) in jibieOptions"
							v-bind:key="index"
							:label="item"
							:value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item v-else class="input" label="级别" prop="jibie">
					<el-input v-model="ruleForm.jibie" placeholder="级别" readonly></el-input>
				</el-form-item>
				<el-form-item class="editor" v-if="type!='info' && !ro.wenkanneirong" label="文刊内容" prop="wenkanneirong">
					<editor
						:style='{"minHeight":"400px"}'
						v-model="ruleForm.wenkanneirong"
						class="editor"
						@blur="editorBlur"
						action="file/upload">
					</editor>
				</el-form-item>
				<el-form-item v-else-if="ruleForm.wenkanneirong" class="editor" label="文刊内容" prop="wenkanneirong">
					<div v-html="ruleForm.wenkanneirong" class="content-display"></div>
				</el-form-item>
			</template>
			<el-form-item class="btn">
				<el-button class="btn3" v-if="type!='info'" type="success" @click="onSubmit">
					<span class="icon iconfont icon-queren15"></span>
					提交
				</el-button>
				<el-button class="btn4" v-if="type!='info'" type="success" @click="back()">
					<span class="icon iconfont icon-guanbi2"></span>
					取消
				</el-button>
				<el-button class="btn5" v-if="type=='info'" type="success" @click="back()">
					<span class="icon iconfont icon-fanhui13"></span>
					返回
				</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
	import editor from "@/components/common/Editor";
	export default {
		data() {
			return {
				id: '',
				type: '',
				ro:{
					mingcheng : false,
					jibie : false,
					wenkanneirong : false,
				},
				ruleForm: {
					id: '',
					mingcheng: '',
					jibie: '',
					wenkanneirong: '',
				},
				jibieOptions: [],
				rules: {
					mingcheng: [
						{ required: true, message: '名称不能为空', trigger: 'blur' },
					],
					jibie: [
						{ required: true, message: '级别不能为空', trigger: 'blur' },
					],
					wenkanneirong: [
						{ required: true, message: '文刊内容不能为空', trigger: 'blur' },
					],
				},
			};
		},
		props: ["parent"],
		computed: {
			sessionTable() {
				return this.$storage.get('sessionTable')
			},
		},
		components: {
			editor,
		},
		created() {
		},
		methods: {
			imgPreView(url){
				this.$parent.imgPreView(url)
			},
			download(file){
				window.open(`${file}`)
			},
			init(id, type) {
				if (id) {
					this.id = id;
					this.type = type;
				}
				if(this.type=='info'||this.type=='else'||this.type=='msg'){
					this.info(id);
				}else if(this.type=='cross'){
					var obj = this.$storage.getObj('crossObj');
					for (var o in obj){
						if(o=='mingcheng'){
							this.ruleForm.mingcheng = obj[o];
							this.ro.mingcheng = true;
							continue;
						}
						if(o=='jibie'){
							this.ruleForm.jibie = obj[o];
							this.ro.jibie = true;
							continue;
						}
						if(o=='wenkanneirong'){
							this.ruleForm.wenkanneirong = obj[o];
							this.ro.wenkanneirong = true;
							continue;
						}
					}
				}

				this.$http({
					url: `${this.sessionTable}/session`,
					method: "get"
				}).then(({ data }) => {
					if (data && data.code === 0) {
						var json = data.data;
					} else {
						this.$message.error(data.msg);
					}
				});
				this.jibieOptions = "小学,初中,高中,四级,六级,考研".split(',')
			},

			async info(id) {
				await this.$http({
					url: `wenkan/info/${id}`,
					method: "get"
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.ruleForm = data.data;
						console.log('文刊详情数据:', this.ruleForm);
					} else {
						this.$message.error(data.msg);
					}
				});
			},

			editorBlur(e) {
				this.ruleForm.wenkanneirong = e
			},

			async onSubmit() {
					if(this.ruleForm.wenkanneirong!=null) {
						this.ruleForm.wenkanneirong = this.ruleForm.wenkanneirong.replace(new RegExp(this.$base.url,"g"),"");
					}
					var objcross = this.$storage.getObj('crossObj');
					if(!this.ruleForm.id) {
						delete this.ruleForm.userid
					}
					await this.$refs["ruleForm"].validate(async valid => {
						if (valid) {
							if(this.type=='cross'){
								var statusColumnName = this.$storage.get('statusColumnName');
								var statusColumnValue = this.$storage.get('statusColumnValue');
								if(statusColumnName!='') {
									var obj = this.$storage.getObj('crossObj');
									if(statusColumnName && !statusColumnName.startsWith("[")) {
										for (var o in obj){
											if(o==statusColumnName){
												obj[o] = statusColumnValue;
											}
										}
										var table = this.$storage.get('crossTable');
										await this.$http({
											url: `${table}/update`,
											method: "post",
											data: obj
										}).then(({ data }) => {});
									}
								}
							}
							await this.$http({
								url: `wenkan/${!this.ruleForm.id ? "save" : "update"}`,
								method: "post",
								data: this.ruleForm
							}).then(async ({ data }) => {
								if (data && data.code === 0) {
									this.$message({
										message: "操作成功",
										type: "success",
										duration: 1500,
										onClose: () => {
											this.parent.showFlag = true;
											this.parent.addOrUpdateFlag = false;
											this.parent.wenkanCrossAddOrUpdateFlag = false;
											this.parent.search();
										}
									});
								} else {
									this.$message.error(data.msg);
								}
							});
						}
					});
			},
			getUUID () {
				return new Date().getTime();
			},
			back() {
				this.parent.showFlag = true;
				this.parent.addOrUpdateFlag = false;
				this.parent.wenkanCrossAddOrUpdateFlag = false;
			},
		}
	};
</script>
<style lang="scss" scoped>
	.addEdit-block {
		padding: 15px 30px 30px 30px;
	}
	.add-update-preview {
		border-radius: 8px;
		padding: 30px;
		margin: 0 0 0px;
		background: #fff;
		display: flex;
		width: 100%;
		flex-wrap: wrap;
	}
	.el-date-editor.el-input {
		width: auto;
	}
	.add-update-preview ::v-deep .el-form-item {
		margin: 10px 0px 10px 0 ;
		display: flex;
		width: calc(50% - 0px);
	}
	.add-update-preview .el-form-item ::v-deep .el-form-item__label {
		padding: 0 10px;
		color: #333333;
		white-space: nowrap;
		background: none;
		font-weight: 600;
		width: 140px;
		font-size: 16px;
		line-height: 64px;
		text-align: left;
		height: 64px;
	}
	.add-update-preview .el-form-item ::v-deep .el-form-item__content {
		margin-left: auto !important;
		margin: auto !important;
		display: flex;
		width: 100%;
		justify-content: flex-start;
		align-items: flex-start;
		flex-wrap: wrap;
	}
	.add-update-preview .el-input {
		flex: 1;
	}
	.add-update-preview .el-input ::v-deep .el-input__inner {
		border: 1px solid #DADFE6;
		border-radius: 4px;
		padding: 0 12px;
		box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
		outline: none;
		color: #333;
		background: #FFFFFF;
		flex: 1;
		width: 100%;
		font-size: 16px;
		height: 64px;
	}
	.add-update-preview .el-input ::v-deep .el-input__inner[readonly="readonly"] {
		border: 1px solid #DADFE6;
		border-radius: 4px;
		padding: 0 12px;
		box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
		outline: none;
		color: #333;
		background: #FFFFFF;
		flex: 1;
		width: 100%;
		font-size: 16px;
		height: 64px;
	}
	.add-update-preview .el-select {
		width: auto;
		min-width: 100%;
	}
	.add-update-preview .el-select ::v-deep .el-input__inner {
		border: 1px solid #DADFE6;
		border-radius: 4px;
		padding: 0 12px;
		box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
		outline: none;
		color: #333;
		background: #FFFFFF;
		flex: 1;
		width: 100%;
		font-size: 16px;
		height: 64px;
	}
	.add-update-preview ::v-deep .el-upload--picture-card {
		background: transparent;
		border: 0;
		border-radius: 0;
		width: auto;
		height: auto;
		line-height: initial;
		vertical-align: middle;
	}
	.add-update-preview ::v-deep .upload .upload-img {
		border: 1px solid #DADFE6;
		cursor: pointer;
		border-radius: 4px;
		box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
		color: #999;
		background: #fff;
		font-weight: 600;
		width: 80px;
		font-size: 30px;
		line-height: 80px;
		text-align: center;
		height: 80px;
	}
	.add-update-preview ::v-deep .el-upload-list .el-upload-list__item {
		border: 1px solid #DADFE6;
		cursor: pointer;
		border-radius: 4px;
		box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
		color: #999;
		background: #fff;
		font-weight: 600;
		width: 80px;
		font-size: 30px;
		line-height: 80px;
		text-align: center;
		height: 80px;
	}
	.add-update-preview ::v-deep .el-upload .el-icon-plus {
		border: 1px solid #DADFE6;
		cursor: pointer;
		border-radius: 4px;
		box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
		color: #999;
		background: #fff;
		font-weight: 600;
		width: 80px;
		font-size: 30px;
		line-height: 80px;
		text-align: center;
		height: 80px;
	}
	.add-update-preview ::v-deep .el-upload__tip {
		padding: 0 10px;
		color: #666;
		font-size: 15px;
	}
	.add-update-preview ::v-deep .el-form-item.btn {
		padding: 0 30px;
		margin: 30px  auto 0 auto;
		background: none;
		display: flex;
		width: auto;
		justify-content: center;
		flex-wrap: wrap;
		.btn1 {
			border: 0px solid #206cb4;
			cursor: pointer;
			padding: 0 24px;
			margin: 4px;
			color: #fff;
			font-weight: bold;
			font-size: 16px;
			border-radius: 24px  24px  24px  24px;
			outline: none;
			background: #FA6B04;
			width: auto;
			min-width: 176px;
			height: 47px;
			.iconfont {
				margin: 0 2px;
				color: #fff;
				display: none;
				font-size: 16px;
				height: 40px;
			}
		}
		.btn1:hover {
			opacity: 0.8;
		}
		.btn2 {
			border: 0px solid #206cb4;
			cursor: pointer;
			padding: 0 24px;
			margin: 4px;
			color: #fff;
			font-weight: bold;
			font-size: 16px;
			border-radius: 24px  24px  24px  24px;
			outline: none;
			background: #FA6B04;
			width: auto;
			min-width: 176px;
			height: 47px;
			.iconfont {
				margin: 0 2px;
				color: #fff;
				display: none;
				font-size: 16px;
				height: 40px;
			}
		}
		.btn2:hover {
			opacity: 0.8;
		}
		.btn3 {
			border: 0px solid #206cb4;
			cursor: pointer;
			padding: 0 24px;
			margin: 4px;
			color: #fff;
			font-weight: bold;
			font-size: 16px;
			border-radius: 24px  24px  24px  24px;
			outline: none;
			background: #FA6B04;
			width: auto;
			min-width: 176px;
			height: 47px;
			.iconfont {
				margin: 0 2px;
				color: #fff;
				display: none;
				font-size: 16px;
				height: 40px;
			}
		}
		.btn3:hover {
			opacity: 0.8;
		}
		.btn4 {
			border: 0px solid #206cb4;
			cursor: pointer;
			padding: 0 24px;
			margin: 4px;
			color: #000000;
			font-weight: bold;
			font-size: 16px;
			border-radius: 24px  24px  24px  24px;
			outline: none;
			background: #FAFAFA;
			width: auto;
			min-width: 176px;
			height: 47px;
			.iconfont {
				margin: 0 2px;
				color: #fff;
				display: none;
				font-size: 16px;
				height: 40px;
			}
		}
		.btn4:hover {
			opacity: 0.8;
		}
		.btn5 {
			border: 0px solid #206cb4;
			cursor: pointer;
			padding: 0 24px;
			margin: 4px;
			color: #000000;
			font-weight: bold;
			font-size: 16px;
			border-radius: 24px  24px  24px  24px;
			outline: none;
			background: #FAFAFA;
			width: auto;
			min-width: 176px;
			height: 47px;
			.iconfont {
				margin: 0 2px;
				color: #fff;
				display: none;
				font-size: 16px;
				height: 40px;
			}
		}
		.btn5:hover {
			opacity: 0.8;
		}
	}
	.content-display {
		padding: 10px;
		border: 1px solid #DADFE6;
		border-radius: 4px;
		background: #FFFFFF;
		width: 100%;
	}
</style>
