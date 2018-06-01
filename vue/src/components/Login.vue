<template id="login">
    <div style="width: 100%;height: 100%;">
        <div class="loginContainer demo-1">
            <div class="">
                <div id="large-header" class="large-header">
                    <!-- <canvas id="demo-canvas"></canvas> -->
                    <div class="logo_box">
                        <img src="../../static/img/logo.png">
                        <h3>欢迎您</h3>
                        <el-form :model="userLogin" :rules="rules" ref="userLogin">
                            <div class="input_outer">
                                <span class="u_user"></span>
                                <el-form-item prop="userName">
                                    <el-input v-model="userLogin.userName" name="userName" class="text" type="text"
                                              style="width: 20%"
                                              placeholder="请输入用户名" clearable
                                              @keyup.enter.native="login('userLogin')"></el-input>
                                </el-form-item>
                            </div>
                            <div class="input_outer">
                                <span class="us_uer"></span>
                                <el-form-item prop="password">
                                    <el-input v-model="userLogin.password" name="password" class="text" type="password"
                                              style="width: 20%"
                                              placeholder="请输入密码" clearable
                                              @keyup.enter.native="login('userLogin')"></el-input>
                                </el-form-item>
                            </div>
                            <div class="mb2">
                                <el-button class="act-but submit" type="primary" @click="login('userLogin')">登录
                                </el-button>
                            </div>
                        </el-form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
      name: "Login",
      template: '#login',
      data(){
        return {
          userLogin:{
            userName:'',
            password:''
          },
          rules: {
            userName: [
              { required: true, message: '请输入用户名称', trigger: 'blur' },
            ],
            password: [
              { required: true, message: '请输入用户密码', trigger: 'blur' },
            ]
                }
            }
        },
        methods: {
            login: function (formName) {
                let _this = this;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let loginUrl = '/user/login';
                        let params = {
                            userName: this.userLogin.userName,
                            password: this.userLogin.password
                        }
                        _this.getData(loginUrl, 'POST', params, function (data) {
                            console.log("login result:", data);
                            if (data.message == "OK") {
                                console.log('登录成功');
                                _this.$router.push({path: '/home'}); //登录成功跳转至主页
                                //设置用户信息的本地存储
                                localStorage.setItem("userToken", JSON.stringify(data.content.token));
                                //设置用户菜单权限的本地存储
                                localStorage.setItem("menuAuth", (data.content.licensed).join(","));
                                //设置用户ID的本地存储
                                localStorage.setItem("userId", (data.content.userId).toString());
                                //设置用户ID的本地存储
                                localStorage.setItem("loginUserName", (data.content.userName).toString());
                            } else {
                                console.log('用户不存在');
                                _this.$message.error('用户不存在或者密码错误');
                            }
                        }, function (err) {
                            console.log(err);
                        }, false, false)
                    } else {
                        return false;
                    }
                });
            },
        },
        mounted:function () {
            let userToken = JSON.parse(localStorage.getItem("userToken")); //取出登录用户信息
            if(userToken == null){
                console.log('not login');
                this.$router.push({path:'/login'}); //无缓存登录信息，跳转回登录页
            }
        }
    }
</script>

<style scoped>
    /*@import  '../../static/css/home.css';*/

    .el-button {
        display: inline-block;
        line-height: 1;
        white-space: nowrap;
        cursor: pointer;
        background: #41b883;
        border: 1px solid #dcdfe6;
        color: #606266;
        -webkit-appearance: none;
        text-align: center;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        outline: 0;
        margin: 0;
        -webkit-transition: .1s;
        transition: .1s;
        padding: 12px 20px;
        font-size: 14px;
        border-radius: 4px;
        width: 8%;
    }




</style>
