Component({
  properties: {
    // 数据
    value: {
      type: Number,
      default: 0
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data: {
  },
  methods: {
    scoreChange(e) {
      if(this.data.disabled) {
        return false
      }
      let score = e.currentTarget.dataset.score
      this.triggerEvent('scoreChange',Number(score))
    },
  }
})