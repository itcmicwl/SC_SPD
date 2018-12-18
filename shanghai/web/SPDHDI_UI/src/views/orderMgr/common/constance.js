export const PURCHASE_STATUS = {
    "10": { text: '已保存', index: 1 },
    "20": { text: '已提交', index: 2 },
    "30": { text: '部分确认', index: 3 },
    "40": { text: '全部确认', index: 4 },
    "50": { text: '已配送', index: 5 },
    "60": { text: '已完成', index: 6 },
    "70": { text: '关闭', index: 7 }
};

export const PURCHASE_STATUS_SELECT = [
    {value:'20',label:'已提交'},
    {value:'30',label:'部分确认'}
];

export const PURCONFIRM_STATUS = {
    "10": { text: '待提交', index: 1 },
    "20": { text: '待配送', index: 2 },
    "30": { text: '部分配送', index: 3 },
    "40": { text: '全部配送', index: 4 }

};

export const PURCONFIRM_STATUS_SELECT = [
    {value:'',label:'全部'},
    {value:'10',label:'待提交'},
    {value:'20',label:'待配送'},
    {value:'30',label:'部分配送'},
    {value:'40',label:'全部配送'}
];

export const DISTRBILL_STATUS = {
    "10": { text: '待提交', index: 1 },
    "11": { text: '驳回改价', index: 2 },
    "12": { text: '待转换', index: 3 },
    "20": { text: '已配送', index: 4 },
    "25": { text: '已验收', index: 5 },
    "30": { text: '部分收货', index: 6 },
    "40": { text: '全部收货', index: 7 }

};

