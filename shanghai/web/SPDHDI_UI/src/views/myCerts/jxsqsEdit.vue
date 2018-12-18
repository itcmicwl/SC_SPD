<template>
  <div id="jxsqsEdit" style="height: 100%;margin-left: 8px;">
    <div style="height: 85%">
    <el-form :model="jxsqsVo" label-width="75px" :rules="editFormRules" ref="editForm" style="height: 43%"
             class="jxsqsEditClass" >
      <el-col :span="15">
        <el-col :span="19">
          <el-form-item label="授权单位">
            <el-select style="width: 100%"
              @change="handleSelectChief"
              v-model="jxsqsVo.chiefId"
              filterable clearable
              remote
              reserve-keyword
              placeholder="输入授权单位"
              :remote-method="queryChiefAsync"
              :loading="loading">
              <el-option
                v-for="item in chiefInfos"
                :key="item.id"
                :label="item.cname"
                track-by="$index"
                :value="item.id">
                <el-col :span="22"><span style="float: left">{{ item.cname }}</span></el-col>
                <el-col :span="2"><span style="float: right; color: #8492a6;font-style: italic;">
                  {{ getCorpKind(item.kind)}}</span>
                </el-col>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="我是厂家">
            <el-checkbox v-model="ifMfrs" :disabled="isDisabled" @change="changeIfMfrs"></el-checkbox>
          </el-form-item>
        </el-col>
        <el-col  :span="24">
          <el-form-item label="被授权单位">
            <el-select style="width: 100%"
              @change="handleSelect"
              v-model="jxsqsVo.provId"
              filterable clearable
              remote
              reserve-keyword
              placeholder="输入授权单位"
              :remote-method="queryChiefAsync"
              :loading="loading">
              <el-option
                v-for="item in chiefInfos"
                :key="item.id"
                track-by="$index"
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
        <el-col  :span="24" style="margin-bottom: -8px;">
          <el-form-item label="授权效期">
            <el-col :span="6">
              <el-form-item prop="expdtBeginDate">
                <el-date-picker type="date" placeholder="起始日期" v-model="jxsqsVo.expdtBeginDate"
                                style="width: 100%;" ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col style="text-align:center;" :span="1">-</el-col>
            <el-col :span="6">
              <el-form-item prop="expdtEndDate">
                <el-date-picker type="date" placeholder="截止日期" v-model="jxsqsVo.expdtEndDate"
                                style="width: 100%;" ></el-date-picker>
              </el-form-item>
            </el-col>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="授权区域" prop="remark">
            <el-input type="textarea" :row="2" v-model="jxsqsVo.saleArea"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24" style="margin-bottom: -5px;">
          <el-form-item label="授权品种" prop="legal">
            <el-col :span="3">
              <el-button type="info" size="mini" @click="selectGoods" style="width: 96px;height: 28px;" round plain icon="el-icon-plus">选择品种</el-button>
            </el-col>
          </el-form-item>
        </el-col>
        <el-col style="margin-top: 10px;">
          <el-form :inline="true" label-width="80px" :model="filters">
            <el-form-item label="">
              <el-input v-model="goodsFilters.goodsName" style="height: 24px;" placeholder="输入商品名称或规格" clearable></el-input>
            </el-form-item>
            <el-form-item label="">
              <el-input v-model="goodsFilters.mfrsName" style="height: 24px;" placeholder="输入生产厂家" clearable></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" v-on:click="getJxsqsGoodsInfo(1)" plain style="width: 73px;height: 28px;"  icon="el-icon-search">搜索</el-button>
            </el-form-item>
          </el-form>
        </el-col>
        </el-col>
      <el-col :span="8" style="margin-left: 30px;">
        <span style="line-height: 29px;">上传附件：</span>
        <span class="fontStyle">只能上传jpg/png/bmp/pdf文件，且不超过15MB</span>
        <div class="uploadJxsqsClass">
        <el-upload
          ref="uploadJxsqs"
          accept=".bmp,.jpg,.png,.pdf"
          :headers="userInfo"
          :multiple=false
          :action="uploadCompanyCertsUrl"
          :show-file-list="true"
          :file-list="fileList"
          :on-change="handleCnsLocalAdd"
          :before-upload="beforeUpload" :on-error="handleCnsUploadErr"
          :on-success="handleJxsqsSuccess"
          :on-preview="checkCerts"
          :on-remove="deleteJxsqsCert"
          :auto-upload="autoUpload">
          <img v-if="imageUrl" :src="imgBasePath+imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        </div>
      </el-col>
    </el-form>
    <el-col :span="24" style="height: 57%">
        <el-table :data="selectedGoodsInfos" highlight-current-row
                  border fit max-height="280" style="width: 100%">
          <el-table-column prop="goodsName" label="商品名称"  header-align="center">
          </el-table-column>
          <el-table-column prop="goodsGg" label="规格型号"  header-align="center">
          </el-table-column>
          <el-table-column prop="unit" label="单位" align="center" width="40">
          </el-table-column>
          <el-table-column prop="mfrsName" label="生产厂商"  header-align="center">
          </el-table-column>
          <el-table-column prop="made" label="产地" align="center" width="60">
          </el-table-column>
          <el-table-column label="操作" width="80" header-align="center">
            <template slot-scope="scope">
              <el-button type="danger" style="height: 18px;padding: 2px 10px;" size="mini" @click="deleteSelectedGoods(scope.row)"
                         plain icon="el-icon-delete">移除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--工具条-->
        <el-col :span="24" class="toolbar" v-show="jgtotal>0">
          <el-pagination @size-change="jghandleSizeChange" @current-change="jghandleCurrentChange" :current-page="jgpage"
                         :page-sizes="[20, 50, 100, 200]" :page-size="jgpageSize"
                         layout="total, sizes, prev, pager, next" :total="jgtotal" style="float:right;" small>
          </el-pagination>
        </el-col>
      </el-col>
    <el-dialog title="选择品种" :visible.sync="goodsInfoVisible" top="6vh" width="75%">
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" label-width="80px" :model="filters">
          <el-form-item label="商品名称">
            <el-input v-model="filters.goodsName" placeholder="输入商品名称或规格" clearable></el-input>
          </el-form-item>
          <el-form-item label="生产厂家">
            <el-input v-model="filters.mfrsName" placeholder="输入生产厂家" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" v-on:click="getGoodsList(1)" plain style="width:80px;height:27px;"  icon="el-icon-search">搜索</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <el-table :data="goodsInfos" ref="goodsTable" @select="handleGoodsSelectionChange"
                @select-all="selectAllGoods" highlight-current-row
                style="width: 100%;" border fit max-height="400">
        <el-table-column type="selection" align="center"  width="40"></el-table-column>
        <el-table-column prop="goodsName" label="商品名称" header-align="center" min-width="200">
        </el-table-column>
        <el-table-column prop="goodsGg" label="规格型号" header-align="center">
        </el-table-column>
        <el-table-column prop="made" label="产地" align="center" width="60">
        </el-table-column>
        <el-table-column prop="mfrsName" label="生产厂商" header-align="center" min-width="180"></el-table-column>
        <el-table-column prop="unit" label="单位" align="center" width="40">
        </el-table-column>
      </el-table>
      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination @size-change="ghandleSizeChange" @current-change="ghandleCurrentChange" :current-page="gpage"
                       :page-sizes="[30, 100, 200, 400]" :page-size="gpageSize"
                       layout="total, sizes, prev, pager, next" :total="gtotal" style="float:right;" small>
        </el-pagination>
      </el-col>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelSelectGoods" type="warning" plain icon="el-icon-back">取消</el-button>
        <el-button type="primary" @click="confirmSelectGoods" plain icon="el-icon-check">确定</el-button>
      </span>
    </el-dialog>
    </div>
    <div style="height: 15%">
      <el-col :span="24" style="text-align: center">
        <el-button type="warning" size="mini" style="width: 100px;height: 28px;"  @click="cancel" plain  icon="el-icon-back">返回</el-button>
        <el-button type="success" size="mini" style="width: 100px;height: 28px;" plain @click="_addJxsqs"  icon="el-icon-check">保存</el-button>
      </el-col>
    </div>
  </div>
