<template>
    <div class="index-vue">
        <!-- 侧边栏 -->
        <aside :class="asideClassName">
            <!-- logo -->
            <div class="logo-c">
                <img src="https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2974438632,2797102034&fm=26&gp=0.jpg" alt="logo" class="logo">
                <span v-show="isShowAsideTitle">食品供应管理系统</span>
            </div>
            <!-- 菜单栏 -->
            <Menu class="menu" ref="asideMenu" theme="dark" width="100%" @on-select="gotoPage"
            accordion :open-names="openMenus" :active-name="currentPage" @on-open-change="menuChange">
                <!-- 动态菜单 -->
                <div v-for="(item, index) in menuItems" :key="index">
                    <Submenu v-if="item.children" :name="index">
                        <template slot="title">
                            <Icon :size="item.size" :type="item.type"/>
                            <span v-show="isShowAsideTitle">{{item.text}}</span>
                        </template>
                        <div v-for="(subItem, i) in item.children" :key="index + i">
                            <Submenu v-if="subItem.children" :name="index + '-' + i">
                                <template slot="title">
                                    <Icon :size="subItem.size" :type="subItem.type"/>
                                    <span v-show="isShowAsideTitle">{{subItem.text}}</span>
                                </template>
                                <MenuItem class="menu-level-3" v-for="(threeItem, k) in subItem.children" :name="threeItem.name" :key="index + i + k">
                                    <Icon :size="threeItem.size" :type="threeItem.type"/>
                                    <span v-show="isShowAsideTitle">{{threeItem.text}}</span>
                                </MenuItem>
                            </Submenu>
                            <MenuItem v-else :name="subItem.name">
                                <Icon :size="subItem.size" :type="subItem.type"/>
                                <span v-show="isShowAsideTitle">{{subItem.text}}</span>
                            </MenuItem>
                        </div>
                    </Submenu>
                    <MenuItem v-else :name="item.name">
                        <Icon :size="item.size" :type="item.type" />
                        <span v-show="isShowAsideTitle">{{item.text}}</span>
                    </MenuItem>
                </div>
            </Menu>
        </aside>

        <!-- 右侧部分 -->
        <section class="sec-right">
            <!-- 头部 -->
            <div class="top-c">
                <header>
                    <div class="h-left">
                        <div class="pointer" @click="isShrinkAside" title="收缩/展开">
                            <Icon type="ios-apps" />
                        </div>
                        <!-- 面包屑功能 -->
                        <p class="crumbs">{{crumbs}}</p>
                    </div>
                    <div class="h-right">
                        <!-- 消息 -->
                        <div class="notice-c" @click="info" title="查看新消息">
                            <div :class="{newMsg: msgNum==0?false:hasNewMsg}"></div>
                            <Icon type="ios-notifications-outline" />
                        </div>
                        <!-- 用户头像 -->
                        <div class="user-img-c">
                            <img :src="userImg">
                        </div>
                        <!-- 下拉菜单 -->
                        <Dropdown trigger="click" @on-click="userOperate" @on-visible-change="showArrow">
                            <div class="pointer">
                                <span>{{this.$store.state.user.adminName}}</span>
                                <Icon v-show="arrowDown" type="md-arrow-dropdown"/>
                                <Icon v-show="arrowUp" type="md-arrow-dropup"/>
                            </div>
                            <DropdownMenu slot="list">
                                <!-- name标识符 -->
                                <DropdownItem name="1">修改密码</DropdownItem>
                                <DropdownItem name="2">基本资料</DropdownItem>
                                <DropdownItem divided  name="3">退出登陆</DropdownItem>
                            </DropdownMenu>
                        </Dropdown>
                    </div>
                </header>

                <!-- 标签栏 -->
                <div class="div-tags">
                    <ul class="ul-c">
                        <li v-for="(item, index) in tagsArry" :class="{active: isActive(item.name)}" @click="activeTag(index)">
                            <a class="li-a">
                                {{item.text}}
                            </a>
                            <Icon size="16" @click="closeTag(index)" type="md-close" />
                        </li>
                    </ul>
                    <!-- 标签栏相关功能 -->
                    <div class="div-icons">
                        <div class="refresh-c" @click="reloadPage" title="刷新当前标签页">
                            <Icon type="md-refresh" />
                        </div>
                        <div class="tag-options" title="关闭标签">
                            <Dropdown trigger="click" @on-click="closeTags">
                                <Icon type="ios-options" />
                                <DropdownMenu slot="list">
                                    <DropdownItem name="1">关闭其他标签</DropdownItem>
                                    <DropdownItem name="2">关闭所有标签</DropdownItem>
                                </DropdownMenu>
                            </Dropdown>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 页面主体和头部之间放一个遮罩层分隔开 -->
            <div class="mask"></div>
            <!-- 页面主体 -->
            <div class="main-content">
                <div class="view-c">
                    <keep-alive :include="keepAliveData">
                        <!-- 子页面 -->
                        <router-view v-if="isShowRouter"/>
                    </keep-alive>
                    <div class="loading-c" v-show="showLoading">
                        <Spin size="large"></Spin>
                    </div>
                </div>
            </div>
        </section>
    </div>
