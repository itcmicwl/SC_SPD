package gyqx.platform.permission.vo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import common.db.meta.Title;

public class CompanyInfo 
{
	@Id
	@Size(max=36)
	@Column(name="id")
	@Title("ID")
	private String id ;
	/**
	 * 公司/医院编号
	*/
	@Size(max=36)
	@Column(name="code")
	@Title("公司/医院编号")
	private String code ;
	/**
	 * 公司/医院名称
	*/
	@Size(max=300)
	@Column(name="cname")
	@Title("公司/医院名称")
	private String cname ;
	/**
	 * 公司/医院别名
	*/
	@Size(max=36)
	@Column(name="another_name")
	@Title("公司/医院别名")
	private String anotherName ;
	/**
	 * 拼音码
	*/
	@Size(max=36)
	@Column(name="short_pinyin")
	@Title("拼音码")
	private String shortPinyin ;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getAnotherName() {
		return anotherName;
	}
	public void setAnotherName(String anotherName) {
		this.anotherName = anotherName;
	}
	public String getShortPinyin() {
		return shortPinyin;
	}
	public void setShortPinyin(String shortPinyin) {
		this.shortPinyin = shortPinyin;
	}

}
