<template>
    <div id="provGoodsHoslist" class="fullheight">
        <el-row  class="fullheight">
            <el-col :span="4" class="fullheight " >
                <div style="margin-bottom: 10px">
                    <el-input v-model="hosFilters.hosName" placeholder="请输入医院名称"   icon="search" @keyup.enter.native="handleHosSearch" :on-icon-click="handleHosSearch"   ></el-input>
                </div>
                <el-table :show-header="false" class="tableheight" :data="hosList" @row-click="selectHosClick" style="width: 100%;margin-top: 5px;" highlight-current-row >
                    <el-table-column prop="hosName" label="医院" show-overflow-tooltip>
                    </el-table-column>
                </el-table>
                <el-pagination @size-change="handleHosSizeChange" @current-change="handleHosCurrentChange" :current-page="hosPage" :page-sizes="[30, 100, 200, 400]" :page-size="hosPageSize" layout="sizes,prev,next" :total="hosTotal"  small>
			          </el-pagination>
            </el-col>
            <el-col :span="20" class="fullheight" style="padding-left: 5px;">
                <div style="margin-bottom: 7px;">
                   <el-button  type="primary"  @click="handleImport">批量导入</el-button>
                   <el-button type="primary" @click="multDel()" >批量删除</el-button>
                </div>
                <el-table highlight-current-row  class="tableheight"
                         :data="provList" style="width: 100%"
                          ref="multipleTable4Main"
                          row-key='id'  @selection-change="handleSelectionChange4Main"
                          v-loading="goodsLoading" border
                          :default-sort="{prop: 'provGoodsGoodsName', order: 'descending'}">
                    <el-table-column  type="selection" :reserve-selection="true" width="55"> </el-table-column>
					<el-table-column prop="code" label="供应商编号" width="110" header-align="center" sortable>
					</el-table-column>
					<el-table-column prop="cname" label="供应商名称" min-width="180" header-align="center" sortable>
					</el-table-column>
					<el-table-column prop="shortPinyin" label="拼音码" width="120" header-align="center" sortable>
					</el-table-column>
					<el-table-column prop="linkman" width="90" label="联系人" header-align="center">
					</el-table-column>
                    <el-table-column prop="legal" width="90" label="法人代表" header-align="center">
					</el-table-column>
					<el-table-column prop="contactWay" label="联系方式" width="120" header-align="center">
					</el-table-column>
					<el-table-column label="是否查看" width="150">
						<template slot-scope="scope">
							<el-switch v-model="scope.row.isHosView" active-color="#13ce66" inactive-color="#ff4949" active-text="是" inactive-text="否" active-value="1" inactive-value="0"  @change="hosViewChange(scope.row)">
							</el-switch>
						</template>
					</el-table-column>
                    <!--<el-table-column type="selection" width="40"></el-table-column>-->
                </el-table>
                <el-pagination @size-change="handleProvSizeChange" @current-change="handleProvCurrentChange" :current-page="provPage" :page-sizes="[10, 100, 200, 400]" :page-size="provPageSize" layout="total, sizes, prev, pager, next" :total="provTotal" style="float:right;" small>
			   </el-pagination>
            </el-col>
        </el-row>

        <!--<el-pagination @current-change="handleCurrentChange" :current-page="currentPage" layout="total, prev, pager, next, jumper" :total="total">
                                                                                                                                                                                                                                                                                                                                                                                                                                                            </el-pagination>-->
        <el-dialog :title="provImportTitle" close="onClose" :visible.sync="provImportVisible" :close-on-click-modal="false">
            <el-form :inline="true" label-width="80px">
                            <el-form-item label="供应商名称">
                                <el-input v-model="provNotRelatedFilters.cname" placeholder="输入供应商名称" ></el-input>
                            </el-form-item>
                            <el-button type="primary" v-on:click="getProvList(1)" >查询</el-button>

                        </el-form>
                         <template>
                                <el-table
                                    ref="multipleTable" :data="provNotRelatedList"  border tooltip-effect="dark" row-key='id'
                                    style="width: 100%" @selection-change="handleSelectionChange">
										<el-table-column  type="selection" :reserve-selection="true" width="55"> </el-table-column>
										<el-table-column prop="code" label="供应商编号" width="110" header-align="center" sortable>
										</el-table-column>
										<el-table-column prop="cname" label="供应名称" min-width="180" header-align="center" sortable>
										</el-table-column>
										<el-table-column prop="shortPinyin" label="拼音码" width="120" header-align="center" sortable>
										</el-table-column>
										<el-table-column prop="linkman" width="90" label="联系人" header-align="center">
										</el-table-column>
				                        <el-table-column prop="legal" width="90" label="法人代表" header-align="center">
										</el-table-column>
										<el-table-column prop="contactWay" label="联系方式" width="120" header-align="center">
										</el-table-column>
                                </el-table>
                                <el-pagination @size-change="handleNotRelatedProvSizeChange" @current-change="handleNotRelatedProvCurrentChange" :current-page="notRelatedProvPage" :page-sizes="[10, 100, 200, 400]" :page-size="notRelatedProvPageSize" layout="total, sizes, prev, pager, next" :total="notRelatedProvTotal" style="float:right;" small>
			                      </el-pagination>
                                <div style="margin-top: 20px">

                                    <el-button @click="toggleSelection()">取消选择</el-button>
                                    <el-button @click="provImprotSubmit()">确定</el-button>
                                </div>
                         </template>
            </el-dialog>
    </div>
