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
					<el-button class="search" :style='{"border":"0","cursor":"pointer","padding":"0px 24px","outline":"none","color":"#fff","borderRadius":"24px","background":"#FA6B04","width":"176px","fontSize":"16px","fontWeight":"bold","height":"41px"}' type="success" @click="search()">
						<span class="icon iconfont icon-fangdajing01" :style='{"margin":"0 0px","fontSize":"16px","color":"#fff","display":"none","height":"40px"}'></span>
						查询
					</el-button>
				</el-row>
				<el-row class="actions" :style='{"width":"calc(100% + 8px)","padding":"0px","margin":"0px 0 0px -4px","flexWrap":"wrap","justifyContent":"0","display":"flex"}'>
					<download-excel v-if="isAuth('examrecord','导出')" class = "export-excel-wrapper" :data = "dataList" :fields = "json_fields" name = "考试记录.xls">
						<!-- 导出excel -->
						<el-button class="btn18" type="success">
							<span class="icon iconfont icon-xiazai7" :style='{"margin":"0 0px","fontSize":"16px","color":"#FA6B04","height":"auto"}'></span>
							导出
						</el-button>
					</download-excel>
					<el-button class="btn18" v-if="isAuth('examrecord','打印')" type="success" @click="printJson">
						<span class="icon iconfont icon-dayin2" :style='{"margin":"0 0px","fontSize":"16px","color":"#FA6B04","height":"auto"}'></span>
						打印
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
					<el-table-column :resizable='true' :sortable='true' prop="examno" header-align="center" align="center" sortable label="考试编号"></el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="username" header-align="center" align="center" sortable label="姓名"></el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="papername"
						header-align="center"
						align="center"
						sortable
						label="试卷名称"
					></el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="myscore"
						header-align="center"
						align="center"
						sortable
						label="考试得分"
					>
						<template slot-scope="scope">
							<el-tag v-if="scope.row.myscore==0&&scope.row.ismark==0" type="danger">{{scope.row.myscore}}</el-tag>
							<el-tag v-else-if="scope.row.myscore>0&&scope.row.ismark==0" type="success">{{scope.row.myscore}}</el-tag>
							<el-tag v-else-if="scope.row.ismark>0" type="warning">批阅中</el-tag>
						</template>
					</el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="accuracy"
						header-align="center"
						align="center"
						sortable
						label="准确率"
					>
						<template slot-scope="scope">
							<el-tag type="success" v-if="scope.row.ismark==0">{{(scope.row.accuracy * 100).toFixed(0)}}%</el-tag>
							<el-tag type="warning" v-if="scope.row.ismark>0">/</el-tag>
						</template>
					</el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="accuracy"
						header-align="center"
						align="center"
						sortable
						label="错误率"
					>
						<template slot-scope="scope">
							<el-tag type="danger" v-if="scope.row.ismark==0">{{((1 - Number(scope.row.accuracy)) * 100).toFixed(0)}}%</el-tag>
							<el-tag type="warning" v-if="scope.row.ismark>0">/</el-tag>
						</template>
					</el-table-column>
					<el-table-column
						header-align="center"
						align="center"
						width="150"
						label="操作"
					>
						<template slot-scope="scope">
							<el-button class="view" :style='{"border":"0","cursor":"pointer","padding":"0 10px","margin":"0 5px 5px 0","outline":"none","color":"#FA6B04","borderRadius":"6px","background":"none","width":"auto","fontSize":"14px","height":"32px","order":"3"}' type="success" @click="addOrUpdateHandler(scope.row)">
								<span class="icon iconfont icon-chakan2" :style='{"margin":"0 0px","fontSize":"14px","color":"#333","display":"none","height":"40px"}'></span>
								详情
							</el-button>
							<el-button class="del" :style='{"border":"0","cursor":"pointer","padding":"0 10px","margin":"0 5px 5px 0","outline":"none","color":"#FA6B04","borderRadius":"6px","background":"none","width":"auto","fontSize":"14px","height":"32px","order":"3"}' v-if="isAuth('examrecord','删除')" type="success" @click="deleteHandler(scope.row)">
								<span class="icon iconfont icon-shanchu6" :style='{"margin":"0 0px","fontSize":"14px","color":"rgba(220, 38, 38, 1)","display":"none","height":"40px"}'></span>
								删除
							</el-button>
							<el-button class="edit" :style='{"border":"0","cursor":"pointer","padding":"0 10px","margin":"0 5px 5px 0","outline":"none","color":"#FA6B04","borderRadius":"6px","background":"none","width":"auto","fontSize":"14px","height":"32px","order":"3"}' v-if="isAuth('examrecord','批卷')&&scope.row.ismark>0" type="primary" @click="gradeClick(scope.row)">
								<span class="icon iconfont icon-xiugai13" :style='{"margin":"0 0px","fontSize":"14px","color":"rgba(255, 140, 0, 1)","display":"none","height":"40px"}'></span>
								批卷
							</el-button>
						</template>
					</el-table-column>
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
		<add-or-update v-if="showFlag" :parent="this" ref="addOrUpdate"></add-or-update>
		<el-dialog title="批卷" :visible.sync="gradeVisible" fullscreen>
			<el-card v-for="(item,index) in gradeList" :key="index" style="width: 90%;margin: 10px auto">
				<div style="padding: 20px;box-sizing:border-box;border-bottom:1px solid #eee">
					{{index + 1}}、<span class="ql-snow ql-editor" v-html="item.questionname"></span> （ {{item.score}} ）					<el-tag type="success" v-if="item.type==0">单选题</el-tag>
					<el-tag type="warning" v-if="item.type==1">多选题</el-tag>
					<el-tag type="info" v-if="item.type==2">判断题</el-tag>
					<el-tag type="primary" v-if="item.type==3">填空题</el-tag>
					<el-tag type="danger" v-if="item.type==4">主观题</el-tag>
				</div>
				<div style="padding: 10px;box-sizing:border-box">
					考生答案：{{item.myanswer}}
				</div>
				<div style="padding: 10px;box-sizing:border-box" v-if="item.type!=4">
					正确答案：{{item.answer}}
				</div>
				<div style="padding: 20px;box-sizing:border-box">
					解析：{{item.analysis}}
				</div>
				<div style="padding: 20px;box-sizing:border-box;display:flex;align-items:center" v-if="item.type==4">
					评分：<el-input-number v-model="item.myscore" :min="0" :max="item.score"></el-input-number>
				</div>
			</el-card>
			<span slot="footer" class="dialog-footer">
				<el-button @click="gradeVisible=false">取 消</el-button>
				<el-button type="primary" @click="gradeSave">确 定</el-button>
			</span>
		</el-dialog>
	</div>
