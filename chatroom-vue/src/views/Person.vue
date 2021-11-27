<template>
  <div style="padding: 20px 50px;">
    <el-card class="box-card" style="width: 50%">
      <div slot="header" class="clearfix">
        <span>我的信息</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="save">保存</el-button>
      </div>
      <div class="text item">
        <el-form ref="formRef" :rules="formRules" :model="form" label-width="100px">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" style="width: 50%"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input show-password v-model="form.password" style="width: 50%"></el-input>
          </el-form-item>
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="form.nickname" style="width: 50%"></el-input>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input v-model="form.age" style="width: 50%"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
          </el-form-item>
          <el-form-item label="地址">
            <el-input type="textarea" v-model="form.address" style="width: 50%"></el-input>
          </el-form-item>
          <el-form-item label="头像">
            <el-input v-model="form.avatar" style="width: 50%"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>

  </div>
</template>

<script>
export default {
  name: 'Person',
  data () {
    return {
      form: {},
      formRules: {
        // prop="username"
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' }
        ]
      }
    }
  },
  mounted () {
    // 将字符串解析为json对象，即反序列化
    this.form = JSON.parse(sessionStorage.getItem('user'))
  },
  methods: {
    save () {
      this.$refs.formRef.validate(async valid => {
        if (!valid) return
        // 解构赋值：将data赋值给res
        const { data: res } = await this.$http.put('/user', this.form)
        if (res.flag) {
          this.$message.success(res.message)
          // 更新sessionStorage中user
          sessionStorage.setItem('user', JSON.stringify(res.data))
          // 触发自定义事件，父组件会监听此事件，进而执行更新sessionStorage操作，实现个人信息与头组件之间的信息同步
          this.$emit('userInfo')
        } else {
          return this.$message.error(res.message)
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
