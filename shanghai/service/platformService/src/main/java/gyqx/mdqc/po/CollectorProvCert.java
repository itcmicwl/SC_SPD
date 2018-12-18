
package gyqx.mdqc.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import common.db.IBean;
import common.db.meta.Title;

/**
 * 配送商对应合作商信息
 */
@Table(name = "collector_prov_cert")
public class CollectorProvCert implements IBean {
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
	 * 集配商ID
	 */
	@Size(max = 36)
	@JoinColumn(name = "collector_id", table = "bas_company_info", referencedColumnName = "id")
	@Title("集配商ID")
	private String collectorId;
	/**
	 * 合作商ID
	 */
	@Size(max = 36)
	@Column(name = "prov_id")
	@Title("合作商ID")
	private String provId;
	/**
	 * 标志
	 */
	@Size(max = 1)
	@Column(name = "flag")
	@Title("标志")
	private String flag;
	/**
	 * 备注信息
	 */
	@Size(max = 128)
	@Column(name = "remark")
	@Title("备注信息")
	private String remark;
	/**
	 * 建档时间
	 */
	@Column(name = "fill_date")
	@Title("建档时间")
	private java.util.Date fillDate;
	/**
	 * 最后更新时间
	 */
	@Column(name = "last_update_datetime")
	@Title("最后更新时间")
	private java.util.Date lastUpdateDatetime;
	/**
	 * 操作员ID
	 */
	@Size(max = 36)
	@Column(name = "uxid")
	@Title("操作员ID")
	private String uxid;
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

	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}

	public String getProvId() {
		return provId;
	}

	public void setProvId(String provId) {
		this.provId = provId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public java.util.Date getFillDate() {
		return fillDate;
	}

	public void setFillDate(java.util.Date fillDate) {
		this.fillDate = fillDate;
	}

	public java.util.Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateDatetime(java.util.Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	public String getUxid() {
		return uxid;
	}

	public void setUxid(String uxid) {
		this.uxid = uxid;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "CollectorProvCert [id=" + id + ", collectorId=" + collectorId + ", provId=" + provId + ", flag=" + flag
				+ ", remark=" + remark + ", fillDate=" + fillDate + ", lastUpdateDatetime=" + lastUpdateDatetime
				+ ", uxid=" + uxid + ", version=" + version + "]";
	}

}