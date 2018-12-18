<template>
  <div id="view-jxsqs" class="full-height overflow">
    <transition name="el-fade-in-linear">
      <div v-show="!bShowTrackingChain" style="height: 100%;">
        <el-row class="tool-bar">
          <el-input v-model="filter.input" class="input-box" placeholder="请输入名称或规格"></el-input>
          <el-select v-model="filter.mfrsId" :loading="mfrsSearchLoading" :remote-method="querySearchMfrsAsync" placeholder="请搜索生产厂家" clearable filterable remote>
            <el-option v-for="item in mfrsArray" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
          <el-select v-if="!bIsHos" v-model="filter.hosId" :loading="hosSearchLoading" :remote-method="querySearchHosAsync" placeholder="请搜索适用医院" clearable filterable remote>
            <el-option v-for="item in hosArray" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
          <el-button @click="listGoodsInfo(true)" icon="el-icon-search">搜索</el-button>
          <div style="float: right">
            <el-tooltip seffect="dark" content="选择医院下载对应Excel" placement="top">
              <div>
                <el-button v-if="!bIsHos" :disabled="filter.hosId===''" @click="getExcel()" icon="el-icon-download" type="primary" style="margin-right:10px;">下载Excel</el-button>
              </div>
            </el-tooltip>
          </div>
        </el-row>
        <el-row class="table-class">
          <el-table :data="goodsTableData" style="height: 100%;" v-loading="tableLoading" height="css" border fit>
            <el-table-column type="index" width="40" align="center" label="序号">
            </el-table-column>
            <el-table-column prop="goodsName" label="产品名称" width="180">
            </el-table-column>
            <el-table-column prop="goodsGg" label="产品规格" width="180">
            </el-table-column>
            <el-table-column prop="certificateCode" label="注册证编号">
            </el-table-column>
            <el-table-column v-if="bIsHos" prop="provName" label="供应商">
            </el-table-column>
            <el-table-column prop="mfrsName" label="厂家">
            </el-table-column>
            <el-table-column label="操作" width="90" fixed="right" align="center">
              <template slot-scope="scope">
                <el-button :disabled="!scope.row.trackable" type="success" size="mini" icon="el-icon-view" @click="viewJxsqsChain(scope.row)">授权关系</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-row>
        <el-row>
          <el-pagination :current-page="pageInfo.page" :page-size="pageInfo.size" :page-sizes="[30, 50, 100]" :total="pageInfo.total" @current-change="handlePageChange" @size-change="handlePageSizeChange" layout="total, sizes, prev, pager, next" style="float:right;" small>
          </el-pagination>
        </el-row>
      </div>
    </transition>

    <transition name="el-fade-in-linear">
      <div v-show="bShowTrackingChain">
        <div class="goods-info">
          <dl>
            <dt>产品信息：</dt>
            <dd>名称：{{ curGoods.goodsName }}</dd>
            <dd>规格：{{ curGoods.goodsGg }}</dd>
            <dd>注册证编号：{{ curGoods. certificateCode }}</dd>
            <dd>生产厂家：{{ curGoods.mfrsName }}</dd>
          </dl>
          <dl v-if="!bIsHos">
            <dt>授权书适用医院：</dt>
            <dd v-for="(chain, key) in trackingChainList" :key="key">
              授权书{{ key + 1 }}：
              <el-tag class="tag" v-for="(item, index) in applyHos[chain[0].sqsId]" :key="index" :type="tagType(index+key)">{{ item }}</el-tag>
            </dd>
          </dl>
          <dl v-else>
            <dt>入院供应商：</dt>
            <dd><el-tag class="tag" v-for="(item, index) in Object.values(applyHos)" :key="index" :type="tagType(index)">{{ item }}</el-tag></dd>
          </dl>
        </div>
        <div class="chain-list">
          <div v-for="(chain, key) in trackingChainList" :key="key" class="steps">
            <div class="cert-title">
              授权书{{ key + 1 }}
            </div>
            <div v-for="(item, index) in chain" :key="item.id" class="step" style="flex-basis:50%;">
              <div class="head">
                <div class="line">
                  <i class="inner"></i>
                </div>
                <i class="arrow"/>
                <div class="icon">
                  <div class="inner">
                    {{ index+1 }}
                  </div>
                </div>
              </div>
              <div class="main">
                <div class="title">
                  {{ item.provName || "未知" }}
                </div>
                <div class="description">
                  授权单位：{{ item.chiefName }}<br/>
                  授权范围：{{ item.saleArea }}<br/>
                  效期：<span :class="validExpdtDate(item)">{{ item.expdtBeginDate }} - {{ item.isValiding==='1'?'长期有效':item.expdtEndDate }}</span><br/>
                  授权书附件：<a v-if="item.filePath" target="_blank" :href="'/upload/'+item.filePath">点击查看</a>
                </div>
              </div>
            </div>
            <div class="step">
              <div class="head">
                <div class="icon">
                  <div class="inner">
                    {{ chain.length+1 }}
                  </div>
                </div>
              </div>
              <div class="main">
                <div class="title">
                  {{ chain[chain.length-1].isMfrs==='1'?'生产商：':'' }}{{ chain[chain.length-1].chiefName || "未知" }}
                </div>
                <div class="description" style="color:red">
                  <div v-if="chain[chain.length-1].isMfrs!=='1'" >
                    顶级授权单位非生产厂家
                  </div>
                  <div v-else>
                    <div v-if="chain[chain.length-1].chiefName!==curGoods.mfrsName">
                      顶级授权单位与产品登记生产厂家不符
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <el-button style="margin-top:10px;" type="primary" icon="el-icon-arrow-left" @click="bShowTrackingChain = false">返回</el-button>
      </div>
    </transition>
  </div>
