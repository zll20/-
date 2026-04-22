const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const base = getApp().base
const validate = getApp().validate
Page({
	data: {
		baseurl: base.base.url,
		id: 0,
		forum: {
			content: '',
			title: '',
			isdone: '开放',
			parentid: 0,
			isanon: 0,
			cover: ''
		},
		isdoneIndex: 0,
		options: ['开放', '关闭'],
		isanonIndex: 0,
		options2: ['实名', '匿名'],
		picList: [],
		username: '',
	},
	async onLoad(options) {
		let forum = this.data.forum
		let id = this.data.id
		if (options.id) {
			id = options.id;
			await api.get(`forum/info/${id}`).then(rs=>{
				if(rs.data.cover) {
					this.setData({
						picList: rs.data.cover.split(',')
					})
				}
				if(rs.data.isdone) {
					for(let x in this.data.options) {
						if(this.data.options[x] == rs.data.isdone) {
							this.setData({
								isdoneIndex: Number(x)
							})
						}
					}
				}
				if(rs.data.isanon) {
					this.setData({
						isanonIndex: Number(rs.data.isanon)
					})
				}
				forum = rs.data
			})
		}
		this.setData({
			forum: forum,
			id: id,
			username: wx.getStorageSync('nickname'),
		})
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
	async uploadImg(){
		let that = this;
		await api.upload('image').then(rs=>{
			that.data.picList.push('upload/' + rs.file)
			that.setData({
				picList: that.data.picList
			})
		})
	},
	titleInput(e) {
		this.setData({
			[`forum.${e.target.dataset.name}`]: e.detail.value
		})
	},
	contentChange(e) {
		this.setData({
			[`forum.content`]: e.detail
		})
	},
	pickerChange(e) {
		this.setData({
			[`forum.isdone`]: this.data.options[e.detail.value],
			isdoneIndex: e.detail.value
		})
	},
	pickerChange2(e) {
		this.setData({
			[`forum.isanon`]: e.detail.value,
			isanonIndex: e.detail.value
		})
	},
	async onSubmitTap() {
		if(this.data.forum.title==''){
			utils.msg('标题不能为空');
			return false
		}
		if(this.data.forum.content==''){
			utils.msg('内容不能为空');
			return false
		}
		if(this.data.picList.length){
			this.data.forum.cover = this.data.picList.join(',')
		}else {
			utils.msg('封面图不能为空');
			return false
		}
		this.data.forum.username = this.data.username;
		let url = 'forum/save'
		if (this.data.id) {
			url = 'forum/update'
		}
		api.post(url, this.data.forum).then(res=>{
			wx.setStorageSync('forumCleanType',true);
			utils.msgBack('操作成功')
		})
	},
})