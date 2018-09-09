package com.example.hayao.ticketter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.solver.Cache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Model.OrmaDatabase;
import Util.CacheUtil;

public class DefaultActivity extends AppCompatActivity {


    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        context = this;
        new Thread(){
            @Override
            public void run() {
                OrmaDatabase ormaDatabase = OrmaDatabaseProvider.getInstance(context);
                CacheUtil.loadEventCache(context);
                CacheUtil.loadUserCache(context);
                CacheUtil.loadTicketCache(context);
            }
        }.start();
        setCalculateButton();
        setEventAddButton();
        setUserAddButton();
        setEventListButton();
        setUserListButton();
        setTicketAddButton();
        setTicketListButton();
        setTicketSumButton();
    }

    void setTicketSumButton(){
        Button button = (Button)findViewById(R.id.ticketSumButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), TicketSumActivity.class);
                startActivity(intent);
            }
        });
    }

    void setTicketListButton(){
        Button button = (Button)findViewById(R.id.ticketListButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), TicketListActivity.class);
                startActivity(intent);
            }
        });

    }

    void setTicketAddButton(){
        Button button = (Button)findViewById(R.id.ticketAddButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), TicketAddActivity.class);
                startActivity(intent);
            }
        });
    }

    void setCalculateButton(){
        Button button = (Button)findViewById(R.id.calculateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    void setEventAddButton(){
        Button button = (Button)findViewById(R.id.addEventButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), EventAddActivity.class);
                startActivity(intent);
            }
        });
    }

    void setUserAddButton(){
        Button button = (Button)findViewById(R.id.userAddButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), UserAddActivity.class);
                startActivity(intent);
            }
        });
    }

    void setUserListButton(){
        Button button = (Button)findViewById(R.id.userListButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), UserListActivity.class);
                startActivity(intent);
            }
        });
    }

    void setEventListButton(){
        final Button button = findViewById(R.id.eventListButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setActivated(false);
                Intent intent = new Intent(getApplication(), EventListActivity.class);
                startActivity(intent);
                button.setActivated(true);
            }
        });
    }

}
