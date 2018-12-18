package gyqx.ws.yg.service.impl;

import javax.annotation.Resource;

import common.db.SimpleDao;
import gyqx.ws.yg.common.YgSendUtil;
import gyqx.ws.yg.dao.YgSendBillDao;
import gyqx.ws.yg.service.IYgSendBillListService;
import gyqx.ws.yg.service.IYgSendBillListStateService;
import gyqx.ws.yg.service.IYgSendBillService;
import gyqx.ws.yg.vo.repVo.YY154_REP_DETAIL;
import gyqx.ws.yg.vo.repVo.YY155_REP_DETAIL;
import gyqx.ws.yg.vo.repVo.YY161_REP_DETAIL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import common.utils.PageUtils;
import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;

@Service
public class YgSendBillService implements IYgSendBillService {
	
	@Resource
	private YgSendBillDao dao;
	@Resource
	private IYgSendBillListService ygSendBillLstSer;
	@Resource
	private IYgSendBillListStateService ygSendBillLstStateSer;
	@Override
	public YY154_REP_DETAIL get(String psdbh) throws Exception{
        YY154_REP_DETAIL res = dao.get(psdbh);
        if(res != null){
            res.setLstDeatil(ygSendBillLstSer.list(psdbh));
            res.setLstState(ygSendBillLstStateSer.list(psdbh));
        }
        return res;
	}

    public boolean checkExists(String psdbh) throws Exception{
        YY154_REP_DETAIL query = new YY154_REP_DETAIL();
        query.setPsdbh(psdbh);
        List<YY154_REP_DETAIL> res = dao.list(query);
        if(res == null){
            return false;
        }
        return res.size()>0;
    }
	@Override
	public int update(YY154_REP_DETAIL bean) throws Exception {
		return dao.update(bean);
	}

	@Override
	public int insert(YY154_REP_DETAIL bean) throws Exception {
        boolean flag = checkExists(bean.getPsdbh());
        if(flag){return 0;}
		for (YY155_REP_DETAIL yy155_rep_detail : bean.getLstDeatil()) {
			yy155_rep_detail.setPsdbh(bean.getPsdbh());
		}
		for (YY161_REP_DETAIL yy161_rep_detail : bean.getLstState()) {
			yy161_rep_detail.setPsdbh(bean.getPsdbh());
		}
		ygSendBillLstSer.insertByBatch(bean.getLstDeatil());
		ygSendBillLstStateSer.insertByBatch(bean.getLstState());
		return dao.insert(bean);
	}

	@Override
	public int insertByBatch(List<YY154_REP_DETAIL> lst) throws Exception {
		return dao.insertByBatch(lst);
	}

	@Override
	public List<YY154_REP_DETAIL> list(YY154_REP_DETAIL queryInfo) throws Exception {
		return (List<YY154_REP_DETAIL>) dao.list(queryInfo);
	}

	@Override
	public QueryResult<YY154_REP_DETAIL> listByPage(QueryInfo<YY154_REP_DETAIL> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}


}


