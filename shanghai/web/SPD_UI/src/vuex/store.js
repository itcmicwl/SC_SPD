import Vue from 'vue';
import Vuex from 'vuex';
import * as actions from './actions';
import * as getters from './getters';
import dialog_store from '../components/js/dialog_store';
import gyWebSocket from '@/common/js/gyWebSocket';

Vue.use(Vuex);
const state = {
    loginUser: null,
    loginFlag: false,
    routes: {
        homeNavs: null,
        sideNavs: [],
        sysRoutes: null
    },
    permission: {
        userMenuTree:[],
        pageEls: []
    },
    settleBill:null,
    spdMsg:[],
    socketMsg:[],
    onlineUsers:[]
};
const mutations = {
    userLogin(state, data) {
        if (data) {
            state.loginUser = data;
            LocalStorage.setItem('user', JSON.stringify(data));
            Vue.prototype.user = state.loginUser;  
            let webSocket = gyWebSocket({onMessage:function(evtData){
                if(!evtData.msgKind || evtData.msgKind == '1'){
                    state.socketMsg.push(evtData);
                }else if(evtData.msgKind && evtData.msgKind == '2'){
                    state.onlineUsers = evtData.data;
                }
            }});
            webSocket.connect(state.loginUser.userId);
            Vue.prototype.gyWebSocket = webSocket;
        } else {
            state.loginUser = null;
            localStorage.removeItem('user');
            localStorage.removeItem('routes');
            Vue.prototype.user = null;
            Vue.prototype.gyWebSocket = null;
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
    setPageEls(state, data) {
        if(data){
            state.permission.pageEls = data;
        }
    },
    setUserMenuTree(state, data) {
        if(Array.isArray(data) && data.length > 0){
            state.permission.userMenuTree = data;
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
    setSocketMsg(state,data){
        state.socketMsg.push(data);
    },
    setOnlineUsers(state,data){
        if(Array.isArray(data) && data.length>0){
            state.onlineUsers = data;
        }
    },
    clearCache(state){
        state.routes = {
            homeNavs: null,
            sideNavs: [],
            sysRoutes: null
        };
        state.permission = {
            userMenuTree: [],
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
