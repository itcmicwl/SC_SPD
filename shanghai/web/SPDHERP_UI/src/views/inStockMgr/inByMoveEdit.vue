<template>
    <el-row id="in-by-move-edit" v-loading="diaLoging">
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form label-width="120px">
                <el-col :span="8">
                    <el-form-item label="出库单号:">
                        <span>{{ inStockBill.outBillId }}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="医院：">
                        <span>{{ inStockBill.outOrgName }}</span>
                    </el-form-item>
                </el-col>                
                <el-col :span="8">
                   <el-form-item label="入库库房:">
                        <Stock4Slt v-model="inStockBill.inStocId" :hosId="user.corpId" :deptId='user.orgId'></Stock4Slt>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="入库部门:">
                        <span>{{ inStockBill.inDeptName }}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="制单时间:">
                        <span>{{ inStockBill.fillDate | dateFormat('YYYY-MM-DD')}}</span>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="制单人:">
                        <span>{{ inStockBill.fillterName }}</span>
                    </el-form-item>
                </el-col>
              
            </el-form>
        </el-col>
        <el-col :span="2" class="toolbar" style="padding-bottom: 0px;">
            <el-switch v-model="barcodeType" active-text="仅主码" inactive-text="主副码" :width="60" @change="barcodeTypeChange">
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
        <el-table ref="receListTable" :data="inStockBill.lstDetail" style="width: 100%;" fit height="300" stripe border @selection-change="sltRowsChange" row-key="inBillRow">
            <el-table-column type="selection" width="30">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <div class="icoBtn">
                        <i class="fa fa-trash-o fa-lg" @click="delRow(scope.row)" title="删除" style="color:red;margin-left:5px;"></i>
                        <i class="fa fa-barcode fa-lg" @click="editBarcode(scope.row)" title="编辑条码" v-if="scope.row.uniqueKind<3"></i>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="序号" prop="inBillRow" width="60">
            </el-table-column>
            <el-table-column label="产品名称" prop="goodsName" width="180" show-overflow-tooltip>
            </el-table-column>
            <el-table-column label="出库数量" prop="outQty" width="90">
            </el-table-column>
            <el-table-column label="出库包数" prop="outPacketQty" width="90">
            </el-table-column>
              <el-table-column label="是否定数包管理" prop="isPacket"  :formatter="isNotFormatter">
            </el-table-column>
            <el-table-column label="入库数量" width="90">
                <template slot-scope="scope">
                    <el-input-number placeholder="请输入数量" v-model="scope.row.inQty" size="mini" :min="0" :max="scope.row.unrecieveQty" class="col-input-num" :disabled="scope.row.inQty>=scope.row.outQty||scope.row.isPacket==1"  @change="inQtyChange(scope.row)">
                    </el-input-number>
                </template>
            </el-table-column>
            <el-table-column label="入库包数" width="90">
                <template slot-scope="scope">
                    <el-input-number placeholder="请输入数量" v-model="scope.row.intPacketQty" size="mini" :min="0" :disabled = "scope.row.intPacketQty>=scope.row.outPacketQty||scope.row.isPacket==0" class="col-input-num"  @change="inQtyChange(scope.row)">
                    </el-input-number>
                </template>
            </el-table-column>
            <el-table-column label="单位" prop="unit">
            </el-table-column>
            <el-table-column label="批号" prop="batchCode">
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
            <el-table-column label="有效期" width="130">
                <template slot-scope="scope">
                    {{scope.row.expdtEndDate | dateFormat('YYYY-MM-DD')}}
                </template>
            </el-table-column>
            <el-table-column label="规格" prop="goodsGg">
            </el-table-column>
            <el-table-column label="产地" prop="made" width="90">
            </el-table-column>
        </el-table>
         <el-col :span="20" class="toolbar" style="padding-bottom: 0px;text-align: center;">
            <el-button   @click.native="cancel">取消</el-button>
            <el-button type="primary" @click.native="submitPurconfirm(10)" >保存</el-button>
            <el-button type="primary" @click.native="submitPurconfirm(20)" >提交</el-button>
        </el-col>
        <el-dialog title="条码设置" :visible.sync="barcodeV" :close-on-click-modal="false" >
            <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                <el-form label-width="70px" class="el-form-item-nomsg" label-position="left">
                    <el-col :span="7">
                        <el-form-item label="商品名称:">
                            <el-input v-model="UniqueCode.goodsName" :disabled="true" placeholder="商品名称" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="唯一码:">
                            <el-input class="input-barcode-small" ref="sbarcode" :disabled="true" v-model="UniqueCode.uniqueCode" placeholder="请录入唯一码"   @keyup.native.enter="addBarCode"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4">
                        <el-form-item>
                            <el-button type="primary" @click.native="addBarCode"  :disabled="true">添加</el-button>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-col>
            <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                <el-tag type="primary" v-for="item in lstUniqueCode" :key="item.id" :closable="false"   :title="item.batchCode" @close="delBarCode(item)">
                    <i class="fa fa-barcode"></i>
                    {{item.uniqueCode}}
                </el-tag>
            </el-col>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="barcodeV = false" >关闭</el-button>
            </div>
        </el-dialog>
    </el-row>
