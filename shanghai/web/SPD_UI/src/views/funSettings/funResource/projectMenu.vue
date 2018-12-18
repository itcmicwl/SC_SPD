<template>
  <div id="project-menu" style="height:100%">
    <!-- <section> -->
    <!--工具条-->
    <el-col :span="24" style="height:100%">
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" label-width="80px">
          <el-form-item label="所属系统">
            <el-cascader v-model="filteProjectCode" :options="projectTree" :props="sysProProps" @change="fprojectChange" placeholder="请选择系统" filterable></el-cascader>
          </el-form-item>
          <el-form-item label="分类">
            <el-cascader v-model="fProMenuId" :options="fSysProMenu" :props="meunProps" style="width:88%" placeholder="请选择" clearable filterable change-on-select @change="getSysProMenuListMod()"></el-cascader>
          </el-form-item>
          <el-form-item label="名称/编码">
            <el-input v-model="filters.cname" placeholder="菜单名称或编码"></el-input>
          </el-form-item>
          <el-button @click="getSysProMenuList(1)" type="primary">查询</el-button>
          <el-button @click="sysModuleFunEdit(null)" type="primary">新增</el-button>
        </el-form>
      </el-col>
      <!--列表-->
      <template>
        <el-table :data="sysProMenuList" class="table_content" highlight-current-row v-loading="loading" style="width: 100%;" border fit height="css">
          <el-table-column prop="cname" label="菜单名称" width="200" sortable>
          </el-table-column>
          <el-table-column prop="ordinal" label="排序" width="50">
          </el-table-column>
          <el-table-column prop="pName" label="父级菜单" width="100" sortable>
          </el-table-column>
          <el-table-column prop="directory" label="路径" width="250" show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="meta" label="参数信息" width="100" sortable>
          </el-table-column>

          <el-table-column prop="path" label="访问URL" width="180"></el-table-column>
          <el-table-column prop="redirect" label="重定向" width="180">
          </el-table-column>
          <el-table-column label="操作" width="100" fixed="right">
            <template slot-scope="scope">
              <el-button @click="sysModuleFunEdit(scope.row)" type="text">编辑</el-button>
              <el-button @click="delsysModuleFun(scope.row)" type="text">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--工具条-->
        <el-col :span="24" class="toolbar">
          <el-pagination :current-page="page" :page-size="pageSize" :page-sizes="[30, 100, 200, 400]" :total="total" @current-change="handleCurrentChange" @size-change="handleSizeChange" layout="total, sizes, prev, pager, next" style="float:right;" small>
          </el-pagination>
        </el-col>
      </template>
      <!--编辑界面-->
      <el-dialog :close-on-click-modal="false" :title="modelTitle" :visible.sync="editFormVisible" close="onClose">
        <el-form ref="editForm" :inline="true" :model="sysProMenu" :rules="editFormRules" :show-message="true" label-width="85px">
          <el-form-item label="所属系统">
            <el-cascader v-model="EditProjectId" :options="projectTree" :props="sysProProps" @change="projectChange" style="width:88%" placeholder="请选择模块" filterable></el-cascader>
          </el-form-item>
          <el-form-item label="父级菜单">
            <el-cascader v-model="pProMenuId" :options="pSysProMenu" :props="meunProps" @change="menuChange" style="width:88%" placeholder="请选择" clearable filterable change-on-select></el-cascader>
          </el-form-item>
          <el-form-item :error="errors.nameErr" label="菜单名称" prop="cname">
            <el-input v-model="sysProMenu.cname" placeholder="请输入菜单名称"></el-input>
          </el-form-item>
          <el-form-item label="路径" prop="directory">
            <el-input v-model="sysProMenu.directory" :disabled="isDisable" placeholder="views为根目录"></el-input>
          </el-form-item>
          <el-form-item label="访问URL">
            <el-input v-model="sysProMenu.path" :disabled="isDisable" placeholder="当前页面URL，非全URL"></el-input>
          </el-form-item>
          <el-form-item label="图标">
            <el-input v-model="sysProMenu.iconCls" placeholder="图标"></el-input>
          </el-form-item>
          <el-form-item label="重定向">
            <el-input v-model="sysProMenu.redirect" placeholder="重定向的URL"></el-input>
          </el-form-item>
          <el-form-item label="页面参数">
            <el-input v-model="sysProMenu.meta" placeholder="页面参数,形如：xx=abc&yy=efg"></el-input>
          </el-form-item>
          <el-form-item label="显示排序">
            <el-input-number v-model="sysProMenu.ordinal" :max="100" :min="1"></el-input-number>
          </el-form-item>
          <el-form-item label="是否显示">
            <el-switch v-model="sysProMenu.hidden" active-color="#13ce66" inactive-color="#ff4949" active-text="显示" inactive-text="隐藏" active-value="0" inactive-value="1">
            </el-switch>
          </el-form-item>
          <el-form-item label="权限控制">
            <el-switch v-model="sysProMenu.requireAuth" active-color="#13ce66" inactive-color="#ff4949" active-text="是" inactive-text="否" active-value="1" inactive-value="0">
            </el-switch>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click.native="editFormVisible = false">取消</el-button>
          <el-button :loading="editLoading" @click.native="editSubmit" type="primary">提交</el-button>
        </div>
      </el-dialog>
    </el-col>
    <!-- </section> -->
  </div>
