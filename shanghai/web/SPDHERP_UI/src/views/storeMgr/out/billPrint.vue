<template>
   <el-row :gutter="10" id="billPrintContent" class="printArea" v-show='false'>
     <div id="div1">
       <DIV style="LINE-HEIGHT: 30px" class=size16 align=center><STRONG><font size =5 color="#0000FF">国药集团四川省医疗器械有限公司出库单</font></STRONG></DIV>        
       <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
            <TBODY>
              <TR>
                <TD width="30%"><font color="#0000FF" size=1>收货单位：<SPAN> {{ currentOutStockBill.outOrgName }}</SPAN></font></TD>
                <TD width="30%"><font color="#0000FF" size=1>送货日期：<SPAN >{{ currentOutStockBill.auditDate | dateFormat('YYYY-MM-DD')}}</SPAN></font></TD>
                <TD width="20%"><font color="#0000FF" size=1>单号：<SPAN >{{ currentOutStockBill.billId }}</SPAN></font></TD>

               
                <TD align="right">
                    <span  tdata="pageNO" format="#"><b><font color="#0000FF" size=1>第#页</font>/</b></span>
                    <span  tdata="pageCount" format="#"><b><font color="#0000FF" size=1>共#页</font></b></span>
                  
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
                          <th width="15%"><font  size=1>产品名称</font></th>
                          <th width="8%"><font  size=1>规格型号</font></th>
                          <th width="13%"><font  size=1>生产企业</font></th>
                          <th width="10%"><font  size=1>批号</font></th>
                          <th width="8%"><font  size=1>产品效期</font></th>
                          <th width="5%"><font  size=1>单位</font></th>
                      
                          <th width="5%"><font  size=1>数量</font></th>
                          <th width="6%"><font  size=1>单价</font></th> 
                          <th width="8%"><font  size=1>金额</font></th>
                          <th width="13%"><font  size=1>注册证</font></th>
                    </tr>
            </thead>
            <tbody>
                      <tr v-for="(item,index) in currentOutStockBill.subList" :key="item.id">
                          <td align="center" ><font  size=1>{{ index+1 }}</font></td>
                          <td align="center" ><font  size=1>{{item.hitCode}}</font></td>
                          <td align="center" style="letter-spacing:1px"><font  size=1>{{item.goodsName}}</font></td>
                          <td align="center"  style="letter-spacing:1px"><font  size=1>{{item.goodsGg}}</font></td>
                          <td align="center"  style="letter-spacing:1px"><font  size=1>{{item.mfrsName}}</font></td>
                          <td align="center"><font  size=1>{{item.batchCode}}</font></td>
                          <td align="center"><font  size=1>{{item.expdtEndDate | dateFormat('YYYY-MM-DD')}}</font></td>
                          <td align="center"><font  size=1>{{item.unit}}</font></td>
                          <td align="center"><font  size=1>{{item.outQty}}</font></td>
                          <td align="center" style="letter-spacing:1px"><font  size=1>{{item.price}}</font></td>
                          <td align="center" style="letter-spacing:1px"><font  size=1>{{item.rowJe}}</font></td>          
                          <td align="center" style="letter-spacing:1px"><font  size=1>{{item.certificateCode}}</font></td>
                      </tr>
            </tbody>
            <tfoot>
                   <tr>
                        <td colspan=2 style=" border:0px"> 
                                        <span><font size=1>本页合计&nbsp;&nbsp;&nbsp;</font></span> 
                        </td>
                        <td colspan=4  style=" border:0px" > 
                                        <span tindex=11 tdata="SubSum" format="UpperMoney"><font size=1 >页小计金额（大写）：</font><font style="font-weight: bold "  size=1 >#</font></span>
                        </td>
                        <td colspan=4 style=" border:0px" > 
                                        <span  tindex=11 tdata="SubSum" format="###0.00"><font size=1>页金额 ：</font><font style="font-weight: bold "  size=1 >###元</font></span>
                        </td>
                        <td colspan=2 style=" border:0px" > 
                                        <span  tindex=9 tdata="SubSum" format="#0.00"><font size=1>页数量 ：#</font></span>
                        </td>
                   </tr>
                      <tr>
                        <td colspan=2 style=" border:0px"> 
                                       <span><font size=1>合计&nbsp;&nbsp;&nbsp;</font></span>
                            </td>
                            <td colspan=4  style=" border:0px" > 
                                        <span tindex=11 tdata="AllSum" format="UpperMoney"><font size=1>合计金额（大写）：</font><font style="font-weight: bold "  size=1 >#</font></span>
                            </td>
                            <td colspan=4 style=" border:0px" > 
                                            <span  tindex=11 tdata="AllSum" format="###0.00"><font size=1>合计金额 ：</font><font style="font-weight: bold "  size=1 >###元</font></span>
                            </td>
                            <td colspan=2 style=" border:0px" > 
                                       <span widtd=100 tindex=9 tdata="AllSum" format="#0.00"><font size=1>总数量 ：#</font></span>
                            </td>
                    </tr>
            </tfoot>
        </TABLE>        
     </div>
    
    <div id="div3">
      <DIV style="LINE-HEIGHT: 30px" 
       <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
              <TBODY>
                  <tr v-if="currentOutStockBill.outStockType === 20"  style="padding-top:2px">
                            <td colspan=3 style=" border:0px;padding-top:4px" align="left"> 
                                  <span ><font color="#0000FF" size=1>患者姓名 : {{ currentOutStockBill.patient }}</font></span>
                            </td>
                            <td colspan=3 style=" border:0px;padding-top:4px" align="left"> 
                                 <span ><font color="#0000FF" size=1>患者住院号 :{{ currentOutStockBill.patientName }}</font></span>
                            </td>
                            <td colspan=3 style=" border:0px;padding-top:4px" align="left"> 
                                  <span ><font color="#0000FF" size=1>患者床号 : {{ currentOutStockBill.patientBedNum }}</font></span>
                            </td>
                            <td colspan=3 style=" border:0px;padding-top:4px"  align="left"> 
                                    <span ><font color="#0000FF" size=1>住院科室 : {{ getDeptName(currentOutStockBill.patientDeptCode)  }}</font></span>
                            </td>
                  </tr>
                      <tr style="padding-top:2px">
                            <td colspan=3 style=" border:0px;padding-top:4px" align="left"> 
                                  <span ><font color="#0000FF" size=1>制单人：{{ currentOutStockBill.auditorName }}</font></span>
                            </td>
                            <td colspan=3  style=" border:0px;padding-top:4px" align="left"> 
                                  <span ><font color="#0000FF" size=1>复核员：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></span>
                            </td>
                            <td colspan=3 style=" border:0px;padding-top:4px" align="left"> 
                                  <span ><font color="#0000FF" size=1> 联系电话：{{ currentOutStockBill.tel }}</font></span>
                            </td>
                            <td colspan=3 style=" border:0px;padding-top:4px"  align="left"> 
                                  <span ><font color="#0000FF" size=1> 收货地址：{{ currentOutStockBill.recAddress }}</font></span>
                            </td>
                    </tr>
                    <tr ><!--
                            <td colspan=12 style=" border:0px" align="left"> 
                                  <span ><font color="#0000FF" size=1>说明:本清单一式4联，院方1联，
                                                                      其中白联：供方留存 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                      红联：财务 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                      蓝联：院方 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                      黄联：院方
                                         </font></span>
                            </td>
                          -->
                            <td colspan=9 style=" border:0px;padding-top:4px" align="left"> 
                                  <font color="#0000FF" size=1>说明:本清单一式4联，院方1联，
                                                                      其中白联：供方留存 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                      红联：财务 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                      蓝联：院方 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                  </font></span>
                            </td>
                            <td colspan=3 style=" border:0px;padding-top:4px"  align="left"> 
                                  <span ><font color="#0000FF" size=1> 黄联：院方</font></span>
                            </td>

                    </tr>
                      <tr >
                            <td colspan=1 style=" border:0px;padding-top:4px" align="left"> 
                                  <span ><font color="#0000FF" size=1>送货人签字：</font></span>
                            </td>
                             <td colspan=8 style=" border:0px;padding-top:4px" align="left"> 
                            </td>
                             <td colspan=3 style=" border:0px;padding-top:4px" align="left"> 
                                  <span ><font color="#0000FF" size=1>收货人签字：</font></span>
                            </td>
                    </tr>
                    <tr >
                            <td colspan=1 style=" border:0px;padding-top:4px" align="left" > 
                                  <span ><font color="#0000FF" size=1>时间：</font></span>
                            </td>
                             <td colspan=8 style=" border:0px;padding-top:4px" align="left"> 
                            </td>
                             <td colspan=3 style=" border:0px;padding-top:4px" align="left"> 
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
import moment from 'moment';
import $ from 'jquery';
import '../../../common/third/printThis/printThis';

