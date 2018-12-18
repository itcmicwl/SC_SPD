<template>
 <div id="erp-code-mgr" style="height: 100%;">
	 <el-row gutter="10" style="height: 100%;">
			 <el-col :span="6" style="height: 100%;">
               <div style="padding-bottom: 5px">
                    <el-input v-model="filters.orgName" placeholder="请输入机构名称"   icon="search" :on-icon-click="searchOrgs"   ></el-input>
                </div>
					<el-tree :data="treeValue" node-key="id"
							 :default-expanded-keys="expadoIds"
							 :props="defaultProps"
							 :highlight-current="true"
							 @node-click="handleNodeClick"  >
					</el-tree>
			</el-col>
			<el-col :span="18" >
              <el-row  style="padding-left:50px;" >
                     <div style="padding-bottom: 7px;">
						<el-button type="primary"  @click="updateOrg">保存</el-button>
                     </div>
               </el-row>
                <el-row  style="padding-left:5px;" >
                    <el-col   :span="18">
                        <el-form  :model="formValue" labelWidth="120px" :rules="editFormRules" ref="editForm" class="el-form-item-nomsg">
                           <!--
                             <el-form-item label="供应商Id" v-show="false">
                                <el-input v-model="formValue.provId" :disabled="true" ></el-input>
                            </el-form-item>
                            <el-form-item label="医院ID"  v-show="false">
                                <el-input v-model="formValue.hosId" :disabled="true" ></el-input>
                            </el-form-item>
                            <el-form-item label="业务员ID"  v-show="false">
                                <el-input v-model="formValue.salemanId" :disabled="true" ></el-input>
                            </el-form-item>
                            -->
                            <el-row>
                               <el-col  >
                                    <el-form-item label="医院名称：" v-show='false' prop="selectedHosName">
                                        <el-input v-model="selectedHosName" :disabled="true" ></el-input>
                                    </el-form-item>
                                </el-col>

                            </el-row>
                            <el-row>
                                <el-col >
                                    <el-form-item label="医院|部门名称：" >
                                        <el-input v-model="selectedNode.label" :disabled="true"  ></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                             <el-row>
                                <el-col >
                                    <el-form-item label="ERP编码：" prop="erpCode" :error="errors.erpCodeErr">
                                        <el-input v-model="formValue.erpCode" ></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                             <el-row>
                                <el-col >

                                    <el-form-item label="业务员ID：" prop="salemanId" :error="errors.salemanIdErr">
                                          <div @click='selectUser'>
                                                <el-input v-model="formValue.salemanId"
                                                icon="search"  :disabled="true" placeholder="点击选择业务员" ></el-input>
                                          </div>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                              <el-row>
                                <el-col >
                                    <el-form-item label="业务员名称：" prop="salemanName" >
                                        <el-input v-model="salemanName"  :disabled="true" ></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col >
                                    <el-form-item label="业务员电话：" prop="salemanTel" >
                                        <el-input v-model="salemanTel"  :disabled="true" ></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>


                        </el-form>
                      </el-col>
                </el-row>
			    <el-row>
                     <div style="padding-top: 7px;text-align:center;">
						<el-button type="primary" size="small" @click="updateOrg">保存</el-button>
                     </div>
               </el-row>

            </el-col>

		</el-row>
            <el-dialog title="请选择业务员" close="onClose" :visible.sync="selectUserVisible" :close-on-click-modal="false" >
                        <el-form :inline="true" label-width="80px">
                            <el-form-item label="业务员名称">
                                <el-input v-model="filters.userEname" placeholder="输入业务员名称" ></el-input>
                            </el-form-item>
                            <el-button type="primary" v-on:click="getUserList(1)" >查询</el-button>

                        </el-form>
                         <template>
                                <el-table
                                    ref="multipleTable" :data="userList"  border tooltip-effect="dark" row-key='id'
                                    style="width: 100%" @selection-change="handleSelectionChange">
                                    <el-table-column  type="selection" :reserve-selection="true" width="55"> </el-table-column>
                                    <el-table-column  prop="ename" label="业务员名称" width="200"></el-table-column>
                                    <el-table-column prop="corpName" label="所属单位" show-overflow-tooltip> </el-table-column>
                                    <el-table-column prop="orgName" label="所属部门" show-overflow-tooltip> </el-table-column>
                                </el-table>
                                  <el-pagination @size-change="handleSizeChange"
                                                @current-change="handleCurrentChange"
                                                :current-page="page" :page-sizes="[10, 20, 30, 50]" :page-size="pageSize"
                                                layout="total, sizes, prev, pager, next"
                                                :total="total" style="float:right;" small>
			                      </el-pagination>
                                <div style="padding-top: 20px">
                                    <el-button @click="toggleSelection()">取消选择</el-button>
                                    <el-button @click="selectUserSubmit()">确定</el-button>
                                </div>
                         </template>
                 </el-dialog>
