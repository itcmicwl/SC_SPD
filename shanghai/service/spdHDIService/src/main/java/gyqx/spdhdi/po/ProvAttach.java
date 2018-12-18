package gyqx.spdhdi.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import common.db.IBean;
import common.db.meta.Title;

@Table(name = "prov_attach")
public class ProvAttach implements IBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Id
	@Column(name="id")
	@Title("ID")
	private String id;
	
	/**
	 * 医院ID
	 */
	@Column(name="hos_id")
	@Title("医院ID")
	private String hosId;
	
	/**
	 * 供应商ID
	 */
	@Column(name="prov_id")
	@Title("供应商ID")
	private String provId;
	
	/**
	 * 标题
	 */
	@Column(name="title")
	@Title("标题")
	private String title;

	/**
	 * 说明
	 */
	@Column(name="content")
	@Title("说明")
	private String content;
	
	/**
	 * 路径
	 */
	@Column(name="file_path")
	@Title("路径")
	private String filePath;
	
	/**
	 * 文件类型
	 */
	@Column(name="attach_type")
	@Title("文件类型")
	private String attachType;
	
	/**
	 * 文件后缀
	 */
	@Column(name="file_ext")
	@Title("文件后缀")
	private String fileExt;
	
	/**
	 * 文件大小
	 */
	@Column(name="file_size")
	@Title("文件大小")
	private long fileSize;
	
	/**
	 * 是否删除,0：删除;1:启用
	 */
	@Column(name="flag")
	@Title("是否删除")
	private String flag;
	
	/**
	 * 录入人
	 */
	@Column(name="fill_user")
	@Title("录入人")
	private String fillUser;
	
	/**
	 * 最后更新时间
	 */
	@Column(name="last_update_datetime")
	@Title("最后更新时间")
	private Date lastUpdateDatetime;
	
	/**
	 * 数据版本
	 */
	@Column(name="version")
	@Title("数据版本")
	private int version;
	
	/**
	 * 文档类型
	 */
	@Column(name="type")
	@Title("文档类型")
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public String getProvId() {
		return provId;
	}

	public void setProvId(String provId) {
		this.provId = provId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getAttachType() {
		return attachType;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFillUser() {
		return fillUser;
	}

	public void setFillUser(String fillUser) {
		this.fillUser = fillUser;
	}

	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		return "ProvAttach [" +" id=" +  id   +", hosId=" +  hosId   +", provId=" +  provId   +", title=" +  title   +", content=" +  content   +", filePath=" +  filePath   +", fileExt=" +  fileExt   +", fileSize=" +  fileSize   +", flag=" +  flag   +", attachType=" +  attachType   +", flag=" +  flag   +", fillUser=" +  fillUser  +", lastUpdateDatetime=" +  lastUpdateDatetime   +", version=" +  version   
			 + "]";
	}
	
}
