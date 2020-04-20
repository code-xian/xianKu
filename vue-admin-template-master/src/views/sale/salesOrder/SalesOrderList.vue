<template>
    <div class="mod-config">
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="init()">
            <el-form-item label="订单状态" prop="type">
                <el-select v-model="dataForm.orderStatus">
                    <el-option
                            v-for="(item , index) in typeList"
                            :key="index"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="门店名称" >
                <el-input  placeholder="请输入门店名称" clearable v-model="dataForm.storeName"></el-input>
            </el-form-item>
            <el-form-item label="供应单号" >
                <el-input  placeholder="请输入供应单号" clearable v-model="dataForm.saleId"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="init()" type="warning">查询</el-button>
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
                        prop="saleId"
                        header-align="center"
                        align="center"
                        label="订单编号"
                        width="105">
                </el-table-column>
                <el-table-column
                        prop="storeName"
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
                        prop="storePhone"
                        header-align="center"
                        align="center"
                        label="门店电话"
                        width="145">
                </el-table-column>
                <el-table-column
                        prop="submissionDate"
                        header-align="center"
                        align="center"
                        :formatter="dateFormat"
                        label="交货期限"
                        width="145">
                </el-table-column>
                <el-table-column
                        show-overflow-tooltip
                        prop="storeAddress"
                        header-align="center"
                        align="center"
                        label="门店地址"
                >
                </el-table-column>
                <el-table-column
                        prop="submissionWay"
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
                        prop="reviewer"
                        header-align="center"
                        align="center"
                        label="审核人"
                        width="145">
                </el-table-column>
                <el-table-column
                        header-align="center"
                        align="center"
                        label="订单状态"
                        width="145">
                    <template slot-scope="scope">
                        <div slot="reference">
                            {{ scope.row.saleStatus=="12"?"未审核":scope.row.saleStatus=="10"?"审核通过":scope.row.saleStatus=="11"?"审核未通过":"" }}
                        </div>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="purchaseAmount"
                        header-align="center"
                        align="center"
                        label="订单总金额"
                        width="145">
                </el-table-column>
                <el-table-column
                        show-overflow-tooltip
                        prop="createTime"
                        header-align="center"
                        align="center"
                        label="创建时间"
                        :formatter="dateFormat"
                        width="145">
                </el-table-column>
                <el-table-column
                        show-overflow-tooltip
                        prop="saleRemarks"
                        header-align="center"
                        align="center"
                        label="备注"
                        width="145">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="200"
                        header-align="center"
                        align="center"
                        label="操作">
                    <template slot-scope="scope">
                        <el-button type="primary" size="mini"  @click="audit(scope.row.saleId,true,scope.row.purchaseAmount)" v-if="scope.row.saleStatus==12">审核</el-button>
                        <el-button type="primary" size="mini"  @click="audit(scope.row.saleId,false,scope.row.purchaseAmount)" v-if="scope.row.saleStatus==10||scope.row.saleStatus==11">详情</el-button>
                        <el-button type="primary" size="mini"  @click="fahuo(scope.row.saleId,false,scope.row.purchaseAmount)" v-if="scope.row.saleStatus==10||scope.row.saleStatus==11">发货</el-button>
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
        <sales-order-audit v-if="auditVisible" ref="audit" @refreshDataList="init"></sales-order-audit>
        <sales-order-add v-if="addVisible" ref="add" @refreshDataList="init"></sales-order-add>
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
                dataForm: {
                    saleId:"",
                    storeName:"",
                    orderStatus:"",
                },
                dataList: [],
                typeList : [
                    {label : '全部' , value : ''},
                    {label : '未审核' , value : '12'},
                    {label : '审核通过' , value : '10'},
                    {label : '审核不通过' , value : '11'},
                ],
                pageIndex: 1,
                pageSize: 20,
                totalPage: 0,
                dataListLoading: false,
                dataListSelections: [],
                addVisible: false,
                seeDetailVisible : false,
                currentPage:'',
                crumbs: '',
                tagsArry:[]
            }
        },
        mounted() {
            this.init();
        },
        methods:{
            init() {
                this.pageIndex = 1;
                this.getDataList();
                // this.getDataList2();
            },
            getDataList() {
                this.dataListLoading = true;
                this.$http({
                    url: "/sale/list",
                    method: "get",
                    params: this.$http.adornParams({
                        saleId:this.dataForm.saleId,
                        storeName:this.dataForm.storeName,
                        orderStatus:this.dataForm.orderStatus,
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
            fahuo(){
                this.gotoPage('sendList')
            },
            // 跳转页面 路由名称和参数
            gotoPage(name, params) {
                this.currentPage = name
                this.crumbs = this.paths[name]
                this.$router.replace({name, params})

                // if (!this.keepAliveData.includes(name)) {
                //     // 如果标签超过8个 则将第一个标签删除
                //     if (this.tagsArry.length == 8) {
                //         this.tagsArry.shift()
                //     }
                //     this.tagsArry.push({name, text: this.nameToTitle[name]})
                // }
            },
            // getDataList2() {
            //     this.$socket.emit('push_event',this.typeList)
            //     this.dataListLoading = true;
            //     this.$http({
            //         url: "/socket/send",
            //         method: "get",
            //         params: this.$http.adornParams({
            //             loginUser:"linzf",
            //             content:"1231"
            //         })
            //     }).then(({ data }) => {
            //         if (data && data.code === 0) {
            //             this.dataList=data.data.content;
            //             this.totalPage=data.data.totalElements;
            //         } else {
            //             this.dataList = [];
            //             this.totalPage = 0;
            //         }
            //         this.dataListLoading = false;
            //     });
            // },
            audit(id,flag,purchaseAmount) {
                this.auditVisible=true;
                this.$nextTick(()=>{
                    this.$refs.audit.init(id,flag,purchaseAmount)
                })
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
