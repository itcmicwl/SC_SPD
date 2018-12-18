<template>
    <section>
        <el-dialog title="产品详情" v-loading = "loading" close="onClose" v-model="editFormVisible" :close-on-click-modal="false" size="small">
            <el-form :model="good"  label-width="100px"  class="el-form-item-nomsg">
                    <el-row>
                        <el-col><el-form-item label="医院商品名称" ><el-input v-model="good.goodsName" size="small" readonly></el-input></el-form-item></el-col>    
                    </el-row>
                    <el-row>
                        <el-col :span="16"><el-form-item label="供应商名称"><el-input v-model="good.provName" size="small" readonly></el-input></el-form-item></el-col> 
                        <el-col :span="8"><el-form-item label="商品分类"><el-input v-model="good.hosKindName" size="small" readonly></el-input></el-form-item></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="8"><el-form-item label="医院商品编号"><el-input v-model="good.code" size="small" readonly></el-input></el-form-item></el-col>
                        <el-col :span="16"><el-form-item label="生产厂家"><el-input v-model="good.mfrsName" size="small" readonly></el-input></el-form-item></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="8"><el-form-item label="产地"><el-input v-model="good.made" size="small" readonly></el-input></el-form-item></el-col>                                
                        <el-col :span="16"><el-form-item label="规格型号"><el-input v-model="good.goodsGg" size="small" readonly></el-input></el-form-item></el-col>
                    </el-row>                        
                    <el-row>                                
                        <el-col :span="8"><el-form-item label="出厂编码"><el-input v-model="good.mfrsCode" size="small" readonly></el-input></el-form-item></el-col>
                        <el-col :span="8"><el-form-item label="单位"><el-input v-model="good.unit" size="small" readonly></el-input></el-form-item></el-col>
                        <el-col :span="8"><el-form-item label="包装"><el-input v-model.number="good.packeage" size="small" readonly></el-input></el-form-item></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12"><el-form-item label="统一码"><el-input v-model="good.masterCode" size="small" readonly></el-input></el-form-item></el-col>
                        <el-col :span="12"><el-form-item label="68分类">
                                <el-select v-model="kind68code" filterable placeholder = "请选择" style='width:100%' size="small" >
                                    <el-option 
                                        v-for="item in dict68s"
                                        :key="item.val"
                                        :label="item.ename" 
                                        :value="item.val">
                                        <span style="float: left">{{ item.ename }}</span><span style="float: right; color: #8492a6; font-size: 13px">{{ item.val }}</span>
                                    </el-option>
                                </el-select>
                                <!--<el-input v-model="good.kind68code" size="small" ></el-input>-->
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12"><el-form-item label="价格"><el-input v-model.number="good.price" size="small" readonly></el-input></el-form-item></el-col>
                        <el-col :span="12"><el-form-item label="医院厂商名称"><el-input v-model="good.hosMfrsName" size="small" readonly></el-input></el-form-item></el-col>  
                    </el-row>
                    <el-row>
                        <el-col :span="12"><el-form-item label="阳光采购编码"><el-input v-model="good.hitCode" size="small" readonly></el-input></el-form-item></el-col>
                        <el-col :span="12"><el-form-item label="阳光采购价"><el-input v-model.number="good.hitPrice" size="small" readonly></el-input></el-form-item></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12"><el-form-item label="erp编码"><el-input v-model="good.erpCode" size="small" readonly></el-input></el-form-item></el-col>
                        <el-col :span="12"><el-form-item label="平台编码"><el-input v-model="good.spdGoodsCode" size="small" readonly></el-input></el-form-item></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12"><el-form-item label="HIS销售价"><el-input v-model.number="good.hisPrice" size="small" readonly></el-input></el-form-item></el-col>
                        <el-col :span="12">
                            <el-form-item label="税率">
                                    <el-select v-model="taxRate" filterable placeholder="请选择" size="small" style="width:100%">
                                        <el-option
                                            v-for="item in SL"
                                            :key="item.ename"
                                            :label="item.val" 
                                            :value="item.ename">
                                        </el-option>
                                </el-select>
                                <!--<el-input v-model="good.taxRate" size="small" ></el-input>-->
                            </el-form-item>
                         </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12">
                                <el-form-item label="唯一码管理策略">
                                    <el-select v-model="uniqueCodeStrategy" filterable placeholder="请选择" size="small"  style="width:100%">
                                        <el-option
                                            v-for="item in dictUCodeStrategys"
                                            :key="item.ename"
                                            :label="item.ename"
                                            :value="item.val">
                                        </el-option>
                                    </el-select>
                                    <!--<el-input v-model="good.uniqueCodeStrategy" size="small" ></el-input>-->
                                </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col ><el-form-item label=" 备注信息"><el-input v-model="good.remark" size="small" readonly></el-input></el-form-item></el-col>
                    </el-row>
            </el-form>  
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editFormVisible = false">取消</el-button>
            </div>
        </el-dialog>
    </section>
</template>
<script>
export default {
    data() {
        return {
            dictUrl:'/platformService/sys/dict',
            baseUrl:'/spdHERPService/myGoods/hosGoods',
            loading:false,
            // 产品信息
            good:{},
            // 税率
            SL:[],
            taxRate:'',
            // 68分类
            dict68s:[],
            kind68code:'',
            // 唯一码管理
            dictUCodeStrategys:[],
            uniqueCodeStrategy:''
        };
    },
    props: ['goodsId'],
    computed: {
        editFormVisible :{
            get:function(){
                this.getHosGoodsInfoById();
                return this.$store.state.dialog.visiable;
            },
            set(val){
                this.$store.state.dialog.visiable = val;
            }
        }
    },
    methods: {
        getHosGoodsInfoById:function(){
            var param = {id : this.goodsId};
            this.loading = true;
            if(this.goodsId){
                this.axios.post(this.baseUrl + '/getHosGoodsInfoById', param).then(res => {
                    if(res.data.code == '0' && res.data.data){
                        this.good = res.data.data;
                        this.taxRate = this.good.taxRate;
                        this.kind68code = this.good.kind68code;
                        this.uniqueCodeStrategy = this.good.uniqueCodeStrategy;
                        this.loading = false;
                    }
                }, err => {
                    this.loading = false;
                });
            }
        },
        getSL: function () {
            this.loading = true;
            this.axios.post(this.dictUrl + '/getDictValueByDictEname',{dictName:'SL'}).then(res => {                    this.loading = false;
                if (res.data.code == "0") {
                    this.SL = res.data.data;
                }
                else{
                    this.$message.error( res.data.msg);
                }
            }, err => {
                this.loading = false;
            });
        },
        getDict68s: function () {
            this.loading = true;
            this.axios.post(this.dictUrl + '/getDictValueByDictEname',{dictName:'68FL'}).then(res => {                    this.loading = false;
                if (res.data.code == "0") {
                    this.dict68s = res.data.data;
                }
                else{
                    this.$message.error( res.data.msg);
                }
            }, err => {
                this.loading = false;
            });
        },
        getDictUCodeStrategys:function(){
            this.loading = true;
            this.axios.post(this.dictUrl + '/getDictValueByDictEname',{dictName:'WYMGLCL'}).then(res => {
                if (res.data.code == "0") {
                    this.dictUCodeStrategys = res.data.data;
                }
                else{
                    this.$message.error( res.data.msg);
                }
                    this.loading = false;
                }, err => {
                this.loading = false;
            });
        }
    },
    mounted() {
        this.getSL();
        this.getDict68s();
        this.getDictUCodeStrategys();
    }
};
</script>