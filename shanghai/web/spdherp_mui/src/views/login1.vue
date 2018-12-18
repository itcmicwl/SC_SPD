<template>
  <div id = "login" @keyup.enter="login">
    <h1>SPD医院院内物资管理系统</h1>
    <div class="app-location">
      <h2>欢迎登录</h2>
      <div class="line"><span></span></div>
      <div class="location"><img src="../styles/images/location.png" class="img-responsive" alt="" /></div>
      <form>
        <input  type="text" class="text" placeholder="请输入用户名"  autocomplete="off" v-model="user.account" >
			  <input type="password" placeholder="请输入密码" autocomplete="off" v-model="user.checkPass">
			  <div class="submit">
          <mt-button type="primary"  @click.native.prevent="login" :loading="logining">登录</mt-button>
          <!--<input type="submit" @click.native.prevent="login" :loading="logining" value="登录" >-->
        </div>
        <div class="clear"></div>
        <div class="new">
          <div class="clear"></div>
        </div>
      </form>
    </div>
    <!--start-copyright-->
    <div class="copy-right">
        <p>Copyright &copy; 2017.Company name All rights reserved.
          <a href="http://www.cmic.com.cn/" target="_blank" title="国药器械">国药器械</a> 
        </p>
    </div>
  </div>
    <!--//end-copyright-->
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
  @import '../styles/css/login.css';
}
</style>
<style>
  
  #login .mint-button{
      background: none;
      border: 3px solid #0bd38a;
      color: #0bd38a;
      font-size: 20px;
      font-weight: 300;
      cursor: pointer;
      outline: none;
      padding: 17px 15px;
      width: 100%;
      height:60px;
      border-radius: 0.3em;
      -webkit-border-radius: 0.3em;
    }
</style>

