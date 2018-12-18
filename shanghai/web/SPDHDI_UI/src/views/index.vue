<template>
    <el-row>
        <index4Hos v-if="userKind == '1'"></index4Hos>
        <index4Hos v-else-if="userKind == '2'"></index4Hos>
    </el-row>  
</template>
<script>
import index4Hos from './index/index4Hos';
import index4Prov from './index/index4Prov';
export default {
        data() {
            return {
                userKind:''
            }
        },        
        methods: {
           
        },
         components: {
            index4Hos,index4Prov
        },
        mounted: function () {
            this.userKind = this.user.corpKind;
        }
    }
</script>

<style scoped>

</style>