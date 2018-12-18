<template>
	<div  style="height: 100%;">
			<!--工具条-->

				<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
					<el-form :inline="true" label-width="120px">
						<el-form-item label="商品名称">
							<el-input v-model="filters.goodsName" placeholder="输入商品名称或规格" ></el-input>
						</el-form-item>
						<el-button type="primary" v-on:click="getGoodsList(1)" >查询</el-button>
                        <el-button type="primary" @click="handleImport(null)" >批量导入</el-button>
						<el-button type="primary" @click="goodEdit(null)" >新增</el-button>
                        <el-button type="primary" @click="exportExcel()" >导出Excel</el-button>
                     <!--   <el-button type="primary" @click="importExcel()" >从Excel导入</el-button> -->
                        <el-button type="primary" @click="multDel()" >批量删除</el-button>
					</el-form>
				</el-col>
				<!--列表-->
				<template>
					<el-table  ref="multipleTable" :data="goods"  row-key='id'  @selection-change="handleSelectionChange"
                        highlight-current-row v-loading="loading"
                        style="width: 100%;"   border fit  class="provgoods-tableheight" height="outer">
					  <!--	<el-table-column type="index"></el-table-column>-->
                        <el-table-column  type="selection" :reserve-selection="true" width="55"> </el-table-column>
						<el-table-column prop="erpCode" header-align="center" label="ERP编码" sortable></el-table-column>
						<el-table-column prop="goodsName" header-align="center" label="商品名称" sortable></el-table-column>
                        <el-table-column prop="unit" header-align="center" label="单位" ></el-table-column>
						<el-table-column prop="goodsGg" header-align="center" label="规格型号" sortable></el-table-column>
						<el-table-column prop="made" header-align="center" label="产地" ></el-table-column>
                        <el-table-column prop="mfrsName" header-align="center" label="生产厂商" sortable></el-table-column>


                        <el-table-column prop="masterCode" header-align="center" label="统一码" sortable> </el-table-column>
                     <!--   <el-table-column prop="certificateCode" label="注册证号" sortable></el-table-column> -->
                        <el-table-column prop="brand" header-align="center" label="品牌"></el-table-column>
						<el-table-column label="操作" width="140" header-align="center" fixed="right">
							<template slot-scope="scope">
                                 <el-button-group>
                                    <el-button type="primary" size="mini" @click="goodEdit(scope.row)" icon="edit" >编辑</el-button>
                                    <el-button type="danger" size="mini" @click="delGood(scope.row)" icon="delete" >删除</el-button>
                                 </el-button-group>
							</template>
						</el-table-column>
					</el-table>
					<!--工具条-->
					<el-col :span="24" class="toolbar">
						<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[20, 100, 200, 400]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
						</el-pagination>
					</el-col>
				</template>
				<!--编辑界面-->
				<el-dialog :title="modelTitle" close="onClose" :visible.sync="editFormVisible" :close-on-click-modal="false" >
                    <el-form :model="good"  label-width="100px" :rules="editFormRules" ref="editForm"  class="el-form-item-nomsg">
                            <el-row>



                                <el-col>
                                    <el-form-item label="产品名称" prop="goodsName" :error="errors.goodsNameErr" >
                                        <el-input v-model="good.goodsName"  placeholder="请输入商品名称"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="8">
                                    <el-form-item label="拼音码">
                                        <el-input v-model="good.shortPinyin"  placeholder="请输入拼音码"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="16">
                                    <el-form-item label="规格型号" prop="goodsGg" :error="errors.goodsGgErr">
                                        <el-input v-model="good.goodsGg"  placeholder="请输入规格型号"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="8">
                                    <el-form-item label="商品编号" prop="code" :error="errors.codeErr" >
                                        <el-input v-model="good.code" placeholder="请输入商品编号" ></el-input>
                                    </el-form-item>
                                </el-col>
                                    <el-col :span="16">
                                        <el-form-item label="生产厂商"  prop="mfrsId" :error="errors.mfrsIdErr">
                                        <!--	<el-input v-model="good.mfrsId"  placeholder="请输入生产厂商"></el-input>  -->
                                         <!--    <el-select v-model="selectedId" filterable placeholder="请选择"  >
                                                    <el-option
                                                        v-for="item in mfrs"
                                                        :key="item.id"
                                                        :label="item.mfrsName"
                                                        :value="item.id">
                                                    </el-option>
                                            </el-select>
                                            -->
                                              <el-autocomplete style="width:100%"
                                                    class="inline-input"
                                                    v-model="selectedName"
                                                    :fetch-suggestions="querySearch"
                                                    placeholder="请输入内容"
                                                    valueKey ="mfrsName"

                                                      size='small'
                                                    @select="handleSelect"

                                                    >
                                                </el-autocomplete>
                                        </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="12">
                                    <el-form-item label="产地"  prop="made" :error="errors.madeErr">
                                        <el-input v-model="good.made"  placeholder="请输入产地"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                        <el-form-item label="单位"  prop="unit" :error="errors.unitErr">
                                            <el-input placeholder="请输入单位" v-model="good.unit" ></el-input>
                                        </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="12">
                                        <el-form-item label="品牌"  prop="brand" :error="errors.brandErr">
                                            <el-input placeholder="请输入品牌" v-model="good.brand" ></el-input>
                                        </el-form-item>
                                </el-col>
                                    <el-col :span="12">
                                        <el-form-item label="包装" prop="packeage" :error="errors.packeageErr">
                                            <el-input placeholder="请输入包装"  v-model.number="good.packeage" ></el-input>
                                        </el-form-item>
                                    </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="12">
                                        <el-form-item label="统一码">
                                        <el-input placeholder="请输入统一码" v-model="good.masterCode" ></el-input>
                                    </el-form-item>
                                    </el-col>
                                    <el-col :span="12">
                                        <el-form-item label="厂家出厂编码">
                                            <el-input placeholder="请输入厂家出厂编码" v-model="good.mfrsCode" ></el-input>
                                        </el-form-item>
                                    </el-col>
                            </el-row>
                            <el-row>
                            <!--
                                <el-col :span="12">
                                        <el-form-item label="类别">
                                            <el-input placeholder="请输入类别" v-model="good.kindCode" ></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="12">
                                    -->

										 <el-col :span="12">
                                             <el-form-item label="分类" prop="kind68code" >
                                                     <el-select v-model="kind68code" filterable placeholder="请选择"
                                                       style='width:100%' >
                                                            <el-option
                                                                v-for="item in dict68s"
                                                                :key="item.val"
                                                                :label="item.ename"
                                                                :value="item.val">
                                                                 <span style="float: left">{{ item.ename }}</span>
                                                                 <span style="float: right; color: #8492a6; font-size: 13px">{{ item.val }}</span>
                                                            </el-option>
                                                      </el-select>
                                                </el-form-item>
                                        </el-col>
										<el-col :span="12">
												<el-form-item label="ERP编码">
													<el-input placeholder="请输入类别" v-model="good.erpCode" ></el-input>
												</el-form-item>
                                        </el-col>

                            </el-row>
                            <el-row>
                                <!--<el-col :span="12">
                                        <el-form-item label="类别属性">
                                            <el-input placeholder="请输入类别属性" v-model="good.lbsx" ></el-input>
                                        </el-form-item>
                                </el-col>-->
                                <!--
                                    <el-col :span="12">
                                        <el-form-item label=" 备注信息">
                                            <el-input placeholder="请输入 备注信息" v-model="good.remark" ></el-input>
                                        </el-form-item>
                                    </el-col>
                                    -->
                            </el-row>
                            <el-row>
                                <el-col>
                                    <el-form-item label=" 备注信息">
                                            <el-input placeholder="请输入 备注信息" v-model="good.remark" ></el-input>
                                        </el-form-item>
                                </el-col>
                            </el-row>
                    </el-form>

                    <div slot="footer" class="dialog-footer">
                        <el-button @click.native="editFormVisible = false">取消</el-button>
                        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
                    </div>
				</el-dialog>

                 <el-dialog :title="selectExportColumnsTitle" close="onClose" :visible.sync="selectExportColumnsVisible" :close-on-click-modal="false"  >
					    <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
                            <div style="padding: 15px 0;"></div>
                            <el-checkbox-group v-model="checkedColumns" @change="handleCheckedColumnsChange">
                                <el-col :span="8" v-for="column in columns" :label="column" :key="column">
                                    <el-checkbox  :label="column" >{{column}}</el-checkbox>
                               </el-col>
                            </el-checkbox-group>
				       <div  v-show="downLoadVisible">
                        <el-col >
                            <a :href="fileSrc">导出完毕，请点击此处进行下载！</a>
                        </el-col>
                        </div >
					<div slot="footer" class="dialog-footer">
                    <el-col  style="padding-bottom: 5px;">
						<el-button @click.native="selectExportColumnsVisible = false">取消</el-button>
						<el-button type="primary" @click.native="selectExportColumnsSubmit" :loading="editLoading">提交</el-button>
					 </el-col>
                    </div>
				</el-dialog>

                <el-dialog title="从Excel中导入商品" close="onClose" :visible.sync="importExcelVisible" :show-close='false' :close-on-press-escape='false' :close-on-click-modal="false">
	                    下载模板</br> <a :href="ProvGoodsTempUrl" >ProvGoodsTemp.xls</a>
                        <template>
                                    <div>
                                        <el-upload
                                        class="upload-demo"
                                        :disabled="uploadState"
                                        :action= "uploadUrl"
                                        :on-preview="handlePreview"
                                        :on-remove="handleRemove"
                                        :on-success="handleFileSuccess"
                                        :before-upload="handleBeforeUpload"
                                        :file-list="fileList"
                                        list-type="text">
                                        <el-button  type="primary">点击上传</el-button>
                                        <div slot="tip" class="el-upload__tip">只能上传一个Excel文件</div>
                                        </el-upload>
                                    </div>
                                </template>

					<div slot="footer" class="dialog-footer">
						<el-button @click.native="importExcelVisible = false;uploadState=false">取消</el-button>
						<el-button type="primary" @click.native="importExcelSubmit" :loading="editLoading">提交</el-button>
					</div>
				</el-dialog>
                <el-dialog title="从平台商品中导入" close="onClose" :visible.sync="goodImportVisible" :close-on-click-modal="false" size="large">
                        <el-form :inline="true" label-width="120px">
                            <el-form-item label="商品名称">
                                <el-input v-model="basGoodsFilters.goodsName" placeholder="输入商品名称或规格" ></el-input>
                            </el-form-item>
                            <el-button type="primary" v-on:click="getBasGoodsList(1)" >查询</el-button>
                        </el-form>
                         <template>
                                <el-table
                                    ref="multipleTable" :data="basGoods"  border tooltip-effect="dark" row-key='id'
                                    style="width: 100%" @selection-change="handleSelectionChange" :loading="qLoading">
                                    <el-table-column  type="selection" :reserve-selection="true" width="55"> </el-table-column>

                                    <el-table-column  prop="goodsName" label="商品名称" width="200"></el-table-column>
                                    <el-table-column prop="code" label="商品编号" show-overflow-tooltip> </el-table-column>
                                     <el-table-column prop="mfrsName" label="生产厂家" show-overflow-tooltip> </el-table-column>
                                        <el-table-column prop="goodsGg" label="规格" show-overflow-tooltip> </el-table-column>
                                       <el-table-column prop="unit" label="单位" show-overflow-tooltip> </el-table-column>

                                </el-table>
                                  <el-pagination @size-change="basHandleSizeChange" @current-change="basHandleCurrentChange" :current-page="basPage" :page-sizes="[10, 100, 200, 400]" :page-size="basPageSize" layout="total, sizes, prev, pager, next"
                                   :total="basTotal" style="float:right;" small>
			                      </el-pagination>
                                <div style="padding-top: 20px">

                                    <el-button @click="toggleSelection()">取消选择</el-button>
                                    <el-button @click="goodImprotSubmit()">确定</el-button>
                                </div>
                         </template>
                </el-dialog>
                <el-dialog title="导入失败数据" close="onClose" :visible.sync="goodImportErrorsVisible" :close-on-click-modal="false" :show-close='false'  :close-on-press-escape='false'  size="large">
                            <template>
                                    <el-table
                                        ref="multipleTable" :data="goodImportErrors"  border tooltip-effect="dark"
                                        style="width: 100%">
                                        <el-table-column  prop="goodsName" label="商品名称" width="200"></el-table-column>
                                        <el-table-column prop="code" label="商品编号" show-overflow-tooltip> </el-table-column>
                                        <el-table-column prop="mfrsId" label="生产厂家" show-overflow-tooltip> </el-table-column>
                                        <el-table-column prop="goodsGg" label="规格" show-overflow-tooltip> </el-table-column>
                                        <el-table-column prop="remark" label="失败原因" show-overflow-tooltip> </el-table-column>
                                    </el-table>
                                    <div style="padding-top: 20px">
                                        <el-button @click="goodImprotErrorClose()">关闭</el-button>
                                    </div>
                            </template>
                </el-dialog>

    </div>

	<!--选择导出字段界面-->



