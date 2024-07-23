package com.example.springinandroid.util;

import androidx.annotation.Nullable;

public final class Objects {

    Objects() {
        throw new RuntimeException("Stub!");
    }

    public static void requireNonNull(@Nullable Object obj, String message){
        if(obj==null)
            throw new RuntimeException(message);
    }

    public static void requireNonNull(@Nullable String str, String message){
        if(str==null)
            throw new RuntimeException(message);
    }


    public static void requireMatch(String message){
        throw new RuntimeException(message);
    }


}

