package demo.test.aviator;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.persistence.Id;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.junit.Before;
import org.junit.Test;

//import com.googlecode.aviator.AviatorEvaluator;

import common.base.KV;
import common.transform.Tx;

public class AviatorCase {
	
	public static class A{
		private String id;
		private String name;
		private int v;
		private Date curDate;
		
		private Long aV;
		
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
		
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws Exception {
		TestAviator foo = new TestAviator(100, 3.14f, new Date());
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("foo", foo);
//        System.out.println(AviatorEvaluator.execute("foo.i + foo.toString()", env)); 
//        System.out.println(AviatorEvaluator.execute("'foo.i = '+foo.i", env));   // foo.i = 100
//        System.out.println(AviatorEvaluator.execute("'foo.f = '+foo.f", env));   // foo.f = 3.14
//        System.out.println(AviatorEvaluator.execute("'foo.date.year = '+(foo.date.year+1990)", env));  // foo.date.year = 2106
        
        A a = new A();
        a.setId("af");
        a.setName("中国人");
        a.setV(123);
        a.setaV(888L);
        a.setCurDate(new Date());       
        
        
        B b = new B(); 

        b = new B();
        //ConvertUtils.register(myConverter, Integer.class);
     /*   Converter myConverter =
        	    new IntegerConverter();
        	   ConvertUtils.register(myConverter, Integer.TYPE);    // Native type
*/        	   
        //
        //ConvertUtils.register(new DateConverter(null), java.util.Date.class);

        
        //DateTimeConverter dateConverter = (DateTimeConverter) ConvertUtils.lookup(Date.class);
        
        Tx.transform(a).to(b);
        //BeanUtilsBean.getInstance().getConvertUtils().register(false, true,-1);
        
        System.out.println(b.toString());
        
        b = new B();
        
        System.out.println(
        		Tx.transform(new KV("id","id value","name","中国人","curDate",null,"av","不相干的属性！")
        				).to(b).toString());
        
        env.clear();
        env.put("a",a);
        env.put("b",b);
        
//        System.out.println(AviatorEvaluator.execute("'b.v = '+ (b.v +a.v)", env)); 
        
        //Tx.transform(a).named("a").from("a.*").to(b);
        Thread thread = new Thread( new Runnable(){

			@Override
			public void run() {
				
				B b = new B();
		        
		        try {
					System.out.println(
							Tx.transform(new KV("id","id value","name","中国人","curDate",null,"av","不相干的属性！")
									).to(b).toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        	
        });
        thread.start();
        thread.join();
        
	}
	private void multhreadtest() throws Exception
	{
		B b = new B();
        
        System.out.println(
        		Tx.transform(new KV("id","id value","name","中国人","curDate",null,"av","不相干的属性！")
        				).to(b).toString());
	}
	
	

}
