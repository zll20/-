<template>
  <view class="tingli-container">
    <view class="header">
      <text class="title">听力材料</text>
    </view>

    <view class="level-switch">
      <view class="level-title">选择级别</view>
      <view class="level-grid">
        <view
            v-for="(level, index) in levelList"
            :key="index"
            class="level-item"
            :class="{ 'active': currentLevel === level }"
            @click="selectLevel(level)">
          <view class="level-icon" :class="iconMap[level] || 'cuIcon-sound'"></view>
          <text class="level-text">{{ level }}</text>
        </view>
      </view>
    </view>

    <view class="content-area" v-if="currentLevel">
      <view class="content-title">当前级别：{{ currentLevel }}</view>
      <view class="empty-tip" v-if="materialList.length === 0">
        <text>暂无听力材料</text>
      </view>
      <view class="material-list" v-else>
        <view
            v-for="(item, index) in materialList"
            :key="item.id"
            class="material-item"
            @click="playMaterial(item)">
          <view class="material-icon cuIcon-sound"></view>
          <view class="material-info">
            <text class="material-name">{{ item.mingcheng }}</text>
            <text class="material-desc">{{ item.jieshao }}</text>
          </view>
          <text class="play-btn cuIcon-playfill"></text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      currentLevel: '',
      levelList: ['小学', '初中', '高中', '四级', '六级', '考研'],
      materialList: [],
      iconMap: {
        '小学': 'cuIcon-child',
        '初中': 'cuIcon-friend',
        '高中': 'cuIcon-milk',
        '四级': 'cuIcon-vipcard',
        '六级': 'cuIcon-crown',
        '考研': 'cuIcon-read'
      }
    }
  },
  computed: {
    baseUrl() { return this.$base.url }
  },
  onLoad() {
  },
  methods: {
    selectLevel(level) {
      this.currentLevel = level
      this.loadMaterials(level)
    },
    async loadMaterials(level) {
      try {
        let res = await this.$api.list('tingli', {
          page: 1,
          limit: 100,
          jibie: level,
          sort: 'id',
          order: 'desc'
        })
        if (res.data && res.data.list) {
          this.materialList = res.data.list
        }
      } catch (err) {
        console.error('加载听力材料失败:', err)
        uni.showToast({
          title: '加载失败',
          icon: 'none'
        })
      }
    },
    playMaterial(item) {
      if (item.shiyinshipin) {
        uni.navigateTo({
          url: `/pages/tingli/play?id=${item.id}&shiyinshipin=${encodeURIComponent(item.shiyinshipin)}&mingcheng=${encodeURIComponent(item.mingcheng)}`
        })
      } else {
        uni.showToast({ title: '暂无音频', icon: 'none' })
      }
    }
  }
}
</script>

<style scoped>
.tingli-container { min-height: 100vh; background: #f5f5f5; padding-bottom: 40rpx; }
.header { background: #fff; padding: 40rpx 30rpx; text-align: center; border-bottom: 1rpx solid #eee; }
.title { font-size: 36rpx; font-weight: bold; color: #333; }
.level-switch { background: #fff; margin: 30rpx; border-radius: 20rpx; padding: 40rpx 30rpx; min-height: 55vh; display: flex; flex-direction: column; }
.level-title { font-size: 32rpx; font-weight: bold; color: #333; margin-bottom: 40rpx; text-align: center; }
.level-grid { display: grid; grid-template-columns: repeat(2, 1fr); gap: 30rpx; flex: 1; }
.level-item { background: #f8f9fa; border-radius: 16rpx; padding: 40rpx 20rpx; display: flex; flex-direction: column; align-items: center; justify-content: center; border: 2rpx solid transparent; }
.level-item.active { background: #e6f7ff; border-color: #1890ff; }
.level-icon { font-size: 60rpx; color: #666; margin-bottom: 20rpx; }
.level-item.active .level-icon { color: #1890ff; }
.level-text { font-size: 28rpx; color: #333; font-weight: 500; }
.content-area { background: #fff; margin: 0 30rpx 30rpx; border-radius: 20rpx; padding: 30rpx; }
.content-title { font-size: 28rpx; color: #666; margin-bottom: 20rpx; }
.empty-tip { text-align: center; padding: 100rpx 0; color: #999; font-size: 28rpx; }
.material-list { display: flex; flex-direction: column; gap: 20rpx; }
.material-item { background: #f8f9fa; border-radius: 12rpx; padding: 24rpx; display: flex; align-items: center; }
.material-icon { font-size: 48rpx; color: #1890ff; margin-right: 20rpx; width: 80rpx; height: 80rpx; display: flex; align-items: center; justify-content: center; background: #e6f7ff; border-radius: 8rpx; }
.material-info { flex: 1; display: flex; flex-direction: column; }
.material-name { font-size: 28rpx; color: #333; margin-bottom: 8rpx; }
.material-desc { font-size: 24rpx; color: #999; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.play-btn { font-size: 40rpx; color: #1890ff; }
</style>
