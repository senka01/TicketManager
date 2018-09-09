package com.example.hayao.ticketter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import Util.CacheUtil;
import Util.StringUtil;
import Util.TicketUtil;

public class TicketSumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_sum);
        TextView textView1 = findViewById(R.id.sumPrice1);
        textView1.setText(StringUtil.getNumberFormatString(CacheUtil.getTicketSumPrice()) + "円");
        TextView textView2 = findViewById(R.id.sumPrice2);
        textView2.setText(StringUtil.getNumberFormatString(CacheUtil.getUndecidedTicketSumPrice()) + "円");
        TextView textView3 = findViewById(R.id.sumPrice3);
        textView3.setText(StringUtil.getNumberFormatString(CacheUtil.getTicketSumPrice() - CacheUtil.getUndecidedTicketSumPrice()) + "円");
    }
}
