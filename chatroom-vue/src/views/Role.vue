<template>
  <div style="padding: 10px">
    <!--功能区-->
    <div style="margin: 10px 0">
      <el-input v-model="search" clearable placeholder="请输入关键字" style="width: 15%"></el-input>
      <el-button @click="load" size="small" type="primary" style="margin:0 5px" icon="el-icon-search">查询</el-button>
      <el-button @click="add" size="small" type="primary" icon="el-icon-plus">新增</el-button>
    </div>
    <!--数据区-->
    <el-table
      v-loading="loading"
      size="small"
      :data="tableData"
      border
      stripe
      style="width: 100%">
      <el-table-column
        prop="id"
        label="ID"
        sortable
      >
      </el-table-column>
      <el-table-column
        prop="name"
        label="名称">
      </el-table-column>
      <el-table-column
        prop="comment"
        label="备注">
      </el-table-column>
      <!--给角色分配权限-->
      <el-table-column
        label="权限菜单" prop="permissions">
        <!--作用域插槽：子组件给父组件传递数据（scope对象）-->
        <template slot-scope="scope">
          <!--v-model绑定的是选中的value组成的数组-->
          <el-select v-model="scope.row.permissionsId" multiple placeholder="请选择">
            <el-option
              v-for="item in allPermissions"
              :key="item.id"
              :label="item.comment"
              :value="item.id">
            </el-option>
          </el-select>
        </template>
      </el-table-column>
      <!--操作-->
      <el-table-column label="操作">
        <!--通过带属性的插槽可以获取到 row, column, $index 和 store（table 内部的状态管理）的数据-->
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleChange(scope.row)">保存权限菜单</el-button>
          <el-button size="mini" icon="el-icon-edit" title="编辑"
                     @click="handleEdit( scope.row)"></el-button>
          <el-button size="mini" type="danger" icon="el-icon-delete" title="删除"
                     @click="handleDelete(scope.row.id)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <div style="margin: 10px 0">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="currentPage" :page-sizes="pageSizes" :page-size="pageSize" :total="total"
                     layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </div>
    <!--弹窗-->
    <el-dialog
      title="信息"
      :visible.sync="dialogVisible"
      width="30%">
      <el-form ref="roleForm" :model="form" :rules="formRules" label-width="120px">
        <el-form-item label="用户名" prop="name">
          <el-input v-model="form.name" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="comment">
          <el-input v-model="form.comment" style="width: 80%"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="resetForm">重 置</el-button>
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>
  </div>

</template>
<script>
export default {
  name: 'Role',
  data () {
    return {
      loading: false,
      tableData: [],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页的大小
      pageSizes: [5, 10, 20, 30], // 可选择的每页大小
      total: 100, // 数据总数
      dialogVisible: false, // 弹窗默认不可视
      form: {},
      search: '',
      formRules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      },
      allPermissions: []
    }
  },
  mounted () {
    // 挂载完成后，加载load方法，去渲染table
    this.load()
  },
  methods: {
    // 重置表单
    resetForm () {
      // this.$refs是一个对象，其中的属性formRef是表单的实例对象，可以直接调用表单方法
      this.$refs.roleForm.resetFields()
    },
    // 渲染table中的数据
    async load () {
      this.loading = true
      // 获取用户
      const { data: res } = await this.$http.get('/role', {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      })
      if (res.flag) {
        this.total = res.data.total
        // 记录集合，其中每一个记录都有permissionsId属性，为自己权限id组成的数组
        this.tableData = res.data.records
      } else {
        this.$message.error('加载失败')
      }
      // 获取全部可用权限
      const { data: res2 } = await this.$http.get('/permission/all')
      if (res2.flag) {
        this.allPermissions = res2.data
      } else {
        this.$message.error(res2.message)
      }
      this.loading = false
    },
    // 保存权限的更改
    async handleChange (row) {
      // 当前的roleId
      const roleId = row.id
      const userId = JSON.parse(sessionStorage.getItem('user')).id

      // 保存权限的更改
      const { data: res } = await this.$http.put('/role/changePermission', row)
      if (res.flag) {
        this.$message.success('保存成功')
        // 获取当前用户的roleId，如果操作是其所属role，那么需要重新登录
        const { data: res2 } = await this.$http.get('/role/getByUserId', { params: { userId: userId } })
        // res2.data中是当前用户的角色集合
        res2.data.forEach(role => {
          // 如果当前操作的角色是当前登录用户的角色，那么需要重新登录
          if (role.id === roleId) {
            this.$router.push('/login')
          }
        })
      } else {
        this.$message.error(res.message)
      }
    },
    // 改变当前每页的个数
    handleSizeChange (pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    // 改变当前页码数
    handleCurrentChange (pageNum) {
      this.currentPage = pageNum
      this.load()
    },
    add () {
      // 弹出框可见
      this.dialogVisible = true
      this.form = {}// 重置当前form为空
    },
    save () {
      // 首先根据有无ID判断是更新（有ID）还是插入（无ID）
      this.$refs.roleForm.validate(async valid => {
        if (!valid) return
        let res
        if (this.form.id) { // 更新
          // 解构赋值：将data赋值给res
          res = (await this.$http.put('/role', this.form)).data
        } else { // 插入
          res = (await this.$http.post('/role', this.form)).data
        }
        if (res.flag) {
          this.$message.success(res.message)
        } else {
          return this.$message.error(res.message)
        }
        this.load() // 刷新表格的数据
        this.dialogVisible = false // 关闭弹窗
      })
    },
    // 编辑事件
    handleEdit (row) {
      // 深拷贝
      this.form = JSON.parse(JSON.stringify(row))
      // 打开弹窗
      this.dialogVisible = true
    },
    // 删除事件
    async handleDelete (id) {
      const { data: res } = await this.$http.delete('/role/' + id)
      if (res.flag) {
        this.$message.success(res.message)
      } else {
        this.$message.error(res.message)
      }
      // 删除之后重新加载表格的数据
      this.load()
    }
  }
}
</script>

<style scoped lang="less">
</style>
