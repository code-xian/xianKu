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
                <el-button  type="primary" @click="addOrUpdateHandle()">新增</el-button>
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
                <el-table-column width="65" header-align="center" align="center" label>
                    <template slot-scope="scope">
                        <el-radio
                                class="radio"
                                :label="scope.row.serialnumber"
                                v-model="radio"
                                @change.native="getTemplateRow(scope.$index,scope.row)"
                        >&nbsp;</el-radio>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="sort"
                        header-align="center"
                        align="center"
                        label="排序"
                        width="105">
                </el-table-column>
                <el-table-column
                        prop="type"
                        header-align="center"
                        align="center"
                        label="活动名称"
                        width="145">
                    <template slot-scope='scope'>
                        <span v-if="scope.row.type == '1'">业务办理</span>
                        <span v-else-if="scope.row.type == '2'">常用业务</span>
                        <span v-else>其它业务</span>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="name"
                        header-align="center"
                        align="center"
                        label="负责人"
                        width="145">
                </el-table-column>
                <el-table-column
                        prop="name"
                        header-align="center"
                        align="center"
                        label="所属部门"
                        width="145">
                </el-table-column>
                <el-table-column
                        prop="name"
                        header-align="center"
                        align="center"
                        label="活动地址"
                >
                </el-table-column>
                <el-table-column
                        prop="name"
                        header-align="center"
                        align="center"
                        label="活动类型"
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
                        label="活动状态"
                        width="145">
                    <template slot-scope='scope'>
                        <span v-if="scope.row.status == '1'">上线</span>
                        <span v-else-if="scope.row.status == '2'">下线</span>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="name"
                        header-align="center"
                        align="center"
                        label="开始时间"
                        width="145">
                </el-table-column>
                <el-table-column
                        prop="name"
                        header-align="center"
                        align="center"
                        label="结束时间"
                        width="145">
                </el-table-column>
                <el-table-column
                        header-align="center"
                        align="center"
                        label="操作">
                    <template slot-scope="scope">
                        <el-button type="text" size="small"  @click="audit(scope.row.id)">审核</el-button>
                        <el-button type="text" size="small"  @click="audit(scope.row.id)">详情</el-button>
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
    </div>
</template>

<script>
    export default {
        name: "SalesOrder",
        data() {
            return{
                dataForm: {
                    type: ''
                },
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
