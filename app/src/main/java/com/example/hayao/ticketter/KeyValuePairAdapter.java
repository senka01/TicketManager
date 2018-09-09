package com.example.hayao.ticketter;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class KeyValuePairAdapter extends ArrayAdapter<KeyValuePair> {

    public KeyValuePairAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }
    /**
     * @brief コンストラクタ
     * @param context
     * @param textViewResourceId
     * @param list
     */
    public KeyValuePairAdapter(Context context, int textViewResourceId, List<KeyValuePair> list) {
        super(context, textViewResourceId, list);
    }

    /**
     * @brief Spinerに表示するViewを取得します。
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setText(getItem(position).getValue());
        return view;

    }
    /**
     * @brief Spinerのドロップダウンアイテムに表示するViewを取得します。
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setText(getItem(position).getValue());
        return view;
    }

    /**
     * @brief keyに一致するインデックスを取得します。
     * @param key
     * @return
     */
    public int getPosition(int key){
        int position = -1;
        for (int i = 0 ; i < this.getCount(); i++){
            if (this.getItem(i).getKey() == key) {
                position = i;
                break;
            }
        }
        return position;

    }



}
