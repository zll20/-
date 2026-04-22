// components/wx-popup/wx-popup.js
Component({
  observers: {
    type: function (newVal) {
      switch (newVal) {
        case 'top':
          this.setData({
            transClass: {
              'position': 'fixed',
              'left': 0,
              'right': 0,
            }
          })
          break
        case 'bottom':
          this.setData({
            transClass: {
              'position': 'fixed',
              'left': 0,
              'right': 0,
              'bottom': 0
            }
          })
          break
        case 'center':
          this.setData({
            transClass: {
              'position': 'fixed',
              'display': 'flex',
              'flexDirection': 'column',
              'bottom': 0,
              'left': 0,
              'right': 0,
              'top': 0,
              'justifyContent': 'center',
              'alignItems': 'center'
            }
          })
          break
      }
    }
  },
  properties: {
    animation: {
      type: Boolean,
      default: true
    },
    // 弹出层类型，可选值，top: 顶部弹出层；bottom：底部弹出层；center：全屏弹出层
    type: {
      type: String,
      default: 'center'
    },
    // maskClick
    maskClick: {
      type: Boolean,
      default: true
    },
    showPopup: {
      type: Boolean,
      default: false
    }
  },
  /**
   * 页面的初始数据
   */
  data: {
    ani: [],
    maskClass: {
      'position': 'fixed',
      'bottom': 0,
      'top': 0,
      'left': 0,
      'right': 0,
      'backgroundColor': 'rgba(0, 0, 0, 0.4)'
    },
    transClass: {
      'position': 'fixed',
      'left': 0,
      'right': 0,
    }
  },

  methods: {
    clear(e) {
      // TODO nvue 取消冒泡
      e.stopPropagation()
    },
    close() {
      this.triggerEvent('cancel', false)
    },
    onTap() {
      if (!this.data.maskClick) return
      this.close()
    }
  }

})