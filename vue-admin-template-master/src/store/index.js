import Vue from 'vue'
import Vuex from 'vuex'
import user from './user'
import msg from './msg'

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
                text: '供应管理',
                type: 'ios-paper',
                children: [
                    {
                        type: 'ios-grid',
                        name: 'salesOrderList',
                        text: '供应订单列表'
                    },
                    {
                        type: 'ios-grid',
                        name: 'outList',
                        text: '供应出库单列表'
                    },
                    {
                        type: 'ios-grid',
                        name: 'returnedSale',
                        text: '供应退货单列表'
                    },
                    {
                        type: 'ios-grid',
                        name: 'exchangedSale',
                        text: '供应换货单列表'
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
                        type: 'ios-grid',
                        name: 'inList',
                        text: '采购入库单列表'
                    },
                    {
                        type: 'ios-grid',
                        name: 'returnedPurchase',
                        text: '采购退货单列表'
                    },
                    {
                        type: 'ios-grid',
                        name: 'exchangePurchase',
                        text: '采购换货单列表'
                    },

                ]
            },
            {
                text: '库存管理',
                type: 'ios-paper',
                children: [
                    {
                        type: 'ios-grid',
                        name: 'sendList',
                        text: '发货'
                    },{
                        type: 'ios-grid',
                        name: 'receiveList',
                        text: '收货'
                    }, {
                        type: 'ios-grid',
                        name: 'adjustmentList',
                        text: '仓库管理'
                    }, {
                        type: 'ios-grid',
                        name: 'foodList',
                        text: '食品大全'
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
                    },
                    {
                        type: 'md-person',
                        name:'orderAmount',
                        text:'供应/采购统计'
                    },
                ]
            },
            {
              text:'往来单位',
              type:'ios-paper',
              children:[
                  {
                      type: 'md-person',
                      name:'customer',
                      text:'线下门店'
                  },{
                      type: 'md-person',
                      name:'supplier',
                      text:'供应商'
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
            }
        ],
    },
    mutations: {
        setMenus(state, items) {
            state.menuItems = [...items]
        },
        pushMenus(state, items) {
            if(JSON.stringify(state.menuItems).indexOf(JSON.stringify(items))==-1){
                state.menuItems.push(items); // 进行动态的操作
            }

        },
        deleteMenus(state , items){
            state.menuItems.map((value, index) => {
                if(value.text == items){
                    state.menuItems.splice(index,1)
                }
            })
        }
    },
    actions:{
        push_menus({commit},item) {
            commit('pushMenus',item)
        },
        delete_menus({commit},item) {
            commit('deleteMenus',item)
        }
    },
    modules:{
        user,
        msg
    }
})

export default store
