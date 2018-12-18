<template>
<!--二级供应商商品维护-->
<div id='subGoodsMgr' style="height:100%;">
<el-row >
       <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
              <el-form :inline="true" label-width="80px">
                 <el-col :span="24">
                        <el-form-item label="集配商:">
                            <el-select v-model="filters.colId" filterable placeholder="请选择"  @change="provChange">
                                <el-option v-for="item in colProvLst" :key="item.id" :label="item.cname" :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
             
              </el-form>
       </el-col>
</el-row>
<el-row style="height:95%;">
   <el-col :span="8" style="height:100%;">
              <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form :inline="true" label-width="60px" ref="searchForm" >
                        <el-form-item label="合作商名">
                            <el-input v-model="filters.subProvName" placeholder="名称" ></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" icon="search" v-on:click="queryPartnerList(1)" >查询</el-button>
                        </el-form-item>
                    </el-form>
                 
                </el-col>
              <el-table :data="partnerList" highlight-current-row height="outter"
                    @row-click="clickPartner"
                    ref="partnerTable" style="width: 100%;" border fit  class="subGoodsMgr-tableHeight">
                    <!--<el-table-column type="index" label="序号" align="center"></el-table-column>-->
            
                    <el-table-column prop="cname" label="合作商名" style="width:250px;" >
                    </el-table-column>                           
                    <el-table-column prop="isUseSubprov" label="是否二级" :formatter="statusFormat"> style="width:10%;" >
                    </el-table-column>
                
                    <!--
                    <el-table-column label="合作商信息" min-width="250" header-align="center">
                        <template slot-scope="scope">
                            <div slot="reference">
                                <span>{{ scope.row.cname}}</span><br/>
                                <span 二级：{{scope.row.isUseSubprov}}</span>
                            </div>
                        </template>
                    </el-table-column>
                     -->
             
                </el-table>
                              <!--分页工具条-->
                    <el-col :span="24" class="toolbar">
                        <el-pagination @size-change="pHandleSizeChange" @current-change="pHandleCurrentChange"
                                    :current-page="pPage" :page-sizes="[50, 100, 200, 400]"
                                    :page-size="pPageSize" layout="total, sizes, prev, pager, next"
                                    :total="pTotal" style="float:right;" small>
                        </el-pagination>
                    </el-col>
   </el-col>
   <el-col :span="16" class="toolbar" style="padding-bottom: 0px; padding-left:5px; height: 99%; ">
      <!--二级商商品列表-->
                <div style="padding-bottom: 4px;">
                    <el-form :inline="true" :model="filters" ref="goodsFilterForm" label-width="80px">
                        <el-form-item label="产品名称" prop="goodsName">
                            <el-input v-model="filters.goodsName" placeholder="请输入产品名称|规格" ></el-input>
                        </el-form-item>
                        <el-form-item label="国药编码" prop="erpCode">
                            <el-input v-model="filters.erpCode" placeholder="请输入国药编码" ></el-input>
                        </el-form-item>
                        <el-button-group>
                            <el-button type="primary" v-on:click="queryGoodsList(1)" >查询</el-button>
                            <el-button type="primary" v-on:click="resetGoodsFilterForm('goodsFilterForm')" >重置</el-button>
                          <!--  <el-button type="success" @click="provProvGoodsSave()" >保存</el-button> -->
                            <el-button type="success" @click="provProvGoodsAdd()" >导入</el-button>
                            <el-button type="success" @click="provProvGoodsDel()" >删除</el-button>

                        </el-button-group>
                    </el-form>
                </div>
                <el-table :data="goods" highlight-current-row  @selection-change="goodsSelectionChange" ref="goodsTable" style="width: 100%;" border fit height="outter" class="subGoodsMgr-tableHeight">
                    <el-table-column type="selection" align="center" width="40"> </el-table-column>
                    <el-table-column prop="goodsName" header-align="center" label="产品名称" width="200" fixed></el-table-column>
                    <el-table-column prop="goodsGg" header-align="center" label="规格型号" width="100"></el-table-column>
                    <el-table-column prop="unit" align="center" label="单位" width="50"></el-table-column>
                    <el-table-column prop="erpCode" align="center" label="国药编码" width="70"></el-table-column>
                    <el-table-column prop="made" align="center" label="产地" width="60"></el-table-column>
                    <el-table-column prop="mfrsName" header-align="center" label="生产厂商" width="220"></el-table-column>
                    <el-table-column prop="certificateCode" header-align="center" label="注册证号" width="220"></el-table-column>
                </el-table>
                <!--工具条-->
                <el-col :span="24" class="toolbar">
                    <el-pagination @size-change="goodsHandleSizeChange" @current-change="goodsHandleCurrentChange"
                                   :current-page="goodsPage" :page-sizes="[50, 100, 200, 400]"
                                   :page-size="goodsPageSize" layout="total, sizes, prev, pager, next"
                                   :total="goodsTotal" style="float:right;" small>
                    </el-pagination>
                </el-col>

                <el-dialog title="为二级供应商添加商品" close="onClose" :visible.sync="importVisible" :close-on-click-modal="false" >
                    <el-form :inline="true" :model="filters" label-width="80px" ref="ppfiltersForm">
                        <el-form-item label="商品名称" prop="goodsName">
                            <el-input v-model="ppfilters.goodsName" placeholder="请输入产品名称|规格" ></el-input>
                        </el-form-item>
                        <el-form-item label="国药编码" prop="goodsName">
                            <el-input v-model="ppfilters.erpCode" placeholder="国药编码" ></el-input>
                        </el-form-item>
                        <el-button type="primary" v-on:click="getCanImportGoods(1)" >查询</el-button>
                        <el-button type="primary" v-on:click="resetPPfiltersForm('ppfiltersForm')" >重置</el-button>
                    </el-form>
                    <template>
                        <el-table ref="multipleImportListTable" :data="canInGoodsList"  border tooltip-effect="dark" row-key='id'
                                style="width: 100%" @selection-change="handleImportSelectionChange">
                      <el-table-column type="selection" align="center" width="40"> </el-table-column>
                    <el-table-column prop="goodsName" header-align="center" label="产品名称" width="200" fixed></el-table-column>
                    <el-table-column prop="goodsGg" header-align="center" label="规格型号" width="100"></el-table-column>
                    <el-table-column prop="unit" align="center" label="单位" width="50"></el-table-column>
                    <el-table-column prop="erpCode" align="center" label="国药编码" width="70"></el-table-column>
                    <el-table-column prop="made" align="center" label="产地" width="60"></el-table-column>
                    <el-table-column prop="mfrsName" header-align="center" label="生产厂商" width="220"></el-table-column>
                    <el-table-column prop="certificateCode" header-align="center" label="注册证号" width="220"></el-table-column>
                
                        </el-table>
                        <div style="padding-top: 6px">
                            <el-pagination @size-change="handleImportSizeChange" @current-change="handleImportCurrentChange"
                                        :page-size="importGoodsSize" layout="total, sizes, prev, pager, next"
                                        :total="importGoodsTotal" style="float:right;" small> 
                            </el-pagination>
                            <el-button @click="clearImportSelection()" >取消选择</el-button>
                            <el-button @click="importSubmit()" type="success" >确定</el-button>
                            <el-button @click.native="importVisible = false" type="primary" >返回</el-button>
                        </div>
                    </template>
            </el-dialog>
   </el-col>
