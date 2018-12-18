package gyqx.txp.utils.sql;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.util.Assert;

import gyqx.txp.configuration.DbSources;

public class DbUtilsFactory 
{
	public static DbUtils newDbUtils(String dataSourceName) throws SQLException
	{
		Assert.notNull(DbSources.getCurrent(),"DbSources.getCurrent() == null");
		DataSource ds = DbSources.getCurrent().getDbSource(dataSourceName);
		return new DbUtils(ds);
	}

}
