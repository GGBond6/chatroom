<template>
  <div class="login_container">
    <div class="login_box">
      <div class="title">欢迎注册</div>
      <!--登录表单区域-->
      <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="login_form">
        <!--用户名-->
        <el-form-item label="" prop="username">
          <el-input v-model="loginForm.username" prefix-icon="el-icon-user" placeholder="用户名"></el-input>
        </el-form-item>
        <!--密码-->
        <el-form-item label="" prop="password">
          <el-input v-model="loginForm.password"  show-password prefix-icon="el-icon-lock" placeholder="密码"></el-input>
        </el-form-item>
        <!--验证密码-->
        <el-form-item prop="checkPass">
          <el-input v-model="loginForm.checkPass" show-password  prefix-icon="el-icon-lock" placeholder="重复密码" autocomplete="off"></el-input>
        </el-form-item>
        <!--按钮区域-->
        <el-form-item label="" class="btns">
          <el-button type="primary" @click="register">注册</el-button>

          <el-button  @click="resetLoginForm">重置</el-button>
          <el-button  @click="$router.push('/login')">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Register',
  data () {
    return {
      // 登录表单的数据绑定对象
      loginForm: {
        username: '',
        password: '',
        checkPass: ''
      },
      loginFormRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        checkPass: [
          { required: true, message: '请输入重复密码', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 重置表单
    resetLoginForm () {
      // this.$refs是一个对象，其中的属性loginFormRef是表单的实例对象，可以直接调用表单方法
      this.$refs.loginFormRef.resetFields()
    },
    register () {
      // 判断两次密码是否一样
      if (this.loginForm.checkPass !== this.loginForm.password) {
        this.$message.error('两次输入密码不一致!')
      }
      // 发送请求前需要验证
      this.$refs.loginFormRef.validate(async valid => {
        if (!valid) return
        const { data } = await this.$http.post('/user/register', this.loginForm)// 解构赋值出data
        if (data.flag) {
          this.$message.success(data.message)
          // 将token保存至sessionStorage中
          window.sessionStorage.setItem('token', 'test')
          return this.$router.push('/login')// 成功后路由到登录页面
        }
        return this.$message.error(data.message)
      })
    }
  }
}
</script>

<style lang="less" scoped>
.title{
  font-size: 30px;
  text-align: center;
  padding: 30px 0;
  color: #333
}

.login_container{
  background-color: #2b4b6b;
  height: 100%;
}
.login_box{
  width: 450px;
  height: 350px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  /*在横轴和纵轴上移动，百分比是根据盒子的高度进行位移的*/
  transform: translate(-50%,-50%);
  /*less支持语法嵌套，css不支持*/
}
.btns{
  display: flex;
  justify-content: flex-end;
}
.login_form{
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}
</style>
