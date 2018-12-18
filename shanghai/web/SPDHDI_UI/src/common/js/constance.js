
export const fileServer = {
  upload: '/platformService/sys/upload/file/',
  download: '/upload/',
  imgBasePath: '/upload/'
}

export const companyKind = {
  HOS: "1",
  PROV: "2",
  MFRS: "3",
  MFRS_PROV: "4"
};
export const comKind ={
  '1':'医院',
  '2':'供应商',
  '3':'厂家',
  '4':'厂供一体'
}

// 出库单状态
export const OUTSTOCK_STATUS = {
  '90': { text: '保存' },
  '10': { text: '提交' },
  '20': { text: '审核' },
  '30': { text: '记帐' },
  '40': { text: '作废' },
  '50': { text: '驳回' },
  '60': { text: '入库' },
  '70': { text: '结算' }
}

export const OUTSTOCK_BILL_STATUS = {
  'SAVE': { text: '保存', value: '90' },
  'SUBMIT': { text: '提交', value: '10' },
  'CHECKED': { text: '审核', value: '20' },
  'ACCOUNT': { text: '记帐', value: '30' },
  'ABOLISH': { text: '作废', value: '40' },
  'REJECT': { text: '驳回', value: '50' },
  'INSTOCK': { text: '入库', value: '60' },
  'SETTLEMENT': { text: '入库', value: '80' }
}

// 出库类型
export const OUTSTOCK_KIND = {
  '40': { text: '请购出库' },
  '10': { text: '调拨出库' },
  '20': { text: '手工出库' },
  '30': { text: '科室出库' },
  '50': { text: '退货出库' }
}

export const OUTSTOCK_BILL_KIND = {
  'REQUEST': { text: '请购出库', value: '40' },
  'TRANSFER': { text: '调拨出库', value: '10' },
  'MANUAL': { text: '手工出库', value: '20' },
  'CONSUME': { text: '科室出库', value: '30' },
  'RETURNS': { text: '退货出库', value: '50' }
}

// 出库方式
export const OUTSTOCK_TYPE = {
  'PURCHASE': { text: '实购', value: 10 },
  'PREORDER': { text: '预购', value: 20 }
}

// 请购单主表状态
export const DEPTBUY_MAIN_STATUS = {
  '10': { text: '已保存', index: 1 },
  '20': { text: '已提交', index: 2 },
  '30': { text: '处理中', index: 3 },
  '40': { text: '已处理', index: 4 }

}
// 请购单子表状态
export const DEPTBUY_SUB_STATUS = {
  '10': { text: '待处理', index: 1 },
  '20': { text: '已驳回', index: 2 },
  '30': { text: '不采购', index: 3 },
  '40': { text: '部分配送', index: 4 },
  '50': { text: '全部配送', index: 5 },
  '60': { text: '已采购', index: 6 }
}
//出库单状态
export const DISTRBILL_STATUS = {
  "10": { text: '待提交', index: 1 },
  "20": { text: '已配送', index: 2 },
  "30": { text: '部分收货', index: 3 },
  "40": { text: '全部收货', index: 4 }
};
//结算单状态
export const SETTLE_STATUS = {
  "10": { text: '已保存', index: 1 },
  "20": { text: '已审核', index: 2 },
  "30": { text: '已上传', index: 3 },
  "40": { text: '已确认', index: 4 }
};

// 报告来源
export const REPORT_SOURCE1 = {
    "1": { text: '使用单位', index: 1 },
    "2": { text: '经营企业', index: 2 },
    "3": { text: '生产企业', index: 3 },
    "4": { text: '个人', index: 4 }
};
// 报告来源
export const REPORT_SOURCE = [
    { text: '使用单位', value: '1' },
    { text: '经营企业', value: '2' },
    { text: '生产企业', value: '3' },
    { text: '个人', value: '4' }
];
// 报告人类别
export const REPORTER_TYPE = {
    "1": { text: '医师', index: 1 },
    "2": { text: '技师', index: 2 },
    "3": { text: '护士', index: 3 },
    "4": { text: '工程师', index: 4 },
    "5": { text: '其它', index: 5 }
};
// 报告人类别
export const EVENT_RESULT = {
    "1": { text: '死亡', index: 1 },
    "2": { text: '危及生命', index: 2 },
    "3": { text: '机体功能结构永久性损伤', index: 3 },
    "4": { text: '可能导致机体功能结构永久性损伤', index: 4 },
    "5": { text: '需要内、外科治疗避免上述永久损伤', index: 5 },
    "6": { text: '其它', index: 6 }
};
// 医疗器械使用场所
export const APPARATUS_WHEREUSED = {
    "1": { text: '医疗机构', index: 1 },
    "2": { text: '家庭', index: 2 },
    "3": { text: '其它', index: 3 }
};
// 事件报告状态
export const EVENTREPORT_STATUS = {
    "1": { text: '已通知使用单位', index: 1 },
    "2": { text: '已通知生产企业', index: 2 },
    "3": { text: '已通知经营企业', index: 3 },
    "4": { text: '已通知药监部门', index: 4 }
};
  // K科室商品申请审核状态
export const APPLY_STATUS = {
  '0': { text: '初始' },
  '10': { text: '已提交' },
  '20': { text: '已批准' },
  '30': { text: '已驳回' },
  '40': { text: '已阅' }
};

// 供应商证照类型（类型2空缺，不管）
export const PROV_CERT_TYPE = [
  {id:'1',name:'营业执照'},
  {id:'3',name:'税务登记证'},
  {id:'4',name:'组织机构代码证'},
  {id:'5',name:'经营许可证'},
  {id:'6',name:'经营备案证'},
  {id:'7',name:'生产许可证'},
  {id:'8',name:'生产备案证'}
];
