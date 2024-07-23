package com.example.springinandroid.resource;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultReadXmlFactory implements ReadXmlFactory {

    @Override
    public Map<String,Object> xmlParse(InputStream in) {


        Map<String,Object> map=new HashMap<>();
        List<Integer> key=new ArrayList<>();//计数
        List<Bean> beans = new ArrayList<>();//装载bean
        List<Constructor> constructors = new ArrayList<>();//插入null做标识
        List<String> components = new ArrayList<>();
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in,"UTF-8");
            int eventType = parser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:/**【文档开始事件】**/
                        break;
                    case XmlPullParser.START_TAG:/**【元素（即标签）开始事件】**/
                        //beans()  bean(id class)  constructor-arg(name value)
                        String tagname = parser.getName();
                        int number=parser.getAttributeCount();
                       if(tagname.equals("bean")){
                         key.add(1);
                           Bean bean = new Bean();
                           bean.setId(parser.getAttributeValue(null,"id"));
                           bean.setClass1(parser.getAttributeValue(null,"class"));
                           beans.add(bean);

                            /*System.out.println(tagname+"--id--"+parser.getAttributeValue(null,"id")+
                                    "--class--"+parser.getAttributeValue(null,"class"));*/

                        }else if(tagname.equals("constructor-arg")){

                           key.add(1);
                           Constructor constructor = new Constructor();
                           constructor.setName(parser.getAttributeValue(null,"name"));
                           if(parser.getAttributeValue(null,"value")!=null){
                               constructor.setValue(parser.getAttributeValue(null,"value"));
                           }else {
                               constructor.setValue(parser.getAttributeValue(null,"ref")+"_ref");//使用ref做标识
                           }
                           constructors.add(constructor);
                        }else if(tagname.equals("component-scan")){
                           components.add(parser.getAttributeValue(null,"base-package"));
                       }

                        break;
                    case XmlPullParser.END_TAG:/**【元素结束事件】**/

                    key.add(-1);
                    if(key.size()>2&&key.get(key.size()-2)==-1){//一个bean结束了
                        constructors.add(null);
                    }

                        break;
                }
                eventType = parser.next();
            }
            in.close();

            map.put("beans",beans);
            map.put("constructors",constructors);
            map.put("components",components);


            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }


}

