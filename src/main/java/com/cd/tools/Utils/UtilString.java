package com.cd.tools.utils;

import java.util.Objects;

/**
 * @author ChuD
 * @date 2019-06-04 15:33
 */
public class UtilString {


    /**
     * 地址检查特殊字符
     */
    public static boolean isNullOrEmptyString(Object str){
        return str == null || Objects.equals(str.toString().toUpperCase(), "NULL") || str.toString().length() == 0;
    }
}
