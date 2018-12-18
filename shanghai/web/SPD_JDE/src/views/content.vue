<template>
	<el-col :span="24">
		<home>
		</home>
		<el-col :span="24" class="main">
		<el-menu default-active="1-4-1" class="el-menu-vertical-demo" :collapse="collapsed" router>
			<el-submenu index="1">
				<template slot="title">
					<i class="el-icon-message"></i>
					<span slot="title">厂商查询</span>
				</template>
				<el-menu-item index="/mfrs/mfrs">生产厂商查询</el-menu-item>
			</el-submenu>

			<el-submenu index="2">
				<template slot="title">
					<i class="el-icon-message"></i>
					<span slot="title">商品查询</span>
				</template>
				<el-menu-item index="/goods/goods_register">商品注册证查询</el-menu-item>
				<el-menu-item index="/goods/goods_propertis">商品唯一属性查询</el-menu-item>
			</el-submenu>

			<el-submenu index="3">
				<template slot="title">
					<i class="el-icon-message"></i>
					<span slot="title">客商查询</span>
				</template>
				<el-menu-item index="/cust/cust">客户查询</el-menu-item>
				<el-menu-item index="/cust/prov">供应商查询</el-menu-item>
			</el-submenu>
		</el-menu>
		
			<div class="nav_split" @click="collapse">
				<div class="nav_split_icon" :class="collapsed?'nav_split_icon_left':'nav_split_icon_right'" style="height:71px;width:7px; position:absolute; top:50%; margin-top:-36px;"></div>
			</div>
			<!--<section class="content-container">-->
			<div class="content-container">
				<el-col :span="24" class="breadcrumb-container">
					<el-breadcrumb separator="/" class="breadcrumb-inner">
						<el-breadcrumb-item v-for="item in $route.matched" :key="item.path" :to="item.path?item.path:'/'">
							{{ item.name }}
						</el-breadcrumb-item>
					</el-breadcrumb>
				</el-col>
				<el-col :span="24" class="content-wrapper">
					<transition name="el-zoom-in-center">
						<router-view></router-view>
					</transition>
				</el-col>
			</div>
			<!--</section>-->
		</el-col>
	</el-col>
</template>
<script>
import { mapGetters } from 'vuex';
import home from './Home'
export default {
	data() {
		return {
			collapsed: false
		}
	},
	components: { home },
	computed: {
		...mapGetters(["getSideNavs"])
	},
	methods: {
		isActive(r) {
			var rName = this.$route.name;
			if (rName && r && r.children) {
				return r.children.findIndex(item => item.name == rName) >= 0;
			}
			return false;
		},	
		handleselect: function (a, b) {
		},
		//折叠导航栏
		collapse: function () {
			this.collapsed = !this.collapsed;
		},
		showMenu(i, status) {
			this.$refs.menuCollapsed[0].getElementsByClassName('submenu-hook-' + i)[0].style.display = status ? 'block' : 'none';
		},
		getPath(pPath, cPath) {
			return pPath + "/" + cPath;
		},
		beforeLeave(e) {
			e.style.width = 0;
		}
	},
	mounted() {
	}
}

</script>

<style lang="scss">
//@import '~scss_vars';
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
.main {
	display: flex; // background: #324057;
	position: absolute;
	top: 80px;
	bottom: 0px;
	overflow: hidden;
	padding: 2px;

	.nav_split {
		//border: 1px solid rgb(229, 221, 209);
		width: 7px;
		cursor: pointer;
		overflow: hidden;
		border: 0px solid #9da8ad;
		.nav_split_icon {
			font-family: FontAwesome;
			font-style: normal;
			font-weight: 400;
			-webkit-font-smoothing: antialiased;
			-moz-osx-font-smoothing: grayscale;
			transform: translate(0, 0);
			text-align: center;
			color: #fff; //background: $color-primary;			
		}
		.nav_split_icon_left:before {
			content: "\f0da";
		}
		.nav_split_icon_right:before {
			content: "\f0d9";
		}
		.nav_split_icon:before {
			position: absolute;
			left: 0;
			margin-top: 28px;
		}
	}
	.content-container {
		// background: #f1f2f7;
		flex: 1; // position: absolute;
		// right: 0px;
		// top: 0px;
		// bottom: 0px;
		// left: 230px;
		height: 100%;
		overflow: hidden; //overflow-y: scroll;
		padding: 10px 10px 10px 0;
		.breadcrumb-container:after {
			content: "";
			display: block;
			bottom: 0;
			left: 0;
			width: 100%;
			height: 2px; //background: $color-primary;		
			margin-top: 5px;
		}
		.content-wrapper {
			margin-top: 10px;
			background-color: #fff;
			box-sizing: border-box;
			height: calc(100% - 30px);
			overflow-x: hidden;
		}
	}
}

.el-submenu [class^=fa] {
	vertical-align: baseline;
	margin-right: 10px;
	font-size:14px;
}

.el-submenu__title {
	border-top: none;
	border-right: none;
	border-bottom: none;
}

.el-submenu {
	margin-bottom: 2px;
}
</style>