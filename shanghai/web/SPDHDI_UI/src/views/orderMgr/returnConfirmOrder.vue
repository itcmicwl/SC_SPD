<template>
        <el-col :span="24" style="height: 100%;">
            <el-col :span="24" class="toolbar">
                <el-form :inline="true" label-width="80px">
                    <el-form-item label="退货单号">
                        <el-input v-model="filters.billId" placeholder="退货单号" ></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" icon="search" v-on:click="getReturnBillList(1)" >查询</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
            <!--列表-->
            <template>
                    <el-table :data="returnBillList" highlight-current-row v-loading="loading" style="width: 100%;" fit class="RO-Main-tableheight">
                        <el-table-column label="操作" width="150">
                            <template slot-scope="scope">
                                <el-button  @click="showRetrunBillDialog(scope.row.billId)" type="text">订单确认</el-button>
                            </template>
                        </el-table-column>


                        <el-table-column prop="billId" label="退货单编号" sortable>
                        </el-table-column>

                          <el-table-column prop="returnCompanyName" label="请退机构" sortable>
                        </el-table-column>

                        <el-table-column prop="returnOrgName" label="请退科室" sortable>
                        </el-table-column>
                        <el-table-column prop="outOrgName" label="出库科室" sortable>
                        </el-table-column>

                        <el-table-column prop="status" label="状态" sortable :formatter="statusFormat">
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

                    </el-table>
                    <!--工具条-->
                    <el-col :span="24" class="toolbar">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[20, 30, 50, 100]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
                        </el-pagination>
                    </el-col>

                <el-dialog title="订单确认" @close="onClose" :visible.sync="confirmVisible" :close-on-click-modal="false"  v-loading="diaLoging" width="70%">
                    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                        <el-form label-width="120px">
                            <el-col :span="8">
                                <el-form-item label="请购机构:">
                                    <span>{{ reBill.returnCompanyName }}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="退货科室:">
                                    <span>{{ reBill.returnOrgName }}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="供应商:">
                                    <span>{{ reBill.provName }}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="状态:">
                                    <span>{{ reBill.status | fStatus}}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="制单时间:">
                                    <span>{{ reBill.fillDate | dateFormat('YYYY-MM-DD')}}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="制单人:">
                                    <span>{{ reBill.fillterName }}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24">
                                <el-form-item label="备注:">
                                    <span>{{ reBill.memo }}</span>
                                </el-form-item>
                            </el-col>
                        </el-form>
                    </el-col>
                    <el-table :data="returnconfirm.lstDetail" style="width: 100%;" fit height="300" stripe border @selection-change="sltRowsChange">
                        <el-table-column type="selection" width="30" >
                        </el-table-column>
                        <el-table-column label="序号" prop="rowNumber" width="60">
                        </el-table-column>
                        <!--<el-table-column label="状态" width="60">
                                                    <template slot-scope="scope">
                                                        <el-tag type="danger" v-if="scope.row.purStatus==1">已确认</el-tag>
                                                        <el-tag type="success" v-if="scope.row.purStatus==0">未确认</el-tag>
                                                    </template>
                                                </el-table-column>-->
                        <el-table-column label="医院产品名称" prop="hosGoodsName" width="180" show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column label="供应商产品名称" prop="provGoodsErpName" width="180" show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column label="规格" prop="goodsGg" width="180" show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column label="产地" prop="made" width="180" show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column label="厂家" prop="mfrsName" width="180" show-overflow-tooltip>
                        </el-table-column>

                        <el-table-column label="退货数量" width="90">
                            <template slot-scope="scope">
                                <el-input-number placeholder="请输入数量" v-model="scope.row.returnQty" size="mini" :min="0" :max="scope.row.purQty" class="col-input-num" @change="qtyChange($event,scope.row)">
                                </el-input-number>
                            </template>
                        </el-table-column>
                        <el-table-column label="退货原因" prop="returnReason" width="180" show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column label="退货单位" prop="returnUnit" width="60" show-overflow-tooltip>
                    </el-table-column>
                        <el-table-column label="发货单位" prop="sendUnit" width="60" show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column label="发货单价" prop="sendPrice" width="90" show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column label="退货单价" prop="returnrice" width="90" show-overflow-tooltip>
                        </el-table-column>
                    </el-table>

                    <div slot="footer" class="dialog-footer">
                        <el-button @click.native="confirmVisible = false" >取消</el-button>
                        <el-button type="primary" @click.native="submitRetrunConfirm"  :disabled="sltRows.length == 0">生成退货确认单</el-button>
                    </div>
                </el-dialog>
            </template>
        </el-col>
