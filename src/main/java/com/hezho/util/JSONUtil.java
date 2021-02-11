package com.hezho.util;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;


public class JSONUtil {
    private static Gson g = new Gson();

    public static String toJSON(Object o){
        return g.toJson(o);
    }


}
