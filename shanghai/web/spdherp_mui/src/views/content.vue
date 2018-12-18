<template>
	<div id = 'content'>
		<mt-header title="">
			<div slot="left">
				<mt-button icon="back" @click.native="logout">退出</mt-button>
			</div>
		</mt-header>
		<div class="content-wrapper" style='margin-top:2px;'>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="alert alert-success">
					 	<div class="row">
							<div class="media">
								<a class="media-left" href="#">
                                    <img src="../styles/images/login-logo1.png" alt="" class="img-responsive" />
                                </a>
								<br>
							</div>
					   </div>
					
					中国医药集团（SINOPHARM,简称“国药集团“）是由国务院国资委直属的国内唯一专注于医疗健康领域，规模最大、产业链最全、综合实力最强的世界500强企业集团。
					<br>中国医疗器械有限公司（CMDC,简称“国药器械”），始建于1966年，隶属于国药集团，是其医疗器械板块的主力军。
					
                    </div>
                </div>
            </div>
      
            <div class="row">
                 <div class="col-md-3 col-sm-3 col-xs-6">
                    <div class="dashboard-div-wrapper bk-clr-one" v-on:click="handleHref(1)">
                        <i class="fa fa-venus dashboard-div-icon"></i>
                        <div class="progress progress-striped active">
						  <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">
						  </div>
						</div>
                        <h5>订单管理</h5>
                    </div>
                </div>
                 <div class="col-md-3 col-sm-3 col-xs-6">
                    <div class="dashboard-div-wrapper bk-clr-two"  v-on:click="handleHref(2)">
                        <i class="fa fa-edit dashboard-div-icon"></i>
                        <div class="progress progress-striped active">
					     <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" style="width: 70%">
					     </div>   
					  </div>
                      <h5>结算管理 </h5>
                    </div>
                </div>
                 <div class="col-md-3 col-sm-3 col-xs-12">
                    <div class="dashboard-div-wrapper bk-clr-three"  v-on:click="handleHref(3)">
                        <i class="fa fa-cogs dashboard-div-icon"></i>
                        <div class="progress progress-striped active">
							<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
							</div>
                           
						</div>
                         <h5>手术计划 </h5>
                    </div>
                </div>
                <div class="col-md-3 col-sm-3 col-xs-12">
                    <div class="dashboard-div-wrapper bk-clr-four"  v-on:click="handleHref(1)">
                        <i class="fa fa-bell-o dashboard-div-icon"></i>
                        <div class="progress progress-striped active">
						  <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width: 50%">
						  </div>
                           
						</div>
                        <h5>采购管理 </h5>
                    </div>
                </div>

            </div>
           
            
        </div>
    </div>
	</div>
</template>
<script>
import { MessageBox } from 'mint-ui';
export default {
	data() {
		return {
		}
	},
	computed: {
	},
	methods: {
		handleHref:function(href){
			let redirect = decodeURIComponent(this.$route.query.last || '/');
         	this.$router.push({ path: '/orderConfirm' });//views/orderMgr/orderComfirm.vue
		},

		//退出登录
		logout: function () {
			MessageBox.confirm('您确定退出系统吗?').then(action => {
				this.logining = true;
				this.$router.replace("/login");
				this.axios.get(`/platform/sys/login/exit`);
			});
		},

	},
	mounted() {
	}
}

</script>

<style scoped>{
	@import '../styles/css/bootstrap.css';
	@import '../styles/css/font-awesome.css';
	@import '../styles/css/content.css';
}
</style>

<style>
	
	#content h1.mint-header-title {
		margin-top: 0px; 
    	margin-bottom: 0px; 
	}
</style>