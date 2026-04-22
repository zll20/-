const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const base = getApp().base
const validate = getApp().validate
const des = getApp().des
Page({
	data: {
		list: [],
		listQuery: {
			page: 1,
			limit: 10,
			status: 1
		},
		searchForm: {},
		scrollTop: 0,
		userid: 0,
	},
	onLoad() {
		this.setData({
			userid: wx.getStorageSync("appUserid")
		})
	},
	onShow() {
		this.getList()
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
	onPullDownRefresh(){
		this.data.listQuery.page = 1
		this.getList()
		setTimeout(()=>{
			wx.stopPullDownRefresh()
		},1000)
	},
	// 上拉加载
	onReachBottom(){
		this.data.listQuery.page++
		this.getList()
	},
	// 获取列表
	async getList() {
		let params = JSON.parse(JSON.stringify(this.data.listQuery))
		params.userid = this.data.userid
		if(this.data.searchForm.papername) {
			params.papername = `%${this.data.searchForm.papername}%`
		}
		await api.get('examrecord/groupby',params).then(res=>{
			if(this.data.listQuery.page==1){
				this.data.list = []
			}else if(this.data.listQuery.page>1&&res.data.list.length==0) {
				this.data.listQuery.page--
				}
				res.data.list = res.data.list.map(item=>{
					item.rightAcc = (item.accuracy * 100).toFixed(2)
					item.errorAcc = ((1 - item.accuracy) * 100).toFixed(2)
					return item;
				})
			res.data.list = this.data.list.concat(res.data.list)
			this.setData({
				list: res.data.list
			})
		})
	},
	// 详情
	onDetailTap(e) {
		let item = e.currentTarget.dataset.item
		utils.jump(`./detail?paperid=${item.paperid}&userid=${item.userid}`)
	},
	search() {
		this.data.listQuery.page = 1
		this.getList()
	},
	searchKeyInput(e) {
		this.setData({
			[`searchForm.papername`]: e.detail.value
		})
	},
})