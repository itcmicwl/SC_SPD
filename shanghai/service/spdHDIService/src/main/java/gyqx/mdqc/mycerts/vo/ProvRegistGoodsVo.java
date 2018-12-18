package gyqx.mdqc.mycerts.vo;


import java.io.Serializable;

import gyqx.spdhdi.po.ProvRegistGoods;

/**
 * Created by cjzyw on 2018/8/27.
 */
public class ProvRegistGoodsVo extends ProvRegistGoods implements Serializable{
    private String goodsName;
    private String goodsGg;
    private String unit;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
