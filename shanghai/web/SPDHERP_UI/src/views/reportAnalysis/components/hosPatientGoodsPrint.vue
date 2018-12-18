<template>
    <el-row :gutter="10" id="printContent" class="printArea"  v-show='false'>
        <el-col :span="24" align="center" style="padding-bottom:10px">
            
            <span><b>患者使用信息</b></span>
        </el-col>
        
        <table class="gridtable">
            <tr>
                <th width=200>单据日期</th>
             
                <th width=150>商品名称</th>
                <th width=40>规格型号</th>
                <th width=80>计量单位</th>
                <th width=60>出库部门</th>
                <th width=60>消耗部门</th>
                <th width=40>出库单价</th>
                <th width=40>出库数量</th>
                <th width=40>出库金额</th>
            </tr>
            <tr v-for="item in rowDatas" >
                <td>{{item.billDate|dateFormat('YYYY-MM-DD')}}</td>
                <td>{{item.goodsName}}</td>
                <td>{{item.goodsGg}}</td>
                <td>{{item.unit}}</td>
                <td>{{item.outDeptName}}</td>
                <td>{{item.patientDeptName}}</td>
                <td>{{item.price}}</td>
                <td>{{item.qty}}</td>
                <td>{{item.outAmount}}</td>
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
          printOrder:{
              deptName:'',
              ownExpense:'',
              financialAppropriation:'',
              donation:'',
              researchFee:'',
              total:''
          },
        rowDatas: [],
        /**过滤条件 */
        filters: { 
                     patientInHosId:'pzyy-001',
                     patientName:''
                },
      }
  },

  methods:{
        print(datas,filters) {
            this.rowDatas = datas;
            this.filters = filters;
            $("#printContent").printThis({
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
        }
  }
}
</script>
>

<style>
#printContent {
  padding: 10px 10px 10px 10px;
}
table.gridtable {
  font-family: verdana, arial, sans-serif;
  font-size: 11px;
  color: #333333;
  border-width: 1px;
  border-color: #666666;
  border-collapse: collapse;
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
  text-align: center;
}
</style>