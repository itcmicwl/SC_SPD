<template>
  <div id="uniqueCode" style="height: 90%;">
    <el-row style="height:35px">
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px; padding-left:5px; height: 99%;">
      <div style="padding-bottom: 1px; ">
        <el-form :inline="true" :models="filters" labelWidth="80px" ref="disBillId">
          <el-form-item label="配送单号" prop="billId">
            <el-input v-model="filters.billId" :clearable="true" placeholder="请输入配送单号"></el-input>
          </el-form-item>
          <el-button-group>
            <el-button type="primary" v-on:click="createCode" >生成唯一码</el-button>
            <el-button type="primary" v-on:click="printWym" >打印</el-button>
          </el-button-group>
        </el-form>
      </div>
    </el-col>
    </el-row>
   <div>
                <el-col :span="24" style="height:100%; overflow:auto;" id="printView-unique">
                    <template v-for="(o,index) in uniqueVoList">
                        <div class="printBarcode"  :key="o.packageId">
                            <div class="barDesc">
                              &nbsp;&nbsp;名称：{{d.goodsName}}<br/>
                              &nbsp;&nbsp;规格：{{d.goodsGg}}<br/>
                              &nbsp;&nbsp;效期：{{d.expdtEndDate}}
                              &nbsp;&nbsp;批号：{{d.batchCode}}<br/>
                            </div>
                            <div style="height:120px;">
                                <svg :id='"barcode"+d.uniqueCode'></svg>
                            </div>
                        </div>
                    </template> 
                </el-col>
  </div>






 <!--     <div  style="height: 100%;">
          <el-col :span="24" style="height: 900px">
             <div v-for="d in uniqueVoList" class="printBarCode" :key="d.uniqueCode" >
              <el-row>
                <el-col :span="24"> <span class="printBarcode">名称：{{d.goodsName}}</span></el-col>
              </el-row>
              <el-row>
                <el-col :span="24"><span class="printBarcode">规格：{{d.goodsGg}}</span></el-col>
              </el-row>
              <el-row>
                <el-col :span="12"><span class="printBarcode">效期：{{d.expdtEndDate}}</span> </el-col>
                <el-col :span="12"> <span class="printBarcode">批号：{{d.batchCode}}</span></el-col>
              </el-row>

              <svg :id='"barcode"+d.uniqueCode'></svg>

             </div>
          </el-col>
      </div>-->

  </div>
</template>
<script>
  import moment from 'moment';
  import $ from 'jquery';
  import '../../common/third/printThis/printThis';
  import '../../common/third/qrcode/jquery.qrcode.min';
  import JsBarcode from 'jsbarcode';
  export default {
    data(){
          return{
            filters:{
              billId:''
            },
            distrBillVo:{},
            uniqueVoList:[],


            loading: false
          }
      },
    methods:{
      printWym() {
        $("#printView-unique").printThis({
          debug: false,
          importCSS: true,
          imporSttyle: true,
          printContainer: true,
          loadCSS: './static/style/print.css',
          pageTitle: "唯一码",
          removeInline: false,
          printDelay: 333,
          header: null,
          formValues: true
        });
      },
      createCode:function () {
              var param = {billId: this.filters.billId };
              this.loading=true;
              let temp =[];
        this.axios.post('/spdHERPService/barcodePrintingCenter/uniqueCode/create',param).then(res=>{
            if(res.data.code=='0'){
                this.distrBillVo =res.data.data
              //获取配送单中需要打印的内容
              let sys_billList =  res.data.data.distrBillList.filter(sub=>{return sub.uniqueKind ==0})
              sys_billList.forEach(s=>{
           //     alert(s.hosGoodsName)
           //   var   unique_s =  s.distrBillUniqueList.map(u=>{u.goodsName =s.hosGoodsName})
            //    alert(unique_s.length)
                var   unique_s =[];
                s.distrBillUniqueList.forEach(u=>{
                   u.goodsName =s.hosGoodsName
                   u.goodsGg = s.goodsGg
                   u.expdtEndDate = s.expdtEndDate
                  unique_s.push(u)
                })
              temp =  temp.concat( unique_s)
              })
              this.uniqueVoList = temp;
              this.$nextTick(function () {
                this.uniqueVoList.forEach(u=>{

                  JsBarcode("#barcode"+u.uniqueCode , u.uniqueCode, { height: 50, displayValue: false });
                })
              })



            }else{
              this.$message.error(res.data.msg)
            }
        },err =>{
            this.loading=false
          }
        )

      }
    },
    mounted() {
    }
  }
</script>
<style>
 #uniqueCode .printBarcode{
    font-size: xx-small;
    color: black;
    margin-bottom: 45px;
  }
  #uniqueCode .barDesc{
    margin-left: 9px;
    margin-bottom: -5px;
    height: 55px;
  }
</style>
