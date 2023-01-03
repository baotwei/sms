<template>
  <div>
    <el-container>
      <el-main>
        <el-card>
          <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="学号" prop="sno">
              <el-input v-model.number="ruleForm.sno"></el-input>
            </el-form-item>
            <el-form-item label="学生姓名" prop="sname">
              <el-input v-model="ruleForm.sname"></el-input>
            </el-form-item>
            <el-form-item label="模糊查询" prop="password">
              <el-switch v-model="ruleForm.password"></el-switch>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="resetForm('ruleForm')">重置</el-button>
              <el-button @click="flush('ruleForm')">刷新</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card style="margin-top:5px">
          <studentList :ruleForm="this.ruleForm"></studentList>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>
<script>
import studentList from "@/views/Admin/studentManage/studentList";

export default {
  components: {studentList},

  data() {
    
    return {
      ruleForm: {
        sno: null,
        sname: null,
        password: true
      },
      rules: {
        sid: [
          { type: 'number', message: '必须是数字类型' }
        ],
        sname: [

        ],
      }
    };
  },
  create() {
    this.sno = null
    this.sname = null
    this.password = true
  },
  methods: {
   
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>