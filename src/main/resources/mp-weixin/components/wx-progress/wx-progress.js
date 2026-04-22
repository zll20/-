// components/wx-progress/wx-progress.js

Component({
  properties:{
    value: {
      type: Number,
      default: 0
    },
  },
  data: {
    percent: 0, // 保存进度值的变化前后值，用于比较用
    value: 0
  },
  methods: {
    drawProgressbg() {
      // 自定义组件实例 this ，表示在这个自定义组件下查找拥有 canvas-id 的 <canvas/>
      const query = wx.createSelectorQuery().in(this)
      query.select('#cpbg')
        .fields({ node: true, size: true })
        .exec((res) => {
          if (!res[0]) return;
          console.log(res)
          const canvas = res[0].node;
          const ctx = canvas.getContext('2d');

          // 设置线条样式
          ctx.lineWidth = 12;
          ctx.strokeStyle = '#E2E2E2';
          ctx.lineCap = 'round';

          // 开始绘制路径
          ctx.beginPath();
          ctx.arc(125, 125, 100, 0.99 * Math.PI, 0.01 * Math.PI, false);
          ctx.stroke();
        })
    },
    // 画圆（递归调用）
    drawCircle(step) {
      this.data.value = step
      if (step === 0) return;
      
      // 新版API：使用选择器获取Canvas节点
      const query = wx.createSelectorQuery().in(this)
      query.select('#cpbar').fields({
        node: true,
        size: true
      }).exec((res) => {
        if (!res[0]) return;
        
        const canvas = res[0].node;
        const ctx = canvas.getContext('2d');
        
        
        // 创建渐变
        const gradient = ctx.createLinearGradient(28, 55, 0, 55);
        gradient.addColorStop('0', '#FFE97C');
        gradient.addColorStop('1', '#FFE97C');
        
        // 设置绘图样式
        ctx.lineWidth = 12;
        ctx.strokeStyle = gradient;
        ctx.lineCap = 'round';
        
        // 计算圆弧参数
        step = Number((0.01 * step + 0.99).toFixed(2));
        if (step === 1) step = 0.99;
        if (step >= 2) step = step % 2;
        if (step === 1.99) step = 0.01;
        
        // 开始绘制路径
        ctx.beginPath();
        ctx.arc(125, 125, 100, 0.99 * Math.PI, step * Math.PI, false);
        ctx.stroke();
        
        // 递归调用实现动画效果
        if (this.data.value > this.data.percent) {
          this.data.percent++;
          setTimeout(() => {
            this.drawCircle(this.data.percent);
          }, 15);
        }
      });
    }
  }
})