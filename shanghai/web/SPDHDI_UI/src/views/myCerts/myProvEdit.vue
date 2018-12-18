<template>
  <div id="myProvEdit" style="height: 100%;overflow:auto;">
    <el-row style="max-width: 960px;margin:0 auto;">
      <el-col :span="24">
        <div>
          <fieldset style="border: 1px solid rgb(97, 192, 254);;margin-bottom: 5px;background-color: #e0ebef38;">
            <legend style="font-weight: bolder;color: #2f8bc7">基本信息</legend>
              <el-form :model="company" label-width="100px" :rules="editFormRules" ref="editForm"
                       class="myInfocompanyOutput">
                <el-row>
                  <el-col :span="16">
                    <el-form-item label="企业名称" :error="errors.codeErr">
                      <el-input v-model="company.cname" :readonly="editDisable" style="border: 0px;"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col  :span="8">
                    <el-form-item label="法人代表" prop="legal" :error="errors.codeErr">
                      <el-input v-model="company.legal" :readonly="editDisable" ></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                    <el-col :span="8">
                      <el-form-item label="我是厂家" :error="errors.codeErr">
                        <el-checkbox v-model="ifMfrs" disabled></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8" >
                      <el-form-item label="厂家类型" label-width="99px" prop="mfrsKind" :error="errors.codeErr">
                        <template>
                          <el-radio-group disabled v-model="company.mfrsKind" size="mini">
                            <el-radio label="1" border>境内</el-radio>
                            <el-radio label="2" border>境外</el-radio>
                          </el-radio-group>
                        </template>
                      </el-form-item>
                    </el-col>
                  <el-col :span="8">
                    <el-form-item label="联系人" prop="linkman" :error="errors.codeErr">
                      <el-input v-model="company.linkman" :readonly="editDisable" ></el-input>
                    </el-form-item>
                  </el-col>

                </el-row>
                <el-row>
                    <el-col :span="16">
                        <el-form-item label="公司地址" prop="address" :error="errors.codeErr">
                          <el-input v-model="company.address" :readonly="editDisable" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item label="联系方式" prop="contactWay" :error="errors.codeErr">
                        <el-input v-model="company.contactWay" :readonly="editDisable" ></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item label="邮政编码" prop="postCode" :error="errors.codeErr">
                        <el-input v-model="company.postCode" :readonly="editDisable" ></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item label="传真" prop="contactFax" :error="errors.codeErr">
                        <el-input v-model="company.contactFax" :readonly="editDisable" ></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item label="电子邮箱" prop="email" :error="errors.codeErr">
                        <el-input v-model="company.email" :readonly="editDisable" ></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item label="经营许可证" prop="jyxkCode" :error="errors.codeErr">
                        <el-input v-model="company.jyxkCode" :readonly="editDisable" ></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item label="营业执照" prop="regCode" :error="errors.codeErr">
                        <el-input v-model="company.regCode" :readonly="editDisable" ></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item label="内部erp编码" prop="erpCode" :error="errors.codeErr">
                        <el-input v-model="company.erpCode" :readonly="editDisable" ></el-input>
                      </el-form-item>
                    </el-col>
                </el-row>
                <el-col :span="16">
                  <el-form-item label="备注信息" prop="remark">
                    <el-input :readonly="editDisable" v-model="company.remark"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="主数据编码" prop="masterCode" :error="errors.codeErr">
                    <el-input v-model="company.masterCode" :readonly="editDisable" ></el-input>
                  </el-form-item>
                </el-col>
              </el-form>
              <el-col :span="24">
                <el-col :span="20">
                  &nbsp;
                </el-col>
                <el-col :span="4" style="text-align: right;margin-left: -55px;">
                  <el-button type="primary" size="mini" @click="cancel" plain icon="el-icon-back">返回</el-button>
                </el-col>
              </el-col>
          </fieldset>
        </div>
      </el-col>
    </el-row>
    <el-row style="max-width: 960px;margin:0 auto">
      <el-col :span="24">
        <el-button type="text" plain @click="addProvCerts" :disabled="!company.id">新增证照</el-button>
        <el-col>
          <el-table :data="company.collectorProvCertificates" highlight-current-row  style="width: 100%;" border fit>
            <el-table-column prop="certKind" align="left" width="180" :formatter="formateCertType" label="证照类型">
            </el-table-column>
            <el-table-column prop="certCode" label="证照编码">
            </el-table-column>
            <el-table-column header-align="center" width="150" :formatter="formateDate" label="效期">
              <template slot-scope="scope">
                <div>
                  起始日期：{{scope.row.expdtBeginDate?scope.row.expdtBeginDate.substr(0,10):''}}
                </div>
                <div style="color: #57b547" v-if="scope.row.isValiding==='1'">
                  截止日期：{{scope.row.isValiding==='1'?'长期有效':(scope.row.expdtEndDate?scope.row.expdtEndDate.substr(0,10):'')}}
                </div>
                <div :class="expdtDateClass(scope.row.expdtEndDate)" v-else>
                  截止日期：{{scope.row.isValiding==='1'?'长期有效':(scope.row.expdtEndDate?scope.row.expdtEndDate.substr(0,10):'')}}
                </div>
              </template>
            </el-table-column>
            <el-table-column label="证照附件" width="180" align="center">
              <template slot-scope="scope">
                <template v-for="i in scope.row.provCertificateImage.length">
                  <el-col :span="12">
                    <el-row>
                      <a :href='imgBasePath+scope.row.provCertificateImage[i-1].filePath'
                         style="text-decoration: none;" target="_blank">
                        <el-tag type="danger" color="#ccb100" size="small" v-if="scope.row.certKind=='1'"
                                style="color: white;font-weight: bold" class="box_shadow_class">营业执照
                        </el-tag>
                        <el-tag type="success" color="#99cc11" size="small" v-if="scope.row.certKind=='3'"
                                style="font-weight: bold;color: white" class="box_shadow_class">税务登记
                        </el-tag>
                        <el-tag type="success" color="#78be9c" size="small" v-if="scope.row.certKind=='4'"
                                style="font-weight: bold;color: white" class="box_shadow_class">机构代码
                        </el-tag>
                        <el-tag type="success" color="#3e6ff2" size="small" v-if="scope.row.certKind=='5'"
                                style="font-weight: bold;color: white" class="box_shadow_class">经营许可
                        </el-tag>
                        <el-tag type="success" color="#3369a4" size="small" v-if="scope.row.certKind=='6'"
                                style="font-weight: bold;color: white" class="box_shadow_class">经营备案
                        </el-tag>
                        <el-tag type="success" color="#e96867" size="small" v-if="scope.row.certKind=='7'"
                                style="font-weight: bold;color: white" class="box_shadow_class">生产许可
                        </el-tag>
                        <el-tag type="success" color="#b72655" size="small" v-if="scope.row.certKind=='8'"
                                style="font-weight: bold;color: white" class="box_shadow_class">生产备案
                        </el-tag>
                      </a>
                    </el-row>
                  </el-col>
                </template>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="90" align="center">
              <template slot-scope="scope">
                <el-button  @click="updateProvCerts(scope.row)" type="text">编辑</el-button>
                <el-button  @click="deleteProvCerts(scope.row)" style="color: #f56c6c;" type="text">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
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
          :on-change="certChang"
          :on-preview="checkCerts"
          :on-success="handleProvCertsSuccess"
          :auto-upload="false">
          <el-button slot="trigger" size="mini" type="primary">上传附件</el-button>
          <div slot="tip" class="el-upload__tip">注：只能上传jpg/png/bmp/pdf文件，且不超过15MB</div>
        </el-upload>
      </div>
      <span slot="footer" class="dialog-footer">
              <el-button @click="dlgVisible=false" >返回</el-button>
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
              <el-button @click="updateCertVisible=false" >返回</el-button>
              <el-button type="primary" @click="updateCerts" :loading="dlgConfirmBtnLoading">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import moment from "moment";
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
        cname:'',
        ifMfrs:false,
        imgBasePath: '',
        certKind: '',
        logoUrl: '',
        loading: false,
        company: {
          mfrsKind:'',
          kind:'2',
          cname: '',
          code: '',
          collectorProvCertificates: [],
        },
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
          pid:'',
          isValiding:0,
          provCertificateImage:[]
        },
        tempProvCertificateImage:[],
        dlgVisible:false,
        certKinds: [],
        allCertKinds: [],
        dlgConfirmBtnLoading:false,
        updateCertVisible:false,
        uploadCompanyCertsUrl: '',
        fileList:[],
        fileUploadCount:0,
        imageList:[],
        submitEnable:false
      }
    },
    methods: {
      expdtDateClass (date) {
        let endDate = Date.parse(date);
        let today = Date.parse(new Date());
        let days = 30 * 24 * 60 * 60 * 1000;
        let expiring = false;
        let expired = false;
        if (endDate - today < 0) {
          expired = true;
        } else if (endDate - today < days) {
          expiring = true;
        }
        return {
          'expiring': expiring,
          'expired': expired
        }
      },
      cancel:function () {
        this.$router.push({path: 'myProv',query:{cname:this.cname}});
      },
      checkCerts:function (file) {
        window.open(fileServer.imgBasePath + file.url);
      },
      updateCerts () {
        this.dlgConfirmBtnLoading = true;
        let tempCert = this.companyCerts;
        let serverUrl = "/spdHDIService/myCerts/myProv/updateProvCerts";
        tempCert.pid = this.company.id;
        this.tempProvCertificateImage = [].concat(tempCert.provCertificateImage);
        delete tempCert.hcpId;
        delete tempCert.provCertificateImage;
        this.axios.post(serverUrl, tempCert).then(res => {
          if (res.data.code >= 0) {
            this.$message.success('操作成功。');
            tempCert.provCertificateImage = this.tempProvCertificateImage;
            this.dlgConfirmBtnLoading = false;
            this.updateCertVisible = false;
          } else {
            this.$message.error('操作失败，' + res.data.msg.substring(0, 50) + '...');
          }
          // this.getCompanyInfo(this.corpId);
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
        this.companyCerts.provCertificateImage.push({filePath:certPath});
        if(this.fileUploadCount==fileList.length){// 待所有附件上传完成了再一起保存
          this.dlgConfirm();
        }
      },
      handleUpdateProvCertsSuccess(response, file, fileList) {
        var id = file.response.data[0].id;
        var ext = file.response.data[0].ext;
        var path = file.response.data[0].path;
        var certPath = path + "/" + id + "." + ext;
        var imageObj = {
          certificateId:this.companyCerts.id,
          filePath:certPath
        };
        this.addImage(imageObj);
      },
      addImage (imageObj) {
        let serverUrl = "/spdHDIService/myCerts/myProv/addImage";
        this.axios.post(serverUrl, imageObj).then(res => {
          if (res.data.code >= 0) {
            this.$message.success('操作成功。');
          } else {
            this.$message.error('操作失败，' + res.data.msg.substring(0, 50) + '...');
          }
          // this.getCompanyInfo(this.corpId);
        }).catch(err => {
          this.$message.error('数据异常，请联系管理员！');
        })
      },
      updateProvCerts:function (d1) {
        this.fileList = [];
        this.companyCerts = d1;
        var count = 0;
        d1.provCertificateImage.forEach(item=>{
          this.fileList.push({name:item.filePath,url:item.filePath,id:item.id});
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
          pid:'',
          isValiding:0,
          provCertificateImage:[]
        },
        this.dlgVisible = true;
        this.fileList = [];
      },
      deleteProvCerts:function (row) {
        this.$confirm('确认删除吗?', '提示', {}).then(() => {
          this.axios.post('/spdHDIService/myCerts/myProv/deleteProvCerts', row).then(res => {
            if (res.data.code == "0") {
              this.$message({message: '操作成功！', type: 'success'});
              let tempArray = this.company.collectorProvCertificates;
              tempArray.splice(tempArray.findIndex(o => o.id === row.id), 1);
            }else{
              this.$message.error(res.data.msg);
            }
          }, err => {
            this.$message.error('数据异常，请联系管理员！')
          });
        }).catch(() => {
        });
      },
      deleteCert:function (file, fileList) {
        if(file.id==null||file.id==''){
          return;
        }
        this.axios.post('/spdHDIService/myCerts/myProv/deleteImage', {id:file.id}).then(res => {
          if (res.data.code == "0") {
            this.$message({message: '操作成功！', type: 'success'});
            this.fileList = fileList;
            let images = this.companyCerts.provCertificateImage;
            images.splice(images.findIndex(o => o.id === file.id), 1);
          }else{
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.$message.error('数据异常，请联系管理员！')
        });
      },
      certChang:function (file,fileList) {
        if(fileList.length>0){
          this.submitEnable = true;
        }else{
          this.submitEnable = false;
        }
      },
      _dlgConfirm:function(){
        if(!this.submitEnable){
          this.$message({message: '请上传附件！', type: 'warning', duration:1500, showClose:true});
          return;
        }
        this.$refs.provCertsUpload.submit();
      },
      dlgConfirm () {
        this.$refs.cnsForm.validate((valid) => {
          if (valid) {
            this.dlgConfirmBtnLoading = true;
            let serverUrl = "/spdHDIService/myCerts/myProv/addMyProvCerts";
            this.companyCerts.pid = this.company.id;
            this.companyCerts.provId = this.company.provId;
            this.axios.post(serverUrl, this.companyCerts).then(res => {
              if (res.data.code >= 0) {
                this.$message.success('操作成功。');
                this.company.collectorProvCertificates.push(res.data.data);
                this.dlgConfirmBtnLoading = false;
                this.dlgVisible = false;
              } else {
                this.$message.error('操作失败，' + res.data.msg.substring(0, 50) + '...');
              }
              this.submitEnable = false;
              this.fileUploadCount = 0;
              // this.getCompanyInfo(this.corpId);
            }).catch(err => {
              this.submitEnable = false;
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
        // let obj = this.certKinds.find(item => item.val == d3);
        let obj = this.allCertKinds.find(item => item.val == d3);
        return obj?obj.ename:d3;
      },
      formateDate:function (d1,d2,d3) {
        if(d3==''||d3==null){
          return '';
        }
        return d3.substr(0,10);
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
        let that = this;
        that.axios.post('/platformService/sys/dict/getDictValueByDictEname', { dictName: 'ZZLX' }).then(res => {
          that.loading = false;
          if (res.data.code == "0") {
            // this.certKinds = res.data.data;
            this.allCertKinds = res.data.data;
            let tempArray = [];
            if(this.company.kind=='2'){// 如果机构类型是'3-厂商'，则取厂商证照类型字典
              this.allCertKinds.forEach(item=>{
                if(item.remark=='供应商证照'){
                  tempArray.push(item);
                }
              });
            }else {
              this.allCertKinds.forEach(item=>{
                if(item.remark=='厂商证照'||item.remark=='供应商证照'){
                  tempArray.push(item);
                }
              });
            }
            this.certKinds = tempArray;
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
      var tempObj = this.$route.query;
      if(tempObj.company){
        this.cname = tempObj.cname;
        this.company = tempObj.company;
        this.ifMfrs = tempObj.company.kind == '4'?true:false;
      }
      this.uploadCompanyCertsUrl = fileServer.upload + "companyCert";
      this.imgBasePath = fileServer.imgBasePath;
      this.userInfo = {'X-US':this.user.xus};
    }
  };

</script>
<style>
  .myInfocompanyOutput .el-form-item {
    margin-bottom: 2px;
    padding-right: 40px;
  }
  .uploadCertClass{
    border-radius: 4px;
    border: 1px solid #dcdfe6;
    margin: 15px;
    padding: 10px;
  }
  .myInfocompanyOutput .el-radio.is-bordered.is-checked {
    background-color: white;
    border-color: #62c1fe;
  }
  .myInfocompanyOutput .el-radio--mini.is-bordered {
    padding: 4px 18px 0 15px;
    border-radius: 4px;
    height: 25px;
  }
  .box_shadow_class{
    box-shadow: inset 0 0 5px 0 #ffffff;
  }
  #myProvEdit .el-button+.el-button{
    margin-left: 0px;
  }
  #myProvEdit .cell {
    padding-bottom: 3px;
    padding-top: 3px;
    line-height: 14px;
  }
  #myProvEdit .expiring {
    /*color: #ff47e8;*/
    color: #E6A23C;
  }
  #myProvEdit .expired {
    color: #ff311f;
  }
</style>
