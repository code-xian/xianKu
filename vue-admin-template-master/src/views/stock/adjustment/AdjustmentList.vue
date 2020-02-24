<template>
    <div class="mod-config">
        <el-form
                :inline="true"
                style="margin-left:20px;"
                :model="dataForm"
                @keyup.enter.native="init()"
        >
            <el-form-item label="仓库类型">
                <el-select v-model="dataForm.stockType" placeholder="请选择">
                    <el-option
                            v-for="item in stockTypeList"
                            :key="item.value"
                            :label="item.label"
                            :value="item.label"
                    ></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="仓库名称" prop="searchIpt">
                <el-input v-model="dataForm.searchIpt" placeholder="仓库名称" clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="init()" type="warning">查询</el-button>
                <el-button @click="add()" type="primary">新增</el-button>
            </el-form-item>
        </el-form>
        <div class="tableBox">
            <el-table
                    height="90%"
                    max-height="90%"
                    :data="dataList"
                    border
                    :header-cell-style="{background:'#f5f7fa'}"
                    v-loading="dataListLoading"
                    style="width: 99%;"
            >
                <el-table-column
                        type="index"
                        width="50"
                        header-align="center"
                        align="center"
                        label="No"
                ></el-table-column>
                <el-table-column  prop="stockName" header-align="center" align="center" label="仓库名称"></el-table-column>
                <el-table-column  prop="stockType" header-align="center" align="center" label="仓库类型"></el-table-column>
                <el-table-column  prop="stockFzr" header-align="center" align="center" label="仓库负责人"></el-table-column>
                <el-table-column  header-align="center" align="center" label="仓库状态">
                    <template slot-scope="scope">
                        <div>{{scope.row.stockStatus==0?"在用":scope.row.stockStatus==1?"废弃":""}}</div>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="stockNote"
                        show-overflow-tooltip
                        header-align="center"
                        align="center"
                        label="仓库简介"
                ></el-table-column>
                <el-table-column  fixed="right" header-align="center" align="center" width="300" label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" plain @click="info(scope.row.stockId)">详述</el-button>
                        <el-button size="mini" type="danger" plain @click="del(scope.row.stockId)" v-if="scope.row.stockStatus==0">废弃</el-button>
                        <el-button size="mini" type="success" plain @click="enable(scope.row.stockId)" v-if="scope.row.stockStatus==1">启用</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                    @size-change="sizeChangeHandle"
                    @current-change="currentChangeHandle"
                    :current-page="pageIndex"
                    :page-sizes="[10, 20, 50, 100]"
                    :page-size="pageSize"
                    :total="totalPage"
                    layout="total, sizes, prev, pager, next, jumper"
                    style="float: right;margin:5px"
            ></el-pagination>
        </div>
<!--        &lt;!&ndash; 预约工单详述 &ndash;&gt;-->
        <adjustment-overview
                v-if="adjustmentOverviewVisible"
                ref="adjustmentOverview"
                @refreshDataList="getDataList"
        ></adjustment-overview>
    </div>
</template>

<script>
    import AdjustmentOverview from "./AdjustmentOverview";
    import StockAdd from "./StockAdd";
    import {deteleObject} from "../../../utils/deteleObject"
    export default {
        name: "AdjustmentList",
        data() {
            return {
                dataForm: {
                    stockType: "",
                    searchIpt: "",
                },
                dataList: [],
                pageIndex: 1,
                pageSize: 20,
                totalPage: 0,
                dataListLoading: false,
                dataListSelections: [],
                adjustmentOverviewVisible: false,
                stockTypeList: [
                    { label: "全部", value: "" },
                ],
            };
        },
        components: {
            AdjustmentOverview,
            StockAdd
        },
        mounted() {
            this.init();
            this.getStatusList()
        },
        methods: {
            init () {
                this.pageIndex=1;
                this.getDataList()
            },
            // 获取数据列表
            getDataList() {
                this.dataListLoading = true;
                this.$http({
                    url: "/stock/list",
                    method: "get",
                    params: this.$http.adornParams({
                        page: this.pageIndex,
                        size: this.pageSize,
                        typeNumber: this.dataForm.stockType,
                        name:this.dataForm.searchIpt,
                    })
                }).then(({ data }) => {
                    if (data && data.code === 0) {
                        // for (let i = 0; i < data.data.content.length; i++) {
                        //     data.data.content.push(
                        //         {
                        //             StockStatusMsg:data.data.content==0?"在用":data.data.content==1?"废弃":""
                        //         }
                        //     )
                        // }
                        this.dataList = data.data.content;
                        this.totalPage = data.data.totalElements;
                    } else {
                        this.dataList = [];
                        this.totalPage = 0;
                    }
                    this.dataListLoading = false;
                });
            },
            //删除
            del(id) {
                    this.$confirm(
                        `确定是否进行废弃操作?`,
                        "提示",
                        {
                            confirmButtonText: "确定",
                            cancelButtonText: "取消",
                            type: "warning"
                        }
                    ).then(()=>{
                        this.$http({
                            url: "/stock/delete",
                            method: "post",
                            data: this.$http.adornData({
                                stockId:id
                            })
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.$message.success("废弃成功")
                            } else {
                                this.$message.error(data.msg)
                            }
                            this.dataListLoading = false;
                            this.getDataList();
                        });
                    })
                },
            //启用
            enable(id) {
                this.$confirm(
                    `确定是否进行启用操作?`,
                    "提示",
                    {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning"
                    }
                ).then(()=>{
                    this.$http({
                        url: "/stock/enable",
                        method: "post",
                        data: this.$http.adornData({
                            stockId:id
                        })
                    }).then(({ data }) => {
                        if (data && data.code === 0) {
                            this.$message.success("启用成功")
                        } else {
                            this.$message.error(data.msg)
                        }
                        this.dataListLoading = false;
                        this.getDataList();
                    });
                })
            },
            //新增
            add() {

            },
            // 查询仓库类型下拉列表
            getStatusList() {
                this.$http({
                    url: "/stock/list/stockName",
                    method: "get",
                    params: this.$http.adornParams()
                }).then(({ data }) => {
                    if (data && data.code === 0) {
                        console.log(data.data);
                        for (let i = 0; i < data.data.length; i++) {
                            this.stockTypeList.push(
                               data.data[i]
                            )
                        }
                        this.stockTypeList=deteleObject(this.stockTypeList)
                        // this.stockTypeList.concat(data.data)
                    } else {
                        this.stockTypeList = [{ label: "全部", value: "" }];
                    }
                });
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
            // 多选
            // selectionChangeHandle(val) {
            //     this.dataListSelections = val;
            // },
            //详述
            info(id) {
                this.adjustmentOverviewVisible = true;
                this.$nextTick(() => {
                    this.$refs.adjustmentOverview.init(id);
                });
            }
        }
    }
</script>

<style scoped lang="scss">
    .el-dialog__wrapper {
        margin: 0;
    }
    .mod-config {
    .tableBox {
        height: calc(100vh - 220px);
    }
    }
    .el-form-item {
        margin-bottom: 10px;
    }
</style>
