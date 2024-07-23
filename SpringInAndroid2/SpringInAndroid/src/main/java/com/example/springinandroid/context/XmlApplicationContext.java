package com.example.springinandroid.context;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.springinandroid.beans.BeanReference;
import com.example.springinandroid.beans.GenericBeanDefinition;
import com.example.springinandroid.beans.PreBuildBeanFactory;
import com.example.springinandroid.resource.Bean;
import com.example.springinandroid.resource.Constructor;
import com.example.springinandroid.resource.DefaultReadXmlFactory;
import com.example.springinandroid.resource.DefaultResourceFactory;
import com.example.springinandroid.resource.ReadXmlFactory;
import com.example.springinandroid.resource.ResourceFactory;
import com.example.springinandroid.util.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XmlApplicationContext extends AbstractApplicationContext {
    //private BeanDefinitionReader reader;

    PreBuildBeanFactory bf = new PreBuildBeanFactory();


    @RequiresApi(api = Build.VERSION_CODES.O)
    public XmlApplicationContext(int re, Context context) throws Throwable {
        super();

        // 加载解析注配置 生成BeanDefinition 并注册BeanFactory

        ResourceFactory resourceFactory = new DefaultResourceFactory();
        ReadXmlFactory readXmlFactory = new DefaultReadXmlFactory();

        //获取xml配置信息
        Map<String, Object> map_xml = readXmlFactory.xmlParse(resourceFactory.inputStream(re, context));

        List<Bean> beanList = (List<Bean>) map_xml.get("beans");//获取bean标签

        List<Constructor> constructorList = (List<Constructor>) map_xml.get("constructors");//获取构造方法



        GenericBeanDefinition bd = new GenericBeanDefinition();
        int j = 0;
        //循环注入
        for (int i = 0; i < beanList.size(); i++) {

            bd = new GenericBeanDefinition();
            //获取类
            List<Class<?>> classes = resourceFactory.getClassFromAbsolutePath(resourceFactory.getNameFromDex(context), beanList.get(i).getClass1());

            if (classes.size() > 0) {
                Objects.requireNonNull(classes, "该资源下没有目标文件");
            }

            bd.setBeanClass(classes.get(0));
            String beanname = generateBeanName(classes.get(0));

            //构造注入
            List<Object> args = new ArrayList<>();
            for (; j < java.util.Objects.requireNonNull(constructorList).size(); j++) {
                Constructor con = constructorList.get(j);
                if (con == null) {
                    j++;
                    break;
                }
                //判断是否为引用类型
                if (con.getValue().contains("_ref")) {
                    String name = con.getValue().substring(0, con.getValue().length() - 4);
                    if (checkRef(beanList, name)) {
                        args.add(new BeanReference(name));
                    } else {
                        throw new RuntimeException("引用类型没有注册");
                    }
                } else {
                    args.add(con.getValue());
                }

            }

            bd.setConstructorArgumentValues(args);
            bf.registerBeanDefinition(beanname, bd);
        }
        super.refresh();
    }

    private String generateBeanName(Class<?> clazz) {
        //应用名称生成规则生成beanName:  类名首字母小写
        String className = clazz.getSimpleName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }


    Boolean checkRef(List<Bean> beanList, String name) {
        for (Bean bean : beanList) {
            if (name.equals(bean.getId())) {
                return true;
            }
        }
        return false;
    }

}
