<template>
  <div class="header">
    <div class="left">聊天系统</div>
    <div class="middle"></div>
    <div class="right">
      <el-dropdown>
        <span class="el-dropdown-link">
          <!--如果是刚注册的用户，会有默认昵称"用户xx"与默认头像-->
          <el-avatar :size="30" :src="user.avatar" style="position: relative; top: 10px"></el-avatar>
           {{ user.nickname }}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="$router.push('/person')">个人信息</el-dropdown-item>
          <el-dropdown-item divided @click.native="$router.push('/login')">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Header',
  // 父组件传来的user对象
  props: ['user']
}
</script>

<style scoped lang="less">
.header {
  display: flex; //弹性布局
  border-bottom: solid 1px #ccc;
  height: 50px;
  line-height: 50px;

  .left {
    width: 200px;
    padding-left: 40px;
    font-weight: bold;
    color: dodgerblue;
  }

  .middle {
    flex: 1;
  }

  .right {
    width: 100px;
  }
}

</style>
