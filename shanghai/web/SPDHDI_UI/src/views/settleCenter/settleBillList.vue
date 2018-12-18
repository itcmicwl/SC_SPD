<template>
    <el-tabs type="border-card" v-model="showTab">
        <el-tab-pane label="结算单列表" name="purconfirmLst">
            <el-col :span="24" style="height:100%">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form :inline="true" label-width="80px" ref="searchForm" v-model="sFilters">
                        <el-form-item label="入库单号">
                            <el-input v-model="sFilters.billId" placeholder="单号" ></el-input>
                        </el-form-item>
                        
                        <el-form-item label="制单日期">
                         <el-date-picker
                            v-model="sFilters.ssDate"  type="datetimerange" align="right" start-placeholder="开始日期"  @change="dateChangeEvt"
                            end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" :editable="false" :clearable="false" unlink-panels>
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="未上传">
                            <el-switch v-model="sFilters.status"  @change="stateChangeEvt"></el-switch>
                        </el-form-item>
                        <el-form-item>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" icon="search" style="width:60px;" v-on:click="getSettleBillList(1)" >查询</el-button>
                        </el-form-item>
                    </el-form>
                </el-col>
                <!--列表-->
                <el-table :data="settleBillList" highlight-current-row v-loading="sLoading" style="width: 100%;"
                 fit class="table_content">
                     <el-table-column label="操作" width="150">
                        <template slot-scope="scope">
                            <el-button  @click="showBill(scope.row.billId)" type="text">查看</el-button>
                            <el-button  @click="upBillToErp(scope.row.billId)" type="text" v-if="scope.row.status==20">结算单上传</el-button>
                          <!--  <el-button  @click="editBill(scope.row.id)" type="text" v-if="scope.row.status==30">编辑</el-button>-->
                        </template>
                    </el-table-column>
                    <el-table-column prop="billId" label="结算单号"  width="150">
                    </el-table-column>
                    <el-table-column prop="status" label="状态" :formatter="statusFormat">
                    </el-table-column>
                    <el-table-column prop="hosName" label="付款单位" >
                    </el-table-column>
                    <el-table-column prop="provName" label="结算单位"  width="220" show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column prop="provDeptErpName" label="结算部门" >
                    </el-table-column>
                    <el-table-column prop="sumRow" label="笔数" >
                    </el-table-column>
                    <el-table-column prop="fillterName" label="制单人" >
                    </el-table-column>
                    <el-table-column prop="fillDate" label="制单时间">
                        <template slot-scope="scope">
                            {{scope.row.fillDate | dateFormat('YYYY-MM-DD')}}
                        </template>
                    </el-table-column>
                </el-table>
                <!--分页条-->
                <el-pagination @size-change="sSizeChange" @current-change="sCurrentChange" :current-page="sPage" :page-sizes="[30, 100, 200, 400]"
                 :page-size="sPageSize" layout="total, sizes, prev, pager, next" :total="sTotal" style="float:right;" small>
                </el-pagination>
            </el-col>
        </el-tab-pane>
    </el-tabs>
