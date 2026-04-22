const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const base = getApp().base
const validate = getApp().validate
Page({
	data: {
		id: 0,
		detail: {},
		isAuth: [],
		userid: '',
		title: '',
		baseurl: base.base.url,
		user: null,
		getUserType: true,
		username: wx.getStorageSync("nickname"),
		swiperList: [],
		scrollTop: 0,
	},
	async onLoad(options) {
		if(options.id) {
			this.data.id = options.id
		}
		if(options.userid) {
			this.data.userid = options.userid
		}
		if(wx.getStorageSync('appToken')) {
			this.data.getUserType = false
			// 获取用户信息
			await api.get(`${wx.getStorageSync("nowTable")}/session`,{}).then((rs)=>{
				this.setData({
					user: rs.data
				})
			})
		}
		this.getDetail()
	},
	scrollTopClick(){
		wx.pageScrollTo({
			scrollTop: 0
		})
	},
	onPageScroll(e) {
		this.setData({
			scrollTop: e.scrollTop
		})
	},
	async onShow() {
		if(wx.getStorageSync('appToken')) {
			if(this.data.getUserType) {
				// 获取用户信息
				await api.get(`${wx.getStorageSync("nowTable")}/session`,{}).then((rs)=>{
					this.setData({
						user: rs.data
					})
				})
			}else {
				this.data.getUserType = true
			}
		}
		if(this.data.userid){
			this.setData({
				userid: this.data.userid,
				isAuth: utils.isAuth('dancifenlei')
			})
		}else {
			this.setData({
				isAuth: utils.isAuthFront('dancifenlei')
			})
		}
		let crossCleanType = wx.getStorageSync('crossCleanType')
		if(crossCleanType) {
			wx.removeStorageSync('crossCleanType')
			this.getDetail();
		}
	},
	getDetail(type=1) {
		api.get('dancifenlei/detail/' + this.data.id, {}).then((res)=>{
			let reg=new RegExp('http://localhost:8081/code/upload','g')//g代表全部
			let reg2=new RegExp('<img','g')//g代表全部

			if(type==2){
				res.data.discussnum++
				api.post('dancifenlei/update',res.data).then((rs2)=>{})
			}
			// 数据存储
			this.setData({
				detail: res.data,
			})
		})
	},
	isJSON(str) {
		try {
			JSON.parse(str);
			return true;
		} catch (e) {
			return false;
		}
	},
	// 拨打电话
	callClick(e){
		let phone = e.currentTarget.dataset.phone
		wx.makePhoneCall({
			phoneNumber: phone
		})
	},
	linkOthers(e) {
		let url = e.currentTarget.dataset.url
		wx.navigateTo({
			url:"/auth/linkOthers/linkOthers?url=" + encodeURIComponent(url)
		});
	},
	// 跳转其他详情
	onDetailTap(e) {
		let item = e.currentTarget.dataset.item
		wx.setStorageSync("useridTag",this.data.userid);
		utils.jump(`./detail?id=${item.id}&userid=`+this.data.userid)
	},
	// 跨表
	async onAcrossTap(e){
		let tableName = e.currentTarget.dataset.tableName
		let crossOptAudit = e.currentTarget.dataset.crossOptAudit
		let crossOptPay = e.currentTarget.dataset.crossOptPay
		let statusColumnName = e.currentTarget.dataset.statusColumnName
		let tips = e.currentTarget.dataset.tips
		let statusColumnValue = e.currentTarget.dataset.statusColumnValue
		let that = this
		if(!this.data.user){
			utils.msg("请先登录")
			setTimeout(()=>{
				utils.jump('/auth/login/login')
			},1500)
			return false
		}
		wx.setStorageSync('crossTable','dancifenlei');
		wx.setStorageSync(`crossObj`, this.data.detail);
		wx.setStorageSync(`statusColumnName`, statusColumnName);
		wx.setStorageSync(`statusColumnValue`, statusColumnValue);
		wx.setStorageSync(`tips`, tips);
		if(statusColumnName!=''&&!statusColumnName.startsWith("[")) {
			var obj = wx.getStorageSync('crossObj');
			for (var o in obj){
				if(o==statusColumnName && obj[o]==statusColumnValue){
					utils.msg(tips);
					return
				}
			}
		}
		utils.jump(`../${tableName}/add-or-update?cross=true`);
	},
	getSecondToDate() {
		var time = this.data.count;
		if (null != time && "" != time) {
			if (time > 60 && time < 60 * 60) {
				time =
					parseInt(time / 60.0) +
					"分钟" +
					parseInt((parseFloat(time / 60.0) - parseInt(time / 60.0)) * 60) +
					"秒";
			} else if (time >= 60 * 60 && time < 60 * 60 * 24) {
				time =
					parseInt(time / 3600.0) +
					"小时" +
					parseInt(
						(parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
					) +
					"分钟" +
					parseInt(
						(parseFloat(
								(parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
							) -
							parseInt(
								(parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
							)) *
						60
					) +
					"秒";
			} else if (time >= 60 * 60 * 24) {
				time =
					parseInt(time / 3600.0 / 24) +
					"天" +
					parseInt(
						(parseFloat(time / 3600.0 / 24) - parseInt(time / 3600.0 / 24)) *
						24
					) +
					"小时" +
					parseInt(
						(parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
					) +
					"分钟" +
					parseInt(
						(parseFloat(
								(parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
							) -
							parseInt(
								(parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
							)) *
						60
					) +
					"秒";
			} else {
				time = parseInt(time) + "秒";
			}
		}
		return time;
	},
	imgView(e) {
		let url = e.currentTarget.dataset.url
		let arr = []
		for(let x in this.data.swiperList){
			arr.push(this.data.swiperList[x].substr(0,4)=='http'?this.data.swiperList[x]:this.data.baseurl + this.data.swiperList[x])
		}
		wx.previewImage({
			current: url,
			urls: arr
		})
	},
	// 下载
	download(e){
		let url = e.currentTarget.dataset.url
		if(!url){
			return false
		}
		let that = this;
		url=that.data.baseurl +  url;
		wx.downloadFile({
			url: url,
			success: (res) => {
				if (res.statusCode === 200) {
					utils.msg('下载成功');
					wx.saveFile({
						tempFilePath: res.tempFilePath, //临时路径
						success: function(obj) {
							wx.showToast({
								icon: 'success',
								mask: true,
								title: '下载成功' , 
								duration: 2000,
							});
							setTimeout(() => {
								var filePath = obj.savedFilePath;
								wx.openDocument({ //新开页面打开文档，支持格式：doc, xls, ppt, pdf, docx, xlsx, pptx。
									filePath: filePath,
									showMenu: true,
									success: function(res) {
									}
								});
							}, 2000)
						}
					});
				}
			}
		});
	},

})