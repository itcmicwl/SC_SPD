<template>
    <el-tabs type="border-card" v-model="showTab">
        <el-tab-pane label="退货确认单" name="returnConfirmOrderLst">
            <section>
                <el-col :span="24">
                    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                        <el-col :span="2">
                            <span>退货确认单号</span>
                        </el-col>
                        <el-col :span="8">
                            <el-input v-model="filters.billId" placeholder="单号" ></el-input>
                        </el-col>
                        <el-col :span="8" :push="1">
                            <el-button type="primary" icon="search" v-on:click="getPurchaseList(1)" >查询</el-button>
                            <el-button @click="resetForm" >重置</el-button>
                        </el-col>
                    </el-col>
                    <!--列表-->
                    <template>
                        <el-table :data="comfirmList" highlight-current-row v-loading="loading" style="width: 100%;" fit height="400">
                            <el-table-column label="操作" width="150">
                                <template slot-scope="scope">
                                    <el-button  @click="ComfirmView(scope.row.billId)" type="text">查看</el-button>

                                </template>
                            </el-table-column>
                            <el-table-column label="确认单号" prop="billId" width="180"></el-table-column>
                      <!--      <el-table-column prop="status" label="状态" sortable :formatter="statusFormat" width="80"></el-table-column>
                       -->
                           <el-table-column prop="returnCompanyName" label="请购机构" sortable width="180">
                            </el-table-column>
                            <el-table-column prop="returnOrgName" label="请退科室" sortable width="180">
                            </el-table-column>
                            <el-table-column prop="outOrgName" label="出库科室" sortable width="180">
                            </el-table-column>

                            <el-table-column prop="fillterName" label="制单人" sortable width="120">
                            </el-table-column>
                            <el-table-column prop="fillDate" label="制单时间" width="120">
                                <template slot-scope="scope">
                                    {{scope.row.fillDate | dateFormat('YYYY-MM-DD')}}
                                </template>
                            </el-table-column>
                        </el-table>
                        <!--工具条-->
                        <el-col :span="24" class="toolbar">
                            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[30, 100, 200, 400]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
                            </el-pagination>
                        </el-col>
                    </template>
                </el-col>
            </section>
            <el-dialog title="确认单查看" :visible.sync="confirmViewVisible" :close-on-click-modal="false" >
                <returnConfirmView :billid="billid"></returnConfirmView>
                <div slot="footer" class="dialog-footer">
                  <!--  <el-button :plain="true" type="danger"  @click="print">打印</el-button>-->
                    <el-button @click.native="confirmViewVisible = false" >关闭</el-button>
                </div>
            </el-dialog>
        </el-tab-pane>
        <el-tab-pane label="退货单" name="purLst">
            <returnConfirmOrder v-on:savePurcon="savePurcon"></returnConfirmOrder>
        </el-tab-pane>
    </el-tabs>
</template>
<script>
    import returnConfirmOrder from './returnConfirmOrder';
    import returnConfirmView from './returnConfirmView';
    import { PURCHASE_STATUS } from './common/constance';
   // import $ from 'jquery';
//    import '../../common/third/printThis/printThis';
    export default {
        data() {
            return {
                filters: {
                    billId: '',
                    provId: ''
                },
                billid: '',
                confirmViewVisible: false,
                showTab: 'returnConfirmOrderLst',
                total: 0,
                page: 1,
                pageSize: 30,
                loading: false,
                comfirmList: []
            }
        },
        methods: {
            //获取列表
            getComfirmList: function (pIndex) {
                this.page = pIndex;
                var param = {
                    orderBy: "",
                    page: this.page,
                    rows: this.pageSize,
                    queryObject: {
                        billId: this.filters.billId,
                        provId: this.filters.provId
                    }
                };
                this.loading = true;
                this.axios.post('/spdHDIService/orderMgr/returnconfirm/listByPageVo', param).then(res => {
                    this.loading = false;
                    this.comfirmList = res.data.data.data;
                    this.total = res.data.data.total;
                }, err => {
                    this.loading = false;
                });
            },
            handleCurrentChange(val) {
                this.page = val;
                this.getComfirmList(this.page);
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.getComfirmList(this.page);
            },
            savePurcon(billids) {
                this.$nextTick(function () {
                    this.showTab = "returnConfirmOrderLst";
                    this.filters.billId = billids.join(",");
                    this.getComfirmList(1);
                });
            },
            ComfirmView(billId) {
                this.billid = billId;
                this.confirmViewVisible = true;
                this.$router.push({ path: 'returnConfirmView', query: { billId: billId } });
            },
            resetForm() {
                this.filters.billId = "";
            },
            statusFormat(row) {
                return PURCHASE_STATUS[row.status].text;
            },
//            PurchaseView(billId) {
//                alert("xx:"+billId);
//                this.billid = billId;
//                this.confirmViewVisible = true;
//                this.$router.push({ path: 'returnConfirmView', query: { billId: billId } });
//            },
           /* print() {
                $("#printContent").printThis({
                    debug: false,
                    importCSS: true,
                    importStyle: true,
                    printContainer: true,
                    loadCSS: './static/style/print.css',
                    pageTitle: "采购确认单",
                    removeInline: false,
                    printDelay: 333,
                    header: null,
                    formValues: true
                });
            }*/
        },
        mounted() {
            this.filters.provId = this.user.corpId;
            this.getComfirmList(1);
        },
        components: { returnConfirmOrder, returnConfirmView }

    }
</script>
<style scoped>
.RO-Main-tableheight{
        height: calc(100% - 100px);
    }
    .el-table {
        font-size: 12px;
    }
    .el-tabs,.el-tabs .el-tabs__content{
        height:94%;
    }
    .el-tabs__item{
        height:30px;
        line-height: 30px;
        font-size:12px;
    }
</style>

