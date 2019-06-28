package cn.jboost.swagger;

import cn.jboost.swagger.service.SwaggerRegisterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SwaggerRegisterApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private SwaggerRegisterService swaggerRegisterService;

    @Test
    public void testRegister(){
        swaggerRegisterService.register("测试项目", "http://www.baidu.com");
    }

}
