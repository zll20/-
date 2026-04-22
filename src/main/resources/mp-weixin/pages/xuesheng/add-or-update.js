  
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
		cross:'',
		baseurl: base.base.url,
		isAuth: [],
		userid: '',
		ruleForm: {


			xuehao: '',


			mima: '',


			xingming: '',


			xingbie: '',


			touxiang: '',


			youxiang: '',


			mobile: '',


			pquestion: '',


			panswer: '',
		},
		xingbieOptions: [],
		xingbieIndex: 0,
		// 登录用户信息
		user: {},
		ro:{
			xuehao : false,
			mima : false,
			xingming : false,
			xingbie : false,
			touxiang : false,
			youxiang : false,
			mobile : false,
			pquestion : false,
			panswer : false,
		},
		showType: 1,
	},
	computed:{



	},
	async onLoad(options) {
		let user = {}
		await api.get(`${wx.getStorageSync("nowTable")}/session`).then(rs=>{
			user = rs.data
		})
		let ruleForm = this.data.ruleForm
		let ro = this.data.ro
		// ss读取

		// 自定义下拉框值
		let xingbieOptions = "男,女".split(',')
		xingbieOptions.unshift("请选择性别");
		let xingbieIndex = 0
		if(ruleForm.xingbie) {
			for(let x in xingbieOptions) {
				if(xingbieOptions[x] == ruleForm.xingbie) {
					xingbieIndex = Number(x)
				}
			}
		}
		this.setData({
			xingbieOptions: xingbieOptions,
			xingbieIndex: xingbieIndex
		})
		this.data.ruleForm.userid = wx.getStorageSync("appUserid")
		if (options.refid) {
			// 如果上一级页面传递了refid，获取改refid数据信息
			this.data.ruleForm.refid = Number(options.refid);
			this.data.ruleForm.nickname = wx.getStorageSync("nickname");
		}
		// 如果是更新操作
		if (options.id) {
			// 获取信息
			await api.get('xuesheng/info/' + options.id).then(res=>{
				ruleForm = res.data;
			})
		}
		this.data.cross = options.cross||false;
		if(options.cross){
			var obj = wx.getStorageSync('crossObj');
			for (var o in obj){
				if(o=='xuehao'){
					ruleForm.xuehao = obj[o];
					ro.xuehao = true;
					continue;
				}
				if(o=='mima'){
					ruleForm.mima = obj[o];
					ro.mima = true;
					continue;
				}
				if(o=='xingming'){
					ruleForm.xingming = obj[o];
					ro.xingming = true;
					continue;
				}
				if(o=='xingbie'){
					ruleForm.xingbie = obj[o];
					for(let x in this.data.xingbieOptions) {
						if(this.data.xingbieOptions[x] == ruleForm.xingbie) {
							this.setData({
								xingbieIndex: Number(x)
							})
						}
					}
					ro.xingbie = true;
					continue;
				}
				if(o=='touxiang'){
					ruleForm.touxiang = obj[o].split(",")[0];
					ro.touxiang = true;
					continue;
				}
				if(o=='youxiang'){
					ruleForm.youxiang = obj[o];
					ro.youxiang = true;
					continue;
				}
				if(o=='mobile'){
					ruleForm.mobile = obj[o];
					ro.mobile = true;
					continue;
				}
				if(o=='pquestion'){
					ruleForm.pquestion = obj[o];
					ro.pquestion = true;
					continue;
				}
				if(o=='panswer'){
					ruleForm.panswer = obj[o];
					ro.panswer = true;
					continue;
				}
			}
		}
		this.setData({
			user: user,
			ro: ro,
			ruleForm: ruleForm,
			userid: wx.getStorageSync("appUserid"),
		})
		if (wx.getStorageSync('raffleType') && wx.getStorageSync('raffleType') != null) {
			wx.removeStorageSync('raffleType')
			setTimeout(() => {
				this.onSubmitTap(null)
			}, 300)
		}
	},
	// 输入框回调
	bindKeyInput(e) {
		this.setData({
			[`ruleForm.${e.target.dataset.name}`]:e.detail.value
		})
	},
	// 下拉选择
	selChange(e) {
		this.setData({
			[`ruleForm.${e.target.dataset.columnname}`]: this.data[`${e.target.dataset.columnname}Options`][e.detail.value],
			[`${e.target.dataset.columnname}Index`]: e.detail.value
		})
	},
	// 下拉多选
	mulSelShow(e) {
		if(this.data.ro[e.target.dataset.columnname]) {
			return false
		}
		this.setData({
			[`${e.target.dataset.columnname}Show`]: true
		})
	},
	async mulSelConfirm(e){
		let columnname = e.target.dataset.columnname
		this.setData({
			[`${columnname}Show`]: false,
			[`ruleForm.${columnname}`]: e.detail.join(','),
			[`ruleForm.${columnname}s`]: e.detail
		})
	},
	mulSelCancel(e){
		this.setData({
			[`${e.target.dataset.columnname}Show`]: false
		})
	},
	// 日期时间选择
	dateTimeShow(e) {
		if(this.data.ro[e.target.dataset.columnname]) {
			return false
		}
		var name = e.target.dataset.columnname + 'Show'
		this.setData({
			[name]:true
		})
	},
	dateTimeChange(e) {
		var name = e.target.dataset.columnname
		this.setData({
			[`ruleForm.${name}`]: e.detail.date,
			[`${name}Show`]: false
		})
	},
	dateTimeCancel(e){
		var name = e.target.dataset.columnname
		this.setData({
			[`${name}Show`]: false
		})
	},
	// 日期选择
	dateChange(e) {
		this.setData({
			[`ruleForm.${e.target.dataset.columnname}`]: e.detail.value
		})
	},
	// 富文本回调
	editorChange(e) {
		this.setData({
			[`ruleForm.${e.target.dataset.name}`]:e.detail
		})
	},
	// 上传回调
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
	getUUID () {
		return new Date().getTime();
	},
	beforeSubmit() {
		this.onSubmitTap(null)
	},
	async onSubmitTap(subMitType) {
		let that = this
		if(this.data.xingbieIndex==0) {
			this.data.ruleForm.xingbie = ''
		}
		//跨表计算判断
		var obj;
		if(this.data.ruleForm.youxiang&&(!validate.isEmail(this.data.ruleForm.youxiang))){
			utils.msg(`邮箱应输入邮件格式`);
			return
		}
		if(this.data.ruleForm.mobile&&(!validate.isMobile(this.data.ruleForm.mobile))){
			utils.msg(`手机号应输入手机格式`);
			return
		}
		//更新跨表属性
		var crossuserid;
		var crossrefid;
		var crossoptnum;
		var crossoptnum2;
		if(this.data.cross){
			var statusColumnName = wx.getStorageSync('statusColumnName');
			var statusColumnValue = wx.getStorageSync('statusColumnValue');
			if(statusColumnName!='') {
				if(!obj) {
					obj = wx.getStorageSync('crossObj');
				}
				if(!statusColumnName.startsWith("[")) {
					for (var o in obj){
						if(o==statusColumnName){
							obj[o] = statusColumnValue;
						}
					}
					var table = wx.getStorageSync('crossTable');
					await api.post(`${table}/update`,obj).then(rs=>{})
				} else {
					   crossuserid=Number(wx.getStorageSync('appUserid'));
					   crossrefid=obj['id'];
					   crossoptnum=wx.getStorageSync('statusColumnName');
					   crossoptnum=crossoptnum.replace(/\[/,"").replace(/\]/,"");
				}
			}
			//跨表计算
		}
		if(crossrefid && crossuserid) {
			this.data.ruleForm.crossuserid=crossuserid;
			this.data.ruleForm.crossrefid=crossrefid;
			let params = {
				page: 1,
				limit: 10,
				crossuserid: crossuserid,
				crossrefid: crossrefid,
			}
			await api.get('xuesheng/list',params).then(rs=>{
				crossoptnum2 = rs.data.total
			})
		}
		if (crossoptnum2&&crossoptnum2 >= crossoptnum) {
			utils.msg(wx.getStorageSync('tips'));
			wx.removeStorageSync('crossCleanType');
			return false;
		}
		let oet = {}
		let url = 'xuesheng/add'
		if(this.data.ruleForm.id){
			url = 'xuesheng/update'
		}
		await api.post(url, this.data.ruleForm).then(async oet=>{
			if(this.data.cross){
				wx.setStorageSync('crossCleanType',true);
			}
			utils.msgBack('提交成功');
		})
	},
})