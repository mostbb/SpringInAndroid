package com.example.springinandroid.util;

import java.util.Collection;

public class CollectionUtils {

    public static Boolean isEmpty(Collection collection){
        if(collection==null){
            return true;
        }
        return collection.isEmpty();
    }

    public static Boolean isNotEmpty(Collection collection){return !isEmpty(collection);}



}
