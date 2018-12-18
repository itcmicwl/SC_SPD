package gyqx.mdqc.mycerts.controller;

import common.db.query.QueryResult;
import common.web.AjaxResult;
import gyqx.mdqc.mycerts.service.CertAlertService;
import gyqx.mdqc.mycerts.vo.BasRegistrationInfoVo;
import gyqx.mdqc.mycerts.vo.JxsqsData;
import gyqx.spdhdi.myInfos.service.ICompanyVoService;
import gyqx.spdhdi.myInfos.vo.CompanyCertificateVo;
import gyqx.spdhdi.mycerts.service.*;
import gyqx.spdhdi.mycerts.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@RequestMapping("/certAlert")
public class CertAlertControl {

    @Resource
    private ProvCnsService provCnsService;

    @Resource
    private ProvFrwtsService provFrwtsService;

    @Resource
    private ProvSalesRepService provSalesRepService;

    @Resource
    private ProvIncorruptAgrmtService provIncorruptAgrmtService;

    @Resource
    private CertAlertService certAlertService;
    @GetMapping("/getCnsListByLoginComp")
    @ResponseBody
    public AjaxResult<QueryResult<ProvCnsVo>> getCnsListByLoginComp(@RequestParam(value = "start", required = false) Date start,
            @RequestParam(value = "end", required = false) Date end,
            @RequestParam(value = "compKind", defaultValue = "") String compKind,
            @RequestParam(value = "aboutName", defaultValue = "") String aboutName,
            @RequestParam(value = "pageNum") int pageNum,
            @RequestParam(value = "pageSize") int pageSize) {
        AjaxResult<QueryResult<ProvCnsVo>> result = new AjaxResult<>();
        result.setData(provCnsService.getCnsListByLoginComp(compKind,start, end, aboutName, pageNum, pageSize));
        return result;
    }

    @GetMapping("/getFrwtsListByLoginComp")
    @ResponseBody
    public AjaxResult<QueryResult<ProvFrwtsVo>> getFrwtsListByLoginComp(@RequestParam(value = "start", required = false) Date start,
                                                                    @RequestParam(value = "end", required = false) Date end,
                                                                    @RequestParam(value = "compKind", defaultValue = "") String compKind,
                                                                    @RequestParam(value = "aboutName", defaultValue = "") String aboutName,
                                                                    @RequestParam(value = "pageNum") int pageNum,
                                                                    @RequestParam(value = "pageSize") int pageSize) {
        AjaxResult<QueryResult<ProvFrwtsVo>> result = new AjaxResult<>();
        result.setData(provFrwtsService.getFrwtsListByLoginComp(compKind,start, end, aboutName, pageNum, pageSize));
        return result;
    }

    @GetMapping("/getSalesRepListByLoginComp")
    @ResponseBody
    public AjaxResult<QueryResult<ProvSalesRepVo>> getSalesRepListByLoginComp(@RequestParam(value = "start", required = false) Date start,
                                                                    @RequestParam(value = "end", required = false) Date end,
                                                                    @RequestParam(value = "compKind", defaultValue = "") String compKind,
                                                                    @RequestParam(value = "aboutName", defaultValue = "") String aboutName,
                                                                    @RequestParam(value = "pageNum") int pageNum,
                                                                    @RequestParam(value = "pageSize") int pageSize) {
        AjaxResult<QueryResult<ProvSalesRepVo>> result = new AjaxResult<>();
        result.setData(provSalesRepService.getSalesRepListByLoginComp(compKind,start, end, aboutName, pageNum, pageSize));
        return result;
    }

