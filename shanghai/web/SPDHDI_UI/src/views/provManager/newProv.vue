<template>
  <div id="newProv">
      <!--工具条-->
      <el-col :span="24" style="height: 100%">
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
          <el-form :inline="true" :model="filters">
            <el-form-item label="供应商名称：">
              <el-input v-model="filters.provName" placeholder="供应商名称" ></el-input>
            </el-form-item>
            <el-form-item label="联系人：">
              <el-input v-model="filters.provLxr" placeholder="联系人" ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" v-on:click="getProvHosInfoVoList(1)" >查询</el-button>
            </el-form-item>
          </el-form>
        </el-col>
        <!--列表-->
        <template>
          <el-table :data="provHosInfos" highlight-current-row v-loading="loading" style="width: 100%;" border fit class="newProv-tableheight">
            <el-table-column type="index" label="序号" align="center" width="62"></el-table-column>
            <el-table-column prop="provName" label="供应商名称"  header-align="center"></el-table-column>
            <el-table-column prop="provLxr" label="联系人"></el-table-column>
            <el-table-column prop="provLxrTelphonenum" label="联系电话"></el-table-column>
            <el-table-column prop="lastUpdateDatetime" label="最后更新时间"></el-table-column>
            <el-table-column label="操作" width="80" align="center">
              <template slot-scope="scope">
                  <el-button size="mini" @click="newProvAudit(scope.row)" type="info">审核</el-button>
              </template>
            </el-table-column>
          </el-table>
          <!--工具条-->
          <el-col :span="24" class="toolbar">
            <el-pagination v-if="total>0" small @size-change="handlePSizeChange" style="padding-right: 20px;text-align: right;"
                           @current-change="handleCurrentChange" :current-page="page"
                           layout="total, sizes, prev, pager, next" :page-sizes="[20, 30, 50, 100]" :page-size="pageSize" :total="total">
            </el-pagination>
          </el-col>
        </template>
        <!--审核界面-->
        <el-dialog :title="modelTitle" top="10vh" close="onClose" :visible.sync="editFormVisible" :close-on-click-modal="false" width="75%">
          <el-row :gutter="2">
            <!--列表-->
            <template>
              <el-col :span="24">
                <div style="padding-left: 10px;height: 100%">
                  <div id="newProvQuery">
                    <el-form :model="company" label-width="120px">
                      <el-row>
                        <el-col :span="12">
                          <el-form-item label="公司名称：" style="margin-bottom: 0px;" prop="cname">
                            <el-col :span="24">
                              <el-input v-model="company.cname" readonly ></el-input>
                            </el-col>
                          </el-form-item>
                          <!--<el-form-item label="企业编号：" style="margin-bottom: 0px;" prop="code">-->
                            <!--<el-col :span="24">-->
                              <!--<el-input v-model="company.code" readonly ></el-input>-->
                            <!--</el-col>-->
                          <!--</el-form-item>-->
                          <el-form-item label="公司别名：" style="margin-bottom: 0px;" prop="anotherName">
                            <el-col :span="24">
                              <el-input v-model="company.anotherName" readonly ></el-input>
                            </el-col>
                          </el-form-item>
                          <el-form-item label="法人代表：" style="margin-bottom: 0px;" prop="legal">
                            <el-col :span="24">
                              <el-input v-model="company.legal" readonly ></el-input>
                            </el-col>
                          </el-form-item>
                          <el-form-item label="公司地址：" style="margin-bottom: 0px;" prop="address">
                            <el-col :span="24">
                              <el-input v-model="company.address" readonly ></el-input>
                            </el-col>
                          </el-form-item>
                          <el-form-item label="电子邮箱：" style="margin-bottom: 0px;" prop="email">
                            <el-col :span="24">
                              <el-input v-model="company.email" readonly ></el-input>
                            </el-col>
                          </el-form-item>
                            <el-form-item label="联系人：" style="margin-bottom: 0px;" prop="linkman">
                              <el-input v-model="company.linkman" readonly ></el-input>
                            </el-form-item>
                            <el-form-item label="联系方式：" style="margin-bottom: 0px;" prop="contactWay">
                              <el-input v-model="company.contactWay" readonly ></el-input>
                            </el-form-item>
                            <el-form-item label="传真：" style="margin-bottom: 0px;" prop="contactFax">
                              <el-input v-model="company.contactFax" readonly ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="10">
                          <el-form-item labelWidth="11%" style="margin-bottom: 0px;">
                            <img v-if="logoUrl" :src="logoUrl" style="width: 100%;max-height: 197px;border: 1px dashed lightgray;border-radius: 6px;">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                          </el-form-item>
                        </el-col>
                      </el-row>
                      <el-col :span="24">

                      </el-col>
                      <!--<el-col :span="24">-->
                        <!--<el-col :span="12">-->
                          <!--<el-form-item label="传真：" style="margin-bottom: 0px;" prop="contactFax">-->
                            <!--<el-input v-model="company.contactFax" readonly ></el-input>-->
                          <!--</el-form-item>-->
                        <!--</el-col>-->
                        <!--&lt;!&ndash;<el-col :span="12">&ndash;&gt;-->
                          <!--&lt;!&ndash;<el-form-item label="邮政编号：" labelWidth="100px" style="width: 86.2%;margin-bottom: 0px;" prop="postCode">&ndash;&gt;-->
                            <!--&lt;!&ndash;<el-input v-model="company.postCode" readonly ></el-input>&ndash;&gt;-->
                          <!--&lt;!&ndash;</el-form-item>&ndash;&gt;-->
                        <!--&lt;!&ndash;</el-col>&ndash;&gt;-->
                      <!--</el-col>-->
                      <el-col :span="24" style="margin-top: 2px;">
                        <el-form-item label="备注信息：" prop="remark" style="margin-bottom: 0px;">
                          <el-col :span="22">
                            <el-input type="textarea" :row="2" readonly v-model="company.remark" ></el-input>
                          </el-col>
                        </el-form-item>
                      </el-col>
                      <el-col :span="24">
                        <el-form-item label="企业资质：" style="margin-bottom: 0px;">
                        </el-form-item>
                      </el-col>
                      <el-col :span="24">
                        <div class="companyCertsClass">
                        <el-table :data="company.companyCertificateVos" highlight-current-row  style="width: 100%;" border fit>
                          <el-table-column prop="certKind" align="left" width="180" :formatter="formateCertType" label="证照类型">
                          </el-table-column>
                          <el-table-column prop="certCode" label="证照编码" min-width="150">
                          </el-table-column>
                          <el-table-column prop="expdtBeginDate" align="center" width="100" :formatter="formateDate" label="有效期自">
                          </el-table-column>
                          <el-table-column prop="expdtEndDate" align="center" width="100" :formatter="formateDate" label="有效期至">
                          </el-table-column>
                          <el-table-column prop="isValiding" align="center" width="80" :formatter="formateIsValiding" label="长期有效">
                          </el-table-column>
                          <el-table-column label="查看附件" width="120" align="center">
                            <template slot-scope="scope">
                              <template v-for="i in scope.row.basCompanyCertificateImages.length">
                                <a :href='imgBasePath+scope.row.basCompanyCertificateImages[i-1].filePath'
                                   style="text-decoration: none;" target="_blank">
                                  附件{{i}}&nbsp;</a>
                              </template>
                            </template>
                          </el-table-column>
                        </el-table>
                        </div>
                      </el-col>
                    </el-form>
                  </div>
                </div>
              </el-col>
            </template>
          </el-row>
          <div slot="footer" class="dialog-footer" style="text-align: right;margin-right: 88px;">
              <el-button type="success" @click="agreeNewProv" :loading="editLoading" >通过</el-button>
              <el-button type="warning" @click="disagreeNewProv" :loading="editLoading" >驳回</el-button>
              <!--<el-button type="primary" @click="editFormVisible = false" >取消</el-button>-->
          </div>
        </el-dialog>

      </el-col>
  </div>
