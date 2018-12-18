import axios from 'axios';
import sha256 from './sha256';
import store from '../../vuex/store';
let login = function (loginParams) {
    return new Promise(resolve => {
        var result = {
            flag: false,
            accountErr: '',
            passwordErr: '',
            errInfo: null
        }
        axios.post(`/baseDataQuery/sys/login/checkUserLoginCode`, { userLoginCode: loginParams.username }).then(res => {
            if (res.data.code == "-120") {
                result.accountErr = "用户名不存在!";
                resolve(result);
                return;
            }
            if (res.data.code == "-122") {
                result.accountErr = "用户名错误!";
                resolve(result);
                return;
            }
            let r = res.data.data;
            var hash = sha256(loginParams.password + r.salt);
            let loginPara = { userLoginCode: loginParams.username, hash: loginParams.password, rcode: r.randomCode };
            axios.post(`/baseDataQuery/sys/login/login`, loginPara).then(res => {
                if (res.data.code == 0) {   //登陆成功
                    var user = res.data.data;
                    user.xus = res.data.tag;
                    store.commit('userLogin', user);
                    LocalStorage.setItem("userCode",user.userCode)
                    result.flag = true;
                    resolve(result);
                } else if (res.data.code == "-111") {
                    result.passwordErr = "密码错误!";
                    resolve(result);;
                } else {
                    result.accountErr = "登陆失败!";
                    resolve(result);;
                }
            }, err => {
                result.accountErr = "登陆失败!";
                result.errInfo = err;
                resolve(result);
            });
        }, err => {
            result.accountErr = "登陆失败!";
            result.errInfo = err;
            resolve(result);
        });
    });
}
export default login;