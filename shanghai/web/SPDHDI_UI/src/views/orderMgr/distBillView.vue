<template>
<div style="height: 100%">
    <el-row :gutter="10" v-loading="pageLoading" style="height:100%">
        <el-col :span="24" id="distBillPrintView">
            <el-col :span="18" class="toolbar" style="padding-bottom: 0px;">
                <el-form label-width="120px" class="order_view">
                    <el-col :span="8">
                        <el-form-item label="配送单号">
                            <span>{{ distrBill.billId }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="请购机构">
                            <span>{{ distrBill.purchaseCompanyName }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="请购科室">
                            <span>{{ distrBill.applyOrgName }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="收货科室">
                            <span>{{ distrBill.recieveOrgName }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="配送机构">
                            <span>{{ distrBill.provName }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="制单时间">
                            <span>{{ distrBill.fillDate | dateFormat('YYYY-MM-DD') }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="制单人">
                            <span>{{ distrBill.fillterName }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="10">
                        <el-form-item label="备注">
                            <span>{{ distrBill.remark }}</span>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-col>
            <el-col :span="6">
                <svg id="barcode"></svg>
                <div id="erbarcode"></div>
            </el-col>
        </el-col>
        <!--列表-->
        <el-table :data="distrBill.distrBillList" style="width: 100%;" fit class="table_content" border height="outer">
                    <el-table-column label="操作" width="60">
                        <template slot-scope="scope">
                            <div class="icoBtn">
                                <i class="fa fa-edit fa-lg" @click="showOthers(scope.row)" title="查看其他信息"></i>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="rowNumber" label="序号" width="60">
                    </el-table-column>
                    <el-table-column prop="provGoodsErpName" label="商品名称" width="200">
                    </el-table-column>
                    <el-table-column prop="goodsGg" label="规格" width="180" show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column prop="made" label="产地">
                    </el-table-column>
                    <el-table-column prop="hitCode" label="阳光编码">
                    </el-table-column>
                    <el-table-column prop="sendScale" label="转换比">
                    </el-table-column>
                    <el-table-column v-if="distrBill.provId == distrBill.subProvId" prop="sendPrice" label="配送单价">
                    </el-table-column>
                    <el-table-column v-if="distrBill.provId != distrBill.subProvId" prop="subSendPrice" label="配送单价">
                    </el-table-column>
                    <!-- <el-table-column prop="subSendQty" label="配送数量"> -->
                    <el-table-column prop="sendQty" label="配送数量">
                    </el-table-column>
                    <el-table-column prop="sendUnit" label="配送单位">
                    </el-table-column>
                    <el-table-column prop="hosQty" label="医院数量" width="90">
                    </el-table-column>
                    <el-table-column prop="hosUnit" label="医院单位">
                    </el-table-column>
                </el-table>
        <el-col :span="24" class="toolbar" style="margin-top:10px; padding-bottom: 0px;">
            <div style="text-align: center;">
                <el-button :plain="true" type="danger"  @click="print()">打印</el-button>
                <el-button :plain="true" type="danger"  @click="cancel">返回</el-button>
            </div>

            <el-dialog title="其他信息" :visible.sync="otherV" :close-on-click-modal="false"  @close="distrDetailClose">
                <el-form label-width="120px" class="el-form-item-nomsg order_view">
                    <el-col :span="12">
                        <el-form-item label="销售单号">
                            <span>{{ distrDetail.saleBillId }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="批次号">
                            <span>{{ distrDetail.batchNum }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="启用唯一码管理">
                            <span>{{ distrDetail.isUniqueCode }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="批号">
                            <span>{{ distrDetail.batchCode }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="有效日期">
                            <span>{{ distrDetail.expdtEndDate | dateFormat('YYYY-MM-DD')}}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="生产日期">
                            <span>{{ distrDetail.productDate | dateFormat('YYYY-MM-DD')}}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="灭菌批号">
                            <span>{{ distrDetail.sterilizationCode }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="出库库区">
                            <span>{{ distrDetail.outStorearea }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="出库类型">
                            <span>{{ distrDetail.outKind }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="换货标识">
                            <span>{{ distrDetail.isExchange }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="数据来源">
                            <span>{{ distrDetail.sourceData }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="定量包装">
                            <span>{{ distrDetail.packetCode }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="条码">
                            <span>{{ distrDetail.barcode }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="说明">
                            <span>{{ distrDetail.memo }}</span>
                        </el-form-item>
                    </el-col>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click.native="otherV = false" >取消</el-button>
                    <el-button type="primary" @click.native="otherV = false" >确定</el-button>
                </div>
            </el-dialog>
            <el-dialog title="条码信息" :visible.sync="barcodeV" :close-on-click-modal="false"  @close="distrDetailClose">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-tag type="primary" v-for="item in distrUniqueList" :key="item.id" :title="item.batchCode">
                        <i class="fa fa-barcode"></i>
                        {{item.uniqueCode}}
                    </el-tag>
                </el-col>
                <div slot="footer" class="dialog-footer">
                    <el-button @click.native="barcodeV = false" >关闭</el-button>
                </div>
            </el-dialog>
            <el-dialog title="温湿度信息" :visible.sync="humitureV" :close-on-click-modal="false"  @close="distrDetailClose">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <IEcharts :option="setEcharts(distrHumitureList,distrDetail.provGoodsErpName)" :loading="echartLoading"></IEcharts>
                </el-col>
                <div slot="footer" class="dialog-footer">
                    <el-button @click.native="humitureV = false" >关闭</el-button>
                </div>
            </el-dialog>
        </el-col>
    </el-row>
   <distBillViewPrint4SC ref="printD"></distBillViewPrint4SC>
</div>
</template>

<script>
import distBillViewPrint from './distBillViewPrint';
import distBillViewPrint4SC from './distBillViewPrint4SC';  //四川公司配送单打印组件
import moment from 'moment';
// import IEcharts from 'vue-echarts-v3/src/lite.vue';
// import 'echarts/lib/chart/line';
// import 'echarts/lib/component/title';
import IEcharts from 'vue-echarts-v3';
//import $ from 'jquery';
import '../../common/third/printThis/printThis';
import '../../common/third/qrcode/jquery.qrcode.min';
//import IEcharts from 'echarts/lib/chart/line';
import JsBarcode from 'jsbarcode';

export default {
    data() {
        return {
            chartColumn: null,
            echartLoading: false,
            pageLoading: false,
            distrBill: {
                id: '',
                billId: '',
                spdCode: '',
                purchaseCompanyId: '',
                purchaseCompanyCode: '',
                purchaseCompanyName: '',
                purchaseCompanyErpCode: '',
                applyOrgId: '',
                applyOrgCode: '',
                applyOrgName: '',
                applyOrgScmCode: '',
                applyOrgErpCode: '',
                recieveOrgId: '',
                recieveOrgCode: '',
                recieveOrgName: '',
                recieveOrgScmCode: '',
                recieveOrgErpCode: '',
                provId: '',
                provCode: '',
                provName: '',
                provErpCode: '',
                fillter: '',
                remark: '',
                sumRow: '',
                status: '',
                fillDate: '',
                version: '',
                fillterName: '',
                distrBillList: []
            },
            distrDetail: {},
            otherV: false,
            barcodeV: false,
            humitureV: false,
            distrUniqueList: [],
            distrHumitureList: [],
            loading: false
        }
    },
    components: {
        distBillViewPrint,IEcharts,distBillViewPrint4SC
    },
    methods: {
        cancel() {
            this.$emit("close");
        },
        showBarcode(row) {
            this.barcodeV = true;
            this.distrDetail = Object.assign({}, row);
            this.distrUniqueList = Object.assign([], this.distrDetail.distrBillUniqueList);
            this.distrUnique = {
                id: '',
                pid: '',
                distrRowNumber: row.rowNumber,
                provGoodsId: row.provGoodsId,
                goodsName: row.provGoodsErpName,
                batchCode: '',
                uniqueCode: '',
                sourceData: 0,
                version: 0
            }
        },
        showHumiture(row) {
            this.humitureV = true;
            this.distrDetail = Object.assign({}, row);
            this.distrHumitureList = Object.assign([], this.distrDetail.distrBillHumitureList);
        },
        setEcharts(humitureList, title) {
            var opt = null;
            if (!humitureList || humitureList.length == 0) {
                humitureList = [];
            }
            var tDate = humitureList.map(function (item) {
                return item.temprature;
            });
            var hDate = humitureList.map(function (item) {
                return item.humidity;
            });
            var timeDate = humitureList.map(function (item) {
                return moment(item.measureDate).format("MM-DD");
            });
            opt = {
                title: {
                    text: '温湿度信息',
                    subtext: title,
                    x: 'center'
                },
                toolbox: {
                    show: true,
                    feature: {
                        dataView: { readOnly: false },
                        saveAsImage: {}
                    }
                },
                tooltip: {
                    trigger: 'axis',
                    formatter: '{b0}: {c0}°C / {c1}%'
                },
                legend: {
                    data: ['温度', '湿度'],
                    x: 'left'
                },
                xAxis: [
                    {
                        type: 'category',
                        data: timeDate
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value} °C'
                        }
                    },
                    {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value} %'
                        }
                    }
                ],
                series: [
                    {
                        name: '温度',
                        type: 'line',
                        data: tDate
                    },
                    {
                        name: '湿度',
                        type: 'line',
                        yAxisIndex: 1,
                        data: hDate
                    }
                ]
            }
            return opt;
        },
        distrDetailClose() {
            this.distrDetail = {};
            this.distrUnique = {};
            this.distrUniqueList = [];
        },
        showOthers(row) {
            this.otherV = true;
            this.distrDetail = row;//Object.assign({}, row);
        },
        getDistrBill(billId) {
            this.pageLoading = true;
            this.axios.get('/spdHDIService/orderMgr/distrBill/distrBillByBillId/' + billId).then(res => {
                this.distrBill = res.data.data;
                this.pageLoading = false;
            }, err => { this.pageLoading = false; });
        },
        print() {
             this.$refs.printD.print(this.distrBill);
           /* $("#distBillPrintView").printThis({
                debug: false,
                importCSS: true,
                importStyle: true,
                printContainer: true,
                loadCSS: './static/style/print.css',
                pageTitle: "配送单",
                removeInline: false,
                printDelay: 333,
                header: null,
                formValues: true
            });*/
        },
        showBill(billId){
            JsBarcode("#barcode", billId,{height:50,displayValue:false});
            this.getDistrBill(billId);
        }
    },
    mounted() {
    }
};

</script>

<style scoped>
.vue-echarts {
    width: 100%;
    height: 400px;
}

.el-table {
    font-size: 12px;
}
.table_content{
   height: calc(100% - 200px);
}
.el-dialog--small {
    width: 60%;
}

.el-card__body {
    padding: 0px;
}

.orderInput .el-form-item input {
    /*BORDER-TOP-STYLE: none;
    BORDER-RIGHT-STYLE: none;
    BORDER-LEFT-STYLE: none;
    BORDER-BOTTOM-STYLE: solid;*/
    width: 200px;
}

.provListInfo {
    width: 97%;
    height: 300px;
    max-height: 400px;
    padding: 3px;
    background-color: #eef1f6;
    border: 1px solid #bfcbd9;
}

.barlst {
    list-style: none;
    margin: 0px;
    padding: 0;
}

.barlst li {
    cursor: pointer;
    float: left;
}

.icoBtn [class^=fa] {
    vertical-align: baseline;
    cursor: pointer;
    margin-right: 5px;
}
</style>
