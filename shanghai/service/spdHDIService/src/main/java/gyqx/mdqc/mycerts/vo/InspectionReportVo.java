package gyqx.mdqc.mycerts.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author liangwu
 * @Date 18-9-10 下午3:37
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InspectionReportVo {
    private String id;
    @JsonIgnore
    private String provId;
    private String provName;
    private String mfrsName;
    @JsonIgnore
    private String mfrsNamePinyin;
    @JsonIgnore
    private String filler;
    private Integer imgNum;
    private Integer version;

    List<InspectionReportSubVo> reportSubVoList;
    List<InspectionReportImageVo> reportImageVoList;

    @JsonIgnore
    public List<InspectionReportImageVo> getReportImageVoList() {
        return reportImageVoList;
    }

    @JsonProperty
    public void setReportImageVoList(List<InspectionReportImageVo> reportImageVoList) {
        this.reportImageVoList = reportImageVoList;
    }
}
