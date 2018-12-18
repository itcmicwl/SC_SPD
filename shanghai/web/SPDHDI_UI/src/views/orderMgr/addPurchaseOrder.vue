<template>
    <el-row :gutter="10" v-loading="pageLoading" style="height:100%">
            <!--工具条-->
        <el-col :span="24" style="height:100%">
            <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                <el-form label-width="120px" class="orderInput">
                    <el-col :span="8">
                        <el-form-item label="采购机构">
                            <el-input v-model="purOrder.purchaseComName" readonly placeholder="请购机构" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="采购科室">
                            <el-cascader :options="orgInfo"  v-model="purDept" :props="orgProps" :show-all-levels="false" :change-on-select="true">
                            </el-cascader>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="采购方式">
                            <el-switch v-model="purOrder.purType"  active-color="#13ce66"  inactive-color="#ff4949" active-text="实采" inactive-text="虚采" :active-value="10"  :inactive-value="20" ></el-switch>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="收货科室">
                            <el-cascader :options="orgInfo"  v-model="recDept" :props="orgProps" :show-all-levels="false" :change-on-select="true">
                            </el-cascader>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="供应商">
                            <el-select v-model="purOrder.provId" filterable placeholder="请选择" no-data-text="没有对应的供应商" >
                                <template v-if="ProvsInfo.length > 0">
                                    <el-option v-for="item in ProvsInfo" :key="item.provId" :label="item.provName" :value="item.provId">
                                    </el-option>
                                </template>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="制单时间">
                            <el-date-picker v-model="purOrder.fillDate" readonly type="date" placeholder="选择日期" >
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="制单人">
                            <el-input v-model="purOrder.fillterName" readonly placeholder="制单人" ></el-input>
                        </el-form-item>
                    </el-col>
                    <!-- <el-col :span="5">
                        <el-form-item label="收货人">
                            {{purOrder.recLinkman}}
                        </el-form-item>
                    </el-col>
                    <el-col :span="5">
                        <el-form-item label="电话">
                            {{purOrder.recLinkmanPhone}}
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="收货地址">
                            {{purOrder.recAddress}}
                        </el-form-item>
                    </el-col>
                    <el-col :span="2">
                        <el-form-item label-width="0px">
                            <deliveryAddress btn_text="修改" v-on:addressChange="setAddress" :hosId="user.corpId" :deptId="user.orgId"></deliveryAddress>
                        </el-form-item>
                    </el-col> -->
                    <el-col :span="16">
                        <el-form-item label="备注">
                            <el-input type="textarea" v-model="purOrder.remark"  autosize></el-input>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-col>
            <!--列表-->
            <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                <el-button-group>
                    <el-button type="primary" class="el-icon-plus" size="mini" @click.native="addManyRow">添加行</el-button>
                </el-button-group>
            </el-col>
            <el-table :data="purOrder.purchaseList" style="width: 100%;" fit class="table_content" border>
                <el-table-column label="操作" width="80">
                    <template slot-scope="scope">
                        <el-button  @click="delRow(scope.row)" type="text">删除</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="rowNumber" label="序号" width="60">
                </el-table-column>
                <el-table-column prop="applyDeptName" label="请购科室" width="150">
                </el-table-column>
                <el-table-column prop="hosGoodsName" label="产品名称" width="180" show-overflow-tooltip>
                </el-table-column>
                <el-table-column prop="hosPrice" label="请购单价">
                </el-table-column>
                <el-table-column label="请购数量" width="90">
                    <template slot-scope="scope">
                        <el-input-number placeholder="请输入数量" v-model="scope.row.hosApplyQty" size="mini" :min="0" class="col-input-num" @change="qtyChange($event,scope.row,true)">
                        </el-input-number>
                    </template>
                </el-table-column>
                <el-table-column label="采购数量" width="90">
                    <template slot-scope="scope">
                        <el-input-number placeholder="请输入数量" v-model="scope.row.hosPurQty" size="mini" :min="0" class="col-input-num" @change="qtyChange($event,scope.row)">
                        </el-input-number>
                    </template>
                </el-table-column>
                <el-table-column prop="hosUnit" label="单位">
                </el-table-column>
                <!-- <el-table-column prop="purAmount" label="含税金额(元)" width="120">
                </el-table-column>
                <el-table-column prop="purTaxAmount" label="不含税金额(元)" width="120">
                </el-table-column> -->
                <el-table-column label="科室请购日期" width="130">
                    <template slot-scope="scope">
                        <el-date-picker v-model="scope.row.applyDate" type="date" placeholder="选择日期" size="mini" @change="dataChange($event,scope.row,'applyDate')">
                        </el-date-picker>
                    </template>
                </el-table-column>
                <el-table-column label="预计到货日期" width="130">
                    <template slot-scope="scope">
                        <el-date-picker v-model="scope.row.receiveDate" type="date" placeholder="选择日期" size="mini" @change="dataChange($event,scope.row,'receiveDate')">
                        </el-date-picker>
                    </template>
                </el-table-column>

            </el-table>
            <el-col :span="24" class="toolbar" style="margin-top:10px; padding-bottom: 0px;">
                <div style="margin-left:100px;text-align: center;">
                    <el-button :plain="true" type="success"  @click="submitPurchase('20')">提交</el-button>
                    <el-button :plain="true" type="warning"  @click="submitPurchase('10')">保存</el-button>
                    <el-button :plain="true" type="danger"  @click="cancel">取消</el-button>
                </div>
            </el-col>

            <el-dialog title="请选择商品" @close="onClose" :visible.sync="goodsListVisible" :close-on-click-modal="false" >
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form>
                        <el-col :span="12">
                            <el-form-item label="">
                                <el-input placeholder="商品名称、拼音缩写" v-model="filters.goodsName"  class="input-search">
                                    <el-button slot="append" icon="search" v-on:click="getGoodsInfo(1)"></el-button>
                                </el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="请购科室">
                                <el-cascader :options="orgInfo"  v-model="deptIdTmp" :props="orgProps" :show-all-levels="false" :change-on-select="true">
                                </el-cascader>
                            </el-form-item>
                        </el-col>
                    </el-form>
                </el-col>

                <el-table :data="goodsList" ref="sltGoodsT" highlight-current-row v-loading="loading" style="width: 100%;min-height:300px;" fit max-height="300" border row-key="id" @selection-change="goodsSltChange">
                    <el-table-column type="selection" :reserve-selection="true" width="30">
                    </el-table-column>
                    <el-table-column prop="hisCode" label="编码" show-overflow-tooltip width="100">
                    </el-table-column>
                    <el-table-column prop="goodsName" label="名称" width="150" sortable show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column prop="purPrice" label="单价" width="60">
                    </el-table-column>
                    <el-table-column prop="packeage" label="包装" width="60">
                    </el-table-column>
                    <el-table-column prop="goodsGg" label="规格" width="100" show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column prop="hisUnit" label="单位" width="100" show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column prop="mfrsName" label="生产厂家" show-overflow-tooltip>
                    </el-table-column>
                </el-table>
                <el-col :span="24" class="toolbar">
                    <el-pagination @size-change="SizeChange" @current-change="CurrentChange" :current-page="goodsPage.page" :page-sizes="[15,30, 50]" :page-size="goodsPage.pageSize" layout="total, sizes, prev, pager, next" :total="goodsPage.total" style="float:right;" small>
                    </el-pagination>
                </el-col>
                <div slot="footer" class="dialog-footer">
                    <el-button @click.native="goodsListVisible = false" >取消</el-button>
                    <el-button type="primary" @click.native="getSltGoods" >确定</el-button>
                </div>
            </el-dialog>

        </el-col>
    </el-row>
