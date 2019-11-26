import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        // 左侧菜单栏数据
        menuItems: [
            {
                name: 'home', // 要跳转的路由名称 不是路径
                size: 18, // icon大小
                type: 'md-home', // icon类型
                text: '主页' // 文本内容
            },
            {
                text: '销售管理',
                type: 'ios-paper',
                children: [
                    {
                        type: 'ios-grid',
                        name: 'salesOrderList',
                        text: '销售订单列表'
                    },{
                        type: 'ios-grid',
                        name: 'invoiceList',
                        text: '出库单列表'
                    },
                ]
            },
            {
                text: '采购管理',
                type: 'ios-paper',
                children: [
                    {
                        type: 'ios-grid',
                        name: 'purchaseList',
                        text: '采购订单列表'
                    },
                    {
                        text: '三级菜单',
                        type: 'ios-paper',
                        children: [
                            {
                                type: 'md-lock',
                                name: 'password',
                                text: '修改密码'
                            },
                            {
                                type: 'md-person',
                                name: 'userinfo',
                                text: '基本资料',
                            }
                        ]
                    }
                ]
            },
            {
                text: '库存管理',
                type: 'ios-paper',
                children: [
                    {
                        type: 'ios-grid',
                        name: 'inList',
                        text: '入库管理'
                    },{
                        type: 'ios-grid',
                        name: 'outList',
                        text: '出库管理'
                    },{
                        type: 'ios-grid',
                        name: 'adjustmentList',
                        text: '库存调整'
                    },{
                        type: 'ios-grid',
                        name: 'packagingList',
                        text: '食品分装'
                    },{
                        type: 'ios-grid',
                        name: 'transferList',
                        text: '移库管理'
                    },{
                        type: 'ios-grid',
                        name: 'log',
                        text: '库存日志'
                    },
                ]
            },

            // {
            //     text: '其他',
            //     type: 'ios-paper',
            //     children: [
            //         {
            //             type: 'ios-grid',
            //             name: 't1',
            //             text: '表格'
            //         },
            //
            //         {
            //             text: '三级菜单',
            //             type: 'ios-paper',
            //             children: [
            //                 {
            //                     type: 'ios-notifications-outline',
            //                     name: 'msg',
            //                     text: '查看消息'
            //                 },
            //                 {
            //                     type: 'md-lock',
            //                     name: 'password',
            //                     text: '修改密码'
            //                 },
            //                 {
            //                     type: 'md-person',
            //                     name: 'userinfo',
            //                     text: '基本资料',
            //                 }
            //             ]
            //         }
            //     ]
            // },
            {
                text:'消息中心',
                type: 'ios-paper',
                children:[
                    {
                        type: 'md-person',
                        name:'msg',
                        text:'通知公告'
                    }
                ]
            },
            {
                text: '系统管理',
                type: 'ios-paper',
                children: [
                    {
                        type: 'ios-grid',
                        name: 'userControl',
                        text: '管理员管理'
                    },
                ]
            },
        ],
    },
    mutations: {
        setMenus(state, items) {
            state.menuItems = [...items]
        },
    }
})

export default store
