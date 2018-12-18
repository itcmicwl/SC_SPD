<template>
    <div id="barcode-rules" style="height: 100%;">
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form label-width="60px">
                <el-form-item label="条码">
                    <el-col :span="7">
                        <el-input  v-model="filters.barcode" icon="search" :on-icon-click="getbarcodeRulesList"></el-input>
                    </el-col>
                    <el-col :span="2" align="center">条码长度</el-col>
                    <el-col :span="1">
                        <el-input  v-model="filters.barcodeLen"></el-input>
                    </el-col>
                    <el-col :span="2" align="right">
                            <el-button type="primary" @click="barcodeRuleEdit(null)" >新增</el-button>
                    </el-col>
                </el-form-item>
            </el-form>
        </el-col>
        <!--列表-->
        <el-table highlight-current-row class="barcoderules-tableheight" v-loading="loading" :data="barcodeRulesData" style="width: 100%" border height="outer">
            <el-table-column type="index" label="序号" width="55" align="center"></el-table-column>
            <el-table-column prop="barcode" width="300" label="条码样例" header-align="center" ></el-table-column>
            <el-table-column prop="barcodeLen" :formatter="formatter" label="条码长度" header-align="center" width="60" ></el-table-column>
            <el-table-column prop="barleader" label="条码前缀" header-align="center" width="60" ></el-table-column>
            <el-table-column prop="sign1Position" :formatter="formatter" header-align="center" width="78" label="特征1码位置" ></el-table-column>
            <el-table-column prop="sign1" header-align="center" width="55" label="特征1码"></el-table-column>
            <el-table-column prop="sign2Position" :formatter="formatter" header-align="center" width="78" label="特征2码位置" ></el-table-column>
            <el-table-column prop="sign2" header-align="center" width="55" label="特征2码"></el-table-column>
            <el-table-column prop="isMain" :formatter="formatter" label="条码类型" header-align="center" width="60" ></el-table-column>
            <el-table-column prop="consumablesPosition" :formatter="formatter" header-align="center" width="83" label="耗材条码位置" ></el-table-column>
            <el-table-column prop="consumablesLen" :formatter="formatter" header-align="center" width="83" label="耗材条码长度" ></el-table-column>
            <el-table-column prop="periodPosition" :formatter="formatter" header-align="center" width="78" label="有效期位置" ></el-table-column>
            <el-table-column prop="periodFormat" header-align="center" width="78" label="有效期格式" ></el-table-column>
            <el-table-column prop="tracknumPosition" :formatter="formatter" header-align="center" width="78" label="跟踪码位置" ></el-table-column>
            <el-table-column prop="tracknumLen" :formatter="formatter" header-align="center" width="78" label="跟踪码长度" ></el-table-column>
            <el-table-column prop="productionPosition" :formatter="formatter" header-align="center" width="83" label="生产批号位置" ></el-table-column>
            <el-table-column prop="productionLen" :formatter="formatter" header-align="center" width="83" label="生产批号长度" ></el-table-column>
            <el-table-column label="操作" width="140" header-align="center" fixed="right">
                <template slot-scope="scope">
                    <el-button-group>
                        <el-button type="primary" size="mini" @click="barcodeRuleEdit(scope.row)" icon="edit"  >编辑</el-button>
                        <el-button type="danger" size="mini" @click="handleDelete(scope.row)" icon="delete" >删除</el-button>
                    </el-button-group>
                </template>
            </el-table-column>
        </el-table>

        <!--编辑界面-->
        <el-dialog :title="modelTitle"  close="onClose" :visible.sync="editFormVisible" :close-on-click-modal="false">
            <el-form :model="barcodeRule" label-width="90px" :rules="editFormRules" ref="editForm" :show-message="false" class="el-form-item-nomsg">
                <el-row>
                    <el-col>
                        <el-form-item label="条码样例" prop="barcode" :error="errors.barcodeErr">
                            <el-input v-model="barcodeRule.barcode" ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="条码长度" prop="barcodeLen" :error="errors.barcodeLenErr">
                            <el-input v-model="barcodeRule.barcodeLen" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="条码前缀" prop="barleader" :error="errors.barleaderErr">
                            <el-input v-model="barcodeRule.barleader"  ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="特征1码位置">
                            <el-input  v-model="barcodeRule.sign1Position" :formatter="formatter"  ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="特征1码">
                            <el-input  v-model="barcodeRule.sign1" ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="特征2码位置">
                            <el-input  v-model="barcodeRule.sign2Position" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="特征2码">
                            <el-input  v-model="barcodeRule.sign2" ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="耗材码位置">
                            <el-input  v-model="barcodeRule.consumablesPosition" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="耗材码长度">
                            <el-input  v-model="barcodeRule.consumablesLen" ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="有效期位置">
                            <el-input  v-model="barcodeRule.periodPosition" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="有效期格式">
                            <el-input  v-model="barcodeRule.periodFormat" ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="跟踪码位置">
                            <el-input  v-model="barcodeRule.tracknumPosition" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="跟踪码长度">
                            <el-input  v-model="barcodeRule.tracknumLen" ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="生产批号位置">
                            <el-input  v-model="barcodeRule.productionPosition" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="生产批号长度">
                            <el-input  v-model="barcodeRule.productionLen" ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="条码类型">
                            <el-radio-group v-model="isMain" >
                                <el-radio-button label="1">主码</el-radio-button>
                                <el-radio-button label="0">副码</el-radio-button>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="是否启用">
                            <el-radio-group v-model="isFlag" >
                                <el-radio-button label="1">启用</el-radio-button>
                                <el-radio-button label="0">停用</el-radio-button>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                filters: {
                    barcode: '',
                    barcodeLen:''
                },
                loading: false,
                barcodeRulesData: [
                ],

                barcodeRule: {},
                isMain:'1',
                isFlag:'1',
                modelTitle: "",
                editFormVisible: false,
                editLoading: false,
                editFormRules: {
                    barcode: [
                        { required: true, message: '请输入条码样例', trigger: 'blur' }
                    ],
                    barcodeLen: [
                        { required: true, message: '请输入条码长度', trigger: 'blur' }
                    ],
                    barleader: [
                        { required: true, message: '请输入条码前缀', trigger: 'blur' }
                    ],
                },
               errors: {
				barcodeErr: '',
                barcodeLenErr:'',
                barleaderErr:''
               }
            }
        },
        methods: {
            //获取列表
            getbarcodeRulesList: function (pIndex) {
                this.page = pIndex;
                var param = {  barcode: this.filters.barcode,barcodeLen:this.filters.barcodeLen,flag:'1'} ;
                this.loading = true;
                this.axios.post('/spdHDIService/baseData/basBarcodeRules/list', param).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
                        this.barcodeRulesData = res.data.data;
                    }
                    else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.loading = false;
                });
            },
            barcodeRuleEdit(item) {
                this.barcodeErr = '';
                this.barcodeLenErr = '';
				if (item) {
                    item.barcodeLen = item.barcodeLen.toString();
                    item.sign1Position = item.sign1Position>0?item.sign1Position.toString():'';
                    item.sign2Position = item.sign2Position>0?item.sign2Position.toString():'';
                    item.consumablesPosition = item.consumablesPosition>0?item.consumablesPosition.toString():'';
                    item.consumablesLen = item.consumablesLen>0?item.consumablesLen.toString():'';
                    item.periodPosition = item.periodPosition>0?item.periodPosition.toString():'';
                    item.tracknumPosition = item.tracknumPosition>0?item.tracknumPosition.toString():'';
                    item.tracknumLen = item.tracknumLen>0?item.tracknumLen.toString():'';
                    item.productionPosition = item.productionPosition>0?item.productionPosition.toString():'';
                    item.productionLen = item.productionLen>0?item.productionLen.toString():'';
                    let barcodeRule = Object.assign({}, item);

                    this.barcodeRule = barcodeRule;
                    this.isMain=this.barcodeRule.isMain;
                    this.isFlag=this.barcodeRule.flag;
                    this.modelTitle = "修改条码";
                } else {
                    this.barcodeRule={};
                    this.modelTitle = "添加条码";
                    this.isMain='1';
                    this.isFlag='1';
                }
                this.editFormVisible = true;

            },
            handleDelete(barcodeRule) {
                 this.$confirm('确认删除这条记录吗?', '提示', {
                 }).then(() => {
                     this.loading = true;
                     this.axios.post("/spdHDIService/baseData/basBarcodeRules/delete", barcodeRule).then(res => {
                         this.loading = false;
                         if (res.data.code == "0") {
                             this.loading = false;
                             this.$message({ message: '操作成功！', type: 'success' });
                         } else {
                             this.$message.error( res.data.msg);

                         }
                         this.getbarcodeRulesList(this.page);
                     }, err => {
                         this.loading = false;
                     });
                 }).catch(() => {

                 });
             },
            editSubmit: function () {
              var _this = this;
              var form =  this.$refs.editForm;
              const h = this.$createElement;
              var arr=new Array();
              this.barcodeRule.isMain=this.isMain;
              this.barcodeRule.flag=this.isFlag;
              form.validate((valid,l) => {
                    if (valid) {
                        this.editLoading = true;
                        let serviceUrl = this.barcodeRule.id ? "/spdHDIService/baseData/basBarcodeRules/update" : "/spdHDIService/baseData/basBarcodeRules/add";
						this.axios.post(serviceUrl, this.barcodeRule).then(res => {
                            this.editLoading = false;
                            if (res.data.code == "0") {
                                this.$message({ message: '操作成功！', type: 'success' });
                                this.editFormVisible = false;

                                this.getbarcodeRulesList(this.page);
                            } else if (res.data.code == "-122") {
                                    let err = res.data.validateErrors.fieldErrors[0];
                                        if (err.field == "barcode") {
                                        this.errors.barcodeErr += err.message;
                                        }
                                        if (err.field == "barcodeLen") {
                                            this.errors.barcodeLenErr += err.message;
                                        }
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
                 //this.$Msgbox({title:"error",message:msg,type:"error"});
					}
                });
            },
            formatter(row,column){
                var strValue=row[column.property]>0?row[column.property].toString():'';
                if (column.property=='isMain'){
                    strValue=='1'?strValue='主码':strValue='副码';
                }
                return strValue;
            }
        },
        mounted() {
            this.getbarcodeRulesList(1);
        }
    };

</script>
<style>
    #barcode-rules .barcoderules-tableheight {
        height:  calc(100% - 80px);
    }
</style>
