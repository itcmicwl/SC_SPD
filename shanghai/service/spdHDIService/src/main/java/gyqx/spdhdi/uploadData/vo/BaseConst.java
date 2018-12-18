package gyqx.spdhdi.uploadData.vo;

public interface BaseConst {

	String TYPE = "接口类型";
	String SYSTEM_SIGN = "接收系统标识";
	String DATA = "接口数据";
	String MX = "明细";
	String HOS_MX = "医疗机构明细";
	String PURCHASE_MX = "采购订单明细";
	String BALANCE_MX = "结算明细";
	
	/**
	 * SPD调用，ERP接收
	 */
	interface SPDHEAD{
		String SPD_DD = "SPD订单接口表";
		String SPD_QT = "SPD请退接口表";
		String SPD_BHSHQ = "SPD补货申请接口表";
		String SPD_YCSHQ = "SPD移出申请接口表";
		String SPD_JS = "SPD结算接口表";
		String SPD_FKDZH = "SPD付款对账接口表";
		String SPD_GTSHSDJ = "SPD跟台手术登记接口表";
		String SPD_PSHH = "SPD配送换货接口表";
		String SHPXSHMX = "商品销售明细查询";
		String YJKFLZH = "应急库分类账查询";
		String YJKKC = "应急库库存查询";
	}
	/**
	 * ERP调用，SPD接收
	 */
	interface ERPHEAD{
		String ZYBMXX = "职员部门信息";
		String GYSXX = "供应商信息";
		String YYYC = "医药药材信息";
		String SPPSJ = "商品配送价";
		String HPXX = "货品信息";
		String SPD_DDFH = "SPD订单返回接口表";
		String SPD_BHRK = "SPD补货入库接口表";
		String SPD_YCH = "SPD移出接口表";
		String SPD_FKDZH_FH = "SPD付款对账返回接口表";
	}
}
