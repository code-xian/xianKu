<template>
    <div class="content">
        <div class="title">
            消息栏
        </div>
        <div>
            <el-row type="flex">
                <el-col :span="10" :offset="1">
                    <el-card :body-style="{ height: '40vh' }">
                        <div slot="header" >
                            <p class="card-title">仓库消息栏</p>
<!--                            <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>-->
                        </div>
                    <div v-for="(key,index) in messageList" :key="index">
                        <p style="line-height: 26px">{{key}}</p>
                    </div>
                </el-card>
                </el-col>
                    <el-col :span="10"  :offset="1" :push="1">
                <el-card  :body-style="{ height: '40vh' }">
                    <div slot="header" >
                        <p class="card-title">订单消息栏</p>
<!--                        <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>-->
                    </div>
                    <div v-for="o in 4" :key="o" >

                    </div>
                </el-card>

                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
import {getCurrentDate} from "../../utils";

export default {
    name: 'msg',
    data() {
        return{
            messageList:this.$store.state.msg.message
        }
    },
    destroyed() {
        console.log(666);
    },
    methods:{
        getDataList() {
            this.$http({
                url: '',
                method: '',
                params:this.$http.adornParams({

                })
            }).then((data)=>{

            })
        },

    },
}
</script>

<style scoped lang="scss">
.content{
    height: calc(100vh - 160px);
    .card-title{
        color:grey;
        font-size: 18px;
        font-weight: 600;
        text-align: center;
    }
}
.title {
    text-shadow: 5px 5px 5px rgba(0,0,0,0.5);/*黑色阴影，用rgba表示，透明度为0.9*/ ;
    letter-spacing:8px;
    margin-bottom: 32px;
    line-height: 120px;
    font-size: 30px;
    text-align: center;
    font-weight: 600;
}

</style>
