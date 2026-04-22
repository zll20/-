  
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


			xingming: '',


			xuehao: '',


			riqi: '',


			xuexishizhang: Number('2') ,


			xuexirenwu: '',


			fuxijihua: '',
		},
		// 登录用户信息
		user: {},
		ro:{
			xingming : false,
			xuehao : false,
			riqi : false,
			xuexishizhang : false,
			xuexirenwu : false,
			fuxijihua : false,
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
		ruleForm.xingming = user.xingming
		ro.xingming = true;
		ruleForm.xuehao = user.xuehao
		ro.xuehao = true;

		this.data.ruleForm.userid = wx.getStorageSync("appUserid")
		if (options.refid) {
			// 如果上一级页面传递了refid，获取改refid数据信息
			this.data.ruleForm.refid = Number(options.refid);
			this.data.ruleForm.nickname = wx.getStorageSync("nickname");
		}
		// 如果是更新操作
		if (options.id) {
			// 获取信息
			await api.get('xuexijihua/info/' + options.id).then(res=>{
				ruleForm = res.data;
			})
		}
		this.data.cross = options.cross||false;
		if(options.cross){
			var obj = wx.getStorageSync('crossObj');
			for (var o in obj){
				if(o=='xingming'){
					ruleForm.xingming = obj[o];
					ro.xingming = true;
					continue;
				}
				if(o=='xuehao'){
					ruleForm.xuehao = obj[o];
					ro.xuehao = true;
					continue;
				}
				if(o=='riqi'){
					ruleForm.riqi = obj[o];
					ro.riqi = true;
					continue;
				}
				if(o=='xuexishizhang'){
					ruleForm.xuexishizhang = obj[o];
					ro.xuexishizhang = true;
					continue;
				}
				if(o=='xuexirenwu'){
					ruleForm.xuexirenwu = obj[o];
					ro.xuexirenwu = true;
					continue;
				}
				if(o=='fuxijihua'){
					ruleForm.fuxijihua = obj[o];
					ro.fuxijihua = true;
					continue;
				}
			}
		}
		this.setData({
			user: user,
			ro: ro,
			ruleForm: ruleForm,
			userid: wx.getStorageSync("appUserid"),
			[`ruleForm.riqi`]: utils.getCurDate(),
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
		//跨表计算判断
		var obj;
		if(this.data.ruleForm.xuexishizhang&&(!validate.isNumber(this.data.ruleForm.xuexishizhang))){
			utils.msg(`学习时长应输入数字`);
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
			await api.get('xuexijihua/list',params).then(rs=>{
				crossoptnum2 = rs.data.total
			})
		}
		if (crossoptnum2&&crossoptnum2 >= crossoptnum) {
			utils.msg(wx.getStorageSync('tips'));
			wx.removeStorageSync('crossCleanType');
			return false;
		}
		let oet = {}
		let url = 'xuexijihua/add'
		if(this.data.ruleForm.id){
			url = 'xuexijihua/update'
		}
		await api.post(url, this.data.ruleForm).then(async oet=>{
			if(this.data.cross){
				wx.setStorageSync('crossCleanType',true);
			}
			utils.msgBack('提交成功');
		})
	},
})