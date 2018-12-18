<template>
    <section>    
        <el-form-item label="入库库房">
            <el-cascader :options="stckInfos" size="small" 
                v-model="stockInfo"
                :props="stckProps" :show-all-levels="false" 
                :change-on-select="false" @change="test">  
            </el-cascader>
        </el-form-item>
    </section>
</template>

<script>
export default {
    data() {
        return {
            loading: false,
            stckInfos:[
                  {id:0,value:0,label:"一级库房", children:[]},
                  {id:1,value:1,label:"二级库房", children:[]},
                  {id:2,value:2,label:"三级库房", children:[]}
            ],
            stckProps:{},
            d:{ inStocId: '', inStocCode: '', inStocName: '' },
             stocNum:{
                '一级库房':'0',
                '二级库房':'1',
                '三级库房':'2'
            },
              stocs: [
                {
                    value: '一级库房'
                },
                {
                    value: '二级库房'
                },
                {
                    value: '三级库房'
                }
            ],
          
        };
    },
 
    props: [ 'hosId', 'userId','inStocCode'],
    methods: {
           
          getStocsByParam: function () {
              this.stckInfos=[
                  {id:0,value:0,label:"一级库房", children:[]},
                  {id:1,value:1,label:"二级库房", children:[]},
                  {id:2,value:2,label:"三级库房", children:[]}
            ];
            let param = {hosId:this.hosId,userId:this.userId};
         
            this.axios.post( `/spdHERPService/deptMgr/stocInfo/getStocksByUser`, param).then(res => {
                let map = res.data.data;
                let nodes = {};
                for(let key in map){
                    if(key == 'stocInfoList'){
                        nodes = map[key];   
                    }
                }
               for(let i = 0; i < nodes.length; i++){                    
                   if(nodes[i].stockLevel == this.stocs[this.stocNum[nodes[i].stockLevel]].value){
                       this.stckInfos[this.stocNum[nodes[i].stockLevel]].children.push({
                           id: nodes[i].stockId, value:nodes[i].stockId, label:nodes[i].stockName
                       });
                   } 
            } 
        });
        
        // this.$emit('sltChange', []);         
    },
        



        test(dx){

             this.$emit('sltChange', dx);             
        },
         getStockByDeptChange(){
            this.getStocsByParam();
            this.$emit('sltChange', []);
        }
       
    },
    computed: {
           stockInfo: {
            get: function () {
                var res = [];
                if (this.inStocCode) {
                    let path = this.inStocCode.split('.');
                    if (path.length > 1) {
                       res.push(parseInt(path[0]));
                       res.push(path[1]);
                    } else {
                        res = path;
                    }
                   
                }
                
               // res= [0,'stoc-010'];
              
                return res;
            },
            set: function (pathArr) {
                let res = { inStocId: '', inStocCode: '', inStocName: '' };
                if (pathArr.length > 0) {
                    //res = pathArr[pathArr.length - 1];
                    var tmpArr = this.stockInfo.concat();
                    for (var i = 0; i < pathArr.length; i++) {
                        if (tmpArr) {
                            var node = tmpArr.find(item => item.stockCode == pathArr[i]);
                            if (node) {
                                tmpArr = node.children;
                                res.inStocCode = node.stockCode;
                                res.inStocId = node.id;
                                res.inStocName = node.label;
                            }
                        }
                    }
                }
                this.d.inStocId = res.inStocId;
                this.d.inStocCode = res.inStocCode;
                this.d.inStocName = res.inStocName;
                
            }
        },
        
    },
    mounted() {
      //  alert(this.userId);
        this.getStocsByParam();
        
    }
}

</script>

