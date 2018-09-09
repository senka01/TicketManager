package com.example.hayao.ticketter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Model.OrmaDatabase;
import Model.User;
import Util.AlertUtil;
import Util.CacheUtil;
import Util.StringUtil;

public class UserAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);
        setSaveButtonEvent();
    }

    void setSaveButtonEvent(){
        Button button = findViewById(R.id.saveUserButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateUser()){
                    return;
                }
                new Thread(){
                    @Override
                    public void run() {
                        saveUser();
                    }
                }.start();
                finish();
            }
        });
    }

    void saveUser(){
        TextView userNameView = (TextView)findViewById(R.id.userName);
        String userName = userNameView.getText().toString();
        TextView twitterIDView = (TextView)findViewById(R.id.twitterID);
        String twitterID = twitterIDView.getText().toString();
        User user = new User();
        user.setName(userName);
        user.setTwitterId(twitterID);
        OrmaDatabase orma = OrmaDatabaseProvider.getInstance(this);
        orma.insertIntoUser(user);
        CacheUtil.loadUserCache(this);

    }

    boolean validateUser(){
        TextView userNameView = (TextView)findViewById(R.id.userName);
        String userName = userNameView.getText().toString();
        if(StringUtil.isNullOrBlank(userName)){
            AlertUtil.showInputAlert(this,"ユーザー名が未入力です。");
            return false;
        }
        return true;
    }
}
