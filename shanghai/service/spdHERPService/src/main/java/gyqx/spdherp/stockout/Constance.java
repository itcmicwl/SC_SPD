package gyqx.spdherp.stockout;

/**
 * @Auther Liangwu
 * @Date 17-9-11 下午4:51
 */
public class Constance {
    // 出库单状态
    // 保存
    public static final Integer OUTSTOCKBILL_STATUS_SAVE = 90;
    // 提交
    public static final Integer OUTSTOCKBILL_STATUS_SUBMIT = 10;
    // 审核
    public static final Integer OUTSTOCKBILL_STATUS_CHECKED = 20;
    // 记帐
    public static final Integer OUTSTOCKBILL_STATUS_ACCOUNT = 30;
    // 作废
    public static final Integer OUTSTOCKBILL_STATUS_ABOLISH = 40;
    // 驳回
    public static final Integer OUTSTOCKBILL_STATUS_REJECT = 50;
    // 入库
    public static final Integer OUTSTOCKBILL_STATUS_INSTOCK = 60;
    // 结算
    public static final Integer OUTSTOCKBILL_STATUS_SETTLEMENT = 70;


    // 出库类型
    // 请购出库
    public static final Integer OUTSTOCK_KIND_REQUEST = 40;
    // 调拨出库
    public static final Integer OUTSTOCK_KIND_TRANSFER = 10;
    // 手工出库
    public static final Integer OUTSTOCK_KIND_MANUAL = 20;
    // 科室出库消耗
    public static final Integer OUTSTOCK_KIND_CONSUME = 30;

    public static final Integer OUTSTOCK_KIND_RETURN = 50;

    //出库单明细表状态
    public static final Integer OUTSTOCK_LIST_STATUS_SVAE=0;            //默认状态

    public static final Integer OUTSTOCK_LIST_STATUS_SETTLE=10;         //已结算



}
