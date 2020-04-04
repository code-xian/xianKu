<template>
    <el-dialog
            :close-on-click-modal="false"
            :modal="true"
            :visible.sync="visible"
            width = "75%"
            style="margin-bottom: 5vh"
            top="7vh"
            title="新增"
    >
        <div class="box">
            <el-row style="margin-bottom: 10px;">
                <el-col :span="24">
                    <el-form
                        :model="dataForm"
                         ref="dataForm"
                        :rules="dataFormRules"
                         @keyup.enter.native="dataFormSubmit()"
                    >
                        <el-card class="box-card">
                            <table class="tabBox">
                                <tr>
                                    <td class="label"><span style="color:red;">*</span>交货方式：</td>
                                    <td class="content">
                                        <el-form-item prop="jiaoHuoFangShi">
                                            <el-select
                                                    v-model="dataForm.jiaoHuoFangShi"
                                                    value-key="value"
                                                    placeholder="请选择"
                                            >
                                                <el-option
                                                        v-for="item in jiaoHuoList"
                                                        :key="item.value"
                                                        :label="item.label"
                                                        :value="item.label"
                                                ></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </td>
                                    <td class="label"><span style="color:red;">*</span>交货日期：</td>
                                    <td class="content">
                                        <el-form-item prop="time">
                                            <el-date-picker
                                                    v-model="dataForm.time"
                                                    type="date"
                                                    placeholder="选择交货日期"
                                                    :picker-options="expireTimeOption"
                                            >
                                            </el-date-picker>
                                        </el-form-item>
                                    </td>
                                    <td class="label"><span style="color:red;">*</span>供应门店：</td>
                                    <td class="content">
                                        <el-form-item prop="storeName">
                                            <el-input

                                                    v-model="dataForm.storeName"
                                                    class="input-with-select"
                                                    placeholder="请选择供应门店"
                                                    :readonly="true"
                                            >
                                                <el-button slot="append" icon="el-icon-search" @click="selectSupplier()"></el-button>
                                            </el-input>
                                        </el-form-item>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">订单备注：</td>
                                    <td class="content" colspan="4">
                                        <el-form-item>
                                            <el-input
                                                    type="textarea"
                                                    :autosize="{ minRows: 2, maxRows: 2}"
                                                    placeholder="请输入备注"
                                                    v-model="dataForm.textarea">
                                            </el-input>
                                        </el-form-item>
                                    </td>
                                </tr>
                            </table>
                        </el-card>
                    </el-form>
                </el-col>
            </el-row>
            <el-row>
                <div class="title">
                    <i class="el-icon-share icon" style="color:coral;"></i>订单详情明细
                    <el-button style="float: right;margin:5px 10px" type="danger" round size="small" plain @click="delFood()">删除食品</el-button>
                    <el-button style="float: right;margin:5px 10px" type="primary" round size="small" plain @click="addFood()">添加食品</el-button>
<!--                    <el-select v-model="value" placeholder="请选择" @change="selectGetData()" style="float: right;margin-right:5px">-->
<!--                        <el-option-->
<!--                                v-for="item in categoryList"-->
<!--                                :key="item.value"-->
<!--                                :label="item.label"-->
<!--                                :value="item.value">-->
<!--                        </el-option>-->
<!--                    </el-select>-->
                </div>
                <div class="tableData">
                    <el-table
                            ref="table"
                            :summary-method="getSummaries"
                            :show-summary="true"
                            height="90%"
                            max-height="90%"
                            :data="dataList"
                            border
                            :header-cell-style="{background:'#f5f7fa'}"
                            v-loading="dataListLoading"
                            @selection-change="selectionChangeHandle"
                            style="width: 99%;">
