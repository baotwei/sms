<template>
  <div>
    <el-table :data="tableData" border stripe style="width: 80%">
      <el-table-column fixed prop="mname" label="专业">
      </el-table-column>
      <el-table-column prop="cname" label="课程名">
      </el-table-column>
      
      <el-table-column prop="tname" label="教师名">
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
          <el-popconfirm confirm-button-text='删除' cancel-button-text='取消' icon="el-icon-info" icon-color="red"
            title="删除不可复原" @confirm="deleteTeacher(scope.row)">
            <el-button slot="reference" type="text" size="small">删除</el-button>
          </el-popconfirm>
          <el-button @click="editor(scope.row)" type="text" size="small">编辑</el-button>
          <el-dialog title="修改学生成绩" :visible.sync="dialogFormVisible" append-to-body>
            <el-form style="width: 70%" :model="templateList" :rules="rules" label-width="120px" class="demo-ruleForm">
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
      console.log(row)
      const sid = row.sid
      const cid = row.cid
      const tid = row.tid
      const term = row.term
      axios.get("http://localhost:8084/SCT/deleteById/" + sid + '/' + cid + '/' + tid + '/' + term).then(function (resp) {
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
      axios.get("http://localhost:8084/SCT/updateById/" + this.templateList.sid + '/' 
      + this.templateList.cid + '/' + this.templateList.tid + '/' + this.templateList.term + '/' 
      + this.templateList.grade).then(function (resp) {
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
        if (sessionStorage.getItem('type') === 'admin') {
          that.$router.push("/queryGradeCourse")
        } else {
          that.$router.push("/teacherQueryGradeCourseManage")
        }
      })
      this.dialogFormVisible = false
      that.reload()

    },
  },

  data() {
    var checkGrade = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('成绩不能为空'));
      }
      if (!Number.isInteger(value)) {
        callback(new Error('请输入数字值'));
      } else {
        if (value > 100 || value < 0) {
          callback(new Error('成绩范围 [0 - 100]'));
        } else {
          callback();
        }
      }
    };
    return {
      tableData: null,
      pageSize: 5,
      total: null,
      tmpList: null,

      templateList: [],
      loading: false,
      dialogFormVisible: false,
      formLabelWidth: '120px',
      curpage: 1,
      pageshow: true,
      rules: {
        grade: [
          { required: true, message: '请输入学分', trigger: 'change'},
          { type: 'number', message: '请输入数字', trigger: 'change'},
          { validator: checkGrade, trigger: 'blur'}
        ],
      }
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
        })
      },
      deep: true,
      immediate: true
    }
  },
}
</script>