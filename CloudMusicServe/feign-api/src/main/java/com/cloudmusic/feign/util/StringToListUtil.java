package com.cloudmusic.feign.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringToListUtil {
    public static List<String> StringToList(String string) {
        String[] strings = string.replace("[", "").replace("]", "").replaceAll("\"", "").replaceAll(" ","").split(",");
        return new ArrayList<String>(Arrays.asList(strings));
    }
}
