<template>
    <div id="list-buy" style="height: 100%;">	
            <el-col :span="24" style="height: 100%;">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form :inline="true" label-width="80px">
                        <el-form-item label="请购单号">
                            <el-input v-model="filters.billId" placeholder="请购单号" ></el-input>
                        </el-form-item>
                      
                        <el-form-item label="请购日期：">
                            <el-date-picker v-model="filters.sDate" type="daterange" align="right" placeholder="选择日期范围" :picker-options="pickerOptions2" >
                            </el-date-picker>
                        </el-form-item>
                 
                        <el-form-item>
                            <el-button type="primary" icon="search" v-on:click="getDeptBuyList(1)" >查询</el-button>
                        </el-form-item>
                    </el-form>
                </el-col>
              
                <!--列表-->
                <template>
                        <el-table :data="deptBuyList" highlight-current-row v-loading="loading" style="width: 100%;" fit border class="RO-Main-tableheight">
                              <el-table-column prop="billId" label="请购单号" sortable></el-table-column>
                              <el-table-column prop="state" label="状态" sortable :formatter="statusFormat"></el-table-column>
                              <el-table-column prop="buyDeptName" label="请购科室" sortable> </el-table-column>
                              <el-table-column prop="sourceWarehouseName" label="目标科室" sortable> </el-table-column>
                                <!-- <el-table-column prop="totalJe" label="总金额" sortable> </el-table-column> -->
                            
                                <el-table-column prop="fillterName" label="制单人" sortable></el-table-column>
                                <el-table-column prop="fillDate" label="制单时间">
                                    <template slot-scope="scope">
                                        {{scope.row.fillDate | dateFormat('YYYY-MM-DD')}}
                                    </template>
                                </el-table-column>
                                <el-table-column label="操作" width="150">
                                    <template slot-scope="scope">
                                        <el-button   @click="viewBill(scope.row.billId)" type="text">查看</el-button>
                                        <el-button v-if="scope.row.state != 10"  @click="again(scope.row.billId)" type="text">再次请购</el-button>
                                        <!--<el-button v-if="scope.row.state == 10"  @click="editBill(scope.row.billId)" type="text">编辑</el-button>-->
                                        <el-button  v-if="scope.row.status == 10" @click="delBill(scope.row)" type="text">删除</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                            <!--工具条-->
                            <el-col :span="24" class="toolbar">
                                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[50, 100, 200, 400]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
                                </el-pagination>
                            </el-col>
                </template>
                <!--请购单 修改 审核  -->
                <el-dialog :title="modelTitle" close="onClose" :visible.sync="editFormVisible" :close-on-click-modal="false" width="70%">
                            <template>
                                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                                    <el-form label-width="120px">
                                            
                                       
                                          <el-col :span="12">
                                            <el-form-item label="目标科室:">
                                                    <el-input type="text"  v-model="buyBill.sourceWarehouseName" :disabled="true" ></el-input>
                                            </el-form-item>
                                        </el-col>
                                         <el-col :span="12">
                                            <el-form-item label="请购科室:">
                                                  <!--  <el-input type="text"  v-model="buyBill.buyDeptName" :disabled=true ></el-input> -->
                                                  <span>{{ buyBill.buyDeptName }}</span>
                                            </el-form-item>
                                        </el-col>
                                     
                                            <el-col :span="6">
                                                    <el-form-item label="ID:" v-show="false">
                                                        <span>{{ buyBill.recAddressId }}</span>
                                                    </el-form-item>
                                            </el-col>
                                               <el-col :span="5">
                                                    <el-form-item label="收货人:">
                                                        {{buyBill.recLinkman}}
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="5">
                                                    <el-form-item label="电话:">
                                                        {{buyBill.recLinkmanPhone}}
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="12">
                                                    <el-form-item label="收货地址:">
                                                        {{buyBill.recAddress}}
                                                    </el-form-item>
                                                </el-col>
                                            
                                                <el-col :span="2">
                                                    <el-form-item label-width="0px">
                                                    <deliveryAddress btn_text="修改" v-on:addressChange="setAddress" :hosId="this.user.corpId" :deptId="this.user.orgId"></deliveryAddress>
                                                    </el-form-item>
                                                </el-col>
                                                
                                    </el-form>
                                </el-col>
                            </template>

                               <!--请购明细列表-->
                            <template>
                                  <el-table :data="buyBill.subVoList" style="width: 100%;" fit border height="300">
                                       <el-table-column v-if="!againFlag" label="操作" width="50">
                                            <template slot-scope="scope">
                                                <el-button  @click="delRow(scope.row)" type="text">删除</el-button>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="rowNumber" label="序号" width="60">
                                        </el-table-column>
                                        <el-table-column prop="goodsName" label="名称" width="180" show-overflow-tooltip>
                                        </el-table-column>
                                        <el-table-column prop="code" label="编号">
                                        </el-table-column>
                                        <el-table-column prop="goodsGg" label="规格">
                                        </el-table-column>
                                        <el-table-column prop="brand" label="品牌">
                                        </el-table-column>
                                        <!-- <el-table-column prop="price" label="单价" width="120">
                                        </el-table-column> -->
                                    
                                        <el-table-column prop="applyQty" label="请购数量"> 
                                            <template slot-scope="scope">
                                                    <el-input-number placeholder="请输入数量" v-model="scope.row.applyQty" size="mini" :min="1"  :step="scope.row.reqNum?scope.row.reqNum:1" class="col-input-num" :disabled="scope.row.isPacket==1" @change="qtyChange($event,scope.row)">
                                                    </el-input-number>
                                            </template>  
                                        </el-table-column>
                                        <el-table-column label="请购包数"  width="100">
                                            <template slot-scope="scope">
                                                <el-input-number placeholder="请输入数量"   v-model="scope.row.needPacketQty" size="mini" :min="0" :step="scope.row.reqNum?scope.row.reqNum:1"  class="col-input-num" :disabled="scope.row.isPacket==0||scope.row.isPacket==null" @change="qtyPacketChange($event,scope.row)">
                                                </el-input-number>
                                            </template>
                                        </el-table-column>
                                        <!-- <el-table-column prop="je" label="金额">
                                        </el-table-column> -->
                                        <el-table-column prop="mfrsName" label="产家">
                                        </el-table-column>
                                        <el-table-column prop="provName" label="供应商">
                                        </el-table-column> 
                                         <el-table-column prop="storeQty" label="库存数量">   
                                        </el-table-column>
                                        <el-table-column prop="isPacket"   min-idth="10" label="是否定数包" header-align="center"  :formatter="dsFormatter">
                                        </el-table-column>                               
                                    </el-table>
                                
                            </template>
                              <el-col :span="24" class="toolbar" style="margin-top:10px; padding-bottom: 0px;">
                                    <div style="text-align: center;">
                                        <el-button v-if="againFlag" :plain="true" type="success"  @click="createBillAgain">确定</el-button>
                                        <el-button v-if="!againFlag" :plain="true" type="warning"  @click="saveBill()">保存</el-button>
                                        <el-button :plain="true" type="danger"  @click="cancel">取消</el-button>
                                    </div>
                              </el-col>
                </el-dialog>   
     
				
            </el-col>
        <view-buy ref="viewBuy" v-on:datacomplete="dataComplete()"></view-buy>
    </div>
