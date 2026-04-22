  
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


			danci: '',


			jibie: '',


			dancifenlei: '',


			shiyi: '',


			liju: '',


			fengmiantu: '',




			discussnum: '',


			storeupnum: '',
		},
		jibieOptions: [],
		jibieIndex: 0,
		dancifenleiOptions: [],
		dancifenleiIndex: 0,
		// 登录用户信息
		user: {},
		ro:{
			danci : false,
			jibie : false,
			dancifenlei : false,
			shiyi : false,
			liju : false,
			fengmiantu : false,
			clicktime : false,
			discussnum : false,
			storeupnum : false,
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
		let jibieOptions = "四级,六级".split(',')
		jibieOptions.unshift("请选择级别");
		let jibieIndex = 0
		if(ruleForm.jibie) {
			for(let x in jibieOptions) {
				if(jibieOptions[x] == ruleForm.jibie) {
					jibieIndex = Number(x)
				}
			}
		}
		this.setData({
			jibieOptions: jibieOptions,
			jibieIndex: jibieIndex
		})
		var dancifenleiRefParams = {}
		// 下拉框
		await api.get('option/dancifenlei/dancifenlei',dancifenleiRefParams).then(rs=>{
			let dancifenleiOptions = rs.data
			dancifenleiOptions.unshift("请选择单词分类");
			let dancifenleiIndex = 0
			if(ruleForm.dancifenlei) {
				for(let x in dancifenleiOptions) {
					if(dancifenleiOptions[x] == ruleForm.dancifenlei) {
						dancifenleiIndex = Number(x)
					}
				}
			}
			this.setData({
				dancifenleiOptions: dancifenleiOptions,
				dancifenleiIndex: dancifenleiIndex
			})
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
			await api.get('danciku/info/' + options.id).then(res=>{
				ruleForm = res.data;
			})
		}
		this.data.cross = options.cross||false;
		if(options.cross){
			var obj = wx.getStorageSync('crossObj');
			for (var o in obj){
				if(o=='danci'){
					ruleForm.danci = obj[o];
					ro.danci = true;
					continue;
				}
				if(o=='jibie'){
					ruleForm.jibie = obj[o];
					for(let x in this.data.jibieOptions) {
						if(this.data.jibieOptions[x] == ruleForm.jibie) {
							this.setData({
								jibieIndex: Number(x)
							})
						}
					}
					ro.jibie = true;
					continue;
				}
				if(o=='dancifenlei'){
					ruleForm.dancifenlei = obj[o];
					for(let x in this.data.dancifenleiOptions) {
						if(this.data.dancifenleiOptions[x] == ruleForm.dancifenlei) {
							this.setData({
								dancifenleiIndex: Number(x)
							})
						}
					}
					ro.dancifenlei = true;
					continue;
				}
				if(o=='shiyi'){
					ruleForm.shiyi = obj[o];
					ro.shiyi = true;
					continue;
				}
				if(o=='liju'){
					ruleForm.liju = obj[o];
					ro.liju = true;
					continue;
				}
				if(o=='fengmiantu'){
					ruleForm.fengmiantu = obj[o].split(",")[0];
					ro.fengmiantu = true;
					continue;
				}
				if(o=='clicktime'){
					ruleForm.clicktime = obj[o];
					ro.clicktime = true;
					continue;
				}
				if(o=='discussnum'){
					ruleForm.discussnum = obj[o];
					ro.discussnum = true;
					continue;
				}
				if(o=='storeupnum'){
					ruleForm.storeupnum = obj[o];
					ro.storeupnum = true;
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
		if(this.data.jibieIndex==0) {
			this.data.ruleForm.jibie = ''
		}
		if(this.data.dancifenleiIndex==0) {
			this.data.ruleForm.dancifenlei = ''
		}
		//跨表计算判断
		var obj;
		if((!this.data.ruleForm.danci)){
			utils.msg(`单词不能为空`);
			return
		}
		if((!this.data.ruleForm.jibie)){
			utils.msg(`级别不能为空`);
			return
		}
		if(this.data.ruleForm.discussnum&&(!validate.isIntNumer(this.data.ruleForm.discussnum))){
			utils.msg(`评论数应输入整数`);
			return
		}
		if(this.data.ruleForm.storeupnum&&(!validate.isIntNumer(this.data.ruleForm.storeupnum))){
			utils.msg(`收藏数应输入整数`);
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
			await api.get('danciku/list',params).then(rs=>{
				crossoptnum2 = rs.data.total
			})
		}
		if (crossoptnum2&&crossoptnum2 >= crossoptnum) {
			utils.msg(wx.getStorageSync('tips'));
			wx.removeStorageSync('crossCleanType');
			return false;
		}
		let oet = {}
		let url = 'danciku/add'
		if(this.data.ruleForm.id){
			url = 'danciku/update'
		}
		await api.post(url, this.data.ruleForm).then(async oet=>{
			if(this.data.cross){
				wx.setStorageSync('crossCleanType',true);
			}
			utils.msgBack('提交成功');
		})
	},
})