</template>
<script>
import util from "../../common/js/util.js";
import orgTreeSlt from '../../components/orgTreeSlt';
import { mapGetters } from 'vuex';
var Child =  {
  template: '<div><p v-for="item in errMsg" v-html="item"></p></div>',
  props:["errMsg"]
};
export default {
    data() {
        return {
            pageLoading: false,
            opModule: '',
            purOrder: {
                id: '',
                billId: '',
                purchaseComId: '',
                purchaseComName: '',
                purDeptId: '',
                recOrgId: '',
                recOrgCode: '',
                recOrgName: '',
                purKind:0,
                purType:10,
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
                recAddressId: '',
                recLinkman: '',
                recLinkmanPhone: '',
                recAddress: '',
                fillDate: '',
                lastUpdateDt: '',
                version: '',
                purComCode: '',
                purComErpCode: '',
                purOrgCode: '',
                purOrgCname: '',
                purOrgErpCode: '',
                purOrgScmCode: '',
                recOrgErpCode: '',
                recOrgScmCode: '',
                provCode: '',
                provErpCode: '',
                purchaseList: []
            },
            purDetail: {
                id: '',
                pid: '',
                billId: '',
                rowNumber: 0,
                applyDeptId: '',
                applyDeptCode: '',
                applyDeptName: '',
                applyBillId: '',
                applyRowNum: 0,
                hosGoodsId: '',
                hosGoodsCode: '',
                hosGoodsName: '',
                hosPrice: 0,
                hosUnit: '',
                hosScale: 1,
                packetCode:0,
                hosApplyQty: 0,
                applyQty: 0,
                hosPurQty: 0,
                purQty: 0,
                unitQty: 0,
                qty: 0,
                purTax: 0.17,           // 税率
                purTaxPrice: 0,      // 采购不含税单价
                purTaxAmount: 0,     //采购不含税金额
                purAmount: 0,        //采购含税金额
                purTaxMoney: 0,      //税额
                status: 0,
                provId: '',
                applyDate: '',
                receiveDate: '',
                version: ''
            },
            filters: {
                goodsName: '',
                code: '',
                provId: '',
                prov: {
                    name: ''
                }
            },
            goodsPage: {
                total: 0,
                page: 1,
                pageSize: 15,
            },
            orgProps: {
                value: "code",
                label: "label"
            },
            orgInfo: [],
            loading: false,
            goodsList: [],              //商品列表
            goodsListVisible: false,
            sltGoodsList: [],           //已选中商品
            ProvsInfo: [],              //供应商列表
            //filterProvs: []               //过滤后的供应商列表
        }
    },
    components:{orgTreeSlt},
    computed: {
        ...mapGetters(["getLoginUser"]),
        purDept: {
            get: function () {
                var res = [];
                if (this.purOrder.purOrgCode) {
                    let path = this.purOrder.purOrgCode.split('.');
                    if (path.length > 1) {
                        var i = 0;
                        for (i; i < path.length; i++) {
                            let tempArr = path.slice(0, i + 1);
                            res.push(tempArr.join('.'));
                        }
                    } else {
                        res = path;
                    }
                }
                return res;
            },
            set: function (pathArr) {
                let res = { purDeptId: '', purDeptCode: '', purDeptName: '' };
                if (pathArr.length > 0) {
                    //res = pathArr[pathArr.length - 1];
                    var tmpArr = this.orgInfo.concat();
                    for (var i = 0; i < pathArr.length; i++) {
                        if (tmpArr) {
                            var node = tmpArr.find(item => item.code == pathArr[i]);
                            if (node) {
                                tmpArr = node.children;
                                res.purDeptCode = node.code;
                                res.purDeptId = node.id;
                                res.purDeptName = node.label;
                            }
                        }
                    }
                }
                this.purOrder.purDeptId = res.purDeptId;
                this.purOrder.purOrgCode = res.purDeptCode;
                this.purOrder.purOrgCname = res.purDeptName;
            }
        },
        recDept: {
            get: function () {
                var res = [];
                if (this.purOrder.recOrgCode) {
                    let path = this.purOrder.recOrgCode.split('.');
                    if (path.length > 1) {
                        var i = 0;
                        for (i; i < path.length; i++) {
                            let tempArr = path.slice(0, i + 1);
                            res.push(tempArr.join('.'));
                        }
                    } else {
                        res = path;
                    }
                }
                return res;
            },
            set: function (pathArr) {
                let res = { applyDeptId: '', applyDeptCode: '', applyDeptName: '' };
                if (pathArr.length > 0) {
                    //res = pathArr[pathArr.length - 1];
                    var tmpArr = this.orgInfo.concat();
                    for (var i = 0; i < pathArr.length; i++) {
                        if (tmpArr) {
                            var node = tmpArr.find(item => item.code == pathArr[i]);
                            if (node) {
                                tmpArr = node.children;
                                res.applyDeptCode = node.code;
                                res.applyDeptId = node.id;
                                res.applyDeptName = node.label;
                            }
                        }
                    }
                }
                this.purOrder.recOrgId = res.applyDeptId;
                this.purOrder.recOrgCode = res.applyDeptCode;
                this.purOrder.recOrgName = res.applyDeptName;
            }
        },
        deptIdTmp: {
            get: function () {
                let res = [];
                if (this.purDetail.applyDeptCode) {
                    let path = this.purDetail.applyDeptCode.split('.');
                    if (path.length > 1) {
                        var i = 0;
                        for (i; i < path.length; i++) {
                            let tempArr = path.slice(0, i + 1);
                            res.push(tempArr.join('.'));
                        }
                    } else {
                        res = path;
                    }
                }
                return res;
            },
            set: function (pathArr) {
                let res = { applyDeptId: '', applyDeptCode: '', applyDeptName: '' };
                if (pathArr.length > 0) {
                    //res = pathArr[pathArr.length - 1];
                    var tmpArr = this.orgInfo.concat();
                    for (var i = 0; i < pathArr.length; i++) {
                        if (tmpArr) {
                            var node = tmpArr.find(item => item.code == pathArr[i]);
                            if (node) {
                                tmpArr = node.children;
                                res.applyDeptCode = node.code;
                                res.applyDeptId = node.id;
                                res.applyDeptName = node.label;
                            }
                        }
                    }
                }
                this.purDetail.applyDeptId = res.applyDeptId;
                this.purDetail.applyDeptCode = res.applyDeptCode;
                this.purDetail.applyDeptName = res.applyDeptName;
            }
        }
    },
    methods: {
        setAddress(d) {
            this.purOrder.recAddressId = d.id;
            this.purOrder.recLinkman = d.lxr;
            this.purOrder.recLinkmanPhone = d.lxrPhone;
            this.purOrder.recAddress = d.province + d.city + d.area + d.address;
        },
        addManyRow() {
            if (!this.purOrder.provId) {
                this.$message.error('请选择供应商!');
                return;
            } else {
                this.filters.provId = this.purOrder.provId;
            }
            this.goodsListVisible = true;
            this.getGoodsInfo(1);
        },
        getGoodsInfo(pIndex) {
            this.goodsListVisible = true;
            this.goodsPage.page = pIndex;
            var param = {
                orderBy: "",
                page: this.goodsPage.page,
                rows: this.goodsPage.pageSize,
                queryObject: {
                    goodsName: this.filters.goodsName,
                    shortPinyin: this.filters.goodsName,
                    code: this.filters.code,
                    provId: this.filters.provId,
                    isHisCodeNotNull: '1'
                }
            };
            this.loading = true;
            this.axios.post('/spdHDIService/myGoods/provGoodsHos/getProHosGoodsList', param).then(res => {
                this.loading = false;
                if (res.data.code == "0") {
                    this.loading = false;
                    this.goodsList = res.data.data.data;
                    this.goodsPage.total = res.data.data.total;
                }
            }, err => {
                this.loading = false;
            });

        },
        delRow(pd) {
            var index = this.purOrder.purchaseList.findIndex(item => { return item.rowNumber == pd.rowNumber });
            this.purOrder.purchaseList.splice(index, 1);
            this.purOrder.purchaseList.forEach((item, i) => item.rowNumber = i + 1);
        },
        onClose() {
            this.$refs.sltGoodsT.clearSelection(this.sltGoodsList);
            this.sltGoodsList = [];
            this.purDetail.applyDeptId = "";
            this.purDetail.applyDeptCode = "";
            this.purDetail.applyDeptName = "";
        },
        CurrentChange(val) {
            this.goodsPage.page = val;
            this.getGoodsInfo(this.goodsPage.page);
        },
        SizeChange(val) {
            this.goodsPage.pageSize = val;
            this.getGoodsInfo(this.goodsPage.page);
        },
        goodsSltChange(sltedRows) {
            this.sltGoodsList = sltedRows;
        },
        getSltGoods() {
            if (this.purDetail.applyDeptId == "") {
                this.$message.error('请选择科室!');
                return;
            }
            if (this.sltGoodsList.length > 0) {
                var date = new Date();
                var applyDate = util.formatDate.format(date, "yyyy-MM-dd");
                date.setDate(date.getDate() + 3);
                var receiveDate = util.formatDate.format(date, "yyyy-MM-dd");
                this.sltGoodsList.forEach(item => {
                    let row = Object.assign({}, this.purDetail);
                    row.hosGoodsCode = item.hisCode;
                    row.hosGoodsId = item.id;
                    row.hosGoodsName = item.hisName;
                    row.hosPrice = item.purPrice || 0;
                    row.purTax = item.taxRate || 1;
                    row.hosScale = item.unitRate || 1;
                    row.hosUnit = item.hisUnit;
                    row.provId = this.purOrder.provId;
                    row.applyDate = applyDate;
                    row.receiveDate = receiveDate;
                    row.packetCode = item.packeage || 0;
                    row.uniqueKind = item.uniqueCodeStrategy;
                    row.purTaxPrice = (row.hosPrice / (1 + row.purTax)).toFixed(6);
                    this.purOrder.purchaseList.push(row);
                    row.rowNumber = this.purOrder.purchaseList.length;
                });
                this.goodsListVisible = false;
            } else {
                this.$message.error('请选择商品!');
            }
        },
        dataChange(val, row, att) {
            if (att == "applyDate") {
                var date = new Date(val);
                date.setDate(date.getDate() + 3);
                row.receiveDate = date.toDateString();
            }
            row[att] = val;
        },
        qtyChange(val, row, flag) {
            if (flag === true) {
                row.hosApplyQty = val;
            }
            row.hosPurQty = val;
            row.purQty = row.hosPurQty * row.hosScale;
            row.applyQty = row.hosApplyQty * row.hosScale;
            row.purAmount = (row.hosPrice * row.hosPurQty).toFixed(2);
            row.purTaxAmount = (row.purTaxPrice * row.hosPurQty).toFixed(2);
            this.setAmount();
        },
        setAmount(){
            var sumAmount=0,sumTaxAmount=0;
            this.purOrder.purchaseList.forEach((item,index)=>{
                sumTaxAmount += Number(item.purTaxAmount);
                sumAmount += Number(item.purAmount);
            });
            this.purOrder.sumAmount = sumAmount.toFixed(2);
            this.purOrder.sumTaxAmount = sumTaxAmount.toFixed(2);
        },
        getOrg() {
            var params = {};
            return this.axios.post('/platformService/sys/org/getOrgsByUnit', params);
        },
        getProv() {
            //var params = { kind: "2" };
            //return this.axios.post('/platformService/bas/company/companysList', params);
            var param = { orderBy: "", queryObject: {}, page: 1, rows: 200 };
            return this.axios.post('/spdHDIService/provManager/hosProvInfo/hosProvInfoVoList', param);
        },
        cancel() {
            this.$router.push("purchase");
        },
        submitPurchase(status) {
            var _this = this;
            const h = _this.$createElement;
            this.pageLoading = true;
            this.purOrder.status = status;
            this.setAmount();
            var params = this.purOrder;
            var serviceUrl = this.opModule == "ADD" ? '/spdHDIService/order/purchase/insertPurchase' : '/spdHDIService/order/purchase/updatePurchase';
            this.axios.post(serviceUrl, params).then(res => {
                _this.pageLoading = false;
                if (res.data.code == "0") {
                    var billId = res.data.data.billId;
                    _this.$msgbox({
                        title: "提示",
                        message: "操作成功",
                        type: "success",
                        callback: action => {
                            _this.$router.push({ path: 'purchase', query: { billId: billId } });
                        }
                    });
                } else if (res.data.code == "-122"){
                    var msg = res.data.validateErrors.fieldErrors.map(x=>x.message);
                    _this.$msgbox({
                        title:"错误",type:"error",
                        message:h(Child,{props:{errMsg:msg}})
                    });
                }else{
                    _this.$msgbox({ title: "提示", message: "操作失败", type: "error" });
                }
            }, err => {
                _this.pageLoading = false;
                _this.$msgbox({
                    title: "提示",
                    message: "操作失败",
                    type: "error"
                });
            });
        },
        getPurchaseInfo(billId) {
            return this.axios.get('/spdHDIService/order/purchase/getPurchase/' + billId);
        }
    },
    mounted() {
        var _this = this;
        this.pageLoading = true;
        var billId = _this.$route.query.billId;
        var Methods = [_this.getOrg(), _this.getProv()];
        if (billId) {
            _this.opModule = "EDIT";
            Methods.push(_this.getPurchaseInfo(billId));
        } else {
            _this.opModule = "ADD";
            _this.purOrder.fillterName = _this.getLoginUser.ename;
            _this.purOrder.fillter = _this.getLoginUser.userId;
            _this.purOrder.purchaseComId = _this.getLoginUser.corpId;
            _this.purOrder.purchaseComName = _this.getLoginUser.corpName;
            _this.purOrder.purDeptId = _this.getLoginUser.orgId;
            _this.purOrder.purOrgCode = _this.getLoginUser.orgCode;
            _this.purOrder.purOrgCname = _this.getLoginUser.orgName;
            _this.purOrder.recOrgName = _this.getLoginUser.orgName;
            _this.purOrder.recOrgId = _this.getLoginUser.orgId;
            _this.purOrder.recOrgCode = _this.getLoginUser.orgCode;
            _this.purOrder.fillDate = util.formatDate.format(new Date(), "yyyy-MM-dd");
        }
        _this.axios.all(Methods).then(_this.axios.spread(function (orgInfo, provInfo, purchase) {
            _this.pageLoading = false;
            if (orgInfo.data.code == "0") {
                _this.orgInfo = orgInfo.data.data.children[0].children;
            }
            if (provInfo.data.code == "0") {
                _this.ProvsInfo = provInfo.data.data.data;
                //_this.filterProvs = _this.ProvsInfo;
            }
            if (purchase && purchase.data.code == "0") {
                _this.purOrder = purchase.data.data;
            }
        }), err => {
            _this.pageLoading = false;
            this.$message.error('页面初始化失败,请刷新重试或联系管理员');
        });
    }
};

</script>

<style scoped>
.table_content{
    font-size: 12px;
    height: calc(100% - 180px);
    overflow-y: auto;
}
.el-dialog--small {
    width: 60%;
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

.provListCls {
    margin-top: 8px;
    padding: 3px;
    height: 86%;
    overflow: auto;
    background-color: #fff;
}

.provListCls ul {
    list-style: none;
    margin: 0px;
    padding: 0;
}

.provListCls ul li {
    cursor: pointer;
}
</style>
