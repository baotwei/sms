<template>
  <div>
    <el-container>
      <el-main>
        <el-card>
          <el-form  :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="工号" prop="tno">
                  <el-input v-model.number="ruleForm.tno"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="教师名" prop="tname">
                  <el-input v-model="ruleForm.tname"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-form-item label="模糊查询" prop="tFuzzy">
                  <el-switch v-model="ruleForm.tFuzzy"></el-switch>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="课程名" prop="cname">
              <el-input v-model.number="ruleForm.cname"></el-input>
            </el-form-item>
              </el-col>
              <el-col :span="2">
                <el-form-item label="课程模糊查询">
              <el-switch v-model="ruleForm.cFuzzy"></el-switch>
            </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-form-item>
              <el-button type="primary" @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
              </el-col>
            </el-row>
            
            
           
          </el-form>
        </el-card>
        <el-card style="margin-top: 10px">
          <course-tacher-list :ruleForm="ruleForm"></course-tacher-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>
<script>
import CourseTacherList from "@/views/Admin/selectCourseManage/CourseTacherList";
export default {
  components: {CourseTacherList},
  data() {
    return {
      ruleForm: {
        tno: null,
        cid: null,
        cname: null,
        tname: null,
        tFuzzy: true,
        cFuzzy: true
      },
      rules: {
        tno: [
          { type: 'number', message: '必须是数字类型' }
        ],
        cid: [
          { type: 'number', message: '必须是数字类型' }
        ],
      }
    };
  },
  methods: {
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>