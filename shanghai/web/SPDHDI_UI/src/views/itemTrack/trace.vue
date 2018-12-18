<template>
    <div id="trace">
        <Echart v-if="showModal" @close="showModal = false"></Echart>
        <el-row>
            <el-col :span="6">
                <div style="text-align:center">
                    <img :src="itemData.item_img" height="230px" class="img-box"/>
                </div>
            </el-col>
            <el-col :span="18">
                <el-row>
                    <el-col :span="12" style="line-height:25px">
                        商品名称：{{itemData.name}}
                        <br/>商品规格：{{itemData.spec}}
                        <br/>注册证号：{{itemData.cn}}
                    </el-col>
                    <el-col :span="12"style="line-height:25px">
                        <br/>
                        <a :href="item_data_url" target="_blank">药监信息</a>
                    </el-col>
                </el-row>
                <el-row class="rowSpace" type="flex" justify="left">
                    <el-steps space="50%" :active="4">
                        <el-step title="山东瑞安泰医疗技术有限公司" description="生产商">
                            <i slot="icon" class="fa fa-industry" aria-hidden="true"></i>
                        </el-step>
                        <el-step title="国药器械" description="经销商1">
                            <i slot="icon" class="fa fa-shopping-cart" aria-hidden="true"></i>
                        </el-step>
                        <el-step title="国药器械上海分公司" description="经销商2">
                            <i slot="icon" class="fa fa-shopping-cart" aria-hidden="true"></i>
                        </el-step>
                        <el-step title="华山北院" description="医院">
                            <i slot="icon" class="fa fa-medkit" aria-hidden="true"></i>
                        </el-step>
                    </el-steps>
                </el-row>
                <el-row class="rowSpace">
                    <el-col :span="3">
                        <el-tooltip placement="right" effect="light">
                            <div slot="content">发票代码：173001710011
                                <br/> 发票号码：02160767
                                <br/> 发票日期：2017/10/22
                                <br/>
                                <a href="//s9.rr.itc.cn/r/wapChange/20168_31_21/a7owhp6426694651708.jpeg"
                                target="_blank">显示发票图片</a>
                            </div>
                            <img class="img-box" width="100px" src="//s9.rr.itc.cn/r/wapChange/20168_31_21/a7owhp6426694651708.jpeg"/>
                        </el-tooltip>
                    </el-col>
                    <el-col :span="3" :offset="10">
                        <el-tooltip placement="right" effect="light">
                            <div slot="content">发票代码：2222
                                <br/> 发票号码：2222
                                <br/> 发票日期：2222
                                <br/>
                                <a href="//s9.rr.itc.cn/r/wapChange/20168_31_21/a7owhp6426694651708.jpeg"
                                target="_blank">显示发票图片</a>
                            </div>
                            <img class="img-box" width="100px" src="//s9.rr.itc.cn/r/wapChange/20168_31_21/a7owhp6426694651708.jpeg"/>
                        </el-tooltip>
                    </el-col>
                    <el-col :span="3" :offset="3">
                        <el-tooltip placement="top" effect="light">
                            <div slot="content" style="color:green">
                                国药集团已验两票
                            </div>
                            <img style="margin-top:26px;position:relative" src="./assets/valide.png"/>
                        </el-tooltip>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
        <el-row type="flex" justify="center" class="order-track">
            <el-col :span="22">
                <el-collapse v-model="activePanels" @change="handleChange">
                    <el-collapse-item v-for="(traces,index) in datas" :key="traces" :title="'序列号：'+traces[1].sn"
                                      :name="index" class="track-list">
                        <ul>
                            <li v-for="(value, index) in traces" :class="{first : index==0}">
                                <i class="node-icon"></i>
                                <span style="margin-right:10px;">{{value.opDate}}</span>
                                <span>{{value.type | removeEng}}</span>
                                <!--<br/>-->
                                <!--<span>{{value.source}}</span>-->
                                <span v-if="value.org">【{{value.org}}】</span>
                                <span>【{{value.oper}}】</span>
                                <span>{{value.comment}}</span>
                                <span>{{value.num}}{{value.unit}}</span>
                                <span v-if="value.bn">批号：{{value.bn}}</span>
                                <span v-if="value.valide">有效期：{{value.valide}}</span>
                                <span v-if="value.temp">温度：{{value.temp}}</span>
                                <!--<span v-if="value.sn">唯一码：{{value.sn}}</span>-->
                                <!--<span v-if="value.gs1">GS1条码：{{value.gs1}}</span>-->
                                <!--<span v-if="value.hibc">HIBC条码：{{value.hibc}}</span>-->
                                <!--<span>{{value.opDate}}</span>-->
                                <span v-if="value.type == '上游发货'">
                                    <br/>&nbsp</span>
                                <div v-if="value.invoiceCode" style="float:right">
                                    <el-button id="show-modal" type="text" size="mini" @click="showModal = true">
                                        <i class="fa fa-thermometer-half"></i> 显示温度
                                    </el-button>
                                </div>
                                <div v-if="value.type == '上游发货'" style="float:right">
                                    <el-tooltip placement="left" effect="light">
                                        <div slot="content">
                                            <a :href="importer_img" target="_blank">
                                                <img width="400px" :src="importer_img"/>
                                            </a>
                                        </div>
                                        <el-button type="text" size="mini" style="cursor: default">
                                            <i class="fa fa-picture-o"></i> 进口货物报关单
                                        </el-button>
                                    </el-tooltip>
                                    <el-tooltip placement="left" effect="light">
                                        <div slot="content">
                                            <a :href="invoice_img" target="_blank">
                                                <img width="400px" :src="invoice_img"/>
                                            </a>
                                        </div>
                                        <el-button type="text" size="mini" style="cursor: default">
                                            <i class="fa fa-picture-o"></i> 随货同行单
                                        </el-button>
                                    </el-tooltip>
                                </div>
                            </li>
                        </ul>
                    </el-collapse-item>
                </el-collapse>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import Echart from './echart.vue'

