import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'
import UserManage from '@/components/sys/UserManage.vue'
import ConfigurationManage from '@/components/connect/ConfigurationManage.vue'
import CityTaskManage from '@/components/connect/CityTaskManage.vue'

import QueryCityData from '@/components/connect/QueryCityManage.vue'


Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'Login',
            component: Login
        },{
            path: '/login',
            name: 'Login',
            component: Login
        }, {
            path: '/home',
            name: 'Home',
            component: Home,
            children: [
                {
                    path: 'configurationManage',
                    name: 'ConfigurationManage',
                    component: ConfigurationManage
                }, {
                    path: 'userManage',
                    name: 'UserManage',
                    component: UserManage
                },{

                    path: 'cityTaskManage',
                    name: 'CityTaskManage',
                    component: CityTaskManage
				},{
                    path:'queryCityData',
                    name:'QueryCityData',
                    component:QueryCityData
                }
            ]
        },

    ]
})
