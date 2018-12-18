<template>
        <el-col :span="24" style="height:100%" id="ec-purc">
            <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                <el-form :inline="true" label-width="80px">
                    <el-form-item label="二级供应商">
                           <secProvSlt :userId="user.userId" @subProvChange="subProvChange" @onLoad="onSecProvLoad"></secProvSlt>
                        </el-form-item>
                    <el-form-item label="采购单号">
                        <el-input v-model="filters.billId" placeholder="采购单号" ></el-input>
                    </el-form-item>
                    <el-form-item label="状态">
                        <el-select v-model="filters.status" placeholder="请选择" >
                            <el-option
                            v-for="item in statusDropdown"
                            :key="item.label"
                            :label="item.label"
                            :value="item.value">
                            {{item.label}}
                            </el-option>
                        </el-select>
                    </el-form-item>
                    
                    <el-form-item label="制单时间">
                        <el-date-picker v-model="filters.sDate" type="daterange" align="right" placeholder="选择日期范围" :picker-options="pickerOptions2" >
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item style="margin-left:50px;">
                        <el-button type="primary" icon="search" v-on:click="getPurchaseList(1)" >查询</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
            <!--列表-->
            <el-table :data="purchaseList" highlight-current-row v-loading="loading" style="width: 100%;" fit class="table_content">
                <el-table-column label="操作" width="80">
                    <template slot-scope="scope">
                        <el-button  @click="showPurcDialog(scope.row.id)" type="text">确认</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="billId" label="采购单号" sortable width="150">
                </el-table-column>
                <el-table-column prop="status" label="状态" width="80">
                    <template slot-scope="scope">
                        {{scope.row.status | fStatus}}
                    </template>
                </el-table-column>
                <!--<el-table-column prop="provName" label="供应商名称" sortable width="200">
                </el-table-column>-->
                <el-table-column prop="subProvName" label="供应商名称" sortable width="200">
                </el-table-column><!--设置为二级供应商名称-->
                <el-table-column prop="purOrgCname" label="请购科室" sortable width="200">
                </el-table-column>
                <el-table-column prop="recOrgName" label="收货科室" sortable width="200">
                </el-table-column>
                <el-table-column prop="fillterName" label="制单人" sortable width="120">
                </el-table-column>
                <el-table-column prop="fillDate" label="制单时间" width="120">
                    <template slot-scope="scope">
                        {{scope.row.fillDate | dateFormat('YYYY-MM-DD')}}
                    </template>
                </el-table-column>
                <el-table-column prop="remark" label="备注" sortable width="200">
                </el-table-column>
            </el-table>
            <!--工具条-->
            <el-col :span="24" class="toolbar">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[30, 100, 200, 400]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
                </el-pagination>
            </el-col>
            <el-dialog title="订单确认" @close="onClose" :visible.sync="confirmVisible" :close-on-click-modal="false"  v-loading="diaLoging" width="70%">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form label-width="120px">
                        <el-col :span="8">
                            <el-form-item label="请购机构:">
                                <span>{{ purOrder.purchaseComName }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="收货科室:">
                                <span>{{ purOrder.recOrgName }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="供应商:">
                                <span>{{ purOrder.subProvName }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="状态:">
                                <span>{{ purOrder.status | fStatus}}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="制单时间:">
                                <span>{{ purOrder.fillDate | dateFormat('YYYY-MM-DD')}}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="制单人:">
                                <span>{{ purOrder.fillterName }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-form-item label="备注:">
                                <span>{{ purOrder.remark }}</span>
                            </el-form-item>
                        </el-col>
                    </el-form>
                </el-col>
                <el-table :data="purconfimOrder.lstDetail" style="width: 100%;" fit height="280" stripe border @selection-change="sltRowsChange">
                    <el-table-column type="selection" width="30" :selectable="canSlt">
                    </el-table-column>
                    <el-table-column label="序号" prop="rowNumber" width="60">
                    </el-table-column>
                    <!--<el-table-column label="状态" width="60">
                                                <template slot-scope="scope">
                                                    <el-tag type="danger" v-if="scope.row.purStatus==1">已确认</el-tag>
                                                    <el-tag type="success" v-if="scope.row.purStatus==0">未确认</el-tag>
                                                </template>
                                            </el-table-column>-->
                    <el-table-column label="产品名称" prop="hosGoodsName" width="180" show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column label="规格" prop="goodsGg" width="100" show-overflow-tooltip>
                    </el-table-column>
                     <el-table-column label="厂家" prop="mfrsName" width="180" show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column label="销售单价" prop="provGoodsErpPrice">
                    </el-table-column>
                    <el-table-column label="采购数量" prop="purQty" width="90">
                    </el-table-column>
                    <el-table-column label="订单数量" width="90">
                        <template slot-scope="scope">
                            <el-input-number placeholder="请输入数量" v-model="scope.row.qty" size="mini" :min="0" :max="scope.row.purQty" class="col-input-num" @change="qtyChange($event,scope.row)">
                            </el-input-number>
                        </template>
                    </el-table-column>
                    <el-table-column label="单位" prop="unit">
                    </el-table-column>
                    <el-table-column label="含税金额(元)" prop="qrAmount" width="120">
                    </el-table-column>
                    <el-table-column label="不含税金额(元)" prop="qrTaxAmount" width="120">
                    </el-table-column>
                    <el-table-column label="科室请购日期" width="130">
                        <template slot-scope="scope">
                            {{scope.row.applyDate | dateFormat('YYYY-MM-DD')}}
                        </template>
                    </el-table-column>
                    <el-table-column label="预计到货日期" width="130">
                        <template slot-scope="scope">
                            {{scope.row.esArrivalDate | dateFormat('YYYY-MM-DD')}}
                        </template>
                    </el-table-column>
                </el-table>
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form label-width="120px">
                        <el-col :span="8">
                            <el-form-item label="笔数:">
                                <span>{{ purconfimOrder.sumRow }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="总金额:">
                                <span>
                                    <i class="fa fa-yen"></i>{{ purconfimOrder.sumAmount }}</span>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="不含税金额:">
                                <span>
                                    <i class="fa fa-yen"></i>{{ purconfimOrder.sumTaxAmount }}</span>
                            </el-form-item>
                        </el-col>
                    </el-form>
                </el-col>
                <div slot="footer" class="dialog-footer">
                    <el-button @click.native="confirmVisible = false" >取消</el-button>
                    <el-button type="primary" @click.native="submitPurconfirm"  :disabled="sltRows.length == 0">确认</el-button>
                </div>
            </el-dialog>
        </el-col>
</template>
<script>
import moment from 'moment';
import secProvSlt from '@/components/secProvSlt';
import { PURCHASE_STATUS } from '../orderMgr/common/constance';
import { PURCHASE_STATUS_SELECT } from '../orderMgr/common/constance';
import { mapGetters } from 'vuex';
export default {
    name: 'purconfirm',
    data() {
        return {
            diaLoging: false,
            filters: {
                billId: '',
                provId: '',
                sDate: [],
                status:'',
                subProvId:'',
                hosLst:[]
            },
            pickerOptions2: {
                shortcuts: [{
                    text: '最近一周',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近一个月',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近三个月',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                        picker.$emit('pick', [start, end]);
                    }
                }]
            },
            total: 0,
            page: 1,
            pageSize: 30,
            loading: false,
            purchaseList: [
            ],
            statusDropdown:[],
            purOrder: {
                id: '',
                billId: '',
                purType:10,
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
                purchaseList: []
            },
            purconfimOrder: {
                id: '',
                billId: '',
                spdCode: '',
                purBillId: '',
                purType:10,
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
                subProvId: '',
                subProvCode: '',
                subProvName: '',
                subProvErpCode: '',
                provId: '',
                provCode: '',
                provName: '',
                provErpCode: '',
                provOrgCode: '',
                provSystemId: '',
                deliveryRelation: '',
                balanceOrgId: '',
                sumAmount: 0,
                sumTaxAmount: 0,
                status: 0,
                fillter: '',
                fillterName: '',
                remark: '',
                sumRow: 0,
                fillDate: '',
                lstDetail: []
            },
            purconfirmDetail: {
                id: '',
                pid: '',
                billId: '',
                rowNumber: '',
                applyDeptId: '',
                applyDeptCode: '',
                applyDeptName: '',
                purBillId: '',
                purBillRownum: '',
                purBillDate: '',
                applyBillId: '',
                applyRowNumber: 0,
                provGoodsId: '',
                goodsGg: '',
                goodsMrfsId: '',
                mfrsName:'',
                made: '',
                hosGoodsId: '',
                hosGoodsCode: '',
                hosGoodsName: '',
                provGoodsErpCode: '',
                provGoodsErpName: '',
                provGoodsErpPrice: 0,
                hosPrice: 0,
                hosUnit: '',
                hosScale: 1,
                hosApplyQty: 0,
                applyQty: 0,
                hosPurQty: 0,
                purQty: 0,
                unitQty: 0,
                qty: 0,
                qrTax: 0,
                qrTaxPrice: 0,
                qrTaxAmount: 0,
                qrAmount: 0,
                qrTaxMoney: 0,
                applyDate: '',
                esArrivalDate: '',
                uniqueKind:3,
                version: 0
            },
            sltRows: [],
            confirmVisible: false
        }
    },
    computed: {
        ...mapGetters(["getLoginUser"]),
    },
    filters: {
        fStatus: function (value) {
            if (value) {
                return PURCHASE_STATUS[value].text;
            } else {
                return '';
            }
        }
    },
    methods: {
        subProvChange(subProvInfo){
            if(subProvInfo){
                 this.filters.subProvId = subProvInfo.provId;
                 this.filters.hosLst=subProvInfo.hosLst.map(o=>o.hosId);
            }else{
                this.filters.subProvId = '';
                this.filters.hosLst=[];
            }           
        }, 
        onSecProvLoad(subProvInfo){
            this.filters.subProvId = subProvInfo.provId;
            this.filters.hosLst=subProvInfo.hosLst.map(o=>o.hosId);
             this.getPurchaseList(1);
        },
        //获取列表
        getPurchaseList: function (pIndex) {
            this.page = pIndex;
            var param = {
                orderBy: "",
                page: this.page,
                rows: this.pageSize,
                queryObject: {
                    billId: this.filters.billId,
                    //subProvId: this.filters.provId,
                    sDate: this.filters.sDate&&this.filters.sDate[0]!=null?this.filters.sDate[0]:null,
                    eDate: this.filters.sDate&&this.filters.sDate[1]!=null?this.filters.sDate[1]:null,
                    status:this.filters.status,
                    arrStatus: [20, 30],
                    subProvId : this.filters.subProvId,

                    provId : this.filters.provId,
                    hosLst : this.filters.hosLst
                }
            };
            this.loading = true;
            this.axios.post('/spdHDIService/order/purchase/secPurchasePage', param).then(res => {
                this.loading = false;
                this.purchaseList = res.data.data.data;
                this.total = res.data.data.total;
            }, err => {
                this.loading = false;
            });
        },
        handleCurrentChange(val) {
            this.page = val;
            this.getPurchaseList(this.page);
        },
        handleSizeChange(val) {
            this.pageSize = val;
            this.getPurchaseList(this.page);
        },
        onClose() {

        },
        showPurcDialog(purId) {
            this.confirmVisible = true;
            this.diaLoging = true;
            this.getPurchase(purId).then(res => {
                this.diaLoging = false;
                if (res.data.code == "0") {
                    this.purOrder = res.data.data;
                    this.convertPurToPurconfirm(this.purOrder);
                    if (this.purOrder.purchaseList && this.purOrder.purchaseList.length > 0) {
                        this.convertPurLstToPurconfirmLst(this.purOrder.purchaseList);
                    }
                }
            }, err => {
                this.diaLoging = false;
            });;
        },
        getPurchase(purId) {
            return this.axios.get('/spdHDIService/order/purchase/getPurchaseById/' + purId);
        },
        convertPurToPurconfirm(pur) {
            if (pur) {
                this.purconfimOrder.id = '';
                this.purconfimOrder.billId = '';
                this.purconfimOrder.spdCode = '';
                this.purconfimOrder.purBillId = pur.billId;
                this.purconfimOrder.purType =  pur.purType;
                this.purconfimOrder.purchaseCompanyId = pur.purchaseComId;
                this.purconfimOrder.purchaseCompanyCode = pur.purComCode;
                this.purconfimOrder.purchaseCompanyName = pur.purchaseComName;
                this.purconfimOrder.purchaseCompanyErpCode = pur.purComErpCode;
                this.purconfimOrder.applyOrgId = pur.purDeptId;
                this.purconfimOrder.applyOrgCode = pur.purOrgCode;
                this.purconfimOrder.applyOrgName = pur.purOrgCname;
                this.purconfimOrder.applyOrgScmCode = pur.purOrgScmCode;
                this.purconfimOrder.applyOrgErpCode = pur.purOrgErpCode;
                this.purconfimOrder.recieveOrgId = pur.recOrgId;
                this.purconfimOrder.recieveOrgCode = pur.recOrgCode;
                this.purconfimOrder.recieveOrgName = pur.recOrgName;
                this.purconfimOrder.recieveOrgScmCode = pur.recOrgScmCode;
                this.purconfimOrder.recieveOrgErpCode = pur.recOrgErpCode;
                /**二级供应商相关信息 */
                this.purconfimOrder.subProvId = pur.subProvId;
                this.purconfimOrder.subProvCode = pur.subProvCode;
                this.purconfimOrder.subProvName = pur.subProvName;
                this.purconfimOrder.subProvErpCode = pur.subProvErpCode;

                this.purconfimOrder.provId = pur.provId;
                this.purconfimOrder.provCode = pur.provCode;
                this.purconfimOrder.provName = pur.provName;
                this.purconfimOrder.provErpCode = pur.provErpCode;

                this.purconfimOrder.provOrgCode = '';
                this.purconfimOrder.provSystemId = '';
                this.purconfimOrder.deliveryRelation = '';
                this.purconfimOrder.balanceOrgId = '';
                this.purconfimOrder.sumAmount = 0;
                this.purconfimOrder.sumTaxAmount = 0;
                this.purconfimOrder.status = 0;
                this.purconfimOrder.fillter = this.getLoginUser.userId;
                this.purconfimOrder.fillterName = this.getLoginUser.ename;
                this.purconfimOrder.remark = '';
                this.purconfimOrder.sumRow = 0;
                this.purconfimOrder.fillDate = moment(new Date()).format("YYYY-MM-DD");
                this.purconfimOrder.lstDetail = [];
                this.purconfimOrder.recAddress = pur.recAddressId;
                this.purconfimOrder.lastArrivalDate = pur.lastArrivalDate;
            }
        },
        convertPurLstToPurconfirmLst(purLst) {
            if (purLst && purLst.length > 0) {
                var res = [];
                purLst.forEach(function (item, index) {
                    if (item.status == 0) {
                        var row = Object.assign({}, this.purDetail);
                        row.id = '';
                        row.pid = '';
                        row.billId = '';
                        row.rowNumber = index + 1;
                        row.saleMan = item.saleMan;
                        row.applyDeptId = item.applyDeptId;
                        row.applyDeptCode = item.applyDeptCode;
                        row.applyDeptName = item.applyDeptName;
                        row.purBillId = item.billId;
                        row.purBillRownum = item.rowNumber;
                        row.purBillDate = item.applyDate;
                        row.applyBillId = item.applyBillId;
                        row.applyRowNumber = item.applyRowNum;
                        row.provGoodsId = item.provGoodsId;
                        row.goodsGg = item.goodsGg;
                        row.goodsMrfsId = item.mfrsId;
                        row.mfrsName = item.mfrsName;
                        row.made = item.made;
                        row.hosGoodsId = item.hosGoodsId;
                        row.hosGoodsCode = item.hosGoodsCode;
                        row.hosGoodsName = item.hosGoodsName;
                        row.provGoodsErpCode = item.erpCode;
                        row.provGoodsErpName = item.goodsName;
                        //row.provGoodsErpPrice = item.salePrice || 0;
                        // 二级供应商登录系统
                        if(this.purconfimOrder.subProvId != this.purconfimOrder.provId){
                            row.provGoodsErpPrice = item.purPrice || 0;
                        } else {
                            row.provGoodsErpPrice = item.salePrice || 0;
                        }

                        row.hosPrice = item.hosPrice || 0;
                        row.hosUnit = item.hosUnit;
                        row.hosScale = item.hosScale || 1;
                        row.hosApplyQty = item.hosApplyQty;            //请购数量(院内单位)
                        row.applyQty = item.applyQty;  //请购数量
                        row.hosPurQty = item.hosPurQty;                //采购数量(院内单位)
                        row.purQty = item.purQty;       //采购数量
                        row.unitQty = item.hosPurQty;                   //确认数量(院内单位)(默认采购数量)
                        row.qty = row.purQty;                           //默认医院采购数量
                        row.qrTax = item.purTax || 1;
                        row.qrTaxPrice = (row.provGoodsErpPrice / (1 + row.qrTax)).toFixed(6);
                        row.qrTaxAmount = (row.qrTaxPrice * row.qty).toFixed(2);
                        row.qrAmount = (row.provGoodsErpPrice * row.qty).toFixed(2);
                        row.qrTaxMoney = row.qrAmount - row.qrTaxAmount;
                        row.applyDate = item.applyDate;
                        row.esArrivalDate = item.receiveDate;
                        row.uniqueKind = item.uniqueKind;
                        row.unit = item.unit;
                        row.version = 0;
                        row.purStatus = item.status;
                        row.checkMark = item.checkMark;
                        res.push(row);
                    }
                }, this);
                this.purconfimOrder.lstDetail = res;
            }
        },
        sltRowsChange(sltRows) {
            this.sltRows = sltRows;
            this.calculateAmount();
        },
        calculateAmount() {
            this.purconfimOrder.sumRow = this.sltRows.length;
            var qrAmount = 0, qrTaxAmount = 0;
            this.sltRows.forEach(item => {
                item.unitQty = item.qty / item.hosScale;
                qrAmount += Number(item.qrAmount);
                qrTaxAmount += Number(item.qrTaxAmount);
            });
            this.purconfimOrder.sumAmount = qrAmount;
            this.purconfimOrder.sumTaxAmount = qrTaxAmount;
        },
        qtyChange(val, row) {
            row.qty = val;
            row.qrTaxAmount = row.qrTaxPrice * row.qty;
            row.qrAmount = row.provGoodsErpPrice * row.qty;
            this.calculateAmount();
        },
        submitPurconfirm() {
            this.diaLoging = true;
            this.purconfimOrder.status = 20;
            var params = Object.assign({}, this.purconfimOrder);
            if(this.sltRows == 0){return;}
            params.lstDetail = this.sltRows;
            var serviceUrl = '/spdHDIService/orderMgr/purconfirm/submitPurconfirm';
            var _this = this;
            this.axios.post(serviceUrl, params).then(res => {
                _this.diaLoging = false;
                if (res.data.code == "0") {
                    _this.$msgbox({ title: "提示", message: "操作成功", type: "success" });
                    var billIds = res.data.data;
                    _this.$msgbox({
                        title: "提示",
                        message: "操作成功",
                        type: "success",
                        callback: action => {
                            _this.confirmVisible = false;
                            _this.getPurchaseList(_this.page);
                            _this.$nextTick(function () {
                                _this.$emit('savePurcon', billIds);
                            });
                        }
                    });
                } else if(res.data.code == "-1000") {
                    this.$msgbox({ title: "提示", message: res.data.msg, type: "error" });
                }else {
                    this.$msgbox({ title: "提示", message: "操作失败", type: "error" });
                }
            }, err => {
                this.diaLoging = false;
                this.$msgbox({ title: "提示", message: "操作失败", type: "error" });
            });
        },
        canSlt(row, index) {
            return row.purStatus == 0 && row.checkMark == 1;
        }
    },
    mounted() {
        this.statusDropdown = PURCHASE_STATUS_SELECT;
        this.filters.provId = this.getLoginUser.corpId;
        var end = new Date();
        var start = new Date();
        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
        this.filters.sDate = [start, end];
        this.getPurchaseList(1);
    },
    components: { secProvSlt }
};

</script>

<style>
.fa {
    margin-right: 5px;
}

#ec-purc .table_content{
    font-size: 12px;
    height: calc(100% - 80px);
    overflow-y: auto;
}

fieldset {
    border-width: 1px;
    border-style: solid;
    border-color: #1fa0ff;
    border-image: initial;
}
</style>