<template>
    <el-row style="height:100%">
        <div id="chat">
            <div class="sidebar">
                <div class="m-card">
                    <header><p class="name">{{user.ename}}</p></header>
                    <footer><input class="search" placeholder="search user..."></footer>
                </div>
                <div class="m-list">
                    <ul>
                        <li v-for="us in getOnlineUsers"  :key="us.userId" :class="{ active: us.userId == sltUser }" 
                        v-if="us.userId !== user.userId" @click="sltUser=us.userId">
                            <span class="name">{{us.ename}}</span>
                        </li>                        
                    </ul>
                </div>
            </div>
            <div class="main">
                <div class="m-message">
                    <ul>
                        <li v-for="msg in getSocketMsg">
                            <p class="time"><span>{{msg.msgDate | dateFormat}}</span></p>
                            <div class="main" :class="{self: msg.fromUser == user.userId}">                                
                                <div class="text">{{getUsrName(msg.fromUser)}}:{{msg.data}}</div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="m-text">
                    <textarea v-model="sendMsg"></textarea>
                    <el-button type="primary" plain @click="sendMessage" size="medium" style="float: right;margin-right: 10px;">发送</el-button>                  
                </div>
            </div>
        </div>
    </el-row>
</template>
<script>
import { appConfig } from '@/config/appConfigs';
import { mapGetters } from 'vuex';
export default {
    data() {
        return {
            sltUser:'',        
            sendMsg:'',
            recMsg:''
        }
    },    
    computed: {
		...mapGetters(["getSocketMsg","getOnlineUsers"])
	},
    methods: {
        getUsrName(userId){
            let usr = this.getOnlineUsers.find(o=>o.userId == userId);
            if(usr) return usr.ename;
            return '未知用户';
        },
       sendMessage(){
           if(this.sendMsg && this.sltUser){
               var msg ={
                   fromUser:this.user.userId,
                   toUser:this.sltUser,
                   data:this.sendMsg,
                   appCode:appConfig.appCode,
                   msgDate:new Date()
               };
            this.gyWebSocket.send(JSON.stringify(msg));
            this.sendMsg = '';
           }
       },
       closeWebSocket(){
           this.gyWebSocket.close();
       }      
    },
    mounted() {
        this.userName = this.user.ename;
    }
}
</script>
<style scoped>
ul {
    padding:0px;
    list-style: none;
}
#chat{overflow:hidden;border-radius:3px;margin: 20px auto;width: 800px;height: calc(100% - 200px);}
#chat .sidebar{height:100%;float:left;width:200px;color:#f4f4f4;background-color:#2e3238}
#chat .main{height:100%;position:relative;overflow:hidden;background-color:#eee}
#chat .m-text{position:absolute;width:100%;bottom:0;left:0}
#chat .m-message{height:80%}
.m-card {padding:9pt;border-bottom:1px solid #24272c}
.m-card footer{margin-top:10px}
.m-card .avatar,.m-card .name{vertical-align:middle}
.m-card .avatar{border-radius:2px}
.m-card .name{display:inline-block;margin:0 0 0 15px;font-size:1pc}
.m-card .search{padding:0 10px;width:100%;font-size:9pt;color:#fff;height:30px;line-height:30px;border:1px solid #3a3a3a;border-radius:4px;outline:0;background-color:#26292e}
.m-list li{padding:5pt 5px;border-bottom:1px solid #292c33;cursor:pointer;-webkit-transition:background-color .1s;transition:background-color .1s}
.m-list li:hover{background-color:hsla(0,0%,100%,.03)}
.m-list li.active{background-color:hsla(0,0%,100%,.1)}
.m-list .avatar,.m-list .name{vertical-align:middle}
.m-list .avatar{border-radius:2px}
.m-list .name{display:inline-block;margin:0 0 0 15px}
.m-text {border-top:1px solid #ddd;background-color: #fff;}
.m-text textarea{padding:10px;height:calc(100% - 40px);width:100%;border:none;outline:0;font-family:Micrsofot Yahei;resize:none}
.m-message {padding:10px 15px;overflow-y:scroll}
.m-message li{margin-bottom:15px}
.m-message .time{margin:7px 0;text-align:center}
.m-message .time>span{display:inline-block;padding:0 18px;font-size:9pt;color:#fff;border-radius:2px;background-color:#dcdcdc}
.m-message .avatar{float:left;margin:0 10px 0 0;border-radius:3px}
.m-message .text{display:inline-block;position:relative;padding:0 10px;max-width:calc(100% - 40px);min-height:30px;line-height:2.5;font-size:9pt;text-align:left;word-break:break-all;background-color:#fafafa;border-radius:4px}
.m-message .text:before{content:" ";position:absolute;top:9px;right:100%;border:6px solid transparent;border-right-color:#fafafa}
.m-message .self {text-align:right}
.m-message .self .avatar{float:right;margin:0 0 0 10px}
.m-message .self .text{background-color:#b2e281}
.m-message .self .text:before{right:inherit;left:100%;border-right-color:transparent;border-left-color:#b2e281}
</style>