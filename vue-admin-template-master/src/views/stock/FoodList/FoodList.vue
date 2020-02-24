<template>
    <div class="mod-config">
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
            <!--            <el-form-item label="门店类型" prop="type">-->
            <!--                <el-select v-model="dataForm.type">-->
            <!--                    <el-option-->
            <!--                            v-for="(item , index) in typeList"-->
            <!--                            :key="index"-->
            <!--                            :label="item.label"-->
            <!--                            :value="item.value">-->
            <!--                    </el-option>-->
            <!--                </el-select>-->
            <!--            </el-form-item>-->
            <el-form-item label="食品名称">
                <el-input  placeholder="请输入食品名称" clearable v-model="dataForm.foodName"></el-input>
            </el-form-item>
            <el-form-item label="食品种类">
                <el-select v-model="dataForm.foodCategory" clearable>
                    <el-option
                            v-for="item in foodCategoryList"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                    ></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button @click="getDataList()" type="warning">查询</el-button>
                <el-button  type="primary" @click="SupplierAddButton()">新增</el-button>
            </el-form-item>
        </el-form>
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
                        header-align="center"
                        align="center"
                        label="食品货号"
                        prop="foodId">
                </el-table-column>
                <el-table-column
                        header-align="center"
                        align="center"
                        label="食品名称"
                        prop="foodName">
                </el-table-column>
                <el-table-column
                        prop="foodPrice"
                        header-align="center"
                        align="center"
                        label="食品单价"
                >
                </el-table-column>
                <el-table-column
                        prop="supplierPhone"
                        header-align="center"
                        align="center"
                        label="食品类目"
                >
                </el-table-column>
                <el-table-column
                        header-align="center"
                        align="center"
                        label="食品状态"
                >
                    <template slot-scope="scope">
                        <div>{{scope.row.foodStatus==0?"上架":scope.row.foodStatus==1?"下架":""}}</div>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="shelfLife"
                        header-align="center"
                        align="center"
                        label="保质期"
                >
                </el-table-column>
                <el-table-column
                        prop="foodDescription"
                        header-align="center"
                        align="center"
                        label="食品描述"
                >
                </el-table-column>
<!--                <el-table-column-->
<!--                        prop="supplierType"-->
<!--                        header-align="center"-->
<!--                        align="center"-->
<!--                        label="供应商类型"-->
<!--                >-->
<!--                </el-table-column>-->
<!--                <el-table-column-->
<!--                        prop="supplierNote"-->
<!--                        header-align="center"-->
<!--                        align="center"-->
<!--                        label="供应商简介">-->
<!--                </el-table-column>-->
                <!--                <el-table-column-->
                <!--                        prop="status"-->
                <!--                        header-align="center"-->
                <!--                        align="center"-->
                <!--                        label="活动状态"-->
                <!--                        width="145">-->
                <!--                    <template slot-scope='scope'>-->
                <!--                        <span v-if="scope.row.status == '1'">上线</span>-->
                <!--                        <span v-else-if="scope.row.status == '2'">下线</span>-->
                <!--                    </template>-->
                <!--                </el-table-column>-->
                <!--                <el-table-column-->
                <!--                        prop="name"-->
                <!--                        header-align="center"-->
                <!--                        align="center"-->
                <!--                        label="开始时间"-->
                <!--                        width="145">-->
                <!--                </el-table-column>-->
                <!--                <el-table-column-->
                <!--                        prop="name"-->
                <!--                        header-align="center"-->
                <!--                        align="center"-->
                <!--                        label="结束时间"-->
                <!--                        width="145">-->
                <!--                </el-table-column>-->
                <el-table-column
                        header-align="center"
                        align="center"
                        label="操作">
                    <template slot-scope="scope">
                        <el-button type="danger" plain size="mini"  @click="down(scope.row.foodId)" v-if="scope.row.foodStatus==0">下架</el-button>
                        <el-button type="success" plain size="mini"  @click="up(scope.row.foodId)" v-if="scope.row.foodStatus==1">上架</el-button>
                    </template>
                </el-table-column>
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
<!--        <supplier-add v-if="addVisible" ref="supplierAdd" @refreshDataList="getDataList"></supplier-add>-->
    </div>
</template>

<script>
    // import SupplierAdd from "./SupplierAdd";
    export default {
        name: "FoodList",
        data() {
            return{
                dataForm: {
                    foodName: '',
                    foodCategory:'',
                },
                dataList: [],
                foodCategoryList: [],
                pageIndex: 1,
                pageSize: 20,
                totalPage: 0,
                dataListLoading: false,
                // dataListSelections: [],
                addVisible: false,
                // upOrDownVisible:false
            }
        },
        components:{
            // SupplierAdd
        },
        mounted() {
            this.getDataList();
            this.getStatusList();
        },
        methods:{
            // 获取数据列表
            getDataList() {
                this.dataListLoading = true;
                this.$http({
                    url: "/foodInfo/list",
                    method: "get",
                    params: this.$http.adornParams({
                        foodName:this.dataForm.foodName,
                        categoryId:this.dataForm.foodCategory,
                        page: this.pageIndex,
                        size: this.pageSize,
                    })
                }).then(({ data }) => {
                    if (data && data.code === 0) {
                        this.dataList=data.data.content;
                        this.totalPage=data.data.totalElements;
                    } else {
                        this.dataList = [];
                        this.totalPage = 0;
                    }
                    this.dataListLoading = false;
                });
            },
            //新增
            SupplierAddButton() {
                this.addVisible=true
                this.$nextTick(()=>{
                    this.$refs.supplierAdd.init();
                })
            },
            //下架
            down(id) {
                this.$confirm(
                    `确定是否进行下架操作?`,
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
                            foodId:id
                        })
                    }).then(({ data }) => {
                        if (data && data.code === 0) {
                            this.$message.success("下架成功")
                        } else {
                            this.$message.error(data.msg)
                        }
                        this.dataListLoading = false;
                        this.getDataList();
                    });
                })
            },
            //上架
            up(id) {
                this.$confirm(
                    `确定是否进行上架操作?`,
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
                            foodId:id
                        })
                    }).then(({ data }) => {
                        if (data && data.code === 0) {
                            this.$message.success("上架成功")
                        } else {
                            this.$message.error(data.msg)
                        }
                        this.dataListLoading = false;
                        this.getDataList();
                    });
                })
            },
            // 查询食品类目下拉列表
            getStatusList() {
                this.$http({
                    url: "/foodCategory/list/foodCategoryName",
                    method: "get",
                    params: this.$http.adornParams()
                }).then(({ data }) => {
                    if (data && data.code === 0) {
                        this.foodCategoryList = data.data
                        this.foodCategoryList.unshift({
                            label: "全部", value: ""
                        })
                    } else {
                        this.foodCategoryList = [{ label: "全部", value: "" }];
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
        }
    }
</script>

<style scoped lang="scss">
    .mod-config{
        .tableData{
            height: calc(100vh - 220px);
        }
        .page{
            float:right;
            margin-top: 5px;
        }
    }
</style>

