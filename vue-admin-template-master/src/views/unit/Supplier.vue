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
            <el-form-item label="供应商名称">
                <el-input  placeholder="请输入供应商名称" clearable v-model="dataForm.supplierName"></el-input>
            </el-form-item>
            <el-form-item label="供应商名称">
                <el-select v-model="dataForm.supplierType" clearable>
                    <el-option
                        v-for="item in foodTypeList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.label"
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

                        header-align="center"
                        align="center"
                        label="供应商编号">
                    <template slot-scope="scope">
                        <span>{{ scope.row.supplierId.toString().length==1? '00'+scope.row.supplierId:scope.row.supplierId.toString().length==2?'0'+scope.row.supplierId:scope.row.supplierId}}</span>
                    </template>
                </el-table-column>
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
                        prop="supplierType"
                        header-align="center"
                        align="center"
                        label="供应商类型"
                >
                </el-table-column>
                <el-table-column
                        prop="supplierNote"
                        header-align="center"
                        align="center"
                        label="供应商简介">
                </el-table-column>
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
                        <el-button type="text" size="small"  @click="del(scope.row.storeId)">删除</el-button>
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
        <supplier-add v-if="addVisible" ref="supplierAdd" @refreshDataList="getDataList"></supplier-add>
    </div>
</template>

<script>
    import SupplierAdd from "./SupplierAdd";
    export default {
        name: "Supplier",
        data() {
            return{
                dataForm: {
                    supplierName: '',
                    supplierType:'',
                },
                dataList: [

                ],
                foodTypeList:[
                    {
                        value:'冷冻食品类',
                        label:'冷冻食品类'
                    },{
                        value:'酒水饮料类',
                        label:'酒水饮料类'
                    },{
                        value:'速食类',
                        label:'速食类'
                    },{
                        value:'膨化食品类',
                        label:'膨化食品类'
                    },{
                        value:'糖果类',
                        label:'糖果类'
                    },
                ],
                pageIndex: 1,
                pageSize: 20,
                totalPage: 0,
                dataListLoading: false,
                // dataListSelections: [],
                addVisible: false,
            }
        },
        components:{
            SupplierAdd
        },
        mounted() {
            this.getDataList();
        },
        methods:{
            // 获取数据列表
            getDataList() {
                this.dataListLoading = true;
                this.$http({
                    url: "/supplier/list",
                    method: "get",
                    params: this.$http.adornParams({
                        supplierName:this.dataForm.supplierName,
                        supplierType:this.dataForm.supplierType,
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
            //删除
            del(id) {
                this.$http({
                    url: "/supplier/delete",
                    method: "post",
                    data: this.$http.adornData({
                        storeId:id
                    })
                }).then(({ data }) => {
                    if (data && data.code === 0) {
                        this.$message.success("删除成功")
                    } else {
                        this.$message.error(data.msg)
                    }
                    this.dataListLoading = false;
                    this.getDataList();
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
