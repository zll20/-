  
<template>
	<div class="main-content" :style='{"minHeight":"100vh","width":"100%","padding":"15px 30px 30px 30px","fontSize":"16px","height":"auto"}'>
		<!-- 列表页 -->
		<template v-if="showFlag ">
			<el-form class="center-form-pv" :style='{"width":"100%","padding":"0","margin":"0 0 30px 0","fontSize":"inherit","borderRadius":"8px","background":"non"}' :inline="true" :model="searchForm">
				<el-row :style='{"padding":"20px 10px 0px","boxShadow":"none","borderColor":"#00000050","borderRadius":"0px","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","fontSize":"inherit","position":"relative","borderStyle":"solid"}' v-if="!forumChild">
					<div  :style='{"margin":"0 1% 20px 0","fontSize":"inherit","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"#333333","display":"inline-block","lineHeight":"41px","fontSize":"inherit","fontWeight":"500","height":"41px"}' class="item-label">帖子标题</label>
						<el-input v-model="searchForm.title" placeholder="帖子标题" @keydown.enter.native="search()" clearable></el-input>
					</div>
					<div :style='{"margin":"0 1% 20px 0","fontSize":"inherit","display":"flex"}' class="select">
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"#333333","display":"inline-block","lineHeight":"41px","fontSize":"inherit","fontWeight":"500","height":"41px"}' class="item-label">是否通过</label>
						<el-select clearable v-model="searchForm.sfsh" placeholder="是否通过">
							<el-option v-for="(item,index) in sfshOptions" v-bind:key="index" :label="item" :value="item"></el-option>
						</el-select>
					</div>
					<el-button class="search" type="success" @click="search()">
						<span class="icon iconfont icon-fangdajing01" :style='{"margin":"0 0px","fontSize":"16px","color":"#fff","display":"none","height":"40px"}'></span>
						查询
					</el-button>
				</el-row>
				<el-row :style='{"padding":"20px 10px 0px","boxShadow":"none","borderColor":"#00000050","borderRadius":"0px","flexWrap":"wrap","borderWidth":"0","display":"flex","width":"100%","fontSize":"inherit","position":"relative","borderStyle":"solid"}' v-if="forumChild">
					<el-button :style='{"border":"0","cursor":"pointer","padding":"0px 24px","outline":"none","color":"#fff","borderRadius":"24px","background":"#FA6B04","width":"176px","fontSize":"16px","fontWeight":"bold","height":"41px"}' type="success" @click="search()">返回</el-button>
				</el-row>

				<el-row class="actions" :style='{"width":"calc(100% + 8px)","padding":"0px","margin":"0px 0 0px -4px","flexWrap":"wrap","justifyContent":"0","display":"flex"}'>
					<el-button class="add" v-if="isAuth('forum','新增')" type="success" @click="addOrUpdateHandler()">
						<span class="icon iconfont icon-tianjia17" :style='{"margin":"0 0px","fontSize":"16px","color":"#FA6B04","height":"auto"}'></span>
						添加
					</el-button>
					<el-button class="del" v-if="isAuth('forum','删除')" :disabled="dataListSelections.length?false:true" type="danger" @click="deleteHandler()">
						<span class="icon iconfont icon-shanchu6" :style='{"margin":"0 0px","fontSize":"16px","color":"#FA6B04","height":"auto"}'></span>
						删除
					</el-button>


					<el-button class="btn18" v-if="isAuth('forum','审核')" :disabled="dataListSelections.length?false:true" type="success" @click="shBatchDialog()">
						<span class="icon iconfont icon-shenhe9" :style='{"margin":"0 0px","fontSize":"16px","color":"#FA6B04","height":"auto"}'></span>
						审核
					</el-button>
				</el-row>
			</el-form>
			<div :style='{"width":"100%","padding":"20px ","borderRadius":"8px","background":"#fff","borderWidth":"0"}'>
				<el-table class="tables"
					:stripe='false'
					:style='{"padding":"0","borderColor":"#e7e8fc","borderRadius":"10px","borderWidth":"0px 0 0 0px","background":"none","width":"100%","borderStyle":"solid"}' 
					:border='false'
					v-if="isAuth('forum','查看')"
					:data="dataList"
					v-loading="dataListLoading"
					@selection-change="selectionChangeHandler">
					<el-table-column :resizable='true' type="selection" align="center" width="50"></el-table-column>
					<el-table-column :resizable='true' :sortable='true' label="序号" type="index" width="50" />
					<el-table-column :resizable='true' :sortable='true'
												v-if="!forumChild"
						prop="title"
						label="帖子标题">
						<template slot-scope="scope">
							{{scope.row.title}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' v-if="forumChild" prop="content" label="帖子内容">
						<template slot-scope="scope">
							<span class="ql-snow ql-editor" v-html="scope.row.content"></span>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="username"
						label="用户名">
						<template slot-scope="scope">
							{{scope.row.username}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												v-if="!forumChild"
						prop="isdone"
						label="状态">
						<template slot-scope="scope">
							{{scope.row.isdone}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' v-if="!forumChild" prop="istop" label="是否置顶">
						<template slot-scope="scope">
							<el-switch
								v-model="scope.row.istop"
								:width="36"
								active-icon-class="el-icon-edit"
								inactive-icon-class="el-icon-share"
								active-text="开"
								inactive-text="关"
								:active-value="1"
								:inactive-value="0"
								active-color="#FA6B04"
								inactive-color="#ccc"
								@change="(e)=>forumistopChange(e,scope.row)">
							</el-switch>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' v-if="!forumChild" prop="toptime" label="置顶时间">
						<template slot-scope="scope">
							{{scope.row.toptime}}
						</template>
					</el-table-column>
					<el-table-column  v-if="!forumChild"  :resizable='true' prop="cover" width="200" label="封面">
						<template slot-scope="scope">
							<div v-if="scope.row.cover">
								<img v-if="scope.row.cover.substring(0,4)=='http'&&scope.row.cover.split(',w').length>1" :src="scope.row.cover" width="100" height="100" style="object-fit: cover" @click="imgPreView(scope.row.cover)">
								<img v-else-if="scope.row.cover.substring(0,4)=='http'" :src="scope.row.cover.split(',')[0]" width="100" height="100" style="object-fit: cover" @click="imgPreView(scope.row.cover.split(',')[0])">
								<img v-else :src="$base.url+scope.row.cover.split(',')[0]" width="100" height="100" style="object-fit: cover" @click="imgPreView($base.url+scope.row.cover.split(',')[0])">
							</div>
							<div v-else>无图片</div>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' v-if="!forumChild" prop="isanon" label="是否匿名">
						<template slot-scope="scope">
							{{ scope.row.isanon==1?'是':'否' }}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="shhf" label="审核回复" show-overflow-tooltip>
						<template slot-scope="scope">
							<div style="white-space: nowrap;">{{scope.row.shhf}}</div>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="sfsh" label="审核状态">
						<template slot-scope="scope">
							<el-tag v-if="scope.row.sfsh=='否'" type="danger">未通过</el-tag>
							<el-tag v-if="scope.row.sfsh=='待审核'" type="warning">待审核</el-tag>
							<el-tag v-if="scope.row.sfsh=='是'" type="success">通过</el-tag>
						</template>
					</el-table-column>
					
					<el-table-column width="300" label="操作">
						<template slot-scope="scope">
							<el-button class="edit" v-if="!forumChild &&  isAuth('forum','修改')  && scope.row.sfsh=='待审核' " type="success" @click="addOrUpdateHandler(scope.row.id)">
								<span class="icon iconfont icon-xiugai13" :style='{"margin":"0 0px","fontSize":"14px","color":"rgba(255, 140, 0, 1)","display":"none","height":"40px"}'></span>
								修改
							</el-button>

							<el-button v-if="!forumChild||(!forumChild2&&forumChild)" class="view" type="success" @click="search(scope.row.id,'child')">
								<span class="icon iconfont icon-chakan2" :style='{"margin":"0 0px","fontSize":"14px","color":"#333","display":"none","height":"40px"}'></span>
								查看评论
							</el-button>



							<el-button class="del" v-if="isAuth('forum','删除')" type="primary" @click="deleteHandler(scope.row.id)">
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
		<!-- 添加/修改页面  将父组件的search方法传递给子组件-->
		<add-or-update v-if="addOrUpdateFlag" :parent="this" ref="addOrUpdate"></add-or-update>


		
		<el-dialog :title="this.batchIds.length>1?'批量审核':'审核'" :visible.sync="sfshBatchVisiable" width="50%">
			<el-form ref="shBatchForm" :model="shBatchForm" :rules="shRules" label-width="80px">
				<el-form-item label="审核状态" prop="sfsh">
					<el-select v-model="shBatchForm.sfsh" placeholder="审核状态">
						<el-option label="通过" value="是"></el-option>
						<el-option label="不通过" value="否"></el-option>
						<el-option label="待审核" value="待审核"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="内容" prop="shhf">
					<el-input type="textarea" :rows="8" v-model="shBatchForm.shhf"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="sfshBatchVisiable=false">取 消</el-button>
				<el-button type="primary" @click="shBatchHandler">确 定</el-button>
			</span>
		</el-dialog>



		<el-dialog title="预览图" :visible.sync="previewVisible" width="50%">
			<img :src="previewImg" alt="" style="width: 100%;">
		</el-dialog>
	</div>
</template>

<script>
	import * as echarts from 'echarts'
	import chinaJson from "@/components/echarts/china.json";
	import axios from 'axios';
	import AddOrUpdate from "./add-or-update";
	import {
		Loading
	} from 'element-ui';
	export default {
		data() {
			return {
				indexQueryCondition: '',
				searchForm: {
					key: ""
				},
				form:{},
				sfshOptions: [],
				forumChild: false,
				forumChild2: false,
				parentid: 0,
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				showFlag: true,
				sfshVisiable: false,
				shForm: {},
				sfshBatchVisiable: false,
				shBatchForm: {
					sfsh:'',
					shhf:''
				},
				shRules: {
					sfsh:[{ required: true, message: '审核状态不能为空', trigger: 'blur' },],
				},
				batchIds:[],
				shList: [],
				addOrUpdateFlag:false,
				layouts: ["total","prev","pager","next","sizes","jumper"],
				previewImg: '',
				previewVisible: false,
			};
		},
		created() {
			if(this.statType) {
				return false
			}
			this.init();
			this.getDataList();
		},
		mounted() {
		},
		watch: {
		},
		filters: {
			htmlfilter: function (val) {
				return val.replace(/<[^>]*>/g).replace(/undefined/g,'');
			}
		},
		computed: {
			tablename(){
				return this.$storage.get('sessionTable')
			},
			role(){
				return this.$storage.get('role')
			},
		},
		components: {
			AddOrUpdate,
		},
		methods: {
			queryChange(arr){
				for(let x in arr) {
					if(arr[x] == this.role) {
						return true
					}
				}
				return false
			},
			imgPreView(url){
				this.previewImg = url
				this.previewVisible = true
				
			},
			init () {
				this.sfshOptions = "是,否,待审核".split(',');
			},
			search(id,flag) {
				if(id!='' && id!=undefined) {
					if(this.forumChild) {
						this.forumChild2 = true
					}else {
						this.parentid = id
					}
					this.forumChild = true;
					
				} else {
					if(this.forumChild2) {
						this.forumChild2 = false
					}else {
						this.forumChild = false;
						this.parentid = 0
					}
					
				}
				this.pageIndex = 1;
				this.getDataList(id,flag);
			},

			forumistopChange(e,row){
				if(e==1){
					row.toptime = this.getCurDateTime()
				}else {
					row.toptime = row.addtime
				}
				
				this.$http({
					url:'forum/update',
					method:'post',
					data:row
				}).then(res=>{})
			},
			getDataList(id,flag) {
				this.dataListLoading = true;
				let params = {
					page: this.pageIndex,
					limit: this.pageSize,
					parentid : 0,
					sort: 'id',
				}
				if(this.searchForm.title!='' && this.searchForm.title!=undefined){
					params['title'] = '%' + this.searchForm.title + '%'
				}
				if(this.searchForm.typename!='' && this.searchForm.typename!=undefined){
					params['typename'] = this.searchForm.typename
				}
				if(this.searchForm.sfsh!='' && this.searchForm.sfsh!=undefined){
					params['sfsh'] = this.searchForm.sfsh
				}
				if(id!='' && id!=undefined){
					params['parentid'] = id;
				}else if(this.parentid) {
					params['parentid'] = this.parentid;
				}
				this.$http({
					url: "forum/page",
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
			// 添加/修改
			addOrUpdateHandler(id,type) {
				this.showFlag = false;
				this.addOrUpdateFlag = true;
				this.crossAddOrUpdateFlag = false;
				if(type!='info'&&type!='msg'){
					type = 'else';
				}
				this.$nextTick(() => {
					this.$refs.addOrUpdate.init(id,type );
				});
			},
			//批量审核窗口
			shBatchDialog(){
				for(let x in this.dataListSelections){
					if(this.dataListSelections[x].sfsh&&this.dataListSelections[x].sfsh!='待审核'){
						this.$message.error('存在已审核数据，不能继续操作');
						this.batchIds = []
						return false
					}
					this.batchIds.push(this.dataListSelections[x].id)
				}
				this.shList = this.dataListSelections
				this.sfshBatchVisiable = true
			},
			//批量审核
			shBatchHandler(){
				this.$refs["shBatchForm"].validate(valid => {
					if(valid){
						this.$confirm(`是否${this.batchIds.length>1?'一键审核':'审核'}选中数据?`, "提示", {
							confirmButtonText: "确定",
							cancelButtonText: "取消",
							type: "warning"
						}).then(async() => {
							this.$http({
								url: "forum/shBatch?sfsh="+this.shBatchForm.sfsh+"&shhf="+this.shBatchForm.shhf,
								method: "post",
								data: this.batchIds
							}).then(async ({ data }) => {
								if (data && data.code === 0) {
									if(this.shBatchForm.sfsh!='待审核'){
										for(let x in this.shList){
										}
									}
									this.$message({
										message: "操作成功",
										type: "success",
										duration: 1500,
										onClose: () => {
											this.getDataList();
											this.sfshBatchVisiable = false
											this.batchIds = []
										}
									});
								} else {
									this.$message.error(data.msg);
								}
							});
						});
					}
				})
			},
			// 删除
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
						url: "forum/delete",
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

	};
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
	
	.center-form-pv .actions .statis {
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
	
	.center-form-pv .actions .statis:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .btn18 {
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
	
	.center-form-pv .actions .btn18:hover {
		opacity: 0.8;
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
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .add {
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .add:hover {
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
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .btn8 {
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
		order: 5;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .btn8:hover {
		opacity: 0.8;
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
	
	// list one
	.one .list1-view {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #dc3dfa;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-view:hover {
		opacity: 0.8;
	}
	
	.one .list1-edit {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #dc3dfa;
		background: #fbe5fe;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-edit:hover {
		opacity: 0.8;
	}
	
	.one .list1-del {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #f00;
		background: #ffefed;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-del:hover {
		opacity: 0.8;
	}
	
	.one .list1-btn8 {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #7b4ffb;
		width: auto;
		font-size: 14px;
		height: 32px;
		order: 11;
	}
	
	.one .list1-btn8:hover {
		opacity: 0.8;
	}
	
	.main-content .el-table .el-switch {
		display: inline-flex;
		vertical-align: middle;
		line-height: 30px;
		position: relative;
		align-items: center;
		height: 30px;
	}
	.main-content .el-table .el-switch ::v-deep .el-switch__label--left {
		cursor: pointer;
		margin: 0 10px 0 0;
		color: #333;
		font-weight: 500;
		display: none;
		vertical-align: middle;
		font-size: 16px;
		transition: .2s;
		height: 30px;
	}
	.main-content .el-table .el-switch ::v-deep .el-switch__label--right {
		cursor: pointer;
		margin: 0 0 0 10px;
		color: #333;
		font-weight: 500;
		display: none;
		vertical-align: middle;
		font-size: 16px;
		transition: .2s;
		height: 30px;
	}
	.main-content .el-table .el-switch ::v-deep .el-switch__core {
		border: 1px solid #ff8b00;
		cursor: pointer;
		border-radius: 15px;
		margin: 0;
		outline: 0;
		background: #ff8b00;
		display: inline-block;
		width: 36px;
		box-sizing: border-box;
		transition: border-color .3s,background-color .3s;
		height: 18px;
	}
	.main-content .el-table .el-switch ::v-deep .el-switch__core::after {
		border-radius: 100%;
		top: 1px;
		left: 2px;
		background: #FFF;
		width: 14px;
		position: absolute;
		transition: all .3s;
		height: 14px;
	}
	.main-content .el-table .el-switch.is-checked ::v-deep .el-switch__core::after {
		margin: 0 0 0 -16px;
		left: 100%;
	}
	
	.main-content .el-table .el-rate ::v-deep .el-rate__item {
		cursor: pointer;
		display: inline-block;
		vertical-align: middle;
		font-size: 0;
		position: relative;
	}
	.main-content .el-table .el-rate ::v-deep .el-rate__item .el-rate__icon {
		margin: 0 3px;
		display: inline-block;
		font-size: 18px;
		position: relative;
		transition: .3s;
	}

	.chartDialog ::v-deep .el-dialog {
		background: #fff;
	}
</style>

