package com.example.springinandroid;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws NoSuchFieldException {
        test02();
    }


    private Map<String, Integer> map = new HashMap<>();

    void test02() throws NoSuchFieldException {

        //Class<?> type = ExampleUnitTest.class.getDeclaredField("map").getType();

        Field field = ExampleUnitTest.class.getDeclaredField("map");

        // 获取泛型类型
        Type genericFieldType = field.getGenericType();

        if (genericFieldType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
            Class<?> valueType = (Class<?>) fieldArgTypes[1]; // 获取值的类型

            System.out.println("The type of the values in the map is: " + valueType.getTypeName());
        } else {
            System.out.println("The map field does not have generic types.");
        }
    }



    void test01(){
        ArrayList<String> list=new ArrayList<>();
        list.add(null);
        System.out.println("size---------------"+list.size());
    }
}