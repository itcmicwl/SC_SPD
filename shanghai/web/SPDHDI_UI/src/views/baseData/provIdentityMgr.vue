<template>
    <div style="height: 100%;" id="provIdentityMgr">
        <el-row height="auto" :gutter="10" style="height: 100%;">
            <el-col :span="6"  style="height: 100%;">
                <el-input placeholder="请输入配送商名称" style="margin-bottom: 2px" v-model="filters.provName">
                    <el-button slot="append" icon="el-icon-search" @click="test" style="width: 60px" ></el-button>
                </el-input>
                <template>
                    <el-table :data="provList" highlight-current-row border style="width: 100%;"  @current-change="selectProvRow"
                              :showHeader="false"  class="provIdentityMgr-tableheight" ref="provListTable">
                        <el-table-column type="index" label="序号" width="50" align="center">
                        </el-table-column>
                        <el-table-column prop="cname" label="单位名称" min-width="200" header-align="center" sortable>
                        </el-table-column>
                    </el-table>
                    <!--工具条-->
                    <el-col :span="24" class="toolbar">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                                       :current-page="page" :page-sizes="[20, 30, 50, 100]"
                                       :page-size="pageSize" layout="sizes, prev, next"  :total="total" style="float:right;" small>
                        </el-pagination>
                    </el-col>
                </template>
            </el-col>
            <el-col :span="18" height="auto" style="padding-left:5px;height: 100%;max-width: 600px;">
                <el-form :model="provOrgInnerCode" labelWidth="120px" ref="editForm" class="formclass">
                    <div class="provOrgTitle"><span class="provOrgParam">&nbsp;【{{currentProv.cname}}】内部标识设置</span></div>
                    <div class="provDiv">
                        <div class="divTitle"><span class="uploadParam">&nbsp;配送机构</span></div>
                        <div class="rowDiv">
                        <el-row>
                            <el-form-item label="配送商名称：">
                                <el-input v-model="currentProv.cname" readonly value="currentProv.id" ></el-input>
                            </el-form-item>
                        </el-row>
                        <el-row>
                            <el-form-item label="配送商系统标识：">
                                <el-input v-model="provOrgInnerCode.sysFlag" ></el-input>
                            </el-form-item>
                        </el-row>
                        <el-row>
                            <el-form-item label="配送商ERP编码：">
                                <el-input v-model="provOrgInnerCode.provErpCode" ></el-input>
                            </el-form-item>
                        </el-row>
                        </div>
                    </div>

                    <div class="putDiv">
                        <div class="divTitle"><span class="uploadParam">&nbsp;分配KEY</span></div>
                        <div class="rowDiv">
                            <el-row>
                                <el-form-item label="appKey：">
                                    <el-input v-model="provOrgInnerCode.putKey" ></el-input>
                                </el-form-item>
                            </el-row>
                            <el-row>
                                <el-form-item label="password：">
                                    <el-input v-model="provOrgInnerCode.putPass" ></el-input>
                                </el-form-item>
                            </el-row>
                            <el-row >
                                <div style="margin-bottom: 7px;margin-top:10px;text-align: right">
                                    <el-button   style="width: 100px" @click="clearAllot">清空</el-button>
                                    <el-button type="info"  style="width: 100px" @click="allot">分配</el-button>
                                </div>
                            </el-row>
                        </div>
                    </div>
                    <div class="uploadDiv">
                        <div class="divTitle"><span class="uploadParam">&nbsp;上传参数</span></div>
                        <div class="rowDiv">
                            <el-row>
                                <el-form-item label="url：">
                                    <el-input v-model="provOrgInnerCode.uploadUrl" ></el-input>
                                </el-form-item>
                            </el-row>
                            <el-row>
                                <el-form-item label="appKey：">
                                    <el-input v-model="provOrgInnerCode.uploadKey" ></el-input>
                                </el-form-item>
                            </el-row>
                            <el-row>
                                <el-form-item label="password：">
                                    <el-input v-model="provOrgInnerCode.uploadPass" ></el-input>
                                </el-form-item>
                            </el-row>
                            <el-row>
                                <div style="text-align: right;margin-top: 5px;">
                                    <el-button type="success" size="small" style="width: 100px;margin-right: 20px;" @click="submit">提交</el-button>
                                </div>
                            </el-row>
                        </div>
                    </div>


                </el-form>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    export default {
        data(){
            return {
                filters: {
                    provName: ''
                },
                total: 0,
                page: 1,
                pageSize: 20,
                provList:[],

                provOrgInnerCode:{
                    provId:'',
                    sysFlag:'',
                    provErpCode:'',
                    putKey:'',
                    putPass:'',
                    uploadKey:'',
                    uploadPass:'',
                    uploadUrl:''
                },
                provId:'',
                provName:'',
                currentProv:{}
            }
        },
        methods:{
            submit:function () {
                let o = this;
                o.provOrgInnerCode.provId = o.currentProv.id;
                var serviceUrl = o.provOrgInnerCode.id ? 'updateProvOrgInnerCode':'insertProvOrgInnerCode';
                o.axios.post('/spdHDIService/bas/provOrgInnerCode/'+serviceUrl, o.provOrgInnerCode).then(res => {
                    if (res.data.code == "0") {
                        this.$message({message: '操作成功！', type: 'success'});
                        this.queryProvOrgInnerCode();
                    } else{
                        o.$message.error( res.data.msg);
                    }
                }, err => {});
            },

            clearAllot:function () {
                this.provOrgInnerCode.putKey = null;
                this.provOrgInnerCode.putPass = null;
            },

            allot:function () {
                let o = this;
                if(o.provOrgInnerCode.putKey){
                    var message = '配送商【' + o.currentProv.cname + '】已分配key和password，是否重新分配？'
                    this.$confirm(message, '提示', {}).then(() => {
                        o.getAllot(o);
                    }).catch(() => {
                    });
                }else{
                    o.getAllot(o);
                }
            },

            getAllot:function (o) {
                o.axios.post('/spdHDIService/bas/provOrgInnerCode/getUUIDandPassword', null).then(res => {
                    if (res.data.code == "0") {
                        o.provOrgInnerCode.putKey = res.data.data.uuid;
                        o.provOrgInnerCode.putPass = res.data.data.password;
                    } else {
                        o.$message.error(res.data.msg);
                    }
                }, err => {
                });
            },

            test:function () {
                this.getProvList(1);
            },
            getProvList:function (page) {
                let o = this;
                o.page = page;
                var param = { orderBy: "", queryObject: { cname: o.filters.provName,kind:2}, page: o.page, rows: o.pageSize };
                o.axios.post('/platformService/bas/company/getCompanys', param).then(res => {
                    if (res.data.code == "0") {
                        o.provList = res.data.data.data;
                        if (o.provList.length>0){
                            o.$nextTick(function () {
                                o.setCurrent(o.provList[0]);
                            });
                        }
                        o.total = res.data.data.total;
                    } else{
                        o.$message.error( res.data.msg);
                    }
                }, err => {});
            },
            setCurrent(row) {
                this.$refs.provListTable.setCurrentRow(row);
            },
            handleCurrentChange(val) {
                this.page = val;
                this.getProvList(this.page);
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.getProvList(this.page);
            },
            selectProvRow (row, event, column) {
                if (this.currentProv.id === row.id) {
                    return;
                }
                this.currentProv = row;
                this.queryProvOrgInnerCode();
            },
            queryProvOrgInnerCode:function () {
                let o = this;
                o.axios.post('/spdHDIService/bas/provOrgInnerCode/queryProvOrgInnerCode', o.currentProv.id).then(res => {
                    if (res.data.code == "0") {
                        o.provOrgInnerCode = res.data.data;
                    } else{
                        o.$message.error( res.data.msg);
                    }
                }, err => {});
            },
        },
        mounted() {
            this.provId = this.user.corpId;
            this.provName = this.user.corpName;
            this.getProvList(1);
        }
    }
