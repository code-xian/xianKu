import Vue from 'vue'
import axios from 'axios'
import qs from 'qs'
import merge from 'lodash/merge'

// 设置基础URL
axios.defaults.baseURL = 'http://localhost:8091/jxc'
// 设置请求超时时间
axios.defaults.timeout = 5000
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'

const http = axios.create({
    // headers: {
    //     'Content-Type': 'application/json; charset=utf-8'
    // }
})

http.interceptors.request.use(config => {
    // config.headers['token'] = Vue.cookie.get('token') // 请求头带上token
    return config
}, error => {
    return Promise.reject(error)
})

/**
 * 响应拦截
 */
http.interceptors.response.use(response => {
    // if (response.data && response.data.code === 401) { // 401, token失效
    //     Vue.cookie.delete('token')
    //     router.options.isAddDynamicMenuRoutes = false
    //     router.push({
    //         name: 'login'
    //     })
    // }
    return response
}, error => {
    return Promise.reject(error)
})

/**
 * get请求参数处理
 * @param {*} params 参数对象
 * @param {*} openDefultParams 是否开启默认参数?
 */
http.adornParams = (params = {}, openDefultParams = false) => {
    let defaults = {
        't': new Date().getTime()
    }
    return openDefultParams ? merge(defaults, params) : params
}


/**
 * post请求数据处理
 * @param {*} data 数据对象
 * @param {*} openDefultdata 是否开启默认数据?
 * @param {*} contentType 数据格式
 *  json: 'application/json; charset=utf-8'
 *  form: 'application/x-www-form-urlencoded; charset=utf-8'
 */
http.adornData = (data = {}, openDefultdata = true, contentType = 'json') => {
    let defaults = {
        't': new Date().getTime()
    }
    data = openDefultdata ? merge(defaults, data) : data
    return contentType === 'json' ? JSON.stringify(data) : qs.stringify(data)
}

export default http
