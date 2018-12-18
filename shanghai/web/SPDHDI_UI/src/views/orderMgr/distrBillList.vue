<template>
    <el-tabs type="border-card" v-model="showTab" id="dBillTab">
        <el-tab-pane label="配送单列表" name="distrBillTab" >
            <el-col :span="24" v-show="vDistrBillLst" style="height:100%;">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form :inline="true" label-width="80px">
                        <el-form-item label="配送单号">
                            <el-input v-model="filters.billId" placeholder="配送单号" ></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" icon="search" v-on:click="getDistrBillList(1)" >查询</el-button>
                        </el-form-item>
                    </el-form>
                </el-col>
                <!--列表-->
                <el-table :data="purchaseList" highlight-current-row v-loading="loading" style="width: 100%;" fit class="table_content">
                    <el-table-column prop="billId" label="配送单号"  width="150" sortable>
                    </el-table-column>
                    <el-table-column prop="status" label="状态" sortable :formatter="statusFormat">
                    </el-table-column>
                    <el-table-column prop="purchaseCompanyName" label="请购机构" sortable width="200">
                    </el-table-column>
                    <el-table-column prop="applyOrgName" label="请购科室" width="120" sortable>
                    </el-table-column>
                    <el-table-column prop="recieveOrgName" label="收货科室" width="120" sortable>
                    </el-table-column>
                    <el-table-column prop="fillterName" label="制单人" sortable>
                    </el-table-column>
                    <el-table-column prop="fillDate" label="制单时间">
                        <template slot-scope="scope">
                            {{scope.row.fillDate | dateFormat('YYYY-MM-DD')}}
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="150">
                        <template slot-scope="scope">
                            <el-button  @click="showDistBillView(scope.row.billId)" type="text">查看</el-button>
                            <el-button v-if="scope.row.status == 10"  @click="editDistBill(scope.row.billId)" type="text">编辑</el-button>
                            <el-button v-if="scope.row.status == 11"  @click="editDistBillPrice(scope.row.billId)" type="text">编辑价格</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!--工具条-->
                <el-col :span="24" class="toolbar">
                    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[30, 100, 200, 400]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
                    </el-pagination>
                </el-col>
            </el-col>
            <el-row v-show="vDistrBillPrice" style="height:100%;">
                <editDistrPrice ref="billConverter" v-on:cancel="p_addCancel" v-on:submitOrder="p_saveBill"></editDistrPrice>
            </el-row>
            <el-row v-show="vDistBillView" style="height:100%;">
                <distBillView ref="distrBillViewer" @close="distrBillViewClose"></distBillView>
            </el-row>
        </el-tab-pane>
        <el-tab-pane label="我要发货" name="creatDistrBillTab">
            <el-col :span="24" v-show="purcListShow" style="height:100%;">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form :inline="true" label-width="80px">
                        <el-form-item label="确认单号">
                            <el-input v-model="purcfilters.billId" placeholder="配送单号" ></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" icon="search" v-on:click="getPurconfirmList(1)" >查询</el-button>
                        </el-form-item>
                    </el-form>
                </el-col>
                <el-table :data="purconfirmList" highlight-current-row v-loading="cloading" style="width: 100%;" fit class="table_content">
                    <el-table-column prop="billId" label="确认单号" sortable width="150">
                    </el-table-column>
                    <el-table-column prop="status" label="状态" sortable :formatter="cstatusFormat"></el-table-column>
                    <el-table-column prop="purchaseCompanyName" label="请购机构" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="applyOrgName" label="请购科室" width="120" sortable>
                    </el-table-column>
                    <el-table-column prop="recieveOrgName" label="收货科室" width="120" sortable>
                    </el-table-column>
                    <el-table-column prop="sumAmount" label="含税金额">
                    </el-table-column>
                    <el-table-column prop="sumTaxAmount" label="不含税金额">
                    </el-table-column>
                    <el-table-column prop="fillterName" label="制单人" sortable>
                    </el-table-column>
                    <el-table-column prop="fillDate" label="制单时间">
                        <template slot-scope="scope">
                            {{scope.row.fillDate | dateFormat('YYYY-MM-DD')}}
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="120">
                        <template slot-scope="scope">
                            <el-button  @click="showPurconfirmView(scope.row.billId)" type="text">查看</el-button>
                            <el-button  @click="createDistrBill(scope.row.billId)" type="text">发货</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!--工具条-->
                <el-col :span="24" class="toolbar">
                    <el-pagination @size-change="chandleSizeChange" @current-change="chandleCurrentChange" :current-page="cPage" :page-sizes="[30, 100, 200, 400]" :page-size="cPageSize" layout="total, sizes, prev, pager, next" :total="cTotal" style="float:right;" small>
                    </el-pagination>
                </el-col>
            </el-col>
            <el-col :span="24" v-show="vAddDistrBill" style="height:100%;">
                <addDistrBIllByTab ref="distrEditer" v-on:cancel="addCancel" v-on:creatDistrBill="saveBill"></addDistrBIllByTab>
            </el-col>
            <el-row v-show="vPurcView" style="height:100%;">
                <purconfirmViewNew ref="purcBillViewer" @close="purcBillViewClose"></purconfirmViewNew>
            </el-row>
        </el-tab-pane>
    </el-tabs>
