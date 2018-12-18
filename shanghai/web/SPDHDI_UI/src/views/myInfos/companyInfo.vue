<template>
    <div id="companyInfo" style="height: 90%;">
        <el-row style="margin-bottom: 10%;height:100%;overflow:auto;">
            <el-col :span="24" style="min-width: 740px;height:100%;">
                <div>
                    <el-form :model="company" label-width="120px" :rules="editFormRules" ref="editForm"
                             class="myInfocompanyOutput">
                        <el-row>
                            <el-col :span="11">
                                <el-col :span="24">
                                    <el-form-item label="企业名称：" :error="errors.codeErr">
                                        <el-input v-model="company.cname" disabled style="border: 0px;"
                                                  ></el-input>
                                    </el-form-item>
                                </el-col>
                                <!--<el-col :span="24">-->
                                    <!--<el-form-item label="企业编号：" prop="code" :error="errors.codeErr">-->
                                        <!--<el-input v-model="company.code" :readonly="editDisable" ></el-input>-->
                                    <!--</el-form-item>-->
                                <!--</el-col>-->
                                <el-col :span="24">
                                    <el-form-item label="公司别名：" prop="anotherName" :error="errors.codeErr">
                                        <el-input v-model="company.anotherName" :readonly="editDisable" ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="24">
                                    <el-form-item label="法人代表：" prop="legal" :error="errors.codeErr">
                                        <el-input v-model="company.legal" :readonly="editDisable" ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="24">
                                    <el-form-item label="公司地址：" prop="address" :error="errors.codeErr">
                                        <el-input v-model="company.address" :readonly="editDisable" ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="24">
                                    <el-form-item label="电子邮箱：" prop="email" :error="errors.codeErr">
                                        <el-input v-model="company.email" :readonly="editDisable" ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="24">
                                  <el-form-item label="联系人：" prop="linkman" :error="errors.codeErr">
                                    <el-input v-model="company.linkman" :readonly="editDisable" ></el-input>
                                  </el-form-item>
                                </el-col>
                                <el-col :span="24">
                                  <el-form-item label="联系电话：" prop="contactWay" :error="errors.codeErr">
                                    <el-input v-model="company.contactWay" :readonly="editDisable" ></el-input>
                                  </el-form-item>
                                </el-col>
                                <el-col :span="24">
                                  <el-form-item label="传真：" prop="contactFax" :error="errors.codeErr">
                                    <el-input v-model="company.contactFax" :readonly="editDisable" ></el-input>
                                  </el-form-item>
                                </el-col>
                            </el-col>
                            <el-col :span="13">
                                <el-col :span="15" style="margin-left: 17%;">
                                        <el-upload
                                                class="avatar-uploader"
                                                :action="uploadLogoUrl"
                                                :show-file-list="false"
                                                :headers="userInfo"
                                                :on-success="handleAvatarSuccess"
                                                :before-upload="beforeAvatarUpload">
                                            <img v-if="logoUrl" :src="logoUrl" style="border: 1px dashed #d9d9d9;border-radius: 6px;width: 95%;display: block;">
                                            <i v-else class="el-icon-plus logoImgStyle"></i>
                                        </el-upload>
                                </el-col>
                            </el-col>
                        </el-row>
                        <!--<el-row>-->
                            <!--<el-col :span="11">-->
                                <!--<el-form-item label="联系人：" prop="linkman" :error="errors.codeErr">-->
                                    <!--<el-input v-model="company.linkman" :readonly="editDisable" ></el-input>-->
                                <!--</el-form-item>-->
                            <!--</el-col>-->
                            <!--<el-col :span="10">-->
                                <!--<el-form-item label="电话：" prop="contactWay" :error="errors.codeErr">-->
                                    <!--<el-input v-model="company.contactWay" :readonly="editDisable" ></el-input>-->
                                <!--</el-form-item>-->
                            <!--</el-col>-->
                            <!--<el-col :span="11">-->
                                <!--<el-form-item label="邮政编码：" prop="postCode" :error="errors.codeErr">-->
                                    <!--<el-input v-model="company.postCode" :readonly="editDisable" ></el-input>-->
                                <!--</el-form-item>-->
                            <!--</el-col>-->
                            <!--<el-col :span="10">-->
                                <!--<el-form-item label="传真：" prop="contactFax" :error="errors.codeErr">-->
                                    <!--<el-input v-model="company.contactFax" :readonly="editDisable" ></el-input>-->
                                <!--</el-form-item>-->
                            <!--</el-col>-->
                        <!--</el-row>-->
                        <el-col :span="21">
                            <el-form-item label="备注信息：" prop="remark">
                                <el-input type="textarea" :row="2" :readonly="editDisable" v-model="company.remark"
                                          ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="21">
                            <el-form-item label="企业资质：" style="text-align: left;">
                              <el-button type="text" style="margin-left: -5px;" @click="addProvCerts">新增资质</el-button>
                              <el-col>
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
                                  <el-table-column label="操作" width="120" align="center">
                                    <template slot-scope="scope">
                                      <el-button  @click="updateProvCerts(scope.row)" type="text">编辑</el-button>
                                      <el-button  @click="deleteProvCerts(scope.row)" style="color: #d89034;margin-left: 3px;" type="text">删除</el-button>
                                    </template>
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
                              </el-col>
                            </el-form-item>
                          <el-col :span="24">
                            <el-button style="margin-left: 51%;margin-top: 10px;" :loading="editLoading" @click="handleBtnEditClick"  type="primary">
                              {{editDisable ? '编辑' : '提交'}}
                            </el-button>
                            <el-button style="margin-top: 10px;" v-if="editDisable === false" @click="handleBtnCancleClick"  type="warning">取消
                            </el-button>
                          </el-col>
                        </el-col>
                    </el-form>
                </div>
            </el-col>
        </el-row>

      <el-dialog title="添加证照信息" :visible.sync="dlgVisible" >
        <el-form :model="companyCerts" ref="cnsForm" label-width="100px"
                 class="el-form-item-nomsg">
          <el-form-item label="证照类型" prop="certKind">
            <el-select v-model="companyCerts.certKind" placeholder="请选择" clearable @change="provCertSelect">
              <el-option v-for="item in certKinds" :key="item.val" :label="item.ename" :value="item.val">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="证照编码" prop="certCode">
            <el-input v-model="companyCerts.certCode" style="width: 97.5%;"></el-input>
          </el-form-item>
          <el-form-item label="证照有效期">
            <el-col :span="6">
              <el-form-item prop="expdtBeginDate">
                <el-date-picker type="date" placeholder="开始日期" v-model="companyCerts.expdtBeginDate"
                                style="width: 100%;" ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col style="text-align:center;" :span="1">-</el-col>
            <el-col :span="6">
              <el-form-item prop="expdtEndDate">
                <el-date-picker type="date" placeholder="结束日期" v-model="companyCerts.expdtEndDate"
                                style="width: 100%;" ></el-date-picker>
              </el-form-item>
            </el-col>
          </el-form-item>
          <el-form-item label="是否长期有效">
            <el-radio-group v-model="companyCerts.isValiding" @change="isValidingSelect" size="mini">
              <el-radio-button label="1" border>是</el-radio-button>
              <el-radio-button label="0" border>否</el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div class="uploadCertClass">
          <el-upload
            ref="provCertsUpload"
            accept=".bmp,.jpg,.png,.pdf"
            :headers="userInfo"
            :multiple="true"
            :action="uploadCompanyCertsUrl"
            :file-list="fileList"
            :on-preview="checkCerts"
            :on-success="handleProvCertsSuccess"
            :auto-upload="false">
            <el-button slot="trigger" size="mini" type="primary">上传附件</el-button>
            <div slot="tip" class="el-upload__tip">注：只能上传jpg/png/bmp/pdf文件，且不超过15MB</div>
          </el-upload>
        </div>
        <span slot="footer" class="dialog-footer">
              <el-button @click="dlgVisible=false" >取消</el-button>
              <el-button type="primary" @click="_dlgConfirm" :loading="dlgConfirmBtnLoading">保存</el-button>
          </span>
      </el-dialog>

      <el-dialog title="修改证照信息" :visible.sync="updateCertVisible" >
        <el-form :model="companyCerts" ref="updateCertsForm" label-width="100px"
                 class="el-form-item-nomsg">
          <el-form-item label="证照类型" prop="certKind">
            <el-select v-model="companyCerts.certKind" placeholder="请选择" clearable @change="provCertSelect">
              <el-option v-for="item in certKinds" :key="item.val" :label="item.ename" :value="item.val">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="证照编码" prop="certCode">
            <el-input v-model="companyCerts.certCode" style="width: 97.5%;"></el-input>
          </el-form-item>
          <el-form-item label="证照有效期">
            <el-col :span="6">
              <el-form-item prop="expdtBeginDate">
                <el-date-picker type="date" placeholder="开始日期" v-model="companyCerts.expdtBeginDate"
                                style="width: 100%;" ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col style="text-align:center;" :span="1">-</el-col>
            <el-col :span="6">
              <el-form-item prop="expdtEndDate">
                <el-date-picker type="date" placeholder="结束日期" v-model="companyCerts.expdtEndDate"
                                style="width: 100%;" ></el-date-picker>
              </el-form-item>
            </el-col>
          </el-form-item>
          <el-form-item label="是否长期有效">
            <el-radio-group v-model="companyCerts.isValiding" @change="isValidingSelect" size="mini">
              <el-radio-button label="1" border>是</el-radio-button>
              <el-radio-button label="0" border>否</el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div class="uploadCertClass">
          <el-upload
            accept=".bmp,.jpg,.png,.pdf"
            :headers="userInfo"
            :multiple="true"
            :action="uploadCompanyCertsUrl"
            :file-list="fileList"
            :on-preview="checkCerts"
            :on-success="handleUpdateProvCertsSuccess"
            :on-remove="deleteCert"
            :auto-upload="true">
            <el-button slot="trigger" size="mini" type="primary">上传附件</el-button>
            <div slot="tip" class="el-upload__tip">注：只能上传jpg/png/bmp/pdf文件，且不超过15MB</div>
          </el-upload>
        </div>
        <span slot="footer" class="dialog-footer">
              <el-button @click="updateCertVisible=false" >取消</el-button>
              <el-button type="primary" @click="updateCerts" :loading="dlgConfirmBtnLoading">保存</el-button>
      </span>
      </el-dialog>
    </div>
