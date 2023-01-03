<template>
  <div>
    <el-card>
      <el-table
          :data="tableData"
          border
          style="width: 70%">
        <el-table-column
            fixed
            prop="mname"
            label="专业"
            >
        </el-table-column>
        <el-table-column
            prop="cname"
            label="课程名称"
           >
        </el-table-column>
        <el-table-column
            prop="tno"
            label="教师号"
           >
        </el-table-column>
        <el-table-column
            prop="tname"
            label="教师名称"
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
                confirm-button-text='退课'
                cancel-button-text='取消'
                icon="el-icon-info"
                title="确定退课？"
                @confirm="deleteSCT(scope.row)"
            >
              <el-button slot="reference" type="text" size="small">退课</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination" v-if="pageshow">
      <el-pagination background layout="prev, pager, next" :total="total" :current-page="curpage"
      :page-size="pageSize" @current-change="changePage">
    </el-pagination>
    </div>
    </el-card>
  </div>
</template>

<script>
export default {
  methods: {
    deleteSCT(row) {
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
      axios.post('http://localhost:8084/SCT/deleteBySCT', sct).then(function (resp) {
        if (resp.data === true) {
          that.$message({
            showClose: true,
            message: '退课成功',
            type: 'success'
          });
          window.location.reload()
        }
        else {
          that.$message({
            showClose: true,
            message: '退课失败，请联系管理员',
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
      pageSize: 10,
      total: null,
      tmpList: null,
      type: sessionStorage.getItem('type'),
      curpage: 1,
      pageshow: true
    }
  },
  created() {
    const sid = sessionStorage.getItem('sid')
    const term = sessionStorage.getItem('currentTerm')
    const that = this

    axios.get('http://localhost:8084/SCT/findBySid/' + sid + '/' + term).then(function (resp) {
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
}
</script>