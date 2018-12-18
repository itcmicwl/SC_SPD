<template>
  <section class="full-height" id="org">
    <el-row class="full-height" :gutter="10">
      <el-col :span="6" class="content-height">
        <el-radio-group v-model="radioVal" @change="radioClick">
          <el-radio v-for="(item, index) in unitKinds" :key="index" :label="item.val">
            {{ item.ename }} </el-radio>
        </el-radio-group>
        <el-select v-model="sltUnitId" @change="unitSelectClick" filterable>
          <el-option v-for="item in units" :key="item.id" :label="item.cname" :value="item.id">
          </el-option>
        </el-select>
        <el-form :inline="true" :model="filters">
          <el-form-item>
            <el-input v-model="filters.orgName" placeholder="机构名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="searchOrgs()" type="small" icon="el-icon-search"></el-button>
          </el-form-item>
        </el-form>
        <div>
          <el-tree :data="treeValue" :default-expanded-keys="expadoIds" :highlight-current="true" :props="defaultProps" @node-click="handleNodeClick" node-key="id">
          </el-tree>
        </div>
      </el-col>
      <el-col :span="18" class="content-height">
        <el-button-group>
          <el-button :disabled="btoFlag" @click="addOrg" type="primary">添加</el-button>
          <el-button @click="updateOrg" type="primary">保存</el-button>
          <el-button :disabled="btoFlag" @click="delOrg" type="primary">删除</el-button>
        </el-button-group>
        <el-form ref="editForm" :model="formValue" :rules="editFormRules" label-width="110px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="自编码：">
                <el-input v-model="formValue.selfCode"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="机构名称：" prop="ename">
                <el-input v-model="formValue.ename"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item :error="errors.pOrgCodeErr" label="上级机构：" prop="pOrgCode">
                <el-cascader v-model="selectedOrgParent" :change-on-select="true" :options="orgInfo4cascader" :props="orgSltProps" :show-all-levels="false" placeholder="请选择上级机构" filterable>
                </el-cascader>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="地址：" prop="address">
                <el-input v-model="formValue.address"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="所属单位名：" prop="corpName">
                <el-input v-model="formValue.corpName" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="所属单位ID：" prop="corpId">
                <el-input v-model="formValue.corpId" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="所属单位类型值：" prop="corpKind">
                <el-input v-model="formValue.corpKind" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系方式：" prop="contactWay">
                <el-input v-model="formValue.contactWay"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="排序号：">
                <el-input-number v-model="formValue.ordinal" :max="100" :min="1" @change="handleChange"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="ERP编码：">
                <el-input v-model="formValue.erpCode"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row >
            <el-form-item label="备注：">
              <el-input v-model="formValue.remark" :rows="2" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-row>
        </el-form>
      </el-col>
    </el-row>
  </section>
