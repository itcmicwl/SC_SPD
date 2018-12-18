package gyqx.txp;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.camel.dataformat.zipfile.ZipFileDataFormat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyqx.txp.runtime.Starter;
import gyqx.txp.runtime.status.LoggerRedoWorker;

import java.io.IOException;
import java.sql.*;
import oracle.jdbc.*;
import oracle.jdbc.dcn.*;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;


public class MainTest 
{
	private static final Logger logger = LoggerFactory.getLogger("my.txp.test.redo.ccc.xx");
	@Test
	public void test() throws Exception
	{
		ZipFileDataFormat zipFile = new ZipFileDataFormat();
		//testLogger();
		
		Pattern pat = Pattern.compile("([a-zA-Z][a-zA-Z\\-\\.]*)\\s+(as\\s+((\\w+)))?");
		//Matcher mat = pat.matcher("a.b.c as a.bc");
		Matcher mat = pat.matcher("a-.b.c as abc");
		if(mat.matches())
		{
			for(int i = 0; i< mat.groupCount();i++)
			{
				System.out.println( "i=" + i+":" +mat.group(i));
			}
		}
		
		

		Starter.main("/txpConfig/txp.xml");
	}
	
	private void testLogger()
	{
		
//		for(int i = 0; i< 1000; i++)
//		{
//			logger.info("a:{};uuid:{};begin", i,UUID.randomUUID() );
//			logger.info("a:{};uuid:{};end", i,UUID.randomUUID() );
//		}
//		
		System.exit(0);
	}
	
	//static String URL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=192.168.1.250)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=orcl)))";
	static String URL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=192.168.1.250)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=orclpdb)))";
	OracleConnection getConn = null;
	PreparedStatement getPstmt = null;
	DatabaseChangeRegistration dcr = null;
	
	//@Test
	public void testOracle() throws IOException, SQLException
	{
		registerChangeNotification();	    
		System.in.read();
	}
	
	 private OracleConnection connect() throws SQLException 
	  {
		 OracleConnectionPoolDataSource ds = new OracleConnectionPoolDataSource();
		 
		 ds.setURL(URL);
		 ds.setUser("admin");
		 ds.setPassword("admins");		 
		 Properties prop1 = new Properties();
	     prop1.setProperty("MinLimit","5");
	     prop1.setProperty("MaxLimit","50");
	      
		 ds.setConnectionCacheProperties(prop1);
		 
		/* 
	    OracleConnection conn = null;
	    try{
	      OracleDriver dr = new OracleDriver();
	      String url = URL;
	      Properties prop = new Properties();
	      prop.setProperty("user","admin");
	      prop.setProperty("password","admins");
	      conn = (OracleConnection)dr.connect(url,prop);
	     // conn.setAutoCommit(false);
	    } catch( SQLException ex )
	    {
	    	ex.printStackTrace(); 
	    }
	    return conn;
	    */
		 return (OracleConnection) ds.getConnection();
	  }
	 void registerChangeNotification() throws SQLException
	  {
	    OracleConnection conn = null;
	    conn = connect();
	    if( conn == null ) return;
	    Properties prop = new Properties();

	    prop.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS,"true");
	    //prop.setProperty(OracleConnection.DCN_QUERY_CHANGE_NOTIFICATION,"true");
	    
	   // prop.setProperty(OracleConnection.DCN_NOTIFY_CHANGELAG,"true");
	    
	    try
	    {
	      dcr = conn.registerDatabaseChangeNotification(prop);
	      QCNGuiDemoDatabaseChangeListener list = new QCNGuiDemoDatabaseChangeListener(this);
	      dcr.addListener(list);
	      Statement stmt = conn.createStatement();
	      String query = "select * from sys_string ";//where id= '1'";
	      ((OracleStatement)stmt).setDatabaseChangeRegistration(dcr);
	      ResultSet rs = stmt.executeQuery(query);
	      while (rs.next())
	      {
	    	  System.out.println(rs.getString("id"));
	      }
	      rs.close();
	      stmt.close();
	    }
	    catch(SQLException ex)
	    {
	      // if an exception occurs, we need to close the registration in order
	      // to interrupt the thread otherwise it will be hanging around.
	      if(conn != null && dcr != null)
	      {
	        try{ conn.unregisterDatabaseChangeNotification(dcr);}
	        catch(SQLException ex2){ex2.printStackTrace();}
	      }
	      ex.printStackTrace();
	    }
	    finally
	    {
	      try
	      {
	        // Note that we close the connection!
	        conn.close();
	      }
	      catch(Exception innerex){ innerex.printStackTrace(); }
	    } 
	  }

	  /**
	   * Connects to the database and closes the registration.
	   */
	  void unregisterChangeNotification()
	  {
	    OracleConnection conn = null;
	    try { 
	      conn = connect();
	      conn.unregisterDatabaseChangeNotification(dcr);
	    }catch( Throwable t ) { t.printStackTrace();}
	    finally{try{ if( conn != null ) conn.close();}catch(SQLException e){} }

	  }

	  /**
	   * Retrieves the salary given the rowid and updates the progress bar.
	 * @throws SQLException 
	   */
	  void getUpdateForROWID( oracle.sql.ROWID rowid ) throws SQLException
	  {
	    int newValue = Integer.MIN_VALUE;
	    if( getConn == null ) getConn = connect();
	    String sql = "select sal from emp  where rowid = ?";
	    try{
	      if( getPstmt == null ) getPstmt = getConn.prepareStatement( sql );
	      ((OraclePreparedStatement)getPstmt).setROWID( 1, rowid );
	      ResultSet rst = getPstmt.executeQuery();
	      if( rst.next() )
	        newValue = rst.getInt(1);
	      rst.close();
	    } catch( SQLException ex ) { ex.printStackTrace(); }

	   
	  }
	  
	  void cleanup()
	  {
	    if (dcr != null) unregisterChangeNotification();
	    if( getPstmt != null){ try{ getPstmt.close();}catch(SQLException ex){}}
	    if( getConn != null){ try{ getConn.close();}catch(SQLException ex){}}
	   
	    System.exit(0);
	  }

}

class QCNGuiDemoDatabaseChangeListener implements DatabaseChangeListener
{
	MainTest demo = null;
  QCNGuiDemoDatabaseChangeListener( MainTest demo )
  {
	  super();
	  this.demo = demo;
  }

  public void onDatabaseChangeNotification(DatabaseChangeEvent e)
  {
    //QueryChangeDescription [] changes = e.getQueryChangeDescription();
    //QueryChangeDescription change = changes[0];
	
    TableChangeDescription [] tableChanges = e.getTableChangeDescription(); //change.getTableChangeDescription();
    for(TableChangeDescription tableChange:tableChanges)
    {
    	System.out.println("------table: " + tableChange.getTableName() + " --------------");
	    RowChangeDescription[] rowChanges = tableChange.getRowChangeDescription();
	    for(RowChangeDescription rowChange: rowChanges)
	    {
	    	oracle.sql.ROWID rowid = rowChange.getRowid();
	    	System.out.println(String.format("\tchange method:%s ;rowid:%s",rowChange.getRowOperation(), rowid.toString()));
	    }
    }
   // demo.getUpdateForROWID( rowid );
  }
}  
