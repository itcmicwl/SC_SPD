<template> 
    <el-tabs type="border-card" v-model="showTab" id="dblst">
        <el-tab-pane label="配送单列表" name="distrBillTab">
            <el-col :span="24" v-show="vDistrBillLst" style="height:100%;">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form :inline="true" label-width="80px">
                        <el-form-item label="二级供应商">
                            <secProvSlt :userId="user.userId" @subProvChange="subProvChange" @onLoad="onSecProvLoad"></secProvSlt>
                        </el-form-item>
                        <el-form-item label="是否转换">
                            <el-switch
                                v-model="filters.isConverted"
                                active-value="1"
                                inactive-value="0">
                            </el-switch>
                        </el-form-item>
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
                    <el-table-column prop="subProvName" label="二级供应商名称" sortable width="200" >
                    </el-table-column>
                    <el-table-column prop="purchaseCompanyName" label="请购机构" sortable width="200">
                    </el-table-column>
                    <el-table-column prop="applyOrgName" label="请购科室" width="120" sortable>
                    </el-table-column>
                    <el-table-column prop="recieveOrgName" label="收货科室" width="120" sortable>
                    </el-table-column>
                    <el-table-column prop="isConverted" label="是否转换" width="90" sortable>
                        <template slot-scope="scope">
                            {{scope.row.isConverted ==0?"未转换":"已转换"}}
                        </template>
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
                            <el-button  @click="showDistrBillView(scope.row.billId)" type="text">查看</el-button>
                            <el-button v-if="scope.row.status == 11"  @click="editDistBillPrice(scope.row.billId)" type="text">编辑价格</el-button>
                            <el-button v-if="scope.row.status == 10"  @click="editDistBill(scope.row.billId)" type="text">编辑</el-button>
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
                        <el-form-item label="二级供应商">
                            <secProvSlt :userId="user.userId" @subProvChange="cSubProvChange" @onLoad="onSecProvLoadP"></secProvSlt>
                        </el-form-item>
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
                     <el-table-column prop="subProvName" label="二级供应商名称" sortable width="200" >
                    </el-table-column>
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
                            <el-button  @click="PurconfirmView(scope.row.billId)" type="text">查看</el-button>
                            <el-button  @click="createDistrBill(scope.row.billId)" type="text">发货</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!--工具条-->
                <el-col :span="24" class="toolbar">
                    <el-pagination @size-change="chandleSizeChange" @current-change="chandleCurrentChange" :current-page="cPage" :page-sizes="[30, 100, 200, 400]" :page-size="cPageSize" layout="total, sizes, prev, pager, next" :total="cTotal" style="float:right;" small>
                    </el-pagination>
                </el-col>
                <el-dialog title="确认单查看" width="80%" :visible.sync="confirmViewVisible" :close-on-click-modal="false" >
                    <purconfirmView :billid="purcfirmbillid"></purconfirmView>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click.native="confirmViewVisible = false" >关闭</el-button>
                    </div>
                </el-dialog>
            </el-col>
            <el-col :span="24" v-show="!purcListShow" style="height:100%;">
                <addDistrBIllByTab :pcBillId="pcBillId" :distrBillId="distrBillId" v-on:cancel="addCancel" v-on:creatDistrBill="saveBill"></addDistrBIllByTab>
            </el-col>
        </el-tab-pane>
    </el-tabs>
