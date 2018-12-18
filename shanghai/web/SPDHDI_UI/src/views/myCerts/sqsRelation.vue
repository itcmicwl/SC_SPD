<template>
  <el-row style="height:100%;" >
    <searchJxsqsNew ref="searchDia" @onSelectJxsqs="onSelectJxsqs"></searchJxsqsNew>
    <sqs4Hos ref="hosSetter"></sqs4Hos>
    <el-popover ref="menu" popper-class="pop-cls" placement="bottom"  width="200" trigger="click" v-model="nodeMenuV">
      <ul class="el-menu el-menu--popup el-menu--popup-bottom-start my-menu">
        <li class="el-menu-item" @click="contextMenuClick(1)">添加上级授权</li>
        <li class="el-menu-item" v-if="!menuVisibale.first" @click="contextMenuClick(2)">编辑当前授权</li>
        <li class="el-menu-item" v-if="!menuVisibale.first" @click="contextMenuClick(3)">移除当前授权</li>
        <li class="el-menu-item" v-if="menuVisibale.first"  @click="contextMenuClick(4)">适用医院设置</li>
      </ul>
    </el-popover>
    <el-button class="btn-hide" :style="{ top: btnOffset.y+'px', left: btnOffset.x + 'px' }" v-popover:menu></el-button>
    <el-row style="height:100%;">
      <el-col v-if="!jxsqsRelation.id" :span="24"><el-button type="warning" class="big-btn" icon="el-icon-plus" circle @click="showSearchDia(null,0)"></el-button></el-col>
      <el-col v-show="jxsqsRelation.id" :span="24" style="height:100%;">
        <div id="dsqsRelation" style="width:100%;height:500px;"></div>
        <el-button type="success" @click="clearPage" size="small">关闭<i class="el-icon-check el-icon--right"></i></el-button>
      </el-col>
    </el-row>
  </el-row>
