<template>
    <el-row v-loading="diaLoging">
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :model="settleBill" :rules="settleBillRules" ref="settleBill" label-width="120px">
                <el-col :span="12">
                    <el-form-item label="医院:">
                        <span>{{ settleBill.hosName }}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="供应商:">
                        <span>{{ settleBill.provName }}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="制单时间:">
                        <span>{{ settleBill.fillDate | dateFormat('YYYY-MM-DD')}}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="制单人:">
                        <span>{{ settleBill.fillterName }}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="发票号:" prop="invoiceNumber">
                        <el-input type="text"  :maxlength="128" placeholder="请输入内容" v-model="settleBill.invoiceNumber">
                        </el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="发票编码:" prop="invoiceCode">
                        <el-input type="text"  :maxlength="128" placeholder="请输入内容" v-model="settleBill.invoiceCode">
                        </el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="备注:">
                        <el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="settleBill.remark">
                        </el-input>
                    </el-form-item>
                </el-col>
            </el-form>
        </el-col>
        <el-table ref="receListTable" :data="settleBill.lstDetail" style="width: 100%;" fit height="300" stripe border>
            <!--<el-table-column label="操作" width="50">
                <template slot-scope="scope">
                    <div class="icoBtn">
                        <i class="fa fa-trash-o fa-lg" @click="delRow(scope.row)" title="删除" style="color:red;margin-left:5px;"></i>
                    </div>
                </template>
            </el-table-column>-->
            <el-table-column label="序号" prop="rowNum" width="50">
            </el-table-column>
             <el-table-column label="发票号" width="200">
                <template slot-scope="scope">
                    <el-input  :maxlength="128" v-model="scope.row.invoiceNumber" clearable style="width:100%">
                    </el-input>
                </template>
            </el-table-column>
            <el-table-column label="发票编码" width="200">
                <template slot-scope="scope">
                    <el-input  :maxlength="128" v-model="scope.row.invoiceCode" clearable style="width:100%">
                    </el-input>
                </template>
            </el-table-column>
            <el-table-column label="物资名称" prop="hosGoodsName" width="180" show-overflow-tooltip>
            </el-table-column>
            <el-table-column label="出库单号" prop="outBillid" width="110">
            </el-table-column>
            <el-table-column label="出库部门" prop="outDeptName" width="120">
            </el-table-column>
            <el-table-column label="规格" prop="goodsGg" show-overflow-tooltip width="120">
            </el-table-column>
            <el-table-column label="单位" prop="hosUnit">
            </el-table-column>
            <el-table-column label="灭菌批号" prop="sterilizationCode">
            </el-table-column>
            <!-- <el-table-column label="灭菌有效日期" width="130">
                    <template slot-scope="scope">
                        {{scope.row.sterilizationEndDate | dateFormat('YYYY-MM-DD')}}
                    </template>
                </el-table-column>
                <el-table-column label="灭菌日期" width="130">
                    <template slot-scope="scope">
                        {{scope.row.sterilizationDate | dateFormat('YYYY-MM-DD')}}
                    </template>
                </el-table-column> -->
            <el-table-column label="有效期" width="130">
                <template slot-scope="scope">
                    {{scope.row.expdtEndDate | dateFormat('YYYY-MM-DD')}}
                </template>
            </el-table-column>
            <el-table-column label="生产厂家" prop="mfrsName">
            </el-table-column>
            <el-table-column label="产地" prop="made" width="90">
            </el-table-column>
        </el-table>
        <el-col :span="20" class="toolbar" style="padding-bottom: 0px;text-align: center;">
            <el-button  @click.native="cancel">取消</el-button>
            <el-button type="primary" @click.native="submitPurconfirm(40)" >确认</el-button>
        </el-col>
    </el-row>
