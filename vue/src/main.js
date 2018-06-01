// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import axios from 'axios'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.config.productionTip = false
Vue.use(ElementUI)

/* eslint-disable no-new */
var vm = new Vue({
    el: '#app',
    router,
    components: {App},
    template: '<App/>',

})

Vue.prototype.getData = function (url, method, param, doneHandler, failHandler, hasToken, isJson) {
    if(!isJson) {
        var jsonParams = new URLSearchParams();
        for(let key in param) {
            jsonParams.append(key, param[key]);
        }
        param = jsonParams;
    }
    console.log("param:", param);

    if (url) {
        let headerObj = {};
        if(isJson) {
            headerObj["Content-Type"] = 'application/json;charset=UTF-8'
        }
        if (hasToken) {
            let userToken = JSON.parse(localStorage.getItem("userToken")); //取出登录用户信息
            headerObj["auth"] = userToken;
        }
        /**
         * 跨域配置，打包时候去除
         * */
        url = '/pnms' + url;

        axios({
            url: url,
            method: method || "GET",
            data: param || "",
            headers: headerObj,
        }).then(function (response) {
            if (doneHandler) {
                if (response.data.status==401){
                    localStorage.clear();
                }
                console.log("doneHandler:", response.data);
                doneHandler(response.data);
            }
        }).catch(function (err) {
            if (failHandler) {
                console.log("failHandler:", failHandler);
                failHandler(err);
            }
        })
    }
}
/*
Vue.prototype.getData = function (url, method, param, doneHandler, failHandler, hasToken, isJson) {
    if (url) {
        let headerObj = {};
        if (hasToken) {
            let userToken = JSON.parse(localStorage.getItem("userToken")); //取出登录用户信息
            headerObj = {"auth": userToken}
        } else {
            headerObj = {}
        }
        /!**
         * 跨域配置，打包时候去除
         * *!/
        url = '/pnms' + url;
        if (isJson) {
            $.ajax({
                url: url,
                type: method || "GET",
                data: param || "",
                contentType: "application/json",
                headers: headerObj
            }).done(function (data) {
                if (doneHandler) {
                    if (data.status==401){
                        localStorage.clear();
                    }
                    doneHandler(data);
                }
            }).fail(function (err) {
                if (failHandler) {
                    failHandler(err);
                }
            })
        } else {
            $.ajax({
                url: url,
                type: method || "GET",
                data: param || "",
                headers: headerObj
            }).done(function (data) {
                if (doneHandler) {
                    doneHandler(data);
                }
            }).fail(function (err) {
                if (failHandler) {
                    failHandler(err);
                }
            })
        }
    }
}*/

