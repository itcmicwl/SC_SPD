<template>
  <div id="jxsqs" style="height: 100%;">
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" label-width="76px" class="queryFormClass">
        <el-form-item label="授权单位">
          <el-input v-model="filters.chiefName" style="height: 28px;" placeholder="输入授权单位" clearable></el-input>
        </el-form-item>
        <el-form-item label="被授权单位" label-width="80px">
          <!--<el-input v-model="filters.provName" style="height: 28px;" placeholder="输入被授权单位" clearable></el-input>-->
          <el-select style="width: 100%"
                     @change="handleSelectChief($event)"
                     v-model="filters.provName"
                     ref="provName"
                     filterable clearable
                     remote
                     id="authObj"
                     reserve-keyword
                     placeholder="输入授权单位"
                     :remote-method="queryChiefAsync"
                     :loading="queryLoading">
            <el-option
              v-for="item in chiefInfos"
              :key="item.id"
              :label="item.cname"
              track-by="$index"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item style="margin-left: 5px;">
          <el-button type="primary" v-on:click="_getJxsqsInfoList(1)" plain style="width: 80px;height:28px;"  icon="el-icon-search">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="warning" icon="el-icon-search" plain style="height: 28px" @click="hasNotAuth" >未完成授权</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="success" icon="el-icon-plus" plain style="height: 28px" @click="provAdd(null)" >新增授权书</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--列表-->
    <template>
      <el-table :data="provs" highlight-current-row v-loading="loading" border style="width: 100%;" fit
                class="jxsqs-tableheight" height="outer" :row-class-name="tableRowClassName">
        <el-table-column type="index" label="序号" width="40" align="center">
        </el-table-column>
        <el-table-column prop="id" width="120" label="授权书编号">
        </el-table-column>
        <el-table-column prop="chiefName" label="授权单位">
        </el-table-column>
        <el-table-column prop="provName"  label="被授权单位" header-align="center">
        </el-table-column>
        <el-table-column prop="saleArea"  label="授权区域" header-align="center">
        </el-table-column>
        <el-table-column align="center" width="90"  label="效期">
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
        <el-table-column label="操作" width="170" align="center">
          <template slot-scope="scope">
            <el-button-group>
              <el-button type="primary" class="jxsqsButton" size="mini" @click="jxsqsEdit(scope.row)"  icon="el-icon-edit-outline">编辑</el-button>
              <el-button type="info" class="jxsqsButton" size="mini" @click="checkCert(scope.row.filePath)"  icon="el-icon-picture">证照</el-button>
              <el-button type="danger" class="jxsqsButton" size="mini" @click="jxsqsDelete(scope.row)" plain icon="el-icon-delete">删除</el-button>
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
  </div>
