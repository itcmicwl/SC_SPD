<template>
  <div id="myProv" style="height: 100%;">
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" label-width="88px" class="queryFormClass">
        <el-form-item label="供应商名称">
          <el-input v-model="myFilters.cname" style="height: 28px;" placeholder="输入供应商名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="状态" label-width="50px">
          <el-radio-group v-model="myFilters.flag" @change="flagChange" size="mini">
            <el-radio-button label="1" plain>启用</el-radio-button>
            <el-radio-button label="0" plain>停用</el-radio-button>
            <el-radio-button label="" plain>全部</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getMyProvInfoList(1)" plain style="width: 80px;height:28px;"  icon="el-icon-search">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="success" icon="el-icon-plus" plain style="height: 28px" @click="provAdd" >新增供应商</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="success" icon="el-icon-plus" plain style="height: 28px" @click="importDealerExcel" >从Excel导入</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--列表-->
    <template>
      <el-table :data="myProvs" highlight-current-row v-loading="loading" border style="width: 100%;" fit
                class="myProv-tableheight" height="outer">
        <el-table-column type="index" label="序号" width="50" align="center">
        </el-table-column>
        <el-table-column prop="cname" label="供应商名称">
        </el-table-column>
        <el-table-column prop="linkman" width="90" label="联系人" header-align="center">
        </el-table-column>
        <el-table-column prop="legal" width="90" label="法人代表" header-align="center">
        </el-table-column>
        <el-table-column prop="contactWay" label="联系方式" width="100" header-align="center">
        </el-table-column>
        <el-table-column prop="flag" label="状态" align="center" width="55"
          :formatter="formateStatus"
          filter-placement="bottom-end">
          <template slot-scope="scope">
            <el-tag :type="scope.row.flag === '0' ? 'danger' : 'success'" close-transition>{{scope.row.flag==='0'?'停用':'启用'}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope">
            <el-button-group>
              <el-button type="primary" size="mini" @click="provEdit(scope.row)"  icon="el-icon-edit-outline">编辑信息</el-button>
              <span v-if="scope.row.flag=='1'">
                <el-button type="danger" size="mini" @click="updateMyProvFlag(scope.row.id,'0')" plain icon="el-icon-remove">停用</el-button>
              </span>
              <span v-else>
                <el-button type="success" size="mini" @click="updateMyProvFlag(scope.row.id,'1')" plain icon="el-icon-circle-check">启用</el-button>
              </span>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>
      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination @size-change="mhandleSizeChange" @current-change="mhandleCurrentChange" :current-page="mpage"
                       :page-sizes="[30, 100, 200, 400]" :page-size="mpageSize" layout="total, sizes, prev, pager, next"
                       :total="mtotal" style="float:right;" small>
        </el-pagination>
      </el-col>
    </template>
    <el-dialog title="选择供应商" :visible.sync="provInfoVisible" top="6vh" width="75%">
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" label-width="88px" class="queryFormClass">
          <el-form-item label="供应商名称">
            <el-input v-model="filters.cname" style="height: 28px;width: 250px;" placeholder="输入供应商名称" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" v-on:click="getProvInfoList(1)" plain style="width: 80px;height:28px;"  icon="el-icon-search">搜索</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <template>
        <el-table :data="provs" ref="provsTable" highlight-current-row v-loading="loading" border style="width: 100%;" fit
                   max-height="400" @select="handleProvsSelectionChange">
          <el-table-column type="selection" align="center"  width="40"></el-table-column>
          <el-table-column type="index" label="序号" width="50" align="center">
          </el-table-column>
          <el-table-column prop="cname"  label="供应商名称">
          </el-table-column>
          <el-table-column prop="linkman" width="90" label="联系人" header-align="center">
          </el-table-column>
          <el-table-column prop="legal" width="90" label="法人代表" header-align="center">
          </el-table-column>
          <el-table-column prop="contactWay" label="联系方式" width="100" header-align="center">
          </el-table-column>
        </el-table>
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="margin-top: 5px;">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page"
                         :page-sizes="[30, 100, 200, 400]" :page-size="pageSize" layout="total, sizes, prev, pager, next"
                         :total="total" style="float:right;" small>
          </el-pagination>
        </el-col>
      </template>
      <span style="text-align: left;">
        <el-button @click="initSelectProvs" type="warning" plain icon="el-icon-back">取消</el-button>
        <el-button type="primary" @click="confirmSelectProvs" :loading="confirmLoading" plain icon="el-icon-check">确定</el-button>
      </span>
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
  </div>
</template>
<script>
  import { fileServer,PROV_CERT_TYPE } from '../../common/js/constance';
  export default {
    data() {
      return {
        confirmLoading:false,
        tempselectedProvsInfos:[],

        myFilters: {
          cname: '',
          flag:'1'
        },
        myProvs: [],
        mtotal: 0,
        mpage: 1,
        mpageSize: 30,
        provInfoVisible:false,

        imgBasePath: '',
        uploadUrl:'',
        filters: {
          cname: '',
          flag:'1'
        },
        provs: [],
        total: 0,
        page: 1,
        pageSize: 30,
        loading: false,
        myLoading: false,
        certKinds: [],
        unitKinds: [],
        fileList:[],
        importExcelVisible:false,
        uploadState:false,
        editLoading:false,
        fileMap:{id:'',ext:'',name:'',path:''},
      }
    },
    methods: {
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
        // this.BaseGoodsTempUrl = 'upload/'+ob['path']+'.'+ob['ext'];
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

      provEdit:function (row) {
        this.$router.push({ path: 'myProvEdit', query: {company: row,cname:this.myFilters.cname}});
      },
      provAdd:function () {
        this.getProvInfoList(1);
        this.provInfoVisible = true;
      },
      flagChange:function () {
        this.getMyProvInfoList(this.mpage);
      },
      initSelectProvs:function () {
        this.tempselectedProvsInfos = [];
        this.$refs.provsTable.clearSelection();
        this.provInfoVisible = false;
      },
      confirmSelectProvs:function () {
        let param = this.tempselectedProvsInfos;
        this.confirmLoading = true;
        this.axios.post('/spdHDIService/myCerts/myProv/addMyProvInfo', param).then(res => {
          if (res.data.code == "0") {
            this.confirmLoading = false;
            this.$message({message: '操作成功！', type: 'success'});
            this.initSelectProvs();
            this.getMyProvInfoList(1);
          }else{
            this.confirmLoading = false;
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.confirmLoading = false;
          this.$message.error('数据异常，请联系管理员！')
        });
      },

      handleProvsSelectionChange(selection, row) {
        this.tempselectedProvsInfos = selection;
      },

      //获取列表
      getProvInfoList: function (pIndex) {
        this.page = pIndex;
        var param = {
          orderBy: "",
          queryObject: {cname: this.filters.cname,flag:1,kind: 2,remark:this.user.corpId},
          page: this.page,
          rows: this.pageSize
        };
        this.loading = true;
        this.axios.post('/spdHDIService/custProvManage/provInfo/getProvInfoList', param).then(res => {
          this.loading = false;
          if (res.data.code == "0") {
            this.provs = res.data.data.data;
            this.total = res.data.data.total;
          }else {
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.loading = false;
        });
      },
      //获取列表
      getMyProvInfoList: function (pIndex) {
        this.mpage = pIndex;
        var param = {
          orderBy: "",
          queryObject: {cname: this.myFilters.cname,flag:this.myFilters.flag,collectorId:this.user.corpId},
          page: this.mpage,
          rows: this.mpageSize
        };
        this.myLoading = true;
        this.axios.post('/spdHDIService/myCerts/myProv/getMyProvInfo', param).then(res => {
          this.myLoading = false;
          if (res.data.code == "0") {
            this.myProvs = res.data.data.data;
            this.mtotal = res.data.data.total;
          }else {
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.myLoading = false;
        });
      },
      formateStatus:function (d) {
        if(d.flag == '1'){
          return '启用';
        }
        return '停用';
      },
      handleCurrentChange(val) {
        this.page = val;
        this.getProvInfoList(this.page);
      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.getProvInfoList(this.page);
      },
      mhandleCurrentChange(val) {
        this.mpage = val;
        this.getMyProvInfoList(this.mpage);
      },
      mhandleSizeChange(val) {
        this.mpageSize = val;
        this.getMyProvInfoList(this.mpage);
      },
      updateMyProvFlag(id,flag) {
        let message = flag==='0'?'确认停用这条记录吗?':'确认启用这条记录吗?';
        this.$confirm(message, '提示', {}).then(() => {
          this.updateFlagLoading = true;
          this.axios.post("/spdHDIService/myCerts/myProv/updateMyProvFlag", {id:id,flag:flag}).then(res => {
            if (res.data.code == "0") {
              this.updateFlagLoading = false;
              this.$message({message: '操作成功！', type: 'success'});
            } else {
              this.$message.error(res.data.msg);
            }
            this.getMyProvInfoList(this.mpage);
          }, err => {
            this.updateFlagLoading = false;
          });
        }).catch(() => {
        });
      },
      //字典获取单位类型
      getUnitKinds: function () {
        this.axios.post('/platformService/sys/dict/getDictValueByDictEname', {dictName: 'JGLX', val: 2}).then(res => {
          if (res.data.code == "0") {
            this.unitKinds = res.data.data;
          }else {
            this.$message.error(res.data.msg);
          }
        }, err => {
        });
      },
      getZZLX: function () {   //证照类型 字典
        this.axios.post('/platformService/sys/dict/getDictValueByDictEname', { dictName: 'ZZLX' }).then(res => {
          if (res.data.code == "0") {
            this.certKinds = res.data.data;
          }else {
            this.$message.error(res.data.msg);
          }
        }, err => {
        });
      }
    },
    mounted() {
      this.getZZLX();
      var tempObj = this.$route.query;
      this.myFilters.cname = tempObj.cname;
      this.getMyProvInfoList(1);
      this.uploadUrl =fileServer.upload +"baseData";
      this.imgBasePath = fileServer.imgBasePath;
    }
  };

</script>
<style>
  #myProv .myProv-tableheight {
    height: calc(100% - 90px);
  }
  #myProv .el-radio-button__orig-radio:checked+.el-radio-button__inner{
    color: #ffffff;
    background: #535c63;
    border-color: #676082;
  }
  #myProv .el-radio-button--mini .el-radio-button__inner {
    height: 28px;
    width: 38px;
    padding: 7px 7px 6px;
    font-size: 12px;
    font-size: 12px;
    border-radius: 0px;
  }
  #myProv .el-radio-button:first-child .el-radio-button__inner {
    border-left: 1px solid #dcdfe6;
    border-radius: 4px 0 0 4px;
    -webkit-box-shadow: none!important;
    box-shadow: none!important;
  }
  #myProv .el-radio-button:last-child .el-radio-button__inner {
    border-radius: 0px 4px 4px 0px;
    -webkit-box-shadow: none!important;
    box-shadow: none!important;
  }
  #myProv .el-input--mini .el-input__inner{
    height: 28px;
  }
  .queryFormClass{
    padding-top: 3px;
    height: 30px;
    /*box-shadow: 0px 0px 30px #0c629a inset;*/
    box-shadow: 0px 0px 3px 0px #a9a1a1 inset;
    border-radius: 4px;
    background-color: #f9f9f9;
  }
  #myProv .el-form--inline .el-form-item {
     margin-right: 0px;
  }
  #myProv .toolbar {
    margin-bottom: 1px;
    margin-top: -3px;
  }
</style>
