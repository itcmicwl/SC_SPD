package groovy;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.junit.Test;

import common.base.KV;
import common.transform.Tx;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import tools.TxTest.A;
import tools.TxTest.B;

public class GroovyTest 
{
	
	public static class A{
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
	public void test() throws Exception
	{
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
        
        Map map = Tx.transform(b).toMap();
	}
	
	@Test
	public void groovyBindTest() throws IOException
	{
		InputStream inStr = this.getClass().getResourceAsStream( "groovy-script2.txt");
		String script = IOUtils.toString(inStr,Charset.forName("utf-8"));
		
		ImportCustomizer importCustomizer = new ImportCustomizer();
	    //importCustomizer.addStaticStars("common.transform.Tx");
	    importCustomizer.addImports("common.transform.Tx");
	    CompilerConfiguration configuration = new CompilerConfiguration();
	    configuration.addCompilationCustomizers(importCustomizer);
	    
	    Binding binding = new Binding();  		
	    
	    GroovyShell shell = new GroovyShell(binding,configuration);
	    Script sc = shell.parse(script);
	    
	    binding.setVariable("a", 1);  
		
		binding.setVariable("b", 2);  
		
	    Object value = sc.run();
	    
	    for(int i = 0;i < 100000;i++)
	    {
	    	//sc = shell.parse(script);
		    binding.setVariable("a", 10 + i);  
			
			binding.setVariable("b", 20 + i) ;  
			
			value = sc.run();
		    System.out.println(value);
	    }
	}
	
	//@Test
	public void groovyTest() throws IOException
	{
		
		InputStream inStr = this.getClass().getResourceAsStream( "groovy-script.txt");
		String script = IOUtils.toString(inStr,Charset.forName("utf-8"));			
		
		ImportCustomizer importCustomizer = new ImportCustomizer();
	    //importCustomizer.addStaticStars("common.transform.Tx");
	    importCustomizer.addImports("common.transform.Tx");
	    CompilerConfiguration configuration = new CompilerConfiguration();
	    configuration.addCompilationCustomizers(importCustomizer);  
	    
		
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
       // B d = new B();        
      //  HashMap c = new KV();
        
        //Tx.transform(a).to(b);
		
		Binding binding = new Binding();  		
	    
	    GroovyShell shell = new GroovyShell(binding,configuration);
	    Script sc = shell.parse(script);
	    
	    binding.setVariable("a", a);  		
		binding.setVariable("b", b);  
		
	    Object value = sc.run();
	    //Object value =shell.evaluate(script);  
	    
	    System.out.println(value);
	}

}
