<template>
    <div id="provGoodsHoslist" class="fullheight">
        <el-row  class="fullheight">
            <el-col :span="4" class="fullheight " >
                <div style="margin-bottom: 10px">
                    <el-input v-model="hosFilters.hosName" placeholder="请输入医院名称"   icon="search" @keyup.enter.native="handleHosSearch" :on-icon-click="handleHosSearch"   ></el-input>
                </div>
                <el-table :show-header="false" class="tableheight" :data="hosList" @row-click="selectHosClick" style="width: 100%;margin-top: 5px;" highlight-current-row >
                    <el-table-column prop="hosName" label="医院" show-overflow-tooltip>
                    </el-table-column>
                </el-table>
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[30, 100, 200, 400]" :page-size="pageSize" layout="sizes,prev,next" :total="total"  small>
				</el-pagination>
            </el-col>
            <el-col :span="20" class="fullheight" style="padding-left: 5px;">
                	<el-form :inline="true" label-width="120px">
                 <!-- <el-button  type="primary"  @click="goodEdit(null)">新增</el-button>-->
                      <el-form-item label="商品名称" style="margin-bottom:5px;">
							<el-input v-model="filters.goodsName" placeholder="输入商品名称或规格" ></el-input>
					</el-form-item>
					 	<el-button type="primary" v-on:click="query()" >查询</el-button>
                   <el-button  type="primary"  @click="handleImport">批量导入</el-button>
                   <el-button type="primary" @click="multDel()" >批量删除</el-button>
                    <!--<el-button :disabled="multipleSelection.length!=1" type="primary" icon="edit" >编辑</el-button>-->
                    <!--<el-button :disabled="multipleSelection.length<1" type="danger" icon="delete" >批量删除</el-button>-->
               </el-form>
                <el-table highlight-current-row class="tableheight"
                         :data="goodList" style="width: 100%"
                          ref="multipleTable4Main"
                          row-key='id'  @selection-change="handleSelectionChange4Main"
                          v-loading="goodsLoading" border  height="outer"
                          :default-sort="{prop: 'provGoodsGoodsName', order: 'descending'}">
                    <el-table-column  type="selection" :reserve-selection="true" width="55"> </el-table-column>
                    <!--供货目录只列出供应商的 商品信息， 对码不在此处实现 -->
                    <!--
                    <el-table-column prop="hisCode"   min-idth="85" label="医院商品编号" header-align="center" >
                     </el-table-column>
                     -->
                    <el-table-column prop="provGoodsErpCode" label="ERP编码" header-align="center"  width="120" >
                    </el-table-column>
                    <el-table-column prop="provGoodsName"   min-idth="85" label="商品名称" header-align="center" >
                    </el-table-column>
                     <el-table-column prop="provGoodsGg"   min-idth="85" label="商品规格" header-align="center" >
                    </el-table-column>
                    <el-table-column prop="provGoodsMade"   min-idth="85" label="产地" header-align="center" >
                    </el-table-column>
                     <el-table-column prop="provGoodsUnit"   min-idth="85" label="单位" header-align="center" >
                    </el-table-column>

                    <el-table-column prop="provGoodsMfrsName"  header-align="center" width="150"  label="生产厂商" show-overflow-tooltip sortable>
                    </el-table-column>
                    <el-table-column prop="provGoodsMasterCode"  header-align="center" width="110"  label="统一码" >
                    </el-table-column>
                    <el-table-column label="操作" width="120"  header-align="center" fixed="right">
                        <template slot-scope="scope">
                             <el-button-group>
						<!--		<el-button type="primary" size="mini" @click="goodEdit(scope.row)" icon="edit" >编辑</el-button> -->
								<el-button type="danger" size="mini" @click="delGood(scope.row)" icon="delete">删除</el-button>
                             </el-button-group>
						</template>
                    </el-table-column>
                    <!--<el-table-column type="selection" width="40"></el-table-column>-->
                </el-table>
                <el-pagination @size-change="handleGoodsSizeChange" @current-change="handleGoodsCurrentChange" :current-page="goodsPage" :page-sizes="[10, 100, 200, 400]" :page-size="goodsPageSize" layout="total, sizes, prev, pager, next" :total="goodsTotal" style="float:right;" small>
			   </el-pagination>
            </el-col>
        </el-row>

        <!--<el-pagination @current-change="handleCurrentChange" :current-page="currentPage" layout="total, prev, pager, next, jumper" :total="total">
                                                                                                                                                                                                                                                                                                                                                                                                                                                            </el-pagination>-->
        <el-dialog :title="goodImportTitle" close="onClose" :visible.sync="goodImportVisible" :close-on-click-modal="false">
            <el-form :inline="true" label-width="120px">
                            <el-form-item label="商品名称">
                                <el-input v-model="filters.goodsName" placeholder="输入商品名称或规格" ></el-input>
                            </el-form-item>
                            <el-button type="primary" v-on:click="getGoodsListByHost(1)" >查询</el-button>

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
                                  <el-pagination @size-change="handleProvGoodsSizeChange" @current-change="handleProvGoodsCurrentChange" :current-page="provGoodsPage" :page-sizes="[10, 100, 200, 400]" :page-size="provGoodsPageSize" layout="total, sizes, prev, pager, next"
                                   :total="provGoodsTotal" style="float:right;" small>
			                      </el-pagination>
                                <div style="margin-top: 20px">

                                    <el-button @click="toggleSelection()">取消选择</el-button>
                                    <el-button @click="goodImprotSubmit()">确定</el-button>
                                </div>
                         </template>
            </el-dialog>
        <el-dialog :title="modelTitle" :visible.sync="dialogVisible">
            <el-row :gutter="10" class="fullheight">
                <el-col :span="24" class="fullheight">
                    <el-form :model="good" label-width="100px" :rules="editFormRules" ref="editForm" class="el-form-item-nomsg">
                        <el-row>
                            <el-col>
                            <el-form-item label="商品名称" prop="goodsName" >
                                <el-input v-model="good.provGoodsName"  disabled=true placeholder="请输入商品名称"></el-input>
                            </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="商品编号" prop="code"  >
                                    <el-input v-model="good.provGoodsCode" placeholder="请输入商品编号" disabled=true ></el-input>
                                </el-form-item>
                            </el-col>
                            <!--
                            <el-col :span="6">
                                <el-form-item label="拼音码">
                                    <el-input v-model="good.provGoodsShortPinyin" disabled=true ></el-input>
                                </el-form-item>
                            </el-col>
                            -->
                                <el-col :span="12">
                                <el-form-item label="生产厂家">
                                    <el-input v-model="good.provGoodsMfrsName" disabled=true ></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="规格型号" prop="goodsGg" >
                                    <el-input v-model="good.provGoodsGg"  disabled=true placeholder="请输入规格型号"></el-input>
                                </el-form-item>
                            </el-col>
                                <el-col :span="7">
                                    <el-form-item label="商品产地">
                                        <el-input v-model="good.provGoodsMade" disabled=true ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="5">
                                    <el-form-item label="计量单位" prop="unit" >
                                        <el-input disabled=true v-model="good.provGoodsUnit" ></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col>
                                    <el-form-item label="医院产品名称" prop="hisName" :error="errors.hisNameErr">
                                        <el-input v-model="good.hisName" ></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="10">
                                <el-form-item label="医院产品编码" prop="hisCode" :error="errors.hisCodeErr">
                                    <el-input v-model="good.hisCode" ></el-input>
                                </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                <el-form-item label="医院产品单位" prop="hisUnit" :error="errors.hisUnitErr">
                                    <el-input v-model="good.hisUnit" ></el-input>
                                </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="8">
                                <el-form-item label="配送单价" prop="salePrice" :error="errors.salePriceErr">
                                    <el-input v-model.number="good.salePrice" ></el-input>
                                </el-form-item>
                                </el-col>
                            <el-col :span="8">
                                <el-form-item label="采购价" prop="purPrice" :error="errors.purPriceErr">
                                    <el-input v-model.number="good.purPrice" ></el-input>
                                </el-form-item>
                                </el-col>
                            <el-col :span="8">
                                <el-form-item label="单位比率" prop="unitRate" :error="errors.unitRateErr">
                                    <el-input v-model.number="good.unitRate" ></el-input>
                                </el-form-item>
                                </el-col>

                            </el-row>
                            <el-row>
                                <el-col :span="8">
                                    <el-form-item label="税率" prop="taxRate" >
                                        <el-select v-model="taxRate" filterable placeholder="请选择" >
                                                <el-option
                                                    v-for="item in SL"
                                                    :key="item.ename"
                                                    :label="item.val"
                                                    :value="item.ename">
                                                </el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                                              <!--
                                             <el-row>
                                                <el-col>
                                                    <el-form-item label=" 备注信息">
                                                        <el-input type="textarea" disabled=true v-model="good.remark" ></el-input>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                            -->
                        </el-form>
                    </el-col>
                </el-row>

            <span slot="footer" class="dialog-footer">
                <el-button @click.native="dialogVisible = false" >取消</el-button>
                <el-button type="primary" @click.native="editSubmit"   :loading="editLoading">提交</el-button>

            </span>
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
                total: 0,
                page: 1,
                pageSize: 20,
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
                dialogVisible:false,
                goodImportVisible:false,
                modelTitle:'',
                goodImportTitle:'',

                editFormRules: {
                  hisCode: [
                        { required: true, message: '请输入医院商品编码', trigger: 'blur' }
                    ],
                    hisName: [
                        { required: true, message: '请输入医院商品名', trigger: 'blur' }
                    ],
                    salePrice: [
                        {type:"number", required: false, message: '配送单价必须为数字', trigger: 'blur' }
                    ],
                     purPrice: [
                        { type:"number", required: false, message: '采购价必须为数字', trigger: 'blur' }
                    ],
                    hisUnit: [
                        { required: false, message: '请输入医院产品计量单位', trigger: 'blur' }
                    ],
                    unitRate:[
                        {type:"number",  required: false, message: '单位比率必须为数字', trigger: 'blur' }
                    ]
                },
               errors: {

                    hisUnitErr:'',
                    hisCodeErr:'',
                    hisNameErr:'',
                    salePriceErr:'',
                    unitRateErr:'',
                    purPriceErr:''
		     	},
                mfrs:[] ,    //生产厂家
                mfr:{id:'',mfrsName:''},

                currentHos:{},//当前选择的医院
                currentHosId:'',//当前选择的医院ID
                provGoodList:[],
                currentProvGood:[],
                multipleSelection: [],
                multipleSelection4Main: [],
                  SL:[],
                  taxRate:''


            }
        },
        methods: {

    goodEdit(item) {

                    this.errors.hisUnitErr= '';
                    this.errors.hisCodeErr= '';
                    this.errors.hisNameErr= '';
                    this.errors.salePriceErr= '';
                    this.errors.unitRateErr= '';
                    this.errors.purPriceErr= '';
               if(this.currentHosId.length<=0){
                    this.$message.error( "请先选择医院!");
                    return;
                }

                if (item) {
                    let good = Object.assign({}, item);
                    this.good = good;
                    this.selectedId =item.mfrsId;
                            this.taxRate=this.good.taxRate;
                    this.modelTitle = "修改"+this.currentHos.hosName+"供货商品";
                } else {
                    this.selectedId='';
                      this.good={};
                      this.taxRate='';
                    this.modelTitle = "添加"+this.currentHos.hosName+"供货商品";

                 }
               this.dialogVisible = true;
               //this.$refs.editForm.resetFields();

            },

               editSubmit: function () {
                 this.good.taxRate = this.taxRate;
                var _this = this;
                var form =  this.$refs.editForm;
                const h = this.$createElement;
                var arr=new Array();
                form.validate((valid) => {
                    if (valid) {
                        this.editLoading = true;
                        let serviceUrl = this.good.id ? "/spdHDIService/myGoods/provGoodsHos/update" : "/spdHDIService/myGoods/provGoodsHos/insert";
                        this.axios.post(serviceUrl, this.good).then(res => {
                            this.editLoading = false;
                            if (res.data.code == "0") {
                                this.$message({ message: '操作成功！', type: 'success' });
                                   this.dialogVisible = false;
                                   this.getGoodsListByHost( this.currentHosId,this.goodsPage);

                            } else if (res.data.code == "-122") {
                                    let err = res.data.validateErrors.fieldErrors[0];
                                        if (err.field == "hisCode") {
                                        this.errors.hisCodeErr += err.message;
                                        }
                                        if (err.field == "hisName") {
                                            this.errors.hisNameErr += err.message;
                                               arr.push(h('span',null,err.message));
                                        }
                                         if (err.field == "hisUnit") {
                                        this.errors.hisUnitErr += err.message;
                                           arr.push(h('span',null,err.message));
                                        }
                                        if (err.field == "salePrice") {
                                            this.errors.salePriceErr += err.message;
                                               arr.push(h('span',null,err.message));
                                        }
                                         if (err.field == "unitRate") {
                                            this.errors.unitRateErr += err.message;
                                             arr.push(h('span',null,err.message));
                                        }
                                          _this.$msgbox({title:"错误",type:"error",message:h('p',null,arr)});
                                 }
                            else {

                                this.$message.error('操作失败！');
                            }
                        }, err => {
                            this.editLoading = false;
                        });
                      }else{
                        var fields = form.fields;
                        var msg ="";
                      fields.forEach(f=>{
                          if(f.validateState =="error"){
                              msg +="</br>"+f.validateMessage;
                              if (arr.length>0) arr.push(h('br',null,null));
                              arr.push(h('span',null,f.validateMessage));
                          }
                      });
                      _this.$msgbox({title:"错误",type:"error",message:h('p',null,arr)});
                    }
                });
            },

            handleImport () {
                if(this.currentHosId.length<=0){
                   this.$message.error( "请先选择医院!");
                    return;
                }

                this.goodImportVisible=true;
                this.goodImportTitle='从产品列表中导入到'+this.currentHos.hosName;
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

            handleHosSearch () {
                this.getHosListsByUnit(this.page);
            },
             handleCurrentChange(val) {
                this.page = val;
                this.getHosListsByUnit(this.page);
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.getHosListsByUnit(this.page);
            },
            handleGoodsCurrentChange(val) {
                this.goodsPage = val;
                this.getGoodsListByHost( this.currentHosId,this.goodsPage);
            },
            handleProvGoodsSizeChange(val) {
                this.provGoodsPageSize = val;
                this.getProvGoodList(this.provGoodsPage);
            },
           handleProvGoodsCurrentChange(val) {
                    this.provGoodsPage = val;
                    this.getProvGoodList( this.provGoodsPage);
                },
                handleGoodsSizeChange(val) {
                // this.goodsPageSize = val;
                    this.getGoodsListByHost( this.currentHosId,this.goodsPage);
                },

            getHosListsByUnit:function(pIndex){
                this.loading = true;
                this.page = pIndex;
                var param = { orderBy: "", queryObject: {hosName: this.hosFilters.hosName,status:2}, page: this.page, rows: this.pageSize };
                    this.axios.post('/spdHDIService/myInfo/provHosInfo/provHosInfoVoList',param).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                                this.hosList = res.data.data.data;
                                this.total = res.data.data.total;
                          //  this.page = res.data.data.pageNum;
                            if(this.hosList) {
                                this.currentHosId=this.hosList[0].hosId;
                                this.currentHos=this.hosList[0];
                                this.getGoodsListByHost(this.currentHosId,1);
                            }
                        }
                        else{
                            this.$message.error( res.data.msg);
                        }
                    }, err => {
                        this.loading = false;
                    });
            },
            selectHosClick:function(row){
                 if (this.currentHos.id === row.hosId) {
                    return
                 }
                this.currentHos = row;
                this.currentHosId= row.hosId;
                this.getGoodsListByHost(row.hosId,1)
            },
            query:function(){
            //    alert(this.currentHosId);
                this.getGoodsListByHost(this.currentHosId,1);
            },
            getGoodsListByHost:function(hosId,pIndex){
                  this.goodsPage = pIndex;
                  this.loading = true;
                var param = { orderBy: "", queryObject: {hosId:hosId,provId:this.user.corpId,provGoodsName:this.filters.goodsName}, page: this.goodsPage, rows: this.goodsPageSize };
                    this.axios.post('/spdHDIService/myGoods/provGoodsHos/getProGoods4HosList',param).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.goodList = res.data.data.data;
                             this.goodsTotal = res.data.data.total;
                           //  this.goodsPage = res.data.data.pageNum;
                        }
                        else{
                            this.$message.error( res.data.msg);
                        }
                    }, err => {
                        this.loading = false;
                    });
            },
           selectProvGoodClick:function(row){
            this.currentProvGood = row;
            this.good.goodsName = row.goodsName;
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

           getProvGoodList:function(pIndex){

                this.provGoodsPage = pIndex;
                //传递当前hosId到后台,过滤已经与医院关联的供应商商品 ,zhangzhihua
                var param = { orderBy: "", queryObject: { hosId:this.currentHosId,goodsName: this.provGoodsFilters.goodsName}, page: this.provGoodsPage, rows: this.provGoodsPageSize };
                this.loading = true;
                this.axios.post('/spdHDIService/myGoods/provGoods/getGoodsOutHos', param).then(res => {
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

    #provGoodsHoslist .tableheight {
        height: calc(100% - 95px);
    }
</style>
