<template>
  <div>
    <el-table :data="tableData" border style="width: 80%">
      <el-table-column fixed prop="mname" label="专业">
      </el-table-column>
      <el-table-column fixed prop="tno" label="工号">
      </el-table-column>
      <el-table-column prop="tname" label="姓名">
      </el-table-column>
      <el-table-column prop="password" label="密码">
      </el-table-column>
      <el-table-column prop="phone" label="电话">
      </el-table-column>
      <el-table-column prop="posticon" label="职称">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-popconfirm confirm-button-text='删除' cancel-button-text='取消' icon="el-icon-info" icon-color="red"
            title="删除不可复原" @confirm="deleteTeacher(scope.row)">
            <el-button slot="reference" type="text" size="small">删除</el-button>
          </el-popconfirm>
          <el-button @click="editor(scope.row)" type="text" size="small">编辑</el-button>
          <el-dialog title="修改教师信息" :visible.sync="dialogFormVisible" append-to-body destroy-on-close >
            <el-form style="width: 70%" :model="templateList" :rules="rules" label-width="120px" class="demo-ruleForm">
              <el-form-item label="教师姓名" prop="tname">
                <el-input v-model="templateList.tname" :value="templateList.tname"></el-input>
              </el-form-item>
              <el-form-item label="初始密码" prop="password">
                <el-input v-model="templateList.password" :value="templateList.password"></el-input>
              </el-form-item>
              <el-form-item label="电话" prop="phone">
                <el-input v-model="templateList.phone" :value="templateList.phone"></el-input>
              </el-form-item>
              <el-form-item label="职称" prop="posticon">
                <el-input v-model="templateList.posticon" :value="templateList.posticon"></el-input>
              </el-form-item>
            
                <el-form-item label="性别" prop="sex">
                <el-radio-group v-model="templateList.sex">
                  <el-radio v-model="templateList.sex" :label="1">男</el-radio>
                  <el-radio v-model="templateList.sex" :label="0">女</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="生日" prop="birthday">
                <el-date-picker type="date" placeholder="选择日期" v-model="templateList.birthday"
                  style="width: 100%"></el-date-picker>
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
    deleteTeacher(row) {
      if (row.tname === 'admin') {
        this.$message({
          showClose: true,
          message: 'admin 不可删除',
          type: 'error'
        });
        return
      }
      const that = this
      axios.get('http://localhost:8084/teacher/deleteById/' + row.tid).then(function (resp) {
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
      }).catch(function (e) {
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
      if (row.tname === 'admin') {
        this.$message({
          showClose: true,
          message: 'admin 不可编辑',
          type: 'error'
        });
        return
      }
    
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
      axios.post("http://localhost:8084/teacher/updateTeacher", this.templateList).then(function (resp) {
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
        that.$router.push("/queryTeacher")
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

      templateList: [],
      loading: false,
      dialogFormVisible: false,
      formLabelWidth: '120px',
      curpage: 1,
      pageshow: true,
      rules: {
        tname: [
          { required: true, message: '请输入名称', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'change' }
        ],
      }
    }
  },
  props: {
    ruleForm: Object
  },
  watch: {
    ruleForm: {
      handler(newRuleForm, oldRuleForm) {
        console.log("组件监听 form")
        const that = this
        that.tmpList = null
        that.total = null
        that.tableData = null
        axios.post("http://localhost:8084/teacher/findBySearch", newRuleForm).then(function (resp) {
          console.log("查询结果:");
          console.log(newRuleForm)
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