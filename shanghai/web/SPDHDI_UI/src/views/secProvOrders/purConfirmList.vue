<template>
<div style="height:100%" id="s_d_purcLst">
    <el-tabs type="border-card" v-model="showTab" >
        <el-tab-pane label="订单确认单" name="purconfirmLst">
            <el-col :span="24" style="height:100%;">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form :inline="true" label-width="80px">
                        <el-form-item label="二级供应商">
                           <secProvSlt :userId="user.userId" @subProvChange="subProvChange" @onLoad="onSecProvLoad"></secProvSlt>
                        </el-form-item>
                        <el-form-item label="确认单号">
                            <el-input v-model="filters.billId" placeholder="确认单号" ></el-input>
                        </el-form-item>
                        <el-form-item label="制单时间">
                            <el-date-picker v-model="filters.sDate" type="daterange" align="right" placeholder="选择日期范围" :picker-options="pickerOptions2" >
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="状态">
                            <el-select v-model="filters.status" placeholder="请选择" >
                                <el-option
                                v-for="item in statusDropDown"
                                :key="item.label"
                                :label="item.label"
                                :value="item.value">
                                {{item.label}}
                                </el-option>
                            </el-select>
                        </el-form-item>                      
                        
                 
                        <el-form-item style="margin-left:30px;">
                            <el-button type="primary" icon="search" v-on:click="getPurchaseList(1)" >查询</el-button>
                            <el-button @click="resetForm" >重置</el-button>
                        </el-form-item>
                    </el-form>
                </el-col>
                <!--列表-->
                <el-table :data="purchaseList" highlight-current-row v-loading="loading" style="width: 100%;" fit class="table_content">
                    <el-table-column label="操作" width="90">
                        <template slot-scope="scope">
                            <el-button  @click="PurchaseView(scope.row.billId)" type="text">查看</el-button>
                            <el-button v-if="scope.row.status == 10"  @click="editPurchase(scope.row.billId)" type="text">编辑</el-button>
                            <el-button  v-if="scope.row.status == 10" @click="delPurchase(scope.row)" type="text">删除</el-button>
                        </template>
                    </el-table-column>
                    <el-table-column label="确认单号" prop="billId" width="155"></el-table-column>
                    <el-table-column prop="status" label="状态" sortable :formatter="statusFormat" width="80"></el-table-column>
					<el-table-column prop="subProvName" label="二级供应商" sortable width="200">
                    </el-table-column>
                    <el-table-column prop="purchaseCompanyName" label="请购机构" sortable width="200">
                    </el-table-column>
                    <el-table-column prop="applyOrgName" label="请购科室" sortable width="180">
                    </el-table-column>
                    <el-table-column prop="recieveOrgName" label="收货科室" sortable width="180">
                    </el-table-column>
                    <el-table-column prop="sumAmount" label="含税金额" width="120">
                    </el-table-column>
                    <el-table-column prop="sumTaxAmount" label="不含税金额" width="120">
                    </el-table-column>
                    <el-table-column prop="fillterName" label="制单人" sortable width="120">
                    </el-table-column>
                    <el-table-column prop="fillDate" label="制单时间" width="120">
                        <template slot-scope="scope">
                            {{scope.row.fillDate | dateFormat('YYYY-MM-DD')}}
                        </template>
                    </el-table-column>                            
                </el-table>
                <!--工具条-->
                <el-col :span="24" class="toolbar">
                    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[30, 100, 200, 400]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
                    </el-pagination>
                </el-col>
            </el-col>
            <el-dialog title="确认单查看" :visible.sync="confirmViewVisible" :close-on-click-modal="false" width="80%" >
                <purconfirmView :billid="billid"></purconfirmView>
                <div slot="footer" class="dialog-footer">
                    <el-button :plain="true" type="danger"  @click="print">打印</el-button>
                    <el-button @click.native="confirmViewVisible = false" >关闭</el-button>
                </div>
                
            </el-dialog>
        </el-tab-pane>
        <el-tab-pane label="采购单确认" name="purLst">
            <secPurconfirm v-on:savePurcon="savePurcon" ></secPurconfirm>
        </el-tab-pane>
    </el-tabs>
    <purconfirmViewPrint ref="printP" :billid="billid"></purconfirmViewPrint>
</div>   
</template>
<script>
import secProvSlt from '@/components/secProvSlt';

