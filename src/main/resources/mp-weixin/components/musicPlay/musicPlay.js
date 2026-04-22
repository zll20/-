// components/musicPlay/musicPlay.js
const base = getApp().base
var innerAudioContext = wx.createInnerAudioContext();
innerAudioContext.autoplay = false; //不自动播放
innerAudioContext.src = '';
Component({
  /**
   * 页面的初始数据
   */
  data: {
    baseurl: base.base.url,
    listShow: false,
    list: [],
    baifenbi: 0,
    allTime: 0,
    nowTime: 0,
    playTime: 0,
    musicIndex: -1,
    playType: false,
    playIndex: 1, //1单曲 //2列表 //3随机
    bottomHeight: 0,
    nowPic: ''
  },
  methods: {
    listShowHide() {
      this.setData({
        listShow: false
      })
    },
    stopClick() {
      innerAudioContext.stop()
    },
    getTimeStr(game_over_timestamp) {
      if (game_over_timestamp == 0)
        return "00:00";
      var date = new Date(parseInt(game_over_timestamp));
      var minutes = date.getMinutes() >= 10 ? date.getMinutes().toString() : "0" + date.getMinutes();
      var seconds = date.getSeconds() >= 10 ? date.getSeconds().toString() : "0" + date.getSeconds();

      return minutes + ":" + seconds
    },
    sliderChange(e) {

      let i = e.detail.value * this.data.allTime / 100
      innerAudioContext.seek(i)
    },
    changeMusic(item) {
      for (let x in this.data.list) {
        if (this.data.list[x].id == item.id) {
          this.setData({
            musicIndex: Number(x),
            nowPic: this.data.baseurl + this.data.list[x].pic
          })
          return false
        }
      }
      return true
    },
    playAllClick(item,list){
      this.setData({
        list: list
      })
      this.playClick(item)
    },
    playClick(item) {
      if (this.changeMusic(item)) {
        this.data.list.push(item)
        this.data.musicIndex = this.data.list.length - 1
        this.setData({
          list: this.data.list,
          musicIndex: this.data.musicIndex,
        })
      }
      innerAudioContext.src = this.data.baseurl + this.data.list[this.data.musicIndex].src;
      wx.nextTick(function(){
        innerAudioContext.play()
      })
      innerAudioContext.onPlay(() => {
        this.setData({
          playType: true
        })
      })
      innerAudioContext.onPause(() => {
        this.setData({
          playType: false
        })
      })
      innerAudioContext.onTimeUpdate(() => {
        const {
          currentTime,
          duration
        } = innerAudioContext;
        this.setData({
          nowTime: currentTime,
          allTime: duration ? duration : 0,
          baifenbi: duration ? Number((currentTime / duration * 100).toFixed(2)) : 0
        })
        let user = wx.getStorageSync("appUserid")?JSON.parse(wx.getStorageSync('userSession')):{}
        if(currentTime>30&&this.data.list[this.data.musicIndex].isfree==0&&(!user||!user.vip||(user.vip&&user.vip!='是'))){
          innerAudioContext.stop()
          this.playClicks()
        }
      })
      innerAudioContext.onEnded(() => {
        this.playClicks()
      })
    },
    random(min, max) {

      return Math.floor(Math.random() * (max - min)) + min;

    },
    playClicks(){
      if (this.data.playIndex == 1) {
        innerAudioContext.src = this.data.baseurl + this.data.list[this.data.musicIndex].src;
        innerAudioContext.play()
      } else if (this.data.playIndex == 2) {
        let i = this.data.musicIndex
        if (i == this.data.list.length - 1) {
          i = 0
        } else {
          i++
        }
        this.setData({
          musicIndex: i
        })
        innerAudioContext.src = this.data.baseurl + this.data.list[this.data.musicIndex].src;
        innerAudioContext.play()
      } else if (this.data.playIndex == 3) {
        let i = Number(this.data.list.length - 1)
        let a = this.random(0, i)
        this.setData({
          musicIndex: a
        })
        innerAudioContext.src = this.data.baseurl + this.data.list[this.data.musicIndex].src;
        innerAudioContext.play()
      }
    },
    playsClick() {
      innerAudioContext.play()
    },
    pauseClick() {
      innerAudioContext.pause()
    },
    playIndexClick(e) {
      this.setData({
        playIndex: e.currentTarget.dataset.index
      })
    },
    listClick() {
      this.setData({
        listShow: true
      })
    },
    delListClick(e) {
      let index = e.currentTarget.dataset.index
      if(this.data.musicIndex == index){
        if (this.data.list.length == 1) {
          innerAudioContext.stop()
          this.setData({
            list: this.data.list.splice(index, 1)
          })
          return false
        }
        if(this.data.musicIndex!=0){
          this.data.musicIndex--
          this.setData({
            limusicIndext: this.data.musicIndex
          })
        }
        
        innerAudioContext.play()
      }
      this.setData({
        list: this.data.list.splice(index, 1)
      })
    },
    playListClick(e) {
      this.setData({
        musicIndex: e.currentTarget.dataset.index
      })
      innerAudioContext.src = this.data.baseurl + this.data.list[this.data.musicIndex].src;
      innerAudioContext.play()
    },
  }
})