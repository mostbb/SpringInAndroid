package com.example.springinandroid.context;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.springinandroid.beans.BeanPostProcessor;
import com.example.springinandroid.beans.PreBuildBeanFactory;
import com.example.springinandroid.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractApplicationContext implements ApplicationContext{
    protected PreBuildBeanFactory beanFactory;

    public AbstractApplicationContext() {
        super();
        this.beanFactory = new PreBuildBeanFactory();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void refresh() throws Throwable {

        beanFactory.registerTypeMap();

        doRegisterBeanPostProcessor();

        beanFactory.preInstantiateSingletons();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void doRegisterBeanPostProcessor() throws Throwable {
        // 从BeanFactory中得到所有用户配置的BeanPostProcessor类型的Bean实例，注册到BeanFactory
        List<BeanPostProcessor> beanPostProcessors = beanFactory.getBeansOfTypeList(BeanPostProcessor.class);
        if(CollectionUtils.isNotEmpty(beanPostProcessors)){
            for (BeanPostProcessor bpp : beanPostProcessors) {
                beanFactory.registerBeanPostProcessor(bpp);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Object getBean(String name) throws Throwable {
        return beanFactory.getBean(name);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public <T> T getBean(Class<T> type) throws Throwable {
        return this.beanFactory.getBean(type);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws Throwable {
        return this.beanFactory.getBeansOfType(type);
    }

    @Override
    public Class<?> getType(String name) throws Throwable {
        return this.beanFactory.getType(name);
    }

    @Override
    public void registerBeanPostProcessor(BeanPostProcessor bpp) {
        this.beanFactory.registerBeanPostProcessor(bpp);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public <T> List<T> getBeansOfTypeList(Class<T> type) throws Throwable {
        return this.beanFactory.getBeansOfTypeList(type);
    }

}