</template>
<script>
    import { fileServer } from '../../common/js/constance';
    export default {
        data() {
            return {
                certKinds:[],

                currentRow:{},
                logoUrl:'',
                company:{},
                filters: {
                    provName: '',
                    provLxr: ''
                },
                provId: '',
                total: 0,
                page: 1,
                pageSize:20,
                loading: false,
                provHosInfos: [
                ],
                provHosInfoVo: {
                    isThreeInOne: '1',
                    flag: '1',
                    kind: '1'
                },
                modelTitle: "",
                editFormVisible: false,
                editLoading: false,
                nameErr: ''
            }
        },
        methods: {
            formateCertType (d1,d2,d3){
              return this.certKinds.find(item => item.val == d3).ename;
            },
            formateDate:function (d1,d2,d3) {
              if(d3==''||d3==null){
                return '';
              }
              return d3.substr(0,10);
            },
            formateIsValiding:function (d) {
              return d.isValiding=='1'?'是':'否';
            },

            //获取新供应商列表
            getProvHosInfoVoList: function (pIndex) {
                this.page = pIndex;
                var param = {
                    orderBy: "",
                    queryObject: {
                        provName: this.filters.provName,
                        provLxr: this.filters.provLxr
                    },
                    page: this.page,
                    rows: this.pageSize
                };
                this.loading = true;
                this.axios.post('/spdHDIService/provManager/newProvInfo/newProvInfoVoList', param).then(res => {
                    this.loading = false;
                    this.provHosInfos = res.data.data.data;
                    this.total = res.data.data.total;
                    // this.page = res.data.data.pageNum;
                }, err => {
                    this.loading = false;
                });
            },
            handleCurrentChange(val) {
                this.page = val;
                this.getProvHosInfoVoList(this.page);
            },
            handlePSizeChange(val) {
                this.pageSize = val;
                this.getProvHosInfoVoList(this.page);
            },
            newProvAudit(prov) {
                this.nameErr = '';
                this.modelTitle = '新供应商审核';
                this.editFormVisible = true;
                this.getProvCompany(prov);
            },
            // 获取供应商信息
            getProvCompany(prov){
                this.currentRow = prov;
                var param = {
                    orderBy: "",
                    queryObject: {
                        provId : prov.provId
                    },
                    page: this.pPageNum,
                    rows: this.pPageSize
                };
                this.axios.post('/spdHDIService/provManager/newProvInfo/getProvCompanyInfo', param).then(res => {
                    this.company = res.data.data;
                    this.logoUrl = this.imgBasePath + res.data.data.logo;
                }, err =>{
                })
            },
            /**
             * 新供应商审核通过
             */
            agreeNewProv: function () {
                var provHosInfoVo = this.currentRow;
                this.$confirm('确认通过吗?', '提示', {}).then(() => {
                    this.loading = true;
                    provHosInfoVo.status = '2';// 已通过
                    this.axios.post("/spdHDIService/myInfo/provHosInfo/setStatus", provHosInfoVo).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.loading = false;
                            this.$message({message: '操作成功！', type: 'success'});
                            this.editFormVisible = false;
                            this.getProvHosInfoVoList(this.page);
                        } else {
                            this.$message.error(res.data.msg);
                        }
                    }, err => {
                        this.loading = false;
                    });
                }).catch(() => {
                });
            },
            /**
             * 新供应商审核驳回
             */
            disagreeNewProv: function () {
                var provHosInfoVo = this.currentRow;
                this.$confirm('确认驳回吗?', '提示', {}).then(() => {
                    this.loading = true;
                    provHosInfoVo.status = '3';// 已通过
                    this.axios.post("/spdHDIService/myInfo/provHosInfo/setStatus", provHosInfoVo).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.loading = false;
                            this.$message({message: '操作成功！', type: 'success'});
                            this.editFormVisible = false;
                            this.getProvHosInfoVoList(this.page);
                        } else {
                            this.$message.error(res.data.msg);
                        }
                    }, err => {
                        this.loading = false;
                    });
                }).catch(() => {
                });
          },
          getZZLX: function () {   //证照类型 字典
            this.loading = true;
            this.axios.post('/platformService/sys/dict/getDictValueByDictEname', { dictName: 'ZZLX' }).then(res => {
              this.loading = false;
              if (res.data.code == "0") {
                this.certKinds = res.data.data;
              }
              else {
                this.$message.error(res.data.msg);
              }
            }, err => {
              this.loading = false;
            });
          }
        },
        mounted() {
            this.getZZLX();
            this.getProvHosInfoVoList(1);
            this.imgBasePath = fileServer.imgBasePath;
        }
    };

