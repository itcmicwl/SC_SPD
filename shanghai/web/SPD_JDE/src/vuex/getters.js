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
export const getAllSideNavs = state => {
    return state.routes.allSideNavs;
}

export const getAllHomeNavs = state => {
    return state.routes.homeNavs;
}
export const getPerPageList = state=>{
    return state.permission.pageList;
}
export const getSettleBill = state=>{
    return state.settleBill;
}
export const getSpdMsg = state =>{
    return state.spdMsg;    
}