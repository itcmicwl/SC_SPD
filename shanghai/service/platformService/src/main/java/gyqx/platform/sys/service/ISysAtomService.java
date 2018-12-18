package gyqx.platform.sys.service;

import java.util.HashMap;
import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.AtomItem;
import gyqx.platform.po.SysAtom;
import gyqx.platform.po.SysConfig;

public interface ISysAtomService {
	HashMap<String,AtomItem> getAtomItemMap();
	QueryResult<SysAtom> getSysAtomList(QueryInfo<SysAtom> queryInfo);
}
