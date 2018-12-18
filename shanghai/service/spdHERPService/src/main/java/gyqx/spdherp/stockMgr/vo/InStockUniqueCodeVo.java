package gyqx.spdherp.stockMgr.vo;

import gyqx.spdherp.po.InStockList;
import gyqx.spdherp.po.InStockUniqueCode;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class InStockUniqueCodeVo extends InStockUniqueCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7345301975026878561L;
	private String goodsName;
	private String bigBatchCode;
	public String getGoodsName() {
		return goodsName;
	}

	public String getBigBatchCode(InStockList isl, Integer inStockType) {
		SimpleDateFormat f = new SimpleDateFormat("yyMMdd");
		SimpleDateFormat f1 = new SimpleDateFormat("yyMMddhhmmss");
		String bigBatchCode = String.format("%s-%s-%s-%s-%s-%s-%s-%s-%s-%s-%s-%s", inStockType.toString(),this.getInPrice().toString(),isl.getBatchCode(),this.getGoodsBatchId(),f.format(isl.getExpdtEndDate()),
				isl.getSterilizationCode(),f.format(isl.getSterilizationEndDate()),f.format(isl.getSterilizationDate()),f1.format(this.getInTime()),isl.getMasterCode(),isl.getSecCode(),isl.getHibcCode());
		return bigBatchCode;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}
