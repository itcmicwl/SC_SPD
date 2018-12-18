export const userLogin = ({commit},user) => {
    commit('userLogin',user)
}
export const loginDialogShow = ({commit},flag) => {
    commit('loginDialogShow',flag)
}
export const setSysRoutes = ({commit},data) => {
    commit('setSysRoutes',data)
}
export const setSideNavs = ({commit},data) => {
    commit('setSideNavs',data)
}
export const setHomeNavs = ({commit},data) => {
    commit('setHomeNavs',data)
}
export const setAllSideNavs = ({commit},data) => {
    commit('setSideNavs',data)
}
export const setAllHomeNavs = ({commit},data) => {
    commit('setHomeNavs',data)
}
export const setPerPageList = ({commit},data) => {
    commit('setPerPageList',data)
}
export const setSettleBill = ({commit},data) => {
    commit('setSettleBill',data)
}
export const clearCache = ({commit}) => {
    commit('clearCache')
}
export const setSpdMsg =  ({commit},data) => {
    commit('setSpdMsg',data)
}