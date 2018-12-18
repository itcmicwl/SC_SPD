<template>
	<el-row class="container">
		<el-col :span="24" class="layOutHeader">		
			<el-col :span="18" class="logo">
				<el-col :span="6">
					<img :src="getLogoPath()" v-if="user.corpLogo && imgNotError" class="corpLogo" @error="imgError"></img>
				</el-col>
				<el-col :span="18" class="div_nav">
					<ul class="nav">
						<li class="nav-item" v-for="item in getHomeNavs" v-if="!item.hidden" :key="item.path">
							<router-link :to="item.path">{{item.name}}</router-link>
						</li>
					</ul>
				</el-col>				
			</el-col>
			<el-col :span="4"  v-if="user">
				<div class="i-msg">
						<a><router-link to="/baseData/baseInfo">{{user.ename}}</router-link></a>						
						<el-popover ref="p_msg" v-model="msgPopover"	placement="bottom" width="400" trigger="click" popper-class="p-msg" @show="onMsgShow" @hide="onMsgHide">
							<div v-show="!showDetailMsg">
								<div class="el-popover__title">通知</div>
								<dl class="lst_notice" >
									<dt v-for="(item, index) in getSpdMsg" :key="item.id" @click="showDetail(item.id)"><i></i><span class="notice_title">{{item.title}}</span><span class="notice_date">{{item.writeDatetime | dateFormat}}</span></dt>
								</dl>
							</div>
							<div v-show="showDetailMsg">
								<dl class="msgInfo" v-if="msgDetail">
										<dt>
											<el-col :span="2">
												<i class="fa fa-mail-reply back" @click="showDetailMsg = false" title="返回消息列表"></i>
											</el-col>
											<el-col :span="16">
												<span class="notice_title" :title="msgDetail.title">{{msgDetail.title}}</span>												
											</el-col>
											<el-col :span="6">
												<div class="up_down">
													<el-button size="mini" type="text" :disabled="currentMsgIndex<=0" @click="msgPrev()">上一条</el-button>
													<el-button size="mini" type="text" :disabled="currentMsgIndex>=getSpdMsg.length-1" @click="msgNext()">下一条</el-button>
												</div>
											</el-col>
										</dt>
										<dd class="dd_content"><span class="notice_content">{{msgDetail.message}}</span><span class="notice_date">{{msgDetail.writeDatetime | dateFormat}}</span></dd>
										<dd class="dd_foot" v-show="msgDetail.url"><el-button size="mini" type="text" @click="dealMsg(msgDetail.url)">现在处理</el-button></dd>
								</dl>								
							</div>							
						</el-popover>	
						<el-badge :value="getSpdMsg.length" class="msg-bar">
							<i class="fa fa-bell-o gyqx_color" v-popover:p_msg title="您有新的未读消息"></i>
						</el-badge>
				</div>
			</el-col>
			<el-col :span="2" class="userinfo" v-if="user">		
				<el-dropdown trigger="click">
					<span class="el-dropdown-link userinfo-inner"><img src="../assets/user.png"></img></span>						
					<el-dropdown-menu slot="dropdown">
						<el-dropdown-item @click.native="showThemeDialog=true">切换主题</el-dropdown-item>
						<el-dropdown-item divided @click.native="userSetting">个人设置</el-dropdown-item>
						<el-dropdown-item divided @click.native="cleanCache">清空缓存</el-dropdown-item>
						<el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
					</el-dropdown-menu>
				</el-dropdown>
			</el-col>
			<el-col :span="4" class="userinfo" v-else>
				<a><router-link to="/login">您当前未登陆，请登陆</router-link></a>
			</el-col>		
		</el-col>
		<el-dialog title="切换主题" close="onClose" v-model="showThemeDialog" :close-on-click-modal="false" size="tiny">
			<div>
				<el-button type="primary" icon="edit" size="small"></el-button>
				<el-button type="primary" icon="fa-skype" size="small"></el-button>
				<el-select v-model="currentTheme" placeholder="请选择">
					<el-option v-for="item in themeData" :key="item.value" :label="item.value" :value="item.value">
						<span style="float: left;width:50%;height:100%">{{ item.value }}</span>
						<span style="float: right;width:50%;height:100%" :class="item.class"></span>
					</el-option>
				</el-select>
			</div>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="showThemeDialog = false">取消</el-button>
				<el-button type="primary" @click.native="changeTheme">切换</el-button>
			</div>
		</el-dialog>
	</el-row>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';
