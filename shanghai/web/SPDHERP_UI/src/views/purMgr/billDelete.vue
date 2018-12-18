<template>
    <div id="modify_price" style="height:100%;">
            <el-col :span="24"  style="max-height:15%;">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form :inline="true" label-width="80px">
                        <el-form-item label="配送单号">
                            <el-input v-model="filters.billId" placeholder="配送单号" ></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" icon="search" v-on:click="getDistrBillVos(1)" >查询</el-button>
                        </el-form-item>
                    </el-form>
                </el-col>
                 <!--列表-->
                <el-table :data="distrBillVos" highlight-current-row v-loading="loading" style="width: 100%;" fit class="table_content">
                    <el-table-column prop="billId" label="配送单号"  width="150" sortable>
                    </el-table-column>

                    <el-table-column prop="provName" label="供应商名称" sortable width="200">
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
                            <el-button  v-if="scope.row.subProvId != scope.row.provId"   @click="catDistBillPrice(scope.row.billId)" type="text">查看</el-button>
                            <el-button  v-if="scope.row.subProvId == scope.row.provId"    type="text">直销类不允许删除单据</el-button>

                        </template>
                    </el-table-column>
                </el-table>
            </el-col>


    <el-row v-show="vDistrBillPrice" style="height:85%;">

    <el-row :gutter="10" v-loading="pageLoading" style="height:100%" id="billConv">
         <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form label-width="120px" class="order_view">
                <el-col :span="8">
                    <el-form-item label="配送单号">
                        <span>{{ distrBillDetail.billId }}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="制单时间">
                        <span>{{ distrBillDetail.fillDate | dateFormat('YYYY-MM-DD') }}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="制单人">
                        <span>{{ distrBillDetail.fillterName }}</span>
                    </el-form-item>
                </el-col>    
            </el-form>
        </el-col>
        <!--列表-->
        <el-table :data="distrBillDetail.distrBillList"  @selection-change="handleSelectionChange"
                style="width: 100%;" height="75%" fit class="table_content" border>
            <el-table-column label="操作" width="60">
                <template slot-scope="scope">
                    <div class="icoBtn">
                        <i class="el-icon-delete" @click="delRow(scope.row)" title="删除"></i>
                    </div>
                </template>
            </el-table-column>

            <!-- <el-table-column type="selection"width="55">
            </el-table-column>-->
            <el-table-column prop="rowNumber" label="序号" width="60">
            </el-table-column>
            <el-table-column prop="provGoodsErpName" label="商品名称" width="200" show-overflow-tooltip>
            </el-table-column>
            <el-table-column prop="goodsGg" label="规格" width="180" show-overflow-tooltip>
            </el-table-column>
              <el-table-column prop="batchCode" label="批号" width="180" show-overflow-tooltip>
            </el-table-column>
            <el-table-column prop="made" label="产地">
            </el-table-column>
            <el-table-column  label="采购单价" prop="subSendPrice">
            </el-table-column>
            <el-table-column  label="发货单价" prop="sendPrice">
            </el-table-column>
            <el-table-column prop="sendQty" label="配送数量">
            </el-table-column>
            <el-table-column prop="sendUnit" label="配送计量单位">
            </el-table-column>
   
            </el-table-column>
        </el-table>
        <el-col :span="24" class="toolbar" style="margin-top:10px; padding-bottom: 0px;">
            <div style="text-align: center;">
            <!--
                <el-button :plain="true" type="danger"  @click="submitDistr()" style="width:60px;">提交</el-button>
               -->
                <el-button :plain="true" type="danger"  @click="cancel" style="width:60px;">返回</el-button>
            </div>

        </el-col>
    </el-row>

                
      
    </el-row>
   </div>
</template>
<script>
import { PURCHASE_STATUS,PURCHASE_KIND } from './common/constance';
import { mapGetters } from 'vuex';
import editDistrPrice from './editDistrPrice';
export default {
    data() {
        return {
            filters: {
                billId: ''
            },
            total: 0,
            page: 1,
            pageSize: 30,
            loading: false,
            vDistrBillPrice:false,

            distrBillVos: [],
            pageLoading:false,
            distrBillDetail:'',
            multipleSelection: []

        }
    },
      components: { editDistrPrice},

    methods: {

        //依据输入单号查询配送单
        getDistrBillVos: function (pIndex) {

           var param = {
                orderBy: "",
                page: this.page,
                rows: this.pageSize,
                queryObject: {
                    billId: this.filters.billId
                }
           };
            this.loading = true;
            this.axios.post('/spdHERPService/orderMgr/distrBill/listByPage4Simple/', param).then(res => {
                this.loading = false;
                this.distrBillVos = res.data.data.data;
                this.total = res.data.data.total;
            }, err => {
                this.loading = false;
            });
        //   this.axios.get('/spdHERPService/orderMgr4Del/billMgr/getStockBillIds/PShs333201805300002').then(res => {
        //         this.loading = false;
        //         this.distrBillVos = res.data.data.data;
        //         this.total = res.data.data.total;
        //     }, err => {
        //         this.loading = false;
        //     });
        },
         catDistBillPrice(billId){
            
           this.pageLoading = true;
            this.axios.get('/spdHERPService/orderMgr/distrBill/distrBillByBillId/'+billId ).then(res => {
                this.distrBillDetail = res.data.data;
                  this.vDistrBillPrice=true;
                this.pageLoading = false;
            }, err => { this.pageLoading = false; });
        },

   
        cancel() {
         //   this.vDistrBillLst = true;
            this.vDistrBillPrice=false;
          //  this.vDistBillView=false;
        },
         handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        delRow(row){
            this.$confirm('删除后不可恢复，确认删除吗?', '提示', {}).then(() => {

                    this.pageLoading = true;
                    this.axios.post('/spdHERPService/orderMgr4Del/billMgr/delDistrBillRow/',row).then(res => {
                        if(res.data.data===-1) {
                                this.$message.error("该行存在分单,且总数量暂未匹配完成，不能执行删除！");
                                this.pageLoading = false;
                        }
                        else if(res.data.data===-2){
                                this.$message.error("该行已提交结算，不能执行删除！");
                                this.pageLoading = false;
                        }
                        else {
                                    this.$message.success("该行删除成功，3秒后外网数据会同步删除！");
                                    this.pageLoading = false;
                                    this.catDistBillPrice(this.filters.billId);
                        }
                            
                        
                    }, err => { this.pageLoading = false; });
                 }).catch(() => {

            });

        },
        submitDistr(){    //批量删除

           alert(this.multipleSelection.length)
        
           this.pageLoading = true;
            this.axios.post('/spdHERPService/orderMgr/distrBill/distrBillByBillId/' + billId).then(res => {
                this.distrBillDetail = res.data.data;
                this.pageLoading = false;
            }, err => { this.pageLoading = false; });
           
        }
    },
    mounted() {

    }
};

</script>

<style>
#un-rec-pur-bill-list .el-table {
    font-size: 12px;
}
</style>
