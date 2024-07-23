package com.example.springinandroid.context;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.springinandroid.annotation.Autowired;
import com.example.springinandroid.annotation.Component;
import com.example.springinandroid.annotation.Primary;
import com.example.springinandroid.annotation.Qualifier;
import com.example.springinandroid.annotation.Scope;
import com.example.springinandroid.annotation.Value;
import com.example.springinandroid.beans.BeanDefinitionRegistException;
import com.example.springinandroid.beans.BeanDefinitionRegistry;
import com.example.springinandroid.beans.BeanReference;
import com.example.springinandroid.beans.GenericBeanDefinition;
import com.example.springinandroid.beans.PropertyValue;
import com.example.springinandroid.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ClassPathBeanDefinitionScanner {

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super();
        this.registry = registry;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void scan(List<Class<?>> classes) throws BeanDefinitionRegistException {

        if (classes != null && classes.size() > 0) {
            this.readAndRegisterBeanDefintion(classes);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void readAndRegisterBeanDefintion(List<Class<?>> classes) throws BeanDefinitionRegistException {
        for (Class<?> clazz : classes) {
            // 解析Class上的注解，获得Bean定义信息，注册Bean定义
            Component component = clazz.getAnnotation(Component.class);
            if (component != null) {
                String beanName = component.value();
                if (StringUtils.isBlank(beanName)) {
                    beanName = this.generateBeanName(clazz);
                }
                GenericBeanDefinition bd = new GenericBeanDefinition();
                bd.setBeanClass(clazz);
                Scope scope = clazz.getAnnotation(Scope.class);
                if (scope != null) {
                    bd.setScope(scope.value());
                }
                //处理primary
                Primary primary = clazz.getAnnotation(Primary.class);
                if (primary != null) {
                    bd.setPrimary(true);
                }

                // 处理构造方法，在构造方法上找@Autowired注解，如有，将这个构造方法set到bd;
                this.handleConstructor(clazz, bd);

                // 处理属性依赖
                this.handlePropertyDi(clazz, bd);

                // 注册bean定义
                this.registry.registerBeanDefinition(beanName, bd);

            }
        }
    }

    private String generateBeanName(Class<?> clazz) {
        //应用名称生成规则生成beanName:  类名首字母小写
        String className = clazz.getSimpleName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleConstructor(Class<?> clazz, GenericBeanDefinition bd) {
        // 获得所有构造方法，在构造方法上找@Autowired注解，如有，将这个构造方法set到bd;
        Constructor<?>[] cs = clazz.getConstructors();
        if (cs != null && cs.length > 0) {
            for (Constructor<?> c : cs) {
                if (c.getAnnotation(Autowired.class) != null) {
                    bd.setConstructor(c);
                    //构造参数依赖处理
                    bd.setConstructorArgumentValues(this.handleMethodParamters(c.getParameters()));
                    break;
                }
            }
        }
    }

    //构造参数依赖处理方法
    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Object> handleMethodParamters(Parameter[] ps) {
        //遍历获取参数上的注解，及创建构造参数依赖
        List<Object> argValues = new ArrayList<>();
        for (Parameter parameter : ps) {
            //找@Value注解
            Value v = parameter.getAnnotation(Value.class);
            if (v != null) {
                argValues.add(v.value());
                continue;
            }
            //找@Qualifier
            Qualifier q = parameter.getAnnotation(Qualifier.class);
            if (q != null) {
                argValues.add(new BeanReference(q.value()));
            } else {
                argValues.add(new BeanReference(parameter.getType()));
            }
        }
        return argValues;
    }


    //对的属性依赖的处理
    private void handlePropertyDi(Class<?> clazz, GenericBeanDefinition bd) {
        List<PropertyValue> propertyValues = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {//处理Autowired注解
                Log.d("test__","clazz--"+clazz.getSimpleName()+"--当前字段--"+field.getName());
                BeanReference beanReference = null;
                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                if (qualifier != null) {

                    beanReference = new BeanReference(qualifier.value());
                } else {
                    Log.d("test__","field.getType()--"+field.getGenericType());
                    beanReference = new BeanReference(field.getType(), field.getGenericType());
                }
                propertyValues.add(new PropertyValue(field.getName(), beanReference));
            }
        }
        bd.setPropertyValues(propertyValues);
    }

    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

}
