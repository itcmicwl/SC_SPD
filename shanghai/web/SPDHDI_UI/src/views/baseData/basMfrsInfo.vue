<template>
  <div style="height: 100%;">
      <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
          <el-form :inline="true" :model="filters">
            <el-form-item label="厂家类型：">
              <el-select v-model="filters.mfrsKind" placeholder="请选择" :clearable="true" >
                <el-option
                v-for="item in mfrsKinds"
                :key="item.val"
                :label="item.ename"
                :value="item.val">
                </el-option>
              </el-select>
            </el-form-item>
              <el-form-item label="厂家名称：">
                <el-input v-model="filters.mfrsName" placeholder="厂家名称" clearable></el-input>
              </el-form-item>
            <el-form-item>
              <el-button type="primary" v-on:click="getbasMfrsInfoList(1)" >查询</el-button>
              <el-button type="primary" @click="basMfrsInfoEdit(null)" >新增</el-button>
              <el-button type="success" @click="importDealerExcel()">从Excel导入</el-button>
          </el-form-item>
          </el-form>
        </el-col>
        <!--列表-->
        <template>
          <el-table :data="basMfrsInfos" highlight-current-row v-loading="loading" style="width: 100%;"
                    border fit class="basmfrsinfo-tableheight" height="outer">
            <el-table-column type="index" label="序号"  align="center" width="50"></el-table-column>
            <el-table-column prop="mfrsName" label="厂家名称" header-align="center"  ></el-table-column>
            <el-table-column prop="mfrsKind" label="厂家类型"  align="center" width="80" :formatter="formatmfrsKind"></el-table-column>
            <!--<el-table-column prop="productCode" label="生产许可证" header-align="center" width="180"></el-table-column>-->
            <!--<el-table-column prop="ename" label="操作员" width="130" align="center" :formatter="formatename"></el-table-column>-->
            <!--<el-table-column prop="lastUpdateDatetime" label="最后更新时间" header-align="center" width="140" sortable></el-table-column>-->
            <el-table-column prop="remark" label="备注" header-align="center" width="200"></el-table-column>
            <el-table-column label="标志" width="150" align="center">
              <template slot-scope="scope">
                <el-switch v-model="scope.row.flag" active-color="#13ce66" inactive-color="#ff4949" active-text="启用" inactive-text="停用" active-value="1" inactive-value="0"
                           @change="setStatus(scope.row)">
                </el-switch>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="90" align="center" fixed="right" >
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
        <el-dialog :title="modelTitle"   close="onClose"  :visible.sync="editFormVisible" :close-on-click-modal="false">
          <el-form :model="basMfrsInfo" label-width="120px" :rules="editFormRules" ref="editForm" class="el-form-item-nomsg">
            <el-row>
              <el-form-item label="厂家名称" prop="cname" :error="nameErr">
                <el-input v-model="basMfrsInfo.cname" placeholder="请输入厂家名称" ></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-col :span="12">
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
                  <el-input v-model="basMfrsInfo.agentName"  placeholder="请输入全国总代理"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="生产备案证">
                  <el-input v-model="basMfrsInfo.recordCard"  placeholder="请输入生产备案证号"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="生产许可证">
                  <el-input v-model="basMfrsInfo.productCode"  placeholder="请输入生产许可证"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="备注">
              <el-input type="textarea" :row="2" placeholder="请输入备注信息" v-model="basMfrsInfo.remark" ></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click.native="editFormVisible = false">取消</el-button>
            <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
          </div>
        </el-dialog>
        <el-dialog title="从Excel中导入厂商模板" close="onClose" :visible.sync="importExcelVisible" :show-close='true' :close-on-press-escape='true' :close-on-click-modal="true">
          <template>
            <div>
              <el-upload
                class="upload-demo"
                :disabled="uploadState"
                :action= "uploadUrl"
                :on-remove="handleRemove"
                :on-success="handleFileSuccess"
                :file-list="fileList"
                list-type="text">
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传一个Excel文件</div>
              </el-upload>
            </div>
          </template>

          <div slot="footer" class="dialog-footer">
            <el-button @click.native="importExcelVisible = false;uploadState=false">取消</el-button>
            <el-button type="primary" @click.native="importExcelSubmit" :loading="editLoading">提交</el-button>
          </div>
        </el-dialog>
  </div>
