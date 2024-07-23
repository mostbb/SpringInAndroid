项目介绍：SpringInAndroid是一个专属于Android的ioc框架，该框架借鉴了spring的思想，提供了一种新的android开发方式,
让android开发变得像crud一样简单，旨在帮助后端开发人员更好的上手Android，帮助Android开发人员更好的解耦Android端的程序，
提升开发效率，使用者也可根据实际项目的情况进行二次开发，定义自己的规则。


功能：
1、本库内置了开发中常用的一些注解供你使用。

2、本库有xml+注解和xml两种配置方式，推荐使用xml+注解的配置方式。

3、本库支持二次开发，让你自己定义注解以及xml标签规则。

4、本库目前只支持java代码，对于kotlin代码的支持后序更新。

5、本库使用pull解析方式解析xml文件，更加高效安全。

6、本库使用异步线程扫描注解和解析xml文件，不会阻塞UI线程。

7、本项目内置了一些场景下的解耦案例，后序会更新更多场景下的解耦案例。

8、本库支持可与其他框架(如mvp,mvc,mvvm等)配合使用，解耦效果更佳。

9、本库后期将和其他类型的框架进行整合，致力于实现springboot开箱即用的效果。



通过配置文件描述Bean及Bean之间的依赖关系，利用java语言的反射功能实例化Bean并建立Bean之间的依赖关系。

Bean实例缓存、生命周期管理、Bean实例代理、事件发布、资源装载

ioc5种不同方式的自动装配


1、如何使用：
注册bintray.com账号暂时没有成功，只能通过下载的方式使用。

解耦原理：通过将对象的创建过程交给ioc容器，从而实现解耦。

具体在项目中应用参考mvpproject、mvcproject、mvvmproject


2、配置Bean方式：
1、基于xml文件实现Bean配置
例如：
<bean id="boy" class="com.example.Boy">
<property name="name" value="example"/>
<property name="id" value="1"/>
</bean>
<bean id="myBean" class="com.example.MyBean">
<property name="name" value="example"/>
<property name="id" value="1"/>
<property name="boyfriend" ref="boy"/>
</bean>

2、基于注解+xml实现Bean配置
@Component 实例化Bean
@Autowired spring内置注解，默认注入方式为byType(根据类型进行匹配)，也就是说优先根据接口类型去匹配并且注入Bean
@Qualifier 当有多个实现类的情况下，确定哪一个实现类
@Bean 可将第三方库中的类装配到ioc容器
<context:component-scan base-package="com.example"/>


注意：无论基于哪种形式都要保证与DI相关的类都应在IOC容器中。使用注解形式则默认产生的BeanName为类名首字母小写，比如Person类生成beanname为person


使用注解后，产生的BeanName默认名称为类名首字母小写


二次开发必读

解惑：
为什么使用pull解析？
原生spring使用DOM解析器会将整个XML文件加载到内存中并构建树形结构，适合处理较小的XML文件。
但pull解析采用一行行读取数据的方式更加高效,便捷。

在获取文件时Android原生方式遍历dex文件，避免使用第三方依赖。实际上整个项目在后序开发中也应避免使用第三方依赖，借此降低项目的不确定性。

注解的定义与处理和xml的解析在主线程之外，防止阻塞UI线程。

为什么使用ClassLoader而不使用Class.forName？
ClassLoader不解析不初始化类，并且提高了类的加载速度。Class.forName对类进行了初始化。

为什么在子线程中对数据进行加载线程？
如果在主线程中加载会阻塞UI线程。



可扩展方向
IOC容器方便解耦，简化开发 （已实现）
持久层的ORM（未实现）
AOP编程未实现
申明式事务未实现
暂未整合其他框架










