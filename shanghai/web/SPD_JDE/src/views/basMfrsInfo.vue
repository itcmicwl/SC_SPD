<template>
  <div style="height: 100%;">
      <!--工具条-->      
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
          <el-form :inline="true" :model="filters">
            <el-form-item label="厂家类型：">
              <el-select v-model="filters.mfrsKind" placeholder="请选择" :clearable="true" size="small">
                <el-option
                v-for="item in mfrsKinds"
                :key="item.val"
                :label="item.ename"
                :value="item.val">
                </el-option>
              </el-select>
            </el-form-item>
              <el-form-item label="厂家名称：">
                <el-input v-model="filters.mfrsName" placeholder="厂家名称" size="small"></el-input>
              </el-form-item>
            <el-form-item>
              <el-button type="primary" v-on:click="getbasMfrsInfoList(1)" size="small">查询</el-button>
              <el-button type="primary" @click="basMfrsInfoEdit(null)" size="small">新增</el-button>
          </el-form-item>
          </el-form>
        </el-col>
        <!--列表-->
        <template>
          <el-table :data="basMfrsInfos" highlight-current-row v-loading="loading" style="width: 100%;" border fit height="auto" class="basmfrsinfo-tableheight">
            <el-table-column type="index" label="序号"  align="center" width="62"></el-table-column>
            <el-table-column prop="mfrsName" label="厂家名称" header-align="center" min-width="250" ></el-table-column>
            <el-table-column prop="mfrsKind" label="厂家类型"  align="center" width="90" :formatter="formatmfrsKind"></el-table-column>
            <!--<el-table-column prop="productCode" label="生产许可证" header-align="center" width="180"></el-table-column>-->
            <el-table-column prop="ename" label="操作员" width="150" align="center" :formatter="formatename"></el-table-column>
            <el-table-column prop="lastUpdateDatetime" label="最后更新时间" header-align="center" width="140" sortable></el-table-column>
            <el-table-column prop="remark" label="备注" header-align="center" min-width="250"></el-table-column>
            <el-table-column label="标志" width="70" header-align="center">
              <template slot-scope="scope">
                <el-switch v-model="scope.row.flag" active-color="#13ce66" inactive-color="#ff4949" active-text="启用" inactive-text="停用" active-value="1" inactive-value="0" size="small"
                           @change="setStatus(scope.row)">
                </el-switch>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120" header-align="center" fixed="right" >
                <template slot-scope="scope">
                    <el-button-group>
                        <el-button type="primary" size="mini" @click="basMfrsInfoEdit(scope.row)" icon="edit"  >编辑</el-button>
                        <el-button type="danger" size="mini" @click="deleteSubmit(scope.row)" icon="delete" >删除</el-button>
                    </el-button-group>
                </template>
            </el-table-column>
          </el-table>
          <!--工具条-->
          <el-col :span="24" class="toolbar">
            <el-pagination small @size-change="handlePSizeChange" style="padding-right: 20px;text-align: right;"
                           @current-change="handleCurrentChange" :current-page="page"
                           layout="total, sizes, prev, pager, next" :page-sizes="[20, 30, 50, 100]" :page-size="pageSize" :total="total">
            </el-pagination>
          </el-col>
        </template>
        <!--编辑界面-->
        <el-dialog :title="modelTitle"   close="onClose"  v-model="editFormVisible" :close-on-click-modal="false">
          <el-form :model="basMfrsInfo" label-width="120px" :rules="editFormRules" ref="editForm" class="el-form-item-nomsg">
            <el-row>
              <el-form-item label="厂家名称" prop="mfrsName" :error="nameErr">
                <el-input v-model="basMfrsInfo.mfrsName" placeholder="请输入厂家名称" size="small"></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-col :span="9">
              <el-form-item label="厂家类型">
                <el-radio-group v-model="kind">
                  <el-radio
                          v-for="item in mfrsKinds"
                          :key="item.val"
                          :label="item.val"
                          :value="item.val">
                    {{item.ename}}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
              </el-col>
            </el-row>
            <el-row v-if="this.kind==2">
              <el-col>
                <el-form-item label="全国总代理">
                  <el-input v-model="basMfrsInfo.agentName" size="small" placeholder="请输入全国总代理"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="营业执照注册号">
                  <el-input v-model="basMfrsInfo.regCode" size="small" placeholder="请输入营业执照注册号"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="经营许可证编号">
                  <el-input v-model="basMfrsInfo.jyxkCode" size="small" placeholder="请输入经营许可证编号"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="纳税人识别号">
                  <el-input v-model="basMfrsInfo.nsrsbCode" size="small" placeholder="请输入纳税人识别号"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="生产许可证">
                  <el-input v-model="basMfrsInfo.productCode" size="small" placeholder="请输入生产许可证"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="备注">
              <el-input type="textarea" :row="2" placeholder="请输入备注信息" v-model="basMfrsInfo.remark" size="small"></el-input>
            </el-form-item>
            <el-form-item label="是否三证合一">
              <el-radio-group v-model="isThreeInOne">
                <el-radio label="1">是</el-radio>
                <el-radio label="0">否</el-radio>
              </el-radio-group>
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
              mfrsKinds:[],
              filters: {
                  mfrsKind: '',
                  mfrsName: ''
              },
              kind: '1',
              isThreeInOne: '0',
              total: 0,
              page: 1,
              pageSize:20,
              loading: false,
              basMfrsInfos: [],
              basMfrsInfo: {
                  isThreeInOne: '1',
                  flag: '1',
                  kind: '1'
              },
              modelTitle: "",
              editFormVisible: false,
              editLoading: false,
              editFormRules: {
                  mfrsName: [
                      {required: true, message: '请输入名称', trigger: 'blur'}
                  ]
              },
              nameErr: ''
          }
      },
      methods: {
          //获取供应商列表
          getbasMfrsInfoList: function (pIndex) {
              this.page = pIndex;
              var param = {
                  orderBy: "",
                  queryObject: {mfrsKind: this.filters.mfrsKind, mfrsName: this.filters.mfrsName},
                  page: this.page,
                  rows: this.pageSize
              };
              this.loading = true;
              this.axios.post(`/spdHDIService/bas/mfrsInfo/basMfrsInfoList`, param).then(res => {
                  this.loading = false;
                  this.basMfrsInfos = res.data.data.data;
                  this.total = res.data.data.total;
                //   this.page = res.data.data.pageNum;
              }, err => {
                  this.loading = false;
              });
          },
          handleCurrentChange(val) {
              this.page = val;
              this.getbasMfrsInfoList(this.page);
          },
          handlePSizeChange(val) {
              this.pageSize = val;
              this.getbasMfrsInfoList(this.page);
          },
          basMfrsInfoEdit(item) {
              this.nameErr = '';
              if (item) {
                  this.basMfrsInfo = Object.assign({}, item);
                  this.modelTitle = "修改生产厂家";
                  this.kind = this.basMfrsInfo.mfrsKind;
                  this.isThreeInOne = this.basMfrsInfo.isThreeInOne;
              } else {
                  this.basMfrsInfo = {};
                  this.kind = '1';
                  this.isThreeInOne = '0';
                  this.modelTitle = "添加生产厂家";
              }
              this.editFormVisible = true;

          },
          setStatus(basMfrsInfo) {
              this.loading = true;
              delete basMfrsInfo.ename;
              delete basMfrsInfo.cname;
              this.axios.post("/spdHDIService/bas/mfrsInfo/setStatus", basMfrsInfo).then(res => {
                  this.loading = false;
                  if (res.data.code == "0") {
                      this.loading = false;
                      this.$message({message: '操作成功！', type: 'success'});
                      this.getbasMfrsInfoList(this.page);
                  } else {
                      this.$message.error('操作失败！');
                      basMfrsInfo.flag = basMfrsInfo.flag == "1" ? "0" : "1";
                  }
              }, err => {
                  this.loading = false;
              });
          },
          editSubmit: function () {
              var _this = this;
              var form = this.$refs.editForm;
              const h = this.$createElement;
              var arr = new Array();
              form.validate((valid) => {
                  if (valid) {
                      this.editLoading = true;
                      this.basMfrsInfo.mfrsKind = this.kind;
                      this.basMfrsInfo.isThreeInOne = this.isThreeInOne;
                      delete this.basMfrsInfo.ename;
                      delete this.basMfrsInfo.cname;
                      let serviceUrl = this.basMfrsInfo.id ? "/spdHDIService/bas/mfrsInfo/update" : "/spdHDIService/bas/mfrsInfo/insert";
                      this.axios.post(serviceUrl, this.basMfrsInfo).then(res => {
                          this.editLoading = false;
                          if (res.data.code == "0") {
                              this.$message({message: '操作成功！', type: 'success'});
                              this.editFormVisible = false;
                              this.getbasMfrsInfoList(this.page);
                          } else if (res.data.code == "-2") {
                              this.nameErr = res.data.msg;
                          }
                          else {
                              this.$message.error('操作失败！');
                          }
                      }, err => {
                          this.editLoading = false;
                      });
                  } else {
                      var fields = form.fields;
                      var msg = "";
                      fields.forEach(f => {
                          if (f.validateState == "error") {
                              msg += "</br>" + f.validateMessage;
                              if (arr.length > 0) arr.push(h('br', null, null));
                              arr.push(h('span', null, f.validateMessage));
                          }
                      });
                      _this.$msgbox({title: "错误", type: "error", message: h('p', null, arr)});
                  }

              });
          },
          deleteSubmit: function (basMfrsInfo) {
              this.$confirm('确认删除这条记录吗?', '提示', {}).then(() => {
                  this.loading = true;
                  this.axios.post("/spdHDIService/bas/mfrsInfo/delete", basMfrsInfo).then(res => {
                      this.loading = false;
                      if (res.data.code == "0") {
                          this.loading = false;
                          this.$message({message: '操作成功！', type: 'success'});
                      } else {
                          this.$message.error(res.data.msg);

                      }
                      this.getbasMfrsInfoList(this.page);
                  }, err => {
                      this.loading = false;
                  });
              }).catch(() => {

              });
          },
          //字典获取：生产厂家类型
          getMfrsKinds: function () {
              this.loading = true;
              this.axios.post('/platformService/sys/dict/getDictValueByDictEname',{dictName:'CJLX'}).then(res => {
                  this.loading = false;
                  if (res.data.code == "0") {
                      this.mfrsKinds = res.data.data;
                  }
                  else{
                      this.$message.error( res.data.msg);
                  }
              }, err => {
                  this.loading = false;
              });
          },
          /**
           * 格式化生产厂家类型
           */
          formatmfrsKind (row){
              return this.mfrsKinds.find(item => item.val == row.mfrsKind).ename;
          },
          formatename (row){
              return row.ename?row.ename:row.cname;
          },
      },
      mounted() {
          this.getbasMfrsInfoList(1);
          this.getMfrsKinds();
      }
  }

</script>
<style scoped>
    .basmfrsinfo-tableheight {
        height: calc(100% - 85px);
    }
</style>