</template>
<script>
import { DEPTBUY_MAIN_STATUS } from './common/constance';
import util from "../../common/js/util.js";
import { mapGetters } from 'vuex';
import deliveryAddress from '../../components/deliveryAddress.vue';
import viewBuy from './viewBuy'
export default {
    data() {
        return {
            filters: {
                billId: '',
                sDate: []
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
            billId:"",
            total: 0,
            page: 1,
            pageSize: 50,  
            deptBuyList:[],
            loading: false,
            editFormVisible:false,
            modelTitle:null,
            buyBill: {
                id: '', billId: '', fillDate: '', returnCompanyId: '', returnCompanyName: '',
                returnOrgId: '', returnOrgCode: '', returnOrgName: '',
                outOrgId: '', outOrgName: '', outOrgCode: '',
                provId: '', provName: '',
                fillter: '', memo: '', isStartInvoice: '', sumRow: '', status: '',
                subVoList: [],  recAddressId: '', recLinkman: '', recLinkmanPhone: '',recAddress: ''
            },
            // 再次请购
            againFlag:false
        }
    },
    components: { deliveryAddress, viewBuy },
    methods: {
        //获取列表
        getDeptBuyList: function (pIndex) {
            this.page = pIndex;
            var param = {
                orderBy: "",
                page: this.page,
                rows: this.pageSize,
                queryObject: {
                    billId: this.filters.billId,
                    buyKind:0, // 10 实采 20 虚采 ，0华山北院需求，后台进行判断查询所有单据
                    hosId:this.user.corpId,
                    buyDeptId:this.user.orgId,
                    sDate: this.filters.sDate?this.filters.sDate[0]:null,
                    eDate: this.filters.sDate?this.filters.sDate[1]:null,
                    stateArr:[10,11,20,30,40] // 10草稿 20 已提交 30

                }
            };
            this.loading = true;
            this.axios.post('/spdHERPService/applyMgr/deptBuyMain/listVoByPage', param).then(res => {
                this.loading = false;
                this.deptBuyList = res.data.data.data;
                this.total = res.data.data.total;
            }, err => {
                this.loading = false;
            });
        },
        
          
        handleCurrentChange(val) {
            this.page = val;
            this.getDeptBuyList(this.page);
        },
        handleSizeChange(val) {  
            this.pageSize = val;
            this.getDeptBuyList(this.page);
        },
        statusFormat(row) {
            return DEPTBUY_MAIN_STATUS[row.state].text;
        },
        viewBill(billId) {
            // this.$router.push({ path: 'viewBuy', query: { billId: billId } });
            this.loading = true
            this.$refs['viewBuy'].open(billId)
        },
        // 再次请购
        again(billId){
            this.againFlag = true;
            this.billId = billId;
            this.editFormVisible = true;
            this.getBillDetails(billId);
        },
        // 确定
        createBillAgain(){
            this.buyBill.subVoList.forEach((item)=> delete item.needPacketQty);
            this.axios.post('/spdHERPService/applyMgr/deptBuyMain/createBillAgain/'+this.user.userId, this.buyBill).then(res => {
                if (res.data.code == "0") {
                    this.$msgbox({title: "提示",message: "操作成功",type: "success",});
                    this.editFormVisible = false;
                    this.againFlag = false;
                    // this.getDeptBuyList(1);
                    var id = res.data.data;
                   // this.$router.push({ path: 'unAuditBuy', query: { billId: id } });
				   this.$router.push({ path: 'runningList', query: { } });
                } else {
                    this.$msgbox({ title: "提示", message: "操作失败", type: "error" });
                }
            }, err => {
                this.loading = false;
                this.$msgbox({title: "提示",message: "操作失败",type: "error"});
            });
        },
        editBill(billId){
            this.modelTitle="修改请购单";
            this.againFlag = false;
            this.editFormVisible = true;
            this.getBillDetails(billId);
        },
        delRow(pd) {
            var index = this.buyBill.subVoList.findIndex(item => { return item.rowNumber == pd.rowNumber });
            this.buyBill.subVoList.splice(index, 1);
            this.buyBill.subVoList.forEach((item, i) => item.rowNumber = i + 1);
        },
        getBillDetails(billId) {
            let params ={billId:billId};
            this.axios.post('/spdHERPService/applyMgr/deptBuyMain/getBillDetails/' , params).then(res => {
                if (res.data.code == "0") {
                    this.buyBill = res.data.data;
                          this.buyBill.subVoList.forEach((g)=>{
                              g.needPacketQty  = g.isPacket==1?g.qty/g.packetCode:0;
                         });
                }
            }, err => {
            });
        },
        cancel() {
            this.againFlag = false;
            this.editFormVisible=false;
        },
        saveBill: function () {
            this.loading = true;
         //   this.buyBill.state = status;
          this.buyBill.subVoList.forEach((item)=> delete item.needPacketQty);
       
            this.axios.post('/spdHERPService/applyMgr/deptBuyMain/update/'+this.user.userId, this.buyBill).then(res => {
                this.loading = false;
                if (res.data.code == "0") {
                 //   var billId = res.data.data.billId;
                    this.$msgbox({
                        title: "提示",
                        message: "操作成功",
                        type: "success",
                        //  callback: action => {
                        //      this.$router.push({ path: 'purchase', query: { billId: billId } });
                        //  }
                        
                    });
                    this.editFormVisible=false;
                    this.getDeptBuyList(1);
                } else {
                    this.$msgbox({ title: "提示", message: "操作失败", type: "error" });
                }
            }, err => {
                this.loading = false;
                this.$msgbox({
                    title: "提示",
                    message: "操作失败",
                    type: "error"
                });
            });
        },
         qtyChange(val, row) {
             row.qty = val;
             row.je = (row.price * row.qty).toFixed(2);
         
        },
           qtyPacketChange(val,row){
               row.needPacketQty = val;
               row.qty =  (row.needPacketQty * row.packetCode).toFixed(2);
           },
           dsFormatter (row, column, cellValue) {
               if(cellValue==1)
               return "是"
               else return "否"
            },
        setAddress(d) {
            
            this.buyBill.recAddressId = d.id;
            this.buyBill.recLinkman = d.lxr;
            this.buyBill.recLinkmanPhone = d.lxrPhone;
            this.buyBill.recAddress = (""+d.province + d.city + d.area + d.address).replace(/null/g,"");
        
          },
        dataComplete(){
            this.loading = false
        },
    },

    mounted() {
        var end = new Date();
        var start = new Date();
        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
        this.filters.sDate = [start, end];
        this.getDeptBuyList(1);
       
    }
    
};

</script>

<style>
#list-buy .el-table {
    font-size: 12px;
}
#list-buy .RO-Main-tableheight{
       height:  calc(100% - 90px);
}

#list-buy .el-dialog__body {
    padding: 10px 10px 50px 10px;
}
</style>