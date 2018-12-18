<template>
  <div class="full-height">
    <!--工具条-->
    <div class="toolbar" style="padding-bottom: 0px;">
      <el-input style="width:200px;margin-right:10px;" v-model="filters.cname" placeholder="输入医疗机构名称" ></el-input>
      <el-button type="primary" @click="getUnitList(1)" >查询</el-button>
      <el-button type="primary" @click="unitEdit(null)" >新增</el-button>
    </div>
    <!--列表-->
    <div class="full-height">
      <el-table :data="units" highlight-current-row v-loading="loading" border style="width: 100%;" fit class="hospital-tableheight" height="css">
        <el-table-column type="index" width="50">
        </el-table-column>
        <el-table-column prop="code" label="单位编号">
        </el-table-column>
        <el-table-column prop="cname" label="单位名称">
        </el-table-column>
        <el-table-column prop="anotherName" label="单位别名">
        </el-table-column>
        <el-table-column prop="shortPinyin" label="拼音码">
        </el-table-column>
        <el-table-column prop="linkman" label="联系人">
        </el-table-column>
        <el-table-column prop="contactWay" label="联系方式">
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button @click="unitEdit(scope.row)" type="text">编辑</el-button>
            <el-button @click="delUnit(scope.row)" type="text">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[30, 100, 200, 400]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
        </el-pagination>
      </el-col>
    </div>
    <!--编辑界面-->
    <el-dialog :title="modelTitle" close="onClose" :visible.sync="editFormVisible" :close-on-click-modal="false">
      <el-form :model="unit" :inline="true" label-width="100px" :rules="editFormRules" ref="editForm">
        <el-form-item label="主数据编码" prop="masterCode" :error="nameErr">
          <el-input v-model="unit.masterCode" placeholder="请输入主数据编码" ></el-input>
        </el-form-item>
        <el-form-item label="公司名称">
          <el-input v-model="unit.cname" placeholder="请输入公司名称"></el-input>
        </el-form-item>
        <el-form-item label="公司别名">
          <el-input v-model="unit.anotherName" placeholder="请输入公司别名"></el-input>
        </el-form-item>
        <el-form-item label="拼音码">
          <el-input v-model="unit.shortPinyin" placeholder="请输入拼音码"></el-input>
        </el-form-item>
        <el-form-item label="营业执照注册号">
          <el-input v-model="unit.regCode" placeholder="请输入营业执照注册号"></el-input>
        </el-form-item>
        <el-form-item label="经营许可证编号">
          <el-input v-model="unit.jyxkCode" placeholder="请输入经营许可证编号"></el-input>
        </el-form-item>
        <el-form-item label="纳税人识别号">
          <el-input v-model="unit.nsrsbCode" placeholder="请输入纳税人识别号"></el-input>
        </el-form-item>
        <el-form-item label="单位类型">
          <el-select style="width:88%" v-model="unitKind" filterable placeholder="请选择单位类型" >
            <el-option v-for="item in unitKinds" :key="item.id" :label="item.ename" :value="item.val">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="法人代表">
          <el-input placeholder="请输入法人代表" v-model="unit.legal" ></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input placeholder="请输入地址" v-model="unit.address" ></el-input>
        </el-form-item>
        <el-form-item label="联系人">
          <el-input placeholder="请输入联系人" v-model="unit.linkman" ></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input placeholder="请输入联系方式" v-model="unit.contactWay" ></el-input>
        </el-form-item>
        <el-form-item label="传真">
          <el-input placeholder="请输入传真" v-model="unit.contactFax" ></el-input>
        </el-form-item>
        <el-form-item label="企业logo">
          <el-input placeholder="请输入企业logo" v-model="unit.logo" ></el-input>
        </el-form-item>
        <el-form-item label="邮政编码">
          <el-input placeholder="请输入邮政编码" v-model="unit.postCode" ></el-input>
        </el-form-item>
        <el-form-item label="电子邮箱">
          <el-input placeholder="请输入电子邮箱" v-model="unit.email" ></el-input>
        </el-form-item>
        <el-form-item label=" 备注信息">
          <el-input placeholder="请输入 备注信息" v-model="unit.remark" ></el-input>
        </el-form-item>
        <el-form-item label=" 是否三证合一">
          <el-input placeholder="请输入 备注信息" v-model="unit.isThreeInOne" ></el-input>
        </el-form-item>
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
      filters: {
        cname: ""
      },
      total: 0,
      page: 1,
      pageSize: 30,
      loading: false,
      units: [],
      unit: {},
      unitKind: "1",
      unitKinds: [],
      modelTitle: "",
      editFormVisible: false,
      editLoading: false,
      editFormRules: {
        cname: [{ required: true, message: "请输入单位名称", trigger: "blur" }]
      },
      nameErr: ""
    };
  },
  mounted() {
    this.getUnitList(1);
    this.getUnitKinds();
  },
  methods: {
    //字典获取单位类型
    getUnitKinds: function() {
      this.loading = true;
      this.axios
        .post("/platformService/sys/dict/getDictValueByDictEname", {
          dictName: "JGLX",
          val: "1"
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
    //获取列表
    getUnitList: function(pIndex) {
      this.page = pIndex;
      var param = {
        orderBy: "",
        queryObject: { cname: this.filters.cname, kind: 1 },
        page: this.page,
        rows: this.pageSize
      };
      this.loading = true;
      this.axios.post("/platformService/bas/company/getCompanys", param).then(
        res => {
          this.loading = false;
          if (res.data.code == "0") {
            this.units = res.data.data.data;
            this.total = res.data.data.total;
            //      this.page = res.data.data.pageNum;
          } else {
            this.$message.error(res.data.msg);
          }
        },
        err => {
          this.loading = false;
        }
      );
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getUnitList(this.page);
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.getUnitList(this.page);
    },
    unitEdit(item) {
      this.nameErr = "";
      if (item) {
        let unit = Object.assign({}, item);
        this.unit = unit;
        this.modelTitle = "修改医疗机构";
        this.unit.kind = this.unitKind;
      } else {
        this.unit = {};
        this.modelTitle = "添加医疗机构";
        this.unit.kind = this.unitKind;
      }
      this.editFormVisible = true;
    },
    delUnit(unit) {
      this.$confirm("确认删除这条记录吗?", "提示", {})
        .then(() => {
          this.loading = true;
          this.axios.post("/platformService/bas/company/delCompany", unit).then(
            res => {
              this.loading = false;
              if (res.data.code == "0") {
                this.loading = false;
                this.$message({ message: "操作成功！", type: "success" });
              } else {
                this.$message.error(res.data.msg);
              }
              this.getUnitList(this.page);
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
          //   this.unit.kind= 1;
          let serviceUrl = this.unit.id
            ? "/platformService/bas/company/update"
            : "/platformService/bas/company/insert";
          this.axios.post(serviceUrl, this.unit).then(
            res => {
              this.editLoading = false;
              if (res.data.code == "0") {
                this.$message({ message: "操作成功！", type: "success" });
                this.editFormVisible = false;
                this.getUnitList(this.page);
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
<style scoped>
.hospital-tableheight {
  height: calc(100% - 85px);
}
.el-table__body-wrapper {
  overflow-x: hidden;
}
.full-height {
  height: 100%;
}
</style>
