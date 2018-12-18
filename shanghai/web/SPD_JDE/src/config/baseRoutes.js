/**
 * 构建路由及菜单列表
 */
import Vue from 'vue';
import axios from 'axios';
import VueRouter from 'vue-router';
import store from '../vuex/store';
import { appConfig } from './appConfigs';
import { Message } from 'element-ui';
const Content = r => require.ensure([], () => r(require('../views/content.vue')), 'main');
const Main = r => require.ensure([], () => r(require('../views/Main.vue')), 'main');
const Home = r => require.ensure([], () => r(require('../views/Home.vue')), 'main');
const NotFound = r => require.ensure([], () => r(require('../views/404.vue')), 'main');
const Forbidden = r => require.ensure([], () => r(require('../views/403.vue')), 'main');
const login = r => require.ensure([], () => r(require('../views/login.vue')), 'main');
const index = r => require.ensure([], () => r(require('../views/index/index4Hos.vue')), 'main');
const mfrs = r => require.ensure([], () => r(require('../views/mfrs.vue')), 'main');
const cust = r => require.ensure([], () => r(require('../views/cust.vue')), 'main');
const prov = r => require.ensure([], () => r(require('../views/prov.vue')), 'main');
const goods_propertis = r => require.ensure([], () => r(require('../views/goods_propertis.vue')), 'main');
const goods_register = r => require.ensure([], () => r(require('../views/goods_register.vue')), 'main');
//const baseInfo = r => require.ensure([], () => r(require('../views/userSettings/baseInfo.vue')), 'main');
Vue.use(VueRouter);

let baseRoutes = [
    {
        path: '/',
        component: Content,
        name: '',
        hidden: true,
        redirect: { path: '/mfrs/mfrs' }
    },
    {
        path: "/mfrs",
        component: Content,
        name: "生产厂商",
        redirect: "/mfrs/mfrs",
        children: [
            {
                path: "mfrs",
                component: mfrs,
                redirect: null,
                name: "厂商查询",
                hidden: true
          }
        ]
      },
    {
        path: "/cust",
        component: Content,
        name: "客商",
        redirect: "/cust/cust",
        children: [
            {
                path: "cust",
                component: cust,
                redirect: null,
                name: "客户查询",
                hidden: true
            },
            {
                path: "prov",
                component: prov,
                redirect: null,
                name: "供应商查询",
                hidden: true
            },
        ]
      },
    {
        path: "/goods",
        component: Content,
        name: "商品",
        redirect: "/goods/cust",
        children: [
             {
                path: "goods_register",
                component: goods_register,
                redirect: null,
                name: "商品注册证查询",
                hidden: true
            },
             {
                path: "goods_propertis",
                component: goods_propertis,
                redirect: null,
                name: "商品唯一属性查询",
                hidden: true
            }
        ]
      },
    {
        path: '/404',
        component: NotFound,
        name: '404',
        hidden: true
    },
    {
        path: '/403',
        component: Forbidden,
        name: '403',
        hidden: true
    },
    {
        path: '/login',
        component: login,
        name: 'login',
        hidden: true
    },
    {
        path: '*',
        name: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

let router = new VueRouter({ routes: baseRoutes });
router.beforeEach((to, from, next) => {
    Vue.prototype.$permission = {};
    var user = store.getters.getLoginUser;
    if (user) {
        next();
    }
    else {
        if (to.name == 'login') {
            next();
        } else {
            var last = from.path == "/login" ? "/" : from.path;
            next({ path: '/login', query: { last: last } });
        }
    }
});
router.afterEach(transition => {
});

let routeBuilder = function () {
    let routeMap = {
        routes: baseRoutes,       
        routeNav: [
            {
                path: "index",
                name: "首页",
                redirect: null,
                hidden: false
            },
          ]//左边导航菜单
    };
    return routeMap;
}

let initRoute = function () {
    return new Promise(function (resolve) {
        var routeMap = routeBuilder();
        store.commit('setSideNavs', routeMap.routeNav);
        store.commit('setHomeNavs', routeMap.homeNavs);
        store.commit('setSysRoutes', routeMap.routes);
        router.addRoutes(routeMap.routes);
        resolve(router);
    });
}
export default initRoute;