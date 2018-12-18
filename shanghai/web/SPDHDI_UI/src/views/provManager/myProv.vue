<template>
  <div id="myProv">
    <!--工具条-->
    <el-col :span="24" style="height: 100%">
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
          <el-form :inline="true" :model="filters" ref="provInfoForm">
            <el-form-item label="供应商名称：" prop="provName">
              <el-input v-model="filters.provName" placeholder="供应商名称" ></el-input>
            </el-form-item>
            <el-form-item label="联系人：" prop="provLxr">
              <el-input v-model="filters.provLxr" placeholder="联系人" ></el-input>
            </el-form-item>
            <el-button-group>
              <el-button type="primary" v-on:click="getHosProvInfoVoList(1)" >查询</el-button>
              <el-button type="primary" v-on:click="resetProvInfo('provInfoForm')" >重置</el-button>
              <el-button type="success" @click="provHosInfoEdit(null)" >新增</el-button>
            </el-button-group>
          </el-form>
        </el-col>
        <!--列表-->
        <template>
          <el-table :data="provHosInfos" highlight-current-row v-loading="loading" style="width: 100%;" border fit  class="myProv-tableheight">
            <el-table-column type="index" label="序号" align="center" width="62"></el-table-column>
            <el-table-column prop="provName" label="供应商名称"  header-align="center"></el-table-column>
            <el-table-column prop="provLxr" label="联系人" header-align="center"></el-table-column>
            <el-table-column prop="provLxrTelphonenum" label="联系电话" align="center"></el-table-column>
            <el-table-column prop="lastUpdateDatetime" label="最后更新时间" align="center" sortable></el-table-column>
            <el-table-column label="操作" width="200" align="center">
              <template slot-scope="scope">
                <el-button-group>
                  <el-button size="mini" @click="provHosInfoEdit(scope.row)" type="primary" icon="edit">编辑</el-button>
                  <el-button size="mini" @click="queryProvInfo(scope.row)" type="info" icon="search">详情</el-button>
                  <el-button size="mini" @click="deleteSubmit(scope.row)"  type="warning" icon="warning" >解除关系</el-button>
                </el-button-group>
              </template>
            </el-table-column>
          </el-table>
          <!-- 工具条 -->
          <el-col :span="24" class="toolbar">
            <el-pagination v-if="total>0" small @size-change="handlePSizeChange" style="padding-right: 20px;text-align: right;"
                           @current-change="handleCurrentChange" :current-page="page"
                           layout="total, sizes, prev, pager, next" :page-sizes="[20, 30, 50, 100]" :page-size="pageSize" :total="total">
            </el-pagination>
          </el-col>
        </template>
        <!-- 编辑界面 -->
        <el-dialog :title="modelTitle"   close="onClose"  :visible.sync="editFormVisible" :close-on-click-modal="false" size="tiny">
          <el-form :model="provHosInfoVo" label-width="120px" ref="editForm" style="margin-top: 3%" class="el-form-item-nomsg">
            <el-row>
              <el-col>
              <el-form-item label="供应商名称：" prop="provName">
                <el-select v-model="provId" filterable placeholder="请选择" :clearable="true" :disabled="selhos" style="width: 70%;" @change="" >
                  <el-option
                          v-for="item in hosInfos"
                          :key="item.id"
                          :label="item.cname"
                          :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col>
                <el-form-item label="联系人：">
                  <el-input v-model="linkman" style="width: 70%;"  placeholder="请输入联系人"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col>
                <el-form-item label="联系电话：">
                  <el-input v-model="tel"  style="width: 70%;" placeholder="请输入联系电话"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col>
                <el-form-item label="备注：">
                  <el-input type="textarea" :row="3" style="width: 70%;" placeholder="请输入备注信息" v-model="provHosInfoVo.hosRemark" ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer myHos-button-align">
            <el-button @click.native="editFormVisible = false" >取消</el-button>
            <el-button type="primary" @click.native="editSubmit" :loading="editLoading" >提交</el-button>
          </div>
        </el-dialog>
        <!-- 详情界面 -->
        <el-dialog :title="queryProvTitle" top="10vh" width="75%" close="onClose" :visible.sync="queryProvVisible" :close-on-click-modal="false" >
          <el-row :gutter="2" style="margin-bottom: 20px;">
            <!--列表-->
            <template>
              <el-col :span="24">
                <div style="padding-left: 10px;height: 100%">
                  <div id="myProvQuery">
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
          <!--<div slot="footer" class="dialog-footer" style="text-align: center;">-->
            <!--<el-button type="primary" @click="queryProvVisible = false" >取消</el-button>-->
          <!--</div>-->
        </el-dialog>

      </el-col>
  </div>
