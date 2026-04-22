const api = getApp().api
const base = getApp().base

Page({
  data: {
    jibie: '',
    wenkanList: [],
    baseUrl: ''
  },

  onLoad(options) {
    console.log('wenkan-list onLoad 接收到的参数:', options)
    if (options.jibie) {
      let jibie = decodeURIComponent(options.jibie)
      console.log('解码后的级别:', jibie)
      this.setData({
        jibie: jibie
      })
      wx.setNavigationBarTitle({
        title: jibie + '文刊'
      })
      this.loadWenkanList(jibie)
    }
    this.setData({
      baseUrl: base.base.url
    })
  },

  loadWenkanList(level) {
    wx.showLoading({ title: '加载中...' })
    console.log('=== 请求文刊列表 ===')
    console.log('级别（未编码）:', level)
    api.get('wenkan/list', {
      jibie: level,
      sort: 'id',
      order: 'desc',
      page: 1,
      limit: 100
    }).then(res => {
      wx.hideLoading()
      console.log('=== 接口返回原始数据 ===')
      console.log('res:', JSON.stringify(res))
      console.log('res.code:', res.code)
      console.log('res.data:', JSON.stringify(res.data))

      if (res.code === 0) {
        let list = []
        if (res.data && res.data.list) {
          list = res.data.list
        }
        console.log('解析后的列表数据:', list.length, '条')
        if (list.length > 0) {
          console.log('第一条数据:', list[0])
        }
        this.setData({
          wenkanList: list
        })
      } else {
        this.setData({
          wenkanList: []
        })
        wx.showToast({
          title: '加载失败',
          icon: 'none'
        })
      }
    }).catch(err => {
      wx.hideLoading()
      console.error('加载文刊列表失败:', err)
      wx.showToast({
        title: '网络错误',
        icon: 'none'
      })
    })
  },

  viewWenkan(e) {
    const item = e.currentTarget.dataset.item
    console.log('点击的文刊数据:', item)
    wx.navigateTo({
      url: `/pages/wenkan/wenkan-detail?id=${item.id}&mingcheng=${encodeURIComponent(item.mingcheng)}`
    })
  }
})
