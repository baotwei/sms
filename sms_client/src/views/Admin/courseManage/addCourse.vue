<template>
  <div>
    <el-card>
      <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="专业" prop="major">
        <el-cascader expand-trigger="hover"  :options="options" v-model="majorIdList"
          :props="{ value: 'mid', label: 'mname', children: 'majorList' }" @change="handleChange" clearable
          ></el-cascader> </el-form-item>
      <el-form-item label="课程名" prop="cname">
        <el-input v-model="ruleForm.cname"></el-input>
      </el-form-item>
      <el-form-item label="学分" prop="ccredit">
        <el-input v-model.number="ruleForm.ccredit"></el-input>
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
        mno:null,
        cname: null,
        ccredit: null
      },
      rules: {
        cname: [
          { required: true, message: '请输入名称', trigger: 'blur' },
        ],
        ccredit: [
          { required: true, message: '请输入学分', trigger: 'change' },
          { type: 'number', message: '请输入数字', trigger: 'blur' },
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
          // console.log(this.ruleForm)

          axios.post("http://localhost:8084/course/save", this.ruleForm).then(function (resp) {
            console.log(resp)
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
            that.$router.push("/queryCourse")
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
      this.ruleForm.mno=value[2]
    }
  }
}
</script>