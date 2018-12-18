package gyqx.txp.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import gyqx.txp.configuration.DbSources;
import gyqx.txp.utils.exception.ErrorMessager;
import gyqx.txp.utils.json.Json;
import gyqx.txp.utils.sql.DbUtils;

public class UserMessageUtils 
{
	private static final Logger logger = LoggerFactory.getLogger(UserMessageUtils.class);
	private String defaultDataSourceName;
	private DataSource dataSource;
	private static UserMessageUtils current = null;
	public UserMessageUtils(String defaultDataSourceName)
	{
		dataSource = DbSources.getCurrent().getDbSource(defaultDataSourceName);
		this.defaultDataSourceName = defaultDataSourceName;
		current = this;
	}
	
	public UserMessageUtils getCurrent()
	{
		return current;
	}
	
	public String writeMessage(String json) throws Exception
	{
		try{
			Map map = Json.read(json, Map.class);
			String message = (String)map.get("message");
			String title = (String)map.get("title");
			String projectCode = (String)map.get("projectCode");
			String url = (String)map.get("url");
			String writerId = (String)map.get("writerId");
			String orgId = (String)map.get("orgId");
			Object o = map.get("userIds");
			List<String> userIds = null;
			if(String[].class.equals(o.getClass()))
			{
				userIds = new ArrayList<>();
				String[] ids = (String[]) o;
				for(String id: ids)
				{
					userIds.add(id);
				}
			}else
				userIds = (List<String>)map.get("userIds");
			
			String messageId = UUID.randomUUID().toString().replaceAll("-","");
			
			String sql = "insert into sys_message (id,project_code,range_type,title,message,url,group_id,write_datetime,write_user_id,version) "
					+ " values(?,?,'1',?,?,?,?,?,?,0)";
			DbUtils db = new DbUtils(dataSource);
			db.beginTransaction();
			try{
				db.updateWithParams(sql,messageId,projectCode,title,message,url,orgId,new Date(),writerId);	
				if(!CollectionUtils.isEmpty(userIds))
				{
					String sqld = "";
					for(String userId:userIds)
					{
						if(DbSources.getCurrent().getDbType(defaultDataSourceName).equalsIgnoreCase("mysql"))
							sqld = "insert into sys_message_confirm (id,message_id,user_id,last_update_datetime,version) "
								+ "values( uuid() ,'%s' ,  ?, sysdate() , 0  )";
						else if(DbSources.getCurrent().getDbType(defaultDataSourceName).equalsIgnoreCase("oracle"))
							sqld = "insert into sys_message_confirm (id,message_id,user_id,last_update_datetime,version) "
									+ "values(sys_guid() ,'%s' ,?,  sysdate() ,0)";
						db.updateWithParams(String.format(sqld, messageId), userId);
					}
				}else if(StringUtils.hasText(orgId))
				{
					String sqld = "";
					if(DbSources.getCurrent().getDbType(defaultDataSourceName).equalsIgnoreCase("mysql"))
						sqld = "insert into sys_message_confirm (id,message_id,user_id,last_update_datetime,version) "
							+ "select uuid() as id,'%s' as message_id,  user_id, sysdate() as last_update_datetime,0 as version from sys_user_org uo where uo.org_id = ?";
					else if(DbSources.getCurrent().getDbType(defaultDataSourceName).equalsIgnoreCase("oracle")){
						sqld = "insert into sys_message_confirm (id,message_id,user_id,last_update_datetime,version) "
								+ "select sys_guid() as id,'%s' as message_id,  user_id,sysdate as last_update_datetime, 0 as version from sys_user_org uo where uo.org_id = ?";
					}
					db.updateWithParams(String.format(sqld, messageId), orgId);	
					
				}
				db.commit();
			}catch(Exception ex)
			{
				db.rollback();
				throw ex;
			}
		}catch(Exception exo)
		{
			logger.error(ErrorMessager.fromException(exo));
			//throw exo;
		}
		return json;
		
	}

}
