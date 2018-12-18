<template>
	<div id = 'login'  @keyup.enter="login">
		<h1>欢迎登录</h1>
		<div class="login-form">
			<div class="close"> </div>
				<div class="head-info">
					<label class="lbl-1"> </label>
					<label class="lbl-2"> </label>
					<label class="lbl-3"> </label>
				</div>
					<div class="clear"> </div>
			<div class="avtar">
				<img src="../styles/images/avtar.png" />
			</div>
				<form>
					<input type="text" class="text"  placeholder="请输入用户名"  autocomplete="off" v-model="user.account">
					<div class="key">
						<input type="password" placeholder="请输入密码" autocomplete="off" v-model="user.checkPass">
					</div>
				</form>
			<div class="signin">
				<button class="loginBtn" @click = "login" :loading="logining">登录</button>
			</div>
		</div>
		<div class="copy-rights">
			<p>Copyright &copy; 2017.Company name All rights reserved.
         		 <a href="http://www.cmic.com.cn/" target="_blank" title="国药器械">国药器械</a> 
        	</p>		
		</div>
	</div>
</template>


<script>
import ulogin from '../common/js/userLogin';
import { mapGetters, mapActions } from 'vuex';
import { MessageBox } from 'mint-ui';
export default {
  data() {
    return {
      logining: false,
      user: {
        account: '',
        checkPass: '',
      },
      err: {
        accountErr: "",
      },
      checked: true
    };
  },
  methods: {
    ...mapActions([
      'userLogin'
    ]),
    handleReset2() {
      this.user.account = "";
      this.user.checkPass = "";
    },
    login(ev) {
      var _this = this;
      this.logining = true;
      var loginParams = { username: this.user.account, password: this.user.checkPass, remember: this.checked };
      ulogin(loginParams).then(res => {
        this.logining = false;
        if (res.flag === true) {
          let redirect = decodeURIComponent(_this.$route.query.last || '/');
          _this.$router.push({ path: redirect });
        } else {
          _this.err.accountErr = res.accountErr || res.passwordErr;
          MessageBox('提示',_this.err.accountErr  );
        }
      });
    }
  },
  mounted() {
    var userCode = localStorage.getItem("userCode");
    this.userLogin(null);
    if (userCode) {
      this.user.account = userCode;
    } else {
      this.checked = false;
    }
  }
}
</script>
<style scoped>{
  @import '../styles/css/login1.css';
}
</style>

<style>
	
#login .loginBtn{
	font-size: 20px;
	color: #fff;
	outline: none;
	border: none;
	background: #3ea751;
	width: 100%;
	padding: 18px 0;
	border-bottom-left-radius: 15px;
	-webkit-border-bottom-left-radius: 15px;
	-moz-border-bottom-left-radius: 15px;
	-o-border-bottom-left-radius: 15px;
	border-bottom-right-radius: 15px;
	-webkit-border-bottom-right-radius: 15px;
	-moz-border-bottom-right-radius: 15px;
	-o-border-bottom-right-radius: 15px;
	cursor: pointer;
}
#login .loginBtn:hover {
	background: #ff2775;
  	border-bottom-left-radius: 15px;
	-webkit-border-bottom-left-radius: 15px;
	-moz-border-bottom-left-radius: 15px;
	-o-border-bottom-left-radius: 15px;
	border-bottom-right-radius: 15px;
	-webkit-border-bottom-right-radius: 15px;
	-moz-border-bottom-right-radius: 15px;
	-o-border-bottom-right-radius: 15px;
  	transition: 1s all;
	-webkit-transition: 1s all;
	-moz-transition: 1s all;
	-o-transition: 1s all;
}
</style>


