<template>
    <div id = 'orderDetail' >
        <mt-header title="订单详情" >
            <router-link to="/orderConfirm" slot="left">
                <mt-button icon="back"></mt-button>
            </router-link>
            <mt-button slot="right" @click.native="submitPurconfirm">确认</mt-button>
        </mt-header>
        <div id = 'headerMessge'>
            <ul class="list-inline">
                <li>笔数:<span>{{ purconfimOrder.sumRow }}</span></li>
                <li>总金额:￥<span>{{ purconfimOrder.sumAmount }}</span></li>
                <li>不含税金额:￥<span>{{ purconfimOrder.sumTaxAmount }}</span></li>
            </ul>
        </div>
        <div class='order-list'>
                <div class="list-group">
                    <div href="#" class="list-group-item" v-model ="purOrder" style="background-color: rgb(253, 249, 249);">
                        <h4 class="list-group-item-heading">采购单号：<span>{{purOrder.billId}}</span></h4>
                        <p class="list-group-item-text">状态：<span>{{ purOrder.status | fStatus}}</span></p>
                        <p class="list-group-item-text">请购机构：<span>{{ purOrder.purchaseComName }}</span></p>
                        <p class="list-group-item-text">收货科室：<span>{{ purOrder.recOrgName }}</span></p>
                        <p class="list-group-item-text">供应商：<span>{{ purOrder.provName }}</span></p>
                        <p class="list-group-item-text">制单时间：<span>{{ purOrder.fillDate | dateFormat('YYYY-MM-DD')}}</span></p>
                        <p class="list-group-item-text">制单人：<span>{{ purOrder.fillterName }}</span></p>
                        <p class="list-group-item-text">备注:<span>{{ purOrder.remark }}</span></p>
                    </div>
                </div>
        </div>
        <div>
            <table class="table" style="background-color: rgb(253, 249, 249);">
            <thead>
                <tr>
                <td></td>
                <td>
                  <!--<mt-switch v-model="value" @change="allSelect">全选</mt-switch>-->
                </td>
                </tr>
            </thead>
            <tbody>
                <tr v-for="detail in purconfimOrder.lstDetail">
                    <td>
                        <address>
                            <strong>{{detail.hosGoodsName}}</strong><br>
                            采购：￥{{detail.provGoodsErpPrice}} × {{detail.purQty}} 支<br>
                            核对订单数量：<br><mt-field  placeholder="请输入订单数量" type="number" v-model="detail.qty" @change="qtyChange($event,detail)"></mt-field><br>
                            含税金额（元） {{detail.qrAmount}} <br>
                            不含税金额（元） {{detail.qrTaxAmount}} <br>
                            预计 {{detail.esArrivalDate | dateFormat('YYYY-MM-DD')}} 到货<br>
                        </address>
                    </td>
                    <td>
                       <mt-switch v-model="values[detail.rowNumber-1]" @change="selectRow($event,detail)"></mt-switch>
                    </td>
                </tr>
            </tbody>
            </table>
        </div>
    </div>