</template>
<script>
import addDistrBIllByTab from './addDistrBIllByTab';
import purconfirmViewNew from './purconfirmViewNew';
import distBillView from './distBillView';
import editDistrPrice from './editDistrPrice';
import { DISTRBILL_STATUS, PURCONFIRM_STATUS } from './common/constance';
export default {
    data() {
        return {
            showTab: 'distrBillTab',
            filters: {
                billId: '',
                userHos:[]
            },
            total: 0,
            page: 1,
            pageSize: 30,
            loading: false,
            purchaseList: [],
            confirmViewVisible: false,
            pcBillId: '',
            distrBillId: '',
            purcfirmbillid: '',
            purcfilters: {
                billId: ''
            },
            cloading: false,
            cTotal: 0,
            cPage: 1,
            cPageSize: 30,
            vDistrBillLst:true,
            vDistBillView:false,
            vDistrBillPrice:false,
            purcListShow: true,
            vAddDistrBill:false,
            vPurcView:false,
            purconfirmList: []
        }
    },
    components: { addDistrBIllByTab, purconfirmViewNew, editDistrPrice, distBillView },
    methods: {

        getUserHos(){
            var _this = this;
            return this.axios.post(`/spdHDIService/myInfo/userHos/getHosLists/${this.user.userId}`);
        },
        //获取列表
        getDistrBillList: function (pIndex) {
            this.page = pIndex;
            var param = {
                orderBy: "",
                page: this.page,
                rows: this.pageSize,
                queryObject: {
                    billId: this.filters.billId,
                   // provId: this.user.corpId,
                    subProvId:this.user.corpId,
                    hosArr:this.filters.userHos
                   // isConverted:1
                }
            };
            this.loading = true;
            this.axios.post('/spdHDIService/orderMgr/distrBill/listByPage', param).then(res => {
                this.loading = false;
                this.purchaseList = res.data.data.data;
                this.total = res.data.data.total;
            }, err => {
                this.loading = false;
            });
        },
        handleCurrentChange(val) {
            this.page = val;
            this.getDistrBillList(this.page);
        },
        handleSizeChange(val) {
            this.pageSize = val;
            this.getDistrBillList(this.page);
        },
        delPurchase(purchase) {
            this.$confirm('确认删除这条记录吗?', '提示', {
            }).then(() => {
                this.loading = true;
                this.axios.post("/spdHDIService/order/purchase/delPurchase", purchase).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
                        this.loading = false;
                        this.$message({ message: '操作成功！', type: 'success' });
                    } else {
                        this.$message.error('操作失败！');
                    }
                    this.getDistrBillList(this.page);
                }, err => {
                    this.loading = false;
                });
            }).catch(() => {

            });
        },
        statusFormat(row) {
            return DISTRBILL_STATUS[row.status].text;
        },
        showDistBillView(billId) {
            //this.$router.push({ path: 'distBillView', query: { billId: billId } });
            this.vDistrBillLst = false;
            this.vDistBillView=true;
            this.$refs.distrBillViewer.showBill(billId);
        },
        distrBillViewClose(){
            this.vDistrBillLst = true;
            this.vDistBillView=false;
        },
        cstatusFormat(row) {
            return PURCONFIRM_STATUS[row.status.toString()].text;
            //return row.status.toString();
        },
        getPurconfirmList: function (pIndex) {
            this.cPage = pIndex;
            var param = {
                orderBy: "",
                page: this.cPage,
                rows: this.cPageSize,
                queryObject: {
                    billId: this.purcfilters.billId,
                  //  provId: this.user.corpId,
                    subProvId: this.user.corpId,
                    hosLst:this.filters.userHos,
                    arrStatus: [20, 30]
                }
            };
            this.cloading = true;
            this.axios.post('/spdHDIService/orderMgr/purconfirm/listByPage', param).then(res => {
                this.cloading = false;
                this.purconfirmList = res.data.data.data;
                this.cTotal = res.data.data.total;
            }, err => {
                this.cloading = false;
            });
        },
        chandleCurrentChange(val) {
            this.cPage = val;
            this.getPurconfirmList(this.cPage);
        },
        chandleSizeChange(val) {
            this.cPageSize = val;
            this.getPurconfirmList(this.cPageSize);
        },
        editDistBill(billId) {
            this.purcListShow = false;
            this.showTab = "creatDistrBillTab";
            // this.pcBillId = '';
            // this.distrBillId = billId;
             this.$refs.distrEditer.show(billId,1);
              this.vAddDistrBill = true;
            this.vPurcView = false;
        },
        editDistBillPrice(billId){
            this.vDistrBillLst = false;
            this.vDistrBillPrice=true;
            this.vDistBillView=false;
            this.$refs.billConverter.setBillId(billId);
        },
        createDistrBill(billId) {
            this.showTab = "creatDistrBillTab";
            this.purcListShow = false;
            // this.pcBillId = billId;
            // this.distrBillId = '';
            this.$refs.distrEditer.show(billId,0);
            this.vAddDistrBill = true;
            this.vPurcView = false;

        },
        saveBill(billId) {
            this.purcListShow = true;
            this.showTab = "distrBillTab";
            this.addCancel();
            if (billId) {
                this.filters.billId = billId;
                this.getDistrBillList(1);
            }
        },
        addCancel() {
            this.pcBillId = '';
            this.distrBillId = '';
            this.purcListShow = true;
            this.vAddDistrBill=false;
            this.vPurcView = false;
        },
        p_saveBill(billId) {
            this.vDistrBillLst = true;
            this.vDistrBillPrice=false;
            this.vDistBillView=false;
            this.getDistrBillList(1);
        },
        p_addCancel() {
            this.vDistrBillLst = true;
            this.vDistrBillPrice=false;
            this.vDistBillView=false;
        },
        showPurconfirmView(billId) {
            this.purcListShow = false;
            this.vAddDistrBill=false;
            this.vPurcView = true;
            this.$refs.purcBillViewer.showBill(billId);
        },
        purcBillViewClose(){
            this.purcListShow = true;
            this.vAddDistrBill=false;
            this.vPurcView = false;
        }
    },
    mounted() {
        var billId = this.$route.query.billId;
        if (billId) {
            this.filters.billId = billId;
        }
        this.loading = true;
        var _this =this;
        this.getUserHos().then(res=>{
            _this.loading = false;
            if(res.data.code == 0){
                _this.filters.userHos = res.data.data.map(o=>o.hosId);
                _this.getDistrBillList(1);
                _this.getPurconfirmList(1);
            }
        },err=>{_this.loading = false;});

    }
};

</script>

<style scoped>
.table_content{
    font-size: 12px;
    height: calc(100% - 75px);
    overflow-y: auto;
}

.el-tabs,
.el-tabs .el-tabs__content {
    height: 94%;
}

.el-tabs__item {
    height: 30px;
    line-height: 30px;
    font-size: 12px;
}
</style>