</template>
<script>
    import {fileServer} from '../../common/js/constance';
    import { PROV_CERT_TYPE } from '../../common/js/constance';
    export default {
        data() {
          let validateBeginDate = (rule, value, callback) => {
            if (!value) {
              return callback(new Error('开始日期不能为空'))
            } else {
              if (this.companyCerts.expdtEndDate) {
                this.$refs.cnsForm.validateField('expdtEndDate');
              }
              callback();
            }
          };
          let validateEndDate = (rule, value, callback) => {
            if (!value) {
              return callback(new Error('结束日期不能为空'))
            } else {
              if (Date.parse(this.companyCerts.expdtBeginDate) > Date.parse(this.companyCerts.expdtEndDate)) {
                return callback(new Error('结束日期必须大于开始日期'))
              }
              callback();
            }
          };
            return {
                uploadLogoUrl: '',
                imgBasePath: '',
                certKind: '',
                logoUrl: '',
                loading: false,
                company: {
                    cname: '',
                    code: '',
                    companyCertificateVos: [],
                },
                editFormVisible: false,
                editFormVisible1: false,
                editLoading: false,
                editCertDateLoading: false,
                editFormRules: {},
                errors: {
                    codeErr: '',
                    cnameErr: ''
                },
                editDisable: true,
                userInfo:{},
                corpId:'',
                companyCerts:{
                  companyId:'',
                  isValiding:0,
                  basCompanyCertificateImages:[]
                },
                dlgVisible:false,
                dlgAddMode:false,
                certKinds: [],
                dlgConfirmBtnLoading:false,
                updateCertVisible:false,
                uploadCompanyCertsUrl: '',
                fileList:[],
                fileUploadCount:0,
                imageList:[]
            }
        },
        methods: {
          checkCerts:function (file) {
            window.open(fileServer.imgBasePath + file.url);
          },
          updateCerts () {
            this.dlgConfirmBtnLoading = true;
            let serverUrl = "/spdHDIService/myInfo/companyInfo/updateProvCerts";
            this.companyCerts.companyId = this.corpId;
            delete this.companyCerts.basCompanyCertificateImages;
            this.axios.post(serverUrl, this.companyCerts).then(res => {
              if (res.data.code >= 0) {
                this.$message.success('操作成功。');
                this.dlgConfirmBtnLoading = false;
                this.updateCertVisible = false;
              } else {
                this.$message.error('操作失败，' + res.data.msg.substring(0, 50) + '...');
              }
              this.getCompanyInfo(this.corpId);
            }).catch(err => {
              this.$message.error('数据异常，请联系管理员！');
            })
            this.dlgConfirmBtnLoading = false;
          },
          handleProvCertsSuccess(response, file, fileList) {
            this.fileUploadCount += 1;
            var id = file.response.data[0].id;
            var ext = file.response.data[0].ext;
            var path = file.response.data[0].path;
            var name = file.response.data[0].name;
            var certPath = path + "/" + id + "." + ext;
            this.companyCerts.basCompanyCertificateImages.push({fileName:name,filePath:certPath});
            if(this.fileUploadCount==fileList.length){// 待所有附件上传完成了再一起保存
              this.dlgConfirm();
            }
          },
          handleUpdateProvCertsSuccess(response, file, fileList) {
            var id = file.response.data[0].id;
            var ext = file.response.data[0].ext;
            var path = file.response.data[0].path;
            var name = file.response.data[0].name;
            var certPath = path + "/" + id + "." + ext;
            var imageObj = {
              certificateId:this.companyCerts.id,
              fileName:name,
              filePath:certPath
            };
            this.addImage(imageObj);
          },

          addImage (imageObj) {
            let serverUrl = "/spdHDIService/myInfo/companyInfo/addImage";
            this.axios.post(serverUrl, imageObj).then(res => {
              if (res.data.code >= 0) {
                this.$message.success('操作成功。');
              } else {
                this.$message.error('操作失败，' + res.data.msg.substring(0, 50) + '...');
              }
              this.getCompanyInfo(this.corpId);
            }).catch(err => {
              this.$message.error('数据异常，请联系管理员！');
            })
          },

          updateProvCerts:function (d1) {
            this.fileList = [];
            this.companyCerts = d1;
            var count = 0;
            d1.basCompanyCertificateImages.forEach(item=>{
              if(item.fileName == null || item.fileName == ''){
                this.fileList.push({name:item.filePath,url:item.filePath,id:item.id});
              }else {
                this.fileList.push({name:item.fileName,url:item.filePath,id:item.id});
              }
            });
            this.updateCertVisible = true;
          },

          isValidingSelect:function (d) {
              this.companyCerts.isValiding = d;
          },
          provCertSelect:function (d) {
              this.companyCerts.certKind = d;
          },
          addProvCerts:function () {
            this.companyCerts = {
              companyId:'',
              isValiding:0,
              basCompanyCertificateImages:[]
            },
              this.dlgAddMode = true;
            this.dlgVisible = true;
            this.fileList = [];
          },

          deleteProvCerts:function (row) {
            this.axios.post('/spdHDIService/myInfo/companyInfo/deleteProvCerts', row).then(res => {
              if (res.data.code == "0") {
                this.getCompanyInfo(this.corpId);
                this.$message({message: '操作成功！', type: 'success'});
              }else{
                this.$message.error(res.data.msg);
              }
            }, err => {
              this.$message.error('数据异常，请联系管理员！')
            });
          },

          deleteCert:function (file, fileList) {
            if(file.id==null||file.id==''){
              return;
            }
            this.axios.post('/spdHDIService/myInfo/companyInfo/deleteImage', {id:file.id}).then(res => {
              if (res.data.code == "0") {
                this.getCompanyInfo(this.corpId);
                this.$message({message: '操作成功！', type: 'success'});
              }else{
                this.$message.error(res.data.msg);
              }
            }, err => {
              this.$message.error('数据异常，请联系管理员！')
            });
          },

          _dlgConfirm:function(){
              this.$refs.provCertsUpload.submit();
          },

          dlgConfirm () {
            this.$refs.cnsForm.validate((valid) => {
              if (valid) {
                this.dlgConfirmBtnLoading = true;
                let serverUrl = "/spdHDIService/myInfo/companyInfo/addProvCerts";
                this.companyCerts.companyId = this.corpId;
                this.axios.post(serverUrl, this.companyCerts).then(res => {
                  if (res.data.code >= 0) {
                    this.$message.success('操作成功。');
                    this.dlgConfirmBtnLoading = false;
                    this.dlgVisible = false;
                  } else {
                    this.$message.error('操作失败，' + res.data.msg.substring(0, 50) + '...');
                  }
                  this.fileUploadCount = 0;
                  this.getCompanyInfo(this.corpId);
                }).catch(err => {
                  this.fileUploadCount = 0;
                  this.dlgConfirmBtnLoading = false;
                  this.$message.error('数据异常，请联系管理员！');
                })
              } else {
                this.$message.error('数据验证失败，请更正！');
              }
            })
          },

          formateIsValiding:function (d) {
            return d.isValiding=='1'?'是':'否';
          },

          formateCertType (d1,d2,d3){
            return this.certKinds.find(item => item.val == d3).ename;
          },
          formateDate:function (d1,d2,d3) {
            if(d3==''||d3==null){
              return '';
            }
            return d3.substr(0,10);
          },

            handleBtnCancleClick () {
                this.editDisable = true;
            },
            // 点击编辑后的"提交"按钮
            handleBtnEditClick () {
              if (!this.editDisable) {
                  this.updateCompanyInfo(function () {});
              } else {
                  this.editDisable = !this.editDisable;
              }
            },
            updateLogo: function () {
                this.editFormVisible1 = true;
            },
            //获取企业信息
            getCompanyInfo: function (corpId) {
              this.loading = true;
              var _this = this;
              this.axios.post('/spdHDIService/myInfo/companyInfo/getCompanyInfo', corpId).then(res => {
                  this.loading = false;
                  if (res.data.code == "0") {
                      this.company = res.data.data;
                      _this.logoUrl = _this.imgBasePath + res.data.data.logo;
                  } else {
                      _this.$message.error(res.data.msg);
                  }
              }, err => {
                  _this.loading = false;
              });
            },

            // 更新企业信息
            updateCompanyInfo: function (callback) {
                this.editLoading = true;
                let serviceUrl = "/spdHDIService/myInfo/companyInfo/updateCompanyCertificate";
                this.axios.post(serviceUrl, this.company).then(res => {
                    this.editLoading = false;
                    if (res.data.code == "0") {
                        this.$message({message: '操作成功！', type: 'success'});
                        this.editFormVisible = false;
                        this.getCompanyInfo(this.corpId);
                        this.editDisable = !this.editDisable;
                    } else {
                        this.$message.error('操作失败！');
                    }
                }, err => {
                    this.editLoading = false;
                });
            },
            // 上传后LOGO后，更新logoPath信息
            handleAvatarSuccess(res, file) {
                var id = file.response.data[0].id;
                var ext = file.response.data[0].ext;
                var path = file.response.data[0].path;
                var logoPath = path + "/" + id + "." + ext;
                this.logoUrl = this.imgBasePath + logoPath;
                this.savePathInfo(file,logoPath);
            },
            // 更新LOGO信息
            savePathInfo(file,logoPath){
                this.company.logo = logoPath;
                var param = this.company;
                this.loading = true;
                this.axios.post('/spdHDIService/myInfo/companyInfo/updateLogo', param).then(res => {
                    this.loading = false;

                    if (res.data.code == "0") {
                        this.$message({message: '操作成功！', type: 'success'});
                        this.logoUrl = this.imgBasePath + logoPath;
                        this.getCompanyInfo();
                    } else {
                        this.$message.error(res.data.msg);
                    }
                }, err => {
                    this.loading = false;
                });
            },
            // 上传证件前作校验
            beforeAvatarUpload(file) {
                const isLt2M = file.size / 1024 / 1024 < 20;
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 20MB!');
                }
                return isLt2M;
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
            // this.certKinds = PROV_CERT_TYPE;
            this.getZZLX();
            this.corpId = this.user.corpId;
            this.getCompanyInfo(this.corpId);
            this.uploadLogoUrl = fileServer.upload + "logo";
            this.uploadCompanyCertsUrl = fileServer.upload + "companyCert";
            this.imgBasePath = fileServer.imgBasePath;
            this.userInfo = {'X-US':this.user.xus};
        }
    };

</script>
<style scoped>
    .myInfocompanyOutput .el-form-item {
        margin-bottom: 2px;
    }
    .uploadCertClass{
      border-radius: 4px;
      border: 1px solid #dcdfe6;
      margin: 15px;
      padding: 10px;
    }
</style>
