<!--新的渲染出口-->
<template>
  <div>
    <!--    头部-->
    <Header :user="user"/>
    <!--主体-->
    <div style="display:flex">
      <!--侧边菜单-->
      <Menu></Menu>
      <!--右侧内容-->
      <router-view @userInfo="refreshUser" style="flex:1"/>
    </div>
  </div>
</template>

<script>
import Header from '@/components/Header'
import Menu from '@/components/Menu'

export default {
  name: 'Layout',
  components: {
    Header,
    Menu
  },
  data () {
    return {
      user: {}
    }
  },
  mounted () {
    this.refreshUser()
  },
  methods: {
    async refreshUser () {
      const userJson = sessionStorage.getItem('user')
      if (!userJson) {
        return// user不存在，直接返回
      }
      const userId = JSON.parse(userJson).id
      // 从后台取出更新后的最新用户信息
      const { data: res } = await this.$http('/user/' + userId)
      this.user = res.data
    }
  }
}
</script>

<style scoped>

</style>
