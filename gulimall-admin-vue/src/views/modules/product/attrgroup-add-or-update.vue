<template>
<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible" @closed="dialogClose">
  <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
    <el-form-item label="组名" prop="attrGroupName">
      <el-input v-model="dataForm.attrGroupName" placeholder="组名"></el-input>
    </el-form-item>
    <el-form-item label="排序" prop="sort">
      <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
    </el-form-item>
    <el-form-item label="描述" prop="descript">
      <el-input v-model="dataForm.descript" placeholder="描述"></el-input>
    </el-form-item>
    <el-form-item label="组图标" prop="icon">
      <el-input v-model="dataForm.icon" placeholder="组图标"></el-input>
    </el-form-item>
    <el-form-item label="所属分类" prop="categoryPath">
      <!-- <el-input v-model="dataForm.catelogId" placeholder="所属分类id"></el-input> @change="handleChange" -->
      <!-- <el-cascader filterable placeholder="试试搜索：手机" v-model="categoryPath" :options="categorys"  :props="props"></el-cascader> -->
      <!-- :catelogPath="categoryPath"自定义绑定的属性，可以给子组件传值 -->
      <category-cascader :catelogPath.sync="categoryPath"></category-cascader>
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
  AttrGroupListApi,
  AttrGroupDeleteApi
} from "@/api/product/attrgroup.js"
import { WarningConfirm, SuccessMessage } from "@/utils/message.js";
import { CategoryListTreeApi } from '@/api/product/category.js';
import { 
AttrGroupInfoApi , AttrGroupSaveApi,AttrGroupUpdateApi } from "@/api/product/attrgroup.js"

import CategoryCascader from "../common/category-cascader";
export default {
  data() {
    return {
      props: {
        value: "catId",
        label: "name",
        children: "children"
      },
      visible: false,
      categorys: [],
      categoryPath: [],
      dataForm: {
        attrGroupId: 0,
        attrGroupName: "",
        sort: "",
        descript: "",
        icon: "",
        categoryId: 0
      },
      dataRule: {
        attrGroupName: [
          { required: true, message: "组名不能为空", trigger: "blur" }
        ],
        sort: [{ required: true, message: "排序不能为空", trigger: "blur" }],
        descript: [
          { required: true, message: "描述不能为空", trigger: "blur" }
        ],
        icon: [{ required: true, message: "组图标不能为空", trigger: "blur" }],
        catelogId: [
          { required: true, message: "所属分类id不能为空", trigger: "blur" }
        ]
      }
    };
  },
  components: { CategoryCascader },
  methods: {
    dialogClose() {
      this.categoryPath = [];
    },
    getCategorys() {
      CategoryListTreeApi().then((data) => {
        this.categorys = data.data;
      });
    },
    init(id) {
      this.dataForm.attrGroupId = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.attrGroupId) {
          AttrGroupInfoApi(this.dataForm.attrGroupId).then((data) => {
            data = data.data;
            this.dataForm.attrGroupName = data.attrGroupName;
            this.dataForm.sort = data.sort;
            this.dataForm.descript = data.descript;
            this.dataForm.icon = data.icon;
            this.dataForm.categoryId = data.categoryId;
            //查出catelogId的完整路径
            this.categoryPath = data.categoryPath;
          });
        }
      });
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          let data = {
            attrGroupId: this.dataForm.attrGroupId || undefined,
            attrGroupName: this.dataForm.attrGroupName,
            sort: this.dataForm.sort,
            descript: this.dataForm.descript,
            icon: this.dataForm.icon,
            categoryId: this.categoryPath[this.categoryPath.length - 1]
          }

          let flag = false;
          let text = "";
          if (this.dataForm.attrGroupId) {
            AttrGroupUpdateApi(data).then(data => {
              flag = true;
              text = "修改成功"
            })
          } else {
            AttrGroupSaveApi(data).then(data => {
              flag = true;
              text = "添加成功";
            })
          }
          if (flag) {
            SuccessMessage(text);
            this.visible = false;
            this.$emit("refreshDataList");
          }
        }
      })
    }
  },
  created() {
    this.getCategorys();
  }
};
</script>