export default {
  props: ['datas', 'itemData'],
  data () {
    return {
      processPadding: 180,
      showModal: false,
      activePanels: [],
      importer_img: '//cmic02.guoliangwu.com/img/importer.png',
      invoice_img: '//cmic02.guoliangwu.com/img/invoice.png',
      item_data_url: '//qy1.sfda.gov.cn/datasearch/face3/content.jsp?tableId=27&tableName=TABLE27&tableView=%E8%BF%9B%E5%8F%A3%E5%99%A8%E6%A2%B0&Id=10300'
    }
  },
  methods: {
    handleChange: function (params) {

    }
  },
  filters: {
    removeEng: function (value) {
      if (!value) return ''
      value = value.toString()
      return value.replace(/[a-zA-Z]/g, '')
    }
  },
  components: {
    'Echart': Echart
  }
}
</script>

<style lang="scss" scoped>
    .img-box{
        border: 1px solid grey;
        box-shadow: 5px 5px 10px 0 #000000;
    }

    .order-track {
        position: relative;

        .track-list {
            padding-left: 5px;
            overflow-y: auto;
            position: relative;

            li {
                position: relative;
                padding: 9px 0 0 15px;
                line-height: 22px;
                border-left: 1px solid #d9d9d9;
                color: #333;

                .node-icon {
                    position: absolute;
                    left: -6px;
                    top: 15px;
                    width: 11px;
                    height: 11px;
                    background: url('//misc.360buyimg.com/user/order/0.0.1/css/i/order-icons.png') -21px -72px #fff no-repeat;
                }
            }

            li.first {
                padding-top: 0;

                .node-icon {
                    top: 0px;
                    height: 20px;
                    background-position: -21px -66px;
                }
            }

            li:last-child {
                .node-icon {
                    background-position: 0 -72px;
                }
            }
        }
    }

    ul {
        list-style-type: none;
    }

    li {
        font-family: 'Microsoft YaHei', 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
        color: grey; // margin-bottom: 28px;
    }

    li:last-child {
        margin: 0;
    }

    li span {
        margin: 0 2.5px;
    }

    .rowSpace {
        margin: 5px 0;
    }
</style>
