const base = getApp().base

Page({
  data: {
    currentLevel: '',
    levelList: ['小学', '初中', '高中', '四级', '六级', '考研']
  },

  onLoad(options) {
    if (options.jibie) {
      let jibie = decodeURIComponent(options.jibie)
      this.setData({
        currentLevel: jibie
      })
    }
  },

  selectLevel(e) {
    let level = e.currentTarget.dataset.level
    this.setData({
      currentLevel: level
    })
    wx.navigateTo({
      url: `/pages/wenkan/wenkan-list?jibie=${encodeURIComponent(level)}`
    })
  }
})
