<template>
<el-dialog :title="!dataForm.id ? '新增用户' : '修改用户'" :close-on-click-modal="false" :visible.sync="visible">
  <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户名" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="登录帐号"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password" :class="{ 'is-required': !dataForm.id }">
      <el-input v-model="dataForm.password" type="password" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="comfirmPassword" :class="{ 'is-required': !dataForm.id }">
      <el-input v-model="dataForm.comfirmPassword" type="password" placeholder="确认密码"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="dataForm.email" placeholder="邮箱"></el-input>
    </el-form-item>
    <el-form-item label="手机号" prop="mobile">
      <el-input v-model="dataForm.mobile" placeholder="手机号"></el-input>
    </el-form-item>
    <el-form-item label="角色" size="mini" prop="roleIdList">
      <el-checkbox-group v-model="dataForm.roleIdList">
        <el-checkbox v-for="role in roleList" :key="role.roleId" :label="role.roleId">{{ role.roleName }}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <el-form-item label="状态" size="mini" prop="status">
      <el-radio-group v-model="dataForm.status">
        <el-radio :label="0">禁用</el-radio>
        <el-radio :label="1">正常</el-radio>
      </el-radio-group>
    </el-form-item>
  </el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="visible = false">取消</el-button>
    <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
  </span>
</el-dialog>
</template>

<script>
import { isEmail, isMobile } from "@/utils/validate";

import { UserInfoApi, UserSaveApi, UserUpdateApi } from "@/api/sys/user.js";
import { RoleSelectApi } from "@/api/sys/role.js";
import { SuccessMessage } from "@/utils/message.js"

export default {
  data() {
    var validatePassword = (rule, value, callback) => {
      if (!this.dataForm.id && !/\S/.test(value)) {
        callback(new Error("密码不能为空"));
      } else {
        callback();
      }
    };
    var validateComfirmPassword = (rule, value, callback) => {
      if (!this.dataForm.id && !/\S/.test(value)) {
        callback(new Error("确认密码不能为空"));
      } else if (this.dataForm.password !== value) {
        callback(new Error("确认密码与密码输入不一致"));
      } else {
        callback();
      }
    };
    var validateEmail = (rule, value, callback) => {
      if (!isEmail(value)) {
        callback(new Error("邮箱格式错误"));
      } else {
        callback();
      }
    };
    var validateMobile = (rule, value, callback) => {
      if (!isMobile(value)) {
        callback(new Error("手机号格式错误"));
      } else {
        callback();
      }
    };
    return {
      visible: false,
      roleList: [],
      dataForm: {
        id: 0,
        userName: "",
        password: "",
        comfirmPassword: "",
        salt: "",
        email: "",
        mobile: "",
        roleIdList: [],
        status: 1
      },
      dataRule: {
        userName: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
        ],
        password: [{ validator: validatePassword, trigger: "blur" }],
        comfirmPassword: [
          { validator: validateComfirmPassword, trigger: "blur" }
        ],
        email: [
          { required: true, message: "邮箱不能为空", trigger: "blur" },
          { validator: validateEmail, trigger: "blur" }
        ],
        mobile: [
          { required: true, message: "手机号不能为空", trigger: "blur" },
          { validator: validateMobile, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    init(id) {
      this.dataForm.id = id || 0;
      RoleSelectApi()
        .then(data => {
          this.roleList = data.list || [];
        })
        .then(() => {
          this.visible = true;
          this.$nextTick(() => {
            this.$refs["dataForm"].resetFields();
          });
        })
        .then(() => {
          if (this.dataForm.id) {
            UserInfoApi(this.dataForm.id).then(data => {
              this.dataForm.userName = data.user.username;
              this.dataForm.salt = data.user.salt;
              this.dataForm.email = data.user.email;
              this.dataForm.mobile = data.user.mobile;
              this.dataForm.roleIdList = data.user.roleIdList;
              this.dataForm.status = data.user.status;
            });
          }
        });
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          let data = {
            userId: this.dataForm.id || undefined,
            username: this.dataForm.userName,
            password: this.dataForm.password,
            salt: this.dataForm.salt,
            email: this.dataForm.email,
            mobile: this.dataForm.mobile,
            status: this.dataForm.status,
            roleIdList: this.dataForm.roleIdList
          }
          if (data.userId) {
            UserUpdateApi(data).then((resp) => {
              SuccessMessage('修改用户信息成功', () => {
                this.visible = false;
                this.$emit("refreshDataList");
              })
            })
          } else {
            UserSaveApi(data).then((resp) => {
              console.log('resp :>> ', resp);
              SuccessMessage('添加用户成功', () => {
                this.visible = false;
                this.$emit("refreshDataList");
              })
            })
          }
        }
      });
    }
  }
};
</script>
