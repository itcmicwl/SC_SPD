package gyqx.spdherp.stockout.vo.vo4In;

import gyqx.spdherp.po.OutStockBatch;
import gyqx.spdherp.po.OutStockList;
import gyqx.spdherp.po.OutStockUniqueCode;

import java.util.List;

public class OutSub4InVo extends OutStockList {

    private Double price;

    private Double avlQty;

    private Double reqQty;

    private Double sentQty;

    private List<OutStockBatch> lstOutBatch;

    private List<OutStockUniqueCode> lstOutUniqueCode;

    private String generalName;

    private String goodsDesc;

    private Boolean imgAvailable;

    public Double getPrice() {
        return price;
    }

    public Double getAvlQty() {
        return avlQty;
    }

    public Double getReqQty() {
        return reqQty;
    }

    public Double getSentQty() {
        return sentQty;
    }

    public List<OutStockBatch> getLstOutBatch() {
        return lstOutBatch;
    }

    public List<OutStockUniqueCode> getLstOutUniqueCode() {
        return lstOutUniqueCode;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setAvlQty(Double avlQty) {
        this.avlQty = avlQty;
    }

    public void setReqQty(Double reqQty) {
        this.reqQty = reqQty;
    }

    public void setSentQty(Double sentQty) {
        this.sentQty = sentQty;
    }

    public void setLstOutBatch(List<OutStockBatch> lstOutBatch) {
        this.lstOutBatch = lstOutBatch;
    }

    public void setLstOutUniqueCode(List<OutStockUniqueCode> lstOutUniqueCode) {
        this.lstOutUniqueCode = lstOutUniqueCode;
    }

    public String getGeneralName() {
        return generalName;
    }

    public void setGeneralName(String generalName) {
        this.generalName = generalName;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Boolean getImgAvailable() {
        return imgAvailable;
    }

    public void setImgAvailable(Boolean imgAvailable) {
        this.imgAvailable = imgAvailable;
    }
}
