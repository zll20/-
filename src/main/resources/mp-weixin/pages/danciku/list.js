const utils = getApp().utils
const api = getApp().api
const base = getApp().base

Page({
	data: {
		baseurl: base.base.url,
		jibieOptions: ['小学', '初中', '高中', '四级', '六级', '考研'],
		searchForm: {
			danci: '',
		},
		searchResultList: [],
		showLevelSelector: false,
		scrollTop: 0,
	},
	async onShow() {
		this.setData({
			searchResultList: []
		})
	},
	onPageScroll(e) {
		this.setData({
			scrollTop: e.scrollTop
		})
	},
	scrollTopClick() {
		wx.pageScrollTo({
			scrollTop: 0
		})
	},
	// 显示级别选择器
	showLevelSelector() {
		this.setData({
			showLevelSelector: true
		})
	},
	// 隐藏级别选择器
	hideLevelSelector() {
		this.setData({
			showLevelSelector: false
		})
	},
	// 进入某个级别的单词
	async enterLevel(e) {
		let jibie = e.currentTarget.dataset.jibie
		this.setData({
			showLevelSelector: false
		})

		try {
			// 获取该级别的第一个单词
			let res = await api.get('danciku/list', {
				page: 1,
				limit: 1,
				sort: 'id',
				order: 'asc',
				jibie: jibie
			})

			if (res.data.list && res.data.list.length > 0) {
				// 跳转到该单词的详情页，并标记从列表页进入
				let firstWord = res.data.list[0]
				utils.jump(`./detail?id=${firstWord.id}&fromList=1&jibie=${jibie}`)
			} else {
				utils.msg(`暂无${jibie}级别的单词`)
			}
		} catch(err) {
			console.error('获取单词失败：', err)
			utils.msg('加载失败，请重试')
		}
	},
	// 搜索框输入
	searchKeyInput(e) {
		this.setData({
			[`searchForm.${e.target.dataset.name}`]: e.detail.value
		})
	},
	// 搜索单词
	async search() {
		if (!this.data.searchForm.danci) {
			utils.msg('请输入搜索内容')
			return
		}

		try {
			let res = await api.get('danciku/list', {
				page: 1,
				limit: 20,
				danci: '%' + this.data.searchForm.danci + '%'
			})
			this.setData({
				searchResultList: res.data.list
			})

			if (this.data.searchResultList.length === 0) {
				utils.msg('未找到相关单词')
			}
		} catch(err) {
			console.error('搜索失败：', err)
			utils.msg('搜索失败，请重试')
		}
	},
	// 点击搜索结果进入详情
	onDetailTap(e) {
		let item = e.currentTarget.dataset.item
		utils.jump(`./detail?id=${item.id}&fromList=1&jibie=${item.jibie}`)
	},
})