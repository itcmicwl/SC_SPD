<template>
   <el-row :gutter="10" id="distBillPrintViewPrint" class="printArea" v-show='false'>
     <div id="div1">
       <DIV style="LINE-HEIGHT: 30px" class=size16 align=center><STRONG><font size =5 color="#0000FF">{{distrBill.subProvName}}配送单</font></STRONG></DIV>
       <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
            <TBODY>
              <TR>
                <TD width="40%"><font color="#0000FF" size=1>收货单位：<SPAN v-if="distrBill.provId != distrBill.subProvId"> {{ distrBill.provName }}</SPAN><SPAN v-if="distrBill.provId == distrBill.subProvId"> {{ distrBill.purchaseCompanyName }}</SPAN></font></TD>
                <TD width="20%"><font color="#0000FF" size=1>送货日期：<SPAN >{{ distrBill.fillDate | dateFormat('YYYY-MM-DD')}}</SPAN></font></TD>
                <TD width="20%"><font color="#0000FF" size=1>单号：<SPAN >{{ distrBill.billId }}</SPAN></font></TD>


                <TD align="right">
                    <span  tdata="pageNO" format="#"><b><font size=1>第&nbsp;&nbsp;</font><font color="#0000FF" size=1>#</font><font size=1>&nbsp;&nbsp;页/</font></b></span>
                    <span  tdata="pageCount" format="#"><b><font size=1>共&nbsp;&nbsp;</font><font color="#0000FF" size=1>#</font><font size=1>&nbsp;&nbsp;页</font></b></span>

               </TD>

              </TR>

            </TBODY>
       </TABLE>
     </div>
     <div id='div2'>
       <TABLE border=1 cellSpacing=0 cellPadding=1 width="100%" style="border-collapse:collapse" bordercolor="#333333">
            <thead>

                  <tr>
                          <th width="4%"><font  size=1>序号</font></th>
                          <th width="5%"><font  size=1>流水号</font></th>
                          <th width="10%"><font  size=1>产品名称</font></th>
                          <th width="8%"><font  size=1>规格型号</font></th>
                          <th width="11%"><font  size=1>生产企业</font></th>
                          <th width="8%"><font  size=1>批号</font></th>
                          <th width="9%"><font  size=1>产品效期</font></th>
                          <th width="9%"><font  size=1>生产日期</font></th>
                          <th width="5%"><font  size=1>单位</font></th>
                          <th width="5%"><font  size=1>数量</font></th>
                          <th width="6%"><font  size=1>单价</font></th> 
                          <th width="8%"><font  size=1>金额</font></th>
                          <th width="12%"><font  size=1>注册证</font></th>
                    </tr>
            </thead>
            <tbody>
                      <tr v-for="(item,index) in distrBill.distrBillList" :key="item.id">
                          <td  align="center"><font  size=1>{{ index+1 }}</font></td>
                          <td  align="center"><font  size=1>{{item.hitCode}}</font></td>
                          <td  align="center"><font  size=1>{{item.provGoodsErpName}}</font></td>
                          <td  align="center"><font  size=1>{{item.goodsGg}}</font></td>
                          <td  align="center"><font  size=1>{{item.goodsMrfsName}}</font></td>
                          <td  align="center"><font  size=1>{{item.batchCode}}</font></td>
                          <td  align="center"><font  size=1>{{item.expdtEndDate | dateFormat('YYYY-MM-DD')}}</font></td>
                          <td  align="center" style="letter-spacing:1px"><font  size=1>{{item.productDate | dateFormat('YYYY-MM-DD')}}</font></td>
                          <td  align="center"><font  size=1>{{item.sendUnit}}</font></td>
                          <td  align="center"><font  size=1>{{item.sendQty}}</font></td>
                          <td  align="center"><font  size=1>{{item.subSendPrice}}</font></td>
                          <td  align="center"><font  size=1>{{item.rowJe}}</font></td>
                          <td  align="center"><font  size=1>{{item.certificateCode}}</font></td>
                      </tr>
            </tbody>
             <tfoot>
                   <tr>
                        <td colspan=2 style=" border:0px"> 
                                        <span><font size=1>本页合计&nbsp;&nbsp;&nbsp;</font></span> 
                        </td>
                        <td colspan=4  style=" border:0px" > 
                                        <span tindex=12 tdata="SubSum" format="UpperMoney"><font size=1 >页小计金额（大写）：</font><font style="font-weight: bold "  size=1 >#</font></span>
                        </td>
                        <td colspan=4 style=" border:0px" > 
                                        <span  tindex=12 tdata="SubSum" format="#,##0.00"><font size=1>页金额 ：</font><font style="font-weight: bold "  size=1 >###元</font></span>
                        </td>
                        <td colspan=2 style=" border:0px" > 
                                        <span  tindex=10 tdata="SubSum" format="#"><font size=1>页数量 ：#</font></span>
                        </td>
                   </tr>
                      <tr>
                        <td colspan=2 style=" border:0px"> 
                                       <span><font size=1>合计&nbsp;&nbsp;&nbsp;</font></span>
                            </td>
                            <td colspan=4  style=" border:0px" > 
                                        <span tindex=12 tdata="AllSum" format="UpperMoney"><font size=1>合计金额（大写）：</font><font style="font-weight: bold "  size=1 >#</font></span>
                            </td>
                            <td colspan=4 style=" border:0px" > 
                                            <span  tindex=12 tdata="AllSum" format="#,##0.00"><font size=1>合计金额 ：</font><font style="font-weight: bold "  size=1 >###元</font></span>
                            </td>
                            <td colspan=2 style=" border:0px" > 
                                       <span widtd=100 tindex=10 tdata="AllSum" format="#"><font size=1>总数量 ：#</font></span>
                            </td>
                    </tr>
            </tfoot>
        </TABLE>
     </div>

    <div id="div3">
      <DIV style="LINE-HEIGHT: 30px"
       <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
              <TBODY>
                   <tr>
                            <td colspan=3 style=" border:0px" align="left"> 
                                  <span ><font color="#0000FF" size=1>制单人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></span>
                            </td>
                            <td colspan=3  style=" border:0px" align="left"> 
                                  <span ><font color="#0000FF" size=1>复核员：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></span>
                            </td>
                            <td colspan=3 style=" border:0px" align="left"> 
                                  <span ><font color="#0000FF" size=1> 联系电话：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></span>
                            </td>
                            <td colspan=3 style=" border:0px"  align="left"> 
                                  <span ><font color="#0000FF" size=1> 收货地址：{{recAddress}}</font></span>
                            </td>
                    </tr>
               

                   <tr><!--
                            <td colspan=12 style=" border:0px" align="left"> 
                                  <span ><font color="#0000FF" size=1>说明:本清单一式4联，院方1联，
                                                                      其中白联：供方留存 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                      红联：财务 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                      蓝联：院方 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                      黄联：院方
                                         </font></span>
                            </td>
                          -->
                            <td colspan=9 style=" border:0px" align="left"> 
                                  <font color="#0000FF" size=1>说明:本清单一式4联，院方1联，
                                                                      其中白联：供方留存 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                      红联：财务 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                      蓝联：院方 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                  </font></span>
                            </td>
                            <td colspan=3 style=" border:0px"  align="left"> 
                                  <span ><font color="#0000FF" size=1> 黄联：院方</font></span>
                            </td>

                    </tr>
                  <tr>
                            <td colspan=1 style=" border:0px" align="left"> 
                                  <span ><font color="#0000FF" size=1>送货人签字：</font></span>
                            </td>
                             <td colspan=8 style=" border:0px" align="left"> 
                            </td>
                             <td colspan=3 style=" border:0px" align="left"> 
                                  <span ><font color="#0000FF" size=1>收货人签字：</font></span>
                            </td>
                    </tr>
                    <tr>
                            <td colspan=1 style=" border:0px" align="left"> 
                                  <span ><font color="#0000FF" size=1>时间：</font></span>
                            </td>
                             <td colspan=8 style=" border:0px" align="left"> 
                            </td>
                             <td colspan=3 style=" border:0px" align="left"> 
                                  <span ><font color="#0000FF" size=1>时间：</font></span>
                            </td>
                    </tr>
                </TBODY>
          </TABLE>
      </DIV>
    </div>






   </el-row>
