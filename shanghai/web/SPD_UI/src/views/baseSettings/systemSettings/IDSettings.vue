<template>
  <div style="height: 100%;">
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true">
        <el-form-item label="名称">
          <el-input v-model="filters.ename" placeholder="名称"></el-input>
        </el-form-item>
        <el-form-item label="ID规则说明">
          <el-input v-model="filters.remark" placeholder="ID规则说明"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getAtomMap()" type="primary">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--列表-->
    <template>
      <el-table :data="atomMap" class="IDsetting-tableheight" highlight-current-row v-loading="loading" style="width: 100%;" height="css" fit border>
        <el-table-column prop="ename" label="名称" sortable>
        </el-table-column>
        <el-table-column prop="curVal" label="当前值" width="80">
        </el-table-column>
        <el-table-column prop="prefix" label="前缀" width="100">
        </el-table-column>
        <el-table-column prop="suffix" label="后缀" width="80">
        </el-table-column>
        <el-table-column prop="format" label="格式规则">
        </el-table-column>
        <el-table-column prop="remark" label="ID规则说明" sortable>
        </el-table-column>
        <el-table-column prop="status" label="状态" sortable>
        </el-table-column>
        <el-table-column prop="mt" label="最后修改时间" sortable>
        </el-table-column>
      </el-table>
      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination :current-page="page" :page-size="pageSize" :page-sizes="[30, 100, 200, 400]" :total="total" @current-change="handleCurrentChange" @size-change="handleSizeChange" layout="total, sizes, prev, pager, next" style="float:right;" small>
        </el-pagination>
      </el-col>
    </template>
  </div>
</template>
<script>
export default {
  data() {
    return {
      filters: {
        ename: "",
        remark: ""
      },
      total: 0,
      page: 1,
      pageSize: 20,
      loading: false,
      atomMap: []
    };
  },
  mounted() {
    this.getAtomMap(1);
  },
  methods: {
    //获取ID信息表
    getAtomMap: function(pIndex) {
      this.loading = true;
      var param = {
        orderBy: "",
        queryObject: { ename: this.filters.ename, remark: this.filters.remark },
        page: this.page,
        rows: this.pageSize
      };
      this.axios.post(`/platformService/sys/atom/getSysAtomList`, param).then(
        res => {
          this.loading = false;
          this.atomMap = res.data.data.data;
          this.total = res.data.data.total;
          // this.page = res.data.data.pageNum;
        },
        err => {
          this.loading = false;
        }
      );
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getAtomMap(this.page);
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.getAtomMap(this.page);
    }
  }
};
</script>
<style>
.IDsetting-tableheight {
  height: calc(100% - 85px);
}
</style>
