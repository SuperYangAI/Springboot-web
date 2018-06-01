<template>
    <div class="wrapper">
        <!-- header -->
        <div class="header">
            <div class="logo"><span style="color: #0096e6;font-weight:bolder;margin-left: 30px ;">省 网 管 系 统</span></div>
            <div class="header-right">
                <!-- 用户头像 -->
                <div class="user-avator">
                    <img src="../../static/img/user.png">
                </div>
                <!-- 用户名下拉菜单 -->
                <el-dropdown class="user-name" trigger="click">
                        <span class="el-dropdown-link">
                            {{loginUserName}}
                            <i class="el-icon-caret-bottom"></i>
                        </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>
        <!-- sidebar -->
        <div class="sidebar">
            <el-row class="tac">
                <el-col :span="12">
                    <!--<h5>自定义颜色</h5>-->
                    <el-menu
                        default-active="2"
                        class="el-menu-vertical-demo"
                        @open="handleOpen"
                        @close="handleClose"
                        background-color="#545c64"
                        text-color="#fff"
                        active-text-color="#ffd04b">
                        <el-submenu index="1">
                            <template slot="title">
                                <i class="el-icon-location"></i>
                                <span>运行拓扑</span>
                            </template>
                            <!--<el-menu-item-group>-->
                            <!--<template slot="title">分组一</template>-->
                            <!--<el-menu-item index="1-1">选项1</el-menu-item>-->
                            <!--<el-menu-item index="1-2">选项2</el-menu-item>-->
                            <!--</el-menu-item-group>-->
                            <!--<el-menu-item-group title="分组2">-->
                            <!--<el-menu-item index="1-3">选项3</el-menu-item>-->
                            <!--</el-menu-item-group>-->
                            <!--<el-submenu index="1-4">-->
                            <!--<template slot="title">选项4</template>-->
                            <!--<el-menu-item index="1-4-1">选项1</el-menu-item>-->
                            <!--</el-submenu>-->
                        </el-submenu>
                        <el-submenu index="2">
                            <template slot="title">
                                <i class="el-icon-menu"></i>
                                <span>互联管理</span>
                            </template>
                            <el-menu-item-group>
                                <el-menu-item index="2-1" @click="goConfigurationManage">配置管理</el-menu-item>
                                <el-menu-item index="2-2" @click="goCityDataQuery">数据获取</el-menu-item>
                                <el-menu-item index="2-3" @click="goCityTaskManage">任务管理</el-menu-item>

                            </el-menu-item-group>
                        </el-submenu>
                        <el-submenu index="3">
                            <template slot="title">
                                <i class="el-icon-document"></i>
                                <span>用户管理</span>
                            </template>
                            <el-menu-item-group>
                                <el-menu-item index="3-1">移动台</el-menu-item>
                                <el-menu-item index="3-2">通话组</el-menu-item>
                            </el-menu-item-group>

                        </el-submenu>
                        <el-submenu index="4">
                            <template slot="title">
                                <i class="el-icon-setting"></i>
                                <span>信息统计</span>
                            </template>
                            <el-menu-item-group>
                                <el-menu-item index="4-1">性能统计</el-menu-item>
                                <el-menu-item index="4-2">告警统计</el-menu-item>
                                <el-menu-item index="4-3">终端统计</el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>
                        <el-submenu index="5">
                            <template slot="title">
                                <i class="el-icon-setting"></i>
                                <span>系统管理</span>
                            </template>
                            <el-menu-item-group>
                                <el-menu-item index="5-1" @click="goUserManage">账户管理</el-menu-item>
                                <el-menu-item index="5-2">用户权限</el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>
                    </el-menu>
                </el-col>
            </el-row>
        </div>
        <!-- content -->
        <div class="content-box">
            <router-view/>
        </div>


    </div>

</template>

<script>
    import router from '../router'
    export default {
        data() {
            let userName = localStorage.getItem("loginUserName"); //取出登录用户信息
            console.log("userName", localStorage.getItem("loginUserName"));
            return {
                loginUserName:userName,
            }
        },
        name: "Home",
        methods: {
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            },
            goConfigurationManage(){
                this.$router.push({path:'/home/configurationManage'})
            },
            goCityTaskManage(){
                this.$router.push({path:'/home/cityTaskManage'})
            },
            goUserManage(){
                this.$router.push({path:'/home/userManage'})
            },
            goCityDataQuery(){
                this.$router.push({path:'/home/queryCityData'})
            }
        },
        mounted:function () {
            let userToken = JSON.parse(localStorage.getItem("userToken")); //取出登录用户信息
            if(userToken == null){
                console.log('not login');
                router.push({path:'/login'}); //无缓存登录信息，跳转回登录页
            }
        }
    }
</script>

<style scoped>
    @import  '../../static/css/home.css';



</style>
