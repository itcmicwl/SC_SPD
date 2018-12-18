<template>
    <section>
        <el-col :span="24">
            <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                <el-form :inline="true" label-width="80px">
                    <el-form-item label="配送单号">
                        <el-input v-model="filters.billId" placeholder="配送单号" ></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" icon="search" v-on:click="getDistrBillList(1)" >查询</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
            <!--列表-->
            <template>
                <el-table :data="distrBillList" highlight-current-row v-loading="loading" style="width: 100%;" fit height="400">

                    <el-table-column prop="billId" label="配送单编号" sortable>
                    </el-table-column>
                    <el-table-column prop="status" label="状态">
                        <template slot-scope="scope">
                            {{scope.row.status | fStatus}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="provName" label="供应商名称" sortable>
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
                            <el-button  @click="showPurcDialog(scope.row.id)" type="text">收货</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!--工具条-->
                <el-col :span="24" class="toolbar">
                    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[30, 100, 200, 400]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
                    </el-pagination>
                </el-col>
                <el-dialog title="收货" :visible.sync="confirmVisible" :close-on-click-modal="false"  v-loading="diaLoging">
                    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                        <el-form label-width="120px">
                            <el-col :span="8">
                                <el-form-item label="请购科室:">
                                    <span>{{ recBill.applyOrgName }}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="收货科室:">
                                    <span>{{ recBill.recieveOrgName }}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="供应商:">
                                    <span>{{ recBill.provName }}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="收货人:">
                                    <span>{{ recBill.revieverName }}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="制单时间:">
                                    <span>{{ recBill.fillDate | dateFormat('YYYY-MM-DD')}}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="制单人:">
                                    <span>{{ recBill.fillterName }}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24">
                                <el-form-item label="备注:">
                                    <span>{{ recBill.memo }}</span>
                                </el-form-item>
                            </el-col>
                        </el-form>
                    </el-col>
                    <el-col :span="2" class="toolbar" style="padding-bottom: 0px;">
                        <el-switch v-model="barcodeType" active-text="仅主码" inactive-text="主副码" :width="80" @change="barcodeTypeChange">
                        </el-switch>
                    </el-col>
                    <el-col :span="20" class="toolbar" v-show="barcodeType" style="padding-bottom: 0px;">
                        <el-input type="text" class="input-barcode" ref="mbarcode" v-model="barcode" @keyup.native.enter="addBybarcode" autosize placeholder="请录入条形码">
                        </el-input>
                    </el-col>
                    <el-col :span="20" class="toolbar" v-show="!barcodeType" style="padding-bottom: 0px;">
                        <el-col :span="10">
                            <el-input type="text" class="input-barcode" ref="mainBarcode" v-model="mainBarcode" @keyup.native.enter="barcodeNext" autosize placeholder="请录入条形码主码">
                            </el-input>
                        </el-col>
                        <el-col class="line" :span="2">-</el-col>
                        <el-col :span="10">
                            <el-input type="text" class="input-barcode" ref="fbarcode" v-model="fBarcode" @keyup.native.enter="barcodePre" autosize placeholder="请录入条形码副码">
                            </el-input>
                        </el-col>
                    </el-col>
                    <el-table ref="receListTable" :data="recBill.lstDetail" style="width: 100%;" fit height="300" stripe border @selection-change="sltRowsChange" row-key="distrRowNumber">
                        <el-table-column type="selection" width="30">
                        </el-table-column>
                        <el-table-column label="操作" width="130">
                            <template slot-scope="scope">
                                <div class="icoBtn">
                                    <i class="fa fa-edit fa-lg" @click="editOthers(scope.row)" title="编辑其他信息"></i>
                                    <!-- <i class="fa fa-barcode fa-lg" @click="showBarcode(scope.row)" title="条码信息"></i>
                                            <i class="fa fa-thermometer fa-lg" @click="showHumiture(scope.row)" title="温湿度信息"></i> -->
                                    <i class="fa fa-trash-o fa-lg" @click="delRow(scope.row)" title="删除" style="color:red;margin-left:5px;"></i>
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
                        <el-table-column label="收货数量" width="90">
                            <template slot-scope="scope">
                                <el-input-number placeholder="请输入数量" v-model="scope.row.qty" size="mini" :min="0" :max="scope.row.unrecieveQty" class="col-input-num">
                                </el-input-number>
                            </template>
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
                        <el-table-column label="温度">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.receiveTemprature" placeholder="说明" ></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="湿度">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.receiveHumidity" placeholder="说明" ></el-input>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click.native="confirmVisible = false" >取消</el-button>
                        <el-button type="primary" @click.native="submitPurconfirm" >确认</el-button>
                    </div>
                </el-dialog>
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
                        <!--<el-col :span="12">
                                                            <el-form-item label="定量包装">
                                                                <span>{{ recBillDetail.packetCode }}</span>
                                                            </el-form-item>
                                                        </el-col>-->
                        <!--<el-col :span="12">
                                                            <el-form-item label="条码">
                                                                <span>{{ recBillDetail.barcode }}</span>
                                                            </el-form-item>
                                                        </el-col>-->
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
            </template>
        </el-col>
    </section>
</template>
<script>
import moment from 'moment';
import { DISTRBILL_STATUS } from './common/constance';
import IEcharts from 'vue-echarts-v3';
import barcodeUtil from '../../common/js/barcodeUtil';
import barcodeMathc from './barcodeMathc';
export default {
    name: 'recevieOrder',
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
                distrBillList: []
            },
            recBill: {
                id: '',
                billId: '',
                distrBillId: '',
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
            constRecBillDetail: {
                id: '',
                pid: '',
                billId: '',
                rowNumber: '',
                distrBillId: '',
                distrRowNumber: 0,
                purBillId: '',
                purRowNumber: 0,
                purBillDate: '',
                applyBillId: '',
                saleBillId: '',
                provGoodsId: '',
                goodsGg: '',
                goodsMrfsId: '',
                mfrsName: '',
                made: '',
                hosGoodsId: '',
                hosGoodsCode: '',
                hosGoodsName: '',
                isUniqueCode: '',
                batchNum: '',
                batchCode: '',
                sterilizationCode: '',
                sterilizationEndDate: '',
                sterilizationDate: '',
                expdtEndDate: '',
                productDate: '',
                unrecieveQty: '',
                qty: '',
                price: '',
                revieveResult: '',
                sumRows: '',
                status: '',
                receiveTemprature: 0,
                receiveHumidity: 0,
                memo: '',
                version: '',
                lstUniqueList: [],
                lstHumitureList: []
            },
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
            recHumitureList: [],
            barcode: '',
            mainBarcode: '',
            fBarcode: '',
            barcodeType: true,
            barcodeMatchV: false,
            barcodeMatch: {
                provId: '',
                hosId: '',
                mfrsCode: ''
            }
        }
    },
    filters: {
        fStatus: function (value) {
            if (value) {
                return DISTRBILL_STATUS[value].text;
            } else {
                return '';
            }
        }
    },
    components: {
        IEcharts
    },
    methods: {
        //获取列表
        getDistrBillList: function (pIndex) {
            this.page = pIndex;
            var param = {
                orderBy: "",
                page: this.page,
                rows: this.pageSize,
                queryObject: {
                    billId: this.filters.billId,
                    purchaseCompanyId: this.filters.purchaseCompanyId,
                    status: 20
                }
            };
            this.loading = true;
            this.axios.post('/spdHDIService/orderMgr/distrBill/listByPage', param).then(res => {
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
        distrDetailClose() {
            this.recBillDetail = {};
            this.recUnique = {};
            this.recHumiture = {};
            this.recUniqueList = [];
            this.recHumitureList = [];
        },
        editOthers(row) {
            this.otherV = true;
            this.recBillDetail = row;//Object.assign({}, row);
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
        showPurcDialog(billId) {
            this.confirmVisible = true;
            this.diaLoging = true;
            this.getDistrBill(billId).then(res => {
                this.diaLoging = false;
                if (res.data.code == "0") {
                    this.distrBill = res.data.data;
                    this.convertDistrToRecBill(this.distrBill);
                    if (this.distrBill.distrBillList && this.distrBill.distrBillList.length > 0) {
                        this.convertDistrLstToRecBillLst(this.distrBill.distrBillList);
                    }
                }
            }, err => {
                this.diaLoging = false;
            });;
        },
        getDistrBill(billId) {
            return this.axios.get('/spdHDIService/orderMgr/distrBill/distrBillByBillId/' + billId);
        },
        convertDistrToRecBill(distBill) {
            if (distBill) {
                this.recBill.id = '';
                this.recBill.billId = '';
                this.recBill.distrBillId = distBill.billId; //应该加入配送单号
                this.recBill.purchaseCompanyId = distBill.purchaseCompanyId;
                this.recBill.purchaseCompanyName = distBill.purchaseCompanyName;
                this.recBill.applyOrgId = distBill.applyOrgId;
                this.recBill.applyOrgCode = distBill.applyOrgCode;
                this.recBill.applyOrgName = distBill.applyOrgName;
                this.recBill.recieveOrgId = distBill.recieveOrgId;
                this.recBill.recieveOrgCode = distBill.recieveOrgCode;
                this.recBill.recieveOrgName = distBill.recieveOrgName;
                this.recBill.provId = distBill.provId;
                this.recBill.provName = distBill.provName;
                this.recBill.fillter = this.user.userId;
                this.recBill.fillterName = this.user.ename;
                this.recBill.revieverId = this.user.userId;
                this.recBill.revieverName = this.user.ename;
                this.recBill.memo = '';
                this.recBill.sumRow = 0;
                this.recBill.fillDate = new Date();//moment(new Date()).format("YYYY-MM-DD");
                this.recBill.lstDetail = []
            }
        },
        convertDistrLstToRecBillLst(distrLst) {
            var res = [];
            if (distrLst && distrLst.length > 0) {
                distrLst.forEach(function (item, index) {
                    var row = Object.assign({}, this.constRecBillDetail);
                    row.id = '';
                    row.pid = '';
                    row.billId = '';
                    row.rowNumber = index + 1;
                    row.distrBillId = item.billId;
                    row.distrRowNumber = item.rowNumber;
                    row.purBillId = item.purBillId;
                    row.purRowNumber = 0;
                    row.purBillDate = item.purBillDate;
                    row.applyDeptName = item.applyDeptName;
                    row.applyBillId = item.applyBillId;
                    row.saleBillId = item.saleBillId;
                    row.provGoodsId = item.hosGoodsId;
                    row.goodsGg = item.goodsGg;
                    row.goodsMrfsId = item.goodsMrfsId;
                    row.mfrsName = '';
                    row.made = item.made;
                    row.hosGoodsId = item.hosGoodsId;
                    row.hosGoodsCode = item.hosGoodsCode;
                    row.hosGoodsName = item.hosGoodsName;
                    row.isUniqueCode = item.isUniqueCode;
                    row.batchNum = item.batchNum;
                    row.batchCode = item.batchCode;
                    row.sterilizationCode = item.sterilizationCode;
                    row.sterilizationEndDate = item.sterilizationEndDate | new Date();
                    row.sterilizationDate = item.sterilizationDate;
                    row.expdtEndDate = item.expdtEndDate;
                    row.productDate = item.productDate;
                    row.unrecieveQty = item.hosQty - item.receiveQty / item.sendScale;
                    row.qty = 0;//item.hosQty;
                    row.price = item.hosPrice;
                    row.revieveResult = '';//验收结论
                    row.sumRows = 0;//任务总行数
                    row.receiveTemprature = 0;
                    row.receiveHumidity = 0;
                    row.status = 0;
                    row.version = 0;
                    var uniqueLst = this.convertDistrUniqueToRec(item.distrBillUniqueList, item);
                    row.lstUniqueList = uniqueLst;
                    var humitureLst = this.convertDistrHumToRec(item.distrBillHumitureList, item);
                    row.lstHumitureList = humitureLst;
                    res.push(row);
                }, this);
                this.recBill.lstDetail = res;
            }
        },
        convertDistrUniqueToRec(uniqueLst, detail) {
            var res = [];
            if (uniqueLst && uniqueLst.length > 0) {
                uniqueLst.forEach(function (item, index) {
                    var row = Object.assign({}, this.recUnique);
                    row.id = '';
                    row.pid = '';
                    row.rowNumber = index + 1;
                    row.hosGoodsId = detail.provGoodsId;
                    row.provGoodsId = detail.provGoodsId
                    row.goodsName = detail.hosGoodsName;

                    row.batchCode = item.batchCode;
                    row.uniqueCode = item.uniqueCode;
                    row.isRecieve = 1;
                    //row.sourceData = 0;
                    row.version = 0;
                    res.push(row);
                }, this);
            }
            return res;
        },
        convertDistrHumToRec(humitureLst, detail) {
            var res = [];
            if (humitureLst && humitureLst.length > 0) {
                humitureLst.forEach(function (item, index) {
                    var row = Object.assign({}, this.recHumiture);
                    row.id = '';
                    row.pid = '';
                    row.distrRowNumber = index + 1;
                    row.boxCode = item.boxCode;
                    row.goodsName = detail.hosGoodsName;
                    row.hosGoodsId = detail.hosGoodsId;
                    row.provGoodsId = item.provGoodsId;
                    row.batchCode = item.batchCode;

                    row.uniqueCode = item.uniqueCode;
                    row.temprature = item.temprature;
                    row.humidity = item.humidity;
                    row.measureDate = item.measureDate;
                    row.receiveTemprature = '';
                    row.receiveHumidity = '';
                    row.receiveMeasureDate = new Date();
                    //row.isRecieve = item.batchNum;
                    row.sourceData = 0;
                    row.version = 0;
                    res.push(row);
                }, this);
            }
            return res;
        },
        sltRowsChange(sltRows) {
            this.sltRows = sltRows;
        },
        submitPurconfirm() {
            if(this.sltRows.length==0){
                this.$msgbox({ title: "提示", message: "请勾选产品明细", type: "error" });
                return;
            }
            this.diaLoging = true;
            this.recBill.status = 20;
            var params = Object.assign({}, this.recBill);
            params.lstDetail = this.sltRows;
            var serviceUrl = '/spdHDIService/orderMgr/receiveBill/add';
            var _this = this;
            this.axios.post(serviceUrl, params).then(res => {
                _this.diaLoging = false;
                if (res.data.code == "0") {
                    _this.$msgbox({ title: "提示", message: "操作成功", type: "success" });
                    var billId = res.data.data.billId;
                    _this.$msgbox({
                        title: "提示",
                        message: "操作成功",
                        type: "success",
                        callback: action => {
                            _this.confirmVisible = false;
                            _this.getDistrBillList(_this.page);
                            _this.$emit('saveRecBill', billId);
                        }
                    });
                } else {
                    this.$msgbox({ title: "提示", message: "操作失败", type: "error" });
                }
            }, err => {
                this.diaLoging = false;
                this.$msgbox({ title: "提示", message: "操作失败", type: "error" });
            });
        },
        barcodeTypeChange(val) {
            this.$nextTick(function () {
                if (val) {
                    this.$refs.mbarcode.$el.children[0].focus();
                    this.$refs.mbarcode.$el.children[0].select();
                } else {
                    this.$refs.mainBarcode.$el.children[0].focus();
                    this.$refs.mainBarcode.$el.children[0].select();

                }
            });
        },
        barcodeNext() {
            this.fBarcode = "";
            this.$refs.fbarcode.$el.children[0].focus();
            this.$refs.fbarcode.$el.children[0].select();
        },
        barcodePre() {
            barcodeUtil.parseCombinedBarcode(this.mainBarcode, this.fBarcode, this.recBill.purchaseCompanyId, this.recBill.provId).then(res => {
                var goodsLst = res.data.data;
                this.setSendGoods(goodsLst);
                this.$refs.mainBarcode.$el.children[0].focus();
                this.$refs.mainBarcode.$el.children[0].select();
                this.mainBarcode = "";
                this.fBarcode = "";
            });
        },
        addBybarcode() {
            barcodeUtil.parseCombinedBarcode(this.barcode, null, this.recBill.purchaseCompanyId, this.recBill.provId).then(res => {
                var goodsLst = res.data.data;
                this.setSendGoods(goodsLst);
                this.$refs.mbarcode.$el.children[0].focus();
                this.$refs.mbarcode.$el.children[0].select();
            });
        },
        findGoods(barCodeInfo) {
            var res = null;
            var flag = false;//是否找到匹配的商品
            for (var i = 0; i < this.recBill.lstDetail.length; i++) {
                var item = this.recBill.lstDetail[i];
                if (barCodeInfo.goodsId == item.hosGoodsId) {
                    flag = true;
                    if (item.qty == 0) {
                        res = item;
                        break;
                    } else if (item.qty > 0 && item.qty < item.unrecieveQty) {
                        // if (item.batchNum && item.expdtEndDate && item.batchNum == barCodeInfo.batchNo
                        //     && item.expdtEndDate.getTime() == new Date(barCodeInfo.expiredDate).getTime()) {
                        //     res = item;
                        //     break;
                        // }
                        if (item.batchNum) {
                            if(item.batchNum == barCodeInfo.batchNo){
                                res = item;
                                break;
                            }
                        }else{
                             res = item;
                                break;
                        }
                    }

                }
            }
            if (!flag) {
                this.barcodeMatch.hosId = this.distrBill.purchaseCompanyId;
                this.barcodeMatch.provId = this.distrBill.provId
                this.barcodeMatch.mfrsCode = barCodeInfo.goodsNo;
                this.barcodeMatchV = true;
            }
            return res;
        },
        setSendGoods(goodsLst) {
            if (!Array.isArray(goodsLst) || goodsLst.length == 0) {
                this.$message.error('此条码无法解析!');
                return;
            }
            for (var i = 0; i < goodsLst.length; i++) {
                if (goodsLst[i].mainFlag == "1") {//主码
                    var curRow = this.findGoods(goodsLst[i]);
                    if (curRow && curRow.qty < curRow.unrecieveQty) {
                        this.recBillDetail = curRow;
                        this.recBillDetail.qty++;
                        this.recBillDetail.expdtEndDate = new Date(goodsLst[i].expiredDate);
                        this.recBillDetail.batchNum = goodsLst[i].batchNo;
                        this.$refs.receListTable.toggleRowSelection(curRow,true);
                        break;
                    }
                } else {//副码
                    this.distrDetail.sendQty++;
                }
            }
        },
        barcodeMatchSave() {
            this.barcodeMatchV = false;
            if (barcodeType) {
                this.addBybarcode();
            } else {
                this.barcodePre();
            }
        },
    },
    mounted() {
        this.filters.purchaseCompanyId = this.user.corpId;
        this.getDistrBillList(1);
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
