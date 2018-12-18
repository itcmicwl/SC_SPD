//test
export const getLoginUser = state => {
    return state.loginUser;
}
export const getLoginFlag = state => {
    return state.loginFlag;
}
export const getSysRoutes = state => {
    return state.routes.sysRoutes;
}
export const getSideNavs = state => {
    return state.routes.sideNavs;
}
export const getHomeNavs = state => {
    return state.routes.homeNavs;
}
export const getSettleBill = state=>{
    return state.settleBill;
}
export const getPageEls = state=>{
    return state.permission.pageEls;
}
export const getSpdMsg = state =>{
    return state.spdMsg;    
}
export const getUserMenuTree = state =>{
    return state.permission.userMenuTree;    
}
export const getSocketMsg = state =>{
    return state.socketMsg;    
}
export const getOnlineUsers = state =>{
    return state.onlineUsers;    
}
