import "babel-polyfill"
import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'
import iView from 'view-design'
import http from './axios/request'
import 'view-design/dist/styles/iview.css'
import './permission'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueCookie from 'vue-cookie' // api: https://github.com/alfhen/vue-cookie
import QS from 'qs'
import echarts from 'echarts'


// require('./mock/mock.js')
Vue.config.productionTip = false
Vue.use(iView)
Vue.use(ElementUI)
Vue.use(VueCookie)
Vue.config.devtools = true



Vue.prototype.$echarts = echarts
Vue.prototype.$http = http
Vue.prototype.qs = QS;

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
})