<!--                        <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>-->
                        <el-table-column
                                type="index"
                                width="50"
                                header-align="center"
                                align="center"
                                label="No"
                        ></el-table-column>
                        <el-table-column
                                prop="foodId"
                                header-align="center"
                                align="center"
                                label="食品货号"
                        >
                        </el-table-column>
                        <el-table-column
                                prop="foodName"
                                header-align="center"
                                align="center"
                                label="食品名称"
                        >
                        </el-table-column>
                        <el-table-column
                                prop="foodPrice"
                                header-align="center"
                                align="center"
                                label="单价(元)"
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
                                header-align="center"
                                align="center"
                                prop="saleQuantity"
                                width="300"
                                label="数量">
                            <template slot-scope="scope">
                                <el-input-number v-model="scope.row.saleQuantity" type="number" controls-position="right" :min="1"></el-input-number>
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="stockName"
                                header-align="center"
                                align="center"
                                label="发货仓库">
                        </el-table-column>
                        <el-table-column
                                prop="foodDescription"
                                header-align="center"
                                align="center"
                                label="食品备注">
                        </el-table-column>
                        <el-table-column
                                fixed="right"
                                width="150"
                                header-align="center"
                                align="center"
                                prop="totalAmount"
                                label="金额(元)">
                            <template slot-scope="scope">
                                <div>{{scope.row.saleQuantity*scope.row.foodPrice}}</div>
                            </template>
                        </el-table-column>
                        <!--                        <el-table-column-->
                        <!--                                header-align="center"-->
                        <!--                                align="center"-->
                        <!--                                label="操作">-->
                        <!--                            <template slot-scope="scope">-->
                        <!--                                <el-button type="text" size="small"  @click="del(scope.row.storeId)">删除</el-button>-->
                        <!--                            </template>-->
                        <!--                        </el-table-column>-->
                    </el-table>
<!--                    <el-pagination-->
<!--                            class="page"-->
<!--                            @size-change="sizeChangeHandle"-->
<!--                            @current-change="currentChangeHandle"-->
<!--                            :current-page="pageIndex"-->
<!--                            :page-sizes="[10, 20, 50, 100]"-->
<!--                            :page-size="pageSize"-->
<!--                            :total="dataListSelections.length"-->
<!--                            layout="total, sizes, prev, pager, next, jumper">-->
<!--                    </el-pagination>-->
                    <div style="float: right;font-size: 18px;margin: 5px 10px">共{{dataList.length}}条</div>
                </div>
            </el-row>
        </div>
        <div style="float: right;font-size: 22px;font-weight: 600;color: gray;margin:-15px 10px 10px 0">
            总金额:{{total}}元
        </div>
        <span slot="footer" class="dialog-footer">
              <el-button @click="visible = false">取 消</el-button>
              <el-button type="primary" @click="dataFormSubmit()" :disabled="dataFormSubmitDisabled">确 定</el-button>
         </span>
        <sale-store-choose-list v-if="storeListDialogVisible" ref="storeListDialog" @sureStore="sureStore"></sale-store-choose-list>
        <food-list-add v-if="foodListDialogVisible" ref="foodListDialog" @sureFood="sureFood" :sureFood="dataList"></food-list-add>
    </el-dialog>
</template>

