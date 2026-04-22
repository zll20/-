<template>
	<div class="main-content" :style='{"minHeight":"100vh","width":"100%","padding":"15px 30px 30px 30px","fontSize":"16px","height":"auto"}'>
		<template v-if="showFlag">
			<el-form class="center-form-pv" :style='{"width":"100%","padding":"0","margin":"0 0 30px 0","fontSize":"inherit","borderRadius":"8px","background":"non"}' :inline="true" :model="searchForm">
				<el-row :style='{"padding":"20px 10px 0px","boxShadow":"none","borderColor":"#00000050","borderRadius":"0px","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","fontSize":"inherit","position":"relative","borderStyle":"solid"}'>
					<div :style='{"margin":"0 1% 20px 0","fontSize":"inherit","display":"flex"}' class="select" label="级别" prop="jibie">
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"#333333","display":"inline-block","lineHeight":"41px","fontSize":"inherit","fontWeight":"500","height":"41px"}' class="item-label">级别</label>
						<el-select clearable v-model="searchForm.jibie" placeholder="请选择级别">
							<el-option label="小学" value="小学"></el-option>
							<el-option label="初中" value="初中"></el-option>
							<el-option label="高中" value="高中"></el-option>
							<el-option label="四级" value="四级"></el-option>
							<el-option label="六级" value="六级"></el-option>
							<el-option label="考研" value="考研"></el-option>
						</el-select>
					</div>
					<el-button class="search" type="success" @click="search()">
						<span class="icon iconfont icon-fangdajing01" :style='{"margin":"0 0px","fontSize":"16px","color":"#fff","display":"none","height":"40px"}'></span>
						查询
					</el-button>
				</el-row>

				<el-row class="actions" :style='{"width":"calc(100% + 8px)","padding":"0px","margin":"0px 0 0px -4px","flexWrap":"wrap","justifyContent":"0","display":"flex"}'>
					<el-button class="add" v-if="isAuth('wenkan','新增')" type="success" @click="addOrUpdateHandler()">
						<span class="icon iconfont icon-tianjia17" :style='{"margin":"0 0px","fontSize":"16px","color":"#FA6B04","height":"auto"}'></span>
						添加
					</el-button>
					<el-button class="del" v-if="isAuth('wenkan','删除')" :disabled="dataListSelections.length?false:true" type="danger" @click="deleteHandler()">
						<span class="icon iconfont icon-shanchu6" :style='{"margin":"0 0px","fontSize":"16px","color":"#FA6B04","height":"auto"}'></span>
						删除
					</el-button>
				</el-row>
			</el-form>

			<div :style='{"width":"100%","padding":"20px ","borderRadius":"8px","background":"#fff","borderWidth":"0"}'>
				<el-table class="tables"
					:stripe='false'
					:style='{"padding":"0","borderColor":"#e7e8fc","borderRadius":"10px","borderWidth":"0px 0 0 0px","background":"none","width":"100%","borderStyle":"solid"}'
					:border='false'
					v-if="isAuth('wenkan','查看')"
					:data="dataList"
					v-loading="dataListLoading"
					@selection-change="selectionChangeHandler">
					<el-table-column :resizable='true' type="selection" align="center" width="50"></el-table-column>
					<el-table-column :resizable='true' :sortable='true' label="序号" type="index" width="50" />

					<el-table-column :resizable='true' :sortable='true' prop="mingcheng" label="名称">
						<template slot-scope="scope">
							{{scope.row.mingcheng}}
						</template>
					</el-table-column>

					<el-table-column :resizable='true' :sortable='true' prop="jibie" label="级别">
						<template slot-scope="scope">
							{{scope.row.jibie}}
						</template>
					</el-table-column>

					<el-table-column width="300" label="操作">
						<template slot-scope="scope">
							<el-button class="view" v-if="isAuth('wenkan','查看')" type="success" @click="addOrUpdateHandler(scope.row.id,'info')">
								<span class="icon iconfont icon-chakan2" :style='{"margin":"0 0px","fontSize":"14px","color":"#333","display":"none","height":"40px"}'></span>
								详情
							</el-button>
							<el-button class="edit" v-if="isAuth('wenkan','修改')" type="success" @click="addOrUpdateHandler(scope.row.id)">
								<span class="icon iconfont icon-xiugai13" :style='{"margin":"0 0px","fontSize":"14px","color":"rgba(255, 140, 0, 1)","display":"none","height":"40px"}'></span>
								修改
							</el-button>
							<el-button class="del" v-if="isAuth('wenkan','删除')" type="primary" @click="deleteHandler(scope.row.id)">
								<span class="icon iconfont icon-shanchu6" :style='{"margin":"0 0px","fontSize":"14px","color":"rgba(220, 38, 38, 1)","display":"none","height":"40px"}'></span>
								删除
							</el-button>
						</template>
					</el-table-column>
				</el-table>
			</div>

			<el-pagination
				@size-change="sizeChangeHandle"
				@current-change="currentChangeHandle"
				:current-page="pageIndex"
				background
				:page-sizes="[10, 50, 100, 200]"
				:page-size="pageSize"
				:layout="layouts.join()"
				:total="totalPage"
				prev-text="< "
				next-text="> "
				:hide-on-single-page="false"
				:style='{"padding":"0","margin":"20px 0 0","whiteSpace":"nowrap","color":"#333","display":"flex","width":"100%","fontWeight":"500","justifyContent":"center"}'
			></el-pagination>
		</template>

		<add-or-update v-if="addOrUpdateFlag" :parent="this" ref="addOrUpdate"></add-or-update>
	</div>
