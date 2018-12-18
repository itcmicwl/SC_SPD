package gyqx.spdhdi.provManager.vo;


import java.io.Serializable;

import gyqx.spdherp.po.HosCollectorProv;

/**
 * Created by moonb on 2017/10/19.
 */
public class HosCollectorProvVo extends HosCollectorProv implements Serializable {
    private String colName;
    private String provName;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (obj instanceof ProvGoodsKindsVo){
            ProvGoodsKindsVo o = (ProvGoodsKindsVo) obj;
            return o.getProvId().equals(this.getCollectorId());
        }
        return super.equals(obj);
    }
}
