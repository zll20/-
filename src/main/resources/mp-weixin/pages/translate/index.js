const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const base = getApp().base
const validate = getApp().validate
Page({
	data: {
		fromtext:'',
		totext:'',
		from: 'zh',
		to: 'en',
		timer: null,
		langList: [
			{
				name: '中文',
				value: 'zh'
			},
			{
				name: '英文',
				value: 'en'
			},
			{
				name: '法语',
				value: 'fra'
			},
			{
				name: '俄语',
				value: 'ru'
			},
			{
				name: '德语',
				value: 'de'
			},
		]
	},
	langChange1(e){
		this.setData({
			from: this.data.langList[e.detail.value].value
		})
	},
	langChange2(e){
		this.setData({
			to: this.data.langList[e.detail.value].value
		})
	},
	translateChange(e){
		let that = this
		if(this.data.timer) {
			clearTimeout(this.data.timer)
		}
		if(!e.detail.value){
			return false
		}
		this.data.timer = setTimeout(async()=>{
			api.get('baidu/texttrans', {from: 'auto',to: that.data.to,t: e.detail.value}).then(res=>{
				that.setData({
					totext: res.data
				})
			})
		},1000)
	}
})