package gyqx.mdqc.mycerts.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @Author liangwu
 * @Date 18-9-10 下午3:46
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InspectionReportImageVo {
    @JsonIgnore
    public String id;
    @JsonIgnore
    public String pid;
    public String path;
    @JsonIgnore
    public String filler;
    @JsonIgnore
    public Integer version;
}
