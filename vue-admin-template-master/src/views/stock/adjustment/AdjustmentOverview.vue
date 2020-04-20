<template>
    <el-dialog
            :close-on-click-modal="false"
            :modal="true"
            :visible.sync="visible"
            width = "75%"
            style="margin-bottom: 5vh"
            top="7vh"
    >
        <div class="box">
            <el-row style="margin-bottom: 10px;" v-loading="dataListLoading">
                <el-col :span="5">
                    <el-card class="box-card">
                        <div style="margin:7px auto;display: inline-block;">
                            <p
                                    style="margin:0;font-size:27px;color:#a9d86e;font-weight:bolder;"
                            >{{data.stockName}}</p>
                            <p style="margin:0;font-size:20px;">{{data.stockType}}</p>
                        </div>
                    </el-card>
                </el-col>
                <el-col :span="19">
                    <el-card class="box-card">
                        <table class="tabBox">
                            <tr>
                                <td class="label">负责人：</td>
                                <td class="content">{{data.stockFzr}}</td>
                                <td class="label">仓库状态：</td>
                                <td class="content">{{data.stockStatus==0?"再用":"废弃"}}</td>
                            </tr>
                            <tr>
                                <td class="label">备注：</td>
                                <td class="content" colspan="4">{{data.stockNote}}</td>
                            </tr>
                        </table>
                    </el-card>

                </el-col>
            </el-row>
            <el-row>
                <div class="title">
                    <i class="el-icon-share icon" style="color:coral;"></i>仓库详情
                    <el-button style="float: right;margin:5px 10px" type="primary" round size="small" @click="move()">{{!this.moveFlag?"移库":"取消移库"}}</el-button>
                    <el-select v-model="category" placeholder="请选择食品类目" @change="selectGetData()" style="float: right;margin-right:5px">
                        <el-option
                                v-for="item in categoryList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </div>
                <div class="tableData">
                    <el-form
                            :model="form"
                            ref="ruleForm"
                            style="height: 100%;"
                            >
<!--                            :rules="form.rules2"-->
                    <el-table
                            height="90%"
                            max-height="90%"
                            :data="form.dataList"
                            border
                            :header-cell-style="{background:'#f5f7fa'}"
                            v-loading="stockDetailLoading"
                            @selection-change="selectionChangeHandle"
                            ref="table"
                            style="width: 99%;">
                        <el-table-column type="selection" header-align="center" align="center" width="50" v-if="moveFlag" key="0" ></el-table-column>
                        <el-table-column
                                key="1"
                                type="index"
                                width="50"
                                header-align="center"
                                align="center"
                                label="No"
                        ></el-table-column>
                        <el-table-column
                                key="2"
                                prop="foodId"
                                header-align="center"
                                align="center"
                                label="食品货号"
                        >
                        </el-table-column>
                        <el-table-column
                                key="3"
                                prop="foodName"
                                header-align="center"
                                align="center"
                                label="食品名称"
                        >
                        </el-table-column>
                        <el-table-column
                                key="4"
                                prop="foodPrice"
                                header-align="center"
                                align="center"
                                label="价格"
                        >
                        </el-table-column>
                        <el-table-column
                                key="5"
                                prop="categoryName"
                                header-align="center"
                                align="center"
                                label="食品类目"
                        >
                        </el-table-column>
                        <el-table-column
                                key="6"
                                prop="shelfLife"
                                header-align="center"
                                align="center"
                                label="保质期"
                        >
                        </el-table-column>
                        <el-table-column
                                key="7"
                                prop="stock"
                                header-align="center"
                                align="center"
                                label="库存">
                        </el-table-column>
                        <el-table-column
                                key="8"
                                prop="foodDescription"
                                header-align="center"
                                align="center"
                                label="食品备注">
                        </el-table-column>
                        <el-table-column
                                key="9"
                                v-if="moveFlag"
                                header-align="center"
                                align="center"
                                width="300"
                                label="移库数量">
                            <template slot-scope="scope">
                                <el-form-item :prop="'dataList.' + scope.$index + '.saleQuantity'">
                                    <el-input-number v-model="scope.row.saleQuantity" type="number" controls-position="right" :min="1" :max="scope.row.stock"></el-input-number>
<!--                                    <input type="text" v-model="scope.$index">-->
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column
                                key="10"
                                v-if="moveFlag"
                                header-align="center"
                                align="center"
                                label="转移仓库">
                            <template slot-scope="scope">
<!--                                    :rules="scope.row.flg? rules:[]"-->
                                <el-form-item
                                    :prop="'dataList.' + scope.$index + '.stockId'"
                                      >
<!--                                    :rules="rules2.stockId"-->
                                <el-select
                                        v-model="scope.row.stockId"
                                        value-key="value"
                                        placeholder="请选择"
                                >
                                    <el-option
                                            v-for="item in stockNameList"
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value"
                                    ></el-option>
                                </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                    </el-table>
                    </el-form>
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
            </el-row>
        </div>
        <span slot="footer" class="dialog-footer" v-if="moveFlag">
              <el-button @click="visible = false">取 消</el-button>
              <el-button type="primary" @click="dataFormSubmit()" :disabled="dataFormSubmitDisabled">确 定</el-button>
         </span>
    </el-dialog>
</template>

