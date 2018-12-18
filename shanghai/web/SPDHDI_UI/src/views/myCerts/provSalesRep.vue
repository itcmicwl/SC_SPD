<template>
  <div id="provSalesRep" style="height:100%;">
    <el-row class="queryFormClass">
      <div class="inline">
        <span style="margin-right: 5px; margin-left: 15px;color: #5f6366">效期</span>
        <el-date-picker type="date" placeholder="开始时间" v-model="searchExpdtBeginDate" style="width:130px"></el-date-picker>
        <span>-</span>
        <el-date-picker type="date" placeholder="截止时间" v-model="searchExpdtEndDate"  style="width:130px"></el-date-picker>
      </div>
      <span style="margin-right: 5px; margin-left: 15px;color: #5f6366">医院或销售代表</span>
      <div class="inline">
        <el-input placeholder="请输入医院或销售代表" v-model="searchName"
                  @keyup.enter.native="searchBtnClick" clearable>
        </el-input>
      </div>
      <div class="inline" style="margin-left: 5px;">
        <el-button type="primary" v-on:click="searchBtnClick" plain style="width: 80px;height: 26px" icon="el-icon-search">搜索</el-button>
        <el-button type="success" icon="el-icon-plus" plain style="height: 26px;margin-left: 0px;" @click="addPrepare(null)" >新增销售代表</el-button>
      </div>
    </el-row>
    <el-table highlight-current-row class="provSalesRep-tableheight" ref="frwtsTable" :row-class-name="tableRowClassName"
              :data="frwtsList" style="width: 100%" v-loading="fLoading" border height="outer">
      <el-table-column type="index" label="序号" width="40" align="center">
      </el-table-column>
      <el-table-column label="合作医院" show-overflow-tooltip prop="hosName">
      </el-table-column>
      <el-table-column label="销售代表" prop="salesName">
      </el-table-column>
      <el-table-column label="电话" prop="phoneNum">
      </el-table-column>
      <!--<el-table-column label="效期">-->
      <!--<template slot-scope="scope">-->
      <!--<div v-if="scope.row.expdtBeginDate && scope.row.expdtEndDate"-->
      <!--:class="expdtDateClass(scope.row.expdtEndDate)">-->
      <!--{{formatDate(scope.row.expdtBeginDate)}}至{{formatDate(scope.row.expdtEndDate)}}-->
      <!--</div>-->
      <!--<div v-else>-->
      <!--Error-->
      <!--</div>-->
      <!--</template>-->
      <!--</el-table-column>-->
      <el-table-column :formatter="formatterExpdtDate" prop="expdtDate" label="效期" width="150">
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="170" header-align="center" align="center">
        <template slot-scope="scope">
          <el-button-group>
            <el-button size="mini" type="primary" @click.native.prevent="handleEdit(scope.row)" icon="el-icon-edit-outline">编辑</el-button>
            <el-button size="mini" type="info" @click="checkCert(scope.row.filePath)"  icon="el-icon-picture">证照</el-button>
            <el-button size="mini" type="danger" @click.native.prevent="handleDelete(scope.row)" plain icon="el-icon-delete">删除</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-if="fTotal > 0" style="float:right" small @size-change="handlefSizeChange"
                   @current-change="handlefPageChange"
                   :current-page.sync="fPageNum" layout="total, sizes, prev, pager, next"
                   :page-sizes="[10, 15, 20, 30]" :page-size="fPageSize" :total="fTotal">
    </el-pagination>

    <el-dialog :title="dlgAddMode?'添加销售代表':'销售代表信息'" :visible.sync="dlgVisible" >
      <el-form :model="currentFrwts" ref="frwtsForm" label-width="100px" :rules="validateRules"
               class="el-form-item-nomsg">
        <el-row>
          <el-col :span="23">
            <el-form-item label="合作医院" required prop="hosId">
              <el-select v-model="currentFrwts.hosId" filterable :remote="remote" style="width: 100%" placeholder="请输入关键词"
                         :remote-method="querySearchHosAsync" :loading="hosSearchLoading"
                         clearable @input="handleFreezeRemote" @change="handleHosSelect">
                <el-option v-for="item in hosSearchData" :key="item.id" :label="item.hosName" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="11">
            <el-form-item label="销售代表" required prop="salesName">
              <el-input v-model="currentFrwts.salesName"  class="font" style="width:100%;"
                        placeholder="请输入销售代表姓名"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话号码" required prop="phoneNum">
              <el-input v-model="currentFrwts.phoneNum"  class="font" style="width:100%;"
                        placeholder="请输入联系电话"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="附件信息" required>
          <span class="fontStyle">注：只能上传jpg/png/bmp/pdf文件，且不超过15MB</span>
          <el-row :gutter="10">
            <el-form-item prop="filePath">
              <el-input v-model="currentFrwts.filePath" style="display:none" readonly
                        disabled></el-input>
              <el-upload class="pic-uploader" ref="wtrUpload" accept=".bmp,.jpg,.png,.pdf"
                         :headers="userInfo" :class="{'upload-background':!wtrImageUrl}"
                         :action="uploadURL" :auto-upload="true" :show-file-list="true"
                         :multiple="false" :on-success="handleWtrSuccess"
                         :on-change="handleWtrLocalAdd"
                         :before-upload="beforeUpload" :on-error="handleWtrUploadErr">
                <label :class="{'item-status-label':uploadWtrSuccess}">
                  <i :class="{'upload-success el-icon-check':uploadWtrSuccess}"></i>
                </label>
                <img v-if="wtrImageUrl" :src="wtrImageUrl" class="pic">
                <i v-else class="el-icon-plus pic-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-row>
        </el-form-item>
        <el-form-item label="效期" required>
          <el-col :span="9">
            <el-form-item prop="expdtBeginDate">
              <el-date-picker type="date" placeholder="开始日期" v-model="currentFrwts.expdtBeginDate"
                              style="width: 100%;" ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col style="text-align:center;" :span="1">-</el-col>
          <el-col :span="9">
            <el-form-item prop="expdtEndDate">
              <el-date-picker type="date" placeholder="结束日期" v-model="currentFrwts.expdtEndDate"
                              style="width: 100%;" ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="el-dialog__footer" style="text-align: center">
                <el-button type="primary" size="mini" @click="dlgCancle" plain icon="el-icon-back">返回</el-button>
                <el-button type="success" size="mini" @click="dlgConfirm" plain icon="el-icon-check" :loading="dlgConfirmBtnLoading">保存</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
  import debounce from 'throttle-debounce/debounce'
  import { mapGetters } from 'vuex'
  import { fileServer,PROV_CERT_TYPE } from '../../common/js/constance';
  import moment from "moment";

  export default {
    data () {
      let validateBeginDate = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('开始日期不能为空'))
        } else {
          if (this.currentFrwts.expdtEndDate) {
            this.$refs.frwtsForm.validateField('expdtEndDate')
          }
          callback()
        }
      }
      let validateEndDate = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('结束日期不能为空'))
        } else {
          if (
            Date.parse(this.currentFrwts.expdtBeginDate) >
            Date.parse(this.currentFrwts.expdtEndDate)
          ) {
            return callback(new Error('结束日期必须大于开始日期'))
          }
          callback()
        }
      }
      return {
        validateRules: {
          hosId: [{ required: true, message: '请选择委托医院', trigger: 'change' }],
          salesName: [{ required: true, message: '请输入销售代表姓名', trigger: 'blur' }],
          phoneNum: [{ required: true, message: '请输入电话号码', trigger: 'blur' }],
          expdtBeginDate: [{ validator: validateBeginDate, trigger: 'change' }],
          expdtEndDate: [{ validator: validateEndDate, trigger: 'change' }],
          filePath: [
            { required: true, message: '请上传销售代表身份证照片', trigger: 'change' }
          ]
        },
        baseURL: '/spdHDIService/provSalesRep',
        uploadURL: '/platformService/sys/upload/file/certs/',
        picURLPrefix: '/upload/',
        frwtsList: [],
        currentFrwts: {},
        fLoading: false,
        hosSearchLoading: false,
        fPageNum: 1,
        fPageSize: 30,
        fTotal: 0,

        dlgAddMode: false,
        dlgVisible: false,
        dlgConfirmBtnLoading: false,
        hosSearchData: [],
        remote: true,
        wtrImageUrl: '',
        allowedFileTypes: [
          'image/bmp',
          'image/jpeg',
          'image/png',
          'application/pdf'
        ],

        searchExpdtBeginDate: '',
        searchExpdtEndDate: '',
        searchName: '',

        uploadWtrSuccess: false,
        showBigPic: false
      }
    },
    computed: {
      handleData () {
        let frwtsData = JSON.parse(JSON.stringify(this.currentFrwts))
        frwtsData.expdtBeginDate = Date.parse(frwtsData.expdtBeginDate)
        frwtsData.expdtEndDate = Date.parse(frwtsData.expdtEndDate)
        frwtsData.provId = this.getLoginUser.corpId
        delete frwtsData.hosName
        delete frwtsData.wtrname
        return frwtsData
      },
      largeWtrImageUrl () {
        if (
          this.currentFrwts.filePath &&
          this.showBigPic &&
          this.uploadWtrSuccess
        ) {
          return this.picURLPrefix + this.currentFrwts.filePath
        } else {
          return this.wtrImageUrl
        }
      },
      userInfo () {
        return {
          'X-US': this.getLoginUser.xus
        }
      },
      ...mapGetters(['getLoginUser'])
    },
    filters: {},
    methods: {
      formatterExpdtDate(row, col, cellValue) {
        let begin = moment(row.expdtBeginDate).format("YYYY-MM-DD");
        let end = moment(row.expdtEndDate).format("YYYY-MM-DD");
        return begin + "至" + end;
      },
      tableRowClassName({row, rowIndex}) {
        const expdtEndDate = moment(row.expdtEndDate);
        const now = moment();
        if (expdtEndDate.isBefore(now)) {
          return "warning-red";
        } else if (expdtEndDate.isBefore(now.add(1, "month"))) {
          return "warning-row";
        }
        return "";
      },
      checkCert:function (filePath) {
        window.open(fileServer.imgBasePath + filePath);
      },
      formatDate (item) {
        return new Date(item).toLocaleDateString()
      },
      expdtDateClass (date) {
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
          expiring: expiring,
          expired: expired
        }
      },
      addPrepare () {
        this.showBigPic = false
        this.uploadWtrSuccess = false
        this.wtrImageUrl = ''
        this.currentFrwts = this.newFrwts()
        this.hosSearchData = []
        this.dlgAddMode = true
        this.dlgVisible = true
        this.$refs['frwtsForm'].resetFields()
      },
      handlefSizeChange (val) {
        this.fPageSize = val
        this.getSalesReps()
      },
      handlefPageChange (val) {
        this.fPageNum = val
        this.getSalesReps()
      },
      handleEdit (item) {
        this.showBigPic = false
        if (item.filePath) {
          this.uploadWtrSuccess = true
          let slashIndex = item.filePath.lastIndexOf('/')
          if (slashIndex !== -1) {
            this.wtrImageUrl =
              this.picURLPrefix + item.filePath.substring(0, slashIndex) + '/thumb-' + item.filePath.substring(slashIndex + 1)
            // 处理PDF缩略图，一般情况下，缩略图格式与原图一致，但系统中PDF的缩略图为jpg格式
            let dotIndex = this.wtrImageUrl.lastIndexOf('.');
            let ext = this.wtrImageUrl.substring(dotIndex + 1);
            if(ext==='PDF'||ext==='pdf'){
              this.wtrImageUrl = this.wtrImageUrl.substring(0, dotIndex)+'.jpg';
            }
          } else {
            this.wtrImageUrl = ''
          }
        } else {
          this.uploadWtrSuccess = false
          this.wtrImageUrl = ''
        }
        this.currentFrwts = JSON.parse(JSON.stringify(item))
        this.dlgAddMode = false
        this.hosSearchData = [
          { id: this.currentFrwts.hosId, hosName: this.currentFrwts.hosName }
        ]
        this.dlgVisible = true
        this.handleFreezeRemote()
      },
      handleDelete (item) {
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(_ => {
            this.axios
              .delete(this.baseURL + '/delFrwts', {
                params: {
                  id: item.id,
                  version: item.version
                }
              })
              .then(res => {
                if (res.data.code >= 0) {
                  this.$message({
                    type: 'success',
                    message: '删除证件信息成功。'
                  })
                  let removeIndex = this.frwtsList.findIndex(
                    o => o.id === item.id
                  )
                  this.frwtsList.splice(removeIndex, 1)
                  this.fTotal--
                } else {
                  this.$message({
                    type: 'error',
                    showClose: 'true',
                    duration: 0,
                    message: res.data.msg.substring(0, 50) + '...'
                  })
                }
              })
          })
          .catch(_ => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          })
      },
      debounceQuerySearchHosAsync: debounce(500, function (queryString) {
        this.axios
          .get(this.baseURL + '/getHosList', {
            params: {
              hosName: queryString
            }
          })
          .then(res => {
            if (res.data.code >= 0) {
              this.hosSearchData = res.data.data
              this.hosSearchLoading = false
            } else {
              this.$message.error(
                '获取数据失败。' + res.data.msg.substring(0, 50) + '...'
              )
              this.hosSearchLoading = false
            }
          })
          .catch(_ => {
            this.$message.error('数据异常，请联系管理员。')
          })
      }),
      querySearchHosAsync (queryString) {
        if (queryString !== '') {
          this.hosSearchLoading = true
          this.debounceQuerySearchHosAsync(queryString)
        }
      },
      handleFreezeRemote () {
        this.$set(this, 'remote', false)
        this.$nextTick(function () {
          this.$set(this, 'remote', true)
        })
      },
      handleHosSelect (item) {
        if (item) {
          const hos = this.hosSearchData.find(o => o.id === item)
          if (hos) {
            this.currentFrwts.hosName = hos.hosName
          }
        }
      },
      beforeUpload (file) {
        if (!this.allowedFileTypes.includes(file.type)) {
          this.$message.error(
            '文件格式不符合要求，请按要求上传' + this.allowedFileTypes.join(',') + '文件！'
          )
          return false
        }
        if (file.size / 1024 / 1024 > 15) {
          this.$message.error('上传图片大小不能超过15M！')
          return false
        }
      },
      handleWtrLocalAdd (file, fileList) {
        this.uploadWtrSuccess = false
        fileList.splice(0)
        fileList.push(file)
        // this.wtrImageUrl = file.url
      },
      handleWtrUploadErr () {
        this.$message.error('上传委托书失败，请联系管理员！')
      },
      handleWtrSuccess (response, file, fileList) {
        if (response.code < 0) {
          this.$message({
            type: 'error',
            showClose: 'true',
            duration: 0,
            message: file.name + '上传失败，' + response.msg.substring(0, 50) + '...'
          })
        } else {
          this.$message.success(file.name + '上传完成，请点击保存！')
          var path = file.response.data[0].path;
          var thumbnailId = file.response.data[0].thumbnailId;
          this.wtrImageUrl = this.picURLPrefix + path + "/" + thumbnailId;

          let { ext, id, path } = response.data[0]
          this.currentFrwts.filePath = path + '/' + id + '.' + ext

          this.$nextTick(() => {
            this.uploadWtrSuccess = true
          })
        }
      },
      dlgCancle () {
        this.dlgVisible = false
      },
      dlgConfirm () {
        this.$refs.frwtsForm.validate(valid => {
          if (valid) {
            this.dlgConfirmBtnLoading = true
            if (this.dlgAddMode) {
              // new
              this.axios
                .post(this.baseURL + '/addSalesRep', this.handleData)
                .then(res => {
                  if (res.data.code >= 0) {
                    this.$message.success('新增委托书成功。')
                    this.currentFrwts.id = res.data.data
                    this.frwtsList.push(this.currentFrwts)
                    this.fTotal++
                    this.currentFrwts = this.newFrwts()
                    this.dlgVisible = false
                  } else {
                    this.$message.error(
                      '新增授权书失败，' + res.data.msg.substring(0, 50) + '...'
                    )
                  }
                })
                .catch(_ => {
                  this.$message.error('数据异常，请联系管理员！')
                })
            } else {
              // modify
              this.axios
                .put(this.baseURL + '/modSalesRep', this.handleData)
                .then(res => {
                  if (res.data.code >= 0) {
                    this.$message.success('修改授权书成功。')
                    this.currentFrwts.version = res.data.data
                    let frwts = this.frwtsList.find(
                      o => o.id === this.currentFrwts.id
                    )
                    let {
                      hosId,
                      hosName,
                      salesName,
                      phoneNum,
                      filePath,
                      expdtBeginDate,
                      expdtEndDate,
                      version
                    } = this.currentFrwts
                    frwts.hosId = hosId
                    frwts.hosName = hosName
                    frwts.salesName = salesName
                    frwts.phoneNum = phoneNum
                    frwts.filePath = filePath
                    frwts.expdtBeginDate = expdtBeginDate
                    frwts.expdtEndDate = expdtEndDate
                    frwts.version = version
                    this.dlgVisible = false
                  } else {
                    this.$message.error(
                      '修改授权书信息失败，' + res.data.msg.substring(0, 50) + '...'
                    )
                  }
                })
                .catch(_ => {
                  this.$message.error('数据异常，请联系管理员！')
                })
            }
            this.dlgConfirmBtnLoading = false
          } else {
            this.$message.error('数据验证失败，请更正！')
          }
        })
      },
      getSalesReps () {
        let params = {
          name: this.searchName,
          pageNum: this.fPageNum,
          pageSize: this.fPageSize
        }
        if (this.searchExpdtBeginDate !== '') {
          params.start = Date.parse(this.searchExpdtBeginDate)
        }
        if (this.searchExpdtEndDate !== '') {
          params.end = Date.parse(this.searchExpdtEndDate)
        }
        this.axios
          .get(this.baseURL + '/getSalesReps', {
            params
          })
          .then(res => {
            if (res.data.code >= 0) {
              this.frwtsList = res.data.data.data
              this.fTotal = res.data.data.total
            } else {
              this.$message.error(res.data.msg.substring(0, 50) + '...')
            }
          })
          .catch(_ => {
            this.$message.error('数据异常，请联系管理员！')
          })
      },
      searchBtnClick () {
        this.getSalesReps()
      },
      picMouseEnter (event) {
        this.showBigPic = true
      },
      newFrwts () {
        return {
          id: '',
          provId: '',
          hosId: '',
          hosName: '',
          salesName: '',
          phoneNum: '',
          filePath: '',
          expdtBeginDate: '',
          expdtEndDate: '',
          version: 0
        }
      }
    },
    mounted () {
      this.getSalesReps()
    }
  }