</template>

<script>

export default {
  data () {
    return {
      hosFilters: {
        hosName: ''
      },
      hosGoodsFilters: {
        goodsName: ''
      },
      willShowFlag: false,
      provNotRelatedFilters: {cname: '',hosId:''},

      hosTotal: 0,
      hosPage: 1,
      hosPageSize: 10,

      provTotal: 0,
      provPage: 1,
      provPageSize: 10,
      
      notRelatedProvTotal: 0,
      notRelatedProvPage:1,
      notRelatedProvPageSize: 10,

      hosList: [],
      provList: [],
      loading: false,
      goodsLoading: false,
      editLoading: false,
      dialogVisible: false,
      provImportVisible: false,
      modelTitle: '',
      provImportTitle: '',

      mfrs: [], // 生产厂家
      mfr: {id: '', mfrsName: ''},

      currentHos: {}, // 当前选择的医院
      currentHosId: '', // 当前选择的医院ID
      provNotRelatedList: [],
      currentProvGood: [],
      multipleSelection: [],
      multipleSelection4Main: [],
      SL: [],
      taxRate: '',
      currentRow: {}

    }
  },
  methods: {
    hosGoodsSelectHanlder () {
      this.willShowFlag = true
      this.hosGoodsFilters.goodsName = ''
      this.getHosGoodsList(1)
    },
    handleCurrentChange4Row (val) {
      if (val != null) {
        this.currentRow = val
        this.good.hisCode = val.code
        this.good.hisUnit = val.unit
        this.good.hisName = val.goodsName
      }
    },
    getHosGoodsList: function (pIndex) {
      this.hosPage = pIndex
      var param = { orderBy: '', queryObject: {hosId: this.currentHosId, provId: '', goodsName: this.hosGoodsFilters.goodsName}, page: this.hosGoodsPage, rows: this.hosGoodsPageSize }
      this.loading = true
      this.axios.post('/spdHDIService/myGoods/hosGoods/getHosGoodsByHos', param).then(res => {
        this.loading = false
        if (res.data.code == '0') {
          this.hosGoods = res.data.data.data
          this.hosGoodsTotal = res.data.data.total
        } else {
          this.$message.error(res.data.msg)
        }
      }, _ => {
        this.loading = false
      })
    },
    editSubmit: function () {
      //   this.good.taxRate = this.taxRate;
      this.good.taxRate = this.taxRate ? this.taxRate : 0.17
      this.good.salePrice = this.good.salePrice ? this.good.salePrice : 0
      this.good.purPrice = this.good.purPrice ? this.good.purPrice : 0

      var _this = this
      var form = this.$refs.editForm
      const h = this.$createElement
      var arr = []
      form.validate((valid) => {
        if (valid) {
          this.editLoading = true

          let serviceUrl = '/spdHDIService/myGoods/provGoodsHos/submissionCode'
          this.axios.post(serviceUrl, this.good).then(res => {
            this.editLoading = false
            if (res.data.code == '0') {
              this.$message({ message: '操作成功！', type: 'success' })
              this.willShowFlag = false
              this.dialogVisible = false

              this.getProvListByHost(this.currentHosId, this.goodsPage)
            } else if (res.data.code == '-122') {
              let err = res.data.validateErrors.fieldErrors[0]
              if (err.field == 'hisCode') {
                this.errors.hisCodeErr += err.message
                arr.push(h('span', null, err.message))
              }
              if (err.field == 'hisName') {
                this.errors.hisNameErr += err.message
                arr.push(h('span', null, err.message))
              }
              if (err.field == 'hisUnit') {
                this.errors.hisUnitErr += err.message
                arr.push(h('span', null, err.message))
              }
              if (err.field == 'salePrice') {
                this.errors.salePriceErr += err.message
                arr.push(h('span', null, err.message))
              }
              if (err.field == 'unitRate') {
                this.errors.unitRateErr += err.message
                arr.push(h('span', null, err.message))
              }
              _this.$msgbox({title: '错误', type: 'error', message: h('p', null, arr)})
            } else {
              this.$message.error('操作失败！')
            }
          }, _ => {
            this.editLoading = false
          })
        } else {
          var fields = form.fields
          var msg = ''
          fields.forEach(f => {
            if (f.validateState == 'error') {
              msg += '</br>' + f.validateMessage
              if (arr.length > 0) arr.push(h('br', null, null))
              arr.push(h('span', null, f.validateMessage))
            }
          })
          _this.$msgbox({title: '错误', type: 'error', message: h('p', null, arr)})
        }
      })
    },

    handleImport () {
      if (this.currentHosId.length <= 0) {
        this.$message.error('请先选择医院!')
        return
      }

      this.provImportVisible = true
      this.provImportTitle = '添加供应商到' + this.currentHos.hosName
      this.getProvList(1)
    },

    hosViewChange (item) {
        this.loading = true
        this.axios.post('/spdHDIService/myInfo/provHosInfo/setHosView', item).then(res => {
          this.loading = false
          if (res.data.code == '0') {
            this.loading = false
            this.$message({ message: '操作成功！', type: 'success' })
          } else {
            this.$message.error(res.data.msg)
          }
						 this.getProvListByHost(this.currentHosId, this.goodsPage)
        }, _ => {
          this.loading = false
        })
    },
    multDel () {
      if (this.multipleSelection4Main.length <= 0) {
        this.$message.error('至少选择一条数据！')
        return
      }
      this.$confirm('确认删除选中记录吗?', '提示', {
      }).then(() => {
        this.loading = true
        this.axios.post('/spdHDIService/myInfo/provHosInfo/deleteBatch', this.multipleSelection4Main).then(res => {
          this.loading = false
          if (res.data.code == '0') {
            this.loading = false
            this.$message({ message: '操作成功！', type: 'success' })
          } else {
            this.$message.error(res.data.msg)
          }
          this.getProvListByHost(this.currentHosId, this.goodsPage)
          this.$refs.multipleTable4Main.clearSelection()
        }, _ => {
          this.loading = false
        })
      }).catch(() => {

      })
    },

    handleHosSearch () {
      this.getHosListsByUnit(this.page)
    },
    handleHosCurrentChange (val) {
      this.hosPage = val
      this.getHosListsByUnit(this.hosPage)
    },
    handleHosSizeChange (val) {
      this.hosPageSize = val
      this.getHosListsByUnit(this.hosPageSize)
    },

    handleProvSizeChange (val) {
      this.provPageSize = val
      this.getProvListByHost(this.currentHosId,this.provPageSize)
    },
    handleProvCurrentChange (val) {
      this.provPage = val
      this.getProvListByHost(this.currentHosId,this.provPage)
    },

    handleNotRelatedProvSizeChange (val) {
      this.notRelatedProvPageSize = val;
      this.getProvList(this.notRelatedProvPageSize)
    },
    handleNotRelatedProvCurrentChange (val) {
      this.notRelatedProvPage = val;
      this.getProvList(this.notRelatedProvPage)
    },

    getHosListsByUnit: function (pIndex) {
      this.loading = true
      this.hosPage = pIndex
      var param = { orderBy: '', queryObject: {hosName: this.hosFilters.hosName, status: 2}, page: this.hosPage, rows: this.hosPageSize }
      this.axios.post('/spdHDIService/myInfo/provHosInfo/provHosInfoVoList', param).then(res => {
        this.loading = false
        if (res.data.code == '0') {
          this.hosList = res.data.data.data
          this.hosTotal = res.data.data.total
          //  this.page = res.data.data.pageNum;
          if (this.hosList) {
            this.currentHosId = this.hosList[0].hosId
            this.currentHos = this.hosList[0]
            this.getProvListByHost(this.currentHosId, 1)
          }
        } else {
          this.$message.error(res.data.msg)
        }
      }, _ => {
        this.loading = false
      })
    },
    selectHosClick: function (row) {
      if (this.currentHos.id === row.hosId) {
        return
      }
      this.currentHos = row
      this.currentHosId = row.hosId
      this.provNotRelatedFilters.hosId = row.hosId
      this.getProvListByHost(row.hosId, 1)
    },
    getProvListByHost: function (hosId, pIndex) {
      this.provPage = pIndex
      this.loading = true
      var param = { orderBy: '', queryObject: {hosId: hosId}, page: this.provPage, rows: this.provPageSize }
      this.axios.post('/spdHDIService/myInfo/provHosInfo/getHosProvCompanyInfoVoList', param).then(res => {
        this.loading = false
        if (res.data.code == '0') {
          this.provList = res.data.data.data
          this.provTotal = res.data.data.total
        } else {
          this.$message.error(res.data.msg)
        }
      }, _ => {
        this.loading = false
      })
    },
    selectProvGoodClick: function (row) {
      this.currentProvGood = row
      this.good.goodsName = row.goodsName
    },

    toggleSelection (rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },

    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    handleSelectionChange4Main (val) {
      this.multipleSelection4Main = val
    },

    getProvList: function (pIndex) {
		this.notRelatedProvPage = pIndex;
		var param = { orderBy: "", queryObject: { cname: this.provNotRelatedFilters.cname,id:this.provNotRelatedFilters.hosId}, page: this.notRelatedProvPage, rows: this.notRelatedProvPageSize };
		this.loading = true;
		this.axios.post('/spdHDIService/myInfo/provHosInfo/getNotRelatedCompanys', param).then(res => {
		    this.loading = false;
		    if (res.data.code == "0") {
		        this.provNotRelatedList = res.data.data.data;
		        this.notRelatedProvTotal = res.data.data.total;
		    }
		    else{
		        this.$message.error( res.data.msg);
		    }
		}, err => {
		    this.loading = false;
		});
    },
    provImprotSubmit: function () {
      if (this.multipleSelection.length <= 0) {
        this.$message.error('至少选择一条数据！')
        return
      }
      this.axios.post('/spdHDIService/myInfo/provHosInfo/insertBatch?hosId=' + this.currentHosId, this.multipleSelection).then(res => {
        this.loading = false
        if (res.data.code == '0') {
          this.provImportVisible = false
          this.getProvListByHost(this.currentHosId, 1)
        } else {
          this.$message.error(res.data.msg)
        }
        this.$refs.multipleTable.clearSelection()
      }, _ => {
        this.loading = false
      })
    },
    // 获取产家 供选择使用
    getSL: function () {
      this.loading = true
      this.axios.post('/platformService/sys/dict/getDictValueByDictEname', {dictName: 'SL'}).then(res => {
        this.loading = false
        if (res.data.code == '0') {
          this.SL = res.data.data
        } else {
          this.$message.error(res.data.msg)
        }
      }, _ => {
        this.loading = false
      })
    }

  },

  mounted () {
    this.getHosListsByUnit(1)
    this.getSL()
  }
}
</script>

<style scoped>
    .line {
        text-align: center;
    }

    .el-button {
        margin: 0;
    }

    .el-pagination {
        float: right;
    }

    .fullheight {
        height: calc(100% - 0px);
    }

    .splitborder {
        border-right: rgba(0, 70, 255, 0.6) 1px dashed;
    }

    .tableheight {
        height: calc(100% - 95px);
    }
</style>
