import axios from '../../config/axiosConfig';
let barcodeUtil = {
    parseBarCodeByMainCode: function (barcode,hosId,provId){
        var param = {hosId: hosId, provId: provId, barcode: barcode};
        return axios.post('/spdHERPService/baseData/basBarcode/parseBarcode', param);
    },
    parseCombinedBarcode: function (barcode,slaverBarcode,hosId,provId){
        var param = {hosId: hosId, provId: provId, barcode: barcode,slaverBarcode:slaverBarcode};
        return axios.post('/spdHERPService/baseData/baseDataMaint/BarcodeParse/parseBarcodeCombine', param);
    }
}
export default barcodeUtil;