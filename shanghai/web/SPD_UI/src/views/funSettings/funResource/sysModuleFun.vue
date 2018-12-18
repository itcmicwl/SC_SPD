<template>
  <div style="height:100%">
    <el-col :span="4" style="height:100%">
      <el-tree :data="moduleTree" @node-click="handleNodeClick" highlight-current class="queryTree">
      </el-tree>
    </el-col>
    <!--工具条-->
    <el-col :span="20" style="height:100%">
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" label-width="80px">
          <el-form-item label="功能名称">
            <el-input v-model="filters.ename" placeholder="功能名称" ></el-input>
          </el-form-item>
          <el-form-item label="编码">
            <el-input v-model="filters.code" placeholder="功能编码" ></el-input>
          </el-form-item>
          <el-button type="primary" @click="getsysModuleFunList(1)" >查询</el-button>
          <el-button type="primary" @click="sysModuleFunEdit(null)" >新增</el-button>
        </el-form>
      </el-col>
      <!--列表-->
      <template>
        <el-table :data="sysModuleFunList" highlight-current-row v-loading="loading" style="width: 100%;" border fit class="table_content">
          <el-table-column type="index">
          </el-table-column>
          <el-table-column prop="ename" label="功能名称" sortable>
          </el-table-column>
          <el-table-column prop="code" label="功能编码" sortable>
          </el-table-column>
          <el-table-column prop="cname" label="功能说明" sortable>
          </el-table-column>
          <el-table-column prop="mName" label="所属模块" sortable>
          </el-table-column>
          <el-table-column prop="remark" label="备注">
          </el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-button @click="sysModuleFunEdit(scope.row)" type="text">编辑</el-button>
              <el-button @click="delsysModuleFun(scope.row)" type="text">删除</el-button>
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
        <el-form :model="sysModuleFun" :inline="true" label-width="80px" :rules="editFormRules" ref="editForm">
          <el-form-item label="功能名称" prop="ename" :error="errors.nameErr">
            <el-input v-model="sysModuleFun.ename" placeholder="请输入功能名称" ></el-input>
          </el-form-item>
          <el-form-item label="功能编码" prop="code" :error="errors.codeErr">
            <el-input v-model="sysModuleFun.code" placeholder="请输入功能编码" ></el-input>
          </el-form-item>
          <el-form-item label="功能说明">
            <el-input v-model="sysModuleFun.cname" placeholder="请输入功能功能说明"></el-input>
          </el-form-item>
          <el-form-item label="所属模块">
            <!--<el-input v-model="sysModuleFun.moduleId"  placeholder="请选择模块"></el-input>-->
            <el-cascader style="width:88%" v-model="EditModuleId" placeholder="请选择模块" :show-all-levels="false" :options="moduleTree" filterable :props="cModuleProps"></el-cascader>
          </el-form-item>
          <el-form-item label="备注">
            <el-input placeholder="请输入备注信息" v-model="sysModuleFun.remark" ></el-input>
          </el-form-item>
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
        code: "",
        spCode: "",
        moduleId: ""
      },
      total: 0,
      page: 1,
      pageSize: 30,
      loading: false,
      sysModuleFunList: [],
      sysModuleFun: {
        moduleId: ""
      },
      moduleTree: [],
      sysProProps: {
        label: "label",
        value: "code"
      },
      cModuleProps: {
        label: "label",
        value: "code"
      },
      modelTitle: "",
      editFormVisible: false,
      editLoading: false,
      editFormRules: {
        ename: [
          {
            required: true,
            message: "请输入功能名称",
            trigger: "blur"
          }
        ],
        code: [
          {
            required: true,
            message: "请输入功能编码",
            trigger: "blur"
          }
        ],
        moduleId: [
          {
            required: true,
            message: "请选择模块",
            trigger: "blur"
          }
        ]
      },
      errors: {
        nameErr: "",
        codeErr: ""
      }
    };
  },
  computed: {
    EditModuleId: {
      get: function() {
        let res = [];
        if (this.sysModuleFun.moduleId && this.sysModuleFun.spCode) {
          let path = this.sysModuleFun.spCode.split(".");
          if (path.length > 1) {
            var i = 0;
            for (i; i < path.length; i++) {
              let tempArr = path.slice(0, i + 1);
              res.push(tempArr.join("."));
            }
            res.push(this.sysModuleFun.moduleId);
          } else {
            res = path;
          }
        }
        return res;
      },
      set: function(pathArr) {
        let res = "";
        if (pathArr.length > 0) {
          res = pathArr[pathArr.length - 1];
        }
        this.sysModuleFun.spCode = pathArr[1];
        this.sysModuleFun.moduleId = res;
      }
    }
  },
  mounted() {
    this.getsysModuleFunList(1);
    this.getModuleTree();
  },
  methods: {
    getModuleTree: function() {
      this.loading = true;
      this.axios.get("/platformService/sys/module/moduleTree").then(
        res => {
          this.loading = false;
          this.moduleTree = res.data.data;
        },
        err => {
          this.loading = false;
        }
      );
    },
    //获取列表
    getsysModuleFunList: function(pIndex) {
      this.page = pIndex;
      var param = {
        orderBy: "",
        page: this.page,
        rows: this.pageSize,
        queryObject: {
          ename: this.filters.ename,
          code: this.filters.code,
          spCode: this.filters.spCode,
          moduleId: this.filters.moduleId
        }
      };
      this.loading = true;
      this.axios.post("/platformService/sys/modelFun/modelFunList", param).then(
        res => {
          this.loading = false;
          this.sysModuleFunList = res.data.data.data;
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
      this.getsysModuleFunList(this.page);
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.getsysModuleFunList(this.page);
    },
    handleNodeClick(data) {
      if (data.nodeType == "sysProject") {
        this.filters.spCode = data.code;
        this.filters.moduleId = "";
      } else {
        this.filters.spCode = "";
        this.filters.moduleId = data.code;
      }
      this.getsysModuleFunList(1);
    },
    sysModuleFunEdit(item) {
      this.errors.nameErr = "";
      this.errors.codeErr = "";
      if (item) {
        let moduleFun = Object.assign({}, item);
        delete moduleFun.mName;
        this.sysModuleFun = moduleFun;
        this.modelTitle = "修改功能";
      } else {
        this.sysModuleFun = {
          moduleId: ""
        };
        this.modelTitle = "添加功能";
      }
      this.editFormVisible = true;
    },
    delsysModuleFun(moduleFun) {
      this.$confirm("确认删除这条记录吗?", "提示", {})
        .then(() => {
          this.loading = true;
          this.axios
            .post(
              "/platformService/sys/modelFun/delSysModuleFunById",
              moduleFun
            )
            .then(
              res => {
                this.loading = false;
                if (res.data.code == "0") {
                  this.loading = false;
                  this.$message({
                    message: "操作成功！",
                    type: "success"
                  });
                } else {
                  this.$message.error("操作失败！");
                }
                this.getsysModuleFunList(this.page);
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
          let serviceUrl = this.sysModuleFun.id
            ? "/platformService/sys/modelFun/update"
            : "/platformService/sys/modelFun/insert";
          this.axios.post(serviceUrl, this.sysModuleFun).then(
            res => {
              this.editLoading = false;
              if (res.data.code == "0") {
                this.$message({
                  message: "操作成功！",
                  type: "success"
                });
                this.editFormVisible = false;
                this.getsysModuleFunList(this.page);
              } else if (res.data.code == "-122") {
                // = res.data.msg;
                let err = res.data.validateErrors.fieldErrors[0];
                if (err.field == "code") {
                  this.errors.codeErr += err.message;
                }
                if (err.field == "name") {
                  this.errors.nameErr += err.message;
                }
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

<style scoped>
/* .el-table {
  font-size: 12px;
} */
.table_content {
  font-size: 12px;
  /* height: -moz-calc(100% - 200px);
    height: -webkit-calc(100% - 20px); */
  height: calc(100% - 80px);
}
.el-tree-node__label {
  font-size: 12px;
}
.el-table__body-wrapper {
  overflow-x: hidden;
}
.queryTree {
  height: calc(100%-10px);
}
</style>
