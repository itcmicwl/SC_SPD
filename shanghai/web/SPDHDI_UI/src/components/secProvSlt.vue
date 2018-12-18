<template>
      <el-select v-model="curProvId" style="width:310px" filterable @change="subProvChange">
          <el-option
            v-for="item in lstSubProv"
            :key="item.provId"
            :label="item.provName"
            :value="item.provId">
          </el-option>
      </el-select>
</template>

<script>
export default {
    data() {
        return {
            loading: false,
            lstSubProv:[],
            curProvId:'-1'
        };
    },  
    watch:{
      value:function(cVal,oVal){
        this.curProvId = cVal;
      }
    },  
    props: ['userId', 'value'],
    methods: {
        getSubProvInfo(userId){
           var _this =this;
           if(!userId){
             return;
           }
          this.lstSubProv=[{provId:"-1",provName:"--全部--",hosLst:[]}];
          this.axios.post(`/spdHDIService/collector4orderMgr/hosCollectorProv/getSubProvAndHosByUserId/${userId}`).then(res=>{
              if (res.data.code == "0") {
                if(res.data.data.lstUserHos.length>0){
                  _this.lstSubProv[0].hosLst=res.data.data.lstUserHos;
                }
                if(res.data.data.lstHosProv.length>0){
                  _this.setSubProvLst(res.data.data.lstHosProv);
                } 
                _this.onLoad();
            }
          });
        },
        setSubProvLst(data){
            var _this = this;
           var subProvIds = data.map(o=>o.provId);
           var dstSubProvIds = new Set(subProvIds);
           for(let key of dstSubProvIds){
             var children = data.filter(o=>{return o.provId == key});
             var subProv={provId:key,provName:children[0].provName,hosLst:children};
            _this.lstSubProv.push(subProv);
           }
        },
        subProvChange(cProvId){
          var subProvInfo = this.lstSubProv.find(o=>{return o.provId == cProvId});
          var param = Object.assign({},subProvInfo);
          param.provId = param.provId =="-1"?"":param.provId;
          this.$emit("subProvChange",param);
        },
        onLoad(){
           var subProvInfo = this.lstSubProv.find(o=>{return o.provId == "-1"});
          var param = Object.assign({},subProvInfo);
          param.provId ="";
          this.$emit("onLoad",param);
        }
    },    
    mounted() {
      this.getSubProvInfo(this.userId);
    }
}
</script>

