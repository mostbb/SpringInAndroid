package com.example.springinandroid.resource;

import com.example.springinandroid.annotation.Component;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface ReadXmlFactory {
    Map<String,Object> xmlParse(InputStream in) throws XmlPullParserException, IOException;
}
