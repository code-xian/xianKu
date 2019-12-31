<template>
    <div class="login-vue" :style="bg">
        <div class="container">
            <p class="title">WELCOME</p>
            <div class="input-c">
                    <Input  prefix="ios-contact" v-model="form.account" placeholder="用户名" clearable @on-blur="verifyAccount(form.account)"/>
                <p class="error">{{accountError}}</p>
            </div>
            <div class="input-c">
                    <Input  type="password" v-model="form.pwd" prefix="md-lock" placeholder="密码" clearable @on-blur="verifyPwd(form.pwd)"/>
                <p class="error">{{pwdError}}</p>
            </div>
            <Button :loading="isShowLoading" class="submit" type="primary" @click="submit" >登陆</Button>
            <p class="account"><span @click="register">注册账号</span> | <span @click="forgetPwd">忘记密码</span></p>
        </div>
    </div>
</template>

<script>

export default {
    name: 'login',
    data() {
        return {
            form:{
                account: '',
                pwd: '',
            },
            accountError: '',
            pwdError: '',
            isShowLoading: false,
            bg: {},
            redirect:'',
        }
    },
    created() {
        this.bg.backgroundImage = 'url(' + require('../assets/imgs/bg0' + new Date().getDay() + '.jpg') + ')'
    },
    watch: {
        $route: {
            handler: function(route) {
                this.redirect = route.query && route.query.redirect
            },
            immediate: true
        }
    },
    methods: {
        deleteMenus(id) {
            if (id !== '1') {
                this.$store.dispatch('delete_menus', '系统管理')
            }
        },
        addMenus() {
            let newMenus={
                text: '系统管理',
                type: 'ios-paper',
                children: [
                    {
                        type: 'ios-grid',
                        name: 'userControl',
                        text: '管理员管理'
                    },
                ]
            }
            this.$store.dispatch('push_menus', newMenus)
        },
        verifyAccount(e) {
            if (this.form.account == '') {
                this.accountError = '账号不能为空'
            } else {
                this.accountError = ''
            }
        },
        verifyPwd(e) {
            if (this.form.pwd == '') {
                this.pwdError = '密码不能为空'
            } else {
                this.pwdError = ''
            }
        },
        register() {
            this.$message.warning("没得给你注册账号")
        },
        forgetPwd() {
            this.$message.warning("不能忘记密码")
        },
        submit() {
                    this.$http({
                        method: 'post',
                        url: '/admin/login',
                        data:this.$http.adornData({
                            adminUsername:this.form.account,
                            adminPassword:this.form.pwd,
                        })
                    }).then( (res) => {
                        if (res.data&&res.data.code==0) {
                            this.isShowLoading = true
                            // 登陆成功 设置用户信息
                            this.$cookie.set('userImg', 'https://foter.com/photos/394/anime-pet-kitty.jpg?s=t')
                            // 登陆成功 假设这里是后台返回的 token
                            this.$cookie.set('token','123')
                            if (res.data.data && res.data.data == 1) {
                                this.addMenus();
                            }else if(res.data.data && res.data.data == 0) {
                                this.deleteMenus(res.data.data)
                            }
                            this.$cookie.set('adminUsername',this.form.account)
                            this.$cookie.set('adminAuthority',res.data.data)
                            this.$router.push({path: this.redirect || '/'})
                        } else {
                            this.form.account='',
                                this.form.pwd='',
                                this.$message.error(res.data.msg)
                            this.isShowLoading = false
                        }
                    });
        }
    }
}
</script>

<style>
.login-vue {
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #fff;
}
.login-vue .container {
    background: rgba(255, 255, 255, .5);
    width: 300px;
    text-align: center;
    border-radius: 10px;
    padding: 30px;
}
.login-vue .ivu-input {
    background-color: transparent;
    color: #fff;
    outline: #fff;
    border-color: #fff;
}
.login-vue ::-webkit-input-placeholder { /* WebKit, Blink, Edge */
    color: rgba(255, 255, 255, .8);
}
.login-vue :-moz-placeholder { /* Mozilla Firefox 4 to 18 */
    color: rgba(255, 255, 255, .8);
}
.login-vue ::-moz-placeholder { /* Mozilla Firefox 19+ */
    color: rgba(255, 255, 255, .8);
}
.login-vue :-ms-input-placeholder { /* Internet Explorer 10-11 */
    color: rgba(255, 255, 255, .8);
}
.login-vue .title {
    font-size: 16px;
    margin-bottom: 20px;
}
.login-vue .input-c {
    margin: auto;
    width: 200px;
}
.login-vue .error {
    color: red;
    text-align: left;
    margin: 5px auto;
    font-size: 12px;
    padding-left: 30px;
    height: 20px;
}
.login-vue .submit {
    width: 200px;
}
.login-vue .account {
    margin-top: 30px;
}
.login-vue .account span {
    cursor: pointer;
}
.login-vue .ivu-icon {
    color: #eee;
}
.login-vue .ivu-icon-ios-close-circle {
    color: #777;
}
</style>


