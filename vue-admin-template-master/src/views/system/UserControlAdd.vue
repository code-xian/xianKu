<template>
    <div>
        <el-dialog title="新增管理员"
                   :visible.sync="dialogFormVisible"
                   :modal="true">
            <el-form :model="form"
                     ref="dataForm"
                     :rules="formRules"
                     @keyup.enter.native="dataFormSubmit()"
                     label-width="150px">
                <el-form-item label="管理员名字" prop="name">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="管理员账号" prop="username">
                    <el-input v-model="form.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="管理员密码" prop="password">
                    <el-input v-model="form.password" autocomplete="off"></el-input>
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
                    username:'',
                    name: '',
                    password:'',
                },
                formRules: {
                    name: [
                        { required: true, message: '请输入管理员名字', trigger: 'blur' },
                    ],
                    username: [
                        { required: true, message: '请输入管理员账号', trigger: 'blur' }
                    ],
                    password: [
                        {required: true, message: '请输入管理员密码', trigger: 'blur' }
                    ],
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
