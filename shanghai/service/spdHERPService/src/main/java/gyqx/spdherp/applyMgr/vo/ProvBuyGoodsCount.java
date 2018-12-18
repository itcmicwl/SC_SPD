package gyqx.spdherp.applyMgr.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moonb on 2018/4/3.
 */
public class ProvBuyGoodsCount {
    private String provId;
    private String provName;
    private String subProvId;
    private String subProvName;
    private Integer goodsKinds;
    private Integer isUseSubProv;
    private List<String> subProvIds = new ArrayList<>();
    private List<ProvBuyGoodsCount> lstDetail = new ArrayList<>();
    public String getProvId() {
        return provId;
    }

    public void setProvId(String provId) {
        this.provId = provId;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public String getSubProvId() {
        return subProvId;
    }

    public void setSubProvId(String subProvId) {
        this.subProvId = subProvId;
    }

    public String getSubProvName() {
        return subProvName;
    }

    public void setSubProvName(String subProvName) {
        this.subProvName = subProvName;
    }

    public Integer getGoodsKinds() {
        return goodsKinds;
    }

    public void setGoodsKinds(Integer goodsKinds) {
        this.goodsKinds = goodsKinds;
    }

    public Integer getIsUseSubProv() {
        return isUseSubProv;
    }

    public void setIsUseSubProv(Integer isUseSubProv) {
        this.isUseSubProv = isUseSubProv;
    }

    public List<ProvBuyGoodsCount> getLstDetail() {
        return lstDetail;
    }

    public void setLstDetail(List<ProvBuyGoodsCount> lstDetail) {
        this.lstDetail = lstDetail;
    }

    public List<String> getSubProvIds() {
        return subProvIds;
    }

    public void setSubProvIds(List<String> subProvIds) {
        this.subProvIds = subProvIds;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() == obj.getClass()){
            ProvBuyGoodsCount o = (ProvBuyGoodsCount) obj;
            return o.getProvId().equals(this.provId) && o.getSubProvId().equals(this.getSubProvId());
        }
        if (obj instanceof java.lang.String){
            return this.provId.equals(obj);
        }
        return super.equals(obj);
    }
}
