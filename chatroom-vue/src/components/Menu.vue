<template>
  <div>
    <el-menu
      style="width: 200px;min-height: calc(100vh - 50px)"
      default-active=""
      router
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose">
      <el-menu-item index="chatroom">
        <i class="el-icon-chat-dot-round"></i>
        <span slot="title">聊天室</span>
      </el-menu-item>
      <el-menu-item index="user">
        <i class="el-icon-user"></i>
        <span slot="title">用户管理</span>
      </el-menu-item>
      <el-menu-item index="message">
        <i class="el-icon-time"></i>
        <span slot="title">消息记录</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'Menu'
}
</script>

<style scoped>

</style>
