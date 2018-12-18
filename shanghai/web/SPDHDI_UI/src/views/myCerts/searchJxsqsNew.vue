<template>
<el-dialog title="查找授权书" :visible.sync="diaShow">
  <el-row style="height:100%;" :gutter="10" v-loading="sqslstLoading">
    <el-col :span="8">
        <el-row>
            <!--<el-input placeholder="输入品名查询" v-model="sqsfilter.goodsName" class="input-with-select">
                <el-button slot="append" icon="el-icon-search" @click="searchSqs"></el-button>
            </el-input>-->
            <el-table ref="tbl_sqsLst" :data="sqsList" style="width: 100%;height:400px;" row-key="id" height="outer" highlight-current-row  @current-change="sqsChange">
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
            <el-table-column  prop="goodsGg" label="授权情况">
                <template slot-scope="scope">
                    {{scope.row.sqsLastlevelId || '未授权'}}
                </template>
            </el-table-column>
        </el-table>
    </el-col>
  </el-row>
  <span slot="footer" class="dialog-footer">
    <el-button @click="cancel">取 消</el-button>
    <el-button type="primary"  v-loading="submitLoading" @click="submitSqs">完  成</el-button>
  </span>
</el-dialog>
</template>
<script>
  export default {
    data() {
      return {
        cmdKind:-1,
        firstSqsId:'',
        editSqsId:'',
        sqsfilter:{},
        diaShow:false,
        sqslstLoading:false,
        submitLoading:false,
        sqsList:[],
        sqsBill:{
            chiefName:'',
            provName:'',
            isValiding:false,
            expdtBeginDate:new Date(),
            expdtEndDate:new Date(),
            lstDetail:[]
        },
        sltedGoods:[],
        lstGoods:[]
      }
    },
    methods: {
        init(){
            this.sqsBill = {};
            this.sltedGoods=[];
            this.sqsfilter={};
            this.lstGoods=[];
            this.sqsList=[];
            this.cmdKind = -1;
            this.firstSqsId='';
            this.editSqsId='';
        },
        canSltGoods(row){
            if(this.cmdKind == 1){//添加上级授权书
                var count = this.sqsfilter.lstDetail.filter(item=>item.goodsId == row.goodsId && !item.sqsLastlevelId);
                return count.length>0;
            }
            if(this.cmdKind ==2){//编辑
                var count = this.sqsfilter.lstDetail.filter(item=>item.goodsId == row.goodsId);
                return count.length>0;
            }
            return false;//添加当前授权书
        },
        searchSqs(callback){
            var _this = this;
            this.sqsBill = {};
            this.sltedGoods=[];
            this.sqsList=[];
            var param=null;
            if(this.cmdKind==0){
                param={provId:this.user.corpId};
            }else if(this.cmdKind ==1){
               param={provId:this.sqsfilter.chiefId,goodsIds:this.lstGoods};
               if(Array.isArray(this.sqsfilter.children)){
                    param.notInSqsIds = this.sqsfilter.children.map(o=>o.id);
                }
            }else if(this.cmdKind ==2){
                param={sqsId:this.editSqsId};
            }
            this.axios.post('/spdHDIService/provJxsqs/getJxsqsList',param).then(res=>{
                if(res.data.code == 0){
                    _this.sqsList = res.data.data;
                    if(Array.isArray(_this.sqsList) && _this.sqsList.length>0){
                        _this.$nextTick(()=>{
                            _this.$refs.tbl_sqsLst.setCurrentRow(_this.sqsList[0]);
                        });
                    }
                    _this.sqslstLoading = false;
                }
            },err=>{this.sqslstLoading = false;});
        },
        showAndSearch(sqsfilter,cmdKind,firstSqsId,editSqsId){
            var _this = this;
            this.diaShow = true;
            this.sqslstLoading = true;
            this.firstSqsId = firstSqsId;
            this.editSqsId = editSqsId;
            this.cmdKind = cmdKind;
            this.sqsfilter = Object.assign({},sqsfilter);
            if(sqsfilter && sqsfilter.lstDetail){
                this.lstGoods = sqsfilter.lstDetail.map(o=>o.goodsId);
            }
            this.searchSqs();
        },
        sqsChange(cRow){
            if(!cRow) return;
            var _this = this;
            this.sqsBill = Object.assign({},cRow);
            if(this.cmdKind == 0) {
                this.firstSqsId = this.sqsBill.id;
                return;
            }
            this.$nextTick(function(){
                if(_this.cmdKind ==1){                   //添加上级授权书
                    _this.sqsfilter.lstDetail.forEach(item=>{
                        var canslted = _this.sqsBill.lstDetail.find(o=>o.goodsId == item.goodsId && !item.sqsLastlevelId);
                        if(canslted){
                            _this.$refs.tbl_goodsLst.toggleRowSelection(canslted);
                        }
                    });
                }else if(_this.cmdKind ==2){        //编辑
                    _this.sqsfilter.lstDetail.forEach(item=>{
                        var canslted = _this.sqsBill.lstDetail.find(o=>o.goodsId == item.goodsId && item.sqsLastlevelId == o.sqsId);
                        if(canslted){
                            _this.$refs.tbl_goodsLst.toggleRowSelection(canslted);
                        }
                    });
                }
            });
        },
        goodsChange(rows){
            this.sltedGoods=rows;
        },
        cancel(){
            this.init();
            this.diaShow = false;
        },
        getSqsRelation(sqsId){
            return this.axios.get(`/spdHDIService/provJxsqs/getSqsRelationById/${sqsId}`);
        },
        afterSubmit(){
            this.$emit('onSelectJxsqs',this.firstSqsId);
            this.diaShow = false;
            this.init();
            this.submitLoading=false;
            this.sqslstLoading = false;
        },
        submitRelation(param){
            var _this = this;
            _this.axios.post(`/spdHDIService/provJxsqs/saveRelation/${_this.sqsBill.id}`,param).then(res=>{
                if(res.data.code == 0){
                    this.$message.success('操作成功！');
                    _this.afterSubmit();
                }
            },err=>{ this.$message.error('操作失败！');this.submitLoading=false;});
        },
        submitSqs(){
            var _this = this;
            _this.submitLoading = true;
            if(this.sqsBill.id){
                if(this.cmdKind ==1){
                    if(Array.isArray(_this.sltedGoods) && _this.sltedGoods.length>0){
                        var param = [];
                            _this.sqsfilter.lstDetail.forEach(o=>{
                                if(_this.sltedGoods.filter(item =>item.goodsId == o.goodsId).length>0){
                                    param.push({id:o.id,version:o.version,sqsLastlevelId:_this.sqsBill.id});
                                }
                            });
                        this.submitRelation(param);
                    }else{
                         this.$message.error('请选择一个授权书并且勾选相应产品');
                    }
                }else if(this.cmdKind == 2){    //编辑
                    var toAdd = [],toRemove=[];
                    var oldSlt = _this.sqsfilter.lstDetail.filter(o=>o.sqsLastlevelId == _this.sqsBill.id).map(o=>o.goodsId);
                    var newSlt = this.sltedGoods.map(o=>o.goodsId);
                    var toAdds = this.tools._.difference(newSlt,oldSlt);
                    var toRemoves = this.tools._.difference(oldSlt,newSlt);
                    toAdd = _this.sqsfilter.lstDetail.filter(o=>o.sqsLastlevelId != _this.sqsBill.id && toAdds.indexOf(o.goodsId)>= 0).map(o=>{return {id:o.id,version:o.version,sqsLastlevelId:_this.sqsBill.id}});
                    toRemove = _this.sqsfilter.lstDetail.filter(o=>o.sqsLastlevelId == _this.sqsBill.id && toRemoves.indexOf(o.goodsId)>= 0).map(o=>{return {id:o.id,version:o.version}});
                    this.submitRelation(toAdd.concat(toRemove));
                }else{
                    this.afterSubmit();
                }
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
