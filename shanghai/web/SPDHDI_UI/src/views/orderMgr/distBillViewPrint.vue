<template>
    <el-row :gutter="10" style="height:95%" id="distBillPrintViewPrint"  class="printArea" v-show='false'>
        <el-row>
            <el-col :span="24" style="padding-bottom:10px" align="center" v-show='false'>
                <span style="font-size:20px;"><b>配送单</b></span><br>
            </el-col>
            <el-col :span="8">
                <span class="title">配送单号:</span>
                <span>{{ distrBill.billId }}</span>
            </el-col>
            <el-col :span="8">
                <span class="title">制单时间:</span>
                <span>{{ distrBill.fillDate | dateFormat('YYYY-MM-DD')}}</span>
            </el-col>
            <el-col :span="8">
                <span class="title">制单人:</span>
                <span>{{ distrBill.fillterName }}</span>
            </el-col>
            <el-col :span="24">
                <span class="title">备注:</span>
                <span>{{ distrBill.remark }}</span>
            </el-col>

        </el-row>
            <el-col :span="24">
                <svg id="pbarcode"></svg>
            </el-col>
        <el-row>
            <table class="gridtable">
                <tr>
                    <th width=30>序号</th>
                    <th width=150>商品名称</th>
                    <th width=150>规格</th>
                    <th>转换  比</th>
                    <th>配送单价</th>
                    <th>配送数量</th>
                    <th>计量单位</th>
                    <th>医院数量</th>
                    <th>医院计量单位</th>
                </tr>
                <tr v-for="(item ,index) in distrBill.distrBillList" :key="item.id">
                    <td align="center"> {{index+1}}</td>
                    <td>{{item.provGoodsErpName}}</td>
                    <td>{{item.goodsGg}}</td>
                    <td>{{item.sendScale}}</td>
                     <td>{{item.sendPrice}}</td>
                    <td>{{item.sendQty}}</td>

                    <td>{{item.sendUnit}}</td>
                    <td>{{item.hosQty}}</td>
                    <td>{{item.hosUnit}}</td>
                </tr>
            </table>
        </el-row>

        <el-row class="toolbar" style="padding-bottom: 0px;margin-top:5px;">
            <el-col :span="12">
                <span class="title">请购机构:</span>
                <span>{{ distrBill.purchaseCompanyName }}</span>
            </el-col>
            <el-col :span="12">
                <span class="title">请购科室:</span>
                <span>{{ distrBill.applyOrgName }}</span>
            </el-col>
            <el-col :span="12">
                <span class="title">收货科室:</span>
                <span>{{ distrBill.recieveOrgName }}</span>
            </el-col>
            <el-col :span="12">
                <span class="title">配送机构:</span>
                <span>{{ distrBill.provName }}</span>
            </el-col>
        </el-row>
    </el-row>
</template>
<script>

import $ from 'jquery';
import '../../common/third/printThis/printThis';
import '../../common/third/qrcode/jquery.qrcode.min';
import JsBarcode from 'jsbarcode';
export default {
    data() {
        return {
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
                fillterName: '',
                distrBillList: []
            },
        }
    },

    methods: {
        print(distrBill){
            if(distrBill){
                JsBarcode("#pbarcode", distrBill.billId, { height: 30, displayValue: false });
                this.distrBill = distrBill;
            }
            $("#distBillPrintViewPrint").printThis({
                debug: false,
                importCSS: true,
                importStyle: true,
                printContainer: true,
                loadCSS: './static/style/print.css',
                pageTitle: this.user.corpName,
                removeInline: false,
                printDelay: 333,
                header: null,
                formValues: true
            });
        },

    },
    mounted() {

    }
};

</script>

<style scoped>
#distBillPrintViewPrint{
    padding: 10px 10px 10px 20px;
}
table.gridtable {
    font-family: verdana,arial,sans-serif;
    font-size:11px;
    color:#333333;
    border-width: 1px;
    border-color: #666666;
    border-collapse: collapse;
    width:'95%'
}
table.gridtable th {
    border-width: 1px;
    padding: 8px;
    border-style: solid;
    border-color: #666666;
    background-color: #dedede;
}
table.gridtable td {
    border-width: 1px;
    padding: 8px;
    border-style: solid;
    border-color: #666666;
    background-color: #ffffff;
}
</style>
