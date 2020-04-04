import { asyncRoutes, resetRouter } from '../router'
import Vue from 'vue'

// 将菜单信息转成对应的路由信息 动态添加
export function menusToRoutes(data) {
    console.log(data);
    const result = []
    const children = []

    result.push({
        path: '/',
        component: () => import('../components/Index.vue'),
        children,
    })

    data.forEach(item => {
        generateRoutes(children, item)
    })

    children.push({
        path: 'error',
        name: 'error',
        component: () => import('../components/Error.vue')
    })

    // 最后添加404页面 否则会在登陆成功后跳到404页面
    result.push(
        {path: '*', redirect: '/error'},
    )

    return result
}

function generateRoutes(children, item) {
    if (item.name) {
        children.push(asyncRoutes[item.name])
    } else if (item.children) {
        item.children.forEach(e => {
            generateRoutes(children, e)
        })
    }
}

export function resetTokenAndClearUser() {
    // 退出登陆 清除用户资料
    // localStorage.setItem('token', '')
    // localStorage.setItem('userImg', '')
    // localStorage.setItem('userName', '')

    Vue.cookie.delete('token')
    // 重设路由
    resetRouter()
}

/*
删除数组中指定的某个对象,并返回一个新的数组
 */
export function removeArray(_arr, _obj) {
    let length = _arr.length;
    for (let i = 0; i < length; i++) {
        if (_arr[i].text == _obj) {
            _arr.splice(i, 1); //删除下标为i的元素
            // console.log(_arr[i].name,11111111);
            return _arr;
        }
    }
}

//获取cookie、
export function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return (arr[2]);
    else
        return null;
}

//获取当前时间
export  function getCurrentDate(){
    var myDate = new Date();
    //var year = myDate.getYear();        //获取当前年份(2位)
    var year = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
    var month = myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
    var day = myDate.getDate();        //获取当前日(1-31)
    var hour = myDate.getHours();
    var minutes = myDate.getMinutes();
    if(parseInt(month)<10){
        month = "0"+""+month;
    }
    if(parseInt(day)<10){
        day = "0"+""+day;
    }
    if(parseInt(hour)<10){
        hour = "0"+""+hour;
    }
    if(parseInt(minutes)<10){
        minutes = "0"+""+minutes;
    }
    return year+"-"+month+"-"+day+" "+hour+":"+minutes;
}

