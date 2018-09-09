package Util;

import java.text.NumberFormat;

public class StringUtil {

    private static NumberFormat nfNum = NumberFormat.getNumberInstance();
    public static boolean isNullOrBlank(String str){
        if(str == null){
            return true;
        }
        str = str.trim();
        if(str.equals("")){
            return true;
        }
        return false;
    }
    public static boolean isNotNullOrBlank(String str){
        return !isNullOrBlank(str);
    }

    public static String getNumberFormatString(int price){
        return nfNum.format(price);
    }

}
