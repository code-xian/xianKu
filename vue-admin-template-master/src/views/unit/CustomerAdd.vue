<template>
    <div>
        <el-dialog title="新增门店"
                   :visible.sync="dialogFormVisible"
                   :modal="true">
            <el-form :model="form"
                     ref="dataForm"
                     :rules="formRules"
                     @keyup.enter.native="dataFormSubmit()"
                     label-width="150px">
                <el-form-item label="门店名字" prop="name">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="门店负责人" prop="fzr">
                    <el-input v-model="form.fzr" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="门店联系方式" prop="phone">
                    <el-input v-model="form.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="门店地址" prop="address">
                    <el-input v-model="form.address" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="门店简介">
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
            var checkPhone = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error("手机号不能为空"));
                } else {
                    let reg = /^1[3|4|5|7|8][0-9]\d{8}$/;
                    if (reg.test(value)) {
                        callback();
                    } else {
                        return callback(new Error("请输入正确的手机号"));
                    }
                }
            };
            return {
                dialogFormVisible: false,
                dataFormSubmitDisabled:false,
                loading:'',
                form: {
                    name: '',
                    fzr:'',
                    phone:'',
                    address:'',
                    jianjie:''
                },
                formRules: {
                    name: [
                        { required: true, message: '请输入门店名称', trigger: 'blur' },
                    ],
                    fzr: [
                        { required: true, message: '请输入门店负责人', trigger: 'blur' }
                    ],
                    phone: [
                        {validator: checkPhone}
                    ],
                    address: [
                        {required: true, message: '请输入门店地址', trigger: 'blur' }
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
                            url: "/store/save",
                            method: "post",
                            data: this.$http.adornData({
                                storeName:this.form.name,
                                storePhone:this.form.fzr,
                                storeFzr:this.form.phone,
                                storeAddress:this.form.address,
                                storeNote:this.form.jianjie,

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
