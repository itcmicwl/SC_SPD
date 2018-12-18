package gyqx.mdqc.mycerts.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @Author liangwu
 * @Date 18-8-3 上午11:16
 */
@Data
public class BasGoodsInfoVo {
    private String id;
    @JsonIgnore
    private String certificateId;
    private String goodsId;
    private String goodsName;
    private String goodsGg;
    private Integer version;
}