</template>
<script>
    import moment from 'moment';
    import { PURCHASE_STATUS } from './common/constance';
    import { mapGetters } from 'vuex';
    import { MessageBox } from 'mint-ui';
    export default {
        data() {
            return {
                selected: "2",
                selectIndexArr :[],
                sltRows: [],
                values:[],
                //当前订单对象
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
                }
                
            };
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
            //行数据是否选中
            selectRow : function(isSelected,rowData){
                if(isSelected){
                    this.sltRows.push(rowData);
                }else{
                    for(var i = 0; i < this.sltRows.length; i++){
                        if(this.sltRows[i].rowNumber == rowData.rowNumber) {
                            this.sltRows.splice(i,1); //删除下标为i的元素
                        }
                    }
                }
                this.calculateAmount();
            },
            /**
             * 汇总订单笔数，以及金额
             */
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

            /**
             * 计算采购金额,包括含税金额，和不含税金额
             */
            qtyChange(val, row) {
                if(val<0){
                    MessageBox('提示', '订单数量不得小于0！');
                    return;
                }
                if(row.purQty && val>row.purQty){
                    MessageBox('提示', '订单数量不得大于采购数量！');
                    return;
                }
                row.qty = val;
                row.qrTaxAmount = row.qrTaxPrice * row.qty;
                row.qrAmount = row.provGoodsErpPrice * row.qty;
                this.calculateAmount();
            },
            /**
             * 全选
             */
            allSelect : function(dd){
                alert(dd);
            },
             /**
             * 根据id查询对应的采购单
             */
            getPurchase(purId) {
                return this.axios.get('/spdHDI/order/purchase/getPurchaseById/' + purId);
            },

            handlePurchase(purId) {
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
                });
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
                    this.purconfimOrder.lstDetail = []
                }
            },
            convertPurLstToPurconfirmLst(purLst) {
                if (purLst && purLst.length > 0) {
                    var res = [];
                    var _this = this;
                    purLst.forEach(function (item, index) {
                        if (item.status == 0) {
                            var row = Object.assign({}, this.purDetail);
                            row.id = '';
                            row.pid = '';
                            row.billId = '';
                            row.rowNumber = index + 1;
                            row.applyDeptId = item.applyDeptId;
                            row.applyDeptCode = item.applyDeptCode;
                            row.applyDeptName = item.applyDeptName;
                            row.purBillId = item.billId;
                            row.purBillRownum = item.rowNum;
                            row.purBillDate = item.applyDate;
                            row.applyBillId = '';
                            row.applyRowNumber = 0;
                            row.provGoodsId = item.hosGoodsId;
                            row.goodsGg = item.goodsGg;
                            row.goodsMrfsId = item.mfrsId;
                            row.mfrsName = item.mfrsName;
                            row.made = item.made;
                            row.hosGoodsId = item.hosGoodsId;
                            row.hosGoodsCode = item.hosGoodsCode;
                            row.hosGoodsName = item.hosGoodsName;
                            row.provGoodsErpCode = item.erpCode;
                            row.provGoodsErpName = item.goodsName;
                            row.provGoodsErpPrice = item.salePrice || 0;
                            row.hosPrice = item.hosPrice || 0;
                            row.hosUnit = item.hosUnit;
                            row.hosScale = item.hosScale || 1;
                            row.hosApplyQty = item.hosApplyQty;            //请购数量(院内单位)
                            row.applyQty = item.applyQty;                  //请购数量
                            row.hosPurQty = item.hosPurQty;                //采购数量(院内单位)
                            row.purQty = item.purQty;                      //采购数量
                            row.unitQty = item.hosPurQty;                  //确认数量(院内单位)(默认采购数量)
                            row.qty = row.purQty;                          //默认医院采购数量
                            row.qrTax = item.purTax || 1;
                            row.qrTaxPrice = (row.provGoodsErpPrice / (1 + row.qrTax)).toFixed(6);
                            row.qrTaxAmount = (row.qrTaxPrice * row.qty).toFixed(2);
                            row.qrAmount = (row.provGoodsErpPrice * row.qty).toFixed(2);
                            row.qrTaxMoney = row.qrAmount - row.qrTaxAmount;
                            row.applyDate = item.applyDate;
                            row.esArrivalDate = item.receiveDate;
                            row.uniqueKind = item.uniqueKind;
                            row.version = 0;
                            row.purStatus = item.status;
                            res.push(row);
                        }
                    }, this);
                    this.purconfimOrder.lstDetail = res;
                    res.forEach(function (r, i) {
                        _this.selectIndexArr.push(r.rowNumber);
                        _this.values.push(false)
                    });
                }
            },
            /**
             *确认事件
            * */
            submitPurconfirm : function() {
                this.purconfimOrder.status = 20;
                var params = Object.assign({}, this.purconfimOrder);
                if(this.sltRows == 0){
                     MessageBox('提示', '请选中一笔记录！');
                    return;
                }
                params.lstDetail = this.sltRows;
                var serviceUrl = '/spdHDI/orderMgr/purconfirm/submitPurconfirm';
                var _this = this;
                this.axios.post(serviceUrl, params).then(res => {
                    if (res.data.code == "0") {
                        MessageBox({title: "提示",message: "操作成功"}).then(action=>{
                            this.$router.push({
                                name: 'orderConfirm'
                            })
                        });
                    } else {
                        MessageBox({ title: "提示", message: "操作失败"});
                    }
                }, err => {
                    MessageBox({ title: "提示", message: "操作失败"});
                });
            }, 
        },
        mounted() {
            var id = this.$route.query.routeParams;
            this.handlePurchase(id);
        }
    }
</script>


<style scoped>{
    @import '../../styles/css/bootstrap.css';
    @import '../../styles/css/font-awesome.css';
}  
</style>
<style>
    #orderDetail h4{ font-size: 15px;}
    #orderDetail .mint-button-text{
        margin-bottom: 0px; 
        font-weight: inherit;
    }
    #orderDetail .mint-header-button.is-right {
        text-align: center;
    }
    #headerMessge {
       background-color:#ff7e26;
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
        box-sizing: border-box;
        color: #fff;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        font-size: 13px;
        height: 50px;
        line-height: 1;
        padding: 0 10px;
        padding-top:12px; 
        position: relative;
        text-align: center;
        white-space: nowrap;
    }
    #headerMessge .mint-button--small {
        height: 25px;
    }
    #headerMessge .li-right{
        margin-left:5px;
    }
    #orderDetail .order-list{
        margin-top: 0px; 
        background-color:#f7f4f4
    }
    #orderDetail .list-group {
        padding-left: 0;
        margin-bottom: 0px;
    }
	#orderDetail h1.mint-header-title {
		margin-top: 0px; 
    	margin-bottom: 0px; 
    }
    #orderDetail .list-group-item-text {
        line-height: 1.5;
        color: #888080;
    }
    #orderDetail .list-group-item:last-child {
      margin-bottom: 0;
      border-bottom-right-radius: 0px; 
      border-bottom-left-radius: 0px; 
    }
    .list-group-item:first-child {
        border-top-left-radius: 0px;
        border-top-right-radius: 0px;
    }
    #orderDetail .mint-tab-item-label {
        font-size: 15px;
    }
    #orderDetail .list-group-item {
        border-left: 0px;
        border-right: 0px;
    }
    #orderDetail .mint-cell {
        min-height: 30px;
        width: 90%;
    }
    #orderDetail .mint-cell-wrapper{
        font-size: 13px; 
    }
   #orderDetail .mint-checkbox-label {
        display: none;
    }
</style>