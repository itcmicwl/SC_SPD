<template>
    <div id="provRegInfo" style="height:100%;">
        <el-row style="margin-bottom: 8px;">
            <div class="inline" style="padding-top:1.5px;">
                <el-input placeholder="请输入注册证号、产品名称" v-model="searchText"  @keyup.enter.native="searchBtnClick">
                    <el-select v-model="searchType" slot="prepend" placeholder="全部" style="width:80px;">
                        <el-option label="全部" value="0"></el-option>
                        <el-option label="注册证号" value="1"></el-option>
                        <el-option label="产品名称" value="2"></el-option>
                    </el-select>
                    <el-button slot="append" icon="el-icon-search" @click="searchBtnClick">查询</el-button>
                </el-input>
            </div>
            <div class="inline">
                <el-button type="primary"  @click="addPrepare">新增</el-button>
                <el-button type="primary" @click="importRegExcel()">从Excel导入</el-button>
            </div>
        </el-row>
        <el-table highlight-current-row class="provRegInfo-tableheight" ref="itemsCertsTable" :data="itemsCerts" style="width: 100%" v-loading="iLoading" border :default-sort="{prop: 'productName'}" :row-style="handleCertsTableRowStyle" height="outer">
            <el-table-column prop="productName" label="注册证产品名称" show-overflow-tooltip header-align="center">
            </el-table-column>
            <el-table-column prop="mfrsName" label="生产厂家" show-overflow-tooltip header-align="center">
            </el-table-column>
            <el-table-column prop="certificateCode" label="注册证编码" show-overflow-tooltip header-align="center">
            </el-table-column>
            <el-table-column width="150" prop="regKind" label="注册证类别" :formatter="formatRegKind" show-overflow-tooltip header-align="center">
            </el-table-column>
            <el-table-column label="注册证有效期" show-overflow-tooltip header-align="center">
                <template slot-scope="scope">
                    <div v-if="scope.row.expdtBeginDate && scope.row.expdtEndDate" :class="expdtDateClass(scope.row)">
                        {{formatDate(scope.row.expdtBeginDate)}}至{{formatDate(scope.row.expdtEndDate)}}
                    </div>
                    <div v-else>
                        Error
                    </div>
                </template>
            </el-table-column>
            <el-table-column width="80" prop="isOldInfo" label="历史注册证" header-align="center">
                <template slot-scope="scope">
                    {{scope.row.isOldInfo === '1' ? '是':'否'}}
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="230" align="center">
                <template slot-scope="scope">
                    <el-button-group>
                        <el-button size="mini" icon="edit" type="primary" @click.native.prevent="handleEdit(scope.row)" :disabled="scope.row.isOldInfo === '1'">编辑
                        </el-button>
                        <el-button size="mini" type="warning" @click.native.prevent="handleReplaceCert(scope.row)" :disabled="scope.row.isOldInfo === '1'">
                            <i class="fa fa-exchange" aria-hidden="true" style="margin-right:5px;"></i>换证
                        </el-button>
                        <el-button size="mini" icon="delete" type="danger" @click.native.prevent="handleDelete(scope.row)">
                            删除
                        </el-button>
                        <el-button size="mini" icon="picture" type="info" @click="handleShowPicDlg(scope.row)">注册证({{scope.row.picNum}})
                        </el-button>
                    </el-button-group>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination style="float:right" small @size-change="handleiSizeChange" @current-change="handleiCurrentChange" :current-page.sync="iPageNum" layout="total, sizes, prev, pager, next" :page-sizes="[10, 15, 20, 30]" :page-size="iPageSize" :total="iTotal">
        </el-pagination>

        <el-dialog title="注册证信息" :visible.sync="dlgEditVisible" width="75%">
            <el-form :rules="validateRules" :model="currentCert" ref="certForm" class="el-form-item-nomsg" label-width="110px">
                <el-row>
                    <el-form-item label="生产厂商" prop="mfrsId">
                        <el-select v-model="currentCert.mfrsId" filterable :remote="remoteMfrs" :disabled="mfrsDisable" placeholder="请输入关键词"  class="select-width" :remote-method="querySearchMfrsAsync" :loading="mfrsSearchLoading" clearable @input="handleFreezeRemoteMfrs" @change="handleDlgMfrsSelect">
                            <el-option v-for="item in mfrsSearchData" :key="item.id" :label="item.mfrsName" :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="注册证编号" prop="certificateCode">
                        <el-autocomplete :trigger-on-focus="false" v-model="currentCert.certificateCode" :fetch-suggestions="querySearchCertsAsync" placeholder="请输入注册证编号进行搜索选择" @select="handleDlgCertsSelect" :props="{'value':'certificateCode','label':'certificateCode'}" style="width:100%;" ></el-autocomplete>
                    </el-form-item>
                    <el-form-item label="注册证产品名称" prop="productName">
                        <el-input v-model="currentCert.productName" placeholder="请输入注册证名称" ></el-input>
                    </el-form-item>
                    <el-form-item label="注册证类型" prop="regKind">
                        <el-select  v-model="currentCert.regKind" placeholder="请选择注册证类型" :disabled="kindDisable">
                            <!--@change="handleDlgKindChange"-->
                            <el-option v-for="(value,key,index) in dicts" :key="key" :label="value" :value="key"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="注册证有效期" required>
                        <el-col :span="4">
                            <el-form-item prop="expdtBeginDate">
                                <el-date-picker  type="date" placeholder="开始日期" v-model="currentCert.expdtBeginDate" style="width: 100%;"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col class="line" :span="1">-</el-col>
                        <el-col :span="4">
                            <el-form-item prop="expdtEndDate">
                                <el-date-picker  type="date" placeholder="结束日期" v-model="currentCert.expdtEndDate" style="width: 100%;"></el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-form-item>
                </el-row>
                <el-form-item label="注册证关联产品">
                    <div style="text-align:center;margin-bottom:5px;">
                        <el-button type="info" :plain="true"  icon="search" @click="searchGoods">搜索可选产品
                        </el-button>
                    </div>
                    <el-row type="flex" justify="center">
                        <el-transfer ref="eltransfer" :titles="['可选产品规格','已选产品规格']" filterable :data="goodsInfo" :props="{key:'id',label:'name'}" v-model="selectedGoods" v-loading="elLoading" @change="handleGoodsSelect">
                        </el-transfer>
                    </el-row>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dlgCancle" >取消</el-button>
                <el-button @click="dlgConfirm"  :loading="btnLoading" :type="dlgAddMode ? 'success':'primary'">{{dlgAddMode ? '新增' : '修改'}}</el-button>
            </span>
        </el-dialog>

        <el-dialog title="更换注册证" :visible.sync="dlgReplaceVisible" width="60%" top="5%">
            <el-form :rules="validateReplaceRules" :model="currentCert" ref="certReplaceForm" class="el-form-item-nomsg" label-width="120px">
                <el-row>
                    <el-form-item label="生产厂商" prop="mfrsName">
                        <el-input disabled v-model="currentCert.mfrsName" ></el-input>
                    </el-form-item>
                    <el-form-item label="原注册证编号" prop="OldcertificateCode">
                        <el-input disabled v-model="currentCert.oldCertificateCode" ></el-input>
                    </el-form-item>
                    <el-form-item label="新注册证编号" prop="certificateCode" required>
                        <el-input v-model="currentCert.certificateCode" ></el-input>
                    </el-form-item>
                    <el-form-item label="注册证产品名称" prop="productName">
                        <el-input disabled v-model="currentCert.productName" placeholder="请输入注册证名称" ></el-input>
                    </el-form-item>
                    <el-form-item label="注册证类型" prop="regKind">
                        <el-select  v-model="currentCert.regKind" placeholder="请选择注册证类型" disabled>
                            <el-option v-for="(value,key,index) in dicts" :key="key" :label="value" :value="key"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="新注册证有效期" required>
                        <el-col :span="7">
                            <el-form-item prop="expdtBeginDate">
                                <el-date-picker  type="date" placeholder="开始日期" v-model="currentCert.expdtBeginDate"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="1">-</el-col>
                        <el-col :span="8">
                            <el-form-item prop="expdtEndDate">
                                <el-date-picker  type="date" placeholder="结束日期" v-model="currentCert.expdtEndDate"></el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-form-item>
                </el-row>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dlgReplaceCancle" >取消</el-button>
                <el-button @click="dlgReplaceConfirm"  :loading="btnReplaceLoading">修改</el-button>
            </span>
        </el-dialog>

        <el-dialog title="注册证附件" :visible.sync="dlgPicVisible">
            <el-row :gutter="5">
                <div v-if="showImgList.length === 0" style="text-align:center;">
                    <span>暂无相关证照照片</span>
                </div>
                <el-col :span="4" v-for="(img, index) in showImgList" :key="img.id">
                    <el-card :body-style="{ padding: '0px', height:'120px' }">
                        <img :src="picURLPrefix+img.tImg" style="cursor: pointer;" class="image" @click="handleImgClick(img)">
                        <div class="bottom clearfix">
                            <el-button type="text" class="button" size="mini" @click="handlePicDel(img)">删除
                            </el-button>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
            <hr style="height:1px;border:none;border-top:1px dashed #185598;"></hr>
            <div style="text-align:center">
                <el-upload drag :on-success="handleUploadResponse" :before-upload="beforeUpload" ref="upload" :action="uploadURL" multiple :auto-upload="true" accept=".bmp,.jpg,.png,.pdf" :headers="userInfo" :on-error="handleUploadError" :show-file-list="true" :file-list="uploadFileList">
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或
                        <em>点击上传</em>
                    </div>
                    <div class="el-upload__tip" slot="tip">只能上传bmp/jpg/png/pdf文件，且不超过15MB</div>
                </el-upload>
            </div>
        </el-dialog>

        <el-dialog :visible.sync="dlgPicShowVisible" ref="dlgPicShow">
            <img width="100%" :src="dlgPicShowUrl" alt="">
        </el-dialog>

      <!--Excel导入界面-->
      <el-dialog title="从Excel中导入商品证照模板" close="onClose" :visible.sync="importExcelVisible" :show-close='true' :close-on-press-escape='true' :close-on-click-modal="true">
        <el-col :span="3">
          合作医院
        </el-col>
        <el-col :span="21">
          <el-select v-model="hosId" placeholder="请选择医院" clearable @change="hosSelect">
            <el-option v-for="item in hosInfos" :key="item.hosId" :label="item.hosName" :value="item.hosId">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="24" class="uploadClass">
          <template>
            <div>
              <el-upload
                class="upload-demo"
                :disabled="uploadState"
                :action= "uploadURL"
                :on-remove="handleRemove"
                :on-success="handleFileSuccess"
                :file-list="fileList"
                list-type="text">
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传一个Excel文件</div>
              </el-upload>
            </div>
          </template>
        </el-col>

        <div slot="footer" class="dialog-footer">
          <el-button @click.native="importExcelVisible = false;uploadState=false">取消</el-button>
          <el-button type="primary" @click.native="importExcelSubmit" :loading="editLoading">提交</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
