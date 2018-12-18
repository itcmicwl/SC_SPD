<template>
	<div id="app">
		<transition name="fade" mode="out-in"><router-view></router-view></transition>
	</div>
</template>

<script>
export default {
	name: 'app',	
	data() {
		return {
			
		}
	},
	computed: {
    }
}

</script>

<style>
#app{
	height: 100%;
}
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s
}
.fade-enter, .fade-leave-active {
  opacity: 0
}
</style>