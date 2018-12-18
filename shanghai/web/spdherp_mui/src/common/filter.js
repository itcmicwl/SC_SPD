import Vue from 'vue';
import moment from 'moment';                    //日期格式化
Vue.filter("dateFormat", function (value, strFormat) {   //全局方法 Vue.filter() 注册一个自定义过滤器,必须放在Vue实例化前面
    var res ='';
    if(value){
        res = moment(value).format(strFormat);
    }
    return res;
}); 