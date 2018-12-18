package gyqx.mdqc.mycerts.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 经销授权书授权节点数据
 * @Author liangwu
 * @Date 18-8-23 上午8:52
 */
@Data
@EqualsAndHashCode
public class JxsqsData {
    private String id;
    @JsonIgnore
    private String provId;
    private String provName;
    @JsonIgnore
    private String chiefId;
    private String chiefName;
    private String sqsId;
    private String sqsLastlevelId;
    private String saleArea;
    private String filePath;
    private String isMfrs;
    private String isValiding;
    private Date expdtBeginDate;
    private Date expdtEndDate;
}
