
import { appConfig } from '@/config/appConfigs';
import { Notification } from 'element-ui';
var gyWebSocket = function(option) {
    var Notifi = Notification;
    var obj ={};
    var websocket;
    obj.defaults ={
        host:'10.17.86.108',
        port:'8080',
        onOpen: function () { console.log('connent')},
        onClose: function () {console.log('close') },
        onMessage: function (evt) {console.log(evt) },
        onError: function () { console.log('error')}
    };
    var options = Object.assign(obj.defaults,option);
    obj.connect = function(userId){
        if(!userId) return null;
        if ('WebSocket' in window) {
            websocket = new WebSocket(`ws://${options.host}:${options.port}/platformService/websocket?${userId}/${appConfig.appCode}`);
        } 
        else if ('MozWebSocket' in window) {
            websocket = new MozWebSocket(`ws://${options.host}:${options.port}/platformService/websocket?${userId}/${appConfig.appCode}`);
        } 
        else {
            websocket = new SockJS(`http://${options.host}:${options.port}/platformService/sockjs?${userId}/${appConfig.appCode}`);
        }
        websocket.onopen = options.onOpen;
        websocket.onmessage = wsOnMessage;//options.onMessage;
        websocket.onerror = options.onError;
        websocket.onclose = options.onClose;
    };
    function wsOnMessage (evt){
        let msg = JSON.parse(evt.data);
        console.log(msg);
        if(msg.msgKind == '3'){
            eval(msg.data);
        }else if(msg.msgKind == '4'){
            Notifi({
                type:'info',
                title: '通知',
                dangerouslyUseHTMLString: true,
                message: msg.data
              });
        }
        options.onMessage(msg)
    };
    obj.send = function (s) {
        websocket.send(s);
    };    
    obj.close = function(){
        if(websocket != undefined && websocket != null) {
			websocket.close();
		} 
    };
    obj.websocket = websocket;
    return obj;
}
export default gyWebSocket
