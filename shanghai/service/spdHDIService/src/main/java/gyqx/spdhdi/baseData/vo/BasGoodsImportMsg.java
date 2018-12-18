package gyqx.spdhdi.baseData.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author liangwu
 * @Date 12/14/18 2:33 PM
 */
@Data
public class BasGoodsImportMsg {
    private String provId;
    private List<String> provList;
    private List<String> mfrsList;
}
