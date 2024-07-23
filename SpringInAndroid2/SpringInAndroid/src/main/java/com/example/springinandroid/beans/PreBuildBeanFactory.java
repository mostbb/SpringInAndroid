package com.example.springinandroid.beans;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Map;

public class PreBuildBeanFactory extends DefaultBeanFactory {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void preInstantiateSingletons() throws Throwable {
        synchronized (this.beanDefintionMap) {
            for (Map.Entry<String, BeanDefinition> entry : this.beanDefintionMap.entrySet()) {
                String name = entry.getKey();
                BeanDefinition bd = entry.getValue();
                if (bd.isSingleton()) {
                    this.getBean(name);
                }
            }
        }
    }
}
