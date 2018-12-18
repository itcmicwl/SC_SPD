/**
 * 构建路由及菜单列表
 */
import Vue from 'vue';
import axios from 'axios';
import VueRouter from 'vue-router';
import store from '../vuex/store';
import { appConfig } from './appConfigs';
const Content = r => require.ensure([], () => r(require('../views/content.vue')), 'main');
const Main = r => require.ensure([], () => r(require('../views/Main.vue')), 'main');
//const Home = r => require.ensure([], () => r(require('../views/Home.vue')), 'main');
const NotFound = r => require.ensure([], () => r(require('../views/404.vue')), 'main');
const orderMgr = r => require.ensure([], () => r(require('../views/orderMgr/orderConfirm.vue')), 'orderComfirm');
const orderConfirmDetail = r => require.ensure([], () => r(require('../views/orderMgr/orderConfirmDetail.vue')), 'orderConfirmDetail');
const login = r => require.ensure([], () => r(require('../views/login.vue')), 'main');
Vue.use(VueRouter);

let baseRoutes = [
    {
        path: '/',
        component: Content,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '404',
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
    },
    {
        path:'/orderConfirm',
        component: orderMgr,
        name: 'orderConfirm',
        hidden: true
    },
    {
        path:'/orderConfirmDetail',
        component: orderConfirmDetail,
        name: 'orderConfirmDetail',
        hidden: true
    }

];

let router = new VueRouter({ routes: baseRoutes });
router.beforeEach((to, from, next) => {
    Vue.prototype.$permission = {};
    var user = store.getters.getLoginUser;
    next();
    if (user) {
        if (to.meta.requireAuth) {  // 判断该路由是否需要登录权限
            var flag = store.getters.getPerPageList.findIndex(item => item.id == to.meta.id) >= 0;
            if (flag === true || user.userId == "0000" || to.meta.isMenu == true) {
                var url = `/platform/permission/roleConfig/getUserMenuElementCodes/${user.userId}/${to.meta.id}`;
                axios.get(url).then(res => {
                    var pList = res.data.data;
                    if (Array.isArray(pList)) {
                        pList.forEach(item => {
                            Vue.prototype.$permission[item] = true;
                        });
                    }
                    next();
                });
            }
            else {
                next({ path: '/403', query: { last: from.path } });
            }
        } else {
            next();
        }
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
let buildRouter = function (routJson) {
    let routeMap = {
        routes: []
    };
    if (routJson.length > 0) {
        routJson.forEach(item => {//第一级菜单
            //构建路由
            let comp = r => require.ensure([], () => r(require("../" + item.directory)), "main");
            let pr = { path: item.path, component: comp, name: item.cname, redirect: item.redirect, meta: { navId: item.id, requireAuth: item.requireAuth,isMenu:true,id:item.id }, children: [] };
            let goupMap = {};
            if (item.children && item.children.length > 0) {
                item.children.forEach(citem => {//第二级菜单(分组)
                    if (citem.children && citem.children.length > 0) {  //第三级菜单
                        citem.children.forEach(ccitem => {
                            let cComp = r => require.ensure([], () => r(require("../" + ccitem.directory)), "main");
                            let cr = { path: ccitem.path, component: cComp, name: ccitem.cname, redirect: ccitem.redirect, hidden: ccitem.hidden == "1", meta: { requireAuth: ccitem.requireAuth, id: ccitem.id,isMenu:false } };
                            pr.children.push(cr);
                        });
                    }
                });
            }
            routeMap.routes.push(pr);    //加入路由                
        });
    }
    return routeMap;
}
let routeBuilder = function () {
    return new Promise(function (resolve) {
        let routeJson = JSON.parse(localStorage.getItem("routes"));
        if (routeJson && routeJson.length > 0) {
            var routeMap = buildRouter(routeJson);
            resolve(routeMap);
        } else {
            axios.post('/platform/sys/proMenu/ProMenuTree', { projectCode: appConfig.appCode }).then(res => {
                routeJson = res.data.data;
                localStorage.setItem("routes", JSON.stringify(routeJson))
                if (routeJson.length > 0) {
                    var routeMap = buildRouter(routeJson);
                    resolve(routeMap);
                }
            }, err => { });
        }
    });
}

let initRoute = function () {
    return new Promise(function (resolve) {
        resolve(router); 
        // routeBuilder().then(routeMap => {
        //     store.commit('setSysRoutes', routeMap.routes);
        //     //router.addRoutes(routeMap.routes);    
        //     resolve(router);        
        // });
    });
}
export default initRoute;