// 按需导入
import Vue from 'vue'
import {
  Button, Form, FormItem, Input, Message,
  Container, Header, Aside, Main, Card, Row, Col,
  Dropdown, DropdownItem, DropdownMenu, Menu,
  MenuItem, MenuItemGroup, Submenu,
  Avatar, Table, TableColumn, Popconfirm,
  Pagination, Dialog, Radio, Descriptions, DescriptionsItem,
  Select, Option
} from 'element-ui'
// 使用Vue.use进行全局注册
Vue.use(Button)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Input)
Vue.use(Container)
Vue.use(Header)
Vue.use(Aside)
Vue.use(Main)
Vue.use(Card)
Vue.use(Row)
Vue.use(Col)
Vue.use(Dropdown)
Vue.use(DropdownItem)
Vue.use(DropdownMenu)
Vue.use(Menu)
Vue.use(MenuItem)
Vue.use(MenuItemGroup)
Vue.use(Submenu)
Vue.use(Avatar)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Popconfirm)
Vue.use(Pagination)
Vue.use(Dialog)
Vue.use(Radio)
Vue.use(Descriptions)
Vue.use(DescriptionsItem)
Vue.use(Select)
Vue.use(Option)

Vue.prototype.$message = Message
