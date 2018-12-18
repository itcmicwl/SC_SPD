<template>
    <div  style="height: 100%;">
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" label-width="80px" >
                 <el-form-item>
                      <el-button type="primary" @click="importExcel()" >从Excel导入</el-button>
                </el-form-item>
            </el-form>
       </el-col>
        <!--Excel导入界面-->
        <el-dialog title="从Excel中导入商品" close="onClose" :visible.sync="importExcelVisible" :show-close='false' :close-on-press-escape='false' :close-on-click-modal="false">
                    下载模板</br> <a :href="BaseGoodsTempUrl" >InitGoodsTemp.xls</a>
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
                <div style="margin-top:5px;margin-bottom:5px" v-show="progressFlag">
                        <span>当前处理到 {{cur}}/{{ s_total}}</b></span>
                        <el-progress :text-inside="true" :stroke-width="18" :percentage="percentage" :status="proStatus"></el-progress>
                    </div>
                <div slot="footer" class="dialog-footer">
                    <el-button @click.native="importExcelVisible = false;uploadState=false; isSubmited = false;progressFlag=false;status=2">取消</el-button>
                    <el-button type="primary" @click.native="importExcelSubmit" :disabled="isSubmited" :loading="editLoading">提交</el-button>
                </div>


        </el-dialog>
        <el-dialog title="导入失败数据" close="onClose" :visible.sync="goodImportErrorsVisible" :close-on-click-modal="false" :show-close='false'  :close-on-press-escape='false'  size="large">
                        <template>
					     <div style="margin-top: 20px">
                                <el-button @click="goodImprotErrorClose()">关闭</el-button>
                            </div>
                    	     <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                                      本次导入数： <span>{{ dataSize }}</span>      失败数： <span>{{ errorSize }}</span>
                            </el-col>
                            <el-table
                                ref="multipleTable" :data="goodImportErrors"  border tooltip-effect="dark"
                                style="width: 100%">
                                <el-table-column  prop="goodsName" label="商品名称" width="200"></el-table-column>
                                <el-table-column prop="provCode" label="供应商商品编号" show-overflow-tooltip> </el-table-column>
                                    <el-table-column prop="mfrsName" label="生产厂家" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="goodsGg" label="规格" show-overflow-tooltip> </el-table-column>
                                    <el-table-column prop="errorDesc" label="失败原因" show-overflow-tooltip> </el-table-column>
                            </el-table>
                       
                        </template>
        </el-dialog>
	</div>
</template>
<script>
import { fileServer } from '../../common/js/constance';
    export default {
        data() {
            return {
                proStatus:'',
              //  outFlag:true,
                status :0,
                percentage:0,
                s_taskId:'',
                s_total:0,
                cur:0,
                resultTotal:0,
                progressFlag:false,
                isSubmited:false,
                dataSize:0,
				errorSize:0,
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

       catProDoing(){
              this.axios.get('/spdHDIService/bas/goods/catProvByProperties/1/'+ this.s_taskId).then(res => {
                    this.editLoading = false;
                    if (res.data.code == "0") {
						this.status =res.data.data.status; 
						this.s_total =res.data.data.total;
						this.cur = res.data.data.cur;
                        this.percentage=parseInt(res.data.data.cur*100/res.data.data.total);
                        this.resultTotal = res.data.data.resultTotal;
						if(this.status!=2){
                            window.setTimeout(this.catProDoing,3000);
						}else{
                            this.outFlag=false;
                            this.proStatus = 'success';

                            this.goodImportErrorsVisible=true;
                            this.dataSize =res.data.data.rows.length;
                            res.data.data.rows.find(o=>{
										  if(o.errorFlag=='1')
										    this.goodImportErrors.push(o);
										});
										this.errorSize = this.goodImportErrors.length;
						}
                    }else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                });
			},

            importExcel:function(){
                 this.fileList=[];
                 this.importExcelVisible=true;
            },
            importExcelSubmit:function(){
                this.progressFlag =true;
                this.isSubmited = true;
                this.uploadState=false;
                this.status=1;

                //提交后台，立即返回进度条
            this.axios.post('/spdHDIService/bas/goods/importExcel4init', this.fileMap).then(res => {
                    this.editLoading = false;
                    if (res.data.code == "0") {
                        this.loading = false;
                      //  this.goodImportErrorsVisible =true;
                       // this.goodImportErrors=res.data.data;
                        this.s_taskId=res.data.tag;
                        window.setTimeout(this.catProDoing,3000);
                    }
                    else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.editLoading = false;
                    this.importExcelVisible=false;
                });
              
            },
             goodImprotErrorClose:function(){
			            this.goodImportErrors=[];
					  this.errorSize =0;
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

        },
        mounted() {

            this.BaseGoodsTempUrl='/upload/baseData/InitGoodsTemp.xls';
            this.uploadUrl =fileServer.upload +"baseData";
        }
    };

</script>

<style scoped>
    .goods-tableheight {
        height:  calc(100% - 75px);
    }
</style>
