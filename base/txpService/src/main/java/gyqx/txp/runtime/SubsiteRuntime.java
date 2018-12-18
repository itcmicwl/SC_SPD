package gyqx.txp.runtime;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyqx.txp.runtime.status.TaskWorkStatusUtils;
import gyqx.txp.utils.exception.ErrorMessager;

public class SubsiteRuntime 
{
	private static final Logger logger = LoggerFactory.getLogger(SubsiteRuntime.class);
	private static SubsiteRuntime instance;
	public static SubsiteRuntime current()
	{
		return instance;
	}
	private TaskWorkStatusUtils taskWorkStatusUtils;	

	public void init() throws IOException
	{
		instance = this;
		try{
			taskWorkStatusUtils.init();
		}catch(Exception ex)
		{
			String error = ErrorMessager.fromException(ex);
			logger.error(error);
			throw ex;
		}
	}
	
	public TaskWorkStatusUtils getTaskWorkStatusUtils() {
		return taskWorkStatusUtils;
	}

	public void setTaskWorkStatusUtils(TaskWorkStatusUtils taskWorkStatusUtils) {
		this.taskWorkStatusUtils = taskWorkStatusUtils;
	}

}
