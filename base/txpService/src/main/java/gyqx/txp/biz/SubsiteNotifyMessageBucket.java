package gyqx.txp.biz;

public class SubsiteNotifyMessageBucket extends NotifyMessageBucketBase
{
	private static SubsiteNotifyMessageBucket instance = new SubsiteNotifyMessageBucket();
	public static SubsiteNotifyMessageBucket current()
	{
		return instance;
	}
}
