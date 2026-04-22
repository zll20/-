// app.js
const utils = require('./utils/utils')
const menu = require('./utils/menu')
const api = require('./api/http')
const base = require('./api/base')
const validate = require('./utils/validate')
App({
	remindTimer: null,
	onLaunch() {
		this.getRemindAll()
	},
	compareDate(s1, s2) {
		return ((new Date(s1.replace(/-/g, "\/"))) > (new Date(s2.replace(/-/g, "\/"))));
	},
	removeHtmlTags(html) {
		var regExp = /<[^>]+>/g; // 定义正则表达式，匹配所有的HTML标签
		return html.replace(regExp, ''); // 将匹配到的HTML标签替换为空字符串
	},
	async getRemindAll(){
		let obj = {}
		if(wx.getStorageSync('appToken')&&wx.getStorageSync('appToken')!=null){
			await api.get('popupremind/list', {sort: 'remindtime',order:'asc',limit: 10000}).then(async res=>{
				if(res.data.list.length){
					for(let x in res.data.list){
						if(res.data.list[x].type=='全局'||(res.data.list[x].type=='个人'&&res.data.list[x].userid==wx.getStorageSync('appUserid'))){
							if(this.compareDate(utils.getCurDateTime(),res.data.list[x].remindtime)){
								await api.get('storeup/page', {
									refid: res.data.list[x].id,
									tablename: 'popupremind',
									type: '61'
								}).then(async obj=>{
									if(!obj.data.list.length){
										res.data.list[x].content = this.removeHtmlTags(res.data.list[x].content)
										await wx.showModal({
											title: res.data.list[x].title,
											content: res.data.list[x].content
										})
										await api.post('storeup/add', {
											userid: wx.getStorageSync('appUserid'),
											refid: res.data.list[x].id,
											tablename: 'popupremind',
											name: res.data.list[x].title,
											type: '61',
										}).then(rs=>{})
									}
								})
							}
						}
					}
				}
			})
		}
		this.remindTimer = setTimeout(()=>{
			this.getRemindAll()
		},20000)
	},
	globalData: {
		userInfo: null
	},
	utils: utils,
	menu: menu.menu,
	api: api,
	base: base,
	validate: validate,
})