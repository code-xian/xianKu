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
        component: () => import('../views/purchase/purchaseOrder/PurchaseList.vue')
    },
    'adjustmentList' : {
        path: 'adjustmentList',
        name: 'adjustmentList',
        component: () => import('../views/stock/adjustment/AdjustmentList.vue')
    },
    'inList' : {
        path: 'inList',
        name: 'inList',
        component: () => import('../views/purchase/inOfStock/InList.vue')
    },
    'outList' : {
        path: 'outList',
        name: 'outList',
        component: () => import('../views/sale/outOfStock/OutList.vue')
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
    'sendList' : {
        path: 'sendList',
        name: 'sendList',
        component: () => import('../views/stock/send/SendList.vue')
    },
    'receiveList' : {
        path: 'receiveList',
        name: 'receiveList',
        component: () => import('../views/stock/receive/ReceiveList.vue')
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
    },
    'returnedPurchase':{
        path: 'returnedPurchase',
        name: 'returnedPurchase',
        component: () => import('../views/purchase/returnedPurchase/ReturnedList.vue')
    },
    'returnedSale':{
        path: 'returnedSale',
        name: 'returnedSale',
        component: () => import('../views/sale/returnedSale/ReturnedList.vue')
    },
    'exchangePurchase':{
        path: 'exchangePurchase',
        name: 'exchangePurchase',
        component: () => import('../views/purchase/exchangePurchase/ExchangeList.vue')
    },
    'exchangedSale':{
        path: 'exchangedSale',
        name: 'exchangedSale',
        component: () => import('../views/sale/exchangeSale/ExchangeList.vue')
    },
    customer:{
        path:'customer',
        name:'customer',
        component:() => import('../views/unit/Customer.vue')
    },
    supplier:{
        path:'supplier',
        name:'supplier',
        component:() => import('../views/unit/Supplier.vue')
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