</template>
<script>
function StringFomart(str){
  if(!str || str.length<=10) return str || '';
  if(str.length>10) return str.substr(0,10)+"...";
}
var option = {
    title : {text: '授权关系'},
    tooltip : {
        trigger: 'item',
        position:function(p){   //其中p为当前鼠标的位置
            return [p[0] + 10, p[1] - 10];
        },
        formatter: function(p){
          var str = '<style>td{padding:5px;}</style><table>';
            str += '<tr><td>产品名称</td><td>产品规格</td><td>授权情况</td></tr>';
            for (var i = 0; i <= p.data.lstDetail.length; i++) {
              var node =  p.data.lstDetail[i];
              if(node){
                str += `<tr><td>${node.goodsName}</td><td>${node.goodsGg}</td><td>${node.sqsLastlevelId || '~'}</td></tr>`;
              }
            }
            str += '</table>';
            return str;
          }
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : false,
    series : [
        {
            name:'树图',
            type:'tree',
            orient: 'horizontal',  // vertical horizontal
            rootLocation: {x: '30%', y: '60%'}, // 根节点位置  {x: 'center',y: 10}
            nodePadding: 20,
            symbol: 'rectangle',
            symbolSize: [180,80],
            expandAndCollapse:false,
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        position: 'inside',
                        textStyle: {color: '#000',fontSize: 12},
                        formatter:function(d1){
                          var sqs = d1.data;
                          return [`${sqs.id}`,`{prov_cls|${StringFomart(sqs.provName)}} {pkind|${comKind[sqs.provKind.toString()]}}`,
                                `${StringFomart(sqs.chiefName)} {pkind|${comKind[sqs.chiefKind.toString()]}}`,
                            `${moment(sqs.expdtBeginDate).format("YYYY-MM-DD")}/${moment(sqs.expdtEndDate).format("YYYY-MM-DD")}`
                          ].join('\n');
                        },
                        width:180,
                        height:70,
                        backgroundColor: '#eee',
                        borderColor: 'rgb(199,86,83)',
                        borderWidth: 2,
                        borderRadius: 5,
                        padding: 10,
                        color: '#000',
                        fontSize: 12,
                        shadowBlur: 3,
                        shadowColor: '#888',
                        shadowOffsetX: 0,
                        shadowOffsetY: 3,
                        lineHeight: 20,
                        rich: {
                          prov_cls: {
                              color: '#409eff',
                          },
                          pkind: {
                              backgroundColor: '#339911',
                              color: '#fff',
                              borderRadius: 15,
                              padding: 2,
                              margin:3,
                          }
                      }
                    },
                    lineStyle: {color: '#ccc',width: 1,
                        type: 'solid' // 'curve'|'broken'|'solid'|'dotted'|'dashed'
                    }
                },
                emphasis: {label: {show: true}
                }
            },
            data: []
        }
    ]
};
var myChart=null;
import {comKind} from '@/common/js/constance'
import echarts from 'echarts'
import searchJxsqsNew from './searchJxsqsNew';
import sqs4Hos from './sqs4Hos';
import moment from 'moment';
  export default {
    data() {
      return {
        menuVisibale:{
          first:true,
          last:true
        },
        btnOffset:{
          x:0,
          y:0
        },
        sqsId:'',
        nodeMenuV:false,
        myChart:null,
        jxsqsRelation:{},
        currentSqs:{},
        prevSqs:{}
      }
    },
    watch:{
      nodeMenuV:(val)=>{
        if(val===true){
          myChart.dispatchAction({type: 'hideTip'});
        }
      }
    },
    components:{searchJxsqsNew,sqs4Hos},
    methods: {
      clearPage(){
        this.sqsId='';
        this.jxsqsRelation={};
        this.currentSqs={};
        this.prevSqs={};
      },
      chartResize(){
        let width=document.getElementById("dsqsRelation").offsetWidth-20;
        myChart.resize({width:width});
      },
     drawTree(){
       var _this =this;
       if(!this.jxsqsRelation.id){return;}
        this.$nextTick(()=>{
          option.series[0].data=[this.jxsqsRelation];
          myChart.setOption(option);
          this.chartResize();
        });
     },
     setCurrentNode(node){
       var _this = this;
       _this.currentSqs=null;
       _this.prevSqs = null;
       traverseTree(this.jxsqsRelation,node);
       function traverseTree(tree,data){
         if (!tree) {
              return;
          }
          if(tree.id==node.id){
            _this.currentSqs = tree;
            return;
          }
          if (tree.children && tree.children.length > 0) {
             _this.prevSqs = tree;
              var i = 0;
              for (i = 0; i < tree.children.length; i++) {
                  traverseTree(tree.children[i],data);
              }
          }
       }
     },
     contextMenuClick(cmdKind){
        this.nodeMenuV = false;
       if(cmdKind<2){    //添加或修改
          this.showSearchDia(this.currentSqs,cmdKind);
       }else if(cmdKind==2){
         this.showSearchDia(this.prevSqs,cmdKind,this.currentSqs.id);
       }else if(cmdKind ==3){//移除关系
        this.removeRelation();
       }else if(cmdKind == 4){
         this.$refs.hosSetter.show(this.currentSqs);
       }
     },
      showSearchDia(sqs,cmdKind,editSqsId){
          this.$refs.searchDia.showAndSearch(sqs,cmdKind,this.sqsId,editSqsId);
      },
      removeRelation(){
        var msg=null,cb=null;
        var _this = this;
        if(this.currentSqs.id == this.jxsqsRelation.id){
          msg = "当前为供应商末级，将清空页面但不会删除任何关系，要继续吗？";
          cb=function(){
            _this.jxsqsRelation={};
            _this.getSqsRelation(_this.sqsId);
          }
        }else{
          msg = `<p>确定要移除与${_this.prevSqs.provName}的以下商品授权关系吗？</p>`;
          msg += '<table><tr><td>产品名称</td><td>产品规格</td></tr>';
          var param=[];
          _this.prevSqs.lstDetail.forEach(item=>{
            if(item.sqsLastlevelId==_this.currentSqs.id){
                msg += `<tr><td>${item.goodsName}</td><td>${item.goodsGg}</td></tr>`;
                var data = {
                  id:item.id,
                  version:item.version
                };
                param.push(data);
              }
          });
          msg += '</table>';
          cb=function(){
            _this.axios.post(`/spdHDIService/provJxsqs/delRelation/${_this.currentSqs.id}`,param).then(res=>{
                if(res.data.code==0){
                  _this.$message.success('操作成功！');
                  _this.currentSqs = res.data.data;
                  _this.getSqsRelation(_this.sqsId);
                }
            },err=>{
              _this.$message.error('操作失败，请重试！');
            });
          }
        }
        this.$confirm(msg, '提示', {
            confirmButtonText: '确定删除',
            cancelButtonText: '取消',
            type: 'warning',
            customClass:'my-confirm-cls',
            dangerouslyUseHTMLString:true
          }).then(() => {
            if(typeof(cb)=='function'){
              cb();
            }
          }).catch();
      },
      onSelectJxsqs(sqsId){
        this.sqsId = sqsId;
        this.getSqsRelation(sqsId);
      },
      getSqsRelation(sqsId){
        var _this = this;
        myChart.showLoading();
        this.axios.get(`/spdHDIService/provJxsqs/getSqsRelationById/${sqsId}`).then(res=>{
          if(res.data.code==0 && res.data.data){
            _this.jxsqsRelation = res.data.data;
            _this.drawTree();
          }
          myChart.hideLoading();
        },err=>{myChart.hideLoading();});
      }
    },
    mounted() {
      var _this =this;
      document.oncontextmenu = function () { return false; };
      myChart = echarts.init(document.getElementById("dsqsRelation"));
      myChart.on('mouseover',function(params){
          _this.btnOffset.x=params.event.offsetX+5;
          _this.btnOffset.y=params.event.offsetY+5;
      });
      myChart.on('contextmenu',function(params){
        _this.setCurrentNode(params.data);
        if(!_this.prevSqs || !_this.prevSqs.id){
            _this.menuVisibale.first = true;
        }else{
          _this.menuVisibale.first = false;
        }
        _this.btnOffset.x=params.event.offsetX+5;
        _this.btnOffset.y=params.event.offsetY+5;
        _this.nodeMenuV = false;
        _this.$nextTick(()=>{
            _this.nodeMenuV = true;
        })
      });
      window.onresize = function () {
          _this.chartResize();
      };
      this.sqsId = this.$route.query.sqsId;
      if(this.sqsId){
        this.getSqsRelation(this.sqsId);
      }
    }
  };

</script>
<style>
.big-btn{
    height:200px;
    width:200px;
    position: absolute;
    top: 30%;
    left:40%
  }
   .big-btn i{
    font-size:150px;
  }
.btn-hide{
  width:0px;
  height:0px;
  position: absolute;
  border:0px solid #fff;
}
.pop-cls{
    padding:0;
    opacity: 0.8;
    background-color: rgb(84, 92, 100);
}
.pop-cls[x-placement^=bottom] .popper__arrow::after{
  border-bottom-color: rgb(84, 92, 100);
}
.my-menu li{
  color: rgb(255, 255, 255);
  background-color: rgb(84, 92, 100);
}
.my-menu li:hover{
  color:#44a7ff;
  background-color:#dbecf4;
  font-size: 13px;
  font-weight: bold;
}
.my-confirm-cls{
  width:450px;
}
.my-confirm-cls td{
  padding:1px;
}
.my-confirm-cls table,.my-confirm-cls table tr th,.my-confirm-cls table tr td { border:1px solid #b0b6bb; }
.my-confirm-cls table {line-height: 25px; text-align: center; border-collapse: collapse; padding:2px;}
</style>
