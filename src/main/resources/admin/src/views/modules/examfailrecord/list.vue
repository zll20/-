<template>
	<div class="main-content" :style='{"minHeight":"100vh","width":"100%","padding":"15px 30px 30px 30px","fontSize":"16px","height":"auto"}'>
		<!-- 列表页 -->
		<template v-if="!showFlag">
			<el-form :style='{"width":"100%","padding":"0","margin":"0 0 30px 0","fontSize":"inherit","borderRadius":"8px","background":"non"}' :inline="true" :model="searchForm" class="center-form-pv">
				<el-row :style='{"padding":"20px 10px 0px","boxShadow":"none","borderColor":"#00000050","borderRadius":"0px","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","fontSize":"inherit","position":"relative","borderStyle":"solid"}'>
					<div :style='{"margin":"0 1% 20px 0","fontSize":"inherit","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"#333333","display":"inline-block","lineHeight":"41px","fontSize":"inherit","fontWeight":"500","height":"41px"}' class="item-label">试卷名称</label>
						<el-input v-model="searchForm.papername" placeholder="试卷名称" clearable></el-input>
					</div>
					<div :style='{"margin":"0 1% 20px 0","fontSize":"inherit","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"#333333","display":"inline-block","lineHeight":"41px","fontSize":"inherit","fontWeight":"500","height":"41px"}' class="item-label">试题名称</label>
						<el-input v-model="searchForm.questionname" placeholder="试题名称" clearable></el-input>
					</div>
					<el-button class="search" :style='{"border":"0","cursor":"pointer","padding":"0px 24px","outline":"none","color":"#fff","borderRadius":"24px","background":"#FA6B04","width":"176px","fontSize":"16px","fontWeight":"bold","height":"41px"}' type="success" @click="search()">
						<span class="icon iconfont icon-fangdajing01" :style='{"margin":"0 0px","fontSize":"16px","color":"#fff","display":"none","height":"40px"}'></span>
						查询
					</el-button>
				</el-row>
			</el-form>
			<div :style='{"width":"100%","padding":"20px ","borderRadius":"8px","background":"#fff","borderWidth":"0"}'>
				<el-table
					:stripe='false'
					:style='{"padding":"0","borderColor":"#e7e8fc","borderRadius":"10px","borderWidth":"0px 0 0 0px","background":"none","width":"100%","borderStyle":"solid"}'
					:data="dataList"
					:border='false'
					v-loading="dataListLoading"
					@selection-change="selectionChangeHandler"
					style="width: 100%;"
				>
					<el-table-column :resizable='true' type="selection" header-align="center" align="center" width="50"></el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="username" header-align="center" align="center" label="姓名"></el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="papername"
						header-align="center"
						align="center"
						label="试卷名称"
					></el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="questionname"
						header-align="center"
						align="center"
						label="试题名称"
					>
						<template slot-scope="scope">
							<div class="ql-snow ql-editor" v-html="scope.row.questionname"></div>
						</template>
					</el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="type"
						header-align="center"
						align="center"
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
					<el-table-column :resizable='true' :sortable='true' prop="score" header-align="center" align="center" label="分值"></el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="right" header-align="center" align="center" label="正确答案"></el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="myanswer"
						header-align="center"
						align="center"
						label="考生答案"
					></el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="analysis"
						header-align="center"
						align="center"
						label="试题名称分析"
					></el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="addtime"
						header-align="center"
						align="center"
						width="170"
						label="考试时间"
					></el-table-column>
				</el-table>
			</div>

			<el-pagination
				@size-change="sizeChangeHandle"
				@current-change="currentChangeHandle"
				:current-page="pageIndex"
				:page-sizes="[10, 50, 100, 200]"
				:page-size="pageSize"
				:total="totalPage"
				:layout="layouts.join()"
				prev-text="<"
				next-text=">"
				:hide-on-single-page="false"
				:style='{"padding":"0","margin":"20px 0 0","whiteSpace":"nowrap","color":"#333","display":"flex","width":"100%","fontWeight":"500","justifyContent":"center"}'
			></el-pagination>
		</template>
	</div>