</el-row>
</div>
</template>
<script>
import { IS_USE_SUBPROV } from "../../common/js/constance";
export default {
    data() {
            return {
                    filters: {
                        hosId: this.user.corpId,
                        colId:'',
                        subProvName:'',
                        goodsName:''
                    },
                    ppfilters: {
                        goodsName:''
                    },
                    colProvLst:[], //集配商列表
                    partnerList:[],//二级供应商列表
                    currentCollector:{id:''},//当前选择集配商
                    currentPartner:{}, //当前合作供应商
                    subProvName:'',
                    pTotal: 0,
                    pPage: 1,
                    pPageSize: 50,
                    mulGoodsSelection:[],
                    // 产品信息
                    goods:[],
                    goodsTotal:0,
                    goodsPage:1,
                    goodsPageSize:50,
                    importVisible:false,
                    canInGoodsList:[],  // 二级供应商能导入的商品
                    importGoodsTotal:0,
                    importGoodsPage:1,
                    importGoodsSize:50,
                    multipleImportSelection:[]

            };
        },
    methods:{
        statusFormat(row) {
             return IS_USE_SUBPROV[row.isUseSubprov].text;
       },
             // 查询集配商列表信息
        getColProvLst() {
             this.loading = true;
             this.axios.post("/spdHERPService/provMgr/distriRelation/getCollectorList/" +this.filters.hosId).then(res => {
                this.loading = false;
                this.colProvLst = res.data.data;
                this.filters.colId=res.data.data[0].id;
                this.currentCollector =res.data.data[0];
                this.queryPartnerList();
                },
                err => { this.loading = false; }
            );
       },
         
            //集配商切换事件
            provChange(provId) {
                 //   this.currentCollector = this.colProvLst.find(item => item.id == provId);
                  //  this.queryPartnerList(1);
            },
                 // 集配商切换，查询合作商
            queryPartnerList:function (page) {
                this.partnerList = [];
                let o =this;
                  o.pPage = page;
                var param = {
                    orderBy: "",
                    queryObject: {
                        hosId:this.user.corpId,
                        collectorId:o.currentCollector.id,
                        cname:o.filters.subProvName
                    },
                    page: o.pPage,
                    rows: o.pPageSize                
                };
                o.axios.post('/spdHERPService/provMgr/distriRelation/queryPartnerList', param).then(res => {
                  
                   this.partnerList = res.data.data.data;
                   o.pTotal = res.data.data.total;            
                }, err => {
                });
            },
            pHandleSizeChange(val) {
                this.pPageSize = val;
                this.queryPartnerList(this.pPage);
            },
            pHandleCurrentChange(val) {
                this.pPage = val;
                this.queryPartnerList(this.pPage);
            },
            clickPartner:function (d1,d2,d3) {
                this.currentPartner = d1;
                this.queryGoodsList(1);
            },
              // 查询
            queryGoodsList:function(pIndex){
                this.mulGoodsSelection = [];
                if(this.currentPartner.provId == null || this.currentPartner.provId == ''){
                    return;
                }
                this.goodsPage = pIndex;
                 var param = {
                    orderBy: '',
                    queryObject: {
                         provId:  this.currentPartner.provId,
                         goodsName:this.filters.goodsName,
                         collectorId:this.currentCollector.id,
                         erpCode:this.filters.erpCode
                    },
                    page: this.goodsPage,
                    rows: this.goodsPageSize
                };
                this.axios.post('/spdHERPService/provMgr/distriRelation/queryPPGoodsList', param).then(res => {
                    this.goods = res.data.data.data;
                    this.goodsTotal = res.data.data.total;
                }, err => {
                });
            },
                // 重置
            resetGoodsFilterForm:function(formName){
                this.$refs[formName].resetFields();
                this.filters.goodsName = '';
                this.filters.erpCode = '';
                this.queryGoodsList(1);
            },
            goodsHandleSizeChange(val){
                this.goodsPageSize = val;
                this.queryGoodsList(this.goodsPage);
            },
            goodsHandleCurrentChange(val){
                this.goodsPage = val;
                this.queryGoodsList(this.goodsPage);
            },
            goodsSelectionChange(val){
                this.mulGoodsSelection = val;
            },

            provProvGoodsAdd(){
                this.importVisible=true;
                this.getCanImportGoods(1);

            },
            provProvGoodsDel(){
                   let o = this;
                if(o.mulGoodsSelection.length<=0){
                    o.$message.error( "至少选择一条数据！");
                    return;
                }
                o.$confirm('确认删除选中记录吗?', '提示', {
                }).then(() => {
                    o.axios.post("/spdHERPService/provMgr/distriRelation/mulDeleteGoodsList", this.mulGoodsSelection).then(res => {
                        o.loading = false;
                        if (res.data.code == "0") {
                            o.$message({ message: '操作成功！', type: 'success' });
                        } else {
                            o.$message.error(res.data.msg);
                        }
                        o.queryGoodsList(o.goodsPage);
                    }, err => {
                    });
                }).catch(() => {});
            },
             
            getCanImportGoods:function (page) {            //二级供应商可添加的商品

                let o = this;
                o.importGoodsPage = page;
            
                var param = {
                    orderBy: "",
                    queryObject: {
                         provId:  this.currentPartner.provId,
                         goodsName:this.ppfilters.goodsName,
                         erpCode:this.ppfilters.erpCode,
                         collectorId:this.currentCollector.id,
                         hosId:this.user.corpId
                    },
                    page: o.importGoodsPage,
                    rows: o.importGoodsSize
                };
                o.axios.post('/spdHERPService/provMgr/distriRelation/queryPPGoodsList4Import', param).then(res => {
                    o.canInGoodsList=res.data.data.data;
                    o.importGoodsTotal = res.data.data.total;
                }, err => {
                });
            },
         
            clearSelection() {
                this.$refs.multipleImportListTable.clearSelection();
            },
            resetGoodsFilterForm:function (formName) {
                this.$refs[formName].resetFields();
                this.getCanImportGoods(1);
            },
            handleImportSizeChange(val) {
                this.importGoodsSize = val;
                this.getCanImportGoods(this.importGoodsPage);
            },
            handleImportCurrentChange(val) {
                this.importGoodsPage = val;
                this.getCanImportGoods(this.importGoodsPage);
            },
              handleImportSelectionChange(val) {
                this.multipleImportSelection = val;
            },
            importSubmit(){  //执行二级供应商商品添加
                    let o = this;
                   if(o.multipleImportSelection.length<=0){
                        o.$message.error( "至少选择一条数据！");
                        return;
                   }
                   this.multipleImportSelection.forEach(o=>{
				   o.provId=this.currentPartner.provId;
				   o.hosId= this.user.corpId;
				   });
                this.axios.post('/spdHERPService/provMgr/distriRelation/addSubGoods', this.multipleImportSelection).then(res => {
                    if (res.data.code == "0") {
                        this.$message({ message: '操作成功！', type: 'success' });
                        this.importVisible =false;
                        this.clearSelection();
                        this.queryGoodsList(1);
                    } else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {});
            },
    },
    mounted() {
         this.getColProvLst();

    }
};
</script>
<style>
    .subGoodsMgr-tableHeight{
        height:  calc(100% - 100px);
    }
</style>