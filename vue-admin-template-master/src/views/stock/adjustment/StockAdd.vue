<template>
    <el-dialog
            :title="!dataForm.id ? '新增' : '修改'"
            :close-on-click-modal="false"
            :visible.sync="visible"
            :fullscreen="true"
            :modal="false"
    >
        <el-form
                :model="dataForm"
                ref="dataForm"
                @keyup.enter.native="dataFormSubmit()"
                label-width="95px"
        >



            <el-form-item label="规则分类" prop="ruleType"
                          :rules="[
      { required: true, message: '规则分类不能为空', trigger: 'blur'},
    ]"
                          style="position:relative;left:50%;margin-left:-250px;width: 500px"
            >
                <el-select v-model="dataForm.ruleType" placeholder="请选择">
                    <el-option
                            v-for="item in ruleTypeList"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                    ></el-option>
                </el-select>

            </el-form-item>
            <el-form-item label="数据来源" prop="dataSource"
                          :rules="[
      { required: true, message: '数据来源不能为空', trigger: 'blur'},
    ]"
                          style="position:relative;left:50%;margin-left:-250px;width: 500px"
            >

                <el-input
                        placeholder="数据来源"
                        v-model="dataForm.dataSource"
                ></el-input>

            </el-form-item>
            <el-form-item label="规则" prop="creditRule"
                          :rules="[
      { required: true, message: '规则不能为空', trigger: 'blur'},
    ]"
                          style="position:relative;left:50%;margin-left:-250px;width: 500px"
            >

                <el-input

                        placeholder="标签规则"
                        v-model="dataForm.creditRule"
                ></el-input>

            </el-form-item>

            <el-form-item label="备注" prop="beizhu"
                          :rules="[
      { required: true, message: '备注不能为空', trigger: 'blur'},
    ]"
                          style="position:relative;left:50%;margin-left:-250px;width: 500px"
            >
                <el-input
                        placeholder="备注"
                        v-model="dataForm.beizhu"
                ></el-input>
            </el-form-item>
            <el-form-item label="运算逻辑" prop="yunsuanluoji"
                          :rules="[
      { required: true, message: '运算逻辑不能为空', trigger: 'blur'},
    ]"
                          style="position:relative;left:50%;margin-left:-250px;width: 500px"
            >

                <el-input

                        placeholder="运算逻辑"
                        v-model="dataForm.yunsuanluoji"
                ></el-input>
            </el-form-item>

            <el-form-item label="评分" prop="pingfen"
                          :rules="[
      { required: true, message: '评分不能为空', trigger: 'blur'},
    ]"
                          style="position:relative;left:50%;margin-left:-250px;width: 500px"
            >

                <el-input-number v-model="dataForm.pingfen"></el-input-number>
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
        data () {
            return {
                visible: false,
                dataFormSubmitDisabled: false,
                dataForm: {
                    id: '',
                    ruleType: '',
                    dataSource: '',
                    creditRule: '',
                    beizhu: '',
                    yunsuanluoji:"",
                    pingfen:0
                },
                ruleTypeList: [
                    {label: '全部', value: ''},
                ],
                dataRule: {},
            }
        },
        methods: {
            init () {
                this.visible = true
                this.$nextTick(() => {
                    this.$refs.dataForm.resetFields();
                });
            },
            // 表单提交
            dataFormSubmit () {
                this.$refs['dataForm'].validate(valid => {
                    if (valid) {
                        this.dataFormSubmitDisabled = true
                        this.$http({
                            url: this.$http.adornUrl(
                                `/kehuxinyongguanli/creditrules/${!this.dataForm.id ? 'save' : 'update'}`
                            ),
                            method: 'post',
                            data: this.$http.adornData({
                                creditRulesId:this.dataForm.id,
                                grade: this.dataForm.pingfen,
                                ruleName: this.dataForm.creditRule,
                                remark: this.dataForm.beizhu,
                                dataSources: this.dataForm.dataSource,
                                ruleClassify: this.dataForm.ruleType,
                                arithmeticLogic: this.dataForm.yunsuanluoji,
                            })
                        }).then(({data}) => {
                            if (data && data.code === 0) {
                                this.dataFormSubmitDisabled = false
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
            // 查询标签分类下拉列表
            getTagsList() {
                this.$http({
                    url: this.$http.adornUrl("/kehuxinyongguanli/creditrules/selectClassify"),
                    method: "get",
                    params: this.$http.adornParams()
                }).then(({ data }) => {
                    if (data && data.code === 0) {
                        for (let i = 0; i < data.data.length; i++) {
                            this.ruleTypeList.push({
                                value: data.data[i].id,
                                label: data.data[i].des
                            })
                        }
                    } else {
                        this.ruleTypeList = [{ label: "全部", value: "" }];
                    }
                });
            },
        },
        created () {
            this.getTagsList()
        }
    }
</script>
<style scoped lang="scss">


</style>
