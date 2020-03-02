# zyproject
基于springcloud 简单微服务管理系统


# 一、开发框架

底层开发框架：SpringBoot2.2、SpringCloud2.2

消费者客户端：feign

数据库：mysql5.6

JDK：1.8

IDE：Intellj IDEA

开发环境：win10X64，另安装一台虚拟机CentOS7部署consul和redis

其他：lomok、spring security、quartz等

# 二、注册中心

可选择的有：eureka、consul、Nacos、zookeeper、Redis等。本项目选择consul。

思考：eureka目前已闭源，理论上不会有太大影响。研究完consul以后发现ACL配置、服务健康检测貌似更完善点。且更换配置中心，看起来也不是太麻烦：1）更改pom引用。2）更改yml（或properties配置）3）更改springboot启动入口注解@Enablexxxxx。没有选择SpringCloudAlibaba也就没有仔细研究Dubbo+zookeeper这一套框架了。

参考文章：https://segmentfault.com/a/1190000016677665?utm_source=tag-newest

# 三、分布式配置中心

　　可选择的有：SpringCloud Config、apollo等，阿里和百度的配置中心没有去研究了。本期目选择：springcloud config

　　 选型时，看到有的文章说config主要支持git（或svn）方式部署，需要额外的版本控制服务器配置，更偏向于携程的apollo。经过研究，config一样支持数据库部署（jdbc）或本地文件配置，目前已成功将配置信息保存至MySQL。

　　参考文章：https://blog.csdn.net/forezp/article/details/87866560

　　这里有篇文章，详细对比了各类开源配置中心的优缺点：https://blog.csdn.net/zollty/article/details/85166149

# 四、微服务保护框架

　　Hystrix实现服务出现雪崩效应时的降级、隔离、熔断，保护整个服务的持续可用。

　　参考文章：https://www.cnblogs.com/xiong2ge/p/hystrix_faststudy.html

# 五、消息中间件

　　SpringCloudStream集成了RabbitMQ和kafka，而且切换起来比较简单，屏蔽了创建交换机、路由key、队列等技术细节。你所要做的就是创建通道、绑定、发布，消费者绑定、订阅。从Rabbit切换到kafka也只需要更改依赖、启动入口就可以了。

　　参考文章：https://www.cnblogs.com/leeSmall/p/8900518.html

# 六、网关

　　zuul实现反向代理、接口安全过滤等功能。token算法计划还是放到Common项目里去实现，保障接口调用安全，同时各微服务项目也可以引用，实现安全调用。如果说从开发和调试方便程度来说，放在网关侧实现更简单，日常开发调试可以绕过token机制，直接在浏览器或postman中进行验证，部署时微服务只允许内网访问，也可以 一定程度保障接口安全，但是害怕后期修改起来麻烦。犹豫中。

　　网关层的负载和高可用，计划通过Nginx和浮动IP，再加上keepalive、heartbeat等实现宕机后IP自动浮动到另一台服务器。至于WEB项目中的附件使用共享存储。说实话，操作系统层面高可用，这一块我不是很懂，不多说了，交给专业的人士去做。

　　zuul参考：https://www.cnblogs.com/leeSmall/p/8850215.html

　　高可用参考：https://www.cnblogs.com/zhouyu629/p/linux%E9%AB%98%E5%8F%AF%E7%94%A8%E9%9B%86%E7%BE%A4(HA)%E5%8E%9F%E7%90%86%E8%AF%A6%E8%A7%A3

# 七、API管理工具

　　swagger2.0实现restful API接口文档，减少与客户端、微信端等沟通成本。

　　参考文章：https://blog.csdn.net/qq_37338761/article/details/82259444

# 八、Docker部署

　　还没研究
  
 具体编码过程参考：http://blog.pianhuangw.com
 
![登录页](http://blog.pianhuangw.com/wp-content/uploads/2020/03/login-1024x441.png)
![系统首页](http://blog.pianhuangw.com/wp-content/uploads/2020/02/QQ%E6%88%AA%E5%9B%BE20200228210937-3.png)
![角色](http://blog.pianhuangw.com/wp-content/uploads/2020/03/QQ%E6%88%AA%E5%9B%BE20200226140052-1024x320.png)
![角色权限](http://blog.pianhuangw.com/wp-content/uploads/2020/03/rolepermission-1024x457.png)
![用户](http://blog.pianhuangw.com/wp-content/uploads/2020/03/adduser-1024x458.png)
 
