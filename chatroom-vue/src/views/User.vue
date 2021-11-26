<template>
  <div style="padding: 10px">
    <!--功能区域-->
    <div style="margin: 10px 0 ">
      <el-button @click="add" size="small" type="primary">新增</el-button>
    </div>
    <!--搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search" clearable placeholder="请输入关键字" style="width: 20%"></el-input>
      <el-button @click="load" size="small" type="primary" style="margin-left: 5px">查询</el-button>
    </div>
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
        prop="username"
        label="用户名">
      </el-table-column>
      <el-table-column
        prop="nickname"
        label="昵称">
      </el-table-column>
      <el-table-column
        prop="age"
        label="年龄">
      </el-table-column>
      <el-table-column
        prop="sex"
        label="性别">
      </el-table-column>
      <el-table-column
        prop="address"
        label="地址">
      </el-table-column>
      <el-table-column
        prop="avatar"
        label="头像">
      </el-table-column>
      <!--操作-->
        <el-table-column label="操作">
          <!--通过 Scoped slot 可以获取到 row, column, $index 和 store（table 内部的状态管理）的数据-->
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit( scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" slot="reference"
                         @click="handleDelete(scope.row.id)">删除</el-button>
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
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <el-form ref="formRef" :model="form" :rules="formRules" label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="form.sex" label="男">男</el-radio>
          <el-radio v-model="form.sex" label="女">女</el-radio>
          <el-radio v-model="form.sex" label="未知">未知</el-radio>
        </el-form-item>
        <el-form-item label="地址">
          <el-input type="textarea" v-model="form.address" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <el-input v-model="form.avatar" style="width: 80%"></el-input>
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
  name: 'User',
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
        username: [
          {
            required: true,
            message: '请输入用户名',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  mounted () {
    // 挂载完成后，加载load方法，去渲染table
    this.load()
  },
  methods: {
    // 渲染table中的数据
    async load () {
      this.loading = true
      const { data: res } = await this.$http.get('/user', {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      })
      if (res.flag) {
        this.loading = false
        this.total = res.data.total
        this.tableData = res.data.records
      } else {
        this.$message.error('加载失败')
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
      this.$refs.formRef.validate(async valid => {
        if (!valid) return
        if (this.form.id) { // 更新
          // 解构赋值：将data赋值给res
          const { data: res } = await this.$http.put('/user', this.form)
          if (res.flag) {
            this.$message.success(res.message)
          } else {
            return this.$message.error(res.message)
          }
          this.load() // 刷新表格的数据
          this.dialogVisible = false // 关闭弹窗
        } else { // 插入
          const { data: res } = await this.$http.post('/user', this.form)
          if (res.flag) {
            this.$message.success(res.message)
          } else {
            return this.$message.error(res.message)
          }
          this.load() // 刷新表格的数据
          this.dialogVisible = false // 关闭弹窗
        }
      })
    },
    // 编辑事件
    handleEdit (row) {
      // 深拷贝
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    // 删除事件
    async handleDelete (id) {
      const { data: res } = await this.$http.delete('/user/' + id)
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
