<template>
    <el-tabs id="settle-bill-list" type="border-card" v-model="showTab">
        <el-tab-pane label="我要结算" name="purLst" style="height:100%">
            <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                <el-form :inline="true" label-width="100px">
                    <el-col :span="7">
                        <el-form-item label="集配商:">
                            <el-select v-model="filters.colId" filterable placeholder="请选择"  @change="provChange">
                                <el-option v-for="item in colProvLst" :key="item.id" :label="item.cname" :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="供应商:">
                            <el-select v-model="filters.subProvId" filterable placeholder="请选择"  @change="subProvChange">
                                <el-option v-for="item in subProvLst" :key="item.id" :label="item.cname" :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="入库日期:">
                            <el-date-picker v-model="filters.sDate" type="daterange" align="right" 
                            :editable="false" :clearable="false" @change="selectDateRangChange"
                            placeholder="选择日期范围" :picker-options="pickerOptions2" >
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="3">
                        <el-form-item>
                            <el-button type="primary" icon="search" v-on:click="getOutBillList(1)" >查询</el-button>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-col>
            <!--列表-->
            <el-table :data="outBillDetailList" highlight-current-row v-loading="loading" style="width: 100%;" border fit class="table_2_content" row-key="id" @selection-change="sltRowsChange">
                <el-table-column type="selection"  width="55" reserve-selection></el-table-column>
                <el-table-column prop="billId" label="编号" width=150 sortable></el-table-column>
                <el-table-column prop="goodsName" label="物资名称" show-overflow-tooltip width=180></el-table-column>
                <el-table-column prop="outQty" label="数量"  width=60></el-table-column>
                <el-table-column prop="canQty" label="可结算数"  width=60></el-table-column>
                <el-table-column label="结算数量"  width="120">
                    <template slot-scope="scope">
                        <el-input-number placeholder="请输入数量"   v-model="scope.row.settleQty"  :min=0 :max="scope.row.canQty" class="col-input-num">
                        </el-input-number>
                    </template>
                </el-table-column>
                <el-table-column prop="price" label="单价"  width=60></el-table-column>
                <el-table-column prop="subProvName" label="供应商" show-overflow-tooltip width=200></el-table-column>
                <el-table-column prop="batchCode" label="批号"  width=120></el-table-column>
                <el-table-column prop="goodsGg" label="规格" show-overflow-tooltip width=180></el-table-column>
                <el-table-column prop="batchId" label="批次"  width=150></el-table-column>
                <!--<el-table-column prop="settleAmount" label="结算金额"  width=60></el-table-column>    -->            
                <el-table-column prop="unit" label="单位" width=50 show-overflow-tooltip></el-table-column>
                
                <!-- <el-table-column prop="provName" label="供应商" show-overflow-tooltip width=200></el-table-column>-->
                <el-table-column prop="made" label="产地" width=80></el-table-column>
                <el-table-column prop="mfrsName" label="生产厂商" show-overflow-tooltip width=180></el-table-column>                            
            </el-table>
            <!--工具条-->
            <el-col :span="24" class="toolbar pageBar">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[100, 200, 300, 400]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
                </el-pagination>
            </el-col>  
            <el-col :span="24" class="toolbar" style="margin-top:10px; padding-bottom: 0px;">
            <div style="text-align: center;">
                <el-button  type="info"  @click="createSettleBill" icon="print">生成结算单</el-button>                    
            </div>
            </el-col>              
        </el-tab-pane>
        <el-tab-pane label="结算单列表" name="purconfirmLst" style="height:100%">
            <el-col :span="24" style="height:100%">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form :inline="true" label-width="80px" ref="searchForm" v-model="sFilters">
                        <el-form-item label="结算单号">
                            <el-input v-model="sFilters.billId" placeholder="单号" ></el-input>
                        </el-form-item>
                        <el-form-item label="制单日期">
                         <el-date-picker
                            v-model="sFilters.ssDate"  type="datetimerange" align="right" start-placeholder="开始日期"  @change="dateChangeEvt"
                            end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" :editable="false" :clearable="false">
                          </el-date-picker>
                         </el-form-item>
                        <el-form-item>
                            <el-button style="width:60px;" type="primary" icon="search" v-on:click="getSettleBillList(1)" >查询</el-button>
                            <el-button style="width:60px;" type="success"  @click="exportExcelBtnEvt" >导出</el-button>
                        </el-form-item>
                    </el-form>
                </el-col>
                <!--列表-->
                <el-table :data="settleBillList" highlight-current-row v-loading="sLoading" style="width: 100%;" border fit class="table_content">
                    <el-table-column prop="billId" label="结算单号" sortable width="150">
                    </el-table-column>
                    <el-table-column prop="status" label="状态" :formatter="statusFormat">
                    </el-table-column>
                    <el-table-column prop="hosName" label="付款单位">
                    </el-table-column>                           
                    <el-table-column prop="provName" label="结算单位" width="220" show-overflow-tooltip>
                    </el-table-column>
                    <!--<el-table-column prop="provDeptErpCode" label="结算部门" sortable>-->
                    </el-table-column> 
                    <el-table-column prop="sumRow" label="笔数" width='60' align = 'center'>
                    </el-table-column> 
                    <el-table-column prop="fillterName" label="制单人" sortable>
                    </el-table-column>
                    <el-table-column prop="fillDate" label="制单时间" sortable>
                        <template slot-scope="scope">
                            {{scope.row.fillDate | dateFormat('YYYY-MM-DD')}}
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="150">
                        <template slot-scope="scope">
                            <el-button  @click="showBill(scope.row.billId)" type="text">查看</el-button>
                           <!-- <el-button  @click="editBill(scope.row.id)" type="text" v-if="scope.row.status==10">编辑</el-button>-->
                        </template>
                    </el-table-column>
                </el-table>
                <!--分页条-->
                <el-pagination @size-change="sSizeChange" @current-change="sCurrentChange" :current-page="sPage" :page-sizes="[20, 50, 100, 200]" :page-size="sPageSize" layout="total, sizes, prev, pager, next" :total="sTotal" style="float:right;" small>
                </el-pagination>
            </el-col>
        </el-tab-pane>
        
    </el-tabs>
