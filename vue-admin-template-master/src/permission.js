import "babel-polyfill";
import router from './router'
import store from './store'
import { menusToRoutes, resetTokenAndClearUser ,removeArray,getCookie} from './utils'
import { LoadingBar } from 'view-design'
import axios from 'axios'
import vue from 'vue'

// 是否有菜单数据
let hasMenus = false
router.beforeEach(async (to, from, next) => {
            LoadingBar.start()
            if (localStorage.getItem('token')) {
                if (to.path === '/login') {
                    next({path: '/'})
                } else {
                    if (hasMenus) {
                        next()
                    } else {
                        try {
                            // 这里可以用 await 配合请求后台数据来生成路由
                            // const data = await axios.get('xxx')
                            // const routes = menusToRoutes(data)
                            // if(getCookie('adminAuthority')==1) {
                                // removeArray(store.state.menuItems, '系统管理');
                                const routes = menusToRoutes(store.state.menuItems);
                                // 动态添加路由
                                router.addRoutes(routes)
                            // }
                            hasMenus = true
                            next({path: to.path || '/'})
                        } catch (error) {
                            resetTokenAndClearUser()
                            next('/login')
                        }
                    }
                }
            } else {
                hasMenus = false
                if (to.path === '/login') {
                    next()
                } else {
                    next(`/login?redirect=${to.path}`)
                }
            }





})

router.afterEach(() => {
    LoadingBar.finish()
})
