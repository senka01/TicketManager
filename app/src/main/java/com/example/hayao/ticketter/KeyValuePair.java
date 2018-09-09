package com.example.hayao.ticketter;

import android.util.Pair;

public class KeyValuePair extends Pair<Integer,String> {

    public KeyValuePair(Integer key, String value) {
        super(key, value);
    }

    public Integer getKey(){
        return super.first;
    }

    public String getValue(){
        return super.second;
    }
}