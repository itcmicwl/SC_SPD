<template>
  <div style="height:100%">
    <!--工具条-->
    <el-col :span="24" style="height:100%">
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" label-width="80px">
          <el-form-item label="功能名称">
            <el-input v-model="filters.ename" placeholder="功能名称" ></el-input>
          </el-form-item>
          <el-form-item label="父级功能">
            <el-select v-model="filters.pid" filterable placeholder="请选择" >
              <el-option label="--全部--" value="">
              </el-option>
              <el-option v-for="item in pSysPro" :key="item.id" :label="item.cname" :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-button type="primary" @click="getSysProjectList(1)" >查询</el-button>
          <el-button type="primary" @click="sysProjectEdit(null)" >新增</el-button>
        </el-form>
      </el-col>
      <!--列表-->
      <template>
        <el-table :data="sysProjects" highlight-current-row v-loading="loading" style="width: 100%;" border fit class="table_content">
          <el-table-column type="index">
          </el-table-column>
          <el-table-column prop="ename" label="功能名称" sortable>
          </el-table-column>
          <el-table-column prop="cname" label="功能别名" sortable>
          </el-table-column>
          <el-table-column prop="pName" label="父级功能" sortable>
          </el-table-column>
          <el-table-column prop="remark" label="备注">
          </el-table-column>
          <el-table-column prop="mt" label="最后修改时间" sortable>
          </el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-button @click="sysProjectEdit(scope.row)" type="text">编辑</el-button>
              <el-button @click="delSysProject(scope.row)" type="text">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--工具条-->
        <el-col :span="24" class="toolbar">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[30, 100, 200, 400]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
          </el-pagination>
        </el-col>
      </template>
      <!--编辑界面-->
      <el-dialog :title="modelTitle" close="onClose" :visible.sync="editFormVisible" :close-on-click-modal="false">
        <el-form :model="sysProject" :inline="true" label-width="80px" :rules="editFormRules" ref="editForm">
          <el-row>
            <el-col :span="12">
              <el-form-item label="名称" prop="ename" :error="nameErr">
                <el-input v-model="sysProject.ename" placeholder="请输入功能名称" ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="功能别名">
                <el-input v-model="sysProject.cname" placeholder="请输入功能别名"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="父级功能">
                <el-select style="width:88%" v-model="sysProject.pid" filterable placeholder="请选择父级功能" >
                  <el-option label="--无--" value=""></el-option>
                  <el-option v-for="item in pSysPro" :key="item.id" :label="item.cname" :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="备注">
                <el-input placeholder="请输入备注信息" v-model="sysProject.remark" ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click.native="editFormVisible = false">取消</el-button>
          <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
        </div>
      </el-dialog>
    </el-col>
  </div>
</template>
<script>
export default {
  data() {
    return {
      filters: {
        ename: "",
        pid: ""
      },
      total: 0,
      page: 1,
      pageSize: 30,
      loading: false,
      sysProjects: [],
      sysProject: {
        pid: ""
      },
      pSysPro: [],
      modelTitle: "",
      editFormVisible: false,
      editLoading: false,
      editFormRules: {
        ename: [{ required: true, message: "请输入功能名称", trigger: "blur" }]
      },
      nameErr: ""
    };
  },
  mounted() {
    this.getSysProjectList(1);
    this.getParentSysProList();
  },
  methods: {
    getParentSysProList: function() {
      this.loading = true;
      this.axios.get("/platformService/sys/project/pSysProList").then(
        res => {
          this.loading = false;
          this.pSysPro = res.data.data;
        },
        err => {
          this.loading = false;
        }
      );
    },
    //获取列表
    getSysProjectList: function(pIndex) {
      this.page = pIndex;
      var param = {
        orderBy: "",
        queryObject: { ename: this.filters.ename, pid: this.filters.pid },
        page: this.page,
        rows: this.pageSize
      };
      this.loading = true;
      this.axios
        .post("/platformService/sys/project/sysProjectList", param)
        .then(
          res => {
            this.loading = false;
            this.sysProjects = res.data.data.data;
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
      this.getSysProjectList(this.page);
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.getSysProjectList(this.page);
    },
    sysProjectEdit(item) {
      this.nameErr = "";
      if (item) {
        let sysPro = Object.assign({}, item);
        delete sysPro.pName;
        this.sysProject = sysPro;
        this.modelTitle = "修改配置";
      } else {
        this.sysProject = { pid: "" };
        this.modelTitle = "添加配置";
      }
      this.editFormVisible = true;
    },
    delSysProject(sysProject) {
      this.$confirm("确认删除这条记录吗?", "提示", {})
        .then(() => {
          this.loading = true;
          this.axios
            .post(
              "/platformService/sys/project/delSysProjectByCode",
              sysProject
            )
            .then(
              res => {
                this.loading = false;
                if (res.data.code == "0") {
                  this.loading = false;
                  this.$message({ message: "操作成功！", type: "success" });
                } else {
                  this.$message.error("操作失败！");
                  sysProject.status = sysProject.status == "1" ? "0" : "1";
                }
                this.getParentSysProList();
                this.getSysProjectList(this.page);
              },
              err => {
                this.loading = false;
              }
            );
        })
        .catch(() => {});
    },
    editSubmit: function() {
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.editLoading = true;
          let serviceUrl = this.sysProject.id
            ? "/platformService/sys/project/update"
            : "/platformService/sys/project/insert";
          this.axios.post(serviceUrl, this.sysProject).then(
            res => {
              this.editLoading = false;
              if (res.data.code == "0") {
                this.$message({ message: "操作成功！", type: "success" });
                this.editFormVisible = false;
                this.getParentSysProList();
                this.getSysProjectList(this.page);
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

<style>
.table_content {
  font-size: 12px;
  /* height: -moz-calc(100% - 200px);
    height: -webkit-calc(100% - 20px); */
  height: calc(100% - 80px);
}
</style>
