package com.example.samples;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.samples.demo01.update.Client;
import com.example.springinandroid.context.AnnotationApplicationContext;
import com.example.springinandroid.context.ApplicationContext;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ApplicationContext applicationContext= new AnnotationApplicationContext(R.raw.spring,this);
            ((Client)applicationContext.getBean("client")).getAll("cSVParser");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}