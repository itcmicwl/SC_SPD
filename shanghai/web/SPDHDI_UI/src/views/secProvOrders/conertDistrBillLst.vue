<template> 
<!--配送单转换-->
    <el-row style="height:100%;">
        <el-row v-show="purcListShow" style="height:100%;">
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
            <el-table :data="distrBillList" highlight-current-row v-loading="loading" style="width: 100%;" fit class="c_distrBill_table_content" height="outer">
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
                <el-table-column prop="isConverted" label="是否转换" sortable>
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
                        <el-button  @click="showDistrBill(scope.row.billId)" type="text">查看</el-button>
                        <el-button v-if="scope.row.isConverted==0 &&  scope.row.status == 20"  @click="convertBill(scope.row.billId)" type="text">转换</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <!--工具条-->
            <el-col :span="24" class="toolbar">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[30, 100, 200, 400]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
                </el-pagination>
            </el-col>
        </el-row>
        <el-row v-show="vDistBillConverter" style="height:100%;">
            <distrBillConvert ref="billConverter" v-on:cancel="addCancel" v-on:submitOrder="saveBill"></distrBillConvert>
        </el-row>
        <el-row v-show="vDistBillView" style="height:100%;">
            <distBillView ref="distrBillViewer" @close="distrBillViewClose"></distBillView>
        </el-row>
    </el-row>
</template>
<script>
import distrBillConvert from './distrBillConvert';
import distBillView from './distBillView';
import secProvSlt from '@/components/secProvSlt';
import { DISTRBILL_STATUS, PURCONFIRM_STATUS } from '../orderMgr/common/constance';
export default {
    data() {
        return {
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
            distrBillList:[],
            distrBillId: '',
            purcfirmbillid: '',
            purcListShow: true,
            vDistBillConverter:false,
            vDistBillView:false
        }
    },
    components: { distrBillConvert, secProvSlt,distBillView},
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
        onSecProvLoad(subProvInfo){
            this.filters.subProvId = subProvInfo.provId;
            this.filters.hosLst=subProvInfo.hosLst.map(o=>o.hosId);
            this.getDistrBillList(1);
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
                    provId: this.user.corpId,
                    subProvId:this.filters.subProvId,
                    hosArr:this.filters.hosLst,
                    isConverted:this.filters.isConverted
                   // status:20
                }
            };
            this.loading = true;
            this.axios.post('/spdHDIService/orderMgr/distrBill/secListByPage', param).then(res => {
                this.loading = false;
                this.distrBillList = res.data.data.data;
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
        statusFormat(row) {
            return DISTRBILL_STATUS[row.status].text;
        },
        showDistrBill(billId) {
            //this.$router.push({ path: 'distBillView', query: { billId: billId } });
            this.purcListShow = false;
            this.vDistBillConverter = false;
            this.vDistBillView = true;
            this.$refs.distrBillViewer.showBill(billId);
        }, 
        distrBillViewClose(){
            this.purcListShow = true;
            this.vDistBillConverter = false;
            this.vDistBillView = false;
        },
        convertBill(billId) {
            this.purcListShow = false;
            this.vDistBillConverter = true;
            this.vDistBillView = false;
            this.$refs.billConverter.setBillId(billId);
        },        
        saveBill(billId) {
            this.purcListShow = true;
            this.vDistBillConverter = false;
            this.vDistBillView = false;
            this.getDistrBillList(1);
        },
        addCancel() {
            this.purcListShow = true;
            this.vDistBillConverter = false;
            this.vDistBillView = false;
        },
        PurconfirmView(billId) {
            this.purcfirmbillid = billId;
            this.confirmViewVisible = true;
        }
    },
    mounted() {
        var billId = this.$route.query.billId;
        if (billId) {
            this.filters.billId = billId;
        }        
    }
};

</script>

<style>
.c_distrBill_table_content{
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