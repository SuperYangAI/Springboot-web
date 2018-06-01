<template id="queryCityData">
    <div class="mainContainer content">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-tickets"></i>数据获取</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="container">
            <div style="text-align: left;margin-bottom: 30px">
                <el-tag type="warning" >市网管地址</el-tag>
                <el-select v-model="value" placeholder="请选择" size="small" style="margin-left: 30px">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                </el-select>
            </div>

            <div style="text-align: left;margin-bottom: 30px">
                <el-tag type="warning">选    项</el-tag>
                <el-select v-model="value1" placeholder="请选择" size="small" style="margin-left: 30px">
                    <el-option
                        v-for="item in optionsQueryList"
                        :key="item.value1"
                        :label="item.label"
                        :value="item.value1">
                    </el-option>
                </el-select>

            </div>

            <div style="text-align: left;margin-bottom: 40px" v-if="requireDate">
                <el-tag type="warning">数据时间</el-tag>
                <el-date-picker
                    v-model="beginDate"
                    type="datetime"
                    placeholder="开始时间"
                    default-time="12:00:00" size="small" style="margin-left: 30px">
                </el-date-picker>
                <el-date-picker
                    v-model="endDate"
                    type="datetime"
                    placeholder="结束时间"
                    default-time="12:00:00" size="small" style="margin-left: 30px">
                </el-date-picker>
            </div>

            <div style="margin-right: 600px ">
                <el-button type="primary" size="small">查询</el-button>
            </div>

        </div>
    </div>

</template>

<script>
    import Vue from 'vue'
    export default {
        name: "QueryCityManage",
        data () {
            return {
                radio8: '1',
                requireDate :false,
                pickerOptions1: {
                    shortcuts: [{
                        text: '今天',
                        onClick(picker) {
                            picker.$emit('pick', new Date());
                        }
                    }, {
                        text: '昨天',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '一周前',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', date);
                        }
                    }]
                },

                beginDate: '',
                endDate:'',

                options: [],
                value: '',

                optionsQueryList :[{
                    value1: 'top',
                    label: '拓扑查询',
                },{
                    value1: 'faultInfo',
                    label: '告警统计查询'
                },{
                    value1: 'alarmInfo',
                    label: '告警实时查询'
                },{
                    value1: 'alarmClear',
                    label:'告警清除查询'
                },{
                    value1: 'trafficStatic',
                    label:'话务统计查询'
                },{
                    value1: 'linkInfo',
                    label:'链路信息查询'
                },{
                    value1: 'terminalInfo',
                    label:'终端信息查询'
                },{
                    value1:'terminalUsrInfo',
                    label:'终端使用情况查询'
                },{
                    value1:'terminalDevInfo',
                    label:'终端设备信息查询'
                }],
                value1: '',
            }
        },
        watch:{
            value1:{
                handler(curVal,oldVal){
                    if(curVal == 'top'){
                       this.requireDate =  false;
                    }else if(curVal == 'linkInfo'){
                        this.requireDate =  false;
                    }else if(curVal == 'alarmInfo'){
                        this.requireDate =  true;
                    }else if(curVal == 'alarmClear'){
                        this.requireDate =  true;
                    }else if(curVal == 'linkInfo'){
                        this.requireDate =  false;
                    }else if(curVal == 'terminalInfo'){
                        this.requireDate =  true;
                    }else if(curVal == 'terminalUsrInfo'){
                        this.requireDate =  true;
                    }else if(curVal == 'terminalDevInfo'){
                        this.requireDate =  true;
                    }else if(curVal == 'faultInfo'){
                        this.requireDate = true
                    }

                    console.log(curVal,oldVal)
                },

            }
        },
        methods:{
            getProvinceConnectList:function(){
                let _this = this;
                let getProvinceConnectUrl = '/cityDataQuery/getCityData';
                let params = {};
                _this.getData(getProvinceConnectUrl,'POST',JSON.stringify(params),function (data) {
                    console.log('provinceConnect',data);
                    if(data.content){
                        _this.options = [];
                        data.content.map((item) => {
                            let provice = {
                                value: item.systemId,
                                label: item.systemId
                            }
                            _this.options.push(provice);

                        })
                    }


                },function(err){
                    console.log(err);
                },true,true)
            }
        },
        mounted:function(){
            let userToken = JSON.parse(localStorage.getItem("userToken")); //取出登录用户信息
            if(userToken == null){
                console.log('not login');
                this.$router.push({path:'/login'}); //无缓存登录信息，跳转回登录页
            }
            let _this = this;
            Vue.nextTick(function () {
                _this.getProvinceConnectList();
            });

        }

    }

</script>

<style scoped>
    .el-tag{
        width: 100px;
    }

</style>
