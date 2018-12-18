package gyqx.mdqc.mycerts.dao;

import common.db.query.QueryResult;
import common.utils.PageUtils;
import gyqx.mdqc.mycerts.Constance;
import gyqx.mdqc.mycerts.dao.mapper.InspectionReportMapper;
import gyqx.mdqc.mycerts.vo.InspectionReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @Author liangwu
 * @Date 18-9-10 下午4:21
 */
@Repository
public class InspectionReportDao {
    @Autowired
    private InspectionReportMapper mapper;

    public QueryResult<InspectionReportVo> listReport(HashMap<String, String> params, String kind, String corpId) {
        QueryResult<InspectionReportVo> result;

        String pageStr = params.getOrDefault("page", "1");
        String sizeStr = params.getOrDefault("size", "30");
        int page = Integer.parseInt(pageStr);
        int size = Integer.parseInt(sizeStr);

        switch (kind) {
            case Constance.PROV:
            case Constance.MFRS_PROV:  // 供应商
                PageUtils.startPage(page, size);
                result = PageUtils.endPage(mapper.listProvReport(params, corpId));
                break;
            case Constance.HOS:  // 医院
                PageUtils.startPage(page, size);
                result = PageUtils.endPage(mapper.listHosReport(params));
                break;
            default:
                throw new IllegalArgumentException("非法查询");
        }
        return result;
    }

    public List<String> listReportAttachment(String id, String version, String kind, String corpId) {
        List<String> result;
        switch (kind) {
            case Constance.PROV:
            case Constance.MFRS_PROV:  // 供应商
                result = mapper.listReportAttachment(id, version, corpId);
                break;
            case Constance.HOS:  // 医院
                result = mapper.listReportAttachment(id, version, null);
                break;
            default:
                throw new IllegalArgumentException("非法查询");
        }
        return result;
    }

    public Integer delReport(String id, String version, String corpId) {
        if (mapper.delReport(id, version, corpId) == 1) {
            mapper.delReportSub(id);
            mapper.delReportImg(id);
            return 1;
        } else {
            return -1;
        }
    }

    public Integer addReport(InspectionReportVo vo) {
        mapper.addReport(vo);
        if (vo.getReportSubVoList().size() > 0) {
            mapper.addReportSub(vo.getReportSubVoList());
        }
        if (vo.getReportImageVoList().size() > 0) {
            mapper.addReportImg(vo.getReportImageVoList());
        }
        return 1;
    }
}
