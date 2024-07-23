package com.example.springinandroid.resource;

import android.content.Context;
import android.content.pm.PackageManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ResourceFactory {
    InputStream inputStream(int re, Context context);//
    List<String> getNameFromDex(Context context) throws IOException, PackageManager.NameNotFoundException;//从dex获取所有文件
    List<Class<?>> getClassFromAbsolutePath(List<String> list,String path) throws ClassNotFoundException;
    List<Class<?>> getClassFromAbsolutePath(List<String> list,List<String> path) throws ClassNotFoundException;

}
