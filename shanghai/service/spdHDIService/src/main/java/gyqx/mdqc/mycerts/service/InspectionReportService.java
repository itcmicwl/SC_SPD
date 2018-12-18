package gyqx.mdqc.mycerts.service;

import com.github.stuxuhai.jpinyin.PinyinException;
import common.db.query.QueryResult;
import gyqx.mdqc.mycerts.vo.InspectionReportVo;

import java.util.HashMap;
import java.util.List;

/**
 * @Author liangwu
 * @Date 18-9-10 下午4:21
 */
public interface InspectionReportService {
    QueryResult<InspectionReportVo> listReport(HashMap<String, String> params);

    List<String> listReportAttachment(String id, String version);

    Integer delReport(String id, String version);

    Integer addReport(InspectionReportVo vo) throws PinyinException;
}
