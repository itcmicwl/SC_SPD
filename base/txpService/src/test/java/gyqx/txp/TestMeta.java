package gyqx.txp;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gyqx.txp.configuration.DbSources;
import gyqx.txp.db.meta.TableMeta;
import gyqx.txp.db.meta.mysql.MysqlDbMetaInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:gyqx/txp/txp-junit.xml" })
public class TestMeta 
{
	@Resource(name="dataSource")
	private DataSource dataSource;
	@Test
	public void test() throws SQLException
	{		
		
		BasicDataSource ds = (BasicDataSource) DbSources.getCurrent().getDbSource("dataSource"); 

		if(ds.getDriverClassName().indexOf("mysql") >=0)
		{
			System.out.println("mysql");
		}
		Connection cnn =  ds.getConnection();
		
		MysqlDbMetaInfo mi = new MysqlDbMetaInfo(ds);
		
		for(TableMeta tm: mi.getTables())
		{
			System.out.println(tm.getName());
		}
	}

}
