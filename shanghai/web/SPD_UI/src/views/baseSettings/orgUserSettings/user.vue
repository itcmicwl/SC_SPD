<template>
  <div class="full-height" id="user">
    <!--工具条-->
    <el-col :span="7" class="full-height">
      <el-radio-group v-model="radioVal" @change="radioClick">
        <el-radio v-for="(item, index) in unitKinds" :label="item.val" :key="index" >
          {{ item.ename }}
        </el-radio>
      </el-radio-group>
      <el-select v-model="sltUnitId" filterable @change="unitSelectClick">
        <el-option v-for="item in units" :key="item.id" :label="item.cname" :value="item.id">
        </el-option>
      </el-select>
      <el-tree :data="orgInfo" @node-click="handleNodeClick" highlight-current class="querytree">
      </el-tree>
    </el-col>
    <el-col :span="17" class="full-height">
      <el-row class="toolbar" style="padding-bottom: 0px;">
        <el-input style="width: 200px;margin-right:10px;" v-model="filters.ename" placeholder="姓名" ></el-input>
        <el-button type="primary" @click="getUser(1)" >查询</el-button>
        <el-button type="primary" @click="userEdit(null)" >新增</el-button>
      </el-row>
      <!--列表-->
      <el-row class="full-height">
        <el-table :data="users" highlight-current-row v-loading="loading" border style="width: 100%;" fit class="table_content" height="css">
          <el-table-column type="index" width="50">
          </el-table-column>
          <el-table-column prop="ename" label="姓名" sortable>
          </el-table-column>
          <el-table-column prop="orgName" label="所属机构" min-width="150" sortable>
          </el-table-column>
          <el-table-column prop="tel" label="电话" sortable>
          </el-table-column>
          <el-table-column prop="job" label="职务" sortable>
          </el-table-column>
          <el-table-column label="状态" width="150">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.status" active-color="#13ce66" inactive-color="#ff4949" active-text="启用" inactive-text="停用" active-value="1" inactive-value="0" @change="userChange(scope.row)">
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-button @click="userEdit(scope.row)" type="text">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--工具条-->
        <el-col :span="24" class="toolbar">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[15, 20, 50, 100]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
          </el-pagination>
        </el-col>
      </el-row>
    </el-col>
    <!--编辑界面-->
    <el-dialog :title="modelTitle" close="onClose" :visible.sync="editFormVisible" :close-on-click-modal="false">
      <el-form :model="user" label-width="90px" :rules="editFormRules" ref="editForm">
        <el-row>
          <el-col :span="12">
            <el-form-item label="账号" prop="userCode" :error="errors.userCodeErr">
              <el-input v-model="user.userCode" placeholder="请输入用户账号" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名">
              <el-input v-model="user.ename" placeholder="请输入用户账号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="别名">
              <el-input v-model="user.cname" placeholder="请输入用户别名"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="职务">
              <el-input placeholder="请输入职务信息" v-model="user.job" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="机构" prop="orgCode" :error="errors.orgCodeErr">
              <el-cascader placeholder="请选择机构" v-model="userOrgCode" :show-all-levels="false" :options="orgInfo4cascader" filterable :change-on-select="true" :props="orgSltProps"></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号">
              <el-input v-model="user.tel" placeholder="请输入手机号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="内部ERP编码">
              <el-input v-model="user.erpCode" placeholder="请输入内部ERP编码"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="SCM编号">
              <el-input v-model="user.scmCode" placeholder="请输入SCM编号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="电子邮件">
              <el-input v-model="user.email" placeholder="请输入电子邮件"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="QQ">
              <el-input v-model="user.qq" placeholder="请输入QQ账号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="备注">
              <el-input v-model="user.others" placeholder="备注信息"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序">
              <el-input-number v-model="user.ordinal" :min="1" :max="100" ></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="启用/停用">
            <el-switch v-model="user.status" active-color="#13ce66" inactive-color="#ff4949" active-text="启用" inactive-text="停用" active-value="1" inactive-value="0">
            </el-switch>
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      sltUnitId: "",
      radioVal: "1",
      unitKinds: [],
      userKinds: [],
      unit: {},
      units: [],
      filters: {
        ename: "",
        orgCode: "",
        orgId: "",
        corpId: ""
      },
      total: 0,
      page: 1,
      pageSize: 15,
      loading: false,
      users: [],
      user: {
        kind: "1",
        orgCode: "",
        status: "1"
      },
      modelTitle: "添加用户",
      editFormVisible: false,
      editLoading: false,
      editFormRules: {
        userCode: [
          { required: true, message: "请输入账号", trigger: "blur" },
          { min: 6, max: 20, message: "长度在 6 到 20 个字符" },
          {
            pattern: /^(\w){6,20}$/,
            message: "只能输入6-20个字母、数字、下划线"
          }
        ],
        orgCode: [
          { required: true, message: "请选择机构", trigger: "blur" },
          { min: 1, message: "机构不能为空" }
        ]
      },
      orgInfo: [],
      orgInfo4cascader: [],
      orgSltProps: {
        value: "code",
        label: "label"
      },
      errors: {
        userCodeErr: "",
        orgCodeErr: ""
      }
    };
  },
  computed: {
    userOrgCode: {
      get: function() {
        let res = [];
        if (this.filters.orgCode) {
          let userCodes = this.filters.orgCode.split(".");
          if (userCodes.length > 1) {
            var i = 0;
            for (i; i < userCodes.length; i++) {
              let tempArr = userCodes.slice(0, i + 1);
              res.push(tempArr.join("."));
            }
          } else {
            res = userCodes;
          }
        }
        if (this.user.orgCode) {
          let userCodes = this.user.orgCode.split(".");
          if (userCodes.length > 1) {
            var i = 0;
            for (i; i < userCodes.length; i++) {
              let tempArr = userCodes.slice(0, i + 1);
              res.push(tempArr.join("."));
            }
          } else {
            res = userCodes;
          }
        }
        return res;
      },
      set: function(orgCodeArr) {
        let res = "";
        if (orgCodeArr.length > 0) {
          res = orgCodeArr[orgCodeArr.length - 1];
        }
        this.user.orgCode = res;
      }
    }
  },
  mounted() {
    this.getUnitKinds();
    this.getUnitList();
  },
  methods: {
    //获取用户列表
    getUser: function(pIndex) {
      this.page = pIndex;
      var param = {
        orderBy: "",
        queryObject: {
          ename: this.filters.ename,
          orgId: this.filters.orgId,
          corpId: this.filters.corpId
        },
        page: this.page,
        rows: 20
      };
      this.loading = true;
      this.axios.post(`/platformService/sys/user/userlist`, param).then(
        res => {
          this.loading = false;
          this.users = res.data.data.data;
          this.total = res.data.data.total;
        },
        err => {
          this.loading = false;
        }
      );
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.getUser(this.page);
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getUser(this.page);
    },
    userEdit(item) {
      var param = {};
      this.axios
        .post(`/platformService/sys/org/getOrgsByUnit`, {
          corpId: this.unit.id,
          corpCname: this.unit.cname
        })
        .then(res => {
          this.orgInfo4cascader = res.data.data.children[0].children;
        });
      if (item) {
        this.user = Object.assign({}, item);
        this.modelTitle = "修改用户";
      } else {
        this.user = { kind: "1", orgCode: "", status: "1" };
        this.modelTitle = "添加用户";
        this.user.orgCode = this.filters.orgCode;
      }
      this.editFormVisible = true;
    },
    userChange(user) {
      this.loading = true;
      this.axios.post("/platformService/sys/user/changeStatus", user).then(
        res => {
          this.loading = false;
          if (res.data.code == "0") {
            this.loading = false;
            this.$message({ message: "操作成功！", type: "success" });
          } else {
            this.$message.error("操作失败！");
            user.status = user.status == "1" ? "0" : "1";
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
          let serviceUrl = this.user.userId
            ? "/platformService/sys/user/update"
            : "/platformService/sys/user/insert";
          this.axios.post(serviceUrl, this.user).then(
            res => {
              this.editLoading = false;
              if (res.data.code == "0") {
                this.$message({ message: "操作成功！", type: "success" });
                this.editFormVisible = false;
                this.getUser();
              } else if (res.data.code == "-122") {
                let err = res.data.validateErrors.fieldErrors[0];
                if (err.field == "userCode") {
                  this.errors.userCodeErr += err.message;
                  arr.push(h("span", null, err.message));
                }
                _this.$msgbox({
                  title: "错误",
                  type: "error",
                  message: h("p", null, arr)
                });
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
    handleNodeClick(data) {
      this.filters.orgCode = data.code;
      if (data.id.indexOf("org") == -1) {
        this.filters.orgId = null;
      } else this.filters.orgId = data.id;
      this.getUser(1);
    },
    //字典获取单位类型
    getUnitKinds: function() {
      this.loading = true;
      this.axios
        .post("/platformService/sys/dict/getDictValueByDictEname", {
          dictName: "JGLX"
        })
        .then(
          res => {
            this.loading = false;
            if (res.data.code == "0") {
              this.unitKinds = res.data.data;
            } else {
              this.$message.error(res.data.msg);
            }
          },
          err => {
            this.loading = false;
          }
        );
    },
    radioClick: function() {
      this.unit = null;
      this.getUnitList();
    },
    unitSelectClick: function(d1, d2, d3) {
      this.sltUnitId = d1;
      this.users = [];
      this.total = 0;
      this.units.find(o => {
        if (o.id == d1) this.unit = o;
      });
      this.filters.corpId = this.unit.id;
      this.getOrgsByUnit(this.unit.id); //展开树
    },
    //获取单位列表
    getUnitList: function() {
      var param = {
        orderBy: "",
        queryObject: { flag: 0, kind: this.radioVal },
        page: 0,
        rows: 0
      };
      this.loading = true;
      this.axios.post("/platformService/bas/company/getCompanys", param).then(
        res => {
          this.loading = false;
          if (res.data.code == "0") {
            this.units = res.data.data.data;
            if (this.units) {
              this.unit = this.units[0];
              this.sltUnitId = this.unit.id;
            }
          } else {
            this.$message.error(res.data.msg);
          }
        },
        err => {
          this.loading = false;
        }
      );
    },
    getOrgsByUnit: function(unitId) {
      var param = {};
      this.loading = true;
      this.axios
        .post(`/platformService/sys/org/getOrgsByUnit`, {
          corpId: unitId,
          corpCname: this.unit.cname
        })
        .then(res => {
          this.orgInfo = res.data.data.children;
          this.loading = false;
        });
    }
  }
};
</script>
<style scoped>
.el-tree-node__label {
  font-size: 12px;
}
.table_content {
  height: calc(100% - 80px);
}
.el-tree {
  height: calc(100%-100px);
}
.el-table__body-wrapper {
  overflow-x: hidden;
}
.querytree {
  height: calc(100%-150px);
}
.full-height {
  height: 100%;
}
</style>
<style>
#user .el-radio__label {
  padding-left: 5px;
}
#user .el-radio + .el-radio {
  margin-left: 10px;
}
</style>
