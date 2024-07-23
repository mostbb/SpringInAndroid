package com.example.springinandroid.context;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.springinandroid.beans.BeanDefinitionRegistry;
import com.example.springinandroid.resource.DefaultReadXmlFactory;
import com.example.springinandroid.resource.DefaultResourceFactory;
import com.example.springinandroid.resource.ReadXmlFactory;
import com.example.springinandroid.resource.ResourceFactory;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class AnnotationApplicationContext extends AbstractApplicationContext {

	ResourceFactory resourceFactory = new DefaultResourceFactory();
	ReadXmlFactory readXmlFactory = new DefaultReadXmlFactory();
	Map<String,Object> map_xml = null;

	@RequiresApi(api = Build.VERSION_CODES.O)
	public AnnotationApplicationContext(int re, Context context) throws Throwable {
		super();

		map_xml=readXmlFactory.xmlParse(resourceFactory.inputStream(re,context));
		List<String> components= (List<String>) map_xml.get("components");//获取components标签中的路径

		if(components.size()>0){
			List<Class<?>> classes=resourceFactory.getClassFromAbsolutePath(resourceFactory.getNameFromDex(context),components);//获取所有目标类
			new ClassPathBeanDefinitionScanner((BeanDefinitionRegistry) this.beanFactory).scan(classes);
		}
		refresh();
		super.refresh();

	}

}