</template>

<script>
    import * as io from "socket.io-client";
    import {resetTokenAndClearUser} from '../utils'
    let socket = null;
    export default {

    name: 'index',
    data () {
        return {
            // 用于储存页面路径
            paths: {},
            // 当前显示页面
            currentPage: '',
            openMenus: [], // 要打开的菜单名字 name属性
            menuCache: [], // 缓存已经打开的菜单
            showLoading: false, // 是否显示loading
            hasNewMsg: true, // 是否有新消息
            isShowRouter: true,
            msgNum: this.$store.state.msg.rowCount, // 新消息条数
            // 标签栏         标签标题     路由名称
            // 数据格式 {text: '首页', name: 'home'}
            // 用于缓存打开的路由 在标签栏上展示
            tagsArry: [],
            arrowUp: false, // 用户详情向上箭头
            arrowDown: true, // 用户详情向下箭头
            isShowAsideTitle: true, // 是否展示侧边栏内容
            main: null, // 页面主要内容区域
            asideClassName: 'aside-big', // 控制侧边栏宽度变化
            asideArrowIcons: [], // 缓存侧边栏箭头图标 收缩时用
            // 面包屑
            crumbs: '主页',
            userName: '',
            userImg: '',
            socket: null,
            userName2:this.$cookie.get("adminUsername"),
            messageList:[], //消息栏
            messageCount:0,
        }
    },
    // created() {
    //     // 已经为ajax请求设置了loading 请求前自动调用 请求完成自动结束
    //     // 添加请求拦截器
    //     this.$axios.interceptors.request.use(config => {
    //         this.showLoading = true
    //         // 在发送请求之前做些什么
    //         return config
    //     }, error => {
    //         this.showLoading = false
    //         // 对请求错误做些什么
    //         return Promise.reject(error)
    //     })
    //     // 添加响应拦截器
    //     this.$axios.interceptors.response.use(response => {
    //         // 可以在这里对返回的数据进行错误处理 如果返回的 code 不对 直接报错或退出登陆
    //         // 就可以省去在业务代码里重复判断
    //         // 例子
    //         // if (res.code != 0) {
    //         //     this.$Message.error(res.msg)
    //         //     return Promise.reject()
    //         // }
    //         this.showLoading = false
    //         const res = response.data
    //         return res
    //     }, error => {
    //         this.showLoading = false
    //         // 对响应错误做点什么
    //         return Promise.reject(error)
    //     })
    // },
    created() {

        this.getUserInfo();
    },
    // initSocket() {
    //     this.socket = io.connect("http:/localhost:9099");
    //     this.socket.on("connect", (data) => {
    //         console.log("open:", data);
    //         const loginUser = {
    //             loginUser: this.$cookie.get("adminUsername"), //获取登录缓存的用户
    //         };
    //         console.log("loginUser:", loginUser);
    //         if (this.$cookie.get("adminUsername") !== null) {
    //             this.socket.emit("addUser", loginUser); //推送用户账号给后端socket
    //         }
    //     })
    // },
    mounted() {
        this.getDataList2();
        // this.initSocket();
        this.initSocketio();
        this.$nextTick(()=>{
            // 第一个标签
            const name = this.$route.name
            this.currentPage = name
            this.tagsArry.push({
                text: this.nameToTitle[name],
                name: name
            })

            // 根据路由打开对应的菜单栏
            this.openMenus = this.getMenus(name)
            this.$nextTick(() => {
                this.$refs.asideMenu.updateOpened()
            })

            // 设置用户信息
            this.userName = this.adminName
            this.userImg = this.$cookie.get('userImg')

            this.main = document.querySelector('.sec-right')
            this.asideArrowIcons = document.querySelectorAll('aside .ivu-icon-ios-arrow-down')

            // 监听窗口大小 自动收缩侧边栏
            this.monitorWindowSize()
        })

    },
    watch: {
        $route(to) {
            const name = to.name
            this.currentPage = name
            if (name == 'error') {
                this.crumbs = '404'
                return
            }

            if (!this.keepAliveData.includes(name)) {
                // 如果标签超过8个 则将第一个标签删除
                if (this.tagsArry.length == 8) {
                    this.tagsArry.shift()
                }
                this.tagsArry.push({name, text: this.nameToTitle[name]})
            }

            setTimeout(() => {
                this.crumbs = this.paths[name]
            }, 0)
        }
    },
    computed: {
        // 菜单栏
        menuItems() {
            return this.$store.state.menuItems
        },
        // 需要缓存的路由
        keepAliveData() {
            return this.tagsArry.map(item => item.name)
        },
        // 由于iView的导航菜单比较坑 只能设定一个name参数
        // 所以需要在这定义组件名称和标签栏标题的映射表 有多少个页面就有多少个映射条数
        nameToTitle() {
            const obj = {}
            this.menuItems.forEach(e => {
                this.processNameToTitle(obj, e)
            })

            return obj
        },
        userId: {
            get () { return this.$store.state.user.userId },
            set (val) { this.$store.commit('user/userId', val) }
        },
        adminName: {
            get () { return this.$store.state.user.adminName },
            set (val) { this.$store.commit('user/adminName', val) }
        },
        adminUsername: {
            get () { return this.$store.state.user.adminUsername },
            set (val) { this.$store.commit('user/adminUsername', val) }
        },
        adminAuthority: {
            get () { return this.$store.state.user.adminAuthority },
            set (val) { this.$store.commit('user/adminAuthority', val) }
        },

    },
    methods: {
        getMenus(name) {
            let menus
            const tagTitle = this.nameToTitle[name]
            for (let i = 0, l = this.menuItems.length; i < l; i++) {
                const item = this.menuItems[i]
                menus = []
                menus[0] = i
                if (item.text == tagTitle) {
                    return menus
                }

                if (item.children) {
                    for (let j = 0, ll = item.children.length; j < ll; j++) {
                        const child = item.children[j]
                        menus[1] = i + '-' + j
                        menus.length = 2
                        if (child.text == tagTitle) {
                            return menus
                        }

                        if (child.children) {
                            for (let k = 0, lll = child.children.length; k < lll; k++) {
                                const grandson = child.children[k]
                                menus[2] = i + '-' + j + '-' + k
                                if (grandson.text == tagTitle) {
                                    return menus
                                }
                            }
                        }
                    }
                }
            }
        },

        monitorWindowSize() {
            let w = document.documentElement.clientWidth || document.body.clientWidth
            if (w < 1300) {
                this.shrinkAside()
            }

            window.onresize = () => {
                // 可视窗口宽度太小 自动收缩侧边栏
                if (w < 1300 && this.isShowAsideTitle
                    && w > (document.documentElement.clientWidth || document.body.clientWidth)) {
                    this.shrinkAside()
                }

                w = document.documentElement.clientWidth || document.body.clientWidth
            }
        },

        // 判断当前标签页是否激活状态
        isActive(name) {
            return this.$route.name === name
        },
        // 跳转页面 路由名称和参数
        gotoPage(name, params) {
            this.currentPage = name
            this.crumbs = this.paths[name]
            this.$router.replace({name, params})
            if (!this.keepAliveData.includes(name)) {
                // 如果标签超过8个 则将第一个标签删除
                if (this.tagsArry.length == 8) {
                    this.tagsArry.shift()
                }
                this.tagsArry.push({name, text: this.nameToTitle[name]})
            }
        },
        // 用户操作
        userOperate(name) {
            switch (name) {
                case '1':
                    // 修改密码
                    this.gotoPage('password')
                    break
                case '2':
                    // 基本资料
                    this.gotoPage('userinfo')
                    break
                case '3':
                    resetTokenAndClearUser()
                    this.quitSocket()
                    this.$router.replace({name: 'login'})
                    break
            }
        },
        // 控制用户三角箭头显示状态
        showArrow(flag) {
            this.arrowUp = flag
            this.arrowDown = !flag
        },
        // 判断
        isShrinkAside() {
            this.isShowAsideTitle ? this.shrinkAside() : this.expandAside()
        },
        // 收缩
        shrinkAside() {
            this.asideArrowIcons.forEach(e => {
                e.style.display = 'none'
            })

            this.isShowAsideTitle = false
            this.openMenus = []
            this.$nextTick(() => {
                this.$refs.asideMenu.updateOpened()
            })

            setTimeout(() => {
                this.asideClassName = ''
                this.main.style.width = 'calc(100% - 80px)'
            }, 0)
        },
        // 展开
        expandAside() {
            setTimeout(() => {
                this.isShowAsideTitle = true
                this.asideArrowIcons.forEach(e => {
                    e.style.display = 'block'
                })

                this.openMenus = this.menuCache
                this.$nextTick(() => {
                    this.$refs.asideMenu.updateOpened()
                })
            }, 200)
            this.asideClassName = 'aside-big'
            this.main.style.width = 'calc(100% - 220px)'
        },
        // 刷新当前标签页
        reloadPage() {
            let name = this.$route.name
            let index = this.keepAliveData.indexOf(name)
            this.$nextTick(() => {
                if (this.tagsArry.length) {
                    this.isShowRouter = false
                    this.tagsArry.splice(index, 1)
                    this.$nextTick(() => {
                        this.tagsArry.splice(index, 0, {name, text: this.nameToTitle[name]})
                        this.gotoPage(name)
                        this.isShowRouter = true
                    })
                } else {
                    this.isShowRouter = false
                    this.$nextTick(() => {
                        this.tagsArry.push({name, text: this.nameToTitle[name]})
                        this.gotoPage(name)
                        this.isShowRouter = true
                    })
                }
            })
        },
        // 关闭单个标签
        closeTag(i) {
            let name = this.tagsArry[i].name
            this.tagsArry.splice(i, 1)
            event.stopPropagation()
            // 如果关闭的是当前标签 则激活前一个标签
            // 如果关闭的是第一个标签 则激活后一个标签
            if (this.tagsArry.length) {
                if (this.isActive(name)) {
                    if (i != 0) {
                        this.gotoPage(this.tagsArry[i - 1].name)
                    } else {
                        this.gotoPage(this.tagsArry[i].name)
                    }
                }
            } else {
                // 如果没有标签则跳往首页
                if (name != 'home') {
                    this.gotoPage('home')
                } else {
                    this.reloadPage()
                }
            }

        },
        // 根据路由名称关闭页面
        closeName(name) {
            for (let i = 0, len = this.tagsArry.length; i < len; i++) {
                if (this.tagsArry[i].name == name) {
                    this.closeTag(i)
                    break
                }
            }
        },
        // 批量关闭标签
        closeTags(flag) {
            if (flag == 1) {
                // 关闭其他标签
                this.tagsArry = []
                this.gotoPage(this.$route.name)
            } else {
                // 关闭所有标签
                this.tagsArry = []
                this.gotoPage('home')
                this.reloadPage()
            }
        },
        // 激活标签
        activeTag(i) {
            this.gotoPage(this.tagsArry[i].name)
        },
        // 消息通知
        info() {
            const self = this
            this.$Notice.info({
                title: `您有${this.msgNum}条消息`,
                name: 'notice',
                render(h) {
                    return h('Button', {
                        attrs: {
                            type: 'info',
                            size: 'small'
                        },
                        on: {
                            click() {
                                // 点击查看跳转到消息页
                                self.gotoPage('msg');
                                self.hasNewMsg = false;
                                self.msgNum = 0;
                                self.$Notice.close('notice')
                            }
                        }
                    }, [
                        '点击查看',
                    ])
                }
            })
        },
        // 菜单栏改变事件
        menuChange(data) {
            this.menuCache = data
        },
        processNameToTitle(obj, data, text) {
            if (data.name) {
                obj[data.name] = data.text
                this.paths[data.name] = text ? `${text} / ${data.text}` : data.text
            }
            if (data.children) {
                data.children.forEach(e => {
                    this.processNameToTitle(obj, e, text ? `${text} / ${data.text}` : data.text)
                })
            }
        },
        // 获取当前管理员信息
        getUserInfo() {
            this.$http({
                url: '/admin/query',
                method: 'get',
                params: this.$http.adornParams({
                    adminUsername: this.$cookie.get("adminUsername")
                })
            }).then((res) => {
                if (res && res.data.code == 0) {
                    this.userId = res.data.data.adminId
                    this.adminName = res.data.data.adminName
                    this.adminUsername = res.data.data.adminUsername
                    this.adminAuthority = res.data.data.adminAuthority

                } else {
                    this.$message.error(res.data.msg)
                }
            })
        },
        //     initWebSocket () {
        //         if(typeof(WebSocket) === "undefined"){
        //             alert("您的浏览器不支持socket")
        //         }else{
        //             // 实例化socket
        //             this.socket = new WebSocket("ws://localhost:8091/jxc/websocket")
        //             // 监听socket连接
        //             this.socket.onopen = this.open
        //             // 监听socket错误信息
        //             this.socket.onerror = this.error
        //             // 监听socket消息
        //             this.socket.onmessage = this.getMessage
        //         }
        //     },
        //     open: function () {
        //         console.log("socket连接成功")
        //     },
        //     error: function () {
        //         console.log("连接错误")
        //     },
        //     getMessage: function (msg) {
        //         console.log(msg.data)
        //     },
        //     send: function () {
        //         this.socket.send(params)
        //     },
        //     close: function () {
        //         console.log("socket已经关闭")
        //     },
        // },
        getDataList2() {
            this.$http({
                url: "/socket/sendBroadcast",
                method: "get",
                params: this.$http.adornParams({

                })
            }).then(({ data }) => {
                if (data && data.code === 0) {

                } else {

                }
            });
        },
        pushMessage(data) {
            this.$store.dispatch('msg/push_message', data)
            console.log(this.$store.state.msg.message);
            console.log(this.$store.state.msg.rowCount);
        },
        initSocketio() {
            socket = io.connect('http://localhost:9099', {
                query: 'loginUser='+this.userName2
            })
            socket.on('connect', () =>{
                console.log('连接成功')
            })
            // 接收后端发送过来的消息
            socket.on('push_event', data =>{
                console.log(data)
            })
            // 接收后端发送过来的消息
            socket.on('stock', data => {
                console.log(data)
                // this.$store.dispatch('msg/add_rowCount')
                this.pushMessage(data)
                socket.emit("stock", "你好")
            })
            socket.on('disconnect',  () =>{
                console.log('已经下线')
            })
        },
        quitSocket() {
            socket.disconnect();
        }
    },
}
</script>

