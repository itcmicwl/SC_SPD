package gyqx.txp.utils.comparision;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

public class DataComparator implements java.util.Comparator
{
	private CompilerConfiguration configuration;
	private String content;
	private Script script;
	Binding binding = new Binding();  
	public DataComparator(CompilerConfiguration configuration,String content)
	{
		binding.setVariable("left", null);  		
		binding.setVariable("right", null);  
	    
	    GroovyShell shell = new GroovyShell(binding,configuration);
	    script = shell.parse(content);
	}
	@Override
	public int compare(Object left, Object right) {
		binding.setVariable("left", left);
		binding.setVariable("right", right);
		
		return (int)script.run();
	}

}