</template>
<script>
export default {
  data() {
    return {
      sltUnitId: "",
      radioVal: "1",
      unitKinds: [],
      unit: {},
      units: [],
      btoFlag: false,
      kindOptions: [
        {
          value: "0",
          label: "企业内部机构"
        },
        {
          value: "1",
          label: "上游厂商"
        },
        {
          value: "2",
          label: "下游客户"
        }
      ],
      editFormRules: {
        ename: [{ required: true, message: "请输入机构名称", trigger: "blur" }],
        corpName: [
          { required: true, message: "请从左侧下拉框选择单位", trigger: "blur" }
        ],
        pOrgCode: [
          { required: false, message: "选择上级机构", trigger: "blur" }
        ]
      },
      treeValue: [],
      defaultProps: {
        children: "children",
        label: "label",
        id: "id"
      },
      filters: {
        orgName: ""
      },
      ifUpdate: true,
      formValue: {},
      selectedOrgId: "",
      selectedOrgCode: "",
      expadoIds: ["1"],
      orgInfo4cascader: [],
      orgSltProps: {
        value: "code",
        label: "label"
      },
      errors: {
        contactWayErr: "",
        enameErr: "",
        cnameErr: "",
        pOrgErr: ""
      }
    };
  },
  computed: {
    selectedOrgParent: {
      get: function() {
        let res = [];
        if (this.selectedOrgCode) {
          //通过当前点击的orgId 的orgCode 找到其父code
          let orgCodes = this.selectedOrgCode.split(".");
          if (orgCodes.length > 1) {
            var i = 0;
            for (i; i < orgCodes.length; i++) {
              let tempArr = orgCodes.slice(0, i + 1);
              res.push(tempArr.join("."));
            }
          } else {
            res = orgCodes;
          }
        }
        res.pop(); //移除最后一个
        return res;
      },
      set: function(orgCodeArr) {
        let res = "";
        if (orgCodeArr.length > 0) {
          res = orgCodeArr[orgCodeArr.length - 1];
        }
        this.formValue.pid = res; //借用pid 存储pCode 后台通过该值查询后取真实的pid
      }
    }
  },
  mounted() {
    //  this.getOrg(); //展开树
    this.getUnitKinds();
    this.getUnitList();
  },
  methods: {
    radioClick: function() {
      this.unit = null;
      this.formValue = {};
      this.getUnitList();
    },
    unitSelectClick: function(d1, d2, d3) {
      this.sltUnitId = d1;
      this.orgInfo4cascader = [];

      //   this.unit = this.units.find(o => o.id === d1)
      this.units.find(o => {
        if (o.id == d1) this.unit = o;
      });

      this.formValue = {};
      this.getOrgsByUnit(d1); //展开树
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
              this.unitKinds = res.data.data.filter(u => u.val < 4);
            } else {
              this.$message.error(res.data.msg);
            }
          },
          err => {
            this.loading = false;
          }
        );
    },
    //获取列表
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
              this.orgInfo4cascader = [];
              this.formValue = {};
              this.getOrgsByUnit(this.unit.id);
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
          this.treeValue = res.data.data.children;
          this.expadoIds = [this.treeValue[0].id];
          if (res.data.data.children[0].children) {
            this.orgInfo4cascader = res.data.data.children[0].children;
          }
          this.loading = false;
        });
      /*  this.axios.post(`/platformService/sys/org/getTheOrg`, {id:this.selectedOrgId}).then(res=>{
        this.formValue=res.data.data;
      });*/
    },
    getOrg: function() {
      var param = {};
      this.loading = true;
      this.axios.post(`/platformService/sys/org/getOrgs`, null).then(res => {
        this.treeValue = res.data.data.children;
        this.loading = false;
      });
      this.axios
        .post(`/platformService/sys/org/getTheOrg`, { id: this.selectedOrgId })
        .then(res => {
          this.formValue = res.data.data;
        });
    },
    handleNodeClick(data) {
      this.formValue = {};
      this.ifUpdate = true;
      this.selectedOrgId = data.id;
      this.selectedOrgCode = data.code;

      if (data.id.indexOf("org") >= 0) {
        let orgParams = { id: data.id };
        this.axios
          .post(`/platformService/sys/org/getTheOrg`, orgParams)
          .then(res => {
            this.formValue = res.data.data;
            this.btoFlag = false;
            this.formValue.corpId = this.unit.id;
            this.formValue.corpKind = this.unit.kind;
            this.formValue.corpName = this.unit.cname;
            this.formValue.corpKindName = ""; //需要关联查字典值表
          });
      }
    },
    addOrg: function() {
      this.btoFlag = true;
      this.ifUpdate = false;
      var pCname = this.formValue.cname;
      var pName = this.formValue.ename;

      this.formValue = {
        id: null,
        pid: null,
        code: null,
        selfCode: null,
        kind: null,
        ename: null,
        cname: null,
        ordinal: null,
        shortPinyin: null,
        address: null,
        contactWay: null,
        remark: null,
        nodeKind: null,
        flag: null,
        lastUpdateDatetime: null,
        version: 0
      };
      this.formValue.pid = "";

      if (
        // this.selectedOrgId.indexOf("org") == 0 ||
        // this.selectedOrgId.indexOf("org") == 0 ||
        this.selectedOrgId.indexOf("org") == 0
      ) {
        this.formValue.pid = this.selectedOrgId;
      }
      this.formValue.pCname = pCname;
      this.formValue.pName = pName;
      this.formValue.corpId = this.unit.id;
      this.formValue.corpKind = this.unit.kind;
      this.formValue.corpName = this.unit.cname;
      this.formValue.corpKindName = ""; //需要关联查字典值表
    },
    updateOrg: function() {
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.formValue.cname = this.formValue.ename;
          this.axios
            .post(`/platformService/sys/org/update`, this.formValue)
            .then(
              res => {
                if (res.data.code == "0") {
                  //alert("操作成功");
                  this.$message({
                    message: "操作成功！",
                    type: "success"
                  });
                  this.formValue = res.data.data;
                  this.formValue.corpId = this.unit.id;
                  this.formValue.corpKind = this.unit.kind;
                  this.formValue.corpName = this.unit.cname;
                  this.axios
                    .post(`/platformService/sys/org/getOrgsByUnit`, {
                      corpId: this.unit.id,
                      corpCname: this.unit.cname
                    })
                    .then(res => {
                      this.treeValue = res.data.data.children;
                      this.orgInfo4cascader =
                        res.data.data.children[0].children;
                      this.loading = false;
                      //  alert('修改后默认展开id:'+this.selectedOrgId);
                      this.expadoIds = [this.selectedOrgId];
                    });
                  let orgParams = { id: data.id };
                  this.axios
                    .post(`/platformService/sys/org/getTheOrg`, orgParams)
                    .then(res => {
                      this.formValue = res.data.data;
                      this.btoFlag = false;
                      this.formValue.corpId = this.unit.id;
                      this.formValue.corpKind = this.unit.kind;
                      this.formValue.corpName = this.unit.cname;
                      this.formValue.corpKindName = ""; //需要关联查字典值表
                    });
                  this.btoFlag = false;
                } else {
                  this.$message.error("操作失败！");
                }
              },
              err => {}
            );
        }
      });
    },
    delOrg: function() {
      //  alert('delOrg:'+this.selectedOrgId);
      //删除成功后树应该定位到删除节点的父亲
      var point = this.formValue.pid;
      this.axios
        .post(`/platformService/sys/org/delete`, this.formValue)
        .then(res => {
          if (res.data.code == "0") {
            this.$message({
              message: "操作成功！",
              type: "success"
            });
            this.formValue = {
              id: null,
              pid: null,
              code: null,
              selfCode: null,
              kind: null,
              ename: null,
              cname: null,
              ordinal: null,
              shortPinyin: null,
              address: null,
              contactWay: null,
              remark: null,
              nodeKind: null,
              flag: null,
              lastUpdateDatetime: null,
              version: 0
            };
            this.btoFlag = true;
            this.axios
              .post(`/platformService/sys/org/getOrgsByUnit`, {
                corpId: this.unit.id,
                corpCname: this.unit.cname
              })
              .then(res => {
                this.treeValue = res.data.data.children;
                this.loading = false;
                //   alert('删除后默认展开id:'+point);
                this.selectedOrgId = point;
                this.expadoIds = [this.selectedOrgId];
                if (res.data.data.children[0].children) {
                  this.orgInfo4cascader = res.data.data.children[0].children;
                }
              });
          } else {
            if (res.data.validateErrors.fieldErrors.length > 0) {
              this.$message.error(res.data.validateErrors.fieldErrors[0]);
            } else {
              this.$message.error(res.data.msg);
            }
          }
        });
    },
    searchOrgs: function() {
      if (this.unit.id == null) {
        alert("请先选择单位！");
        return;
      }
      var p = this.filters.orgName;
      let param = { cname: p, corpId: this.unit.id };
      this.loading = true;
      this.axios
        .post(`/platformService/sys/org/searchOrgs`, param)
        .then(res => {
          var ids = [];
          for (var i = 0; i < res.data.data.length; i++) {
            ids[i] = res.data.data[i].id;
          }
          this.expadoIds = ids;
        });
    },
    handleChange(value) {}
  }
};
</script>
<style scoped>
.el-tree-node__label {
  font-size: 12px;
}
.el-table {
  font-size: 12px;
}
.el-tree {
  min-height: 500px;
}
.full-height {
  height: 100%;
}
.content-height {
  height: calc(100% - 20px);
}
</style>
<style>
#org .el-radio__label {
  padding-left: 5px;
}
#org .el-radio + .el-radio {
  margin-left: 10px;
}
</style>
