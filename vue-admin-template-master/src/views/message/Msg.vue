<template>
    <div>
        <div class="clearfix">
            <table style="width: 100%;" cellspacing="50px">
                <tr>
                    <td class="headTD">
                        <p>总销售额：{{20000}}元</p>
                        <p>总销售单：{{13000}}单</p>
                    </td>
                    <td class="headTD">
                        <p>今年销售额：{{300001111111}}元</p>
                        <p>今年销售单：{{15000}}单</p>
                    </td>
                    <td class="headTD">
                        <p>今日销售额：{{2000}}元</p>
                        <p>今天销售单：{{200}}单</p>
                    </td>
                    <td class="headTD">
                        <p>昨天销售额：{{3000}}元</p>
                        <p>昨天销售单：{{150}}单</p>
                    </td>
                </tr>
            </table>
<!--            <div class="float">-->
<!--                <p>总销售额：20，000元</p>-->
<!--                <p>总销售单：13，000单</p>-->
<!--            </div>-->
<!--            <div class="float">-->
<!--                <p>总销售额：20，000元</p>-->
<!--                <p>总销售单：13，000单</p>-->
<!--            </div>-->
<!--            <div class="float">-->
<!--                <p>总销售额：20，000元</p>-->
<!--                <p>总销售单：13，000单</p>-->
<!--            </div>-->
<!--            <div class="float">-->
<!--                <p>总销售额：20，000元</p>-->
<!--                <p>总销售单：13，000单</p>-->
<!--            </div>-->
        </div>
        <div style=" margin: 35px auto;height: 220px">
            <div  class="title">
                <i class="el-icon-tickets" style="margin-right:10px;"></i>销售推荐
            </div>
            <div style="margin: 15px auto">
                <table style="width: 100%">
                    <tr>
                        <td class="middleTD">
                            <img src="../../assets/imgs/bg00.jpg" height="100" width="200"/>
                        </td>
                        <td class="middleTD"><img src="../../assets/imgs/bg00.jpg" height="100" width="200"/></td>
                        <td class="middleTD"><img src="../../assets/imgs/bg00.jpg" height="100" width="200"/></td>
                        <td class="middleTD"><img src="../../assets/imgs/bg00.jpg" height="100" width="200"/></td>
                    </tr>
                    <tr>
                        <td class="middleTD">
                            <p>新用户未安装炉具</p>
                            <p>130000户</p>
                        </td>
                        <td class="middleTD">
                            <p>炉具保质期即将过期</p>
                            <p>130000户</p>
                        </td>
                        <td class="middleTD">
                            <p>炉具已经过期</p>
                            <p>130000户</p>
                        </td>
                        <td class="middleTD">
                            <p>有意向购买炉具（微信访问足迹）</p>
                            <p>130000户</p>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="contain">
            <div  class="title">
                <i class="el-icon-tickets" style="margin-right:10px;"></i>订单统计
            </div>
            <div>
                <el-container>
                    <el-aside width="180px" style="background-color: rgb(238, 241, 246)">
                        <div class="displayTable">
                        <div  class="block">
                        <el-row>
                            <el-col :span="24"><div class="grid-content bg-purple-dark">本月订单总数</div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24"><div class="grid-content bg-purple-dark font">180</div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24"><div class="grid-content bg-purple-dark"><span :class="isMonthCount?red:green">{{monthCount}}%</span>同比上月</div></el-col>
                        </el-row>
                        </div><div class="block">
                        <el-row>
                            <el-col :span="24"><div class="grid-content bg-purple-dark">本周订单总数</div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24"><div class="grid-content bg-purple-dark font">180</div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24"><div class="grid-content bg-purple-dark"><span :class="isWeekCount?red:green">{{weekCount}}%</span>同比上月</div></el-col>
                        </el-row>
                        </div><div class="block">
                        <el-row>
                            <el-col :span="24"><div class="grid-content bg-purple-dark">本月销售总额</div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24"><div class="grid-content bg-purple-dark font">180</div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24"><div class="grid-content bg-purple-dark"><span :class="isMonthAmount?red:green">{{monthAmount}}%</span>同比上月</div></el-col>
                        </el-row>
                        </div>
                            <div class="block" style="height: 0">
                        <el-row>
                            <el-col :span="24"><div class="grid-content bg-purple-dark">本周销售总额</div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24"><div class="grid-content bg-purple-dark font">180</div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24"><div class="grid-content bg-purple-dark"><span :class="isWeekAmount?red:green">{{weekAmount}}%</span>同比上月</div></el-col>
                        </el-row>
                        </div>
                        </div>
                    </el-aside>
                    <el-main>
                        <el-form class="clear" :inline="true">
                            <div style="margin: 10px ;float: right" >
                                <el-form-item>
                                    <el-date-picker
                                            v-model="value1"
                                            type="daterange"
                                            range-separator="至"
                                            start-placeholder="开始日期"
                                            end-placeholder="结束日期">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item>
                                    <el-button
                                        type="primary"
                                        @click="getDataList"
                                    >查询
                                    </el-button>
                                </el-form-item>
                            </div>
                        </el-form>
                        <div class="echartscontain">
                            <div id="echarts" ></div>
                        </div>
                    </el-main>
                </el-container>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'msg',
    data() {
        return{
            myChart:null,
            value1:'',
            isMonthCount:true,
            isMonthAmount:true,
            isWeekAmount:true,
            isWeekCount:true,
            red: 'red',
            green: 'green',
            monthCount:-33,    //数量
            monthAmount:-32,  //金额
            weekAmount:13,
            weekCount:23,
            dingdanshu:[120, 132, 101, 134, 90, 230, 210],
            xiaoshoue:[220, 182, 191, 234, 290, 330, 310],
            dateTime: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        }
    },
    mounted() {
        this.initEchart();
    },
    watch:{
        weekAmount:{
            handler(val,oldVal) {
                if(val>0){
                    this.isWeekAmount = false
                }else  if(val<0){
                    this.isWeekAmount = true
                }
            },
            immediate: true
        },
        monthCount:{
            handler(val,oldVal) {
                if(val>0){
                    this.isMonthCount = false
                }else if(val<0){
                    this.isMonthCount = true
                }
            },
            immediate: true
        },
        monthAmount:{
            handler(val,oldVal) {
                if(val>0){
                    this.isMonthAmount = false
                }else  if(val<0){
                    this.isMonthAmount = true
                }
            },
            immediate: true
        },
        weekCount:{
            handler(val,oldVal) {
                if(val>0){
                    this.isWeekCount = false
                }else if(val<0){
                    this.isWeekCount = true
                }
            },
            immediate: true
        },

    },
    activated() {
        this.getDataList();
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
        initEchart() {
            var option = {
                title: {
                    text: '订单统计图'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['订单数', '销售额']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                // toolbox: {
                //     feature: {
                //         saveAsImage: {}
                //     }
                // },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: this.dateTime
                },
                yAxis: [
                    {
                        type: 'value',
                        name: '订单数',
                        min:0,
                        max:3000,
                        interval: 500
                    },
                    {
                        type: 'value',
                        name:'销售额',
                        min: 0,
                        max: 12000,
                        interval: 2000,
                    }
                ],
                series: [
                    {
                        name: '订单数',
                        type: 'line',
                        stack: '总量',
                        data: this.dingdanshu
                    },
                    {
                        name: '销售额',
                        type: 'line',
                        stack: '总量',
                        data: this.xiaoshoue
                    }
                ]
            };
            let myChart = this.$echarts.init(document.getElementById("echarts"))
            myChart.setOption(option);
            window.addEventListener('resize', ()=> {
                myChart.resize()
            });
        }

    },
}
</script>

