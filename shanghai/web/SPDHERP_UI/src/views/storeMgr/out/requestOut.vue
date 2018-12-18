<template>
  <div id="store-out-requestout" class="full-height">
    <el-row class="full-height">
      <el-col :span="4" class="full-height">
        <div class="inline-display">
          <span class="inline-display">出库库房：</span>
          <el-select v-model="currentStock" placeholder="请选择房库" class="stock-select" @change="handleStockChange">
            <el-option v-for="item in ownStockList" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </div>
        <el-tree :data="orgTree" :props="treeProps" highlight-current node-key="id" :indent="25" class="tree-height" default-expand-all @node-click="handleClickTreeNode" :expand-on-click-node="false" :render-content="renderTreeContent"></el-tree>
      </el-col>

      <el-col :span="20" class="full-height" style="padding-left:10px;">
        <div style="display:block;margin-bottom:3px;">
          <div style="margin-bottom: 5px;" class="inline-display">
            <div class="inline-display">
              <span>请领时间：</span>
              <el-date-picker v-model="searchParams.beginDate" class="data-picker" type="date" placeholder="开始日期" ></el-date-picker>
              <span>-</span>
              <el-date-picker v-model="searchParams.endDate" class="data-picker" type="date" placeholder="结束日期" ></el-date-picker>
            </div>
            <div class="inline-display" style="width:155px;">
              <el-input v-model="searchParams.billId" placeholder="请购单号" @keyup.enter="getRequestBillList">
                <el-button slot="append" icon="search" @click="getRequestBillList"></el-button>
              </el-input>
            </div>
          </div>
        </div>

        <el-table :data="requestBillList" highlight-current-row height="outter" ref="mainTable" class="table-style" border @row-click="handleTableRowClick" :row-class-name="mainTableRowClassName" >
          <!-- @row-dblclick="handlePreviewBill"> v-loading.body="mainLoading" element-loading-text="拼命加载中" -->
          <el-table-column type="index" label="序号" width="45"></el-table-column>
          <el-table-column label="请购单号" prop="billId" width="150">
            <template slot-scope="scope">
              {{ scope.row.billId }}
              <span v-if="!scope.row.available" class="out-of-stock"></span>
            </template>
          </el-table-column>
		   <el-table-column label="制单人" prop="fillterName"></el-table-column>

          <el-table-column label="请购类型">
            <template slot-scope="scope">{{ CGMSDicts[scope.row.buyKind] }}</template>
          </el-table-column>
          <el-table-column label="状态">
            <template slot-scope="scope">{{ DEPTBUY_MAIN_STATUS[scope.row.state].text }}</template>
          </el-table-column>
          <el-table-column label="请购时间" prop="fillDate" :formatter="formatDate"></el-table-column>
          <el-table-column label="品种" prop="kindsCount"></el-table-column>
          <el-table-column label="数量" prop="sumQty"></el-table-column>
          <el-table-column label="待处理" prop="undoneQty"></el-table-column>
          <el-table-column label="备注" prop="remark"></el-table-column>
          <el-table-column fixed="right" label="操作" width="50" header-align="center" align="center">
            <template slot-scope="scope">
              <el-button :disabled="!scope.row.available" size="mini" type="primary" @click.native.prevent="handlePreviewBill(scope.row)">出库
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-table style="margin-top:10px;" :data="requestBillDetailList" highlight-current-row height="outter" ref="detailTable" class="table-style" border :row-class-name="detailTableRowClassName">
          <!-- v-loading.body="detailLoading" element-loading-text="拼命加载中" -->
          <el-table-column type="index" label="序号" width="45"></el-table-column>
          <el-table-column label="请购" prop="reqQty"></el-table-column>
          <el-table-column label="可发" prop="avlQty">
            <template slot-scope="scope">
              {{ scope.row.avlQty }}
              <span v-if="scope.row.avlQty === 0 && scope.row.undoneQty !== 0" class="out-of-stock"></span>
            </template>
          </el-table-column>
          <el-table-column label="待发" prop="undoneQty"></el-table-column>
          <el-table-column label="已发" prop="sendQty"></el-table-column>
          <el-table-column label="商品ID" prop="goodsId" show-overflow-tooltip></el-table-column>
          <el-table-column label="商品名称" prop="goodsName" show-overflow-tooltip></el-table-column>
          <el-table-column label="商品规格" prop="goodsGg" show-overflow-tooltip></el-table-column>
          <el-table-column label="单位" prop="unit"></el-table-column>
          <el-table-column label="单价" prop="price"></el-table-column>
          <el-table-column label="包装规格" prop="pkg"></el-table-column>
          <el-table-column label="状态">
            <template slot-scope="scope">
              {{ DEPTBUY_SUB_STATUS[scope.row.subState].text }}
              <span v-if="scope.row.subState === '40' || scope.row.subState === '50'" :class="[scope.row.subState === '40' ? 'part-delivery':'all-delivery']"></span>
            </template>
          </el-table-column>
          <el-table-column label="产地" prop="made" show-overflow-tooltip></el-table-column>
          <el-table-column label="生产厂家" prop="mfrsName" show-overflow-tooltip></el-table-column>
          <el-table-column label="供应商" prop="provName" show-overflow-tooltip></el-table-column>
          <el-table-column label="唯一码管理" prop="isUnique" :formatter="formatUnique"></el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <el-dialog title="预览出库单" :visible.sync="dlgVisible" width="70%" top="10vh">
      <el-form :model="outStockBill" label-width="70px" ref="editForm" class="el-form-item-nomsg">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="出库类型">
              <el-select v-model="outStockBill.outStockKind" placeholder="请选择出库类型" disabled>
                <el-option v-for="(value,key) in CKLXDicts" :key="key" :label="value" :value="key"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="领用部门">
              <el-select v-model="outStockBill.outDeptId" disabled>
                <el-option v-for="item in orgsData" :key="item.id" :label="item.ename" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="出库人员">
              <el-input v-model="outStockBill.filler" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="制单时间">
              <el-input v-model="outStockBill.fillDate" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <div class="text-style">
              种类：{{ selectedGoodsKinds }}，总数量：{{ selectedGoodsQuantity }}，总金额：{{ selectedGoodsAmount }}
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="收货地址">
              <el-input v-model="outStockBill.recAddress" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系人">
              <el-input v-model="outStockBill.recLinkman" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系电话">
              <el-input v-model="outStockBill.recLinkmanPhone" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="outStockBill.outStockType === 20">
          <el-col :span="6">
            <el-form-item label="住院号">
              <el-autocomplete v-model="outStockBill.patientName" :fetch-suggestions="querySearchAsync" placeholder="请输入患者住院号" @select="handlePatientSelect" :debounce="500" :trigger-on-focus="false" value-key="patientInHosId" popper-class="patient-autocomplete">
                <template slot-scope="{ item }">
                  <div class="name">{{ item.patientName }}</div>
                  <span class="info">{{ item.bedNum }},{{ item.deptName }}</span>
                </template>
              </el-autocomplete>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="患者姓名">
              <el-input v-model="outStockBill.patient" :disabled="patientDisabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="床号">
              <el-input v-model="outStockBill.patientBedNum" :disabled="patientDisabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="科室">
              <el-select v-model="outStockBill.patientDeptCode" :disabled="patientDisabled">
                <el-option v-for="item in orgsData" :key="item.id" :label="item.ename" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <div class="inline-display">
            <label @click="handleLabelClick" style="font-size:12px;margin-left:10px;">副码：</label>
            <el-checkbox v-model="hasSlaverBarcode" :disabled="outStockBill.goodsList.length===0"></el-checkbox>
          </div>
          <div class="inline-display input-barcode-small barcode-input-margin" :class="[hasSlaverBarcode?'slaver-barcode-input':'barcode-input']">
            <el-input v-model="masterBarcode" placeholder="请录入条码主码" ref="masterBarcode" @keyup.native.enter="handleMasterBarcodeEnter" :disabled="outStockBill.goodsList.length===0">
            </el-input>
          </div>
          <div class="inline-display input-barcode-small barcode-input-margin" v-if="hasSlaverBarcode" :class="[hasSlaverBarcode?'slaver-barcode-input':'']">
            <el-input v-model="slaverBarcode" placeholder="请录入条码副码" ref="slaverBarcode" @keyup.native.enter="handleSlaverBarcodeEnter" :disabled="outStockBill.goodsList.length===0">
            </el-input>
          </div>
        </el-row>
      </el-form>
      <el-table :data="outStockBill.goodsList" highlight-current-row ref="requestDetailTable" border row-key="bsId" @selection-change="handleSelectionChange" @select="handleSelect" :row-class-name="billTableRowClassName">
        <el-table-column type="selection" width="35" :reserve-selection="false" :selectable="checkSelectable" ></el-table-column>
        <el-table-column type="index" label="序号" width="45" align="center" ></el-table-column>
        <el-table-column type="index" label="操作" width="35" align="center" >
          <template slot-scope="scope">
            <el-tooltip effect="dark" content="复制当前行" placement="top">
              <el-button type="text" @click="handleClkCopyLine(scope.row)">
                <i class="fa fa-files-o" aria-hidden="true"></i>
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="出库数量" width="140">
          <template slot-scope="scope">
            <el-input-number style="width:100%;" v-model="scope.row.qty" :min="0" :max="scope.row.isUnique !== '3' ? 1 : Math.min(scope.row.avlQty, scope.row.undoneQty, scope.row.selected.id ? scope.row.selected.qty : Number.MAX_VALUE)" :disabled="scope.row.avlQty === 0 || scope.row.isUnique !== '3' || scope.row.selected.isPackage === true" @change="handleInputNumberChange(scope.row)"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="可发" width="45" prop="avlQty">
        </el-table-column>
        <el-table-column label="待发" width="45" prop="undoneQty">
        </el-table-column>
        <el-table-column label="名称" prop="goodsName" width="100" show-overflow-tooltip>
        </el-table-column>
        <el-table-column label="规格型号" prop="goodsGg" width="100" show-overflow-tooltip>
        </el-table-column>
        <el-table-column label="请购" width="35" prop="reqQty">
        </el-table-column>
        <el-table-column label="唯一码管理" width="75" prop="isUnique" :formatter="formatUnique">
        </el-table-column>
        <el-table-column label="批号&批次&唯一码" width="300">
          <template slot-scope="scope">
            <el-select v-model="scope.row.selected.id" @change="changeBatchId(scope.row)" clearable style="width:100%" :disabled="scope.row.selected.isPackage === true">
              <el-option v-for="item in batchNoIdUniqueCodeList[scope.row.goodsId]" :key="item.id" :label="item.label" :value="item.id">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="注册证" prop="certificateCode" align="center">
          <template slot-scope="scope">
            <el-input placeholder="请输入注册证号" v-model="scope.row.certificateCode" size="mini">
            </el-input>
          </template>
        </el-table-column>
        <el-table-column label="单位" width="35" prop="unit">
        </el-table-column>
        <el-table-column label="单价" width="40" prop="price">
        </el-table-column>
        <el-table-column label="金额" width="50">
          <template slot-scope="scope">
            {{ (scope.row.qty * scope.row.price).toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column label="包装规格" width="60" prop="pkg">
        </el-table-column>
        <el-table-column label="生产厂家" width="80" prop="mfrsName" show-overflow-tooltip>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="dlgVisible = false">取消</el-button>
        <el-button type="primary" @click="handleDlgConfirm(true)" :loading="btnLoading" :disabled="outStockBill.goodsList.length === 0">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { DEPTBUY_MAIN_STATUS, DEPTBUY_SUB_STATUS, OUTSTOCK_BILL_STATUS, OUTSTOCK_BILL_KIND } from '../../../common/js/constance'

export default {
  components: {},
  data () {
    return {
      baseURL: '/spdHERPService/stockMgr/out/requestOut',
      BARCODE_PARSE_URL: '/spdHERPService/baseData/baseDataMaint/BarcodeParse/parseBarcodeHERP',
      rootNode: {
        id: '-1',
        pid: '',
        // label: this.user.corpName,
        label: '请购部门',
        children: []
      },
      orgsData: [],
      treeProps: {
        children: 'children',
        label: 'label'
      },

      ownStockList: [],
      currentStock: '',
      currentDeptId: '',
      mainLoading: false,
      detailLoading: false,

      searchParams: {
        beginDate: '',
        endDate: '',
        billId: ''
      },
      multipleSelection: [],
      hasSlaverBarcode: false,
      masterBarcode: '',
      slaverBarcode: '',
      parseBarcodeResultSet: '',
      CGMSDicts: {},
      CKLXDicts: {},

      dlgVisible: false,
      btnLoading: false,
      outStockBill: {
        goodsList: []
      },

      batchNoIdUniqueCodeList: [],
      copiedRowNum: 0,
      uniqueCodeList: [],

      requestBillList: [],
      requestBillDetailList: [],
      currentBillId: '',

      packageCount: 0,
      patientDisabled: false
    }
  },
  computed: {
    orgTree () {
      let orgsData = JSON.parse(JSON.stringify(this.orgsData))
      orgsData.forEach(data => {
        data.label = data.available ? data.ename + '(' + data.billCount + ')' : data.ename
      })
      orgsData.filter(item => item.pid === '/' || item.pid === '').forEach(item => { item.pid = '-1' })
      return [this.addTreeNode(JSON.parse(JSON.stringify(this.rootNode)), orgsData)]
    },
    params () {
      let params = JSON.parse(JSON.stringify(this.searchParams))
      params.stockId = this.currentStock
      params.deptId = this.currentDeptId
      if (params.beginDate === '') {
        delete params.beginDate
      }
      if (params.endDate === '') {
        delete params.endDate
      }
      if (params.billId === '') {
        delete params.billId
      }
      return params
    },
    selectedGoodsKinds () {
      const kinds = new Set(this.multipleSelection.map(o => o.goodsId))
      return kinds.size
    },
    selectedGoodsQuantity () {
      return this.multipleSelection.reduce((acc, cur) => acc + cur.qty, 0)
    },
    selectedGoodsAmount () {
      let sum = this.multipleSelection.reduce((acc, cur) => acc + cur.qty * cur.price, 0)
      return sum.toFixed(2)
    },
    DEPTBUY_MAIN_STATUS () {
      return DEPTBUY_MAIN_STATUS
    },
    DEPTBUY_SUB_STATUS () {
      return DEPTBUY_SUB_STATUS
    }
  },
  mounted () {
    this.getStockList().then(result => {
      this.ownStockList = result
      if (result && result.length !== 0) {
        this.currentStock = result[1].id
        this.handleStockChange(this.currentStock)
      } else {
        return
      }
      this.getPurchaseDict()
      this.getCKLXDict()
    }).catch(err => {
      this.handleError(err)
    })
  },
  methods: {
    handleStockChange (stockId) {
      this.getOrgList(stockId).then(result => {
        this.orgsData = result
        this.requestBillList.splice(0)
        this.requestBillDetailList.splice(0)
        this.currentDeptId = ''
      }).catch(err => {
        this.handleError(err)
      })
    },
    handleClickTreeNode (data, node, treeComponent) {
      if (!data.available) {
        return
      }
      if (data.id === this.currentDeptId) {
        return
      }
      this.currentDeptId = data.id
      this.requestBillList.splice(0)
      this.requestBillDetailList.splice(0)
      if (data.billCount !== 0) {
        this.getRequestBillList()
      }
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    handleSelect (selection, row) {
      if (row.selected && row.selected.barcode) {
        const bSelected = selection.indexOf(row) !== -1
        const dsRows = this.outStockBill.goodsList.filter(o => o.selected.barcode === row.selected.barcode)
        for (let item of dsRows) {
          this.$refs['requestDetailTable'].toggleRowSelection(item, bSelected)
        }
      }
    },
    handleInputNumberChange (row) {
      this.$nextTick(() => {
        if (row.qty > 0) {
          if (!row.batchId) {
            row.selected = JSON.parse(JSON.stringify(this.batchNoIdUniqueCodeList[row.goodsId].find((element, index, array) => element.qty === row.undoneQty) || this.batchNoIdUniqueCodeList[row.goodsId][0]))
            row.price = row.selected.price
          }
          this.$refs['requestDetailTable'].toggleRowSelection(row, true)
        } else {
          row.selected = { id: '' }
          this.$refs['requestDetailTable'].toggleRowSelection(row, false)
        }
      })
    },
    handleLabelClick () {
      if (this.requestStockGoodList.length !== 0) {
        this.hasSlaverBarcode = !this.hasSlaverBarcode
      }
    },
    handleMasterBarcodeEnter () {
      if (this.hasSlaverBarcode) {
        this.$refs['slaverBarcode'].$el.children[0].focus()
      } else {
        this.handleSlaverBarcodeEnter()
      }
    },
    handleSlaverBarcodeEnter () {
      this.getBarcodeParseResult().then(res => {
        if (this.masterBarcode.toLowerCase().indexOf('ds') === 0) {
          if (this.outStockBill.goodsList.some(o => o.selected.barcode === this.masterBarcode)) {
            const dsRows = this.outStockBill.goodsList.filter(o => o.selected.barcode === this.masterBarcode)
            if (this.multipleSelection.indexOf(dsRows[0]) !== -1) {
              this.handleError('已选择此定数包')
            } else {
              for (let item of dsRows) {
                this.$refs['requestDetailTable'].toggleRowSelection(item, true)
              }
            }
          } else {
            this.changeDsGoodsQty(res)
          }
        } else {
          this.parseBarcodeResultSet = res.map(item => { // Set
            return {
              goodsId: item.goodsId,
              batchNo: item.batchNo,
              trackNo: item.trackNo
            }
          })
          this.changeRequestGoodsQty(this.parseBarcodeResultSet)
        }
        this.masterBarcode = ''
        this.slaverBarcode = ''
      }).catch(err => {
        this.handleError(err)
      })
    },
    handlePreviewBill (row) {
      if (row.billId !== this.currentBillId || !this.requestBillDetailList.length > 0 || this.requestBillDetailList.some(o => o.billId !== row.billId)) {
        this.requestBillDetailList.splice(0)
        this.currentBillId = row.billId
        this.getRequestBillDetailList(row.billId).then(res => {
          this.requestBillDetailList = res
          this.requestBillDetailList.forEach(o => {
            o.billId = row.billId
            o.qty = 0
            o.batchId = ''
            o.batchNo = ''
            o.uniqueCode = ''
            o.selected = { id: '' }
          })
          this.initPreviewBill(row)
        }).catch(err => {
          this.handleError(err)
        })
      } else {
        this.initPreviewBill(row)
      }
    },
    initPreviewBill (row) {
      this.outStockBill = this.newOutStockBill()
      this.packageCount = 0
      let distinctGoodsId = {}
      distinctGoodsId = new Set(this.outStockBill.goodsList.map(item => item.goodsId))
      if (distinctGoodsId.size > 0) {
        this.getUniqueCode(distinctGoodsId).then(res => {
          this.uniqueCodeList = JSON.parse(JSON.stringify(res))
          for (let goodsId of distinctGoodsId) {
            let goodsList = res.filter(o => o.goodsId === goodsId)
            this.$set(this.batchNoIdUniqueCodeList, goodsId, [...goodsList])
            for (let item of this.batchNoIdUniqueCodeList[goodsId]) {
              item.label = 'ID:' + item.id
              if (item.batchNo) {
                item.label += ',' + item.batchNo + '|' + item.batchId
              } else {
                item.label += ',' + item.batchId
              }
              if (item.uniqueCode) {
                item.label += '|' + item.uniqueCode
              }
              item.label += ', 数量:' + item.qty
            }
            this.batchNoIdUniqueCodeList[goodsId].sort((a, b) => Number(b.qty) - Number(a.qty))
          }
          this.$nextTick(() => {
            this.outStockBill.goodsList.forEach((item, index) => {
              const row = this.outStockBill.goodsList[index]
              // if ( row.isUnique === '3' ) {
                row.selected = JSON.parse(JSON.stringify(this.batchNoIdUniqueCodeList[row.goodsId].find((element, index, array) => element.qty === row.undoneQty) || this.batchNoIdUniqueCodeList[row.goodsId][0]))
                row.qty = row.undoneQty
                row.price = row.selected.price
                row.certificateCode = row.selected.certificateCode
                this.$refs['requestDetailTable'].toggleRowSelection(row, true)
              // }
            })
          })
        }).catch(err => {
          this.handleError(err)
        })
      }
      this.dlgVisible = true
    },
    handleDlgConfirm (submit) {
      // TODO: 定数包只选择其中部分数据时，需要验证
      if (this.combineDuplicateGoods()) {
        this.handleError('所选商品有重复项')
        return
      }
      if (this.checkGoodsQtyWrong(this.multipleSelection)) {
        this.handleError('所选商品数量总和大于库存发货数量，或同一请购商品数量大于待发货数量。')
        this.$refs['requestDetailTable'].clearSelection()
        return
      }
      if (this.multipleSelection.some(o => !o.selected || o.selected.id === '')) {
        this.handleError('所选商品未选择批号/批次/唯一码。')
        this.$refs['requestDetailTable'].clearSelection()
        return
      }
      if (this.multipleSelection.length === 0) {
        this.handleError('请选择商品')
        return
      }
      if (this.outStockBill.outStockType === 20){ // 判断高值是否输入患者信息
        if (!this.outStockBill.patient || !this.outStockBill.patientName || !this.outStockBill.patientDeptCode || !this.outStockBill.patientBedNum) {
          this.handleError('请输入正确患者信息')
          return
        }
      }
      // if (this.multipleSelection.some(o => o.qty > Math.min(o.avlQty, o.selected.qty, o.undoneQty))) {
      //   this.handleError('所选商品数量大于库存数量。')
      //   this.$refs['requestDetailTable'].clearSelection()
      //   return
      // }
     // return
      this.btnLoading = true
      this.axios.post(this.baseURL + '/saveOutStockBill', this.outStockBillData2Post(submit)).then(res => {
        if (res.data.code === 0) {
          this.$message.success('保存出库单成功')
          this.getRequestBillList()
        } else {
          this.handleError(res.data.msg)
        }
        this.btnLoading = false
        this.dlgVisible = false
      }).catch(err => {
        this.handleError(err)
        this.btnLoading = false
      })
    },
    handleClkCopyLine (row) {
      let index = this.outStockBill.goodsList.findIndex(o => o.bsId === row.bsId)
      let item = JSON.parse(JSON.stringify(row))
      item.qty = 0
      item.batchId = ''
      item.batchNo = ''
      item.uniqueCode = ''
      item.selected = { id: '' }
      this.outStockBill.goodsList.splice(index + 1, 0, item)
      return this.outStockBill.goodsList[index + 1]
    },
    handleTableRowClick (row, event, column) {
      if (row.billId === this.currentBillId && (this.requestBillDetailList.length > 0 && this.requestBillDetailList.every(o => o.billId === row.billId))) {
        return
      }
      this.requestBillDetailList.splice(0)
      this.currentBillId = row.billId
      this.detailLoading = true
      this.getRequestBillDetailList(row.billId).then(res => {
        this.requestBillDetailList = res
        this.requestBillDetailList.forEach(o => {
          o.billId = row.billId
          o.qty = 0
          o.batchId = ''
          o.batchNo = ''
          o.uniqueCode = ''
          o.selected = { id: '' }
        })
        this.detailLoading = false
      }).catch(err => {
        this.handleError(err)
        this.detailLoading = false
      })
    },
    addTreeNode (node, data) {
      for (let i = data.length - 1; i >= 0; i--) {
        if (data[i].pid === node.id) {
          data[i].children = []
          node.children.push(JSON.parse(JSON.stringify(data[i])))
          data.splice(i, 1)
        }
      }
      node.children.forEach(o => this.addTreeNode(o, data))
      return node
    },
    getOrgList (stockId) {
      return new Promise((resolve, reject) => {
        this.axios.get(this.baseURL + '/getOrgs', {
          params: {
            stockId
          }
        }).then(res => {
          if (res.data.code === 0) {
            resolve(res.data.data)
          } else {
            reject(res.data.msg)
          }
        })
      })
    },
    getStockList () {
      return new Promise((resolve, reject) => {
        this.axios.get(this.baseURL + '/getStockList').then(res => {
          if (res.data.code === 0) {
            resolve(res.data.data)
          } else {
            reject(res.data.msg)
          }
        })
      })
    },
    getRequestBillList () {
      this.mainLoading = true
      this.requestBillDetailList.splice(0)
      this.getRequestList().then(res => {
        this.requestBillList = res
        this.mainLoading = false
      }).catch(err => {
        this.handleError(err)
      })
    },
    getUniqueCode (goodsId) {
      let params = {
        stockId: this.currentStock,
        buyKind: this.outStockBill.outStockType,
        goodsId
      }
      return new Promise((resolve, reject) => {
        this.axios.post(this.baseURL + '/getUniqueCodeList', params).then(res => {
          if (res.data.code === 0) {
            resolve(res.data.data)
          } else {
            reject(res.data.msg)
          }
        }).catch(err => {
          reject(err)
        })
      })
    },
    getRequestList () {
      return new Promise((resolve, reject) => {
        this.axios.get(this.baseURL + '/getRequestList', {
          params: this.params
        }).then(res => {
          if (res.data.code === 0) {
            resolve(res.data.data)
          } else {
            reject(res.data.msg)
          }
        }).catch(err => {
          reject(err)
        })
      })
    },
    getRequestBillDetailList (billId) {
      return new Promise((resolve, reject) => {
        this.axios.get(this.baseURL + '/getRequestBillDetailList', {
          params: {
            billId,
            deptId: this.currentDeptId,
            stockId: this.currentStock
          }
        }).then(res => {
          if (res.data.code === 0) {
            resolve(res.data.data)
          } else {
            reject(res.data.msg)
          }
        }).catch(err => {
          reject(err)
        })
      })
    },
    getBarcodeParseResult () {
      let params = {
        masterBarcode: this.masterBarcode,
        slaverBarcode: this.slaverBarcode
      }
      return new Promise((resolve, reject) => {
        this.axios.get(this.BARCODE_PARSE_URL, { params }).then(res => {
          if (res.data.code === 0) {
            resolve(res.data.data)
          } else {
            reject(res.data.msg)
          }
        })
      })
    },
    handleError (err) {
      return new Promise((resolve, reject) => {
        this.$msgbox({ title: '错误', type: 'error', message: err }).then(action => {
          resolve(action)
        }).catch(err => {
          reject(err)
        })
      })
    },
    checkSelectable (row, index) {
      return row.qty !== 0
    },
    checkGoodsQtyWrong (data) {
      const bsIdList = new Set(data.map(item => item.bsId))
      for (let bsId of bsIdList) {
        let goods = data.filter(o => o.bsId === bsId)
        const sum = goods.reduce((acc, cur) => acc + cur.qty, 0)
        if (sum > goods[0].undoneQty) {
          return true
        }
      }

      const kinds = new Set(data.map(item => item.goodsId))
      for (let item of kinds) {
        let goods = data.filter(o => o.goodsId === item)
        const sum = goods.reduce((acc, cur) => acc + cur.qty, 0)
        if (sum > goods[0].avlQty) {
          return true
        }
      }

      return data.some(o => o.qty > o.selected.qty)
    },
    changeDsGoodsQty (data) {
      let dsGoodsIds = new Set(data.map(o => o.goodsId))
      if (dsGoodsIds.size > 1) { // 定数包商品品种大于1
        this.handleError('无效定数包')
        return
      }
      const rows = this.outStockBill.goodsList.filter(o => o.goodsId === data[0].goodsId)
      if (rows.length === 0) {
        this.handleError('未找到相关商品')
        return
      }
      for (let item of data) {
        if (rows.every(o => !!o.selected.id)) {
          this.handleClkCopyLine(rows[0])
        }
        let selectedRow = this.outStockBill.goodsList.find(o => o.goodsId === item.goodsId && !o.selected.id)

        let label = 'ID:ds' + (++this.packageCount)
        if (item.batchCode) {
          label += ',' + item.batchCode + '|' + item.goodsBatchId
        } else {
          label += ',' + item.goodsBatchId
        }
        label += ', 数量:' + item.qty
        this.batchNoIdUniqueCodeList[item.goodsId].push({
          'id': 'ds' + this.packageCount,
          'goodsId': item.goodsId,
          'batchNo': item.batchCode,
          'qty': item.goodsQty,
          'batchId': item.goodsBatchId,
          'uniqueCode': '',
          'label': label,
          'barcode': this.masterBarcode,
          'isPackage': true
        })
        selectedRow.selected = JSON.parse(JSON.stringify(this.batchNoIdUniqueCodeList[selectedRow.goodsId].find(o => o.id === ('ds' + this.packageCount))))
        selectedRow.price = selectedRow.selected.price
        selectedRow.qty = item.goodsQty
        selectedRow.sterilizationDate = item.sterilizationDate
        selectedRow.sterilizationCode = item.sterilizationCode
        selectedRow.sterilizationEndDate = item.sterilizationEndDate
        selectedRow.expdtEndDate = item.expdtEndDate

        this.$refs['requestDetailTable'].toggleRowSelection(selectedRow, true)
      }
    },
    changeRequestGoodsQty (data) {
      let sum1 = 0
      let sum2 = 0
      let sum3 = 0
      let rowAdded = false
      for (let item of data) {
        if (rowAdded) {
          break
        }
        const rows = this.outStockBill.goodsList.filter(o => o.goodsId === item.goodsId)
        if (item.trackNo) { // 条码解析有跟踪码/唯一码
          for (let row of rows) {
            sum1 += row.qty
            if (sum1 >= row.avlQty) { // 商品总出库数量大于可发数量
              this.handleError('"' + row.goodsName + '"' + ' 总出库数超过库存数量')
              return
            }
            if (row.qty >= row.undoneQty) { // 商品出库数量大于待发数量
              continue
            }
            if (rows.filter(o => o.bsId === row.bsId).reduce((acc, cur) => acc + cur.qty, 0) >= row.undoneQty) { // 同请购单明细出库数量大于待发数量
              continue
            }
            if (row.qty === 0) {
              row.qty = 1
              row.selected = JSON.parse(JSON.stringify(this.batchNoIdUniqueCodeList[row.goodsId].find(o => o.uniqueCode === item.trackNo)))
              row.price = row.selected.price
              rowAdded = true
              this.$refs['requestDetailTable'].toggleRowSelection(row, true)
              break
            } else if (row.qty === 1) {
              if (rows.filter(o => o.bsId === row.bsId).some(o => o.qty === 0)) {
                continue
              }
              let copiedRow = this.handleClkCopyLine(row)
              copiedRow.selected = JSON.parse(JSON.stringify(this.batchNoIdUniqueCodeList[copiedRow.goodsId].find(o => o.uniqueCode === item.trackNo)))
              row.price = row.selected.price
              copiedRow.qty = 1
              rowAdded = true
              this.$refs['requestDetailTable'].toggleRowSelection(copiedRow, true)
              break
            } else {
              this.handleError('出库数量不能大于1')
              return
            }
          }
        } else if (item.batchNo) { // 条码解析有批号
          let rowsWithBatchNo = rows.filter(o => o.batchNo === item.batchNo) // 表中已有带此批号的行
          if (rowsWithBatchNo.length > 0) {
            for (let row of rowsWithBatchNo) {
              sum2 += row.qty
              if (sum2 >= row.avlQty) {
                this.handleError('"' + row.goodsName + '"' + ' 总出库数超过库存数量')
                return
              }
              if (row.qty >= row.undoneQty) {
                continue
              }
              if (rowsWithBatchNo.filter(o => o.bsId === row.bsId).reduce((acc, cur) => acc + cur.qty, 0) >= row.undoneQty) {
                continue
              }
              if (row.qty >= row.selected.qty) {
                if (rowsWithBatchNo.filter(o => o.bsId === row.bsId).some(o => o.qty < o.selected.qty)) {
                  continue
                }
                let copiedRow = this.handleClkCopyLine(row)
                copiedRow.selected = JSON.parse(JSON.stringify(this.batchNoIdUniqueCodeList[row.goodsId].find((element, index, array) => element.qty === row.undoneQty) || this.batchNoIdUniqueCodeList[row.goodsId][0]))
                copiedRow.price = copiedRow.selected.price
                copiedRow.qty += 1
                rowAdded = true
                this.$refs['requestDetailTable'].toggleRowSelection(copiedRow, true)
                break
              }
              row.qty += 1
              rowAdded = true
              this.$refs['requestDetailTable'].toggleRowSelection(row, true)
              break
            }
          } else {
            for (let row of rows) {
              sum2 += row.qty
              if (sum2 >= row.avlQty) {
                this.handleError('"' + row.goodsName + '"' + ' 总出库数超过库存数量')
                return
              }
              if (row.qty >= row.undoneQty) {
                continue
              }
              if (rows.filter(o => o.bsId === row.bsId).reduce((acc, cur) => acc + cur.qty, 0) >= row.undoneQty) {
                continue
              }
              row.qty += 1
              row.selected = JSON.parse(JSON.stringify(this.batchNoIdUniqueCodeList[row.goodsId].find((element, index, array) => element.qty === row.undoneQty) || this.batchNoIdUniqueCodeList[row.goodsId][0]))
              row.price = row.selected.price
              rowAdded = true
              this.$refs['requestDetailTable'].toggleRowSelection(row, true)
              break
            }
          }
        } else { // 条码解板无批号，无跟踪码
          for (let row of rows) {
            sum3 += row.qty
            if (sum3 >= row.avlQty) {
              this.handleError('"' + row.goodsName + '"' + ' 总出库数超过库存数量')
              return
            }
            if (row.qty >= row.undoneQty) {
              continue
            }
            row.qty += 1
            rowAdded = true
            this.$refs['requestDetailTable'].toggleRowSelection(row, true)
            break
          }
        }
      }
      if (!rowAdded) {
        this.$message.error('未找到条码对应商品。')
      }
    },
    getPurchaseDict () {
      this.getDict('CGMS').then(res => {
        this.CGMSDicts = res
      }).catch(err => {
        this.handleError(err)
      })
    },
    getCKLXDict () {
      this.getDict('CKLX').then(res => {
        this.CKLXDicts = res
      }).catch(err => {
        this.handleError(err)
      })
    },
    getDict (dict) {
      let dicts = {}
      return new Promise((resolve, reject) => {
        this.axios.get(this.baseURL + '/getDict', {
          params: {
            dict
          }
        }).then(res => {
          if (res.data.code === 0) {
            for (let item of res.data.data) {
              dicts[item.value] = item.name
            }
            resolve(dicts)
          } else {
            reject(res.data.msg)
          }
        }).catch(err => {
          reject(err)
        })
      })
    },
    formatUnique (row, column, cellValue) {
      return cellValue !== '3' ? '是' : '否'
    },
    formatDate (row, column, cellValue) {
      let date = new Date(cellValue)
      return date.toLocaleDateString()
    },
    outStockBillData2Post (submit) {
      let data = JSON.parse(JSON.stringify(this.outStockBill))
      data.goodsList = JSON.parse(JSON.stringify(this.multipleSelection))
      for (let goods of data.goodsList) {
        goods.stockTableId = goods.selected.id
        goods.batchId = goods.selected.batchId
        goods.batchNo = goods.selected.batchNo
        goods.uniqueCode = goods.selected.uniqueCode
        delete goods.recAddressId
        delete goods.recAddress
        delete goods.recLinkman
        delete goods.recLinkmanPhone
        delete goods.avlQty
        delete goods.lockQty
        delete goods.outQty
        delete goods.reqQty
        delete goods.undoneQty
        delete goods.selected
        delete goods.sendType
        delete goods.subState
        delete goods.sendQty
      }
      delete data.fillDate
      delete data.filler

      data.billId = this.currentBillId
      if (submit) {
        //data.status = OUTSTOCK_BILL_STATUS.SUBMIT.value
        data.status = OUTSTOCK_BILL_STATUS.CHECKED.value //提交并审核 by gw
      } else {
        data.status = OUTSTOCK_BILL_STATUS.SAVE.value
      }

      data.outStocId = this.currentStock
      data.inDeptId = this.currentDeptId
      data.inDeptName = this.orgsData.find(o => o.id === this.currentDeptId).ename
      return data
    },
    newOutStockBill () {
      let currentRequestBill = this.requestBillList.find(o => o.billId === this.currentBillId)
      return {
        outStockKind: OUTSTOCK_BILL_KIND.REQUEST.value,
        outStockType: currentRequestBill.buyKind,
        outDeptId: this.currentDeptId,
        patientName: '', // 患者住院号
        patient: '', //患者姓名
        patientDeptCode: '',
        patientBedNum: '',
        fillDate: new Date().toLocaleDateString(),
        filler: this.user.ename,
        recAddressId: currentRequestBill.recAddressId,
        recAddress: currentRequestBill.recAddress,
        recLinkman: currentRequestBill.recLinkman,
        recLinkmanPhone: currentRequestBill.recLinkmanPhone,
        goodsList: JSON.parse(JSON.stringify(this.requestBillDetailList.filter(o => {
          return o.avlQty > 0 && o.undoneQty > 0
        })))
      }
    },
    changeBatchId (row) {
      if (row.selected.id === '') {
        return
      }
      row.selected = JSON.parse(JSON.stringify(this.batchNoIdUniqueCodeList[row.goodsId].find(o => o.id === row.selected.id)))
      row.price = row.selected.price
      row.batchId = row.selected.batchId
      row.batchNo = row.selected.batchNo
      row.uniqueCode = row.selected.uniqueCode
      row.certificateCode= row.selected.certificateCode
    },
    combineDuplicateGoods () {
      let goodsLableSet = new Set()
      for (let good of this.outStockBill.goodsList) {
        goodsLableSet.add(good.selected.id)
      }
      if (this.outStockBill.goodsList.length > goodsLableSet.size) {
        let goodsList = []
        for (let goodsLabel of goodsLableSet) {
          let sumQty = this.outStockBill.goodsList.filter(item => item.selected.id === goodsLabel).reduce((acc, cur) => acc + cur.qty, 0)
          let goodsInfo = this.outStockBill.goodsList.find(item => item.selected.id === goodsLabel)
          goodsInfo.qty = sumQty
          goodsList.push(JSON.parse(JSON.stringify(goodsInfo)))
        }
        this.outStockBill.goodsList = goodsList
        this.$refs['requestDetailTable'].clearSelection()
        return true
      } else {
        return false
      }
    },
    mainTableRowClassName ({row, index}) {
      return row.available ? '' : 'row-disabled'
    },
    detailTableRowClassName ({row, index}) {
      return (row.avlQty === 0 && row.undoneQty !== 0) ? 'row-disabled' : row.avlQty < row.undoneQty ? 'row-disabled-purple-nobg' : row.isUnique !== '3' ? 'row-disabled-green-nobg' : ''
    },
    billTableRowClassName ({row, index}) {
      return row.qty < row.reqQty ? 'info-row' : ''
    },
    renderTreeContent (h, { node, data, store }) {
      return data.available ? (<span>{node.label}</span>) : (<span class="text-grey">{node.label}</span>)
    },
    querySearchAsync (query, callback) {
      this.patientDisabled = false
      this.outStockBill.patient = ''
      this.outStockBill.patientDeptCode = ''
      this.outStockBill.patientBedNum = ''

      this.axios.get(this.baseURL + '/getSickerInfo', {
        params: {
          query
        }
      }).then(res => {
        if (res.data.code === 0) {
          let result = JSON.parse(JSON.stringify(res.data.data))
          result.every(item => {
            item.deptName = this.orgsData.find(o => o.id === item.deptCode).ename
          })
          callback(result)
        } else {
          this.handleError(res.data.msg)
        }
      }).catch(err => {
        this.handleError(err)
      })
    },
    handlePatientSelect (item) {
      this.patientDisabled = true
      this.outStockBill.patient = item.patientName
      this.outStockBill.patientDeptCode = item.deptCode
      this.outStockBill.patientBedNum = item.bedNum
    },
  },
}

</script>

<style scoped>
.tree-height {
    height: calc(100% - 54px);
}

.table-style {
    height: calc(50% - 31px);
}

.full-height {
    height: 100%;
}

.inline-display {
  display: inline-block;
}

.data-picker {
  width: 125px;
}

.stock-select {
  margin-bottom:8px;
  width:calc(100% - 67px);
}

.el-form-item__error {
  display: block;
}

.barcode-input {
  width: 60%;
}

.slaver-barcode-input {
  width: 30%;
}

.barcode-input-margin {
  margin: 5px;
}

.text-style {
  padding-left: 10px;
  font-size: 12px;
  height: 25px;
  line-height: 25px;
  padding-top: 2px;
}

.out-of-stock {
  background: url('../../../assets/images/out_of_stock.png') no-repeat;
  background-size: 50px 25px;
  display: inline-block;
  width: 55px;
  height: 0;
  padding-top: 30px;
  overflow: hidden;
  position: absolute;
  top: 1px;
  /* right: -10px; */
}

.part-delivery {
  background: url('../../../assets/images/part_delivery.png') no-repeat;
  background-size: 20px 20px;
  display: inline-block;
  width: 25px;
  height: 0;
  padding-top: 30px;
  overflow: hidden;
  position: absolute;
  top: 4px;
  /* right: -10px; */
}

.all-delivery {
  background: url('../../../assets/images/all_delivery.png') no-repeat;
  background-size: 20px 20px;
  display: inline-block;
  width: 25px;
  height: 0;
  padding-top: 30px;
  overflow: hidden;
  position: absolute;
  top: 4px;
  /* right: -10px; */
}
</style>

<style>
.patient-autocomplete li {
  line-height: normal;
  padding: 7px;
}

.patient-autocomplete li .name {
  text-overflow: ellipsis;
  overflow: hidden;
}

.patient-autocomplete li .info {
  font-size: 12px;
  color: #b4b4b4;
}

.patient-autocomplete li .info {
  color: #ddd;
}
#store-out-requestout .el-table .info-row {
  background: #FF99FF;
}

#store-out-requestout .el-tree {
  border: 1px solid #ebeef5;
}

#store-out-requestout .el-tree-node__expand-icon {
  cursor: pointer;
  width: auto;
  height: auto;
  margin-left: 10px;
  border: none;
  -ms-transform: rotate(0);
  transform: rotate(0);
  transition: transform 0.5s ease-in-out;

  display: inline-block;
  font: normal normal normal 14px/1 FontAwesome !important;
  font-size: inherit;
  text-rendering: auto;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

#store-out-requestout .el-tree-node__expand-icon:before {
  content: "\F07B";
}

#store-out-requestout .el-tree-node__expand-icon.expanded {
  -ms-transform: rotate(360deg);
  transform: rotate(360deg);
}

#store-out-requestout .el-tree-node__expand-icon.expanded:before {
  content: "\F115";
}

#store-out-requestout .el-tree-node__expand-icon.is-leaf {
  margin-left: 0;
}

#store-out-requestout .el-tree-node__expand-icon.is-leaf:before {
  content: "";
}

#store-out-requestout .el-checkbox__label {
  font-size: 12px;
}

#store-out-requestout .text-grey {
  color: grey;
}
</style>
