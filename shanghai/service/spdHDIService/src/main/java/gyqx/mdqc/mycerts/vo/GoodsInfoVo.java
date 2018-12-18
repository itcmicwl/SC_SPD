package gyqx.mdqc.mycerts.vo;

import lombok.Data;

/**
 * @Author liangwu
 * @Date 18-8-22 上午10:48
 */
@Data
public class GoodsInfoVo {
    private String id;
    private String goodsName;
    private String goodsGg;
    private String mfrsName;
    private String certificateCode;
    private String spdGoodsCode;
    private String provName;
    private String erpCode;
    private boolean trackable;
}
