package gyqx.txp.utils.notify;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

import gyqx.txp.configuration.TxpConfigBase;
import gyqx.txp.message.txp.TxpSqlNotify;
import gyqx.txp.utils.json.Json;
import gyqx.txp.utils.mq.JMSSender;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.jdbc.dcn.TableChangeDescription;

public class OracleDatabaseChangeListener implements DatabaseChangeListener
{
	private static final Logger logger = LoggerFactory.getLogger(OracleDatabaseChangeListener.class);
	
	public OracleDatabaseChangeListener(DataSource dataSource,TxpConfigBase config,
			JMSSender jmsSender,
			String destinationName,String ip,int port) throws SQLException
	{
		this.dataSource = dataSource;
		this.config = config;
		this.jmsSender = jmsSender;
		if(StringUtils.hasText(destinationName))
			this.destinationName = destinationName;
		this.ip = ip;
		this.port = port;
	}
	OracleConnection cnn;
	DataSource dataSource;
	TxpConfigBase config;
	JMSSender jmsSender = null;
	DatabaseChangeRegistration dcr = null;
	String destinationName = "sql.table.changed";
	String ip;
	int port;
	public void init() throws SQLException
	{
		cnn = (OracleConnection) dataSource.getConnection();
		
		Properties prop = new Properties();

	    prop.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS,"true");
	   // prop.setProperty(OracleConnection.NTF_TIMEOUT, "3600");
	    if(StringUtils.hasText(ip))
	    	prop.setProperty(OracleConnection.NTF_LOCAL_HOST,ip);
	    if(port >0)
	    	prop.setProperty(OracleConnection.NTF_LOCAL_TCP_PORT, "" + port);
	    
	    dcr = cnn.registerDatabaseChangeNotification(prop);
	    dcr.addListener(this);
	    Statement stmt = cnn.createStatement();
	    ((OracleStatement)stmt).setDatabaseChangeRegistration(dcr);
	    for(String tabName:config.getAllTableNames())
	    {
	    	logger.info("通知表：{}",tabName);
	    	stmt.executeQuery(String.format("select count(*) from %s where 1<>1",tabName));
	    }
	    stmt.close();
	}
	public void close() throws SQLException
	{
		if(dcr != null)
			dcr.removeListener(this);
		if(cnn != null)
			cnn.close();
	}

	@Override
	public void onDatabaseChangeNotification(DatabaseChangeEvent evt) 
	{
		TableChangeDescription [] tableChanges = evt.getTableChangeDescription(); //change.getTableChangeDescription();
		if(tableChanges == null){
			logger.error("tableChanges == null");
			return;
		}
	    for(TableChangeDescription tableChange:tableChanges)
	    {
	    	try {
		    	
		    	TxpSqlNotify notify = new TxpSqlNotify();
		    	notify.setCmd("");
		    	notify.setMethod("");
		    	String tableName = tableChange.getTableName();
		    	logger.info("------table:{} changed!-----",tableName);
		    	if(tableName.indexOf(".")<=0)
		    		continue;
		    	String[] ss = tableName.split("\\.");
		    	if(ss.length != 2)
		    		continue;
		    	notify.setTableName(ss[1]);
		    	String message="";
		    	
				message = Json.write(notify);
				jmsSender.send(destinationName, message, null);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.toString());
			}
	    }
	}
}
