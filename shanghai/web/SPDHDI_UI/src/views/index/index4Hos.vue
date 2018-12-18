<template>
    <!--<el-row>
        <el-row :gutter="10">
            <el-col :span="3">
                <el-card :body-style="{ padding: '0px' }">
                   <IEcharts :option="unSubmitPur" :loading="echartLoading"></IEcharts>        
                </el-card>
            </el-col>
            <el-col :span="3">
                <el-card :body-style="{ padding: '0px' }">
                    <IEcharts :option="unConfirm" :loading="echartLoading"></IEcharts>
                </el-card>
            </el-col>
            <el-col :span="3">
                <el-card :body-style="{ padding: '0px' }">
                    <IEcharts :option="unRecive" :loading="echartLoading"></IEcharts>
                </el-card>
            </el-col>
            <el-col :span="3">
                <el-card :body-style="{ padding: '0px' }">
                    <IEcharts :option="other" :loading="echartLoading"></IEcharts>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card :body-style="{ padding: '0px' }">
                    
                </el-card>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="20">
                <el-card :body-style="{ padding: '0px' }" class="bigChart">
                    <IEcharts style="height:250px;width:1000px" :option="optionBar" :loading="echartLoading"></IEcharts>
                </el-card>
            </el-col>
        </el-row>
      </el-row>  -->
</template>
<script>
import IEcharts from 'vue-echarts-v3';
export default {
        data() {
            return {
                echartLoading:false,
                /*ordersInfo:[
                    {name:'unSubmitPur',text:'未提交请购单',value:10},
                    {name:'unConfirm',text:'未确认请购单',value:25},
                    {name:'unRecive',text:'未收货',value:48},
                    {name:'other',text:'其他',value:33},
                ],
                unSubmitPur:{},
                unConfirm:{},
                unRecive:{},
                other:{},
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
                    yAxis: {
                        type: 'category',
                        data: ['承诺书','法人委托书','经销商授权书','生产厂商证件','医疗器械注册证']
                    },
                    series: [{
                        name: '数量',
                        type: 'bar',
                        data: [50, 90,400,900,3000]
                    }]
                }    */         
            }
        },
        components: {
            IEcharts
        },
        methods: {           
            setChartsOpt(){
                var _this= this;
                this.ordersInfo.forEach((item,index) => {
                let opt = {
                        title: {
                            text: item.text,
                            link:'http://www.baidu.com',
                            textStyle:{
                                fontSize:12,
                                fontWeight:'normal'
                            }
                        },
                        tooltip : {
                            formatter: "{c}"
                        },     
                        series: [
                            {
                                name: item.text,
                                type: 'gauge',    
                                max:50,
                                radius: '85%',
                                min:0,                                
                                splitNumber:5,
                                center: ['50%', '65%'],    // 默认全局居中
                                //startAngle: 155,
                               // endAngle: 25,                              
                                axisLine: {            // 坐标轴线
                                    lineStyle: {       // 属性lineStyle控制线条样式
                                        width: 5
                                        }
                                    },
                                splitLine: {           // 分隔线
                                    length: 10,         // 属性length控制线长
                                    lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                                        color: 'auto'
                                    }
                                },
                                pointer : {
                                    width : 3,
                                    color:'red'
                                },
                                detail: {formatter:'{value}',fontsize:'12px'},
                                data:[{value:10,name:''}]                          
                            }                        
                        ]
                    }
                    opt.series[0].data[0].value=item.value;
                    this[item.name] = opt;
                }, this);
            }
        },

        mounted: function () {
           // this.setChartsOpt();
        }
    }
</script>

<style scoped>
.vue-echarts{
    width: 100%;
    height: 150px;
  }
  .bigChart{
      margin-top:10px;
  }
  .bigChart .vue-echarts{ 
    width: 100%;
    height: 250px;
  }
.card_header {
    padding: 8px 20px;
    text-align: center;
} 
</style>