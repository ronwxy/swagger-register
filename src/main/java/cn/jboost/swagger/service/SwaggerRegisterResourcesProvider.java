/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package cn.jboost.swagger.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/***
* 提供基于注册的swagger resources
* @Author ronwxy
* @Date 2019/6/28 10:57
*/
@Component
@Primary
public class SwaggerRegisterResourcesProvider implements SwaggerResourcesProvider {

    Logger logger= LoggerFactory.getLogger(SwaggerRegisterResourcesProvider.class);

    @Autowired
    private SwaggerRegisterService registerService;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        for(Map.Entry<String, String> entry : registerService.getRegisterMap().entrySet()){
            resources.add(swaggerResource(entry.getKey(), entry.getValue()));
        }
        Collections.sort(resources);
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        logger.info("name:{},location:{}",name,location);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
