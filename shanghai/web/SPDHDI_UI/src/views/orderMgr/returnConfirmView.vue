<template>
    <el-row :gutter="10">
        <el-col :span="24" id="printContent">
            <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                <el-form label-width="120px" v-loading="loading">
                    <el-col :span="8">
                        <el-form-item label="单据号">
                            <span>{{ returnConfirm.billId }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="退货单号">
                            <span>{{ returnConfirm.returnBillId }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="请退机构">
                            <span>{{ returnConfirm.returnCompanyName }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="请退科室">
                            <span>{{ returnConfirm.returnOrgName }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="出库科室">
                            <span>{{ returnConfirm.outOrgName }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="制单人">
                            <span>{{ returnConfirm.fillterName }}</span>
                        </el-form-item>
                    </el-col>


                    <el-col :span="8">
                        <el-form-item label="制单时间">
                            <span>{{ returnConfirm.fillDate | dateFormat('YYYY-MM-DD')}}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="15">
                        <el-form-item label="备注">
                            <span>{{ returnConfirm.remark }}</span>
                        </el-form-item>
                    </el-col>

                </el-form>
            </el-col>
            <!--列表-->
            <template>
                <el-table :data="returnConfirm.lstDetail" style="width: 100%;" fit height="200">
                    <el-table-column prop="rowNumber" label="序号" width="60">
                    </el-table-column>

                    <el-table-column prop="provGoodsErpName" label="产品名称" width="180" show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column prop="goodsGg" label="规格" width="90" show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column prop="made" label="产地" width="90" >
                    </el-table-column>
                    <el-table-column prop="mfrsName" label="产家" width="90" >
                    </el-table-column>
                    <el-table-column prop="returnPrice" label="请退单价">
                    </el-table-column>
                    <el-table-column prop="returnQty" label="请退数量" width="90">
                    </el-table-column>

                </el-table>
            </template>
        </el-col>
        <el-col :span="24" class="toolbar" style="margin-top:10px; padding-bottom: 0px;">
            <div style="text-align: center;">
                <el-button :plain="true" type="danger"  @click="goBack">返回</el-button>
            </div>
        </el-col>
    </el-row>
</template>
<script>
import { PURCONFIRM_STATUS } from './common/constance';
export default {
    name: 'confirmView',
    data() {
        return {
            loading: false,
            returnConfirm: {
                id: '',
                billId: '',
                returnBillId: '',
                spdCode: '',
                returnCompanyId: '',
                returnCompanyCode: '',
                returnCompanyName: '',
                returnCompanyErpCode: '',
                returnOrgId: '',
                returnOrgCode: '',
                returnOrgName: '',
                returnOrgScmCode: '',
                returnOrgErpCode: '',
                outOrgId: '',
                outOrgCode: '',
                outOrgName: '',
                outOrgScmCode: '',
                outOrgErpCode: '',
                provId: '',
                provCode: '',
                provName:'',
                provErpCode: '',
                provOrgCode: '',
                provSystemId: '',
                deliveryRelation: '',
                balanceOrgId: '',
                version:'',
                status: 0,
                fillter: '',
                remark: '',
                sumRow: 0,
                fillDate: '',
                fillterName:'',
                lstDetail: []
            }
        }
    },
    props: ["billid"],
    watch: {
        billid(curVal, oldVal) {
            this.getReconfirmInfo(curVal);
        }
    },
    methods: {
        goBack() {
            this.$router.go(-1);
        },
        getReconfirmInfo(billId) {
            this.loading = true;
            var _this = this;
            this.axios.get('/spdHDIService/orderMgr/returnconfirm/getByBillId/' + billId).then(res => {
                _this.loading = false;
                if (res.data.code == "0") {
                    _this.returnConfirm = res.data.data;
                }
            }, err => {
                _this.loading = false;
            });
        },
        getStep(status){
            return PURCONFIRM_STATUS[status].index;
        }
    },
    mounted() {
        var billId = this.$route.query.billId;
        alert(billId);
        if (billId) {
            //JsBarcode("#barcode", billId);
            this.getReconfirmInfo(billId);
        }
    }
};

</script>

<style scoped>
.el-table {
    font-size: 12px;
}
</style>
