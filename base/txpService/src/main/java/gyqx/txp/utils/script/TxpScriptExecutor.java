package gyqx.txp.utils.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyqx.txp.utils.exception.ErrorMessager;
import gyqx.txp.utils.groovy.GroovyScriptHelper;
import gyqx.txp.utils.sql.DbUtils;

public class TxpScriptExecutor 
{
	private static final Logger logger = LoggerFactory.getLogger(TxpScriptExecutor.class);
	public static Object execute(ScriptContext ctx,TxpScript script,Object body)
	{
		Object result = null;
		try{
			switch(script.getType())
			{
			case cmd:
				result = executeCmd(script.getScript(),body);
				break;
			case dos:
				result = executeDos(script.getScript());
				break;
			case sql:
				DbUtils db = (DbUtils) ctx.getParams().get("db");
				String sql = script.getScript().trim();
				Pattern pat = Pattern.compile("^\\s*select", Pattern.MULTILINE|Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
				Matcher mat = pat.matcher(sql);
				if(mat.find())
				{
					result = executeSelectSql(db,sql);
				}else{
					result = executeSql(db,sql);
				}
				break;
			case groovy:
				result = executeGroovy(ctx,script.getScript());
				break;
			}
		}catch(Exception ex)
		{
			result = ErrorMessager.fromException(ex);
            logger.error(result.toString());
		}
		return result;
	}
	
	private static Object executeGroovy(ScriptContext ctx,String groovyScript)
	{
		return GroovyScriptHelper.eval(groovyScript, ctx.getParams());
	}
	private static Object executeSql(DbUtils db ,String sql)
	{
		Object result = null;
		try{
			db.beginTransaction();
			result = db.update(sql);
			db.commit();
		}catch(Exception ex)
		{
			db.rollback();
			result = ErrorMessager.fromException(ex);
            logger.error(result.toString());
			
		}
		return result;
	}
	private static Object executeSelectSql(DbUtils db ,String sql)
	{
		Object result = null;
		try{
			result = db.query(sql); 
		}catch(Exception ex)
		{
			result = ErrorMessager.fromException(ex);
            logger.error(result.toString());
		}
		return result;
	}
	private static Object executeCmd(String cmd,Object body)
	{
		String[] cmds = cmd.trim().split("\\r?\\n");
		if(cmds == null || cmds.length == 0) return null;
		cmd = cmds[0];
		String result="";
		try { 
			if(cmd.equalsIgnoreCase("run"))
			{
				if(!StringUtils.hasText(cmds[1]))
					return new Exception("cmds[1] is empty!");
				File file = new File(cmds[1]);
				if(file.exists()){
					File dir = file.getParentFile();
					Runtime.getRuntime().exec(cmds[1].trim(),null,dir);
					return cmds[1] + ":ok";
				}else
					throw new Exception("file not exists!");
			}else if(cmd.equalsIgnoreCase("createfile"))
			{
				if(cmds.length < 2)
					throw new Exception("no file name < 1");
				String filename = cmds[1].trim();
				File file = new File(filename);

				byte[] data = (byte[]) body;
				FileUtils.writeByteArrayToFile(file, data);
				return filename +":ok";
			}
		} catch (Exception e) {  
	            result = ErrorMessager.fromException(e);
	            logger.error(result);
	    }   
		return result;
		
	}
	public static Object executeDos(String cmd)
	{
		//sc start "服务名"
		String commandStr= cmd;//"ping baidu.com ";"cmd /C dir" "cmd /C ipconfig -all"
		BufferedReader br = null;  
		String result="";
		String [] cmds = commandStr.trim().split("\\s");
		
        try { 
            Process p = Runtime.getRuntime().exec(cmds);  

            br = new BufferedReader(new InputStreamReader(p.getInputStream(),"gb2312"));  
            String line = null;  
            StringBuilder sb = new StringBuilder();  
            while ((line = br.readLine()) != null) {  
                sb.append(line + "\n");  
                logger.info(line);
            }  
            result = sb.toString();
        } catch (Exception e) {  
            result = ErrorMessager.fromException(e);
            logger.error(result);
        }   
        finally  
        {  
            if (br != null)  
            {  
                try {  
                    br.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return result;
	}

}
