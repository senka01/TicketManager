package Util;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

public class ShippingUtil {

    public static final int KANIKAKITOME_ID = 1;
    public static final String KANIKAKITOME_WORD = "簡易書留";
    public static final int KANIKAKITOME_PRICE = 392;
    public static final int LETTER_PACK_PLUS_ID = 2;
    public static final String LETTER_PACK_PLUS_WORD = "レターパックプラス";
    public static final int LETTER_PACK_PLUS_PRICE = 510;
    public static final SparseArray SHIPPING_WORD_ARRAY = new SparseArray(){{
        append(KANIKAKITOME_ID,KANIKAKITOME_WORD);
        append(LETTER_PACK_PLUS_ID,LETTER_PACK_PLUS_WORD);
    }};
    public static final Map<Integer,Integer> SHIPPING_PRICE_ARRAY = new HashMap<Integer, Integer>(){{
        put(KANIKAKITOME_ID,KANIKAKITOME_PRICE);
        put(LETTER_PACK_PLUS_ID,LETTER_PACK_PLUS_PRICE);
    }};

    /**
     * 送料を取得する
     * @param shippingId
     * @return
     */
    public static int getPrice(int shippingId){
        return SHIPPING_PRICE_ARRAY.get(shippingId);
    }

    /**
     * 発送方法の文言一覧を取得する
     * @return
     */
    public static SparseArray getWordArray(){
        return SHIPPING_WORD_ARRAY;
    }


}
