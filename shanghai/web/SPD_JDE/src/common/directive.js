import Vue from 'vue';
Vue.directive('focus', {
    // 当绑定元素插入到 DOM 中。
    inserted: function (el, { value }) {
        if (value) {
            el.focus();
        }
    }
});
Vue.directive('sltText', {
    // 当绑定元素插入到 DOM 中。
    inserted: function (el, { value }) {
        if (value) {
            el.select();
        }
    }
});