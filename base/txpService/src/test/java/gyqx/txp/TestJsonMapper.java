package gyqx.txp;



import org.junit.Test;
import gyqx.txp.message.MessageBodyKind;
import gyqx.txp.message.TxpMessage;
import gyqx.txp.utils.json.Json;

public class TestJsonMapper 
{
	
	@Test
	public void test() throws Exception
	{


		
		TxpMessage message = new TxpMessage();

		
		message.setKind(MessageBodyKind.CallService );
		message.setFromId("h001");
		message.setTaskName("taskName001");
		
		String s = Json.write(message,true,true);
		
		System.out.println(s);
		
		TxpMessage message0 = (TxpMessage) Json.read(s, Object.class,true);
		
		s = Json.write(message,false,true);
		
		System.out.println(s);
		
		s = Json.write(message,true,false);
		
		System.out.println(s);
		
		s = Json.write(message,false,false);
		
		System.out.println(s);
		
	}

}