</template>
<script>
  import { fileServer,PROV_CERT_TYPE } from '../../common/js/constance';
  import moment from "moment";
  export default {
    data() {
      return {
        provIdFlag:false,
        chiefInfos:'',
        imgBasePath: '',
        uploadUrl:'',
        filters: {
          chiefName:'',
          provName:'',
          provId:'' // 被授权单位ID，查询未完成授权列表用
        },
        provs: [],
        total: 0,
        page: 1,
        pageSize: 30,
        loading: false,
        queryLoading: false,
        certKinds: [],
        unitKinds: []
      }
    },
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
      checkCert:function (filePath) {
        window.open(fileServer.imgBasePath + filePath);
      },
      jxsqsEdit:function (row) {
        let obj = document.getElementById("authObj")
        this.$router.push({
          path: 'jxsqsEdit',
          query: {
            jxsqsObj: row,
            editDisable: '1',
            chiefName:this.filters.chiefName,
            provName:this.filters.provName
          }});
      },
      provAdd:function () {
        this.$router.push({ path: 'jxsqsEdit', query: {jxsqsObj: null,editDisable: '1'}});
      },
      flagChange:function () {
        this.getJxsqsInfoList(this.page);
      },
      jxsqsDelete:function (row) {
        this.$confirm('确认删除这条记录吗?', '提示', {}).then(() => {
          this.loading = true;
          this.axios.post("/spdHDIService/myCerts/jxsqs/deleteJxsqs", row).then(res => {
            this.loading = false;
            if (res.data.code == "0") {
              this.loading = false;
              this.$message({message: '操作成功！', type: 'success'});
            } else {
              this.$message.error(res.data.msg);
            }
            this.getJxsqsInfoList(this.page);
          }, err => {
            this.loading = false;
          });
        }).catch(() => {
        });
      },
      hasNotAuth:function () {
        this.provIdFlag = true;
        if(!this.filters.provName){
          this.$message({
            message: '请输入直接进院被授权单位！',
            type: 'warning',
            duration:1500,
            showClose:true
          });
          return;
        }
        this.getJxsqsInfoList(1);
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
        var param = {kind:'x',flag:'1'};
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
      handleSelectChief(id) {
        let query = this.$refs.provName.query;
      },
      _getJxsqsInfoList:function (pIndex) {
        this.provIdFlag = false;
        this.getJxsqsInfoList(pIndex);
      },
      //获取列表
      getJxsqsInfoList: function (pIndex) {
        this.page = pIndex;
        var param = {
          orderBy: "",
          queryObject: {
            provName: this.filters.provName,
            provId:this.provIdFlag?this.filters.provName:'',
            chiefName:this.filters.chiefName,
            corpId:this.user.corpId
          },
          page: this.page,
          rows: this.pageSize
        };
        this.loading = true;
        this.axios.post('/spdHDIService/myCerts/jxsqs/getJxsqsList', param).then(res => {
          this.loading = false;
          if (res.data.code == "0") {
            this.provs = res.data.data.data;
            this.total = res.data.data.total;
          }
          else {
            this.$message.error(res.data.msg);
          }
        }, err => {
          this.loading = false;
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
        this.getJxsqsInfoList(this.page);
      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.getJxsqsInfoList(this.page);
      },
      closeProv(provInfo) {
        this.$confirm('确认停用这条记录吗?', '提示', {}).then(() => {
          this.loading = true;
          this.axios.post("/spdHDIService/bas/company/delCompany", provInfo).then(res => {
            this.loading = false;
            if (res.data.code == "0") {
              this.loading = false;
              this.$message({message: '操作成功！', type: 'success'});
            } else {
              this.$message.error(res.data.msg);
            }
            this.getJxsqsInfoList(this.page);
          }, err => {
            this.loading = false;
          });
        }).catch(() => {
        });
      },
      openProv(provInfo) {
        this.$confirm('确认启用这条记录吗?', '提示', {}).then(() => {
          this.loading = true;
          this.axios.post("/spdHDIService/bas/company/openCompany", provInfo).then(res => {
            this.loading = false;
            if (res.data.code == "0") {
              this.loading = false;
              this.$message({message: '操作成功！', type: 'success'});
            } else {
              this.$message.error(res.data.msg);
            }
            this.getJxsqsInfoList(this.page);
          }, err => {
            this.loading = false;
          });
        }).catch(() => {
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
      getZZLX: function () {   //证照类型 字典
        this.loading = true;
        this.axios.post('/platformService/sys/dict/getDictValueByDictEname', { dictName: 'YLJGZZLX' }).then(res => {
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
      this.queryChiefAsync2();
      var tempObj = this.$route.query;
      this.filters.chiefName = this.$route.query.chiefName;
      this.filters.provName = this.$route.query.provName;
      this.getJxsqsInfoList(1);
      this.uploadUrl =fileServer.upload +"baseData";
      this.imgBasePath = fileServer.imgBasePath;
    }
  };

</script>
<style>
  #jxsqs .el-table .cell, .el-table th > div {
    padding-bottom: 1px;
    padding-top: 1px;
    line-height: 14px;
    padding-left: 5px;
    padding-right: 5px;
  }
  .jxsqsButton{
    height: 26px;
  }
  #jxsqs .jxsqs-tableheight {
    height: calc(100% - 90px);
  }
  #jxsqs .el-radio-button__orig-radio:checked+.el-radio-button__inner{
    color: #ffffff;
    background: #535c63;
    border-color: #676082;
  }
  #jxsqs .el-radio-button--mini .el-radio-button__inner {
    height: 28px;
    width: 38px;
    padding: 7px 7px 6px;
    font-size: 12px;
    font-size: 12px;
    border-radius: 0px;
  }
  #jxsqs .el-radio-button:first-child .el-radio-button__inner {
    border-left: 1px solid #dcdfe6;
    border-radius: 4px 0 0 4px;
    -webkit-box-shadow: none!important;
    box-shadow: none!important;
  }
  #jxsqs .el-radio-button:last-child .el-radio-button__inner {
    border-radius: 0px 4px 4px 0px;
    -webkit-box-shadow: none!important;
    box-shadow: none!important;
  }
  #jxsqs .el-input--mini .el-input__inner{
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
  #jxsqs .el-form--inline .el-form-item {
    margin-right: 0px;
  }
  #jxsqs .toolbar {
    margin-bottom: 1px;
    margin-top: -3px;
  }
  #jxsqs .expiring {
    color: #ff47e8;
  }
  #jxsqs .expired {
    color: #ff311f;
  }
  #jxsqs .el-table .warning-red {
    color: #F56C6C;
  }
  #jxsqs .el-table .warning-row {
    color: #E6A23C;
  }
</style>
