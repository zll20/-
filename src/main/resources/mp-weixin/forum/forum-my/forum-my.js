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
			sort: 'istop,toptime',
			order: 'desc,desc',
		},
		scrollTop: 0
	},
	onLoad() {
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
	// 获取列表
	async getList() {
		let params = JSON.parse(JSON.stringify(this.data.listQuery))
		await api.get('forum/page',params).then(res=>{
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
	search() {
		this.data.listQuery.page = 1
		this.getList()
	},
	onDetailTap(e) {
		let id = e.currentTarget.dataset.id
		utils.jump(`../forum-detail/forum-detail?id=${id}`)
	},
	changeInList(id){
		for(let x = 0;x<this.data.list.length;x++) {
			if(this.data.list[x].id==id) {
				return this.data.list[x].sfsh == '是'||this.data.list[x].sfsh == '否'
			}
		}
	},
	onUpdateTap(e) {
		let id = e.currentTarget.dataset.id
		if(id) {
			if(this.changeInList(id)) {
				utils.msg('已审核，不能修改');
				return false
			}
			utils.jump(`../forum-add-or-update/forum-add-or-update?id=${id}`)
		}else {
			utils.jump(`../forum-add-or-update/forum-add-or-update`)
		}
	},
	onDeleteTap(e) {
		let id = e.currentTarget.dataset.id
		let that = this;
		wx.showModal({
			title: '提示',
			content: '是否确认删除',
			success: async function(res) {
				if (res.confirm) {
					await api.post('forum/delete', JSON.stringify([id])).then(rs=>{
						utils.msg('删除成功')
						that.search()
					})
				}
			}
		});
	}
})