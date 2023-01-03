<template>
  <div>
    <el-table
        :data="tableData"
        border
        stripe
        style="width: 50%">
      <el-table-column
          fixed
          prop="mname"
          label="专业"
          >
      </el-table-column>
      <el-table-column
          prop="cname"
          label="课程名"
         >
      </el-table-column>
      <el-table-column
          prop="ccredit"
          label="学分"
          >
      </el-table-column>
      <el-table-column
          label="操作"
          >
        <template slot-scope="scope">
          <el-popconfirm
              confirm-button-text='选择'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="确定开设？"
              @confirm="offer(scope.row)"
          >
            <el-button slot="reference" type="text" size="small">开设</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination" v-if="pageshow">
      <el-pagination background layout="prev, pager, next" :total="total" :current-page="curpage"
      :page-size="pageSize" @current-change="changePage">
    </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    select(row) {
      console.log(row)
    },
    deleteTeacher(row) {
      const that = this
      axios.get('http://localhost:8084/course/deleteById/' + row.cid).then(function (resp) {
        if (resp.data === true) {
          that.$message({
            showClose: true,
            message: '删除成功',
            type: 'success'
          });
          window.location.reload()
        }
        else {
          that.$message({
            showClose: true,
            message: '删除出错，请查询数据库连接',
            type: 'error'
          });
        }
      })
    },
    offer(row) {
      const tid = sessionStorage.getItem("tid")
      const cid = row.cid
      const term = sessionStorage.getItem("currentTerm")

      const that = this
      axios.get('http://localhost:8084/courseTeacher/insert/' + cid + '/' + tid + '/' + term).then(function (resp) {
        if (resp.data === true) {
          that.$message({
            showClose: true,
            message: '开设成功',
            type: 'success'
          });
          window.location.reload()
        }
        else {
          that.$message({
            showClose: true,
            message: '开设失败，请联系管理员',
            type: 'error'
          });
        }
      })


    },
    changePage(page) {
      page = page - 1
      const that = this
      let start = page * that.pageSize, end = that.pageSize * (page + 1)
      let length = that.tmpList.length
      let ans = (end < length) ? end : length
      that.tableData = that.tmpList.slice(start, ans)
    },
    editor(row) {
      this.$router.push({
        path: '/editorCourse',
        query: {
          cid: row.cid
        }
      })
    }
  },
  created() {
    console.log(this.type)
  },
  data() {
    return {
      tableData: null,
      pageSize: 6,
      total: null,
      tmpList: null,
      type: sessionStorage.getItem("type"),
      curpage: 1,
      pageshow: true
    }
  },
  props: {
    ruleForm: Object,
    isActive: Boolean
  },
  watch: {
    ruleForm: {
      handler(newRuleForm, oldRuleForm) {
        console.log("组件监听 form")
        console.log(newRuleForm)
        const that = this
        that.tmpList = null
        that.total = null
        that.tableData = null
        axios.post("http://localhost:8084/course/findBySearch", newRuleForm).then(function (resp) {
          console.log("查询结果:");
          console.log(resp.data)
          that.tmpList = resp.data
          that.total = resp.data.length
          let start = 0, end = that.pageSize
          let length = that.tmpList.length
          let ans = (end < length) ? end : length
          that.tableData = that.tmpList.slice(start, ans)
          that.curpage = 1;//cur_page 当前页
          that.pageshow = false;//让分页隐藏
          that.$nextTick(() => {//重新渲染分页
            that.pageshow = true;
          });
        })
      },
      deep: true,
      immediate: true
    }
  },
}
</script>