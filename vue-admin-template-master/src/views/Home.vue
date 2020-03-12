<template>
    <div class="con">
        <h1 class="title">欢迎登入食品供应管理平台</h1>
        <img src="../assets/imgs/bg02.jpg" alt="" class="img">
    </div>
</template>

<script>
export default {
    name: 'home',
    data() {
        return{
            bg:require("../assets/imgs/bg02.jpg")
        }
    }

}
</script>

<style scoped>
    .con {
        /*background:  no-repeat url(../assets/imgs/bg02.jpg) 100%/100% fixed rgba(0,0,0,0.1);*/
        /*background-color: rgba(0,0,0,0.1);*/
        /*height: 85vh;*/
        /*opacity: 1;*/
        height: 85vh;
        background-color: #20222A;
    }
    .img {
        height: 100%;
        width: 100%;
    }
    .title{
        position: absolute;
        text-align:center;
        color: white;
        width: 100%;
        top:30px;
    }
    /*.img::after {*/
    /*    content: "";*/
    /*    background: no-repeat url(../assets/imgs/bg02.jpg) 100%/100% fixed rgba(0,0,0,0.1);*/
    /*    opacity: 0.5;*/
    /*    top: 0;*/
    /*    left: 0;*/
    /*    bottom: 0;*/
    /*    right: 0;*/
    /*    position: absolute;*/
    /*    !*z-index: 1;*!*/
    /*}*/
</style>
