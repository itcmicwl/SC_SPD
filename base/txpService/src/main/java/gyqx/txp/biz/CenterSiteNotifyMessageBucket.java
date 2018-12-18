package gyqx.txp.biz;

public class CenterSiteNotifyMessageBucket extends NotifyMessageBucketBase
{
	private static CenterSiteNotifyMessageBucket instance = new CenterSiteNotifyMessageBucket();
	public static CenterSiteNotifyMessageBucket current()
	{
		return instance;
	}
}
