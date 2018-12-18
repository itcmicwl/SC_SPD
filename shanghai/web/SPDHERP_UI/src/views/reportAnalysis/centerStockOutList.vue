<!--一级库出库明细查询-->
<template>
    <div  style="height: 100%;" id="centerStockOutQuery" @keyup.enter="getGoodsList(1)">
      
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px; padding-left:5px; height: 99%;">
            <!--工具条-->
            <div style="padding-bottom: 1px;">
                <el-form :inline="true" :model="filters" label-width="100px" ref="goodsName">

                    <el-form-item label="集配商:">
                            <el-select v-model="filters.provId" filterable placeholder="请选择"  @change="provChange">
                                <el-option v-for="item in colProvLst" :key="item.id" :label="item.cname" :value="item.id">
                                </el-option>
                            </el-select>
                    </el-form-item>
                    <el-form-item label="出库日期范围：">
                        <el-date-picker v-model="filters.sDate" unlink-panels type="daterange" align="right" 
                         unlink-panels range-separator="至" start-placeholder="开始日期"  @change="selectDateRangChange"
                         end-placeholder="结束日期" :picker-options="pickerOptions2"  :clearable="false" :editable='false'>
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="">
                        <el-select style="width:80px;" v-model="filters.provType" placeholder="供应商类型" >
                            <el-option label="全部" value=""/>
                            <el-option label="直销" value="0"/>
                            <el-option label="集配" value="1"/>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="">
                        <el-select style="width:80px;" v-model="filters.status" placeholder="出库单状态" >
                            <el-option v-for="(value, key) in CKDZTDict" :key="key" :label="value" :value="key">
                            </el-option>
                        </el-select>
                    </el-form-item>

                <!-- 
                    <el-form-item label="产品名称/规格：" prop="goodsName">
                        <el-input v-model="filters.goodsName" :clearable="true" placeholder="输入产品名称或规格" ></el-input>
                    </el-form-item>
                    -->

                    <el-button-group>
                        <el-button type="primary" v-on:click="getGoodsList(1)" >查询</el-button>
                        <el-button type="primary" v-on:click="resetGoodsName('goodsName')" >重置</el-button>
                        <el-button type="info"  @click="getGoodsListForPrint">打印</el-button>
                        <el-button type="info"  @click="exportExcel">导出</el-button>
                    </el-button-group>
                </el-form>
            </div>
            <template>
                <el-table ref="goodsTable" :data="goods"  row-key='id'
                            highlight-current-row v-loading="loading"  border fit 
                            class="deptgoods-tableheight"  :row-class-name="tableRowClassName">

                    <el-table-column type="index" align="center"  width="40" label="序号"></el-table-column>
                    <el-table-column prop="billDate"  sortable header-align="center" min-width="85" label="出库日期" >
                      <template slot-scope="scope">
                             {{scope.row.billDate | dateFormat('YYYY-MM-DD')}}
                        </template>
                    </el-table-column>
					<el-table-column prop="billId" header-align="center" label="出库单号" width="110"></el-table-column>
                    <el-table-column prop="distrBillId" header-align="center" label="配送单号" width="150"></el-table-column>
                    <el-table-column prop="inOrgName" header-align="center" label="医疗机构名称" width="150"></el-table-column>
                    <el-table-column prop="outDeptName" align="center" label="出库科室" min-width="80" ></el-table-column>
                    <!-- <el-table-column prop="status" label="出库单状态" :formatter="statusFormatter"></el-table-column> -->
                    <el-table-column prop="statusName" label="出库单状态" ></el-table-column>
                    <!-- <el-table-column prop="provGoodsId" sortable header-align="center" align="center" label="商品编号" min-width="120" ></el-table-column> -->
                    <el-table-column prop="erpCode" header-align="center" align="center" label="ERP编码" min-width="100" ></el-table-column>
                    <el-table-column prop="goodsName" align="center"    label="商品名" width="200"></el-table-column>
                    <el-table-column prop="goodsGg" header-align="center" min-width="100" label="规格" ></el-table-column>
                    <el-table-column prop="mfrsName" header-align="center" min-width="200" label="厂家" ></el-table-column>
                    <el-table-column prop="made" header-align="center" min-width="100" label="产地" ></el-table-column>
                    <el-table-column prop="unit" header-align="center" align="center" label="单位" min-width="50"></el-table-column>
                    <el-table-column prop="batchCode" align="center" label="批号" min-width="100"></el-table-column>
                    <el-table-column prop="expdtEndDate" header-align="center" align="center" label="效期" min-width="80">
                         <template slot-scope="scope">
                               {{scope.row.expdtEndDate | dateFormat('YYYY-MM-DD')}}
                        </template>
                    </el-table-column>
                    
                    <el-table-column prop="qty" align="center"  min-width="80" label="数量"></el-table-column>
                    <el-table-column prop="subSendPrice" header-align="center" min-width="80" label="采购价" ></el-table-column>
                    <el-table-column prop="sendPrice" header-align="center" label="销售价" min-width="80"></el-table-column>
                    <el-table-column prop="je" align="center" label="金额" min-width="120"></el-table-column>
                    <el-table-column prop="subProvName" sortable header-align="center" align="center" label="供应商" min-width="200"></el-table-column>
                
                </el-table>
                <el-col :span="24" class="toolbar pageBar">
                    <el-pagination @size-change="handleSizeChange"
                                    @current-change="handleCurrentChange" :current-page="page"
                                    :page-sizes="[1000,1500,2000,2500]" :page-size="pageSize" :total="total"
                                    layout="total, sizes, prev, pager, next" style="float:right;" small>
                    </el-pagination>
                </el-col>
            </template>
        </el-col>
        <centerStockOutListPrint ref="purPrintD" ></centerStockOutListPrint>
    </div>

