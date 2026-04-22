var that;
const base = getApp().base
const api = getApp().api
Component({
  data: {
    baerurl: base.base.url,
    content: '',
    formats: {}, // 样式
    placeholder: '开始输入...',
    userInfo: {},
    parentId: "",
    editorCtx: null,
  },
  properties: {
    value: {
      type: String,
      default: '',
      AIType: false
    },
    AIType:{
      type: Boolean,
      default: false
    }
  },
  observers: {
    'value': function (newValue) {
      setTimeout(() => {
        this.setData({
          content: newValue
        })
      }, 500);
    },
  },
  ready() {
    const that = this;
    const parentId = "点击id";

    this.setData({
      parentId
    });

    that.onEditorReady();
  },
  methods: {
    xuxieClick() {
      let that = this
      this.editorCtx.getSelectionText({
        async success(res){
          wx.showLoading({
            title: 'AI续写中...',
            mask: true
          })
          let text = ''
          let allText = ''
          let allHtml = ''
          let type = false
          await that.editorCtx.getContents({
            async success(rs){
              allText = rs.text
              if(res.text&&res.text!=''){
                type = true
                text = res.text
              }
              await api.post('deepseek/askai', {
                ask: (type?text:allText) + ' 请将前面的内容续写下去，只返回内容即可'
              }).then(obj=>{
                wx.hideLoading()
                if(obj && obj.data) {
                  if(type) {
                    // 如果有选中文本，替换选中部分
                    allText = allText.replace(text, text + obj.data)
                  } else {
                    // 如果没有选中文本，在末尾追加
                    allText += obj.data
                  }
                  that.editorCtx.setContents({
                    html: allText
                  })
                }
              })
            }
          });
          
        }
      })
    },
    youhuaClick() {
      let that = this
      this.editorCtx.getSelectionText({
        async success(res){
          if(!res.text) {
            that.$utils.msg('请选中优化内容')
            return false
          }
          wx.showLoading({
            title: 'AI优化中...',
            mask: true
          })
          let text = ''
          let allText = ''
          let type = false
          that.editorCtx.getContents({
            async success(rs){
              allText = rs.text
              if(res.text&&res.text!=''){
                type = true
                text = res.text
              }
              await api.post('deepseek/askai', {
                ask: (type?text:allText) + ' 请帮我优化一下这段文字的内容，并只返回优化的内容'
              }).then(obj=>{
                wx.hideLoading()
                if(type) {
                  allText = allText.replace(text,obj.data)
                }else {
                  allText += obj.data
                }
                that.editorCtx.setContents({
                  html: allText
                })
              })
            }
          });
        }
      })
    },
    xiaoduiClick() {
      let that = this
      this.editorCtx.getSelectionText({
        async success(res){
          if(!res.text) {
            that.$utils.msg('请选中校对内容')
            return false
          }
          wx.showLoading({
            title: 'AI校对中...',
            mask: true
          })
          let text = ''
          let allText = ''
          let type = false
          await that.editorCtx.getContents({
            async success(rs){
              allText = rs.text
              if(res.text&&res.text!=''){
                type = true
                text = res.text
              }
              await api.post('deepseek/askai', {
                ask: (type?text:allText) + ' 请帮我校对一下这段文字的内容，并只返回校对的内容'
              }).then(obj=>{
                wx.hideLoading()
                if(type) {
                  allText = allText.replace(text,obj.data)
                }else {
                  allText += obj.data
                }
                that.editorCtx.setContents({
                  html: allText
                })
              })
            }
          });
        }
      })
    },
    fanyiClick() {
      let that = this
      this.editorCtx.getSelectionText({
        async success(res){
          if(!res.text) {
            that.$utils.msg('请选中翻译内容')
            return false
          }
          wx.showLoading({
            title: 'AI翻译中...',
            mask: true
          })
          let text = ''
          let allText = ''
          let type = false
          that.editorCtx.getContents({
            async success(rs){
              console.log(rs)
              allText = rs.text
              if(res.text&&res.text!=''){
                type = true
                text = res.text
              }
              await api.post('deepseek/askai', {
                ask: (type?text:allText) + ' 请帮我翻译一下这段文字的内容，并只返回翻译的内容'
              }).then(obj=>{
                wx.hideLoading()
                if(type) {
                  allText = allText.replace(text,obj.data)
                }else {
                  allText += obj.data
                }
                that.editorCtx.setContents({
                  html: allText
                })
              })
            }
          });
          
        }
      })
    },
    // 初始化编辑器
    onEditorReady() {
      const that = this;
      const editorQuery = this.createSelectorQuery().select('#editor');
      setTimeout(() => {
        editorQuery.context((res) => {
          that.editorCtx = res.context;
          // 初始化赋值内容
          that.editorCtx.setContents({
            html: that.data.content
          });
        }).exec();
      }, 100);
    },

    // 返回选区已设置的样式
    onStatusChange(e) {
      const formats = e.detail
      this.setData({
        formats
      })
    },

    undo() {
      this.editorCtx.undo()
    },
    redo() {
      this.editorCtx.redo()
    },
    insertDate() {
      const date = new Date()
      const formatDate = `${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()}`
      this.editorCtx.insertText({
        text: formatDate
      })
    },
    // 内容发生改变
    onContentChange(e) {
      this.triggerEvent('editorChange', e.detail.html)

    },
    // 失去焦点
    onNoFocus(e) {
      // that.setData({
      //   content: e.detail
      // })
      // wx.setStorageSync("content", e.detail)
    },
    // 获取内容
    clickLogText(e) {
      // that.editorCtx.getContents({
      //     success: async function (res) {
      //         that.triggerEvent('acceptChild', {
      //             data: res.html
      //         })

      //     }
      // })
      // that.editorCtx.getContents({
      //     success: async function (res) {
      //         wx.setStorageSync("content", res.html);
      //         const data = {
      //             avatarurl: that.data.userInfo?.touxiang,
      //             content: res.html,
      //             parentid: that.data.parentId,
      //             username: that.data.userInfo?.yonghuming
      //         }
      //         const responed = await forumSave(data)
      //         if (responed.code == 0) {
      //             wx.showToast({
      //                 title: '回复成功',
      //                 icon: "none"
      //             })
      //             wx.navigateBack({
      //                 delta: 1
      //             })
      //         }
      //     }
      // })
    },
    // 清空所有
    clear() {
      this.editorCtx.clear({
        success: function (res) {
        }
      })
    },
    // 清除样式
    removeFormat() {
      this.editorCtx.removeFormat()
    },

    insertImg() {
      let that = this
      let url = that.data.baerurl
      api.upload('image').then(rs=>{
        that.editorCtx.insertImage({
          src: url + 'upload/' + rs.file,
          alt: '图像'
        })
      })
    },
    // 记录样式
    format(e) {
      let {
        name,
        value
      } = e.target.dataset
      if (!name) return
      this.editorCtx.format(name, value)
    },
  }
})