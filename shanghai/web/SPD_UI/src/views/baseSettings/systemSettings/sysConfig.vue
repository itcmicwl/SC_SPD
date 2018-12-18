<template>
  <div style="height: 100%;">
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true">
        <el-form-item label="配置项名称">
          <el-input v-model="filters.ename" placeholder="配置项名称"></el-input>
        </el-form-item>
        <el-form-item label="配置项值">
          <el-input v-model="filters.val" placeholder="配置项值"></el-input>
        </el-form-item>
        <el-form-item label="配置项说明">
          <el-input v-model="filters.remark" placeholder="配置项说明"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getSysConfigList(1)" type="primary">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button @click="sysConfigEdit(null)" type="primary">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--列表-->
    <template>
      <el-table :data="sysConfigs" class="sysConfig-tableheight" highlight-current-row v-loading="loading" style="width: 100%;" border fit height="css">
        <el-table-column type="index">
        </el-table-column>
        <el-table-column prop="ename" label="配置项名称">
        </el-table-column>
        <el-table-column prop="val" label="值" min-width="150">
        </el-table-column>
        <el-table-column prop="format" label="格式化">
        </el-table-column>
        <el-table-column prop="remark" label="备注">
        </el-table-column>
        <el-table-column label="状态" width="150">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.status" @change="setStatus(scope.row)" active-color="#13ce66" inactive-color="#ff4949" active-text="启用" inactive-text="停用" active-value="1" inactive-value="0">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button @click="sysConfigEdit(scope.row)" type="text">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination :current-page="page" :page-size="30" :page-sizes="[30, 100, 200, 400]" :total="total" @current-change="handleCurrentChange" layout="total, sizes, prev, pager, next" style="float:right;" small>
        </el-pagination>
      </el-col>
    </template>
    <!--编辑界面-->
    <el-dialog :close-on-click-modal="false" :title="modelTitle" :visible.sync="editFormVisible" close="onClose">
      <el-form ref="editForm" :inline="true" :model="sysConfig" :rules="editFormRules" label-width="80px">
        <el-form-item :error="nameErr" label="名称" prop="ename">
          <el-input v-model="sysConfig.ename" placeholder="请输入配置项名称"></el-input>
        </el-form-item>
        <el-form-item label="值">
          <el-input v-model="sysConfig.val" placeholder="请输入配置项值"></el-input>
        </el-form-item>
        <el-form-item label="格式化">
          <el-input v-model="sysConfig.format" placeholder="请输入格式化配置"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="sysConfig.remark" placeholder="请输入备注信息"></el-input>
        </el-form-item>
        <el-form-item label="启用/停用">
          <el-switch v-model="sysConfig.status" active-color="#13ce66" inactive-color="#ff4949" active-text="启用" inactive-text="停用" active-value="1" inactive-value="0">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button :loading="editLoading" @click.native="editSubmit" type="primary">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      filters: {
        ename: "",
        val: "",
        remark: ""
      },
      total: 0,
      page: 1,
      loading: false,
      sysConfigs: [],
      sysConfig: {
        status: "1"
      },
      modelTitle: "",
      editFormVisible: false,
      editLoading: false,
      editFormRules: {
        ename: [{ required: true, message: "请输入名称", trigger: "blur" }]
      },
      nameErr: ""
    };
  },
  mounted() {
    this.getSysConfigList(1);
  },
  methods: {
    //获取用户列表
    getSysConfigList: function(pIndex) {
      this.page = pIndex;
      var param = {
        orderBy: "",
        queryObject: {
          ename: this.filters.ename,
          val: this.filters.val,
          remark: this.filters.remark
        },
        page: this.page,
        rows: 20
      };
      this.loading = true;
      this.axios.post(`/platformService/sys/config/sysConfigList`, param).then(
        res => {
          this.loading = false;
          this.sysConfigs = res.data.data.data;
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
      this.getSysConfigList(this.page);
    },
    sysConfigEdit(item) {
      this.nameErr = "";
      if (item) {
        this.sysConfig = Object.assign({}, item);
        this.modelTitle = "修改配置";
      } else {
        this.sysConfig = { status: "1" };
        this.modelTitle = "添加配置";
      }
      this.editFormVisible = true;
    },
    setStatus(sysConfig) {
      this.loading = true;
      this.axios.post("/platformService/sys/config/setStatus", sysConfig).then(
        res => {
          this.loading = false;
          if (res.data.code == "0") {
            this.loading = false;
            this.$message({ message: "操作成功！", type: "success" });
          } else {
            this.$message.error("操作失败！");
            sysConfig.status = sysConfig.status == "1" ? "0" : "1";
          }
        },
        err => {
          this.loading = false;
        }
      );
    },
    editSubmit: function() {
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.editLoading = true;
          let serviceUrl = this.sysConfig.id
            ? "/platformService/sys/config/update"
            : "/platformService/sys/config/insert";
          this.axios.post(serviceUrl, this.sysConfig).then(
            res => {
              this.editLoading = false;
              if (res.data.code == "0") {
                this.$message({ message: "操作成功！", type: "success" });
                this.editFormVisible = false;
                this.getSysConfigList();
              } else if (res.data.code == "-2") {
                this.nameErr = res.data.msg;
              } else {
                this.$message.error("操作失败！");
              }
            },
            err => {
              this.editLoading = false;
            }
          );
        }
      });
    }
  }
};
</script>

<style scoped="">
.el-table {
  font-size: 12px;
}
.sysConfig-tableheight {
  height: calc(100% - 85px);
}
</style>
