package gyqx.mdqc.mycerts.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 注册证Vo
 * @Author liangwu
 * @Date 7/31/18 11:14 AM
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasRegistrationInfoVo {
    private String id;
    private String productName;
    private String mfrsName;
    private String mfrsId;
    private String certificateCode;
    private String regKind;
    private Date expdtBeginDate;
    private Date expdtEndDate;
    private String isOldInfo;
    private Integer version;
    private List<BasGoodsInfoVo> products;
    private List<BasRegistrationImageVo> fileList;

    private List<BasGoodsInfoVo> productsAdd;
    private List<BasGoodsInfoVo> productsDel;
    private List<BasRegistrationImageVo> fileListAdd;
    private List<BasRegistrationImageVo> fileListDel;

    @JsonIgnore
    public List<BasGoodsInfoVo> getProductsAdd() {
        return productsAdd;
    }

    @JsonProperty
    public void setProductsAdd(List<BasGoodsInfoVo> productsAdd) {
        this.productsAdd = productsAdd;
    }

    @JsonIgnore
    public List<BasGoodsInfoVo> getProductsDel() {
        return productsDel;
    }

    @JsonProperty
    public void setProductsDel(List<BasGoodsInfoVo> productsDel) {
        this.productsDel = productsDel;
    }

    @JsonIgnore
    public List<BasRegistrationImageVo> getFileListAdd() {
        return fileListAdd;
    }

    @JsonProperty
    public void setFileListAdd(List<BasRegistrationImageVo> fileListAdd) {
        this.fileListAdd = fileListAdd;
    }

    @JsonIgnore
    public List<BasRegistrationImageVo> getFileListDel() {
        return fileListDel;
    }

    @JsonProperty
    public void setFileListDel(List<BasRegistrationImageVo> fileListDel) {
        this.fileListDel = fileListDel;
    }
}
