<template>
    <el-dialog title="权限不足" :visible.sync="show403" :show-close="false" size="tiny" :close-on-press-escape="false" :close-on-click-modal='false'>
        <div class="page-container">非常抱歉!您没有此页面的访问权限...</div>
        <div slot="footer" class="dialog-footer">
            <el-button @click.native="goLastPage" size="small">返回</el-button>
            <el-button type="primary" @click.native="changeUser" size="small">切换账号</el-button>
        </div>
    </el-dialog>
</template>
<script>
export default {
    name: 'loginDailog',
    data() {
        return {
            show403: true
        };
    },
    methods: {
        changeUser() {
            this.$router.push({ path: '/login' });
        },
        goLastPage(){
            let redirect = decodeURIComponent(this.$route.query.last || '/');
            this.$router.push({ path: redirect });
        }
    },
    mounted() {
    }
}

</script>
<style lang="scss" scoped>
.page-container {
    font-size: 20px;
    text-align: center;
    color: rgb(192, 204, 218);
}
</style>