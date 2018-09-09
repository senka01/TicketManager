package com.example.hayao.ticketter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import Model.OrmaDatabase;
import Model.Ticket;
import Model.Ticket_Selector;
import Model.User_Selector;
import Util.CacheUtil;

public class TicketListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);
        setListView();
    }


    void setListView(){
        ListView listView = (ListView)findViewById(R.id.ticketList);
        List<Ticket> selector = CacheUtil.getTicketCache();
        TicketListAdapter ticketListAdapter = new TicketListAdapter(this);
        ticketListAdapter.setTicketList(selector);
        listView.setAdapter(ticketListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        reload();
    }

    private void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}