</template>
<script>
import moment from 'moment';
import { DISTRBILL_STATUS } from './common/constance';
import Stock4Slt from '../../components/Stock4Slt';
import barcodeUtil from '../../common/js/barcodeUtil';
export default {
    data() {
        return {
            opModel:"ADD",
            diaLoging: false,            
            barcodeV: false,
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
                filler: '',
                remark: '',
                sumRow: '',
                status: '',
                fillDate: '',
                version: '',
                distrBillList: []
            },
            inStockBill: {
                id: '',
                inStocId:null,
                inStocCode:null,
                billId: '',
                inStockKind: 3,//调拨入库
                outBillId: '',
                outOrgId: '',
                outOrgName: '',
                inOrgId: '',
                inOrgName: '',
                inDeptId: '',
                inDeptName: '',
                inStocId:'',
                sendBillid: '',
                filler: '',
                fillterName: '',
                fillDate: '',
                outDeptId: '',
                outDeptName: '',
                auditor: '',
                auditDate: null,
                accounter: '',
                accountDate: '',
                status: 0,
                lstDetail: []
            },
            inStockDetail: {},
            constInStockDetail: {
                id: '',
                pid: '',
                billId: '',
                outBillRow: 0,
                inBillRow: 0,
                provId: '',
                provCode: '',
                provName: '',
                goodsId: '',
                goodsName: '',
                goodsGg: '',
                mfrsId: '',
                mfrsName: '',
                made: '',
                packetCode: '',
                isPacket: '',
                uniqueKind: '',
                batchCode: '',
                sterilizationDate: '',
                sterilizationCode: '',
                sterilizationEndDate: '',
                expdtEndDate: '',
                unit: '',
                outQty: 0,
                outPacketQty: '',   //出库包数
                inQty: '',          //入库数量
                intPacketQty: '',
                inPrice: 0,              //入库单价
                masterCode: '',         //主码
                secCode: '',            //副码
                hibcCode: '',           //hibc
                version: 0,
                lstBatch: [],
                lstUniqueCode: []
            },
            sltRows: [],
            Batch: {
                id: '',
                pid: '',
                billId: '',
                pRowId: '',
                provId: '',
                goodsId: '',
                goodsBatchId: '',
                inPrice: '',
                inTime: '',
                qty: '',
                version: 0
            },
            UniqueCode: {
                id: '',
                pid: '',
                billId: '',
                goodsName:'',
                pRowId: 0,
                provId: '',
                goodsId: '',
                goodsBatchId: '',
                inPrice: '',
                inTime: '',
                uniqueCode: '',
                version: 0
            },
            lstUniqueCode: [],
            lstBatch: [],
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
    components:{Stock4Slt},
    filters: {
        fStatus: function (value) {
            if (value) {
                return DISTRBILL_STATUS[value].text;
            } else {
                return '';
            }
        }
    },
    methods: {
        getRecBill(recId){
            var _this = this;
            this.axios.get('/spdHERPService/stockMgr/inStock/getById?id=' + recId).then(res=>{
                if(res.data.code == 0){
                    _this.inStockBill = res.data.data;
                    for(var i = 0;i< _this.inStockBill.lstDetail.length;i++){
                        _this.inQtyChange( _this.inStockBill.lstDetail[i]);
                    }
                }
            });
        },

        convertDistrToRecBill(distBill) {   //outStockBill
            if (distBill) {
                this.inStockBill.id = '';
                this.inStockBill.billId = '';
                this.inStockBill.outBillId = distBill.billId;
                this.inStockBill.inStockKind = 3;
                this.inStockBill.inStockType = distBill.outStockType;
                this.inStockBill.outOrgId = distBill.outOrgId;
                this.inStockBill.outOrgName = distBill.outOrgName;
                this.inStockBill.outDeptId = distBill.outDeptId;
                this.inStockBill.outDeptName = distBill.outDeptName;
                this.inStockBill.inOrgId = distBill.inOrgId;
                this.inStockBill.inOrgName = distBill.inOrgName;
                this.inStockBill.inDeptId = distBill.inDeptId;
                this.inStockBill.inDeptName = distBill.inDeptName;
                this.inStockBill.inStockId = distBill.inStockId;
                this.inStockBill.filler = this.user.userId;
                this.inStockBill.fillterName = this.user.ename;
                this.inStockBill.fillDate = moment(new Date()).format("YYYY-MM-DD");
                this.inStockBill.status = 0;
                this.inStockBill.lstDetail = []
                if (this.distrBill.lstOutsubVo && this.distrBill.lstOutsubVo.length > 0) {
                
                    this.convertDistrLstToRecBillLst(this.distrBill);
                }
            }
        },
        convertDistrLstToRecBillLst(distrBill) {
            var res = [];
            distrBill.lstOutsubVo.forEach(function (item, index) {
                var row = Object.assign({}, this.constRecBillDetail);
                row.id = '';
                row.pid = '';
                row.billId = '';
                row.inBillRow = index + 1;
                //row.distrBillId = item.billId;
                row.outBillRow = item.rowNumber;
                //row.purBillId = item.purBillId;
                //row.purRowNumber = 0;
                //row.purBillDate = item.purBillDate;
                row.provId = item.provId;
                row.provCode = item.provCode;
                row.provName = item.provName;
                row.goodsId = item.goodsId;
                row.goodsName = item.goodsName;
                row.goodsGg = item.goodsGg;
                row.mfrsId = item.mfrsId;
                row.mfrsName = item.mfrsName;
                row.made = item.made;
                row.packetCode = item.packetCode; //item.hosGoodsId;
                row.isPacket = item.isPacket;
                row.uniqueKind = item.isUnique;
                row.batchCode = item.batchCode;
                // row.batchCode = item.isUniqueCode;
                //row.batchNum = item.batchNum;
                row.sterilizationCode = item.sterilizationCode;
                row.sterilizationEndDate = item.sterilizationEndDate;
                row.sterilizationDate = item.sterilizationDate;
                row.expdtEndDate = item.expdtEndDate;
                row.unit = item.unit;
                row.outQty = item.outQty ;
                //row.outQty = item.hosQty - item.receiveQty / item.sendScale;
                row.outPacketQty = item.outPacketQty;//item.hosQty;
               // row.inQty =  item.outQty;               
                row.inQty = 0;//row.outQty;
               /// row.intPacketQty = item.outPacketQty;
                row.intPacketQty=0;
                row.inPrice = item.price || 0;
                row.masterCode = '';
                row.secCode = '';
                row.hibcCode = '';
                row.version = 0;
                row.lstBatch = item.lstOutBatch;
                row.lstUniqueCode = item.lstOutUniqueCode;                
                res.push(row);
            }, this);
            this.inStockBill.lstDetail = res;
        },
        sltRowsChange(sltRows) {
            this.sltRows = sltRows;
        },
        submitPurconfirm(status) {
            if(!this.inStockBill.inStocId){
                this.$message.error('先选择入库库房');
                return;
            };
            this.diaLoging = true;
            this.inStockBill.status = status;
            var params = Object.assign({}, this.inStockBill);
            params.lstDetail = this.sltRows;
            var serviceUrl = this.opModel=="ADD"? '/spdHERPService/stockMgr/inStock/add' : '/spdHERPService/stockMgr/inStock/update';
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
                            this.$router.push({ path: 'inByReceive', query: { inStockBillId: billId }});
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
        barcodeNext() {
            this.fBarcode = "";
            this.$refs.fbarcode.$el.children[0].focus();
            this.$refs.fbarcode.$el.children[0].select();
        },
        barcodePre() {
            barcodeUtil.parseCombinedBarcode(this.mainBarcode, this.fBarcode, this.distrBill.purchaseCompanyId, this.distrBill.provId).then(res => {
                var goodsLst = res.data.data;
                this.setSendGoods(goodsLst);
                this.$refs.mainBarcode.$el.children[0].focus();
                this.$refs.mainBarcode.$el.children[0].select();
                this.mainBarcode = "";
                this.fBarcode = "";
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
        addBybarcode() {      
            barcodeUtil.parseCombinedBarcode(this.barcode, null).then(res => {
                var goodsLst = res.data.data;
                //与明细行的 goodsId batchNo批号 要一致
                this.setSendGoods(goodsLst);
              //  this.$refs.mbarcode.$el.children[0].focus();
               // this.$refs.mbarcode.$el.children[0].select();
            });
        },
        findGoods(barCodeInfo) {
            var res = null;
            var flag = false;//是否找到匹配的商品
         
            for (var i = 0; i < this.inStockBill.lstDetail.length; i++) {
                var item = this.inStockBill.lstDetail[i];
                if (barCodeInfo.goodsId == item.goodsId) {             
                    flag = true;
                      res = item;
                      break;
                    // if (item.inQty >= 0 && item.inQty < item.outQty) {
                    //     if (item.batchCode) {
                    //         if (item.batchCode == barCodeInfo.batchNo) {
                    //             res = item;
                    //             break;
                    //         }
                    //     } else {
                    //         res = item;
                    //         break;
                    //     }
                    // }
                }
            }
            if (!flag) {
                this.barcodeMatch.hosId = this.distrBill.inOrgId;//this.distrBill.purchaseCompanyId;
                this.barcodeMatch.provId = this.distrBill.outOrgId;
                this.barcodeMatch.mfrsCode = barCodeInfo.goodsNo;
                this.barcodeMatchV = true;
            }
        
            return res;
        },
        setSendGoods(goodsLst) { //goodsLst 为扫码结果集，一个码可能对应多个goodsId
            if (!Array.isArray(goodsLst) || goodsLst.length == 0) {
                this.$message.error('此条码无法解析!');
                return;
            }
            var curRow = null;
            for (var i = 0; i < goodsLst.length; i++) {
                var curRow = this.findGoods(goodsLst[i]);
                 if (curRow && curRow.inQty < curRow.outQty) {                    
                     break;
                 }
               
            }
            if(curRow){        
              //添加是否定数包管理逻辑
                 if(curRow.isPacket==1){ //定数包管理 
                                curRow.intPacketQty++;
                                curRow.inQty = curRow.intPacketQty*curRow.packetCode;
                 }  
                else{    //非定数包管理
                    curRow.inQty++;
                }
                 this.$refs.receListTable.toggleRowSelection(curRow,true);

               
                // curRow.expdtEndDate = new Date(goodsLst[i].expiredDate);
                // curRow.batchCode = goodsLst[i].batchNo;
                // curRow.masterCode = goodsLst[i].barcode;
                // curRow.secCode = "" || goodsLst[i].slaverBarcode;
                // if (curRow.uniqueKind && curRow.uniqueKind == 3) {       //非唯一码管理 直接加数量
                //     curRow.inQty++;
                //     this.inQtyChange(curRow);
                // } else {              //唯一码
                //     this.inStockDetail = curRow;
                //     //this.inStockDetail.inQty = this.inStockDetail.lstUniqueCode.length;
                //     this.lstUniqueCode = this.inStockDetail.lstUniqueCode;
                //     this.UniqueCode = {
                //         provId: this.inStockBill.outOrgId,
                //         goodsId: this.inStockDetail.goodsId,
                //         goodsName: this.inStockDetail.goodsName,
                //         inPrice: this.inStockDetail.inPrice,
                //         uniqueCode: ''
                //     };                    
                //     this.barcodeV = true;
                //     this.$nextTick(function(){
                //         this.$refs.sbarcode.$el.children[0].focus();
                //         this.$refs.sbarcode.$el.children[0].select();
                //     });
                // }
            }
        },
        barcodeMatchSave() {
            this.barcodeMatchV = false;
            if (this.barcodeType) {
                this.addBybarcode();
            } else {
                this.barcodePre();
            }
        },
        editBarcode(row) {
            this.barcodeV = true;
            this.inStockDetail = row;
            this.lstUniqueCode = row.lstUniqueCode;
            this.UniqueCode = {
                provId: this.inStockBill.outOrgId,
                goodsId: row.goodsId,
                goodsName: row.goodsName,
                uniqueCode: ''
            }
            this.$nextTick(function(){
                this.$refs.sbarcode.$el.children[0].focus();
                this.$refs.sbarcode.$el.children[0].select();
            });
        },
        addBarCode(){
            var _this =this;
            if(!_this.UniqueCode.uniqueCode){
                return;
            }
            var index =  this.inStockDetail.lstUniqueCode.findIndex(item =>item.uniqueCode == _this.UniqueCode.uniqueCode);
            if(index >=0){
                this.$message.error('此唯一码已使用!');
                return;
            }
            var barCode = {
                provId : this.UniqueCode.provId,
                goodsId : this.UniqueCode.goodsId,
                inPrice : this.UniqueCode.inPrice,
                uniqueCode : this.UniqueCode.uniqueCode
            }
            this.inStockDetail.lstUniqueCode.push(barCode);
            if(this.inStockDetail.uniqueKind != 3){
                this.inStockDetail.inQty = this.inStockDetail.lstUniqueCode.length;
            }
            this.inQtyChange(this.inStockDetail);
            this.$nextTick(function(){
                this.$refs.sbarcode.$el.children[0].focus();
                this.$refs.sbarcode.$el.children[0].select();
            });
        },
        delBarCode(barcode){
            var index =  this.inStockDetail.lstUniqueCode.findIndex(item =>item.uniqueCode == barcode.uniqueCode);
            this.inStockDetail.lstUniqueCode.splice(index, 1);
            if(this.inStockDetail.uniqueKind != 3){
                this.inStockDetail.inQty = this.inStockDetail.lstUniqueCode.length;
                this.inQtyChange(this.inStockDetail);
            }
        },
        inQtyChange(row){           
            this.$nextTick(function(){
                if(row.inQty>0){
                this.$refs.receListTable.toggleRowSelection(row,true);
                }else{
                    this.$refs.receListTable.toggleRowSelection(row,false);
                }
            });            
        },
        cancel(){
             this.$router.push({ path: 'inByMove'});
        },
        getOutBillDetail (rowId) {
            var _this = this;
            this.diaLoging = true;
       //     this.axios.get('/spdHERPService/stockMgr/out/billMgr/getBillDetail',{
             this.axios.get('/spdHERPService/stockMgr/out4In/getBillDetail4In', {
              params: { id: rowId }
             }).then(res => {
                _this.diaLoging = false;
                if (res.data.code == "0") {
                    _this.distrBill = res.data.data;
                    _this.convertDistrToRecBill(_this.distrBill);                    
                }
            }, err => {
                this.diaLoging = false;
            });
        },
        getSelected_Stock(temp){
          this.inStockBill.inStocId = temp[1];
          this.inStockBill.inStocCode=temp[0]+'.'+temp[1];
            this.axios.post( `/spdHERPService/deptMgr/stocInfo/getOrgsByStock/`+ temp[1]).then(res => {
                let ob = res.data.data;
                this.inStockBill.inDeptId=ob.id;
                 this.inStockBill.inDeptName=ob.ename;
             });
        },
        isNotFormatter (row, column, cellValue) {
          return cellValue==1 ? "是" :"否"
        }
    },
    mounted() {
        var _this = this;
        var recId = this.$route.query.recId;
        if(recId){
            this.opModel="EDIT";
            this.getRecBill(recId);
        }else{
             this.opModel="ADD";
            var outBillId = this.$route.query.outBillId;
            this.getOutBillDetail(outBillId);
        }
    }
};

</script>

<style>
#in-by-move-edit .fa {
    margin-right: 5px;
}

#in-by-move-edit .vue-echarts {
    width: 100%;
    height: 400px;
}

#in-by-move-edit .el-table {
    font-size: 12px;
}

#in-by-move-edit fieldset {
    border-width: 1px;
    border-style: solid;
    border-color: #1fa0ff;
    border-image: initial;
}

#in-by-move-edit .icoBtn [class^=fa] {
    vertical-align: baseline;
    cursor: pointer;
    margin-right: 5px;
}

#in-by-move-edit .el-dialog--small {
    width: 70%;
}
</style>