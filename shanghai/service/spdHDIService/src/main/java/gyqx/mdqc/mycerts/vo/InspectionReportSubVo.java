package gyqx.mdqc.mycerts.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @Author liangwu
 * @Date 18-9-10 下午3:44
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InspectionReportSubVo {
    @JsonIgnore
    private String id;
    @JsonIgnore
    private String pid;
    private String goodsName;
    @JsonIgnore
    private String goodsNamePinyin;
    private String goodsSpec;
    private String lot;
    @JsonIgnore
    private String filler;
    @JsonIgnore
    private Integer version;
}