</template>
<script>
import moment from "moment";
import cloneDeep from "lodash/cloneDeep";
import debounce from "throttle-debounce/debounce";
import { companyKind } from "../../common/js/constance";
export default {
  data() {
    return {
      BASE_URL: "/spdHDIService/viewJxsqs",
      bShowTrackingChain: false,
      bIsHos: false,
      mfrsSearchLoading: false,
      hosSearchLoading: false,
      tableLoading: false,
      filter: {
        input: "",
        mfrsId: "",
        hosId: ""
      },
      pageInfo: {
        page: 1,
        size: 30,
        total: 0
      },
      mfrsArray: [],
      hosArray: [],
      goodsTableData: [],
      trackingChainList: [],
      curGoods: {},
      applyHos: {}
    };
  },
  mounted() {
    this.bIsHos = this.user.corpKind === companyKind.HOS;
    this.listGoodsInfo();
  },
  methods: {
    handlePageSizeChange(val) {
      this.pageInfo.size = val;
      this.listGoodsInfo();
    },
    handlePageChange(val) {
      this.pageInfo.page = val;
      this.listGoodsInfo();
    },
    querySearchMfrsAsync: debounce(800, function(queryString, callback) {
      if (queryString && queryString.length > 2) {
        this.mfrsSearchLoading = true;
        this.axios
          .get(this.BASE_URL + "/getMfrs/" + queryString)
          .then(res => {
            if (res.data.code === 0) {
              this.mfrsArray = res.data.data;
            } else {
              this.$message.error(res.data.msg);
            }
          })
          .catch(err => {
            this.$message.error(err);
          })
          .finally(_ => {
            this.mfrsSearchLoading = false;
          });
      } else {
        this.$message.error("搜索字符必须大于等于3个");
      }
    }),
    querySearchHosAsync: debounce(800, function(queryString, callback) {
      if (queryString && queryString.length > 2) {
        this.hosSearchLoading = true;
        this.axios
          .get(this.BASE_URL + "/getHos/" + queryString)
          .then(res => {
            if (res.data.code === 0) {
              this.hosArray = res.data.data;
            } else {
              this.$message.error(res.data.msg);
            }
          })
          .catch(err => {
            this.$message.error(err);
          })
          .finally(_ => {
            this.hosSearchLoading = false;
          });
      } else {
        this.$message.error("搜索字符必须大于等于3个");
      }
    }),
    listGoodsInfo(newSearch = false) {
      if (newSearch) {
        this.pageInfo.page = 1;
      }
      let params = {
        input: this.filter.input,
        mfrsId: this.filter.mfrsId,
        page: this.pageInfo.page,
        size: this.pageInfo.size
      };
      if (!this.bIsHos) {
        params.hosId = this.filter.hosId;
      }
      this.tableLoading = true;
      this.axios
        .get(this.BASE_URL + "/listGoodsInfo", { params })
        .then(res => {
          if (res.data.code === 0) {
            this.goodsTableData = res.data.data.data;
            this.pageInfo.total = res.data.data.total;
          } else {
            this.$message.error(res.data.msg);
          }
        })
        .catch(err => {
          this.$message.error(err);
        })
        .finally(_ => {
          this.tableLoading = false;
        });
    },
    viewJxsqsChain(row) {
      this.curGoods = row;
      let getTrackingChain = function(node, chain, list) {
        if (node.children.length === 0) {
          node.data.expdtBeginDate = moment(node.data.expdtBeginDate).format(
            "YYYY-MM-DD"
          );
          node.data.expdtEndDate = moment(node.data.expdtEndDate).format(
            "YYYY-MM-DD"
          );
          chain.push(node.data);
          list.push(chain);
        } else {
          for (let child of node.children) {
            const newChain = cloneDeep(chain);
            node.data.expdtBeginDate = moment(node.data.expdtBeginDate).format(
              "YYYY-MM-DD"
            );
            node.data.expdtEndDate = moment(node.data.expdtEndDate).format(
              "YYYY-MM-DD"
            );
            newChain.push(node.data);
            getTrackingChain(child, newChain, list);
          }
        }
        return list;
      };
      this.tableLoading = true;
      let params = {};
      if (!this.bIsHos && this.filter.hosId) {
        params.hosId = this.filter.hosId;
      }
      this.axios
        .get(this.BASE_URL + "/getTrackingChain/" + row.spdGoodsCode, {
          params
        })
        .then(res => {
          if (res.data.code === 0) {
            this.trackingChainList.splice(0);
            const data = res.data.data;
            for (let node of data) {
              const chain = [];
              getTrackingChain(node, chain, this.trackingChainList);
            }
            this.bShowTrackingChain = true;
            if (!this.bIsHos) {
              this.getSqsHos();
            } else {
              this.applyHos = this.trackingChainList.map(
                item => item[0].provName
              );
            }
          } else {
            this.$message.error(res.data.msg);
          }
        })
        .catch(err => {
          this.$message.error(err);
        })
        .finally(_ => {
          this.tableLoading = false;
        });
    },
    getSqsHos() {
      let sqsIds = new Set(this.trackingChainList.map(item => item[0].sqsId));
      this.axios
        .post(this.BASE_URL + "/getSqsHos", sqsIds)
        .then(res => {
          if (res.data.code === 0) {
            this.applyHos = res.data.data;
          } else {
            this.$message.error(res.data.msg);
          }
        })
        .catch(err => {
          this.$message.error(err);
        });
    },
    getExcel() {
      const hosName = this.hosArray.find(o => o.id === this.filter.hosId).name;

      const url = `${this.BASE_URL}/getExcel/${
        this.filter.hosId
      }/${hosName}/X-US-${this.user.xus}/`;

      window.open(url, "_blank");
    },
    validExpdtDate(item) {
      let expdtEndDate = moment(item.expdtEndDate);
      let now = moment();
      return item.isValiding === "1"
        ? "long-valid"
        : expdtEndDate.isBefore(now)
          ? "expired"
          : expdtEndDate.subtract(1, "months").isBefore(now)
            ? "expiring"
            : "";
    },
    tagType(index) {
      let type = "";
      switch (index % 5) {
        case 0:
          break;
        case 1:
          type = "success";
          break;
        case 2:
          type = "info";
          break;
        case 3:
          type = "warning";
          break;
        case 4:
          type = "danger";
          break;
        default:
          break;
      }
      return type;
    }
  }
};
</script>
<style scoped lang="scss">
a {
  color: blue;
}
.tool-bar {
  padding-top: 3px;
  height: 30px;
  box-shadow: 0px 0px 3px 0px #a9a1a1 inset;
  border-radius: 4px;
  background-color: #f9f9f9;
  margin-bottom: 5px;
}
.full-height {
  height: calc(100% - 30px);
}
.table-class {
  width: 100%;
  height: calc(100% - 61px);
}
.overflow {
  overflow-y: auto;
}
.input-box {
  padding: 0 10px;
  width: 200px;
}
.goods-info {
  line-height: 2;
  .tag {
    margin: 0 5px;
  }
}
.chain-list {
  display: flex;
  // align-items: center;
  // justify-content: center;
  flex-flow: row wrap;
  line-height: 2;
  .steps {
    height: 100%;
    flex-flow: column;
    display: flex;
    padding-right: 10px;
    border: 1px dotted lightgrey;
    border-radius: 10px;
    margin: 0 10px 20px;
    max-width: 30%;
    .cert-title {
      text-align: center;
    }
    .step {
      position: relative;
      flex-shrink: 1;
      display: flex;
      .head {
        flex-grow: 0;
        width: 24px;
        color: #409eff;
        border-color: #409eff;
        // position: relative;
        .arrow {
          width: 0;
          height: 0;
          border-style: solid;
          border-width: 0 7px 7px 7px;
          border-color: transparent transparent #007bff transparent;
          top: 50%;
          position: absolute;
          left: 5px;
        }
        .line {
          margin-right: 0px;
          width: 2px;
          top: 0;
          bottom: 0;
          left: 11px;
          position: absolute;
          border-color: inherit;
          background-color: #c0c4cc;
          .inner {
            display: block;
            border-width: 0px;
            border-style: solid;
            border-color: inherit;
            transition: 0.15s ease-out;
            box-sizing: border-box;
            width: 0;
            height: 0%;
          }
        }
        .icon {
          border-radius: 50%;
          border: 2px solid;
          border-color: inherit;
          position: relative;
          z-index: 1;
          display: inline-flex;
          justify-content: center;
          align-items: center;
          width: 24px;
          height: 24px;
          font-size: 14px;
          box-sizing: border-box;
          background: #fff;
          transition: 0.15s ease-out;
          .inner {
            display: inline-block;
            user-select: none;
            text-align: center;
            font-weight: 700;
            line-height: 1;
            color: inherit;
          }
        }
      }
      .main {
        padding-left: 10px;
        flex-grow: 1;
        white-space: normal;
        text-align: left;
        .title {
          line-height: 24px;
          padding-bottom: 8px;
          color: #409eff;
          font-size: 16px;
        }
        .description {
          padding-right: 10px;
          margin-top: -5px;
          font-size: 12px;
          line-height: 20px;
          font-weight: 400;
          margin-bottom: 10px;
          .expired {
            color: deeppink;
          }
          .expiring {
            color: lightpink;
          }
          .long-valid {
            color: lightgreen;
          }
        }
      }
    }
  }
}
</style>
<style>
</style>
