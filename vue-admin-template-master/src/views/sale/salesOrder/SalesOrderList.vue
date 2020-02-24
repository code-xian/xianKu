<template>
    <div class="mod-config">
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
            <el-form-item label="类型" prop="type">
                <el-select v-model="dataForm.type">
                    <el-option
                            v-for="(item , index) in typeList"
                            :key="index"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="活动名称" >
                <el-input  placeholder="请输入活动名称" clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="getDataList()" type="warning">查询</el-button>
                <el-button  type="primary" @click="add()">新增</el-button>
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
                    @selection-change="selectionChangeHandle"
                    style="width: 99%;">
                <el-table-column
                        type="index"
                        width="50"
                        header-align="center"
                        align="center"
                        label="No"
                ></el-table-column>
                <el-table-column
                        prop="sort"
                        header-align="center"
                        align="center"
                        label="订单编号"
                        width="105">
                </el-table-column>
                <el-table-column
                        prop="type"
                        header-align="center"
                        align="center"
                        label="门店名字"
                        width="145">
<!--                    <template slot-scope='scope'>-->
<!--                        <span v-if="scope.row.type == '1'">业务办理</span>-->
<!--                        <span v-else-if="scope.row.type == '2'">常用业务</span>-->
<!--                        <span v-else>其它业务</span>-->
<!--                    </template>-->
                </el-table-column>
                <el-table-column
                        prop="name"
                        header-align="center"
                        align="center"
                        label="门店电话"
                        width="145">
                </el-table-column>
                <el-table-column
                        prop="name"
                        header-align="center"
                        align="center"
                        label="交货期限"
                        width="145">
                </el-table-column>
                <el-table-column
                        prop="name"
                        header-align="center"
                        align="center"
                        label="门店地址"
                >
                </el-table-column>
                <el-table-column
                        prop="name"
                        header-align="center"
                        align="center"
                        label="交货方式"
                        width="145">
                </el-table-column>

                <!--        <el-table-column-->
                <!--          prop="descs"-->
                <!--          header-align="center"-->
                <!--          align="center"-->
                <!--          label="活动状态">-->
                <!--        </el-table-column>-->
                <el-table-column
                        prop="status"
                        header-align="center"
                        align="center"
                        label="审核人"
                        width="145">
                </el-table-column>
                <el-table-column
                        prop="name"
                        header-align="center"
                        align="center"
                        label="订单状态"
                        width="145">
                </el-table-column>
                <el-table-column
                        prop="name"
                        header-align="center"
                        align="center"
                        label="订单总金额"
                        width="145">
                </el-table-column>
                <el-table-column
                        prop="name"
                        header-align="center"
                        align="center"
                        label="创建时间"
                        width="145">
                </el-table-column>
                <el-table-column
                        prop="name"
                        header-align="center"
                        align="center"
                        label="备注"
                        width="145">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="100"
                        header-align="center"
                        align="center"
                        label="操作">
                    <template slot-scope="scope">
                        <el-button type="text" size="small"  @click="audit(scope.row.id,true)" v-if="scope.row.id==0">审核</el-button>
                        <el-button type="text" size="small"  @click="audit(scope.row.id,false)" v-if="scope.row.id==1">详情</el-button>
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
        <!-- 弹窗, 新增 / 修改 -->
        <!--    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>-->
        <!--    <minimenu-see v-if="seeDetailVisible" ref="minimenuSee"></minimenu-see>-->
<!--        <development-audit  v-if="seeDetailVisible" ref="developmentAudit"></development-audit>-->
<!--        <development-add v-if="addVisible" ref="developmentAdd"></development-add>-->
        <sales-order-audit v-if="auditVisible" ref="audit" @refreshDataList="getDataList"></sales-order-audit>
        <sales-order-add v-if="addVisible" ref="add" @refreshDataList="getDataList"></sales-order-add>
    </div>
</template>

<script>
    import SalesOrderAudit from "./SalesOrderAudit";
    import SalesOrderAdd from "./SalesOrderAdd";
    export default {
        name: "SalesOrder",
        data() {
            return{
                auditVisible:false,
                addVisible:false,
                dataForm: {},
                dataList: [],
                typeList : [
                    {label : '所有活动' , value : ''},
                    {label : '我参与的' , value : '1'},
                    {label : '我负责的' , value : '2'},
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

            },
            audit(id,flag) {

            },
            add() {
                this.addVisible=true;
                this.$nextTick(()=>{
                    this.$refs.add.init()
                })
            },
            // 多选
            selectionChangeHandle(val) {
                this.dataListSelections = val;
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
        components:{
            SalesOrderAudit,
            SalesOrderAdd
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
