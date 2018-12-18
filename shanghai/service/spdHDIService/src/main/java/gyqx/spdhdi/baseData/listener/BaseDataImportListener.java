package gyqx.spdhdi.baseData.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.utils.jms.JMSSendUtils;
import gyqx.spdhdi.baseData.service.BasGoodsImportMsgService;
import gyqx.spdhdi.baseData.vo.BasGoodsImportMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author liangwu
 * @Date 12/14/18 11:10 AM
 */
@Component
@Slf4j
public class BaseDataImportListener {
    @Resource
    private BasGoodsImportMsgService service;

    @Resource
    private JMSSendUtils jmsSendUtils;

    @JmsListener(destination = "base.imported")
    public void receiveMessage(String msg) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BasGoodsImportMsg basGoodsImportMsg = objectMapper.readValue(msg, BasGoodsImportMsg.class);
            service.handleMsg(basGoodsImportMsg);
        } catch (Exception e) {
            jmsSendUtils.send("base.imported.fail", msg, null);
            log.error("handle msg error", e);
        }
    }
}
