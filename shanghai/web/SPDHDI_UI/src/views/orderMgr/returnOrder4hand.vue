<template>
    <div  style="height: 100%;">
            <el-col :span="24" style="height: 100%;">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form :inline="true" label-width="80px">
                        <el-form-item label="退货单号">
                            <el-input v-model="filters.billId" placeholder="退货单号" ></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" icon="search" v-on:click="getReturnBillList(1)" >查询</el-button>
                        </el-form-item>
                    </el-form>
                </el-col>
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-button-group>
                        <el-button type="primary" icon="edit" size="mini" @click.native="addReturn">手工添加</el-button>
                    </el-button-group>
                </el-col>
                <!--列表-->
                <template>
                        <el-table :data="returnBillList" highlight-current-row v-loading="loading" style="width: 100%;" fit class="RO-Main-tableheight">
                                <el-table-column prop="billId" label="退货单编号" sortable>
                                </el-table-column>
                                <!--
                                  <el-table-column prop="returnCompanyName" label="请退机构" sortable>
                                </el-table-column>
                                -->
                                  <el-table-column prop="returnOrgName" label="请退科室" sortable>
                                </el-table-column>
                                  <el-table-column prop="outOrgName" label="出库科室" sortable>
                                </el-table-column>

                              <el-table-column prop="status" label="状态" sortable :formatter="statusFormat">
                              </el-table-column>

                                <el-table-column prop="provName" label="供应商名称" sortable>
                                </el-table-column>
                                <el-table-column prop="fillterName" label="制单人" sortable>
                                </el-table-column>
                                <el-table-column prop="fillDate" label="制单时间">
                                    <template slot-scope="scope">
                                        {{scope.row.fillDate | dateFormat('YYYY-MM-DD')}}
                                    </template>
                                </el-table-column>
                                <el-table-column label="操作" width="150">
                                    <template slot-scope="scope">
                                        <el-button  @click="returnBillView(scope.row.billId)" type="text">查看 </el-button>
                                        <el-button v-if="scope.row.status == 10"  @click="editReturn(scope.row.billId)" type="text">编辑</el-button>
                                        <el-button  v-if="scope.row.status == 10" @click="delReturn(scope.row)" type="text">删除</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                            <!--工具条-->
                            <el-col :span="24" class="toolbar">
                                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[20, 30, 50, 100]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
                                </el-pagination>
                            </el-col>

                </template>
                <template>
                     <el-dialog :title="modelTitle" close="onClose" :visible.sync="editFormVisible" :close-on-click-modal="false" width="70%">
                            <template>
                                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                                            <el-form label-width="120px" class="orderInput">
                                                    <!--
                                                        <el-col :span="8">
                                                            <el-form-item label="请购机构">
                                                                <el-input v-model="returnBill.returnCompanyName" placeholder="请购机构" ></el-input>
                                                            </el-form-item>
                                                        </el-col>
                                                        -->
                                                        <el-col :span="6">
                                                            <el-form-item label="请退科室">
                                                                <el-cascader :options="orgInfo"  v-model="returnOrg" :props="orgProps" :show-all-levels="false" :change-on-select="true">
                                                                </el-cascader>
                                                            </el-form-item>
                                                        </el-col>
                                                        <el-col :span="6">
                                                            <el-form-item label="出库科室">
                                                                <el-cascader :options="orgInfo"  v-model="outOrg" :props="orgProps" :show-all-levels="false" :change-on-select="true">
                                                                </el-cascader>
                                                            </el-form-item>
                                                        </el-col>
                                                        <el-col :span="6">
                                                            <el-form-item label="供应商">
                                                                <el-select v-model="returnBill.provId" filterable placeholder="请选择"  @change="reAxisBalnace">
                                                                    <el-option v-for="item in ProvsInfo" :key="item.provId"
                                                                    :label="item.provName"
                                                                    :value="item.provId">
                                                                    </el-option>
                                                                </el-select>
                                                            </el-form-item>
                                                        </el-col>
                                                <el-col :span="6">
                                                    <el-form-item label="结算单位">
                                                        <el-select v-model="returnBill.balanceOrgId" filterable placeholder="请选择" >
                                                            <el-option v-for="item in provs4Balance" :key="item.collectorId"
                                                                       :label="item.collectorName"
                                                                       :value="item.collectorId">
                                                            </el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                        <!--
                                                        <el-col :span="8">
                                                            <el-form-item label="建档时间">
                                                                <el-date-picker v-model="returnBill.fillDate" type="date" placeholder="选择日期" >
                                                                </el-date-picker>
                                                            </el-form-item>
                                                        </el-col>
                                                        -->
                                                        <el-col :span="6">
                                                            <el-form-item label="制单人">
                                                                <el-input v-model="returnBill.fillterName" placeholder="制单人"   :disabled=true ></el-input>
                                                            </el-form-item>
                                                        </el-col>
                                                        <el-col :span="24">
                                                            <el-form-item label="备注">
                                                                <el-input type="textarea" v-model="returnBill.memo"></el-input>
                                                            </el-form-item>
                                                        </el-col>
                                            </el-form>
                                </el-col>
                            </template>

                               <!--退货明细列表-->
                            <template>
                                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                                    <el-button-group>
                                        <el-button type="primary" class="el-icon-plus" size="mini" @click.native="addSltGoods">选择商品</el-button>
                                    </el-button-group>
                                </el-col>
                                <el-table :data="returnBill.returnBillSubList" style="width: 100%; height:90%;" fit  border>
                                    <el-table-column label="操作" width="50">
                                        <template slot-scope="scope">
                                            <el-button  @click="delRow(scope.row)" type="text">删除</el-button>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="rowNumber" label="序号" width="50px">
                                    </el-table-column>
                                     <el-table-column prop="purBillId" label="采购订单号" >
                                    </el-table-column>
                                    <!--
                                     <el-table-column prop="applyBillId" label="科室请购单号" >
                                    </el-table-column>
                               -->
                                    <el-table-column prop="hosGoodsName" label="产品名称"  show-overflow-tooltip>
                                    </el-table-column>
                                     <el-table-column prop="hosGoodsCode" label="产品编号">
                                    </el-table-column>
                                    <el-table-column prop="batchCode" label="批号" width="50px">
                                    </el-table-column>
                                        <el-table-column prop="batchNum" label="批次" width="50px">
                                    </el-table-column>
                                   <el-table-column prop="goodsStatus" label="质量状态" >
                                        <template slot-scope="scope">
                                            <el-select v-model="scope.row.goodsStatus" filterable placeholder="请选择" >
                                                                    <el-option
                                                                        v-for="item in ZLZTDict"
                                                                        :key="parseInt(item.val)"
                                                                        :label="item.ename"
                                                                        :value="parseInt(item.val)">
                                                                    </el-option>
                                            </el-select>
                                        </template>
                                   </el-table-column>

                                    <el-table-column prop="instorePrice" label="入库单价" width="80px">
                                    </el-table-column>
                                    <el-table-column prop="outstorePrice" label="请退单价" width="100px" >
                                      <template slot-scope="scope">
                                                <el-input  v-model="scope.row.outstorePrice"  placeholder="请输入价格" size="mini"></el-input>
                                            </template>
                                    </el-table-column>

                                    <el-table-column prop="canReturnQty" label="可退数量" width="80px" >
                                    </el-table-column>
                                        <el-table-column label="退货数量" >
                                            <template slot-scope="scope">
                                                <el-input-number placeholder="请输入数量" v-model="scope.row.qty" size="mini" :min="0" class="col-input-num"  @change="qtyChange($event,scope.row)">
                                                </el-input-number>
                                            </template>
                                       </el-table-column>


                                     <el-table-column prop="returnReason" label="退货原因">
                                     <template slot-scope="scope">
                                                <el-input  v-model="scope.row.returnReason"  placeholder="请输入内容" width="200px"></el-input>
                                            </template>
                                    </el-table-column>


                                </el-table>
                            </template>
                              <el-col :span="24" class="toolbar" style="margin-top:10px; padding-bottom: 0px;">
                                    <div style="margin-left:100px;text-align: center;">
                                        <el-button :plain="true" type="success"  @click="submitReturnBill('20')">提交</el-button>
                                        <el-button :plain="true" type="warning"  @click="submitReturnBill('10')">保存</el-button>
                                        <el-button :plain="true" type="danger"  @click="cancel">取消</el-button>
                                    </div>
                              </el-col>
                     </el-dialog>

                </template>
                   <!-- 手工选择-->
                <template>
                        <el-dialog title="选择商品" close="onClose" :visible.sync="sltGoodsFormVisible" :close-on-click-modal="false" width="70%">

                            <el-table :data="sltGoodsList" style="width: 100%; height: 30%;"
                                        highlight-current-row
                                        fit  border
                                        row-key='id'
                                      @selection-change="handleSelectionChange4sltGoods"
                            >
                                <el-table-column  type="selection" :reserve-selection="true" width="55"> </el-table-column>

                                <el-table-column prop="rowNumber" label="序号" width="60"></el-table-column>

                                <el-table-column prop="hosGoodsName"  label="产品名称" header-align="center" ></el-table-column>
                                <el-table-column prop="goodsGg"   label="规格" header-align="center" ></el-table-column>
                                <el-table-column prop="returnUnit"   label="单位" header-align="center" ></el-table-column>
                                <el-table-column prop="made" min-width='50%' label="产地" header-align="center" ></el-table-column>
                                <el-table-column prop="mfrsName" label="生产厂商" header-align="center" ></el-table-column>
                                <el-table-column prop="batchCode" label="批号" header-align="center" ></el-table-column>
                                <el-table-column prop="batchNum" label="批次" header-align="center" ></el-table-column>
                            </el-table>
                            <el-col :span="24" class="toolbar">
                                <el-pagination @size-change="handleSltGoodsSizeChange" @current-change="handleSltGoodsCurrentChange" :current-page="sltGoodsPage" :page-sizes="[10, 20, 30, 50]" :page-size="sltGoodsPageSize" layout="total, sizes, prev, pager, next" :total="sltGoodsTotal" style="float:right;" small>
                                </el-pagination>
                            </el-col>


                            <div slot="footer" class="dialog-footer">
                                <el-button @click.native="sltGoodsFormVisible = false">取消</el-button>
                                <el-button type="primary" @click.native="selectSubmit" :loading="editLoading">确定</el-button>
                            </div>
                       </el-dialog>
                </template>

            </el-col>
    </div>
