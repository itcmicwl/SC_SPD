import axios from 'axios';
import store from '../vuex/store';
import { appConfig } from './appConfigs';
let setNav = function () {
    return new Promise(function (resolve) {
        var result = false;
        var user = store.getters.getLoginUser;
        var menu = {
            arrMenu: [],
            homeNavs: [],
            sideNavs: []
        }
        if (!user) {
            resolve(result);
            return;
        }       
        var serviceUrl = `platform/permission/menuPageviewItemsConfig/getUserMenusByProjectCode/${appConfig.appCode}/${user.userId}`;
        axios.post(serviceUrl).then(res => {
            var arrMenu = res.data.data;
            if (arrMenu && arrMenu.length > 0) {
                menu.arrMenu = arrMenu;
                store.commit('setPerPageList', menu.arrMenu);
                resolve(true);
            }
        }, err => {
            resolve(false);
        });
    });
}
export default setNav;