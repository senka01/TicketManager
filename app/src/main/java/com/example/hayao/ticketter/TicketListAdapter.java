package com.example.hayao.ticketter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.gfx.android.orma.SingleAssociation;

import java.util.List;

import Model.Event;
import Model.Ticket;
import Model.Ticket_Selector;
import Model.User;
import Model.User_Selector;
import Util.CacheUtil;
import Util.CommonUtil;
import Util.SiteUtil;
import Util.StringUtil;
import Util.TicketUtil;

import static Util.Constants.TICKET_ID_KEY;

public class TicketListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    List<Ticket> ticketList;
    int sumprice = 0;

    public TicketListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @Override
    public int getCount() {
        return ticketList.size();
    }

    @Override
    public Object getItem(int position) {
        return ticketList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ticketList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.ticketrow,parent,false);
        }
        final Ticket ticket = ticketList.get(position);
        Ticket beforeTicket = null;
        Ticket nextTicket = null;
        if((position + 1) < ticketList.size()){
            nextTicket = ticketList.get(position + 1);
        }
        if(position > 0){
            beforeTicket = ticketList.get(position - 1);
        }
        int sellSite = ticket.getSellSite();
        String name ;
        if(sellSite == 0){
            SingleAssociation<User> singleAssociation = ticket.opponent;
            long id = singleAssociation.getId();
            if(id == 0 ){
                name = "未定";
            }else{
                name = CacheUtil.getUser(id).getName();
            }
        }else{
            name = (String)SiteUtil.SITE_WORD_SHORT_ARRAY.get(sellSite);
        }
        ((TextView)convertView.findViewById(R.id.name)).setText(name);

        if(beforeTicket != null && beforeTicket.event.getId() == ticket.event.getId()){
            convertView.findViewById(R.id.eventName).setVisibility(View.GONE);
            convertView.findViewById(R.id.ticketHeader).setVisibility(View.GONE);
        }else{
            Event event = CacheUtil.getEvent(ticket.event.getId());
            String eventName = CommonUtil.getStrDate(event.getDate()) +"  "+ event.getName();
            ((TextView)convertView.findViewById(R.id.eventName)).setText(eventName);
        }
        ((TextView)convertView.findViewById(R.id.sellPrice)).setText(StringUtil.getNumberFormatString(ticket.getSellPrice()));
        ((TextView)convertView.findViewById(R.id.seat)).setText(ticket.getSeat());
        ((TextView)convertView.findViewById(R.id.number)).setText(String.valueOf(ticket.getNumber()));
        View ticketDetailView = convertView.findViewById(R.id.ticketDetail);
        if(ticket.isPaidFlg()){
            TextView paidTextView = (TextView)convertView.findViewById(R.id.paid);
            paidTextView.setText("済");
            paidTextView.setTextColor(Color.BLUE);
            if(ticket.isReceivedFlg()){
                ticketDetailView.setBackgroundColor(Color.LTGRAY);
            }else{
                ticketDetailView.setBackgroundColor(Color.rgb(255,182,193));
            }
        }

        if(ticket.isReceivedFlg()){
            TextView receivedTextView = (TextView)convertView.findViewById(R.id.received);
            receivedTextView.setText("済");
            receivedTextView.setTextColor(Color.BLUE);
            if(!ticket.isPaidFlg()){
                ticketDetailView.setBackgroundColor(Color.rgb(255,255,51));
            }
        }
        int price = TicketUtil.getPrice(ticket);
        TextView priceTextView = (TextView)convertView.findViewById(R.id.price);
        priceTextView.setText(StringUtil.getNumberFormatString(price));
        if(!ticket.isPaidFlg()){
            priceTextView.setTextColor(Color.BLACK);
            sumprice += price;
            if(!ticket.isReceivedFlg()){
                ticketDetailView.setBackgroundColor(Color.rgb(255,250,250));
            }
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TicketAddActivity.class);
                intent.putExtra(TICKET_ID_KEY,ticket.getId());
                ((TicketListActivity)context).startActivityForResult(intent,100);
            }
        });

        TextView sumPrice = convertView.findViewById(R.id.sumPaidPrice);
        if(nextTicket == null || (nextTicket.event.getId() != ticket.event.getId())){
            //合計金額表示ロジック
            sumPrice.setText("未受取合計金額: " + StringUtil.getNumberFormatString(sumprice) +"円");
            sumPrice.setTextColor(Color.BLACK);
            sumprice = 0;
        }else{
            sumPrice.setVisibility(View.GONE);
        }
        return convertView;
    }

}
