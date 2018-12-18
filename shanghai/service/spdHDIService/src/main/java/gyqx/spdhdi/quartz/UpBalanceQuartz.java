package gyqx.spdhdi.quartz;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.exception.ExceptionMessage;
import gyqx.spdhdi.settleCenter.service.IHosOutBalanceService;
import gyqx.spdhdi.settleCenter.vo.HosOutBalanceVo;
import gyqx.spdhdi.uploadData.vo.Msg;

public class UpBalanceQuartz {

	private static Logger log = LoggerFactory.getLogger(UpBalanceQuartz.class);

	@Resource
	private IHosOutBalanceService hosOutBalanceService;

	public synchronized void sendBalanceToJDE() {
		List<HosOutBalanceVo> hbs = hosOutBalanceService.getAllBillSubmit();
		if (hbs == null || hbs.size() == 0) {
			return;
		}
		for (HosOutBalanceVo hosOutBalanceVo : hbs) {
			String billId = hosOutBalanceVo.getBillId();
			try {
				log.info("#############################SPD结算单{}, 开始上传JDE#############################", billId);
				Msg msg = hosOutBalanceService.balanceUp(hosOutBalanceVo);
				log.info("#############################SPD结算单{}, 上传JDE结果{}#############################", billId, msg);
			} catch (Exception e) {
				log.error("#############################SPD结算单{}, 上传JDE失败, 错误信息: => {}", billId,
						ExceptionMessage.fromException(e));
			}
		}
	}

}
