package cn.jboost.swagger.controller;

import cn.jboost.swagger.service.SwaggerRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/***
 * 注册接口
 * @Author ronwxy
 * @Date 2019/6/28 11:35   
 */
@RestController
@RequestMapping("/swagger/register")
public class SwaggerRegisterController {

    @Autowired
    private SwaggerRegisterService registerService;

    @PostMapping
    public ResponseEntity<?> register(@RequestParam Map<String, String> registerInfoMap) {
        String project = registerInfoMap.get(SwaggerRegisterService.STORE_KEY_PROJECT);
        String url = registerInfoMap.get(SwaggerRegisterService.STORE_KEY_URL);
        Map<String, Object> body = new HashMap<>(2);
        body.put("success", false);
        if (project == null || url == null) {
            body.put("msg", "project or url can not be null");
            return ResponseEntity.badRequest().body(body);
        }
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            body.put("msg", String.format("url is an invalid url[%s]", url));
            return ResponseEntity.badRequest().body(body);
        }
        File newFile = registerService.register(project, url);
        if (newFile == null) {
            body.put("msg", "cache the url json file info failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
        body.put("success", true);
        return ResponseEntity.ok(body);
    }
}
