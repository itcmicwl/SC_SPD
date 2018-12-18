package gyqx.mdqc.mycerts.service.impl;

import com.github.stuxuhai.jpinyin.PinyinException;
import common.base.PinYin;
import common.db.query.QueryResult;
import common.utils.SysAtomUtil;
import common.utils.UtilsContext;
import gyqx.mdqc.mycerts.dao.InspectionReportDao;
import gyqx.mdqc.mycerts.service.InspectionReportService;
import gyqx.mdqc.mycerts.service.ViewJxsqsService;
import gyqx.mdqc.mycerts.vo.InspectionReportSubVo;
import gyqx.mdqc.mycerts.vo.InspectionReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Author liangwu
 * @Date 18-9-10 下午4:21
 */
@Service
public class InspectionReportServiceImpl implements InspectionReportService {
    @Autowired
    private InspectionReportDao dao;

    @Autowired
    private UtilsContext utilsContext;

    @Autowired
    private ViewJxsqsService viewJxsqsService;

    @Override
    public QueryResult<InspectionReportVo> listReport(HashMap<String, String> params) {
        String corpId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        String kind = viewJxsqsService.getCorpKind(corpId);
        return dao.listReport(params, kind, corpId);
    }

    @Override
    public List<String> listReportAttachment(String id, String version) {
        String corpId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        String kind = viewJxsqsService.getCorpKind(corpId);
        return dao.listReportAttachment(id, version, kind, corpId);
    }

    @Override
    public Integer delReport(String id, String version) {
        String corpId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        return dao.delReport(id, version, corpId);
    }

    @Override
    public Integer addReport(InspectionReportVo vo) throws PinyinException {
        SysAtomUtil sysAtomUtil = utilsContext.getSysAtomUtil();
        String filler = utilsContext.getUserStateUtils().getCurrent().getUserId();
        String id = sysAtomUtil.newValue("insp_rpt_id");

        vo.setId(id);
        vo.setProvId(utilsContext.getUserStateUtils().getCurrent().getCorpId());
        vo.setFiller(filler);
        vo.setMfrsNamePinyin(PinYin.getShortPinyin(vo.getMfrsName()));

        for (InspectionReportSubVo item : vo.getReportSubVoList()) {
            item.setId(utilsContext.getSysAtomUtil().newValue("insp_rpt_sub_id"));
            item.setPid(id);
            item.setFiller(filler);
            item.setGoodsNamePinyin(PinYin.getShortPinyin(item.getGoodsName()));
        }

        vo.getReportImageVoList().forEach(item -> {
            item.setId(utilsContext.getSysAtomUtil().newValue("insp_rpt_img_id"));
            item.setPid(id);
            item.setFiller(filler);
        });
        return dao.addReport(vo);
    }
}
