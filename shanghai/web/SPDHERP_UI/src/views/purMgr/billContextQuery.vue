<template>
    <div id="modify_price" style="height:100%;">
            <el-col :span="24"  style="max-height:15%;">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form :inline="true" label-width="80px">
                        <el-form-item label="单号">
                            <el-input v-model="filters.billId" placeholder="单号" ></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" icon="search" v-on:click="getDistrBillVos(1)" >查询</el-button>
                        </el-form-item>
                    </el-form>
                </el-col>
            </el-col>

 <div>
        <ul id="example1">
            <li v-for="(value,key,index) in billMaps">
             <font size="3" color="red">   {{statusFormat(key)}} ：{{value}}</font>
            </li>
        </ul>
    </div>


    
   </div>
</template>
<script>
import { PURCHASE_STATUS,PURCHASE_KIND,BILL_TYPE } from './common/constance';
import { mapGetters } from 'vuex';
export default {
    data() {
        return {
            filters: {
                billId: ''
            },         
            loading: false,

            billMaps:{}
        }
    },
 

    methods: {
      statusFormat(key) {
            return BILL_TYPE[key].text;
        },
        //依据输入单号查询配送单
        getDistrBillVos: function (pIndex) {

   
            this.loading = true;
            this.axios.get('/spdHERPService/orderMgr4Del/billMgr/billContextQuery/'+this.filters.billId).then(res => {
                this.loading = false;
               this.billMaps= res.data.data;
            }, err => {
                this.loading = false;
            });
      
        },
     

   
    
       

    
    },
    mounted() {

    }
};

</script>

<style>
#un-rec-pur-bill-list .el-table {
    font-size: 12px;
}
</style>
