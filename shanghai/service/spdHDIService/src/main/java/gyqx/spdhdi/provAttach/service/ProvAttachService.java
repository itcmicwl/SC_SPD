package gyqx.spdhdi.provAttach.service;

import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.mycerts.vo.BasCompanyInfoJxsqsVo;
import gyqx.spdhdi.provAttach.vo.ProvAttachVo;

public interface ProvAttachService {

	/**
	 * 查询供应商附件数据
	 * @param queryInfo
	 * @return
	 */
	public QueryResult<ProvAttachVo> getAttachList(QueryInfo<ProvAttachVo> queryInfo);
	
	/**
	 * 新增附件信息
	 * @param attachVo
	 * @return
	 */
	public String insertAttach(ProvAttachVo attachVo) throws Exception;
	
	/**
	 * 更新附件信息
	 * @param attachVo
	 * @return
	 */
	public void updateAttach(List<ProvAttachVo> attachVo) throws Exception;
	
	List<BasCompanyInfoJxsqsVo> findProvList(String provName);
	
}
