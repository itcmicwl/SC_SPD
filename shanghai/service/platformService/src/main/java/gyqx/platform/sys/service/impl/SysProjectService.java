package gyqx.platform.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.SysAtomUtil;
import gyqx.platform.po.SysProject;
import gyqx.platform.sys.dao.SysProjectDao;
import gyqx.platform.sys.service.ISysProjectService;
import gyqx.platform.sys.vo.ElTreeVo;
import gyqx.platform.sys.vo.SysProjectVo;

@Service
public class SysProjectService implements ISysProjectService {
	@Resource
	SysProjectDao sysProjectDao;
	@Resource
	private SysAtomUtil atomUtil;

	@Override
	public QueryResult<SysProjectVo> getSysProjectList(QueryInfo<SysProject> queryInfo) {
		QueryResult<SysProjectVo> PagedSysProject = new QueryResult<SysProjectVo>();
		PageHelper.startPage(queryInfo.getPage(), queryInfo.getRows());
		List<SysProjectVo> lstSysProject = sysProjectDao.getSysProjectList(queryInfo.getQueryObject());
		PageInfo<SysProjectVo> sysConfigPage = new PageInfo<SysProjectVo>(lstSysProject);
		PagedSysProject.setData(sysConfigPage.getList());
		PagedSysProject.setPageNum(sysConfigPage.getPageNum());
		PagedSysProject.setPageSize(sysConfigPage.getPageSize());
		PagedSysProject.setTotal(sysConfigPage.getTotal());
		return PagedSysProject;
	}

	@Override
	public SysProject getSysProjectByid(String id) {
		return sysProjectDao.getSysProjectByid(id);
	}

	@Override
	public int insertSysProject(SysProject sysProject) {
		String id = atomUtil.newValue("sys.pro");
		sysProject.setId(id);
		sysProject.setCode(genCode(sysProject));
		return sysProjectDao.insertSysProject(sysProject);

	}

	private String genCode(SysProject sysProject) {
		String code;
		if (sysProject.getPid() != null && !"".equals(sysProject.getPid())) {
			code = sysProject.getPid() + "." + sysProject.getId();
		} else {
			code = sysProject.getId();
		}
		return code;
	}

	@Override
	public int setSysProject(SysProject sysconfig) {
		sysconfig.setMt(new Date());
		sysconfig.setCode(genCode(sysconfig));
		return sysProjectDao.setSysProject(sysconfig);
	}

	@Override
	public boolean existSysProject(SysProject sysProject) throws Exception {
		return sysProjectDao.existSysProject(sysProject);
	}

	@Override
	public int delSysProjectByCode(String code) {
		return sysProjectDao.delSysProjectByCode(code);
	}

	@Override
	public List<SysProject> getParentSysProList(QueryInfo<SysProject> queryInfo) throws Exception {
		return sysProjectDao.getParentSysProList(queryInfo);
	}

	@Override
	public List<ElTreeVo> getSysProjectTree() throws Exception {
		List<ElTreeVo> lstTree = new ArrayList<ElTreeVo>();
		SysProject sysProject = new SysProject();
		List<SysProjectVo> lstSPro = sysProjectDao.getSysProjectList(sysProject);
		List<SysProjectVo> lstRoots = lstSPro.stream().filter(o -> StringUtils.isEmpty(o.getPid())).collect(Collectors.toList());
		lstRoots.forEach(o->{
			ElTreeVo t = new ElTreeVo();
			t.setId(o.getId());
			t.setLabel(o.getCname());
			t.setCode(o.getCode());
			t.setNodeType("sysProject");
			t.setOb(o);
			List<ElTreeVo> lstsubTree = new ArrayList<ElTreeVo>();
			lstSPro.stream().filter(c -> o.getId().equals(c.getPid())).forEach(sub->{
				ElTreeVo subT = new ElTreeVo();
				subT.setOb(sub);
				subT.setId(sub.getId());
				subT.setCode(sub.getCode());
				subT.setLabel(sub.getCname());
				subT.setNodeType("sysProject");
				lstsubTree.add(subT);
			});
			if(lstsubTree.size()>0){
				t.setChildren(lstsubTree);
			}else{
				t.setDisabled(true);
			}
			lstTree.add(t);
		});		
		return lstTree;
	}

}
