<template>
    <el-row :gutter="10" style="height:100%">
        <el-col :span="24"  style="height:100%">
            <el-col :span="24" id="printContent" style="height:100%">
                <el-col :span="18" class="toolbar" style="padding-bottom: 0px;">
                    <el-form label-width="120px" class="order_view">
                        <el-col :span="8">
                            <el-form-item label="请购机构">
                                <span>{{ purOrder.purchaseComName }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="请购科室">
                                <span>{{ purOrder.purOrgCname }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="收货科室">
                                <span>{{ purOrder.recOrgName }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="供应商">
                                <span>{{ purOrder.provName }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="制单时间">
                                <span>{{ purOrder.fillDate | dateFormat('YYYY-MM-DD')}}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="制单人">
                                <span>{{ purOrder.fillterName }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-form-item label="备注">
                                <span>{{ purOrder.remark }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24" class="noPrint">
                            <orderStatus :status="purOrder.status | fStatus"></orderStatus>
                        </el-col>
                    </el-form>
                </el-col>
                <el-col :span="6">
                        <svg id="barcode"></svg>
                        <div id="erbarcode"></div>
                </el-col>
                <!--列表-->
                    <el-table :data="purOrder.purchaseList" style="width: 100%;" fit class="table_content">
                        <el-table-column prop="rowNumber" label="序号" width="60">
                        </el-table-column>
                        <el-table-column prop="applyDeptName" label="请购科室" width="150">
                        </el-table-column>
                        <el-table-column prop="hosGoodsName" label="产品名称" width="180" show-overflow-tooltip>
                        </el-table-column>
                         <el-table-column prop="goodsGg" label="规格" width="180" show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column prop="mfrsName" label="厂家" width="180" show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column prop="hosPrice" label="请购单价">
                        </el-table-column>
                        <el-table-column prop="hosApplyQty" label="请购数量" width="90">
                        </el-table-column>
                        <el-table-column prop="hosPurQty" label="采购数量" width="90">
                        </el-table-column>
                        <el-table-column prop="unitQty" label="确认数量" width="90">
                        </el-table-column>
                        <el-table-column prop="hosUnit" label="单位">
                        </el-table-column>
                        <!-- <el-table-column prop="purAmount" label="含税金额(元)" width="120">
                        </el-table-column>
                        <el-table-column prop="purTaxAmount" label="不含税金额(元)" width="120">
                        </el-table-column> -->
                        <el-table-column prop="applyDate" label="科室请购日期" width="120">
                            <template slot-scope="scope">
                                {{scope.row.applyDate | dateFormat('YYYY-MM-DD')}}
                            </template>
                        </el-table-column>
                        <!-- <el-table-column prop="receiveDate" label="预计到货日期" width="120">
                            <template slot-scope="scope">
                                {{scope.row.receiveDate | dateFormat('YYYY-MM-DD')}}
                            </template>
                        </el-table-column> -->
                        <el-table-column label="状态" width="60">
                            <template slot-scope="scope">
                                <el-tag type="danger" v-if="scope.row.status==1">已确认</el-tag>
                                <el-tag type="success" v-if="scope.row.status==0">未确认</el-tag>
                            </template>
                        </el-table-column>
                    </el-table>

                    <el-col :span="24" class="toolbar">
                    <div style="text-align: center;">
                        <el-button v-show="hideClose" :plain="true" type="danger"  @click="close" icon="close">关闭</el-button>
                        <el-button :plain="true" type="danger"  @click="print" icon="print">打印</el-button>
                        <el-button :plain="true" type="danger"  @click="goBack">返回</el-button>
                    </div>
                </el-col>
            </el-col>
        </el-col>
        <el-dialog title="关闭原因"  :visible.sync="confirmVisible">
            <el-input v-model="purOrder.closingReason"></el-input>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="confirmVisible = false" >取消</el-button>
                <el-button type="primary" @click.native="submitPurconfirm"  >确认</el-button>
            </div>
        </el-dialog>
    </el-row>
</template>
<script>
import orderStatus from './orderStatus';
import { PURCHASE_STATUS } from './common/constance';
import $ from 'jquery';
import '../../common/third/printThis/printThis';
import '../../common/third/qrcode/jquery.qrcode.min';
//import '../../common/third/qrcode/qrcode';
//import '../../common/third/qrcode/jquery.qrcode';
import JsBarcode from 'jsbarcode';

export default {
    data() {
        return {
            purOrder: {
                id: '',
                billId: '',
                purchaseComId: '',
                purchaseComName: '',
                recOrgId: '',
                recOrgName: '',
                provId: '',
                provName: '',
                distrRelation: '',
                fillter: '',
                fillterName: '',
                remark: '',
                status: '',
                autitorId: '',
                autitorName: '',
                autitorSumRow: '',
                sumRow: '',
                fillDate: '',
                lastUpdateDt: '',
                version: '',
                purchaseList: [],
                closingReason:''
            },
            hideClose:true,
            confirmVisible:false
        }
    },
    components: { orderStatus },
    filters: {
        fStatus: function (value) {
            if (value) {
                return PURCHASE_STATUS[value].index;
            } else {
                return 0;
            }
        },
    },
    methods: {
        close(){
            this.confirmVisible = true;
        },
        submitPurconfirm(){
            this.purOrder.status = '70';
            this.axios.post('/spdHDIService/order/purchase/updatePurchase/',this.purOrder).then(res => {
               this.confirmVisible = false;
               if (res.data.code == "0") {
                    this.purOrder = res.data.data;
                    if(parseInt(res.data.data.status)<=30){
                        this.hideClose = true;
                    }else{
                        this.hideClose = false;
                    }
                }
            }, err => {
            });
        },
        goBack() {
            this.$router.go(-1);
        },
        getPurchaseInfo(billId) {
            this.axios.get('/spdHDIService/order/purchase/getPurchase/' + billId).then(res => {
                if (res.data.code == "0") {
                    this.purOrder = res.data.data;
                    if(parseInt(res.data.data.status)<=30){
                        this.hideClose = true;
                    }else{
                        this.hideClose = false;
                    }
                }
            }, err => {
            });
        },
        print() {
            $("#printContent").printThis({
                debug: false,
                importCSS: true,
                importStyle: true,
                printContainer: true,
                loadCSS: './static/style/print.css',
                pageTitle: "采购单",
                removeInline: false,
                printDelay: 333,
                header: null,
                formValues: true
            });
        }
    },
    mounted() {
        var billId = this.$route.query.billId;
        if (billId) {
            JsBarcode("#barcode", billId,{height:50,displayValue:false});
            this.getPurchaseInfo(billId);
            // this.$nextTick(function () {
            //     $("#erbarcode") height: 100, text: billId });
            // });
        }
    }
};

</script>

<style scoped>
.table_content{
    font-size: 12px;
   height: calc(100% - 270px);
   overflow-y: auto;
}
</style>
