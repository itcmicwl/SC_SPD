package gyqx.spdhdi.mycerts.vo;

import gyqx.spdhdi.po.ProvJxsqs;
import gyqx.spdhdi.po.ProvJxsqsGoods;
import gyqx.spdhdi.po.ProvJxsqsHos;
import lombok.Data;

import java.util.List;
@Data
public class SaveJxsqsRelationVo {
    private List<ProvJxsqsGoods> lstSqsRelation;
    private List<ProvJxsqsHos> lstSqsHos;
}
