<template>
    <div id="myHos-id" style="height: 100%;">
    <!--工具条-->
    <el-col :span="24" style="height: 100%">
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="filters">
          <el-form-item label="医院名称：">
            <el-input v-model="filters.hosName" placeholder="医院名称" ></el-input>
          </el-form-item>
          <el-form-item label="联系人：">
            <el-input v-model="filters.hosLxr" placeholder="联系人" ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" v-on:click="getProvHosInfoVoList(1)" >查询</el-button>
            <el-button type="primary" @click="provHosInfoEdit(null)" >新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <!--列表-->
      <template>
        <el-table :data="provHosInfos" highlight-current-row v-loading="loading" style="width: 100%;" border fit  class="myHos-tableheight">
          <el-table-column type="index" label="序号" align="center" width="62"></el-table-column>
          <el-table-column prop="hosName" label="医院名称" width="250" header-align="center"></el-table-column>
          <el-table-column prop="hosLxr" label="联系人"></el-table-column>
          <el-table-column prop="hosLxrTelphonenum" label="联系电话"></el-table-column>
          <el-table-column prop="remark" label="备注"></el-table-column>
          <el-table-column prop="status" label="状态" width="70" v-bind:style="styleObject"></el-table-column>
          <!--<el-table-column prop="lastUpdateDatetime" label="最后更新时间"  sortable></el-table-column>-->
          <el-table-column label="操作" align="center" width="220">
            <template slot-scope="scope">
              <el-button-group style="width: 100%;">
              <el-button size="mini" @click="provHosInfoEdit(scope.row)" type="info" icon="edit">编辑</el-button>
              <span v-if="scope.row.status=='已解除'">
                        <el-button size="mini" @click="relinkSubmit(scope.row)" type="danger" icon="delete" >重建关系</el-button>
                      </span>
              <span v-else>
                        <el-button size="mini" @click="deleteSubmit(scope.row)"  type="warning" icon="warning" >解除关系</el-button>
                      </span>
              <el-button size="mini" :disabled="availablePush(scope.row)"
                         @click="provRegistInfoPush(scope.row)" type="success" icon="edit" >推送证照</el-button>
              </el-button-group>
            </template>
          </el-table-column>
        </el-table>
        <!--工具条-->
        <el-col :span="24" class="toolbar">
          <el-pagination small @size-change="myHosHandleSizeChange" style="padding-right: 20px;text-align: right;"
                         @current-change="myHosHandleCurrentChange" :current-page.sync="page"
                         layout="total, sizes, prev, pager, next" :page-sizes="[10, 20, 30, 50]" :page-size="pageSize" :total="total">
          </el-pagination>
        </el-col>
      </template>

    </el-col>
      <!--编辑界面-->
      <el-dialog :title="modelTitle"   close="onClose"  :visible.sync="editFormVisible" :close-on-click-modal="false" size="tiny">
        <el-form :model="provHosInfoVo" label-width="120px" ref="editForm" style="margin-top: 3%" class="el-form-item-nomsg">
          <el-row>
            <el-form-item label="医院名称：" prop="hosName" :error="nameErr">
              <el-select v-model="hosId" filterable placeholder="请选择" style="width: 70%;" :disabled="selhos" :clearable="true"
                         @visible-change="sltChange" >
                <el-option
                        v-for="item in hosInfos"
                        :key="item.id"
                        :label="item.cname"
                        :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-row>
          <el-row>
            <el-col>
              <el-form-item label="联系人：">
                <el-input v-model="linkman"  style="width: 70%;" placeholder="请输入联系人"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-form-item label="联系电话：">
                <el-input v-model="tel"  style="width: 70%;" placeholder="请输入联系电话"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-form-item label="备注：">
                <el-input type="textarea" :row="3" style="width: 70%;" placeholder="请输入备注信息" v-model="provHosInfoVo.remark" ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer myHos-button-align">
          <el-button @click.native="editFormVisible = false" >取消</el-button>
          <el-button type="primary" @click.native="editSubmit" :loading="editLoading" >提交</el-button>
        </div>
      </el-dialog>
      <el-dialog :title="regCertGoodsDetailDailogTitle"  close="onClose"  :visible.sync="regCertGoodsDetailDailog" :close-on-click-modal="false" >
        <el-table highlight-current-row  class="tableheight" ref="certsTable" :data="pushedProductsData" style="width: 100%"
                  v-loading="productsDetailLoading" border>
          <el-table-column prop="goodsName"  label="产品名称" header-align="center">
          </el-table-column>
          <el-table-column prop="goodsGg" label="规格型号" header-align="center"  show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="unit" header-align="center"   label="单位" show-overflow-tooltip sortable>
          </el-table-column>
          <el-table-column prop="hitCode" header-align="center"  label="平台编码" show-overflow-tooltip sortable>
          </el-table-column>
          <el-table-column prop="hitPrice" header-align="center"  label="中标价格">
          </el-table-column>
        </el-table>
        <el-pagination small @size-change="" style="padding-right: 20px;text-align: right;background-color: rgb(238, 241, 246);"
                       @current-change="" :current-page.sync="pPageNum"
                       layout="total, sizes, prev, pager, next" :page-sizes="[10, 15, 20, 30]" :page-size="pPageSize" :total="pTotal">
        </el-pagination>
        <div slot="footer" class="dialog-footer">
          <el-button @click.native="regCertGoodsDetailDailog = false">取消</el-button>
        </div>
      </el-dialog>
      <!--证照推送界面-->
      <el-dialog :title="modelTitle1"  close="onClose" style="min-height: 50%" :visible.sync="editFormVisible1" :close-on-click-modal="false" width="75%">
        <template>
          <el-tabs v-model="activeName"  @tab-click="handleClick" >
            <el-tab-pane label="推送列表" name="first">
              <el-col :span="24" style="height: 100%;padding: 0px;padding-left: 5px">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                  <el-form :inline="true" :model="pushCertSelectForm" ref="pushCertSelectForm">
                    <el-form-item label="注册证号：" prop="regCertCode">
                      <el-input v-model="pushCertSelectForm.regCertCode" placeholder="注册证号" ></el-input>
                    </el-form-item>
                    <el-form-item label="注册证产品名称：" prop="regCertName">
                      <el-input v-model="pushCertSelectForm.regCertName" placeholder="注册证产品名称" ></el-input>
                    </el-form-item>
                    <el-form-item label="推送状态：" prop="pushState">
                      <el-select v-model="pushCertSelectForm.pushState"  clearable placeholder="请选择">
                        <el-option
                                v-for="item in pushStateDict"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item>
                      <el-button type="primary" v-on:click="getPushedRegCertsList(1)" >查询</el-button>
                      <el-button type="primary" @click="resetForm('pushCertSelectForm')" >重置</el-button>
                    </el-form-item>
                  </el-form>
                </el-col>
                <el-table :data="pushedRegCertsList" highlight-current-row v-loading="loading" style="width: 100%;" border fit class="myHos-pushList">
                  <el-table-column type="index" label="序号" align="center" width="62"></el-table-column>
                  <el-table-column prop="certificateCode" label="注册证号" header-align="center" sortable></el-table-column>
                  <el-table-column prop="productName"  align="center" label="注册证产品名称" header-align="center" ></el-table-column>
                  <el-table-column prop="pushStatus" label="推送状态" align="center" header-align="center" :formatter="formatPushState" ></el-table-column>
                  <el-table-column prop="fillDate" label="推送日期" align="center" header-align="center"  sortable></el-table-column>
                  <el-table-column prop="auditDate" label="审核日期" align="center" header-align="center"  sortable></el-table-column>
                  <el-table-column label="操作" align="center" header-align="center" >
                    <template slot-scope="scope">
                      <el-button size="mini" @click="pushedRegCertGoodsDetail(scope.row)" type="primary" icon="search" >产品明细</el-button>
                    </template>
                  </el-table-column>
                </el-table>
                <!--工具条-->
                <el-col :span="24" class="toolbar">
                  <el-pagination small @size-change="pclHandleSizeChange" style="padding-right: 20px;text-align: right;"
                                 @current-change="pclHandleCurrentChange" :current-page.sync="pclPage"
                                 layout="total, sizes, prev, pager, next" :page-sizes="[5, 10, 15, 20]" :page-size="pclPageSize" :total="pclTotal">
                  </el-pagination>
                </el-col>
              </el-col>
            </el-tab-pane>
            <el-tab-pane label="新增推送" name="second" >
              <div id="certslist" style="height: 100%">
                <el-col :span="6" style="height: 100%">
                  <div style="margin-bottom: 8px;margin-top:1px;">
                    <el-input placeholder="请输入注册证号" icon="search"  v-model="inputCertsSearch" @keyup.enter.native="handleCertSearch"
                              :on-icon-click="handleCertSearch">
                    </el-input>
                  </div>
                  <el-table :show-header="false" height="400" max-height="600px"
                            ref="singleTable" @current-change="handleCurrentChange"
                            :data="regCertsData" style="width: 100%" highlight-current-row
                            @row-click="selectProducts" v-loading="regCertLoading">
                    <el-table-column prop="productName" label="注册证列表" show-overflow-tooltip>
                    </el-table-column>
                  </el-table>
                  <el-pagination small @size-change="" @current-change="" :current-page.sync="rPageNum"
                                 :page-sizes="[10, 15, 20, 30]" :page-size="rPageSize" layout="sizes, prev, next" :total="rTotal">
                  </el-pagination>
                </el-col>
                <el-col :span="18"  style="height: 100%">
                  <el-table highlight-current-row @selection-change="handleSelectionChange" :data="productsData"
                            v-loading="productsLoading" border fit class="myHos-newPushList">
                    <el-table-column type="selection" width="50" align="center" header-align="center">
                    </el-table-column>
                    <el-table-column prop="goodsName" minWidth="200" label="产品名称" header-align="center">
                    </el-table-column>
                    <el-table-column prop="goodsGg"  label="规格型号" align="center"  show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column prop="unit"  align="center"   label="单位" show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column prop="hitCode" align="center"  label="平台编码" show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column prop="hitPrice"  align="center"  label="中标价格">
                    </el-table-column>
                  </el-table>
                  <el-pagination small @size-change="nplSizeChange" style="text-align: right;"
                                 @current-change="nplCurrentChange" :current-page.sync="nplPage"
                                 layout="total, sizes, prev, pager, next" :page-sizes="[5, 10, 15, 20]" :page-size="nplPageSize" :total="nplTotal">
                  </el-pagination>
                  <el-button style="margin-left: 35%;margin-top: 10px;" :loading="pushLoading" @click="pushRegCertsGoods()" type="primary">推送</el-button>
                </el-col>
              </div>
              <div slot="footer" class="dialog-footer">
                <el-button @click.native="editFormVisible1 = false">取消</el-button>
                <el-button type="primary" @click.native="editSubmit" :loading="editLoading">推送</el-button>
              </div>
            </el-tab-pane>
          </el-tabs>
        </template>
      </el-dialog>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                currentRow:{},
                pushCertSelectForm:{
                    regCertCode:'',
                    regCertName:'',
                    pushState:''
                },
                pushStateDict: [{
                    value: '10',
                    label: '医院待审'
                }, {
                    value: '20',
                    label: '已驳回'
                }, {
                    value: '30',
                    label: '已通过'
                }],
                pushState: '',
                styleObject: {
                    fontcolor: 'red',
                    fontSize: '13px'
                },
                filters: {
                    hosName: '',
                    hosLxr: ''
                },
                selhos:false,
                hosId: '',
                myStatus:'0',
                /**** 医院列表分页参数 ****/
                  total: 0,
                  page: 1,
                  pageSize:20,
                /**********************/
                /**** 已推证件列表分页参数 ****/
                  pclTotal: 0,
                  pclPage: 1,
                  pclPageSize:10,
                /**********************/
                /**** 新增推送产品列表分页参数 ****/
                  nplTotal: 0,
                  nplPage: 1,
                  nplPageSize:5,
                /**********************/
                rTotal: 0,
                pTotal: 0,
                rPageNum:1,
                rPageSize: 20,
                pPageNum: 1,
                pPageSize: 20,
                tel:'',
                linkman:'',
                loading: false,
                selectMfrsId: '',
                inputCertsSearch: "",
                currentMfrs: {},
                provHosInfos: [],
                hosInfos:[],
                regCertsData: [],
                productsData: [],
                multipleSelection: [],
                pushedRegCertsList: [],
                pushedProductsData: [],
                provHosInfoVo: {
                    isThreeInOne: '1',
                    flag: '1',
                    kind: '1'
                },
                activeName: 'first',
                modelTitle: "",
                modelTitle1: "",
                regCertGoodsDetailDailogTitle: "",
                editFormVisible: false,
                editFormVisible1: false,
                regCertGoodsDetailDailog: false,
                editLoading: false,
                productsLoading:false,
                productsDetailLoading:false,
                regCertLoading: false,
                pushLoading: false,
                baseURL: '/spdHDIService/myInfo',
                nameErr: ''
            }
        },
        methods: {
            // 获取我的医院列表
            getProvHosInfoVoList: function (pIndex) {
                this.page = pIndex;
                var param = {
                    orderBy: "",
                    queryObject: {
                        hosName: this.filters.hosName,
                        hosLxr: this.filters.hosLxr
                    },
                    page: this.page,
                    rows: this.pageSize
                };
                this.loading = true;
                this.axios.post('/spdHDIService/myInfo/provHosInfo/provHosInfoVoList', param).then(res => {
                    this.loading = false;

                    this.provHosInfos = res.data.data.data;
                    this.total = res.data.data.total;
                  //  this.page = res.data.data.pageNum;
                }, err => {
                    this.loading = false;
                });
            },
            // 获取医院下拉列表
            getHosInfoVoList: function () {
                var param = { orderBy: "", queryObject: {}};
                this.loading = true;
                this.axios.post(`/spdHDIService/myInfo/provHosInfo/getHosInfoVoList`, param).then(res => {
                    this.loading = false;

                    this.hosInfos = res.data.data.data;
                }, err => {
                    this.loading = false;
                });
            },
            // 获取注册证列表
            getRegCertsList:function () {
                var param = {
                    orderBy: "",
                    queryObject: {},
                    page: this.rPageNum,
                    rows: 20
                };
                this.regCertLoading = true;
                this.axios.post(this.baseURL + '/provRegistrationInfo/listByPage', param).then(res => {
                    this.regCertLoading = false;

                    this.regCertsData = res.data.data.data;
                    this.rTotal = res.data.data.total;
                    // this.rPageNum = res.data.data.pageNum;
                    this.$nextTick(function () {
                        this.setCurrent(this.regCertsData[0]);
                    });
                },err => {
                    this.regCertLoading = false;
                });
            },
            // 获取注册证产品列表
            getProducts:function (page) {
                this.nplPage = page;
                var param = {
                    orderBy: "",
                    queryObject: {
                        provId:this.currentMfrs.provId,
                        certificateId:this.currentMfrs.id
                    },
                    page: this.nplPage,
                    rows: this.nplPageSize
                };
                this.productsLoading = true;
                this.axios.post(this.baseURL + '/provRegistrationInfo/regCertsListByPage', param).then(res => {
                    this.productsLoading = false;

                    this.productsData = res.data.data.data;
                    this.nplTotal = res.data.data.total;
                    // this.nplPage = res.data.data.pageNum;
                },err => {
                    this.productsLoading = false;
                });
            },
            setCurrent(row) {
                this.currentRow = row;
                this.$refs.singleTable.setCurrentRow(row);
            },
            handleClick(tab, event) {
                if (tab.name === 'first'){
                    this.getPushedRegCertsList(1)
                }
            },
            handleCurrentChange(){
                this.selectProducts(this.currentRow);
            },
            // 注册证搜索框
            handleCertSearch () {

                var param = {
                    orderBy: "",
                    queryObject: {productName : this.inputCertsSearch},
                    page: this.rPageNum,
                    rows: this.rPageSize
                };
                this.regCertLoading = true;
                this.axios.post(this.baseURL + '/provRegistrationInfo/listByPage', param).then(res => {
                    this.regCertLoading = false;

                    this.regCertsData = res.data.data.data;
                    this.rTotal = res.data.data.total;
                    // this.rPageNum = res.data.data.pageNum;
                },err => {
                    this.regCertLoading = false;
                    this.$message.error('连接数据库失败，请联系管理员。')
                });
            },
            selectProducts (row, event, column) {

                if (this.currentMfrs.id === row.id) {
                    return
                }
                this.selectMfrsId = row.id;
                this.currentMfrs = Object.assign({}, row);
                this.getProducts(1);
            },
            // 推送注册证
            pushRegCertsGoods () {
                if(this.multipleSelection.length<=0){
                    this.$message.info("至少选择一项产品！");
                    return;
                }
                var param = this.multipleSelection;
                param[0].hosId = this.hosId;
                this.pushLoading = true;
                this.axios.post(this.baseURL + '/provRegistInfoPush/multipleAdd', param).then(res => {
                    this.pushLoading = false;

                    if (res.data.code == "0") {

                        this.$message({message: '操作成功！', type: 'success'});
                        this.getProducts();
                    } else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.pushLoading = false;
                });
            },
            getpushedList(){
                this.getPushedRegCertsList(1);
            },
            // 获取已推注册证列表
            getPushedRegCertsList: function (pIndex) {
                this.pclPage = pIndex;

                var param = {
                    orderBy: "",
                    queryObject: {
                        hosId : this.hosId,
                        certificateCode : this.pushCertSelectForm.regCertCode,
                        productName : this.pushCertSelectForm.regCertName,
                        pushStatus : this.pushCertSelectForm.pushState,
                    },
                    page: this.pclPage,
                    rows: this.pclPageSize
                };
                this.axios.post(this.baseURL + '/provRegistInfoPush/listByPage', param).then(res => {

                    this.pushedRegCertsList = res.data.data.data;
                    this.pclTotal = res.data.data.total;
                    // this.pclPage= res.data.data.pageNum;
                }, err => {
                });
            },
            // 获取已推注册证产品列表
            getPushedRegCertsGoodsList: function (d,pIndex) {
                this.page = pIndex;

                var param = {
                    orderBy: "",
                    queryObject: {
                        hosId : this.hosId,
                        certificateId : d.provRegistId,
                        id:d.id
                    },
                    page: this.page,
                    rows: 20
                };
                this.loading = true;
                this.axios.post(this.baseURL + '/provRegistInfoPush/pushedRegCertsListByPage', param).then(res => {
                    this.loading = false;

                    this.pushedProductsData = res.data.data.data;
                    this.total = res.data.data.total;
                    // this.page = res.data.data.pageNum;
//                    this.getHosInfoVoList();
                }, err => {
                    this.loading = false;
                });
            },
            pushedRegCertGoodsDetail(d){

                this.regCertGoodsDetailDailog = true;
                this.regCertGoodsDetailDailogTitle = '已推注册证产品明细'+'【'+d.productName+'】';
                this.getPushedRegCertsGoodsList(d,1);
            },
            availablePush(d){
                var result = d.status == '已审核' ? false:true;
                return result;
            },
            /**
             * '新增推送'产品列表当前页改变事件
             * @param val
             */
            nplCurrentChange(val) {

                this.nplPage = val;
                this.getProducts(this.nplPage);
            },
            /**
             * '新增推送'产品列表当前页面大小改变事件
             * @param val
             */
            nplSizeChange(val) {
                this.nplPageSize = val;
                this.getProducts(this.nplPage);
            },
            /**
             * '已推列表'当前页改变事件
             * @param val
             */
            pclHandleCurrentChange(val) {

                this.pclPage = val;
                this.getPushedRegCertsList(this.pclPage);
            },
            /**
             * '已推列表'列表当前页面大小改变事件
             * @param val
             */
            pclHandleSizeChange(val) {
                this.pclPageSize = val;
                this.getPushedRegCertsList(this.pclPage);
            },
            /**
             * '我的医院'列表当前页改变事件
             * @param val
             */
            myHosHandleCurrentChange(val) {
                this.page = val;
                this.getProvHosInfoVoList(this.page);
            },
            /**
             * '我的医院'列表当前页面大小改变事件
             * @param val
             */
            myHosHandleSizeChange(val) {
                this.pageSize = val;
                this.getProvHosInfoVoList(this.page);
            },

            provHosInfoEdit(item) {
                this.getHosInfoVoList();
                this.nameErr = '';
                if (item) {
                    this.provHosInfoVo = Object.assign({}, item);
                    this.modelTitle = "修改医院";
                    var tempObj = {
                        cname:item.hosName,
                        id:item.hosId,
                        linkman:'',
                        tel:''
                    };
                    this.hosInfos.push(tempObj);
                    this.hosId = this.provHosInfoVo.hosId;
                    this.linkman = this.provHosInfoVo.hosLxr;
                    this.tel = this.provHosInfoVo.hosLxrTelphonenum;
                    this.selhos=true;
                } else {
                    this.provHosInfoVo = {};
                    this.modelTitle = "添加医院";
                    this.hosId = '';
                    this.linkman = '';
                    this.tel = '';
                    this.selhos=false;
                }
                this.editFormVisible = true;
            },
            editSubmit: function () {
                var _this = this;
                var form =  this.$refs.editForm;
                const h = this.$createElement;
                var arr=new Array();
                form.validate((valid) => {
                    if (valid) {
                        this.editLoading = true;
                        this.provHosInfoVo.hosId = this.hosId;
                        this.provHosInfoVo.hosLxr = this.linkman;
                        this.provHosInfoVo.hosLxrTelphonenum = this.tel;
                        let serviceUrl = this.provHosInfoVo.id ? "/spdHDIService/myInfo/provHosInfo/update" : "/spdHDIService/myInfo/provHosInfo/insert";
                        this.axios.post(serviceUrl, this.provHosInfoVo).then(res => {
                            this.editLoading = false;

                            if (res.data.code == "0") {
                                this.$message({ message: '操作成功！', type: 'success' });
                                this.editFormVisible = false;
                                this.getProvHosInfoVoList();
                            } else if (res.data.code == "-2") {
                                this.nameErr = res.data.msg;
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
            sltChange(flag){
                if(flag){
//                    this.getHosInfoVoList();
                }
//
            },
            provRegistInfoPush:function (d) {

                this.hosId = d.hosId;
                this.getRegCertsList();
                this.getPushedRegCertsList(1);
                this.modelTitle1 = "证照推送" + "【"+d.hosName+"】";
                this.editFormVisible1 = true;
            },
            handleSelectionChange(val) {

                this.multipleSelection = val;
            },
            formatPushState (row, column) {
                return this.pushStateDict.find(item => item.value == row.pushStatus).label;
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            deleteSubmit: function (provHosInfoVo) {
                this.$confirm('确认解除关系吗?', '提示', {}).then(() => {
                    this.loading = true;
                    provHosInfoVo.status = '0';// 已解除
                    this.axios.post("/spdHDIService/myInfo/provHosInfo/setStatus", provHosInfoVo).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.loading = false;
                            this.$message({message: '操作成功！', type: 'success'});
                            this.getProvHosInfoVoList(this.page);
                        } else {
                            this.$message.error(res.data.msg);
                        }
                    }, err => {
                        this.loading = false;
                    });
                }).catch(() => {
                });
            },
            relinkSubmit: function (provHosInfoVo) {
                this.$confirm('确认重新建立关系吗?', '提示', {}).then(() => {
                    this.loading = true;
                    provHosInfoVo.status = '1';// 医院待审
                    this.axios.post("/spdHDIService/myInfo/provHosInfo/setStatus", provHosInfoVo).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.loading = false;
                            this.$message({message: '操作成功！', type: 'success'});
                            this.getProvHosInfoVoList(this.page);
                        } else {
                            this.$message.error(res.data.msg);
                        }
                    }, err => {
                        this.loading = false;
                    });
                }).catch(() => {
                });
            }
        },
        mounted() {
            this.getProvHosInfoVoList(1);
//            this.getHosInfoVoList();
        }
    }

</script>
<style scoped>
  .myHos-tableheight {
    height: calc(100% - 85px);
  }
  .myHos-pushList {
    height: 434px;
    max-height: 600px;
  }
  .myHos-newPushList {
    height: 434px;
    max-height: 600px;
  }
  .myHos-button-align{
    text-align: center;
  }
</style>
<style>
  #myHos-id .el-dialog--large{
    width: 70%;
    min-width: 1000px;
  }
</style>
