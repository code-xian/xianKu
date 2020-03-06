<template>
    <el-dialog
            :close-on-click-modal="false"
            :modal="true"
            :visible.sync="visible"
            width = "75%"
            style="margin-bottom: 5vh"
            top="7vh"
            title="新增"
    >
        <div class="box">
            <el-row style="margin-bottom: 10px;">
                <el-col :span="24">
                    <el-form>
                        <el-card class="box-card">
                            <table class="tabBox">
                                <tr>
                                    <td class="label">交货方式：</td>
                                    <td class="content">
                                       {{data.submissionWay}}
                                    </td>
                                    <td class="label">交货日期：</td>
                                    <td class="content">
                                        {{data.submissionDate}}
                                    </td>
                                    <td class="label">门店名称：</td>
                                    <td class="content">
                                      {{data.storeName}}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">门店电话：</td>
                                    <td class="content">
                                        {{data.storePhone}}
                                    </td>
                                    <td class="label">门店负责人：</td>
                                    <td class="content">
                                        {{data.storeFzr}}
                                    </td>
                                    <td class="label">门店地址：</td>
                                    <td class="content">
                                        {{data.storeAddress}}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">订单备注：</td>
                                    <td class="content" colspan="4">
                                        {{data.saleRemarks}}
                                    </td>
                                </tr>
                            </table>
                        </el-card>
                    </el-form>
                </el-col>
            </el-row>
            <el-row>
                <div class="title">
                    <i class="el-icon-share icon" style="color:coral;"></i>订单详情明细
                </div>
                <div class="tableData">
                    <el-table
                            ref="table"
                            :summary-method="getSummaries"
                            :show-summary="true"
                            height="90%"
                            max-height="90%"
                            :data="dataList"
                            border
                            :header-cell-style="{background:'#f5f7fa'}"
                            v-loading="dataListLoading"
                            style="width: 99%;">
                        <!--                        <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>-->
                        <el-table-column
                                type="index"
                                width="50"
                                header-align="center"
                                align="center"
                                label="No"
                        ></el-table-column>
                        <el-table-column
                                prop="foodId"
                                header-align="center"
                                align="center"
                                label="食品货号"
                        >
                        </el-table-column>
                        <el-table-column
                                prop="foodName"
                                header-align="center"
                                align="center"
                                label="食品名称"
                        >
                        </el-table-column>
                        <el-table-column
                                prop="foodPrice"
                                header-align="center"
                                align="center"
                                label="单价(元)"
                        >
                        </el-table-column>
                        <el-table-column
                                header-align="center"
                                align="center"
                                prop="foodQuantity"
                                width="300"
                                label="数量">
                        </el-table-column>
                        <el-table-column
                                prop="stockName"
                                header-align="center"
                                align="center"
                                label="发货仓库">
                        </el-table-column>
                        <el-table-column
                                prop="detailRemarks"
                                header-align="center"
                                align="center"
                                label="食品备注">
                        </el-table-column>
                        <el-table-column
                                width="150"
                                header-align="center"
                                align="center"
                                prop="detailPrice"
                                label="金额(元)">
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
        <div style="float: right;font-size: 22px;font-weight: 600;color: gray;margin:-15px 10px 10px 0">
            总金额:{{totalAmount}}元
        </div>
        <span slot="footer" class="dialog-footer">
              <el-button @click="visible = false" v-if="flag">取 消</el-button>
              <el-button @click="visible = false" v-if="!flag" type="primary">确 定</el-button>
              <el-button type="primary" @click="dataFormSubmit(1)" :disabled="dataFormSubmitDisabled" v-if="flag">不通过</el-button>
              <el-button type="primary" @click="dataFormSubmit(0)" :disabled="dataFormSubmitDisabled" v-if="flag">通 过</el-button>
         </span>
    </el-dialog>
</template>

<script>
    export default {
        name: "SalesOrderAdd",
        data() {
            return{
                number:0,
                dataFormSubmitDisabled:false,
                dataList:[],
                dataListLoading:false,
                visible:false,
                pageIndex: 1,
                pageSize: 20,
                totalPage: 0,
                data:{
                    jiaoHuoFangShi:"",
                    time:"",
                    storePhone:"",
                    textarea:"",
                    storeName:"",
                    storeAddress: "",
                    storeFzr: "",
                    totalAmount:"",
                },
                saleId:"",
                flag:"",
                totalAmount:0,
            }
        },
        computed:{
            total() {
                let a = 0;
                for(let i = 0; i<this.dataList.length;i++){
                    a += this.dataList[i].saleQuantity*this.dataList[i].foodPrice
                }
                return a
            }
        },
        methods:{
            init(saleId,flag,totalAmount) {
                this.flag = flag
                this.saleId = saleId;
                this.totalAmount = totalAmount
                this.visible = true
                this.dataFormSubmitDisabled = false
                this.getInfoData();
            },
            // 表单提交
            dataFormSubmit(flag){
                console.log(flag);
                console.log(this.dataList);
                this.dataFormSubmitDisabled = true
                this.$http({
                    url: "/sale/audit",
                    method: "post",
                    data: this.$http.adornData({
                        saleId:this.saleId,
                        orderStatus:flag,
                        list:this.dataList,
                    })
                }).then(({ data }) => {
                    if (data && data.code === 0) {
                            this.$message({
                                message: "审核操作成功!",
                                type: "success",
                                duration: 1500
                            });
                            this.visible = false;
                            this.$emit("refreshDataList");
                        } else {
                            this.$message({
                                message: data.msg,
                                type: "warning",
                                duration: 1500
                            });
                            this.dataFormSubmitDisabled = false;
                        }
                });
            },
            getInfoData() {
                this.dataListLoading = true;
                this.$nextTick(() => {
                    this.$http({
                        url: "/sale/detail",
                        method: "get",
                        params: this.$http.adornParams({
                            saleId:this.saleId,
                        })
                    }).then(({ data }) => {
                        if (data && data.code === 0) {
                            this.data = data.data
                            this.dataList = data.data.detailList
                        } else {
                            this.$message.error(data.msg);
                        }
                    });
                });
                this.dataListLoading = false;
            },
            //合计
            getSummaries (param) {
                const { columns, data } = param
                const sums = []
                columns.forEach((column, index) => {
                    if (index === 0) {
                        sums[index] = '总计'
                    } else if ( index === 6|| index === 3) {
                        const values = data.map(item => Number(item[column.property]))
                        if (!values.every(value => isNaN(value))) {
                            sums[index] = values.reduce((prev, curr) => {
                                const value = Number(curr)
                                if (!isNaN(value)) {
                                    return prev + curr
                                } else {
                                    return prev
                                }
                            }, 0)
                        } else {
                            sums[index] = 'N/A'
                        }
                    } else {
                        sums[index] = '--'
                    }
                })
                this.$nextTick(()=>{
                    this.$refs.table.doLayout();
                })
                return sums
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
            width: 15%;

        }
        .label {
            text-align: center;
            color: #909399;
            font-weight: 600;
        }
    }
    .title {
        width: 100%;
        font-size: 22px;
        line-height: 45px;
        background-color: #f0f7ff;
    }
    .el-form-item {
        margin-bottom: 0;
    }
</style>



