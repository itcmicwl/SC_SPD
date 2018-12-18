<template>
  <div style="height:100%">
    <!--工具条-->
    <div class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" label-width="60px">
        <el-form-item label="所属系统">
          <el-cascader v-model="projectCodes" :options="sysProjectOptions" :props="sysProjectSltProps" :show-all-levels="false" @change="getGroupsByPorject" placeholder="请选择所属系统">
            <!-- 需要绑定到 moduleVo.projectCode-->
          </el-cascader>
        </el-form-item>
        <el-form-item label="所属分组">
          <el-select v-model="filters.groupId" placeholder="请选择" @change="getModuleVoList(1)">
            <el-option v-for="item in groupOpts" :key="item.id" :label="item.ename" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="模块名称">
          <el-input v-model="filters.ename" placeholder="功能名称"></el-input>
        </el-form-item>
        <el-form-item label="模块别名">
          <el-input v-model="filters.cname" placeholder="功能名称"></el-input>
        </el-form-item>
        <el-button @click="getModuleVoList(1)" type="primary">查询</el-button>
        <el-button @click="moduleVoEdit(null)" type="primary">新增</el-button>
      </el-form>
    </div>
    <template>
      <el-table :data="moduleVos" class="table_content" highlight-current-row v-loading="loading" style="width: 100%;" border fit height="css">
        <el-table-column type="index">
        </el-table-column>
        <el-table-column prop="ename" label="模块名称" sortable>
        </el-table-column>
        <el-table-column prop="cname" label="模块别名" sortable>
        </el-table-column>
        <el-table-column prop="projectName" label="所属系统" sortable>
        </el-table-column>
        <el-table-column prop="groupName" label="所属分组" sortable>
        </el-table-column>
        <el-table-column prop="remark" label="备注">
        </el-table-column>
        <el-table-column prop="mt" label="最后修改时间" sortable>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template slot-scope="scope">
            <el-button @click="moduleVoEdit(scope.row)" type="text">编辑</el-button>
            <el-button @click="delModule(scope.row)" type="text">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination :current-page="page" :page-size="pageSize" :page-sizes="[20, 30, 50, 100]" :total="total" @current-change="handleCurrentChange" @size-change="handleSizeChange" layout="total, sizes, prev, pager, next" style="float:right;" small>
        </el-pagination>
      </el-col>
    </template>
    <!--编辑界面-->
    <el-dialog :close-on-click-modal="false" :title="modelTitle" :visible.sync="editFormVisible" close="onClose">
      <el-form ref="editForm" :inline="true" :model="moduleVo" :rules="editFormRules" label-width="80px">
        <el-form-item :error="nameErr" label="模块名称" prop="ename">
          <el-input v-model="moduleVo.ename" placeholder="请输入功能名称"></el-input>
        </el-form-item>
        <el-form-item label="模块别名">
          <el-input v-model="moduleVo.cname" placeholder="请输入模块别名"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="moduleVo.remark" placeholder="请输入备注信息"></el-input>
        </el-form-item>
        <el-form-item label="排序号" prop="ordinal">
          <el-input-number v-model="moduleVo.ordinal" :max="100" :min="1" @change="handleChange"></el-input-number>
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
      loading: false,
      filters: {
        ename: "",
        cname: "",
        projectCode: "",
        groupId: ""
      },
      total: 0,
      page: 1,
      pageSize: 20,
      moduleVos: [],
      moduleVo: {
        projectCode: "",
        groupId: ""
      },
      modelTitle: "",
      editFormVisible: false,
      editLoading: false,
      nameErr: "",
      editFormRules: {
        ename: [{ required: true, message: "请输入功能名称", trigger: "blur" }]
      },
      sysProjectOptions: [],
      sysProjectSltProps: {
        value: "code",
        label: "label"
      },
      groupOpts: [],
      groupId: "",
      projectCode: ""
    };
  },
  computed: {
    projectCodes: {
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
            res.push(this.filters.groupId);
          } else {
            res = path;
          }
        }
        return res;
      },
      set: function(projectCodeArr) {
        let res = "";
        if (projectCodeArr.length > 0) {
          res = projectCodeArr[projectCodeArr.length - 1];
        }
        this.filters.projectCode = res;
      }
    }
  },
  mounted() {
    this.getModuleVoList(1);
    this.getSysProjectOptions();
  },
  methods: {
    getModuleVoList: function(pIndex) {
      this.page = pIndex;
      this.loading = true;
      //   var param = { orderBy: "", queryObject: { ename: this.filters.ename,cname:this.filters.cname,dictId:'PTXTGNFZ'}, page: this.page, rows: this.pageSize };
      var param = {
        orderBy: "",
        queryObject: {
          ename: this.filters.ename,
          cname: this.filters.cname,
          groupId: this.filters.groupId,
          projectCode: this.filters.projectCode
        },
        page: this.page,
        rows: this.pageSize
      };
      this.axios.post(`/platformService/sys/module/getModuleVos`, param).then(
        res => {
          //  this.axios.post(`/platformService/sys/dict/getDicts`, null).then(res => {
          if (res.data.code == "0") {
            this.moduleVos = res.data.data.data;
            this.total = res.data.data.total;
            // this.page = res.data.data.pageNum;
            this.loading = false;
          } else {
            this.$message.error("查询失败！");
          }
        },
        err => {
          this.editLoading = false;
        }
      );
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getModuleVoList(this.page);
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.getModuleVoList(this.page);
    },
    getGroupsByPorject: function() {
      // alert(this.filters.projectCode);
      this.filters.groupId = "";
      this.getModuleVoList(1);
      this.getGroupOpts();
    },
    moduleVoEdit(item) {
      this.nameErr = "";
      if (item) {
        //   let sysPro = Object.assign({}, item);
        //   delete sysPro.pName;
        //  this.sysProject = sysPro;
        let theModuleVo = Object.assign({}, item);
        this.moduleVo = theModuleVo;
        this.groupId = this.moduleVo.groupId;
        this.modelTitle = "修改配置";
      } else {
        if (this.filters.projectCode == "" || this.filters.groupId == "") {
          alert("请现在所属系统和所属分组！");
          return;
        }
        this.groupId = this.filters.groupId;
        this.projectCode = this.filters.projectCode;
        this.modelTitle = "添加配置";
      }
      this.editFormVisible = true;
    },
    getSysProjectOptions: function() {
      //  this.loading = true;
      // this.axios.post(`/platformService/sys/module/getSysProjectOptions`, {}).then(res => {
      //   if (res.data.code == "0") {
      //     this.sysProjectOptions = res.data.data.children;
      //   } else {
      //     this.$message.error('查询失败！');
      //   }
      // }, err => {
      //   this.editLoading = false;
      // });
      this.axios.get("/platformService/sys/project/sysProjectTree").then(
        res => {
          this.loading = false;
          if (res.data.data) {
            this.sysProjectOptions = res.data.data;
          }
        },
        err => {
          this.loading = false;
        }
      );
    },
    getGroupOpts: function() {
      //查询字典中的分组 dictId:'dict-00013' 固定值，根据数据库情况进行调整 变更为查找sys_module_group表
      this.loading = true;
      this.axios
        .post("/platformService/sys/module/getGroupOpts", {
          projectCode: this.filters.projectCode
        })
        .then(
          res => {
            this.loading = false;
            this.groupOpts = res.data.data.data;
          },
          err => {
            this.loading = false;
          }
        );
    },
    delModule(module) {
      this.$confirm("确认删除这条记录吗?", "提示", {})
        .then(() => {
          this.loading = true;
          this.axios.post("/platformService/sys/module/delModule", module).then(
            res => {
              this.loading = false;
              if (res.data.code == "0") {
                this.loading = false;
                this.$message({ message: "操作成功！", type: "success" });
              } else {
                this.$message.error("操作失败！");
              }
              this.getModuleVoList(this.page);
            },
            err => {
              this.loading = false;
            }
          );
        })
        .catch(() => {});
    },
    editSubmit: function() {
      this.moduleVo.groupId = this.filters.groupId;
      this.moduleVo.projectCode = this.filters.projectCode;
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.editLoading = true;
          let serviceUrl = this.moduleVo.id
            ? "/platformService/sys/module/update"
            : "/platformService/sys/module/insert";
          this.axios.post(serviceUrl, this.moduleVo).then(
            res => {
              this.editLoading = false;
              if (res.data.code == "0") {
                this.$message({ message: "操作成功！", type: "success" });
                this.editFormVisible = false;
                this.getModuleVoList(this.page);
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
    },
    handleChange(value) {}
  }
};
</script>
<style scoped>
.table_content {
  font-size: 12px;
  /* height: -moz-calc(100% - 200px);
    height: -webkit-calc(100% - 20px); */
  height: calc(100% - 85px);
}
</style>