import debounce from 'throttle-debounce/debounce';
import { mapGetters } from 'vuex';

export default {
  data () {
    let validateBeginDate = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('开始日期不能为空'))
      } else {
        if (this.currentCert.expdtEndDate) {
          this.$refs.certForm.validateField('expdtEndDate')
        }
        callback()
      }
    }
    let validateEndDate = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('结束日期不能为空'))
      } else {
        if (Date.parse(this.currentCert.expdtBeginDate) > Date.parse(this.currentCert.expdtEndDate)) {
          return callback(new Error('结束日期不能小于开始日期'))
        }
        callback()
      }
    }
    let validateReplaceBeginDate = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('开始日期不能为空'))
      } else {
        if (this.currentCert.expdtEndDate) {
          this.$refs.certReplaceForm.validateField('expdtEndDate')
        }
        callback()
      }
    }
    return {
      validateRules: {
        productName: [
          { required: true, message: '请输入产品名称', trigger: 'change' }
        ],
        mfrsId: [
          { required: true, message: '请选择生产厂商', trigger: 'change' }
        ],
        regKind: [
          { required: true, message: '请选择注册证类型', trigger: 'change' }
        ],
        expdtBeginDate: [
          { validator: validateBeginDate, trigger: 'change' }
        ],
        expdtEndDate: [
          { validator: validateEndDate, trigger: 'change' }
        ],
        certificateCode: [
          { required: true, message: '请输入注册证编号', trigger: 'change' }
        ]
      },
      validateReplaceRules: {
        expdtBeginDate: [
          { validator: validateReplaceBeginDate, trigger: 'change' }
        ],
        expdtEndDate: [
          { validator: validateEndDate, trigger: 'change' }
        ],
        certificateCode: [
          { required: true, message: '请输入注册证编号', trigger: 'change' }
        ]
      },
      baseURL: '/spdHDIService/provRegInfo',
      uploadURL: '/platformService/sys/upload/file/certs/',
      picURLPrefix: '/upload/',
      dicts: {}, // 注册证字典
      iLoading: false,
      itemsCerts: [], // 供应商产品注册证信息
      iPageNum: 1,
      iPageSize: 30,
      iTotal: 0,
      dlgEditVisible: false,
      dlgPicVisible: false,
      dlgAddMode: false, // 判断新增or编辑
      btnLoading: false,
      currentCert: {},
      origCertCode: '',

      searchText: '',
      searchType: '0',

      allowedFileTypes: ['image/bmp', 'image/jpeg', 'image/png', 'application/pdf'],
      imgList: [],
      uploadFileList: [],

      dlgPicShowVisible: false,
      dlgPicShowUrl: '',

      goodsInfo: [],
      selectedGoods: [],
      elLoading: false,

      remoteMfrs: true,
      mfrsSearchLoading: false,
      mfrsSearchData: [],

      mfrsDisable: false,
      kindDisable: false,

      btnReplaceLoading: false,
      dlgReplaceVisible: false,

      importExcelVisible:false,
      uploadState:true,
      fileMap:{id:'',ext:'',name:'',path:''},
      BaseGoodsTempUrl:'',
      fileList:[],
      editLoading: false,
      hosId:'',
      hosInfos:[]
    }
  },
  computed: {
    showImgList () {
      let len = this.imgList.length
      let temp = []
      for (let i = 0; i < len; i++) {
        let indexof = this.imgList[i].filePath.lastIndexOf('/')
        temp.push({
          'id': this.imgList[i].id,
          'img': this.imgList[i].filePath,
          'tImg': this.imgList[i].filePath.substring(0, indexof) + '/thumb-' + this.imgList[i].filePath.substring(indexof + 1),
          'version': this.imgList[i].version
        })
      }
      return temp
    },
    transData () {
      let result = JSON.parse(JSON.stringify(this.currentCert))
      result.expdtBeginDate = Date.parse(result.expdtBeginDate)
      result.expdtEndDate = Date.parse(result.expdtEndDate)
      delete result.selectedGoods
      if (this.dlgAddMode) {
        delete result.delGoods
      }
      return result
    },
    userInfo () {
      return {
        'X-US': this.getLoginUser.xus
      }
    },
    ...mapGetters(['getLoginUser'])
  },
  methods: {
    hosSelect:function (d) {
      this.hosId = d;
      if(d !=''){
        this.uploadState = false;
      }else{
        this.uploadState = true;
      }
    },
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
    importExcelSubmit:function(){
      this.uploadState=false;
      this.editLoading = true;
      this.fileMap['hosId'] = this.hosId;
      this.axios.post(this.baseURL + '/importMfrsCerts', this.fileMap).then(res => {
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
    importRegExcel:function(){
      this.getProvHosInfoVoList();
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
    formatRegKind (row, column) {
      return this.dicts[row.regKind];
    },
    formatDate (item) {
      return new Date(item).toLocaleDateString()
    },
    expdtDateClass (row) {
      if (row.isOldInfo === '1') {
        return
      }
      let date = row.expdtEndDate
      let endDate = Date.parse(date)
      let today = Date.parse(new Date())
      let days = 30 * 24 * 60 * 60 * 1000
      let expiring = false
      let expired = false
      if (endDate - today < 0) {
        expired = true
      } else if (endDate - today < days) {
        expiring = true
      }
      return {
        'expiring': expiring,
        'expired': expired
      }
    },
    getProvRegInfo (searchType, searchText) {
      this.iLoading = true

      let params = {
        'pageNum': this.iPageNum,
        'pageSize': this.iPageSize
      }
      switch (searchType) {
        case '0':
          params.certificateCode = searchText
          params.productName = searchText
          break
        case '1':
          params.certificateCode = searchText
          break
        case '2':
          params.productName = searchText
          break
      }

      this.axios.get(this.baseURL + '/getProvRegInfo', {
        params
      }).then(res => {
        if (res.data.code >= 0) {
          this.itemsCerts = res.data.data.data
          this.iTotal = res.data.data.total
          this.iLoading = false
        } else {
          this.$message.error('获取数据失败。' + res.data.msg.substring(0, 50) + '...')
        }
      }).catch(_ => {
        this.$message.error('数据异常，请联系管理员。')
      })
    },
    beforeUpload (file) {
      if (!this.allowedFileTypes.includes(file.type)) {
        this.$message.error('文件格式不符合要求，请按要求上传' + this.allowedFileTypes.join(',') + '文件！')
        return false
      }
      if (file.size / 1024 / 1024 > 15) {
        this.$message.error('上传图片大小不能超过15M！')
        return false
      }
    },
    handleUploadError () {
      this.$message.error('上传照片失败，请联系管理员！')
    },
    handleUploadResponse (response, file, fileList) {
      if (response.code < 0) {
        this.$message({
          type: 'error',
          showClose: 'true',
          duration: 0,
          message: file.name + '上传失败，' + response.msg.substring(0, 50) + '...'
        })
      } else {
        const uploadRespone = response.data[0]
        const filePath = uploadRespone.path + '/' + uploadRespone.id + '.' + uploadRespone.ext
        this.axios.post(this.baseURL + '/uploadPic', {
          certificateId: this.currentCert.id,
          filePath
        }).then(res => {
          if (res.data.code >= 0) {
            this.$message.success(file.name + '上传成功！')
            this.imgList.push(res.data.data)
            const tableCert = this.itemsCerts.find(o => o.id === this.currentCert.id)
            tableCert.picNum++
          } else {
            file.status = 'fail'
            this.$message({
              type: 'error',
              showClose: 'true',
              duration: 0,
              message: file.name + '上传失败，' + res.data.msg.substring(0, 50) + '...'
            })
          }
        }).catch(_ => {
          this.$message.error('数据异常，请联系管理员！')
        })
      }
    },
    handlePicDel (img) {
      this.axios.delete(this.baseURL + '/delCertImg', {
        params: {
          'id': img.id,
          'version': img.version
        }
      }).then(res => {
        if (res.data.code >= 0) {
          this.$message({
            type: 'success',
            message: '删除证照照片成功。'
          })
          let removeIndex = this.imgList.map(item => item.id).indexOf(img.id)
          this.imgList.splice(removeIndex, 1)
          this.currentCert.picNum--
          const tableCert = this.itemsCerts.find(o => o.id === this.currentCert.id)
          tableCert.picNum = this.currentCert.picNum
        } else {
          this.$message({
            type: 'error',
            showClose: 'true',
            duration: 0,
            message: res.data.msg.substring(0, 50) + '...'
          })
        }
      }).catch(_ => {
        this.$message({
          type: 'error',
          message: '数据异常，请联系管理员！'
        })
      })
    },
    addPrepare () {
      this.goodsInfo.splice(0)
      this.selectedGoods.splice(0)
      this.currentCert = this.newCert()
      this.currentCert.provId = this.getLoginUser.corpId
      this.certsSearchData = []
      this.mfrsSearchData = []
      this.kindDisable = false
      this.mfrsDisable = false
      this.dlgAddMode = true
      this.dlgEditVisible = true
      this.$refs['certForm'].resetFields()
    },
    handleEdit (itemCert) {
      this.dlgAddMode = false
      this.origCertCode = itemCert.certificateCode
      this.currentCert = JSON.parse(JSON.stringify(itemCert))
      this.kindDisable = true
      this.mfrsDisable = true
      this.mfrsSearchData = [{ id: this.currentCert.mfrsId, mfrsName: this.currentCert.mfrsName }]
      this.searchGoods()
      this.selectedGoods = this.currentCert.selectedGoods.map(o => o.id)
      this.handleFreezeRemoteMfrs()
      this.dlgEditVisible = true
    },
    handleDelete (itemCert) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(_ => {
        this.axios.delete(this.baseURL + '/delCert', {
          params: {
            'id': itemCert.id,
            'version': itemCert.version
          }
        }).then(res => {
          if (res.data.code >= 0) {
            this.$message({
              type: 'success',
              message: '删除注册证信息成功。'
            })
            let removeIndex = this.itemsCerts.findIndex(o => o.id === itemCert.id)
            this.itemsCerts.splice(removeIndex, 1)
            this.iTotal--
          } else {
            this.$message({
              type: 'error',
              showClose: 'true',
              duration: 0,
              message: res.data.msg.substring(0, 50) + '...'
            })
          }
        })
      }).catch(_ => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleShowPicDlg (item) {
      this.currentCert = JSON.parse(JSON.stringify(item))
      this.uploadFileList.splice(0)
      this.axios.get(this.baseURL + '/getCertImg', {
        params: {
          'certId': item.id
        }
      }).then(res => {
        if (res.code < 0) {
          this.$message.error('系统错误，请联系管理员。')
          return
        }
        this.imgList = res.data.data
        this.dlgPicVisible = true
      }).catch(_ => {
        this.$message.error('数据异常，请联系管理员。')
      })
    },
    ShowPic (item) {
      this.currentCert = JSON.parse(JSON.stringify(item))
      this.uploadFileList.splice(0)
      this.axios.get(this.baseURL + '/getCertImg', {
        params: {
          'certId': item.id
        }
      }).then(res => {
        if (res.code < 0) {
          this.$message.error('系统错误，请联系管理员。');
          return;
        }
        this.imgList = res.data.data;
      }).catch(_ => {
        this.$message.error('数据异常，请联系管理员。')
      })
    },
    handleiCurrentChange (val) {
      this.iPageNum = val
      this.getProvRegInfo()
    },
    handleiSizeChange (val) {
      this.iPageSize = val
      this.getProvRegInfo()
    },
    handleDlgMfrsSelect (item) {
      if (item) {
        const mfrs = this.mfrsSearchData.find(o => o.id === item)
        this.currentCert.mfrsName = mfrs.mfrsName
        this.currentCert.selectedGoods.splice(0)
        if (this.dlgAddMode) { this.searchGoods() }
      }
    },
    handleDlgCertsSelect (item) {
      if (item) {
        this.mfrsSearchData = [{ id: item.mfrsId, mfrsName: item.mfrsName }]
        this.currentCert.mfrsName = item.mfrsName
        this.currentCert.mfrsId = item.mfrsId
        this.currentCert.expdtBeginDate = item.expdtBeginDate
        this.currentCert.expdtEndDate = item.expdtEndDate
        this.currentCert.productName = item.productName
        this.currentCert.regKind = item.regKind
        this.handleFreezeRemoteMfrs()
      }
    },
    debounceQuerySearchMfrsAsync: debounce(500, function (queryString, callback) {
      this.axios.get(this.baseURL + '/getMfrs', {
        params: {
          'mfrsName': queryString
        }
      }).then(res => {
        if (res.data.code >= 0) {
          this.mfrsSearchData = res.data.data
          this.mfrsSearchLoading = false
        } else {
          this.$message({
            type: 'error',
            showClose: 'true',
            duration: 0,
            message: res.data.msg.substring(0, 50) + '...'
          })
          this.mfrsSearchLoading = false
        }
      }, _ => {
        this.$message.error('数据异常，请联系管理员。')
      })
    }),
    querySearchMfrsAsync (queryString) {
      if (queryString !== '' && !this.currentCert.certificateCode) {
        this.mfrsSearchLoading = true
        this.debounceQuerySearchMfrsAsync(queryString)
      }
    },
    handleFreezeRemoteMfrs () {
      this.$set(this, 'remoteMfrs', false)
      this.$nextTick(function () {
        this.$set(this, 'remoteMfrs', true)
      })
    },
    querySearchCertsAsync: debounce(500, function (queryString, callback) {
      this.axios.get(this.baseURL + '/getBasRegInfo', {
        params: {
          'certCode': queryString,
          'mfrsId': this.currentCert.mfrsId,
          'regKind': this.currentCert.regKind
        }
      }).then(res => {
        if (res.data.code >= 0) {
          callback(res.data.data)
        } else {
          this.$message({
            type: 'error',
            showClose: 'true',
            duration: 0,
            message: res.data.msg.substring(0, 50) + '...'
          })
          // this.certsSearchLoading = false;
        }
      }, _ => {
        this.$message.error('数据异常，请联系管理员！')
        // this.certsSearchLoading = false
      })
    }),
    dlgCancle () {
      this.dlgEditVisible = false
    },
    dlgConfirm () {
      this.$refs.certForm.validate((valid) => {
        if (valid) {
          this.btnLoading = true
          if (this.dlgAddMode) {
            this.axios.post(this.baseURL + '/addCert', this.transData).then(res => {
              if (res.data.code >= 0) {
                this.$message({
                  type: 'success',
                  message: '新增注册证信息成功。'
                })
                this.currentCert.id = res.data.data
                this.currentCert.picNum = 0
                this.currentCert.version = 0
                this.changeSelectedGoods(this.currentCert)
                this.itemsCerts.push(this.currentCert)
                this.dlgEditVisible = false
                this.iTotal++
              } else {
                this.$message({
                  type: 'error',
                  showClose: 'true',
                  duration: 0,
                  message: res.data.msg.substring(0, 50) + '...'
                })
                return false
              }
            }).catch(_ => {
              this.$message.error('数据异常，请联系管理员！')
              this.btnLoading = false
            })
          } else {
            this.currentCert.oldCertificateCode = this.origCertCode
            this.axios.put(this.baseURL + '/modCert', this.transData).then(res => {
              if (res.data.code >= 0) {
                this.$message({
                  type: 'success',
                  message: '更新注册证信息成功。'
                })
                this.currentCert.version = res.data.data
                this.changeSelectedGoods(this.currentCert)
                this.changeTableData(this.currentCert)
                this.dlgEditVisible = false
              } else {
                this.$message({
                  type: 'error',
                  showClose: 'true',
                  duration: 0,
                  message: res.data.msg.substring(0, 50) + '...'
                })
                return false
              }
            }).catch(_ => {
              this.$message.error('数据异常，请联系管理员！')
              this.btnLoading = false
            })
          }
          this.btnLoading = false
        } else {
          this.$message.error('未按要求填写表单，请按提示更正后重新提交。')
        }
      })
    },
    changeSelectedGoods (items) {
      for (let item of items.addGoods) {
        items.selectedGoods.push(this.goodsInfo.find(o => o.id === item))
      }
      for (let item of items.delGoods) {
        let removeIndex = items.selectedGoods.findIndex(o => o.id === item)
        if (removeIndex !== -1) {
          items.selectedGoods.splice(removeIndex, 1)
        }
      }
      delete items.addGoods
      delete items.delGoods
    },
    handleImgClick1 (img) {
      this.dlgPicShowVisible = true
      this.dlgPicShowUrl = '/upload/' + img.img
    },
    handleImgClick (img) {
      var url = '/upload/' + img.img;
      // window.location=url;
      window.open(url);
    },
    searchBtnClick () {
      this.getProvRegInfo(this.searchType, this.searchText)
    },
    newCert () {
      return {
        'certificateCode': '',
        'expdtBeginDate': '',
        'expdtEndDate': '',
        'id': '',
        'mfrsId': '',
        'productName': '',
        'provId': '',
        'regKind': '',
        'picNum': 0,
        'version': '',
        'selectedGoods': [],
        'addGoods': [],
        'delGoods': []
      }
    },
    changeTableData (item) {
      const cert = this.itemsCerts.find(o => o.id === item.id)
      let { certificateCode, expdtBeginDate, expdtEndDate, version, mfrsId, mfrsName, productName, regKind, selectedGoods } = item
      cert.certificateCode = certificateCode
      cert.expdtBeginDate = expdtBeginDate
      cert.expdtEndDate = expdtEndDate
      cert.version = version
      cert.mfrsId = mfrsId
      cert.productName = productName
      cert.regKind = regKind
      cert.mfrsName = mfrsName
      cert.selectedGoods = [...selectedGoods]
    },
    searchGoods () {
      if (!this.currentCert.certificateCode && !this.currentCert.mfrsId) {
        this.$message.error('请输入生产厂商后再进行搜索')
      } else {
        let params = {
          mfrsId: this.currentCert.mfrsId
        }
        this.elLoading = true
        this.axios.get(this.baseURL + '/getGoodsInfo', { params }).then(res => {
          if (res.data.code >= 0) {
            this.goodsInfo = res.data.data
            if (this.goodsInfo.length === 0) {
              this.$message.info('无可选产品，请在基础数据厂商产品中新增产品')
            }
          } else {
            this.$message({
              type: 'error',
              showClose: 'true',
              duration: 0,
              message: res.data.msg.substring(0, 50) + '...'
            })
          }
          this.elLoading = false
        }).catch(_ => {
          this.$message({
            type: 'error',
            message: '数据异常，请联系管理员！'
          })
          this.elLoading = false
        })
      }
    },
    handleGoodsSelect (rightValues, direction, changeData) {
      if (direction === 'right') {
        for (let item of changeData) {
          if (this.currentCert.selectedGoods.findIndex(o => o.id === item) === -1) {
            this.currentCert.addGoods.push(item)
          }
          let removeIndex = this.currentCert.delGoods.findIndex(o => o === item)
          if (removeIndex !== -1) {
            this.currentCert.delGoods.splice(removeIndex, 1)
          }
        }
      } else if (direction === 'left') {
        for (let item of changeData) {
          if (this.currentCert.selectedGoods.findIndex(o => o.id === item) !== -1) {
            this.currentCert.delGoods.push(item)
          }
          let removeIndex = this.currentCert.addGoods.findIndex(o => o === item)
          if (removeIndex !== -1) {
            this.currentCert.addGoods.splice(removeIndex, 1)
          }
        }
      }
    },
    handleCertsTableRowStyle (row, index) {
      if (row.isOldInfo === '1') {
        return {
          'color': 'grey'
        }
      }
    },
    handleReplaceCert (itemCert) {
      this.currentCert = JSON.parse(JSON.stringify(itemCert))
      this.currentCert.oldCertificateCode = this.currentCert.certificateCode
      this.currentCert.certificateCode = ''
      //   delete this.currentCert.addGoods
      //   delete this.currentCert.delGoods
      //   delete this.currentCert.selectedGoods
      this.dlgReplaceVisible = true
    },
    dlgReplaceCancle () {
      this.dlgReplaceVisible = false
    },
    dlgReplaceConfirm () {
      this.$refs.certReplaceForm.validate((valid) => {
        if (valid) {
          this.axios.post(this.baseURL + '/replaceCert', {
            'id': this.currentCert.id,
            'certificateCode': this.currentCert.certificateCode,
            'expdtBeginDate': this.currentCert.expdtBeginDate,
            'expdtEndDate': this.currentCert.expdtEndDate,
            'version': this.currentCert.version
          }).then(res => {
            if (res.data.code >= 0) {
              this.$message.success('更换注册证成功')
              this.dlgReplaceVisible = false
              this.getProvRegInfo()
            } else {
              this.$message.error('更换注册证失败')
            }
          }).catch(err => {
            this.$msgbox({ title: '错误', type: 'error', message: err })
          })
        } else {
          this.$message.error('未按要求填写表单，请按提示更正后重新提交。')
        }
      })
    }
  },
  mounted () {
    this.iLoading = true;
    this.axios.get(this.baseURL + '/getCertKind').then(res => {
      if (res.data.code >= 0) {
        for (let item of res.data.data) {
          this.dicts[item.value] = item.name
        }
        this.getProvRegInfo()
      }
    }).catch(_ => {
      this.$message.error('数据异常，请联系管理员。')
    })
  }
}
</script>

<style scoped>
.font {
    font-size: 12px;
}

.provRegInfo-tableheight {
    height: calc(100% - 100px);
   /* min-height: 400px;*/
}

.expiring {
    color: #ff00ff;
}

.expired {
    color: red;
}

.image {
    width: 100%;
    display: block;
    max-height: 100px;
}

.is-error {
    margin-bottom: 22px;
}

.inline {
    /*margin-left: 10px;*/
    margin-right: 10px;
    display: inline-block;
}

.right {
    float: right;
}

.button {
    padding: 0;
    float: right;
}

.select-width {
    width: 100%;
}

.line {
    text-align: center;
}

.bottom {
    margin: 5px;
    line-height: 12px;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both
}
</style>

<style>
  #provRegInfo .el-transfer {
      font-size: 12px;
  }

  #provRegInfo .el-dialog--large {
      width: 60%;
      min-width: 800px;
  }

  #provRegInfo .el-transfer-panel {
      width: 300px;
  }

  #provRegInfo .el-transfer-panel__item .el-checkbox__label {
      overflow: inherit;
  }

  #provRegInfo .el-transfer-panel .el-transfer-panel__footer {
      height: 30px;
  }

  #provRegInfo .el-form-item__error {
      display: block;
  }

  .uploadClass{
    margin-bottom: 5px;
    margin-top: 5px;
    padding: 5px;
    border-radius: 4px;
    border: 1px solid #dcdfe6;
  }
</style>
