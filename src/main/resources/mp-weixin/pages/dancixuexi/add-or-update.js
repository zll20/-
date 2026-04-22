  
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


			danciliang: Number('50') ,


			shifoujizhu: '',
		},
		shifoujizhuOptions: [],
		shifoujizhuIndex: 0,
		// 登录用户信息
		user: {},
		ro:{
			xingming : false,
			xuehao : false,
			riqi : false,
			xuexishizhang : false,
			danciliang : false,
			shifoujizhu : false,
		},
		showType: 1,
		chartData1: {},
		opts1: {},
		chartType1: false,
		chartData2: {},
		opts2: {},
		chartType2: false,
	},
	computed:{



	},
	async onLoad(options) {
		if(options.showType) {
			if(options.userid){
				this.setData({
					userid: options.userid,
					isAuth: utils.isAuth('dancixuexi'),
					showType: 2
				})
			}else {
				this.setData({
					isAuth: utils.isAuthFront('dancixuexi'),
					showType: 2
				})
			}
			return false
		}
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

		// 自定义下拉框值
		let shifoujizhuOptions = "已记住,未记住".split(',')
		shifoujizhuOptions.unshift("请选择是否记住");
		let shifoujizhuIndex = 0
		if(ruleForm.shifoujizhu) {
			for(let x in shifoujizhuOptions) {
				if(shifoujizhuOptions[x] == ruleForm.shifoujizhu) {
					shifoujizhuIndex = Number(x)
				}
			}
		}
		this.setData({
			shifoujizhuOptions: shifoujizhuOptions,
			shifoujizhuIndex: shifoujizhuIndex
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
			await api.get('dancixuexi/info/' + options.id).then(res=>{
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
				if(o=='danciliang'){
					ruleForm.danciliang = obj[o];
					ro.danciliang = true;
					continue;
				}
				if(o=='shifoujizhu'){
					ruleForm.shifoujizhu = obj[o];
					for(let x in this.data.shifoujizhuOptions) {
						if(this.data.shifoujizhuOptions[x] == ruleForm.shifoujizhu) {
							this.setData({
								shifoujizhuIndex: Number(x)
							})
						}
					}
					ro.shifoujizhu = true;
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
		if(this.data.shifoujizhuIndex==0) {
			this.data.ruleForm.shifoujizhu = ''
		}
		//跨表计算判断
		var obj;
		if(this.data.ruleForm.xuexishizhang&&(!validate.isNumber(this.data.ruleForm.xuexishizhang))){
			utils.msg(`学习时长应输入数字`);
			return
		}
		if(this.data.ruleForm.danciliang&&(!validate.isIntNumer(this.data.ruleForm.danciliang))){
			utils.msg(`单词量应输入整数`);
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
			await api.get('dancixuexi/list',params).then(rs=>{
				crossoptnum2 = rs.data.total
			})
		}
		if (crossoptnum2&&crossoptnum2 >= crossoptnum) {
			utils.msg(wx.getStorageSync('tips'));
			wx.removeStorageSync('crossCleanType');
			return false;
		}
		let oet = {}
		let url = 'dancixuexi/add'
		if(this.data.ruleForm.id){
			url = 'dancixuexi/update'
		}
		await api.post(url, this.data.ruleForm).then(async oet=>{
			if(this.data.cross){
				wx.setStorageSync('crossCleanType',true);
			}
			utils.msgBack('提交成功');
		})
	},
	// 统计图：学习分析
	async chartClick1() {
		let res = null
		await api.get('dancixuexi/group/shifoujizhu').then(rs=>{
			res = rs.data
		})
		let opts1 = {}
		let chartData1 = {}
		let xAxis = [];
		let yAxis = [];
		let pArray = []
		for(let i=0;i<res.length;i++){
			xAxis.push(res[i]['shifoujizhu']);
			yAxis.push(parseFloat((res[i].total)));
			pArray.push({
				value: parseFloat((res[i].total)),
				name: res[i]['shifoujizhu']
			})
		}
		opts1 = {
			color: ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"],
			padding: [5,5,5,5],
			enableScroll: false,
			title: {
				name: '',
			},
			subtitle: {
				name: '',
			},
			extra: {
				pie: {
					activeOpacity: 0.5,
					activeRadius: 10,
					offsetAngle: 0,
					labelWidth: 15,
					border: false,
					borderWidth: 3,
					borderColor: "#FFFFFF"
				}
			}
		}
		chartData1 = {
			series: [
				{
					data: pArray
				}
			]
		};
		this.setData({
			opts1: opts1,
			chartData1: chartData1,
			chartType1: true,
		})
	},
	// 统计图：学习时长
	async chartClick2() {
		let res = null
		await api.get('dancixuexi/value/riqi/xuexishizhang').then(rs=>{
			res = rs.data
		})
		let opts2 = {}
		let chartData2 = {}
		let xAxis = [];
		let yAxis = [];
		let pArray = []
		for(let i=0;i<res.length;i++){
			xAxis.push(res[i]['riqi']);
			yAxis.push(parseFloat((res[i].total)));
			pArray.push({
				value: parseFloat((res[i].total)),
				name: res[i]['riqi']
			})
		}
		opts2 = {
			color: ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"],
			padding: [15,15,0,5],
			enableScroll: false,
			xAxis: {
				disableGrid: true
			},
			legend: {
				show: false
			},
			yAxis: {},
			extra: {
				column: {
					type: "group",
					width: 30,
					activeBgColor: "#000000",
					activeBgOpacity: 0.08
				}
			}
		}
		chartData2 = {
			categories: xAxis,
			series: [
				{
					data: yAxis
				}
			]
		};
		this.setData({
			opts2: opts2,
			chartData2: chartData2,
			chartType2: true,
		})
	},
})