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
            <el-form-item label="管理员名字">
                <el-input  placeholder="请输入管理员名字" clearable v-model="dataForm.userName"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="getDataList()" type="warning">查询</el-button>
                <el-button  type="primary" @click="UserAddButton()">新增</el-button>
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
                        label="管理员ID">
                    <template slot-scope="scope">
                        <span>{{ scope.row.adminId.toString().length==1? '00'+scope.row.adminId:scope.row.adminId.toString().length==2?'0'+scope.row.adminId:scope.row.adminId}}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="adminName"
                        header-align="center"
                        align="center"
                        label="管理员名字"
                >
                </el-table-column>
                <el-table-column
                        prop="adminUsername"
                        header-align="center"
                        align="center"
                        label="管理员账号"
                >
                </el-table-column>
                <el-table-column
                        prop="adminPassword"
                        header-align="center"
                        align="center"
                        label="管理员密码"
                >
                </el-table-column>
                <el-table-column
                        header-align="center"
                        align="center"
                        label="操作">
                    <template slot-scope="scope">
                        <el-button type="danger" plain size="small"  @click="del(scope.row.adminId)">删除</el-button>
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
        <user-control-add v-if="addVisible" ref="userControlAdd" @refreshDataList="getDataList"></user-control-add>
    </div>
</template>

<script>
    import UserControlAdd from "./UserControlAdd";
    export default {
        name: "UserControl",
        data() {
            return{
                dataForm: {
                    userName: '',
                },
                dataList: [

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
            UserControlAdd
        },
        mounted() {
            this.getDataList();
        },
        methods:{
            // 获取数据列表
            getDataList() {
                this.dataListLoading = true;
                this.$http({
                    url: "/admin/list",
                    method: "get",
                    params: this.$http.adornParams({
                        adminName:this.dataForm.userName,
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
            UserAddButton() {
                this.addVisible=true
                this.$nextTick(()=>{
                    this.$refs.userControlAdd.init();
                })
            },
            //删除
            del(id) {
                this.$http({
                    url: "/admin/delete",
                    method: "post",
                    data: this.$http.adornData({
                        adminId:id
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
