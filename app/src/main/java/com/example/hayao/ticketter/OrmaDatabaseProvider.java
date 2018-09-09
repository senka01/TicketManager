package com.example.hayao.ticketter;

import android.content.Context;

import Model.OrmaDatabase;

public class OrmaDatabaseProvider {
    private static OrmaDatabase ormaDatabase;
    private OrmaDatabaseProvider(){
    }
    public static OrmaDatabase getInstance(Context context) {
        if(ormaDatabase == null){
            ormaDatabase = OrmaDatabase.builder(context)
                    .build();
        }
        return ormaDatabase;
    }

}
