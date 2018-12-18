<template>
    <div id="provGoodsHoslist4Batch" class="fullheight">
        <el-row  class="fullheight">
            <!--医院列表-->
            <el-col :span="4" class="fullheight " >
                <div style="margin-bottom: 10px">
                    <el-input v-model="hosFilters.hosName" placeholder="请输入医院名称"   icon="search" @keyup.enter.native="handleHosSearch" :on-icon-click="handleHosSearch"   ></el-input>
                </div>
                <el-table ref="hosTable" :show-header="false" class="tableheight" :data="hosList" @row-click="selectHosClick" style="width: 100%;margin-top: 5px;" highlight-current-row >
                    <el-table-column prop="hosName" label="医院" show-overflow-tooltip>
                    </el-table-column>
                </el-table>
                <el-col class="pageBar">
                    <el-pagination @size-change="handleSizeChange"
                               @current-change="handleCurrentChange"
                              :current-page="hosPage"
                              :page-sizes="[30, 100, 200, 400]"
                              :page-size="hosPageSize" layout="sizes,prev,next" :total="hosTotal"  small>
			        </el-pagination >
                </el-col>

            </el-col>

            <!--供应商列表-->
            <el-col :span="4" class="fullheight "  style="padding-left: 5px;">
                <div style="margin-bottom: 10px">
                    <el-input v-model="provFilters.provName" placeholder="请输入供应商名称"   icon="search" @keyup.enter.native="handleProvSearch" :on-icon-click="handleProvSearch"   ></el-input>
                </div>
                <el-table ref="provTable" :show-header="false" class="tableheight" :data="provHosInfos" @row-click="selectProvClick" style="width: 100%;margin-top: 5px;" highlight-current-row >
                    <el-table-column prop="provName" label="供应商" show-overflow-tooltip>
                    </el-table-column>
                </el-table>
                <el-col class="pageBar">
                    <el-pagination @size-change="handleProvSizeChange"
                                @current-change="handleProvCurrentChange"
                                :current-page="provPage"
                                :page-sizes="[30, 100, 200, 400]"
                                :page-size="provPageSize"  height="outer"
                                layout="sizes,prev,next" :total="provTotal"  small>
                    </el-pagination>
                </el-col>
            </el-col>

            <!--供应商产品信息-->
            <el-col :span="16" class="fullheight" style="padding-left: 5px;">
                <el-form :inline="true" label-width="120px">
                 <!-- <el-button  type="primary"  @click="goodEdit(null)">新增</el-button>-->
                    <el-form-item label="商品名称" style="margin-bottom:5px;">
							<el-input v-model="filters.goodsName" placeholder="输入商品名称或规格" ></el-input>
					</el-form-item>
					<el-button type="primary" v-on:click="query()" >查询</el-button>
                    <el-button  type="primary"  @click="handleImport">批量导入</el-button>
                    <el-button type="primary" @click="multDel()" >批量删除</el-button>
                </el-form>
                <el-table highlight-current-row  class="tableheight"
                         :data="goodList" style="width: 100%"
                          ref="multipleTable4Main"
                          row-key='id'  @selection-change="handleSelectionChange4Main"
                          v-loading="goodsLoading" border
                          :default-sort="{prop: 'provGoodsGoodsName', order: 'descending'}">
                    <el-table-column  type="selection" :reserve-selection="true" width="55"> </el-table-column>
                    <el-table-column prop="provGoodsCode" label="供应商商品编号" header-align="center"  width="120" >
                    </el-table-column>
                    <el-table-column prop="provGoodsName"   min-idth="150" width="150" label="商品名称" header-align="center" >
                    </el-table-column>
                     <el-table-column prop="provGoodsGg"   min-idth="85" label="商品规格" header-align="center" >
                    </el-table-column>
                    <el-table-column prop="provGoodsMade"   min-idth="85" label="产地" header-align="center" >
                    </el-table-column>
                     <el-table-column prop="provGoodsUnit"  align="center" min-idth="60" label="单位" header-align="center" >
                    </el-table-column>
                    <el-table-column prop="provName"  header-align="center" width="150"  label="供应商" show-overflow-tooltip sortable>
                    </el-table-column>
                    <el-table-column prop="provGoodsMfrsName"  header-align="center" width="150"  label="生产厂商" show-overflow-tooltip sortable>
                    </el-table-column>
                    <el-table-column prop="provGoodsMasterCode"  header-align="center" width="110"  label="统一码" >
                    </el-table-column>
                    <el-table-column label="操作" width="80"  header-align="center" fixed="right" align="center">
                        <template slot-scope="scope">
							<el-button type="danger" size="mini" @click="delGood(scope.row)" icon="delete">删除</el-button>
						</template>
                    </el-table-column>
                </el-table>
                <el-col class="pageBar">
                    <el-pagination   @size-change="handleGoodsSizeChange"
                                    @current-change="handleGoodsCurrentChange"
                                    :current-page="goodsPage" :page-sizes="[10, 100, 200, 400]"
                                    :page-size="goodsPageSize" layout="total, sizes, prev, pager, next"
                                    :total="goodsTotal" style="float:right;" small>
                    </el-pagination>
               </el-col>
            </el-col>
        </el-row>

        <el-dialog :title="goodImportTitle" close="onClose" :visible.sync="goodImportVisible" :close-on-click-modal="false">
            <el-form :inline="true" label-width="120px">
                <el-form-item label="商品名称">
                    <el-input v-model="provGoodsFilters.goodsName" placeholder="输入商品名称或规格" ></el-input>
                </el-form-item>
                <el-button type="primary" v-on:click="getProvGoodList(1)" >查询</el-button>
            </el-form>
            <template>
                <el-table
                    ref="multipleTable" :data="provGoodList"  border tooltip-effect="dark" row-key='id'
                    style="width: 100%" @selection-change="handleSelectionChange">
                    <el-table-column  type="selection" :reserve-selection="true" width="55"> </el-table-column>
                    <el-table-column  prop="goodsName" label="商品名称" width="200"></el-table-column>
                    <el-table-column prop="code" label="商品编号" width="100" show-overflow-tooltip> </el-table-column>
                    <el-table-column prop="mfrsName" label="生产厂家" show-overflow-tooltip> </el-table-column>
                    <el-table-column prop="goodsGg" label="规格" show-overflow-tooltip> </el-table-column>
                    <el-table-column prop="unit" label="单位" show-overflow-tooltip> </el-table-column>
                </el-table>
                    <el-pagination @size-change="handleProvGoodsSizeChange"
                                   @current-change="handleProvGoodsCurrentChange"
                                   :current-page="provGoodsPage" :page-sizes="[10, 100, 200, 400]"
                                   :page-size="provGoodsPageSize" layout="total, sizes, prev, pager, next"
                                   :total="provGoodsTotal" style="float:right;" small>
                    </el-pagination>
                <div style="margin-top: 20px">
                    <el-button @click="toggleSelection()">取消选择</el-button>
                    <el-button @click="goodImprotSubmit()">确定</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script>

    export default {
        data () {

            return {
                hosFilters: {
                    hosName: ''
                },
                filters:{goodsName:''},
                provGoodsFilters:{goodsName:''},
                provHosInfos:[],//医院的供应商列表
                provFilters:{   // 供应商过滤条件
                    provName:''
                },

                hosTotal: 0,
                hosPage: 1,
                hosPageSize: 20,

                provTotal: 0,
                provPage: 1,
                provPageSize: 20,

                goodsTotal:0,
                goodsPageSize:10,
                goodsPage:1,

                provGoodsPage:1,
                provGoodsPageSize:10,
                provGoodsTotal:0,

                good: {
                    goodsName:''
                },
                hosList:[],
                goodList:[],
                loading: false,
                goodsLoading:false,
                editLoading: false,
                goodImportVisible:false,
                goodImportTitle:'',


                mfrs:[] ,    //生产厂家
                mfr:{id:'',mfrsName:''},

                currentHos:{},//当前选择的医院
                currentHosId:'',//当前选择的医院ID
                currentProv : {},//当前选中的供应商
                currentProvId:'',//当前供应商ID

                provGoodList:[],
                currentProvGood:[],
                multipleSelection: [],
                multipleSelection4Main: [],
                SL:[],
                taxRate:''
            }
        },
        methods: {

            // 医院获取供应商列表
            getHosProvInfoVoList: function (pIndex) {
                this.provPage = pIndex;
                var param = {
                    orderBy: "",
                    queryObject: {
                        hosId:this.currentHosId,
                        provName: this.provFilters.provName
                    },
                    page: this.provPage,
                    rows: this.provPageSize
                };
                this.loading = true;
                let o = this;
                o.axios.post('/spdHDIService/myGoods/provGoodsHosBatchMgrController/hosProvInfoVoList', param).then(res => {
                    o.loading = false;
                    o.provHosInfos = res.data.data.data;
                    o.provTotal = res.data.data.total;
                    if(o.provHosInfos.length>0){
                        o.$nextTick(function () {
                            o.setCurrent(o.provHosInfos[0]);
                            this.$refs.provTable.setCurrentRow(o.provHosInfos[0]);
                            this.getGoodsListByHost(this.currentHosId,1);
                        });
                    }else{
                        this.currentProv = {};
                        this.currentProvId = '';
                        this.getGoodsListByHost(this.currentHosId,1);
                    }
                }, err => {
                    this.loading = false;
                });
            },

            setCurrent :function (data){
                this.currentProv = data;
                this.currentProvId = data.provId;
            },

            handleImport () {
                if(this.currentHosId.length<=0){
                   this.$message.error( "请先选择医院!");
                    return;
                }

                this.goodImportVisible=true;
                this.goodImportTitle='从'+(this.currentProv.provName?this.currentProv.provName:'')+'产品列表中导入到'+this.currentHos.hosName;
                this.getProvGoodList(1);
            },

            delGood(item) {
                  this.$confirm('确认删除这条记录吗?', '提示', {
                }).then(() => {
                    this.loading = true;
                    this.axios.post("/spdHDIService/myGoods/provGoodsHos/delProvGoodsHos", item).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.loading = false;
                            this.$message({ message: '操作成功！', type: 'success' });
                        } else {
                            this.$message.error( res.data.msg);

                        }
						 this.getGoodsListByHost( this.currentHosId,this.goodsPage);
                    }, err => {
                        this.loading = false;
                    });
                }).catch(() => {

                });
            },

            /****批量删除操作 */
            multDel(){
                if(this.multipleSelection4Main.length<=0){
                        this.$message.error( "至少选择一条数据！");
                    return;
                }
                this.$confirm('确认删除选中记录吗?', '提示', {
                }).then(() => {
                    this.loading = true;
                    this.axios.post("/spdHDIService/myGoods/provGoodsHos/multDel", this.multipleSelection4Main).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.loading = false;
                            this.$message({ message: '操作成功！', type: 'success' });

                        } else {
                            this.$message.error( res.data.msg);
                        }
                        this.getGoodsListByHost( this.currentHosId,this.goodsPage);
                        this.$refs.multipleTable4Main.clearSelection();
                    }, err => {
                        this.loading = false;
                    });
                }).catch(() => {
                });
            },


            /*******处理医院列表分页事件********start */
            handleHosSearch () {
                this.getHosListsByUnit(this.hosPage);
            },
            handleCurrentChange(val) {
                this.hosPage = val;
                this.getHosListsByUnit(this.hosPage);
            },
            handleSizeChange(val) {
                this.hosPageSize = val;
                this.getHosListsByUnit(this.hosPage);
            },
            /*******处理医院列表分页事件********end */



            /*******处理供应商列表分页事件********start */
            handleProvSearch () {
                this.getHosProvInfoVoList(this.provPage);
            },
            handleProvCurrentChange(val) {
                this.provPage = val;
                this.getHosProvInfoVoList(this.provPage);
            },
            handleProvSizeChange(val) {
                this.provPageSize = val;
                this.getHosProvInfoVoList(this.provPage);
            },
            /*******处理供应商列表分页事件********end */



            /*******处理供货列表分页事件********start */
            handleGoodsCurrentChange(val) {
                this.goodsPage = val;
                this.getGoodsListByHost( this.currentHosId,this.goodsPage);
            },
            handleGoodsSizeChange(val) {
                this.goodsPageSize = val;
                this.getGoodsListByHost(this.currentHosId,this.goodsPage);
            },
            /*******处理供货列表分页事件********end */

            /*******处理弹窗供应商商品列表分页事件********start */
            handleProvGoodsCurrentChange(val) {
                this.provGoodsPage = val;
                this.getProvGoodList( this.provGoodsPage);
            },
            handleProvGoodsSizeChange(val) {
                this.provGoodsPageSize = val;
                this.getProvGoodList(this.provGoodsPage);
            },
            /*******处理弹窗供应商商品列表分页事件********end */


            /***获取医院列表 */
            getHosListsByUnit:function(pIndex){
                this.loading = true;
                this.hosPage = pIndex;
                var param = { orderBy: "", queryObject: {hosName: this.hosFilters.hosName,status:2}, page: this.hosPage, rows: this.hosPageSize };
                    this.axios.post('/spdHDIService/myInfo/provHosInfo/provHosInfoVoList',param).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.hosList = res.data.data.data;
                            this.hosTotal = res.data.data.total;
                            if(this.hosList.length>0) {
                                this.$nextTick(function () {
                                    this.currentHosId=this.hosList[0].hosId;
                                    this.currentHos=this.hosList[0];
                                    this.$refs.hosTable.setCurrentRow(this.hosList[0]);
                                    this.getHosProvInfoVoList(1);
                                 });
                            }else{
                                this.currentHosId='';
                                this.currentHos={};
                                this.getHosProvInfoVoList(1);
                            }
                        }
                        else{
                            this.$message.error( res.data.msg);
                        }
                    }, err => {
                        this.loading = false;
                    });
            },
            /**
             选中 医院 列表 行事件
             */
            selectHosClick:function(row){
                if (this.currentHos.id === row.hosId) {
                    return
                }
                this.currentHos = row;//设置当前选中行
                this.currentHosId= row.hosId;
                this.getHosProvInfoVoList(1);

            },
            /**
             * 选中 供应商 列表行事件
             */
            selectProvClick : function(row){
                if (this.currentProvId === row.provId) {
                    return
                }
                this.currentProv = row;//设置当前选中的供应商
                this.currentProvId= row.provId;
                this.getGoodsListByHost(row.provId,1)
            },
            /**
             * 查询对应供货目录
             */
             query:function(){
                 this.getGoodsListByHost(this.currentHosId,1)
             },
            getGoodsListByHost:function(hosId,pIndex){
                  this.goodsPage = pIndex;
                  this.loading = true;
                var param = { orderBy: "", queryObject: {hosId:this.currentHosId,provId:this.currentProvId,provGoodsName:this.filters.goodsName}, page: this.goodsPage, rows: this.goodsPageSize };
                    this.axios.post('/spdHDIService/myGoods/provGoodsHos/getProGoods4HosList',param).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.goodList = res.data.data.data;
                             this.goodsTotal = res.data.data.total;
                        }else{
                            this.$message.error( res.data.msg);
                        }
                    }, err => {
                        this.loading = false;
                    });
            },


            toggleSelection(rows) {
                if (rows) {
                rows.forEach(row => {
                    this.$refs.multipleTable.toggleRowSelection(row);
                });
                } else {
                this.$refs.multipleTable.clearSelection();
                }
            },

            handleSelectionChange(val) {
                this.multipleSelection = val;
            },

            handleSelectionChange4Main(val) {
                this.multipleSelection4Main = val;
            },

           /**查询供应商商品 */
           getProvGoodList:function(pIndex){
                this.provGoodsPage = pIndex;
                var param = { orderBy: "",
                              queryObject: { goodsName: this.provGoodsFilters.goodsName ,provId : this.currentProvId },
                              page: this.provGoodsPage, rows: this.provGoodsPageSize
                            };
                this.loading = true;
                this.axios.post('/spdHDIService/myGoods/provGoods/getGoodsInfos', param).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
                        this.provGoodList = res.data.data.data;
                        this.provGoodsTotal = res.data.data.total;
                    }
                    else{
                        this.$message.error( res.data.msg);
					}
                }, err => {
                    this.loading = false;
                });
           },

           /** 批量导入供应商商品确认按钮事件*/
            goodImprotSubmit:function(){
                 if(this.multipleSelection.length<=0){
                    this.$message.error( "至少选择一条数据！");
                    return;
                }
                this.axios.post('/spdHDIService/myGoods/provGoodsHos/goodImprotFromProGoods?hosId='+this.currentHosId, this.multipleSelection).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {;
                     this.goodImportVisible =false;
                     this.getGoodsListByHost(this.currentHosId,1);
                    }
                    else{
                        this.$message.error( res.data.msg);
					}
                     this.$refs.multipleTable.clearSelection();
                }, err => {
                    this.loading = false;
                });

            },

           //获取产家 供选择使用
            getSL: function () {
                this.loading = true;
                  this.axios.post('/platformService/sys/dict/getDictValueByDictEname',{dictName:'SL'}).then(res => {                    this.loading = false;
                    if (res.data.code == "0") {
						this.SL = res.data.data;
                    }
                    else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.loading = false;
                });
            },


        },
        computed: {
        },

        mounted () {
            this.getHosListsByUnit(1);
            this.getSL();
        }
    }
</script>

<style scoped>
     #provGoodsHoslist4Batch .pageBar{
        border-bottom: 1px solid #d1dbe5;
        border-left: 1px solid #d1dbe5;
        border-right: 1px solid #d1dbe5;
    }
    .line {
        text-align: center;
    }

    .el-button {
        margin: 0;
    }

    .el-pagination {
        float: right;
    }

    .fullheight {
        height: calc(100% - 0px);
    }

    .splitborder {
        border-right: rgba(0, 70, 255, 0.6) 1px dashed;
    }

    #provGoodsHoslist4Batch .tableheight {
        height: calc(100% - 95px);
    }
</style>