</template>

<script>
import AddOrUpdate from './add-or-update.vue'
export default {
	data() {
		return {
			searchForm: {
				key: "",
				jibie: ""
			},
			dataList: [],
			pageIndex: 1,
			pageSize: 10,
			totalPage: 0,
			dataListLoading: false,
			dataListSelections: [],
			showFlag: true,
			addOrUpdateFlag:false,
			layouts: ["total","prev","pager","next","sizes","jumper"],
		};
	},
	created() {
		this.init();
		this.getDataList();
	},
	mounted() {
	},
	watch: {
	},
	filters: {
	},
	computed: {
	},
	components: {
		AddOrUpdate,
	},
	methods: {
		init () {
		},
		search() {
			this.pageIndex = 1;
			this.getDataList();
		},
		getDataList() {
			this.dataListLoading = true;
			let params = {
				page: this.pageIndex,
				limit: this.pageSize,
				sort: 'id',
				order: 'desc',
			}
			if(this.searchForm.jibie!='' && this.searchForm.jibie!=undefined){
				params['jibie'] = this.searchForm.jibie
			}
			this.$http({
				url: "wenkan/list",
				method: "get",
				params: params
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.dataList = data.data.list;
					this.totalPage = data.data.total;
				} else {
					this.dataList = [];
					this.totalPage = 0;
				}
				this.dataListLoading = false;
			});
		},
		sizeChangeHandle(val) {
			this.pageSize = val;
			this.pageIndex = 1;
			this.getDataList();
		},
		currentChangeHandle(val) {
			this.pageIndex = val;
			this.getDataList();
		},
		selectionChangeHandler(val) {
			this.dataListSelections = val;
		},
		addOrUpdateHandler(id,type) {
			this.showFlag = false;
			this.addOrUpdateFlag = true;
			this.crossAddOrUpdateFlag = false;
			if(type!='info'&&type!='msg'){
				type = 'else';
			}
			this.$nextTick(() => {
				this.$refs.addOrUpdate.init(id,type);
			});
		},
		async deleteHandler(id ) {
			var ids = id? [Number(id)]: this.dataListSelections.map(item => {
				return Number(item.id);
			});
			await this.$confirm(`确定进行[${id ? "删除" : "批量删除"}]操作?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning"
			}).then(async () => {
				await this.$http({
					url: "wenkan/delete",
					method: "post",
					data: ids
				}).then(async ({ data }) => {
					if (data && data.code === 0) {
						this.$message({
							message: "操作成功",
							type: "success",
							duration: 1500,
							onClose: () => {
								this.search();
							}
						});
					} else {
						this.$message.error(data.msg);
					}
				});
			});
		},
	}
}
</script>

<style lang="scss" scoped>
.center-form-pv {
	.el-date-editor.el-input {
		width: auto;
	}
}
.el-input {
	width: auto;
}
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
.center-form-pv .el-select {
	width: 100%;
}
.center-form-pv .el-select ::v-deep .el-input__inner {
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
.center-form-pv .el-date-editor {
	width: 100%;
}
.center-form-pv .el-date-editor ::v-deep .el-input__inner {
	border: 1px solid #DADFE6;
	border-radius: 4px;
	padding: 0 30px;
	box-shadow: none;
	outline: none;
	color: #333;
	width: 100%;
	font-size: 16px;
	height: 41px;
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
.center-form-pv .actions .add {
	border: 0;
	cursor: pointer;
	border-radius: 6px;
	padding: 0 10px;
	margin: 4px;
	outline: none;
	color: #FA6B04;
	background: none;
	width: auto;
	font-size: 14px;
	height: 36px;
}
.center-form-pv .actions .add:hover {
	opacity: 0.8;
}
.center-form-pv .actions .del {
	border: 0;
	cursor: pointer;
	border-radius: 6px;
	padding: 0 10px;
	margin: 4px;
	outline: none;
	color: #FA6B04;
	background: none;
	width: auto;
	font-size: 14px;
	height: 36px;
}
.center-form-pv .actions .del:hover {
	opacity: 0.8;
}
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
.el-table ::v-deep .el-table__body-wrapper tbody tr td .cell {
	padding: 0 0 0 5px;
	overflow: hidden;
	word-break: break-all;
	white-space: normal;
	font-size: inherit;
	line-height: 24px;
	text-overflow: ellipsis;
}
.el-table ::v-deep .el-table__body-wrapper tbody tr td .view {
	border: 0;
	cursor: pointer;
	border-radius: 6px;
	padding: 0 10px;
	margin: 0 5px 5px 0;
	outline: none;
	color: #FA6B04;
	background: none;
	width: auto;
	font-size: 14px;
	height: 32px;
	order: 3;
}
.el-table ::v-deep .el-table__body-wrapper tbody tr td .view:hover {
	opacity: 0.8;
}
.el-table ::v-deep .el-table__body-wrapper tbody tr td .edit {
	border: 0;
	cursor: pointer;
	border-radius: 6px;
	padding: 0 10px;
	margin: 0 5px 5px 0;
	outline: none;
	color: #FA6B04;
	background: none;
	width: auto;
	font-size: 14px;
	height: 32px;
	order: 3;
}
.el-table ::v-deep .el-table__body-wrapper tbody tr td .edit:hover {
	opacity: 0.8;
}
.el-table ::v-deep .el-table__body-wrapper tbody tr td .del {
	border: 0;
	cursor: pointer;
	border-radius: 6px;
	padding: 0 10px;
	margin: 0 5px 5px 0;
	outline: none;
	color: #FA6B04;
	background: none;
	width: auto;
	font-size: 14px;
	height: 32px;
	order: 3;
}
.el-table ::v-deep .el-table__body-wrapper tbody tr td .del:hover {
	opacity: 0.8;
}
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
	border-radius: 8px 8px 8px 8px;
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
	border-radius: 8px 8px 8px 8px;
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
	border-radius: 8px 8px 8px 8px;
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
	border-radius: 8px 8px 8px 8px;
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
	border-radius: 8px 8px 8px 8px;
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
	border-radius: 8px 8px 8px 8px;
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
	border-radius: 8px 8px 8px 8px;
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
</style>
