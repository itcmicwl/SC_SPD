<template>
    <div id="goods" style="height: 100%;">
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" label-width="80px" :model="filters">
                <el-form-item label="商品名称">
                    <el-input v-model="filters.goodsName" placeholder="输入商品名称或规格" ></el-input>
                </el-form-item>
                <el-form-item label="生产厂家">
                    <el-input v-model="filters.mfrsName" placeholder="输入生产厂家" ></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" v-on:click="getGoodsList(1)" >查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="goodEdit(null)" >新增</el-button>
                </el-form-item>
                 <el-form-item>
                      <el-button type="primary" @click="importExcel()" >从Excel导入</el-button>
                </el-form-item>
            </el-form>
       </el-col>
        <!--列表-->
        <template>
            <el-table :data="goods" highlight-current-row v-loading="loading" style="width: 100%;" border fit  class="goods-tableheight" height="outer">
                <el-table-column prop="code" label="商品编号" header-align="center" width="90">
                </el-table-column>
                <el-table-column prop="goodsName" label="商品名称" header-align="center" min-width="200">
                </el-table-column>
                <el-table-column prop="shortPinyin" label="拼音码" header-align="center" width="120">
                </el-table-column>
                <el-table-column prop="goodsGg" label="规格型号" header-align="center" width="180">
                </el-table-column>
                <el-table-column prop="made" label="产地" header-align="center" width="60">
                </el-table-column>
                <!--<el-table-column prop="mfrsId" label="生产厂商" header-align="center" width="100"></el-table-column>-->
                <el-table-column prop="mfrsName" label="生产厂商" header-align="center" min-width="180"></el-table-column>
                <el-table-column prop="unit" label="单位" header-align="center" width="60">
                </el-table-column>
                <!--<el-table-column prop="packeage" label="包装" header-align="center">
                </el-table-column>-->
                <el-table-column prop="masterCode" label="统一码" header-align="center" width="90">
                </el-table-column>
                <el-table-column prop="kind68code" label="药监分类" header-align="center" width="90">
                </el-table-column>
                <el-table-column prop="brand" label="品牌" header-align="center" width="180">
                </el-table-column>
                <!--<el-table-column prop="certificateCode" label="注册证号" header-align="center">
                </el-table-column>-->
                <el-table-column label="操作" width="140" header-align="center" fixed="right">
                    <template slot-scope="scope">
                        <el-button-group>
                            <el-button type="primary" size="mini" @click="goodEdit(scope.row)" icon="edit"  >编辑</el-button>
                            <el-button type="danger" size="mini" @click="delGood(scope.row)" icon="delete" >删除</el-button>
                        </el-button-group>
                    </template>
                </el-table-column>
            </el-table>
            <!--工具条-->
            <el-col :span="24" class="toolbar">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[30, 100, 200, 400]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
                </el-pagination>
            </el-col>
        </template>
		<!--编辑界面-->
        <el-dialog :title="modelTitle" close="onClose" :visible.sync="editFormVisible" :close-on-click-modal="false">
            <el-form :model="good" label-width="80px" :rules="editFormRules" ref="editForm" class="el-form-item-nomsg">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="商品名称" prop="goodsName" :error="errors.goodsNameErr">
                            <el-input v-model="good.goodsName"  placeholder="请输入商品名称"></el-input>
                        </el-form-item>
                    </el-col>
					 <el-col :span="12">
                        <el-form-item label="主数据编码" prop="masterCode" >
                                    <el-input v-model="good.masterCode" placeholder="请输入主数据编码" ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="12">
                        <el-form-item label="规格型号" prop="goodsGg" :error="errors.goodsGgErr">
                            <el-input v-model="good.goodsGg"  placeholder="请输入规格型号"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="产地"  prop="made" :error="errors.madeErr">
                            <el-input v-model="good.made" ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="12">
                        <el-form-item label="品牌" prop="brand" :error="errors.brandErr">
                            <el-input v-model="good.brand"  placeholder="请输入品牌"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="5">
                        <el-form-item label="单位" prop="unit" :error="errors.unitErr">
                            <el-input placeholder="请输入单位" v-model="good.unit" ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col>
                        <el-form-item label="生产厂商" prop="mfrsId" :error="errors.mfrsIdErr">
                            <!--  <el-select v-model="selectedId" filterable placeholder="请选择">
                                    <el-option
                                        v-for="item in mfrs"
                                        :key="item.id"
                                        :label="item.mfrsName"
                                        :value="item.id">
                                    </el-option>
                            </el-select>
                            -->
                            <el-autocomplete
                                            class="inline-input"
                                            v-model="selectedName"
                                            :fetch-suggestions="querySearch"
                                            placeholder="请输入内容"
                                            @select="handleSelect"
                                            :props ="myPorps"
                                            size='small'
                                                style="width:100%"
                                            >
                            </el-autocomplete>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="包装" prop="packeage" :error="errors.packeageErr">
                            <el-input v-model.number="good.packeage" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="统一码">
                            <el-input placeholder="请输入统一码" v-model="good.masterCode" ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="厂家物料编码">
                            <el-input v-model="good.mfrsCode" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <!--<el-form-item label="药监分类">
                            <el-input v-model="good.kind68code" ></el-input>
                        </el-form-item>-->
                        <el-form-item label="药监分类" prop="kind68code" >
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

                </el-row>
                <el-row>
                    <el-col>
                        <el-form-item label=" 备注信息" prop="remark" :error="errors.remarkErr">
                            <el-input type="textarea" v-model="good.remark" ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <!--<el-form-item label="类别">
                    <el-input placeholder="请输入类别" v-model="good.kindCode" ></el-input>
                </el-form-item>-->

                <!--<el-form-item label="注册证号">
                    <el-input placeholder="请输入注册证号" v-model="good.certificateCode" ></el-input>
                </el-form-item>
                <el-form-item label="类别属性">
                    <el-input placeholder="请输入类别属性" v-model="good.lbsx" ></el-input>
                </el-form-item>-->


            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
            </div>
        </el-dialog>
        <!--Excel导入界面-->
        <el-dialog title="从Excel中导入商品" close="onClose" :visible.sync="importExcelVisible" :show-close='false' :close-on-press-escape='false' :close-on-click-modal="false">
                    下载模板</br> <a :href="BaseGoodsTempUrl" >BasGoodsTemp.xls</a>
                <template>
                                <div>
                                    <el-upload
                                    class="upload-demo"
                                    :disabled="uploadState"
                                    :action= "uploadUrl"
                                    :on-remove="handleRemove"
                                    :on-success="handleFileSuccess"

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

                            <div style="margin-top: 20px">
                                <el-button @click="goodImprotErrorClose()">关闭</el-button>
                            </div>
                        </template>
        </el-dialog>
	</div>
