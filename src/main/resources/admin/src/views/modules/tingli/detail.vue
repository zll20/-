<template>
  <el-dialog title="详情" :visible.sync="visible" width="800px">
    <el-form ref="dataForm" label-width="120px" class="demo-ruleForm">
      <el-form-item label="名称">
        <el-input v-model="dataForm.mingcheng" disabled></el-input>
      </el-form-item>
      <el-form-item label="级别">
        <el-input v-model="dataForm.jibie" disabled></el-input>
      </el-form-item>
      <el-form-item label="听力音频">
        <div v-if="dataForm.shiyinshipin">
          <audio controls style="width: 100%;">
            <source :src="getAudioUrl()" type="audio/mpeg">
          </audio>
        </div>
        <div v-else>暂无音频</div>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      visible: false,
      dataForm: {
        id: '',
        mingcheng: '',
        jibie: '',
        shiyinshipin: ''
      }
    }
  },
  methods: {
    init(id) {
      this.visible = true
      this.dataForm = {
        id: '',
        mingcheng: '',
        jibie: '',
        shiyinshipin: ''
      }
      if (id) {
        this.$http({
          url: `tingli/info/${id}`,
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataForm = data.tingli || {
              id: '',
              mingcheng: '',
              jibie: '',
              shiyinshipin: ''
            }
          }
        }).catch(error => {
          console.error('获取听力信息失败:', error)
        })
      }
    },
    getAudioUrl() {
      if (!this.dataForm.shiyinshipin) {
        return '';
      }
      let url = this.dataForm.shiyinshipin;

      // 清理所有前导斜杠
      while (url.startsWith('/')) {
        url = url.substring(1);
      }

      // 清理所有重复的 upload/ 前缀
      while (url.startsWith('upload/upload/')) {
        url = url.substring(7);
      }

      if (url.startsWith('http')) {
        return url;
      }
      return this.$base.url + url;
    }
  }
}
</script>