</template>
<script>
export default {
  data() {
    return {
      filters: {
        cname: "",
        code: "",
        pcode: "",
        projectCode: ""
      },
      total: 0,
      page: 1,
      pageSize: 30,
      loading: false,
      sysProMenuList: [],
      topMenuDir: "views/content.vue",
      dirAndPathNo: "无需填写",
      isDisable: false,
      sysProMenu: {
        projectCode: "",
        code: "",
        hidden: "1",
        ordinal: 0,
        pcode: "",
        requireAuth: "1",
        directory: ""
      },
      projectTree: [],
      sysProProps: {
        label: "label",
        value: "code"
      },
      meunProps: {
        label: "cname",
        value: "code"
      },
      groupItems: [],
      pSysProMenu: [],
      fSysProMenu: [],
      modelTitle: "",
      editFormVisible: false,
      editLoading: false,
      editFormRules: {
        cname: [{ required: true, message: "请输入菜单名称", trigger: "blur" }],
        directory: [{ required: true, message: "请输入路径", trigger: "blur" }]
      },
      errors: {
        nameErr: "",
        codeErr: ""
      }
    };
  },
  computed: {
    fProMenuId: {
      get: function() {
        let res = [];
        if (this.filters.pcode) {
          let path = this.filters.pcode.split(".");
          if (path.length > 1) {
            res.push(path[0]);
            res.push(this.filters.pcode);
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
        this.filters.pcode = res;
      }
    },
    pProMenuId: {
      get: function() {
        let res = [];
        if (this.sysProMenu.pcode) {
          let path = this.sysProMenu.pcode.split(".");
          if (path.length > 1) {
            res.push(path[0]);
            res.push(this.sysProMenu.pcode);
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
        this.sysProMenu.pcode = res;
      }
    },
    filteProjectCode: {
      get: function() {
        let res = [];
        if (this.filters.projectCode) {
          let path = this.filters.projectCode.split(".");
          if (path.length > 1) {
            var i = 0;
            for (i; i < path.length; i++) {
              let tempArr = path.slice(0, i + 1);
              res.push(tempArr.join("."));
            }
            res.push(this.filters.moduleId);
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
        this.filters.projectCode = res;
      }
    },
    EditProjectId: {
      get: function() {
        let res = [];
        if (this.sysProMenu.projectCode) {
          let path = this.sysProMenu.projectCode.split(".");
          if (path.length > 1) {
            var i = 0;
            for (i; i < path.length; i++) {
              let tempArr = path.slice(0, i + 1);
              res.push(tempArr.join("."));
            }
            res.push(this.sysProMenu.moduleId);
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
        this.sysProMenu.projectCode = res;
      }
    }
  },
  mounted() {
    this.init();
    //this.getGroupItems();
  },
  methods: {
    init() {
      this.getSysProMenuList(1);
      this.getProjectTree();
    },
    fprojectChange() {
      if (this.filters.projectCode) {
        this.getPSysProMenu(this.filters.projectCode, "query");
      }
    },
    projectChange() {
      if (this.sysProMenu.projectCode) {
        this.getPSysProMenu(this.sysProMenu.projectCode, "edit");
      }
    },
    menuChange(data) {
      if (data.length == 0) {
        this.sysProMenu.directory = this.topMenuDir;
        this.sysProMenu.path = "/";
        this.isDisable = false;
      } else if (data.length == 1) {
        this.isDisable = true;
        this.sysProMenu.directory = this.dirAndPathNo;
        this.sysProMenu.path = this.dirAndPathNo;
      } else {
        this.isDisable = false;
        if (
          this.sysProMenu.directory == this.topMenuDir ||
          this.sysProMenu.directory == this.dirAndPathNo
        ) {
          this.sysProMenu.directory = "";
        }
        if (
          this.sysProMenu.path == "/" ||
          this.sysProMenu.path == this.dirAndPathNo
        ) {
          this.sysProMenu.path = "";
        }
      }
    },
    getPSysProMenu(projectCode, flag) {
      this.loading = true;
      this.axios
        .get(`/platformService/sys/proMenu/sysMenuKindList/${projectCode}/ss`)
        .then(
          res => {
            this.loading = false;
            if (res.data.data) {
              if (flag === "query") {
                this.fSysProMenu = this.buildTree(res.data.data);
              } else {
                this.pSysProMenu = this.buildTree(res.data.data);
              }
              this.filters.pcode = "";
              this.getSysProMenuList(1);
            }
          },
          err => {
            this.loading = false;
          }
        );
    },
    buildTree(data) {
      if (Array.isArray(data)) {
        var topMenu = data.filter(item => item.menuLevel == 0);
        topMenu.forEach(item => {
          item.children = data.filter(citem => citem.pcode == item.code);
        });
        return topMenu;
      }
      return null;
    },
    getProjectTree: function() {
      this.loading = true;
      this.axios.get("/platformService/sys/project/sysProjectTree").then(
        res => {
          this.loading = false;
          if (res.data.data) {
            this.projectTree = res.data.data;
          }
        },
        err => {
          this.loading = false;
        }
      );
    },
    //获取列表
    getSysProMenuListMod() {
      this.getSysProMenuList(1);
    },
    getSysProMenuList: function(pIndex) {
      this.page = pIndex;
      var param = {
        orderBy: "",
        page: this.page,
        rows: this.pageSize,
        queryObject: {
          cname: this.filters.cname,
          code: this.filters.code,
          projectCode: this.filters.projectCode,
          pcode: this.filters.pcode
        }
      };
      this.loading = true;
      this.axios
        .post("/platformService/sys/proMenu/sysProMenuList", param)
        .then(
          res => {
            this.loading = false;
            this.sysProMenuList = res.data.data.data;
            this.total = res.data.data.total;
            //this.page = res.data.data.pageNum;
          },
          err => {
            this.loading = false;
          }
        );
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getSysProMenuList(this.page);
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.getSysProMenuList(this.page);
    },
    sysModuleFunEdit(item) {
      this.errors.nameErr = "";
      this.errors.codeErr = "";
      if (item) {
        let sysProMenu = Object.assign({}, item);
        this.sysProMenu = sysProMenu;
        this.modelTitle = "修改菜单";
      } else {
        this.sysProMenu = {
          projectCode: "",
          code: "",
          hidden: "0",
          ordinal: 0,
          requireAuth: "1",
          pcode: "",
          directory: this.topMenuDir,
          path: "/"
        };
        this.modelTitle = "添加菜单";
      }
      this.editFormVisible = true;
      // this.projectChange();
    },
    delsysModuleFun(projectMenu) {
      this.$confirm("确认删除这条记录吗?", "提示", {})
        .then(() => {
          this.loading = true;
          this.axios
            .post("/platformService/sys/proMenu/delete", projectMenu)
            .then(
              res => {
                this.loading = false;
                if (res.data.code == "0") {
                  this.loading = false;
                  this.$message({ message: "操作成功！", type: "success" });
                } else if (res.data.code == "-1") {
                  this.$message.error(res.data.msg);
                }
                this.getSysProMenuList(this.page);
              },
              err => {
                this.loading = false;
              }
            );
        })
        .catch(() => {});
    },
    editSubmit: function() {
      var form = this.$refs.editForm;
      form.validate(valid => {
        if (valid) {
          this.editLoading = true;
          let serviceUrl = this.sysProMenu.id
            ? "/platformService/sys/proMenu/update"
            : "/platformService/sys/proMenu/insert";
          this.axios.post(serviceUrl, this.sysProMenu).then(
            res => {
              this.editLoading = false;
              if (res.data.code == "0") {
                this.$message({ message: "操作成功！", type: "success" });
                this.editFormVisible = false;
                this.getSysProMenuList(this.page);
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
        } else {
        }
      });
    }
  }
};
</script>

<style scoped>
.table_content {
  font-size: 12px;
  /* height: -moz-calc(100% - 200px);
    height: -webkit-calc(100% - 20px); */
  height: calc(100% - 80px);
}
.el-tree-node__label {
  font-size: 12px;
}

.queryTree {
  min-height: 500px;
}
</style>