</template>
<script>
  import {fileServer} from '../../common/js/constance';
  import { PROV_CERT_TYPE } from '../../common/js/constance';
  export default {
    data() {
      return {
        isDisabled:false,
        imageUrl:'',
        loading:false,
        chiefInfos:[],
        autoUpload:false,
        selectedGoodsInfos:[],
        jgtotal: 0,
        jgpage: 1,
        jgpageSize: 20,
        tempselectedGoodsInfos:[],
        selectedHosTable:[],
        tempSelectedHosTable:[],
        hosSearchStr: '',
        hosInfoVisible:false,
        htotal: 0,
        hpage: 1,
        hpageSize: 4,
        hosInfos:[],

        filters: {
          goodsName: '',
          mfrsName:''
        },
        goodsFilters: {
          goodsName: '',
          mfrsName:''
        },
        goodsInfoVisible:false,
        gtotal: 0,
        gpage: 1,
        gpageSize: 20,
        goodsInfos:[],

        ifMfrs:false,
        jxsqsVo:{isMfrs:'0',chiefName:'',provName:'',hosInfos:[],basHosInfos:[],chiefId:'',provId:''},
        chiefName:'',
        provName:'',
        imgBasePath: '',
        editFormRules: {},
        userInfo:{},
        uploadCompanyCertsUrl: '',
        fileList:[],
        allowedFileTypes: ['image/bmp', 'image/jpeg', 'image/png', 'application/pdf'],
      }
    },
    methods: {
      getCorpKind:function (kind) {
        if(kind==='2'){
          return '供应商';
        }else if(kind==='3'){
          return '厂商';
        }else{
          return '厂供一体';
        }
      },
      handleCnsUploadErr (err, file, fileList) {
        this.$message.error("上传图片失败，请联系管理员！");
      },
      handleCnsLocalAdd (file, fileList) {
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

      // 获取经销授权书产品列表
      getJxsqsGoodsInfo: function (hIndex) {
        this.jgpage = hIndex;
        var param = {
          orderBy: "",
          queryObject: {
            goodsName: this.goodsFilters.goodsName ,
            mfrsName:this.goodsFilters.mfrsName,
            sqsId:this.jxsqsVo.id
          },
          page: this.jgpage,
          rows: this.jgpageSize
        };
        this.loading = true;
        this.axios.post('/spdHDIService/myCerts/jxsqs/getJxsqsGoodsInfo', param).then(res => {
          this.loading = false;
          if (res.data.code == "0") {
            this.selectedGoodsInfos = res.data.data.data;
            this.jgtotal = res.data.data.total;
          }else {
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.loading = false;
        });
      },
      jghandleCurrentChange(val) {
        this.jgpage = val;
        this.getJxsqsGoodsInfo(this.jgpage);
      },
      jghandleSizeChange(val) {
        this.jgpageSize = val;
        this.getJxsqsGoodsInfo(this.jgpage);
      },
      getGoodsList: function (pIndex) {
        this.gpage = pIndex;
        var param = {
          orderBy: "",
          queryObject: {
            goodsName: this.filters.goodsName ,
            mfrsName:this.filters.mfrsName,
            sqsId:this.jxsqsVo.id
          },
          page: this.gpage, rows: this.gpageSize
        };
        this.loading = true;
        this.axios.post('/spdHDIService/bas/goods/getGoodsInfos', param).then(res => {
          this.loading = false;
          if (res.data.code == "0") {
            this.goodsInfos = res.data.data.data;
            this.gtotal = res.data.data.total;
          }else{
            this.$message.error( res.data.msg);
          }
        }, err => {
          this.loading = false;
        });
      },
      ghandleCurrentChange(val) {
        this.gpage = val;
        this.getGoodsList(this.gpage);
      },
      ghandleSizeChange(val) {
        this.gpageSize = val;
        this.getGoodsList(this.gpage);
      },

      checkCerts:function (d1,d2,d3) {
        window.open(this.imgBasePath + d1.url);
      },
      deleteJxsqsCert:function (file, fileList) {
        if(file.id==null||file.id==''){
          return;
        }
        this.axios.post('/spdHDIService/myCerts/jxsqs/deleteJxsqsCert', {id:file.id,filePath:file.url}).then(res => {
          if (res.data.code == "0") {
            this.$message({message: '操作成功！', type: 'success'});
            this.imageUrl = '';
          }else{
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.$message.error('数据异常，请联系管理员！')
        });
      },
      updateJxsqsCert:function () {
        let param = {id:this.jxsqsVo.id,filePath:this.jxsqsVo.filePath};
        this.axios.post('/spdHDIService/myCerts/jxsqs/updateJxsqsCert', param).then(res => {
          if (res.data.code == "0") {
            this.$message({message: '操作成功！', type: 'success'});
          }else{
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.$message.error('数据异常，请联系管理员！')
        });
      },
      handleJxsqsSuccess(response, file, fileList) {
        this.fileList = [];
        var id = file.response.data[0].id;
        var ext = file.response.data[0].ext;
        var path = file.response.data[0].path;
        var name = file.response.data[0].name;
        var thumbnailId = file.response.data[0].thumbnailId;
        var certPath = path + "/" + id + "." + ext;
        this.fileList.push({name:certPath,url:certPath,id:this.jxsqsVo.id});
        this.imageUrl = path + "/" + thumbnailId;
        this.jxsqsVo.filePath = certPath;
        if(this.jxsqsVo.id){// 编辑状态下，只是更新附件和附件路径
          this.updateJxsqsCert();
        }else {//　新增状态下，更新整个授权书表单
          this.addJxsqs();// 待附件上传成功后提交表单
        }
      },
      _addJxsqs:function () {
        if(this.jxsqsVo.id){// 有id则为编辑
          this.updateJxsqs();
        }else{// 无id则为新增
          try {
            this.$refs.uploadJxsqs.submit();
          }catch(e) {
            this.addJxsqs();
          }
        }
      },
      selectHos:function () {
        this.getHosInfoList(1);
        this.hosInfoVisible = true;
      },
      cancelSelectHos:function () {
        this.tempSelectedHosTable = [];
        this.$refs.hosTable.clearSelection();
        this.hosInfoVisible = false;
      },
      confirmSelectHos:function () {
        let selectedHosTable = this.selectedHosTable;
        let tempSelectedHosTable = this.tempSelectedHosTable;
        tempSelectedHosTable.forEach(item=>{
          if (selectedHosTable.findIndex(o => o.id === item.id) !== -1)
            return;
          selectedHosTable.push(item);
        });
        this.selectedHosTable = selectedHosTable;

        if(this.jxsqsVo.id){// 编辑经销授权书
          this.addJxsqsToHos(this.tempSelectedHosTable);
        }
        this.$refs.hosTable.clearSelection();
        this.hosInfoVisible = false;
      },
      selectGoods:function () {
        this.getGoodsList(1);
        this.goodsInfoVisible = true;
      },
      cancelSelectGoods:function () {
        this.tempselectedGoodsInfos = [];
        this.$refs.goodsTable.clearSelection();
        this.goodsInfoVisible = false;
      },
      confirmSelectGoods:function () {
        let selectedGoodsInfos = this.selectedGoodsInfos;
        let tempselectedGoodsInfos = this.tempselectedGoodsInfos;
        tempselectedGoodsInfos.forEach(item=>{
          if(selectedGoodsInfos.findIndex(o=>o.id==item.id)!==-1)
            return;
          selectedGoodsInfos.push(item);
        });
        if(this.jxsqsVo.id){// 编辑经销授权书
          this.addJxsqsGoods(this.tempselectedGoodsInfos);
        }
        this.$refs.goodsTable.clearSelection();
        this.goodsInfoVisible = false;
      },

      handleHosSelectionChange(selection, row) {
        this.tempSelectedHosTable = selection;
      },
      deleteSelectedHos:function (row) {
        if(this.jxsqsVo.id) {// 编辑经销授权书
          this.deleteJxsqsToHos(row);
        }
        this.selectedHosTable.splice(this.selectedHosTable.findIndex(o => o.id === row.id), 1);
      },
      deleteJxsqsToHos:function (row) {
        let sqsId = this.jxsqsVo.id;
        delete row.cname;
        this.axios.post('/spdHDIService/myCerts/jxsqs/deleteJxsqsToHos', row).then(res => {
          if (res.data.code == "0") {
            this.$message({message: '操作成功！', type: 'success'});
          }else{
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.$message.error('数据异常，请联系管理员！')
        });
      },
      getJxsqsById:function (id) {
        this.axios.post('/spdHDIService/myCerts/jxsqs/getForHospitals', id).then(res => {
          this.loading = false;
          if (res.data.code == "0") {
            let obj = res.data.data;
          }else{
            this.$message.error( res.data.msg);
          }
        }, err => {
          this.loading = false;
        });
      },
      handleGoodsSelectionChange(selection, row) {
        this.tempselectedGoodsInfos = selection;
      },
      selectAllGoods(selection) {
        this.tempselectedGoodsInfos = selection;
      },
      deleteSelectedGoods:function (row) {
        if(this.jxsqsVo.id) {// 编辑经销授权书
          this.deleteJxsqsGoods(row);
        }else {
          this.selectedGoodsInfos.splice(this.selectedGoodsInfos.findIndex(o => o.id === row.id), 1);
        }
      },
      deleteJxsqsGoods:function (row) {
        this.axios.post('/spdHDIService/myCerts/jxsqs/deleteJxsqsGoods', {id:row.pjgId}).then(res => {
          if (res.data.code == "0") {
            this.$message({message: '操作成功！', type: 'success'});
            this.getJxsqsGoodsInfo(1);
            this.selectedGoodsInfos.splice(this.selectedGoodsInfos.findIndex(o => o.id === row.id), 1);
          }else{
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.$message.error('数据异常，请联系管理员！')
        });
      },
      addJxsqsToHos:function (hosInfos) {
        let sqsId = this.jxsqsVo.id;
        let provId = this.jxsqsVo.provId;
        this.axios.post('/spdHDIService/myCerts/jxsqs/addJxsqsToHos/'+sqsId+'/'+provId, hosInfos).then(res => {
          if (res.data.code == "0") {
            this.$message({message: '操作成功！', type: 'success'});
            this.getJxsqsById(sqsId);
          }else{
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.$message.error('数据异常，请联系管理员！')
        });
      },
      addJxsqsGoods:function (goodsInfos) {
        let tempJxsqsVo = this.jxsqsVo;
        tempJxsqsVo.basGoodsInfos = goodsInfos;
        this.axios.post('/spdHDIService/myCerts/jxsqs/addJxsqsGoods', tempJxsqsVo).then(res => {
          if (res.data.code == "0") {
            this.$message({message: '操作成功！', type: 'success'});
            this.getJxsqsGoodsInfo(1);
          }else{
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.$message.error('数据异常，请联系管理员！')
        });
      },
      cancel:function () {
        this.$router.push({ path: 'Jxsqs',query:{chiefName:this.chiefName,provName:this.provName}});
      },
      changeIfMfrs:function (d1) {
        if(d1){
          this.jxsqsVo.isMfrs = '1';
        }else{
          this.jxsqsVo.isMfrs = '0';
        }
      },
      // 查询授权单位，包括厂商、供应商
      queryChiefAsync: function (queryString) {
        var param = {kind:'x',flag:'1'};
        this.loading = true;
        this.axios.post('/spdHDIService/custProvManage/provInfo/getProvInfo', param).then(res => {
          this.loading = false;
          if (res.data.code == "0") {
            this.chiefInfos = res.data.data.filter(o=>o.cname.indexOf(queryString)!=-1);
          }else {
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.loading = false;
        });
      },
      //获取列表
      querySearchAsync: function (queryString, cb) {
        var param = {kind:'1',flag:'1'};
        this.loading = true;
        this.axios.post('/spdHDIService/custProvManage/provInfo/getProvInfo', param).then(res => {
          this.loading = false;
          if (res.data.code == "0") {
            this.hosInfos = res.data.data.filter(o=>o.cname.indexOf(queryString)!=-1);
            cb(this.hosInfos);
          }else {
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.loading = false;
        });
      },
      //获取医院列表
      getHosInfoList: function (hIndex) {
        this.hpage = hIndex;
        let sqsId = this.jxsqsVo.id;
        var param = {
          orderBy: "",
          // remark值作为查询条件判断用
          queryObject: {cname: this.hosSearchStr,flag:1,kind:1,remark:sqsId},
          page: this.hpage,
          rows: this.hpageSize
        };
        this.loading = true;
        this.axios.post('/spdHDIService/custProvManage/provInfo/getProvInfoList', param).then(res => {
          this.loading = false;
          if (res.data.code == "0") {
            this.hosInfos = res.data.data.data;
            this.htotal = res.data.data.total;
          }else {
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.loading = false;
        });
      },
      handleCurrentChange(val) {
        this.hpage = val;
        this.getHosInfoList(this.hpage);
      },
      handleSizeChange(val) {
        this.hpageSize = val;
        this.getHosInfoList(this.hpage);
      },
      createStateFilter(queryString) {
        return (hosInfo) => {
          return (hosInfo.cname.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      handleSelect(item) {
        if(item!==''&&item===this.jxsqsVo.chiefId){
          this.$message.error("授权对象不能与授权单位相同!");
          this.jxsqsVo.provId = '';
          return;
        }
      },
      handleSelectChief(obj) {
        let kind = this.chiefInfos.find(item => item.id == obj).kind;
        if(kind==='3'){
          this.jxsqsVo.isMfrs = '1';
          this.ifMfrs = true;
          this.isDisabled = true;
        }else if(kind==='2'){
          this.jxsqsVo.isMfrs = '0';
          this.ifMfrs = false;
          this.isDisabled = true;
        }else{
          this.jxsqsVo.isMfrs = '0';
          this.ifMfrs = false;
          this.isDisabled = false;
        }
      },
      addJxsqs:function () {
        let serviceUrl = "/spdHDIService/myCerts/jxsqs/addJxsqs";
        let tempJxsqsVo = this.jxsqsVo;
        tempJxsqsVo.basHosInfos = this.selectedHosTable;
        tempJxsqsVo.basGoodsInfos = this.selectedGoodsInfos;
        delete tempJxsqsVo.hosInfos;
        this.axios.post(serviceUrl, tempJxsqsVo).then(res => {
          this.editLoading = false;
          if (res.data.code == "0") {
            this.$message({message: '操作成功！', type: 'success'});
            this.jxsqsVo = res.data.data;
            this.cancel();
          } else if(res.data.code == '-122') {
            this.$message.error(res.data.validateErrors.fieldErrors[0].message);
          }else{
            this.$message.error("操作失败");
          }
        }, err => {
          this.editLoading = false;
        });
      },
      updateJxsqs:function () {
        let _jxsqsVo = this.jxsqsVo;
        let serviceUrl = "/spdHDIService/myCerts/jxsqs/updateJxsqs";
        delete _jxsqsVo.hosInfos;
        delete _jxsqsVo.jxsqsHosInfos;
        this.axios.post(serviceUrl, _jxsqsVo).then(res => {
          this.editLoading = false;
          if (res.data.code == "0") {
            this.$message({message: '操作成功！', type: 'success'});
            this.jxsqsVo = res.data.data;
            this.cancel();
          } else if(res.data.code == '-122') {
            this.$message.error(res.data.validateErrors.fieldErrors[0].message);
          }else{
            this.$message.error("操作失败");
          }
        }, err => {
          this.editLoading = false;
        });
      },
      initEdit:function (jxsqsObj) {
        if(jxsqsObj){// 有经销授权书对象，则为编辑
          this.autoUpload = true;// 编辑状态下，附件自动上传；新增状态下相反。
          this.jxsqsVo = jxsqsObj;// 经销授权书对象从主页面带过来
          this.selectedHosTable  = jxsqsObj.jxsqsHosInfos;// 适用医院从主页面带过来
          this.ifMfrs = jxsqsObj.isMfrs == '1'?true:false;
          this.isDisabled = jxsqsObj.isMfrs == '1'?true:false;
          let filePath = jxsqsObj.filePath;
          if(filePath!=='' && filePath!=null){// 有路径则显示，无路径不显示内容
            let slashIndex = filePath.lastIndexOf("/");
            if (slashIndex !== -1) {
              this.imageUrl = filePath.substring(0, slashIndex) + '/thumb-' + filePath.substring(slashIndex + 1);
              // 处理PDF缩略图，一般情况下，缩略图格式与原图一致，但系统中PDF的缩略图为jpg格式
              let dotIndex = this.imageUrl.lastIndexOf('.');
              let ext = this.imageUrl.substring(dotIndex + 1);
              if(ext==='PDF'||ext==='pdf'){
                this.imageUrl = this.imageUrl.substring(0, dotIndex)+'.jpg';
              }
              this.fileList.push({name:filePath,url:filePath,id:jxsqsObj.id});
            } else {
              this.imageUrl = '';
            }
          }else {
            this.imageUrl = '';
          }
          this.selectedGoodsInfos = [];
          this.getJxsqsGoodsInfo();// 查询授权品种
        }
      }
    },
    mounted() {
      this.queryChiefAsync('');
      this.chiefName = this.$route.query.chiefName;
      this.provName = this.$route.query.provName;
      this.initEdit(this.$route.query.jxsqsObj);
      this.uploadCompanyCertsUrl = fileServer.upload + "certs";
      this.imgBasePath = fileServer.imgBasePath;
      this.userInfo = {'X-US':this.user.xus};
    }
  };

</script>
<style>
  .jxsqsEditClass .el-button--mini, .el-button--mini.is-round {
    padding: 6px 15px;
  }
  .jxsqsEditClass .el-form-item {
    margin-bottom: 9px;
  }
  .jxsqsEditClass .el-form-item--mini.el-form-item, .el-form-item--small.el-form-item {
    margin-bottom: 9px;
  }
  .uploadCertClass{
    margin: 15px;
    padding: 10px;
  }
  .jxsqsEditClass .el-radio.is-bordered.is-checked {
    background-color: white;
    border-color: #62c1fe;
  }
  .jxsqsEditClass .el-radio--mini.is-bordered {
    padding: 4px 18px 0 15px;
    border-radius: 4px;
    height: 25px;
  }
  .certClass{
    height: 35px;
  }
  .uploadJxsqsClass{
    width:96.5%;
  }
  .fontStyle{
    font-style: italic;
    color: #c1c1c1;
  }
  #jxsqsEdit .el-input--mini .el-input__inner {
    height: 28px;
  }
  #jxsqsEdit .el-dialog__footer{
    padding: 0px 10px 15px;
    text-align:left;
    box-sizing: border-box;
  }
  #jxsqsEdit .el-table__empty-block {
    min-height: 30px;
    text-align: center;
    width: 100%;
    height: 100%;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
  }
  .jxsqsEdit-tableheight {
    height: calc(100% - 0px);
  }

  #jxsqsEdit .pic-uploader .el-upload {
    border: 1px dashed #9b9b9b;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  #jxsqsEdit .upload-background .el-upload::after {
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

  #jxsqsEdit .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  #jxsqsEdit .avatar-uploader-icon {
    font-size: 28px;
    color: #0e0e0e;
    width: 200px;
    height: 178px;
    line-height: 178px;
    text-align: center;

    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-image: url("../../assets/images/logo_200x200.png");
    background-repeat: no-repeat;
    opacity: 0.40;
  }
  #jxsqsEdit .avatar {
    width: 200px;
    height: 178px;
    display: block;
    border-radius: 4px;
    border: 1px dashed lightgray;
  }
</style>
