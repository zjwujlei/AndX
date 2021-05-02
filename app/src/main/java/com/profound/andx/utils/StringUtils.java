package com.profound.andx.utils;

public class StringUtils {

    public static boolean isNull(String s) {
        if (s == null || "".equals(s) || s.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotNull(String str) {
        if (str != null && !"".equals(str)) {
            return true;
        }
        return false;
    }


    /**
     * str中是否包含in字符
     *
     * @param str
     * @param in
     * @return
     */
    public static boolean isContain(String str, String in) {
        if (isNull(str)) {
            return false;
        }
        return str.contains(in);
    }


    /**
     * 判断是否是空串（或者是全空格串）
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
}
