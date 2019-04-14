# sm
基于Spring + Servlet + JSTL + jsp + Mybatis的员工信息管理系统。
系统中存在两种系统，需要同时用到IOC容器和web容器（tomcat）。每次初始化Servlet容器时，都会在DispatcherServlet中初始化一个IOC容器，将ApplicationContext。
如员工管理，部门管理，个人管理（密码更改和个人信息的显示）。
定义核心的DispatcheServlet类，来处理用户的所有请求，并在其中利用反射来执行不同的Controller中的方法，Controller是从IOC容器中通过beanName取出的，
而beanName是通过访问路径获得的。如访问路径是 http://localhost:8080/sm/staff/list.do，则控制器是staffController，方法是list。使用DispatcherServlet
过滤掉所有的以*.do为后缀的访问请求。对于所以的访问都要进行过滤，以确定用户是否已经登陆，所以定义了一个LoginFilter拦截器。
定义拦截器，来拦截请求。
如果用户登陆成功，则将用户信息保存在Session中，便于查找和管理。
使用spring的事务管理，实现对不符合要求的数据库操作事务进行回滚。
和基于注解方式和AspectJ的AOP开发（实现日志的记录，这里代码层面尚未实现）。使用注解@Aspect定义切面，使用@Before，@After和@AfterThrowing来定义
不同的通知类型。使用切入点pointcut结合通知来
完成织入形成切面，通过连接点JoinPoint来获取切入点出的类、方法、形式参数和形式参数中嵌入的参数的能，来实现记录系统日志，操作日志和登陆日志。
使用Mybatis完成持久化操作，每个实体类对应一个Dao接口，每个Dao接口对应一个mapper映射文件 ***.xml，采用在配置文件中写sql语句，避免直接在代码中通过
注解来编写sql语句带来的代码的杂乱。
