<template>
  <view class="play-container">
    <view class="header">
      <view class="back-btn" @click="goBack">
        <text class="iconfont icon-jiantou"></text>
      </view>
      <text class="title">听力播放</text>
      <view class="placeholder"></view>
    </view>

    <view class="audio-info">
      <text class="audio-name">{{ mingcheng }}</text>
    </view>

    <view class="player-card">
      <view class="cover-area">
        <view class="cover-icon">
          <text class="iconfont icon-music"></text>
        </view>
      </view>

      <view class="progress-area">
        <view class="progress-bar">
          <slider
            :value="currentTime"
            :max="duration"
            @change="onProgressChange"
            activeColor="#FFD128"
            backgroundColor="#E5E5E5"
            block-size="16"
            block-color="#FFD128"
          />
        </view>
        <view class="time-row">
          <text class="current-time">{{ currentTimeText }}</text>
          <text class="duration-time">{{ durationText }}</text>
        </view>
      </view>

      <view class="controls">
        <view class="play-btn" @click="playOrPause">
          <text class="iconfont" :class="isPlaying ? 'icon-pause' : 'icon-play'"></text>
          <text class="btn-text">{{ isPlaying ? '暂停' : '播放' }}</text>
        </view>
      </view>

      <view class="speed-row">
        <text class="speed-label">播放速度</text>
        <picker
          :value="speedIndex"
          :range="speedOptions"
          @change="onSpeedChange"
          range-key="label"
        >
          <view class="speed-picker">
            <text class="speed-value">{{ speedOptions[speedIndex].label }}</text>
            <text class="iconfont icon-xiajiantou"></text>
          </view>
        </picker>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      audioUrl: '',
      mingcheng: '听力材料',
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
      innerAudioContext: null
    }
  },
  onLoad(options) {
    if (options.mingcheng) {
      this.mingcheng = decodeURIComponent(options.mingcheng)
    }
    if (options.shiyinshipin) {
      const shiyinshipin = decodeURIComponent(options.shiyinshipin)
      if (shiyinshipin.startsWith('http')) {
        this.audioUrl = shiyinshipin
      } else {
        this.audioUrl = this.$base.url + shiyinshipin
      }
    }
    uni.setNavigationBarTitle({
      title: this.mingcheng
    })
  },
  onReady() {
    this.initAudio()
  },
  onUnload() {
    if (this.innerAudioContext) {
      this.innerAudioContext.destroy()
    }
  },
  methods: {
    initAudio() {
      const audioCtx = uni.createInnerAudioContext()
      audioCtx.src = this.audioUrl
      audioCtx.autoplay = false

      audioCtx.onPlay(() => {
        this.isPlaying = true
      })

      audioCtx.onPause(() => {
        this.isPlaying = false
      })

      audioCtx.onEnded(() => {
        this.isPlaying = false
        this.currentTime = 0
        this.currentTimeText = '00:00'
      })

      audioCtx.onTimeUpdate(() => {
        const ct = audioCtx.currentTime
        const dur = audioCtx.duration || this.duration
        this.currentTime = ct
        this.duration = dur
        this.currentTimeText = this.formatTime(ct)
        this.durationText = this.formatTime(dur)
      })

      audioCtx.onError((res) => {
        console.error('音频错误:', res)
        uni.showToast({ title: '音频加载失败', icon: 'none' })
      })

      this.innerAudioContext = audioCtx
    },
    playOrPause() {
      if (this.isPlaying) {
        this.innerAudioContext.pause()
      } else {
        this.innerAudioContext.play()
      }
    },
    onProgressChange(e) {
      const seekTime = e.detail.value
      this.innerAudioContext.seek(seekTime)
      this.currentTime = seekTime
      this.currentTimeText = this.formatTime(seekTime)
    },
    onSpeedChange(e) {
      this.speedIndex = e.detail.value
      const speed = this.speedOptions[this.speedIndex].value
      this.innerAudioContext.playbackRate = speed
      uni.showToast({
        title: `已切换为${this.speedOptions[this.speedIndex].label}`,
        icon: 'none'
      })
    },
    formatTime(seconds) {
      if (!seconds) return '00:00'
      const mins = Math.floor(seconds / 60)
      const secs = Math.floor(seconds % 60)
      return `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`
    },
    goBack() {
      uni.navigateBack()
    }
  }
}
</script>

<style scoped>
.play-container {
  min-height: 100vh;
  background: #F6F7F9;
}

.header {
  background: #fff;
  padding: 30rpx;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.back-btn {
  width: 60rpx;
  height: 60rpx;
  display: flex;
  align-items: center;
  justify-content: center;
}

.placeholder {
  width: 60rpx;
}

.title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
}

.audio-info {
  background: #fff;
  padding: 40rpx 30rpx;
  text-align: center;
}

.audio-name {
  font-size: 30rpx;
  color: #333;
  font-weight: 500;
}

.player-card {
  margin: 30rpx;
  background: #fff;
  border-radius: 24rpx;
  padding: 60rpx 40rpx;
}

.cover-area {
  display: flex;
  justify-content: center;
  margin-bottom: 60rpx;
}

.cover-icon {
  width: 160rpx;
  height: 160rpx;
  background: linear-gradient(135deg, #FFD128, #FF8B04);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.cover-icon .iconfont {
  font-size: 70rpx;
  color: #fff;
}

.progress-area {
  margin-bottom: 40rpx;
}

.progress-bar {
  padding: 0 10rpx;
}

.time-row {
  display: flex;
  justify-content: space-between;
  margin-top: 16rpx;
  padding: 0 10rpx;
}

.current-time,
.duration-time {
  font-size: 24rpx;
  color: #999;
}

.controls {
  display: flex;
  justify-content: center;
  margin-bottom: 50rpx;
}

.play-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.play-btn .iconfont {
  font-size: 80rpx;
  color: #FFD128;
}

.btn-text {
  font-size: 26rpx;
  color: #666;
  margin-top: 10rpx;
}

.speed-row {
  display: flex;
  align-items: center;
  justify-content: center;
  padding-top: 30rpx;
  border-top: 1rpx solid #f0f0f0;
}

.speed-label {
  font-size: 28rpx;
  color: #666;
  margin-right: 20rpx;
}

.speed-picker {
  display: flex;
  align-items: center;
  background: #f5f5f5;
  padding: 16rpx 30rpx;
  border-radius: 30rpx;
}

.speed-value {
  font-size: 26rpx;
  color: #FFD128;
  font-weight: bold;
  margin-right: 10rpx;
}

.speed-picker .iconfont {
  font-size: 20rpx;
  color: #999;
}
</style>
