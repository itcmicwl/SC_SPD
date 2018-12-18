import babelpolyfill from 'babel-polyfill';
import Vue from 'vue';
import App from './App';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-default/index.css';
import './styles/gydeColor.scss';
import './styles/style.less';
import '@/common/js/globalFn'
import store from './vuex/store';
import 'font-awesome-webpack';
import { appConfigs } from './config/appConfigs';
import getRouter from '@/config/baseRoutes';       //基本路由
import themeOP from './config/theme';           //加入主题
import axios from './config/axiosConfig';       //ajax配置
import './common/filter';                       //加入自定义过滤器
import './common/directive';                    //加入自定义指令
Vue.use(ElementUI);
Vue.prototype.appConfig = appConfigs;
Vue.prototype.themeOP = themeOP;
Vue.use(axios);
let user = JSON.parse(LocalStorage.getItem('user'));
if (user) {
    store.commit('userLogin', user);
}
getRouter().then(router => {
  var spdVue = new Vue({
    router,
    store,
    data: {
      $permission:{}
    },
    render: h => h(App),
    mounted() {
      //初始化主题
      let theme = LocalStorage.getItem('theme');
      this.themeOP.initTheme(theme);
    }
  }).$mount('#app');
  
});


