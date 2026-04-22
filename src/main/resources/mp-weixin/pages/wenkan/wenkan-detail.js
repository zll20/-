const api = getApp().api

Page({
  data: {
    id: '',
    mingcheng: '',
    wenkanneirong: '',
    studyStartTime: 0, // 学习开始时间戳
  },

  onLoad(options) {
    this.data.studyStartTime = Date.now(); // 开始计时
    console.log('文刊详情参数:', options)
    if (options.id) {
      this.setData({
        id: options.id
      })
      this.loadWenkanDetail(options.id)
    }
    if (options.mingcheng) {
      this.setData({
        mingcheng: decodeURIComponent(options.mingcheng)
      })
      wx.setNavigationBarTitle({
        title: decodeURIComponent(options.mingcheng)
      })
    }
  },

  onUnload() {
    this.submitStudyRecord(); // 退出时提交学习记录
  },

  loadWenkanDetail(id) {
    wx.showLoading({ title: '加载中...' })
    api.get(`wenkan/info/${id}`, {}).then(res => {
      wx.hideLoading()
      console.log('文刊详情数据:', res)
      if (res.code === 0 && res.data) {
        this.setData({
          wenkanneirong: res.data.wenkanneirong || '<p>暂无内容</p>',
          mingcheng: res.data.mingcheng || this.data.mingcheng
        })
      } else {
        wx.showToast({
          title: '加载失败',
          icon: 'none'
        })
      }
    }).catch(err => {
      wx.hideLoading()
      console.error('加载文刊详情失败:', err)
      wx.showToast({
        title: '网络错误',
        icon: 'none'
      })
    })
  },

  // 提交文刊学习记录
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
		module: 'wenkan',
		studyTime: studyTime,
		wordCount: 0
	}).then(res => {
		console.log('提交文刊学习记录成功', res);
	}).catch(err => {
		console.error('提交文刊学习记录失败', err);
	});
  }
})
