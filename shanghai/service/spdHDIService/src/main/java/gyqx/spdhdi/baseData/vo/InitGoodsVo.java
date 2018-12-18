package gyqx.spdhdi.baseData.vo;

public class InitGoodsVo {
	private String rowIndex;
	public String getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}
	private String  goodsName;
	private String goodsGg;
	private String made;
	private String unit; //供应商计量单位
	private String mfrsName;
	private String brand;
	private String packeage;
	private String mfrsCode;
	private String certificateCode;
	private String provName;
	private String provCode;//供应商商品code
	private String masterCode;
	private String hosName;
	private String hosCode; //医院商品code
	private String generalName; //医院商品俗称
	private String purPrice;// 二级供应商卖给集配的价
	private String hisPrice;
	private String taxRate;
	private String miCode;
	private String charging;
	private String hwDesc; //件包
	private String hosLbsx;//分类
	private String hosUnit; //医院计量单位
	private String isMetering; //是否计量
	private String purMode; //高低值
	private String goodsDesc; //产品描述
    private String kind68Code; //68分类
    

    private String salePrice; //配送价，集配卖给医院的价格
    private String unitRate; //转换比 供应商的数量/医院数量
	
    private String errorDesc;
    private String errorFlag;
    
    private String collectorName; //集配商名称
	private String isSubprov;   //是否二级供应商
	
	private String subProvErpCode; //二级供应商在JDE中编码
    
    
    public String getSubProvErpCode() {
		return subProvErpCode;
	}
	public void setSubProvErpCode(String subProvErpCode) {
		this.subProvErpCode = subProvErpCode;
	}
	public String getCollectorName() {
		return collectorName;
	}
	public void setCollectorName(String collectorName) {
		this.collectorName = collectorName;
	}
	public String getIsSubprov() {
		return isSubprov;
	}
	public void setIsSubprov(String isSubprov) {
		this.isSubprov = isSubprov;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsGg() {
		return goodsGg;
	}
	public void setGoodsGg(String goodsGg) {
		this.goodsGg = goodsGg;
	}
	public String getMade() {
		return made;
	}
	public void setMade(String made) {
		this.made = made;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getMfrsName() {
		return mfrsName;
	}
	public void setMfrsName(String mfrsName) {
		this.mfrsName = mfrsName;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getPackeage() {
		return packeage;
	}
	public void setPackeage(String packeage) {
		this.packeage = packeage;
	}
	public String getMfrsCode() {
		return mfrsCode;
	}
	public void setMfrsCode(String mfrsCode) {
		this.mfrsCode = mfrsCode;
	}
	public String getCertificateCode() {
		return certificateCode;
	}
	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}
	public String getProvName() {
		return provName;
	}
	public void setProvName(String provName) {
		this.provName = provName;
	}
	public String getProvCode() {
		return provCode;
	}
	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}
	public String getMasterCode() {
		return masterCode;
	}
	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}
	public String getHosName() {
		return hosName;
	}
	public void setHosName(String hosName) {
		this.hosName = hosName;
	}
	public String getHosCode() {
		return hosCode;
	}
	public void setHosCode(String hosCode) {
		this.hosCode = hosCode;
	}
	public String getGeneralName() {
		return generalName;
	}
	public void setGeneralName(String generalName) {
		this.generalName = generalName;
	}
	public String getPurPrice() {
		return purPrice;
	}
	public void setPurPrice(String purPrice) {
		this.purPrice = purPrice;
	}
	public String getHisPrice() {
		return hisPrice;
	}
	public void setHisPrice(String hisPrice) {
		this.hisPrice = hisPrice;
	}
	public String getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
	public String getMiCode() {
		return miCode;
	}
	public void setMiCode(String miCode) {
		this.miCode = miCode;
	}
	public String getCharging() {
		return charging;
	}
	public void setCharging(String charging) {
		this.charging = charging;
	}
	public String getHwDesc() {
		return hwDesc;
	}
	public void setHwDesc(String hwDesc) {
		this.hwDesc = hwDesc;
	}

	public String getHosLbsx() {
		return hosLbsx;
	}
	public void setHosLbsx(String hosLbsx) {
		this.hosLbsx = hosLbsx;
	}
	public String getHosUnit() {
		return hosUnit;
	}
	public void setHosUnit(String hosUnit) {
		this.hosUnit = hosUnit;
	}
	public String getIsMetering() {
		return isMetering;
	}
	public void setIsMetering(String isMetering) {
		this.isMetering = isMetering;
	}
	public String getPurMode() {
		return purMode;
	}
	public void setPurMode(String purMode) {
		this.purMode = purMode;
	}
	public String getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public String getKind68Code() {
		return kind68Code;
	}
	public void setKind68Code(String kind68Code) {
		this.kind68Code = kind68Code;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getUnitRate() {
		return unitRate;
	}
	public void setUnitRate(String unitRate) {
		this.unitRate = unitRate;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public String getErrorFlag() {
		return errorFlag;
	}
	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}
	

}
