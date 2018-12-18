<!--首页显示内容-->
<template>
<div id="homeIndx">
    <el-row :gutter="5">
        <el-col :span="12"> 
            <el-row :gutter="5">
                <el-col :span="24">
                    <el-collapse v-model="activeNames1">
                        <el-collapse-item title="证照过期提醒" name='1'>
                            <div style="height: 90px;">
                                <el-row :gutter="1">
                                    <el-col :span="10">
                                        <div class="color-box-icon" style="background-color: #409eff;" >
                                            <i class="fa fa-calendar-times-o fa-5x" aria-hidden="true"></i> 
                                        </div>
                                    </el-col>
                                    <el-col :span="14">
                                        <div class="color-box" style="background-color: #d80000;cursor: pointer;" @click="clickOutDateCertsEvt()">
                                            <div style="margin-bottom:-10px;font-size: 36px;" >{{outDateCoumt}}</div>
                                            <div>已经过期</div>
                                        </div>
                                    </el-col>
                                </el-row>
                            </div>
                        </el-collapse-item>
                    </el-collapse>
                </el-col>
            </el-row>
        </el-col>

        <el-col :span="12">
            <el-row :gutter="5">
                <el-col :span="24">
                        <el-collapse v-model="activeNames2">
                            <el-collapse-item title="证照过期预警" name='2'>
                                <div style="height: 90px;">
                                <el-row :gutter="1">
                                    <el-col :span="10">
                                        <div class="color-box-icon" style="background-color: #409eff;">
                                            <i class="fa fa-warning  fa-5x" aria-hidden="true"></i> 
                                        </div>
                                    </el-col>
                                    <el-col :span="14">
                                        <div class="color-box" style="background-color: #EB9E05;cursor: pointer;" @click="clickOutDateCertsEvt()">
                                            <div style="margin-bottom:-10px;font-size: 36px;">{{outDateInMonth}}</div>
                                            <div>30天内过期</div>
                                        </div>
                                    </el-col>
                                </el-row>
                                </div>
                            </el-collapse-item>
                        </el-collapse>
                </el-col>
            </el-row>
        </el-col>
    </el-row>

    <el-row :gutter="5">
        <el-col :span="12"> 
            <el-row :gutter="5">
                <el-col :span="24">
                    <el-collapse v-model="activeNames3">
                        <el-collapse-item title="供应商数" name='3'>
                            <div style="height: 90px;">
                                <el-row :gutter="1">
                                    <el-col :span="10">
                                        <div class="color-box-icon" style="background-color: #409eff;">
                                            <i class="fa fa-id-card fa-5x" aria-hidden="true"></i> 
                                        </div>
                                    </el-col>
                                    <el-col :span="14">
                                        <div class="color-box" style="background-color: #67C23A;cursor: pointer;" @click="clickProvEvt()">
                                            <div style="margin-bottom:-10px;font-size: 36px;" >{{provCount}}</div>
                                            <div>供应商</div>
                                        </div>
                                    </el-col>
                                </el-row>
                            </div>
                        </el-collapse-item>
                    </el-collapse>
                </el-col>
            </el-row>
        </el-col>

        <el-col :span="12">
            <el-row :gutter="5">
                <el-col :span="24">
                        <el-collapse v-model="activeNames4">
                            <el-collapse-item title="医院商品数" name='4'>
                                <div style="height: 90px;">
                                <el-row :gutter="1">
                                    <el-col :span="10">
                                        <div class="color-box-icon" style="background-color: #409eff;">
                                            <i class="fa fa-hospital-o fa-5x" aria-hidden="true"></i> 
                                        </div>
                                    </el-col>
                                    <el-col :span="14">
                                        <div class="color-box" style="background-color: #67C23A;cursor: pointer;" @click="clickProductsEvt()">
                                            <div style="margin-bottom:-10px;font-size: 36px;">{{hosProductsCount}}</div>
                                            <div>医院商品</div>
                                        </div>
                                    </el-col>
                                </el-row>
                                </div>
                            </el-collapse-item>
                        </el-collapse>
                </el-col>
            </el-row>
        </el-col>
    </el-row>

    <el-row :gutter="5">
        <el-col :span="24">
                <el-collapse v-model="activeNames5">
                    <el-collapse-item title="供应商证照统计" name='5'>
                        <div style="height: 400px;">
                            <index4Hos></index4Hos>
                        </div>
                    </el-collapse-item>
                </el-collapse>
        </el-col>
    </el-row>
   </div> 
