const api = getApp().api
const base = getApp().base

Page({
  data: {
    jibie: '',
    currentLevel: '',
    tingliList: [],
    baseUrl: '',
    levelList: ['小学', '初中', '高中', '四级', '六级', '考研'],
    iconMap: {
      '小学': 'cuIcon-child',
      '初中': 'cuIcon-friend',
      '高中': 'cuIcon-milk',
      '四级': 'cuIcon-vipcard',
      '六级': 'cuIcon-crown',
      '考研': 'cuIcon-read'
    }
  },

  onLoad(options) {
    this.setData({
      baseUrl: base.base.url
    })
  },

  selectLevel(e) {
    let level = e.currentTarget.dataset.level
    console.log('点击级别:', level)
    wx.navigateTo({
      url: `/pages/tingli/audio-list?jibie=${encodeURIComponent(level)}`
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
