const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const base = getApp().base
const validate = getApp().validate
Page({
	data: {
		baseurl: base.base.url,
		userid: wx.getStorageSync('appUserid'),
		list: [],
		listQuery: {
			page: 1,
			limit: 10,
			parentid: 0,
			isdone: '开放',
			sort: 'istop,toptime',
			order: 'desc,desc',
		},
		searchForm:{
			title: ''
		},
		scrollTop: 0
	},
	async onLoad() {
		this.setData({
			userid: wx.getStorageSync("appUserid")
		})
		this.getList()
	},
	onShow() {
		let cleanType = wx.getStorageSync('forumCleanType')
		if(cleanType){
			wx.removeStorageSync('forumCleanType')
			this.getList()
		}
	},
	// 下拉刷新
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
	// 获取列表
	async getList() {
		let params = JSON.parse(JSON.stringify(this.data.listQuery))
		params.delflag = 0
		params.sfsh = '是'
		if(this.data.searchForm.title) {
			params.title = `%${this.data.searchForm.title}%`
		}
		await api.get('forum/flist',params).then(res=>{
			if(this.data.listQuery.page==1){
				this.data.list = []
			}else if(this.data.listQuery.page>1&&res.data.list.length==0) {
				this.data.listQuery.page--
				}
				res.data.list = res.data.list.map(item=>{
					item.cover = item.cover.split(',')[0]
					return item;
				})
			res.data.list = this.data.list.concat(res.data.list)
			this.setData({
				list: res.data.list
			})
		})
	},
	onUpdateTap() {
		utils.jump(`/forum/forum-add-or-update/forum-add-or-update`)
	},
	search() {
		this.data.listQuery.page = 1
		this.getList()
	},
	searchKeyInput(e) {
		this.setData({
			[`searchForm.title`]: e.detail.value
		})
	},
	// 详情
	onDetailTap(e) {
		let id = e.currentTarget.dataset.id
		utils.jump(`/forum/forum-detail/forum-detail?id=${id}`)
	},
})