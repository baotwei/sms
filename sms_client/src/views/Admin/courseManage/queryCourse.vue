<template>
  <div>
    <el-container>
      <el-main>
        <el-card>
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">

            <el-row>
              <el-col :span="6">
                <el-form-item label="课程名" prop="cname">
                  <el-input v-model="ruleForm.cname"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="2">
                <el-form-item label="模糊查询" prop="fuzzy">
                  <el-switch v-model="ruleForm.fuzzy"></el-switch>
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-form-item>
                  <el-button class="el-icon-refresh" type="primary" @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row >
              <el-col :span="6">
                <el-form-item label="成绩下限" prop="lowBound">
                  <el-input v-model.number="ruleForm.lowBound"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="成绩上限" prop="highBound">
                  <el-input v-model.number="ruleForm.highBound"></el-input>
                </el-form-item>
              </el-col>
              
            </el-row>

          </el-form>
        </el-card>

        <el-card style="margin-top:5px">
          <course-list :ruleForm="ruleForm" :isActive="true"></course-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>
<script>
import CourseList from "@/views/Admin/courseManage/courseList";
export default {
  components: { CourseList },
  data() {
    return {
      ruleForm: {
        cid: null,
        cname: null,
        fuzzy: true,
        lowBound: null,
        highBound: null
      },
      rules: {
        cid: [
          { type: 'number', message: '必须是数字类型' }
        ],
        cname: [
        ],
        lowBound: [
          { type: 'number', message: '必须是数字类型' }
        ],
        highBound: [
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