import axios from 'axios';
import { appConfig } from '../config/appConfigs';
export default {
	name: 'home',
	data() {
		return {
			sysName: 'VUEADMIN',
			collapsed: false,
			imgNotError:true,
			showDetailMsg:false,
			msgPopover:false,
			currentMsgIndex:-1,
			msgDetail:null,//消息明细
			showThemeDialog: false,
			currentTheme: '',
			themeData: [
				{
					value: 'default',
					class: "theme_default"
				},
				{
					value: "orange",
					class: "theme_orange"
				}
			]
		}
	},
	computed: {
		...mapGetters(["getHomeNavs","getSpdMsg","getLoginFlag"])
	},
	watch:{
			getLoginFlag(newVal,oldVal){
					if(newVal===true){
							this.clearInv();
					}else{
							this.startInv();
					}
			}
    },
	methods: {
		...mapActions([
			'userLogin','clearCache','setSpdMsg'
		]),
		dealMsg(url){
			if(url){
					this.$router.push(url);
					this.msgPopover = false;
			}
		},
		onMsgShow(){
			this.showDetailMsg = false;
			this.clearInv();
		},
		onMsgHide(){
			// this.startInv();
		},
		showDetail(msgId){
			this.showDetailMsg = true;
			this.currentMsgIndex = this.getSpdMsg.findIndex(item=>item.id==msgId);
			this._setMsgDetail();
		},
		msgPrev(){
			this.currentMsgIndex--;
			this._setMsgDetail();
		},
		msgNext(){
			this.currentMsgIndex++;
			this._setMsgDetail();
		},
		_setMsgDetail(){
				if(this.currentMsgIndex>=0 && this.currentMsgIndex < this.getSpdMsg.length){
					this.msgDetail = this.getSpdMsg[this.currentMsgIndex];
					this.setMsgRead(this.msgDetail);
				}else{
					this.msgDetail = null;
				}
		},
		getMsgList(){
			var _this = this;
			if(this.user){
				var param = {projectCode:appConfig.appCode,userId:this.user.userId}
				this.axios.post(`/platformService/sys/sysMessage/getProUserMsgList`,param).then(res=>{
					if(res.data.code == 0){
						_this.setSpdMsg(res.data.data);
					}
				});
			}
		},
		setMsgRead(msg){
			var param = {messageId:msg.id,userId:this.user.userId}
				this.axios.post(`/platformService/sys/sysMessageConfirm/setMsgRead`,param).then(res=>{
					if(res.data.code == 0){
						var tmpMsg = this.getSpdMsg;
						tmpMsg.splice(this.currentMsgIndex,1);
						this.setSpdMsg(tmpMsg);
					}
				});
		},
		startInv(){
			// this.invId = setInterval(this.getMsgList, 1000*30);
		},
		clearInv(){
			 clearInterval(this.invId);
		},
		getLogoPath(){
			return "/upload/" + this.user.corpLogo;
			//return "../assets/tjlog.jpg"
		},
		imgError(){
			this.imgNotError = false;
		},
		onSubmit() {
		},
		handleopen() {
		},
		handleclose() {
		},
		handleselect: function (a, b) {
		},
		cleanCache: function () {
			window.localStorage.clear();
			this.clearCache();
			this.$router.push("/login");
			//window.location.href = "/login";
		},
		//退出登录
		logout: function () {
			var _this = this;
			this.$confirm('确认退出吗?', '提示', {
			}).then(() => {
				_this.logining = true;
				this.clearInv();
				//_this.userLogin(null);
				_this.$router.replace("/login");
				axios.get(`/platformService/sys/login/exit`);
			}).catch(() => {

			});
		},
		changeTheme: function () {
			this.themeOP.changeTheme(this.currentTheme);
			this.showThemeDialog = false;
		},
		userSetting() {
			this.$router.push("/baseData/baseInfo");
		}
	},
	mounted() {
		this.currentTheme = LocalStorage.getItem('theme') || "default";	
		this.startInv();		
	},
	destroyed () {
		this.clearInv();
	}
}
</script>

