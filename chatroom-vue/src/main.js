import Vue from 'vue'// vue构造函数
import App from './App.vue'// App根组件
import router from './router'
import './plugins/element.js'
import './assets/css/global.css'// 导入全局样式表
import axios from 'axios'// 导入axios

axios.defaults.baseURL = 'http://localhost:8080/'// 配置请求的根路径
// axios.defaults.baseURL = 'http://8.133.163.7:8080/'// 访问服务器

Vue.prototype.$http = axios// 挂载到vue的原型对象上，每一个vue组件都可以通过this（Vue的实例对象）直接访问到$http，从而去发起axios请求
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