<script>

    export default {
        data () {
            return {
                form:{
                    dataList:[],
                    rules2:{
                        saleQuantity:[
                            { required: true, message: '移库数量不能为空', trigger: 'blur'}
                        ],
                        stockId:[
                            { required: true, message: '仓库不能为空', trigger: 'blur'}
                        ]
                    }
                },
                dataListLoading:false,
                stockDetailLoading:false,
                visible:false,
                pageIndex: 1,
                pageSize: 20,
                totalPage: 0,
                categoryList:[],
                stockNameList: [],
                dataListSelections: [],     //增库存数组数据
                decreaseDataList:[],   //减库存数组数据
                category:"",
                stockId:"",
                data: {},
                moveData:{
                    moveQuantity: "",
                    moveStock:"",
                },
                moveFlag:false,
                dataFormSubmitDisabled:false,
                dispaly:{
                    display:"none"
                },
                rules: [{ required: true, message: '移库数量不能为空', trigger: 'blur'}],



            };
        },
        // beforeUpdate() {
        //     this.$nextTick(()=>{
        //         this.$refs.table.doLayout();
        //     })
        // },
        methods: {
            init(id) {
                this.visible = true;
                this.moveFlag = false;
                this.stockId = id
                this.pageIndex = 1;
                this.getInfoData();
                this.getStatusList();
                this.selectGetData();
            },
            getInfoData() {
                this.dataListLoading = true;
                this.$nextTick(() => {
                    this.$http({
                        url: "/stock/detail",
                        method: "get",
                        params: this.$http.adornParams({
                            stockId:this.stockId,
                        })
                    }).then(({ data }) => {
                        if (data && data.code === 0) {
                            this.data = data.data
                        } else {
                            this.$message.error(data.msg);
                        }
                    });
                });
                this.dataListLoading = false;
            },
            selectGetData() {
                this.stockDetailLoading = true;
                this.$nextTick(() => {
                    this.$http({
                        url: "/foodStock/list",
                        method: "get",
                        params: this.$http.adornParams({
                            stockId:this.stockId,
                            categoryId : this.category,
                            page:this.pageIndex,
                            size:this.pageSize,
                        })
                    }).then(({ data }) => {
                        if (data && data.code === 0) {
                            this.form.dataList = data.data.content
                        } else {
                            this.$message.error(data.msg);
                        }
                    });
                });
                this.stockDetailLoading = false;
            },
            //移库
            move() {
                this.$refs['ruleForm'].resetFields();
                this.$refs.table.clearSelection();
                if(!this.moveFlag){
                    this.moveFlag=true;

                }else if (this.moveFlag) {
                    this.moveFlag=false;
                }
                this.$nextTick(()=>{
                    this.$refs.table.doLayout();
                })
                this.getStockNameList();
            },
            //确定移库食品
            dataFormSubmit() {
                if (this.dataListSelections == "") {
                    this.$message({
                        type: "info",
                        message: "请选择需要转移仓库的食品"
                    });
                    return;
                }
                for(var i = 0 ;i<this.dataListSelections.length; i++){
                    if (this.dataListSelections[i].saleQuantity == ""||this.dataListSelections[i].saleQuantity == null) {
                        this.$message.error("请填写移库数量")
                        return;
                    }else if (this.dataListSelections[i].stockId == ""||!this.dataListSelections[i].stockId) {
                        this.$message.error("请填写移库仓库")
                        return;
                    }
                }
                console.log(this.dataListSelections, this.stockId);
                // this.$refs['ruleForm'].validate(valid => {
                //     if (valid) {
                //         // console.log(valid);
                //         console.log(this.$refs.table.store);
                //     } else {
                //         console.log(valid);
                //     }
                // });

                // this.moveFlag = false;
                this.$http({
                    url: "/foodStock/move",
                    method: 'post',
                    data: this.$http.adornData({
                        inList:this.dataListSelections,
                        stockId: this.stockId
                    })
                }).then(({data}) => {
                    if (data && data.code === 0) {
                        this.$message({
                            message: '操作成功',
                            type: 'success',
                            duration: 1500,
                            onClose: () => {
                                this.visible = false;
                                this.$emit('refreshDataList')
                            }
                        })
                    } else {
                        this.$message.error(data.msg)
                    }
                })
            },
            // 查询食品类目下拉列表
            getStatusList() {
                this.$http({
                    url: "/foodCategory/list/foodCategoryName",
                    method: "get",
                    params: this.$http.adornParams()
                }).then(({ data }) => {
                    if (data && data.code === 0) {
                        this.categoryList = data.data
                        this.categoryList.unshift({
                            label: "全部", value: ""
                        })
                    } else {
                        this.categoryList = [{ label: "全部", value: "" }];
                    }
                });
            },
            // 查询仓库类型下拉列表
            getStockNameList() {
                this.$http({
                    url: "/stock/list/stockName",
                    method: "get",
                    params: this.$http.adornParams()
                }).then(({ data }) => {
                    if (data && data.code === 0) {
                        // this.stockNameList=data.data
                        this.stockNameList = data.data.filter(item => item.value != this.stockId)
                        console.log(this.stockNameList);
                    } else {
                        this.stockNameList = [];
                    }
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
            selectionChangeHandle(val) {
                this.dataListSelections = val;
            },
        }
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
         .label {
             width: 130px;
             color: #909399;
             font-weight: 600;
         }
         .content {
             font-size: 18px;
         }
     }
    .title {
        width: 100%;
        font-size: 22px;
        line-height: 45px;
        background-color: #f0f7ff;
    }
    /*.el-checkbox__input {*/
    /*    display:*/
    /*}*/
</style>
