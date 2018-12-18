
package gyqx.spdhdi.baseData.vo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 生产厂商证件信息
*/
@Table(name="bas_mfrs_certificate")
public class BasMfrsCertificateVo
{
	/**
	 * ID
	*/
	@Id
	@Size(max=36)
	@Column(name="id")
	private String id ;
	/**
	 * 公司/医院ID
	*/
	@Size(max=36)
	@JoinColumn(name="mfrs_id",table="bas_mfrs_info",referencedColumnName="id")
	private String mfrsId ;
	/**
	 * 证照类型
	*/
	@Size(max=5)
	@Column(name="cert_kind")
	private String certKind ;
	/**
	 * 证件编号
	*/
	@Size(max=128)
	@Column(name="cert_code")
	private String certCode ;
    /**
     * 用于验证修改时是否重复问题
     */
    @Size(max = 128)
    private String oldCertCode;

    public String getOldCertCode() {
        return oldCertCode;
    }

    public void setOldCertCode(String oldCertCode) {
        this.oldCertCode = oldCertCode;
    }

	/**
	 * 有效开始日期
	*/
	@Column(name="expdt_begin_date")
//    @JsonFormat(pattern = "yyyy-MM-dd"/*, locale="zh", timezone="GMT+8"*/)
    private Date expdtBeginDate ;
	/**
	 * 有效结束日期
	*/
	@Column(name="expdt_end_date")
//    @JsonFormat(pattern = "yyyy-MM-dd"/*, locale="zh", timezone="GMT+8"*/)
    private Date expdtEndDate ;
	/**
	 * 是否长期有效
	*/
	@Size(max=1)
	@Column(name="is_validing")
	private String isValiding ;
	/**
	 * 数据版本
	 */
	@Column(name="version")
	private int version ;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getId ()
	{
		return id ;
	}
	
	public void setId (String id )
	{
		this.id = id;
	}
	public String getMfrsId ()
	{
		return mfrsId ;
	}
	
	public void setMfrsId (String mfrsId )
	{
		this.mfrsId = mfrsId;
	}
	public String getCertKind ()
	{
		return certKind ;
	}
	
	public void setCertKind (String certKind )
	{
		this.certKind = certKind;
	}
	public String getCertCode ()
	{
		return certCode ;
	}
	
	public void setCertCode (String certCode )
	{
		this.certCode = certCode;
	}
	public Date getExpdtBeginDate ()
	{
		return expdtBeginDate ;
	}
	
	public void setExpdtBeginDate (Date expdtBeginDate )
	{
		this.expdtBeginDate = expdtBeginDate;
	}
	public Date getExpdtEndDate ()
	{
		return expdtEndDate ;
	}
	
	public void setExpdtEndDate (Date expdtEndDate )
	{
		this.expdtEndDate = expdtEndDate;
	}
	public String getIsValiding ()
	{
		return isValiding ;
	}
	
	public void setIsValiding (String isValiding )
	{
		this.isValiding = isValiding;
	}

	@Override
	public String toString() {
		return "BasMfrsCertificate [" +" id=" +  id   +", mfrsId=" +  mfrsId   +", certKind=" +  certKind   +", certCode=" +  certCode   +", expdtBeginDate=" +  expdtBeginDate   +", expdtEndDate=" +  expdtEndDate   +", isValiding=" +  isValiding
			 + "]";
	}

}