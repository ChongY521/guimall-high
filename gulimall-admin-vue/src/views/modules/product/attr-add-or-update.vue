<template>
<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible" @closed="dialogClose" width="66%">
  <el-form :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
    <!--       @keyup.enter.native="dataFormSubmit()" -->
    <el-row>
      <el-col :span="12">
        <el-form-item label="属性名" prop="attrName">
          <el-input v-model="dataForm.attrName" placeholder="属性名"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="属性类型" prop="attrType">
          <el-select v-model="dataForm.attrType" placeholder="请选择">
            <el-option label="规格参数" :value="1"></el-option>
            <el-option label="销售属性" :value="0"></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>

    <el-form-item label="值类型" prop="valueType">
      <el-switch v-model="dataForm.valueType" active-text="允许多个值" inactive-text="只能单个值" active-color="#13ce66" inactive-color="#ff4949" :inactive-value="0" :active-value="1"></el-switch>
    </el-form-item>
    <el-form-item label="可选值" prop="valueSelect">
      <!-- <el-input v-model="dataForm.valueSelect"></el-input> -->
      <el-select v-model="dataForm.valueSelect" multiple filterable allow-create placeholder="请输入内容"></el-select>
    </el-form-item>
    <el-row>
      <el-col :span="6">
        <el-form-item label="属性图标" prop="icon">
          <el-input v-model="dataForm.icon" placeholder="属性图标"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="9">
        <el-form-item label="所属分类" prop="categoryId">
          <category-cascader :catelogPath.sync="categoryPath"></category-cascader>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="所属分组" prop="attrGroupId" v-if="type == 1">
          <el-select ref="groupSelect" v-model="dataForm.attrGroupId" placeholder="请选择">
            <el-option v-for="item in attrGroups" :key="item.attrGroupId" :label="item.attrGroupName" :value="item.attrGroupId"></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="5">
        <el-form-item label="可检索" prop="searchType" v-if="type == 1">
          <el-switch v-model="dataForm.searchType" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="快速展示" prop="showDesc" v-if="type == 1">
          <el-switch v-model="dataForm.showDesc" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="启用状态" prop="enable">
          <el-switch v-model="dataForm.enable" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
      </el-col>
    </el-row>

  </el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="visible = false">取消</el-button>
    <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
  </span>
</el-dialog>
</template>

<script>
import CategoryCascader from "../common/category-cascader";

import { AttrInfoApi, AttrSaveApi, AttrUpdateApi } from "@/api/product/attr.js"
import { WarningConfirm, SuccessMessage, ErrorMessage } from "@/utils/message.js";
import { AttrGroupListApi } from "@/api/product/attrgroup.js";

export default {
  data() {
    return {
      visible: false,
      dataForm: {
        attrId: 0,
        attrName: "",
        searchType: 0,
        valueType: 1,
        icon: "",
        valueSelect: "",
        attrType: 1,
        enable: 1,
        categoryId: "",
        attrGroupId: "",
        showDesc: 0
      },
      categoryPath: [],
      attrGroups: [],
      dataRule: {
        attrName: [{ required: true, message: "属性名不能为空", trigger: "blur" }],
        searchType: [{ required: true, message: "是否需要检索不能为空", trigger: "blur" }],
        valueType: [{ required: true, message: "值类型不能为空", trigger: "blur" }],
        icon: [{ required: true, message: "属性图标不能为空", trigger: "blur" }],
        attrType: [{ required: true, message: "属性类型不能为空", trigger: "blur" }],
        enable: [{ required: true, message: "启用状态不能为空", trigger: "blur" }],
        categoryId: [{ required: true, message: "需要选择正确的三级分类数据", trigger: "blur" }],
        showDesc: [{ required: true, message: "快速展示不能为空", trigger: "blur" }]
      }
    };
  },
  props: {
    type: {
      type: Number,
      default: 1
    }
  },
  watch: {
    categoryPath(path) {
      //监听到路径变化需要查出这个三级分类的分组信息
      console.log("路径变了", path);
      this.attrGroups = [];
      this.dataForm.attrGroupId = "";
      this.dataForm.categoryId = path[path.length - 1];
      if (path && path.length == 3) {
        AttrGroupListApi(path[path.length - 1], { page: 1, limit: 100000 }).then((data) => {
          this.attrGroups = data.data.list || [];
        });
      } else if (path.length == 0) {
        this.dataForm.categoryId = "";
      } else {
        ErrorMessage("请选择正确的分类");
        this.dataForm.categoryId = "";
      }

    }
  },
  components: { CategoryCascader },
  methods: {
    init(id) {
      this.dataForm.attrId = id || 0;
      this.dataForm.attrType = this.type;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.attrId) {
          AttrInfoApi(this.dataForm.attrId).then((data) => {
            if (data && data.code === 0) {
              data = data.data;
              this.dataForm.attrName = data.attrName;
              this.dataForm.searchType = data.searchType;
              this.dataForm.valueType = data.valueType;
              this.dataForm.icon = data.icon;
              this.dataForm.valueSelect = data.valueSelect == '' ? "" : data.valueSelect.split(";");
              this.dataForm.attrType = data.attrType;
              this.dataForm.enable = data.enable;
              this.dataForm.categoryId = data.categoryId;
              this.dataForm.showDesc = data.showDesc;
              //attrGroupId
              //categoryPath  分类的完整路径
              this.categoryPath = data.categoryPath;
              this.$nextTick(() => {
                this.dataForm.attrGroupId = data.attrGroupId;
              });
            }
          });
        }
      });
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          let data = {
            attrId: this.dataForm.attrId || undefined,
            attrName: this.dataForm.attrName,
            searchType: this.dataForm.searchType,
            valueType: this.dataForm.valueType,
            icon: this.dataForm.icon,
            valueSelect: this.dataForm.valueSelect ? this.dataForm.valueSelect.join(";") : "",
            attrType: this.dataForm.attrType,
            enable: this.dataForm.enable,
            categoryId: this.dataForm.categoryId,
            attrGroupId: this.dataForm.attrGroupId,
            showDesc: this.dataForm.showDesc
          }

          if (this.dataForm.attrId) {
            AttrUpdateApi(data).then(() => {
              SuccessMessage("属性修改成功");
              this.visible = false;
              this.$emit("refreshDataList");
            })
          } else {
            AttrSaveApi(data).then(() => {
              SuccessMessage("属性添加成功");
              this.visible = false;
              this.$emit("refreshDataList");
            })
          }
        }
      })
    },
    //dialogClose
    dialogClose() {
      this.categoryPath = [];
    }
  }
};
</script>
