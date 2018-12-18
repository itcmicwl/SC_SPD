package gyqx.txp.runtime;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyqx.txp.runtime.status.TaskWorkStatusUtils;
import gyqx.txp.utils.exception.ErrorMessager;

public class CenterRuntime 
{
	private static final Logger logger = LoggerFactory.getLogger(CenterRuntime.class);
	private static CenterRuntime instance;
	public static CenterRuntime current()
	{
		return instance;
	}
	private TaskWorkStatusUtils taskWorkStatusUtils;	

	public void init() throws IOException
	{
		try{
		instance = this;
		taskWorkStatusUtils.init();
		}catch(Exception ex)
		{
			String error = ErrorMessager.fromException(ex);
			logger.error(error);
		}
	}
	
	public TaskWorkStatusUtils getTaskWorkStatusUtils() {
		return taskWorkStatusUtils;
	}

	public void setTaskWorkStatusUtils(TaskWorkStatusUtils taskWorkStatusUtils) {
		this.taskWorkStatusUtils = taskWorkStatusUtils;
	}

}
