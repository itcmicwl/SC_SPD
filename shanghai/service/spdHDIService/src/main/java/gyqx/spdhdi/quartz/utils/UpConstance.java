package gyqx.spdhdi.quartz.utils;

public interface UpConstance {

	Integer GYQX_ZERO = 0; // 表示结算机构自己的结算数据
	Integer PARTNER_ONE = 1; // 表示合作供应商需要通过结算供应商进行结算数据

	Integer NORMAL_OUT = 0; // 正常出库
	Integer RETURN_OUT = 1; // 出库退回
	Integer BAD_OUT = 2; // 报损出库
	Integer OTHERS_OUT = 3; // 其他出库
}
