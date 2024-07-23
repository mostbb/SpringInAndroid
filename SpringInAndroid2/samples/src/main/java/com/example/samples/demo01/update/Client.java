package com.example.samples.demo01.update;

import com.example.samples.demo01.Parser;
import com.example.springinandroid.annotation.Autowired;
import com.example.springinandroid.annotation.Component;


import java.io.Reader;
import java.util.List;
import java.util.Map;


@Component
public class Client {

    @Autowired
    Map<String, Parser> parserMap;

    Reader reader;

    public List getAll(String contentType) {
        return parserMap.get(contentType).parse(reader);
    }

}
