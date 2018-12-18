package gyqx.spdhdi.quartz;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.exception.ExceptionMessage;
import gyqx.spdhdi.orderMgr.service.IPurconfirmService;
import gyqx.spdhdi.orderMgr.vo.PurconfirmVo;
import gyqx.spdhdi.uploadData.vo.Msg;

/**
 * 采购确认单上传JDE
 * 
 * @author LIWENKANG
 *
 */
public class UpPurconfirmQuartz {

	private static Logger log = LoggerFactory.getLogger(UpPurconfirmQuartz.class);

	@Resource
	private IPurconfirmService purconfirmService;

	public synchronized void sendPurconfirmToJDE() {
		List<PurconfirmVo> pcs = purconfirmService.getAllBillsNotSend();
		if (pcs == null || pcs.size() == 0) {
			return;
		}
		for (PurconfirmVo purconfirmVo : pcs) {
			String billId = purconfirmVo.getBillId();
			// 可以测试一下网络
			try {
				log.info("#############################采购确认单{}, 开始上传JDE#############################", billId);
				Msg msg = purconfirmService.purchaseAgain(billId);
				log.info("#############################采购确认单{}, 上传JDE结果{}#############################", billId, msg);
			} catch (Exception e) {
				log.error("#############################采购确认单{}, 上传JDE失败, 错误信息: => {}", billId,
						ExceptionMessage.fromException(e));
			}
		}
	}
}
