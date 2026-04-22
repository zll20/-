const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const base = getApp().base
const validate = getApp().validate
const des = getApp().des
Page({
	data: {
		baseurl: base.base.url,
		ruleForm: {},
		ro: {},
		tableName: '',
		xueshengxingbieOptions: [],
		xueshengxingbieIndex: 0,
	},
	async onLoad() {
		let table = wx.getStorageSync("nowTable");
		await this.getSession()
		let that = this
		let ruleForm = this.data.ruleForm
		let ro = {}
		if(table=='xuesheng'){
			let xueshengxingbieOptions = "男,女".split(',');
			let xueshengxingbieIndex = 0
			xueshengxingbieOptions.forEach((item, index) => {
				if(item==ruleForm.xingbie) {
					xueshengxingbieIndex = index;
				}
			});
			this.setData({
				xueshengxingbieOptions: xueshengxingbieOptions,
				xueshengxingbieIndex: xueshengxingbieIndex
			})
			ro = {
				zhanghao: true,
				nicheng: false,
				xingming: false,
				xingbie: false,
				touxiang: false,
				youxiang: false,
				mobile: false,
			}
		}
		this.setData({
			tableName: table,
			ro: ro,
		})
		
	},
	getSession(){
		return new Promise((resolve) => {
			api.get(`${wx.getStorageSync("nowTable")}/session`).then(rs=>{
				this.setData({
					ruleForm: rs.data
				})
				wx.setStorageSync('userSession',JSON.stringify(rs.data))
				resolve()
			})
		})
	},
	selChange(e) {
		this.setData({
			[`ruleForm.${e.target.dataset.columnname}`]: this.data[`${e.target.dataset.tablename}${e.target.dataset.columnname}Options`][e.detail.value],
			[`${e.target.dataset.tablename}${e.target.dataset.columnname}Index`]: e.detail.value
		})
	},
	selChange2(e) {
		this.data.ruleForm[`${e.target.dataset.columnname}`] = this.data[`${e.target.dataset.tablename}${e.target.dataset.columnname}Options`][e.detail.value]
		api.get(`follow/${e.target.dataset.reftable}/${e.target.dataset.refcolumn}`,{
			columnValue: this.data[`${e.target.dataset.tablename}${e.target.dataset.columnname}Options`][e.detail.value]
		}).then((res)=>{
			if(this.data.tableName == 'xuesheng'){
			}
			this.setData({
				ruleForm: this.data.ruleForm,
				[`${e.target.dataset.tablename}${e.target.dataset.columnname}Index`]: e.detail.value
			})
		})
	},
	mulSelShow(e) {
		if(this.data.ro[e.target.dataset.columnname]) {
			return false
		}
		this.setData({
			[`${e.target.dataset.tablename}${e.target.dataset.columnname}Show`]: true
		})
	},
	mulSelConfirm(e){
		this.setData({
			[`${e.target.dataset.tablename}${e.target.dataset.columnname}Show`]: false,
			[`ruleForm.${e.target.dataset.columnname}`]: e.detail.join(','),
			[`${e.target.dataset.tablename}${e.target.dataset.columnname}s`]: e.detail
		})
	},
	mulSelCancel(e){
		this.setData({
			[`${e.target.dataset.tablename}${e.target.dataset.columnname}Show`]: false
		})
	},
	dateTimeShow(e) {
		if(this.data.ro[e.target.dataset.columnname]) {
			return false
		}
		var name = e.target.dataset.tablename + e.target.dataset.columnname + 'Show'
		this.setData({
			[name]:true
		})
	},
	dateTimeChange(e) {
		var name = e.target.dataset.tablename + e.target.dataset.columnname
		this.setData({
			[`ruleForm.${e.target.dataset.columnname}`]: e.detail.date,
			[`${name}Show`]: false
		})
	},
	dateTimeCancel(e){
		var name = e.target.dataset.tablename + e.target.dataset.columnname
		this.setData({
			[`${name}Show`]: false
		})
	},
	dateChange(e) {
		this.setData({
			[`ruleForm.${e.target.dataset.columnname}`]: e.detail.value
		})
	},
	uploadTap(e) {
		if(this.data.ro[e.currentTarget.dataset.columnname]) {
			return false
		}
		api.upload(e.currentTarget.dataset.mediatype).then((res)=>{
			// 修复：确保路径以 upload/ 开头，且不重复
			let filePath = res.file;
			if(!filePath.startsWith('upload/') && !filePath.startsWith('http')) {
				filePath = 'upload/' + filePath;
			}
			this.setData({
				[`ruleForm.${e.currentTarget.dataset.columnname}`]: filePath
			})
		})
	},
	bindKeyInput(e) {
		this.setData({
			[`ruleForm.${e.target.dataset.name}`]:e.detail.value
		})
	},
	editorChange(e) {
		this.setData({
			[`ruleForm.${e.target.dataset.name}`]:e.detail
		})
	},
	async update() {
		let tableName = this.data.tableName
		let ruleForm = this.data.ruleForm
		if(`xuesheng` == tableName && ruleForm.youxiang&&(!validate.isEmail(ruleForm.youxiang))){
			utils.msg(`邮箱应输入邮件格式`);
			return
		}
		if(`xuesheng` == tableName && ruleForm.mobile&&(!validate.isMobile(ruleForm.mobile))){
			utils.msg(`手机号应输入手机格式`);
			return
		}
		api.post(`${tableName}/update`,ruleForm).then(rs=>{
			utils.msgBack('修改成功')
			// 更新本地存储的用户信息，确保头像实时生效
			wx.setStorageSync('userSession', JSON.stringify(ruleForm));
			this.getSession()
		})
	},
})