</div>

</template>

<script>
    export default {
        data(){
            return {
                ph_code:null,
                ph_dept_code:null,
                selected_nodeType:null,
                updateUrl:'',
                operProvId:'',
                editFormRules: {
					erpCode: [{ required: true, message: '请输入ERP编码', trigger: 'blur' }],
                    salemanId: [{ required: true, message: '请选择业务员', trigger: 'blur' }]
                },
                loading:false,
                treeValue:[],
                defaultProps: {
                    children: 'children',
                    label: 'label',
					id:'id',
                    pid:'pid',
                    code:'code',
                    nodeType:'nodeType'
                },
                selectedNode:{},
                selectedHosName:'',
                salemanName:'',
                 salemanId:'',
                salemanTel:'',
                selectedHosId:'',
                selectedHosDeptId:'',
                erpCode:'',
                expadoIds:['1'],
                formValue:{
                    erpCode:'',
                    salemanId:'',
                    hosId:''

                },
                filters:{
                    orgName:'',
                    userEname:'',
                },
                 errors: {
				          erpCodeErr: '',
				          salemanIdErr: ''
			    },
                selectUserVisible:false,
                userList:[],
                total: 0,
                page: 1,
                pageSize: 10



            }
        },

        methods:{

            selectUser:function(){


                this.selectUserVisible=true;
                this.getUserList(1);
            },
             handleSelectionChange(val) {
                this.multipleSelection = val;
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
             handleCurrentChange(val) {
                this.page = val;
                this.getUserList(this.page);
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.getUserList(this.page);
            },
            getUserList:function(pIndex){
                this.page = pIndex;
                var param = { orderBy: "", queryObject: { ename: this.filters.userEname }, page: this.page, rows: 10 };
                this.loading = true;
                this.axios.post(`/platformService/sys/user/userlistByUnit`, param).then(res => {
                    this.loading = false;
                    this.userList = res.data.data.data;
                    this.total = res.data.data.total;
                   // this.page = res.data.data.pageNum;
                }, err => {
                    this.loading = false;
                });
            },
            selectUserSubmit:function(){
                if(this.multipleSelection.length!=1){

                    this.$message.error( "只能选择一个业务员！");
                    return;
                }


                this.formValue.salemanId=  this.multipleSelection[0].userId;
                this.salemanName=   this.multipleSelection[0].ename;
                this.salemanTel=   this.multipleSelection[0].tel;
				 this.$refs.multipleTable.clearSelection();
                this.selectUserVisible= false;


            },
            getOrgsByUnit:function (unitId) {
                var param = { orderBy: "", queryObject: {provId:this.operProvId,status:'2' }, page: 0, rows: 0 };
                this.loading=true;
                this.axios.post(`/spdHDIService/myInfos/provHosInfoCode/printTree`, param).then(res=>{

					this.treeValue=res.data.data.children;

					this.loading=false;
                });

            },

            handleNodeClick(data) {
                 this.selectedNode={};
                 this.ph_code=null;
                 this.ph_dept_code=null;
                 this.selectedHosDeptId='';
                 this.selected_nodeType=null;
                 this.errors.erpCodeErr = '';
                 this.errors.salemanIdErr = '';
                 this.erpCode="";
			     this.selectedNode = data;
                 this.formValue.erpCode='';
                 this.formValue.salemanId='';
                 this.formValue.hosId='';

                 this.salemanName="";
                 this.salemanTel="";
                 if(data.nodeType=='hos'){
                    //  this.selectedHosName="";
                    //  this.selectedHosId="";
                      this.selected_nodeType='hos';
                      this.selectedHosName =data.label;
                      this.selectedHosId=data.id;
                      this.formValue.hosId= data.id;
                      this.getThePHCodeVo();

                 }
                 else{  //dept
                      this.selected_nodeType='dept';
                      this.selectedHosDeptId=data.id;
                      this.getThePHDeptCodeVo();


                      this.formValue.hosId= this.selectedHosId;


                 }



            },

            getThePHCodeVo:function(){
                  let param = { orderBy: "", queryObject: {provId:this.operProvId,hosId: this.selectedHosId }, page: 0, rows: 0 };
                    this.axios.post(`/spdHDIService/myInfos/provHosInfoCode/getThePHCodeVo`, param).then(res=>{

				                   if(res.data.data ==null){

                                        }
                                    else{
                                        this.formValue =res.data.data;
                                        this.ph_code = res.data.data.id;
                                        this.salemanName =this.formValue.userName;
                                        this.salemanTel = this.formValue.tel;
                                        this.erpCode= this.formValue.erpCode;
                                        this.formValue.id=  this.ph_code;
                                       }



                              });
            },
            getThePHDeptCodeVo:function(){
                  let param = { orderBy: "", queryObject: {provId:this.operProvId,hosDeptId:this.selectedHosDeptId }, page: 0, rows: 0 };
                    this.axios.post(`/spdHDIService/myInfos/provHosDeptInfoCode/getThePHDeptCodeVo`, param).then(res=>{
                                 if(res.data.data ==null){

                                        }
                                    else{
                                        this.ph_dept_code = res.data.data.id;
                                        this.formValue =res.data.data;
                                        this.salemanName =this.formValue.userName;
                                        this.salemanTel = this.formValue.tel;
                                        this.erpCode= this.formValue.erpCode;
                                        this.formValue.id=  this.ph_dept_code;
                                       }



                              });
            },
            updateOrg:function () {

                if(this.selected_nodeType==null){
                    this.$message.error('必须先从左侧选择医院或部门！');
                    return;
                }
                if(this.selected_nodeType=='hos'){
                        // alert("this.ph_code:"+this.ph_code);
                      this.updateUrl =this.ph_code ? '/spdHDIService/myInfos/provHosInfoCode/update':'/spdHDIService/myInfos/provHosInfoCode/add';

                }
                else{
                        // alert("this.ph_dept_code:"+this.ph_dept_code);
                      this.updateUrl =this.ph_dept_code ? '/spdHDIService/myInfos/provHosDeptInfoCode/update/'+this.selectedHosDeptId:'/spdHDIService/myInfos/provHosDeptInfoCode/add/'+this.selectedHosDeptId;

                }
                  this.loading=true;
                this.errors.erpCodeErr = '';
                 this.errors.salemanIdErr = '';
                var _this = this;
                var form =  this.$refs.editForm;
                const h = this.$createElement;
                var arr=new Array();
                form.validate((valid) => {
                if (valid) {
                    this.axios.post(this.updateUrl, this.formValue).then(res=>{

						if(res.data.code=="0"){
                         //查询关联数据
                         if(this.selected_nodeType=='dept'){
                                //alert("重载dept");
                                 this.getThePHDeptCodeVo();
                         }
                          else{
                                 // alert("重载Hos")
                              this.getThePHCodeVo();
                          }

                            this.$message({ message: '操作成功！',  type: 'success'  });

                             this.loading=false;


                        }
                         else if (res.data.code == "-122") {
                                    let err = res.data.validateErrors.fieldErrors[0];
                                        if (err.field == "erpCode") {
                                        this.errors.erpCodeErr += err.message;
                                        }
                                        if (err.field == "salemanId") {
                                            this.errors.salemanIdErr += err.message;
                                        }

                                 }
                        else {
                            this.$message.error('操作失败！');
                        }
                    }, err => {



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

            searchOrgs:function () {

                var p = this.filters.orgName;
                let param = {cname:p};
                this.loading=true;
                this.axios.post(`/platformService/sys/org/searchOrgs`, param).then(res=>{
					var ids=[];
                    for(var i = 0;i < res.data.data.length; i++) {
                        ids[i] =res.data.data[i].id;
                    }
                    this.loading=false;
                    this.expadoIds= ids;
                  // this.expadoIds=['org-21']
                });
            },
            handleChange(value) {
            }
        },


        mounted() {

          //  this.getOrg(); //展开树
		this.getOrgsByUnit();
	    this.operProvId = this.user.provid
        }
    }
</script>

<style>
	#erp-code-mgr .el-tree-node__label {
		font-size: 12px;
	}
	#erp-code-mgr .el-table {
		font-size: 12px;
	}
	#erp-code-mgr .el-tree {
		min-height: calc(100% - 85px);
	}
    #erp-code-mgr .el-form-item__content{
        line-height: 35px;
    }
</style>
