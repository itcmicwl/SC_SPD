<template>

<div id = 'orderMgr'>
    <mt-header title="采购单确认">
        <router-link to="/" slot="left">
            <mt-button icon="back"></mt-button>
        </router-link>
    </mt-header>
    <mt-navbar v-model="selected">
        <mt-tab-item id="1">已认单</mt-tab-item>
        <mt-tab-item id="2">待确认</mt-tab-item>
    </mt-navbar>

    <!-- tab-container -->
    <mt-tab-container v-model="selected">
    <mt-tab-container-item id="1">
        <div class='order-list'  v-model="purchaseOrderList">
            <div class="list-group"  v-for="order in purchaseOrderList">
                <div class="list-group-item">
                    <h4 class="list-group-item-heading">确认编号：<span>{{ order.billId}}</span></h4>
                    <p class="list-group-item-text">状态：<span>{{statusFormat(order.status)}}</span></p>
                    <p class="list-group-item-text">请购机构：<span>{{order.purchaseCompanyName}}</span></p>
                    <p class="list-group-item-text">请购科室：<span>{{order.applyOrgName}}</span></p>
                    <p class="list-group-item-text">收货科室：<span>{{order.recieveOrgName}}</span></p>
                    <p class="list-group-item-text">含税金额：<span>{{order.sumAmount}}</span></p>
                    <p class="list-group-item-text">不含税金额：<span>{{order.sumTaxAmount}}</span></p>
                    <p class="list-group-item-text">制单人：<span>{{order.fillterName}}</span></p>
                    <p class="list-group-item-text">制单时间：<span>{{ order.fillDate | dateFormat('YYYY-MM-DD')}}</span></p>
                </div>
            </div>
        </div>
    </mt-tab-container-item>
        <mt-tab-container-item id="2">
            <div class='order-list' v-model="purchaseList">
                <div class="list-group" v-for="entry in purchaseList">
                    <a href="javascript:return 0" @click="orderConfirm(entry.id)" class="list-group-item" >
                        <h4 class="list-group-item-heading">采购单号：<span>{{entry.billId}}</span></h4>
                        <p class="list-group-item-text">状态：<span>{{fStatus(entry.status)}}</span></p>
                        <p class="list-group-item-text">供应商名称：<span>{{entry.provName}}</span></p>
                        <p class="list-group-item-text">请购科室：<span>{{entry.purOrgCname}}</span></p>
                        <p class="list-group-item-text">收货科室：<span>{{entry.recOrgName}}</span></p>
                        <p class="list-group-item-text">制单人：<span>{{entry.fillterName}}</span></p>
                        <p class="list-group-item-text">制单时间：<span>{{ entry.fillDate | dateFormat('YYYY-MM-DD')}}</span></p>
                        <p class="list-group-item-text">备注：<span>{{entry.remark}}</span></p>
                    </a>
                </div>
            </div>
        </mt-tab-container-item>
    </mt-tab-container>
</div>
</template>
<script>
    import { mapGetters } from 'vuex';
    import { PURCHASE_STATUS } from './common/constance';
    import { PURCONFIRM_STATUS } from './common/constance';
    export default {
        data() {
                return {
                selected: "2",
                total: 0,
                page: 1,
                pageSize: 30,
                loading: false,
                purchaseList: [],//采购单确认  列表数据
                purchaseOrderList :[],//采购确认单 列表数据
                filters: {
                    billId: '',
                    provId: ''
                },
            };
        },
        methods:{
            //获取 '采购单确认' 页签数据
            getPurchaseList: function (pIndex) {
                this.page = pIndex;
                var param = {
                    orderBy: "",
                    page: this.page,
                    rows: this.pageSize,
                    queryObject: {
                        billId: this.filters.billId,
                        provId: this.filters.provId,
                        arrStatus: [20, 30]
                    }
                };
                this.loading = true;
                this.axios.post('/spdHDI/order/purchase/purchasePage', param).then(res => {
                    this.loading = false;
                    this.purchaseList = res.data.data.data;
                }, err => {
                    this.loading = false;
                });
            },
            ////获取 '采购确认单' 页签数据
            getPurchaseOrderList: function (pIndex) {
                this.page = pIndex;
                var param = {
                    orderBy: "",
                    page: this.page,
                    rows: this.pageSize,
                    queryObject: {
                        billId: this.filters.billId,
                        provId: this.filters.provId
                    }
                };
                this.loading = true;
                this.axios.post('/spdHDI/orderMgr/purconfirm/listByPage', param).then(res => {
                    this.loading = false;
                    this.purchaseOrderList = res.data.data.data;
                }, err => {
                    this.loading = false;
                });
            },
            //确认单状态
            fStatus: function (value) {
                if (value) {
                    return PURCHASE_STATUS[value].text;
                } else {
                    return '';
                }
            },
            //采购单状态
            statusFormat :function(status) {
                if(status){
                    return PURCONFIRM_STATUS[status].text;
                }
                return '';
            },
            orderConfirm : function(id){
                this.$router.push({
                    name: 'orderConfirmDetail',
                    query: {
                        routeParams: id
                    }
                })
            }
        },
        computed: {
            ...mapGetters(["getLoginUser"]),
        },
        mounted() {
            this.filters.provId = this.getLoginUser.corpId;
            this.getPurchaseList(1);
            this.getPurchaseOrderList(1);
        }
}
</script>


<style scoped>{
    @import '../../styles/css/bootstrap.css';
    @import '../../styles/css/font-awesome.css';
}  
</style>

<style>
    #orderMgr h4{ font-size: 15px;}
    #orderMgr .mint-navbar {
        /*background-color: #f3f3f3;*/
        border-bottom:1px solid #ddd;
    }
    #orderMgr .order-list{
        margin-top: 5px; 
    }
    #orderMgr .list-group {
        padding-left: 0;
        margin-bottom: 0px;
    }
	#orderMgr h1.mint-header-title {
		margin-top: 0px; 
    	margin-bottom: 0px; 
    }
    ##orderMgr .list-group-item:last-child {
        margin-bottom: 0;
        border-bottom-right-radius: 0px; 
        border-bottom-left-radius: 0px; 
        border-top-left-radius: 0px;
        border-top-right-radius: 0px;
    }
    #orderMgr .list-group-item:first-child {
        border-bottom-right-radius: 0px; 
        border-bottom-left-radius: 0px; 
        border-top-left-radius: 0px;
        border-top-right-radius: 0px;
    }
    #orderMgr .list-group-item-text {
        line-height: 1.5;
        color: #888080;
    }
    #orderMgr .mint-tab-item-label {
        font-size: 15px;
    }
    #orderMgr .list-group-item {
        border-left: 0px;
        border-right: 0px;
    }

</style>
}
</style>