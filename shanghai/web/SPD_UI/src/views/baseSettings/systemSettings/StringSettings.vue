<template>
  <div style="height: 100%;">
    <!--工具条-->
    <el-col :span="24" style="height:100%;">
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true">
          <el-form-item label="配置项值">
            <el-input v-model="filters.val" placeholder="配置项值" ></el-input>
          </el-form-item>
          <el-form-item label="配置项说明">
            <el-input v-model="filters.remark" placeholder="配置项说明" ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getsysStringList(1)" >查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="sysStringEdit(null)" >新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <!--列表-->
      <template>
        <el-table :data="sysStrings" highlight-current-row v-loading="loading" style="width: 100%;" border fit class="StringSettings-tableheight" height="css">
          <el-table-column type="index" width="">
          </el-table-column>
          <el-table-column prop="ename" label="配置项名称">
          </el-table-column>
          <el-table-column prop="val" label="值" min-width="150">
          </el-table-column>
          <el-table-column prop="remark" label="描述">
          </el-table-column>
          <el-table-column prop="name" label="分类">
          </el-table-column>
          <el-table-column label="状态" width="150">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.status" active-color="#13ce66" inactive-color="#ff4949" active-text="启用" inactive-text="停用" active-value="1" inactive-value="0" @change="setStatus(scope.row)">
              </el-switch>
            </template>
          </el-table-column>
          <!--<el-table-column prop="uid" label="操作员ID">
                        </el-table-column>-->
          <el-table-column prop="mt" label="修改时间" width="150">
          </el-table-column>
          <!--<el-table-column prop="version" label="并发控制">
                        </el-table-column>-->
          <el-table-column label="操作" width="100">
            <template slot-scope="scope">
              <el-button @click="sysStringEdit(scope.row)" type="text">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--工具条-->
        <el-col :span="24" class="toolbar">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[15, 20, 30, 100]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
          </el-pagination>
        </el-col>
      </template>
      <!--编辑界面-->
      <el-dialog :title="modelTitle" close="onClose" :visible.sync="editFormVisible" :close-on-click-modal="false">
        <el-form :model="sysString" label-width="120px" :rules="editFormRules" ref="editForm">
          <el-row>
            <el-col>
              <el-form-item label="配置项名称：" prop="ename" :error="nameErr">
                <el-input v-model="sysString.ename" placeholder="请输入配置项名称" ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-form-item label="分类：">
                <el-radio-group v-model="radioVal">
                  <el-radio v-for="(item, index) in stringKinds" :label="item.val" :key="index" >
                    {{ item.ename }}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-form-item label="值：">
                <el-input type="textarea" :rows="6" placeholder="请输入配置项值" v-model="sysString.val"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-form-item label="备注：">
                <el-input v-model="sysString.remark" ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-form-item label="启用/停用：">
                <el-switch v-model="sysString.status" active-color="#13ce66" inactive-color="#ff4949" active-text="启用" inactive-text="停用" active-value="1" inactive-value="0">
                </el-switch>
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
        val: "",
        remark: ""
      },
      radioVal: "0",
      total: 0,
      page: 1,
      pageSize: 15,
      loading: false,
      stringKinds: [],
      sysStrings: [],
      sysString: {
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
    this.getsysStringList(1);
    this.getStringKinds();
  },
  methods: {
    //获取用户列表
    getsysStringList: function(pIndex) {
      this.page = pIndex;
      var param = {
        orderBy: "",
        queryObject: { val: this.filters.val, remark: this.filters.remark },
        page: this.page,
        rows: 20
      };
      this.loading = true;
      this.axios
        .post(`/platformService/sys/string/sysStringVoList`, param)
        .then(
          res => {
            this.loading = false;
            this.sysStrings = res.data.data.data;
            this.total = res.data.data.total;
            //this.page = res.data.data.pageNum;
          },
          err => {
            this.loading = false;
          }
        );
    },
    //字典获取字符分类
    getStringKinds: function() {
      this.loading = true;
      this.axios
        .post("/platformService/sys/dict/getDictValueByDictEname", {
          dictName: "ZFFL"
        })
        .then(
          res => {
            this.loading = false;
            if (res.data.code == "0") {
              this.stringKinds = res.data.data;
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
      this.getsysStringList(this.page);
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.getsysStringList(this.page);
    },
    sysStringEdit(item) {
      this.nameErr = "";
      if (item) {
        this.sysString = Object.assign({}, item);
        this.radioVal = this.sysString.kind;
        this.modelTitle = "修改字符资源";
      } else {
        this.sysString = { status: "1" };
        this.modelTitle = "添加字符资源";
      }
      this.editFormVisible = true;
    },
    setStatus(sysString) {
      this.loading = true;
      delete sysString.name;
      this.axios.post("/platformService/sys/string/setStatus", sysString).then(
        res => {
          this.loading = false;
          if (res.data.code == "0") {
            this.loading = false;
            this.$message({ message: "操作成功！", type: "success" });
            this.getsysStringList();
          } else {
            this.$message.error("操作失败！");
            sysString.status = sysString.status == "1" ? "0" : "1";
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
          this.sysString.kind = this.radioVal;
          let serviceUrl = this.sysString.id
            ? "/platformService/sys/string/update"
            : "/platformService/sys/string/insert";
          this.axios.post(serviceUrl, this.sysString).then(
            res => {
              this.editLoading = false;
              if (res.data.code == "0") {
                this.$message({ message: "操作成功！", type: "success" });
                this.editFormVisible = false;
                this.getsysStringList();
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
    addSubmit: function() {
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.editLoading = true;
          let serviceUrl = "/platformService/sys/string/insert";
          this.axios.post(serviceUrl, this.sysString).then(
            res => {
              this.editLoading = false;
              if (res.data.code == "0") {
                this.$message({ message: "操作成功！", type: "success" });
                this.editFormVisible = false;
                this.getsysStringList();
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
.StringSettings-tableheight {
  height: calc(100% - 85px);
}
</style>
