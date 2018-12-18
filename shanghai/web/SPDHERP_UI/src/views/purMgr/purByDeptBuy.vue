<template>
    <el-row v-loading="ploading" element-loading-text="正生成采购单请稍后..." style="height: 100%;">
        <el-row>
            <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                <el-form label-width="80px" class="order_view" label-position="left">                    
                    <el-col :span="8">
                        <el-form-item label="请领库房:">
                            <Stock4Slt v-model="filters.stockId" :hosId="this.user.corpId" :deptId='user.orgId' sSize='mini'></Stock4Slt>
                        </el-form-item>          
                    </el-col>
                    <el-col :span="8">
                         <el-form-item label="采购方式:">
                            <el-select v-model="filters.buyKind" size="mini" placeholder="请选择采购方式" :disabled="purModes.length==1" @change="resetAndSearch">
                                <el-option v-for="item in purModes"
                                    :key="item.value"  :label="item.text" :value="item.value"
                                    :disabled="item.flag">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="请领日期：">
                            <el-date-picker v-model="filters.sDate" type="daterange" align="right" placeholder="选择日期范围" :picker-options="pickerOptions2" size="mini">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="请领科室：">
                            <el-select  v-model="filters.buyDeptId" :loading="deptLoading" placeholder="请选择" size='mini' @change="deptChange">
                                <el-option v-for="item in DeptList" :key="item.value" :label="item.ename"
                                :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="物资名称:">
                            <el-input v-model="filters.goodsName" size="mini" placeholder="物资名称"></el-input>   
                        </el-form-item>             
                    </el-col>
                    <el-col :span="8" :offset="2">
                            <el-button type="primary" icon="search" @click="resetAndSearch" size="mini">查询</el-button>
                    </el-col> 
                </el-form>               
            </el-col>
        </el-row>
        <el-row :gutter="5" style="height: 100%;">
            <el-col :span="5" style="height:calc(100% - 106px);" :loading="provLoading">
              <el-tree ref="provTree" :data="provList" :props="defaultProps"
              node-key="subProvId" highlight-current @current-change="provChange"></el-tree>
                <!-- 
                <div class="prov_info">
                    <ul class="prov_list">
                        <li v-for="(item,index) in provList" :key="index" @click="provChange(item)" 
                        :class="{ 'gyqx_active': filters.provId == item.provId }" :title="'集配商：'+item.colName">
                            <span>{{item.provName}}</span>
                            <span>({{item.goodsKinds}})</span>
                        </li>
                    </ul>
                </div>-->
            </el-col>
            <el-col :span="19" style="height:calc(100% - 36px);">
                <el-table ref='dt_orderSub' :data="applyDetailList" highlight-current-row border v-loading="sLoading" style="width: 100%;" fit  class="table_sub_content" tooltip-effect="light" @selection-change="sltRows" @select-all="subSltAll"  @current-change="curRChange" @select="subSelect" row-key="goodsId">
                    <el-table-column type="selection" width="30">
                    </el-table-column>                
                    <!-- <el-table-column label="采购数量" sortable width="90">
                        <template slot-scope="scope">
                            <el-input-number placeholder="请输入数量" v-model="scope.row.whQty" size="mini" :min="0"  class="col-input-num" @change="whQtyChange(scope.row,$event)">
                            </el-input-number>
                        </template>
                    </el-table-column>
                    <el-table-column prop="storeQty" label="库房数量" sortable width="85">
                    </el-table-column> -->
                    <el-table-column prop="qty" label="数量" sortable width="60">
                    </el-table-column>
                    <el-table-column prop="goodsName" label="物资名称" sortable width="150" show-overflow-tooltip>
                    </el-table-column>
                    
                    <el-table-column prop="unit" label="单位" sortable width="80">
                    </el-table-column>
                    <el-table-column prop="goodsPrice" label="单价" sortable width="60">
                    </el-table-column>
                    <el-table-column prop="je" label="金额" width="60">
                    </el-table-column>
                    <el-table-column prop="goodsGg" label="包装规格" sortable width="120" show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column prop="brand" label="品牌" width="100" show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column prop="mfrsName" label="生产厂家" show-overflow-tooltip>
                    </el-table-column>
                </el-table>
                <el-table ref='dt_orderMain' :data="applyMainList" highlight-current-row v-loading="mLoading" style="width: 100%;" fit  class="table_sub_content" tooltip-effect="light" @selection-change="mainListChange" row-key="subId">
                    <el-table-column type="selection" width="30" reserve-selection>
                    </el-table-column>
                    <el-table-column label="操作" sortable>
                        <template slot-scope="scope">
                            <el-button  @click="rejectDetail(scope.row,30)" type="text">不采购</el-button>
                            <el-button  @click="rejectDetail(scope.row,20)" type="text">驳回</el-button>
                        </template>
                    </el-table-column>
                    <el-table-column label="送货方式" sortable>
                        <template slot-scope="scope">
                            <el-select placeholder="请选择" size="mini" v-model="scope.row.sendType">
                                <el-option label="直送" :value="30">
                                    <span style="float: left;line-height:25px">直送</span>
                                    <i class="send-type fa fa-truck"></i>
                                </el-option>
                                <el-option label="库房" :value="20">
                                    <span style="float: left;line-height:25px">库房</span>
                                    <i class="send-type fa fa-sign-out"></i>
                                </el-option>
                                <el-option label="自取" :value="10">
                                    <span style="float: left;line-height:25px">自取</span>
                                    <i class="send-type fa fa-cart-plus"></i>
                                </el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column prop="billId" label="请购单号" sortable>
                    </el-table-column>
                    <el-table-column prop="buyDeptName" label="科室名称" sortable>
                    </el-table-column>
                    <el-table-column prop="fillDate" label="请购时间" sortable>
                        <template slot-scope="scope">{{scope.row.fillDate | dateFormat}}</template>
                    </el-table-column>
                    <el-table-column prop="qty" label="请购数量">
                    </el-table-column>
                    <el-table-column prop="je" label="金额">
                    </el-table-column>
                </el-table>
                <el-col :span="24" class="toolbar" style="margin-top:10px; padding-bottom: 0px;text-align: center;">
                    <el-button type="primary"  @click="buildPurOrder">生成采购单</el-button>
                </el-col>          
            </el-col>
        </el-row>    
    </el-row>
