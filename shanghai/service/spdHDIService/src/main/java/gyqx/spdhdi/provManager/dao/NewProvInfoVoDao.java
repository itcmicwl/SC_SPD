package gyqx.spdhdi.provManager.dao;

import java.util.List;

import javax.annotation.Resource;

import gyqx.spdhdi.myInfos.vo.ProvHosInfoVo;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.db.query.QueryInfo;
import gyqx.spdhdi.provManager.dao.mapper.INewProvInfoMapper;

@Repository
public class NewProvInfoVoDao {
	@Resource
	INewProvInfoMapper newProvInfoMapper;

	public PageInfo<ProvHosInfoVo> getNewProvInfoVoList(QueryInfo<ProvHosInfoVo> queryInfo) {
		PageHelper.startPage(queryInfo.getPage(), queryInfo.getRows());
		List<ProvHosInfoVo> lstProvHosInfoVo = newProvInfoMapper.getNewProvInfoVoList(queryInfo.getQueryObject());
		PageInfo<ProvHosInfoVo> ProvHosInfoVoPage = new PageInfo<ProvHosInfoVo>(lstProvHosInfoVo);
		return ProvHosInfoVoPage;
	}

}
