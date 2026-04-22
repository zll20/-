
const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const base = getApp().base
const validate = getApp().validate
Page({
	data: {
		baseurl: base.base.url,
		isAuth: [],
		userid: '',
		scrollTop: 0,
		role: '',
		searchForm: {},
		list: [],
		lists: [],
		leftListList6: [],
		rightListList6: [],
		tempListList6: [],
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
		this.setData({
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
				isAuth: utils.isAuth('xuesheng'),
				role: wx.getStorageSync("appRole")
			})
		}else {
			this.setData({
				isAuth: utils.isAuthFront('xuesheng'),
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
		let params = JSON.parse(JSON.stringify(this.data.listQuery))
		if(!this.data.userid) {
		}
		params.sort = this.data.listSort;
		params.order = this.data.listOrder;
		if(this.data.searchForm.xingming){
			params['xingming'] = '%' + this.data.searchForm.xingming + '%'
		}
		let user = wx.getStorageSync("appUserid")?JSON.parse(wx.getStorageSync('userSession')):{}
		let url = 'xuesheng/list'
		if(this.data.userid) {
			url = 'xuesheng/page'
		}
		api.get(url,params).then(async res=>{
			// 如果是第一页数据置空
			res.data.list = res.data.list.map(item=>{
				item.touxiang = (item.touxiang?item.touxiang.split(',')[0]:'')
				return item;
			})
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
		wx.setStorageSync("useridTag",this.data.userid);
		utils.jump(`./detail?id=${item.id}&userid=${this.data.userid}`)
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
					await api.post('xuesheng/delete',JSON.stringify([item.id])).then(async (rs)=>{
						utils.msg('删除成功');
						// 重置分页参数页数为1
						that.search()
					})
				}
			}
		});
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