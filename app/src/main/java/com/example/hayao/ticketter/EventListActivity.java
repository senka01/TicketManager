package com.example.hayao.ticketter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import Model.Event_Selector;
import Model.OrmaDatabase;

public class EventListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        new Thread(){
            @Override
            public void run() {
                setListView();
            }
        }.start();
    }

    void setListView(){
        ListView listView = (ListView)findViewById(R.id.eventListView);
        long start = System.currentTimeMillis();
        OrmaDatabase orma = OrmaDatabaseProvider.getInstance(this);
        Event_Selector selector = orma.selectFromEvent().orderByIdAsc();
        EventListAdapter eventListAdapter = new EventListAdapter(this);
        eventListAdapter.setEventList(selector);
        listView.setAdapter(eventListAdapter);
        long end = System.currentTimeMillis();
        Log.d("かかった時間",String.valueOf(end-start));
    }


}
