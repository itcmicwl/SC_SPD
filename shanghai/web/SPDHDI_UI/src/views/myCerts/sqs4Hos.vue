<template>
<el-dialog title="适用医院" :visible.sync="diaShow">
  <el-row style="height:100%;" :gutter="10">
    <el-col :span="14">
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
        <el-table ref="tbl_goodsLst" :data="sqsBill.lstDetail" style="width: 100%;height:330px;" height="outer" >
            <el-table-column  prop="goodsName" label="产品名称"></el-table-column>
            <el-table-column  prop="goodsGg" label="产品规格"></el-table-column>
        </el-table>
    </el-col>
    <el-col :span="10">
        <el-autocomplete :fetch-suggestions="querySearchAsync" placeholder="请输入内容"  @select="sltHosChange"></el-autocomplete>
        <el-table :data="hosList" style="width: 100%;height:400px;" height="outer" highlight-current-row v-loading="hosLoading">
            <el-table-column  prop="hosName" label="医院名称"></el-table-column>
            <el-table-column  label="操作" width="50">
                <template slot-scope="scope">
                    <div class="icoBtn">
                        <i class="fa fa-trash-o fa-lg" @click="delHos(scope.row)" title="删除" style="color:red;margin-left:5px;"></i>
                    </div>
                </template>
            </el-table-column>
        </el-table>
    </el-col>
  </el-row>
  <span slot="footer" class="dialog-footer">
    <el-button @click="cancel">关 闭</el-button>
  </span>
</el-dialog>
</template>
<script>
  export default {
    data() {
      return {
        diaShow:false,
        hosLoading:false,
        hosList:[],
        sqsBill:{
            chiefName:'',
            provName:'',
            isValiding:false,
            expdtBeginDate:new Date(),
            expdtEndDate:new Date(),
            lstDetail:[]
        }
      }
    },
    methods: {
        init(){
            this.sqsBill ={};
            this.hosList=[];
        },
        querySearchAsync(queryString, cb) {
            var param={cname:queryString, kind:1};
            this.axios.post('/spdHDIService/bas/company/companysList',param).then(res=>{
            if(res.data.code == 0){
                res.data.data.forEach(item=>item.value = item.cname);
                cb(res.data.data);
            }
            });
        },
        sltHosChange(hosInfo){
            var _this = this;
            _this.hosLoading = true;
            if(this.hosList.filter(o=>o.id == hosInfo.id).length==0){
                var param =[{sqsId:this.sqsBill.id,provId:this.sqsBill.provId,hosId:hosInfo.id,uxid:_this.user.userId}];
                if(param.length==0) return;
                this.axios.post(`/spdHDIService/provJxsqs/saveSqsHosLst/`,param).then(res=>{
                    if(res.data.code == 0){
                        _this.hosList = res.data.data;
                        _this.hosLoading = false;
                    }
                },err=>{_this.hosLoading = false;_this.$message.error('操作失败');});
            }
        },
        searchHostLst(sqsId){
            var _this = this;
            this.axios.get(`/spdHDIService/provJxsqs/getSqsHosLst/${sqsId}`).then(res=>{
                if(res.data.code == 0){
                    _this.hosList = res.data.data;
                }
            },err=>{});
        },
        delHos(row){
            var _this = this;
            _this.hosLoading = true;
            var param = {id:row.id,version:row.version,sqsId:row.sqsId};
            this.axios.post('/spdHDIService/provJxsqs/delSqsHos',param).then(res=>{
                if(res.data.code==0){
                    _this.hosList = res.data.data;
                    _this.hosLoading = false;
                    _this.$message.success('操作成功!');
                }
            },err=>{_this.hosLoading = false;_this.$message.error('操作失败');});
        },
        show(sqsBill){
            this.sqsBill = Object.assign({},sqsBill);
            this.searchHostLst(this.sqsBill.id);
            this.diaShow = true;
        },

        cancel(){
            this.init();
            this.diaShow = false;
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
