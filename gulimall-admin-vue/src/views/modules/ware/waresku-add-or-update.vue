<template>
<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
  <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
    <el-form-item label="sku_id" prop="skuId">
      <el-input v-model="dataForm.skuId" placeholder="sku_id"></el-input>
    </el-form-item>
    <el-form-item label="仓库" prop="wareId">
      <el-select v-model="dataForm.wareId" placeholder="请选择仓库" clearable>
        <el-option :label="w.name" :value="w.id" v-for="w in wareList" :key="w.id"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="库存数" prop="stock">
      <el-input v-model="dataForm.stock" placeholder="库存数"></el-input>
    </el-form-item>
    <el-form-item label="sku_name" prop="skuName">
      <el-input v-model="dataForm.skuName" placeholder="sku_name"></el-input>
    </el-form-item>
    <el-form-item label="锁定库存" prop="stockLocked">
      <el-input v-model="dataForm.stockLocked" placeholder="锁定库存"></el-input>
    </el-form-item>
  </el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="visible = false">取消</el-button>
    <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
  </span>
</el-dialog>
</template>

<script>
import { WareInfoListApi } from "@/api/ware/wareinfo.js"
import { WareSkuInfoApi, WareSkuSaveApi, WareSkuUpdateApi } from "@/api/ware/waresku.js"
import { SuccessMessage } from "@/utils/message.js";

export default {
  data() {
    return {
      visible: false,
      wareList: [],
      dataForm: {
        id: 0,
        skuId: "",
        wareId: "",
        stock: 0,
        skuName: "",
        stockLocked: 0
      },
      dataRule: {
        skuId: [{ required: true, message: "sku_id不能为空", trigger: "blur" }],
        wareId: [
          { required: true, message: "仓库id不能为空", trigger: "blur" }
        ],
        stock: [{ required: true, message: "库存数不能为空", trigger: "blur" }],
        skuName: [
          { required: true, message: "sku_name不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getWares();
  },
  methods: {
    getWares() {
      WareInfoListApi({ page: 1, limit: 500 }).then(data => {
        this.wareList = data.data.list;
      });
    },
    init(id) {
      this.dataForm.id = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.id) {
          WareSkuInfoApi(this.dataForm.id).then(data => {
            this.dataForm.skuId = data.data.skuId;
            this.dataForm.wareId = data.data.wareId;
            this.dataForm.stock = data.data.stock;
            this.dataForm.skuName = data.data.skuName;
            this.dataForm.stockLocked = data.data.stockLocked;
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
            skuId: this.dataForm.skuId,
            wareId: this.dataForm.wareId,
            stock: this.dataForm.stock,
            skuName: this.dataForm.skuName,
            stockLocked: this.dataForm.stockLocked
          }

          if (data.id) {
            WareSkuUpdateApi(data).then(() => {
              SuccessMessage("修改成功");
              this.visible = false
              this.$emit('refreshDataList')
            })
          } else {
            WareSkuSaveApi(data).then(() => {
              SuccessMessage("添加成功");
              this.visible = false
              this.$emit('refreshDataList')
            })
          }
        }
      });
    }
  }
};
</script>
