<template>
    <div id="item-trace">
        <el-row type="flex" justify="center" :gutter="5">
            <el-col :span="5" v-for="item in searchData2" :key="item.label">
                <el-input  placeholder="请输入内容" v-model="item.value">
                    <template slot="prepend">{{item.label}}</template>
                </el-input>
            </el-col>
            <el-col :span="1">
                <el-tooltip  placement="bottom" effect="light" content="搜索">
                    <el-button size="mini" style="color:grey" icon="search" @click="searchTrace"></el-button>
                </el-tooltip>
            </el-col>
        </el-row>
        <el-row type="flex" justify="center" :gutter="5">
            <el-col :span="14">
                <search-input v-for="num in searchNum" :key="num" :index="num" :data="searchData" @delInput="delInput"
                              @doSearch="searchTrace"></search-input>
            </el-col>
            <el-col :span="2">
                <el-tooltip placement="bottom" effect="light" content="增加查询条件">
                    <el-button size="mini" style="color:grey" icon="plus" @click="addSearch"></el-button>
                </el-tooltip>
            </el-col>
        </el-row>
        <div v-if="trkRs">
            <Trace :datas="datas" :item-data="itemData"></Trace>
        </div>
    </div>
</template>

<script>
import Trace from './trace.vue'
import SearchInput from './searchInput.vue'