</script>
<style scoped>
  .image {width: 100%;  display: block;}
  .companyOutput .el-form-item {width: 75%;margin-bottom: 6px;}
  .companyOutput el-input {border: 0px}
  .companyOutput {padding-bottom: 3%;}
  .avatar {width: 124%;display: block;margin-left: -24%;}
  .newProv-tableheight {
    height: calc(100% - 90px);
  }

  #newProvQuery .el-col-16 {
    min-width: 630px;
  }
  #newProvQuery .el-col-10 {
    min-width: 424px;
  }
  #newProvQuery .el-col-9 {
    min-width: 416px;
  }
  #newProvQuery .el-col-7 {
    min-width: 324px;
  }
  #newProvQuery .el-col-6 {
    min-width: 206px;
  }
  #newProvQuery .el-form-item__content {
    line-height: 32px;
  }
  #newProv{
    height: 100%;
  }

  #newProvQuery .certsCard{
    margin-right: 6px;
    background-color: rgb(238, 241, 246);
    border: 1px solid darkgrey;
    border: 1px solid #d1dbe5;
    border-radius: 4px;
    overflow: hidden;
    box-shadow: 0 2px 4px 0 rgba(0,0,0,.12), 0 0 6px 0 rgba(0,0,0,.04);
  }
  .companyCertsClass{
    margin-left: 48px;
    margin-right: 74px;
  }
</style>