</template>
<script>
    import moment from 'moment';
    import { PURCHASE_STATUS } from './common/constance';
    import { mapGetters } from 'vuex';
    export default {
        name: 'reconfirm',
        data() {
            return {
                diaLoging: false,
                filters: {
                    billId: '',
                    provId: ''
                },
                total: 0,
                page: 1,
                pageSize: 30,
                loading: false,
                returnBillList: [],
                reBill: {
                    id: '',
                    billId: '',
                    purType:10,
                    returnCompanyId: '',
                    returnCompanyName: '',
                    returnOrgId: '',
                    returnOrgName: '',
                    returnOrgCode:'',
                    outOrgId:'',
                    outOrgName:'',
                    outOrgCode:'',
                    provId: '',
                    provName: '',
                    distrRelation: '',
                    fillter: '',
                    fillterName: '',
                    memo: '',
                    isStartInvoice:'',
                    sumRow: '',
                    status: '',
                    fillDate: '',
                    version: '',
                    returnBillSubList: []
                },
                returnconfirm: {
                    id: '',
                    billId: '',
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
                    provName: '',
                    provErpCode: '',
                    provOrgCode: '',
                    provSystemId: '',
                    deliveryRelation: '',
                    balanceOrgId: '',

                  //  sumAmount: 0,
                   // sumTaxAmount: 0,

                    status: 0,
                    fillter: '',
                    fillterName: '',
                    remark: '',
                    sumRow: 0,
                    fillDate: '',
                    lstDetail: []
                },

                returnconfirmDetail: {
                    id: '',
                    pid: '',
                    billId: '',
                    rowNumber: '',
                    returnBillId: '',
                    returnBillRownum:'',
                    returnBillDate:'',
                    purBillId:'',
                    applyBillId:'',
                    provGoodsId:'',
                    goodsGg: '',
                    goodsMrfsId: '',
                    mfrsName:'',
                    made: '',
                    hosGoodsId: '',
                    hosGoodsCode: '',
                    hosGoodsName: '',
                    provGoodsErpCode: '',
                    provGoodsErpName: '',
                    batchNum:'',
                    batchCode:'',
                    sterilizationCode:'',
                    expdtEndDate:'',
                    productDate:'',
                    returnUnit:'',
                    returnScale:'',
                    returnPrice:'',
                    returnQty:'',
                    sendUnit:'',
                    sendQty:'',
                    sendPrice:'',
                    outKind:'',
                    sumRows:'',
                    sourceData:'',
                    returnReason:''

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
            //获取退货单列表
            getReturnBillList: function (pIndex) {
                this.page = pIndex;
                var param = {
                    orderBy: "",
                    page: this.page,
                    rows: this.pageSize,
                    queryObject: {
                        billId: this.filters.billId,
                        provId: this.filters.provId,
                        status: 20 //10 保存 20 提交
                    }
                };
                this.loading = true;
                this.axios.post('/spdHDIService/orderMgr/returnBill/listByPageVo', param).then(res => {
                    this.loading = false;
                    this.returnBillList = res.data.data.data;
                    this.total = res.data.data.total;
                }, err => {
                    this.loading = false;
                });
            },
            handleCurrentChange(val) {
                this.page = val;
                this.getReturnBillList(this.page);
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.getReturnBillList(this.page);
            },
            onClose() {

            },
            showRetrunBillDialog(reBillId) {
                this.confirmVisible = true;
                this.diaLoging = true;
                this.getReturnBill(reBillId).then(res => {
                    this.diaLoging = false;
                    if (res.data.code == "0") {
                        this.reBill = res.data.data;
                        this.convertPurToPurconfirm(this.reBill);
                        if (this.reBill.returnBillSubList && this.reBill.returnBillSubList.length > 0) {
                            this.convertReBillLstToReBillconfirmLst(this.reBill);
                        }
                    }
                }, err => {
                    this.diaLoging = false;
                });;
            },
            getReturnBill(purId) {
                return this.axios.get('/spdHDIService/orderMgr/returnBill/getReturnBill/' + purId);
            },
            convertPurToPurconfirm(pur) {
                if (pur) {
                    this.returnconfirm.id = '';
                    this.returnconfirm.billId = '';
                    this.returnconfirm.spdCode = '';
                    this.returnconfirm.returnBillId = pur.billId;
                    this.returnconfirm.returnCompanyId = pur.returnCompanyId;
                    this.returnconfirm.returnCompanyCode = pur.returnCompanyCode;
                    this.returnconfirm.returnCompanyName = pur.returnCompanyName;
                    this.returnconfirm.returnCompanyErpCode = pur.returnCompanyErpCode;
                    this.returnconfirm.returnOrgId  = pur.returnOrgId;
                    this.returnconfirm.returnOrgCode  = pur.returnOrgCode;
                    this.returnconfirm.returnOrgName = pur.returnOrgName;
                    this.returnconfirm.returnOrgScmCode = pur.returnOrgScmCode;
                    this.returnconfirm.returnOrgErpCode = pur.returnOrgErpCode;
                    this.returnconfirm.outOrgId = pur.outOrgId;
                    this.returnconfirm.outOrgCode = pur.outOrgCode;
                    this.returnconfirm.outOrgName = pur.outOrgName;
                    this.returnconfirm.outOrgScmCode = pur.outOrgScmCode;
                    this.returnconfirm.outOrgErpCode =pur.outOrgErpCode;
                    this.returnconfirm.provId = pur.provId;
                    this.returnconfirm.provCode = pur.provCode;
                    this.returnconfirm.provName = pur.provName;
                    this.returnconfirm.provErpCode = pur.provErpCode;
                    this.returnconfirm.provOrgCode = '';
                    this.returnconfirm.provSystemId = '';
                    this.returnconfirm.deliveryRelation = pur.distrRelation;
                    this.returnconfirm.balanceOrgId = pur.balanceOrgId;

                    this.returnconfirm.status=0;
                    this.returnconfirm.sumRow=pur.returnBillSubList.length;
                    this.returnconfirm.fillDate = moment(new Date()).format("YYYY-MM-DD");
                    this.returnconfirm.fillter = this.getLoginUser.userId;
                    this.returnconfirm.lstDetail = [];
                }
            },
            convertReBillLstToReBillconfirmLst(pur) {
              let  subLst = pur.returnBillSubList;
                if (subLst && subLst.length > 0) {
                    var res = [];
                    subLst.forEach(function (item, index) {
                        if (item.status == 1) {  //只显示未确认的明细
                            alert(item.status);
                            var row = Object.assign({}, this.returnconfirmDetail);
                            row.id = '';
                            row.pid = '';
                            row.billId = '';
                            row.rowNumber = index + 1;
                            row.returnBillId = item.returnBillId;
                            alert(row.returnBillId);
                            row.returnBillRownum = item.rowNumber;
                            alert(row.returnBillRownum);

                            row.returnBillDate = pur.fillDate;
                            row.purBillId =item.purBillId;
                            row.applyBillId = item.applyBillId;
                            row.provGoodsId = item.provGoodsId;     // 需要转换
                            row.goodsGg = item.goodsGg;
                            row.goodsMrfsId = item.goodsMrfsId;
                            row.mfrsName = item.mfrsName;
                            row.made = item.made;
                            row.provGoodsErpCode = item.provGoodsErpCode;
                            row.provGoodsErpName = item.provGoodsErpName;
                            row.hosGoodsId = item.hosGoodsId;
                            row.hosGoodsCode = item.hosGoodsCode;
                            row.hosGoodsName = item.hosGoodsName;

                            row.batchCode = item.batchCode;
                            row.batchNum = item.batchNum;


                            row.returnReason = item.returnReason;
                            row.sterilizationCode = item.sterilizationCode;
                            row.expdtEndDate = item.expdtEndDate;
                            row.productDate = item.productDate;
                            row.returnUnit = item.returnUnit    //请退商品医院unit
                            row.returnScale =  item.returnScale               //转换比率
                            row.returnPrice = item.outstorePrice;//请退单位单价
                            row.returnQty = item.qty;
                            row.sendUnit= item.sendUnit //配送商商品unit
                           row.sendPrice= item.sendPrice //配送商单价
                            row.outKind =1  //入库类型默认为1
                            row.sourceData =1 //数据来源 默认值1

                            res.push(row);
                        }
                    }, this);
                     this.returnconfirm.lstDetail= res;
                }
            },
            sltRowsChange(sltRows) {
                this.sltRows = sltRows;
        //        this.calculateAmount();
            },

            qtyChange(val, row) {
//                row.qty = val;
//                row.qrTaxAmount = row.qrTaxPrice * row.qty;
//                row.qrAmount = row.provGoodsErpPrice * row.qty;
//                this.calculateAmount();
            },
            submitRetrunConfirm() {
                this.diaLoging = true;
                this.returnconfirm.status = 10; //暂存
                var params = Object.assign({}, this.returnconfirm);
                if(this.sltRows == 0){return;}
                params.lstDetail = this.sltRows;
                var serviceUrl = '/spdHDIService/orderMgr/returnconfirm/submitReturnconfirm';
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
                                _this.getReturnBillList(_this.page);
                                _this.$nextTick(function () {
                                    _this.$emit('savePurcon', billIds);
                                });
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

            statusFormat(row) {
                return PURCHASE_STATUS[row.status].text;
            },
        },
        mounted() {
            this.filters.provId = this.getLoginUser.corpId;
            this.getReturnBillList(1);
        }
    };

</script>

<style scoped>
    .RO-Main-tableheight{
        height: calc(100% - 100px);
    }
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
</style>
