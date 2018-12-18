package gyqx.spdherp.stockout.vo;

import common.db.meta.Title;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @Auther Liangwu
 * @Date 17-9-1 下午4:32
 */
public class GoodsNoIdUniqueVo {
	@Size(max = 500)
	@Column(name = "certificate_code")
    @Title("注册证")
	private String certificateCode;

	@Size(max = 36)
    @Column(name = "id")
    @Title("ID")
    private String id;

    @Size(max = 36)
    @Column(name = "hos_goods_id")
    @Title("医院产品ID")
    private String goodsId;
    /**
     * 批号
     */
    @Size(max = 36)
    @Column(name = "batch_no")
    @Title("批号")
    private String batchNo;
    /**
     * 批次
     */
    @Size(max = 36)
    @Column(name = "batch_id")
    @Title("批次")
    private String batchId;
    /**
     * 唯一码
     */
    @Size(max = 128)
    @Column(name = "unique_code")
    @Title("唯一码")
    private String uniqueCode;
    /**
     * 数量
     */
    @Column(name = "qty")
    @Title("数量")
    private BigDecimal qty;

    private BigDecimal price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
	
    public String getCertificateCode() {
		return certificateCode;
	}

	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}

}
