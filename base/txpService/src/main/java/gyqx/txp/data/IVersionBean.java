package gyqx.txp.data;

import java.io.Serializable;

public interface IVersionBean extends Serializable
{
	public static final String default_version_name = "version";
	public static final String old_version_field_name="oldVersionValue";
	int getVersion();
	void setVersion(int version);

}
