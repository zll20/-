const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const base = getApp().base
const validate = getApp().validate
Page({
	data: {
		id: 0,
		detail: {},
		isAuth: [],
		userid: '',
		title: '',
		baseurl: base.base.url,
		user: null,
		getUserType: true,
		username: wx.getStorageSync("nickname"),
		swiperList: [],
		commentList: [],
		listQuery: {
			page: 1,
			limit: 10
		},
		// 收藏
		storeupFlag: 0,
		scrollTop: 0,
		// 单词切换
		hasNext: true,
		hasPrev: true,
		showNavigation: false,
		currentJibie: '',
		// 音频播放
		innerAudioContext: null,
		studyStartTime: 0, // 学习开始时间戳
		wordCount: 0, // 本次学习单词数
	},
	async onLoad(options) {
		console.log('=== onLoad 被调用，options:', options)
		// 初始化音频播放器
		this.data.innerAudioContext = wx.createInnerAudioContext()
		this.data.innerAudioContext.onError((res) => {
			console.error('音频播放失败:', res)
			wx.showToast({
				title: '音频播放失败',
				icon: 'none'
			})
		})
		
		if(options.id) {
			this.data.id = options.id
		}
		if(options.userid) {
			this.data.userid = options.userid
		}
		// 判断是否从列表页进入（带fromList和jibie参数）
		if(options.fromList == '1' && options.jibie) {
			this.data.currentJibie = options.jibie
			this.data.showNavigation = true
			console.log('✅ 从列表页进入，设置 showNavigation = true, currentJibie =', this.data.currentJibie)
		} else {
			console.log('❌ 不是从列表页进入，showNavigation = false')
		}
		if(wx.getStorageSync('appToken')) {
			this.data.getUserType = false
			// 获取用户信息
			await api.get(`${wx.getStorageSync("nowTable")}/session`,{}).then((rs)=>{
				this.setData({
					user: rs.data
				})
			})
		}
		this.getDetail()
		this.data.studyStartTime = Date.now(); // 开始计时
	},
	onUnload() {
		this.submitStudyRecord(); // 退出时提交学习记录
		if(this.data.innerAudioContext) {
			this.data.innerAudioContext.destroy();
		}
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
	async onShow() {
		if(wx.getStorageSync('appToken')) {
			if(this.data.getUserType) {
				// 获取用户信息
				await api.get(`${wx.getStorageSync("nowTable")}/session`,{}).then((rs)=>{
					this.setData({
						user: rs.data
					})
				})
			}else {
				this.data.getUserType = true
			}
		}
		if(this.data.userid){
			this.setData({
				userid: this.data.userid,
				isAuth: utils.isAuth('danciku')
			})
		}else {
			this.setData({
				isAuth: utils.isAuthFront('danciku')
			})
		}
		let cleanType = wx.getStorageSync('discussdancikuCleanType')
		if(cleanType){
			wx.removeStorageSync('discussdancikuCleanType')
			this.getDetail(2);
		}
		let crossCleanType = wx.getStorageSync('crossCleanType')
		if(crossCleanType) {
			wx.removeStorageSync('crossCleanType')
			this.getDetail();
		}
	},
	// 播放读音
	playAudio() {
		let duyin = this.data.detail.duyin
		if(!duyin) {
			wx.showToast({
				title: '暂无读音',
				icon: 'none'
			})
			return
		}
		
		// 拼接完整URL：确保路径包含 upload/ 前缀
		let audioUrl = duyin
		
		// 如果已经是完整URL（http开头），直接使用
		if(duyin.indexOf('http') === 0) {
			audioUrl = duyin
		}
		// 如果路径已经包含 upload/，直接拼接baseurl
		else if(duyin.indexOf('upload/') === 0) {
			audioUrl = this.data.baseurl + duyin
		}
		// 否则加上 upload/ 前缀
		else {
			audioUrl = this.data.baseurl + 'upload/' + duyin
		}
		
		console.log('播放音频URL:', audioUrl)
		
		// 创建音频播放器
		const innerAudioContext = wx.createInnerAudioContext()
		innerAudioContext.src = audioUrl
		
		innerAudioContext.onPlay(() => {
			console.log('✅ 开始播放音频')
		})
		
		innerAudioContext.onError((res) => {
			console.error('❌ 音频播放失败:', res)
			wx.showToast({
				title: '播放失败',
				icon: 'none'
			})
		})
		
		innerAudioContext.play()
	},
	// 上一个单词（调用后端API）
	async prevWord() {
		console.log('=== prevWord 被调用 ===')
		try {
			let params = {}
			let jibie = this.data.currentJibie || this.data.detail.jibie
			if(jibie) {
				params.jibie = jibie
			}
			console.log('调用prev接口，参数：', params)

			// 使用 wx.request 直接调用，避免 api.get 自动显示错误
			let res = await new Promise((resolve) => {
				wx.request({
					url: this.data.baseurl + `danciku/prev/${this.data.id}`,
					data: params,
					method: 'GET',
					header: {
						'content-type': 'application/json'
					},
					success: (res) => {
						resolve(res.data)
					},
					fail: (err) => {
						resolve({ code: -1 })
					}
				})
			})
			
			console.log('prev接口返回：', res)
			
			if(res.code === 0 && res.data) {
				console.log('✅ 切换到上一个单词，新ID:', res.data.id)
				this.data.id = res.data.id
				this.getDetail()
				// 滚动到顶部
				wx.pageScrollTo({ scrollTop: 0 })
			} else {
				console.log('没有上一个单词了')
				this.setData({
					hasPrev: false
				})
			}
		} catch(err) {
			console.error('prevWord错误：', err)
			this.setData({
				hasPrev: false
			})
		}
	},
	// 下一个单词（调用后端API）
	async nextWord() {
		console.log('=== nextWord 被调用 ===')
		try {
			let params = {}
			let jibie = this.data.currentJibie || this.data.detail.jibie
			if(jibie) {
				params.jibie = jibie
			}
			console.log('调用next接口，参数：', params)

			// 使用 wx.request 直接调用，避免 api.get 自动显示错误
			let res = await new Promise((resolve) => {
				wx.request({
					url: this.data.baseurl + `danciku/next/${this.data.id}`,
					data: params,
					method: 'GET',
					header: {
						'content-type': 'application/json'
					},
					success: (res) => {
						resolve(res.data)
					},
					fail: (err) => {
						resolve({ code: -1 })
					}
				})
			})
			
			console.log('next接口返回：', res)
			
			if(res.code === 0 && res.data) {
				console.log('✅ 切换到下一个单词，新ID:', res.data.id)
				this.data.id = res.data.id
				this.getDetail()
				// 滚动到顶部
				wx.pageScrollTo({ scrollTop: 0 })
			} else {
				console.log('没有下一个单词了')
				this.setData({
					hasNext: false
				})
			}
		} catch(err) {
			console.error('nextWord错误：', err)
			this.setData({
				hasNext: false
			})
		}
	},
	// 检查导航按钮状态
	async checkNavigation() {
		console.log('=== checkNavigation 被调用 ===')
		try {
			let prevParams = {}
			let jibie = this.data.currentJibie || this.data.detail.jibie
			if(jibie) {
				prevParams.jibie = jibie
			}

			// 使用 wx.request 直接调用，避免 api.get 自动显示错误
			let prevRes = await new Promise((resolve) => {
				wx.request({
					url: this.data.baseurl + `danciku/prev/${this.data.id}`,
					data: prevParams,
					method: 'GET',
					header: {
						'content-type': 'application/json'
					},
					success: (res) => {
						resolve(res.data)
					},
					fail: (err) => {
						resolve({ code: -1 })
					}
				})
			})
			
			console.log('checkNavigation prev:', prevRes)
			this.setData({
				hasPrev: prevRes.code === 0
			})
		} catch(err) {
			console.log('没有上一个单词了')
			this.setData({
				hasPrev: false
			})
		}

		try {
			let nextParams = {}
			let jibie = this.data.currentJibie || this.data.detail.jibie
			if(jibie) {
				nextParams.jibie = jibie
			}

			// 使用 wx.request 直接调用，避免 api.get 自动显示错误
			let nextRes = await new Promise((resolve) => {
				wx.request({
					url: this.data.baseurl + `danciku/next/${this.data.id}`,
					data: nextParams,
					method: 'GET',
					header: {
						'content-type': 'application/json'
					},
					success: (res) => {
						resolve(res.data)
					},
					fail: (err) => {
						resolve({ code: -1 })
					}
				})
			})
			
			console.log('checkNavigation next:', nextRes)
			this.setData({
				hasNext: nextRes.code === 0
			})
		} catch(err) {
			console.log('没有下一个单词了')
			this.setData({
				hasNext: false
			})
		}
	},
	async getDetail(type) {
		console.log('=== getDetail 被调用，id:', this.data.id, 'type:', type)
		console.log('showNavigation:', this.data.showNavigation, 'currentJibie:', this.data.currentJibie)
		let table = wx.getStorageSync("nowTable");
		let url = `danciku/detail/${this.data.id}`
		if(this.data.userid) {
			url = `danciku/detail/${this.data.id}`
		}
		await api.get(url, {}).then(async (rs) => {
			console.log('单词详情数据：', rs.data)
			this.data.detail = rs.data
			this.data.title = rs.data.danci
			if(rs.data.fengmiantu) {
				if(rs.data.fengmiantu.indexOf(',') != -1) {
					this.data.swiperList = rs.data.fengmiantu.split(',')
				} else {
					this.data.swiperList = [rs.data.fengmiantu]
				}
			}
			this.setData({
				detail: this.data.detail,
				swiperList: this.data.swiperList,
				title: this.data.title,
				showNavigation: this.data.showNavigation
			})
			
			// 重置切换按钮状态
			this.setData({
				hasPrev: true,
				hasNext: true
			})
			
			// 如果是从列表页进入，检查导航按钮状态
			if(this.data.showNavigation) {
				console.log('🔍 调用 checkNavigation 检查边界')
				await this.checkNavigation()
			}
			
			if(type == 2) {
				this.getDiscussList()
			}
		})
		this.getStoreup()
		this.getDiscussList()
	},
	async getDiscussList() {
		let params = {
			page: this.data.listQuery.page,
			limit: this.data.listQuery.limit,
			refid: this.data.id,
			sort: 'addtime',
			order: 'desc'
		}
		await api.get('discussdanciku/list', params).then((rs) => {
			if(this.data.listQuery.page == 1) {
				this.data.commentList = rs.data.list
			} else {
				this.data.commentList = this.data.commentList.concat(rs.data.list)
			}
			this.setData({
				commentList: this.data.commentList
			})
		})
	},
	async getStoreup() {
		let table = wx.getStorageSync("nowTable");
		let res = await api.get('storeup/list', {
			page: 1,
			limit: 1,
			tablename: 'danciku',
			refid: this.data.id,
			userid: this.data.user?this.data.user.id:'',
		})
		if(res.data.list.length) {
			this.setData({
				storeupFlag: 1
			})
		} else {
			this.setData({
				storeupFlag: 0
			})
		}
	},
	async shoucang() {
		let table = wx.getStorageSync("nowTable");
		if(this.data.storeupFlag == 0) {
			await api.post('storeup/save', {
				tablename: 'danciku',
				refid: this.data.id,
				name: this.data.detail.danci,
				picture: this.data.detail.fengmiantu,
				userid: this.data.user?this.data.user.id:'',
			})
			this.setData({
				storeupFlag: 1
			})
			utils.msg('收藏成功')
		} else {
			let res = await api.get('storeup/list', {
				page: 1,
				limit: 100,
				tablename: 'danciku',
				refid: this.data.id,
				userid: this.data.user?this.data.user.id:'',
			})
			let arr = []
			for(let x in res.data.list) {
				arr.push(res.data.list[x].id)
			}
			await api.post('storeup/delete', JSON.stringify(arr))
			this.setData({
				storeupFlag: 0
			})
			utils.msg('取消收藏')
		}
	},
	onCommentTap() {
		wx.setStorageSync('discussdancikuRefid', this.data.id)
		utils.jump('../discussdanciku/add-or-update?refid=' + this.data.id)
	},
	comzanClick(e) {
		let item = e.currentTarget.dataset.item
		api.post('discussdanciku/thumbsup', {
			id: item.id,
			type: 1
		}).then(() => {
			this.getDiscussList()
		})
	},
	comcaiClick(e) {
		let item = e.currentTarget.dataset.item
		api.post('discussdanciku/thumbsup', {
			id: item.id,
			type: 2
		}).then(() => {
			this.getDiscussList()
		})
	},
	delClick(e) {
		let id = e.currentTarget.dataset.id
		wx.showModal({
			title: '提示',
			content: '是否确认删除',
			success: async (res) => {
				if (res.confirm) {
					await api.post('discussdanciku/delete', JSON.stringify([id]))
					utils.msg('删除成功')
					this.getDiscussList()
				}
			}
		})
	},
	// 提交单词库学习记录
	submitStudyRecord() {
		const endTime = Date.now();
		const studyTime = Math.floor((endTime - this.data.studyStartTime) / 1000);
		if (studyTime <= 0) return;
		
		const zhanghao = wx.getStorageSync('zhanghao') || wx.getStorageSync('appUserid');
		if (!zhanghao) {
			console.error('无法获取学生账号，未提交学习记录');
			return;
		}
		
		api.post('study/save', {
			xueshengZhanghao: zhanghao,
			module: 'danciku',
			studyTime: studyTime,
			wordCount: this.data.wordCount
		}).then(res => {
			console.log('提交单词库学习记录成功', res);
		}).catch(err => {
			console.error('提交单词库学习记录失败', err);
		});
	},
})