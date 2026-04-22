const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const validate = getApp().validate
Page({
	data: {
		pid: 0,
		content: '',
		username: '',
		avatarurl: '',
	},
	onLoad(options) {
		this.data.pid = options.pid;
		this.data.username = wx.getStorageSync('nickname')
	},
	contentChange(e) {
		this.setData({
			content: e.detail
		})
	},
	async onReplyTap() {
		if(this.data.content==''){
			utils.msg('内容不能为空');
			return false
		}
		let forum = {
			parentid: this.data.pid,
			content: this.data.content,
			avatarurl: wx.getStorageSync('frontHeadportrait')?wx.getStorageSync('frontHeadportrait'):'',
			username: this.data.username,
			toptime: utils.getCurDateTime()
		}
		api.post('forum/save', forum).then(res=>{
			wx.setStorageSync('forumComType', true);
			utils.msgBack('回复成功')
		})
	}
})