import babelpolyfill from 'babel-polyfill';
import Vue from 'vue';
import MintUI from 'mint-ui'
import 'mint-ui/lib/style.css'
import VueAwesomeSwiper from 'vue-awesome-swiper'
import RegionPicker from 'vue-region-picker'
import CHINA_REGION from 'china-area-data'
import App from './App';
import store from './vuex/store';
import 'font-awesome/css/font-awesome.min.css';
import { appConfigs } from './config/appConfigs';
import getRouter from './config/baseRoutes';       //基本路由
import axios from './config/axiosConfig';                  //ajax配置
import './common/filter';                       //加入自定义过滤器
Vue.use(MintUI);
Vue.prototype.appConfig = appConfigs;
Vue.prototype.axios = axios;
let user = JSON.parse(localStorage.getItem('user'));
if (user) {
    store.commit('userLogin', user);
}
//Vue.config.performance=true;

getRouter().then(router => {
  var spdVue = new Vue({
    router,
    store,
    data: {
      $permission:{}
    },
    render: h => h(App),
    mounted() {
     
    }
  }).$mount('#app');
  
});