</template>
<script>
import moment from 'moment';
import { mapGetters } from 'vuex';
import { appConfig } from '../../config/appConfigs';
import { DISTRBILL_STATUS } from '../../common/js/constance';
import barcodeUtil from '../../common/js/barcodeUtil';
var Child =  {
  template: '<div><p v-for="item in errMsg" v-html="item"></p><p v-if="vGoOn">是否忽略以上物资，继续生成！</p></div>',
  props:["errMsg","vGoOn"]
};
export default {
    data() {
        return {
            settleBillRules: {
                invoiceNumber: [
                    { required: true, message: '请输入发票号', trigger: 'blur' },
                    { min: 1, max: 128, message: '长度在 1 到 128个字符', trigger: 'blur' }
                ],
                invoiceCode: [
                    { required: true, message: '请输入发票编码', trigger: 'blur' },
                    { min: 1, max: 128, message: '长度在 1 到 128 个字符', trigger: 'blur' }
                ]
            },
            opModel: "ADD",
            diaLoging: false,
            loading: false,
            settleBill: {
                id: '', billId: '', spdCode: '', hosId: '', hosName: '', hosErpCode: '', provId: '', provName: '', provErpCode: '',
                provDeptErpCode: '', filler: '', remark: '', sumRow: '', status: '', fillDate: '', lstDetail: [],invoiceNumber:'',invoiceCode:''
            },
            settleBillDetail: {
                id: '', pid: '', billId: '', rowNum: '', provId: '', provName: '', provErpCode: '', outDeptId: '',
                outDeptName: '', outDeptErpCode: '', outBillid: '', outRowNum: '', outDate: '', hosGoodsId: '',
                hosGoodsName: '', goodsGg: '', mfrsId: '', made: '', packetCode: '', provGoodsId: '', provGoodsName: '',
                batchId: '', batchCode: '', sterilizationCode: '', expdtEndDate: '', produceDatetime: '', hosUnit: '',
                hosUnitQty: '', provUnit: '', hosScale: '', provUnitQty: '', status: '', outKind: '', version: '',
                invoiceNumber:'',invoiceCode:'',saleMan:'',sterilizationDate:'',sterilizationEndDate:''
            },
            sltRows: []
        }
    },
    filters: {
        fStatus: function(value) {
            if (value) {
                return DISTRBILL_STATUS[value].text;
            } else {
                return '';
            }
        }
    },
    computed: {
        ...mapGetters(["getSettleBill"])
    },
    methods: {

        getSettleBillByBillId(recId) {
            var _this = this;
            this.axios.get('/spdHDIService/settleCenter/hosOutBalance/getByBillId/' + recId).then(res => {
                if (res.data.code == 0) {
                    _this.settleBill = res.data.data;
                }
            });
        },
        sltRowsChange(sltRows) {
            this.sltRows = sltRows;
        },
        delRow(pd) {
            var index = this.settleBill.lstDetail.findIndex(item => { return item.rowNum == pd.rowNum });
            this.settleBill.lstDetail.splice(index, 1);
            this.settleBill.lstDetail.forEach((item, i) => item.rowNum = i + 1);
        },
        submitPurconfirm(status) {
            if (this.settleBill.lstDetail.length == 0) {
                this.$message.error('没有明细记录！');
                return;
            };
            this.$refs['settleBill'].validate((valid) => {
                if (valid) {
                    this.settleBill.sumRow = this.settleBill.lstDetail.length;
                    this.diaLoging = true;
                    this.settleBill.status = status;
                    var serviceUrl = this.opModel == "ADD" ? '/spdHDIService/settleCenter/hosOutBalance/add' : '/spdHDIService/settleCenter/hosOutBalance/update';
                    var _this = this;
                    this.axios.post(serviceUrl, this.settleBill).then(res => {
                        _this.diaLoging = false;
                        if (res.data.code == "0") {
                            _this.$msgbox({ title: "提示", message: "操作成功", type: "success" });
                            var billId = res.data.data.billId;
                            _this.$msgbox({
                                title: "提示",
                                message: "操作成功",
                                type: "success",
                                callback: action => {
                                    this.$router.push({ path: 'settleBill', query: { billId: billId } });
                                }
                            });
                        } else if((res.data.code == "-122")){
                            var errMsgs = res.data.validateErrors.fieldErrors.map(item=>item.message);
                            const h = _this.$createElement;
                            _this.$msgbox({title:"错误",type:"error",message:h(Child,{props:{errMsg:errMsgs}})});
                        }else{
                            this.$msgbox({ title: "提示", message: "操作失败", type: "error" });
                        }
                    }, err => {
                        this.diaLoging = false;
                        this.$msgbox({ title: "提示", message: "操作失败", type: "error" });
                    });
                } else {
                    return false;
                }
            });

        },
        cancel() {
            this.$router.push({ path: 'settleBill' });
        },
        getOutBillDetail() {

        }
    },
    mounted() {
        var _this = this;
        var settleBillId = this.$route.query.settleBillId;
        if (settleBillId) {
            this.opModel = "EDIT";
            this.getSettleBillByBillId(settleBillId);
        } else {
            this.opModel = "ADD";
            this.settleBill = this.getSettleBill;
        }
    }
};

</script>

<style scoped>
.fa {
    margin-right: 5px;
}

.vue-echarts {
    width: 100%;
    height: 400px;
}

.el-table {
    font-size: 12px;
}

fieldset {
    border-width: 1px;
    border-style: solid;
    border-color: #1fa0ff;
    border-image: initial;
}

.icoBtn [class^=fa] {
    vertical-align: baseline;
    cursor: pointer;
    margin-right: 5px;
}

.el-dialog--small {
    width: 70%;
}
</style>
