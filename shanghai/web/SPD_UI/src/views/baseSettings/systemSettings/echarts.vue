<template>
  <section class="chart-container">
    <el-row>
      <el-col :span="12">
        <div id="chartColumn" style="width:100%; height:400px;"></div>
      </el-col>
      <el-col :span="12">
        <div id="chartBar" style="width:100%; height:400px;"></div>
      </el-col>
      <el-col :span="12">
        <div id="chartLine" style="width:100%; height:400px;"></div>
      </el-col>
      <el-col :span="12">
        <div id="chartPie" style="width:100%; height:400px;"></div>
      </el-col>
      <el-col :span="24">
        <a href="http://echarts.baidu.com/examples.html" target="_blank" style="float: right;">more>></a>
      </el-col>
    </el-row>
  </section>
</template>

<script>
export default {
  data() {
    return {
      chartColumn: null,
      chartBar: null,
      chartLine: null,
      chartPie: null
    };
  },

  mounted: function() {
    this.drawCharts();
  },
  updated: function() {
    this.drawCharts();
  },

  methods: {
    drawColumnChart() {
      this.chartColumn = echarts.init(document.getElementById("chartColumn"));
      this.chartColumn.setOption({
        title: { text: "" },
        tooltip: {},
        xAxis: {
          data: ["口罩", "注射器", "消毒水", "生理盐水", "医用酒精", "胶带"]
        },
        yAxis: {},
        series: [
          {
            name: "销量",
            type: "bar",
            data: [5, 20, 36, 10, 10, 20]
          }
        ]
      });
    },
    drawBarChart() {
      this.chartBar = echarts.init(document.getElementById("chartBar"));
      this.chartBar.setOption({
        title: {
          text: "",
          subtext: "数据来自网络"
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow"
          }
        },
        legend: {
          data: ["2011年", "2012年"]
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true
        },
        xAxis: {
          type: "value",
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: "category",
          data: ["湖北", "湖南", "江西", "上海", "北京", "合计(万)"]
        },
        series: [
          {
            name: "2011年",
            type: "bar",
            data: [18203, 23489, 29034, 104970, 131744, 630230]
          },
          {
            name: "2012年",
            type: "bar",
            data: [19325, 23438, 31000, 121594, 134141, 681807]
          }
        ]
      });
    },
    drawLineChart() {
      this.chartLine = echarts.init(document.getElementById("chartLine"));
      this.chartLine.setOption({
        title: {
          text: "销量走势"
        },
        tooltip: {
          trigger: "axis"
        },
        legend: {
          data: ["注射器", "口罩", "消毒水"]
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]
        },
        yAxis: {
          type: "value"
        },
        series: [
          {
            name: "注射器",
            type: "line",
            stack: "总量",
            data: [120, 132, 101, 134, 90, 230, 210]
          },
          {
            name: "口罩",
            type: "line",
            stack: "总量",
            data: [220, 182, 191, 234, 290, 330, 310]
          },
          {
            name: "消毒水",
            type: "line",
            stack: "总量",
            data: [820, 932, 901, 934, 1290, 1330, 1320]
          }
        ]
      });
    },
    drawPieChart() {
      this.chartPie = echarts.init(document.getElementById("chartPie"));
      this.chartPie.setOption({
        title: {
          text: "饼图",
          subtext: "纯属虚构",
          x: "center"
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
          orient: "vertical",
          left: "left",
          data: ["口罩", "注射器", "消毒水", "生理盐水", "医用酒精"]
        },
        series: [
          {
            name: "销量占比",
            type: "pie",
            radius: "55%",
            center: ["50%", "60%"],
            data: [
              { value: 335, name: "口罩" },
              { value: 310, name: "注射器" },
              { value: 234, name: "消毒水" },
              { value: 135, name: "生理盐水" },
              { value: 1548, name: "医用酒精" }
            ],
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)"
              }
            }
          }
        ]
      });
    },
    drawCharts() {
      this.drawColumnChart();
      this.drawBarChart();
      this.drawLineChart();
      this.drawPieChart();
    }
  }
};
</script>

<style scoped>
.chart-container {
  width: 100%;
  float: left;
}
/*.chart div {
        height: 400px;
        float: left;
    }*/

.el-col {
  padding: 30px 20px;
}
</style>
