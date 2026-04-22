const base = getApp().base

Page({
  data: {
    audioUrl: '',
    mingcheng: '',
    isPlaying: false,
    currentTime: 0,
    duration: 0,
    currentTimeText: '00:00',
    durationText: '00:00',
    speedOptions: [
      { value: 0.5, label: '0.5x' },
      { value: 0.75, label: '0.75x' },
      { value: 1.0, label: '1.0x' },
      { value: 1.25, label: '1.25x' },
      { value: 1.5, label: '1.5x' },
      { value: 2.0, label: '2.0x' }
    ],
    speedIndex: 2,
    innerAudioContext: null,
    studyStartTime: 0, // 学习开始时间戳
  },

  onLoad(options) {
    this.data.studyStartTime = Date.now(); // 开始计时
    console.log('播放页面接收到的参数:', options)
    if (options.shiyinshipin) {
      const baseUrl = decodeURIComponent(options.baseUrl || '')
      let shiyinshipin = decodeURIComponent(options.shiyinshipin)
      
      console.log('baseUrl:', baseUrl)
      console.log('shiyinshipin:', shiyinshipin)
      
      // 直接拼接路径，不要双重编码
      if (shiyinshipin.startsWith('http')) {
        this.setData({
          audioUrl: shiyinshipin
        })
      } else {
        // 确保路径正确：baseUrl + shiyinshipin
        let fullUrl = baseUrl + shiyinshipin
        console.log('拼接后的音频URL:', fullUrl)
        this.setData({
          audioUrl: fullUrl
        })
      }
    }
    if (options.mingcheng) {
      this.setData({
        mingcheng: decodeURIComponent(options.mingcheng)
      })
    }
    wx.setNavigationBarTitle({
      title: this.data.mingcheng || '听力播放'
    })
  },

  onReady() {
    const innerAudioContext = wx.createInnerAudioContext()
    
    console.log('=== 开始设置音频播放器 ===')
    console.log('音频URL:', this.data.audioUrl)
    
    innerAudioContext.src = this.data.audioUrl
    innerAudioContext.autoplay = false
    innerAudioContext.loop = false

    innerAudioContext.onPlay(() => {
      console.log('✅ 开始播放音频')
      this.setData({ isPlaying: true })
    })

    innerAudioContext.onPause(() => {
      console.log('⏸ 暂停播放')
      this.setData({ isPlaying: false })
    })

    innerAudioContext.onEnded(() => {
      console.log('⏹ 播放结束')
      this.setData({
        isPlaying: false,
        currentTime: 0,
        currentTimeText: '00:00'
      })
    })

    innerAudioContext.onCanplay(() => {
      console.log('✅ 音频可以播放了')
    })

    innerAudioContext.onWaiting(() => {
      console.log('⏳ 等待音频加载')
    })

    innerAudioContext.onTimeUpdate(() => {
      const currentTime = innerAudioContext.currentTime
      const duration = innerAudioContext.duration || this.data.duration
      this.setData({
        currentTime: currentTime,
        duration: duration,
        currentTimeText: this.formatTime(currentTime),
        durationText: this.formatTime(duration)
      })
    })

    innerAudioContext.onError((res) => {
      console.error('❌ 音频播放错误:', res)
      console.error('错误的URL:', this.data.audioUrl)
      wx.showToast({
        title: '音频加载失败',
        icon: 'none'
      })
    })

    this.setData({
      innerAudioContext: innerAudioContext
    })
  },

  onUnload() {
    this.submitStudyRecord(); // 退出时提交学习记录
    if (this.data.innerAudioContext) {
      this.data.innerAudioContext.stop()
      this.data.innerAudioContext.destroy()
    }
  },

  // 提交听力学习记录
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
		module: 'tingli',
		studyTime: studyTime,
		wordCount: 0
	}).then(res => {
		console.log('提交听力学习记录成功', res);
	}).catch(err => {
		console.error('提交听力学习记录失败', err);
	});
  },

  playOrPause() {
    if (this.data.isPlaying) {
      console.log('点击暂停')
      this.data.innerAudioContext.pause()
    } else {
      console.log('点击播放')
      this.data.innerAudioContext.play()
    }
  },

  onProgressChange(e) {
    const seekTime = e.detail.value
    console.log('滑动进度条到:', seekTime, '秒')
    this.data.innerAudioContext.seek(seekTime)
    this.setData({
      currentTime: seekTime,
      currentTimeText: this.formatTime(seekTime)
    })
  },

  onSpeedChange(e) {
    const speedIndex = e.detail.value
    const speed = this.data.speedOptions[speedIndex].value
    this.data.innerAudioContext.playbackRate = speed
    this.setData({ speedIndex: speedIndex })
    wx.showToast({
      title: `已切换为${this.data.speedOptions[speedIndex].label}`,
      icon: 'none'
    })
  },

  formatTime(seconds) {
    if (!seconds) return '00:00'
    const mins = Math.floor(seconds / 60)
    const secs = Math.floor(seconds % 60)
    return `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`
  }
})
