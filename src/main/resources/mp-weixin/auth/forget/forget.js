const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const base = getApp().base
const validate = getApp().validate
Page({
	data: {
		index: 0,
		tableName: '',
		type: 1,
		repassword: '',
		password: '',
		panswer1: '',
		username: '',
		userForm: {}
	},
	onLoad() {
		
	},
	// 输入框
	bindKeyInput(e) {
		this.data[e.target.dataset.name] = e.detail.value
	},
	// 下拉框
	optionsChange(e) {
		this.setData({
			index: e.detail.value
		})
	},
	// 获取用户信息
	nextClick() {
		if(!this.data.username) {
			utils.msg('请输入账号')
			return;
		}
		this.data.tableName = 'xuesheng'
		api.get(`${this.data.tableName}/security`,{username: this.data.username}).then((res)=>{
			if(!res.data) {
				utils.msg('用户不存在')
				return false
			}
			if (res.code == 0) {
				this.setData({
					userForm: res.data,
					type: 2
				})
			}
		})
	},
	confirmClick() {
		if (this.data.userForm.panswer !== this.data.panswer1) {
			utils.msg('密保答案不正确')
			return false
		}
		utils.msg('验证成功')
		this.password = ''
		setTimeout(() => {
			this.setData({
				password: '',
				type: 3
			})
		}, 1000)
	},
	async updateClick() {
		if(!this.data.password) {
			utils.msg('密码不能为空')
			return false
		}
		if (this.data.password != this.data.repassword) {
			utils.msg('两次密码输入不一致')
			return false
		}
		if (this.data.tableName == 'xuesheng') {
		}
		if(this.data.userForm.mima) {
			this.data.userForm.mima=this.data.password;
		} else {
			this.data.userForm.password=this.data.password;
		}
		api.post(`${this.data.tableName}/update`,this.data.userForm).then((res)=>{
			utils.msg('密码修改成功')
			setTimeout(() => {
				wx.navigateBack({
				
				})
			}, 1000)
		})
	},
})