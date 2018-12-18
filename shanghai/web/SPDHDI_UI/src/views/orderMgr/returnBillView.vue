<template>
    <el-row :gutter="10">
        <section>
            <el-col :span="24">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form label-width="120px">
                        <el-col :span="8">
                            <el-form-item label="请退机构：">
                                <span>{{ returnBill.returnCompanyName }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="请退科室：">
                                <span>{{ returnBill.returnOrgName }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="出库科室：">
                                <span>{{ returnBill.outOrgName }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="供应商：">
                                <span>{{ returnBill.provName }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="制单时间:">
                                <span>{{ returnBill.fillDate | dateFormat('YYYY-MM-DD')}}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="制单人：">
                                <span>{{ returnBill.fillterName }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-form-item label="备注：">
                                <span>{{ returnBill.memo }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-steps :space="100" :active="test" finish-status="success" center>
                                <el-step title="已保存"></el-step>
                                <el-step title="已提交"></el-step>
                            </el-steps>
                        </el-col>
                    </el-form>
                </el-col>
                <!--列表-->
                <template>
                    <el-table :data="returnBill.returnBillSubList" style="width: 100%;" fit height="300">
                        <el-table-column prop="rowNumber" label="序号" width="60">
                        </el-table-column>

                        <el-table-column prop="hosGoodsName" label="产品名称" width="180" show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column prop="hosGoodsCode" label="医院产品编号">
                        </el-table-column>
                        <el-table-column prop="batchCode" label="批号">
                        </el-table-column>
                        <el-table-column prop="batchNum" label="批次">
                        </el-table-column>
                        <el-table-column prop="goodsStatus" label="质量状态" width="120">
                            <template slot-scope="scope">
                                <el-select v-model="scope.row.goodsStatus" :disabled=true filterable placeholder="请选择">
                                    <el-option v-for="item in ZLZTDict" :key="parseInt(item.val)" :label="item.ename" :value="parseInt(item.val)">
                                    </el-option>
                                </el-select>
                            </template>
                        </el-table-column>

                        <el-table-column prop="instorePrice" label="入库单价" width="120">
                        </el-table-column>
                        <el-table-column prop="outstorePrice" label="请退单价" width="120">
                        </el-table-column>

                        <el-table-column prop="qty" label="可退数量">
                        </el-table-column>
                        <el-table-column prop="canReturnQty" label="退货数量">
                        </el-table-column>

                        <el-table-column prop="purBillId" label="采购订单号" width="120">
                        </el-table-column>
                        <el-table-column prop="applyBillId" label="科室请购单号" width="120">
                        </el-table-column>
                        <el-table-column prop="returnReason" label="退货原因" width="120">
                        </el-table-column>
                    </el-table>
                </template>
                <el-col :span="24" class="toolbar" style="margin-top:10px; padding-bottom: 0px;">
                    <div style="margin-left:100px;">
                        <el-button :plain="true" type="danger"  @click="goBack">返回</el-button>
                    </div>
                </el-col>
            </el-col>
        </section>
    </el-row>
</template>
<script>
import orderStatus from './orderStatus';
import { PURCHASE_STATUS } from './common/constance';
export default {
    data() {
        return {
            returnBill: {
                id: '', billId: '', fillDate: '', returnCompanyId: '', returnCompanyName: '',
                returnOrgId: '', returnOrgCode: '', returnOrgName: '',
                outOrgId: '', outOrgName: '', outOrgCode: '',
                provId: '', provName: '',
                distrRelation: '', fillter: '', memo: '', isStartInvoice: '', sumRow: '', status: '',
                returnBillSubList: []
            },
            ZLZTDict: {},
            test: 0,
        }
    },
    components: { orderStatus },

    methods: {
        goBack() {
            this.$router.go(-1);
        },
        getReturnBill(billId) {
            this.axios.get('/spdHDIService/orderMgr/returnBill/getReturnBill/' + billId).then(res => {
                if (res.data.code == "0") {
                    this.returnBill = res.data.data;
                    this.test = PURCHASE_STATUS[this.returnBill.status].index;
                }
            }, err => {
            });
        },
        getZLZT: function () {   //质量状态 字典
            this.loading = true;
            this.axios.post('/platformService/sys/dict/getDictValueByDictEname', { dictName: 'KQZLZT' }).then(res => {
            this.loading = false;
                if (res.data.code == "0") {
                    this.ZLZTDict = res.data.data;

                }
                else {
                    this.$message.error(res.data.msg);
                }
            }, err => {
                this.loading = false;
            });
        }
    },
    mounted() {
        this.getZLZT();
        var billId = this.$route.query.billId;

        if (billId) {
            this.getReturnBill(billId);

        }
    }
};

</script>

<style scoped>
.el-table {
    font-size: 12px;
}
</style>
