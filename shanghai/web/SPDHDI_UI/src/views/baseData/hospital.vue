<template>
	<div id="hospital" style="height: 100%;">
			<!--工具条-->
			<!--<el-col :span="24">-->
				<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
					<el-form :inline="true" label-width="100px">
						<el-form-item label="医疗机构名称">
							<el-input v-model="filters.cname" placeholder="输入医疗机构名称" ></el-input>
						</el-form-item>
                        <el-form-item>
						    <el-button type="primary" v-on:click="getUnitList(1)" >查询</el-button>
                        </el-form-item>
                        <el-form-item>
						    <el-button type="primary" @click="unitEdit(null)" >新增</el-button>
                        </el-form-item>
					</el-form>
				</el-col>
				<!--列表-->
				<template>
					<el-table :data="units" highlight-current-row v-loading="loading" style="width: 100%;" border fit  class="hospitail-tableheight" height="outer">
						<el-table-column type="index" label="序号" width="62" header-align="center">
						</el-table-column>
						<el-table-column prop="code" label="单位编号" width="110" header-align="center" sortable>
						</el-table-column>
						<el-table-column prop="cname" label="单位名称" min-width="180" header-align="center" sortable>
						</el-table-column>
						<!--<el-table-column prop="anotherName" label="单位别名" sortable>
						</el-table-column>-->
						<el-table-column prop="shortPinyin" label="拼音码" width="120" header-align="center" sortable>
						</el-table-column>
						<el-table-column prop="linkman" label="联系人" width="90" header-align="center">
						</el-table-column>
						<el-table-column prop="legal" width="90" label="法人代表" header-align="center">
						</el-table-column>
						<el-table-column prop="contactWay" label="联系方式" width="120" header-align="center">
						</el-table-column>
                          <!--
						<el-table-column prop="lastUpdateDatetime" label="最后修改时间" width="160" header-align="center" sortable>
						</el-table-column>-->
                        <el-table-column label="操作" width="120" align="center" fixed="right">
							<template slot-scope="scope">
                                <el-button-group>
								    <el-button type="primary" size="mini" @click="unitEdit(scope.row)" icon="edit"  >编辑</el-button>
								    <el-button type="danger" size="mini" @click="delUnit(scope.row)" icon="delete" >删除</el-button>
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
					<el-form :model="unit" label-width="120px" :rules="editFormRules" ref="editForm" class="el-form-item-nomsg">
						<el-row>
                            <el-col>
                                <el-form-item label="医疗机构名称" prop="cname" :error="errors.cnameErr" >
                                    <el-input v-model="unit.cname"  placeholder="请输入医疗机构名称"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
						    <!--
                            <el-col :span="12">
                                <el-form-item label="医疗机构编号" prop="code" :error="errors.codeErr">
                                    <el-input v-model="unit.code" placeholder="请输入医疗机构编号" ></el-input>
                                </el-form-item>
                            </el-col>
							-->
                           <!-- <el-col :span="12">
                                <el-form-item label="拼音码">
                                    <el-input v-model="unit.shortPinyin"  ></el-input>
                                </el-form-item>
                            </el-col>
                            -->
                            <el-col :span="12">
                                        <el-form-item label="内部ERP编码">
                                            <el-input v-model="unit.erpCode"  ></el-input>
                                        </el-form-item>
                                    </el-col>
							<el-col :span="12">
                               <el-form-item label="主数据编码" prop="masterCode">
							       <el-input v-model="unit.masterCode" placeholder="请输入主数据编码" ></el-input>
						       </el-form-item>
                            </el-col>
                        </el-row>
						<!--<el-form-item label="公司别名">
							<el-input v-model="unit.anotherName"  placeholder="请输入公司别名"></el-input>
						</el-form-item>-->
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="法人代表">
                                    <el-input  v-model="unit.legal" ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="传真">
                                    <el-input  v-model="unit.contactFax" ></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="联系人">
                                    <el-input  v-model="unit.linkman" ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="联系方式">
                                    <el-input  v-model="unit.contactWay" ></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="9">
                                <el-form-item label="邮政编码">
                                    <el-input  v-model="unit.postCode" ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="15">
                                <el-form-item label="电子邮箱">
                                    <el-input  v-model="unit.email" ></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col>
                                <el-form-item label="地址">
                                    <el-input  v-model="unit.address" ></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                        <!--
                            <el-col :span="15">
                                <el-form-item label="企业logo">
                                    <el-input  v-model="unit.logo" ></el-input>
                                </el-form-item>
                            </el-col>
                        -->
                            <el-col >
                                <el-form-item label=" 是否三证合一">
                                     <el-select v-model="isThreeInOneVal" placeholder="请选择" >
                                        <el-option key="1" label="是" value="1"></el-option>
                                        <el-option key="0" label="否" value="0"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="营业执照注册号">
                                    <el-input v-model="unit.regCode"  ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="经营许可证编号">
                                    <el-input v-model="unit.jyxkCode"  ></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
						<el-row>
                            <el-col>
                                <el-form-item label="纳税人识别号">
                                    <el-input v-model="unit.nsrsbCode"  ></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
						<!--<el-form-item label="单位类型">
							<el-select v-model="unitKind" filterable placeholder="请选择单位类型" >

								<el-option v-for="item in unitKinds" :key="item.id" :label="item.ename" :value="item.val">
								</el-option>
							</el-select>
						</el-form-item>-->
                        <el-row>
                            <el-col>
                                <el-form-item label=" 备注信息">
                                    <el-input type="textarea" :row="2"  v-model="unit.remark" ></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>


						<!--<el-form-item label="营业执照注册号">
							<el-input v-model="unit.regCode"  placeholder="请输入营业执照注册号"></el-input>
						</el-form-item>
						<el-form-item label="经营许可证编号">
							<el-input v-model="unit.jyxkCode"  placeholder="请输入经营许可证编号"></el-input>
						</el-form-item>
						<el-form-item label="纳税人识别号">
							<el-input v-model="unit.nsrsbCode"  placeholder="请输入纳税人识别号"></el-input>
						</el-form-item>

						<el-form-item label="单位类型">
							<el-select v-model="unitKind" filterable placeholder="请选择单位类型" >

								<el-option v-for="item in unitKinds" :key="item.id" :label="item.ename" :value="item.val">
								</el-option>
							</el-select>
						</el-form-item>



						<el-form-item label="地址">
							<el-input placeholder="请输入地址" v-model="unit.address" ></el-input>
						</el-form-item>
						<el-form-item label="联系人">
						   <el-input placeholder="请输入联系人" v-model="unit.linkman" ></el-input>
					   </el-form-item>
						<el-form-item label="联系方式">
							<el-input placeholder="请输入联系方式" v-model="unit.contactWay" ></el-input>
						</el-form-item>
						<el-form-item label="传真">
							<el-input placeholder="请输入传真" v-model="unit.contactFax" ></el-input>
						</el-form-item>
						<el-form-item label="企业logo">
							<el-input placeholder="请输入企业logo" v-model="unit.logo" ></el-input>
						</el-form-item>
						<el-form-item label="邮政编码">
							<el-input placeholder="请输入邮政编码" v-model="unit.postCode" ></el-input>
						</el-form-item>
						<el-form-item label="电子邮箱">
							<el-input placeholder="请输入电子邮箱" v-model="unit.email" ></el-input>
						</el-form-item>
						<el-form-item label=" 备注信息">
							<el-input placeholder="请输入 备注信息" v-model="unit.remark" ></el-input>
						</el-form-item>
						<el-form-item label=" 是否三证合一">
							<el-input placeholder="请输入 备注信息" v-model="unit.isThreeInOne" ></el-input>
						</el-form-item>-->
					</el-form>
					<div slot="footer" class="dialog-footer">
						<el-button @click.native="editFormVisible = false">取消</el-button>
						<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
					</div>
				</el-dialog>
			<!--</el-col>-->

	</div>
