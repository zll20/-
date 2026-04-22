const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const base = getApp().base
const validate = getApp().validate
Page({
	data: {
		baseurl: base.base.url,
		tableName: '',
		user: {},
		role: '',
		menuList: [],
		recommendList: [],
		recommendTable: '',
		recommendTitle: '',
		recommendPicture: '',
		showType: false,
		passwordForm: {
			mima: '',
			newmima: '',
			newmima1: '',
		},
		updatePasswords: false,
		showSettings: false,
	},
	onLoad() {
	},
	onShow() {
		if(wx.getStorageSync('appToken')) {
			let menus = menu.list();
			this.setData({
				role: wx.getStorageSync("appRole"),
				menuList: menus,
			})
			this.getSession()
			wx.removeStorageSync("useridTag");
		} else {
			this.setData({
				user: {},
				tableName: '',
				role: '',
				menuList: [],
			})
		}
	},
	settingClick() {
		this.setData({
			showSettings: true
		})
	},
	settingClose() {
		this.setData({
			showSettings: false
		})
	},
	logout() {
		wx.clearStorageSync()
		this.setData({
			user: {},
			tableName: '',
			role: '',
			menuList: [],
			showSettings: false
		})
		utils.jump('/auth/login/login')
	},
	userinfoClick() {
		utils.jump('/auth/user-info/user-info')
	},
	loginClick() {
		utils.jump('/auth/login/login')
	},
	onPageTap(e) {
		wx.setStorageSync("useridTag",1);
		let url = e.currentTarget.dataset.url;
		let tableName = e.currentTarget.dataset.tableName
		if(tableName&&(tableName=='examrecord'||tableName=='examfailrecord')) {
			url = `/exam/${tableName}/list?userid=${this.data.user.id}`
		}
		utils.jump(url)
	},
	async getSession(){
		let table = wx.getStorageSync("nowTable");
		api.get(`${table}/session`,{}).then((res)=>{
			this.setData({
				tableName: table,
				user: res.data
			})
			this.getStoreup()
		})
		
	},
	async getStoreup(){
		if(!this.data.user||!this.data.user.id){
			return false
		}
		await api.get('storeup/page', {userid: this.data.user.id, type: 1}).then(rs=>{
			this.setData({
				[`user.storenum`]: Number(rs.data.total)
			})
		})
	},

	async getRecommendList(){
		let params = {
			page: 1,
			limit: 6,
		}
		let url = 'danciku/autoSort';
		if(wx.getStorageSync("appUserid")) {
			url = 'danciku/autoSort2'
		}
		api.get(url, params).then((res)=>{
			this.setData({
				recommendList: res.data.list,
				recommendTable: 'danciku',
				recommendTitle: 'danci',
				recommendPicture: 'fengmiantu',
			})
		})
	},
	recommendDetail(e) {
		let id = e.currentTarget.dataset.id;
		utils.jump(`../${this.data.recommendTable}/detail?id=${id}`)
		
	},

	passwordShow() {
		this.setData({
			showSettings: false
		})
		let passwordForm = {
			mima: '',
			newmima: '',
			newmima1: '',
		}
		this.setData({
			passwordForm: passwordForm,
			updatePasswords: true
		})
	},
	passwordClose() {
		this.setData({
			updatePasswords: false
		})
	},
	psdKeyInput(e){
		this.data.passwordForm[e.target.dataset.name] = e.detail.value
	},
	async updatePassword() {
		if (this.data.passwordForm.mima == ''){
			utils.msg('原密码不能为空')
			return false
		}
		if (this.data.passwordForm.newmima == ''){
			utils.msg('新密码不能为空')
			return false
		}
		if (this.data.passwordForm.newmima1 == ''){
			utils.msg('确认密码不能为空')
			return false
		}
		let password = ''
		if (this.data.user.mima) {
			password = this.data.user.mima;
		}
		if(password != this.data.passwordForm.mima){
			utils.msg('原密码不正确')
			return false
		}
		if (this.data.passwordForm.newmima != this.data.passwordForm.newmima1){
			utils.msg('两次密码不一致')
			return false
		}
		if (this.data.passwordForm.mima == this.data.passwordForm.newmima){
			utils.msg('新密码与原密码相同！')
			return false
		}
		this.data.user.mima = this.data.passwordForm.newmima
		api.post(`${this.data.tableName}/update`,this.data.user).then((rs)=>{
			utils.msg('修改密码成功,下次登录系统生效')
			this.getSession()
			this.setData({
				updatePasswords: false
			})
		})
	},
})