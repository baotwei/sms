<template>
  <el-container>
    <el-main>
      <el-card>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="姓名" prop="name">
                <el-input name="ruleForm.name" :value="ruleForm.name"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="专业" prop="major">
                <el-cascader expand-trigger="hover" v-model="ruleForm.majorIdList" :options="options"
                  :props="{ value: 'mid', label: 'mname', children: 'majorList' }" @change="handleChange" clearable
                  disabled></el-cascader> </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="密码" prop="pass">
                <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="确认密码" prop="checkPass">
                <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="5"><el-form-item label="性别" prop="sex">
                <el-radio-group v-model="ruleForm.sex">
                  <el-radio v-model="ruleForm.sex" :label="1">男</el-radio>
                  <el-radio v-model="ruleForm.sex" :label="0">女</el-radio>
                </el-radio-group>
              </el-form-item></el-col>
            <el-col :span="5"><el-form-item label="民族" prop="nation">
                <el-input v-model="ruleForm.nation">民族</el-input>
              </el-form-item></el-col>
            <el-col :span="6">
              <el-form-item label="生日" prop="birthday">
                <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.birthday"
                  style="width: 100%"></el-date-picker>
              </el-form-item></el-col>
            <el-col :span="6"> <el-form-item label="电话" prop="phone">
                <el-input v-model="ruleForm.phone"></el-input>
              </el-form-item></el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="5" v-show="isTeacher != 'student'">
              <el-form-item label="职称" prop="posticon">
                <el-input v-model="ruleForm.posticon"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="isTeacher != 'student' ? 11 : 12">
              <el-form-item label="地址" prop="address">
                <el-input v-model="ruleForm.address"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="备注" prop="remarks">
                <el-input type="textarea" v-model="ruleForm.remarks"></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-main>
  </el-container>
</template>
<script>
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };

    return {
      isTeacher: sessionStorage.getItem('type'),
      options: [],
      ruleForm: {
        pass: '',
        checkPass: '',
        name: sessionStorage.getItem('name'),
        sex: null,
        nation: null,
        birthday: null,
        phone: null,
        address: null,
        majorIdList: null,
        posticon: null,
        remarks: null,
      },
      rules: {
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ],
        name: [
          { require: true, message: '名字不能为空', trigger: 'blur' }
        ]
      }
    };
  },
  created() {
    const that = this
    let ss = null
    const type = sessionStorage.getItem('type')
    if (type === 'student') {
      ss = sessionStorage.getItem('sno')
      axios.get('http://localhost:8084/' + type + '/findById/' + ss).then(function (resp) {
        console.log("--------------", resp.data)
        that.ruleForm.sex = resp.data.sex
        that.ruleForm.nation = resp.data.nation
        that.ruleForm.birthday = resp.data.birthday
        that.ruleForm.phone = resp.data.phone
        that.ruleForm.address = resp.data.address
        that.ruleForm.remarks = resp.data.remarks
        that.ruleForm.posticon = resp.data.posticon
        that.ruleForm.majorIdList = resp.data.majorIdList
      })
    } else {
      ss = sessionStorage.getItem('tno')
      axios.get('http://localhost:8084/' + type + '/findByTno/' + ss).then(function (resp) {
        console.log("--------------", resp.data)
        that.ruleForm.sex = resp.data.sex
        that.ruleForm.nation = resp.data.nation
        that.ruleForm.birthday = resp.data.birthday
        that.ruleForm.phone = resp.data.phone
        that.ruleForm.address = resp.data.address
        that.ruleForm.remarks = resp.data.remarks
        that.ruleForm.posticon = resp.data.posticon
        that.ruleForm.majorIdList = resp.data.majorIdList
      })
    }
    axios.get('http://localhost:8084/major/getMajorList').then(function (resp) {
      console.log("--------------", resp.data)
      that.options = resp.data
    })
    console.log("--------------", that.options)


  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const that = this
          sessionStorage.setItem('name', that.ruleForm.name)
          const type = sessionStorage.getItem('type')
          let form = null
          let ss = null
          if (type === 'student') {
            ss = 'Student'
            form = {
              sid: sessionStorage.getItem('sid'),
              sname: that.ruleForm.name,
              password: that.ruleForm.pass,
              sex: that.ruleForm.sex,
              nation: that.ruleForm.nation,
              birthday: that.ruleForm.birthday,
              phone: that.ruleForm.phone,
              address: that.ruleForm.address,
              remarks: that.ruleForm.remarks
            }
          }
          else {
            ss = 'Teacher'
            form = {
              tid: sessionStorage.getItem('tid'),
              tname: that.ruleForm.name,
              password: that.ruleForm.pass,
              sex: that.ruleForm.sex,
              nation: that.ruleForm.nation,
              birthday: that.ruleForm.birthday,
              phone: that.ruleForm.phone,
              posticon: that.ruleForm.posticon,
              address: that.ruleForm.address,
              remarks: that.ruleForm.remarks
            }
          }

          axios.post('http://localhost:8084/' + type + '/update' + ss, form).then(function (resp) {
            if (resp.data === true) {
              that.$message({
                showClose: true,
                message: '编辑成功',
                type: 'success'
              });
            }
            else {
              that.$message.error('编辑失败，联系管理员');
            }
            that.$router.push("/" + type + 'Home')
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleChange(value) {
      console.log(value);
    }
  }
}
</script>