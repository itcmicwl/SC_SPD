package gyqx.txp.runtime.status;

import gyqx.txp.message.TaskNotifyMessage;

public interface IRedoCallback 
{
	void redoTaskWorkStatus(TaskWorkStatus twStatus);
	void redoTableTxpStatus(TableTxpStatus ttStatus);
	void redoTaskNotifyMessage(TaskNotifyMessage ttStatus);

}
