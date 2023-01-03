<template>
  <div>
    <el-card>
      <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="专业" prop="mname">
        <el-cascader expand-trigger="hover"  :options="options" v-model="majorIdList"
          :props="{ value: 'mid', label: 'mname', children: 'majorList' }" @change="handleChange" clearable
          ></el-cascader> </el-form-item>

      <el-form-item label="教师姓名" prop="tname">
        <el-input v-model="ruleForm.tname"></el-input>
      </el-form-item>
      <el-form-item label="初始密码" prop="password">
        <el-input v-model="ruleForm.password" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <!-- <el-button @click="test">test</el-button> -->
      </el-form-item>
    </el-form>
    </el-card>
    
  </div>
</template>
<script>
export default {
  data() {
    return {
      options: [],
      majorIdList:[],

      ruleForm: {
        mnumber:null,
        tname: '',
        password: ''
      },
      rules: {
        tname: [
          { required: true, message: '请输入名称', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'change' }
        ],
      }
    };
  },
  created(){
    const that = this
    axios.get('http://localhost:8084/major/getMajorList').then(function (resp) {
      console.log("--------------", resp.data)
      that.options = resp.data
    })
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 通过前端校验
          const that = this
          console.log(this.ruleForm)
          if (that.ruleForm.tname === 'admin') {
            that.$message({
              showClose: true,
              message: 'admin 不可添加',
              type: 'error'
            });
            return
          }
          axios.post("http://localhost:8084/teacher/addTeacher", this.ruleForm).then(function (resp) {
            if (resp.data === true) {
              that.$message({
                showClose: true,
                message: '插入成功',
                type: 'success'
              });
            }
            else {
              that.$message.error('插入失败，请检查数据库t');
            }
            that.$router.push("/queryTeacher")
          })
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    test() {
      console.log(this.ruleForm)
    },
    handleChange(value) {
      console.log(value);
      this.ruleForm.mnumber=value[2]
    }
  }
}
</script>