<template>
  <div style="height:100%">
    <!--工具条-->
    <el-col :span="24" style="height:100%">
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" label-width="80px">
          <el-form-item label="分组名称">
            <el-input v-model="filters.ename" placeholder="功能名称"></el-input>
          </el-form-item>
          <el-form-item label="分组别名">
            <el-input v-model="filters.cname" placeholder="功能名称"></el-input>
          </el-form-item>
          <el-button @click="getModuleGroupVoList(1)" type="primary">查询</el-button>
          <el-button @click="moduleGroupVoEdit(null)" type="primary">新增</el-button>
        </el-form>
      </el-col>
      <template>
        <el-table :data="moduleGroupVos" class="table_content" highlight-current-row v-loading="loading" style="width: 100%;" border fit height="css">
          <el-table-column type="index">
          </el-table-column>
          <el-table-column prop="ename" label="模块名称" sortable>
          </el-table-column>
          <el-table-column prop="cname" label="模块别名" sortable>
          </el-table-column>
          <el-table-column prop="projectCname" label="所属系统" sortable>
          </el-table-column>
          <el-table-column prop="remark" label="备注">
          </el-table-column>
          <el-table-column prop="mt" label="最后修改时间" sortable>
          </el-table-column>
          <el-table-column label="操作" width="150" fixed="right">
            <template slot-scope="scope">
              <el-button @click="moduleGroupVoEdit(scope.row)" type="text">编辑</el-button>
              <el-button @click="delModuleGroup(scope.row)" type="text">删除</el-button>
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
        <el-form ref="editForm" :inline="true" :model="moduleGroupVo" :rules="editFormRules" label-position="right" label-width="80px">
          <el-form-item :error="nameErr" label="分组名称" prop="ename">
            <el-input v-model="moduleGroupVo.ename" placeholder="请输入分组名称"></el-input>
          </el-form-item>
          <el-form-item label="分组别名">
            <el-input v-model="moduleGroupVo.cname" placeholder="请输入分组别名"></el-input>
          </el-form-item>
          <el-form-item label="备注">
            <el-input v-model="moduleGroupVo.remark" placeholder="请输入备注信息"></el-input>
          </el-form-item>
          <el-form-item label="所属系统">
            <el-cascader v-model="projectCodes" :options="sysProjectOptions" :props="sysProjectSltProps" :show-all-levels="false" style="width:88%" placeholder="请选择所属系统" filterable change-on-select>  <!-- 需要绑定到 moduleVo.projectCode-->
            </el-cascader>
          </el-form-item>
          <el-form-item label="排序号" prop="ordinal">
            <el-input-number v-model="moduleGroupVo.ordinal" :max="100" :min="1" @change="handleChange"></el-input-number>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click.native="editFormVisible = false">取消</el-button>
          <el-button :loading="editLoading" @click.native="editSubmit" type="primary">提交</el-button>
        </div>
      </el-dialog>
    </el-col>
  </div>
</template>
<script>
export default {
  data() {
    return {
      loading: false,
      filters: {
        ename: "",
        cname: ""
      },
      total: 0,
      page: 1,
      pageSize: 20,
      moduleGroupVos: [],
      moduleGroupVo: {},
      modelTitle: "",
      editFormVisible: false,
      editLoading: false,
      nameErr: "",
      editFormRules: {
        ename: [{ required: true, message: "请输入分组名称", trigger: "blur" }]
      },
      sysProjectOptions: [],
      sysProjectSltProps: {
        value: "code",
        label: "label"
      }
    };
  },
  computed: {
    projectCodes: {
      get: function() {
        let res = [];
        if (this.moduleGroupVo.projectCode) {
          let codes = this.moduleGroupVo.projectCode.split(".");
          if (codes.length > 1) {
            var i = 0;
            for (i; i < codes.length; i++) {
              let tempArr = codes.slice(0, i + 1);
              res.push(tempArr.join("."));
            }
          } else {
            res = codes;
          }
        }
        return res;
      },
      set: function(projectCodeArr) {
        let res = "";
        if (projectCodeArr.length > 0) {
          res = projectCodeArr[projectCodeArr.length - 1];
        }
        this.moduleGroupVo.projectCode = res;
      }
    }
  },
  mounted() {
    this.getModuleGroupVoList();
    this.getSysProjectOptions();
  },
  methods: {
    getModuleGroupVoList: function(pIndex) {
      this.page = pIndex;
      this.loading = true;
      //   var param = { orderBy: "", queryObject: { ename: this.filters.ename,cname:this.filters.cname,dictId:'PTXTGNFZ'}, page: this.page, rows: this.pageSize };
      var param = {
        orderBy: "",
        queryObject: { ename: this.filters.ename, cname: this.filters.cname },
        page: this.page,
        rows: this.pageSize
      };
      this.axios
        .post(`/platformService/sys/moduleGroup/getModuleGroupVos`, param)
        .then(
          res => {
            //  this.axios.post(`/platformService/sys/dict/getDicts`, null).then(res => {
            if (res.data.code == "0") {
              this.moduleGroupVos = res.data.data.data;
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
      this.getModuleGroupVoList(this.page);
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.getModuleGroupVoList(this.page);
    },
    moduleGroupVoEdit(item) {
      this.nameErr = "";
      if (item) {
        //   let sysPro = Object.assign({}, item);
        //   delete sysPro.pName;
        //  this.sysProject = sysPro;
        let theModuleGroupVo = Object.assign({}, item);
        this.moduleGroupVo = theModuleGroupVo;
        this.modelTitle = "修改模块分组";
      } else {
        this.moduleGroupVo = {};
        this.modelTitle = "添加模块分组";
      }
      this.editFormVisible = true;
    },
    getSysProjectOptions: function() {
      //  this.loading = true;
      this.axios
        .post(`/platformService/sys/module/getSysProjectOptions`, {})
        .then(
          res => {
            if (res.data.code == "0") {
              this.sysProjectOptions = res.data.data.children;
            } else {
              this.$message.error("查询失败！");
            }
          },
          err => {
            this.editLoading = false;
          }
        );
    },
    /*      getGroupOpts: function () {  //查询字典中的分组 dictId:'dict-00013' 固定值，根据数据库情况进行调整
        this.loading = true;
        this.axios.post('/platformService/sys/module/getGroupOpts',{dictId:'PTXTGNFZ'}).then(res => {
          this.loading = false;
          this.groupOpts = res.data.data.data;
        }, err => {
          this.loading = false;
        });
      },*/
    delModuleGroup(moduleGroup) {
      this.$confirm("确认删除这条记录吗?", "提示", {})
        .then(() => {
          this.loading = true;
          this.axios
            .post(
              "/platformService/sys/moduleGroup/delModuleGroup",
              moduleGroup
            )
            .then(
              res => {
                this.loading = false;
                if (res.data.code == "0") {
                  this.loading = false;
                  this.$message({ message: "操作成功！", type: "success" });
                } else {
                  this.$message.error(res.data.msg);
                }
                this.getModuleGroupVoList(this.page);
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
          let serviceUrl = this.moduleGroupVo.id
            ? "/platformService/sys/moduleGroup/update"
            : "/platformService/sys/moduleGroup/insert";
          this.axios.post(serviceUrl, this.moduleGroupVo).then(
            res => {
              this.editLoading = false;
              if (res.data.code == "0") {
                this.$message({ message: "操作成功！", type: "success" });
                this.editFormVisible = false;
                this.getModuleGroupVoList(this.page);
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