import secPurconfirm from './secPurconfirm';
import purconfirmView from '../orderMgr/purconfirmView';
import { PURCONFIRM_STATUS } from '../orderMgr/common/constance';
import { PURCONFIRM_STATUS_SELECT } from '../orderMgr/common/constance';
import moment from 'moment';
import $ from 'jquery';
import '../../common/third/printThis/printThis';
import purconfirmViewPrint from '../orderMgr/purconfirmViewPrint'
export default {
    data() {
        return {
            filters: {
                billId: '',
                provId: '',
                sDate: [],
                status:'',
                subProvId:'',
                hosLst:[]
            },
            statusDropDown:[],
            billid: '',
            confirmViewVisible: false,
            showTab: 'purconfirmLst',
            total: 0,
            page: 1,
            pageSize: 30,
            loading: false,
            purchaseList: [
            ],
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
        }
    },
    methods: {
        //获取列表
        getPurchaseList: function (pIndex) {
            this.page = pIndex;
            var param = {
                orderBy: "",
                page: this.page,
                rows: this.pageSize,
                queryObject: {
                    billId: this.filters.billId,
                    provId: this.filters.provId,
                    sDate: this.filters.sDate&&this.filters.sDate[0]!=null?moment(this.filters.sDate[0]).format("YYYY-MM-DD"):null,
                    eDate: this.filters.sDate&&this.filters.sDate[1]!=null?moment(moment(this.filters.sDate[1]).add(1,'days').format('YYYY-MM-DD')).subtract(1,'seconds').format('YYYY-MM-DD HH:mm:ss'):null,
                    status:this.filters.status,
                    subProvId:this.filters.subProvId,
                    hosLst:this.filters.hosLst
                }
            };
            this.loading = true;
            this.axios.post('/spdHDIService/orderMgr/purconfirm/secListByPage', param).then(res => {
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
        savePurcon(billids) {
            this.$nextTick(function () {
                this.showTab = "purconfirmLst";
                this.filters.billId = billids.join(",");
                this.getPurchaseList(1);
            });
        },
        PurchaseView(billId) {
            this.billid = billId;
            this.confirmViewVisible = true;
            // this.$router.push({ path: 'purchaseView', query: { billId: billId } });
        },
        resetForm() {
            this.filters.billId = "";
            this.filters.sDate = [];
            this.filters.status = "";
        },
        statusFormat(row) {
            return PURCONFIRM_STATUS[row.status].text;
        },
        purchaseAgain: function (billId) {
            this.$confirm('确认重新上传吗?', '提示', {
            }).then(() => {
                this.loading = true;
                this.axios.get("/spdHDIService/orderMgr/purconfirm/purchaseAgain/" + billId).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") { // 正常
                        this.loading = false;
                        if(res.data.data.code == "0000")
                        {
                            this.$message({ message: '操作成功！', type: 'success' });
                        }else{
                            this.$message.error(res.data.data.message);
                        }
                    } else if(res.data.code == "-9999"){ // JDE接口服务异常
                            this.$message.error(res.data.msg);
                    }else if(res.data.code == "-9998"){ // JDE接口方法调用失败
                            this.$message.error(res.data.msg);
                    }else if(res.data.code == "-1000"){ // 系统内部异常
                            this.$message.error(res.data.msg);
                    }else{ // 未知
                        this.$message.error("操作失败！");
                    }
                    this.getPurchaseList(this.page);
                }, err => {
                    this.loading = false;
                });
            }).catch(() => {

            });
        },
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
        print() {
            this.$refs.printP.print(this.billid);
        },
        format(date,fmt) { 
            var o = { 
                "M+" : date.getMonth()+1,                 //月份 
                "d+" : date.getDate(),                    //日 
                "h+" : date.getHours(),                   //小时 
                "m+" : date.getMinutes(),                 //分 
                "s+" : date.getSeconds(),                 //秒 
                "q+" : Math.floor((date.getMonth()+3)/3), //季度 
                "S"  : date.getMilliseconds()             //毫秒 
            }; 
            if(/(y+)/.test(fmt)) {
                    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length)); 
            }
            for(var k in o) {
                if(new RegExp("("+ k +")").test(fmt)){
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
                }
            }
            return fmt; 
        }
    },
    mounted() {
        this.statusDropDown = PURCONFIRM_STATUS_SELECT;
        this.filters.provId = this.user.corpId;
        var end = new Date();
        var start = new Date();
        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
        this.filters.sDate = [start, end];
        this.getPurchaseList(1);
    },
    components: { secPurconfirm, purconfirmView,purconfirmViewPrint,secProvSlt }

}
</script>
<style>
#s_d_purcLst .table_content{
    font-size: 12px;
    height: calc(100% - 100px);
    overflow-y: auto;
}
.el-tabs,.el-tabs .el-tabs__content{
    height:94%;
}
.el-tabs__item{
    height:30px;
    line-height: 30px;
    font-size:12px;
}
</style>

