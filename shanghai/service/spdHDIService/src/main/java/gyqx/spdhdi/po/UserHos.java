
package gyqx.spdhdi.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import common.db.IBean;
import common.db.meta.Title;

/**
 * 驻点人员医院
 */
@Table(name = "user_hos")
public class UserHos implements IBean {
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	@Id
	@Size(max = 36)
	@Column(name = "id")
	@Title("ID")
	private String id;
	/**
	 * 医院ID
	 */
	@Size(max = 36)
	@JoinColumn(name = "hos_id")
	@Title("医院ID")
	private String hosId;
	/**
	 * 供应商ID
	 */
	@Size(max = 36)
	@JoinColumn(name = "corp_id")
	@Title("医院ID")
	private String corpId;
	/**
	 * 用户ID
	 */
	@Size(max = 36)
	@JoinColumn(name = "user_id")
	@Title("医院ID")
	private String userId;
	/**
	 * 备注
	 */
	@Size(max = 36)
	@JoinColumn(name = "remark")
	@Title("医院ID")
	private String remark;

	/**
	 * 最后更新时间
	 */
	@Column(name = "last_update_datetime")
	@Title("最后更新时间")
	private java.util.Date lastUpdateDatetime;

	/**
	 * 数据版本
	 */
	@Column(name = "version")
	@Title("数据版本")
	private int version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public java.util.Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateDatetime(java.util.Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}