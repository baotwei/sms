<template>
  <div>
    <el-table :data="tableData" border show-header stripe style="width: 60%">
      <el-table-column fixed prop="mname" label="专业">
      </el-table-column>
      
      <el-table-column prop="cname" label="课程名称">
      </el-table-column>
      <el-table-column prop="tno" label="教师号">
      </el-table-column>
      <el-table-column prop="tname" label="教师名称">
      </el-table-column>
      <el-table-column label="操作">

        <template slot-scope="scope">
          <el-popconfirm confirm-button-text='选择' cancel-button-text='取消' icon="el-icon-info" title="确定选择该教师开设的课程？"
            @confirm="select(scope.row)">
            <el-button slot="reference" type="text" size="small">选择</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination" v-if="pageshow">
      <el-pagination background layout="prev, pager, next" :total="total" :current-page="curpage" :page-size="pageSize"
        @current-change="changePage">
      </el-pagination>
    </div>

  </div>
</template>

<script>
export default {
  methods: {
    select(row) {
      console.log(row)
      const cid = row.cid
      const tid = row.tid
      const sid = sessionStorage.getItem('sid')
      const term = sessionStorage.getItem('currentTerm')
      const sct = {
        cid: cid,
        tid: tid,
        sid: sid,
        term: term
      }
      const that = this
      axios.post('http://localhost:8084/SCT/save', sct).then(function (resp) {
        if (resp.data === '选课成功') {
          that.$message({
            showClose: true,
            message: '选课成功',
            type: 'success'
          });
        }
        else {
          that.$message({
            showClose: true,
            message: resp.data,
            type: 'error'
          });
        }
      })

    },
    deleteCourseTeacher(row) {
      const that = this
      axios.post('http://localhost:8084/courseTeacher/deleteById', row).then(function (resp) {
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
    changePage(page) {
      page = page - 1
      const that = this
      let start = page * that.pageSize, end = that.pageSize * (page + 1)
      let length = that.tmpList.length
      let ans = (end < length) ? end : length
      that.tableData = that.tmpList.slice(start, ans)
    },
  },

  data() {
    return {
      tableData: null,
      pageSize: 6,
      total: null,
      tmpList: null,
      type: sessionStorage.getItem('type'),
      curpage: 1,
      pageshow: true
    }
  },
  props: {
    ruleForm: Object
  },
  watch: {
    ruleForm: {
      handler(newRuleForm, oldRuleForm) {
        const that = this
        that.tmpList = null
        that.total = null
        that.tableData = null
        axios.post("http://localhost:8084/courseTeacher/findCourseTeacherInfo", newRuleForm).then(function (resp) {
          that.tmpList = resp.data
          console.log(resp.data)
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