<style scoped>
.index-vue {
    height: 100%;
    display: flex;
    justify-content: space-between;
    color: #666;
}
/* 侧边栏 */
aside {
    min-width: 80px;
    background: #20222A;
    height: 100%;
    transition: all .5s;
    overflow: auto;
}
.logo-c {
    display: flex;
    align-items: center;
    color: rgba(255,255,255,.8);
    font-size: 16px;
    margin: 20px 0;
    justify-content: center;
}
.logo {
    width: 40px;
    margin-right: 10px;
}
.aside-big {
    min-width: 220px;
}
/* 主体页面 */
.sec-right {
    height: 100%;
    width: 100%;
    display: flex;
    flex-direction: column;
    transition: width .5s;
    overflow: auto;
}
/* 主体页面头部 */
.top-c {
    background: rgba(230,230,230,.5);
    width: 100%;
}
header {
    height: 50px;
    border-bottom: none;
    box-shadow: 0 1px 2px 0 rgba(0,0,0,.05);
    background: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding-right: 40px;
    padding-left: 10px;
    font-size: 14px;
}
header .ivu-icon {
    font-size: 24px;
}
.refresh-c {
    margin: 0 30px;
    cursor: pointer;
}
.h-right {
    display: flex;
    align-items: center;
}
.h-left {
    display: flex;
    align-items: center;
}
.user-img-c img {
    width: 100%;
}
.notice-c {
    cursor: pointer;
    position: relative;
}
.newMsg {
    position: absolute;
    width: 8px;
    height: 8px;
    border-radius: 50%;
    background-color: #FF5722;
    right: 0;
    top: 0;
}
.user-img-c {
    width: 34px;
    height: 34px;
    background: #ddd;
    border-radius: 50%;
    margin: 0 40px;
    overflow: hidden;
}
.tag-options {
    cursor: pointer;
    position: relative;
}
.div-tags {
    display: flex;
    align-items: center;
    justify-content: space-between;
}
.div-icons {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    background: #fff;
    height: 34px;
    width: 160px;
    font-size: 18px;
}
/* 标签栏 */
.ul-c {
    height: 34px;
    margin-top: 2px;
    background: #fff;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    padding: 0 10px;
    overflow: hidden;
    width: calc(100% - 160px);
}
.ul-c li {
    border-radius: 3px;
    cursor: pointer;
    font-size: 12px;
    height: 24px;
    padding: 0 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 3px 5px 2px 3px;
    border: 1px solid #e6e6e6;
}
a {
    color: #666;
    transition: none;
}
.li-a {
    max-width: 80px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
.ul-c .ivu-icon {
    margin-left: 6px;
}
.active {
    background: #409eff;
    border: 1px solid #409eff;
}
.active a {
    color: #fff;
}
.active .ivu-icon {
    color: #fff;
}
/* 主要内容区域 */
.main-content {
    overflow: hidden;
    height: 100%;
    width: 100%;
    background: #eee;
    padding: 10px 12px;
}
.view-c {
    position: relative;
    background: #fff;
    padding: 15px;
}
.pointer {
    cursor: pointer;
}
/* loading */
.loading-c {
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    position: absolute;
    background: rgba(255,255,255,.5);
    display: flex;
    align-items: center;
    justify-content: center;
}
.mask {
    position: fixed;
    background: #eee;
    height: 10px;
    width: 100%;
    top: 85px;
    z-index: 10;
}
.crumbs {
    margin-left: 10px;
    color: #97a8be;
    cursor: default;
}
.menu-level-3 .ivu-icon {
    font-size: 18px;
}
</style>
