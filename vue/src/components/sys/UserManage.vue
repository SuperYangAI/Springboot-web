<template id="userManage">
    <div class="mainContainer content">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-tickets"></i>用户列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" size="small" plain icon="delete"
                           @click="handelAdd"
                           v-show="isUserManageAddShow == true">
                    添加用户
                </el-button>
            </div>
            <el-table :data="userList" stripe border style="width: 100%">
                <el-table-column prop="name" label="用户名称" width="150"></el-table-column>
                <el-table-column prop="password" label="密码" width="150"></el-table-column>
                <el-table-column prop="sex" label="性别" width="100"></el-table-column>
                <el-table-column prop="phone" label="电话号码"></el-table-column>
                <el-table-column prop="groupId" label="设备组" width="100" v-if="isUserGroupOption == true">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" @click="getUserGroup(scope.row.id);dialogGrpFormVisible = true">查看</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="option" label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="dialogFormVisible = true;userUpdate(scope.row.id,scope.row);isUserAdd = false"  v-show="isUserManageUpdateShow == true">修改</el-button>
                        <el-button size="mini"  type="danger" @click="userDelete(scope.row.id)"  v-show="isUserManageDelShow == true">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                               layout="total, sizes, prev, pager, next" :total="total" :page-size="currentSize"
                               :current-page="currentPage" :page-sizes="[10, 20, 50, 100]"></el-pagination>
            </div>
        </div>
        <!-- 用户添加修改对话框 -->
        <el-dialog :title="dialogName" :visible.sync="dialogFormVisible">
            <el-form :model="userInfo" status-icon :rules="rules" ref="userInfo">
                <el-form-item label="用户名称" :label-width="formLabelWidth" prop="userName">
                    <el-input v-model="userInfo.userName" placeholder="请输入用户名" clearable></el-input>
                </el-form-item>
                <el-form-item label="用户密码" :label-width="formLabelWidth" prop="password">
                    <el-input v-model="userInfo.password" placeholder="请输入密码" clearable></el-input>
                </el-form-item>
                <el-form-item label="电话号码" :label-width="formLabelWidth" prop="phone">
                    <el-input v-model="userInfo.phone" placeholder="请输入电话号码" clearable></el-input>
                </el-form-item>
                <el-form-item label="性别" :label-width="formLabelWidth">
                    <el-radio v-model="userInfo.sex" label="男">男</el-radio>
                    <el-radio v-model="userInfo.sex" label="女">女</el-radio>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button v-show="isUserAdd == true" type="primary" @click="userAdd('userInfo')">确 定</el-button>
                <el-button v-show="isUserAdd == false" type="primary" @click="comfirmUpdate('userInfo')">确 定</el-button>
              </span>
        </el-dialog>

    </div>
</template>

