package gyqx.spdherp.sms;

import gyqx.spdherp.sms.service.SmsUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Auther Liangwu
 * @Date 18-1-25 上午9:53
 */
@RunWith(SpringJUnit4ClassRunner.class) // 使用junit4进行测试
@ContextConfiguration({ "classpath:configs/spring-context.xml"})
public class SmsTest {
    @Autowired
    private SmsUtils smsUtils;

    @Test
    public void test() {
        smsUtils.sendToMQ("qg10", "HSBY-SPD:user-00275", "HSBY-SPD:org-154", null, null);
    }
}
