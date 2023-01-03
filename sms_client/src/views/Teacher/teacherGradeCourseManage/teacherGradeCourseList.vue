<template>
  <div>
    <el-table :data="tableData" border stripe style="width: 70%" empty-text>
      <el-table-column fixed prop="mname" label="专业">
      </el-table-column>
      <el-table-column prop="cid" label="课程号">
      </el-table-column>
      <el-table-column prop="cname" label="课程名">
      </el-table-column>
      <el-table-column fixed prop="sno" label="学号">
      </el-table-column>
      <el-table-column prop="sname" label="学生名">
      </el-table-column>
      <el-table-column prop="grade" label="成绩">
      </el-table-column>
      <el-table-column prop="term" label="学期">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button @click="editor(scope.row)" type="text" size="small">编辑</el-button>
          <el-dialog title="评分" :visible.sync="dialogFormVisible" append-to-body>
            <el-form style="width: 70%" :model="templateList" label-width="120px" class="demo-ruleForm">
              <el-form-item label="课程名称" prop="cname">
                <el-input v-model="templateList.cname" :value="templateList.cname" :disabled="true"></el-input>
              </el-form-item>
              <el-form-item label="教师名" prop="tname">
                <el-input v-model="templateList.tname" :value="templateList.tname" :disabled="true"></el-input>
              </el-form-item>
              <el-form-item label="学生名" prop="sname">
                <el-input v-model="templateList.sname" :value="templateList.sname" :disabled="true"></el-input>
              </el-form-item>
              <el-form-item label="分数" prop="grade">
                <el-input v-model.number="templateList.grade" :value="templateList.grade"></el-input>
              </el-form-item>
              <el-form-item>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="editGrade()">确 定</el-button>
            </div>
          </el-dialog>
        </template>
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
  </div>

</template>

<script>
export default {
  inject: ['reload'],
  methods: {
    select(row) {
      console.log('row', row)
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
      this.dialogFormVisible = true
      this.templateList = extendCopy(row);
      // 对象进行深拷贝，否则“编辑”框内修改内容会影响到页面
      console.log(this.templateList);
      this.isEdit = false
      function extendCopy(p) {
        var c = {};
        for (var i in p) {
          c[i] = p[i];
        }
        c.uber = p;
        return c;
      }

    },
    editGrade() {

      const that = this
      const sid = that.templateList.sid
      const cid = that.templateList.cid
      const tid = that.templateList.tid
      const term = that.templateList.term
      const grade = that.templateList.grade
      axios.get("http://localhost:8084/SCT/updateById/" + sid + '/' + cid + '/' + tid + '/' + term + '/' + grade).then(function (resp) {
        if (resp.data === true) {
          that.$message({
            showClose: true,
            message: '编辑成功',
            type: 'success'
          });

        }
        else {
          that.$message.error('编辑失败，请检查数据库');
        }
      })
      this.dialogFormVisible = false
      that.reload()

    },


  },
  data() {
    return {
      tableData: null,
      pageSize: 5,
      total: null,
      tmpList: null,
      avg: 0,
      templateList: [],
      loading: false,
      dialogFormVisible: false,
      formLabelWidth: '120px',
      curpage: 1,
      pageshow: true
    }
  },
  props: {
    ruleForm: Object,
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


        axios.post("http://localhost:8084/SCT/findBySearch", newRuleForm).then(function (resp) {
          console.log("查询结果:");
          console.log(resp)
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
            that.avg += that.tmpList[i].grade
          }
          if (that.total == 0)
            that.avg = 0
          else
            that.avg /= that.total

        })
      },
      deep: true,
      immediate: true
    }
  },
}
</script>