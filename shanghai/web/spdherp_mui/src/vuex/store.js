import Vue from 'vue';
import Vuex from 'vuex';
import * as actions from './actions';
import * as getters from './getters';
Vue.use(Vuex);
const state = {
    loginUser: null,
    loginFlag: false,
    routes: {
        homeNavs: null,
        sideNavs: [],
        allHomeNavs: null,
        allSideNavs: null,
        sysRoutes: null
    },
    permission: {
        pageList: [],
        pageEl: {}
    }
};
const mutations = {
    userLogin(state, data) {
        if (data) {
            state.loginUser = data;
            localStorage.setItem('user', JSON.stringify(data));
            Vue.prototype.user = state.loginUser;
        } else {
            state.loginUser = null;
            localStorage.removeItem('user');
            localStorage.removeItem('routes');
            Vue.prototype.user = null;
        }
    },
    loginDialogShow(state, flag) {
        state.loginFlag = flag;
    },
    setSysRoutes(state, data) {
        state.routes.sysRoutes = data;
    },
    setSideNavs(state, data) {
        state.routes.sideNavs = data;
    },
    setHomeNavs(state, data) {
        state.routes.homeNavs = data;
    },
    setAllSideNavs(state, data) {        
        state.routes.allSideNavs = data;
    },
    setAllHomeNavs(state, data) {
        state.routes.allHomeNavs = data;
    },
    setPerPageList(state, data) {
        if (data.length > 0) {
            state.permission.pageList = data;
        }else{
            state.permission.pageList =[];
        }
    }
};
// 创建 store 实例
export default new Vuex.Store({
    actions,
    getters,
    state,
    mutations    
})