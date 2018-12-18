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
        <el-dialog title="从Excel中导入厂商模板" close="onClose" v-model="importExcelVisible" :show-close='true' :close-on-press-escape='true' :close-on-click-modal="true">
                    <!-- 下载模板</br> <a :href="BaseGoodsTempUrl" >InitGoodsTemp.xls</a> -->
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
        <el-dialog title="厂商查询结果" close="onClose" v-model="goodImportErrorsVisible" :close-on-click-modal="true" :show-close='true'  :close-on-press-escape='true'  size="large">
            <template>
                <el-table
                    ref="multipleTable" :data="goodImportErrors"  border tooltip-effect="dark" 
                    style="width: 100%">
                    <el-table-column type="index" label="序号"  align="center" width="62"></el-table-column>
                    <el-table-column prop="aban8" label="生产厂家编码" width="200"></el-table-column>
                    <el-table-column prop="kse8name" label="生产厂家名称" show-overflow-tooltip> </el-table-column>
                    <el-table-column prop="zze8zzbm" label="证照编码" show-overflow-tooltip> </el-table-column>
                    <el-table-column prop="zze8zzlxa" label="证照类型" show-overflow-tooltip> </el-table-column>
                    <el-table-column prop="zzeftjs" label="证照有效期自" show-overflow-tooltip> </el-table-column>
                    <el-table-column prop="zzexdjs" label="证照有效期至" show-overflow-tooltip> </el-table-column>
                </el-table>
                
                <div style="margin-top: 20px">
                    <el-button @click="goodImprotErrorClose()" size="small">关闭</el-button>
                    <el-button type="primary" @click="exportExcel()" :loading="editLoading" size="small">导出Excel表</el-button>
                </div>
                
            </template> 
        </el-dialog>
        <!--Excel导出下载界面-->
        <el-dialog title="导出厂商模板" close="onClose" v-model="exportExcelVisible" :show-close='true' :close-on-press-escape='true' :close-on-click-modal="true">
                厂商信息反写成功，请点击模板下载</br> 
                <a :href="BaseGoodsTempUrl" style="color:blue;">{{fileMap['name']}}</a>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="exportExcelVisible = false;uploadState=false">取消</el-button>
            </div>
        </el-dialog>
	</div>
</template>
<script>
import { fileServer } from '../common/js/constance';
    export default {
        data() {
            return {
                excelFilePath:'',
                importExcelVisible:false,
                exportExcelVisible:false,
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
                exportLoading: false,
                kind68code:'',
          
                mfrs:[] ,    //生产厂家
            
                mfr:{id:'',mfrsName:''} 
            }
        },
        methods: {
            importExcel:function(){
                 this.fileList=[];
                 this.importExcelVisible=true;
            },
            importExcelSubmit:function(){
                this.uploadState=false;
                this.goodImportErrors = [];
                this.editLoading = true;
                this.axios.post('/baseDataQuery/bas/mfrsInfo/basMfrsInfoList', this.fileMap).then(res => {
                    this.editLoading = false;
                    if (res.data.code == "0") {
                        this.loading = false;
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

            exportExcel:function(){
                this.fileMap['basMfrsInfo'] = this.goodImportErrors;
                this.editLoading = true;
                this.axios.post('/baseDataQuery/bas/mfrsInfo/exportBasMfrsInfo', this.fileMap).then(res => {
                    if (res.data.code == "0") {
                        this.editLoading = false;
                        this.goodImportErrorsVisible = false;
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
</style>
