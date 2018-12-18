<!--患者使用查询-->
<template>
    <div  style="height: 100%;" id="hosPatientGoodsQueryDiv">
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px; padding-left:5px; height: 99%;">
            <!--工具条-->
            <div style="padding-bottom: 1px;">
                <el-form :inline="true" :model="filters" label-width="100px" ref="goodsName">
                  
                  
                    <el-form-item label="患者住院号：">
                        <el-input v-model="filters.patientInHosId" :clearable="true" placeholder="输入患者住院号" ></el-input>
                    </el-form-item>
                    <el-form-item label="患者姓名：">
                        <el-input v-model="filters.patientName" :clearable="true" placeholder="输入患者姓名" ></el-input>
                    </el-form-item>
                    <el-button :inline="true"  type='info' @click="query">查询</el-button>
                    <el-button :inline="true"  type='info' @click="print">打印</el-button>
                    <el-button :inline="true"  type='info' @click="exportExcel">导出</el-button>
                </el-form>
            </div>
            <!--列表-->
            <template>
                <el-table ref="thisTable" :data="rowDatas"  row-key='id'
                            highlight-current-row v-loading="loading"  border fit 
                            class="deptgoods-tableheight" :row-class-name="tableRowClassName">
                    <el-table-column type="index" align="center"  width="50" label="序号"></el-table-column>
                      <el-table-column prop="billDate" header-align="center" align="center"  width="80" label="单据日期" >
                        <template slot-scope="scope">
                             {{scope.row.billDate | dateFormat('YYYY-MM-DD')}}
                        </template>
                    </el-table-column>
                    </el-table-column>
                    <el-table-column prop="goodsName"  header-align="center"  label="商品名称" min-width="120"></el-table-column>
                    <el-table-column prop="goodsGg" header-align="center" label="规格型号"  width="120"></el-table-column>
                    <el-table-column prop="unit" header-align="center" align="center" label="计量单位" width="60"></el-table-column> 
                    <el-table-column prop="outDeptName" align="center" label="出库部门" width="120"></el-table-column>
                    <el-table-column prop="patientDeptName" align="center" label="消耗部门" width="80"></el-table-column>
                    <el-table-column prop="price" header-align="center" align="center" label="出库单价" width="80"></el-table-column>
                    <el-table-column prop="qty" align="center" label="出库数量" width="80"></el-table-column>
                    <el-table-column prop="outAmount" header-align="center" align="center" label="出库金额" width="80"></el-table-column>                     
                </el-table>
                <!--分业工具栏-->
                <el-col :span="24" class="toolbar pageBar">
                    <el-pagination @size-change="handleSizeChange"
                                    @current-change="handleCurrentChange" :current-page="page"
                                    :page-sizes="[50, 100, 200, 400]" :page-size="pageSize" :total="total"
                                    layout="total, sizes, prev, pager, next" style="float:right;" small>
                    </el-pagination>
                </el-col>
            </template>
            <outStockQueryPrint ref="purPrintD"></outStockQueryPrint>

        </el-col>
    </div>

