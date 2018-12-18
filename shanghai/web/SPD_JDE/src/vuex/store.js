import Vue from 'vue';
import Vuex from 'vuex';
import * as actions from './actions';
import * as getters from './getters';
import dialog_store from '../components/js/dialog_store';
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
    },
    settleBill:null,
    spdMsg:[]
};
const mutations = {
    userLogin(state, data) {
        if (data) {
            state.loginUser = data;
            LocalStorage.setItem('user', JSON.stringify(data));
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
    },
    setSettleBill(state,data){
        state.settleBill = data;
    },
    setSpdMsg(state,data){
        if(Array.isArray(data) && data.length>0){
            state.spdMsg = data;
        }else{
            state.spdMsg = [];
        }
    },
    clearCache(state){
        state.routes = {
            homeNavs: null,
            sideNavs: [],
            allHomeNavs: null,
            allSideNavs: null,
            sysRoutes: null
        };
        state.permission = {
            pageList: [],
            pageEl: {}
        };
        state.loginUser = null;
    }
};
// 创建 store 实例
export default new Vuex.Store({
    actions,
    getters,
    state,
    mutations,
    modules:{
        dialog : dialog_store
    }
})