</template>
<script>
	export default {
		data() {
			return {
				layouts: ["total","prev","pager","next","sizes","jumper"],
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
				user: {}
			};
		},
		mounted() {
			this.$http({
				url: `${this.$storage.get("sessionTable")}/session`,
				method: "get"
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.user = data.data;
				} else {
					this.$message.error(data.msg);
				}
			});
			this.init();
			this.getDataList();
		},
		components: {},
		methods: {
			init() {},
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
					sort: "id",
					myscore: 0,
					userid: this.id,
					ismark: 1
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
						let arr = []
						for(let x in data.data.list){
							data.data.list[x].questionname = data.data.list[x].questionname.replace(/img src/gi,"img style=\"width:100%;\" src");
							if(data.data.list[x].type==0||data.data.list[x].type==2){
								arr = JSON.parse(data.data.list[x].options)
								for(let i in arr){
									if(data.data.list[x].answer == arr[i].code){
										data.data.list[x].right = arr[i].text
									}
								}
							}else if(data.data.list[x].type==1){
								arr = JSON.parse(data.data.list[x].options)
								data.data.list[x].answer.split(',').forEach(item=>{
									for(let i in arr){
										if (item == arr[i].code) {
											if (data.data.list[x].right) {
												data.data.list[x].right += ','
												data.data.list[x].right = data.data.list[x].right + arr[i].text
											}else{
												data.data.list[x].right = arr[i].text
											}
										}
									}
								})
							}else if(data.data.list[x].type==3){
								data.data.list[x].right = data.data.list[x].answer
							}else if(data.data.list[x].type==4){
								data.data.list[x].right = '略'
							}
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

	
	// form
	.center-form-pv .el-input {
				width: 100%;
			}
	
	.center-form-pv .el-input ::v-deep .el-input__inner {
				border: 1px solid #DADFE6;
				border-radius: 4px;
				padding: 0 12px;
				box-shadow: none;
				outline: none;
				color: #333;
				width: 100%;
				font-size: 16px;
				height: 41px;
			}
	
	// table
	.el-table ::v-deep .el-table__header-wrapper thead {
				color: #212D3F;
				font-weight: 400;
				width: 100%;
			}
	
	.el-table ::v-deep .el-table__header-wrapper thead tr {
				background: none;
			}
	
	.el-table ::v-deep .el-table__header-wrapper thead tr th {
				padding: 12px 0;
				background: none;
				border-color: #999999;
				border-width: 0 0px 1px 0;
				border-style: solid;
				text-align: center;
			}
	
	.el-table ::v-deep .el-table__header-wrapper thead tr th .cell {
				padding: 0 0 0 5px;
				word-wrap: normal;
				color: #212D3F;
				white-space: normal;
				font-weight: 400;
				display: flex;
				vertical-align: middle;
				font-size: 14px;
				line-height: 24px;
				text-overflow: ellipsis;
				word-break: break-all;
				width: 100%;
				justify-content: flex-start;
				align-items: center;
				position: relative;
				min-width: 110px;
			}
	
	
	.el-table ::v-deep .el-table__body-wrapper {
				position: relative;
			}
	.el-table ::v-deep .el-table__body-wrapper tbody {
				width: 100%;
			}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr {
				background: #fff;
			}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td {
				padding: 4px 0;
				color: #555;
				background: none;
				border-color: #999999;
				border-width: 0 0px 1px 0;
				border-style: solid;
				text-align: left;
			}
	
		
	.el-table ::v-deep .el-table__body-wrapper tbody tr:hover td {
				padding: 4px 0;
				color: #000;
				background: rgba(250, 107, 4, 0.1);
				border-color: #ddd;
				border-width: 0 0px 1px 0;
				border-style: solid;
				text-align: left;
			}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td {
				padding: 4px 0;
				color: #555;
				background: none;
				border-color: #999999;
				border-width: 0 0px 1px 0;
				border-style: solid;
				text-align: left;
			}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .cell {
				padding: 0 0 0 5px;
				overflow: hidden;
				word-break: break-all;
				white-space: normal;
				font-size: inherit;
				line-height: 24px;
				text-overflow: ellipsis;
			}
	
	// pagination
	.main-content .el-pagination ::v-deep .el-pagination__total {
				margin: 0 20px 0 0;
				color: #333333;
				display: inline-block;
				vertical-align: top;
				font-size: 15px;
				line-height: 40px;
				height: 40px;
			}
	
	.main-content .el-pagination ::v-deep .btn-prev {
				border: 1px solid #333333;
				cursor: not-allowed;
				padding: 0;
				margin: 0 5px;
				color: #333333;
				display: inline-block;
				vertical-align: top;
				font-size: 15px;
				border-radius: 8px  8px  8px  8px;
				box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				background: none;
				width: 40px;
				height: 40px;
				order: 2;
			}
	
	.main-content .el-pagination ::v-deep .btn-next {
				border: 1px solid #333333;
				cursor: not-allowed;
				padding: 0;
				margin: 0 5px;
				color: #333333;
				display: inline-block;
				vertical-align: top;
				font-size: 15px;
				border-radius: 8px  8px  8px  8px;
				box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				background: none;
				width: 40px;
				height: 40px;
				order: 2;
			}
	
	.main-content .el-pagination ::v-deep .btn-prev:disabled {
				border: 1px solid #333333;
				cursor: not-allowed;
				padding: 0;
				margin: 0 5px;
				color: #333333;
				display: inline-block;
				vertical-align: top;
				font-size: 15px;
				border-radius: 8px  8px  8px  8px;
				box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				background: none;
				width: 40px;
				height: 40px;
			}
	
	.main-content .el-pagination ::v-deep .btn-next:disabled {
				border: 1px solid #333333;
				cursor: not-allowed;
				padding: 0;
				margin: 0 5px;
				color: #333333;
				display: inline-block;
				vertical-align: top;
				font-size: 15px;
				border-radius: 8px  8px  8px  8px;
				box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				background: none;
				width: 40px;
				height: 40px;
			}
	
	.main-content .el-pagination ::v-deep .el-pager {
				padding: 0;
				margin: 0;
				display: inline-block;
				vertical-align: top;
				order: 2;
			}
	
	.main-content .el-pagination ::v-deep .el-pager .number {
				border: 1px solid #000;
				cursor: not-allowed;
				padding: 0;
				margin: 0 5px;
				color: #333333;
				display: inline-block;
				vertical-align: top;
				font-size: 15px;
				line-height: 40px;
				border-radius: 8px  8px  8px  8px;
				background: none;
				width: 40px;
				height: 40px;
			}
	
	.main-content .el-pagination ::v-deep .el-pager .number:hover {
				border: 1px solid #333333;
				cursor: not-allowed;
				padding: 0;
				margin: 0 5px;
				color: #212D3F;
				display: inline-block;
				vertical-align: top;
				font-size: 15px;
				line-height: 40px;
				border-radius: 8px  8px  8px  8px;
				background: none;
				width: 40px;
				height: 40px;
			}
	
	.main-content .el-pagination ::v-deep .el-pager .number.active {
				border: 1px solid #333333;
				cursor: not-allowed;
				padding: 0;
				margin: 0 5px;
				color: #212D3F;
				display: inline-block;
				vertical-align: top;
				font-size: 15px;
				line-height: 40px;
				border-radius: 8px  8px  8px  8px;
				background: none;
				width: 40px;
				height: 40px;
			}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes {
				display: inline-block;
				vertical-align: top;
				font-size: 15px;
				line-height: 40px;
				height: 40px;
			}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes .el-input {
				margin: 0 5px;
				width: 100px;
				position: relative;
			}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes .el-input .el-input__inner {
				border: 1px solid #707070;
				cursor: pointer;
				padding: 0 25px 0 8px;
				color: #333333;
				display: inline-block;
				font-size: 15px;
				line-height: 40px;
				border-radius: 3px;
				box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				outline: 0;
				background: none;
				width: 100%;
				text-align: center;
				height: 40px;
			}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes .el-input span.el-input__suffix {
				top: 0;
				position: absolute;
				right: 0;
				height: 100%;
			}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes .el-input .el-input__suffix .el-select__caret {
				cursor: pointer;
				color: #fff;
				width: 25px;
				font-size: 15px;
				line-height: 28px;
				text-align: center;
			}
	
	.main-content .el-pagination ::v-deep .el-pagination__jump {
				margin: 0 0 0 24px;
				color: #333333;
				display: inline-block;
				vertical-align: top;
				font-size: 15px;
				line-height: 40px;
				height: 40px;
				order: 3;
			}
	
	.main-content .el-pagination ::v-deep .el-pagination__jump .el-input {
				border-radius: 3px;
				padding: 0 2px;
				margin: 0 2px;
				display: inline-block;
				width: 50px;
				font-size: 15px;
				line-height: 18px;
				position: relative;
				text-align: center;
				height: 28px;
			}
	
	.main-content .el-pagination ::v-deep .el-pagination__jump .el-input .el-input__inner {
				border: 0px solid #333333;
				cursor: pointer;
				padding: 0 3px;
				color: #333333;
				display: inline-block;
				font-size: 15px;
				line-height: 40px;
				border-radius: 8px;
				box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				outline: 0;
				background: #fff;
				width: 100%;
				text-align: center;
				height: 40px;
			}
	
	.center-form-pv .search {
				border: 0;
				cursor: pointer;
				border-radius: 24px;
				padding: 0px 24px;
				outline: none;
				color: #fff;
				background: #FA6B04;
				font-weight: bold;
				width: 176px;
				font-size: 16px;
				height: 41px;
			}
	
	.center-form-pv .search:hover {
				opacity: 0.8;
			}
</style>

