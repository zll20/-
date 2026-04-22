const computedBehavior = require("miniprogram-computed").behavior;
Component({
  behaviors: [computedBehavior],
  /**
   * 组件的属性列表
   */
  properties: {
    timetableType: {
      type: Array,
      default: []
    },
    timetables: {
      type: Array,
      default: () => {
        return []
      }
    },
    palette: {
      type: Array,
      default: () => {
        return []
      }
    },
    jiaoshi: {
      type: Array,
      default: () => {
        return []
      }
    },
    ids: {
      type: Array,
      default: () => {
        return []
      }
    },
  },
  data: {
    allPalette: ['#f05261', '#48a8e4', '#ffd061', '#52db9a', '#70d3e6', '#52db9a', '#3f51b5',
      '#f3d147', '#4adbc3', '#673ab7', '#f3db49', '#76bfcd', '#b495e1', '#ff9800', '#8bc34a'
    ],
    week: ['一', '二', '三', '四', '五', '六', '日']
  },
  computed: {
    courseData(data) {
      // 为数据标记背景颜色的函数
      let paletteIndex = 0
      const getBackgroundColor = () => {
        const backgroundColor = data.allPalette[paletteIndex]
        paletteIndex++
        if (paletteIndex >= data.allPalette.length) {
          paletteIndex = 0
        }
        return backgroundColor
      }
      // 合并
      const listMerge = []
      data.timetables.forEach(function (list, i) {
        if (!listMerge[i]) {
          listMerge[i] = []
        }
        list.forEach(function (item, index) {
          if (!index) {
            return listMerge[i].push({
              name: item,
              length: 1,
              backgroundColor: item === '' ? 'none' : getBackgroundColor(),
              jiaoshi: data.jiaoshi[i][index],
              id: data.ids[i][index]
            })
          }
          if (item === (listMerge[i][index - 1] || {}).name && item) {
            const sameIndex = (listMerge[i][index - 1] || {}).sameIndex
            if (sameIndex || sameIndex === 0) {
              listMerge[i][sameIndex].length++
              return listMerge[i].push({
                name: item,
                length: 0,
                sameIndex: sameIndex
              })
            }
            listMerge[i][index - 1].length++
            return listMerge[i].push({
              name: item,
              length: 0,
              sameIndex: index - 1
            })
          } else {
            return listMerge[i].push({
              name: item,
              length: 1,
              backgroundColor: item === '' ? 'none' : getBackgroundColor(),
              jiaoshi: data.jiaoshi[i][index],
              id: data.ids[i][index]
            })
          }
        })
      })
      return listMerge
    },
    todayWeekIndex() {
      let weekIndex = new Date().getDay() - 1
      if (weekIndex === -1) {
        weekIndex = 6
      }
      return weekIndex
    }
  },
  methods: {
    handleCourseClick(e) {
      let course = e.currentTarget.dataset.course
      let weekIndex = e.currentTarget.dataset.weekIndex
      let courseIndex = e.currentTarget.dataset.courseIndex
      const data = {
        index: courseIndex + 1,
        length: course.length,
        week: this.data.week[weekIndex],
        weekIndex: weekIndex,
        name: course.name
      }
      this.triggerEvent('courseClick', course);
    }
  }
})