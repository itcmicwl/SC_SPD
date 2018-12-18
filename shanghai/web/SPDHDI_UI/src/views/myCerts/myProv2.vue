<template>
  <div id="dealer" style="height: 100%;">
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" label-width="100px">
        <el-form-item label="配送机构名称">
          <el-input v-model="filters.cname" clearable placeholder="输入配送机构名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUnitList(1)">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="success" @click="unitEdit(null)">新增</el-button>
        </el-form-item>
        <!--<el-form-item>-->
          <!--<el-button type="primary" @click="importDealerExcel()">从Excel导入</el-button>-->
        <!--</el-form-item>-->
      </el-form>
    </el-col>
    <!--列表-->
    <template>
      <el-table :data="units" highlight-current-row v-loading="loading" border style="width: 100%;" fit
                class="dealer-tableheight" height="outer">
        <el-table-column type="index" label="序号" width="50" align="center">
        </el-table-column>
        <el-table-column prop="cname" label="供应商名称"  header-align="center">
        </el-table-column>
        <el-table-column prop="hosName" label="合作医院" header-align="center">
        </el-table-column>
        <el-table-column prop="linkman" width="90" label="联系人" header-align="center">
        </el-table-column>
        <el-table-column prop="legal" width="90" label="法人代表" header-align="center">
        </el-table-column>
        <el-table-column prop="contactWay" label="联系方式" width="120" header-align="center">
        </el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope">
            <el-button-group>
              <el-button type="primary" size="mini" @click="unitEdit(scope.row)" icon="edit">编辑</el-button>
              <el-button type="danger" size="mini" @click="delUnit(scope.row)" icon="delete">删除</el-button>
              <el-button type="info" size="mini" @click="getCompanyInfo(scope.row)" icon="delete">证照</el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>
      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page"
                       :page-sizes="[30, 100, 200, 400]" :page-size="pageSize" layout="total, sizes, prev, pager, next"
                       :total="total" style="float:right;" small>
        </el-pagination>
      </el-col>
    </template>
    <!-- 企业资质 -->
    <el-dialog :title="provCertsTitle" close="onClose" :visible.sync="provCertsVisible" :show-close='true'
               :close-on-press-escape='true' :close-on-click-modal="true" width="85%" :loading="queryProvCertsloading">
      <div class="addCertClass"><el-button type="info" @click="addProvCerts">新增证照</el-button></div>
      <el-table :data="companyCertsList" highlight-current-row  style="width: 100%;" border fit height="250">
        <el-table-column prop="certKind" :formatter="formateCertType" width="180" label="证照类型">
        </el-table-column>
        <el-table-column prop="certCode" label="证照编码" min-width="150">
        </el-table-column>
        <el-table-column prop="expdtBeginDate" :formatter="formateDate" label="有效期自">
        </el-table-column>
        <el-table-column prop="expdtEndDate" :formatter="formateDate" label="有效期至">
        </el-table-column>
        <el-table-column prop="isValiding" :formatter="formateIsValiding" label="是否长期有效">
        </el-table-column>
        <el-table-column label="查看附件" width="150">
          <template slot-scope="scope">
            <template v-for="i in scope.row.provCertificateImage.length">
              <a :href='imgBasePath+scope.row.provCertificateImage[i-1].filePath'
                 style="text-decoration: none;color: #2a7dce;" target="_blank">
                附件{{i}}&nbsp;</a>
            </template>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="98" align="center">
          <template slot-scope="scope">
            <el-button  @click="updateProvCerts(scope.row)" type="text">编辑</el-button>
            <el-button  @click="deleteProvCerts(scope.row)" style="color: #f56c6c;margin-left: 3px;" type="text">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <!--编辑界面-->
    <el-dialog :title="modelTitle" close="onClose" :visible.sync="editFormVisible" :close-on-click-modal="false">
      <el-form :model="unit" label-width="120px" :rules="editFormRules" ref="editForm" class="el-form-item-nomsg">
        <el-row>
          <!--<el-col :span="3">-->
            <!--合作医院-->
          <!--</el-col>-->
          <!--<el-col :span="21">-->
          <el-form-item label="合作医院">
            <el-select v-model="hosId" placeholder="请选择医院" clearable>
              <el-option v-for="item in hosInfos" :key="item.hosId" :label="item.hosName" :value="item.hosId">
              </el-option>
            </el-select>
          </el-form-item>
          <!--</el-col>-->
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="公司名称" prop="cname" :error="errors.cnameErr">
              <el-input v-model="unit.cname" placeholder="请输入公司名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="主数据编码" prop="masterCode">
              <el-input v-model="unit.masterCode" placeholder="请输入主数据编码"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="内部ERP编码">
              <el-input v-model="unit.erpCode"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="法人代表">
              <el-input v-model="unit.legal"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="传真">
              <el-input v-model="unit.contactFax"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系人">
              <el-input v-model="unit.linkman"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系方式">
              <el-input v-model="unit.contactWay"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="9">
            <el-form-item label="邮政编码">
              <el-input v-model="unit.postCode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="15">
            <el-form-item label="电子邮箱">
              <el-input v-model="unit.email"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="地址">
              <el-input v-model="unit.address"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label=" 是否供货单位">

              <el-select v-model="isSupplyVal" placeholder="请选择">
                <el-option key="1" label="是" value="1"></el-option>
                <el-option key="0" label="否" value="0"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label=" 是否三证合一">

              <el-select v-model="isThreeInOneVal" placeholder="请选择">
                <el-option key="1" label="是" value="1"></el-option>
                <el-option key="0" label="否" value="0"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="营业执照注册号">
              <el-input v-model="unit.regCode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="经营许可证编号">
              <el-input v-model="unit.jyxkCode"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="纳税人识别号">
              <el-input v-model="unit.nsrsbCode"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label=" 备注信息">
              <el-input type="textarea" :row="2" v-model="unit.remark"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>
    <!--Excel导入界面-->
    <el-dialog title="从Excel中导入供应商模板" close="onClose" :visible.sync="importExcelVisible" :show-close='true'
               :close-on-press-escape='true' :close-on-click-modal="true">
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
          :file-list="certsFileList"
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
          :file-list="certsFileList"
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
  import { fileServer,PROV_CERT_TYPE } from '../../common/js/constance';
  export default {
    data() {
      return {
        uploadCompanyCertsUrl: '',
        userInfo:{},
        certsFileList:[],
        dlgVisible:false,
        updateCertVisible:false,
        companyCerts:{
          pid:'',
          provId:'',
          isValiding:0,
          provCertificateImage:[]
        },
        certKinds: [],
        queryProvCertsloading: false,
        provCertsTitle:'',
        imgBasePath: '',
        currentCompany:{},
        companyCertsList:[],
        provCertsVisible:false,
        uploadUrl:'',
        importExcelVisible:false,
        uploadState:false,
        fileMap:{id:'',ext:'',name:'',path:''},
        BaseGoodsTempUrl:'',
        fileList:[],

        filters: {
          cname: ''

        },
        total: 0,
        page: 1,
        pageSize: 30,
        loading: false,
        units: [],

        unit: {
          // isSupply:'1',
          // isThreeInOne:'1'

        },
        isSupplyVal: '',
        isThreeInOneVal: '',
        unitKind: '2',
        unitKinds: [],
        modelTitle: "",
        editFormVisible: false,
        editLoading: false,
        editFormRules: {
          cname: [
            {required: true, message: '请输入单位名称', trigger: 'blur'}
          ],
          hosId: [
            {required: true, message: '请选择合作医院', trigger: 'blur'}
          ]
        },
        errors: {
          cnameErr: ''
        },
        fileUploadCount:0,
        dlgConfirmBtnLoading:false,
        corpId:'',
        hosId:'',
        hosInfos:[]
      }
    },
    methods: {
      // 获取我的医院列表
      getProvHosInfoVoList: function () {
        var param = {
          orderBy: "",
          queryObject: {
          }
        };
        this.axios.post('/spdHDIService/myInfo/provHosInfo/provHosInfoVoList', param).then(res => {
          this.hosInfos = res.data.data.data;
        }, err => {
        });
      },
      checkCerts:function (file) {
        window.open(fileServer.imgBasePath + file.url);
      },
      updateProvCerts:function (d1) {
        this.certsFileList = [];
        this.companyCerts = d1;
        var count = 0;
        d1.provCertificateImage.forEach(item=>{
          if(item.fileName == null || item.fileName == ''){
            this.certsFileList.push({name:item.filePath,url:item.filePath,id:item.id});
          }else {
            this.certsFileList.push({name:item.fileName,url:item.filePath,id:item.id});
          }
        });
        this.updateCertVisible = true;
      },

      deleteProvCerts:function (row) {
        delete row.provCertificateImage;
        this.axios.post('/spdHDIService/myInfo/companyInfo/deleteMyProvCerts', row).then(res => {
          if (res.data.code == "0") {
            this.$message({message: '操作成功！', type: 'success'});
            this.getMyProvCerts(this.currentCompany);
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
        this.axios.post('/spdHDIService/myInfo/companyInfo/deleteMyprovImage', {id:file.id}).then(res => {
          if (res.data.code == "0") {
            // this.getCompanyInfo(this.currentCompany);
            this.getMyProvCerts(this.currentCompany);
            this.$message({message: '操作成功！', type: 'success'});
          }else{
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.$message.error('数据异常，请联系管理员！')
        });
      },

      isValidingSelect:function (d) {
        this.companyCerts.isValiding = d;
      },
      provCertSelect:function (d) {
        this.companyCerts.certKind = d;
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
        var name = file.response.data[0].name;
        var certPath = path + "/" + id + "." + ext;
        var imageObj = {
          certificateId:this.companyCerts.id,
          filePath:certPath
        };
        this.addImage(imageObj);
      },

      addImage (imageObj) {
        let serverUrl = "/spdHDIService/myInfo/companyInfo/addMyProvImage";
        this.axios.post(serverUrl, imageObj).then(res => {
          if (res.data.code >= 0) {
            this.$message.success('操作成功。');
            this.getMyProvCerts(this.currentCompany);
          } else {
            this.$message.error('操作失败，' + res.data.msg.substring(0, 50) + '...');
          }
        }).catch(err => {
          this.$message.error('数据异常，请联系管理员！');
        })
      },

      _dlgConfirm:function(){
        this.fileUploadCount = 0;
        this.$refs.provCertsUpload.submit();
      },

      dlgConfirm () {
        this.$refs.cnsForm.validate((valid) => {
          if (valid) {
            // this.dlgConfirmBtnLoading = true;
            let serverUrl = "/spdHDIService/myInfo/companyInfo/addMyProvCerts";
            this.companyCerts.pid = this.currentCompany.hcpId;
            this.companyCerts.provId = this.currentCompany.id;
            this.axios.post(serverUrl, this.companyCerts).then(res => {
              if (res.data.code >= 0) {
                this.$message.success('操作成功。');
                this.dlgVisible = false;
                this.dlgConfirmBtnLoading = false;
              } else {
                this.$message.error('操作失败，' + res.data.msg.substring(0, 50) + '...');
              }
              this.fileUploadCount = 0;
              this.getMyProvCerts(this.currentCompany);
            }).catch(err => {
              this.fileUploadCount = 0;
              this.dlgVisible = false;
              this.dlgConfirmBtnLoading = false;
              this.$message.error('数据异常，请联系管理员！');
            })
          } else {
            this.$message.error('数据验证失败，请更正！');
          }
        })
      },

      updateCerts () {
        this.dlgConfirmBtnLoading = true;
        let serverUrl = "/spdHDIService/myInfo/companyInfo/updateMyProvCerts";
        this.companyCerts.pid = this.currentCompany.hcpId;
        this.companyCerts.provId = this.currentCompany.id;
        delete this.companyCerts.provCertificateImage;
        this.axios.post(serverUrl, this.companyCerts).then(res => {
          if (res.data.code >= 0) {
            this.$message.success('操作成功。');
            this.getMyProvCerts(this.currentCompany);
            this.dlgConfirmBtnLoading = false;
            this.updateCertVisible = false;
          } else {
            this.$message.error('操作失败，' + res.data.msg.substring(0, 50) + '...');
          }
          // this.getCompanyInfo(this.currentCompany);
        }).catch(err => {
          this.$message.error('数据异常，请联系管理员！');
        })
        this.dlgConfirmBtnLoading = false;
      },

      addProvCerts:function () {
        this.companyCerts = {
          pid:'',
          provId:'',
          isValiding:0,
          provCertificateImage:[]
        },
        this.dlgVisible = true;
        this.certsFileList = [];
      },

      //获取企业信息
      getCompanyInfo: function (row) {
        this.currentCompany = row;
        this.companyCertsList = row.provCertificates;
        this.provCertsTitle = '【'+ row.cname+'】' + '企业资质';
        this.provCertsVisible = true;
      },

      formateCertType (d1,d2,d3){
        // return d3;
        // return this.certKinds.find(item => item.id == d3).name;
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
        this.axios.post('/platformService/bas/company/importCompanyCerts', this.fileMap).then(res => {
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


      //字典获取单位类型
      getUnitKinds: function () {
        this.loading = true;
        this.axios.post('/platformService/sys/dict/getDictValueByDictEname', {dictName: 'JGLX', val: 2}).then(res => {

          this.loading = false;
          if (res.data.code == "0") {
            this.unitKinds = res.data.data;
          }
          else {
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.loading = false;
        });
      },

      //获取列表
      getUnitList: function (pIndex) {
        this.page = pIndex;
        var param = {
          orderBy: "",
          queryObject: {cname: this.filters.cname, collectorId: this.user.corpId},
          page: this.page,
          rows: this.pageSize
        };
        this.loading = true;
        this.axios.post('/platformService/bas/company/getMyProvs', param).then(res => {
          this.loading = false;
          if (res.data.code == "0") {
            this.units = res.data.data.data;
            this.total = res.data.data.total;
            this.getZZLX();
            //  this.page = res.data.data.pageNum;
          }
          else {
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.loading = false;
        });
      },

      //获取我的供应商证照列表
      getMyProvCerts: function (currentCompany) {
        let tempCompany = currentCompany;
        delete tempCompany.provCertificates;
        this.axios.post('/platformService/bas/company/getMyProvCerts', currentCompany).then(res => {

          if (res.data.code == "0") {
            this.companyCertsList = res.data.data;
          }else {
            this.$message.error(res.data.msg);
          }
        }, err => {
        });
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
        this.nameErr = '';
        if (item) {
          let unit = Object.assign({}, item);

          this.unit = unit;
          this.modelTitle = "修改配送机构";
          this.unit.kind = this.unitKind;
          this.isSupplyVal = this.unit.isSupply;
          this.isThreeInOneVal = this.unit.isThreeInOne;
          this.hosId = this.unit.hosId;
          delete this.unit.collectorId;
          delete this.unit.hosId;
          delete this.unit.hosName;
          delete this.unit.hcpId;
          delete this.unit.provCertificates;

        } else {
          this.unit = {};
          this.isSupplyVal = "";
          this.isThreeInOneVal = "";
          this.modelTitle = "添加配送机构";
          this.unit.kind = this.unitKind;

        }
        this.getProvHosInfoVoList();
        this.editFormVisible = true;

      },
      delUnit(unit) {
        this.$confirm('确认删除这条记录吗?', '提示', {}).then(() => {
          this.loading = true;
          this.axios.post("/platformService/bas/company/delMyProv", unit).then(res => {
            this.loading = false;
            if (res.data.code == "0") {
              this.loading = false;
              this.$message({message: '操作成功！', type: 'success'});
            } else {
              this.$message.error(res.data.msg);

            }
            this.getUnitList(this.page);
          }, err => {
            this.loading = false;
          });
        }).catch(() => {

        });
      },
      editSubmit: function () {
        var _this = this;
        var form = this.$refs.editForm;
        const h = this.$createElement;
        var arr = new Array();
        this.unit.isSupply = this.isSupplyVal;
        this.unit.isThreeInOne = this.isThreeInOneVal;

        form.validate((valid, l) => {
          if (valid) {
            this.editLoading = true;
            let serviceUrl = this.unit.id ? "/platformService/bas/company/update" : "/platformService/bas/company/insertMyProv";
            this.axios.post(serviceUrl+"?hosId="+this.hosId, this.unit).then(res => {
              this.editLoading = false;
              if (res.data.code == "0") {
                this.$message({message: '操作成功！', type: 'success'});
                this.editFormVisible = false;

                this.getUnitList(this.page);
              } else if (res.data.code == "-122") {
                let err = res.data.validateErrors.fieldErrors[0];

                if (err.field == "cname") {
                  this.errors.cnameErr += err.message;
                  arr.push(h('span', null, err.message));
                }
                else {
                  arr.push(h('span', null, err.message));
                }
                _this.$msgbox({title: "错误", type: "error", message: h('p', null, arr)});
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
      // this.certKinds = PROV_CERT_TYPE;
      this.getUnitList(1);
      this.uploadUrl =fileServer.upload +"baseData";
      this.imgBasePath = fileServer.imgBasePath;
      this.userInfo = {'X-US':this.user.xus};
      this.uploadCompanyCertsUrl = fileServer.upload + "companyCert";
    }
  };

</script>
<style>
  #dealer .dealer-tableheight {
    height: calc(100% - 90px);
  }
  .addCertClass{
    margin-bottom: 2px;
    text-align: left;
  }
  .uploadCertClass{
    border-radius: 4px;
    border: 1px solid #dcdfe6;
    margin: 15px;
    padding: 10px;
  }
</style>
