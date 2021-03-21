<template>
<div class="mod-config">
  <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
    <el-form-item label="状态">
      <el-select style="width:120px;" v-model="dataForm.status" placeholder="请选择状态" clearable>
        <el-option label="新建" :value="0"></el-option>
        <el-option label="已分配" :value="1"></el-option>
        <el-option label="已领取" :value="2"></el-option>
        <el-option label="已完成" :value="3"></el-option>
        <el-option label="有异常" :value="4"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="关键字">
      <el-input style="width:120px;" v-model="dataForm.key" placeholder="参数名" clearable></el-input>
    </el-form-item>
    <el-form-item>
      <el-button @click="getDataList()">查询</el-button>
      <el-button v-if="isAuth('ware:purchase:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      <el-button v-if="isAuth('ware:purchase:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
    </el-form-item>
  </el-form>
  <el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
    <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
    <el-table-column prop="id" header-align="center" align="center" label="采购单id"></el-table-column>
    <el-table-column prop="assigneeId" header-align="center" align="center" label="采购人id"></el-table-column>
    <el-table-column prop="assigneeName" header-align="center" align="center" label="采购人名"></el-table-column>
    <el-table-column prop="phone" header-align="center" align="center" label="联系方式"></el-table-column>
    <el-table-column prop="priority" header-align="center" align="center" label="优先级"></el-table-column>
    <el-table-column prop="status" header-align="center" align="center" label="状态">
      <template slot-scope="scope">
        <el-tag v-if="scope.row.status == 0">新建</el-tag>
        <el-tag type="info" v-if="scope.row.status == 1">已分配</el-tag>
        <el-tag type="warning" v-if="scope.row.status == 2">已领取</el-tag>
        <el-tag type="success" v-if="scope.row.status == 3">已完成</el-tag>
        <el-tag type="danger" v-if="scope.row.status == 4">有异常</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="wareId" header-align="center" align="center" label="仓库id"></el-table-column>
    <el-table-column prop="amount" header-align="center" align="center" label="总金额"></el-table-column>
    <el-table-column prop="createTime" header-align="center" align="center" label="创建日期"></el-table-column>
    <el-table-column prop="updateTime" header-align="center" align="center" label="更新日期"></el-table-column>
    <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
      <template slot-scope="scope">
        <el-button type="text" size="small" v-if="scope.row.status==0||scope.row.status==1" @click="opendrawer(scope.row)">分配</el-button>
        <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
        <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper"></el-pagination>
  <!-- 弹窗, 新增 / 修改 -->
  <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  <el-dialog title="分配采购人员" :visible.sync="caigoudialogVisible" width="30%">
    <el-select v-model="userId" filterable placeholder="请选择">
      <el-option v-for="item in userList" :key="item.userId" :label="item.username" :value="item.userId"></el-option>
    </el-select>
    <span slot="footer" class="dialog-footer">
      <el-button @click="caigoudialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="assignUser">确 定</el-button>
    </span>
  </el-dialog>
</div>
</template>

<script>
import AddOrUpdate from "./purchase-add-or-update";
import {PurchaseListApi,PurchaseUpdateApi,PurchaseDeleteApi} from "@/api/ware/purchase.js"
import {UserListApi} from "@/api/sys/user.js"

import { WarningConfirm, SuccessMessage } from "@/utils/message.js";
export default {
  data() {
    return {
      currentRow: {},
      dataForm: {
        key: "",
        status: ""
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      caigoudialogVisible: false,
      userId: "",
      userList: []
    };
  },
  components: {
    AddOrUpdate
  },
  activated() {
    this.getDataList();
  },
  created() {

  },
  methods: {
    opendrawer(row) {
      this.getUserList();
      this.currentRow = row;
      this.caigoudialogVisible = true;
    },
    assignUser() {
      let _this = this;
      let user = {};
      this.userList.forEach(item => {
        if (item.userId == _this.userId) {
          user = item;
        }
      });
      this.caigoudialogVisible = false;
      PurchaseUpdateApi({
        id: this.currentRow.id || undefined,
        assigneeId: user.userId,
        assigneeName: user.username,
        phone: user.mobile,
        status: 1
      }).then((data) => {
        SuccessMessage("操作成功");
        this.userId = "";
        this.getDataList();
      });
    },
    getUserList() {
      UserListApi({
        page: 1,
        limit: 500
      }).then(data => this.userList = data.page.list);
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      PurchaseListApi({
        page: this.pageIndex,
        limit: this.pageSize,
        key: this.dataForm.key
      }).then((data) => {
        this.dataList = data.data.list || [];
        this.totalPage = data.data.totalCount || 0;
        this.dataListLoading = false;
      });
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    },
    // 删除
    deleteHandle(id) {
      var ids = id ? [id] :
        this.dataListSelections.map(item => {
          return item.id;
        });
      WarningConfirm(() => {
        PurchaseDeleteApi(ids).then(data => {
          SuccessMessage("删除成功", () => {
            this.getDataList();
          });
        });
      }, `确定对[id=${ids.join(",")}]进行[${id ? "删除" : "批量删除"}]操作?`);
    }
  }
};
</script>
