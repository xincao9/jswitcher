package com.github.xincao9.jswitcher.server.util;

/**
 *
 * @author xin.cao@asiainnovations.com
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
