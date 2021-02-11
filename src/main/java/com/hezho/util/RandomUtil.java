package com.hezho.util;

import org.springframework.stereotype.Component;

import java.util.Random;


public class RandomUtil {
    private static Random r = new Random();
    public static int getCode(){
        return r.nextInt(900000)+100000;
    };
}
