<template>
    <div class="mod-config">
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="init()">
            <el-form-item label="单据类型" prop="type">
                <el-select v-model="dataForm.documentType">
                    <el-option
                            v-for="(item , index) in typeList"
                            :key="index"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="仓库" >
                <el-input  placeholder="请输入仓库名称" clearable v-model="dataForm.stockName"></el-input>
            </el-form-item>
            <el-form-item label="单据编号" >
                <el-input  placeholder="请输入单据编号" clearable v-model="dataForm.documentNumber"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="init()" type="warning">查询</el-button>
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
                        prop="foodName"
                        header-align="center"
                        align="center"
                        label="食物名称"
                       >
                </el-table-column>
                <el-table-column
                        prop="stockName"
                        header-align="center"
                        align="center"
                        label="仓库名称"
                        >
                </el-table-column>
                <el-table-column
                        prop="documentNumber"
                        show-overflow-tooltip
                        header-align="center"
                        align="center"
                        label="单据编号"
                       >
                </el-table-column>
                <el-table-column
                        prop="incOrDec"
                        header-align="center"
                        align="center"
                        label="库存增减"
                        >
                </el-table-column>
                <el-table-column
                        prop="quantity"
                        header-align="center"
                        align="center"
                        label="增减数量"
                >
                </el-table-column>
                <el-table-column
                        prop="restStock"
                        header-align="center"
                        align="center"
                        label="操作后库存"
                >
                </el-table-column>
                <el-table-column
                        header-align="center"
                        align="center"
                        label="单据类型"
                >
                    <template slot-scope="scope">
                        <div slot="reference">
                            {{ scope.row.documentType=="1"?"入库":scope.row.documentType=="2"?"出库":"" }}
                        </div>
                    </template>
                </el-table-column>
                <el-table-column
                        show-overflow-tooltip
                        prop="createTime"
                        header-align="center"
                        align="center"
                        label="创建时间"
                        :formatter="dateFormat"
                >
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
    </div>
</template>

<script>
    export default {
        name: "Log",
        data() {
            return{
                auditVisible:false,
                addVisible:false,
                dataForm: {
                    documentNumber:"",
                    stockName:"",
                    documentType:"",
                },
                dataList: [],
                typeList : [
                    {label : '全部' , value : ''},
                    {label : '入库' , value : '1'},
                    {label : '出库' , value : '2'},
                ],
                pageIndex: 1,
                pageSize: 20,
                totalPage: 0,
                dataListLoading: false,
                dataListSelections: [],
                addVisible: false,
                seeDetailVisible : false
            }
        },
        mounted() {
            this.init();
        },
        methods:{
            init() {
                this.pageIndex = 1;
                this.getDataList();
            },
            getDataList() {
                this.dataListLoading = true;
                this.$http({
                    url: "/stockLog/list",
                    method: "get",
                    params: this.$http.adornParams({
                        documentNumber:this.dataForm.documentNumber,
                        stockName:this.dataForm.stockName,
                        documentType:this.dataForm.documentType,
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
            dateFormat(row, column, cellValue, index) {
                const date = cellValue
                if(date == null){return ''};
                return this.$moment(date).format("YYYY-MM-DD")
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