<script>
    import Vue from 'vue'
    export default {
        name: "UserManage",
        data:function(){
            //手机号码验证规则
            let checkPhone = (rule, value, callback) => {
                let phoneReg=/^[1][3,4,5,7,8][0-9]{9}$/;
                if (!value) {
                    return callback(new Error('手机号码不能为空'));
                } else {
                    return callback();
                }
                setTimeout(() => {
                    if (!phoneReg.test(value)) {
                        callback(new Error('请输入正确的手机号码'));
                    } else {
                        return callback()
                    }
                }, 1000);
            };
            return {
                userList:[],
                currentUserId:null,
                userInfo:{
                    userName:'',
                    password:'',
                    sex:'女',
                    phone:''
                },
                dialogFormVisible: false,
                dialogGrpFormVisible:false,
                formLabelWidth: '120px',
                GroupList:[],
                checkGroupList:[],
                loading:true,
                isUserAdd:true,
                isUserManageAddShow:false,
                isUserManageDelShow:false,
                isUserManageUpdateShow:false,
                isUserGroupOption:false,
                currentPage:1,
                currentSize:10,
                total:0,
                dialogName:'添加配置',
                rules: {
                    userName: [
                        { required: true, message: '请输入用户名称', trigger: 'blur' },
                    ],
                    password: [
                        { required: true, message: '请输入用户密码', trigger: 'blur' },
                        { min: 5, message: '密码长度至少5位', trigger: 'blur' }
                    ],
                    phone: [
                        { validator: checkPhone, trigger: 'blur' }
                    ]
                }
            }
        },
        methods:{
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.currentSize = val;
                this.getUserList(this.currentPage,val);
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.currentPage = val;
                this.getUserList(val,this.currentSize);
            },
            //用户增删改查
            getUserList:function(pageNum, pageSize){
                let _this = this;
                let getUserUrl = '/user/page/'+pageNum+'/'+pageSize;
                let params = {};
                _this.getData(getUserUrl,'POST',JSON.stringify(params), function(data){
                    console.log(data);
                    if(data.content){
                        _this.total = data.content.count;
                        _this.currentPage = data.content.pageNo;
                        let contentList = data.content.list;
                        if(contentList.length !== 0){
                            _this.userList = [];
                            contentList.map((item) => {
                                _this.userList.push({
                                    id:item.id,
                                    name:item.name,
                                    password:item.password,
                                    sex:item.sex,
                                    phone:item.phone
                                })
                            })
                            // $.each(contentList,function(i){
                            //     _this.userList.push({
                            //         id:contentList[i].id,
                            //         name:contentList[i].name,
                            //         password:contentList[i].password,
                            //         sex:contentList[i].sex,
                            //         phone:contentList[i].phone
                            //     })
                            // })
                            // _this.userList = data.content.list;
                            console.log("user data",_this.userList);
                        }
                    }else{
                        console.log("no user data");
                    }
                },function(err){
                    console.log(err);
                },true,true)
            },
            handelAdd:function(){
                this.dialogFormVisible = true;
                this.isUserAdd = true;
                this.dialogName='添加用户';
                this.userInfo.userName = '';
                this.userInfo.password = '';
                this.userInfo.sex = '女';
                this.userInfo.phone = '';
            },
            userAdd:function(formName){
                let _this = this;
                _this.$refs[formName].validate((valid) => {
                    if (valid) {
                        _this.dialogFormVisible = false;
                        let addUrl = '/user';
                        let params = {
                            "name":this.userInfo.userName,
                            "password":this.userInfo.password,
                            "sex":this.userInfo.sex,
                            "phone":this.userInfo.phone
                        }
                        _this.getData(addUrl,'POST',JSON.stringify(params),function(data){
                            console.log(data);
                            if(data.message == 'OK'){
                                console.log('用户添加成功');
                                _this.$message({
                                    type: 'success',
                                    message: '用户添加成功!'
                                });
                                _this.getUserList(_this.currentPage, _this.currentSize);
                            }else{
                                console.log('用户添加失败');
                                _this.$message.error("用户添加失败");
                            }
                        },function(err){
                            console.log(err);
                        },true,true)
                    } else {
                        return false;
                    }
                });
            },
            userUpdate:function(userId,user){
                console.log(userId,user);
                this.dialogName='修改用户';
                this.currentUserId = userId;
                //默认填入修改用户信息
                this.userInfo.userName = user.name;
                this.userInfo.password = user.password;
                this.userInfo.sex = user.sex;
                this.userInfo.phone = user.phone;
            },
            comfirmUpdate:function(formName){
                let _this = this;
                _this.$refs[formName].validate((valid) => {
                    if (valid) {
                        _this.dialogFormVisible = false;
                        let updateUrl = '/user'
                        let params = {
                            "id":this.currentUserId,
                            "name":this.userInfo.userName,
                            "password":this.userInfo.password,
                            "sex":this.userInfo.sex,
                            "phone":this.userInfo.phone
                        }
                        _this.getData(updateUrl,'PUT',JSON.stringify(params),function(data){
                            console.log(data);
                            if(data.message == 'OK'){
                                console.log('用户修改成功');
                                _this.$message({
                                    type: 'success',
                                    message: '用户修改成功!'
                                });
                                _this.getUserList(_this.currentPage,_this.currentSize);
                            }else{
                                console.log('用户修改失败');
                                _this.$message.error("用户修改失败");
                            }
                        },function(err){
                            console.log(err);
                        },true,true)
                    } else {
                        return false;
                    }
                });
            },
            userDelete:function(userId){
                this.$confirm('即将删除该用户及其所有数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let _this = this;
                    let deleteUrl = '/user/'+userId;
                    let params = {

                    }
                    _this.getData(deleteUrl,'DELETE',params,function(data){
                        console.log(data);
                        if(data.message == 'OK'){
                            console.log('用户删除成功');
                            _this.$message({
                                type: 'success',
                                message: '用户删除成功!'
                            });
                            _this.getUserList(_this.currentPage,_this.currentSize);
                        }else{
                            console.log('用户删除失败');
                            _this.$message.error("用户删除失败");
                        }
                    },function(err){
                        console.log(err);
                    },true,true)
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }
        },
        mounted:function(){
            let userToken = JSON.parse(localStorage.getItem("userToken")); //取出登录用户信息
            if(userToken == null){
                console.log('not login');
                this.$router.push({path:'/login'}); //无缓存登录信息，跳转回登录页
            }

            let _this = this;
            Vue.nextTick(function(){
                let userToken = JSON.parse(localStorage.getItem("userToken")); //取出登录用户信息
                if(userToken == null){
                    console.log('not login');
                    _this.$router.push({path:'/login'}); //无缓存登录信息，跳转回登录页
                }else{
                    let menuAuth = (localStorage.getItem("menuAuth")).split(",");
                    if(menuAuth.indexOf('用户管理') !== -1){
                        _this.getUserList(_this.currentPage,_this.currentSize);
                        // _this.getAllGrpList();
                    }else{
                        $(".mainContainer").html('<h2>无权限</h2>').css({"color":"#666"});
                    }
                    if(menuAuth.indexOf('添加用户') !== -1){
                        _this.isUserManageAddShow = true;
                    }
                    if(menuAuth.indexOf('删除用户') !== -1){
                        _this.isUserManageDelShow = true;
                    }
                    if(menuAuth.indexOf('修改用户') !== -1){
                        _this.isUserManageUpdateShow = true;
                    }
                    if(menuAuth.indexOf('绑定用户设备组') !== -1){
                        _this.isUserGroupOption = true;
                    }
                };

            })
        }
    }
</script>

<style scoped>
    .handle-box{
        margin-bottom: 20px;
        text-align: left;
    }
</style>
