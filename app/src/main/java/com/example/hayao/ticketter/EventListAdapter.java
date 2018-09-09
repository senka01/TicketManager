package com.example.hayao.ticketter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Model.Event;
import Model.Event_Selector;
import Util.CommonUtil;

import static Util.Constants.EVENT_ID_KEY;
import static Util.Constants.TICKET_ID_KEY;

public class EventListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    Event_Selector eventList;

    public EventListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setEventList(Event_Selector eventList) {
        this.eventList = eventList;
    }

    @Override
    public int getCount() {
        return eventList.count();
    }

    @Override
    public Object getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return eventList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.eventrow,parent,false);
        final Event event = eventList.get(position);
        ((TextView)convertView.findViewById(R.id.name)).setText(event.getName());
        if(event.getDate() != null){
            ((TextView)convertView.findViewById(R.id.date)).setText(CommonUtil.getStrDate(event.getDate()));
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventAddActivity.class);
                intent.putExtra(EVENT_ID_KEY,event.getId());
                ((EventListActivity)context).startActivityForResult(intent,100);
            }
        });

        return convertView;
    }
}
