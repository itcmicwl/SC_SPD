
# To start
# 安装淘宝镜像
npm install -g cnpm --registry=https://registry.npm.taobao.org
``` bash
# 安装依赖
cnpm install

# serve with hot reload at localhost:8081
npm run dev

# build for production with minification
npm run build

```

# Folder structure
SPD_UI
│  .babelrc
│  .gitignore
│  index.html
│  LICENSE
│  package.json
│  README.md
│  
├─build 
│      
│      
├─config
│      dev.env.js
│      index.js
│      prod.env.js
│      
├─dist
├─src                       //源代码，SVN只需提交SRC中的文件
│  │  App.vue               
│  │  main.js               //入口文件（全局配置）
│  │  routes.js             //路由配置
│  │  
│  ├─assets
│  │  │  bg1.jpg
│  │  │  logo.jpg
│  │  │  logo.png
│  │  │  logo4.png
│  │  │  user.png
│  │  │  
│  │  └─theme
│  │      ├─theme-green
│  │      └─theme-orange        
│  ├─common
│  │  └─js
│  │          sha256.js
│  │          util.js
│  │          
│  ├─components                 //通用组件存放位置
│  ├─styles
│  │      vars.scss
│  │      
│  └─views
│      │  404.vue
│      │  content.vue
│      │  Home.vue
│      │  Login.vue
│      │  Login4Ajax.vue
│      │  Main.vue
│      │  
│      ├─module1
│      │  ├─charts
│      │  │      echarts.vue
│      │  │      
│      │  ├─nav1
│      │  │      Form.vue
│      │  │      Table.vue
│      │  │      user.vue
│      │  │      
│      │  ├─nav2
│      │  │      Page4.vue
│      │  │      Page5.vue
│      │  │      
│      │  └─nav3
│      │          Page6.vue
│      │          
│      └─settings
└─static
        .gitkeep
# Browser support

Modern browsers and IE 10+.

# License
[MIT](http://opensource.org/licenses/MIT)
