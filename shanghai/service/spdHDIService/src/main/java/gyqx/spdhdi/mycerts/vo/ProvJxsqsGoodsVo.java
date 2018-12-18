package gyqx.spdhdi.mycerts.vo;

import gyqx.spdhdi.po.ProvJxsqsGoods;
import lombok.Data;

import java.util.List;

@Data
public class ProvJxsqsGoodsVo extends ProvJxsqsGoods {
    private String goodsName;
    private String goodsGg;
    private String provName;
    private String mfrsName;
    private String chiefName;
    private List<String> notInSqsIds;
    private List<String> goodsIds;
}
