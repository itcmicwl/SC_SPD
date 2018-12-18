package gyqx.spdhdi.mycerts.vo;

import gyqx.spdhdi.po.ProvJxsqs;
import lombok.Data;

import java.util.List;

@Data
public class ProvJxsqsNVo extends ProvJxsqs {
    private Integer provKind;
    private String provName;
    private String mfrsName;
    private String chiefName;
    private Integer chiefKind;
    private List<ProvJxsqsGoodsVo> lstDetail;
    private List<ProvJxsqsNVo> children;
}
