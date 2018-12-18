<template>
    <div  style="height: 100%;">	
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" label-width="80px" >
                 <el-form-item>
                      <el-button type="primary" @click="importExcel()" size="small">从Excel导入</el-button>
                </el-form-item>
            </el-form>
       </el-col>
        <!--列表-->
	
        <!--Excel导入界面-->
        <el-dialog title="从Excel中导入商品" close="onClose" v-model="importExcelVisible" :show-close='true' :close-on-press-escape='true' :close-on-click-modal="true">
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
                            <el-button size="small" type="primary">点击上传</el-button>
                            <div slot="tip" class="el-upload__tip">只能上传一个Excel文件</div>
                            </el-upload>
                        </div>
                </template>
            
                <div slot="footer" class="dialog-footer">
                    <el-button @click.native="importExcelVisible = false;uploadState=false">取消</el-button>
                    <el-button type="primary" @click.native="importExcelSubmit" :loading="editLoading">提交</el-button>
                </div>
        </el-dialog>
        <el-dialog title="商品查询结果" close="onClose" v-model="goodImportErrorsVisible" :close-on-click-modal="true" :show-close='true'  :close-on-press-escape='true'  size="full">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <el-tab-pane label="商品信息" name="first">
                        <el-table
                                ref="multipleTable" :data="goodImportErrors"  border tooltip-effect="dark" 
                                style="width: 100%">
                                <el-table-column type="index" label="序号"  align="center" width="62"></el-table-column>
                                <el-table-column prop="zzco" label="公司编码" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="zzlitm" label="商品编码" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8name" label="商品名称" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8dsc2" label="规格型号" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="imuom1" label="计量单位" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="imuom3" label="采购计量单位" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="imuom4" label="定价/销售计量单位" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="imglpt" label="总帐分类" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="imlnty" label="行类型" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8jx" label="剂型" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="span8" label="生产厂家" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="sporig" label="生产国" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8pzwh" label="批准文号" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8zdyl" label="诊断药品大类" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8jhg" label="进口/合资/国产" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8ztw" label="自产/贴牌/外产" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8gfg" label="国家/非国家基本药物" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8ptyp" label="普通/特殊药品" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8atcdl" label="ATC大类" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8atcxl" label="ATC小类" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8yyks" label="应用科室" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8fxdj" label="风险等级" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8cctj" label="存储条件" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8qtcc" label="存储条件说明" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8jgg" label="进口/国产/港澳台" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8tym" label="通用名" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8xlhgl" label="是否序列号管理" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8zdyh" label="是否重点养护" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8yhzq" label="养护周期" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="ele8zgkc" label="最高库存" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="ele8zdkc" label="最低库存" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="ele8zdxj" label="销售最低限价" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="ele8zgxj" label="采购最高限价" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="ele8zdmll" label="销售最低毛利率" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="ele8bzq" label="商品保质期" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="ele8bzqdw" label="保质期单位" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8yhnr" label="优惠内容" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8yhzc" label="是否享受优惠政策" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8lsl" label="零税率" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8kce" label="扣除额" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8cpfl" label="产品分类" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8xqts" label="效期预警天数" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="spe8cjwlbm" label="厂家物料编码" show-overflow-tooltip> </el-table-column>
                            </el-table>
                    </el-tab-pane>
                    <el-tab-pane label="商品证照" name="second">
                        <el-table ref="multipleTable" :data="goodImportErrors"  border tooltip-effect="dark" style="width: 100%">
                            <el-table-column type="index" label="序号"  align="center"></el-table-column>
                            <el-table-column prop="zzco" label="公司编码" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="zzlitm" label="商品编码" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="spe8name" label="商品名称" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="spe8dsc2" label="规格型号" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="zze8zzbm" label="证照编码" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="zze8zzlxa" label="证照类型" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="zzeftj" label="有效期自" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="zzexdj" label="有效期至" show-overflow-tooltip> </el-table-column>
                        </el-table>
                    </el-tab-pane>
                </el-tabs>
                <div class="exportDiv">
                    <el-button @click.native="goodImportErrorsVisible = false;uploadState=false" size="small">关闭</el-button>
                    <el-button type="primary" @click="exportExcel('provInfo')" :loading="editLoading" size="small">导出商品信息</el-button>
                </div>
        </el-dialog>
         <!--Excel导出下载界面-->
        <el-dialog title="导出商品模板" close="onClose" v-model="exportExcelVisible" :show-close='true' :close-on-press-escape='true' :close-on-click-modal="true">
                供应商信息反写成功，请点击模板下载</br> 
                <a :href="BaseGoodsTempUrl" style="color:blue;">{{fileMap['name']}}</a>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="exportExcelVisible = false;uploadState=false">关闭</el-button>
            </div>
        </el-dialog>
	</div>
</template>
<script>
import { fileServer } from '../common/js/constance';
    export default {
        data() {
            return {
                activeName: 'first',
                excelFilePath:'',
                exportExcelVisible:false,
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
                mfrs:[] ,    //生产厂家
                mfr:{id:'',mfrsName:''} 
            }
        },
        methods: {
            handleClick(tab, event) {
            },
            importExcel:function(){
                 this.fileList=[];
                 this.importExcelVisible=true;
            },
            importExcelSubmit:function(){
                this.uploadState=false;
                this.editLoading = true;
                this.axios.post('/baseDataQuery/bas/goodsInfo/getBasGoodsInfoListByRegister', this.fileMap).then(res => {
<<<<<<< .mine
                    this.editLoading = false;
=======
                    this.loading = true;
>>>>>>> .theirs
                    if (res.data.code == "0") {
                        this.goodImportErrorsVisible =true;
                        this.goodImportErrors=res.data.data;
                    }else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.editLoading = false;
                    this.importExcelVisible=false;
                });
            },

            exportExcel:function(prov){
                this.fileMap['basGoodsInfo'] = this.goodImportErrors;
                this.editLoading = true;
                this.axios.post('/baseDataQuery/bas/goodsInfo/exportBasGoodsInfo', this.fileMap).then(res => {
                    this.editLoading = false;
                    if (res.data.code == "0") {
                        this.excelFilePath=res.data.data;
                        this.exportExcelVisible = true;
                    }else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.editLoading = false;
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
                this.BaseGoodsTempUrl = 'upload/'+ob['path']+'/'+ob['id']+'.'+ob['ext'];
                this.uploadState=true;
            },

        },
        mounted() {
            this.BaseGoodsTempUrl='/upload/baseData/mfrsModal.xls';
            this.uploadUrl =fileServer.upload +"baseData";
        }
    };

</script>

<style>	
    .goods-tableheight {        
        height:  calc(100% - 75px);
    }
    .exportDiv{
        text-align: center;
        margin-top: 10px;
        margin-bottom: 5px;
    }
</style>