export default {
  data () {
    return {
      datas: [],
      itemData: {},
      searchData: {
        options: [{
          value: 'sn', // series number
          label: '序列号'
        }, {
          value: 'bn', // batch number
          label: '批号'
        }, {
          value: 'bc', // bar code
          label: '条码'
        }],
        params: [{
          input: '',
          value: ''
        }]
      },
      searchData2: [
        {
          //                        value: 'item_name',
          label: '商品名称',
          value: ''
        }, {
          //                        value: 'iten_spec',
          label: '规格型号',
          value: ''
        }, {
          //                        value: 'item_lic',
          label: '注册证号',
          value: ''
        }
      ],

      trkRs: false,
      loading: true,

      timeout: null
    }
  },
  computed: {
    searchNum: function () {
      return this.searchData.params.length
    }
  },
  methods: {
    validateInput: function (value) {
      return value.some(function (item) {
        return (!item.value || !item.input)
      })
    },
    validateInput2 (value) {
      return value.some(function (item) {
        return item.value
      })
    },
    searchTrace () {
      if (this.validateInput(this.searchData.params) || !this.validateInput2(this.searchData2)) {
        this.$notify.error({
          title: '错误',
          message: '商品名称、规格型号、注册证号必须输入一项，其他项必须输入一项，请重新输入'
        })
      } else {
        clearTimeout(this.timeout)
        this.timeout = setTimeout(() => {
          this.datas = this.getTrackData()
          this.datas[0].sort(function (a, b) {
            return a.opDate < b.opDate ? -1 : 1
          })
          this.itemData = {
            item_img: 'https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=0a5a67e746086e067ea5371963611091/8435e5dde71190efc4056909c41b9d16fcfa606e.jpg',
            // name: this.datas[0][0].itemName,
            // spec: this.datas[0][0].itemSpec,
            // cn: this.datas[0][0].itemLic
            name: '冠状动脉药物涂层支架系统',
            spec: '3.5x23',
            cn: '国械注准20163460822'
          }
          this.trkRs = true
          this.loading = false
        }, 1000 * Math.random())
      }
    },
    addSearch () {
      this.searchData.params.push({
        input: '',
        value: ''
      })
      this.searchNum += 1
    },
    delInput: function (index) {
      this.searchData.params.splice(index - 1, 1)
    },
    getTrackData () {
      return [[
        {
          'id': 1,
          'type': 'SPD要货单',
          'source': 'SPDYH10000002',
          'opDate': '2017-04-04 03:09:00',
          'oper': '张老师',
          'org': '华山北院',
          'itemName': '微型高频针状电极',
          'itemSpec': 'E1175',
          'itemLic': '国食药监械(进)字2013第2250408号',
          'num': 2,
          'unit': '套',
          'bn': null,
          'valide': null,
          'temp': null,
          'comment': '20号送达',
          'invoiceCode': null,
          'invoiceNum': null,
          'invoiceDate': null,
          'sn': null,
          'gs1': null,
          'hibc': null
        },
        {
          'id': 3,
          'type': '上游发货',
          'source': 'QS100000001',
          'opDate': '2017-01-03 00:05:01',
          'oper': '张华',
          'org': '山东瑞安泰医疗技术有限公司',
          'itemName': '微型高频针状电极',
          'itemSpec': 'E1175',
          'itemLic': '国食药监械(进)字2013第2250408号',
          'num': 1,
          'unit': '套',
          'bn': 'QPh0001',
          'valide': '2020-04-01',
          'temp': 20,
          'comment': '发运合格',
          'invoiceCode': null,
          'invoiceNum': null,
          'invoiceDate': null,
          'sn': 'qswy0000002',
          'gs1': '(01)5555(10)6666(17)7777',
          'hibc': '+H3333$$4444'
        },
        {
          'id': 5,
          'type': 'ERP入库单',
          'source': 'ERPRK10000003',
          'opDate': '2017-04-01 23:05:01',
          'oper': '李文',
          'org': '国药集团上海医疗器械有限公司',
          'itemName': '微型高频针状电极',
          'itemSpec': 'E1175',
          'itemLic': '国食药监械(进)字2013第2250408号',
          'num': 1,
          'unit': '套',
          'bn': 'QPh0001',
          'valide': '2020-04-01',
          'temp': 20,
          'comment': '验收合格',
          'invoiceCode': '2222222222',
          'invoiceNum': '12121212',
          'invoiceDate': '2017-04-03',
          'sn': 'qswy0000002',
          'gs1': '(01)5555(10)6666(17)7777',
          'hibc': '+H3333$$4444'
        },
        {
          'id': 7,
          'type': 'ERP出库单',
          'source': 'ERPCK10000002',
          'opDate': '2017-04-06 01:05:01',
          'oper': '王强',
          'org': '国药集团上海医疗器械有限公司',
          'itemName': '微型高频针状电极',
          'itemSpec': 'E1175',
          'itemLic': '国食药监械(进)字2013第2250408号',
          'num': 1,
          'unit': '套',
          'bn': 'QPh0001',
          'valide': '2020-04-01',
          'temp': 20,
          'comment': '出库复核合格',
          'invoiceCode': '3333333333',
          'invoiceNum': '22112211',
          'invoiceDate': '2017-04-06',
          'sn': 'qswy0000002',
          'gs1': '(01)5555(10)6666(17)7777',
          'hibc': '+H3333$$4444'
        },
        {
          'id': 9,
          'type': 'SPD收货单',
          'source': 'SPDSH10000002',
          'opDate': '2017-04-19 03:16:31',
          'oper': '张老师',
          'org': '华山北院',
          'itemName': '微型高频针状电极',
          'itemSpec': 'E1175',
          'itemLic': '国食药监械(进)字2013第2250408号',
          'num': 1,
          'unit': '套',
          'bn': 'QPh0001',
          'valide': '2020-04-01',
          'temp': 20,
          'comment': '设备科验收合格',
          'invoiceCode': null,
          'invoiceNum': null,
          'invoiceDate': null,
          'sn': 'qswy0000002',
          'gs1': '(01)5555(10)6666(17)7777',
          'hibc': '+H3333$$4444'
        },
        {
          'id': 11,
          'type': 'SPD领用单',
          'source': 'SPDLY10000002',
          'opDate': '2017-04-19 03:21:31',
          'oper': '王护士',
          'org': '华山北院护士科',
          'itemName': '微型高频针状电极',
          'itemSpec': 'E1175',
          'itemLic': '国食药监械(进)字2013第2250408号',
          'num': 1,
          'unit': '套',
          'bn': 'QPh0001',
          'valide': '2020-04-01',
          'temp': 20,
          'comment': '科室入库合格',
          'invoiceCode': null,
          'invoiceNum': null,
          'invoiceDate': null,
          'sn': 'qswy0000002',
          'gs1': '(01)5555(10)6666(17)7777',
          'hibc': '+H3333$$4444'
        },
        {
          'id': 13,
          'type': 'SPD使用单',
          'source': 'SPDSY10000003',
          'opDate': '2017-04-21 07:26:35',
          'oper': '曹患者',
          'org': null,
          'itemName': '微型高频针状电极',
          'itemSpec': 'E1175',
          'itemLic': '国食药监械(进)字2013第2250408号',
          'num': 1,
          'unit': '套',
          'bn': 'QPh0001',
          'valide': '2020-04-01',
          'temp': 20,
          'comment': '患者正常使用',
          'invoiceCode': null,
          'invoiceNum': null,
          'invoiceDate': null,
          'sn': 'qswy0000002',
          'gs1': '(01)5555(10)6666(17)7777',
          'hibc': '+H3333$$4444'
        }
      ]]
    }
  },
  components: {
    'Trace': Trace,
    'searchInput': SearchInput
    // "Echart": Echart
  }
}
</script>

<style scoped>
    .el-input {
        margin-bottom: 5px;
    }

    .el-button--mini {
        font-size: 14px;
    }
</style>
