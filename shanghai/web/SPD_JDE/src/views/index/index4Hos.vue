
<template>
    <el-row>
        <el-row>
            <el-col :span="24">
                <el-card :body-style="{ padding: '0px' }" class="bigChart">
                    <IEcharts :option="optionBar" :loading="echartLoading" style="width:100%;height:350px;"></IEcharts>
                </el-card>
            </el-col>
        </el-row>
      </el-row>  
</template>
<script>
import IEcharts from 'vue-echarts-v3';
export default {
        name:"index4Hos",
        data() {
            return {
                echartLoading:false,

                seriesData : [],
                yAxisData : [],

                optionBar:{
                    color: ['#3398DB'],
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    xAxis: {
                        type: 'value',
                        boundaryGap: [0, 0.01]
                    },
                    grid:{
                        left:'12%'
                    },
                    /***
                    供应商税务登记证
                    供应商经营许可证
                    供应商营业执照
                    医疗器械注册证
                    廉政协议书
                    承诺书
                    法人委托书
                    生产厂家税务登记证
                    生产厂家营业执照
                    销售代表

                     */
                    yAxis: {
                        type: 'category',
                        data: ['供应商税务登记证','供应商经营许可证','供应商营业执照','医疗器械注册证','廉政协议书',
                                '承诺书','法人委托书','生产厂家税务登记证','生产厂家营业执照','销售代表']
                    },
                    series: [{
                        name: '数量',
                        type: 'bar',
                        data: [0,0,0,0,0, 0,0,0,0,0]
                    }]
                }             
            }
        },
        components: {
            IEcharts
        },
        methods: {           
            setChartsOpt(){
                var _this= this;
                let hosId  = this.hosId;
                let param = { orderBy: "", queryObject: {hosId:this.hosId}, page:1, rows: 9999 };
                this.axios.post(`/spdHERPService/reportAnalysis/IndexStatisticsController/queryCertificateStacs`,param).then(res=>{
                     if(res.data.data){
                        for(var i = 0;i < res.data.data.length; i++) {
                            var item = res.data.data[i];
                            _this.seriesData.push(item.qty);
                            _this.yAxisData.push(item.certKind);
                        }
                        _this.optionBar.yAxis.data = _this.yAxisData;
                        _this.optionBar.series[0].data = _this.seriesData;
                     }
                });
            }
        },

        mounted: function () {
            this.hosId = this.user.corpId;
            this.setChartsOpt();
        }
    }
</script>

<style scoped>
.vue-echarts{
    width: 100%;
    height: 150px;
  }
  .bigChart{
      margin-top:15px;
  }
  .bigChart .vue-echarts{ 
    width: 100%;
    height: 300px;
  }
.card_header {
    padding: 8px 20px;
    text-align: center;
} 
</style>