</template>
<script>
import colAndProvs from '../../components/colAndProvs';
import { SETTLE_STATUS,OUTSTOCK_STATUS } from '../../common/js/constance';
import { mapActions } from 'vuex';
export default {
    data() {
        return {
            sFilters: {
                billId: '',
                hosId:this.user.corpId,
                ssDate:[],
                status:true
            },
            showTab:'purconfirmLst',
            sTotal: 0,
            sPage: 1,
            sPageSize: 30,
            sLoading: false,
            settleBillList: [
            ],

            filters: {
                colId:'',
                hosId:this.user.corpId,
                provId:'',
                startDate:'',
                endDate:''

            },
            total: 0,
            page: 1,
            pageSize: 10,
            loading: false,
            outBillDetailList: [
            ],
            colProvLst:[],
            sltProv:{},
            sltRows:[],
            pickerOptions2: {
            shortcuts: [{
                text: '最近一周',
                onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                    picker.$emit('pick', [start, end]);
                }
            }, {
                text: '最近一个月',
                onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                    picker.$emit('pick', [start, end]);
                }
            }, {
                text: '最近三个月',
                onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                    picker.$emit('pick', [start, end]);
                }
            }]
        }
        }
    },
    filters: {
        fStatus: function (value) {
            if (value) {
                return OUTSTOCK_STATUS[value].text;
            } else {
                return '';
            }
        },
        SStatus:function(value){
            if (value) {
                return SETTLE_STATUS[value].text;
            } else {
                return '';
            }
        }
    },
    components: { colAndProvs},
    methods: {
        ...mapActions([
            'setSettleBill'
        ]),
        //获取列表
        getSettleBillList: function (pIndex) {
            this.sPage = pIndex;
            var param = {
                orderBy: "",
                page: this.sPage,
                rows: this.sPageSize,
                queryObject: {
                    billId: this.sFilters.billId,
                    provId:this.sFilters.purchaseCompanyId,
                    startDate:this.sFilters.ssDate[0],
                    endDate:this.sFilters.ssDate[1],
                    status : this.sFilters.status?20:30
                }
            };
            this.sLoading = true;
            this.axios.post('/spdHDIService/settleCenter/hosOutBalance/listByPage', param).then(res => {
                this.sLoading = false;
                if(res.data.code == 0 && res.data.data.data){
                    this.settleBillList = res.data.data.data;
                    this.sTotal = res.data.data.total;
                }
            }, err => {
                this.sLoading = false;
            });
        },
        dateChangeEvt(){
            this.getSettleBillList(1);
        },
        stateChangeEvt(){
            this.getSettleBillList(1);
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
            this.$router.push({ path: 'settleBillView', query: { billId: billId} });
        },
        // 结算单上传
        upBillToErp(billId){
             this.$confirm('确认上传结算单吗?', '提示', {
            }).then(() => {
                this.loading = true;
                this.axios.get("/spdHDIService/settleCenter/hosOutBalance/upBillToErp/" + billId).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
                        this.loading = false;
                        if(res.data.data.code == 0)
                        {
                            this.$message({ message: '操作成功！', type: 'success' });
                        }else{
                            this.$message.error(res.data.data.message);
                        }
                    }else if(res.data.code == "-9999"){ // JDE接口服务异常
                            this.$message.error(res.data.msg);
                    }else if(res.data.code == "-9998"){ // JDE接口方法调用失败
                            this.$message.error(res.data.msg);
                    }else if(res.data.code == "-1000"){ // 系统内部异常
                            this.$message.error(res.data.msg);
                    }else{ // 未知
                        this.$message.error("操作失败！");
                    }
                    this.getSettleBillList(this.sPage);
                }, err => {
                    this.loading = false;
                });
            }).catch(() => {

            });
        },
        editBill(billId){
            this.$router.push({ path: 'settleEdit', query: { settleBillId: billId } });
        },
        resetForm(){
            this.sFilters.billId = "";
        },
        statusFormat(row) {
            return SETTLE_STATUS[row.status].text;
        },
        //获取出库单列表
        getOutBillList: function (pIndex) {
            this.outBillDetailList = [];
            this.page = pIndex;
            var param = {
                orderBy: "",
                page: this.page,
                rows: this.pageSize,
                queryObject: {
                    hosId: this.filters.hosId,
                    colId: this.filters.colId,
                    provId: this.filters.provId,
                    startDate:this.filters.startDate[0],
                    endDate:this.filters.startDate[1],
                }
            };
            this.loading = true;
            this.axios.post('/spdHERPService/stockMgr/out4In/outBill4settle', param).then(res => {
                this.loading = false;
                this.outBillDetailList = res.data.data.data;
                this.total = res.data.data.total;
            }, err => {
                this.loading = false;
            });
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
            this.$router.push({ path: 'settleEdit', query: { outBillId: billId } });
        },
        provChange(provId){
            this.sltProv = this.colProvLst.find(item=>item.id == provId);
            this.getOutBillList(1);
        },
        getColProvLst(){
            this.loading = true;
            this.axios.post('/spdHDIService/provMgr/distriRelation/getCollectorList/' + this.filters.hosId).then(res => {
                this.loading = false;
                this.colProvLst = res.data.data;
            }, err => {
                this.loading = false;
            });
        },
        sltRowsChange(rows){
            this.sltRows = rows;
        },
        createSettleBill(){
            if(!Array.isArray(this.sltRows) || this.sltRows.length == 0){
                this.$message.error('请至少选择一条记录。');
                return;
            }
            var settleBill = {
                hosId: this.user.corpId,hosName:this.user.corpName,hosErpCode:this.user.corpCode,
                provId:this.sltProv.id,provName:this.sltProv.cname,provErpCode:this.sltProv.erpCode,
		        provDeptErpCode:'',filler:this.user.userId,fillterName:this.user.ename,fillDate:new Date(),remark:'',sumRow:'',status:10,lstDetail:[],invoiceNumber:'',invoiceCode:''
            };
            this.sltRows.forEach((item,index)=>{
                var inStockDetail = {
                    id:'',pid:'',billId:'',rowNum:index + 1,provId:item.provId,provName:item.provName,provErpCode:item.provCode,outDeptId:item.outDeptId,
                    outDeptName:item.outDeptName,outDeptErpCode:item.outDeptErpCode,outBillid:item.billId,outRowNum:item.outBillRow,outDate:'',hosGoodsId:item.goodsId,
                    hosGoodsName:item.goodsName,goodsGg:item.goodsGg,mfrsId:item.mfrsId,mfrsName:item.mfrsName,made:item.made,packetCode:item.packetCode,provGoodsId:item.provGoodsId,provGoodsName:item.provGoodsName,
                    batchId:'',batchCode:item.batchCode,sterilizationCode:item.sterilizationCode,expdtEndDate:item.expdtEndDate,produceDatetime:'',hosUnit:item.unit,
                    hosUnitQty:item.outQty,provUnit:item.provUnit,hosScale:item.hosScale,provUnitQty:item.outQty * item.hosScale,status:10,outKind:item.outKing,invoiceNumber:'',invoiceCode:'',
                    sterilizationDate:item.sterilizationDate,sterilizationEndDate:item.sterilizationEndDate,saleMan:item.saleMan
                };
                settleBill.lstDetail.push(inStockDetail);
            });
            settleBill.sumRow = settleBill.lstDetail.length;
            this.setSettleBill(settleBill);
            this.$router.push({ path: 'settleEdit'});
        }
    },
    mounted() {
        this.sFilters.billId = this.$route.query.inStockBillId;
        this.sFilters.purchaseCompanyId = this.user.corpId;
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
        this.getSettleBillList(1);
        //this.getColProvLst();
    }
}
</script>
<style scoped>
.table_content{
    font-size: 12px;
    height: calc(100% - 140px);
    overflow-y: auto;
}
.table_2_content{
    font-size: 12px;
    height: calc(100% - 130px);
    overflow-y: auto;
}
.el-tabs,.el-tabs .el-tabs__content{
    height:94%;
}
.el-tabs__item{
    height:30px;
    line-height: 30px;
    font-size:12px;
}
</style>