</template>

<script>
//请领采购
import Vue from "vue";
import colAndProvs from "@/components/colAndProvs";
import Stock4Slt from "@/components/Stock4Slt";
import moment from "moment";
import { BUY_KIND } from "./common/constance";
var Child = {
  template:
    '<div><p v-for="item in errMsg" v-html="item"></p><p v-if="vGoOn">是否忽略以上物资，继续生成！</p></div>',
  props: ["errMsg", "vGoOn"]
};
export default {
  data() {
    return {
      filters: {
        sDate: [],
        goodsName: "",
        colId: "",
        colName: "",
        provId: "",
        stockId: "",
        buyDeptId: "",
        buyKind: 0
      },
      provLoading: false,
      deptLoading: false,
      sLoading: false,
      mLoading: false,
      ploading: false,
      pickerOptions2: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            }
          }
        ]
      },
      defaultProps: {
        children: "lstDetail",
        label: function(data, node) {
          if (data.subProvName.length > 15) {
            return `${data.subProvName.substring(0, 13)}...(${
              data.goodsKinds
            })`;
          }
          return `${data.subProvName}(${data.goodsKinds})`;
        }
      },
      sltGoods: [],
      sltMainList: [],
      applyDetailList: [],
      applyMainList: [],
      provList: [],
      DeptList: [],
      purModes: []
    };
  },
  components: { colAndProvs, Stock4Slt },
  computed: {
    buy_kind: {
      get: function() {
        return BUY_KIND;
      }
    }
  },
  methods: {
    whQtyChange(row, val) {
      row.whQty = val;
      row.je = row.whQty * row.price || 0;
    },
    deptChange(deptId) {
      this.getProvList();
    },
    provChange(provInfo, node) {
      this.filters.colId = provInfo.provId;
      this.filters.colName = provInfo.provName;
      this.filters.provId = provInfo.subProvId;
      this.searchNew();
    },
    searchNew() {
      var _this = this;
      this.applyMainList = [];
      this.applyDetailList = [];
      this.$refs.dt_orderSub.clearSelection();
      this.$refs.dt_orderMain.clearSelection();
      var param = {
        goodsName: this.filters.goodsName,
        buyDeptId: this.filters.buyDeptId,
        provId: this.filters.colId,
        subProvId: this.filters.provId,
       
        hosId: this.user.corpId,
        subState: 10,
        buyKind: this.filters.buyKind
      };
      if(Array.isArray(this.filters.sDate) && this.filters.sDate.length>0){
        param.sDate = moment(this.filters.sDate[0]).format("YYYY-MM-DD");
        param.eDate = moment(this.filters.sDate[1]).format("YYYY-MM-DD");
      }
      this.sLoading = true;
      this.axios
        .post("/spdHERPService/applyMgr/deptBuyMain/getApplyPurInfos", param)
        .then(
          res => {
            _this.sLoading = false;
            _this.setTableData(res.data.data);
            //   var applyDetailList = res.data.data;
            //   _this.applyDetailList = applyDetailList;
            //  _this.applyMainList = applyDetailList;
          },
          err => {
            _this.sLoading = false;
          }
        );
    },
    setTableData(data) {
      var _this = this;
      var goodsSet = new Set(data.map(o => o.goodsId)); //根据goodsId分组
      for (let key of goodsSet) {
        var children = data.filter(o => {
          return o.goodsId == key;
        });
        var goodsGroup =Object.assign({},children[0]);
        if (children.length > 1) {
          var count = 0;
          children.forEach(item => {
            count += item.qty;
          });
          goodsGroup.qty = count;
          goodsGroup.je = goodsGroup.qty * goodsGroup.goodsPrice;
        }
        goodsGroup.lstMain = children;
        _this.applyDetailList.push(goodsGroup);
      }
    },

    subSelect(selection, row) {
      this.applyMainList = row.lstMain;
      this.$nextTick(function() {
        var flag = selection.find(o => o.goodsId == row.goodsId); //判断是否选中
        if (flag) {
          row.lstMain.forEach(item => {
            this.$refs.dt_orderMain.toggleRowSelection(item, true);
          });
        } else {
          row.lstMain.forEach(item => {
            this.$refs.dt_orderMain.toggleRowSelection(item, false);
          });
        }
      });
    },
    subSltAll(selection) {
      var _this = this;
      if (selection.length == 0) {
        //取消全选
        this.$refs.dt_orderMain.clearSelection();
      } else {
        //全选
        _this.applyMainList = [];
        this.applyDetailList.forEach(item => {
          _this.applyMainList = _this.applyMainList.concat(item.lstMain);
        });
        this.applyMainList.forEach(item => {
          this.$refs.dt_orderMain.toggleRowSelection(item, true);
        });
      }
    },
    curRChange(row) {
      //this.getMainList(row.goodsId);
      if (row) {
        this.applyMainList = row.lstMain;
      }
    },
    mainListChange(rows) {
      this.sltMainList = rows;
    },
    sltRows(rows) {
      this.sltGoods = rows;
    },
    getAllMainLst() {
      var _this = this;
      _this.applyDetailList.forEach(item => {
        _this.MainListApi(item.goodsId).then(
          res => {
            if (res.data.code == 0) {
              item.lstMain = res.data.data;
            }
          },
          err => {}
        );
      });
    },
    getMainList(goodsId) {
      var _this = this;
      this.mLoading = true;
      this.MainListApi(goodsId).then(
        res => {
          _this.mLoading = false;
          _this.applyMainList = res.data.data;
        },
        err => {
          _this.mLoading = false;
        }
      );
    },
    MainListApi(goodsId) {
      var param = {
        goodsId: goodsId,
        hosId: this.user.corpId,
        //sDate: this.filters.sDate[0],
        //eDate: this.filters.sDate[1],
        subState: 10,
        buyKind: this.filters.buyKind,
        buyDeptId: this.filters.buyDeptId
      };
       if(Array.isArray(this.filters.sDate) && this.filters.sDate.length>0){
        param.sDate = moment(this.filters.sDate[0]).format("YYYY-MM-DD");
        param.eDate = moment(this.filters.sDate[1]).format("YYYY-MM-DD");
      }
      return this.axios.post(
        "/spdHERPService/applyMgr/deptBuyMain/getMainBillVo4pur",
        param
      );
    },
    notPur(goodsId) {
      //不采购 批量操作
      var _this = this;
      this.MainListApi(goodsId).then(
        res => {
          var lst = res.data.data;
          if (lst) {
            var params = [];
            lst.foreach(item => {
              var param = {
                billId: item.billId,
                id: item.subId,
                subState: 30,
                warehouseDealMan: _this.user.userId
              };
              params.push(params);
            });
            _this.changeState(goodsId, params);
          }
        },
        err => {}
      );
    },
    rejectDetail(row, status) {
      var param = {
        billId: row.billId,
        rowNumber: row.rowNumber,
        subState: status || 30,
        warehouseDealMan: this.user.userId
      };
      this.changeState(row.goodsId, [param]);
    },
    changeState(goodsId, param) {
      var _this = this;
      _this.axios
        .post("/spdHERPService/applyMgr/deptBuySub/dealBill4Pur", param)
        .then(
          res => {
            if (res.data.code == 0) {
              _this.$message({ message: "操作成功！", type: "success" });
              _this.getProvList();
              _this.search();
            } else {
              _this.$message({ message: "操作失败！", type: "error" });
            }
          },
          err => {
            _this.$message({ message: "操作失败！", type: "error" });
          }
        );
    },
    buildPurOrder() {
      var _this = this;
      this.ploading = true;
      var errMsg = [];
      const h = _this.$createElement;
      var methods = [];
      if (this.sltGoods.length == 0) {
        this.$message.error("请选择要采购的物资");
        _this.ploading = false;
        return;
      }
      if (this.sltMainList.length == 0) {
        this.$message.error("请选择要采购的请购单");
        _this.ploading = false;
        return;
      }
      var mainDateList = [];
      this.sltGoods.forEach(item => {
        var mainList = this.sltMainList.filter(mItem => {
          return mItem.goodsId == item.goodsId;
        });
        if (mainList.length > 0) {
          mainDateList.push(mainList);
        } else {
          errMsg.push(
            `物资：<font color='red'>${item.goodsName}</font>下没有选择单据。`
          );
        }
      });
      if (errMsg.length > 0) {
        _this.ploading = false;
        _this.$msgbox({
          title: "错误",
          type: "error",
          message: h(Child, { props: { errMsg: errMsg } })
        });
        return false;
      }
      _this.creatPurBill(mainDateList);
      //_this.createByNoSlt();
    },
    createByNoSlt() {
      var _this = this;
      for (var j = 0; j < this.sltGoods.length; j++) {
        methods.push(_this.MainListApi(this.sltGoods[j].goodsId));
      }
      _this.axios.all(methods).then(
        _this.axios.spread(function() {
          if (arguments.length > 0) {
            var mainList = Array.prototype.slice.apply(arguments).map(item => {
              return item.data.data;
            });
            _this.creatPurBill(mainList);
          }
        })
      );
    },
    creatPurBill(mainListData) {
      var errMsg = [];
      var _this = this;
      const h = _this.$createElement;
      var org = this.DeptList.find(item => {
        return (item.id = _this.filters.buyDeptId);
      });
      var purOrder = {
        provId: this.filters.colId,
        provName: this.filters.colName,
        subProvId: this.filters.provId,
        recOrgId: org.id,
        recOrgCode: org.code,
        recOrgName: org.ename,
        status: "0",
        purKind: 1,
        purchaseList: []
      };
      for (var j = 0; j < mainListData.length; j++) {
        var mainList = mainListData[j];
        if (mainList && mainList.length > 0) {
          var goodsInfo = _this.sltGoods.find(
            o => o.goodsId == mainList[0].goodsId
          );
          if (goodsInfo.goodsCode) {
            var storeQty = 0; //Number(goodsInfo.storeQty);
            var whQty = Number(goodsInfo.qty); //填入的采购合计数量
            var purQty = 0; //科室采购数量合计
            for (var i = 0; i < mainList.length; i++) {
              var applyMain = mainList[i];
              storeQty -= Number(applyMain.qty);
              if (storeQty <= 0 && whQty > 0) {
                var thisPurQty = Math.abs(storeQty);
                var tempQty = whQty - thisPurQty;
                thisPurQty = tempQty <= 0 ? whQty : thisPurQty;
                whQty = tempQty;
                purQty += thisPurQty;
                var purDetail = {
                  applyDeptId: org.id,
                  applyDeptCode: org.code,
                  applyDeptName: org.ename,
                  applyBillId: applyMain.billId,
                  applyRowNum: applyMain.rowNumber,
                  hosGoodsId: goodsInfo.goodsId,
                  hosGoodsCode: goodsInfo.goodsCode,
                  hosGoodsName: goodsInfo.goodsName,
                  hosPrice: goodsInfo.goodsPrice,
                  hosUnit: goodsInfo.unit,
                  hosScale: goodsInfo.unitRate,
                  hosApplyQty: Math.abs(thisPurQty),
                  applyDate: applyMain.fillDate,
                  purTax: goodsInfo.taxRate || 0.17, // 税率
                  saleMan: goodsInfo.salemanId,
                  //purTaxPrice: (row.hosPrice / (1 + row.purTax)).toFixed(6);,      // 采购不含税单价
                  status: 0,
                  uniqueKind: goodsInfo.uniqueKind,
                  provId: _this.filters.provId
                };
                if (purDetail.hosApplyQty > 0) {
                  purOrder.purchaseList.push(purDetail);
                }
                storeQty = 0;
              }
            }
            //多余数量舍弃
            // if(Number(goodsInfo.whQty) > purQty){//多余的数量为当前科室采购
            //     var purDetail = {
            //         //rowNum: i+1,
            //         applyDeptId: _this.user.orgId,
            //         applyDeptName: _this.user.orgName,
            //         applyBillId: '',
            //         applyRowNum: 0,
            //         hosGoodsId: goodsInfo.goodsId,
            //         hosGoodsCode: goodsInfo.goodsCode,
            //         hosGoodsName: goodsInfo.goodsName,
            //         hosPrice: goodsInfo.price,
            //         hosUnit: goodsInfo.unit,
            //         hosScale: goodsInfo.unitRate,
            //         saleMan:goodsInfo.salemanId,
            //         hosApplyQty: Number(goodsInfo.whQty) - purQty,
            //         applyDate:new Date(),
            //         purTax: goodsInfo.taxRate || 0.17,           // 税率
            //         //purTaxPrice: (row.hosPrice / (1 + row.purTax)).toFixed(6);,      // 采购不含税单价
            //         status: 0,
            //         uniqueKind:goodsInfo.uniqueKind,
            //         provId: _this.filters.provId
            //     };
            //     purOrder.purchaseList.push(purDetail);
            // }
          } else {
            errMsg.push(
              `物资：<font color='red'>${
                goodsInfo.goodsName
              }</font>没有与供应商商品相关联。`
            );
          }
        }
      }
      _this.ploading = false;
      if (errMsg.length > 0) {
        _this.$msgbox({
          title: "警告",
          type: "warning",
          showCancelButton: true,
          message: h(Child, { props: { errMsg: errMsg, vGoOn: true } }),
          callback: action => {
            if (action == "confirm") {
              _this.setPurBill(purOrder);
            }
          }
        });
        return false;
      } else {
        _this.setPurBill(purOrder);
      }
    },
    setPurBill(purOrder) {
      var _this = this;
      if (purOrder.purchaseList.length > 0) {
        var sessionId =
          _this.user.corpId +
          _this.user.orgId +
          moment(new Date()).format("YYYYMMDDHHmmss");
        LocalStorage.setItem(sessionId, JSON.stringify(purOrder));
        _this.$router.push({
          path: "addPurchase",
          query: {
            type: "purByapply",
            applyInfo: sessionId,
            purType: _this.filters.buyKind
          }
        });
      }
    },
    getProvList() {
      var _this = this;
      if (!this.filters.buyKind || this.filters.buyKind == 0) {
        return;
      }
      if (!this.filters.buyDeptId) {
        return;
      }
      this.provLoading = true;
      var param = {
        goodsName: this.filters.goodsName,
        //sDate: this.filters.sDate[0],
        //eDate: this.filters.sDate[1],
        hosId: this.user.corpId,
        buyDeptId: this.filters.buyDeptId,
        subState: 10,
        buyKind: this.filters.buyKind,
        sourceWarehouseId: this.user.orgId
      };
       if(Array.isArray(this.filters.sDate) && this.filters.sDate.length>0){
        param.sDate = moment(this.filters.sDate[0]).format("YYYY-MM-DD");
        param.eDate = moment(this.filters.sDate[1]).format("YYYY-MM-DD");
      }
      this.axios
        .post("/spdHERPService/applyMgr/deptBuySub/getQGDProvList", param)
        .then(
          res => {
            _this.provLoading = false;
            _this.provList = res.data.data;
            if (res.data.code == 0) {
              // _this.filters.provId = _this.provList[0].provId;
              //_this.provChange(_this.provList[0]);
              _this.$refs.provTree.setCurrentKey(_this.provList[0].provId);
            }
          },
          err => {
            _this.provLoading = false;
          }
        );
    },
    getBuyDeptList() {
      var _this = this;
      if (!this.filters.buyKind || this.filters.buyKind == 0) {
        return;
      }
      var param = {
        //goodsName: this.filters.goodsName,
        // sDate: this.filters.sDate[0],
        // eDate: this.filters.sDate[1],
        hosId: this.user.corpId,
        buyKind: this.filters.buyKind,
        sourceWarehouseId: this.user.orgId
      };
       if(Array.isArray(this.filters.sDate) && this.filters.sDate.length>0){
        param.sDate = moment(this.filters.sDate[0]).format("YYYY-MM-DD");
        param.eDate = moment(this.filters.sDate[1]).format("YYYY-MM-DD");
      }
      this.deptLoading = true;
      this.axios
        .post("/spdHERPService/applyMgr/deptBuyMain/getBuyDeptList", param)
        .then(
          res => {
            _this.deptLoading = false;
            if (res.data.code == 0) {
              _this.DeptList = res.data.data;
              if (_this.DeptList.length > 0) {
                _this.filters.buyDeptId = _this.DeptList[0].id;
                _this.getProvList();
              }
            }
          },
          err => {
            _this.deptLoading = false;
          }
        );
    },
    getUserPurMode() {
      var _this = this;
      var param = {
        userId: _this.user.userId
      };
      this.axios.post("/spdHERPService/deptMgr/applyAuditRel/list", param).then(
        res => {
          if (res.data.code == 0 && res.data.data.length > 0) {
            _this.setPurModes(res.data.data);
            _this.getBuyDeptList();
          } else {
            _this.setPurModes(0);
            _this.$message.error("当前用户未设置采购模式！");
          }
        },
        err => {}
      );
    },
    setPurModes(lstPModes) {
      var _this = this;
      var res = [];
      if (Array.isArray(lstPModes) && lstPModes.length > 0) {
        lstPModes.forEach(item => {
          var buyKind = _this.buy_kind.find(o => {
            return o.value == item.purMode;
          });
          if (buyKind) {
            res.push(buyKind);
          }
        });
      }

      if (res.length == 0) {
        res = [{ value: 0, text: "无", flag: false }];
      }
      _this.purModes = res.sort(function(a, b) {
        return a.value - b.value;
      });
      _this.filters.buyKind = _this.purModes[0].value;
    },
    resetAndSearch() {
      this.DeptList = [];
      this.provList = [];
      this.applyMainList = [];
      this.applyDetailList = [];
      this.$refs.dt_orderSub.clearSelection();
      this.$refs.dt_orderMain.clearSelection();
      this.filters.provId = "";
      this.buyDeptId = "-1";
      this.getBuyDeptList();
    }
  },
  mounted() {
    var end = new Date();
    var start = new Date();
    start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
    this.filters.sDate = [start, end];
    //this.filters.buyKind =Number(this.$route.query.buyKind);
    this.filters.buyKind = 10;
    this.getUserPurMode();
  }
};
</script>

<style>
.send-type {
  float: right;
  line-height: 25px;
}
.table_sub_content {
  font-size: 12px;
  height: calc(48% - 35px);
  overflow-y: auto;
}
.prov_list {
  list-style-type: none;
  padding: 0px;
  margin: 0px;
}
.prov_list li {
  height: 25px;
  line-height: 25px;
  padding: 0px 0px 0px 10px;
  cursor: pointer;
  border-bottom: 1px solid #ccc;
}
.prov_info {
  height: 100%;
  width: 100%;
  border: 1px solid #ccc;
  overflow: auto;
}
.prov_list li:hover {
  background-color: #ccc;
}
.el-select-dropdown__item span {
  line-height: 25px;
}
</style>