</template>
<script>
import { fileServer } from '../../common/js/constance';
    export default {
        data() {
            return {

                 selectedId:'' ,    //选中的厂家Id
                 selectedName:'' ,    //选中的厂家name

                ProvGoodsTempUrl:'',
                uploadUrl:'',
                filters: { goodsName: '' },
                basGoodsFilters:{goodsName:''},
                total: 0,
                page: 1,
                pageSize: 20,
				qLoading:false,
                loading: false,
                goods: [],
                good: {mfrsId:''},
                basGoods:[],
                basTotal: 0,
                basPage: 1,
                basPageSize: 10,
                modelTitle: "",
                importExcelVisible:false,
                editFormVisible: false,
                editLoading: false,
                downLoadVisible:false,
                goodImportErrorsVisible:false,
                goodImportErrors:[],
                fileSrc:'',
                fileMap:{id:'',ext:'',name:'',path:''},
                kind68code:'',
                editFormRules: {
                   goodsName: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
                   code: [{ required: true, message: '请输入商品编号', trigger: 'blur' } ],
                   goodsGg: [{ required: true, message: '请输入商品规格', trigger: 'blur' }],
                   unit: [{ required: true, message: '请输入商品单位', trigger: 'blur' }],
                   mfrsId: [{ required: true, message: '请选择生产厂商', trigger: 'blur' }],
                   made: [{ required: true, message: '请输入产地', trigger: 'blur' }],
                   brand: [
                          {max:128,message:'品牌必须小于128个字符'}
                    ],
                   packeage: [ {type:"number", required: false, message: '请输入包装，必须为数字', trigger: 'blur' }]
                },
                errors: {goodsNameErr: '',codeErr: '', goodsGgErr:'',unitErr:'',mfrsIdErr:'',brandErr:'',madeErr:'',  packeageErr:''},
                selectExportColumnsTitle:"选择需要导出的列",
                selectExportColumnsVisible:false,
                checkedColumns:[],
                checkAll: true,
                isIndeterminate: true,
                columns:[],
                dict68s:[],
                mfrs:[] ,    //生产厂家
                mfr:{id:'',mfrsName:''},

                multipleSelection:[],
                fileList:[],
                uploadState:false,
                goodImportVisible:false,

                distriRelationList:[]
          }
        },
        methods: {





        /* 输入框自动补全**/
            querySearch(queryString, cb) {
                var restaurants = this.mfrs;
                var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
                // 调用 callback 返回建议列表的数据
                cb(results);
            },
            createFilter(queryString) {

                return (restaurant) => {
                              return (restaurant.mfrsName.indexOf(queryString) >= 0 );
                };
            },
            handleSelect(item) {
                    this.selectedId= item.id;
                    this.selectedName=item.mfrsName;
                    this.mfrsId =  item.id;
            },
          /* 输入框自动补全**/

            //获取列表
            getGoodsList: function (pIndex) {
                this.page = pIndex;
                var param = { orderBy: "", queryObject: { goodsName: this.filters.goodsName}, page: this.page, rows: this.pageSize };
                this.loading = true;
                this.axios.post('/spdHDIService/myGoods/provGoods/getGoodsInfos', param).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
                        this.goods = res.data.data.data;
                        this.total = res.data.data.total;

                    }
                    else{
                        this.$message.error( res.data.msg);
					}
                }, err => {
                    this.loading = false;
                });
            },
            handleCurrentChange(val) {
                this.page = val;
                this.getGoodsList(this.page);
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.getGoodsList(this.page);
            },
            goodEdit(item) {

                this.errors.goodsNameErr = '';
                 this.errors.goodsNameErr= '';
                 this.errors.codeErr= '';
                 this.errors.goodsGgErr= '';
                 this.errors.unitErr= '';
                 this.errors.mfrsIdErr= '';
                 this.errors.madeErr= '';
                 this.errors.brandErr= '';
                 this.errors.packeageErr= '';

                if (item) {
                    let good = Object.assign({}, item);
                    this.good = good;
                    this.kind68code=this.good.kind68code;
                    this.selectedId =item.mfrsId;
                    this.selectedName=item.mfrsName;
                    this.modelTitle = "修改商品";

                } else {

                     this.selectedId='';
                     this.selectedName='';
                     this.good={};
                     this.kind68code='';
                     this.modelTitle = "添加商品";

                }
                this.editFormVisible = true;
				//this.$refs.editForm.resetFields();

            },
            delGood(good) {
                this.$confirm('确认删除这条记录吗?', '提示', {
                }).then(() => {
                    this.loading = true;
                    this.axios.post("/spdHDIService/myGoods/provGoods/delGoodsInfo", good).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.loading = false;
                            this.$message({ message: '操作成功！', type: 'success' });
                            	this.getGoodsList(this.page);

                          } else if (res.data.code == "-122") {
                                    let err = res.data.validateErrors.fieldErrors[0];
                                        if (err.field == "goodsName") {
                                       this.$message({ message: err.message, type: 'error' });
                                        }

                        } else {
                            this.$message.error( res.data.msg);

                        }

                    }, err => {
                        this.loading = false;
                    });
                }).catch(() => {

                });
            },
            editSubmit: function () {
                this.good.mfrsName=this.selectedName;
                this.good.mfrsId=this.selectedId;
                 this.good.kind68code = this.kind68code;

                var _this = this;
                var form =  this.$refs.editForm;
                const h = this.$createElement;
                var arr=new Array();
                 form.validate((valid) => {
                    if (valid) {
                        this.editLoading = true;
                        let serviceUrl = this.good.id ? "/spdHDIService/myGoods/provGoods/update" : "/spdHDIService/myGoods/provGoods/insert";

                        this.axios.post(serviceUrl, this.good).then(res => {
                            this.editLoading = false;
                            if (res.data.code == "0") {
                                this.$message({ message: '操作成功！', type: 'success' });
                                this.editFormVisible = false;
                                this.getGoodsList(this.page);
                            } else if (res.data.code == "-122") {
                                    let err = res.data.validateErrors.fieldErrors[0];
                                        if (err.field == "code") {
                                        this.errors.codeErr += err.message;
                                            arr.push(h('span',null,err.message));
                                        }
                                          if (err.field == "mfrsId") {
                                        this.errors.mfrsIdErr += err.message;
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
            exportExcel:function(){

                this.selectExportColumnsVisible=true;

                //列出可选字段
              this.axios.post('/spdHDIService/myGoods/provGoods/getAllColumns', null).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {


                      this.columns =res.data.data;
                    }
                    else{
                        this.$message.error( res.data.msg);
					}
                }, err => {
                    this.loading = false;
                });




            },

            importExcel:function(){
                this.fileList=[];
                this.importExcelVisible=true;
            },

            importExcelSubmit:function(){
                this.uploadState=false;


                this.axios.post('/spdHDIService/myGoods/provGoods/importExcel', this.fileMap).then(res => {
                                    this.loading = true;
                                    if (res.data.code == "0") {
                                        this.loading = false;
                                        this.goodImportErrorsVisible =true;
                                        this.goodImportErrors=res.data.data;
                                    }
                                    else{
                                        this.$message.error( res.data.msg);
                                    }
                                }, err => {
                                    this.loading = false;
                                    this.importExcelVisible=false;
                                });

            },
            goodImprotErrorClose:function(){
                     this.goodImportErrorsVisible =false;
                      this.importExcelVisible=false;
            },
            handleImport:function () {

                this.goodImportVisible=true;
                this.getBasGoodsList(1);
            },
     //获取平台基础商品列表
            getBasGoodsList: function (pIndex) {
                this.page = pIndex;
                var param = { orderBy: "", queryObject: { goodsName: this.basGoodsFilters.goodsName}, page: this.basPage, rows: this.basPageSize };
                this.qLoading = true;
                this.axios.post('/spdHDIService/bas/goods/getGoodsInfos', param).then(res => {
                    this.qLoading = false;
                    if (res.data.code == "0") {
                        this.basGoods = res.data.data.data;
                        this.basTotal = res.data.data.total;
                       // this.basPage = res.data.data.pageNum;
                    }
                    else{
                        this.$message.error( res.data.msg);
					}
                }, err => {
                    this.qLoading = false;
                });
            },
            goodImprotSubmit:function(){
              if(this.multipleSelection.length<=0){
                   this.$message.error( "至少选择一条数据！");
                  return;
              }

                 this.axios.post('/spdHDIService/myGoods/provGoods/goodImprotFromBasGoods', this.multipleSelection).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
                     this.goodImportVisible =false;
                       this.getGoodsList(1);

                    }
                    else{
                        this.$message.error( res.data.msg);
					}
                     this.$refs.multipleTable.clearSelection();
                }, err => {
                    this.loading = false;
                });

            },
            basHandleCurrentChange(val) {
                this.basPage = val;
                this.getBasGoodsList(this.basPage);
            },
            basHandleSizeChange(val) {
                this.basPageSize = val;
                this.getBasGoodsList(this.basPage);
            },

            handleCheckAllChange(event) {
                this.checkedColumns = event.target.checked ? this.columns : [];
                this.isIndeterminate = false;
            },
            handleCheckedColumnsChange(value) {
                let checkedCount = value.length;
                this.checkAll = checkedCount === this.columns.length;
                this.isIndeterminate = checkedCount > 0 && checkedCount < this.columns.length;
            },
            selectExportColumnsSubmit:function(){
                if(this.checkedColumns.length<=0){
                    this.$message.error( "至少选择一条数据！");
                    return;
                }
                var param = this.checkedColumns;
                this.loading = true;
                this.axios.post('/spdHDIService/myGoods/provGoods/exportGoodsInfos', param).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {

                    }
                        else if(res.data.code == "998"){
                            this.downLoadVisible =true;
                            this.fileSrc=res.data.msg;
                    }
                    else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.loading = false;
                });


            },

                 //获取产家 供选择使用
            getMfrs: function () {


                this.loading = true;
                this.axios.post('/spdHDIService/bas/mfrsInfo/basMfrsInfoList', {}).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {


						let ms = res.data.data.data;
                         ms.forEach(m=>{
                             m.mfrsId = m.id
                             this.mfrs.push(m)
                         })

						if(this.mfrs)
                        this.mfr=this.mfrs[0];

                    }
                    else{
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

            multDel(){
                 if(this.multipleSelection.length<=0){

                   this.$message.error( "至少选择一条数据！");
                  return;
                  }

                   this.$confirm('确认删除选中记录吗?', '提示', {
                }).then(() => {
                    this.loading = true;
                    this.axios.post("/spdHDIService/myGoods/provGoods/multDel", this.multipleSelection).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.loading = false;
                            this.$message({ message: '操作成功！', type: 'success' });

                        } else if(res.data.code == "999"){
                            // this.$message.error( '您选择的'+this.multipleSelection.length+'条记录中有'+res.data.msg+'条与其他信息关联，这'+res.data.msg+'条未删除！');
                                                   this.$message.error( res.data.msg);


                        }

                        else {
                            this.$message.error( res.data.msg);

                        }
						this.getGoodsList(this.page);
					    this.$refs.multipleTable.clearSelection();

                    }, err => {
                        this.loading = false;
                    });
                }).catch(() => {

                });
            },
                handleRemove(file, fileList) {
                    this.uploadState=false;
                },
                handlePreview(file) {
                },
                handleFileSuccess(response, file, fileList){
                    var ob  = response.data[0];
                    this.fileMap=ob;
                    this.uploadState=true;
                },
                handleBeforeUpload(file){
                },
                getDict68s: function () {
                this.loading = true;
                  this.axios.post('/platformService/sys/dict/getDictValueByDictEname',{dictName:'68FL'}).then(res => {
                      this.loading = false;
                    if (res.data.code == "0") {
						this.dict68s = res.data.data;



                    }
                    else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.loading = false;
                });
            },
                //获取当前供应商的合作商
        getDistriRelations(){
                var param = { orderBy: "",
                              queryObject: { collectorId: this.user.corpId},
                              page: 0, rows: 0
                             };

                 this.axios.post('/spdHDIService/provMgr/distriRelation/queryPartnerList', param).then(res => {
                    if (res.data.code == "0") {

						var temp = res.data.data.data;
                        var re =[];
                        if(temp.length>0){
                            re.push(temp[0])
                            for(var p of temp){

                                if(!re.find(o=>o.provId==p.provId)){
                                    re.push(p);
                                }
                            }


                        }





                    }
                    else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                });
        }


        },
        mounted() {
            this.getGoodsList(1);
            this.getMfrs();


            this.getDict68s();
            this.ProvGoodsTempUrl=fileServer.download+'myGoods/provGoodsTemp.xls';
            this.uploadUrl =fileServer.upload +"myGoods";
            this.getDistriRelations();

        }
    };

</script>
<style>
.el-checkbox__label{
     font-size: 12px;
     vertical-align: middle;
}
.el-checkbox-group{
    vertical-align: middle;
}
.provgoods-tableheight {
        height:  calc(100% - 95px);
}
</style>


