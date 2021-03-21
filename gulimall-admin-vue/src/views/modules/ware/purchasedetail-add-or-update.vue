<template>
<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
  <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
    <el-form-item label="采购商品id" prop="skuId">
      <el-input v-model="dataForm.skuId" placeholder="采购商品id"></el-input>
    </el-form-item>
    <el-form-item label="采购数量" prop="skuNum">
      <el-input v-model="dataForm.skuNum" placeholder="采购数量"></el-input>
    </el-form-item>
    <el-form-item label="仓库" prop="wareId">
      <el-select v-model="dataForm.wareId" placeholder="请选择仓库" clearable>
        <el-option :label="w.name" :value="w.id" v-for="w in wareList" :key="w.id"></el-option>
      </el-select>
    </el-form-item>
    <!-- [0新建，1已分配，2正在采购，3已完成，4采购失败] -->
    <!-- <el-form-item label="状态" prop="status">
        <el-select v-model="dataForm.status" placeholder="请选择状态" clearable>
          <el-option label="新建" :value="0"></el-option>
          <el-option label="已分配" :value="1"></el-option>
          <el-option label="正在采购" :value="2"></el-option>
          <el-option label="已完成" :value="3"></el-option>
          <el-option label="采购失败" :value="4"></el-option>
        </el-select>
      </el-form-item>-->
  </el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="visible = false">取消</el-button>
    <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
  </span>
</el-dialog>
</template>

<script>
import { WareInfoListApi } from "@/api/ware/wareinfo.js"
import {
  PurchaseDetailInfoApi,
  PurchaseDetailUpdateApi,
  PurchaseDetailSaveApi
} from "@/api/ware/purchasedetail.js"
import { WarningConfirm, SuccessMessage } from "@/utils/message.js";
export default {
  data() {
    return {
      visible: false,
      wareList: [],
      dataForm: {
        id: 0,
        purchaseId: "",
        skuId: "",
        skuNum: "",
        skuPrice: "",
        wareId: "",
        status: 0
      },
      dataRule: {
        skuId: [
          { required: true, message: "采购商品id不能为空", trigger: "blur" }
        ],
        skuNum: [
          { required: true, message: "采购数量不能为空", trigger: "blur" }
        ],
        wareId: [{ required: true, message: "仓库id不能为空", trigger: "blur" }]
      }
    };
  },
  created() {
    this.getWares();
  },
  methods: {
    getWares() {
      WareInfoListApi({
        page: 1,
        limit: 500
      }).then(data => {
        this.wareList = data.data.list;
      });
    },
    init(id) {
      this.dataForm.id = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.id) {
          PurchaseDetailInfoApi(this.dataForm.id).then(data => {
            this.dataForm.purchaseId = data.data.purchaseId;
            this.dataForm.skuId = data.data.skuId;
            this.dataForm.skuNum = data.data.skuNum;
            this.dataForm.skuPrice = data.data.skuPrice;
            this.dataForm.wareId = data.data.wareId;
            this.dataForm.status = data.data.status;
          });
        }
      });
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {

          let data = {
            id: this.dataForm.id || undefined,
            purchaseId: this.dataForm.purchaseId,
            skuId: this.dataForm.skuId,
            skuNum: this.dataForm.skuNum,
            skuPrice: this.dataForm.skuPrice,
            wareId: this.dataForm.wareId,
            status: this.dataForm.status
          };
          if (data.id) {
            PurchaseDetailUpdateApi(data).then(() => {
              SuccessMessage("修改成功")
              this.visible = false;
              this.$emit("refreshDataList");
            })
          } else {
            PurchaseDetailSaveApi(data).then(() => {
              SuccessMessage("添加成功")
              this.visible = false;
              this.$emit("refreshDataList");
            })
          }
        }
      });
    }
  }
};
</script>
