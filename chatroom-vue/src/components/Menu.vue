<template>
  <div>
    <el-menu
      style="width: 200px;min-height: calc(100vh - 50px)"
      default-active=""
      router
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose">
      <el-menu-item :index=p.path v-for="(p,index) in user.permissions" :key="index">
        <i :class=p.icon></i>
        <span slot="title">{{p.comment}}</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'Menu',
  data: function () {
    return {
      user: {}
    }
  },
  mounted () {
    this.user = JSON.parse(sessionStorage.getItem('user'))
  }
}
</script>

<style scoped>

</style>
