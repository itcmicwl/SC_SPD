<template>
    <el-tabs type="border-card" v-model="showTab">
        <el-tab-pane label="收货单列表" name="purconfirmLst">
            <section>
                <el-col :span="24">
                    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                        <el-form :inline="true" label-width="80px" ref="searchForm" v-model="filters">
                            <el-form-item label="收货单号">
                                <el-input v-model="filters.billId" placeholder="单号" ></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" icon="search" v-on:click="getRecBillList(1)" >查询</el-button>
                                <el-button @click="resetForm" >重置</el-button>
                            </el-form-item>
                        </el-form>
                    </el-col>
                    <!--列表-->
                    <template>
                        <el-table :data="receiveBillList" highlight-current-row v-loading="loading" style="width: 100%;" fit height="420">
                            <el-table-column prop="billId" label="收货单号" sortable width="120">
                            </el-table-column>
                            <el-table-column prop="provName" label="配送机构" sortable>
                            </el-table-column>
                            <el-table-column prop="applyOrgName" label="请购科室" sortable>
                            </el-table-column>
                            <el-table-column prop="recieveOrgName" label="收货科室" sortable>
                            </el-table-column>
                            <el-table-column prop="revieverName" label="收货人" sortable>
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
                                    <el-button  @click="recBillView(scope.row.billId)" type="text">查看</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <!--工具条-->
                        <el-col :span="24" class="toolbar">
                            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[30, 100, 200, 400]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
                            </el-pagination>
                        </el-col>
                    </template>
                </el-col>
            </section>
        </el-tab-pane>
        <el-tab-pane label="我要收货" name="purLst">
            <recevieOrder v-on:saveRecBill="saveRecBill"></recevieOrder>
        </el-tab-pane>
    </el-tabs>
</template>
<script>
import recevieOrder from './recevieOrder';
import { PURCONFIRM_STATUS } from './common/constance';
export default {
    data() {
        return {
            filters: {
                billId: '',
                purchaseCompanyId:''
            },
            showTab:'purconfirmLst',
            total: 0,
            page: 1,
            pageSize: 30,
            loading: false,
            receiveBillList: [
            ]
        }
    },
    methods: {
        //获取列表
        getRecBillList: function (pIndex) {
            this.page = pIndex;
            var param = {
                orderBy: "",
                page: this.page,
                rows: this.pageSize,
                queryObject: {
                    billId: this.filters.billId,
                    purchaseCompanyId:this.filters.purchaseCompanyId
                }
            };
            this.loading = true;
            this.axios.post('/spdHDIService/orderMgr/receiveBill/listByPage', param).then(res => {
                this.loading = false;
                this.receiveBillList = res.data.data.data;
                this.total = res.data.data.total;
            }, err => {
                this.loading = false;
            });
        },
        handleCurrentChange(val) {
            this.page = val;
            this.getRecBillList(this.page);
        },
        handleSizeChange(val) {
            this.pageSize = val;
            this.getRecBillList(this.page);
        },
        saveRecBill(billid){
            this.showTab="purconfirmLst";
            this.filters.billId = billid;
            this.getRecBillList(1);
       },
        recBillView(billId) {
            this.$router.push({ path: 'recBillView', query: { billId: billId } });
        },
        resetForm(){
            this.filters.billId = "";
        },
         statusFormat(row) {
            return PURCONFIRM_STATUS[row.status].text;
        },
    },
    mounted() {
        this.filters.purchaseCompanyId = this.user.corpId;
        this.getRecBillList(1);
    },
    components:{recevieOrder}

}
</script>
<style scoped>
.el-table {
    font-size: 12px;
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

