<template>
    <!-- 地址列表的查询组件 -->
    <el-dialog
            title="供应商选择"
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
            <el-form-item>
                <el-input v-model="dataForm.supllierName"  clearable>
                    <template slot="prepend">供应商名称</template>
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button icon="el-icon-search" type="warning" @click="getCasDataInfo()">查询</el-button>
            </el-form-item>
        </el-form>
        <div class="tableData">
            <el-table
                    :data="dataList"
                    @row-click="rowClick"
                    border
                    max-height="90%"
                    :header-cell-style="{background:'#f5f7fa'}"
                    v-loading="dataListLoading"
                    style="width: 99%;"
            >
                <el-table-column  width="65">
                    <template slot-scope="scope">
                        <el-radio
                                class="radio"
                                v-model="radio"
                                :label="scope.row.supplierId"
                                @change.native="getCurrentRow(scope.$index,scope.row)"
                        >&nbsp;</el-radio>
                    </template>
                </el-table-column>
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
                        prop="supplierName"
                        header-align="center"
                        align="center"
                        label="供应商名称"
                >
                </el-table-column>
                <el-table-column
                        prop="supplierPhone"
                        header-align="center"
                        align="center"
                        label="供应商联系方式"
                >
                </el-table-column>
                <el-table-column
                        prop="supplierFzr"
                        header-align="center"
                        align="center"
                        label="供应商负责人"
                >
                </el-table-column>
                <el-table-column
                        prop="supplierAddress"
                        header-align="center"
                        align="center"
                        label="供应商地址"
                >
                </el-table-column>
                <el-table-column
                        prop="supplierNote"
                        header-align="center"
                        align="center"
                        label="供应商简介">
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
        name: "SupplierChooseList",
        data() {
            return{
                dataForm: {
                    supplierName: ""
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
                this.dataForm.supllierName = "";
                this.dialogVisible = true;
                this.getCasDataInfo();
            },
            // 获取数据列表
            getCasDataInfo(){
                this.dataListLoading = true;
                this.$http({
                    url: "/supplier/list",
                    method: "get",
                    params: this.$http.adornParams({
                        supplierName:this.dataForm.supplierName,
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
            getCurrentRow(index, row) {
                this.templateSelection = row;
            },
            //确定提交
            sureCar(){
                if (this.templateSelection == "") {
                    this.$message({
                        type: "info",
                        message: "请选择供应商"
                    });
                    return;
                }
                var obj = this.templateSelection;
                this.$emit("sureStore", obj);
            },
            rowClick(row, event, column) {
                this.radio = row.supplierId;
                this.getCurrentRow("", row);
            },
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

        },
    }
</script>

<style scoped>
    .tableData{
        height: calc(100vh - 430px);
    }
</style>



