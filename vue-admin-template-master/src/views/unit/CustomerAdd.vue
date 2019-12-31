<template>
    <div>
        <el-dialog title="新增门店"
                   :visible.sync="dialogFormVisible"
                   :modal="true">
            <el-form :model="form"
                     ref="dataForm"
                     @keyup.enter.native="dataFormSubmit()"
                     label-width="150px">
                <el-form-item label="标题">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="标题">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="标题">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="标题">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="标题">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="dataFormSubmit">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                dialogFormVisible: false,
                loading:'',
                form: {
                    name: '',
                    region: '',
                    address: '',
                    textarea: ''
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
                            url: "",
                            method: "post",
                            data: this.$http.adornData({
                                id: this.dataForm.id,
                                activityName: this.dataForm.activityName,
                                leadId: this.dataForm.leadId,
                                deptId: this.dataForm.deptId,
                                starttime: this.dataForm.time[0],
                                endtime: this.dataForm.time[1],
                                address: this.dataForm.address,
                                type: this.dataForm.type,
                                content: this.dataForm.content,
                                filePath: this.dataForm.enclosure,
                                fileName: this.dataForm.fileName,
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
