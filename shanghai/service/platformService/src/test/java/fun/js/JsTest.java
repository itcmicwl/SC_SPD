package fun.js;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import common.base.KV;
import common.transform.Tx;
import tools.TxTest.A;
import tools.TxTest.B;

public class JsTest {
	public static class A{
		public static String my_static="static_value"; 
		private String id;
		private String name;
		private int v;
		private Date curDate;
		
		private Long aV;
		
		private List<A> children = new ArrayList<A>();
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getV() {
			return v;
		}
		public void setV(int v) {
			this.v = v;
		}
		public Date getCurDate() {
			return curDate;
		}
		public void setCurDate(Date curDate) {
			this.curDate = curDate;
		}
		public Long getaV() {
			return aV;
		}
		public void setaV(Long aV) {
			this.aV = aV;
		}
		public String fun(String a,String b)
		{
			return a + b;
		}
		public List<A> getChildren() {
			return children;
		}
		public void setChildren(List<A> children) {
			this.children = children;
		}
		
		
	}
	public static class B{
		private String id;
		private String name;
		private int v;
		private Date curDate;
		private Long bv;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getV() {
			return v;
		}
		public void setV(int v) {
			this.v = v;
		}
		public Date getCurDate() {
			return curDate;
		}
		public void setCurDate(Date curDate) {
			this.curDate = curDate;
		}
		public Long getBv() {
			return bv;
		}
		public void setBv(Long bv) {
			this.bv = bv;
		}
		@Override
		public String toString() {
			return "B [id=" + id + ", name=" + name + ", v=" + v + ", curDate=" + curDate + ", bv=" + bv + "]";
		}
		public static int add(int a,int b)
		{
			return a + b;
		}
	}
	
	//@Test
	public void test1() throws Exception{
		
		InputStream inStr = this.getClass().getResourceAsStream( "t2.js");
		String js = IOUtils.toString(inStr,Charset.forName("utf-8"));	
		
		ScriptEngine engine = new ScriptEngineManager()  
                .getEngineByExtension("js");  
		
		Bindings bind = engine.createBindings();  
		
		
		A a = new A();
        a.setId("af");
        a.setName("中国人");
        a.setV(123);
        a.setaV(888L);
        a.setCurDate(new Date()); 
        
		A child = new A();
		child.setId("child_idv");
		child.setName("中国人child");
		child.setV(777);
		child.setaV(23L);
		child.setCurDate(new Date()); 
        
		a.getChildren().add(child);
        
        
        B b = new B();
        B d = new B();
        
        HashMap c = new KV();
        
        Tx.transform(a).to(b);
        
        b.setBv(789L);
        b.setV(111);
        b.setCurDate(new Date());
        
        Tx.transform(b).to(c);
        
        System.out.println(engine.eval(js));  
        
        Object z = engine.get("a");
	}
	//@Test
	public void test() throws IOException, ScriptException{
		InputStream inStr = this.getClass().getResourceAsStream( "t.js");
		String js = IOUtils.toString(inStr,Charset.forName("utf-8"));	
		
		ScriptEngine engine = new ScriptEngineManager()  
                .getEngineByExtension("js");  
		
		Bindings bind = engine.createBindings();  
        
		List list = new ArrayList();  
        // 得到挷定的键值对象，把当前的两个JAVA对象放进去  
        list.add("abc");            
               
        bind.put("list", list);  
        
        HashMap<String,Object> map = new HashMap<>();
        
        map.put("x", 1234);
        map.put("y",123.78);  
        
        bind.put("map", map);
        // 把挷下的键值对象放进去，作用域是当前引擎的范围  
  
        engine.setBindings(bind, ScriptContext.ENGINE_SCOPE);          
        System.out.println(engine.eval(js));          
        Object z = engine.get("z");        
        Object m = engine.get("m");    
		
	}
	
	@Test
	public void test2() throws IOException, ScriptException
	{
		InputStream inStr = this.getClass().getResourceAsStream( "t2.js");
		String js = IOUtils.toString(inStr,Charset.forName("utf-8"));	
		
		ScriptEngine engine = new ScriptEngineManager()  
                .getEngineByExtension("js");  
		
		Bindings bind = engine.createBindings(); 
		engine.setBindings(bind, ScriptContext.ENGINE_SCOPE);  
		
		System.out.println(engine.eval(js)); 
		
		Object b = engine.get("b");     
	}

}