</template>
<script>

import $ from 'jquery';
import '../../common/third/printThis/printThis';
import lodop from '../../common/third/lodop/LodopFuncs';

import '../../common/third/qrcode/jquery.qrcode.min';
import JsBarcode from 'jsbarcode';
export default {
    data() {
        return {
            recAddress:'',
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
              //  JsBarcode("#pbarcode", distrBill.billId, { height: 30, displayValue: false });
               this.distrBill = distrBill;
                   this.distrBill.distrBillList.forEach(item=>{
		                     item.rowJe = (item.subSendPrice*item.sendQty).toFixed(2);
                 });
               this.recAddress =this.getRecAddress(distrBill.distrBillList[0].purconfirmBillId);
            }
            setTimeout(this.callLodop,5000)

        },
        callLodop(){
            var LODOP =lodop.getLodop();
            LODOP.PRINT_INIT("gmy")
		    var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}</style>"
            LODOP.SET_PRINT_PAGESIZE(1, '21cm','14cm','B5')
            LODOP.ADD_PRINT_HTM("5mm","1%","98%","2cm",document.getElementById("div1").innerHTML);
            LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
            LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);
       // var strResult=LODOP.FORMAT("UpperMoney","1567.48");
	        LODOP.ADD_PRINT_TABLE("18mm","1%","98%","10cm",strStyle+document.getElementById("div2").innerHTML);
            LODOP.SET_PRINT_STYLEA(0,"Vorient",3);

            LODOP.ADD_PRINT_HTM("12.5cm","1%","98%","BottomMargin:1mm",document.getElementById("div3").innerHTML);
            LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
            LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);
            LODOP.PREVIEW();
       },
       getRecAddress(id){
          this.axios.get(`/spdHDIService/orderMgr/distrBill/getRecAddress/`+id).then(res => {
                this.recAddress = res.data.data;
            }, err => {});
       }


    },
    mounted() {

    }
};

</script>

<style scoped>
#distBillPrintViewPrint{
    padding: 10px 10px 10px 20px;
}

.spanwidth{
width:150px;height:100px;
border:1px solid #000;
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