package gyqx.txp.utils.groovy;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.language.groovy.GroovyShellFactory;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import gyqx.txp.configuration.TxpClientConfig;

public class CustomGroovyShellFactory implements GroovyShellFactory 
{	
	private List<String> imports;	
 
	public GroovyShell createGroovyShell(Exchange exchange) {
		ImportCustomizer importCustomizer = new ImportCustomizer();
		for(String s: imports)
			importCustomizer.addImports(s); //"gyqx.txp.utils.DbUtils");
		
		CompilerConfiguration configuration = new CompilerConfiguration();
		configuration.addCompilationCustomizers(importCustomizer);		
		
		Binding binding = new Binding();  
		
		return new GroovyShell(binding,configuration);
	}


	public List<String> getImports() {
		return imports;
	}

	public void setImports(List<String> imports) {
		this.imports = imports;
	}
	 
}