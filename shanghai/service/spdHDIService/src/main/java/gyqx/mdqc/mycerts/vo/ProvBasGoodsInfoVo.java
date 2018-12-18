package gyqx.mdqc.mycerts.vo;

/**
 * Created by cjzyw on 2018/8/28.
 */
public class ProvBasGoodsInfoVo{
    private String id ;
    private String goodsId;
    private String goodsName ;
    private String goodsGg ;
    private String unit ;
    private int version ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
