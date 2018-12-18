import Vue from 'vue';
import store from '../vuex/store';
Vue.directive('focus', {
    // 当绑定元素插入到 DOM 中。
    inserted: function (el, { value }) {
        if (value) {
            el.focus();
        }
    }
});
Vue.directive('gy-permission', {
    bind: function (el, binding) {
        if (binding.value) {
            var els = store.getters.getPageEls;
            if(els.indexOf(binding.value) < 0){
                el.parentNode.removeChild(el);
            }
        }
    }
});