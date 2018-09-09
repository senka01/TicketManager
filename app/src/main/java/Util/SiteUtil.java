package Util;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

public class SiteUtil {
    public static final int TICKET_STREET_ID = 2;
    public static final String TICKET_STREET_WORD = "チケットストリート";
    public static final double TICKET_STREET_COMMISION = 0;
    public static final int TICKET_DISTRIBUTION_CENTER_ID = 1;
    public static final double TICKET_DSTRIBUTION_CENTER_COMMISION = 0.1026;
    public static final String TICKET_DSTRIBUTION_CENTER_WORD = "チケット流通センター";
    public static final int YAFUOKU_ID = 3;
    public static final double YAFUOKU_COMMISION = 0.0864;
    public static final String YAFUOKU_WORD = "ヤフオク(プレミアム会員)";
    public static final SparseArray SITE_WORD_ARRAY = new SparseArray(){{
        append(TICKET_DISTRIBUTION_CENTER_ID,TICKET_DSTRIBUTION_CENTER_WORD);
        append(TICKET_STREET_ID,TICKET_STREET_WORD);
        append(YAFUOKU_ID,YAFUOKU_WORD);
    }};
    public static final SparseArray SITE_WORD_SHORT_ARRAY = new SparseArray(){{
        append(TICKET_DISTRIBUTION_CENTER_ID,"チケ流");
        append(TICKET_STREET_ID,"チケスト");
        append(YAFUOKU_ID,"ヤフオク");
    }};

    public static final Map<Integer,Double> COMMISION_ARRAY = new HashMap<Integer, Double>(){{
        put(TICKET_STREET_ID,TICKET_STREET_COMMISION);
        put(TICKET_DISTRIBUTION_CENTER_ID,TICKET_DSTRIBUTION_CENTER_COMMISION);
        put(YAFUOKU_ID,YAFUOKU_COMMISION);
    }};

    public static SparseArray getSiteWordArray(){
        return SITE_WORD_ARRAY;
    }

    public static double getCommissionPercent(int siteId){
        return COMMISION_ARRAY.get(siteId);
    }




}
