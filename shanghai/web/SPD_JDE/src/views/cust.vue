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
        <el-dialog title="从Excel中导入客户模板" close="onClose" v-model="importExcelVisible" :show-close='true' :close-on-press-escape='true' :close-on-click-modal="true">
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
        <el-dialog title="客户查询结果" close="onClose" v-model="goodImportErrorsVisible" :close-on-click-modal="true" :show-close='true'  :close-on-press-escape='true'  size="full">
            <template>
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <el-tab-pane label="客户信息" name="first">
                        <el-table ref="multipleTable" :data="goodImportErrors"  border tooltip-effect="dark" style="width: 100%">
                            <el-table-column type="index" label="序号"  align="center"></el-table-column>
                            <el-table-column prop="ksbm" label="供应商编码"></el-table-column>
                            <el-table-column prop="ksmc" label="供应商名称" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="corp" label="公司名称" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="address" label="地址" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="gb" label="国家" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="szq" label="省直辖市自治区" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="city" label="地市" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="country" label="区县" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="isscs" label="是否生产商" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="isjxs" label="是否经销商" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="isjks" label="是否进口商" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="isqt" label="是否其他" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="scsdwxz" label="生产商单位性质" show-overflow-tooltip> </el-table-column>

                            <el-table-column prop="isyy" label="是否医院" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="isjcyjg" label="是否基层医疗机构" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="isgr" label="是否个人" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="yyf" label="医院终端大类" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="yyg" label="医院终端小类" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="yyisyl" label="医院是否营利" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="yya" label="医院是否公立" show-overflow-tooltip> </el-table-column>

                            <el-table-column prop="yyb" label="是否社保定点" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="yyc" label="医院归属" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="yyd" label=" 医疗级别" show-overflow-tooltip> </el-table-column>

                            <el-table-column prop="yye" label=" 是否托管药房" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="yynsr" label="医院年收入(万元)" show-overflow-tooltip> </el-table-column>

                            <el-table-column prop="qridcard" label="个人身份证" show-overflow-tooltip> </el-table-column>

                        </el-table>
                        
                    </el-tab-pane>
                    <el-tab-pane label="客户证照" name="second">
                        <el-table ref="multipleTable" :data="goodImportErrors"  border tooltip-effect="dark" style="width: 100%">
                            <el-table-column type="index" label="序号"  align="center"></el-table-column>
                            <el-table-column prop="ksbm" label="供应商编码"></el-table-column>
                            <el-table-column prop="ksmc" label="供应商名称" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="lictype" label="证照类型" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="licno" label="证照编码" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="lictodate" label="有效期至" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="licfw" label="一类范围" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="licfwt" label="二类范围" show-overflow-tooltip> </el-table-column>
                            <el-table-column prop="licfws" label="三类范围" show-overflow-tooltip> </el-table-column>
                        </el-table>
                    </el-tab-pane>
                    <div class="exportDiv">
                        <el-button @click.native="goodImportErrorsVisible = false;uploadState=false" size="small">关闭</el-button>
                        <el-button type="primary" @click="exportExcel('custInfo')" :loading="editLoading" size="small">导出供应商信息</el-button>
                    </div>
                </el-tabs>
            </template> 
        </el-dialog>
        <!--Excel导出下载界面-->
        <el-dialog title="导出客户模板" close="onClose" v-model="exportExcelVisible" :show-close='true' :close-on-press-escape='true' :close-on-click-modal="true">
                客户信息反写成功，请点击模板下载</br> 
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
                this.goodImportErrors = [];
                this.editLoading = true;
                this.axios.post('/baseDataQuery/bas/custInfo/basCustInfoList', this.fileMap).then(res => {
                    this.editLoading = false;
                    if (res.data.code == "0") {
                        this.loading = false;
                        this.goodImportErrorsVisible =true;
                        this.goodImportErrors=res.data.data;
                    }
                    else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.editLoading = false;
                    this.importExcelVisible=false;
                });
            },

            exportExcel:function(cust){
                this.fileMap['custOrProv'] = cust;
                this.fileMap['basCustInfo'] = this.goodImportErrors;
                this.editLoading = true;
                this.axios.post('/baseDataQuery/bas/custInfo/exportBasCustInfo', this.fileMap).then(res => {
                    if (res.data.code == "0") {
                        this.editLoading = false;
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
