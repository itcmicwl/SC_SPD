
var A = Java.type("my.camel.example.js.A");

var flag ={val: 0.12,name:'flagname'};


result = A.my_static + request.headers.get('my-header') +  (body * 2 + 1 + flag.val) + ":" + flag.name;