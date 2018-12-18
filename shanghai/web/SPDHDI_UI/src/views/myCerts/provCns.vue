<template>
  <div id="provCns" style="height:100%">
    <el-row class="queryFormClass">
      <div class="inline">
        <span style="margin-right: 5px; margin-left: 15px;color: #5f6366">效期</span>
        <el-date-picker type="date" placeholder="开始日期" v-model="searchExpdtBeginDate" style="width:130px"></el-date-picker>
        <span>-</span>
        <el-date-picker type="date" placeholder="结束日期" v-model="searchExpdtEndDate" style="width:130px"></el-date-picker>
      </div>
      <span style="margin-right: 5px; margin-left: 15px;color: #5f6366">医院名称</span>
      <div class="inline">
        <el-input placeholder="请输入医院" v-model="searchName"
                  @keyup.enter.native="searchBtnClick">
        </el-input>
      </div>
      <div class="inline" style="margin-left: 5px;">
        <el-button type="primary" v-on:click="searchBtnClick" plain style="width: 80px;height: 26px" icon="el-icon-search">搜索</el-button>
        <el-button type="success" icon="el-icon-plus" plain style="height: 26px;margin-left: 0px;" @click="addPrepare" >新增承诺书</el-button>
      </div>
    </el-row>
    <el-table highlight-current-row  ref="cnsTable" class="provCns-tableheight" :row-class-name="tableRowClassName"
              :data="cnsList" style="width: 100%" v-loading="cLoading" border height="outer">
      <el-table-column label="承诺书编号" prop="id" width="120">
      </el-table-column>
      <el-table-column label="承诺医院" show-overflow-tooltip prop="hosName">
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
            <el-button size="mini" type="info"    @click="checkCert(scope.row.filePathCns)"  icon="el-icon-picture">证照</el-button>
            <el-button size="mini" type="danger"  @click.native.prevent="handleDelete(scope.row)" plain icon="el-icon-delete">删除</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-if="cTotal > 0" style="float:right" small @size-change="handleCSizeChange"
                   @current-change="handleCPageChange"
                   :current-page.sync="cPageNum" layout="total, sizes, prev, pager, next"
                   :page-sizes="[10, 15, 20, 30]" :page-size="cPageSize" :total="cTotal">
    </el-pagination>

    <el-dialog :title="dlgAddMode?'添加承诺书':'承诺书信息'" :visible.sync="dlgVisible" >
      <el-form :model="currentCns" ref="cnsForm" label-width="100px" :rules="validateRules"
               class="el-form-item-nomsg">
        <el-row>
          <el-col :span="23">
            <el-form-item label="承诺医院" required prop="hosId">
              <el-select v-model="currentCns.hosId" style="width: 100%" filterable :remote="remote" placeholder="请输入关键词"
                         :remote-method="querySearchHosAsync" :loading="hosSearchLoading"
                         clearable @input="handleFreezeRemote" @change="handleHosSelect">
                <el-option v-for="item in hosSearchData" :key="item.id" :label="item.hosName" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="承诺书信息" prop="filePathCns" required>
          <el-input v-model="currentCns.filePathCns" style="display:none" readonly
                    disabled></el-input>
          <span class="fontStyle">注：只能上传jpg/png/bmp/pdf文件，且不超过15MB</span>
          <el-upload class="pic-uploader" ref="cnsUpload" accept=".bmp,.jpg,.png,.pdf"
                     :headers="userInfo" :class="{'upload-background':!cnsImageUrl}"
                     :action="uploadURL" :auto-upload="true" :show-file-list="true"
                     :multiple="false" :on-success="handleCnsSuccess" :on-change="handleCnsLocalAdd"
                     :before-upload="beforeUpload" :on-error="handleCnsUploadErr">
            <label :class="{'item-status-label':uploadSuccess}">
              <i :class="{'upload-success el-icon-check':uploadSuccess}"></i>
            </label>
            <img v-if="cnsImageUrl" :src="cnsImageUrl" class="pic">
            <i v-else class="el-icon-plus pic-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="承诺有效期" required>
          <el-col :span="9">
            <el-form-item prop="expdtBeginDate">
              <el-date-picker type="date" placeholder="开始日期" v-model="currentCns.expdtBeginDate"
                              style="width: 100%;" ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col style="text-align:center;" :span="1">-</el-col>
          <el-col :span="9">
            <el-form-item prop="expdtEndDate">
              <el-date-picker type="date" placeholder="结束日期" v-model="currentCns.expdtEndDate"
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
  import debounce from 'throttle-debounce/debounce';
  import {mapGetters} from 'vuex';
  import { fileServer,PROV_CERT_TYPE } from '../../common/js/constance';
  import moment from "moment";

  export default {
    data () {
      let validateBeginDate = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('开始日期不能为空'))
        } else {
          if (this.currentCns.expdtEndDate) {
            this.$refs.cnsForm.validateField('expdtEndDate');
          }
          callback();
        }
      };
      let validateEndDate = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('结束日期不能为空'))
        } else {
          if (Date.parse(this.currentCns.expdtBeginDate) > Date.parse(this.currentCns.expdtEndDate)) {
            return callback(new Error('结束日期必须大于开始日期'))
          }
          callback();
        }
      };
      return {
        validateRules: {
          hosId: [
            {required: true, message: '请选择医院', trigger: 'change'}
          ],
          expdtBeginDate: [
            {validator: validateBeginDate, trigger: 'change'}
          ],
          expdtEndDate: [
            {validator: validateEndDate, trigger: 'change'}
          ],
          filePathCns: [
            {required: true, message: '请上传承诺书照片', trigger: 'change'}
          ]
        },
        baseURL: '/spdHDIService/provCns',
        uploadURL: '/platformService/sys/upload/file/certs/',
        picURLPrefix: '/upload/',
        searchExpdtBeginDate: '',
        searchExpdtEndDate: '',
        searchName: '',

        cPageNum: 1,
        cPageSize: 30,
        cTotal: 0,
        cLoading: false,
        cnsList: [],
        currentCns: {},

        hosSearchLoading: false,
        hosSearchData: [],
        remote: true,

        cnsImageUrl: '',

        dlgAddMode: false,
        dlgVisible: false,
//                uploadCnsBtnLoading: false,
        dlgConfirmBtnLoading: false,
        uploadSuccess: false,
        showBigPic: false,

        allowedFileTypes: ['image/bmp', 'image/jpeg', 'image/png', 'application/pdf'],
      }
    },
    computed: {
      largeCnsImageUrl () {
        if (this.currentCns.filePathCns && this.showBigPic && this.uploadSuccess)
          return this.picURLPrefix + this.currentCns.filePathCns;
        else
          return this.cnsImageUrl;
      },
      handleData() {
        let cnsData = JSON.parse(JSON.stringify(this.currentCns));
        cnsData.expdtBeginDate = Date.parse(cnsData.expdtBeginDate);
        cnsData.expdtEndDate = Date.parse(cnsData.expdtEndDate);
        delete cnsData.hosName;
        return cnsData;
      },
      userInfo () {
        return {
          'X-US': this.getLoginUser.xus
        }
      },
      ...mapGetters(["getLoginUser"])
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
        return new Date(item).toLocaleDateString();
      },
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
      addPrepare () {
        this.cnsImageUrl = '';
        this.hosSearchData = [];
        this.showBigPic = false;
        this.uploadSuccess = false;
        this.dlgAddMode = true;
        this.currentCns = this.newCns();
        this.dlgVisible = true;
        this.$refs['cnsForm'].resetFields();
      },
      searchBtnClick () {
        this.getCns();
      },
      debounceQuerySearchHosAsync: debounce(500, function (queryString) {
        this.axios.get(this.baseURL + '/getHosList', {
          params: {
            'hosName': queryString
          }
        }).then(res => {
          if (res.data.code >= 0) {
            this.hosSearchData = res.data.data;
            this.hosSearchLoading = false;
          } else {
            this.$message.error("获取数据失败。" + res.data.msg.substring(0, 50) + '...');
            this.hosSearchLoading = false;
          }
        }).catch(err => {
          this.$message.error('数据异常，请联系管理员。')
        });
      }),
      querySearchHosAsync (queryString) {
        if (queryString !== '') {
          this.hosSearchLoading = true;
          this.debounceQuerySearchHosAsync(queryString);
        }
      },
      handleHosSelect (item) {
        if (item) {
          const hos = this.hosSearchData.find(o => o.id === item);
          if (hos)
            this.currentCns.hosName = hos.hosName;
        }
      },
      handleFreezeRemote () {
        this.$set(this, 'remote', false);
        this.$nextTick(function () {
          this.$set(this, 'remote', true);
        });
      },
      handleCSizeChange (val) {
        this.cPageSize = val;
        this.getCns();
      },
      handleCPageChange (val) {
        this.cPageNum = val;
        this.getCns();
      },
      handleEdit (item) {
        this.showBigPic = false;
        if (item.filePathCns) {
          this.uploadSuccess = true;
          let slashIndex = item.filePathCns.lastIndexOf("/");
          if (slashIndex !== -1) {
            this.cnsImageUrl = this.picURLPrefix + item.filePathCns.substring(0, slashIndex) + '/thumb-' + item.filePathCns.substring(slashIndex + 1);
            // 处理PDF缩略图，一般情况下，缩略图格式与原图一致，但系统中PDF的缩略图为jpg格式
            let dotIndex = this.cnsImageUrl.lastIndexOf('.');
            let ext = this.cnsImageUrl.substring(dotIndex + 1);
            if(ext==='PDF'||ext==='pdf'){
              this.cnsImageUrl = this.cnsImageUrl.substring(0, dotIndex)+'.jpg';
            }
          } else {
            this.cnsImageUrl = '';
          }
        } else {
          this.uploadSuccess = false;
          this.cnsImageUrl = '';
        }
        this.currentCns = JSON.parse(JSON.stringify(item));
        this.hosSearchData = [
          {id: this.currentCns.hosId, hosName: this.currentCns.hosName}
        ];
        this.dlgAddMode = false;
        this.dlgVisible = true;
        this.handleFreezeRemote();
      },
      handleDelete (item) {
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(_ => {
          this.axios.delete(this.baseURL + '/delCns', {
            params: {
              'id': item.id,
              'version': item.version
            }
          }).then(res => {
            if (res.data.code >= 0) {
              this.$message({
                type: 'success',
                message: '删除承诺书信息成功。'
              });
              let removeIndex = this.cnsList.findIndex(o => o.id === item.id);
              this.cnsList.splice(removeIndex, 1);
              this.cTotal--
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
      handleCnsLocalAdd (file, fileList) {
        this.uploadSuccess = false;
        fileList.splice(0);
        fileList.push(file);
      },
      beforeUpload (file) {
        if (!this.allowedFileTypes.includes(file.type)) {
          this.$message.error('文件格式不符合要求，请按要求上传' + this.allowedFileTypes.join(',') + '文件！');
          return false;
        }
        if (file.size / 1024 / 1024 > 15) {
          this.$message.error("上传图片大小不能超过15M！");
          return false;
        }
      },
      handleCnsSuccess (response, file, fileList) {
        if (response.code < 0) {
          this.$message({
            type: 'error',
            showClose: 'true',
            duration: 0,
            message: file.name + '上传失败，' + response.msg.substring(0, 50) + '...'
          })
        } else {
          this.$message.success(file.name + '上传成功！');
          var path = file.response.data[0].path;
          var thumbnailId = file.response.data[0].thumbnailId;
          this.cnsImageUrl = this.picURLPrefix + path + "/" + thumbnailId;

          let {ext, id, path} = response.data[0];
          this.currentCns.filePathCns = path + "/" + id + "." + ext;
          this.$refs.cnsForm.validateField('filePathCns');
          this.$nextTick(() => {
            this.uploadSuccess = true
          });
        }
      },
      handleCnsUploadErr (err, file, fileList) {
        this.$message.error("上传图片失败，请联系管理员！");
      },
      dlgCancle () {
        this.dlgVisible = false;
      },
      dlgConfirm () {
        this.$refs.cnsForm.validate((valid) => {
          if (valid) {
            this.dlgConfirmBtnLoading = true;
            if (this.dlgAddMode) { // new
              this.axios.post(this.baseURL + '/addCns', this.handleData).then(res => {
                if (res.data.code >= 0) {
                  this.$message.success('新增承诺书成功。');
                  this.currentCns.id = res.data.data;
                  this.cnsList.push(this.currentCns);
                  this.cTotal++;
                  this.currentCns = this.newCns();
                  this.dlgVisible = false;
                } else {
                  this.$message.error('新增承诺书失败，' + res.data.msg.substring(0, 50) + '...');
                }
              }).catch(err => {
                this.$message.error('数据异常，请联系管理员！');
              })
            } else { // modify
              this.axios.put(this.baseURL + '/modCns', this.handleData).then(res => {
                if (res.data.code >= 0) {
                  this.$message.success('修改授权书成功。');
                  this.currentCns.version = res.data.data;
                  this.changeTableData(this.currentCns);
                  this.dlgVisible = false;
                } else {
                  this.$message.error('修改授权书信息失败，' + res.data.msg.substring(0, 50) + '...');
                }
              }).catch(err => {
                this.$message.error('数据异常，请联系管理员！');
              })
            }
            this.dlgConfirmBtnLoading = false;
          } else {
            this.$message.error('数据验证失败，请更正！');
          }
        })
      },
      getCns () {
        let params = {
          'hosName': this.searchName,
          'pageNum': this.cPageNum,
          'pageSize': this.cPageSize
        };
        if (this.searchExpdtBeginDate !== '')
          params.start = Date.parse(this.searchExpdtBeginDate);
        if (this.searchExpdtEndDate !== '')
          params.end = Date.parse(this.searchExpdtEndDate);
        this.axios.get(this.baseURL + '/getCns', {
          params
        }).then(res => {
          if (res.data.code >= 0) {
            this.cnsList = res.data.data.data;
            this.cTotal = res.data.data.total;
          } else {
            this.$message.error(res.data.msg.substring(0, 50) + '...');
          }
        }).catch(err => {
          this.$message.error('数据异常，请联系管理员！');
        })
      },
      changeTableData (item) {
        let cns = this.cnsList.find(o => o.id === item.id);
        let {hosId, hosName, filePathCns, expdtBeginDate, expdtEndDate, version} = item;
        cns.hosId = hosId;
        cns.hosName = hosName;
        cns.filePathCns = filePathCns;
        cns.expdtBeginDate = expdtBeginDate;
        cns.expdtEndDate = expdtEndDate;
        cns.version = version;
      },
      picMouseEnter (event) {
        this.showBigPic = true;
      },
      newCns () {
        return {
          id: '',
          provId: this.getLoginUser.corpId,
          hosId: '',
          hosName: '',
          filePathCns: '',
          expdtBeginDate: '',
          expdtEndDate: '',
          version: 0
        }
      }
    },
    mounted () {
      this.getCns();
    }
  }
</script>

<style>
  #provCns .font {
    font-size: 12px;
  }
  #provCns .el-date-editor.el-input {
    width: 120px;
  }
  #provCns .fullheight {
    height: 100%;
  }
  #provCns .provCns-tableheight {
    height: calc(100% - 85px);
  }
  #provCns .expiring {
    color: #ff00ff;
  }
  #provCns .expired {
    color: red;
  }
  #provCns .pic-uploader .el-upload {
    border: 1px dashed #9b9b9b;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  #provCns .upload-background .el-upload::after {
    content: '';
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
    opacity: 0.20;
  }
  #provCns .pic-uploader .el-upload:hover {
    border-color: #20a0ff;
  }
  #provCns .pic-uploader .item-status-label {
    position: absolute;
    right: -15px;
    top: -6px;
    width: 40px;
    height: 24px;
    background: #13ce66;
    text-align: center;
    transform: rotate(45deg);
    box-shadow: 0 0 1pc 1px rgba(0, 0, 0, .2);
  }
  #provCns .pic-uploader .item-status-label .upload-success {
    font-size: 12px;
    margin-top: 11px;
    transform: rotate(-45deg) scale(.8);
    color: #fff;
  }
  #provCns .pic-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  #provCns .pic {
    min-width: 50px;
    min-height: 50px;
    max-width: 178px;
    max-height: 178px;
    display: block;
  }
  #provCns img.tooltip-pic {
    max-width: 400px;
  }
  #provCns .el-upload-list__item, .el-upload-list__item:first-child {
    margin-top: 0;
    margin-bottom: 5px;
  }
  #provCns .is-error {
    margin-bottom: 22px;
  }
  #provCns .el-form-item__error {
    display: block;
  }
  #provCns .inline {
    display: inline-block;
  }
  #provCns .right {
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
  #provCns .el-dialog__footer {
    padding: 10px 20px 20px;
    text-align: center;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
  }
  #provCns .el-table .warning-red {
    color: #F56C6C;
  }
  #provCns .el-table .warning-row {
    color: #E6A23C;
  }
</style>
