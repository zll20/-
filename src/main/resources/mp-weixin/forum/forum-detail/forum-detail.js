const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const base = getApp().base
const validate = getApp().validate
Page({
	data: {
		baseurl: base.base.url,
		userid: wx.getStorageSync('appUserid'),
		commentData: [],
		forum: {},
		id: '',
		swiperList: [],
	},
	async onLoad(options) {
		this.data.id = options.id;
		this.init()
	},
	async onShow() {
		let forumComType = wx.getStorageSync('forumComType');
		if(forumComType){
			wx.removeStorageSync('forumComType')
			this.init()
		}
	},
	async init() {
		await api.get(`forum/list/${this.data.id}`).then(res=>{
			this.data.swiperList = res.data.cover ? res.data.cover.split(",") : [];
			res.data.childs = this.changeContent(res.data.childs)
			res.data.content = res.data.content.replace(/img src/gi,"img style=\"width:100%;\" src");
			this.setData({
				swiperList: this.data.swiperList,
				prevNumList4: this.data.prevNumList4,
				forum: res.data,
				commentData: res.data.childs
			})
		})
	},
	changeContent(childs){
		if(childs&&childs!=null&&childs.length){
			childs = change(childs)
		}
		return childs
		function change(childs){
			for(let x in childs){
				childs[x].content = childs[x].content.replace(/img src/gi,"img style=\"width:100%;\" src");
				if(childs[x].childs){
					childs[x].childs = change(childs[x].childs)
				}
			}
			return childs
		}
	},
	imgView(url,index){
		let that = this
		wx.showActionSheet({
			itemList: ['预览','删除'],
			success(res) {
				if(res.tapIndex==0){
					let arr = []
					for(let x in that.data.picList){
						arr.push(that.data.baseurl + that.data.picList[x])
					}
					wx.previewImage({
						current: that.data.baseurl + url,
						urls: arr
					})
				}else if(res.tapIndex==1){
					that.data.picList.splice(index,1)
					that.setData({
						picList: that.data.picList
					})
				}
			}
		})
	},
	imgView2(e) {
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
	onReplyTap(e) {
		let pid = e.currentTarget.dataset.id
		utils.jump(`../forum-reply/forum-reply?pid=${pid}`);
	},
	// 删除当前评论
	delClick(e) {
		let id = e.currentTarget.dataset.id
		let that = this
		wx.showModal({
			title: '提示',
			content: '是否删除此评论？',
			async success(obj) {
				if (obj.confirm) {
					api.post('forum/delete', JSON.stringify([id])).then(rs=>{
						utils.msg('删除成功')
						setTimeout(async () => {
							api.get(`forum/list/${this.data.id}`).then(res=>{
								res.data.childs = that.changeContent(res.data.childs)
								that.setData({
									commentData: res.data.childs
								})
							})
						}, 1500)
					})
				}
			}
		})
	},
})