</template>
<script>
  import { fileServer } from '../../common/js/constance';
  export default {
      data() {
          return {
              uploadUrl:'',
              importExcelVisible:false,
              uploadState:false,
              fileMap:{id:'',ext:'',name:'',path:''},
              BaseGoodsTempUrl:'',
              fileList:[],

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
                  kind: '3'
              },
              modelTitle: "",
              editFormVisible: false,
              editLoading: false,
              editFormRules: {
                  cname: [
                      {required: true, message: '请输入名称', trigger: 'blur'}
                  ]
              },
              nameErr: ''
          }
      },
      methods: {
        importDealerExcel:function(){
          this.fileList=[];
          this.importExcelVisible=true;
        },
        handleRemove(file, fileList) {
          this.uploadState=false;
        },
        handleFileSuccess(response, file, fileList){
          var ob  = response.data[0];
          this.fileMap=ob;
          this.BaseGoodsTempUrl = 'upload/'+ob['path']+'.'+ob['ext'];
          this.uploadState=true;
        },
        importExcelSubmit:function(){
          this.uploadState=false;
          this.editLoading = true;
          this.axios.post('/spdHDIService/bas/mfrsInfo/importMfrsCerts', this.fileMap).then(res => {
            this.editLoading = false;
            if (res.data.code == "0") {
              this.$message({message: '操作成功！', type: 'success'});
            }else{
              this.$message.error(res.data.msg);
            }
          }, err => {
            this.editLoading = false;
            this.importExcelVisible=false;
          });
        },

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
                  this.basMfrsInfo.cname = this.basMfrsInfo.mfrsName;
                  this.kind = this.basMfrsInfo.mfrsKind;
                  this.basMfrsInfo.code = this.basMfrsInfo.id;
                  this.basMfrsInfo.kind = '3';
                  this.isThreeInOne = this.basMfrsInfo.isThreeInOne;
              } else {
                  this.basMfrsInfo = {};
                  this.kind = '1';
                  this.basMfrsInfo.kind = '3';
                  this.isThreeInOne = '0';
                  this.modelTitle = "添加生产厂家";
              }
              this.editFormVisible = true;

          },
          setStatus(basMfrsInfo) {
              this.loading = true;
              delete basMfrsInfo.ename;
              delete basMfrsInfo.cname;
              let tempMfrs = {id:basMfrsInfo.id,flag:basMfrsInfo.flag};
              this.axios.post("/spdHDIService/bas/mfrsInfo/setStatus", tempMfrs).then(res => {
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
                      let basMfrsInfo = this.basMfrsInfo;
                      this.editLoading = true;
                      basMfrsInfo.mfrsKind = this.kind;
                      basMfrsInfo.isThreeInOne = this.isThreeInOne;
                      delete basMfrsInfo.mfrsName;
                      delete basMfrsInfo.ename;
                      // let serviceUrl = this.basMfrsInfo.id ? "/spdHDIService/bas/mfrsInfo/update" : "/spdHDIService/bas/mfrsInfo/insert";
                      let serviceUrl = basMfrsInfo.id ? "/platformService/bas/company/update" : "/platformService/bas/company/insert";
                      this.axios.post(serviceUrl, basMfrsInfo).then(res => {
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
            if(row.mfrsKind=='1'){
              return '境内';
            }
            return '境外';
              // return this.mfrsKinds.find(item => item.val == row.mfrsKind).ename;
          }
      },
      mounted() {
        this.getMfrsKinds();
        this.getbasMfrsInfoList(1);
        this.uploadUrl =fileServer.upload +"baseData";
      }
  }

</script>
<style scoped>
    .basmfrsinfo-tableheight {
        height: calc(100% - 100px);
    }
</style>