</template>
<script>
    import { fileServer } from '../../common/js/constance';
    export default {
        data() {
            return {
                hosId:'',
                company:{},
                logoUrl:'',
                filters: {
                    provName: '',
                    provLxr: ''
                },
                selhos:false,
                provId: '',
                provName:'',
                total: 0,
                page: 1,
                pageSize:20,
                tel:'',
                linkman:'',
                userId:'',
                loading: false,
                provHosInfos:[],
                hosInfos:[],
                provHosInfoVo: {
                    isThreeInOne: '1',
                    flag: '1',
                    kind: '1'
                },
                modelTitle: "",
                queryProvTitle: "",
                editFormVisible: false,
                queryProvVisible: false,
                editLoading: false,
                certKinds:[]
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
            },

            // 医院获取供应商列表
            getHosProvInfoVoList: function (pIndex) {
                this.page = pIndex;
                var param = {
                    orderBy: "",
                    queryObject: {
                        hosId:this.hosId,
                        provName: this.filters.provName,
                        provLxr: this.filters.provLxr
                    },
                    page: this.page,
                    rows: this.pageSize
                };
                this.loading = true;
                this.axios.post('/spdHDIService/provManager/hosProvInfo/hosProvInfoVoList', param).then(res => {
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
                this.getHosProvInfoVoList(this.page);
            },
            handlePSizeChange(val) {
                this.pageSize = val;
                this.getHosProvInfoVoList(this.page);
            },
            /**
             * 重置供应商查询
             */
            resetProvInfo:function (formName) {
                this.$refs[formName].resetFields();
                this.getHosProvInfoVoList(1);
            },
            provHosInfoEdit(item) {
                this.getProvInfoVoList();
                if (item) {
                    this.provHosInfoVo = Object.assign({}, item);
                    this.modelTitle = "修改供应商";
                    var tempObj = {
                        cname:item.provName,
                        id:item.provId,
                        linkman:'',
                        tel:''
                    };
                    // 编辑供应商信息时，由于查询出的数组里没有当前供应商信息，需要手动入栈，便回显
                    this.hosInfos.push(tempObj);
                    this.provId = this.provHosInfoVo.provId;
                    this.linkman = this.provHosInfoVo.provLxr;
                    this.tel = this.provHosInfoVo.provLxrTelphonenum;
                    this.selhos=true;
                } else {
                    this.provHosInfoVo = {};
                    this.modelTitle = "添加供应商";
                    this.provId = '';
                    this.linkman = '';
                    this.tel = '';
                    this.selhos=false;
                }
                this.editFormVisible = true;
            },
            editSubmit: function () {
                this.editLoading = true;
                this.provHosInfoVo.hosId = this.hosId;
                this.provHosInfoVo.provId = this.provId;
                this.provHosInfoVo.provLxr = this.linkman;
                this.provHosInfoVo.provLxrTelphonenum = this.tel;
                this.provHosInfoVo.status = '2';
                this.provHosInfoVo.uid = this.userId;
                let serviceUrl = this.provHosInfoVo.id ? "/spdHDIService/provManager/hosProvInfo/update" : "/spdHDIService/provManager/hosProvInfo/insert";
                this.axios.post(serviceUrl, this.provHosInfoVo).then(res => {
                    this.editLoading = false;
                    if (res.data.code == "0") {
                        this.$message({ message: '操作成功！', type: 'success' });
                        this.editFormVisible = false;
                        this.getHosProvInfoVoList(this.page);
                    } else {
                        this.$message.error('操作失败！');
                    }
                }, err => {
                    this.editLoading = false;
                });
            },
            // 查看供应商
            queryProvInfo(prov) {
                this.queryProvTitle = '供应商查看';
                this.queryProvVisible = true;
                this.getProvCompany(prov);
            },
            // 获取供应商信息
            getProvCompany(prov){
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

            deleteSubmit: function (provHosInfoVo) {
                this.$confirm('确认解除关系吗?', '提示', {}).then(() => {
                    this.loading = true;
                    provHosInfoVo.status = '0';// 已解除
                    this.axios.post("/spdHDIService/provManager/hosProvInfo/setStatus", provHosInfoVo).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.loading = false;
                            this.$message({message: '操作成功！', type: 'success'});
                            this.getHosProvInfoVoList(this.page);
                        } else {
                            this.$message.error(res.data.msg);
                        }
                    }, err => {
                        this.loading = false;
                    });
                }).catch(() => {
                });
            },
            // 获取供应商下拉列表
            getProvInfoVoList: function () {
                var hosProvVo = { hosId:this.hosId };
                this.loading = true;
                this.axios.post(`/spdHDIService/provManager/hosProvInfo/getHosProvVoList`, hosProvVo).then(res => {
                    this.loading = false;
                    this.hosInfos = res.data.data;
                }, err => {
                    this.loading = false;
                });
            },
        },
        mounted() {
            this.getZZLX();
            this.hosName = this.user.corpName;
            this.hosId = this.user.corpId;
            this.userId = this.user.userId;
            this.imgBasePath = fileServer.imgBasePath;
            this.getHosProvInfoVoList(1);
        }
    };

</script>
<style scoped>
  .myProv-tableheight {
    height: calc(100% - 90px);
  }
  .myHos-button-align{
    text-align: center;
  }

  #myProvQuery .el-col-16 {
    min-width: 630px;
  }
  #myProvQuery .el-col-10 {
    min-width: 424px;
  }
  #myProvQuery .el-col-9 {
    min-width: 416px;
  }
  #myProvQuery .el-col-7 {
    min-width: 324px;
  }
  #myProvQuery .el-col-6 {
    min-width: 206px;
  }
  #myProvQuery .el-form-item__content {
    line-height: 30px;
  }

  #myProv{
    height: 100%;
  }
  .companyCertsClass{
    margin-left: 48px;
    margin-right: 74px;
  }



</style>