</template>
<script>
    import outStockQueryPrint from './components/hosPatientGoodsPrint';
    import moment from 'moment';
    import $ from 'jquery';
    import '../../common/third/tableExport/tableExport';
    import '../../common/third/tableExport/jquery.base64';
    export default {
        data() {
            return {
                baseURL: '/spdHERPService/stockMgr/out/billMgr',
                hosId:'',
                deptId:'',
                hosName:'',
                deptName:'',
                userDepts:[],
                filters: { 
                     patientInHosId:'',
                     patientName:''
                },
                total: 0,
                page: 1,
                pageSize: 50,
                loading: false,
                rowDatas: [],
                printDatas:[],
                tableModel:[],
            }
        },
        watch:{
        },
        components: { outStockQueryPrint },
        methods: {
            //获取患者使用产品列表
            hosPatientGoodsQuery: function (pIndex) {
                this.page = pIndex;
                var param = {
                    orderBy: "",
                    queryObject: {
                        hosId:this.hosId,
                        patientInHosId:this.filters.patientInHosId,
                        patientName:this.filters.patientName
                    },
                    page: this.page,
                    rows: this.pageSize
                };
                this.loading = true;
                this.axios.post('/spdHERPService/reportAnalysis/reportStatistics/getHosPatientGoodsQuery', param).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
                        this.rowDatas = res.data.data.data;
                        this.total = res.data.data.total;
                        this.rowDatas.forEach(o =>{
                            o.outAmount = o.qty*o.price
                        })
                        
                    }else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.loading = false;
                });
            },
     
            query : function (){
                if(this.filters.patientInHosId==''&&this.filters.patientName==''){
                     this.$message.error("请输入条件！");
                     return
                }
                this.hosPatientGoodsQuery(1);
                this.getPrintData();
            },
           print:function() {
            //   alert("print")
                this.$refs.purPrintD.print(this.printDatas,this.filters);
            },
                 /**获取打印或导出数据 */
            getPrintData:function(){
               var param = {
                    orderBy: "",
                    queryObject: {
                        hosId:this.hosId,
                        patientInHosId:this.filters.patientInHosId
                    },
                    page: this.page,
                    rows: this.pageSize
                };
                this.axios.post('/spdHERPService/reportAnalysis/reportStatistics/getHosPatientGoodsQuery', param).then(res => {
                    if (res.data.code == "0") {
                        var temp = res.data.data.data;                      
                        this.printDatas = temp;
                         this.printDatas.forEach(o =>{
                            o.outAmount = o.qty*o.price
                            o.billDate= moment(o.billDate).format("YYYY-MM-DD")
                        })
                    }else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                });
            },
              /**导出excel */
            exportExcel:function(){
               let tableHtml = '<table><thead><tr><th>序号</th><th>单据日期</th>'
                +'<th>商品名称</th><th>规格型号</th><th>计量单位</th>'
                +'<th>出库部门</th><th>消耗部门</th><th>出库单价</th>'
                +'<th>出库数量</th><th>总金额</th></tr></thead><tbody>';
               if(this.printDatas.length>0){
                    //this.$refs.purPrintD.print(res.data.data.data,this.filters);
                    this.printDatas.forEach(function(item,index){
                        tableHtml+='<tr>'
                        +'<td>'+(index+1)+'</td>'
                        +'<td>'+item.billDate+'</td>'
                        +'<td>'+item.goodsName+'</td>'
                        +'<td>'+item.goodsGg+'</td>'
                        +'<td>'+item.unit+'</td>'
                        +'<td>'+item.outDeptName+'</td>'
                        +'<td>'+item.patientDeptName+'</td>'
                        +'<td>'+item.price+'</td>'
                        +'<td>'+item.qty+'</td>'
                        +'<td>'+item.outAmount+'</td>'
                        +'</tr>'
                    });
                    tableHtml+='</tbody></table>';
                    $(tableHtml).tableExport({type:'excel',
                        escape:'false',
                        tableName:this.deptName+'收货汇总'
                    });
                }else{
                    this.$message.error('无数据导出！');
                    return false;
               }
            },

            /**行样式 */
            tableRowClassName (row, index) {
                // return (row.monthQty > row.mQtyUpper) ? 'row-disabled-red-nobg' : ''
            },
            /**分业处理事件 */
            handleCurrentChange : function(val) {
                this.page = val;
                this.hosPatientGoodsQuery(this.page);
            },
            handleSizeChange:function(val) {
                this.pageSize = val;
                this.hosPatientGoodsQuery(this.page);
            },
        
        },
        mounted() {
            this.deptId = this.user.orgId,//科室Id
            this.hosId = this.user.corpId;//单位id
            this.hosName = this.user.corpName;
            this.deptName = this.user.orgName;        
        
         //   this.getUserDepts();
           // this.getPrintData();
        }
    };

</script>
<style>
    #hosOutStocGoodsQueryDiv .el-checkbox__label{
        font-size: 12px;
        vertical-align: middle;
    }
    #hosOutStocGoodsQueryDiv .el-checkbox-group{
        vertical-align: middle;
    }
    #hosOutStocGoodsQueryDiv .deptgoods-tableheight {
        width: 100%;
        height:  calc(100% - 90px);
    }
    #hosOutStocGoodsQueryDiv .deptGoods{
        border-radius: 6px;
        padding-bottom: 10px;
        padding-top: 12px;
        padding-right: 19px;
        border: 1px solid #d1dbe5;
        padding-left: 0px;
        margin-left: 15px;
        margin-right: 15px;
    }
    #hosOutStocGoodsQueryDiv .deptGoods .el-row{
        height: 36px;
    }
    
    #hosOutStocGoodsQueryDiv .pageBar{
        border-bottom: 1px solid #d1dbe5;
        border-left: 1px solid #d1dbe5;
        border-right: 1px solid #d1dbe5;
    }
    

</style>