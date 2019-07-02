# swagger-register
swagger api 注册服务，支持多项目swagger api文档集中管理


### docker运行方式
1. 构建镜像
```shell
mvn clean package -Dmaven.test.skip=true dockerfile:build
```

2. 运行
```shell
docker run -d --name swagger-register -p 11090:11090 -v /home/jenkins/swagger-register/register-data:/register-data -v /home/jenkins/swagger-register/logs:/logs --restart=always springboot/swagger-register:latest
```