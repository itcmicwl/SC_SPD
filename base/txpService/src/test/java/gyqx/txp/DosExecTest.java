package gyqx.txp;

import org.junit.Test;

import gyqx.txp.utils.script.TxpScriptExecutor;

public class DosExecTest 
{
	@Test
	public void test()
	{
		//String cmd = "cmd /C ipconfig -all";//cmd.exe", "/C", "ipconfig
		
		String cmd = "netstat -anp TCP"; //"sc query \"mysql5.7\"";//"cmd /C dir";
		Object ret = TxpScriptExecutor.executeDos(cmd);
	}

}
