package gyqx.mdqc.mycerts.service.impl;

import common.db.query.QueryResult;
import common.utils.UtilsContext;
import gyqx.mdqc.mycerts.Constance;
import gyqx.mdqc.mycerts.dao.ViewJxsqsDao;
import gyqx.mdqc.mycerts.service.ViewJxsqsService;
import gyqx.mdqc.mycerts.vo.GoodsInfoVo;
import gyqx.mdqc.mycerts.vo.IdNameVo;
import gyqx.mdqc.mycerts.vo.JxsqsData;
import gyqx.mdqc.mycerts.vo.TreeNode;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * @Author liangwu
 * @Date 18-8-22 上午10:42
 */
@Service
public class ViewJxsqsServiceImpl implements ViewJxsqsService {
    private static String[] columns = {"ERP编码", "产品名称", "产品规格", "注册证编号", "厂家", "是否关联授权书"};
    private final static ConcurrentMap<String, String> corpKindMap = new ConcurrentHashMap<>();

    @Autowired
    private UtilsContext utilsContext;
    @Autowired
    private ViewJxsqsDao dao;

    @Override
    public QueryResult<GoodsInfoVo> listGoodsInfo(String input, String mfrsId, String hosId, Integer page, Integer size) {
        String corpId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        String kind = getCorpKind(corpId);
        return dao.listGoodsInfo(input, mfrsId, corpId, kind, hosId, page, size);
    }

    @Override
    public ByteArrayOutputStream getExcel(String hosId, String hosName) throws IOException {
        String corpId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        List<GoodsInfoVo> result = dao.listGoodsInfoByHosId(corpId, hosId);
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet(hosName);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        for (GoodsInfoVo item : result) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.getErpCode());
            row.createCell(1).setCellValue(item.getGoodsName());
            row.createCell(2).setCellValue(item.getGoodsGg());
            row.createCell(3).setCellValue(item.getCertificateCode());
            row.createCell(4).setCellValue(item.getMfrsName());
            row.createCell(5).setCellValue(item.isTrackable() ? "是" : "否");
        }
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        workbook.write(byteOut);
        workbook.close();

        return byteOut;
    }

    @Override
    public List<TreeNode<JxsqsData>> getTrackingChain(String goodsId, String hosId) {
        final String corpId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        String kind = getCorpKind(corpId);
        List<String> sqsIds = new ArrayList<>();
        if (kind.equals(Constance.HOS)) {
            sqsIds.addAll(dao.getSqsIdsByHosId(corpId));
        }
        List<JxsqsData> jxsqsDataList = dao.getJxsqsDataList(corpId, kind, goodsId, sqsIds, hosId);
        List<JxsqsData> rootNodeDatas;
        if (kind.equals(Constance.HOS)) {
            rootNodeDatas = jxsqsDataList.stream().filter(item -> sqsIds.contains(item.getSqsId())).collect(toList());
        } else {
            rootNodeDatas = jxsqsDataList.stream().filter(item -> item.getProvId().equals(corpId)).collect(Collectors.toList());
        }
//        jxsqsDataList.removeAll(rootNodeDatas);
        List<TreeNode<JxsqsData>> roots = new ArrayList<>();
        for (JxsqsData jxsqsData : rootNodeDatas) {
            TreeNode<JxsqsData> root = new TreeNode<>(jxsqsData);
            roots.add(root);
        }
        makeTree(roots, jxsqsDataList);
        return roots;
    }

    @Override
    public Map<String, List<String>> getSqsHos(Set<String> sqsIds) {
        String corpId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        String kind = getCorpKind(corpId);
        return dao.getSqsHos(sqsIds, corpId, kind).stream().collect(groupingBy(IdNameVo::getId, mapping(IdNameVo::getName, toList())));
    }

    public String getCorpKind(String corpId) {
        String kind;
        if (corpKindMap.containsKey(corpId)) {
            kind = corpKindMap.get(corpId);
        } else {
            kind = dao.getCorpKind(corpId);
            corpKindMap.put(corpId, kind);
        }
        return kind;
    }

    private List<TreeNode<JxsqsData>> makeTree(List<TreeNode<JxsqsData>> nodes, List<JxsqsData> list) {
        for (TreeNode<JxsqsData> node : nodes) {
            JxsqsData curData = node.getData();
            List<JxsqsData> children = list.stream().
                    filter(child ->
                            curData.getChiefId() != null
                                    && curData.getChiefId().equals(child.getProvId())
                                    && curData.getSqsLastlevelId() != null
                                    && curData.getSqsLastlevelId().equals(child.getSqsId()))
                    .collect(Collectors.toList());

            if (!children.isEmpty()) {
                for (JxsqsData child : children) {
                    node.addChild(child);
                }
//                list.removeAll(children);
                makeTree(node.getChildren(), list);
            }
        }
        return nodes;
    }
}