<style lang="scss">
//@import '~scss_vars';
body {
	margin: 0px;
	padding: 0px;
	font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, SimSun, sans-serif;
	font-size: 12px;
	-webkit-font-smoothing: antialiased;
	#app {
		position: absolute;
		top: 0px;
		bottom: 0px;
		width: 100%;
		.container {
			position: absolute;
			top: 0px;
			bottom: 0px;
			width: 100%;
			.layOutHeader {
				height: 80px;
				line-height: 80px;
				color: #fff; //background: #20a0ff;
				//background: $color-primary;
				.i-msg{
					padding-right: 10px;
					float:right;
					.msg-bar{						
						.el-badge__content.is-fixed{
							top:25px;
						}
  					//margin-right: 40px;
					}
					a{
						text-decoration:none;
					}
					i{
							font-size:22px;
						cursor: pointer;
					}					
				}
				.userinfo {				
					.userinfo-inner {
						cursor: pointer;
						img {
							width: 60px;
							height: 60px;
							border-radius: 20px;
							margin: 10px 0px 10px 10px;
							float: right;
						}						
					}
				}
				.logo {
					//width:230px;
					height: 80px;
					font-size: 22px; //padding-left: 20px;
					//padding-right: 20px;
					background-color: #f7f9fa;
					background: url(../styles/images/top.png) no-repeat;
					img {
						//height: 40px;
						float: left; //margin: 10px 10px 10px 10px;
					}
					.corpLogo{
							//width: 100%;
							height: 80px;
							margin-left: 200px;
					}
					.txt {
						color: #fff;
					}
				}
				.div_nav{
						float: right;
				}
				.nav {
					float: right;
					height: 100%;
					line-height: 80px;
					background: transparent;
					padding: 0;
					margin: 0;
					.nav-item {
						margin: 0;
						float: left;
						list-style: none;
						position: relative;
						cursor: pointer; //margin-left: 20px;
						a {
							text-decoration: none;
							color: #9FA8B1;
							display: block;
							padding: 0 10px;
							opacity: .9;
							font-size: 14px;
						}
						a.router-link-active {
							font-weight: 700;
						}
						a.router-link-active:before {
							content: "";
							display: block;
							position: absolute;
							bottom: 0;
							left: 0;
							width: 100%;
							height: 4px;
						}
					}
				}
			}
		}
	}
}

.el-dropdown-item,
.el-dropdown-menu__item {
	line-height: 30px;
}

.el-dropdown-menu__item--divided {
	margin-top: 0px;
}


.p-msg{
	padding:0px;
	.el-popover__title{
		padding:10px 0px 0px 10px;
	}
	.lst_notice{
		margin: 0px;
		max-height: 300px;
		overflow-y: auto;
		dt{
				border-top: 1px solid #ccc;
				padding: 15px 5px 15px 10px;
				cursor: pointer;
			.notice_title{
				font-size: 13px;
				font-weight: 400;
				color: #999;
			}
			.notice_date{
					font-size: 12px;
				float: right;
				margin: 0 0 0 5px;
				color:#ccc;
			}
		}
		dt:hover{
			background-color: rgb(240, 248, 255);
		}	
	}
	.msgInfo{
		margin: 0px;
		max-height: 300px;
		overflow-y: auto;
		dt{
				height:20px;			
    		padding: 10px 5px 2px 10px;
			.notice_title{
				-webkit-line-clamp: 1;  
				overflow: hidden;  
				display: -webkit-box;  
				-webkit-box-orient: vertical;  
				white-space: normal;  
				font-size: 13px;
				font-weight: 600;
				color: #737070;
			}			
		}
		dd.dd_content{
			margin: 0px;
    	padding: 10px 5px 10px 22px;
			border-top: 1px solid #ccc;
			.notice_content{
				font-size: 12px;
				font-weight: 400;
				color: #6f6b6b;
			}
			.notice_date{
				font-size: 12px;
				float: right;
				margin: 0 0 0 5px;
				color:#ccc;
			}
		}
		dd.dd_foot{
			margin: 0px;
    	padding: 5px 5px 5px 300px;
			border-top: 1px solid #ccc;			
		}
	}
	i.back{
		cursor: pointer;
	}

}





/**
	主题样式
*/

.theme_orange {
	color: #ff7500;
	background-color: #ff7500;
}

.theme_default {
	color: #20a0ff;
	background-color: #20a0ff;
}
</style>