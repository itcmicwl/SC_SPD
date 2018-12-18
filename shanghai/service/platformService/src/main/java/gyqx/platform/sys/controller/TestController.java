package gyqx.platform.sys.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.utils.UtilsContext;
import common.validate.ValidateErrors;
import common.web.AjaxResult;
import common.web.BaseController;

@Controller
@RequestMapping(value="sys/test")
public class TestController extends BaseController
{
	private static final Log logger = LogFactory.getLog(TestController.class);
	
	public static class SomeClassSimple
	{
		@NotEmpty(message = "{user.name.null}")  
	    @Length(min = 5, max = 20, message = "{user.name.length.illegal}")  
		private String val;
		private Date current;
		private BigDecimal sale;
		private int version;

		public String getVal() {
			return val;
		}

		public void setVal(String val) {
			this.val = val;
		}

		public Date getCurrent() {
			return current;
		}

		public void setCurrent(Date current) {
			this.current = current;
		}

		public BigDecimal getSale() {
			return sale;
		}

		public void setSale(BigDecimal sale) {
			this.sale = sale;
		}

		public int getVersion() {
			return version;
		}

		public void setVersion(int version) {
			this.version = version;
		}
	}
	public static class SomeClass<T>
	{
		private String name;
		private String id;
		@Valid
		private T data;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public T getData() {
			return data;
		}
		public void setData(T data) {
			this.data = data;
		}
	}
	
	@Resource
	private UtilsContext utilsContext;
	@RequestMapping(value="getValue")
	@ResponseBody
	public AjaxResult getValue(@RequestBody @Valid SomeClass<SomeClassSimple> a,Errors error)
	{
		AjaxResult result = new AjaxResult();
		
		if(error.hasErrors()) {
			ValidateErrors verrors =  utilsContext.getValidateUtil().from(error);
			result.setValidateErrors(verrors);
		}
		result.setData(a);
		return result;
	}
	
	@RequestMapping(value="getResult")
	@ResponseBody
	public AjaxResult getResult()
	{
		AjaxResult result = new AjaxResult();
		result.setMsg("getResult中国人");
		return result;
	}
	@RequestMapping(value="postResult")
	@ResponseBody
	public AjaxResult postResult(@RequestBody AjaxResult result) throws Exception
	{
		result.setMsg("postResult中国人：" + result.getMsg());
		if(result.getMsg().length() >0)
			throw new Exception(result.getMsg());
		return result;
	}
	
	@RequestMapping(value="getMapValue")
	@ResponseBody
	public AjaxResult getMapValue(HttpServletRequest  request,HttpServletResponse response,
			@RequestBody Map<String,String> map)
	{
		AjaxResult result = new AjaxResult();
		for( Entry<String,String[]> item: request.getParameterMap().entrySet())
		{
			System.out.println(item.getKey());
			System.out.println(item.getValue());
		}
		result.setData(map);
		return result;
	}
	@RequestMapping(value="getMulValue")
	@ResponseBody
	public AjaxResult getMulValue(HttpServletRequest  request,HttpServletResponse response,
			@RequestBody @Valid Map<String, SomeClassSimple> scs1 ,Errors error1)
	{
		AjaxResult result = new AjaxResult();
		if(error1.hasErrors()) {
			ValidateErrors verrors =  utilsContext.getValidateUtil().from(error1);
			result.setValidateErrors(verrors);
		}
		
		result.setData(scs1);
		return result;
	}
	
	@RequestMapping(value="getValueWithValidate")
	@ResponseBody
	public AjaxResult getValueWithValidate(HttpServletRequest  request,
			@RequestBody @Valid SomeClassSimple scs ,Errors error)
	{
		AjaxResult result = new AjaxResult();
		for( Entry<String,String[]> item: request.getParameterMap().entrySet())
		{
			System.out.println(item.getKey());
			System.out.println(item.getValue());
		}
		
		if(error.hasErrors()) {
			ValidateErrors verrors =  utilsContext.getValidateUtil().from(error);
			result.setValidateErrors(verrors);
		}
		
		result.setData(scs);
		return result;
	}
	@RequestMapping(value="getDynamicBean")
	@ResponseBody
	public AjaxResult getDynamicBean() throws IllegalAccessException, InstantiationException
	{
		DynaProperty properties[] = new DynaProperty[] {
			      new DynaProperty("title", String.class),
			      new DynaProperty("dateOfRelease", Date.class),
			      new DynaProperty("keywords", String[].class),
			      new DynaProperty("genre", Map.class),
			      new DynaProperty("actors", List.class),
			      new DynaProperty("director", DynaBean.class)
			    };

			    // next using the properties define the class
		DynaClass movieClass = new BasicDynaClass("movie", null, properties);
		
		AjaxResult result = new AjaxResult();
		
		// now, with the class, create a new instance
	    DynaBean movieBean = movieClass.newInstance();
		// set its properties
	    movieBean.set("title", "The Italian Job");
	    movieBean.set("dateOfRelease",
	      new GregorianCalendar(1969, 0, 1).getTime());
	    movieBean.set("keywords", new String[] {"Italy", "Bank Robbery"});

	    Map genre = new HashMap();
	    genre.put("THR", "Thriller");

	    movieBean.set("genre", genre);
	    movieBean.set("genre", "ACT", "Action");
	    DynaBean director = createPersonBean();
	    director.set("name", "Peter Collinson");
	    director.set("gender", new Integer(1));

	    movieBean.set("director", director);
	    
	    result.setData(movieBean);
	    
	    return result;
			    
	}
	private static DynaBean createPersonBean() {
	    DynaBean person = new LazyDynaBean();
	    return person;
	  }

}
