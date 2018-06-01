<template id="configurationManagement">
    <div class="mainContainer content">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-tickets"></i>互联配置管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" size="small" plain icon="delete" @click="handelAdd" v-show="isConfManagmentAddShow == true">添加配置</el-button>
            </div>
            <el-table :data="provinceConnects" stripe border style="width: 100%">
                <el-table-column prop="index" label="序号" width="50" align="center"></el-table-column>
                <el-table-column prop="cityName" label="市" width="100" align="center"></el-table-column>
                <el-table-column prop="cityId" label="市标识" width="80" align="center"></el-table-column>
                <el-table-column prop="systemId" label="系统号" width="150" align="center"></el-table-column>
                <el-table-column prop="cityNmsUrl" label="市网管接口路径" align="center"></el-table-column>
                <el-table-column prop="operation" label="操作" width="150" align="center">
                    <template scope="scope">
                        <el-button size="mini" type="primary" icon="edit" @click="configurationInfo.editMethod='PUT';dialogFormVisible = true; handleUpdate(scope.$index, scope.row)" >修改</el-button>
                        <el-button size="mini" type="danger" icon="delete2" @click="handleDelete(scope.row.id)" >删除</el-button>
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
            <el-form :model="configurationInfo" status-icon :rules="rules" ref="configurationInfo" label-width="120px">
                <el-form-item label="区号"  prop="cityId">
                    <el-input class="dialog-input" v-model="configurationInfo.cityId" placeholder="请输入用户名" clearable></el-input>
                </el-form-item>
                <el-form-item label="系统域名" prop="systemId">
                    <el-input class="dialog-input" v-model="configurationInfo.systemId" placeholder="请输入密码" clearable></el-input>
                </el-form-item>
                <el-form-item label="互联路径" prop="connectUrl">
                    <el-input  class="dialog-input" v-model="configurationInfo.connectUrl" placeholder="请输入市网管互联路径" clearable></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button v-show="true" type="primary" @click="configurationAdd('configurationInfo')">确 定</el-button>
              </span>
        </el-dialog>
    </div>
</template>
<script>
    import Vue from 'vue'
    export default {
        name: "ConfigurationManage",
        data:function(){
            return{
                configurationInfo:{
                    id:'',
                    cityId:'',
                    systemId:'',
                    connectUrl:'',
                    editMethod:'POST',
                },
                provinceConnects:[],
                currentPage:1,
                currentSize:10,
                total:0,
                isConfManagmentAddShow:true,
                dialogFormVisible:false,
                dialogName:'添加配置',
                rules: {
                    cityId: [
                        { required: true, message: '请输入区号', trigger: 'blur' },
                    ],
                    systemId: [
                        { required: true, message: '请输入市网管系统号', trigger: 'blur' },
                    ],
                    connectUrl: [
                        { required: true, message: '请输入市网管互联路径', trigger: 'blur' },
                    ]
                }

            }
        },
        methods:{
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.currentSize = val;
                this.getProvinceConnects(this.currentPage,val);
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.currentPage = val;
                this.getProvinceConnects(val,this.currentSize);
            },
            //用户增删改查
            getProvinceConnects:function(pageNum, pageSize){
                let _this = this;
                let getProvinceConnectUrl = '/interconnect/page/'+pageNum+'/'+pageSize;
                let params = {

                };
                _this.getData(getProvinceConnectUrl,'POST',JSON.stringify(params), function(data){
                    console.log(data);
                    if(data.content){
                        _this.total = data.content.count;
                        _this.currentPage = data.content.pageNo;
                        let contentList = data.content.list;
                        if(contentList.length !== 0){
                            _this.provinceConnects = [];
                            var index = 1;
                            contentList.map((item) => {
                                _this.provinceConnects.push({
                                    index:index++,
                                    id:item.id,
                                    cityName:item.cityId,
                                    cityId:item.cityId,
                                    systemId:item.systemId,
                                    cityNmsUrl:item.connectUrl,
                                })
                            })
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
                this.dialogFormVisible=true;
                this.configurationInfo.editMethod='POST';
                this.configurationInfo.cityId = '';
                this.configurationInfo.systemId = '';
                this.configurationInfo.connectUrl = '';
            },
            handleUpdate:function(configurationId, configurationInfo){
                this.dialogName='修改配置'

                this.index = configurationId;
                //默认填入修改信息
                this.configurationInfo.id = configurationInfo.id;
                this.configurationInfo.cityId = configurationInfo.cityId;
                this.configurationInfo.systemId = configurationInfo.systemId;
                this.configurationInfo.connectUrl = configurationInfo.cityNmsUrl;

            },
            handleDelete:function(id){
                console.log("id",id);
                this.$confirm('即将删除该配置及其所有数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let _this = this;
                    let deleteUrl = '/interconnect/'+id;
                    let params = {

                    }
                    this.getData(deleteUrl,'DELETE',params,function(data){
                        console.log(data);
                        if(data.message == 'OK'){
                            console.log('删除成功');
                            _this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                            _this.getProvinceConnects(_this.currentPage,_this.currentSize);
                        }else{
                            console.log('删除失败');
                            _this.$message.error("删除失败");
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
            },
            configurationAdd:function (configurationInfo) {
                console.log("ccc", this.configurationInfo.editMethod);
                let _this = this;
                _this.$refs[configurationInfo].validate((valid) => {
                    if (valid) {
                        _this.dialogFormVisible = false;
                        let addUrl = '/interconnect';
                        let params = {
                            "id":this.configurationInfo.id,
                            "cityId":this.configurationInfo.cityId,
                            "systemId":this.configurationInfo.systemId,
                            "connectUrl":this.configurationInfo.connectUrl,
                        }
                        let tip = '添加';
                        if (this.configurationInfo.editMethod == 'PUT') {
                            tip = '修改';
                        }
                        _this.getData(addUrl,this.configurationInfo.editMethod,JSON.stringify(params),function(data){
                            console.log(data);
                            if(data.message == 'OK'){
                                console.log('添加成功');
                                _this.$message({
                                    type: 'success',
                                    message: tip + '成功!'
                                });
                                _this.getProvinceConnects(_this.currentPage,_this.currentSize);
                            }else{
                                console.log(tip + '失败');
                                _this.$message.error(tip + "失败");
                            }
                        },function(err){
                            console.log(err);
                        },true,true)
                    } else {
                        return false;
                    }
                });
            }
        },
        mounted:function(){
            let _this = this;
            Vue.nextTick(function(){
                let userToken = JSON.parse(localStorage.getItem("userToken")); //取出登录用户信息
                if(userToken == null){
                    console.log('not login');
                    this.$router.push({path:'/login'}); //无缓存登录信息，跳转回登录页
                }else{
                    _this.getProvinceConnects(_this.currentPage,_this.currentSize);
                    // let menuAuth = (localStorage.getItem("menuAuth")).split(",");
                    // if(menuAuth.indexOf('用户管理') !== -1){
                    // }else{
                    //     $(".mainContainer").html('<h2>无权限</h2>').css({"color":"#666"});
                    // }
                };

            });
        }

    }
</script>

<style scoped>
    .handle-box{
        margin-bottom: 20px;
        text-align: left;
    }

    .el-input--suffix .el-input__inner {
        padding-right: 30px;
    }


</style>
