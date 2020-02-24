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
            <el-form-item label="门店名称">
                <el-input  placeholder="请输入门店名称" clearable v-model="dataForm.storeName"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="getDataList()" type="warning">查询</el-button>
                <el-button  type="primary" @click="CustomerAddButton()">新增</el-button>
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
<!--                <el-table-column width="65" header-align="center" align="center" label>-->
<!--                    <template slot-scope="scope">-->
<!--                        <el-radio-->
<!--                                class="radio"-->
<!--                                :label="scope.row.serialnumber"-->
<!--                                v-model="radio"-->
<!--                                @change.native="getTemplateRow(scope.$index,scope.row)"-->
<!--                        >&nbsp;</el-radio>-->
<!--                    </template>-->
<!--                </el-table-column>-->
                <el-table-column

                        header-align="center"
                        align="center"
                        label="门店编号">
                        <template slot-scope="scope">
                            <span>{{ scope.row.storeId.toString().length==1? '00'+scope.row.storeId:scope.row.storeId.toString().length==2?'0'+scope.row.storeId:scope.row.storeId}}</span>
                        </template>
                </el-table-column>
<!--                <el-table-column-->
<!--                        prop="storeName"-->
<!--                        header-align="center"-->
<!--                        align="center"-->
<!--                        label="活动名称"-->
<!--                        width="145">-->
<!--                    <template slot-scope='scope'>-->
<!--                        <span v-if="scope.row.type == '1'">业务办理</span>-->
<!--                        <span v-else-if="scope.row.type == '2'">常用业务</span>-->
<!--                        <span v-else>其它业务</span>-->
<!--                    </template>-->
<!--                </el-table-column>-->
                <el-table-column
                        prop="storeName"
                        header-align="center"
                        align="center"
                        label="门店名称"
                        >
                </el-table-column>
                <el-table-column
                        prop="storePhone"
                        header-align="center"
                        align="center"
                        label="门店联系方式"
                        >
                </el-table-column>
                <el-table-column
                        prop="storeFzr"
                        header-align="center"
                        align="center"
                        label="门店负责人"
                >
                </el-table-column>
                <el-table-column
                        prop="storeAddress"
                        header-align="center"
                        align="center"
                        label="门店地址"
                        >
                </el-table-column>
                <el-table-column
                  prop="storeNote"
                  header-align="center"
                  align="center"
                  label="门店简介">
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
                        <el-button type="danger" plain size="small"  @click="del(scope.row.storeId)">删除</el-button>
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
        <customer-add v-if="addVisible" ref="customerAdd" @refreshDataList="getDataList"></customer-add>
        <!-- 弹窗, 新增 / 修改 -->
        <!--    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>-->
        <!--    <minimenu-see v-if="seeDetailVisible" ref="minimenuSee"></minimenu-see>-->
        <!--        <development-audit  v-if="seeDetailVisible" ref="developmentAudit"></development-audit>-->
        <!--        <development-add v-if="addVisible" ref="developmentAdd"></development-add>-->
    </div>
</template>

<script>
    import CustomerAdd from "./CustomerAdd";
    export default {
        name: "Customer",
        data() {
            return{
                dataForm: {
                    storeName: ''
                },
                dataList: [
                    // {
                    //     storeId:'123',
                    //     storeAddress:'',
                    //     storeFzr:'',
                    //     storeNote:'',
                    //     storeName:'',
                    //     storePhone:''
                    // }
                ],
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
        components:{
            CustomerAdd
        },
        mounted() {
            this.getDataList();
        },
        methods:{
            // 获取数据列表
            getDataList() {
                this.dataListLoading = true;
                this.$http({
                    url: "/store/list",
                    method: "get",
                    params: this.$http.adornParams({
                        storeName:this.dataForm.storeName,
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
            CustomerAddButton() {
                this.addVisible=true
                this.$nextTick(()=>{
                    this.$refs.customerAdd.init();
                })
            },
            //删除
            del(id) {
                this.$http({
                    url: "/store/delete",
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