</script>

<style>
  #provSalesRep .font {
    font-size: 12px;
  }
  #provSalesRep .el-date-editor.el-input {
    width: 120px;
  }
  #provSalesRep .provSalesRep-tableheight {
    height: calc(100% - 85px);
  }
  #provSalesRep .expiring {
    color: #ff00ff;
  }
  #provSalesRep .expired {
    color: red;
  }
  #provSalesRep .pic-uploader .el-upload {
    border: 1px dashed #9b9b9b;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  #provSalesRep .upload-background .el-upload::after {
    content: "";
    display: block;
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-size: 100%;
    background-image: url("../../assets/images/logo_200x200.png");
    background-repeat: no-repeat;
    opacity: 0.2;
  }
  #provSalesRep .pic-uploader .el-upload:hover {
    border-color: #20a0ff;
  }
  #provSalesRep .pic-uploader .item-status-label {
    position: absolute;
    right: -15px;
    top: -6px;
    width: 40px;
    height: 24px;
    background: #13ce66;
    text-align: center;
    transform: rotate(45deg);
    box-shadow: 0 0 1pc 1px rgba(0, 0, 0, 0.2);
  }
  #provSalesRep .pic-uploader .item-status-label .upload-success {
    font-size: 12px;
    margin-top: 11px;
    transform: rotate(-45deg) scale(0.8);
    color: #fff;
  }
  #provSalesRep .pic-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  #provSalesRep .pic {
    min-width: 50px;
    min-height: 50px;
    max-width: 178px;
    max-height: 178px;
    display: block;
  }
  #provSalesRep img.tooltip-pic {
    max-width: 400px;
  }
  #provSalesRep .el-upload-list__item,
  .el-upload-list__item:first-child {
    margin-top: 0;
    margin-bottom: 5px;
  }
  #provSalesRep .el-upload-list__item,
  .el-upload-list__item:first-child {
    margin-top: 0;
    margin-bottom: 5px;
  }
  #provSalesRep .font-center {
    font-size: 12px;
  }
  #provSalesRep .is-error {
    margin-bottom: 22px;
  }
  #provSalesRep .el-form-item__error {
    display: block;
  }
  #provSalesRep .inline {
    display: inline-block;
  }
  #provSalesRep .right {
    float: right;
  }
  .queryFormClass{
    padding-top: 2px;
    height: 30px;
    box-shadow: 0px 0px 3px 0px #a9a1a1 inset;
    border-radius: 4px;
    background-color: #f9f9f9;
    margin-bottom: 1px;
  }
  .fontStyle{
    font-style: italic;
    color: #c1c1c1;
  }
  #provSalesRep .el-dialog__footer {
    padding: 10px 20px 20px;
    text-align: center;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
  }
  #provSalesRep .el-table .warning-red {
    color: #F56C6C;
  }
  #provSalesRep .el-table .warning-row {
    color: #E6A23C;
  }
</style>
