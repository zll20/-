const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const base = getApp().base
const validate = getApp().validate
Page({
	data: {
		baseurl: base.base.url,
		loginType: 1,
		username: '',
		password: '',
		options: [
			'请选择登录用户类型',
		],
		optionsValues: [
			'',
			'xuesheng',
		],
		roleNum: 0,
		index: 0,
		phone: '',
		phonecode: '',
		phoneroles: [
			{
				tableName: '',
				roleName: '请选择登录用户类型'
			},
			{
				tableName: 'xuesheng',
				roleName: '学生'
			},
		],
		phonerolesIndex: 0,
		count: 60,
		inter: null,
		isEndFlag: true,
		emailText: '',
		indexBgUrl: '',
		indexLogoUrl: '',
	},
	onLoad() {
		let options = ['请选择登录用户类型'];
		let menus = menu.list();
		this.data.menuList = menus;
		for(let i=0;i<this.data.menuList.length;i++){
			if(this.data.menuList[i].hasFrontLogin=='是'){
				options.push(this.data.menuList[i].roleName);
				this.data.roleNum++;
			}
		}
		if(this.data.roleNum==1) {
			this.data.index = 1;
		}
		this.setData({
			roleNum: this.data.roleNum,
			options: options
		})
		this.styleChange()
	},
	async styleChange() {
		api.get('config/info?name=appLoginBackgroundImg').then((rs)=>{
			api.get('config/info?name=appLoginLogo').then((rs2)=>{
				this.setData({
					indexBgUrl: rs.data&&rs.data.value?(this.data.baseurl + rs.data.value):'',
					indexLogoUrl: rs2.data&&rs2.data.value?(this.data.baseurl + rs2.data.value):'http://codegen.caihongy.cn/20250814/2cb518a34dd04bd1b6c5e52e74d8f581.jpg'
				})
			})
		})
	},
	bindKeyInput(e){
		this.setData({
			[e.target.dataset.name]:e.detail.value
		})
	},
	

	async phoneClick() {
		var roles = ''
		if(!this.data.phone){
			utils.msg(`手机号不能为空`);
			return
		}
		if(this.data.phone&&(!validate.isMobile(this.data.phone))){
			utils.msg(`请输入正确的手机号格式`);
			return
		}
		if (this.data.phoneroles.length>2) {
			if (!this.data.phoneroles[this.data.phonerolesIndex].tableName) {
				utils.msg("请选择登录用户类型");
				return false;
			}
			roles = this.data.phoneroles[this.data.phonerolesIndex].tableName
		}else {
			roles = this.data.phoneroles[1].tableName
		}
		var _this = this;
		api.get(`${roles}/sendsms/login?mobile=${this.data.phone}`).then((res)=>{
			if(res.code==0) {
				this.setData({
					isEndFlag: false
				})
				this.data.inter = setInterval(function() {
					_this.data.count = _this.data.count - 1;
					_this.setData({
						count: _this.data.count,
						emailText: parseInt(_this.data.count) + '秒后重发'
					})
					if (_this.data.count <= 0) {
						clearInterval(_this.data.inter);
						_this.setData({
							count: 60,
							isEndFlag: true,
							emailText: '60秒后重发'
						})
					}
				}, 1000);
				utils.msg("发送成功！");
			}else {
				utils.msg(res.msg);
			}
		})
	},
	async phoneLogin() {
		var roles = ''
		var rolesName = ''
		if (this.data.phoneroles.length>2) {
			roles = this.data.phoneroles[this.data.phonerolesIndex].tableName
			rolesName = this.data.phoneroles[this.data.phonerolesIndex].roleName
		}else {
			roles = this.data.phoneroles[1].tableName
			rolesName = this.data.phoneroles[1].roleName
		}
		api.post(`${roles}/sms/login?mobile=${this.data.phone}&&smscode=${this.data.phonecode}`).then((res)=>{
			if(res.code==0) {
				wx.removeStorageSync("useridTag");
				wx.setStorageSync("appToken", res.token);
				wx.setStorageSync("nickname",res.username);
				wx.setStorageSync("nowTable", `${roles}`);
				api.get(`${roles}/session`).then((rs)=>{
					if(rs.data.touxiang) {
						wx.setStorageSync('frontHeadportrait', rs.data.touxiang);
					} else if(rs.data.headportrait) {
						wx.setStorageSync('frontHeadportrait', rs.data.headportrait);
					}
					if(roles== 'xuesheng') {
						wx.setStorageSync('appExamName', rs.data.xingming);
					}
					wx.setStorageSync('userSession',JSON.stringify(rs.data))
					wx.setStorageSync("appUserid", rs.data.id);
					wx.setStorageSync("zhanghao", rs.data.zhanghao);
					if(rs.data.vip) {
						wx.setStorageSync("vip", rs.data.vip);
					}
					wx.setStorageSync("appRole", `${rolesName}`);
					utils.tab('/pages/home/home');
				})
			}
		})
	},
	phonerolesChange(e){
		this.setData({
			phonerolesIndex: e.detail.value
		})
	},
	optionsChange(e) {
		this.setData({
			index: e.detail.value
		})
	},
	changeLogin(e) {
		this.setData({
			loginType: e.target.dataset.type
		})
	},
	onLoginTap() {
		if(this.data.loginType==1) {
			if (!this.data.username) {
				utils.msg('请输入手机号')
				return
			}
			if(this.data.username&&(!validate.isMobile(this.data.username))){
				utils.msg('请输入正确的手机号格式');
				return
			}
			if (!this.data.password) {
				utils.msg('请输入密码')
				return
			}
		}
		if(this.data.loginType==4) {
			if (!this.data.phone) {
				utils.msg('请输入手机号')
				return
			}
			if (!this.data.phonecode) {
				utils.msg('请输入手机验证码')
				return
			}
			if (this.data.phoneroles.length>2&&!this.data.phoneroles[this.data.phonerolesIndex].tableName) {
				utils.msg('请选择登录用户类型')
				return
			}
		}
		this.loginPost()

	},
	async loginPost() {
		let that = this
		if(this.data.loginType==4) {
			this.phoneLogin()
			return false
		}
		api.getNoLogin(`${this.data.optionsValues[this.data.index]}/loginByMobile`,{
			mobile: this.data.username,
			password: this.data.password
		}).then((res)=>{
			if(res.code==0) {
				wx.showToast({
					title: '您已成功登录',
					icon: 'success',
					duration: 2000
				});
				wx.removeStorageSync("useridTag");
				wx.setStorageSync("appToken", res.token);
				wx.setStorageSync("nickname",res.data.nicheng?res.data.nicheng:res.data.zhanghao);
				wx.setStorageSync("nowTable", `${this.data.optionsValues[this.data.index]}`);
				api.get(`${this.data.optionsValues[this.data.index]}/session`).then((rs)=>{
					if(rs.data.touxiang) {
						wx.setStorageSync('frontHeadportrait', rs.data.touxiang);
					} else if(rs.data.headportrait) {
						wx.setStorageSync('frontHeadportrait', rs.data.headportrait);
					}
					if(that.data.optionsValues[that.data.index]== 'xuesheng') {
						wx.setStorageSync('appExamName', rs.data.xingming);
					}
					wx.setStorageSync('userSession',JSON.stringify(rs.data))
					wx.setStorageSync("appUserid", rs.data.id);
					wx.setStorageSync("zhanghao", rs.data.zhanghao);
					if(rs.data.vip) {
						wx.setStorageSync("vip", rs.data.vip);
					}
					wx.setStorageSync("appRole", `${that.data.options[that.data.index]}`);
					utils.tab('/pages/home/home')
				})
			}else {
				utils.msg(res.msg);
			}
		})
	},
	onRegisterTap(e) {
		utils.jump('../register/register?loginTable=' + e.target.dataset.role)
	},
	onForgetTap() {
		utils.jump('../forget/forget')
	},
})