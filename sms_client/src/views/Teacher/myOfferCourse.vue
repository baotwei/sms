<template>
  <div>
    <el-container>
      <el-main>
        <h1>当前学期开设的课程</h1>
        <el-card>
          <el-table
              :data="tableData"
              border
              stripe
              style="width: 40%">
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
          </el-table>
          <div class="pagination" v-if="pageshow">
      <el-pagination background layout="prev, pager, next" :total="total" :current-page="curpage"
      :page-size="pageSize" @current-change="changePage">
    </el-pagination>
    </div>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  methods: {
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
      tid: null,
      term: null,
      curpage: 1,
      pageshow: true
    }
  },
  created() {
    this.tid = sessionStorage.getItem("tid");
    this.term = sessionStorage.getItem("currentTerm");

    console.log(this.term)

    const that = this
    axios.get('http://localhost:8084/courseTeacher/findMyCourse/' + this.tid + '/' + this.term).then(function (resp) {

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

  }

}
</script>