</template>
<script>
import distBillView from './distBillView';
import editDistrPrice from './editDistrPrice';
import addDistrBIllByTab from './addDistrBIllByTab';
import secProvSlt from '@/components/secProvSlt';
import purconfirmView from '../orderMgr/purconfirmView';
import { DISTRBILL_STATUS, PURCONFIRM_STATUS } from '../orderMgr/common/constance';
export default {
    data() {
        return {
            showTab: 'distrBillTab',
            filters: {
                billId: '',
                subProvId:'',
                isConverted:1,
                hosLst:[]
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
                billId: '',
                subProvId:'',                
                hosLst:[]
            },
            cloading: false,
            cTotal: 0,
            cPage: 1,
            cPageSize: 30,
            purcListShow: true,
            vDistrBillLst:true,
            vDistrBillPrice:false,
            vDistBillView:false,
            purconfirmList: [],
            userHos:[]
        }
    },
    components: { addDistrBIllByTab, purconfirmView ,secProvSlt,editDistrPrice,distBillView},
    methods: {        
        subProvChange(subProvInfo){
            if(subProvInfo){
                 this.filters.subProvId = subProvInfo.provId;
                 this.filters.hosLst=subProvInfo.hosLst.map(o=>o.hosId);
            }else{
                this.filters.subProvId = '';
                this.filters.hosLst=[];
            }           
        },
        cSubProvChange(subProvInfo){
            if(subProvInfo){
                 this.purcfilters.subProvId = subProvInfo.provId;
                 this.purcfilters.hosLst=subProvInfo.hosLst.map(o=>o.hosId);
            }else{
                this.purcfilters.subProvId = '';
                this.purcfilters.hosLst=[];
            }           
        },
        // addPurchase() {
        //     this.$router.push("addDistrBill");
        // },
        //获取列表
        getDistrBillList: function (pIndex) {
            this.page = pIndex;
			/*
            if(!this.filters.subProvId){
                return;
            }
			*/
            var param = {
                orderBy: "",
                page: this.page,
                rows: this.pageSize,
                queryObject: {
                    billId: this.filters.billId,
                    provId: this.user.corpId,
                    subProvId:this.filters.subProvId,
                    hosArr:this.filters.hosLst,
                    isConverted:this.filters.isConverted
                }
            };
            this.loading = true;
            this.axios.post('/spdHDIService/orderMgr/distrBill/secListByPage', param).then(res => {
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
            if(row.isConverted==0 && row.status==20){
                return "待转换";
            }else{
                return DISTRBILL_STATUS[row.status].text;
            }
        },
        showDistrBillView(billId) {
            //this.$router.push({ path: 'distBillView', query: { billId: billId } });
            this.vDistrBillLst = false;
            this.vDistrBillPrice=false;
            this.vDistBillView=true;
            this.$refs.distrBillViewer.showBill(billId);
        },
        distrBillViewClose(){
             this.vDistrBillLst = true;
            this.vDistrBillPrice=false;
            this.vDistBillView=false;
        },
        cstatusFormat(row) {
            return PURCONFIRM_STATUS[row.status.toString()].text;
            //return row.status.toString();
        },
        getPurconfirmList: function (pIndex) {
            this.cPage = pIndex;
           /* if(!this.purcfilters.subProvId){
                return;
            }
			*/
            var param = {
                orderBy: "",
                page: this.cPage,
                rows: this.cPageSize,
                queryObject: {
                    billId: this.purcfilters.billId,
                    provId: this.user.corpId,
                    subProvId:this.purcfilters.subProvId,
                    hosLst:this.purcfilters.hosLst,
                    arrStatus: [20, 30]
                }
            };
            this.cloading = true;
            this.axios.post('/spdHDIService/orderMgr/purconfirm/secListByPage', param).then(res => {
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
            this.pcBillId = '';
            this.distrBillId = billId;
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
            this.pcBillId = billId;
            this.distrBillId = '';

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
            this.getPurconfirmList(this.cPage);
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
        PurconfirmView(billId) {
            this.purcfirmbillid = billId;
            this.confirmViewVisible = true;
        },
        onSecProvLoad(subProvInfo){
            this.filters.subProvId = subProvInfo.provId;
            this.filters.hosLst=subProvInfo.hosLst.map(o=>o.hosId);
            this.getDistrBillList(1);
        },
        onSecProvLoadP(subProvInfo){
           this.purcfilters.subProvId = subProvInfo.provId;
            this.purcfilters.hosLst=subProvInfo.hosLst.map(o=>o.hosId);
            this.getPurconfirmList(1);
        }
    },
    mounted() {
        var billId = this.$route.query.billId;
        if (billId) {
            this.filters.billId = billId;
        }
        //this.getDistrBillList(1);
        //this.getPurconfirmList(1);
        
    }
};

</script>

<style>
#dblst .table_content{
    font-size: 12px;
    height: calc(100% - 80px);
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