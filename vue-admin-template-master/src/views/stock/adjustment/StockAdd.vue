<template>
    <el-dialog
            :title="!dataForm.id ? '新增' : '修改'"
            :visible.sync="visible"
            :modal="true"
    >
        <el-form
                :model="dataForm"
                ref="dataForm"
                @keyup.enter.native="dataFormSubmit()"
                label-width="95px"
        >


            <el-form-item label="仓库名称" prop="stockName"
                          :rules="[
      { required: true, message: '仓库名称不能为空', trigger: 'blur'},
    ]"
                          style="position:relative;left:50%;margin-left:-250px;width: 500px"
            >
                <el-input v-model="dataForm.stockName" placeholder="仓库名称"></el-input>

            </el-form-item>
            <el-form-item label="仓库负责人"
                          prop="stockFzr"
                          :rules="[
                            { required: true, message: '仓库负责人不能为空', trigger: 'blur'},
                          ]"
                          style="position:relative;left:50%;margin-left:-250px;width: 500px"
            >

                <el-input
                        placeholder="仓库负责人"
                        v-model="dataForm.stockFzr"
                ></el-input>

            </el-form-item>
            <el-form-item label="仓库类型" prop="stockType"
                          :rules="[
      { required: true, message: '仓库类型不能为空', trigger: 'blur'},
    ]"
                          style="position:relative;left:50%;margin-left:-250px;width: 500px"
            >

                <el-input

                        placeholder="仓库类型"
                        v-model="dataForm.stockType"
                ></el-input>

            </el-form-item>

            <el-form-item label="仓库描述" prop="stockNote"
                          :rules="[
      { required: true, message: '仓库描述不能为空', trigger: 'blur'},
    ]"
                          style="position:relative;left:50%;margin-left:-250px;width: 500px"
            >
                <el-input
                        placeholder="仓库描述"
                        v-model="dataForm.stockNote"
                ></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" :disabled="dataFormSubmitDisabled">确 定</el-button>
    </span>
    </el-dialog>
</template>

<script>

    export default {
        data() {
            return {
                visible: false,
                dataFormSubmitDisabled: false,
                dataForm: {
                    stockFzr: "",
                    stockType: "",
                    stockName: "",
                    stockNote: "",
                },
            }
        },
        methods: {
            init() {
                this.visible = true;
                this.dataFormSubmitDisabled = false
                this.$nextTick(() => {
                    this.$refs.dataForm.resetFields();
                });
            },
            // 表单提交
            dataFormSubmit() {
                this.$refs['dataForm'].validate(valid => {
                    if (valid) {
                        this.dataFormSubmitDisabled = true
                        this.$http({
                            url: "/stock/save",
                            method: 'post',
                            data: this.$http.adornData({
                                stockName:this.dataForm.stockName,
                                stockType:this.dataForm.stockType,
                                stockFzr:this.dataForm.stockFzr,
                                stockNote:this.dataForm.stockNote,
                                stockStatus:0,
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
        },
    }
</script>
<style scoped lang="scss">


</style>
