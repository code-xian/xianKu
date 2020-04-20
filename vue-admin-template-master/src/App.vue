<template>
    <div id="app">
        <transition name="fade">
            <router-view/>
        </transition>
    </div>
</template>

<script>
    import {getCookie, removeArray,getCurrentDate} from "./utils";

export default {
    name: 'App',
    // created(){
    //     if(this.$cookie.get('adminAuthority')==1) {
    //         console.log(this.$cookie.get('adminAuthority'));
    //         console.log(1111);
    //     removeArray(this.$store.state.menuItems, '系统管理');
    //         console.log(this.$store.state.menuItems);
    //         // const routes = menusToRoutes(store.state.menuItems);
    //     // 动态添加路由
    //     // router.addRoutes(routes)
    //     }
    // },
    // methods:{
    //
    // }
}
</script>

<style>
body {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}
li, ul, p, div, body, html, table {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
html, body {
    height: 100%;
    overflow: hidden;
}
li {
    list-style: none;
}
#app {
    height: 100%;
}
</style>
