let themeChange = function (themeName) {
    let oldTheme = LocalStorage.getItem('theme');
    var link = document.getElementById("gyTheme");
    link.href = './static/theme/' + themeName + '/index.css';
    LocalStorage.setItem('theme', themeName);
}
let addThemeStyle = function (themeName) {
    var link = document.createElement('link');
    link.type = 'text/css';
    link.id = "gyTheme";
    link.rel = 'stylesheet';
    link.href = './static/theme/' + themeName + '/index.css';
    document.getElementsByTagName("head")[0].appendChild(link);
    LocalStorage.setItem('theme', themeName);
}
let addGlobalStyle = function () {
    var link = document.createElement('link');
    link.type = 'text/css';
    link.rel = 'stylesheet';
    link.href = './static/style/gyqx.css';
    document.getElementsByTagName("head")[0].appendChild(link);
}
let initTheme = function(theme){
    theme = theme || 'default';
    addThemeStyle(theme);    
    addGlobalStyle();      //这里加载全局样式
}
let themeOP = {
    changeTheme: themeChange,
    initTheme: initTheme
}
export default themeOP;