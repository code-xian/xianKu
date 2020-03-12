<template>
    <div>
        <el-dialog title="新增食品"
                   :visible.sync="dialogFormVisible"
                   :modal="true">
            <el-form :model="form"
                     ref="dataForm"
                     :rules="formRules"
                     @keyup.enter.native="dataFormSubmit()"
                     label-width="150px">
                <el-form-item label="食品名称" prop="name">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="食物价格" prop="price">
                    <el-input-number v-model="form.price" :precision="2" :step="0.1":min="0" controls-position="right"></el-input-number>
                </el-form-item>
                <el-form-item label="保质期" prop="life">
                    <el-input-number v-model="form.life" controls-position="right" :min="1" :max="100" :step="1"></el-input-number>
                </el-form-item>
                <el-form-item label="食品所属类目" prop="category">
                    <el-select v-model="form.category" clearable>
                        <el-option
                                v-for="item in foodCategoryList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="食物描述">
                    <el-input  type="textarea"
                               v-model="form.jianjie"
                               autocomplete="off"
                               :autosize="{ minRows: 2, maxRows: 4}"
                               maxlength="100"
                               show-word-limit=true
                    ></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="dataFormSubmit" :disabled="dataFormSubmitDisabled">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "FoodAdd",
        data() {
            return {
                dialogFormVisible: false,
                dataFormSubmitDisabled:false,
                loading:'',
                form: {
                    name: '',
                    price:'',
                    life:'',
                    category:'',
                    jianjie:''
                },
                foodCategoryList:[],
                formRules: {
                    name: [
                        { required: true, message: '请输入食品名称', trigger: 'blur' },
                    ],
                    price: [
                        { required: true, message: '请输入食品价格', trigger: 'blur' }
                    ],
                    life: [
                        { required: true, message: '请输入食品保质期', trigger: 'blur' }
                    ],
                    category: [
                        {required: true, message: '请输入食品所属类别', trigger: 'blur' }
                    ],
                }
            }
        },
        methods: {
            init() {
                this.dialogFormVisible = true;
                this.form.jianjie="";
                this.$nextTick(() => {
                    this.$refs.dataForm.resetFields();
                });
                this.getStatusList();
            },
            //表单提交
            dataFormSubmit() {
                this.$refs["dataForm"].validate(valid => {
                    if (valid) {
                        this.dataFormSubmitDisabled = true;
                        this.loading = this.$loading({
                            lock: true,
                            text: "正在保存",
                            spinner: "el-icon-loading",
                            background: "rgba(0, 0, 0, 0.7)"
                        });
                        this.$http({
                            url: "/foodInfo/save",
                            method: "post",
                            data: this.$http.adornData({
                                foodName:this.form.name,
                                foodPrice:this.form.price,
                                categoryId:this.form.category,
                                shelfLife:this.form.life,
                                foodDescription:this.form.jianjie,

                            })
                        }).then(({data}) => {
                            this.loading.close();
                            this.dataFormSubmitDisabled = false;
                            if (data && data.code === 0) {
                                this.$message({
                                    message: "保存成功",
                                    type: "success"
                                });
                                this.dialogFormVisible = false;
                                this.$emit("refreshDataList");
                            } else {
                                this.$message.error(data.msg);
                            }
                        });
                    }
                });
            },
            // 查询食品类目下拉列表
            getStatusList() {
                this.$http({
                    url: "/foodCategory/list/foodCategoryName",
                    method: "get",
                    params: this.$http.adornParams()
                }).then(({ data }) => {
                    if (data && data.code === 0) {
                        this.foodCategoryList = data.data
                    } else {
                        this.foodCategoryList = [];
                    }
                });
            },

        }
    }
</script>

<style scoped>

</style>


