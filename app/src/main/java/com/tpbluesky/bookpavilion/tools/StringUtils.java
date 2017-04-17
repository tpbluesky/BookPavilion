package com.tpbluesky.bookpavilion.tools;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tpbluesky on 2017/2/13.
 */

public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.equals("") || str.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    public static <T> T String2Gson(String jsonString, Type type, ExclusionStrategy strategy) {
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(strategy)
                .create();
        return gson.fromJson(jsonString, type);
    }

    public static <T> T String2Gson(String jsonString, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, type);
    }
}
