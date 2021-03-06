package gyqx.spdhdi.provManager.vo;

import gyqx.spdhdi.provManager.vo.HosCollectorProvVo;

import java.util.List;

/**
 * Created by moonb on 2017/11/1.
 */
public class ProvGoodsKindsVo {
    private String provId;
    private int goodsKinds;
    private String provName;
    private List<String> subProvId;
    public String getProvId() {
        return provId;
    }

    public void setProvId(String provId) {
        this.provId = provId;
    }

    public int getGoodsKinds() {
        return goodsKinds;
    }

    public void setGoodsKinds(int goodsKinds) {
        this.goodsKinds = goodsKinds;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public List<String> getSubProvId() {
        return subProvId;
    }

    public void setSubProvId(List<String> subProvId) {
        this.subProvId = subProvId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() == obj.getClass()){
            ProvGoodsKindsVo o = (ProvGoodsKindsVo) obj;
            return o.getProvId().equals(this.provId);
        }
        if(obj instanceof HosCollectorProvVo){
            HosCollectorProvVo o = (HosCollectorProvVo) obj;
            return o.getProvId().equals(this.provId);
        }
        if (obj instanceof java.lang.String) {
            return this.provId.equals(obj.toString());
        }
        return super.equals(obj);
    }
}
