<template>
  <div>
    <el-form>
      <el-form-item label="选择学期">
        <el-select v-model="term" placeholder="请选择学期">
          <el-option v-for="(item, index) in termList" :key="index" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <el-card>
      <el-table :data="tableData" border style="width: 75%">
        <el-table-column fixed prop="mname" label="专业">
        </el-table-column>
        <el-table-column prop="cname" label="课程名称">
        </el-table-column>
        <el-table-column prop="tno" label="教师号">
        </el-table-column>
        <el-table-column prop="tname" label="教师名称">
        </el-table-column>
        <el-table-column prop="ccredit" label="学分">
        </el-table-column>
        <el-table-column prop="grade" label="成绩">
        </el-table-column>
      </el-table>
      <p>
        平均成绩：{{ avg }}
      </p>
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
      avg: 0,
      term: sessionStorage.getItem('currentTerm'),
      termList: null,
      curpage: 1,
      pageshow: true
    }
  },
  created() {
    const that = this
    axios.get('http://localhost:8084/SCT/findAllTerm').then(function (resp) {
      that.termList = resp.data
    })
  },
  watch: {
    term: {
      handler(newTerm, oldTerm) {
        const sid = sessionStorage.getItem('sid')
        const that = this
        axios.get('http://localhost:8084/SCT/findBySid/' + sid + '/' + newTerm).then(function (resp) {
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

          that.avg = 0
          for (let i = 0; i < that.total; i++) {
            // totalScore += that.tmpList[i].ccredit
            // that.avg += that.tmpList[i].ccredit * that.tmpList[i].grade
            that.avg += that.tmpList[i].grade
            console.log(that.avg)
          }
          if (that.total == 0)
            that.avg = 0
          else
            that.avg /= that.total
          // if (totalScore === 0)
          //   that.avg = 0
          // else
          //   that.avg /= totalScore
        })
      },
      immediate: true
    }
  }
}
</script>

