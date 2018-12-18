<template>
<el-dialog title="查找授权书" :visible.sync="diaShow">
  <el-row style="height:100%;" :gutter="10">
    <el-col :span="8">
        <el-row>
            <el-input placeholder="输入品名查询" v-model="sqsfilter.goodsName" class="input-with-select">   
                <el-button slot="append" icon="el-icon-search" @click="searchSqs"></el-button>
            </el-input>            
            <el-table :data="sqsList" style="width: 100%;height:400px;" height="outer" highlight-current-row v-loading="sqslstLoading" @current-change="sqsChange">
                <el-table-column  prop="chiefName" label="授权单位"></el-table-column>
            </el-table>
        </el-row>
    </el-col>
    <el-col :span="16">
        <el-form label-width="75px">
            <el-col :span="24">
                <el-form-item label="授权单位:">
                    <span>{{ sqsBill.chiefName }}</span><i class="fa fa-long-arrow-right" style="font-size:16px;margin: 0 10px; color: #50bffe;"></i><span>{{ sqsBill.provName }}</span>
                </el-form-item>
            </el-col>
            <el-col :span="8">
                <el-form-item label="长期有效:">
                    <span>{{ sqsBill.isValiding==0?'否':'是' }}</span>
                </el-form-item>
            </el-col>
            <el-col :span="16">
                <el-form-item label="起止时间:">
                    <span>{{ sqsBill.expdtBeginDate | dateFormat }}/{{sqsBill.expdtEndDate | dateFormat}}</span>
                </el-form-item>
            </el-col>
        </el-form>
        <el-table ref="tbl_goodsLst" :data="sqsBill.lstDetail" style="width: 100%;height:330px;" height="outer" @selection-change='goodsChange' >
            <el-table-column type="selection" width="55" :selectable='canSltGoods'></el-table-column>
            <el-table-column  prop="goodsName" label="产品名称"></el-table-column>
            <el-table-column  prop="goodsGg" label="产品规格"></el-table-column>
        </el-table>
    </el-col>
  </el-row>
  <span slot="footer" class="dialog-footer">
    <el-button @click="cancel">取 消</el-button>
    <el-button type="primary" @click="submitSqs">确 定</el-button>
  </span>
</el-dialog>
</template>
<script>
  export default {
    data() {
      return {
        sqsfilter:{
            provId:'',
            chiefId:'',
            goodsId:'',
            goodsName:'',
            goodsIds:[],
        },
        diaShow:false,
        sqslstLoading:false,
        sqsList:[],
        sqsBill:{
            chiefName:'',
            provName:'',
            isValiding:false,
            expdtBeginDate:new Date(),
            expdtEndDate:new Date(),
            lstDetail:[]
        },
        sltedGoods:[]
      }
    },    
    methods: {
        init(){
            this.sqsBill = {};
            this.sltedGoods=[];
            this.sqsList=[];
        },
        canSltGoods(row){
            return !row.sqsLastlevelId;
        },
        searchSqs(callback){
            var _this = this;
            this.init();
            var param={
                provId : this.sqsfilter.provId,
                chiefId : this.sqsfilter.chiefId,
                goodsId : this.sqsfilter.goodsId,
                goodsIds: this.sqsfilter.goodsIds
            };
            this.axios.post('/mdqcService/provJxsqs/getJxsqsList',param).then(res=>{              
                if(res.data.code == 0){
                    _this.sqsList = res.data.data;
                    _this.diaShow = true;
                    // if(_this.sqsList.length == 1 && typeof(callback) == 'function'){
                    //     //callback(_this.sqsList[0]);
                    // }else{
                    //     _this.diaShow = true;
                    // }
                }
            },err=>{});
        },        
        showAndSearch(sqsfilter){
            var _this = this;
            this.sqsfilter = Object.assign({},sqsfilter);
            this.sqsfilter.goodsIds = this.sqsfilter.goodsIds || [];
            this.searchSqs(function(data){
                _this.$emit('onSelectJxsqs',data);
            });  
        },
        checkCanEmit(sqsBill,goodsIds){
            var res = false;
            if(goodsIds.length == 0){
                res = true;
            }else{
                for(var i=0;i<goodsIds.length;i++){
                    res = sqsBill.lstDetail.filter(o=>o.goodsIds == goodsIds[i]).length>0;
                    if(res){
                        break;
                    }
                }
            }            
            return res;
        },
        sqsChange(cRow){
            if(!cRow) return;
            var _this = this;
            this.sqsBill = Object.assign({},cRow);
            this.$nextTick(function(){
                if(_this.sqsfilter.goodsIds.length == 0){
                    _this.sqsBill.lstDetail.forEach(item=>{
                        if(!item.sqsLastlevelId){
                            _this.$refs.tbl_goodsLst.toggleRowSelection(item,true);
                        }
                    });
                }else{
                    _this.sqsfilter.goodsIds.forEach(item=>{
                        var canslted = _this.sqsBill.lstDetail.find(o=>o.goodsId == item && !o.sqsLastlevelId);
                        if(canslted){
                            _this.$refs.tbl_goodsLst.toggleRowSelection(canslted);
                        }
                    });
                }
            })
        },
        goodsChange(rows){
            this.sltedGoods=rows;
        },
        cancel(){
            this.init();
            this.diaShow = false;
        },
        submitSqs(){
            if(this.sqsBill.id && this.sltedGoods.length>0){
                this.sqsBill.sltedGoods = this.sltedGoods;
                this.sqsBill.children=[];
                this.$emit('onSelectJxsqs',this.sqsBill);
                this.diaShow = false;
                this.init();
            }else{
                this.$message.error('请选择一个授权书并且勾选相应产品');
            }
        }
    },
    mounted() {
    }
  };

</script>
<style scoped>
.el-form-item--mini.el-form-item, .el-form-item--small.el-form-item {
    margin-bottom: 5px;
}
</style>
