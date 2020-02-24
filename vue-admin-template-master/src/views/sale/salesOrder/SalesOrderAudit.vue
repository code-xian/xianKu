<template>
    <el-dialog
            :close-on-click-modal="false"
            :modal="true"
            :visible.sync="visible"
            width = "75%"
            style="overflow: hidden;margin-bottom: 5vh"
            top="7vh"
    >
        <div class="box">
            <el-row style="margin-bottom: 10px;">
                <el-col :span="5">
                    <el-card class="box-card">
                        <div style="margin:7px auto;display: inline-block;">
                            <p
                                    style="margin:0;font-size:27px;color:#a9d86e;font-weight:bolder;"
                            >仓库一</p>
                            <p style="margin:0;font-size:20px;">冷冻类</p>
                        </div>
                    </el-card>
                </el-col>
                <el-col :span="19">
                    <el-card class="box-card">
                        <table class="tabBox">
                            <tr>
                                <td class="label">负责人：</td>
                                <td class="content">{{123}}</td>
                                <td class="label">证件号：</td>
                                <td class="content">{{123}}</td>
                                <td class="label">客户编号：</td>
                                <td class="content">{{123}}</td>
                            </tr>
                            <tr>
                                <td class="label">备注：</td>
                                <td class="content" colspan="4">{{123}}</td>
                            </tr>
                        </table>
                    </el-card>

                </el-col>
            </el-row>
            <el-row>
                <div class="title">
                    <i class="el-icon-share icon" style="color:coral;"></i>仓库详情
                    <el-button style="float: right;margin:5px 10px" type="primary" round size="small" @click="move()">移库</el-button>
                    <el-select v-model="value" placeholder="请选择" @change="selectGetData()" style="float: right;margin-right:5px">
                        <el-option
                                v-for="item in categoryList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </div>
                <div class="tableData">
                    <el-table
                            height="90%"
                            max-height="90%"
                            :data="dataList"
                            border
                            :header-cell-style="{background:'#f5f7fa'}"
                            v-loading="dataListLoading"
                            style="width: 99%;">
                        <el-table-column
                                type="index"
                                width="50"
                                header-align="center"
                                align="center"
                                label="No"
                        ></el-table-column>
                        <el-table-column
                                prop="supplierName"
                                header-align="center"
                                align="center"
                                label="食品货号"
                        >
                        </el-table-column>
                        <el-table-column
                                prop="supplierPhone"
                                header-align="center"
                                align="center"
                                label="食品名称"
                        >
                        </el-table-column>
                        <el-table-column
                                prop="supplierFzr"
                                header-align="center"
                                align="center"
                                label="价格"
                        >
                        </el-table-column>
                        <el-table-column
                                prop="supplierAddress"
                                header-align="center"
                                align="center"
                                label="食品类目"
                        >
                        </el-table-column>
                        <el-table-column
                                prop="supplierType"
                                header-align="center"
                                align="center"
                                label="保质期"
                        >
                        </el-table-column>
                        <el-table-column
                                prop="supplierNote"
                                header-align="center"
                                align="center"
                                label="库存">
                        </el-table-column>
                        <el-table-column
                                prop="supplierNote"
                                header-align="center"
                                align="center"
                                label="食品备注">
                        </el-table-column>
                        <!--                        <el-table-column-->
                        <!--                                header-align="center"-->
                        <!--                                align="center"-->
                        <!--                                label="操作">-->
                        <!--                            <template slot-scope="scope">-->
                        <!--                                <el-button type="text" size="small"  @click="del(scope.row.storeId)">删除</el-button>-->
                        <!--                            </template>-->
                        <!--                        </el-table-column>-->
                    </el-table>
                    <el-pagination
                            class="page"
                            @size-change="sizeChangeHandle"
                            @current-change="currentChangeHandle"
                            :current-page="pageIndex"
                            :page-sizes="[10, 20, 50, 100]"
                            :page-size="pageSize"
                            :total="totalPage"
                            layout="total, sizes, prev, pager, next, jumper">
                    </el-pagination>
                </div>
            </el-row>
        </div>
    </el-dialog>
</template>

<script>
    export default {
        name: "SalesOrderAudit",
        data() {
            return{
                dataList:[],
                dataListLoading:false,
                visible:false,
                pageIndex: 1,
                pageSize: 20,
                totalPage: 0,
                categoryList:[]
            }
        },
        methods:{
            init(id) {
                console.log(id);
                this.pageIndex = 1;
                this.visible = true
                this.getInfoData();
            },
            // 表单提交
            dataFormSubmit(){

            },
            // 选择供应商弹窗
            selectMember(){

            },
            //确定客户
            sureAddress (obj){

            },
            getInfoData() {

            },
            // 每页数
            sizeChangeHandle(val) {
                this.pageSize = val;
                this.pageIndex = 1;
                this.getDataList();
            },
            // 当前页
            currentChangeHandle(val) {
                this.pageIndex = val;
                this.getDataList();
            },
        },
    }
</script>

<style lang="scss" scoped>
    .box {
        overflow: hidden;
    }
    .tableData{
        height: calc(100vh - 350px);
    }
    .page{
        float:right;
        margin-top: 5px;
    }
    .tabBox {
        width: 100%;
        td {
            font-size: 20px;
            line-height: 40px;
        }
        .label {
            width: 130px;
            color: #909399;
            font-weight: 600;
        }
        .content {
            font-size: 18px;
        }
    }
    .title {
        width: 100%;
        font-size: 22px;
        line-height: 45px;
        background-color: #f0f7ff;
    }
</style>
