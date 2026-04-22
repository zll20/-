<template>
	<div class="addEdit-block">
		<el-form
			class="add-update-preview"
			ref="ruleForm"
			:model="ruleForm"
			:rules="rules"
			label-width="140px"
		>
			<template >
				<el-form-item class="input" v-if="type!='info'"  label="单词" prop="danci" >
					<el-input v-model="ruleForm.danci" placeholder="单词" clearable  :readonly="ro.danci"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="单词" prop="danci" >
					<el-input v-model="ruleForm.danci" placeholder="单词" readonly></el-input>
				</el-form-item>
				<el-form-item class="select" v-if="type!='info'"  label="级别" prop="jibie" >
					<el-select :disabled="ro.jibie" v-model="ruleForm.jibie" placeholder="请选择级别"  filterable>
						<el-option
							v-for="(item,index) in jibieOptions"
							v-bind:key="index"
							:label="item"
							:value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item v-else class="input" label="级别" prop="jibie" >
					<el-input v-model="ruleForm.jibie"
						placeholder="级别" readonly></el-input>
				</el-form-item>
				<el-form-item class="select" v-if="type!='info'"  label="单词分类" prop="dancifenlei" >
					<el-select :disabled="ro.dancifenlei" v-model="ruleForm.dancifenlei" placeholder="请选择单词分类"  filterable>
						<el-option
							v-for="(item,index) in dancifenleiOptions"
							v-bind:key="index"
							:label="item"
							:value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item v-else class="input" label="单词分类" prop="dancifenlei" >
					<el-input v-model="ruleForm.dancifenlei"
						placeholder="单词分类" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="释义" prop="shiyi" >
					<el-input v-model="ruleForm.shiyi" placeholder="释义" clearable  :readonly="ro.shiyi"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="释义" prop="shiyi" >
					<el-input v-model="ruleForm.shiyi" placeholder="释义" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="例句" prop="liju" >
					<el-input v-model="ruleForm.liju" placeholder="例句" clearable  :readonly="ro.liju"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="例句" prop="liju" >
					<el-input v-model="ruleForm.liju" placeholder="例句" readonly></el-input>
				</el-form-item>
				<el-form-item class="upload" v-if="type!='info' && !ro.fengmiantu" label="封面图" prop="fengmiantu" >
					<file-upload
						tip="点击上传封面图"
						action="file/upload"
						:limit="3"
						:disabled="ro.fengmiantu"
						:multiple="true"
						:fileUrls="ruleForm.fengmiantu?ruleForm.fengmiantu:''"
						@change="fengmiantuUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item class="upload" v-else-if="ruleForm.fengmiantu" label="封面图" prop="fengmiantu" >
					<img v-if="ruleForm.fengmiantu.substring(0,4)=='http'&&ruleForm.fengmiantu.split(',w').length>1" class="upload-img" style="margin-right:20px;" :key="'http-img-all'" :src="ruleForm.fengmiantu" width="100" height="100" @click="imgPreView(ruleForm.fengmiantu)">
					<img v-else-if="ruleForm.fengmiantu.substring(0,4)=='http'" class="upload-img" style="margin-right:20px;" :key="'http-img-first'" :src="ruleForm.fengmiantu.split(',')[0]" width="100" height="100" @click="imgPreView(ruleForm.fengmiantu.split(',')[0])">
					<img v-else class="upload-img" style="margin-right:20px;" v-bind:key="index" v-for="(item,index) in ruleForm.fengmiantu.split(',')" :src="$base.url+item" width="100" height="100" @click="imgPreView($base.url+item)">
				</el-form-item>
				<el-form-item class="upload" v-if="type!='info' && !ro.duyin" label="读音" prop="duyin" >
					<file-upload
						tip="点击上传音频文件（mp3/wav格式）"
						action="file/upload"
						:limit="1"
						:disabled="ro.duyin"
						:multiple="false"
						:fileUrls="ruleForm.duyin?ruleForm.duyin:''"
						@change="duyinUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item class="upload" v-else-if="ruleForm.duyin" label="读音" prop="duyin" >
					<audio v-if="ruleForm.duyin.substring(0,4)=='http'" :key="'http-audio'" :src="ruleForm.duyin" controls style="width: 300px;"></audio>
					<audio v-else :key="'local-audio'" :src="$base.url+ruleForm.duyin" controls style="width: 300px;"></audio>
				</el-form-item>
			</template>
			<el-form-item class="btn">
				<el-button class="btn3"  v-if="type!='info'" type="success" @click="onSubmit">
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
	import {
		isIntNumer,
	} from "@/utils/validate";
	export default {
		data() {
			var validateIntNumber = (rule, value, callback) => {
				if(!value){
					callback();
				} else if (!isIntNumer(value)) {
					callback(new Error("请输入整数"));
				} else {
					callback();
				}
			};
			return {
				id: '',
				type: '',


				ro:{
					danci : false,
					jibie : false,
					dancifenlei : false,
					shiyi : false,
					liju : false,
					fengmiantu : false,
					duyin : false,
					clicktime : false,
					discussnum : false,
					storeupnum : false,
				},

				ruleForm: {
					danci: '',
					jibie: '',
					dancifenlei: '',
					shiyi: '',
					liju: '',
					fengmiantu: '',
					duyin: '',
					clicktime: '',
					discussnum: Number('0'),
					storeupnum: Number('0'),
				},
				jibieOptions: [],
				dancifenleiOptions: [],

				rules: {
					danci: [
						{ required: true, message: '单词不能为空', trigger: 'blur' },
					],
					jibie: [
						{ required: true, message: '级别不能为空', trigger: 'blur' },
					],
					dancifenlei: [
					],
					shiyi: [
					],
					liju: [
					],
					fengmiantu: [
					],
					duyin: [
					],
					clicktime: [
					],
					discussnum: [
						{ validator: validateIntNumber, trigger: 'blur' },
					],
					storeupnum: [
						{ validator: validateIntNumber, trigger: 'blur' },
					],
				},
			};
		},
		props: ["parent"],
		computed: {
			sessionForm() {
				return JSON.parse(this.$storage.getObj('userForm'))
			},
			sessionTable() {
				return this.$storage.get('sessionTable')
			},



		},
		components: {
		},
		created() {
		},
		methods: {
			imgPreView(url){
				this.$parent.imgPreView(url)
			},
			// 下载
			download(file){
				window.open(`${file}`)
			},
			// 初始化
			init(id,type ) {
				if (id) {
					this.id = id;
					this.type = type;
				}
				if(this.type=='info'||this.type=='else'||this.type=='msg'){
					this.info(id);
				}else if(this.type=='logistics'){
					for(let x in this.ro) {
						this.ro[x] = true
					}
					this.logistics=false;
					this.info(id);
				}else if(this.type=='cross'){
					var obj = this.$storage.getObj('crossObj');
					for (var o in obj){
						if(o=='danci'){
							this.ruleForm.danci = obj[o];
							this.ro.danci = true;
							continue;
						}
						if(o=='jibie'){
							this.ruleForm.jibie = obj[o];
							this.ro.jibie = true;
							continue;
						}
						if(o=='dancifenlei'){
							this.ruleForm.dancifenlei = obj[o];
							this.ro.dancifenlei = true;
							continue;
						}
						if(o=='shiyi'){
							this.ruleForm.shiyi = obj[o];
							this.ro.shiyi = true;
							continue;
						}
						if(o=='liju'){
							this.ruleForm.liju = obj[o];
							this.ro.liju = true;
							continue;
						}
						if(o=='fengmiantu'){
							this.ruleForm.fengmiantu = obj[o];
							this.ro.fengmiantu = true;
							continue;
						}
						if(o=='duyin'){
							this.ruleForm.duyin = obj[o];
							this.ro.duyin = true;
							continue;
						}
						if(o=='clicktime'){
							this.ruleForm.clicktime = obj[o];
							this.ro.clicktime = true;
							continue;
						}
						if(o=='discussnum'){
							this.ruleForm.discussnum = obj[o];
							this.ro.discussnum = true;
							continue;
						}
						if(o=='storeupnum'){
							this.ruleForm.storeupnum = obj[o];
							this.ro.storeupnum = true;
							continue;
						}
					}
				}

				// 获取用户信息
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

				// 设置级别选项：小学、初中、高中、四级、六级、考研
				this.jibieOptions = ["小学", "初中", "高中", "四级", "六级", "考研"];

				this.$http({
					url: `option/dancifenlei/dancifenlei`,
					method: "get",
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.dancifenleiOptions = data.data;
					} else {
						this.$message.error(data.msg);
					}
				});
			},
			// 多级联动参数

			async info(id) {
				await this.$http({
					url: `danciku/info/${id}`,
					method: "get"
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.ruleForm = data.data;
						//解决前台上传图片后台不显示的问题
						let reg=new RegExp('../../../upload','g')//g代表全部
					} else {
						this.$message.error(data.msg);
					}
				});
			},

			// 提交
			async onSubmit() {
					if(this.ruleForm.fengmiantu!=null) {
						this.ruleForm.fengmiantu = this.ruleForm.fengmiantu.replace(new RegExp(this.$base.url,"g"),"");
					}
					if(this.ruleForm.duyin!=null) {
						this.ruleForm.duyin = this.ruleForm.duyin.replace(new RegExp(this.$base.url,"g"),"");
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
								url: `danciku/${!this.ruleForm.id ? "save" : "update"}`,
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
											this.parent.dancikuCrossAddOrUpdateFlag = false;
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
			// 获取uuid
			getUUID () {
				return new Date().getTime();
			},
			// 返回
			back() {
				this.parent.showFlag = true;
				this.parent.addOrUpdateFlag = false;
				this.parent.dancikuCrossAddOrUpdateFlag = false;
			},
			fengmiantuUploadChange(fileUrls) {
				this.ruleForm.fengmiantu = fileUrls;
			},
			duyinUploadChange(fileUrls) {
				this.ruleForm.duyin = fileUrls;
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
	.amap-wrapper {
		width: 100%;
		height: 500px;
	}

	.search-box {
		position: absolute;
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
	.add-update-preview ::v-deep .el-form-item.editorBox {
		margin: 10px 0px 10px 0 ;
		display: flex;
		width: 100%;
	}
	.add-update-preview .el-form-item.editorBox ::v-deep .el-form-item__label {
		padding: 0 10px;
		color: #333333;
		white-space: nowrap;
		font-weight: 600;
		width: 140px;
		font-size: 16px;
		line-height: 64px;
		text-align: left;
		height: 64px;
	}

	.add-update-preview .el-form-item.editorBox ::v-deep .el-form-item__content {
		margin-left: auto !important;
		padding: 0 10px;
		margin: auto !important;
		display: flex;
		width: 100%;
		justify-content: flex-start;
		align-items: flex-start;
		flex-wrap: wrap;
	}
	.add-update-preview ::v-deep.el-form-item.editorBox .editor {
		border-radius: 4px;
		box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
		max-width: 100% !important;
		flex: 1;
		background: #fff;
		height: auto;
	}
	.add-update-preview ::v-deep.el-form-item.editorBox .editor .ql-toolbar {
		background: none;
	}
	.add-update-preview ::v-deep.el-form-item.editorBox .editor .ql-container {
		background: none;
		min-height: 200px;
	}
	.add-update-preview ::v-deep.el-form-item.editorBox .editor .ql-container .ql-blank::before {
		color: #000;
	}

	.add-update-preview ::v-deep .el-form-item.textBox {
		margin: 10px 0px 10px 0 ;
		display: flex;
		width: calc(50% - 0px);
	}
	.add-update-preview .el-form-item.textBox ::v-deep .el-form-item__label {
		padding: 0 10px;
		color: #333333;
		white-space: nowrap;
		font-weight: 500;
		width: 140px;
		font-size: 16px;
		line-height: 64px;
		text-align: left;
		height: 64px;
	}

	.add-update-preview .el-form-item.textBox ::v-deep .el-form-item__content {
		margin-left: auto !important;
		padding: 0 10px;
		margin: auto !important;
		display: flex;
		width: 100%;
		justify-content: flex-start;
		align-items: flex-start;
		flex-wrap: wrap;
	}
	.add-update-preview ::v-deep.el-form-item.textBox span.text {
		border: 1px solid #DADFE6;
		border-radius: 4px;
		padding: 15px;
		box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
		outline: none;
		color: #333;
		background: #fff;
		flex: 1;
		width: 100%;
		font-size: 16px;
		line-height: 30px;
		height: auto;
	}

	.add-update-preview .el-input {
		flex: 1;
	}
	.add-update-preview .el-input ::v-deep .el-input__inner {
		border:  1px solid #DADFE6;
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
		border:  1px solid #DADFE6;
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
	.add-update-preview .el-input-number {
		text-align: left;
		flex: 1;
	}
	.add-update-preview .el-input-number ::v-deep .el-input__inner {
		text-align: left;
		border:  1px solid #DADFE6;
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
	.add-update-preview .el-input-number ::v-deep .is-disabled .el-input__inner {
		text-align: left;
		border:  1px solid #DADFE6;
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
	.add-update-preview .el-input-number ::v-deep .el-input-number__decrease {
		display: none;
	}
	.add-update-preview .el-input-number ::v-deep .el-input-number__increase {
		display: none;
	}
	.add-update-preview .el-select {
		width: auto;
		min-width: 100%;
	}
	.add-update-preview .el-select ::v-deep .el-input__inner {
		border:  1px solid #DADFE6;
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
	.add-update-preview .el-select ::v-deep .is-disabled .el-input__inner {
		border:  1px solid #DADFE6;
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
	.add-update-preview .el-date-editor {
		width: auto;
		min-width: 100%;
	}
	.add-update-preview .el-date-editor ::v-deep .el-input__inner {
		border:  1px solid #DADFE6;
		border-radius: 4px;
		padding: 0 30px;
		box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
		outline: none;
		color: #333;
		background: #FFFFFF;
		flex: 1;
		width: 100%;
		font-size: 16px;
		height: 64px;
	}
	.add-update-preview .el-date-editor ::v-deep .el-input__inner[readonly="readonly"] {
		border:  1px solid #DADFE6;
		border-radius: 4px;
		padding: 0 30px;
		box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
		outline: none;
		color: #333;
		background: #FFFFFF;
		flex: 1;
		width: 100%;
		font-size: 16px;
		height: 64px;
	}
	.add-update-preview .viewBtn {
		border: 1px solid #DADFE6;
		border-radius: 8px;
		padding: 0 12px;
		outline: none;
		color: #FFFFFF;
		background: #FA6B04;
		flex: 1;
		font-weight: bold;
		width: 100%;
		font-size: 16px;
		height: 64px;
		.iconfont {
			margin: 0 2px;
			color: #fff;
			display: none;
			font-size: 14px;
			height: 40px;
		}
	}
	.add-update-preview .viewBtn:hover {
		opacity: 0.8;
	}
	.add-update-preview .downBtn {
		border: 1px solid #DADFE6;
		border-radius: 8px;
		padding: 0 12px;
		outline: none;
		color: #FFFFFF;
		background: #FA6B04;
		flex: 1;
		font-weight: bold;
		width: 100%;
		font-size: 16px;
		height: 64px;
		.iconfont {
			margin: 0 2px;
			color: #fff;
			display: none;
			font-size: 14px;
			height: 40px;
		}
	}
	.add-update-preview .downBtn:hover {
		opacity: 0.8;
	}
	.add-update-preview .unBtn {
		border: 1px solid #DADFE6;
		border-radius: 8px;
		padding: 0 12px;
		outline: none;
		color: #FFFFFF;
		background: #FA6B04;
		flex: 1;
		font-weight: bold;
		width: 100%;
		font-size: 16px;
		height: 64px;
		.iconfont {
			margin: 0 2px;
			color: #fff;
			display: none;
			font-size: 14px;
			height: 40px;
		}
	}
	.add-update-preview .unBtn:hover {
		opacity: 0.8;
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
	.add-update-preview ::v-deep .el-form-item.fileupload {
		margin: 14px 20px;
		display: flex;
		height: auto;
	}
	.add-update-preview .el-form-item.fileupload ::v-deep .el-form-item__label {
		padding: 0 10px;
		color: #333333;
		white-space: nowrap;
		font-weight: 600;
		width: 180px;
		font-size: 16px;
		line-height: 64px ;
		text-align: right;
		height: 64px ;
	}

	.add-update-preview .el-form-item.fileupload ::v-deep .el-form-item__content {
		margin-left: auto !important;
		margin: auto !important;
		display: flex;
		width: 100%;
		justify-content: flex-start;
		align-items: flex-start;
		flex-wrap: wrap;
	}
	.add-update-preview .el-form-item.fileupload ::v-deep .el-upload-dragger {
		border-radius: 4px;
		padding: 10px 12px;
		box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
		outline: none;
		color: #333;
		background: #fff;
		width: auto;
		font-size: 24px;
		border-color: #e8e9eb;
		border-width: 1px;
		border-style: solid;
		height: auto;
	}
	.add-update-preview .el-form-item.fileupload .el-upload-dragger ::v-deep .el-icon-upload {
		margin: 40px 0 16px;
		color: #f00;
		font-size: 67px;
		line-height: 50px;
	}
	.add-update-preview .el-form-item.fileupload .el-upload-dragger ::v-deep .el-upload__text {
		color: #606266;
		textalign: center;
		fontsize: 14px;
	}
	.add-update-preview .el-form-item.fileupload .el-upload-dragger ::v-deep .el-upload__text em {
		fontstyle: normal;
		color: #409EFF;
	}

	.add-update-preview .el-textarea ::v-deep .el-textarea__inner {
		border-radius: 4px;
		padding: 12px;
		box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
		outline: none;
		color: #666;
		background: #fff;
		width: 100%;
		font-size: 16px;
		border-color: #e8e9eb;
		border-width: 1px;
		border-style: solid;
		height: auto;
	}
	.add-update-preview .el-textarea ::v-deep .el-textarea__inner[readonly="readonly"] {
		border-radius: 4px;
		padding: 12px;
		box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
		outline: none;
		color: #666;
		background: #fff;
		width: 100%;
		font-size: 16px;
		border-color: #e8e9eb;
		border-width: 1px;
		border-style: solid;
		height: auto;
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
	.add-update-preview .el-form-item.btn ::v-deep .el-form-item__label {
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

	.add-update-preview .el-form-item.btn ::v-deep .el-form-item__content {
	}
</style>