</template>
<script>
import colAndProvs from "../../components/colAndProvs";
import { SETTLE_STATUS, OUTSTOCK_STATUS } from "../../common/js/constance";
import { mapActions } from "vuex";
import moment from 'moment';
import $ from 'jquery';
import '../../common/third/tableExport/tableExport';
import '../../common/third/tableExport/jquery.base64';
export default {
  data() {
    return {
      sFilters: {
        billId: "",
        hosId: this.user.corpId,
        ssDate:[]
      },
      showTab: "purLst",
      sTotal: 0,
      sPage: 1,
      sPageSize: 20,
      sLoading: false,
      settleBillList: [],
      subProvLst:[],

      filters: {
        colId: "",
        hosId: this.user.corpId,
        provId: "",
        sDate: [],
        subProvId:""
      },
      total: 0,
      page: 1,
      pageSize: 100,
      loading: false,
      outBillDetailList: [],
      colProvLst: [],
      sltProv: {},
      sltRows: [],
      pickerOptions2: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            }
          }
        ]
      }
    };
  },
  filters: {
    fStatus: function(value) {
      if (value) {
        return OUTSTOCK_STATUS[value].text;
      } else {
        return "";
      }
    },
    SStatus: function(value) {
      if (value) {
        return SETTLE_STATUS[value].text;
      } else {
        return "";
      }
    }
  },
  components: { colAndProvs },
  methods: {
    ...mapActions(["setSettleBill"]),
    //获取列表
    getSettleBillList: function(pIndex) {
      this.sPage = pIndex;
      var param = {
        orderBy: "",
        page: this.sPage,
        rows: this.sPageSize,
        queryObject: {
          billId: this.sFilters.billId,
          hosId: this.sFilters.hosId,
          startDate:this.sFilters.ssDate[0],
          endDate:this.sFilters.ssDate[1],
        }
      };
      this.sLoading = true;
      this.axios.post("/spdHERPService/settleCenter/hosOutBalance/listByPage", param).then(
          res => {
            this.sLoading = false;
            if (res.data.code == 0 && res.data.data.data.length > 0) {
              this.settleBillList = res.data.data.data;
              this.sTotal = res.data.data.total;
            }
          },
          err => {
            this.sLoading = false;
          }
        );
    },
    sCurrentChange(val) {
      this.sPage = val;
      this.getSettleBillList(this.sPage);
    },
    sSizeChange(val) {
      this.sPageSize = val;
      this.getSettleBillList(this.sPage);
    },
    showBill(billId) {
      this.$router.push({ path: "settleBillView", query: { billId: billId } });
    },
    editBill(billId) {
      this.$router.push({
        path: "settleEdit",
        query: { settleBillId: billId }
      });
    },
    exportExcelBtnEvt() {
      var param = {
        queryObject: {
          billId: this.sFilters.billId,
          hosId: this.sFilters.hosId,
          startDate:this.sFilters.ssDate[0],
          endDate:this.sFilters.ssDate[1],
        }
      };
      this.sLoading = true;
      this.axios.post("/spdHERPService/settleCenter/hosOutBalance/getExportData", param).then(
          res => {
            this.sLoading = false;
            if (res.data.code == 0 && res.data.data.data.length > 0) {
               this.exportExcel(res.data.data.data);
            }else{
              this.$message.error('无数据导出！');
              return false;
            }
          },
          err => {
             this.sLoading = false;
          }
        );
    },
     /**导出excel */
    exportExcel:function(excelData){
        let tableHtml = '<table><thead><tr><th>结算单号</th><th>结算单行号</th><th>入库单号</th><th>入库单行号</th><th>商品名称</th>'
        +'<th>结算数量</th><th>销售价</th><th>采购价</th><th>规格型号</th><th>计量单位</th>'
        +'<th>批号</th><th>供应商</th><th>生产厂家</th><th>出库部门</th><th>批次号</th>'
        +'<th>生产日期</th><th>有效期</th><th>产地</th></tr></thead><tbody>';
        if(excelData.length>0){
            excelData.forEach(function(item,index){
                tableHtml+='<tr>'
                +'<td>'+item.billId+'</td>'
                +'<td>'+item.rowNum+'</td>'
                +'<td>'+item.outBillid+'</td>'
                +'<td>'+item.outRowNum+'</td>'
                +'<td>'+item.hosGoodsName+'</td>'
                +'<td>'+(item.settleQty==null?item.hosUnitQty:item.settleQty)+'</td>'
                +'<td>'+item.price+'</td>'
                +'<td>'+item.purPrice+'</td>'
                +'<td>'+item.goodsGg+'</td>'
                +'<td>'+item.hosUnit+'</td>'
                +'<td>'+item.batchCode+'</td>'
                +'<td>'+item.subProvName+'</td>'
                +'<td>'+item.mfrsName+'</td>'
                +'<td>'+item.outDeptName+'</td>'
                +'<td>'+item.batchId+'</td>'
                 +'<td>'+item.produceDatetime+'</td>'
                +'<td>'+item.expdtEndDate+'</td>'
                +'<td>'+item.made+'</td>'
                +'</tr>'
            });
            tableHtml+='</tbody></table>';
            $(tableHtml).tableExport({type:'excel',
                escape:'false',
                tableName:this.deptName+'结算报表'
            });
        }else{
            this.$message.error('无数据导出！');
            return false;
        }
    },
    statusFormat(row) {
      return SETTLE_STATUS[row.status].text;
    },
    //获取出库单列表
    getOutBillList: function(pIndex) {
      this.outBillDetailList = [];
      this.page = pIndex;
      let eDate = null;
      if(this.filters.sDate){
        eDate = moment(moment(this.filters.sDate[1]).add(1,'days').format('YYYY-MM-DD')).subtract(1,'seconds').format('YYYY-MM-DD HH:mm:ss')
      }
      var param = {
        orderBy: "",
        page: this.page,
        rows: this.pageSize,
        queryObject: {
          hosId: this.filters.hosId,
          colId: this.filters.colId,
          subProvId: this.filters.subProvId,
          startDate: this.filters.sDate?moment(this.filters.sDate[0]).format("YYYY-MM-DD"):null,
          endDate: eDate?eDate:null,
        }      
      };
      this.loading = true;
      this.axios.post('/spdHERPService/stockMgr/inStock/inBill4settle', param).then(
          res => {
            this.loading = false;
            if (res.data.code == "0") {
             this.outBillDetailList = res.data.data.data;
              this.total = res.data.data.total;
              this.outBillDetailList.forEach(x=>x.settleQty=x.canQty);
            }
          },
          err => {
            this.loading = false;
          }
        );
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getOutBillList(this.page);
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.getOutBillList(this.page);
    },
    showPurcDialog(billId) {
      this.$router.push({ path: "settleEdit", query: { outBillId: billId } });
    },
    provChange(provId) {
      this.sltProv = this.colProvLst.find(item => item.id == provId);
      this.getOutBillList(1);
    },
    subProvChange(){
      this.getOutBillList(1);
    },
    selectDateRangChange(){
       this.getOutBillList(1);
    },
    dateChangeEvt(){
      this.getSettleBillList(1);
    },
    getColProvLst() {
      this.axios.post("/spdHERPService/provMgr/distriRelation/getCollectorList/" +this.filters.hosId).then(
          res => {
             this.colProvLst = res.data.data;
             this.filters.colId=res.data.data[0].id;
             this.sltProv = res.data.data[0];
             this.getSubProvLst();
          },
          err => {
          }
        );
    },
    getSubProvLst() {
      var param = {
        queryObject: {
          hosId: this.filters.hosId,
          colId: this.filters.colId,
        }      
      };
      this.axios.post("/spdHERPService/settleCenter/hosOutBalance/getSettleSubProvList" ,param).then(
          res => {
             this.filters.subProvId=res.data.data[0].id;
             this.subProvLst = res.data.data;
             this.getOutBillList(1);
          },
          err => {
            this.loading = false;
          }
        );
    },
    sltRowsChange(rows) {
      this.sltRows = rows;
    },
    createSettleBill() {
      if (!Array.isArray(this.sltRows) || this.sltRows.length == 0) {
        this.$message.error("请至少选择一条记录。");
        return;
      }
      if ( this.sltRows.find(o=>o.settleQty==0)) {
        this.$message.error("结算数量不能为0。");
        return;
      }
      let sameSubProvId = this.sltRows[0].subProvId;
      if ( this.sltRows.find(o=>o.subProvId!=sameSubProvId)) {
        this.$message.error("不同供应商的商品不能一起结算！");
        return;
      }
      
      var settleBill = {
        hosId: this.user.corpId,
        hosName: this.user.corpName,
        hosErpCode: this.user.corpCode,
        provId: this.sltProv.id,
        provName: this.sltProv.cname,
        provErpCode: this.sltProv.erpCode,
        provDeptErpCode: "",
        filler: this.user.userId,
        fillterName: this.user.ename,
        fillDate: new Date(),
        remark: "",
        sumRow: "",
        status: 10,
        lstDetail: [],
        //invoiceNumber: "",
        //invoiceCode: "",
        settleAmount: 0,
        invoiceAmount: 0,
        settleType:10//入库结算
      };
      this.sltRows.forEach((item, index) => {
        settleBill.hosErpCode = item.hosErpCode;
        var inStockDetail = {
          id: "",
          pid: "",
          billId: "",
          rowNum: index + 1,
          provId: item.provId,
          provName: item.provName,
          provErpCode: item.provCode,
          outDeptId: item.outDeptId,
          outDeptName: item.outDeptName,
          outDeptErpCode: item.outDeptErpCode,
          outBillid: item.billId,
          outRowNum: item.outBillRow,
          outDate: item.outDate,
          hosGoodsId: item.goodsId,
          hosGoodsName: item.goodsName,
          goodsGg: item.goodsGg,
          mfrsId: item.mfrsId,
          mfrsName: item.mfrsName,
          made: item.made,
          packetCode: item.packetCode,
          provGoodsId: item.provGoodsId,
          provGoodsName: item.provGoodsName,
          batchCode: item.batchCode,
          sterilizationCode: item.sterilizationCode,
          expdtEndDate: item.expdtEndDate,
          produceDatetime: item.produceDatetime,
          hosUnit: item.unit,
          hosUnitQty: item.outQty,
         // hosUnitQty:item.settleQty,
          provUnit: item.provUnit,
          hosScale: item.hosScale,
          provUnitQty: item.outQty * item.hosScale,
          status: 10,
          outKind: item.outKind,
         //invoiceNumber: "",
          //invoiceCode: "",
          sterilizationDate: item.sterilizationDate,
          sterilizationEndDate: item.sterilizationEndDate,
          saleMan: item.saleMan,
          price: item.price,
          settleAmount: item.settleAmount,
          invoiceAmount: 0,

          subProvErpCode:item.subProvErpCode,
          subProvId:item.subProvId,
          subProvName:item.subProvName,

          receiveTemprature:item.receiveTemprature,
          sendTemprature:item.sendTemprature,
          receiveDate:item.receiveDate,
          deliveryDate:item.deliveryDate,
          purPrice:item.purPrice,
          transportType:item.transportType,
          settleQty:item.settleQty,
          batchId: item.batchId,
          goodsErpCode:item.goodsErpCode
        };
        settleBill.lstDetail.push(inStockDetail);
      });

      settleBill.sumRow = settleBill.lstDetail.length;
      this.setSettleBill(settleBill);
      this.$router.push({ path: "settleEdit" });
    }
  },
  mounted() {
    /*var end = new Date();
    var start = new Date();
    start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
    this.filters.startDate = [start, end];*/
     /**查询时间默认为 上月25号至本月25号 */
    var nowDate = new Date();
    var thisMonth = nowDate.getMonth() + 1;
    var thisDay = nowDate.getDate();
    var start = null;
    if(thisMonth==1){
        start = new Date((nowDate.getFullYear()-1)+'/'+12+'/26 00:00:00');
    }else{
        start = new Date(nowDate.getFullYear()+'/'+nowDate.getMonth()+'/26 00:00:00');
    }
    var end = new Date();
    end.setTime(end.getTime() + 3600 * 1000 * 24 * (25-thisDay));
    this.filters.sDate = [start, end];

    this.sFilters.ssDate = [start, end];
    this.sFilters.billId = this.$route.query.inStockBillId;
    this.sFilters.purchaseCompanyId = this.user.corpId;
    this.getSettleBillList(1);
    this.getColProvLst();
  }
};
</script>
<style>
#settle-bill-list{
    height: 100%;
}
#settle-bill-list .table_content {
  font-size: 12px;
  height: calc(100% - 90px);
  overflow-y: auto;
}
#settle-bill-list .table_2_content {
  font-size: 12px;
  height: calc(100% - 140px);
}
#settle-bill-list .el-tabs,
.el-tabs .el-tabs__content {
  height: 94%;
}
#settle-bill-list .el-tabs__item {
  height: 30px;
  line-height: 30px;
  font-size: 12px;
}
#settle-bill-list .pageBar{
    border-bottom: 1px solid #d1dbe5;
    border-left: 1px solid #d1dbe5;
    border-right: 1px solid #d1dbe5;
}
</style>

