<template id="cityTaskManage.vue">
    <div style="margin-top:10px;margin-left: 10px">
        <div>
            <div class="crumbs">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item><i class="el-icon-tickets"></i>任务管理</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <el-tabs type="border-card">
                <el-tab-pane label="任务列表">
                    <h1>Hello task list</h1>
                    <h1>Hello task list</h1>
                    <h1>Hello task list</h1>
                    <h1>Hello task list</h1>
                    <h1>Hello task list</h1>
                    <h1>Hello task list</h1>

                </el-tab-pane>


                <el-tab-pane label="下发任务">
                    <div style="margin-top: 10px;text-align: left">
                        <el-select v-model="cityValue" placeholder="请选择">
                            <el-option
                                v-for="item in cities"
                                :key="item.value"
                                :label="item.label+' '+item.value"
                                :value="item.value">
                                <span style="float: left">{{ item.label }}</span>
                                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
                            </el-option>
                        </el-select>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>




        </div>
    </div>

</template>

<script>
    import Vue from 'vue'
    export default {
        name: "CityTaskManage",
        data:function(){
            return{
                isTaskListShow:false,
                cities: [{
                    value: '328',
                    label: '天津'
                }],
                cityValue:'',

            }
        },
        methods:{

        },
        mounted:function(){
            Vue.nextTick(function(){
                let userToken = JSON.parse(localStorage.getItem("userToken")); //取出登录用户信息
                if(userToken == null){
                    console.log('not login');
                    this.$router.push({path:'/login'}); //无缓存登录信息，跳转回登录页
                }else{
                    // let menuAuth = (localStorage.getItem("menuAuth")).split(",");
                    // if(menuAuth.indexOf('用户管理') !== -1){
                    // }else{
                    //     $(".mainContainer").html('<h2>无权限</h2>').css({"color":"#666"});
                    // }
                };

            });
        },
        watch:{

        }
    }
</script>

<style scoped>

</style>
