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
		showIndex: -1,
	},
	onLoad(options) {
		this.setData({
			userid: options.userid,
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
		params.ismark = 1
		params.myscore = 0
		params.sort = 'id'
		if(this.data.searchForm.questionname) {
			params.questionname = `%${this.data.searchForm.questionname}%`
		}
		await api.get('examrecord/list',params).then(res=>{
			if(this.data.listQuery.page==1){
				this.data.list = []
			}else if(this.data.listQuery.page>1&&res.data.list.length==0) {
				this.data.listQuery.page--
			}
			let arr = []
			for(let x in res.data.list){
				res.data.list[x].questionname = res.data.list[x].questionname.replace(/img src/gi,"img style=\"width:100%;\" src");
				if(res.data.list[x].type==0||res.data.list[x].type==2){
					arr = JSON.parse(res.data.list[x].options)
					for(let i in arr){
						if(res.data.list[x].answer == arr[i].code){
							res.data.list[x].right = arr[i].text
						}
					}
				}else if(res.data.list[x].type==1){
					arr = JSON.parse(res.data.list[x].options)
					res.data.list[x].answer.split(',').forEach(item=>{
						for(let i in arr){
							if (item == arr[i].code) {
								if (res.data.list[x].right) {
									res.data.list[x].right += ','
									res.data.list[x].right = res.data.list[x].right + arr[i].text
								}else{
									res.data.list[x].right = arr[i].text
								}
							}
						}
					})
				}else if(res.data.list[x].type==3){
					res.data.list[x].right = res.data.list[x].answer
				}else if(res.data.list[x].type==4){
					res.data.list[x].right = '略'
				}
			}
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
	searchKeyInput(e) {
		this.setData({
			[`searchForm.questionname`]: e.detail.value
		})
	},
	showClick(e) {
		let index = e.currentTarget.dataset.index
		if(this.data.showIndex == index){
			this.setData({
				showIndex: -1
			})
		}else {
			this.setData({
				showIndex: index
			})
		}
	},
})