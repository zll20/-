const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const base = getApp().base
const validate = getApp().validate
const des = getApp().des
const computedBehavior = require("miniprogram-computed").behavior;
Page({
	behaviors: [computedBehavior],
	data: {
		baseurl: base.base.url,
		ruleForm: {},
		ro: {},
		loginTable: '',
		xueshengxingbieOptions: [],
		xueshengxingbieIndex: 0,
		smscode: "",
		inter: null,
		count: 60,
		isEndFlag: true,
		indexBgUrl: '',
		indexLogoUrl: '',
	},
	computed:{
		SecondToDate(data) {
			var time = data.count;
			time = parseInt(time) + "秒后重发";
			return time;
		}
	},
	onLoad(options) {
		if(options.loginTable) {
			var ruleForm,ro
			if(options.loginTable == 'xuesheng') {
				ruleForm = {
					mima: '',
					password: '',
					password2: '',
					xingming: '',
					xingbie: '',
					touxiang: '',
					youxiang: '',
					mobile: '',
				}
				ro = {
					mima: false,
					xingming: false,
					xingbie: false,
					touxiang: false,
					youxiang: false,
					mobile: false,
				}
				this.xueshengInit()
			}
			
			this.setData({
				ruleForm: ruleForm,
				ro: ro,
				tableName: options.loginTable
			})
		}
		this.styleChange()
	},
	async styleChange() {
		api.get('config/info?name=appRegisterBackgroudImg').then((rs)=>{
			api.get('config/info?name=appRegLogo').then((rs2)=>{
				this.setData({
					indexBgUrl: rs.data&&rs.data.value?(this.data.baseurl + rs.data.value):'',
					indexLogoUrl: rs2.data&&rs2.data.value?(this.data.baseurl + rs2.data.value):'http://codegen.caihongy.cn/20250814/2cb518a34dd04bd1b6c5e52e74d8f581.jpg'
				})
			})
		})
	},
	onShow() {
		
	},
	async xueshengInit() {
		this.data.xueshengxingbieOptions = "男,女".split(',');
		this.setData({
			xueshengxingbieOptions: this.data.xueshengxingbieOptions,
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
		if(this.data.ro[e.currentTarget.dataset.columnname]) {
			return false
		}
		this.setData({
			[`${e.currentTarget.dataset.tablename}${e.currentTarget.dataset.columnname}Show`]: true
		})
	},
	mulSelConfirm(e){
		this.setData({
			[`${e.target.dataset.tablename}${e.target.dataset.columnname}Show`]: false,
			[`ruleForm.${e.target.dataset.columnname}`]: e.detail.join(',')
		})
	},
	mulSelCancel(e){
		this.setData({
			[`${e.target.dataset.tablename}${e.target.dataset.columnname}Show`]: false
		})
	},
	getUUID () {
		return new Date().getTime();
	},
	uploadTap(e) {
		if(this.data.ro[e.currentTarget.dataset.columnname]) {
			return false
		}
		api.upload(e.currentTarget.dataset.mediatype).then((res)=>{
			this.setData({
				[`ruleForm.${e.currentTarget.dataset.columnname}`]: 'upload/' + res.file
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
	bindKeyInput2(e) {
		this.setData({
			[e.target.dataset.name]:e.detail.value
		})
	},
	async sendsmscode(){
		if(!this.data.ruleForm.mobile){
			utils.msg(`手机号码不能为空`);
			return
		}
		if(this.data.ruleForm.mobile&&(!validate.isMobile(this.data.ruleForm.mobile))){
			utils.msg(`请输入正常的手机格式`);
			return
		}
		if (this.data.count > 0) {
			this.setData({
				isEndFlag: false
			})
			var that = this;
			this.data.inter = setInterval(function() {
				that.data.count = that.data.count - 1;
				that.setData({
					count: that.data.count
				})
				if (that.data.count <= 0) {
					clearInterval(that.data.inter);
					that.setData({
						count: 60,
						isEndFlag: true
					})
				}
			}, 1000);
		}
		api.get(`${this.data.tableName}/sendsms`,{
			mobile: this.data.ruleForm.mobile
		}).then((res)=>{
			if (res.code==0) {
				utils.msg(`发送成功`);
			} else {
				utils.msg(res.msg);
				return;
			}
		})
	},
	register() {
		if(`xuesheng` == this.data.tableName){
			if((this.data.ruleForm.password!=this.data.ruleForm.password2)){
				utils.msg(`两次密码输入不一致`);
				return
			}
			if(this.data.ruleForm.youxiang&&(!validate.isEmail(this.data.ruleForm.youxiang))){
				utils.msg(`邮箱应输入邮件格式`);
				return
			}
			if(this.data.ruleForm.mobile&&(!validate.isMobile(this.data.ruleForm.mobile))){
				utils.msg(`手机号应输入手机格式`);
				return
			}
		}
		let registerData = {
			mima: this.data.ruleForm.mima,
			xingming: this.data.ruleForm.xingming,
			xingbie: this.data.ruleForm.xingbie,
			touxiang: this.data.ruleForm.touxiang,
			youxiang: this.data.ruleForm.youxiang,
			mobile: this.data.ruleForm.mobile,
		}
		api.post(`${this.data.tableName}/register?smscode=${this.data.smscode}`,registerData).then((res)=>{
			if(res.code==0) {
				utils.msgBack('注册成功')
			}
		})
	},
})