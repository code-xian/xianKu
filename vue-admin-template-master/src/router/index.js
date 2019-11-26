import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const commonRoutes = [
    {
        path: '/login',
        name: 'login',
        component: () => import('../components/Login.vue')
    },
    {path: '/', redirect: '/home'},
]

// 需要通过后台数据来生成的组件
export const asyncRoutes = {
    'home': {
        path: 'home',
        name: 'home',
        component: () => import('../views/Home.vue')
    },
    't1': {
        path: 't1',
        name: 't1',
        component: () => import('../views/T1.vue')
    },
    'password': {
        path: 'password',
        name: 'password',
        component: () => import('../views/Password.vue')
    },
    'msg': {
        path: 'msg',
        name: 'msg',
        component: () => import('../views/message/Msg.vue')
    },
    'userinfo': {
        path: 'userinfo',
        name: 'userinfo',
        component: () => import('../views/UserInfo.vue')
    },
    'salesOrderList' : {
        path: 'salesOrderList',
        name: 'salesOrderList',
        component: () => import('../views/sale/salesOrder/SalesOrderList.vue')
    },
    'purchaseList' : {
        path: 'purchaseList',
        name: 'purchaseList',
        component: () => import('../views/purchase/PurchaseList.vue')
    },
    'invoiceList' : {
        path: 'invoiceList',
        name: 'invoiceList',
        component: () => import('../views/sale/invoice/InvoiceList.vue')
    },
    'adjustmentList' : {
        path: 'adjustmentList',
        name: 'adjustmentList',
        component: () => import('../views/stock/adjustment/AdjustmentList.vue')
    },
    'inList' : {
        path: 'inList',
        name: 'inList',
        component: () => import('../views/stock/inOfStock/InList.vue')
    },
    'outList' : {
        path: 'outList',
        name: 'outList',
        component: () => import('../views/stock/outOfStock/OutList.vue')
    },
    'packagingList' : {
        path: 'packagingList',
        name: 'packagingList',
        component: () => import('../views/stock/packaging/PackagingList.vue')
    },
    'transferList' : {
        path: 'transferList',
        name: 'transferList',
        component: () => import('../views/stock/transfer/TransferList.vue')
    },
    'log' : {
        path: 'log',
        name: 'log',
        component: () => import('../views/stock/Log.vue')
    },
    'userControl' : {
        path: 'userControl',
        name: 'userControl',
        component: () => import('../views/system/UserControl.vue')
    }

}

const createRouter = () => new Router({
    routes: commonRoutes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher
}

export default router
