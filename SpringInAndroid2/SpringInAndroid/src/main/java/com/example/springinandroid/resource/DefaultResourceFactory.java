package com.example.springinandroid.resource;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

public class DefaultResourceFactory implements ResourceFactory {
    @Override
    public InputStream inputStream(int re, Context context) {
        return context.getResources().openRawResource(re);
    }

    @Override
    public List<String> getNameFromDex(Context context) throws IOException, PackageManager.NameNotFoundException {
        ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
        DexFile dexFile = new DexFile(appInfo.sourceDir);
        Enumeration<String> entries = dexFile.entries();
        List<String> list=new ArrayList<>();

        while (entries.hasMoreElements()) {
            String className = entries.nextElement();
                list.add(className);
        }
        return list;
    }

    /**
     *
     * @param list 所有文件绝对路径
     * @param path 目标文件绝对路径
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<Class<?>> getClassFromAbsolutePath(List<String> list,String path) throws ClassNotFoundException {
        List<Class<?>> list1=new ArrayList<>();
        for (String l:list){
            if(l.contains(path)){
                list1.add(this.getClass().getClassLoader().loadClass(l));
            }
        }
        return list1;
    }

    @Override
    public List<Class<?>> getClassFromAbsolutePath(List<String> list, List<String> path) throws ClassNotFoundException {

        List<Class<?>> list1=new ArrayList<>();

        for (String p:path){
            for (String l:list){
                if(l.contains(p)){
                    list1.add(this.getClass().getClassLoader().loadClass(l));
                }
            }
        }
        return list1;
    }
}