</template>
<script>
    export default {
        data() {
            return {
                filters: {
                    cname: ''

                },
                total: 0,
                page: 1,
                pageSize: 30,
                loading: false,
                units: [
                ],
             //   isSupplyVal:'',
                isThreeInOneVal:'',
                unit: {},
				unitKind:'1',
                unitKinds:[],
                modelTitle: "",
                editFormVisible: false,
                editLoading: false,
                editFormRules: {
                    cname: [
                        { required: true, message: '请输入单位名称', trigger: 'blur' }
                    ],
                    code: [
                        { required: true, message: '请输入单位编号', trigger: 'blur' }
                    ],
                },
               errors: {


                cnameErr:''

			}
            }
        },
        methods: {
            //字典获取单位类型
            getUnitKinds: function () {
                this.loading = true;
                this.axios.post('/platformService/sys/dict/getDictValueByDictEname',{dictName:'JGLX',val:1}).then(res => {

                    this.loading = false;
                    if (res.data.code == "0") {
                        this.unitKinds = res.data.data;
                    }
                    else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.loading = false;
                });
            },

            //获取列表
            getUnitList: function (pIndex) {
                this.page = pIndex;
                var param = { orderBy: "", queryObject: { cname: this.filters.cname,kind:1}, page: this.page, rows: this.pageSize };
                this.loading = true;
                this.axios.post('/platformService/bas/company/getCompanys', param).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
                        this.units = res.data.data.data;
                        this.total = res.data.data.total;
                      //	  this.page = res.data.data.pageNum;
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
                this.getUnitList(this.page);
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.getUnitList(this.page);
            },
            unitEdit(item) {

                 this.errors.cnameErr='';

                if (item) {
                    let unit = Object.assign({}, item);

                    this.unit = unit;
                    this.modelTitle = "修改医疗机构";
                    this.unit.kind= this.unitKind;
                  //  this.isSupplyVal= this.unit.isSupply;
                    this.isThreeInOneVal=this.unit.isThreeInOne;
                } else {
                    this.unit={};
                    this.modelTitle = "添加医疗机构";
                    this.unit.kind=this.unitKind;
                }
                this.editFormVisible = true;
               // this.$refs.editForm.resetFields();
            },
            delUnit(unit) {
                this.$confirm('确认删除这条记录吗?', '提示', {
                }).then(() => {
                    this.loading = true;
                    delete unit.collectorId;
                    delete unit.hosId;
                    delete unit.hosName;
                    delete unit.hcpId;
                    delete unit.provCertificates;
                    this.axios.post("/platformService/bas/company/delCompany", unit).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.loading = false;
                            this.$message({ message: '操作成功！', type: 'success' });
                        } else {
                            this.$message.error( res.data.msg);

                        }
						            this.getUnitList(this.page);
                    }, err => {
                        this.loading = false;
                    });
                }).catch(() => {

                });
            },
            editSubmit: function () {
                var _this = this;
                delete _this.unit.collectorId;
                delete _this.unit.hosId;
                delete _this.unit.hosName;
                delete _this.unit.hcpId;
                delete _this.unit.provCertificates;
                var form =  this.$refs.editForm;
                const h = this.$createElement;
                var arr=new Array();
              //  this.unit.isSupply=this.isSupplyVal;
              _this.unit.isThreeInOne=this.isThreeInOneVal;

                form.validate((valid) => {
                    if (valid) {
                      _this.editLoading = true;
                     //   this.unit.kind= 1;
                        let serviceUrl = _this.unit.id ? "/platformService/bas/company/update" : "/platformService/bas/company/insert";
                        this.axios.post(serviceUrl, this.unit).then(res => {
                            this.editLoading = false;
                            if (res.data.code == "0") {
                                this.$message({ message: '操作成功！', type: 'success' });
                                this.editFormVisible = false;

                                this.getUnitList(this.page);
                            } else if (res.data.code == "-122") {
                                    let err = res.data.validateErrors.fieldErrors[0];

                                        if (err.field == "cname") {
                                            this.errors.cnameErr += err.message;
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
            }
        },
        mounted() {
          this.getUnitList(1);
           // this.getUnitKinds();
         //  this.unitEdit='1';
        }
    };

</script>
<style>
    #hospital .hospitail-tableheight {
        height:  calc(100% - 100px);
    }
</style>
