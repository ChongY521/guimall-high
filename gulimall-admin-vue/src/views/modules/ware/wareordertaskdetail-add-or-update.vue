<template>
<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
  <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
    <el-form-item label="sku_id" prop="skuId">
      <el-input v-model="dataForm.skuId" placeholder="sku_id"></el-input>
    </el-form-item>
    <el-form-item label="sku_name" prop="skuName">
      <el-input v-model="dataForm.skuName" placeholder="sku_name"></el-input>
    </el-form-item>
    <el-form-item label="购买个数" prop="skuNum">
      <el-input v-model="dataForm.skuNum" placeholder="购买个数"></el-input>
    </el-form-item>
    <el-form-item label="工作单id" prop="taskId">
      <el-input v-model="dataForm.taskId" placeholder="工作单id"></el-input>
    </el-form-item>
  </el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="visible = false">取消</el-button>
    <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
  </span>
</el-dialog>
</template>

<script>
import {
  WareOrderTaskDetailDetailInfoApi,
  WareOrderTaskDetailUpdateApi,
  WareOrderTaskDetailSaveApi
} from "@/api/ware/wareordertaskdetail.js"
import { SuccessMessage } from "@/utils/message.js";
export default {
  data() {
    return {
      visible: false,
      dataForm: {
        id: 0,
        skuId: '',
        skuName: '',
        skuNum: '',
        taskId: ''
      },
      dataRule: {
        skuId: [
          { required: true, message: 'sku_id不能为空', trigger: 'blur' }
        ],
        skuName: [
          { required: true, message: 'sku_name不能为空', trigger: 'blur' }
        ],
        skuNum: [
          { required: true, message: '购买个数不能为空', trigger: 'blur' }
        ],
        taskId: [
          { required: true, message: '工作单id不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init(id) {
      this.dataForm.id = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          WareOrderTaskDetailDetailInfoApi(this.dataForm.id).then(data => {
            this.dataForm.skuId = data.data.skuId
            this.dataForm.skuName = data.data.skuName
            this.dataForm.skuNum = data.data.skuNum
            this.dataForm.taskId = data.data.taskId
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          let data = {
            'id': this.dataForm.id || undefined,
            'skuId': this.dataForm.skuId,
            'skuName': this.dataForm.skuName,
            'skuNum': this.dataForm.skuNum,
            'taskId': this.dataForm.taskId
          };

          if (this.dataForm.id) {
            WareOrderTaskDetailUpdateApi(data).then(() => {
              SuccessMessage("修改成功");
              this.visible = false
              this.$emit('refreshDataList')
            })
          } else {
            WareOrderTaskDetailSaveApi(data).then(() => {
              SuccessMessage("添加成功");
              this.visible = false
              this.$emit('refreshDataList')
            })
          }
        }
      })
    }
  }
}
</script>