<style scoped>

.title {
    padding-left: 20px;
    line-height: 35px;
    font-size: 20px;
    font-weight: 500;
    background-color: #f0f7ff;
}
    #echarts{
        /*position:relative;*/
        width: 99%;
        height: 50vh;
        margin: 0 auto;
        text-align: center;
        /*border:1px solid red;*/
    }
    .echartscontain{
        position: relative;
        /*border:1px solid green;*/
        /*text-align: center;*/
    }
    .clear:after{
        display: block;
        clear: both;
        content: ''
    }
    .clear{
        zoom: 1;
    }
    .displayTable{
        display: table;
        height: 650px;
        overflow: hidden;
    }
    .block{
        height: 25%;
        margin: 12px
    }
    .font{
        font-weight: 700;
        font-size: 22px;
    }
    .grid-content{
        margin:3px 0;
    }
    .red{
        color:red;
    }
    .green{
        color: green;
    }
    td {
        /*background-color: #409eff;*/
        text-align: center;
        font-size: 18px;
        font-weight: 700;
    }
    .float {
        float: left;
        /*display: inline-block;*/
        /*margin: 0 10px;*/
        padding: 10px;
        width: 25%;
        /*height: 200px;*/
        /*line-height: 200px;*/
        background-color: red;
        text-align: center;
        vertical-align: middle;
        display: table-cell;
        border: 1px solid greenyellow;
    }
    /*.clearfix:after {*/
    /*    content: '';*/
    /*    display: block;*/
    /*    clear:both;*/
    /*    visibility:hidden;*/
    /*    height:0*/
    /*}*/
    .clearfix {
        /*zoom: 1;*/
        width: 100%;
        /*display: table;*/
        height: 220px;
        /*width: 100%;*/
        margin: 0 auto 70px;
    }
    .headTD{
        background-color: #409eff77;
        border: 1px solid black;
        margin: 5px;
        height: 200px;
        box-shadow: 3px 3px 5px #97a8be;
        width: 25%;
    }
    .middleTD {
        width: 25%;

    }

</style>
