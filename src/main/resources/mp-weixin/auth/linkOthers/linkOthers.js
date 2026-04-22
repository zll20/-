// auth/linkOthers/linkOthers.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    url: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.data.url = decodeURIComponent(options.url);
    // 设置当前的title 如果外链中有的话将被覆盖
    if (this.isNotEmpty(options.title)) {
      this.setTitle(options.title);
    }
    this.setData(({
      url: this.data.url
    }))
  },
  isNotEmpty(obj) {
    if (typeof obj == undefined || obj == null || obj == "" || obj == "undefined" || obj.length == 0) {
      return false;
    } else {
      return true;
    }
  },
  // 设置title
  setTitle(title) {
    wx.setNavigationBarTitle({
      title: title
    })
  },
})