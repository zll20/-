<template>
	<div class="main-content" :style='{"minHeight":"100vh","width":"100%","padding":"15px 30px 30px 30px","fontSize":"16px","height":"auto"}'>
		<!-- 列表页 -->
		<div v-if="!showFlag">
			<el-form :style='{"padding":"30px","margin":"0 0 0px","borderRadius":"8px","flexWrap":"wrap","background":"#fff","display":"flex","width":"100%"}' :inline="true" :model="searchForm" class="add-update-preview">
				<el-form-item :style='{"width":"calc(50% - 0px)","margin":"10px 0px 10px 0 ","display":"flex"}' label="试卷名称">
					<el-input v-model="searchForm.papername" placeholder="试卷名称" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"calc(50% - 0px)","margin":"10px 0px 10px 0 ","display":"flex"}' label="试题名称">
					<el-input v-model="searchForm.questionname" placeholder="试题名称" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"calc(50% - 0px)","margin":"10px 0px 10px 0 ","display":"flex"}'>
					<el-button class="btn3" round @click="search()">
						<span class="icon iconfont icon-queren15" :style='{"margin":"0 2px","fontSize":"16px","color":"#fff","display":"none","height":"40px"}'></span>
						查询
					</el-button>
					<el-button class="btn3" type="primary" round @click="back()">
						<span class="icon iconfont icon-guanbi2"></span>
						返回
					</el-button>
				</el-form-item>
			</el-form>
			<div class="table-content">
				<el-table
					:data="dataList"
					border
					v-loading="dataListLoading"
					@selection-change="selectionChangeHandler"
					style="width: 100%;"
					>
					<el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
					<el-table-column prop="examno" header-align="center" align="center" sortable label="考试编号"></el-table-column>
					<el-table-column prop="username" header-align="center" align="center" sortable label="姓名"></el-table-column>
					<el-table-column
						prop="papername"
						header-align="center"
						align="center"
						sortable
						label="试卷名称"
						></el-table-column>
					<el-table-column
						prop="questionname"
						header-align="center"
						align="center"
						sortable
						label="试题名称"
					>
						<template slot-scope="scope">
							<div class="ql-snow ql-editor" v-html="scope.row.questionname"></div>
						</template>
					</el-table-column>
					<el-table-column
						prop="myscore"
						header-align="center"
						align="center"
						sortable
						label="试题名称类型"
						>
						<template slot-scope="scope">
							<el-tag type="success" v-if="scope.row.type==0">单选题</el-tag>
							<el-tag type="warning" v-if="scope.row.type==1">多选题</el-tag>
							<el-tag type="info" v-if="scope.row.type==2">判断题</el-tag>
							<el-tag type="primary" v-if="scope.row.type==3">填空题</el-tag>
							<el-tag type="danger" v-if="scope.row.type==4">主观题</el-tag>
						</template>
					</el-table-column>
					<el-table-column prop="score" header-align="center" align="center" sortable label="分值"></el-table-column>
					<el-table-column prop="answer" header-align="center" align="center" sortable label="正确答案"></el-table-column>
					<el-table-column
						prop="myanswer"
						header-align="center"
						align="center"
						sortable
						label="考生答案"
					></el-table-column>
					<el-table-column
						prop="myscore"
						header-align="center"
						align="center"
						sortable
						label="考生分值"
						>
						<template slot-scope="scope">
							<el-tag v-if="scope.row.myscore==0" type="info">{{scope.row.myscore}}</el-tag>
							<el-tag v-else type="warning">{{scope.row.myscore}}</el-tag>
						</template>
					</el-table-column>
					<el-table-column
						prop="addtime"
						header-align="center"
						align="center"
						sortable
						width="170"
						label="考试时间"
						></el-table-column>
				</el-table>
				<el-pagination
					@size-change="sizeChangeHandle"
					@current-change="currentChangeHandle"
					:current-page="pageIndex"
					:page-sizes="[10, 50, 100, 200]"
					:page-size="pageSize"
					:total="totalPage"
					layout="total, sizes, prev, pager, next, jumper"
					class="pagination-content"
					></el-pagination>
			</div>
		</div>
	</div>
</template>
<script>
	export default {
		data() {
			return {
				searchForm: {
					key: ""
				},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				showFlag: false,
				paperid: 0,
				userid: 0,
				examno: 0
			};
		},
		props: ["parent"],
		components: {},
		methods: {
			init(row) {
				this.paperid = row.paperid;
				this.userid = row.userid;
				this.examno = row.examno;
				this.getDataList();
			},
			search() {
				this.pageIndex = 1;
				this.getDataList();
			},
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true;
				var params = {
					page: this.pageIndex,
					limit: this.pageSize,
					paperid: this.paperid,
					userid: this.userid,
					examno: this.examno
				};
				if (this.searchForm.papername) {
					params["papername"] = `%${this.searchForm.papername}%`;
				}
				if (this.searchForm.questionname) {
					params["questionname"] = `%${this.searchForm.questionname}%`;
				}
				this.$http({
					url: this.$api.examrecordpage,
					method: "get",
					params: params
				}).then(({ data }) => {
					if (data && data.code === 0) {
						for(let x in data.data.list){
							data.data.list[x].questionname = data.data.list[x].questionname.replace(/img src/gi,"img style=\"width:100%;\" src");
						}
						this.dataList = data.data.list;
						this.totalPage = data.data.total;
					} else {
						this.dataList = [];
						this.totalPage = 0;
					}
					this.dataListLoading = false;
				});
			},
			// 每页数
			sizeChangeHandle(val) {
				this.pageSize = val;
				this.pageIndex = 1;
				this.getDataList();
			},
			// 当前页
			currentChangeHandle(val) {
				this.pageIndex = val;
				this.getDataList();
			},
			// 多选
			selectionChangeHandler(val) {
				this.dataListSelections = val;
			},
			back() {
				this.parent.showFlag = false;
			}
		}
	};
</script>
<style lang="scss" scoped>
	.form-content {
		background: transparent;
	}
	.table-content {
		background: transparent;
	}
	.add-update-preview .btn3 {
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
	.add-update-preview .btn3:hover {
		opacity: 0.8;
	}
	.add-update-preview .btn4 {
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
	.add-update-preview .btn4:hover {
		opacity: 0.8;
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
		margin-left: 140px;
	}
	
	.add-update-preview .el-input {
				width: 100%;
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
</style>

