<template>
  <div>
    <el-table :data="tableData" border stripe style="width: 40%">
      <el-table-column fixed prop="mname" label="专业" width="150">
      </el-table-column>
      <el-table-column prop="cname" label="课程名">
      </el-table-column>
      <el-table-column prop="ccredit" label="学分">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-popconfirm confirm-button-text='删除' cancel-button-text='取消' icon="el-icon-info" icon-color="red"
            title="删除不可复原" @confirm="deleteTeacher(scope.row)">
            <el-button slot="reference" type="text" size="small">删除</el-button>
          </el-popconfirm>
          <el-button @click="editor(scope.row)" type="text" size="small">编辑</el-button>
          <el-dialog title="修改课程信息" :visible.sync="dialogFormVisible" append-to-body>
            <el-form style="width: 70%" :model="templateList" :rules="rules" label-width="120px" class="demo-ruleForm">
              
              <el-form-item label="课程名称" prop="cname">
                <el-input v-model="templateList.cname" :value="templateList.cname"></el-input>
              </el-form-item>
              <el-form-item label="学分" prop="ccredit">
                <el-input v-model.number="templateList.ccredit" :value="templateList.ccredit"></el-input>
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
      console.log(row)
    },
    deleteTeacher(row) {
      const that = this
      axios.get('http://localhost:8084/course/deleteById/' + row.cid).then(function (resp) {
        console.log(resp)
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
      }).catch(function (error) {
        that.$message({
          showClose: true,
          message: '删除出错，存在外键依赖',
          type: 'error'
        });
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
      axios.post("http://localhost:8084/course/updateCourse", this.templateList).then(function (resp) {
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
            that.$router.push("/queryCourse")
          })
      this.dialogFormVisible = false
      that.reload()

    }
  },
  created() {
    console.log(this.type)
  },
  data() {
    return {

      tableData: null,
      pageSize: 5,
      total: null,
      tmpList: null,
      type: sessionStorage.getItem("type"),

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
        })
      },
      deep: true,
      immediate: true
    }
  },
}
</script>