<script>
    import SaleStoreChooseList from "./SaleStoreChooseList";
    import FoodListAdd from "./FoodListAdd";
    export default {
        name: "SalesOrderAdd",
        data() {
            return{
                number:0,
                foodListDialogVisible:false,
                storeListDialogVisible:false,
                dataFormSubmitDisabled:false,
                dataList:[],
                dataListLoading:false,
                visible:false,
                pageIndex: 1,
                pageSize: 20,
                totalPage: 0,
                categoryList:[],
                jiaoHuoList:[
                    {value:1,label:"物流"},
                    {vaule:2,label:"空运"}
                ],
                dataForm:{
                    jiaoHuoFangShi:"",
                    time:"",
                    storeId:"",
                    textarea:"",
                    storeName:""
                },
                //日期范围
                expireTimeOption: {
                    disabledDate(time) {
                        return time.getTime() < Date.now() - 8.64e7;   //禁用以前的日期，今天不禁用
                    }
                },
                // dataListSelections: [],
                dataFormRules:{
                    jiaoHuoFangShi: [ { required: true, message: "交货方式不能为空" }],
                    time: [ { required: true, message: "交货日期不能为空" }],
                    storeName: [ { required: true, message: "门店不能为空" }],
                }
            }
        },
        computed:{
            total() {
                let a = 0;
                for(let i = 0; i<this.dataList.length;i++){
                    a += this.dataList[i].saleQuantity*this.dataList[i].foodPrice
                }
                return a
            }
        },
        components:{
            SaleStoreChooseList,
            FoodListAdd
        },
        methods:{
            init() {
                this.visible = true
                this.dataFormSubmitDisabled = false;
                this.dataForm.textarea = "";
                this.dataList = [];
                this.$nextTick(() => {
                    this.$refs["dataForm"].resetFields();
                });
            },
            // 表单提交
            dataFormSubmit(){
                this.$refs['dataForm'].validate(valid => {
                    if (valid) {
                        if (this.dataList == "") {
                            this.$message({
                                type: "info",
                                message: "请添加供应的食品"
                            });
                            return;
                        }
                        this.dataFormSubmitDisabled = true;
                        this.$http({
                            url: "/sale/create",
                            method: 'post',
                            data: this.$http.adornData({
                                submissionWay:this.dataForm.jiaoHuoFangShi,
                                submissionDate: this.dataForm.time,
                                storeId: this.dataForm.storeId,
                                saleRemarks: this.dataForm.textarea,
                                reviewer: this.$store.state.user.adminName,
                                saleDetailList: this.dataList,
                            })
                        }).then(({data}) => {
                            if (data && data.code === 0) {
                                this.$message({
                                    message: '操作成功',
                                    type: 'success',
                                    duration: 1500,
                                    onClose: () => {
                                        this.visible = false
                                        this.$emit('refreshDataList')
                                    }
                                })
                            } else {
                                this.dataFormSubmitDisabled = false
                                this.$message.error(data.msg)
                            }
                        })
                    }
                })
            },
            // 选择供应商弹窗
            selectSupplier(){
                this.storeListDialogVisible = true;
                this.$nextTick(() => {
                    this.$refs.storeListDialog.init();
                });
            },
            //确定门店
            sureStore (obj){
                this.storeListDialogVisible = false;
                this.dataForm.storeName = obj.storeName;
                this.dataForm.storeId = obj.storeId;
            },
            //确定食品
            sureFood(list) {
                this.foodListDialogVisible = false;
                // this.dataList.concat(list);
                // for (let i = 0; i < this.dataList.length; i++) {
                //     for (let j = 0; j < list.length; j++)
                //     {
                //         //判断添加的数组是否为空了
                //         if (this.dataList.length > 0) {
                //             if (this.dataList[i]["NAME"] === list[j]["NAME"]) {
                //                 jsonArr.splice(i, 1); //利用splice函数删除元素，从第i个位置，截取长度为1的元素
                //                 length1--;
                //                 console.log(jsonArr2[j]);//重复元素
                //             }
                //         }
                //     }
                //
                // }
                for (let n = 0; n < list.length; n++) {
                    this.dataList.push(list[n]);
                }
                this.$nextTick(() => {
                    this.dataList.forEach(item => {
                            this.$refs.table.toggleRowSelection(item, true)
                    })
                })
            },
            getInfoData() {

            },
            //添加食品
            addFood() {
                this.foodListDialogVisible = true;
                this.$nextTick(() => {
                    this.$refs.foodListDialog.init();
                });
            },
            //删除食品
            delFood() {

            },
            //合计
            getSummaries (param) {
                const { columns, data } = param
                const sums = []
                columns.forEach((column, index) => {
                    if (index === 0) {
                        sums[index] = '总计'
                    } else if ( index === 6|| index === 3) {
                        const values = data.map(item => Number(item[column.property]))
                        if (!values.every(value => isNaN(value))) {
                            sums[index] = values.reduce((prev, curr) => {
                                const value = Number(curr)
                                if (!isNaN(value)) {
                                    return prev + curr
                                } else {
                                    return prev
                                }
                            }, 0)
                        } else {
                            sums[index] = 'N/A'
                        }
                    } else {
                        sums[index] = '--'
                    }
                })
                this.$nextTick(()=>{
                    this.$refs.table.doLayout();
                })
                return sums
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
    }
</script>

<style lang="scss" scoped>
    .box {
        overflow: hidden;
    }
    .tableData{
        height: calc(100vh - 350px);
    }
    .page{
        float:right;
        margin-top: 5px;
    }
    .tabBox {
        width: 100%;
        td {
            font-size: 20px;
            line-height: 40px;
        }
        tr{
            height: 78px;
        }
        .label {
            text-align: center;
            color: #909399;
            font-weight: 600;
        }
    }
    .title {
        width: 100%;
        font-size: 22px;
        line-height: 45px;
        background-color: #f0f7ff;
    }
    .el-form-item {
         margin-bottom: 0;
    }
</style>



