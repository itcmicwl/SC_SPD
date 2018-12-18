<template>
    <div id="modify_price" style="height:100%;">
            <el-col :span="24"  style="max-height:25%;">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form :inline="true" label-width="80px">
                        <el-form-item label="配送单号">
                            <el-input v-model="filters.billId" placeholder="配送单号" ></el-input>
                        </el-form-item>
                        <el-form-item label="出库单号">
                            <el-input v-model="filters.outStockBillId" placeholder="出库单号" ></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" icon="search" v-on:click="getDistrBillVos(1)" >查询</el-button>
                        </el-form-item>
                    </el-form>
                </el-col>
                 <!--列表-->
                <el-table :data="distrBillVos" highlight-current-row v-loading="loading" style="width: 100%; " fit class="table_content">
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
                            <el-button  v-if="scope.row.subProvId != scope.row.provId"   @click="editDistBillPrice(scope.row.billId)" type="text">编辑价格</el-button>
                            <el-button  v-if="scope.row.subProvId == scope.row.provId"    type="text">直销类不允许改价</el-button>

                        </template>
                    </el-table-column>
                </el-table>
            </el-col>


    <el-row v-show="vDistrBillPrice" style="height:75%;">
                <editDistrPrice ref="billConverter" v-on:cancel="p_addCancel" v-on:submitOrder="p_saveBill"></editDistrPrice>
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
                billId: '',
                outStockBillId: ''
            },
            total: 0,
            page: 1,
            pageSize: 30,
            loading: false,
            vDistrBillPrice:false,

            distrBillVos: []
        }
    },
      components: { editDistrPrice},

    methods: {

        //依据输入单号查询配送单
        getDistrBillVos: function (pIndex) {

            if(!this.filters.billId&&!this.filters.outStockBillId){
              this.$message.error("必须填写单号才能查询！");
                return;
            }
           var param = {
                orderBy: "",
                page: this.page,
                rows: this.pageSize,
                queryObject: {
                    billId: this.filters.billId,
                    outStockBillId: this.filters.outStockBillId
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
        },
         editDistBillPrice(billId){
            this.vDistrBillPrice=true;
           // this.vDistBillView=false;
            this.$refs.billConverter.setBillId(billId);
        },

        p_saveBill(billId) {
           // this.vDistrBillLst = true;
            this.vDistrBillPrice=false;
         //   this.vDistBillView=false;
       //     this.getDistrBillList(1);
        },
        p_addCancel() {
         //   this.vDistrBillLst = true;
            this.vDistrBillPrice=false;
          //  this.vDistBillView=false;
        },

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
