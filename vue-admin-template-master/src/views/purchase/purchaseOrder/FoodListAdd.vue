<template>
    <!-- 地址列表的查询组件 -->
    <el-dialog
            title="食品选择"
            :close-on-click-modal="false"
            :modal="true"
            :modal-append-to-body="false"
            :append-to-body="true"
            :visible.sync="dialogVisible"
            width = "75%"
            style="margin-bottom: 5vh"
            top="7vh"
    >
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getCasDataInfo()">
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
                <el-button icon="el-icon-search" type="warning" @click="getCasDataInfo()">查询</el-button>
            </el-form-item>
        </el-form>
        <div class="tableData">
            <el-table
                    :data="dataList"
                    border
                    max-height="90%"
                    height="90%"
                    :header-cell-style="{background:'#f5f7fa'}"
                    v-loading="dataListLoading"
                    style="width: 99%;"
                    @selection-change="selectionChangeHandle"
            >
                <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
                <!--<el-radio type="selection" header-align="center" align="center" width="50"> </el-radio>-->
                <!--            <el-table-column prop="rowId" header-align="center" align="center" width="150" label="ID"></el-table-column>-->
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
                        prop="purchasePrice"
                        header-align="center"
                        align="center"
                        label="食品进价"
                >
                </el-table-column>
                <el-table-column
                        prop="categoryName"
                        header-align="center"
                        align="center"
                        label="食品类目"
                >
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
                        show-overflow-tooltip
                        label="食品描述"
                >
                </el-table-column>
            </el-table>
            <el-pagination
                    style="float:right;margin-top: 5px;"
                    @size-change="sizeChangeHandle"
                    @current-change="currentChangeHandle"
                    :current-page="pageIndex"
                    :page-sizes="[10, 20, 50, 100]"
                    :page-size="pageSize"
                    :total="totalPage"
                    layout="total, sizes, prev, pager, next, jumper"
            ></el-pagination>
        </div>
        <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">关 闭</el-button>
      <el-button type="primary" @click="sureCar()">确 定</el-button>
    </span>
    </el-dialog>
</template>

<script>
    export default {
        name: "FoodListAdd",
        data() {
            return{
                foodCategoryList:[],
                dataForm: {
                    foodName: "",
                    foodCategory:"",
                    stockName:""
                },
                dialogVisible: false,
                dataList: [],
                pageIndex: 1,
                pageSize: 10,
                totalPage: 0,
                dataListLoading: false,
                dataListSelections: [],
                radio: "",
                templateSelection: ""
            }
        },
        methods:{
            init(){
                // this.dataForm.csrname = "";
                this.dialogVisible = true;
                this.getCasDataInfo();
                this.getStatusList();
            },
            // 获取数据列表
            getCasDataInfo(){
                this.dataListLoading = true;
                this.$http({
                    url: "/purchase/foodList",
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
            //获取当前行
            // getCurrentRow(index, row) {
            //     console.log(row);
            //     this.templateSelection = row;
            // },
            // 多选
            selectionChangeHandle(val) {
                this.dataListSelections = val;
            },
            //确定提交
            sureCar(){
                if (this.dataListSelections == "") {
                    this.$message({
                        type: "info",
                        message: "请选择食品"
                    });
                    return;
                }
                var list = this.dataListSelections;
                this.$emit("sureFood", list);
            },
            // rowClick(row, event, column) {
            //     this.radio = row.storeId;
            //     this.getCurrentRow("", row);
            // },
            // 每页数
            sizeChangeHandle(val) {
                this.pageSize = val;
                this.pageIndex = 1;
                this.getCasDataInfo();
            },
            // 当前页
            currentChangeHandle(val) {
                this.pageIndex = val;
                this.getCasDataInfo();
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
        },
    }
</script>

<style scoped>
    .tableData{
        height: calc(100vh - 430px);
    }
</style>


