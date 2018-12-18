<template>
    <el-row :gutter="10" id="HosMonthInStock"  class="printArea"  v-show='false'>
            <el-col :span="24" style="padding-bottom:10px" align="center" >
                一级库出库查询</b></span>
            </el-col>
            <!--表格-->
                <table class="gridtable">
                    <tr>
                        <th width=30>序号</th>
                        <th width=80>出库日期</th>
                        <!-- <th width=40>医疗机构名称</th> -->
                        <th width=40>出库科室</th>
                        <th width=40>出库单状态</th>
                        <!-- <th width=40>商品编号</th> -->
                        <th width=40>ERP编码</th>
                        <th width=40>商品名</th>
                        <th width=40>商品规格</th>
                        <th width=40>厂家</th>
                        <th width=40>产地</th>                        
                        <th width=40>单位</th>
                        <th width=40>批号</th>
                        <th width=40>效期</th>
                        <th width=40>数量</th>
                        <th width=40>采购价</th>
                        <th width=40>销售价</th>
                        <th width=40>金额</th>
                        <th width=40>供应商</th>

                    </tr>
                    <tr v-for="(item,index) in printDatas" :key="item.id">
                        <td align="center">{{ index+1 }}</td>
                        <td>{{item.billDate | dateFormat('YYYY-MM-DD')}}</td>
                        <!-- <td align="center">{{item.inOrgName}}</td> -->
                        <td align="center">{{item.outDeptName}}</td>
                        <td align="center">{{item.statusName}}</td>
                        <!-- <td >{{item.provGoodsId}}</td> -->
                        <td >{{item.erpCode}}</td>
                        <td align="center">{{item.goodsName}}</td>
                        <td align="center">{{item.goodsGg}}</td>
                        <td align="center">{{item.mfrsName}}</td>
                        <td align="center">{{item.made}}</td>
                        <td align="center">{{item.unit}}</td>
                        <td align="center">{{item.batchCode}}</td>
                        <td>{{item.expdtEndDate | dateFormat('YYYY-MM-DD')}}</td>
                      
                        <td align="center">{{item.qty}}</td>
                        <td >{{item.subSendPrice}}</td>
                        <td align="center">{{item.sendPrice}}</td>
                        <td align="center">{{item.je}}</td>
                        <td align="center">{{item.subProvName}}</td>
                        
                    </tr>
                </table>
    </el-row>
</template>

<script>
    import $ from 'jquery';
    import '../../../common/third/printThis/printThis';
    import '../../../common/third/qrcode/jquery.qrcode.min';
    export default {
        data(){
            return {
                printDatas:[],
                sDate:'',eDate:''
            }
        },
        props:['goodsForPrint'],
        methods: {
            print(printDatas,sDate,eDate){
                this.printDatas = printDatas;
                this.sDate =sDate
                this.eDate=eDate
                $("#HosMonthInStock").printThis({
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
        mounted(){
        }
    }
</script>

<style>
#HosMonthInStock{
    padding: 10px 10px 10px 10px;
}
table.gridtable {
    font-family: verdana,arial,sans-serif;
    font-size:10px;
    color:#333333;
    border-width: 1px;
    border-color: #666666;
    border-collapse: collapse;
}
table.gridtable th {
    border-width: 1px;
    padding: 3px;
    border-style: solid;
    border-color: #666666;
    background-color: #dedede;
}
table.gridtable td {
    border-width: 1px;
    padding: 3px;
    border-style: solid;
    border-color: #666666;
    background-color: #ffffff;
}
</style>