</template>

<script>
    import index4Hos from './index4Hos';
    export default {
        data () {   
            return {
                activeNames1: ['1'],
                activeNames2: ['2'],
                activeNames3: ['3'],
                activeNames4: ['4'],
                activeNames5: ['5'],

                outDateCoumt:0,//已经过期的证照数
                outDateInMonth:0,//一个月内过期的证照数
                provCount:0,//医院供应商数量
                hosProductsCount:0,//医院商品数量
            }
        },
        components: {
            index4Hos
        },
        methods:{
            // 查询过期证件
            queryProvOutdateCerts:function () {
                var param = {
                    orderBy: "",
                    queryObject: {
                        hosId:this.hosId,
                       // outDate:this.outDateCertQuery.outDate
                    },
                };
                let o = this;
                o.axios.post('/spdHERPService/reportAnalysis/IndexStatisticsController/queryProvOutdateCerts', param).then(res => {
                    if(res.data.data){
                        for(var i = 0;i < res.data.data.length; i++) {
                            var item = res.data.data[i];
                            if(item.outDate && item.outDate>0){
                                o.outDateCoumt++;
                            }else if(item.outDate && item.outDate>= -30){
                                o.outDateInMonth++;
                            }
                        }
                    }
                }, err => {});
            },

            // 医院获取供应商列表
            getHosProvInfoVoList: function () {
                var param = {
                    orderBy: "",
                    queryObject: {
                        hosId:this.hosId
                    },
                };
                this.loading = true;
                this.axios.post('/spdHERPService/provManager/hosProvInfo/hosProvInfoVoList', param).then(res => {
                    this.loading = false;
                    this.provCount = res.data.data.total;
                    // this.page = res.data.data.pageNum;
                }, err => {
                    this.loading = false;
                });
            },

            //医院商品数
            getHosGoodsList: function () {
                let hosId = this.user.corpId
                var param = {
                    orderBy: '',
                    queryObject: {
                        hosId: hosId,
                    },
                }
                this.axios.post('/spdHERPService/myGoods/hosGoods/getHosGoodsByHos', param).then(
                    res => {
                        this.loading = false
                        if (res.data.code == '0') {
                            this.hosProductsCount = res.data.data.total
                        } else {
                            this.$message.error(res.data.msg)
                        }
                    },
                    err => {
                        this.loading = false
                    }
                )
            },
            /**点击过期证照数 */
            clickOutDateCertsEvt : function(){
                this.$router.push({ path: 'provCerts'});
            },
            /**点击供应商数 */
            clickProvEvt : function(){
                this.$router.push({ path: 'provList'});
            },
            /**点击医院商品数 */
            clickProductsEvt : function(){
                this.$router.push({ path: 'productInfo'});
            }
        },
        mounted:function (){
            this.hosId = this.user.corpId;
            this.queryProvOutdateCerts();
            this.getHosProvInfoVoList();
            this.getHosGoodsList();
        }
    }
</script>

<style scoped>
    .el-row {
        margin-bottom: 5px;
        &:last-child {
        margin-bottom: 0;
    }
  }
  .el-col {
        border-radius: 4px;
}
</style>

<style>
    #homeIndx .el-collapse-item__header {
        height: 25px;
        line-height: 25px;
        padding-left: 15px;
        color: #48576a;
        cursor: pointer;
        font-size: 13px;
        background:url(../../styles/images/portal_tab_single.gif?V=3_50_2_29);
    }
    #homeIndx .color-box {
        border-radius: 4px;
        height: 90px;
        box-sizing: border-box;
        color: #fff;
        font-size: 14px;
        text-align: center;
    }
    #homeIndx .color-box-icon {
        border-radius: 4px;
        padding: 10px;
        height: 90px;
        box-sizing: border-box;
        color: #fff;
        text-align: center;
    }  
    #homeIndx .value {
        display: inline-block;
        text-align: center;
        padding-top: 10px;
        color: #fff;
    }
</style>