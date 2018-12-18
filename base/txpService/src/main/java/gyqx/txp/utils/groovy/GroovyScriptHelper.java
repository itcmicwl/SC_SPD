package gyqx.txp.utils.groovy;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.component.file.GenericFile;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.codehaus.groovy.control.CompilerConfiguration;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import gyqx.txp.utils.json.Json;

public class GroovyScriptHelper 
{
	private static CompilerConfiguration configuration;
	private static GroovyShell shell;
	private static int evalcount = 0;
	private static Map<String,Script> scriptCache ;
	static{
		configuration = new CompilerConfiguration();
		shell = new GroovyShell(configuration);		
		scriptCache = new HashMap<>();
	}
	public static void setShell(GroovyShell newShell)
	{
		shell = newShell;
	}
	public static synchronized Script parse(String content)
	{
		evalcount ++;
		clearCache();
		
		Script script = scriptCache.get(content);
		if(script == null){
			script = shell.parse(content);
			scriptCache.put(content, script);
		}
		return script;
	}
	
	public static void clearCache()
	{
		if(evalcount > 1000){
			forceClear();
		}
	}
	public static void forceClear()
	{
		scriptCache.clear();
		shell.resetLoadedClasses();
		evalcount =0;
	}
	
	public static Object eval(String content)
	{		
		Script script = parse(content);
		
		return script.run();
	}
	
	public static Object eval(String content,Map<String,Object> params)
	{
		Script script = parse(content);
		if(params != null)
			params.forEach((k,v)->{
				script.getBinding().setVariable(k, v);
			});	

		return script.run();
	}
	
	public static Object eval(Script script,Map<String,Object> params)
	{
		if(params != null)
			params.forEach((k,v)->{
				script.getBinding().setVariable(k, v);
			});	

		return script.run();
	}
	
	public static Object eval(File file,Map<String,Object> params) throws IOException
	{
		String script  = FileUtils.readFileToString(file, "utf8");
		
		return eval(script,params);
	}
	
	public static <T> T getObjectFromGenericFile(GenericFile gfile,Class<T> cls) throws Exception
	{
		return getObjectFromFile((File) gfile.getFile(),cls);
	}
	public static <T> T getObjectFromFile(File file,Class<T> cls) throws Exception
	{
		return getObject(FileUtils.readFileToString(file,"utf-8"),cls);
	}
	public static <T> T getObject(String groovyScript,Class<T> cls) throws Exception
	{
		Object obj = eval(groovyScript);
		//T target = cls.newInstance();
		//BeanUtils.copyProperties(target,obj);
		String json = Json.write(obj);
		T target = Json.read(json, cls);
		return target;
	}
	public <T> T getObject(File file,Class<T> cls) throws Exception
	{
		String script = FileUtils.readFileToString(file, "utf8");
		return getObject(script,cls);
	}

}