</template>
<script>
import { PURCHASE_STATUS } from "./common/constance";
import util from "../../common/js/util.js";
import { mapGetters } from "vuex";
export default {
  data() {
    return {
      filters: {
        billId: "",
        provId: ""
      },
      ZLZTDict: {},
      modelTitle: "",
      billId: "",
      total: 0,
      page: 1,
      pageSize: 20,
      sltGoodsTotal: 0,
      sltGoodsPage: 1,
      sltGoodsPageSize: 10,
      loading: false,
      opModule: "",
      orgInfo: [],
      orgProps: {
        value: "code",
        label: "label"
      },
      ProvsInfo: [],
      provs4Balance: [],
      returnBill: {
        id: "",
        billId: "",
        fillDate: "",
        returnCompanyId: "",
        returnCompanyName: "",
        returnOrgId: "",
        returnOrgCode: "",
        returnOrgName: "",
        outOrgId: "",
        outOrgName: "",
        outOrgCode: "",
        provId: "",
        provName: "",
        distrRelation: "",
        fillter: "",
        memo: "",
        isStartInvoice: "",
        sumRow: "",
        status: "",
        balanceOrgId: "",
        returnBillSubList: []
      },
      returnBillList: [],
      returnBillSub: {
        id: "",
        pid: "",
        rowNumber: "",
        returnBillId: "",
        hosGoodsId: "",
        batchNum: "",
        batchCode: "",
        isUniqueCode: "",
        hosGoodsCode: "",
        hosGoodsName: "",
        goodsStatus: "",
        instorePrice: "",
        outstorePrice: "",
        canReturnQty: "",
        qty: "",
        purBillId: "",
        applyBillId: "",
        returnReason: "",
        version: "",
        lstUniqueList: []
      },
      editFormVisible: false,
      editLoading: false,
      currentRow: { billId: "", pid: "" },
      sltGoodsFormVisible: false,
      sltGoodsList: [],
      multipleSelection4sltGoods: []
    };
  },
  computed: {
    ...mapGetters(["getLoginUser"]),
    returnOrg: {
      get: function() {
        var res = [];
        if (this.returnBill.returnOrgCode) {
          let path = this.returnBill.returnOrgCode.split(".");
          if (path.length > 1) {
            var i = 0;
            for (i; i < path.length; i++) {
              let tempArr = path.slice(0, i + 1);
              res.push(tempArr.join("."));
            }
          } else {
            res = path;
          }
        }
        return res;
      },
      set: function(pathArr) {
        let res = { returnOrgId: "", returnOrgCode: "", returnOrgName: "" };
        if (pathArr.length > 0) {
          //res = pathArr[pathArr.length - 1];
          var tmpArr = this.orgInfo.concat();
          for (var i = 0; i < pathArr.length; i++) {
            if (tmpArr) {
              var node = tmpArr.find(item => item.code == pathArr[i]);
              if (node) {
                tmpArr = node.children;
                res.returnOrgCode = node.code;
                res.returnOrgId = node.id;
                res.returnOrgName = node.label;
              }
            }
          }
        }
        this.returnBill.returnOrgId = res.returnOrgId;
        this.returnBill.returnOrgCode = res.returnOrgCode;
        this.returnBill.returnOrgName = res.returnOrgName;
      }
    },
    outOrg: {
      get: function() {
        var res = [];
        if (this.returnBill.outOrgCode) {
          let path = this.returnBill.outOrgCode.split(".");
          if (path.length > 1) {
            var i = 0;
            for (i; i < path.length; i++) {
              let tempArr = path.slice(0, i + 1);
              res.push(tempArr.join("."));
            }
          } else {
            res = path;
          }
        }
        return res;
      },
      set: function(pathArr) {
        let res = { outOrgId: "", outOrgCode: "", outOrgName: "" };
        if (pathArr.length > 0) {
          //res = pathArr[pathArr.length - 1];
          var tmpArr = this.orgInfo.concat();
          for (var i = 0; i < pathArr.length; i++) {
            if (tmpArr) {
              var node = tmpArr.find(item => item.code == pathArr[i]);
              if (node) {
                tmpArr = node.children;
                res.outOrgCode = node.code;
                res.outOrgId = node.id;
                res.outOrgName = node.label;
              }
            }
          }
        }
        this.returnBill.outOrgId = res.outOrgId;
        this.returnBill.outOrgCode = res.outOrgCode;
        this.returnBill.outOrgName = res.outOrgName;
      }
    }
  },
  methods: {
    returnBillView(billId) {
      this.$router.push({ path: "returnBillView", query: { billId: billId } });
    },
    addReturn() {
      //    this.$router.push("addReturnBill"); 改为弹出
      this.editFormVisible = true;
      this.modelTitle = "添加退货单";
      // this.returnBill={}; 太粗暴了
      this.returnBill.returnBillSubList = [];
      this.returnBill.id = "";
      this.returnBill.billId = "";

      this.returnBill.fillDate = "";
      this.returnBill.provId = "";
      this.returnBill.provName = "";
      this.returnBill.distrRelation = "";
      this.returnBill.isStartInvoice = "";
      this.returnBill.status = "";
      this.returnBill.sumRow = "";
      this.returnBill.memo = "";
    },
    editReturn(billId) {
      this.editFormVisible = true;
      this.modelTitle = "修改退货单";
      //给returnBill 赋值
      this.getReturnBill(billId);
    },
    submitReturnBill: function(status) {
      //   this.opModule ="ADD";
      this.loading = true;
      this.returnBill.status = status;

      var params = this.returnBill;
      let serviceUrl = this.returnBill.billId
        ? "/spdHDIService/orderMgr/returnBill/updateReturnBill"
        : "/spdHDIService/orderMgr/returnBill/addReturnBill";

      this.axios.post(serviceUrl, params).then(
        res => {
          this.loading = false;
          if (res.data.code == "0") {
            //   var billId = res.data.data.billId;
            this.$msgbox({
              title: "提示",
              message: "操作成功",
              type: "success"
              //  callback: action => {
              //      this.$router.push({ path: 'purchase', query: { billId: billId } });
              //  }
            });
            this.editFormVisible = false;
            this.getReturnBillList(1);
          } else {
            this.$msgbox({ title: "提示", message: "操作失败", type: "error" });
          }
        },
        err => {
          this.loading = false;
          this.$msgbox({
            title: "提示",
            message: "操作失败",
            type: "error"
          });
        }
      );
    },
    delReturn(returnBill) {
      this.$confirm("确认删除这条记录吗?", "提示", {})
        .then(() => {
          this.loading = true;
          this.axios
            .post(
              "/spdHDIService/orderMgr/returnBill/delReturnBill",
              returnBill
            )
            .then(
              res => {
                this.loading = false;
                if (res.data.code == "0") {
                  this.loading = false;
                  this.$message({ message: "操作成功！", type: "success" });
                } else {
                  this.$message.error("操作失败！");
                }
                this.getReturnBillList(this.page);
              },
              err => {
                this.loading = false;
              }
            );
        })
        .catch(() => {});
    },

    getReturnBill(billId) {
      this.axios
        .get("/spdHDIService/orderMgr/returnBill/getReturnBill/" + billId)
        .then(
          res => {
            if (res.data.code == "0") {
              this.returnBill = res.data.data;
            }
          },
          err => {}
        );
    },
    delRow(pd) {
      var index = this.returnBill.returnBillSubList.findIndex(item => {
        return item.rowNumber == pd.rowNumber;
      });
      this.returnBill.returnBillSubList.splice(index, 1);
      this.returnBill.returnBillSubList.forEach(
        (item, i) => (item.rowNumber = i + 1)
      );
    },

    getReturnBillData() {
      if (!this.returnBill.provId) {
        this.$message.error("请选择供应商!");
        return;
      } else {
        this.filters.provId = this.returnBill.provId;
      }
      this.getReceiveBillList(1);
      this.receiveFormVisible = true;
    },
    qtyChange(val, row) {
      // row.purQty = val;
      // row.purAmount = (row.hosPrice * row.purQty).toFixed(2);
      // row.purTaxAmount = (row.purTaxPrice * row.purQty).toFixed(2);
    },

    getSltGoodsList: function(pIndex) {
      this.sltGoodsPage = pIndex;
      var param = {
        orderBy: "",
        page: this.sltGoodsPage,
        rows: this.sltGoodsPageSize,
        queryObject: {
          hosId: this.user.corpId,
          provId: this.returnBill.provId,
          collectorId: this.returnBill.balanceOrgId
        }
      };
      this.loading = true;
      this.axios
        .post(
          "/spdHDIService/collector4orderMgr/provProvGoods/listVoByPage",
          param
        )
        .then(
          res => {
            this.loading = false;
            this.sltGoodsList = res.data.data.data;
            this.sltGoodsTotal = res.data.data.total;
            //      this.convertSltGoods2ReturnBill(this.sltGoodsList);
          },
          err => {
            this.loading = false;
          }
        );
    },
    convertSltGoods2ReturnBill(goodList) {},
    handleCurrentChange4Row(val) {
      if (val != null) {
        this.currentRow = val;
      }
    },
    handleSelectionChange4sltGoods(val) {
      this.multipleSelection4sltGoods = val;
    },
    addSltGoods() {
      this.sltGoodsFormVisible = true;
      this.getSltGoodsList(1);
    },

    selectSubmit: function() {
      if (this.multipleSelection4sltGoods.length > 0) {
        this.multipleSelection4sltGoods.forEach(item => {
          let row = Object.assign({}, this.returnBillSub);
          row.hosGoodsCode = item.hosGoodsCode;
          row.hosGoodsId = item.hosGoodsId;
          row.hosGoodsName = item.hosGoodsName;
          row.batchNum = item.batchNum; //批次
          row.batchCode = item.batchCode;
          row.isUniqueCode = item.isUniqueCode;
          //row.goodsStatus =    质量状态
          row.instorePrice = item.price; //入库单价
          // row.outstorePrice   请退单价
          //row.canReturnQty      可退数量
          //   row.canReturnQty =item.qty;

          row.purBillId = item.purBillId;
          row.applyBillId = item.applyBillId;
          // row.returnReason =  退货原因
          row.lstUniqueList = item.lstUniqueList;

          if (row.lstUniqueList) {
            row.lstUniqueList.forEach(u => {
              if (u.rowNumber) {
                u.returnRowNumber = u.rowNumber;
                delete u.rowNumber;
                delete u.isRecieve;
              }
            });
          } else row.lstUniqueList = [];

          this.returnBill.returnBillSubList.push(row);

          row.rowNumber = this.returnBill.returnBillSubList.length;
        });
        //  this.receiveFormVisible = false;
        this.sltGoodsFormVisible = false;
      } else {
        this.$message.error("请选择商品!");
      }
    },

    cancel() {
      //  this.$router.push("purchase");
      this.editFormVisible = false;
    },

    //获取列表
    getReturnBillList: function(pIndex) {
      this.page = pIndex;
      var param = {
        orderBy: "",
        page: this.page,
        rows: this.pageSize,
        queryObject: {
          billId: this.filters.billId,
          returnCompanyId: this.user.corpId
        }
      };
      this.loading = true;
      this.axios
        .post("/spdHDIService/orderMgr/returnBill/listByPageVo", param)
        .then(
          res => {
            this.loading = false;
            this.returnBillList = res.data.data.data;
            this.total = res.data.data.total;
          },
          err => {
            this.loading = false;
          }
        );
    },
    getOrg() {
      var params = {};
      return this.axios.post("/platformService/sys/org/getOrgsByUnit", params);
    },
    getProv() {
      //  var params = { kind: "2" };
      // return this.axios.post('/platformService/bas/company/companysList', params);

      //改为医院所关联了的供应商
      //  var param = {
      //         queryObject: {pushStatus:20},
      //     };
      //  return this.axios.post('/provRegistInfoPush/pushedRegCertsProvListByPage', params);

      let param = { orderBy: "", queryObject: {}, page: 0, rows: 0 };
      return this.axios.post(
        "/spdHDIService/provManager/hosProvInfo/hosProvInfoVoList",
        param
      );
    },
    reAxisBalnace() {
      if (!this.returnBill.provId) {
        this.$message.error("请先选择供应商");
      }
      this.returnBill.balanceOrgId = "";
      this.provs4Balance = [];
      this.getProvs4Balance();
    },
    getProvs4Balance() {
      let param = {
        orderBy: "",
        queryObject: {
          hosId: this.user.corpId,
          provId: this.returnBill.provId
        },
        page: 0,
        rows: 0
      };
      //  return this.axios.post('/spdHDIService/collector4orderMgr/hosCollectorProv/listVoByPage', param);

      this.axios
        .post(
          "/spdHDIService/collector4orderMgr/hosCollectorProv/listVoByPage",
          param
        )
        .then(
          res => {
            this.loading = false;
            this.provs4Balance = res.data.data.data;
          },
          err => {
            this.loading = false;
          }
        );
    },

    handleCurrentChange(val) {
      this.page = val;
      this.getReturnBillList(this.page);
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.getReturnBillList(this.page);
    },
    handleSltGoodsCurrentChange(val) {
      this.sltGoodsPage = val;
      this.getSltGoodsList(this.sltGoodsPage);
    },
    handleSltGoodsSizeChange(val) {
      this.sltGoodsPage = val;
      this.getSltGoodsList(this.sltGoodsPage);
    },

    statusFormat(row) {
      return PURCHASE_STATUS[row.status].text;
    },
    // editReturn(billId) {
    //     this.$router.push({ path: 'addReturn', query: { billId: billId } });
    // },
    // ReturnView(billId) {
    //     this.$router.push({ path: 'ReturnView', query: { billId: billId } });
    // }
    getZLZT: function() {
      //质量状态 字典
      this.loading = true;
      this.axios
        .post("/platformService/sys/dict/getDictValueByDictEname", {
          dictName: "KQZLZT"
        })
        .then(
          res => {
            this.loading = false;
            if (res.data.code == "0") {
              this.ZLZTDict = res.data.data;
            } else {
              this.$message.error(res.data.msg);
            }
          },
          err => {
            this.loading = false;
          }
        );
    }
  },

  mounted() {
    //    var billId = this.$route.query.billId;

    this.getZLZT();
    var billId = this.billId;
    if (billId) {
      this.filters.billId = billId;
    }
    this.getReturnBillList(1);

    var _this = this;
    this.pageLoading = true;

    //   var Methods = [_this.getOrg(), _this.getProv(),_this.getProvs4Balance()];
    var Methods = [_this.getOrg(), _this.getProv()];
    if (billId) {
      _this.opModule = "EDIT";
      //  Methods.push(_this.getPurchaseInfo(billId));
    } else {
      _this.opModule = "ADD";

      _this.returnBill.fillterName = _this.getLoginUser.ename;
      _this.returnBill.fillter = _this.getLoginUser.userId;
      _this.returnBill.returnCompanyId = _this.getLoginUser.corpId;
      _this.returnBill.returnCompanyName = _this.getLoginUser.corpName;
      _this.returnBill.returnOrgId = _this.getLoginUser.orgId;
      _this.returnBill.returnOrgCode = _this.getLoginUser.orgCode;
      _this.returnBill.returnOrgName = _this.getLoginUser.orgName;
      _this.returnBill.outOrgName = _this.getLoginUser.orgName;
      _this.returnBill.outOrgId = _this.getLoginUser.orgId;
      _this.returnBill.outOrgCode = _this.getLoginUser.orgCode;
      _this.returnBill.fillDate = util.formatDate.format(
        new Date(),
        "yyyy-MM-dd"
      );
    }
    _this.axios.all(Methods).then(
      _this.axios.spread(function(orgInfo, provInfo) {
        _this.pageLoading = false;
        if (orgInfo.data.code == "0") {
          _this.orgInfo = orgInfo.data.data.children[0].children;
        }
        if (provInfo.data.code == "0") {
          _this.ProvsInfo = provInfo.data.data.data;

          _this.filterProvs = _this.ProvsInfo;
        }
        //            if (provs4Balance.data.code == "0") {
        //              _this.provs4Balance = provs4Balance.data.data.data;
        //
        //            }
        _this.getProvs4Balance();
      }),
      err => {
        _this.pageLoading = false;
        this.$message.error("页面初始化失败,请刷新重试或联系管理员");
      }
    );
  }
};
</script>

<style scoped>
.el-table {
  font-size: 12px;
}
.RO-Main-tableheight {
  height: calc(100% - 120px);
}
</style>
