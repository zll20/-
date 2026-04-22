
const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const base = getApp().base
const validate = getApp().validate
Page({
	data: {
		baseurl: base.base.url,
		type: 1,
		isAuth: [],
		userid: '',
		scrollTop: 0,
		role: '',
		searchForm: {},
		list: [],
		lists: [],
		stripe: true,
		borderline:false,
		headerbg:'#f5f5f5',
		herderColor: '#999',
		column: [
			{
				name: 'content',
				label: '评论内容',
			},
			{
				name: 'reply',
				label: '回复内容',
			},
		],
		listQuery: {
			page: 1,
			limit: 10,
		},
		listSort: 'id',
		listOrder: 'desc',
		categoryName: '全部',
		screenBoxShow: false,
	},
	async onLoad(options) {
		if(options.menuJump) {
			this.data.type=options.menuJump;
		} else {
			this.data.type = '1';
		}
		if(options.menuJump==1) {
			wx.setNavigationBarTitle({
				title: '我的收藏'
			})
		}else if(options.menuJump==21) {
			wx.setNavigationBarTitle({
				title: '我的点赞'
			})
		}else if(options.menuJump==31) {
			wx.setNavigationBarTitle({
				title: '我的参拍'
			})
		}else if(options.menuJump==41) {
			wx.setNavigationBarTitle({
				title: '我的关注'
			})
		}else if(options.menuJump==51) {
			wx.setNavigationBarTitle({
				title: '浏览历史'
			})
		}else if(options.menuJump==81) {
			wx.setNavigationBarTitle({
				title: '我的评论'
			})
		}
		this.setData({
			type: this.data.type,
		})
	},
	onUnload() {
		wx.removeStorageSync("useridTag");
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
		if(wx.getStorageSync("useridTag")==1){
			this.setData({
				userid:wx.getStorageSync("useridTag"),
				isAuth: utils.isAuth('storeup'),
				role: wx.getStorageSync("appRole")
			})
		}else {
			this.setData({
				isAuth: utils.isAuthFront('storeup'),
				role: wx.getStorageSync("appRole"),
				userid: ''
			})
		}
		this.getList()
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
	// 搜索框输入
	searchKeyInput(e) {
		this.setData({
			[`searchForm.${e.target.dataset.name}`]:e.detail.value
		})
	},
	// 搜索
	search() {
		this.data.listQuery.page = 1
		this.getList()
	},
	async getList() {
		if(this.data.type==81) {
			api.get('comment/list',{}).then((res)=>{
				this.setData({
					list: res.data
				})
			})
			return false
		}
		let params = JSON.parse(JSON.stringify(this.data.listQuery))
		if(!this.data.userid) {
		}
		params.sort = this.data.listSort;
		params.order = this.data.listOrder;
		params.type = this.data.type;
		let user = wx.getStorageSync("appUserid")?JSON.parse(wx.getStorageSync('userSession')):{}
		let url = 'storeup/list'
		if(this.data.userid) {
			url = 'storeup/page'
		}
		api.get(url,params).then(async res=>{
			// 如果是第一页数据置空
			if (this.data.listQuery.page == 1) this.data.list = [];
			this.data.list = this.data.list.concat(res.data.list);
			if(this.data.listQuery.page>1&&res.data.list.length==0) {
				this.data.listQuery.page--
			}
			let length = Math.ceil(this.data.list.length/6)
			let arr = [];
			for (let i = 0; i<length; i++){
				arr[i] = this.data.list.slice(i*6, (i+1)*6)
			}
			this.data.lists = arr
			this.setData({
				list: this.data.list,
				lists: this.data.lists,
			})
		})
	},
	uGetRect(selector, all) {
		return new Promise(resolve => {
			wx.createSelectorQuery()
			.in(this)
			[all ? 'selectAll' : 'select'](selector)
			.boundingClientRect(rect => {
				if (all && Array.isArray(rect) && rect.length) {
					resolve(rect);
				}
				if (!all && rect) {
					resolve(rect);
				}
			})
			.exec();
		});
	},
	cloneData(data) {
		return JSON.parse(JSON.stringify(data));
	},
	sortClick(e){
		let type = e.currentTarget.dataset.name
		if(this.data.listSort==type){
			if(this.data.listOrder == 'desc'){
				this.setData({
					listOrder: 'asc'
				})
			}else{
				this.setData({
					listOrder: 'desc'
				})
			}
		}else{
			this.setData({
				listSort: type,
				listOrder: 'desc'
			})
		}
		this.search()
	},
	onDetailTap(e) {
		let item = e.currentTarget.dataset.item
		if(item.tablename =='news'){
			utils.jump(`/news/news-detail/news-detail?id=${item.refid}`)
			return false
		}
		if(item.tablename =='forum'){
			utils.jump(`/forum/forum-detail/forum-detail?id=${item.refid}`)
			return false
		}
		if(item.tablename.substr(0,7)=='chapter'){
			let tablename = item.tablename.substring(7,item.tablename.length)
			utils.jump(`../${tablename}/chapter?id=${item.refid}&refid=${item.remark}`)
			return false
		}
		utils.jump(`../${item.tablename}/detail?id=${item.refid}`)
	},
	// 修改
	onUpdateTap(e){
		let item = e.currentTarget.dataset.item
		wx.setStorageSync("useridTag",this.data.userid);
		utils.jump(`./add-or-update?id=${item.id}`)
	},
	onDeleteTap(e){
		let item = e.currentTarget.dataset.item
		var that = this;
		wx.showModal({
			title: '提示',
			content: '是否确认删除',
			success: async function(res) {
				if (res.confirm) {
					if(that.data.type==81){
						await api.post(`discuss${item.tablename}/delete`,JSON.stringify([item.id])).then(async(res)=>{
							await api.get(`${item.tablename}/info/${item.refid}`,{}).then(async (rs)=>{
								rs.data.discussnum--
								await api.post(`${item.tablename}/update`,rs.data).then((rs2)=>{})
							})
						})
						that.search()
						return false
					}
					if(that.data.type==1) {
						await api.get(`${item.tablename}/info/${item.refid}`,{}).then(async (rs)=>{
							rs.data.storeupnum--
							await api.post(`${item.tablename}/update`,rs.data).then((rs2)=>{})
						})
					}
					if(that.data.type==21) {
						await api.get(`${item.tablename}/info/${item.refid}`,{}).then(async (rs)=>{
							rs.data.thumbsupnum--
							await api.post(`${item.tablename}/update`,rs.data).then((rs2)=>{})
						})
					}
					await api.post(`storeup/delete`,JSON.stringify([item.id])).then(async(res)=>{
						utils.msg('删除成功')
						that.search()
					})
				}
			}
		});
	},
	// 查看
	onUpdate(row){
		wx.setStorageSync("useridTag",this.data.userid);
		utils.jump(`../${row.tablename}/detail?id=${row.refid}`)
	},
	onDelete(row){
		var that = this;
		wx.showModal({
			title: '提示',
			content: '是否确认删除',
			success: async function(obj) {
				if (obj.confirm) {
					if(that.data.type==81){
						await api.post(`discuss${row.tablename}/delete`, JSON.stringify([row.id])).then(rs=>{})
						await api.get(`${row.tablename}/info/${row.refid}`).then(async rs=>{
							rs.data.discussnum--
							await api.post(`${row.tablename}/update`, rs.data).then(rs2=>{})
							utils.msg('删除成功');
							that.search()
						})
						return false
					}
					if(that.data.type==1) {
						await api.get(`${row.tablename}/info/${row.refid}`).then(async rs=>{
							rs.data.storeupnum--
							await api.post(`${row.tablename}/update`, rs.data).then(rs2=>{})
						})
					}
					if(that.data.type==21) {
						await api.get(`${row.tablename}/info/${row.refid}`).then(async rs=>{
							rs.data.thumbsupnum--
							await api.post(`${row.tablename}/update`, rs.data).then(rs2=>{})
						})
					}
					await api.post(`storeup/delete`, JSON.stringify([row.id])).then(rs=>{})
					utils.msg('删除成功');
					that.search()
				}
			}
		});
	},
	onUpdateOrDel(e){
		let row = e.detail.row
		let that = this
		wx.showActionSheet({
			itemList: ['查看','删除'],
			success(res) {
				if(res.tapIndex==0){
					that.onUpdate(row)
				}else if(res.tapIndex==1){
					that.onDelete(row)
				}
			}
		})
	},
	screenBoxShowClick(e) {
		let type = e.currentTarget.dataset.name
		this.setData({
			screenBoxShow: type==1?true:false
		})
	},
	// 添加
	onAddTap(){
		wx.setStorageSync("useridTag",this.data.userid);
		utils.jump(`./add-or-update`)
	},
	staticClick() {
		utils.jump(`./add-or-update?showType=2&userid=` + this.data.userid)
	},
	screenReset(){
		this.setData({
			searchForm: {}
		})
		this.search()
	},
	screenConfirm(){
		this.setData({
			screenBoxShow: false
		})
		this.search()
	},
	dateTimeShow(e) {
		var name = e.currentTarget.dataset.name + 'Show'
		this.setData({
			[name]:true
		})
	},
	dateTimeChange(e) {
		var name = e.currentTarget.dataset.name
		this.setData({
			[`searchForm.${name}`]: e.detail.dete,
			[`${name}Show`]: false
		})
	},
	dateTimeCancel(e) {
		var name = e.currentTarget.dataset.name
		this.setData({
			[`${name}Show`]: false
		})
	},
})