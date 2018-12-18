import store from '../vuex/store';
import axios from 'axios';
axios.interceptors.request.use(
    config => {
        var user = store.getters["getLoginUser"];
        if (user && user.xus) {  // 判断是否存在xus，如果存在的话，则每个http header都加上xus
            config.headers["X-US"] = user.xus;
        }
        return config;
    },
    err => {
        return Promise.reject(err);
    });
axios.interceptors.response.use(
    response => {
        return response;
    },
    error => {
        if (error.response) {
            switch (error.response.status) {
                case 401:
                    //store.commit('userLogin', null);
                    if (error.config.url !== "/platform/sys/login/exit") {
                        store.commit('loginDialogShow', true);
                    }
            }
        }
        return Promise.reject(error.response.data)   // 返回接口返回的错误信息
    });

export default axios;