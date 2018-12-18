
export const fileServer = {
  upload: '/platform/sys/upload/file/',
  download: '/upload/',
  imgBasePath: '/upload/'
}

// 出库单状态
export const OUTSTOCK_STATUS = {
  '90': { text: '保存' },
  '10': { text: '提交' },
  '20': { text: '审核' },
  '30': { text: '记帐' },
  '40': { text: '作废' },
  '50': { text: '驳回' },
  '60': { text: '入库' }
}

export const OUTSTOCK_BILL_STATUS = {
  'SAVE': { text: '保存', value: '90' },
  'SUBMIT': { text: '提交', value: '10' },
  'CHECKED': { text: '审核', value: '20' },
  'ACCOUNT': { text: '记帐', value: '30' },
  'ABOLISH': { text: '作废', value: '40' },
  'REJECT': { text: '驳回', value: '50' },
  'INSTOCK': { text: '入库', value: '60' }
}

// 出库类型
export const OUTSTOCK_KIND = {
  '40': { text: '请购出库' },
  '10': { text: '调拨出库' },
  '20': { text: '手工出库' },
  '30': { text: '科室出库' }
}

export const OUTSTOCK_BILL_KIND = {
  'REQUEST': { text: '请购出库', value: '40' },
  'TRANSFER': { text: '调拨出库', value: '10' },
  'MANUAL': { text: '手工出库', value: '20' },
  'CONSUME': { text: '科室出库', value: '30' }
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
  "30": { text: '已确认', index: 3 }
};
