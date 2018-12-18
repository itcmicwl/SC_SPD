package gyqx.mdqc.mycerts.service;

import common.db.query.QueryResult;
import gyqx.mdqc.mycerts.vo.GoodsInfoVo;
import gyqx.mdqc.mycerts.vo.JxsqsData;
import gyqx.mdqc.mycerts.vo.TreeNode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author liangwu
 * @Date 18-8-22 上午10:42
 */
public interface ViewJxsqsService {
    List<TreeNode<JxsqsData>> getTrackingChain(String goodsId, String hosId);

    Map<String, List<String>> getSqsHos(Set<String> sqsIds);

    String getCorpKind(String corpId);

    QueryResult<GoodsInfoVo> listGoodsInfo(String input, String mfrsId, String hosId, Integer page, Integer size);

    ByteArrayOutputStream getExcel(String hosId, String hosName) throws IOException;
}