</script>

<style scoped>

    .uploadParam{
        margin-left: 2px;
        font-weight: bold;
    }
    .provOrgParam{
        margin-left: 2px;
        font-weight: bold;
    }
    .divTitle{
        background-color: #eef1f6;
        border: 1px solid #e4e8f1;
        height: 25px;
        line-height: 25px;
    }
    .provOrgTitle{
        margin: -10px -20px 20px -10px;
        background-color: #e4e8f1;
        border-bottom: 1px solid lightgray;
        line-height: 32px;
        height: 33px;
    }
    .putDiv{
        margin-left: 25px;
        margin-bottom: 10px;
    }
    .uploadDiv{
        margin-left: 25px;
    }
    .provDiv{
        margin-left: 25px;
        margin-bottom: 10px;
    }
    .rowDiv{
        border-bottom: 1px solid  #dfe6ec;
        border-left: 1px solid  #dfe6ec;
        border-right: 1px solid  #dfe6ec;
        /*border: 1px solid lightgray;*/
        padding-right: 15px;
        padding-top: 5px;
        padding-bottom: 5px;
    }
    #provIdentityMgr .el-form-item{
        margin-bottom: 0px;
    }
    .formclass{
        padding-left: 10px;
        padding-top: 10px;
        padding-right: 20px;
        padding-bottom: 10px;
        /*height: 95.6%;*/
        height: calc(100% - 75px);
        border: 1px solid #dfe6ec;
    }
    .provIdentityMgr-tableheight {
        height: calc(100% - 100px);
    }
</style>
