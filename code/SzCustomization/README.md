该Springboot Demo工程集成了如下组件，Jdk为1.8。
1、	mybatis
2、	swagger2在线文档
启动后打开http://localhost:8080/swagger-ui.html#/
3、	fastjson组件统一封装返回接口json
4、	数据库（oracle与pgsql）
注：Demo里的连接数据库为公共测试库
5、	数据库分页
参见DemoController里/ findObjectsByPage接口
6、	Websocket
\websocket\DemoWebsocket.java
7、	Timertask
\timertask\DemoTimerTask.java
8、	async异步方法
参见DemoController里/testAsync接口
9、	统一结果返回
参见DemoController里的HttpResult
10、统一异常处理
参见DemoController里/ testException接口
实现方法\aop\ControllerException.java
11、日志
使用的是log4j框架
log4j.properties里有日志写入数据库的例子
12、实体类
application.yml里已配置了实体类完整路径的前缀，因此在Mapper Xml里可简写实体类的名称
基类BaseModel，里面有sys_uuid属性，可在Mapper Xml里写关键字参数；以后会添加相关通用属性如当前时间，当前时间片，当前登录用户信息等
13、Http请求调用框架RestTemplate
参见DemoController里/ testRest接口
可参见https://blog.csdn.net/cowbin2012/article/details/85250855
或https://my.oschina.net/Mkeeper/blog/2054351

14、待续

关于私服，请参见pom.xml，已设置为http://maven.sutpc.cc/repository/public/