</template>
<script>
	import AddOrUpdate from "./add-or-update";
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
				//导出excel
				json_fields: {
					"姓名": "username",    //常规字段
					"试卷名称": "papername",    //常规字段
					"总分": "myscore",    //常规字段
				},
				json_meta: [
					[
						{
							" key ": " charset ",
							" value ": " utf- 8 "
						}
					]
				],
				gradeList:[],
				gradeVisible:false
			};
		},
		mounted() {
			this.init();
			this.getDataList();
		},
		components: {
			AddOrUpdate
		},
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
					limit: this.pageSize
				};
				if (this.searchForm.papername) {
					params["papername"] = `%${this.searchForm.papername}%`;
				}
			  this.$http({
					url: this.$api.examrecordgroupby,
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
			addOrUpdateHandler(row) {
				this.showFlag = true;
				this.$nextTick(() => {
					this.$refs.addOrUpdate.init(row);
				});
			},
			async printJson() {
				//通过getdata调用后台接口获取数据封装到res
				this.list = this.dataList;
				let data = []
				for (let i=0; i < this.list.length; i++) {
					data.push({
						username: this.list[i].username,
						papername: this.list[i].papername,
						myscore: this.list[i].myscore,
					})
				}
				printJS({
					printable: data,
					properties: [
						{
							field: 'username',
							displayName: '姓名',
							columnSize: 1
						},
						{
							field: 'papername',
							displayName: '试卷名称',
							columnSize: 1
						},
						{
							field: 'myscore',
							displayName: '总分',
							columnSize: 1
						},
					],
					type: 'json',
					header: '考试记录',
					// 样式设置
					gridStyle: 'border: 2px solid #3971A5;',
					gridHeaderStyle: 'color: red;  border: 2px solid #3971A5;'
				})
			},
			// 批卷
			gradeClick(row) {
				this.$http({
					url: `${this.$api.examrecordpage}`,
					method: 'get',
					params: {
						page:1,
						limit: 100,
						paperid: row.paperid,
						userid: row.userid,
						sort: 'addtime',
						order: 'asc',
						examno: row.examno
					}
				}).then(({data})=>{
					if(data&&data.code==0){
						for(let x in data.data.list){
							if(data.data.list[x].type==4){
								data.data.list[x].myscore = 0
							}
							data.data.list[x].questionname = data.data.list[x].questionname.replace(/img src/gi,"img style=\"width:100%;\" src");
						}
						this.gradeList = data.data.list
						this.gradeVisible = true
					}
				})
			},
			async gradeSave(){
				for(let i in this.gradeList){
					await this.updaterecord(this.gradeList[i])
				}
				this.$message({
					message: "批卷成功",
					type: "success",
					duration: 1500,
					onClose: () => {
						this.getDataList()
						this.gradeVisible = false
					}
				});
			},
			updaterecord(item){
				item.ismark = 1
				this.$http({
					url: `${this.$api.examrecordupdate}`,
					method: 'post',
					data: item
				}).then(({data})=>{})
			},
			deleteHandler(row){
				this.$confirm(`确定删除此考试记录？`, "提示", {
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning"
				}).then(()=>{
					this.$http({
						url: `examrecord/deleteRecords?userid=${row.userid}&paperid=${row.paperid}&examno=${row.examno}`,
						method: "post",
						data: {}
					}).then(({ data }) => {
						this.$message.success('删除成功')
						this.getDataList()
					});
				})
			},
		}
	};
</script>
<style lang="scss" scoped>
	//导出excel
	.export-excel-wrapper{
		display: inline-block;
	}
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
</style>

