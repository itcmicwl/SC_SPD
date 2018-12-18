<template>
    <transition name="modal">
        <div class="modal-mask">
            <div class="modal-wrapper">
                <div class="modal-container">

                    <!--<div class="modal-header">-->
                    <!--<slot name="header">-->
                    <!--default header-->
                    <!--</slot>-->
                    <!--</div>-->

                    <div class="modal-body">
                        <!--<slot name="body">-->
                        <!--default body-->
                        <!--</slot>-->
                        <IEcharts style="height:400px;width:400px" :option="linedata" :loading="loading"
                                  @ready="onReady" @click="onClick"></IEcharts>
                    </div>

                    <div class="modal-footer">
                        <slot name="footer">
                            <!--default footer-->
                            <el-button icon="close" @click="$emit('close')">
                                关闭
                            </el-button>
                        </slot>
                    </div>
                </div>
            </div>
        </div>
    </transition>
</template>

<script>
import IEcharts from 'vue-echarts-v3/src/lite.js'

export default {
  name: 'view',
  components: {
    IEcharts
  },
  props: {},
  data () {
    return {
      loading: false,
      option: {},
      linedata: {
        tooltip: {
          trigger: 'axis'
          // formatter: function (params) {
          //     params = params[0];
          //     var date = new Date(params.name);
          //     return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
          // },
        },
        xAxis: {
          type: 'time',
          name: '日期',
          splitLine: {
            show: true
          }
        },
        yAxis: {
          type: 'value',
          name: '℃'
        },
        series: [{
          name: '温度',
          type: 'line',
          showSymbol: false,
          hoverAnimation: false,
          data: []
        }]
      }
    }
  },
  methods: {
    onReady (instance) {
    },
    onClick (event, instance, echarts) {
    }
  },
  mounted () {
    let now = +new Date(2017, 3, 1)
    let oneDay = 24 * 3600 * 1000
    let value = Math.random() * 20

    function randomData () {
      now = new Date(+now + oneDay)
      value = value + Math.random() * 2 - 1
      return {
        name: now.toString(),
        value: [
          [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
          Math.round(value)
        ]
      }
    }

    for (let i = 0; i < 30; i++) {
      this.linedata.series[0].data.push(randomData())
    }
  }
}
</script>

<style>
    .modal-mask {
        position: fixed;
        z-index: 9998;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, .5);
        display: table;
        transition: opacity .3s ease;
    }

    .modal-wrapper {
        display: table-cell;
        vertical-align: middle;
    }

    .modal-container {
        width: 400px;
        margin: 0px auto;
        padding: 20px 30px;
        background-color: #fff;
        border-radius: 2px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
        transition: all .3s ease;
        font-family: Helvetica, Arial, sans-serif;
    }

    .modal-header h3 {
        margin-top: 0;
        color: #42b983;
    }

    .modal-body {
        margin: 20px 0;
    }

    .modal-default-button {
        float: right;
    }

    /*
     * The following styles are auto-applied to elements with
     * transition="modal" when their visibility is toggled
     * by Vue.js.
     *
     * You can easily play with the modal transition by editing
     * these styles.
     */

    .modal-enter {
        opacity: 0;
    }

    .modal-leave-active {
        opacity: 0;
    }

    .modal-enter .modal-container,
    .modal-leave-active .modal-container {
        -webkit-transform: scale(1.1);
        transform: scale(1.1);
    }
</style>
