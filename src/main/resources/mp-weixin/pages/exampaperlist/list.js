
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
		userid: '',
		scrollTop: 0,
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
		let user = wx.getStorageSync("appUserid")?JSON.parse(wx.getStorageSync('userSession')):{}
		if(this.data.searchForm.name) {
			params.name = `%${this.data.searchForm.name}%`
		}
		await api.get('exampaper/list',params).then(res=>{
			if(this.data.listQuery.page==1){
				this.data.list = []
			}else if(this.data.listQuery.page>1&&res.data.list.length==0) {
				this.data.listQuery.page--
			}
			res.data.list = this.data.list.concat(res.data.list)
			this.setData({
				list: res.data.list
			})
		})
	},
	// 详情
	async onDetailTap(e) {
		let item = e.currentTarget.dataset.item
		if(item.examnum>0) {
			let num = 0
			await api.get('examrecord/groupby', {
				paperid: item.id,
				userid: Number(wx.getStorageSync("appUserid"))
			}).then(rs=>{
				num = rs.data.list.length
			})
			if(num>=item.examnum) {
				utils.msg('超过当前考试最大次数！')
				return false
			}
		}
		utils.jump(`/exam/exampaperlist/exam?id=${item.id}`)
	},
	searchKeyInput(e) {
		this.setData({
			[`searchForm.name`]: e.detail.value
		})
	},
	search() {
		this.data.listQuery.page = 1
		this.getList()
	},
})