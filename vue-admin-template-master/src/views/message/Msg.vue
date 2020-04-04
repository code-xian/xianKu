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
                        {{key.message}}  <span style="float: right">{{key.time}}</span>
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
                        {{'列表内容 ' + o }}
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
            messageList:[]
        }
    },
    created() {
        // console.log(333,this.$store.state.msg.message);
        for (var i of this.$store.state.msg.message) {
            console.log(123);
            this.messageList.push({
                message:i,
                time:getCurrentDate()
            })
        }
        // console.log(333);
        // for (var i of this.$store.state.msg.message) {
        //     console.log(123);
        //     this.messageList.push({
        //         message:i,
        //         time:getCurrentDate()
        //     })
        // }
    },
    mounted() {

    },
    activated() {

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
        text-align: center;
    }
}
.title {
    margin-bottom: 32px;
    line-height: 120px;
    font-size: 30px;
    text-align: center;
    font-weight: 600;
}

</style>
