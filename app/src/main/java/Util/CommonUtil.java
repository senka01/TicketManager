package Util;

import android.content.Context;
import android.widget.TextView;

import com.example.hayao.ticketter.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

    private static SimpleDateFormat date_YYYYMMDD = new SimpleDateFormat("yyyy/MM/dd");
    /**
     * dateを日付形式にして返す
     * @param date
     * @return
     */
    public static String getStrDate(Date date){
        if(date == null) return "";
        return date_YYYYMMDD.format(date);
    }

    public static Date getDate(String strDate){
        try{
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdFormat.parse(strDate);
            return date;
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

}
