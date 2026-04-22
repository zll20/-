const api = getApp().api
const base = getApp().base

Page({
  data: {
    jibie: '',
    tingliList: [],
    baseUrl: ''
  },

  onLoad(options) {
    console.log('audio-list onLoad 接收到的参数:', options)
    if (options.jibie) {
      // 先解码URL参数
      let jibie = decodeURIComponent(options.jibie)
      console.log('解码后的级别:', jibie)
      this.setData({
        jibie: jibie
      })
      // 设置页面标题
      wx.setNavigationBarTitle({
        title: jibie + '听力材料'
      })
      // 加载数据（使用未编码的jibie）
      this.loadTingliList(jibie)
    }
    this.setData({
      baseUrl: base.base.url
    })
  },

  loadTingliList(level) {
    wx.showLoading({ title: '加载中...' })
    console.log('=== 请求听力列表 ===')
    console.log('级别（未编码）:', level)
    api.get('tingli/list', {
      jibie: level,  // 直接传原始字符串，不要编码
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
        // res 是 {code:0, data:{total:2, list:[...]}}
        // res.data 是 {total:2, list:[...]}
        // res.data.list 是数组
        if (res.data && res.data.list) {
          list = res.data.list
        }
        console.log('解析后的列表数据:', list.length, '条')
        if (list.length > 0) {
          console.log('第一条数据:', list[0])
        }
        this.setData({
          tingliList: list
        })
      } else {
        this.setData({
          tingliList: []
        })
        wx.showToast({
          title: '加载失败',
          icon: 'none'
        })
      }
    }).catch(err => {
      wx.hideLoading()
      console.error('加载听力材料失败:', err)
      wx.showToast({
        title: '网络错误',
        icon: 'none'
      })
    })
  },

  playTingli(e) {
    const item = e.currentTarget.dataset.item
    console.log('点击的音频数据:', item)
    if (!item.shiyinshipin) {
      wx.showToast({
        title: '该听力材料暂无音频',
        icon: 'none'
      })
      return
    }

    wx.navigateTo({
      url: `/pages/tingli/tingli-play?shiyinshipin=${encodeURIComponent(item.shiyinshipin)}&mingcheng=${encodeURIComponent(item.mingcheng)}&baseUrl=${encodeURIComponent(this.data.baseUrl)}&id=${item.id}`
    })
  }
})
