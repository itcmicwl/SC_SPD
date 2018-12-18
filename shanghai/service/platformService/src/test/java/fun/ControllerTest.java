package fun;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gyqx.platform.sys.controller.LoginController;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:configs/*.xml" })
public class ControllerTest {

	// 模拟request,response  
    private MockHttpServletRequest request;  
    private MockHttpServletResponse response;   
      
    // 注入loginController  
    @Autowired  
    private LoginController loginController ;  
      
    // 执行测试方法之前初始化模拟request,response  
	@Before
	public void setUp() throws Exception {
		request = new MockHttpServletRequest();      
        request.setCharacterEncoding("UTF-8");      
        response = new MockHttpServletResponse();      
	}

	@Test
	public void test() {
		try {  
        	request.setParameter("userName", "admin");
        	request.setParameter("password", "2");
           // assertEquals("login",loginController.loginIn(request,response)) ;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}

}
