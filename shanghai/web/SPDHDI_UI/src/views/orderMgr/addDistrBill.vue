<template>
    <el-row id="add-distr-bill" :gutter="10" v-loading="pageLoading">
        <el-col :span="24">
            <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                <el-form label-width="120px" class="orderInput">
                    <el-col :span="8">
                        <el-form-item label="请购机构" :error="errs.purComId">
                            <el-select v-model="distrBill.purchaseCompanyId" filterable placeholder="请选择" @change="purComChange" >
                                <el-option v-for="item in ProvsInfo" :key="item.id" :label="item.cname" :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="请购科室" :error="errs.applyOrgId">
                            <el-cascader :options="orgInfo"  v-model="purDept" :loading="orgLoading" :props="orgProps" :show-all-levels="false" :change-on-select="true">
                            </el-cascader>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="收货科室" :error="errs.recOrgId">
                            <el-cascader :options="orgInfo"  v-model="recDept" :props="orgProps" :show-all-levels="false" :change-on-select="true">
                            </el-cascader>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="配送机构">
                            <el-input v-model="distrBill.provName" placeholder="配送机构" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="制单时间">
                            <el-date-picker v-model="distrBill.fillDate" type="date" readonly placeholder="选择日期" >
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="制单人">
                            <el-input v-model="distrBill.fillterName" placeholder="制单人" :default-value="new Date()" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="23">
                        <el-form-item label="备注">
                            <el-input type="textarea" v-model="distrBill.remark"></el-input>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-col>
            <!--列表-->
            <template>
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-button-group>
                        <el-button type="primary" class="el-icon-plus" size="mini" @click.native="addManyRow">添加行</el-button>
                    </el-button-group>
                </el-col>
                <el-table :data="distrBill.distrBillList" style="width: 100%;" fit height="300" border>
                    <el-table-column label="操作" width="100">
                        <template slot-scope="scope">
                            <div class="icoBtn">
                                <i class="fa fa-edit fa-lg" @click="editOthers(scope.row)" title="编辑其他信息"></i>
                                <i class="fa fa-barcode fa-lg" @click="editBarcode(scope.row)" title="编辑条码"></i>
                                <i class="fa fa-thermometer fa-lg" @click="editHumiture(scope.row)" title="编辑温湿度"></i>
                                <i class="fa fa-trash-o fa-lg" @click="delRow(scope.row)" title="删除" style="color:red;margin-left:5px;"></i>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="rowNumber" label="序号" width="60">
                    </el-table-column>
                    <el-table-column prop="provGoodsErpName" label="商品名称" width="200">
                    </el-table-column>
                    <el-table-column prop="sendScale" label="转换比">
                    </el-table-column>
                    <el-table-column prop="hosQty" label="请购数量" width="90">
                    </el-table-column>
                    <el-table-column prop="canSendQty" label="可送数量" width="90">
                    </el-table-column>
                    <el-table-column label="配送数量">
                        <template slot-scope="scope">
                            <el-input-number placeholder="请输入数量" v-model="scope.row.sendQty" size="mini" :min="0" :max="scope.row.canSendQty" class="col-input-num" @change="qtyChange($event,scope.row)">
                            </el-input-number>
                        </template>
                    </el-table-column>
                    <el-table-column prop="sendUnit" label="单位">
                    </el-table-column>
                    <el-table-column prop="hosUnit" label="单位">
                    </el-table-column>
                </el-table>
            </template>
            <el-col :span="24" class="toolbar" style="margin-top:10px; padding-bottom: 0px;">
                <div style="margin-left:100px;text-align: center;">
                    <el-button :plain="true" type="success"  @click="submitDistrBill('20')">提交</el-button>
                    <el-button :plain="true" type="warning"  @click="submitDistrBill('10')">保存</el-button>
                    <el-button :plain="true" type="danger"  @click="cancel">取消</el-button>
                </div>
            </el-col>
            <el-dialog title="请选择" @close="onClose" :visible.sync="purconfirmVisible" :close-on-click-modal="false" size="large">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form>
                        <el-col :span="6">
                            <el-form-item label="">
                                <el-input placeholder="确认单号" v-model="filters.billId"  class="input-search">
                                    <el-button slot="append" icon="search" v-on:click="getPurConfirmInfo(1)"></el-button>
                                </el-input>
                            </el-form-item>
                        </el-col>
                    </el-form>
                </el-col>
                <el-table :data="purconfirmList" highlight-current-row v-loading="loading" style="width: 100%;" fit max-height="150" @current-change="curRowChange">
                    <el-table-column prop="billId" label="确认单号" sortable show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column prop="purchaseCompanyName" label="请购机构" sortable>
                    </el-table-column>
                    <el-table-column prop="sumAmount" label="总金额" sortable>
                    </el-table-column>
                    <el-table-column prop="sumTaxAmount" label="不含税金额" show-overflow-tooltip>
                    </el-table-column>
                </el-table>
                <el-col :span="24" class="toolbar">
                    <el-pagination @size-change="SizeChange" @current-change="pageChange" :current-page="pcPage.page" :page-sizes="[15,30, 50]" :page-size="pcPage.pageSize" layout="total, sizes, prev, pager, next" :total="pcPage.total" style="float:right;" small>
                    </el-pagination>
                </el-col>
                <el-table :data="purclstDetail" ref="sltpcT" tooltip-effect="dark" style="width: 100%" border @selection-change="purcSltChange">
                    <el-table-column type="selection" width="55" :selectable="canSlt">
                    </el-table-column>
                    <el-table-column prop="rowNumber" label="序号" width="60">
                    </el-table-column>
                    <el-table-column prop="applyDeptName" label="请购科室" width="150">
                    </el-table-column>
                    <el-table-column prop="hosGoodsName" label="产品名称" width="180" show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column prop="purQty" label="采购数量" width="90">
                    </el-table-column>
                    <el-table-column prop="sendQty" label="已配送数量" width="90">
                    </el-table-column>
                    <el-table-column prop="applyQty" label="可配送数量" width="90">
                        <template slot-scope="scope">
                            {{scope.row.purQty - scope.row.sendQty}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="hosUnit" label="单位">
                    </el-table-column>
                    <el-table-column prop="applyDate" label="科室请购日期" width="120">
                        <template slot-scope="scope">
                            {{scope.row.applyDate | dateFormat('YYYY-MM-DD')}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="receiveDate" label="预计到货日期" width="120">
                        <template slot-scope="scope">
                            {{scope.row.receiveDate | dateFormat('YYYY-MM-DD')}}
                        </template>
                    </el-table-column>
                </el-table>
                <div slot="footer" class="dialog-footer">
                    <el-button @click.native="purconfirmVisible = false" >取消</el-button>
                    <el-button type="primary" @click.native="getSltPurc" >确定</el-button>
                </div>
            </el-dialog>
            <el-dialog title="其他信息设置" :visible.sync="otherV" :close-on-click-modal="false"  @close="distrDetailClose">
                <el-form label-width="120px" class="el-form-item-nomsg">
                    <el-col :span="12">
                        <el-form-item label="销售单号">
                            <el-input v-model="distrDetail.saleBillId" placeholder="销售单号" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="批次号">
                            <el-input v-model="distrDetail.batchNum" placeholder="批次号" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="唯一码管理">
                            <el-switch v-model="distrDetail.isUniqueCode" active-color="#13ce66" inactive-color="#ff4949" :width="70" active-text="启用" inactive-text="不启用" active-value="1" inactive-value="0" >
                            </el-switch>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="批号">
                            <el-input v-model="distrDetail.batchCode" placeholder="批号" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="有效日期">
                            <el-date-picker v-model="distrDetail.expdtEndDate" type="date" placeholder="选择日期" >
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="生产日期">
                            <el-date-picker v-model="distrDetail.productDate" type="date" placeholder="选择日期" >
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="灭菌日期">
                            <el-date-picker v-model="distrDetail.sterilizationDate" placeholder="灭菌批号" ></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="灭菌有效日期">
                            <el-date-picker v-model="distrDetail.sterilizationEndDate" placeholder="灭菌批号" ></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="灭菌批号">
                            <el-input v-model="distrDetail.sterilizationCode" placeholder="灭菌批号" ></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="12">
                        <el-form-item label="出库库区">
                            <el-input v-model="distrDetail.outStorearea" placeholder="出库库区" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="出库类型">
                            <el-input v-model="distrDetail.outKind" placeholder="出库类型" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="换货标识">
                            <el-input v-model="distrDetail.isExchange" placeholder="换货标识" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="定量包装">
                            <el-input-number v-model="distrDetail.packetCode"  :min="0"></el-input-number>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="条码">
                            <el-input v-model="distrDetail.barcode" placeholder="条码" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="送货日期">
                            <el-date-picker v-model="distrDetail.deliveryDate" placeholder="灭菌批号" ></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="说明">
                            <el-input v-model="distrDetail.memo" placeholder="说明" ></el-input>
                        </el-form-item>
                    </el-col>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click.native="otherV = false" >取消</el-button>
                    <el-button type="primary" @click.native="otherV = false" >确定</el-button>
                </div>
            </el-dialog>
            <el-dialog title="条码设置" :visible.sync="barcodeV" :close-on-click-modal="false"  @close="distrDetailClose">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form label-width="120px" class="el-form-item-nomsg">
                        <el-col :span="12">
                            <el-form-item label="商品名称">
                                <el-input v-model="distrUnique.goodsName" :disabled="true" placeholder="商品名称" ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="批次号">
                                <el-input v-model="distrUnique.batchCode" placeholder="批次号" ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="唯一码">
                                <el-input v-model="distrUnique.uniqueCode" placeholder="批次号" ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item>
                                <el-button type="primary" @click.native="addBarCode" >添加</el-button>
                            </el-form-item>
                        </el-col>
                    </el-form>
                </el-col>
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-tag type="primary" v-for="item in distrUniqueList" :key="item.id" :closable="true" :title="item.batchCode" @close="delBarCode(item)">
                        <i class="fa fa-barcode"></i>
                        {{item.uniqueCode}}
                    </el-tag>
                </el-col>
                <div slot="footer" class="dialog-footer">
                    <el-button @click.native="barcodeV = false" >关闭</el-button>
                    <el-button type="primary" @click.native="setBarCode" >确定</el-button>
                </div>
            </el-dialog>
            <el-dialog title="温湿度设置" :visible.sync="humitureV" :close-on-click-modal="false"  @close="distrDetailClose">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form label-width="120px" class="el-form-item-nomsg">
                        <el-col :span="12">
                            <el-form-item label="商品名称">
                                <el-input v-model="distrHumiture.goodsName" placeholder="商品名称" :disabled="true" ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="箱号">
                                <el-input v-model="distrHumiture.boxCode" placeholder="箱号" ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="产品批次">
                                <el-input v-model="distrHumiture.batchCode" placeholder="箱号" ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="唯一码">
                                <el-input v-model="distrHumiture.uniqueCode" placeholder="唯一码" ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="温度">
                                <el-input-number v-model="distrHumiture.temprature" :min="1" :max="100" ></el-input-number>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="湿度">
                                <el-input-number v-model="distrHumiture.humidity" :min="1" :max="100" ></el-input-number>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="测量时间">
                                <el-date-picker v-model="distrHumiture.measureDate" type="datetime" placeholder="选择日期时间" >
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item>
                                <el-button type="primary" @click.native="addHumiture" >添加</el-button>
                            </el-form-item>
                        </el-col>
                    </el-form>
                </el-col>
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-tag type="primary" v-for="item in distrHumitureList" :key="item.id" :closable="true" :title="item.measureDate.toLocaleString()" @close="delHumiture(item)">
                        <i class="fa fa-thermometer"></i>
                        {{item.temprature}}℃ / {{item.humidity}}%
                    </el-tag>
                </el-col>
                <div slot="footer" class="dialog-footer">
                    <el-button @click.native="humitureV = false" >关闭</el-button>
                    <el-button type="primary" @click.native="setHumiture" >确定</el-button>
                </div>
            </el-dialog>
        </el-col>
    </el-row>
</template>
<script>
import moment from 'moment';
import { mapGetters } from 'vuex';
export default {
    data() {
        return {
            pageLoading: false,
            opModule: '',
            orgLoading: false,
            errs: {
                purComId: '',
                applyOrgId: '',
                recOrgId: ''
            },
            distrBill: {
                id: '',
                billId: '',
                spdCode: '',
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
                fillter: '',
                remark: '',
                sumRow: '',
                status: '',
                fillDate: '',
                version: '',
                distrBillList: []
            },
            constDistrDetail: {
                id: '',
                pid: '',
                billId: '',
                rowNumber: '',
                purconfirmBillId: '',
                purconfirmRowNumber: '',
                purBillId: '',
                purRowNumber: '',
                purBillDate: '',
                applyBillId: '',
                saleBillId: '',
                deliveryDate: new Date(),
                provGoodsId: '',
                goodsGg: '',
                goodsMrfsId: '',
                made: '',
                hosGoodsId: '',
                hosGoodsCode: '',
                hosGoodsName: '',
                provGoodsErpCode: '',
                provGoodsErpName: '',
                batchNum: '',
                isUniqueCode: "1",
                batchCode: '',
                sterilizationCode: '',
                sterilizationEndDate: new Date(),
                sterilizationDate: new Date(),
                expdtEndDate: new Date(),
                productDate: new Date(),
                sendUnit: '',
                sendScale: '',
                canSendQty: '',
                sendQty: '',
                sendPrice: '',
                hosUnit: '',
                hosQty: '',
                hosPrice: '',
                outStorearea: '',
                outKind: '',
                sumRow: '',
                memo: '',
                barcode: '',
                packetCode: '',
                sourceData: 0,
                isExchange: '',
                version: '',
                distrBillUniqueList: [],
                distrBillHumitureList: []
            },
            otherV: false,
            barcodeV: false,
            humitureV: false,
            distrDetail: {
                distrBillUniqueList: [],
                distrBillHumitureList: []
            },
            distrUnique: {
                id: '',
                pid: '',
                distrRowNumber: 0,
                provGoodsId: '',
                goodsName: '',
                batchCode: '',
                uniqueCode: '',
                sourceData: 0,
                version: 0
            },
            distrHumiture: {
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
                sourceData: 0,
                version: 0
            },
            distrUniqueList: [],
            distrHumitureList: [],
            filters: {
                billId: ''
            },
            pcPage: {
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
            purconfirmList: [],              //请购确认单列表
            purconfirmVisible: false,
            tempPurconfirm: null,
            sltedPurconfirm: [],               //当前已选中确认单
            purclstDetail: [],
            ProvsInfo: [],                  //请购机构列表
            filterProvs: []               //过滤后的供应商列表
        }
    },
    computed: {
        ...mapGetters(["getLoginUser"]),
        purDept: {
            get: function () {
                var res = [];
                if (this.distrBill.applyOrgCode) {
                    let path = this.distrBill.applyOrgCode.split('.');
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
                let res = { purDeptId: '', purDeptCode: '', purDeptName: '', purDeptScmCode: '', purDeptErpCode: '' };
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
                                res.applyOrgScmCode = node.ob.scmCode;
                                res.applyOrgErpCode = node.ob.erpCode;
                            }
                        }
                    }
                }
                this.distrBill.applyOrgId = res.purDeptId;
                this.distrBill.applyOrgCode = res.purDeptCode;
                this.distrBill.applyOrgName = res.purDeptName;
                this.distrBill.applyOrgScmCode = res.applyOrgScmCode;
                this.distrBill.applyOrgErpCode = res.applyOrgErpCode;
            }
        },
        recDept: {
            get: function () {
                var res = [];
                if (this.distrBill.recieveOrgCode) {
                    let path = this.distrBill.recieveOrgCode.split('.');
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
                let res = { applyDeptId: '', applyDeptCode: '', applyDeptName: '', applyOrgScmCode: '', applyOrgErpCode: '' };
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
                                res.applyOrgScmCode = node.ob.scmCode;
                                res.applyOrgErpCode = res.applyOrgErpCode;


                            }
                        }
                    }
                }
                this.distrBill.recieveOrgId = res.applyDeptId;
                this.distrBill.recieveOrgCode = res.applyDeptCode;
                this.distrBill.recieveOrgName = res.applyDeptName;
                this.distrBill.recieveOrgScmCode = res.applyOrgScmCode;
                this.distrBill.recieveOrgErpCode = res.applyOrgErpCode;
            }
        }
    },
    methods: {
        addManyRow() {
            var flag = true;
            if (!this.distrBill.purchaseCompanyId) {
                this.errs.purComId = "请选择请购机构";
                flag = false;
            } else {
                this.errs.purComId = "";
            }
            if (!this.distrBill.applyOrgId) {
                this.errs.applyOrgId = "请选择请购机构";
                flag = false;
            } else {
                this.errs.applyOrgId = "";
            }
            if (!this.distrBill.recieveOrgId) {
                this.errs.recOrgId = "请选择请购机构";
                flag = false;
            } else {
                this.errs.recOrgId = "";
            }
            if (!flag) {
                return;
            }
            this.purconfirmVisible = true;
            this.getPurConfirmInfo(1);
        },
        getPurConfirmInfo(pIndex) {//获取确认单信息
            this.purconfirmVisible = true;
            this.pcPage.page = pIndex;
            var param = {
                page: this.pcPage.page,
                rows: this.pcPage.pageSize,
                queryObject: {
                    purchaseCompanyId: this.distrBill.purchaseCompanyId,
                    applyOrgId: this.distrBill.applyOrgId,
                    billId: this.filters.billId,
                    recieveOrgId: this.distrBill.recieveOrgId
                }
            };
            this.loading = true;
            this.axios.post('/spdHDIService/orderMgr/purconfirm/listByPage', param).then(res => {
                this.loading = false;
                if (res.data.code == "0") {
                    this.loading = false;
                    this.purconfirmList = res.data.data.data;
                    this.pcPage.total = res.data.data.total;
                }
            }, err => {
                this.loading = false;
            });

        },
        delRow(pd) {
            var index = this.distrBill.distrBillList.findIndex(item => { return item.rowNumber == pd.rowNumber });
            this.distrBill.distrBillList.splice(index, 1);
            this.distrBill.distrBillList.forEach((item, i) => item.rowNumber = i + 1);
        },
        purComChange(comId) {
            var purCom = this.ProvsInfo.find(item => item.id == comId);
            this.distrBill.purchaseCompanyCode = purCom.code;
            this.distrBill.purchaseCompanyErpCode = purCom.erpCode;
            this.distrBill.purchaseCompanyName = purCom.cname;
            this.orgInfo = [];
            this.getOrg(this.distrBill.purchaseCompanyId);
        },
        onClose() {
            this.$refs.sltpcT.clearSelection();
            this.sltedPurconfirm = [];
            this.purclstDetail = [];
        },
        pageChange(val) {
            this.pcPage.page = val;
            this.getPurConfirmInfo(this.pcPage.page);
        },
        SizeChange(val) {
            this.pcPage.pageSize = val;
            this.getPurConfirmInfo(this.pcPage.page);
        },
        purcSltChange(sltedRows) {
            this.sltedPurconfirm = sltedRows;
        },
        getSltPurc() {
            if (this.sltedPurconfirm.length > 0) {
                this.sltedPurconfirm.forEach(item => {
                    let row = Object.assign({}, this.constDistrDetail);
                    row.purconfirmBillId = item.billId;
                    row.purconfirmRowNumber = item.rowNumber;
                    row.purBillId = item.purBillId;
                    row.purRowNumber = item.purBillRownum;
                    row.purBillDate = item.purBillDate;
                    row.applyBillId = item.applyBillId;
                    //row.saleBillId = item.saleBillId;
                    row.provGoodsId = item.provGoodsId;
                    row.goodsGg = item.goodsGg;
                    row.goodsMrfsId = item.goodsMrfsId;
                    row.hosPrice = item.hosPrice;
                    row.made = item.made;
                    row.hosGoodsId = item.hosGoodsId;
                    row.hosGoodsCode = item.hosGoodsCode;
                    row.hosGoodsName = item.hosGoodsName;
                    row.provGoodsErpCode = item.provGoodsErpCode;
                    row.provGoodsErpName = item.provGoodsErpName;
                    row.hosUnit = item.hosUnit;
                    row.hosQty = item.applyQty;
                    row.canSendQty = item.applyQty - item.sendQty;
                    row.sendQty = row.hosQty;
                    row.sendScale = item.hosScale;
                    row.sendUnit = item.unit;
                    row.sendPrice = item.provGoodsErpPrice;
                    this.distrBill.distrBillList.push(row);
                    row.rowNumber = this.distrBill.distrBillList.length;
                });
                this.purconfirmVisible = false;
            } else {
                this.$message.error('请至少选择一条记录!');
            }
        },
        canSlt(row) {
            if (this.distrBill.distrBillList.length > 0) {
                return this.distrBill.distrBillList.findIndex(item => item.purconfirmBillId == row.billId && item.purconfirmRowNumber == row.rowNumber) < 0;
            } else {
                return true;
            }
        },
        qtyChange(val, row) {
            // row.sendQty = val;
            // row.purAmount = (row.hosPrice * row.sendQty).toFixed(2);
            // row.purTaxAmount = (row.purTaxPrice * row.sendQty).toFixed(2);
        },
        getOrg(comId) {
            var _this = this;
            _this.orgLoading = true;
            return this.axios.post('/platformService/sys/org/getOrgsByUnit', { corpId: comId }).then(res => {
                _this.orgLoading = false;
                if (res.data.code == "0") {
                    if (res.data.data.children && res.data.data.children.length > 0 && res.data.data.children[0].children) {
                        _this.orgInfo = res.data.data.children[0].children;
                    }
                }
            }, err => { _this.orgLoading = false; });
        },
        getPurCom() {
            var params = { kind: "1" };
            return this.axios.post('/platformService/bas/company/companysList', params);
        },
        cancel() {
            this.$router.go(-1);
        },
        submitDistrBill(status) {
            this.pageLoading = true;
            this.distrBill.status = status;
            var params = this.distrBill;
            var serviceUrl = this.opModule == "ADD" ? '/spdHDIService/orderMgr/distrBill/add' : '/spdHDIService/order/purchase/update';
            this.axios.post(serviceUrl, params).then(res => {
                this.pageLoading = false;
                if (res.data.code == "0") {
                    var billId = res.data.data.billId;
                    this.$msgbox({
                        title: "提示",
                        message: "操作成功",
                        type: "success",
                        callback: action => {
                            this.$router.push({ path: 'distrBillList', query: { billId: billId } });
                        }
                    });
                } else if (res.data.code == "-122") {
                    var msg = res.data.validateErrors.fieldErrors[0].message;
                    this.$msgbox({ title: "验证失败", message: msg, type: "error" });
                } else {
                    this.$msgbox({ title: "操作失败", message: msg, type: "error" });
                }
            }, err => {
                this.pageLoading = false;
                this.$msgbox({
                    title: "提示",
                    message: "操作失败",
                    type: "error"
                });
            });
        },
        getDistrBill(billId) {
            return this.axios.get('/spdHDIService/orderMgr/distrBill/distrBillByBillId/' + billId);
        },
        getPurcDetailInfo(billId) {
            return this.axios.get('/spdHDIService/orderMgr/purconfirm/purConfirmDetail/' + billId);
        },
        curRowChange(row) {
            if (row == null) {
                return;
            }
            this.tempPurconfirm = row;
            if (row.lstDetail != null && row.lstDetail.length > 0) {
                this.purclstDetail = row.lstDetail;
            } else {
                this.getPurcDetailInfo(row.billId).then(res => {
                    row.lstDetail = res.data.data;
                    this.purclstDetail = row.lstDetail;
                });

            }

        },
        editBarcode(row) {
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
        addBarCode() {
            var barCode = Object.assign({}, this.distrUnique);
            this.distrUniqueList.push(barCode);
        },
        delBarCode(barcode) {
            var index = this.distrUniqueList.findIndex(item => item.batchCode == barcode.batchCode && item.uniqueCode == barcode.uniqueCode)
            this.distrUniqueList.splice(index, 1);
        },
        setBarCode() {
            var barCode = Object.assign({}, this.distrUnique);
            var row = this.distrBill.distrBillList.find(item => item.rowNumber == barCode.distrRowNumber);
            row.distrBillUniqueList = this.distrUniqueList;
            this.barcodeV = false;
        },
        editHumiture(row) {
            this.humitureV = true;
            this.distrDetail = Object.assign({}, row);
            this.distrHumitureList = Object.assign([], this.distrDetail.distrBillHumitureList);
            this.distrHumiture = {
                id: '',
                pid: '',
                distrRowNumber: row.rowNumber,
                boxCode: '',
                provGoodsId: row.provGoodsId,
                goodsName: row.provGoodsErpName,
                batchCode: '',
                uniqueCode: '',
                temprature: '',
                humidity: '',
                measureDate: '',
                sourceData: 0,
                version: 0
            }
        },
        addHumiture() {
            var humiture = Object.assign({}, this.distrHumiture);
            this.distrHumitureList.push(humiture);
        },
        delHumiture(humiture) {
            // var index = this.distrUniqueList.findIndex(item=>item.batchCode==barcode.batchCode && item.uniqueCode==barcode.uniqueCode)
            // this.distrUniqueList.splice(index,1);
        },
        setHumiture() {
            var humiture = Object.assign({}, this.distrHumiture);
            var row = this.distrBill.distrBillList.find(item => item.rowNumber == humiture.distrRowNumber);
            row.distrBillHumitureList = this.distrHumitureList;
            this.humitureV = false;
        },
        distrDetailClose() {
            this.distrDetail = {};
            this.distrUnique = {};
            this.distrUniqueList = [];
        },
        editOthers(row) {
            this.otherV = true;
            this.distrDetail = row;//Object.assign({}, row);
        }
    },
    mounted() {
        var _this = this;
        this.pageLoading = true;
        var billId = _this.$route.query.billId;
        var Methods = [_this.getPurCom()];
        if (billId) {
            _this.opModule = "EDIT";
            Methods.push(_this.getDistrBill(billId));
        } else {
            _this.opModule = "ADD";
            _this.distrBill.fillterName = _this.getLoginUser.ename;
            _this.distrBill.fillter = _this.getLoginUser.userId;
            _this.distrBill.provId = _this.getLoginUser.corpId;
            _this.distrBill.provCode = _this.getLoginUser.corpCode;
            _this.distrBill.provName = _this.getLoginUser.corpName;
            _this.distrBill.fillDate = moment(new Date()).format("YYYY-MM-DD");
        }
        _this.axios.all(Methods).then(_this.axios.spread(function (provInfo, purchase) {
            _this.pageLoading = false;
            // if (orgInfo.data.code == "0") {
            //     _this.orgInfo = orgInfo.data.data.children[0].children;
            // }
            if (provInfo.data.code == "0") {
                _this.ProvsInfo = provInfo.data.data;
                _this.filterProvs = _this.ProvsInfo;
            }
            if (purchase && purchase.data.code == "0") {
                _this.distrBill = purchase.data.data;
            }
        }), err => {
            _this.pageLoading = false;
            this.$message.error('页面初始化失败,请刷新重试或联系管理员');
        });
    }
};

</script>

<style scoped>
.el-table {
    font-size: 12px;
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
