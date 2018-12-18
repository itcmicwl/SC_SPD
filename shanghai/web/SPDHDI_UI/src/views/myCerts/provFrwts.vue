<template>
  <div id="provFrwts" style="height:100%;">
    <el-row class="queryFormClass">
      <div class="inline">
        <span style="margin-right: 5px; margin-left: 15px;color: #5f6366">效期</span>
        <el-date-picker type="date" placeholder="开始时间" v-model="searchExpdtBeginDate"  style="width:130px"></el-date-picker>
        <span>-</span>
        <el-date-picker type="date" placeholder="截止时间" v-model="searchExpdtEndDate"  style="width:130px"></el-date-picker>
      </div>
      <span style="margin-right: 5px; margin-left: 15px;color: #5f6366">委托单位</span>
      <div class="inline">
        <el-input placeholder="请输入委托单位" v-model="provName" clearable></el-input>
      </div>
      <span style="margin-right: 5px; margin-left: 15px;color: #5f6366">被委托单位</span>
      <div class="inline">
        <el-input placeholder="请输入被委托单位" v-model="name" clearable></el-input>
      </div>
      <div class="inline" style="margin-left: 5px;">
        <el-button type="primary" v-on:click="searchBtnClick" plain style="width: 80px;height: 26px" icon="el-icon-search">搜索</el-button>
        <el-button type="success" icon="el-icon-plus" plain style="height: 26px;margin-left: 0px;" @click="addPrepare" >新增委托书</el-button>
      </div>
    </el-row>
    <el-table highlight-current-row  class="provFrwts-tableheight" ref="frwtsTable" :row-class-name="tableRowClassName"
              :data="frwtsList" style="width: 100%" v-loading="fLoading" border height="outer">
      <el-table-column type="index" label="序号" width="40" align="center">
      </el-table-column>
      <el-table-column label="委托书编号" prop="id" width="115">
      </el-table-column>
      <el-table-column label="委托单位"  prop="provName">
      </el-table-column>
      <el-table-column label="被委托单位"  prop="hosName">
      </el-table-column>
      <el-table-column label="业务员" width="80" prop="wtrId">
      </el-table-column>
      <el-table-column label="业务员电话" width="90" prop="wtrPhone">
      </el-table-column>
      <el-table-column label="效期" align="center"  width="90">
        <template slot-scope="scope">
          <div>
            {{scope.row.expdtBeginDate?scope.row.expdtBeginDate.substr(0,10).replace(/-/g,'/'):''}}至
          </div>
          <div>
            {{scope.row.expdtEndDate?scope.row.expdtEndDate.substr(0,10).replace(/-/g,'/'):''}}
          </div>
          <!--<div :class="expdtDateClass(scope.row.expdtEndDate)">-->
          <!--{{scope.row.expdtEndDate?scope.row.expdtEndDate.substr(0,10).replace(/-/g,'/'):''}}-->
          <!--</div>-->
        </template>
      </el-table-column>
      <el-table-column  label="操作" width="170" header-align="center" align="center">
        <template slot-scope="scope">
          <el-button-group>
            <el-button size="mini" type="primary" @click.native.prevent="handleEdit(scope.row)" icon="el-icon-edit-outline">编辑</el-button>
            <el-button size="mini" type="info" @click="checkCert(scope.row.filePathWts)"  icon="el-icon-picture">证照</el-button>
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
    <el-dialog :title="dlgAddMode?'添加委托书':'委托书信息'" :visible.sync="dlgVisible" >
      <el-form :model="currentFrwts" ref="frwtsForm" label-width="100px" :rules="validateRules"
               class="el-form-item-nomsg">
        <el-row>
          <el-col :span="23">
            <el-form-item label="委托单位" required prop="provId">
              <el-select style="width: 100%"
                         @change="handleSelectChief($event)"
                         v-model="currentFrwts.provId"
                         ref="provName"
                         filterable clearable
                         remote
                         id="authObj"
                         reserve-keyword
                         placeholder="输入委托单位"
                         :remote-method="queryChiefAsync"
                         :loading="queryLoading">
                <el-option
                  v-for="item in chiefInfos"
                  :key="item.id"
                  :label="item.cname"
                  :value="item.id">
                  <el-col :span="22"><span style="float: left">{{ item.cname }}</span></el-col>
                  <el-col :span="2"><span style="float: right; color: #8492a6;font-style: italic;">
                          {{ getCorpKind(item.kind)}}</span>
                  </el-col>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="23">
            <el-form-item label="被委托单位" required prop="hosId">
              <el-select style="width: 100%"
                         @change="handleSelect"
                         v-model="currentFrwts.hosId"
                         ref="hosName"
                         filterable clearable
                         remote
                         id="authObj"
                         reserve-keyword
                         placeholder="输入被委托单位"
                         :remote-method="queryAllCorps"
                         :loading="queryLoading">
                <el-option
                  v-for="item in chiefInfos"
                  :key="item.id"
                  :label="item.cname"
                  :value="item.id">
                  <el-col :span="22"><span style="float: left">{{ item.cname }}</span></el-col>
                  <el-col :span="2"><span style="float: right; color: #8492a6;font-style: italic;">
                        {{ getCorpKind(item.kind)}}</span>
                  </el-col>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="11">
            <el-form-item label="业务员" required prop="wtrId">
              <el-input v-model="currentFrwts.wtrId"  class="font" placeholder="请输入业务员姓名"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话号码" required prop="wtrPhone">
              <el-input v-model="currentFrwts.wtrPhone"  class="font" placeholder="请输入联系电话"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="23">
            <el-form-item label="附件信息" required>
              <span class="fontStyle">注：只能上传jpg/png/bmp/pdf文件，且不超过15MB</span>
              <!--<span style="font-size:12px;color:grey;">注：只允许上传15M大小以下文件。</span>-->
              <el-form-item prop="filePathWts">
                <el-input v-model="currentFrwts.filePathWts" style="display:none" readonly
                          disabled></el-input>
                <el-upload class="pic-uploader" ref="wtsUpload" accept=".bmp,.jpg,.png,.pdf"
                           :headers="userInfo" :class="{'upload-background':!wtsImageUrl}"
                           :action="uploadURL" :auto-upload="true" :show-file-list="true"
                           :multiple="false" :on-success="handleWtsSuccess"
                           :on-change="handleWtsLocalAdd"
                           :before-upload="beforeUpload" :on-error="handleWtsUploadErr">
                  <label :class="{'item-status-label':uploadWtsSuccess}">
                    <i :class="{'upload-success el-icon-check':uploadWtsSuccess}"></i>
                  </label>
                  <img v-if="wtsImageUrl" :src="wtsImageUrl" class="pic">
                  <i v-else class="el-icon-plus pic-uploader-icon"></i>
                </el-upload>
              </el-form-item>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="效期" required>
          <el-col :span="9">
            <el-form-item prop="expdtBeginDate">
              <el-date-picker type="date" placeholder="开始日期" v-model="currentFrwts.expdtBeginDate"
                              style="width: 100%"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col style="text-align:center;" :span="1">-</el-col>
          <el-col :span="9">
            <el-form-item prop="expdtEndDate">
              <el-date-picker type="date" placeholder="结束日期" v-model="currentFrwts.expdtEndDate"
                              style="width: 100%"></el-date-picker>
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
  import { fileServer,PROV_CERT_TYPE } from '../../common/js/constance';
  import {mapGetters} from 'vuex';
  import moment from 'moment';                    //日期格式化
  export default {
    data () {
      let validateBeginDate = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('开始日期不能为空'))
        } else {
          if (this.currentFrwts.expdtEndDate) {
            this.$refs.frwtsForm.validateField('expdtEndDate');
          }
          callback();
        }
      };
      let validateEndDate = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('结束日期不能为空'))
        } else {
          if (Date.parse(this.currentFrwts.expdtBeginDate) > Date.parse(this.currentFrwts.expdtEndDate)) {
            return callback(new Error('结束日期必须大于开始日期'))
          }
          callback();
        }
      };
      return {
        queryLoading: false,
        chiefInfos:[],
        validateRules: {
          hosId: [
            {required: true, message: '请选择被委托单位', trigger: 'change'}
          ],
          provId: [
            {required: true, message: '请选择委托单位', trigger: 'change'}
          ],
          wtrId: [
            {required: true, message: '请输入业务员', trigger: 'change'}
          ],
          wtrPhone: [
            {required: true, message: '请输入业务员电话', trigger: 'change'}
          ],
          expdtBeginDate: [
            {validator: validateBeginDate, trigger: 'change'}
          ],
          expdtEndDate: [
            {validator: validateEndDate, trigger: 'change'}
          ],
          filePathWts: [
            {required: true, message: '请上传委托书照片', trigger: 'change'}
          ]/*,
                    filePathWtr: [
                        {required: true, message: '请上传委托人身份证照片', trigger: 'change'}
                    ]*/
        },
        baseURL: '/spdHDIService/provFrwts',
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
        wtsImageUrl: '',
        wtrImageUrl: '',
        allowedFileTypes: ['image/bmp', 'image/jpeg', 'image/png', 'application/pdf'],

        searchExpdtBeginDate: '',
        searchExpdtEndDate: '',
        name: '',
        provName:'',
        uploadWtsSuccess: false,
        uploadWtrSuccess: false,
        showBigPic: false
      }
    },
    computed: {
      handleData () {
        let frwtsData = JSON.parse(JSON.stringify(this.currentFrwts));
        frwtsData.expdtBeginDate = Date.parse(frwtsData.expdtBeginDate);
        frwtsData.expdtEndDate = Date.parse(frwtsData.expdtEndDate);
        // frwtsData.provId = this.getLoginUser.corpId;
        delete frwtsData.hosName;
        delete frwtsData.wtrname;
        return frwtsData;
      },
      largeWtsImageUrl () {
        if (this.currentFrwts.filePathWts && this.showBigPic && this.uploadWtsSuccess)
          return this.picURLPrefix + this.currentFrwts.filePathWts;
        else
          return this.wtsImageUrl;
      },
      largeWtrImageUrl () {
        if (this.currentFrwts.filePathWtr && this.showBigPic && this.uploadWtrSuccess)
          return this.picURLPrefix + this.currentFrwts.filePathWtr;
        else
          return this.wtrImageUrl;
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
      getCorpKind:function (kind) {
        if(kind==='2'){
          return '供应商';
        }else if(kind==='3'){
          return '厂商';
        }else{
          return '厂供一体';
        }
      },
      // 查询授权单位，包括厂商、供应商
      queryChiefAsync: function (queryString) {
        var param = {kind:'x',flag:'1'};
        this.queryLoading = true;
        this.axios.post('/spdHDIService/custProvManage/provInfo/getProvInfo', param).then(res => {
          this.queryLoading = false;
          if (res.data.code == "0") {
            this.chiefInfos = res.data.data.filter(o=>o.cname.indexOf(queryString)!=-1);
          }else {
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.queryLoading = false;
        });
      },
      queryChiefAsync2: function () {
        var param = {kind:'',flag:'1'};
        this.queryLoading = true;
        this.axios.post('/spdHDIService/custProvManage/provInfo/getProvInfo', param).then(res => {
          this.queryLoading = false;
          if (res.data.code == "0") {
            this.chiefInfos = res.data.data;
          }else {
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.queryLoading = false;
        });
      },
      // 查询授权单位，包括厂商、供应商
      queryAllCorps: function (queryString) {
        var param = {kind:'',flag:'1'};
        this.queryLoading = true;
        this.axios.post('/spdHDIService/custProvManage/provInfo/getProvInfo', param).then(res => {
          this.queryLoading = false;
          if (res.data.code == "0") {
            this.chiefInfos = res.data.data.filter(o=>o.cname.indexOf(queryString)!=-1);
          }else {
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.queryLoading = false;
        });
      },
      handleSelect(item) {
        if(item!==''&&item===this.currentFrwts.provId){
          this.$message.error("委托单位不能与被委托单位相同!");
          this.currentFrwts.hosId = '';
          return;
        }
      },
      handleSelectChief(id) {
        let query = this.$refs.provName.query;
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
        this.showBigPic = false;
        this.uploadWtsSuccess = false;
        this.uploadWtrSuccess = false;
        this.wtsImageUrl = '';
        this.wtrImageUrl = '';
        this.currentFrwts = this.newFrwts();
        this.hosSearchData = [];
        this.dlgAddMode = true;
        this.dlgVisible = true;
        this.$refs['frwtsForm'].resetFields();
      },
      handlefSizeChange (val) {
        this.fPageSize = val;
        this.getFrwts();
      },
      handlefPageChange (val) {
        this.fPageNum = val;
        this.getFrwts();
      },
      handleEdit(item) {
        this.queryChiefAsync2();
        this.showBigPic = false;
        if (item.filePathWts) {
          this.uploadWtsSuccess = true;
          let slashIndex = item.filePathWts.lastIndexOf("/");
          if (slashIndex !== -1) {
            this.wtsImageUrl = this.picURLPrefix + item.filePathWts.substring(0, slashIndex) + '/thumb-' + item.filePathWts.substring(slashIndex + 1);
            // 处理PDF缩略图，一般情况下，缩略图格式与原图一致，但系统中PDF的缩略图为jpg格式
            let dotIndex = this.wtsImageUrl.lastIndexOf('.');
            let ext = this.wtsImageUrl.substring(dotIndex + 1);
            if(ext==='PDF'||ext==='pdf'){
              this.wtsImageUrl = this.wtsImageUrl.substring(0, dotIndex)+'.jpg';
            }
          } else {
            this.wtsImageUrl = '';
          }
        } else {
          this.uploadWtsSuccess = false;
          this.wtsImageUrl = '';
        }
        this.currentFrwts = JSON.parse(JSON.stringify(item));
        this.dlgAddMode = false;
        this.hosSearchData = [{id: this.currentFrwts.hosId, hosName: this.currentFrwts.hosName}];
        this.dlgVisible = true;
        // this.handleFreezeRemote();
      },
      handleDelete(item) {
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(_ => {
          this.axios.delete(this.baseURL + '/delFrwts', {
            params: {
              'id': item.id,
              'version': item.version
            }
          }).then(res => {
            if (res.data.code >= 0) {
              this.$message({
                type: 'success',
                message: '删除证件信息成功。'
              });
              let removeIndex = this.frwtsList.findIndex(o => o.id === item.id);
              this.frwtsList.splice(removeIndex, 1);
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
        }).catch(_ => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
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
        })
      }),
      querySearchHosAsync (queryString) {
        if (queryString !== '') {
          this.hosSearchLoading = true;
          this.debounceQuerySearchHosAsync(queryString);
        }
      },
      handleFreezeRemote () {
        this.$set(this, 'remote', false);
        this.$nextTick(function () {
          this.$set(this, 'remote', true);
        });
      },
      handleHosSelect (item) {
        if (item) {
          const hos = this.hosSearchData.find(o => o.id === item);
          if (hos)
            this.currentFrwts.hosName = hos.hosName;
        }
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
      handleWtsLocalAdd (file, fileList){
        this.uploadWtsSuccess = false;
        fileList.splice(0);
        fileList.push(file);
      },
      handleWtsUploadErr (err, file, fileList) {
        this.$message.error("上传委托书失败，请联系管理员！")
      },
      handleWtsSuccess (response, file, fileList) {
        if (response.code < 0) {
          this.$message({
            type: 'error',
            showClose: 'true',
            duration: 0,
            message: file.name + '上传失败，' + response.msg.substring(0, 50) + '...'
          })
        } else {
          this.$message.success(file.name + '上传完成，请点击保存！');
          var path = file.response.data[0].path;
          var thumbnailId = file.response.data[0].thumbnailId;
          this.wtsImageUrl = this.picURLPrefix + path + "/" + thumbnailId;

          let {ext, id, path} = response.data[0];
          this.currentFrwts.filePathWts = path + "/" + id + "." + ext;

          this.$nextTick(() => {
            this.uploadWtsSuccess = true
          });
        }
      },
      handleWtrLocalAdd (file, fileList){
        this.uploadWtrSuccess = false;
        fileList.splice(0);
        fileList.push(file);
        this.wtrImageUrl = file.url;
      },
      handleWtrUploadErr (err, file, fileList) {
        this.$message.error("上传委托书失败，请联系管理员！")
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
          this.$message.success(file.name + '上传完成，请点击保存！');
          let {ext, id, path} = response.data[0];
          this.currentFrwts.filePathWtr = path + "/" + id + "." + ext;

          this.$nextTick(() => {
            this.uploadWtrSuccess = true
          });
        }
      },
      dlgCancle () {
        this.dlgVisible = false;
      },
      dlgConfirm () {
        this.$refs.frwtsForm.validate((valid) => {
          if (valid) {
            this.dlgConfirmBtnLoading = true;
            if (this.dlgAddMode) { // new
              this.axios.post(this.baseURL + '/addFrwts', this.handleData).then(res => {
                if (res.data.code >= 0) {
                  this.$message.success('新增委托书成功。');
                  // this.currentFrwts.id = res.data.data;
                  this.getFrwts();
                  // this.frwtsList.push(this.currentFrwts);
                  // this.fTotal++;
                  // this.currentFrwts = this.newFrwts();
                  this.dlgVisible = false;
                } else {
                  this.$message.error('新增授权书失败，' + res.data.msg.substring(0, 50) + '...');
                }
              }).catch(err => {
                this.$message.error('数据异常，请联系管理员！');
              })
            } else { // modify
              this.axios.put(this.baseURL + '/modFrwts', this.handleData).then(res => {
                if (res.data.code >= 0) {
                  this.$message.success('修改授权书成功。');
                  this.currentFrwts.version = res.data.data;
                  let frwts = this.frwtsList.find(o => o.id === this.currentFrwts.id);
                  let {
                    hosId, hosName, wtrId, wtrName, wtrPhone, filePathWts,
                    filePathWtr, expdtBeginDate, expdtEndDate, version
                  } = this.currentFrwts;
                  frwts.hosId = hosId;
                  frwts.hosName = hosName;
                  frwts.wtrId = wtrId;
                  frwts.wtrname = wtrName;
                  frwts.wtrPhone = wtrPhone;
                  frwts.filePathWts = filePathWts;
                  frwts.filePathWtr = filePathWtr;
                  frwts.expdtBeginDate = expdtBeginDate;
                  frwts.expdtEndDate = expdtEndDate;
                  frwts.version = version;
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
      getFrwts() {
        let params = {
          'name': this.name,
          'provName': this.provName,
          'pageNum': this.fPageNum,
          'pageSize': this.fPageSize
        };
        if (this.searchExpdtBeginDate)
          params.start = moment(this.searchExpdtBeginDate).format("YYYY-MM-DD 00:00:00");//Date.parse(this.searchExpdtBeginDate);
        if (this.searchExpdtEndDate)
          params.end = moment(this.searchExpdtEndDate).format("YYYY-MM-DD 23:59:59");//Date.parse(this.searchExpdtEndDate);
        this.fLoading = true;
        this.axios.get(this.baseURL + '/getFrwts', {
          params
        }).then(res => {
          if (res.data.code >= 0) {
            this.frwtsList = res.data.data.data;
            this.fTotal = res.data.data.total;
          } else {
            this.$message.error(res.data.msg.substring(0, 50) + '...');
          }
          this.fLoading = false;
        }).catch(err => {
          this.fLoading = false;
          this.$message.error('数据异常，请联系管理员！');
        })
      },
      searchBtnClick () {
        this.getFrwts();
      },
      picMouseEnter (event) {
        this.showBigPic = true;
      },
      newFrwts() {
        return {
          id: '',
          provId: '',
          hosId: '',
          hosName: '',
          wtrId: '',
          wtrName: '',
          wtrPhone: '',
          filePathWts: '',
          filePathWtr: '',
          expdtBeginDate: '',
          expdtEndDate: '',
          version: 0
        }
      }
    },
    mounted () {
      this.getFrwts();
    }
  }
</script>

<style>
  #provFrwts .font {
    font-size: 12px;
  }
  #provFrwts .el-date-editor.el-input {
    width: 120px;
  }
  #provFrwts .provFrwts-tableheight {
    height: calc(100% - 85px);
  }
  #provFrwts .expiring {
    color: #ff00ff;
  }
  #provFrwts .expired {
    color: red;
  }
  #provFrwts .pic-uploader .el-upload {
    border: 1px dashed #9b9b9b;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  #provFrwts .upload-background .el-upload::after {
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
  #provFrwts .pic-uploader .el-upload:hover {
    border-color: #20a0ff;
  }
  #provFrwts .pic-uploader .item-status-label {
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
  #provFrwts .pic-uploader .item-status-label .upload-success {
    font-size: 12px;
    margin-top: 11px;
    transform: rotate(-45deg) scale(.8);
    color: #fff;
  }
  #provFrwts .pic-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  #provFrwts .pic {
    min-width: 50px;
    min-height: 50px;
    max-width: 178px;
    max-height: 178px;
    display: block;
  }
  #provFrwts img.tooltip-pic {
    max-width: 400px;
  }
  #provFrwts .el-upload-list__item, .el-upload-list__item:first-child {
    margin-top: 0;
    margin-bottom: 5px;
  }
  #provFrwts .el-upload-list__item, .el-upload-list__item:first-child {
    margin-top: 0;
    margin-bottom: 5px;
  }
  #provFrwts .font-center {
    font-size: 12px;
    text-align: center;
  }
  #provFrwts .is-error {
    margin-bottom: 22px;
  }
  #provFrwts .el-form-item__error {
    display: block;
  }
  #provFrwts .inline {
    display: inline-block;
  }
  #provFrwts .right {
    float: right;
  }
  .queryFormClass{
    padding-top: 2px;
    margin-bottom: 1px;
    height: 30px;
    box-shadow: 0px 0px 3px 0px #a9a1a1 inset;
    border-radius: 4px;
    background-color: #f9f9f9;
  }
  #provFrwts .el-table .cell, .el-table th > div {
    padding-bottom: 1px;
    padding-top: 1px;
    line-height: 14px;
    padding-left: 5px;
    padding-right: 5px;
  }
  .fontStyle{
    font-style: italic;
    color: #c1c1c1;
  }
  #provFrwts .el-dialog__footer {
    padding: 10px 20px 20px;
    text-align: center;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
  }
  #provFrwts .el-table .warning-red {
    color: #F56C6C;
  }
  #provFrwts .el-table .warning-row {
    color: #E6A23C;
  }
</style>
