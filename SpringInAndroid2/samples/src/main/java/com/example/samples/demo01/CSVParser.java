package com.example.samples.demo01;

import android.util.Log;

import com.example.springinandroid.annotation.Component;

import java.io.Reader;
import java.util.List;

@Component
public class CSVParser implements Parser {
    @Override
    public List parse(Reader r) {
        Log.d("test","CSVParser解析");
        return null;
    }
}
