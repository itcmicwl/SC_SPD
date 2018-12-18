<template>
    <!--<el-col :span="24">-->
    <el-row :gutter="10">
        <el-col :span="24" id="distBillPrintView">
            <el-col :span="18" class="toolbar" style="padding-bottom: 0px;">
                <el-form label-width="120px" v-loading="loading">
                    <el-col :span="8">
                        <el-form-item label="单据号">
                            <span>{{ recBill.billId }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="配送机构">
                            <span>{{ recBill.provName }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="请购科室">
                            <span>{{ recBill.applyOrgName }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="收货科室">
                            <span>{{ recBill.recieveOrgName }}</span>
                        </el-form-item>
                    </el-col>

                    <el-col :span="8">
                        <el-form-item label="制单时间">
                            <span>{{ recBill.fillDate | dateFormat('YYYY-MM-DD')}}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="制单人">
                            <span>{{ recBill.fillterName }}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="备注">
                            <span>{{ recBill.memo }}</span>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-col>
            <el-col :span="6">
                <svg id="barcode"></svg>
                <div id="erbarcode"></div>
            </el-col>
            <!--列表-->
            <template>
                <el-table :data="recBill.lstDetail" style="width: 100%;" fit height="200">
                    <el-table-column label="操作" width="130" class="noPrint">
                        <template slot-scope="scope">
                            <div class="icoBtn">
                                <i class="fa fa-edit fa-lg" @click="showOthers(scope.row)" title="编辑其他信息"></i>
                                <i class="fa fa-barcode fa-lg" @click="showBarcode(scope.row)" title="条码信息"></i>
                                <i class="fa fa-thermometer fa-lg" @click="showHumiture(scope.row)" title="温湿度信息"></i>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="序号" prop="rowNumber" width="60">
                    </el-table-column>
                    <el-table-column label="产品名称" prop="hosGoodsName" width="180" show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column label="单价" prop="price">
                    </el-table-column>
                    <el-table-column label="待收数量" prop="unrecieveQty" width="90">
                    </el-table-column>
                    <el-table-column label="收货数量" width="90" prop="qty">
                    </el-table-column>
                    <el-table-column label="灭菌批号" prop="sterilizationCode">
                    </el-table-column>
                    <el-table-column label="灭菌有效日期" width="130">
                        <template slot-scope="scope">
                            {{scope.row.sterilizationEndDate | dateFormat('YYYY-MM-DD')}}
                        </template>
                    </el-table-column>
                    <el-table-column label="灭菌日期" width="130">
                        <template slot-scope="scope">
                            {{scope.row.sterilizationDate | dateFormat('YYYY-MM-DD')}}
                        </template>
                    </el-table-column>
                    <el-table-column label="温度" prop="receiveTemprature">
                    </el-table-column>
                    <el-table-column label="湿度" prop="receiveHumidity">
                    </el-table-column>
                </el-table>
            </template>
        </el-col>
        <el-col :span="24" class="toolbar" style="margin-top:10px; padding-bottom: 0px;">
            <div style="text-align: center;">
                <el-button :plain="true" type="danger"  @click="print">打印</el-button>
                <el-button :plain="true" type="danger"  @click="goBack">返回</el-button>
            </div>
        </el-col>
        <el-dialog title="其他信息" :visible.sync="otherV" :close-on-click-modal="false"  @close="distrDetailClose">
            <el-form label-width="120px" class="el-form-item-nomsg order_view">
                <el-col :span="12">
                    <el-form-item label="销售单号">
                        <span>{{ recBillDetail.saleBillId }}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="批次号">
                        <span>{{ recBillDetail.batchNum }}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="启用唯一码管理">
                        <span>{{ recBillDetail.isUniqueCode }}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="批号">
                        <span>{{ recBillDetail.batchCode }}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="有效日期">
                        <span>{{ recBillDetail.expdtEndDate | dateFormat('YYYY-MM-DD')}}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="生产日期">
                        <span>{{ recBillDetail.productDate | dateFormat('YYYY-MM-DD')}}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="灭菌批号">
                        <span>{{ recBillDetail.sterilizationCode }}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="出库库区">
                        <span>{{ recBillDetail.outStorearea }}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="出库类型">
                        <span>{{ recBillDetail.outKind }}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="换货标识">
                        <span>{{ recBillDetail.isExchange }}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="说明">
                        <span>{{ recBillDetail.memo }}</span>
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
                <el-tag type="primary" v-for="item in recUniqueList" :key="item.id" :title="item.batchCode">
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
                <IEcharts :option="setEcharts(recHumitureList,recBillDetail.hosGoodsName)" :loading="echartLoading"></IEcharts>
            </el-col>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="humitureV = false" >关闭</el-button>
            </div>
        </el-dialog>
    </el-row>
    <!--</el-col>-->
</template>
<script>
import moment from 'moment';
import IEcharts from 'vue-echarts-v3';
import { PURCHASE_STATUS } from './common/constance';
import $ from 'jquery';
import '../../common/third/printThis/printThis';
import '../../common/third/qrcode/jquery.qrcode.min';
import JsBarcode from 'jsbarcode';
export default {
    data() {
        return {
            diaLoging: false,
            filters: {
                billId: '',
                purchaseCompanyId: ''
            },
            echartLoading: false,
            otherV: false,
            barcodeV: false,
            humitureV: false,
            total: 0,
            page: 1,
            pageSize: 30,
            loading: false,
            distrBillList: [
            ],
            recBill: {
                id: '',
                billId: '',
                purchaseCompanyId: '',
                purchaseCompanyName: '',
                applyOrgId: '',
                applyOrgName: '',
                applyOrgCode: '',
                recieveOrgId: '',
                recieveOrgName: '',
                recieveOrgCode: '',
                provId: '',
                provName: '',
                revieverId: '',
                revieverName: '',
                fillter: '',
                fillterName: '',
                memo: '',
                status: 10,
                sumRow: 0,
                version: 0,
                fillDate: '',
                lstDetail: []
            },
            recBillDetail: {},
            sltRows: [],
            confirmVisible: false,
            recUnique: {
                id: '',
                pid: '',
                rowNumber: '',
                hosGoodsId: '',
                provGoodsId: '',
                goodsName: '',
                batchCode: '',
                uniqueCode: '',
                isRecieve: '',
                //sourceData: '',
                version: '',
            },
            recHumiture: {
                id: '',
                pid: '',
                distrRowNumber: 0,
                boxCode: '',
                provGoodsId: '',
                goodsName: '',
                batchCode: '',
                uniqueCode: '',
                temprature: '',
                humidity: '',
                measureDate: '',
                receiveTemprature: '',
                receiveHumidity: '',
                receiveMeasureDate: '',
                sourceData: 0,
                version: 0
            },
            recUniqueList: [],
            recHumitureList: []
        }
    },
    components: {
        IEcharts
    },
    methods: {
        goBack() {
            this.$router.go(-1);
        },
        showOthers(row) {
            this.otherV = true;
            this.recBillDetail = row;//Object.assign({}, row);
        },
        distrDetailClose() {
            this.recBillDetail = {};
            this.recUnique = {};
            this.recHumiture = {};
            this.recUniqueList = [];
            this.recHumitureList = [];
        },
        showBarcode(row) {
            this.barcodeV = true;
            this.recBillDetail = Object.assign({}, row);
            this.recUniqueList = Object.assign([], this.recBillDetail.lstUniqueList);
            this.recUnique = {
                id: '',
                pid: '',
                rowNumber: row.rowNumber,
                provGoodsId: row.provGoodsId,
                goodsName: row.provGoodsErpName,
                batchCode: '',
                uniqueCode: '',
                //sourceData: 0,
                version: 0
            }
        },
        showHumiture(row) {
            this.humitureV = true;
            this.recBillDetail = Object.assign({}, row);
            this.recHumitureList = Object.assign([], this.recBillDetail.lstHumitureList);
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
        getReceBill(billId) {
            this.loading = true;
            var _this = this;
            this.axios.get('/spdHDIService/orderMgr/receiveBill/getRecBillById/' + billId).then(res => {
                _this.loading = false;
                if (res.data.code == "0") {
                    _this.recBill = res.data.data;
                }
            }, err => {
                _this.loading = false;
            });
        },
        print() {
            $("#distBillPrintView").printThis({
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
            });
        }
    },
    mounted() {
        var billId = this.$route.query.billId;
        if (billId) {
            JsBarcode("#barcode", billId, { height: 50, displayValue: false });
            this.getReceBill(billId);
            // this.$nextTick(function () {
            //     $("#erbarcode").qrcode({ width: 100, height: 100, text: billId });
            // });
        }
    }
};

</script>

<style scoped>
.fa {
    margin-right: 5px;
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

.el-dialog--small {
    width: 70%;
}
</style>
