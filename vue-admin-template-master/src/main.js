import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'
import iView from 'view-design'
import axios from 'axios'
import 'view-design/dist/styles/iview.css'
import './permission'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

require('./mock/mock.js')
Vue.config.productionTip = false
Vue.use(iView)
Vue.use(ElementUI)

// 设置基础URL
axios.defaults.baseURL = ''
// 设置请求超时时间
axios.defaults.timeout = 5000

Vue.prototype.$axios = axios

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
})
