
const base = getApp().base
Page({
  data: {
    isModalShow: false,       // 弹窗显示状态
    canvasCtx: null,          // Canvas 2D上下文
    canvasNode: null,         // Canvas节点实例
    isDrawing: false,         // 是否正在绘制
    hasDrawn: false,          // 是否有签名内容
    markerInfo: {             // 签名数据存储
      user_sign_a: ''
    },
    currentSignType: '',       // 当前签名类型（支持多签名扩展）
    name: ''
  },
  onLoad(options){
    this.data.name = options.name
  },
  /**
   * 打开签名弹窗（核心：确保点击响应）
   */
  openSignModal(e) {
    console.log('触发签名弹窗', e);
    const signType = e.currentTarget.dataset.type || 'user_sign_a';
    this.setData({
      isModalShow: true,
      currentSignType: signType
    }, () => {
      // 延迟初始化Canvas，避免阻塞事件响应
      setTimeout(() => {
        this.initCanvas();
      }, 100);
    });
  },

  /**
   * 关闭签名弹窗（彻底清空状态）
   */
  closeSignModal() {
    const { canvasCtx, canvasNode } = this.data;
    // 强制清空Canvas内容，避免透传残留
    if (canvasCtx && canvasNode) {
      const dpr = wx.getSystemInfoSync().pixelRatio || 1;
      canvasCtx.fillStyle = '#fff';
      canvasCtx.fillRect(0, 0, canvasNode.width / dpr, canvasNode.height / dpr);
    }
    // 重置所有状态
    this.setData({
      isModalShow: false,
      hasDrawn: false,
      isDrawing: false
    });
  },

  /**
   * 阻止弹窗内容区点击穿透
   */
  stopPropagation() {},

  /**
   * 初始化Canvas（适配像素比，解决模糊）
   */
  initCanvas() {
    try {
      const query = wx.createSelectorQuery().in(this);
      query.select('#signCanvas')
        .fields({ node: true, size: true })
        .exec((res) => {
          if (!res[0] || !res[0].node) return;

          const canvas = res[0].node;
          const ctx = canvas.getContext('2d');
          const dpr = wx.getSystemInfoSync().pixelRatio || 1;

          // 适配设备像素比
          canvas.width = res[0].width * dpr;
          canvas.height = res[0].height * dpr;
          ctx.scale(dpr, dpr);

          // 初始化画笔样式
          ctx.lineWidth = 3;
          ctx.lineCap = 'round'; // 笔迹端点圆润
          ctx.lineJoin = 'round'; // 笔迹拐角圆润
          ctx.strokeStyle = '#000';

          // 填充白色背景，避免透明底问题
          ctx.fillStyle = '#fff';
          ctx.fillRect(0, 0, res[0].width, res[0].height);

          this.setData({ canvasCtx: ctx, canvasNode: canvas });
        });
    } catch (e) {
      console.error('Canvas初始化失败', e);
      wx.showToast({ title: '画布初始化失败', icon: 'none' });
    }
  },

  /**
   * 触摸开始：开始绘制签名
   */
  handleTouchStart(e) {
    const { canvasCtx } = this.data;
    if (!canvasCtx) return;

    this.setData({ isDrawing: true });
    const x = e.touches[0].x;
    const y = e.touches[0].y;
    canvasCtx.beginPath();
    canvasCtx.moveTo(x, y);
  },

  /**
   * 触摸移动：绘制签名轨迹
   */
  handleTouchMove(e) {
    const { canvasCtx, isDrawing } = this.data;
    if (!canvasCtx || !isDrawing) return;

    const x = e.touches[0].x;
    const y = e.touches[0].y;
    canvasCtx.lineTo(x, y);
    canvasCtx.stroke();
    this.setData({ hasDrawn: true });
  },

  /**
   * 触摸结束：停止绘制
   */
  handleTouchEnd() {
    this.setData({ isDrawing: false });
  },

  /**
   * 清空签名画布
   */
  clearSign() {
    const { canvasCtx, canvasNode } = this.data;
    if (!canvasCtx || !canvasNode) return;

    const dpr = wx.getSystemInfoSync().pixelRatio || 1;
    canvasCtx.fillStyle = '#fff';
    canvasCtx.fillRect(0, 0, canvasNode.width / dpr, canvasNode.height / dpr);
    this.setData({ hasDrawn: false });
  },

  /**
   * 保存并上传签名（核心：更新表单显示）
   */
  saveAndUploadSign() {
    const { hasDrawn, canvasNode, currentSignType } = this.data;
    let that = this
    // 校验签名内容
    if (!hasDrawn) {
      wx.showToast({ title: '请先完成手写签名', icon: 'none' });
      return;
    }
    if (!canvasNode) {
      wx.showToast({ title: '画布初始化失败', icon: 'none' });
      return;
    }

    // 将Canvas转为临时文件
    wx.canvasToTempFilePath({
      canvas: canvasNode,
      fileType: 'png',
      quality: 0.8,
      success: (res) => {
        console.log(res)
        console.log(base.base.url + 'file/upload')
        // 上传签名文件到后端（替换为实际接口）
        wx.uploadFile({
          url: base.base.url + 'file/upload', // 后端接口地址
          filePath: res.tempFilePath,
          name: 'file',
          header: {
            'Token': wx.getStorageSync("appToken")
          },
          success: (uploadRes) => {
            let result = JSON.parse(uploadRes.data);
            const pages = getCurrentPages();
            if (pages.length > 1) {
              const prevPage = pages[pages.length - 2];
              prevPage.signBack(that.data.name,result.file)
              wx.navigateBack()
            }
          },
          fail: (err) => {
            console.error('上传失败', err);
            wx.showToast({ title: '网络错误，上传失败', icon: 'none' });
          }
        });
      },
      fail: (err) => {
        console.error('Canvas转文件失败', err);
        wx.showToast({ title: '签名生成失败', icon: 'none' });
      }
    }, this);
  },

  /**
   * 删除已保存的签名
   */
  deleteSignature(e) {
    const signType = e.currentTarget.dataset.type;
    wx.showModal({
      title: '提示',
      content: '确定删除该签名吗？',
      success: (res) => {
        if (res.confirm) {
          const updateData = {};
          updateData[`markerInfo.${signType}`] = '';
          this.setData(updateData);
        }
      }
    });
  }
});
