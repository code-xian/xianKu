<template>
    <div>
        <el-dialog title="新增供应商"
                   :visible.sync="dialogFormVisible"
                   :modal="true">
            <el-form :model="form"
                     ref="dataForm"
                     :rules="formRules"
                     @keyup.enter.native="dataFormSubmit()"
                     label-width="150px">
                <el-form-item label="供应商名字" prop="name">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="供应商负责人" prop="fzr">
                    <el-input v-model="form.fzr" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="供应商联系方式" prop="phone">
                    <el-input v-model="form.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="供应商地址" prop="address">
                    <el-input v-model="form.address" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="供应商类型" prop="foodType">
                    <el-select v-model="form.foodType" placeholder="请选择食品供应类型">
                        <el-option
                            v-for="item in foodTypeList"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="供应商简介">
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
        data() {
            return {
                dialogFormVisible: false,
                dataFormSubmitDisabled:false,
                loading:'',
                form: {
                    foodType:'',
                    name: '',
                    fzr:'',
                    phone:'',
                    address:'',
                    jianjie:''
                },
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
                formRules: {
                    name: [
                        { required: true, message: '请输入门店名称', trigger: 'blur' },
                    ],
                    fzr: [
                        { required: true, message: '请输入门店负责人', trigger: 'blur' }
                    ],
                    phone: [
                        {required: true, message: '请输入门店联系方式', trigger: 'blur' }
                    ],
                    address: [
                        {required: true, message: '请输入门店地址', trigger: 'blur' }
                    ],
                    foodType:[
                        {required: true , message : '请选择食品类型' , trigger : 'change'}
                    ]
                }
            }
        },
        methods: {
            init() {
                this.dialogFormVisible = true;
                this.$nextTick(() => {
                    this.$refs.dataForm.resetFields();
                });
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
                            url: "/supplier/save",
                            method: "post",
                            data: this.$http.adornData({
                                supplierName:this.form.name,
                                supplierPhone:this.form.fzr,
                                supplierFzr:this.form.phone,
                                supplierAddress:this.form.address,
                                supplierNote:this.form.jianjie,
                                supplierType:this.form.foodType

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
        }
    }
</script>

<style scoped>

</style>