    /**
     * 廉政协议书
     * @param start
     * @param end
     * @param compKind
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/getIncorruptAgrmtListByLoginComp")
    @ResponseBody
    public AjaxResult<QueryResult<ProvIncorruptAgrmtVo>> getIncorruptAgrmtListByLoginComp(@RequestParam(value = "start", required = false) Date start,
                                                                    @RequestParam(value = "end", required = false) Date end,
                                                                    @RequestParam(value = "compKind", defaultValue = "") String compKind,
                                                                    @RequestParam(value = "aboutName", defaultValue = "") String aboutName,
                                                                    @RequestParam(value = "pageNum") int pageNum,
                                                                    @RequestParam(value = "pageSize") int pageSize) {
        AjaxResult<QueryResult<ProvIncorruptAgrmtVo>> result = new AjaxResult<>();
        result.setData(provIncorruptAgrmtService.getIncorruptAgrmtListByLoginComp(compKind,start, end, aboutName, pageNum, pageSize));
        return result;
    }

    @GetMapping("/listRegCertByLoginComp")
    @ResponseBody
    public AjaxResult<QueryResult<BasRegistrationInfoVo>> listRegCertByLoginComp(@RequestParam(value = "start", required = false) Date start,
                                                                                          @RequestParam(value = "end", required = false) Date end,
                                                                                          @RequestParam(value = "isOld", defaultValue = "") String isOld,
                                                                                          @RequestParam(value = "aboutName", defaultValue = "") String aboutName,
                                                                                          @RequestParam(value = "compKind", defaultValue = "") String compKind,
                                                                                          @RequestParam(value = "pageNum") int pageNum,
                                                                                          @RequestParam(value = "pageSize") int pageSize) {
        AjaxResult<QueryResult<BasRegistrationInfoVo>> result = new AjaxResult<>();
        result.setData(certAlertService.listRegCertByCompId(start, end, isOld, aboutName, compKind,pageNum, pageSize));
        return result;
    }

    @GetMapping("/listSqsByLoginComp")
    @ResponseBody
    public AjaxResult<QueryResult<JxsqsData>> listSqsByLoginComp(@RequestParam(value = "start", required = false) Date start,
                                                                 @RequestParam(value = "end", required = false) Date end,
                                                                 @RequestParam(value = "aboutName", defaultValue = "") String aboutName,
                                                                 @RequestParam(value = "compKind", defaultValue = "") String compKind,
                                                                 @RequestParam(value = "pageNum") int pageNum,
                                                                 @RequestParam(value = "pageSize") int pageSize) {
        AjaxResult<QueryResult<JxsqsData>> result = new AjaxResult<>();
        result.setData(certAlertService.listSqsByLoginComp(start, end, aboutName, compKind,pageNum, pageSize));
        return result;
    }

    @GetMapping("/listProvCertByLoginComp")
    @ResponseBody
    public AjaxResult<QueryResult<CompanyCertificateVo>> listProvCertByLoginComp(@RequestParam(value = "start", required = false) Date start,
                                                                 @RequestParam(value = "end", required = false) Date end,
                                                                 @RequestParam(value = "aboutName", defaultValue = "") String aboutName,
                                                                 @RequestParam(value = "compKind", defaultValue = "") String compKind,
                                                                 @RequestParam(value = "pageNum") int pageNum,
                                                                 @RequestParam(value = "pageSize") int pageSize) {
        AjaxResult<QueryResult<CompanyCertificateVo>> result = new AjaxResult<>();
        result.setData(certAlertService.listProvCertByLoginComp(start, end, aboutName, compKind,pageNum, pageSize));
        return result;
    }

    @GetMapping("/listMfrsCertByProv")
    @ResponseBody
    public AjaxResult<QueryResult<ProvMfrsCertificateVo>> listMfrsCertByProv(@RequestParam(value = "start", required = false) Date start,
                                                                             @RequestParam(value = "end", required = false) Date end,
                                                                             @RequestParam(value = "aboutName", defaultValue = "") String aboutName,
                                                                             @RequestParam(value = "compKind", defaultValue = "") String compKind,
                                                                             @RequestParam(value = "pageNum") int pageNum,
                                                                             @RequestParam(value = "pageSize") int pageSize) {
        AjaxResult<QueryResult<ProvMfrsCertificateVo>> result = new AjaxResult<>();
        result.setData(certAlertService.listMfrsCertByProv(start, end, aboutName, compKind,pageNum, pageSize));
        return result;
    }

    @GetMapping("/listCompCertById")
    @ResponseBody
    public AjaxResult<QueryResult<CompanyCertificateVo>> listCompCertById(@RequestParam(value = "start", required = false) Date start,
                                                                             @RequestParam(value = "end", required = false) Date end,
                                                                             @RequestParam(value = "aboutName", defaultValue = "") String aboutName,
                                                                             @RequestParam(value = "compKind", defaultValue = "") String compKind,
                                                                             @RequestParam(value = "pageNum") int pageNum,
                                                                             @RequestParam(value = "pageSize") int pageSize) {
        AjaxResult<QueryResult<CompanyCertificateVo>> result = new AjaxResult<>();
        result.setData(certAlertService.listCompCertById(start, end, aboutName, pageNum, pageSize));
        return result;
    }
}
