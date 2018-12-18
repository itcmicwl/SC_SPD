package tools;

import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.util.ReflectionUtils;

import com.googlecode.aviator.AviatorEvaluator;

import common.base.KV;
import common.cache.redis.StringObjectRedisTemplate;
import common.db.IBean;
import common.transform.Tx;
import common.utils.json.Json;
import gyqx.platform.po.SysAtom;
import gyqx.platform.po.SysDict;
import gyqx.platform.sys.service.impl.SysDictService;
import ognl.Ognl;
import ognl.OgnlContext;


public class TxTest {
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

	@Before
	public void setUp() throws Exception {
		
	}
	private static final Logger logger = LoggerFactory.getLogger(Test.class);
	@Test
	public void test() throws Exception {
		
		/*Class<? extends IBean> atomClass =  SysAtom.class;
		
		Pattern pat = Pattern.compile("(select|update|insert|call|,|\\:|\\*|%|0x|&|@|exe|\\{|\\})",Pattern.CASE_INSENSITIVE );
		
		Matcher mat = pat.matcher("calL0Xvvvvvv");
		StringBuffer sb = new StringBuffer();
		while(mat.find())
		{
			System.out.println("");
			mat.appendReplacement(sb, "replacement");
		}
		mat.appendTail(sb);
		System.out.println(sb);*/
		System.out.println(System.nanoTime());
		System.out.println(System.nanoTime());
		
		System.out.println(System.currentTimeMillis());
		System.out.println(System.currentTimeMillis());
		String sjson = Json.write(KV.from("f1","12","f2",567,"f3",new String[]{"a","b"}));
		
		Map mapsj = Json.read(sjson, Map.class);

		//KV.from(new Object[][]{{"a","aaa"},{"ab","abaa"}});


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
        
        
        //Tx.transform(c).named("a").from("a.id,a.name , a.aV + 356 as bv ").to(b);
        
        
        //Tx.transform(a,b,c).named("a,b,c").from("a.id,string.substring(a.name,0,1)+b.name as name , a.v + c.v as bv ").to(d);
        
        //Tx.transform(a).named("a").from("a.id,a.name").to(b);
        
        //Tx.transform(a).named("a").from("a.id,a.name , a.aV as bv ").to(b);
        
        Tx.transform(a).named("a").from("a.id,a.name , a.aV + a.v as bv ").to(b);
        
        b = new B();
        
        //Tx.transform(c).named("a").from("a.id,a.name , a.aV + a.v as bv ").to(b);
        RedisCacheManager manager = new RedisCacheManager(new StringObjectRedisTemplate());
        
        OgnlContext context = new OgnlContext(new KV("a",a,"b",b));
       // context.put("a", a);
      //  context.put("b", b);
        Object pexpr = Ognl.parseExpression("#a.aV + #b.v");
        
       	Ognl.getValue("#a.aV + #b.v", context, context.getRoot());
       	
        long t = System.currentTimeMillis();
        for(int i = 0;i < 100000;i++)
        {
        	/*b = new B();
        	Tx.transform(a).named("a").from("a.id,a.name , a.aV + a.v as bv ").to(b);*/
        	/*c = new KV();            
            Tx.transform(a).to(c);*/
        	/*b = new B();
        	Tx.transform(a).to(b);*/
        	
        	/*b = new B();
        	Tx.transform(a).named("a").from("a.id,a.name , a.aV as bv ").to(b);*/
        	
        	Object v = Ognl.getValue(Ognl.parseExpression("#a.aV + #b.v"),context, context.getRoot());
        }
        t =  System.currentTimeMillis() - t;
        System.out.println(t); 


	}
	
	

}