</template>
<script>
import centerStockOutListPrint from './components/centerStockOutListPrint';
import $ from 'jquery';
import '../../common/third/tableExport/tableExport';
import '../../common/third/tableExport/jquery.base64';
    export default {
        data() {
            return {
                hosId:'',
                hosName:'',
                deptName:'',
                userDepts:[],
                colProvLst: [],
                sltProv: {},
                //默认选中当前月
                filters: { 
                    goodsName: '' ,
                    orgName:'',
                    sDate:[],
                    status:'',
                    provType: ''
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

                CKDZTDict: {},
                total: 0,
                page: 1,
                pageSize: 1000,
                loading: false,
                goods: [],
                goodsForPrint:[],
                good: {mfrsId:''},

                tableModel:[],
                // 树
                expandIds: [],
                orgInfo: [],
                orgInfoProps: {
                    children: 'children',
                    label: 'label',
                    id: 'id',
                    pid: 'pid'
                },
                // 快速查询
                orgName:''
            }
        },
      /*  watch:{
            orgName(val) {
                this.$refs.orgInfoTree.filter(val);
            }
        },*/
        components: { centerStockOutListPrint },
        methods: {

             provChange(provId) {
                   this.sltProv = this.colProvLst.find(item => item.id == provId);
                  // this.getOutBillList(1);
            },
             getColProvLst() {
                this.loading = true;
                this.axios
                    .post(
                    "/spdHERPService/provMgr/distriRelation/getCollectorList/" +
                        this.hosId,
                    )
                    .then(
                    res => {
                        this.loading = false;
                        this.colProvLst = res.data.data;
                        this.filters.provId=this.colProvLst[0].id;
                        this.sltProv.id = this.filters.provId
                         this.getGoodsList(1);//selectDateRangChange事件里触发查询
                      
                    },
                    err => {
                        this.loading = false;
                    }
                    );
                },
             print () {
                this.$refs.purPrintD.print(this.goodsForPrint,this.filters.sDate,this.filters.eDate, this.filters.status)
            },
            filterNode(value, data) {
                if (!value) return true;
                return data.label.indexOf(value) !== -1;
            },
             
      
            exportExcel:function(){
               let tableHtml = '<table><thead><tr><th>序号</th>'
                +'<th>出库日期</th><th>医疗机构名称</th><th>出库科室</th>'
                +'<th>出库单状态</th>'
                +'<th>ERP编码</th><th>商品名</th><th>商品规格</th>'
                +'<th>厂家</th><th>产地</th><th>批号</th><th>单位</th>'
                +'<th>效期</th><th>数量</th><th>采购价</th>'
                +'<th>销售价</th><th>金额</th><th>供应商</th>'

                +'</tr></thead><tbody>';
               if(this.goodsForPrint.length>0){
                    //this.$refs.purPrintD.print(res.data.data.data,this.filters);
                    this.goodsForPrint.forEach(function(item,index){
                        tableHtml+='<tr>'
                        +'<td>'+(index+1)+'</td>'
                        +'<td>'+item.billDate+'</td>'
                        +'<td>'+item.inOrgName+'</td>'
                        +'<td>'+item.outDeptName+'</td>'
                        +'<td>'+ item.statusName +'</td>'
                        //+'<td>'+item.provGoodsId+'</td>'
                        +'<td>'+item.erpCode+'</td>'
                        +'<td>'+item.goodsName+'</td>'
                        +'<td>'+item.goodsGg+'</td>'
                        +'<td>'+item.mfrsName+'</td>'
                        +'<td>'+item.made+'</td>'
                        +'<td>'+item.batchCode+'</td>'
                        +'<td>'+item.unit+'</td>'
                        +'<td>'+item.expdtEndDate+'</td>'
                        +'<td>'+item.qty+'</td>'
                        +'<td>'+item.subSendPrice+'</td>'
                        +'<td>'+item.sendPrice+'</td>'
                        +'<td>'+item.je+'</td>'
                        +'<td>'+item.subProvName+'</td>'
                        +'</tr>'
                    });
                    tableHtml+='</tbody></table>';
                    $(tableHtml).tableExport({type:'excel',
                        escape:'false',
                       // tableName:this.deptName+'收货汇总'
                    });
                }else{
                    this.$message.error('无数据导出！');
                    return false;
               }
            },
     
      
            query : function (){
                this.getHosMonthGoodsReceiptQuery(1);
            },
         
            /**月份选择事件 */
            selectDateRangChange:function(){
                this.$refs.goodsTable.clearSelection();
                this.getGoodsList(1);
            },
            /**表格行样式渲染 */
            tableRowClassName (row, index) {
                 return (row.row.monthQty > row.row.mQtyUpper) ? 'red-row' : ''
            },
            //获取列表数据
            getGoodsList: function (pIndex) {
                this.page = pIndex;
                var param = {
                    orderBy: "",
                    queryObject: {
                        goodsName: this.filters.goodsName,
                        inOrgId:this.hosId,
                        provId: this.sltProv.id,
                        sDate: this.filters.sDate?this.filters.sDate[0]:null,
                        eDate: this.filters.sDate?this.filters.sDate[1]:null,
                        status: this.filters.status,
                        provType: this.filters.provType
                    },
                    page: this.page,
                    rows: this.pageSize
                };
                this.loading = true;
                this.axios.post('/spdHERPService/reportAnalysis/reportStatistics/getCenterStockOutList', param).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
                        this.goods = res.data.data.data;
                        this.goods.forEach(o=>{
                            o.je= (o.qty*o.sendPrice).toFixed(2);
                            o.statusName = this.CKDZTDict[o.status];
                        })
                        this.goodsForPrint= this.goods;
                        this.total = res.data.data.total;
                    }else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.loading = false;
                });
            },

            //获取列表数据打印列表数据
            getGoodsListForPrint: function () {
                var param = {
                    orderBy: "",
                    queryObject: {
                        goodsName: this.filters.goodsName,
                        inOrgId:this.hosId,
                        provId: this.sltProv.id,
                        sDate: this.filters.sDate?this.filters.sDate[0]:null,
                        eDate: this.filters.sDate?this.filters.sDate[1]:null,
                        status: this.filters.status,
                        provType: this.filters.provType
                    },
                };
                this.loading = true;
                var that = this;
                this.axios.post('/spdHERPService/reportAnalysis/reportStatistics/getCenterStockOutList', param).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
                       
                        this.goodsForPrint = res.data.data.data;
                        this.goodsForPrint.forEach(o=>{
                            o.je=o.qty*o.sendPrice;
                            o.statusName = that.CKDZTDict[o.status];
                        })
                        this.print();
                    }else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.loading = false;
                });
            },
           
            resetGoodsName:function (formName) {
                this.$refs[formName].resetFields();
                this.$refs.goodsTable.clearSelection();
                this.filters.provType = "";
                this.filters.status = "";
                this.defaultDate();
                this.goods = [];
                //this.getGoodsList(1);
                //this.getGoodsListForPrint();
            },
 
        
            /**分页处理事件 */
            handleCurrentChange : function(val) {
                this.page = val;
                this.getGoodsList(this.page);
            },
            handleSizeChange:function(val) {
                this.pageSize = val;
                this.getGoodsList(this.page);
            },

            getDict (dict) {
                return new Promise((resolve, reject) => {
                    this.axios.get('/spdHERPService/reportAnalysis/reportStatistics/getDict', {
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
            defaultDate:function(){
                /**查询时间默认为 上月25号至本月25号 */
                var nowDate = new Date();
                var thisMonth = nowDate.getMonth() + 1;
                var thisDay = nowDate.getDate();
                var start = null;
                if(thisMonth==1){
                    start = new Date((nowDate.getFullYear()-1)+'/'+12+'/25 00:00:00');
                }else{
                    start = new Date(nowDate.getFullYear()+'/'+nowDate.getMonth()+'/25 00:00:00');
                }
                var end = new Date();
                end.setTime(end.getTime() + 3600 * 1000 * 24 * (25-thisDay));
                this.filters.sDate = [start, end];
            }
 /*         
            statusFormatter (row, column, cellValue) {
                return this.CKDZTDict[cellValue]
            } */
        },
        mounted() {
            this.hosId = this.user.corpId;//单位id
            this.getColProvLst();

            this.getDict('CKDZT').then(res => {
                this.CKDZTDict = res
                this.CKDZTDict[''] = '全部'
            })

            this.hosName = this.user.corpName;
            this.deptName = this.user.orgName;
            
            this.defaultDate();

        }
    };

</script>
<style>
    #centerStockOutQuery .el-checkbox__label{
        font-size: 12px;
        vertical-align: middle;
    }
    #centerStockOutQuery .el-checkbox-group{
        vertical-align: middle;
    }
    #centerStockOutQuery .deptgoods-tableheight {
        width: 100%;
        height:  calc(100% - 90px);
    }
    #centerStockOutQuery .deptGoods{
        border-radius: 6px;
        padding-bottom: 10px;
        padding-top: 12px;
        padding-right: 19px;
        border: 1px solid #d1dbe5;
        padding-left: 0px;
        margin-left: 15px;
        margin-right: 15px;
    }
    #centerStockOutQuery .deptGoods .el-row{
        height: 36px;
    }
    
    #centerStockOutQuery .deptMonthPurchaseForm{
        padding-bottom: 10px;
       /* padding-top: 20px;
        padding-right: 19px;*/
        border: 1px solid #d1dbe5;
        padding-left: 0px;
        margin-top:57px;
    }
    #centerStockOutQuery .pageBar{
        border-bottom: 1px solid #d1dbe5;
        border-left: 1px solid #d1dbe5;
        border-right: 1px solid #d1dbe5;
    }
    
    #centerStockOutQuery .red-row {
         color: #ff2020;
    }
</style>