import lodop from '../../../common/third/lodop/LodopFuncs';
import JsBarcode from 'jsbarcode';
export default {
  data () {
    return {
      baseURL: '/spdHERPService/stockMgr/out/billMgr',
      
      ownStockList: [],
      currentStock: '',
      currentDeptId: '',

      CKLXDict: {},
      CKDZTDict: {},
      CGMSDict: {},
      FYLXDict: { '10': '日常消耗' },

      orgsData:[],
      inDeptName:'',//领用部门
      currentOutStockBill: {
        subList: []
      },
      deptBuySub:[],
      totalAmout:'',
      bigTotalAmout:''
    }
  },
  computed: {
    goodsCount () {
      return new Set(this.currentOutStockBill.subList.map(o => o.goodsId)).size
    },
    goodsSum () {
      return this.currentOutStockBill.subList.reduce((acc, cur) => acc + cur.outQty, 0)
    },
    amount () {
      return this.currentOutStockBill.subList.reduce((acc, cur) => acc + (cur.outQty * cur.price), 0)
    }
  },
  methods: {
    print(thisOutStockBill,orgsData,deptBuySub,CKLXDict,CKDZTDict){
      //  JsBarcode("#pbarcode", thisOutStockBill.billId, { height: 30, displayValue: false });
        this.currentOutStockBill = thisOutStockBill;
        this.orgsData = orgsData;
         this.currentOutStockBill.subList.forEach(item=>{
          // alert((item.price*100)*item.outQty/100);
		  item.rowJe = (item.price*item.outQty).toFixed(2);

         });
        //alert(this.currentOutStockBill.subList.length)
        this.totalAmout  =  thisOutStockBill.subList.reduce((acc, cur) => acc + (cur.outQty * cur.price), 0)
        this.bigTotalAmout =this.smalltoBIG(this.totalAmout);
        this.deptBuySub = deptBuySub;
        this.CKLXDict = CKLXDict;
        this.CKDZTDict = CKDZTDict;

          setTimeout(this.callLodop,1000) 


       // this.getInDeptName(this.currentOutStockBill.inDeptId);
   
        // $("#billPrintContent").printThis({
        //     debug: false,
        //     importCSS: true,
        //     importStyle: true,
        //     printContainer: true,
        //     loadCSS: './static/style/print.css',
        //     pageTitle: this.user.corpName,
        //     removeInline: false,
        //     printDelay: 333,
        //     header: null,
        //     formValues: true
        // });
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
	      LODOP.ADD_PRINT_TABLE("18mm","1%","98%","9cm",strStyle+document.getElementById("div2").innerHTML);
        LODOP.SET_PRINT_STYLEA(0,"Vorient",3);		
    
        LODOP.ADD_PRINT_HTM("11.5cm","1%","98%","BottomMargin:1mm",document.getElementById("div3").innerHTML);
        LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	    	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);	
        LODOP.PREVIEW();	
    },
    
    getInDeptName(orgId){
      for(let i = 0;i<this.orgsData.length;i++){
        let item = this.orgsData[i];
        if(item.id==orgId){
          this.inDeptName = item.ename;
          break;
        }
      }
    },
    getStockList () {
      return new Promise((resolve, reject) => {
        this.axios.get(this.baseURL + '/getStockList').then(res => {
          if (res.data.code === 0) {
            resolve(res.data.data)
          } else {
            reject(res.data.msg)
          }
        })
      })
    },
    getDict (dict) {
      return new Promise((resolve, reject) => {
        this.axios.get(this.baseURL + '/getDict', {
          params: {
            dict
          }
        }).then(res => {
          if (res.data.code === 0) {
            let dicts = {}
            for (let item of res.data.data) {
              dicts[item.value] = item.name
            }
            resolve(dicts)
          } else {
            reject(res.data.msg)
          }
        })
      })
    },
    getBillDetail (billId) {
      return new Promise((resolve, reject) => {
        this.axios.get(this.baseURL + '/getBillDetail', {
          params: {
            id: billId
          }
        }).then(res => {
          if (res.data.code === 0) {
            resolve(res.data.data)
          } else {
            reject(res.data.msg)
          }
        }).catch(err => {
          reject(err)
        })
      })
    },
    typeFormatter (type) {
      return this.CGMSDict[type]
    },
    kindFormatter (kind) {
      return this.CKLXDict[kind]
    },
    statusFormatter (status) {
      return this.CKDZTDict[status]
    },
    getDeptName (deptId) {
      return this.orgsData.find( item => item.id === deptId ).ename
    },
    handleError (err, loading) {
      return new Promise((resolve, reject) => {
        this.$msgbox({ title: '错误', type: 'error', message: err }).then(action => {
          if (loading !== null && loading !== undefined) {
            loading = false
          }
          resolve(action)
        }).catch(err => {
          reject(err)
        })
      })
    },
     /** 数字金额大写转换(可以处理整数,小数,负数) */    
	 
     smalltoBIG(n)     
    {    
        var fraction = ['角', '分'];    
        var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];    
        var unit = [ ['元', '万', '亿'], ['', '拾', '佰', '仟']  ];    
        var head = n < 0? '欠': '';    
        n = Math.abs(n);    
      
        var s = '';    
      
        for (var i = 0; i < fraction.length; i++)     
        {    
            s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');    
        }    
        s = s || '整';    
        n = Math.floor(n);    
      
        for (var i = 0; i < unit[0].length && n > 0; i++)     
        {    
            var p = '';    
            for (var j = 0; j < unit[1].length && n > 0; j++)     
            {    
                p = digit[n % 10] + unit[1][j] + p;    
                n = Math.floor(n / 10);    
            }    
            s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')  + unit[0][i] + s;    
        }    
        return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');    
    },
	test (f, digit) {
    var m = Math.pow(10, digit);
    return parseInt(f * m, 10) / m;
	}
  },
 
  mounted () {
    // this.getStockList().then(result => {
    //   this.ownStockList = result
    //   if (result) {
    //     this.currentStock = result[0].id
    //   }
    //   this.getDict('CKLX').then(res => {
    //     this.CKLXDict = res
    //   })
    //   this.getDict('CKDZT').then(res => {
    //     this.CKDZTDict = res
    //     this.CKDZTDict['undone'] = '待处理'
    //     this.CKDZTDict['all'] = '全部'
    //   })
    //   this.getDict('CGMS').then(res => {
    //     this.CGMSDict = res
    //   })
    // }).catch(err => {
    //   this.handleError(err)
    // })
	
	//alert(200*9.8)
	
	//alert(this.test(200*9.8,2));
}
  
}
</script>

<style>
    #billPrintContent{
        padding: 10px 10px 10px 20px;
    }
    table.gridtable {
        font-family: verdana,arial,sans-serif;
        font-size:11px;
        color:#333333;
        border-width: 1px;
        border-color: #666666;
        border-collapse: collapse;
        width: 100%;
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
