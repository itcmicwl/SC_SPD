/**
 * 
 */



var A = Java.type("fun.js.JsTest.A"); 
var Tx = Java.type("common.transform.Tx"); 
var a = new A();

a.id = 'lkfdsid ';
a.name = "www" + A.my_static;
var b = new A();
Tx.transform(a).to(b);

