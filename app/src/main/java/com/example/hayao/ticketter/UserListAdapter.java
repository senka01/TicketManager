package com.example.hayao.ticketter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import Model.User;
import Model.User_Selector;
import Util.CommonUtil;

public class UserListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    User_Selector userList;

    public UserListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setUserList(User_Selector userList) {
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.count();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.userrow,parent,false);
        User user = userList.get(position);
        ((TextView)convertView.findViewById(R.id.name)).setText(user.getName());
        ((TextView)convertView.findViewById(R.id.twitterID)).setText(user.getTwitterId());

        return convertView;
    }
}
