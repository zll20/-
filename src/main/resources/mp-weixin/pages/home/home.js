const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const base = getApp().base
const validate = getApp().validate
Page({
	data: {
		baseurl: base.base.url,
		menuList: [],
		role: '',
		user: {},
		tableName:'',
		dancikulist: [],
		scrollTop: 0,
		menuColor: '#409eff,#67c23a,#909399,#e6a23c,#f56c6c,#356c6c,#351c6c,#f093a9,#a7c23a,#104eff,#10441f,#a21233,#503319'.split(','),
	},
	onPageScroll(e) {
		this.setData({
			scrollTop: e.scrollTop
		})
	},
	onLoad() {
		this.data.menuColor = this.data.menuColor.sort(()=> {
			return (0.5-Math.random());
		});
		this.setData({
			menuColor: this.data.menuColor
		})
	},
	onShow() {
		let swiperMenuList = []
		let role = wx.getStorageSync("appRole");
		let table = wx.getStorageSync("nowTable");
		let menus = menu.list();
		let menuList = menus;

		this.setData({
			role: role,
			tableName: table,
			menuList: menuList,
		})

		if(table) {
			api.get(`${table}/session`,{}).then((res)=>{
				this.setData({
					user: res.data
				})
			})
		}

		this.getList()
	},
	checkLogin() {
		let role = wx.getStorageSync("appRole");
		if (!role) {
			wx.showToast({
				title: '请先登录',
				icon: 'none',
				duration: 2000
			});
			setTimeout(() => {
				wx.navigateTo({
					url: '/auth/login/login'
				});
			}, 2000);
			return false;
		}
		return true;
	},
	onPageTapCustom(e) {
		let type = e.currentTarget.dataset.type
		if(type === 'tingli') {
			if (!this.checkLogin()) return
			utils.jump('/pages/tingli/tingli-list')
		} else if(type === 'wenkan') {
			if (!this.checkLogin()) return
			utils.jump('/pages/wenkan/wenkan-index')
		}
	},
	onSwiperTap(e) {
		if(e.url) {
			if (e.url.indexOf('https') != -1) {
				// #ifdef MP-WEIXIN
				wx.navigateTo({
					url: '/auth/linkOthers/linkOthers?url=' + encodeURIComponent(e.url),
				});
				return false
				// #endif
				window.open(e.url)
			} else {
				this.$utils.jump(e.url)
			}
		}
	},
	getList() {
		this.getdancikuRecom()
	},
	getdancikuRecom() {
		let params = {
			page: 1,
			limit: 4,
		}
		let url = 'danciku/autoSort'
		if(wx.getStorageSync("appUserid")) {
			url = 'danciku/autoSort2'
		}
		api.get(url,params).then((res)=>{
			res.data.list = res.data.list.map(item=>{
				item.fengmiantu = (item.fengmiantu?item.fengmiantu.split(',')[0]:'')
				return item;
			})
			this.setData({
				dancikulist: res.data.list,
			})
		})
	},
	autoPlayList4() {
		var numList4 = this.data.numList4
		numList4++
		if (numList4 == this.data.swiperList.length) numList4 = 0
		this.setData({
			prevNumList4: this.data.numList4,
			numList4: numList4
		})
	},
	touchStart(event) {
		this.setData({
			startX: event.touches[0].clientX
		})
		
		clearInterval(this.data.timerList4)
		this.setData({
			flagList4: true
		})
	},
	touchMove(event) {
		const currentX = event.touches[0].clientX;
		const deltaX = currentX - this.data.startX;
		
		if (deltaX > 50) {
			// 向右滑动逻辑
			if (this.data.flagList4) {
				this.setData({
					flagList4: false
				})
				var numList4 = this.data.numList4
				numList4--
				if (numList4 < 0) numList4 = this.data.swiperList.length - 1
				this.setData({
					prevNumList4: this.data.numList4,
					numList4: numList4
				})
			}
			
		} else if (deltaX < -50) {
			// 向左滑动逻辑
			if (this.data.flagList4) {
				this.setData({
					flagList4: false
				})
				
				var numList4 = this.data.numList4
				numList4++
				if (numList4 == this.data.swiperList.length) numList4 = 0
				this.setData({
					prevNumList4: this.data.numList4,
					numList4: numList4
				})
			}
		}
	},
	touchEnd() {
		this.setData({
			startX: 0,
			flagList4: false
		})
		this.data.timerList4 = setInterval(this.autoPlayList4, 3000)
	},
	onDetailTap(e) {
		let tableName = e.currentTarget.dataset.tablename;
		let id = e.currentTarget.dataset.id;
		utils.jump(`../${tableName}/detail?id=${id}`)
	},
	onPageTap(e) {
		let tableName = e.currentTarget.dataset.tablename;
		if(tableName=='danciku') {
			if (!this.checkLogin()) return
		}
		let url = `../${tableName}/list`
		if(tableName=='forum') {
			if (!this.checkLogin()) return
			url = '../forum-index/forum-index'
		}
		utils.jump(url);
	},
	onPageTap2(e) {
		let tableName = e.currentTarget.dataset.tablename;
		if(tableName=='danciku') {
			if (!this.checkLogin()) return
		}
		if(tableName=='forum'){
			if (!this.checkLogin()) return
		}
		let url = '../' + tableName + '/list'
		if(tableName=='forum'){
			url = '../forum-index/forum-index'
		}
		utils.jump(url);
	},
	scrollTopClick(){
		wx.pageScrollTo({
			scrollTop: 0
		})
	},
})