package gyqx.txp.utils.script;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class TxpScript 
{
	public enum ScriptType{
		cmd,dos,sql,groovy
	}	
	private ScriptType type;	
	private String script;
	
	public static TxpScript parse(String s)
	{
		if(!StringUtils.hasText(s))
			return null;
		Pattern pat = Pattern.compile("^#<\\s*(.+)\\s*>\\r?\\n(.+)\\s*$",Pattern.MULTILINE|Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher mat = pat.matcher(s);
		String tp = "cmd";
		String cmd = "";
		if(mat.find())
		{
			tp = mat.group(1);
			cmd = mat.group(2);
		}else{
			cmd = s;
		}
		if(!StringUtils.hasText(cmd))
			return null;
		
		ScriptType sy = ScriptType.valueOf(tp);
		if(sy == null)
			return null;
		
		TxpScript tscript = new TxpScript();
		tscript.setType(sy);
		tscript.setScript(cmd);
		return tscript;
		
	}

	public ScriptType getType() {
		return type;
	}

	public void setType(ScriptType type) {
		this.type = type;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public String toString() {
		return "TxpScript [type=" + type + ", script=" + script + "]";
	}

}
