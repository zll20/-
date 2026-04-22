Component({
  properties: {
    showType: {
      type: Boolean,
      default: false
    },
    // 是否允许点击遮罩层关闭
    maskCloseAble: {
      type: Boolean,
      default: true
    },
    // 是否显示全选
    allShow: {
      type: Boolean,
      default: true
    },
    // 模式
    mode: {
      type: String,
      default: "multiple"
    },
    // 默认选中值
    defaultSelected: {
      type: Array,
      default: function () {
        return [];
      }
    },
    // 数据源
    list: {
      type: Array,
      default: function () {
        return [];
      }
    }
  },
  data: {
    show: false, //是否显示
    activeClass: false, //激活样式状态
    selectedArr: [], //选择对照列表
    selectedArrOld: [], //选择对照列表上一次的数据
    valueName: 'value',
    labelName: 'label',
    maskCloseAble: true
  },
  observers: {
    "showType": function (newValue) {
      this.data.selectedArr = this.data.list.map(el => false);
      this.setItemActiveState()
      this.setData({
        show: newValue,
        activeClass: newValue,
        list: this.data.list
      })
    },
  },
  methods: {
    onSelected(e) {
      let index = e.currentTarget.dataset.index
      if (this.data.list[index].disabled) return;
      let index2Active = this.data.selectedArr[index];
      this.data.selectedArr.splice(index, 1, !index2Active);
      this.setData({
        selectedArr: this.data.selectedArr
      })
    },
    setItemActiveState() {
      if (this.data.list.length && this.data.defaultSelected.length) {
        this.data.list.forEach((item, i) => {
          for (let n = 0; n < this.data.defaultSelected.length; n++) {
            if (
              !item.disabled &&
              item[this.data.valueName] === this.data.defaultSelected[n]
            ) {
              this.data.selectedArr.splice(i, 1, true);
              break;
            }
          }
        });
        this.setData({
          selectedArr: this.data.selectedArr,
          selectedArrOld: this.data.selectedArr
        })
      }
    },
    onCancel(e) {
      this.data.show = false;
      this.data.selectedArr = JSON.parse(JSON.stringify(this.data.selectedArrOld));
      this.triggerEvent('cancel')
    },
    returnWipeDisabledList() {
      let arr = [];
      this.data.selectedArr.forEach((el, index) => {
        if (!this.data.list[index].disabled) arr.push(el);
      });
      return arr;
    },
    onAllToggle() {
      let wipeDisabledList = this.returnWipeDisabledList();
      // 如果去除了disabled的对照列表有false的数据，代表未全选
      if (wipeDisabledList.includes(false)) {
        this.data.selectedArr.forEach((el, index) => {
          if (!this.data.list[index].disabled)
            this.data.selectedArr.splice(index, 1, true);
        });
      } else {
        this.data.selectedArr.forEach((el, index) => {
          if (!this.data.list[index].disabled)
            el = this.data.selectedArr.splice(index, 1, false);
        });
      }
      this.setData({
        selectedArr: this.data.selectedArr
      })
    },
    onConfirm() {
      this.data.show = false;
      let selectedData = [];
      this.data.selectedArr.forEach((el, index) => {
        if (el) {
          selectedData.push(this.data.list[index].value);
        }
      });
      this.triggerEvent('confirm', selectedData)
    }
  }
})