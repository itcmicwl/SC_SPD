export const PURCHASE_STATUS = {
    "10": { text: '已保存', index: 1 },
    "20": { text: '已提交', index: 2 },
    "30": { text: '部分确认', index: 3 },
    "40": { text: '全部确认', index: 4 },
    "50": { text: '已配送', index: 5 },
    "60": { text: '已完成', index: 6 },
    "70": { text: '已关闭', index: 6 }
};
export const PURCHASE_STATUS_SELECT = [
    { value:'',label: '全部'},
    { value:'10',label: '已保存'},
    { value:'20',label: '已提交'},
    { value:'30',label: '部分确认'},
    { value:'40',label: '全部确认'},
    { value:'50',label: '已配送'},
    { value:'60',label: '已完成'},
    { value:'70',label: '已关闭'}   
];
export const PURCONFIRM_STATUS = {
    "10": { text: '待提交', index: 1 },
    "20": { text: '待配送', index: 2 },
    "30": { text: '部分配送', index: 3 },
    "40": { text: '全部配送', index: 4 }

};
export const DISTRBILL_STATUS = {
    "10": { text: '待提交', index: 1 },
    "20": { text: '已配送', index: 2 },
    "30": { text: '部分收货', index: 3 },
    "40": { text: '全部收货', index: 4 }
};
export const PURCHASE_KIND = {
    "0": { text: '手工采购', index: 1 },
    "1": { text: '请购采购', index: 2 }
};
export const BUY_KIND = [
    {value:10,text:"实物采购",flag:false},
    {value:20,text:"虚拟采购",flag:false},
    {value:30,text:"办公用品",flag:false}];

export const BILL_TYPE = {
    "1": { text: '请购单', index: 1 },
    "2": { text: '采购单', index: 2 },
    "3": { text: '确认单', index: 3 },
    "4": { text: '配送单', index: 4 },
    "5": { text: '入库单', index: 5 },
    "6": { text: '出库单', index: 6 }
};