</template>
<script>
import { fileServer } from '../../common/js/constance';
    export default {
        data() {
            return {
                filters: {
                  goodsName: '',
                  mfrsName:''
                },
                importExcelVisible:false,
                fileSrc:'',
                fileMap:{id:'',ext:'',name:'',path:''},
                fileList:[],
                goodImportErrorsVisible:false,
                goodImportErrors:[],
                BaseGoodsTempUrl:'',
                uploadUrl:'',
                uploadState:false,
                selectedId:'' ,    //选中的厂家Id
                selectedName:'' ,    //选中的厂家name
                myPorps:{
                        value:'mfrsId',
                        label:'mfrsName'
                 },
                total: 0,
                page: 1,
                pageSize: 30,
                loading: false,
                goods: [],
                good: {},
                modelTitle: "",
                editFormVisible: false,
                editLoading: false,
                kind68code:'',
                editFormRules: {
                  goodsName: [
                        { required: true, message: '请输入商品名称', trigger: 'blur' },
                        {max:150,message:'必须小于150个字符'}
                    ],
                    code: [
                        { required: true, message: '请输入商品编号', trigger: 'blur' }
                    ],
                    goodsGg: [
                        { required: true, message: '请输入商品规格', trigger: 'blur' }
                    ],
                    unit: [
                        { required: true, message: '请输入商品单位', trigger: 'blur' }
                    ],
                    mfrsId: [
                        { required: true, message: '请选择生产厂商', trigger: 'blur' }
                    ],
                     made: [
                        { required: true, message: '请输入产地', trigger: 'blur' }
                    ],
                      packeage: [
                        {type:"number", required: false, message: '请输入包装，必须为数字', trigger: 'blur' }
                    ],
                    brand: [
                          {max:128,message:'品牌必须小于128个字符'}
                    ],
                      remark: [
                          {max:64,message:'备注必须小于64个字符'}
                    ]

                },
                errors: {
                    goodsNameErr: '',
                    codeErr: '',
                    goodsGgErr:'',
                    unitErr:'',
                    mfrsIdErr:'',
                    madeErr:'',
                    packeageErr:'',
                    brandErr:'',
                    remarkErr:''
                },
                mfrs:[] ,    //生产厂家
                dict68s:[],
                mfr:{id:'',mfrsName:''}
            }
        },
        methods: {

           /* 输入框自动补全**/
            handleClick(){
          //      alert("xx");
            },
            querySearch(queryString, cb) {
                var restaurants = this.mfrs;
                var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
                // 调用 callback 返回建议列表的数据
                cb(results);
            },
            createFilter(queryString) {
                return (restaurant) => {
                return (restaurant.mfrsName.indexOf(queryString) >= 0);
                };
            },
            handleSelect(item) {
                    this.selectedId= item.id;
                    this.selectedName=item.mfrsName;
            },
          /* 输入框自动补全**/
            //获取列表
            getGoodsList: function (pIndex) {
               // alert(this.filter.mfrsName);
                this.page = pIndex;
                var param = {
                     orderBy: "",
                     queryObject: { goodsName: this.filters.goodsName ,mfrsName:this.filters.mfrsName},
                     page: this.page, rows: this.pageSize
                     };
                this.loading = true;
                this.axios.post('/spdHDIService/bas/goods/getGoodsInfos', param).then(res => {
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

                // this.errors={};
                 this.errors.goodsNameErr = '';
                 this.errors.codeErr = '';
                 this.errors.mfrsIdErr = '';
                 this.errors.goodsGgErr = '';
                 this.errors.unitErr = '';
                 this.errors.madeErr = '';
                 this.errors.brandErr = '';
                 this.errors.packeageErr = '';
                  this.errors.remarkErr  = '';

                if (item) {
                    let good = Object.assign({}, item);
                    this.good = good;
                    this.kind68code=this.good.kind68code;

                    this.modelTitle = "修改商品";
                    this.selectedId =item.mfrsId;
                    this.selectedName=item.mfrsName;
                } else {
                    this.good={};
                     this.kind68code='';
                    this.modelTitle = "添加商品";
                    this.selectedId='';
                    this.selectedName='';
                }
                 this.editFormVisible = true;
                // this.$refs.editForm.resetFields();

            },
            delGood(good) {
                this.$confirm('确认删除这条记录吗?', '提示', {
                }).then(() => {
                    this.loading = true;
                    this.axios.post("/spdHDIService/bas/goods/delGoodsInfo", good).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.loading = false;
                            this.$message({ message: '操作成功！', type: 'success' });
                        } else {
                            this.$message.error( res.data.msg);

                        }
						this.getGoodsList(this.page);
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

                        let serviceUrl = this.good.id ? "/spdHDIService/bas/goods/update" : "/spdHDIService/bas/goods/insert";

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
                                        if (err.field == "goodsName") {
                                            this.errors.goodsNameErr += err.message;
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
            importExcel:function(){
                 this.fileList=[];
                 this.importExcelVisible=true;
            },
            importExcelSubmit:function(){
                this.uploadState=false;
                this.axios.post('/spdHDIService/bas/goods/importExcel', this.fileMap).then(res => {
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
            handleRemove(file, fileList) {
                    this.uploadState=false;
                },

            handleFileSuccess(response, file, fileList){
                    var ob  = response.data[0];
                    this.fileMap=ob;
                    this.uploadState=true;
                },
                    //获取产家 供选择使用
            getMfrs: function () {


                this.loading = true;
                this.axios.post('/spdHDIService/bas/mfrsInfo/basMfrsInfoList', {}).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
						this.mfrs = res.data.data.data;
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
            getDict68s: function () {


                this.loading = true;
                  this.axios.post('/platformService/sys/dict/getDictValueByDictEname',{dictName:'68FL'}).then(res => {                    this.loading = false;
                    if (res.data.code == "0") {
						this.dict68s = res.data.data;



                    }
                    else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.loading = false;
                });
            }
        },
        mounted() {
            this.getGoodsList(1);
            this.getMfrs();
            this.getDict68s();
            this.BaseGoodsTempUrl='/upload/baseData/BaseGoodsTemp.xls';
            this.uploadUrl =fileServer.upload +"baseData";
        }
    };

</script>

<style>
    #goods .goods-tableheight {
        height:  calc(100% - 100px);
    }
</style>
