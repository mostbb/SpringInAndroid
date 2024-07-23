package com.example.samples.demo01;

import android.util.Log;

import com.example.springinandroid.annotation.Component;

import java.io.Reader;
import java.util.List;

@Component("jSONParser")
public class JSONParser implements Parser {
    @Override
    public List parse(Reader r) {
        Log.d("test__", "JSONParser解析");
        return null;
    }
}
