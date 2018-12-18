package gyqx.mdqc.mycerts.dao;

import common.db.query.QueryResult;
import common.utils.PageUtils;
import gyqx.mdqc.mycerts.Constance;
import gyqx.mdqc.mycerts.dao.mapper.ViewJxsqsMapper;
import gyqx.mdqc.mycerts.vo.GoodsInfoVo;
import gyqx.mdqc.mycerts.vo.IdNameVo;
import gyqx.mdqc.mycerts.vo.JxsqsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author liangwu
 * @Date 18-8-22 上午11:30
 */
@Repository
public class ViewJxsqsDao {
    @Autowired
    private ViewJxsqsMapper mapper;

    public String getCorpKind(String corpId) {
        return mapper.getCorpKind(corpId);
    }

    public QueryResult<GoodsInfoVo> listGoodsInfo(String input, String mfrsId, String corpId, String kind, String hosId, Integer page, Integer size) {
        QueryResult<GoodsInfoVo> result;
        switch (kind) {
            case Constance.PROV:
            case Constance.MFRS_PROV:  // 供应商
                PageUtils.startPage(page, size);
                result = PageUtils.endPage(mapper.listProvGoodsInfo(input, mfrsId, corpId, hosId));
                break;
            case Constance.HOS:  // 医院
                PageUtils.startPage(page, size);
                result = PageUtils.endPage(mapper.listHosGoodsInfo(input, mfrsId, corpId));
                break;
            default:
                throw new IllegalArgumentException("非法查询");
        }
        return result;
    }

    public List<JxsqsData> getJxsqsDataList(String corpId, String kind, String goodsId, List<String> sqsIds, String hosId) {
        Set<JxsqsData> result;
        switch (kind) {
            case Constance.PROV:
            case Constance.MFRS_PROV:
                result = mapper.getProvJxsqsDataList(corpId, goodsId, hosId);
                break;
            case Constance.HOS:
                result = mapper.getHosJxsqsDataList(corpId, goodsId, sqsIds);
                break;
            default:
                throw new IllegalArgumentException("非法查询");
        }
        return new ArrayList<>(result);
    }

    public List<IdNameVo> getSqsHos(Set<String> sqsIds, String corpId, String kind) {
        List<IdNameVo> result;
        switch (kind) {
            case Constance.PROV:
            case Constance.MFRS_PROV:
                result = mapper.getProvSqsHos(corpId, sqsIds);
                break;
            case Constance.HOS:
                result = mapper.getHosSqsHos(corpId, sqsIds);
                break;
            default:
                throw new IllegalArgumentException("非法查询");
        }
        return result;
    }

    public List<String> getSqsIdsByHosId(String corpId) {
        return mapper.getSqsIdsByHosId(corpId);
    }

    public List<GoodsInfoVo> listGoodsInfoByHosId(String corpId, String hosId) {
        return mapper.listProvGoodsInfo(null, null, corpId, hosId);
    }
}
