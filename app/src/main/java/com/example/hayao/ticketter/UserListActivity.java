package com.example.hayao.ticketter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import Model.OrmaDatabase;
import Model.User_Selector;

public class UserListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        setListView();
    }

    void setListView(){
        ListView listView = (ListView)findViewById(R.id.userList);
        OrmaDatabase orma = OrmaDatabaseProvider.getInstance(this);
        User_Selector selector = orma.selectFromUser().orderByIdAsc();
        UserListAdapter userListAdapter = new UserListAdapter(this);
        userListAdapter.setUserList(selector);
        listView.setAdapter(userListAdapter);
    }
}
