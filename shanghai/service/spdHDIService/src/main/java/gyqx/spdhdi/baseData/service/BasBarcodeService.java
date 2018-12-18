package gyqx.spdhdi.baseData.service;

import gyqx.spdhdi.baseData.vo.BarcodeParseResult;

import java.util.Set;

/**
 * @Auther Liangwu
 * @Date 17-7-14 下午3:06
 */
public interface BasBarcodeService {
    Set<BarcodeParseResult> parseBarcode(String barcodeStr) throws Exception;

    Set<BarcodeParseResult> parseBarcode(String barcodeStr, String hosId, String provId) throws Exception;

    Set<BarcodeParseResult> parseBarcode(String masterBarcode, String slaverBarcode, String hosId, String provId) throws Exception;
}
