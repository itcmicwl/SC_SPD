<template>
    <el-dialog title="条码匹配" :visible.sync="barcodeMatchShow" :show-close='false' :close-on-click-modal="false" >
        <section>
            <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                <el-form :inline="true" label-width="80px">
                    <el-form-item label="商品名称">
                        <el-input v-model="filters.goodsName" placeholder="请输入商品名称" icon="search" @keyup.enter.native="handleGoodsSearch" :on-icon-click="handleGoodsSearch" ></el-input>
                    </el-form-item>
                </el-form>
            </el-col>
            <el-table ref="multipleTable" :data="provGoodList" highlight-current-row border tooltip-effect="dark"  style="width: 100%" @current-change="handleSelectionChange">
                <el-table-column prop="goodsName" label="商品名称" width="200"></el-table-column>
                <el-table-column prop="code" label="商品编号" width="100" show-overflow-tooltip> </el-table-column>
                <el-table-column prop="mfrsName" label="生产厂家" show-overflow-tooltip> </el-table-column>
                <el-table-column prop="goodsGg" label="规格" show-overflow-tooltip> </el-table-column>
                <el-table-column prop="unit" label="单位" show-overflow-tooltip> </el-table-column>
            </el-table>
            <el-pagination @size-change="handleProvGoodsSizeChange" @current-change="handleProvGoodsCurrentChange" :current-page="provGoodsPage" :page-sizes="[10, 20, 30, 50]" :page-size="provGoodsPageSize" layout="total, sizes, prev, pager, next" :total="provGoodsTotal" style="float:right;" small>
            </el-pagination>
    
            <!-- <div style="margin-top: 20px">
                <el-button @click="toggleSelection()">取消选择</el-button>
                <el-button @click="theSubmit()">确定</el-button>
            </div> -->
        </section>
        <div slot="footer" class="dialog-footer">
            <el-button @click.native="onBarcodeClose" >取消</el-button>
            <el-button type="primary" @click.native="theSubmit" >确定</el-button>
        </div>
    </el-dialog>
</template>

<script>
export default {
    data() {
        return {
            loading: false,
            provGoodList: [],
            multipleSelection: null,
            provGoodsPage: 1,
            provGoodsPageSize: 10,
            provGoodsTotal: 0,
            filters: {
                goodsName: ''
            }
        };
    },
    props:['provId','hosId','mfrsCode','barcodeMatchShow'],
    watch:{
        barcodeMatchShow(curVal, oldVal){
            if(curVal){
                 this.getProvGoodList(1);
            }
        }
    },
    methods: {    
        onBarcodeClose(){
            this.$emit('barcodeMatchClose');
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        handleProvGoodsSizeChange(val) {

            this.provGoodsPageSize = val;

            this.getProvGoodList(this.provGoodsPage);
        },
        handleProvGoodsCurrentChange(val) {
            this.provGoodsPage = val;
            this.getProvGoodList(this.provGoodsPage);
        },
        getProvGoodList: function (pIndex) {
            this.provGoodsPage = pIndex;
            var param = { orderBy: "", queryObject: { provId: this.provId, hosId: this.hosId, hisName: this.filters.goodsName }, page: this.provGoodsPage, rows: this.provGoodsPageSize };
            this.loading = true;
            this.axios.post('/spdHDIService/myGoods/provGoods/getProvGoodsInfoListByProvAndHos_Pages', param).then(res => {
                this.loading = false;
                if (res.data.code == "0") {
                    this.provGoodList = res.data.data.data;
                    this.provGoodsTotal = res.data.data.total;
                }
                else {
                    this.$message.error(res.data.msg);
                }
            }, err => {
                this.loading = false;
            });


        },
        theSubmit: function () {
             this.multipleSelection.mfrsCode = this.mfrsCode;
                this.axios.post('/spdHDIService/myGoods/provGoods/update', this.multipleSelection).then(res => {
                    this.editLoading = false;
                    if (res.data.code == "0") {
                        this.$message({ message: '操作成功！', type: 'success' });
                        this.$emit('barcodeMatchSave');
                    }
                    else {
                        this.$message.error('操作失败！');
                    }
                }, err => {

                });
        },
        handleGoodsSearch: function () {
            this.getProvGoodList(1);
        }

    }
}

</script>