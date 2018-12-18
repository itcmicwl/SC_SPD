package gyqx.spdherp.stockMgr.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import gyqx.spdhdi.orderMgr.vo.DistrBillListVo;
import gyqx.spdherp.po.InStockList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InStockListVo extends InStockList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6557360787597881439L;
	private String outBillId;
	private BigDecimal inPrice;

	private List<InStockBatchVo> lstBatch;
	private List<InStockUniqueCodeVo> lstUniqueCode;
	/**
	 * 储运条件
	 */
	private String stCondition;
	/**
	 * 收货日期
	 */
	private Date receiveDate;
	/**
	 * 到货温度
	 */
	private BigDecimal receiveTemprature;
	
	public BigDecimal getInPrice() {
		return inPrice;
	}
	public void setInPrice(BigDecimal inPrice) {
		this.inPrice = inPrice;
	}
	public List<InStockBatchVo> getLstBatch() {
		return lstBatch;
	}
	public void setLstBatch(List<InStockBatchVo> lstBatch) {
		this.lstBatch = lstBatch;
	}
	public List<InStockUniqueCodeVo> getLstUniqueCode() {
		return lstUniqueCode;
	}
	public void setLstUniqueCode(List<InStockUniqueCodeVo> lstUniqueCode) {
		this.lstUniqueCode = lstUniqueCode;
	}

	public String getOutBillId() {
		return outBillId;
	}

	public void setOutBillId(String outBillId) {
		this.outBillId = outBillId;
	}

	public String getStCondition() {
		return stCondition;
	}
	public void setStCondition(String stCondition) {
		this.stCondition = stCondition;
	}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public BigDecimal getReceiveTemprature() {
        return receiveTemprature;
    }

    public void setReceiveTemprature(BigDecimal receiveTemprature) {
        this.receiveTemprature = receiveTemprature;
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (obj instanceof DistrBillListVo) {
			DistrBillListVo d = (DistrBillListVo) obj;
			return d.getRowNumber() == this.getOutBillRow();
		}
		return super.equals(obj);
	}
}