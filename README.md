# SpringInAndroid

## 功能

1、本库内置了开发中常用的一些注解供你使用。

2、本库有xml+注解和xml两种配置方式，推荐使用xml+注解的配置方式。

3、本库支持二次开发，让你自己定义注解以及xml标签规则。

4、本库目前只有java代码版本，对于kotlin代码的支持后序更新。

5、本库使用pull解析方式解析xml文件，更加高效安全。

6、本库不支持循环依赖(出现循环依赖会直接抛出异常)，编写代码的时候还请注意。

7、本项目内置了一些场景下的解耦案例，后序会更新更多场景下的解耦案例。

8、本库支持可与其他框架(如mvp,mvc,mvvm等)配合使用，解耦效果更佳。

9、本库后期将和其他类型的框架进行整合，致力于实现springboot开箱即用的效果。

### 版本限制

最低Gradle版本：7.6（支持8.0以上）

最低SDK版本：minSdkVersion >= 21

## 使用步骤

由于JCenter账号没有注册成功，目前只支持下载到本地使用

### 一、进行配置，下边两种方式二选一（必须）


#### 方式一：xml+注解（推荐）

在 xml 文件中配置需要扫描的目录，使用注解对该目录下的相关信息进行标注。
#####  本库内置了一些功能注解和标签可供你直接使用（根据项目实际情况可自行扩展）

######  注解
| 注解名称             |                                                参数说明                                                 |                                        功能说明                             |
|------------------|:---------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------:|
| @Autowired       |                                      boolean required() default true                                |          实现自动装配，通过这个注解，IOC 容器可以自动地将所需的依赖注入到被标注的字段入                        |
| @Component       |                                      String value() default ""                                      |                              IOC 容器会将标记类的实例作为一个 Bean 管理                                 |
| @Primary         |                                      无参数                                                          |                       当存在多个相同类型的 bean 时，应该优先选择哪一个进行自动装配                    |
| @Qualifier       |                                      String value() default ""                                       |                              指定要注入的具体 bean                                           |
| @Scope           |                                      String value() default BeanDefinition.SCOPE_SINGLETION          |                                  定义 Bean 的作用域                                           |
| @Value           |                                      String value()                                                  |                            将外部的值注入到 Bean 的方法参数或构造器参数中              |


######  xml标签
| 标签名称             |                                                属性说明                                                 |                                        功能说明                             |
|----------------------|:---------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------:|
| beans              |                                                        无                                              |                                   用于定义和配置 应用程序上下文中的 bean                                  |
| bean               |                                          id(指定 Bean 的唯一标识符),class(指定 Bean 的实现类)           |                                            用于定义一个  Bean                                            |
| constructor-arg    |                                       name(参数名称),value(注入基本类型参数值),ref(注入依赖)          |                                    指定通过构造函数注入依赖
                     |                                                                                             
| component-scan     |                                       base-package(指定扫描组件的基础包路径)                           |                                指定要注入的具体 bean                               



#### 方式二：纯xml 方式（不推荐）

直接xml文件中配置所有信息

### 二、项目实战
#### 1、假设我们有一个从各种来源获取数据的应用程序，我们必须解析不同类型的文件，比如解析CSV文件和JSON文件。
##### 传统写法：
定义一个接口
```java
public interface Parser {
    List parse(Reader r);
}
```

根据不同文件类型有不同的实现类。
```java
public class JSONParser implements Parser {
    @Override
    public List parse(Reader r) {
        Log.d("test__", "JSONParser解析");
        return null;
    }
}
```
```java
public class CSVParser implements Parser{
    @Override
    public List parse(Reader r) {
        Log.d("test","CSVParser解析");
        return null;
    }
}
```
客户端进行调用，可以明显发现解析器与客户端调用耦合严重
```java
public class Client {
    private Parser csvParser, jsonParser;

    public Client(Parser csvParser, Parser jsonParser) {
        this.csvParser = csvParser;
        this.jsonParser = jsonParser;
    }

    Reader reader;

    public List getAll(String contentType) {

        switch (contentType) {
            case "CSV":
                return csvParser.parse(reader);
            case "JSON":
                return jsonParser.parse(reader);
        }

        return null;
    }

}
```

使用本库后可写成：
```java
@Component
public class Client {

    @Autowired
    Map<String, Parser> parserMap;

    Reader reader;

    public List getAll(String contentType) {
        return parserMap.get(contentType).parse(reader);
    }

}
```
客户端与解析器实现解耦，具体实现细节可参考项目或者《SpringInAndroid使用说明》。



