package com.example.hayao.ticketter;

import android.content.Intent;
import android.support.constraint.solver.Cache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.github.gfx.android.orma.SingleAssociation;

import java.util.Date;
import java.util.logging.Logger;

import Model.Event;
import Model.Event_Selector;
import Model.OrmaDatabase;
import Model.Ticket;
import Model.User;
import Model.User_Selector;
import Util.AlertUtil;
import Util.CacheUtil;
import Util.CommonUtil;
import Util.Constants;
import Util.ShippingUtil;
import Util.SiteUtil;
import Util.StringUtil;

import static Util.Constants.TICKET_ID_KEY;

public class TicketAddActivity extends AppCompatActivity {

    OrmaDatabase orma;
    Intent intent;
    int ticketId = 0;
    KeyValuePairAdapter eventAdapter;
    KeyValuePairAdapter opponentAdapter;
    KeyValuePairAdapter siteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_add);
        orma = OrmaDatabaseProvider.getInstance(this);
        intent = getIntent();
        ticketId = (int)intent.getLongExtra(TICKET_ID_KEY,0);
        setEventSpinner();
        setSaveButtonEvent();
        setSite();
        setUsers();
        setSwitch();
        setDeleteTicketButtonEvent();
        loadTicket(ticketId);
    }
    void setDeleteTicketButtonEvent(){
        Button deleteButton = findViewById(R.id.deleteTicketButton);
        if(ticketId == 0){
            deleteButton.setVisibility(View.GONE);
        }else{
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Thread(){
                        @Override
                        public void run() {
                            deleteTicket(ticketId);
                        }
                    }.start();
                    finish();
                }
            });
        }
    }

    void loadTicket(int ticketId){
        if(ticketId == 0) return;
        Ticket ticket = orma.selectFromTicket().idEq(ticketId).get(0);

        Spinner eventSelect = findViewById(R.id.eventSpinner);
        eventSelect.setSelection(eventAdapter.getPosition((int)ticket.event.getId()));

        EditText sellPrice =  (EditText)findViewById(R.id.sellPriceText);
        sellPrice.setText(String.valueOf(ticket.getSellPrice()));

        EditText detail =  (EditText)findViewById(R.id.detail);
        detail.setText(ticket.getDetail());

        EditText seat =  (EditText)findViewById(R.id.seatTextView);
        seat.setText(ticket.getSeat());

        int siteId = ticket.getSellSite();
        if(siteId > 0){
            Switch siteFlg = (Switch)findViewById(R.id.siteFlg);
            siteFlg.setChecked(true);
            Spinner sellSite = (Spinner)findViewById(R.id.spinnerSite);
            sellSite.setSelection(siteAdapter.getPosition(siteId));
            setVisibleSite(true);
        }else{
            SingleAssociation opponent =  ticket.opponent;
            if(opponent != null){
                long userId = opponent.getId();
                Spinner opponentSpinner = (Spinner)findViewById(R.id.opponentSpinner);
                opponentSpinner.setSelection(opponentAdapter.getPosition((int)userId));
            }
        }

        ((CheckBox)findViewById(R.id.receivedCheckBox)).setChecked(ticket.isReceivedFlg());

        ((CheckBox)findViewById(R.id.paidCheckBox)).setChecked(ticket.isPaidFlg());

        Spinner number = (Spinner)findViewById(R.id.spinnerNumber);
        number.setSelection(ticket.getNumber() -1);

    }

    void setSwitch(){
        Switch switchView = findViewById(R.id.siteFlg);
        setVisibleSite(switchView.isChecked());
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                setVisibleSite(isChecked);
            }
        });
    }

    void setVisibleSite(boolean isChecked){
        TextView siteTextView = findViewById(R.id.textViewSite);
        TextView opponentTextView = findViewById(R.id.textViewOpponent);
        Spinner siteSpinner = findViewById(R.id.spinnerSite);
        Spinner opponentSpinner = findViewById(R.id.opponentSpinner);
        if(isChecked){
            siteSpinner.setVisibility(View.VISIBLE);
            siteTextView.setVisibility(View.VISIBLE);
            opponentSpinner.setVisibility(View.GONE);
            opponentTextView.setVisibility(View.GONE);
        }else{
            siteSpinner.setVisibility(View.GONE);
            siteTextView.setVisibility(View.GONE);
            opponentSpinner.setVisibility(View.VISIBLE);
            opponentTextView.setVisibility(View.VISIBLE);
        }
    }

    void setSaveButtonEvent(){
        Button button = findViewById(R.id.saveTicketButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateTicket()){
                    return;
                }
                new Thread(){
                    @Override
                    public void run() {
                        saveTicket();
                    }
                }.start();
                finish();
            }
        });
    }

    void saveTicket(){
        Ticket ticket;
//        if(ticketId == 0){
            ticket = new Ticket();
//        }else{
//            ticket = orma.selectFromTicket().idEq(ticketId).get(0);
//        }
        int eventId = getSpinnerSelectedId(R.id.eventSpinner);
        ticket.event = SingleAssociation.id(eventId);

        String sellPriceStr =  ((TextView)findViewById(R.id.sellPriceText)).getText().toString();
        if(StringUtil.isNotNullOrBlank(sellPriceStr)){
            int price = Integer.parseInt(sellPriceStr);
            ticket.setSellPrice(price);
        }
        String detail =  ((TextView)findViewById(R.id.detail)).getText().toString();
        ticket.setDetail(detail);

        String seat =  ((TextView)findViewById(R.id.seatTextView)).getText().toString();
        ticket.setSeat(seat);

        boolean isSite = ((Switch)findViewById(R.id.siteFlg)).isChecked();

        if(isSite){
            KeyValuePair sitePair =  (KeyValuePair)((Spinner)findViewById(R.id.spinnerSite)).getSelectedItem();
            int site = sitePair.getKey();
            ticket.setSellSite(site);
        }else{
            KeyValuePair opponent =  (KeyValuePair)((Spinner)findViewById(R.id.opponentSpinner)).getSelectedItem();
            long opponentId = opponent.getKey();
            if(opponentId > 0){
                ticket.opponent = SingleAssociation.id(opponentId);
            }else if(opponentId == Constants.USER_ADD_ID){
                String newUserName = ((EditText)findViewById(R.id.newUserName)).getText().toString();
                User user = new User();
                user.setName(newUserName);
                long newUserId = orma.insertIntoUser(user);
                ticket.opponent = SingleAssociation.id(newUserId);
                CacheUtil.loadUserCache(this);
            }
        }

        boolean isReceived = ((CheckBox)findViewById(R.id.receivedCheckBox)).isChecked();
        ticket.setReceivedFlg(isReceived);

        boolean isPaid = ((CheckBox)findViewById(R.id.paidCheckBox)).isChecked();
        ticket.setPaidFlg(isPaid);

        String number = ((Spinner)findViewById(R.id.spinnerNumber)).getSelectedItem().toString();
        ticket.setNumber(Integer.parseInt(number));
        if(ticketId == 0) {
            orma.insertIntoTicket(ticket);
        }else{
            deleteTicket(ticketId);
            orma.insertIntoTicket(ticket);
        }
        CacheUtil.loadTicketCache(this);
    }

    void deleteTicket(long ticketId){
        orma.deleteFromTicket().idEq(ticketId).execute();
        CacheUtil.loadTicketCache(this);
    }

    boolean validateTicket(){
        Spinner eventSpinner = (Spinner)findViewById(R.id.eventSpinner);
        KeyValuePair keyValuePair = (KeyValuePair)eventSpinner.getSelectedItem();
        if(keyValuePair.getKey() == 0){
            AlertUtil.showInputAlert(this,"公演を選択してください");
            return false;
        }
        boolean isSite = ((Switch)findViewById(R.id.siteFlg)).isChecked();
        if(!isSite){
            KeyValuePair opponent =  (KeyValuePair)((Spinner)findViewById(R.id.opponentSpinner)).getSelectedItem();
            long opponentId = opponent.getKey();
            if(opponentId == Constants.USER_ADD_ID){
                String newUserName = ((EditText)findViewById(R.id.newUserName)).getText().toString();
                if(StringUtil.isNullOrBlank(newUserName)){
                    AlertUtil.showInputAlert(this,"名前を入力してください");
                    return false;
                }
            }

        }
        return true;
    }

    int getSpinnerSelectedId(int id){
        Spinner spinner = (Spinner) findViewById(id);
        return ((KeyValuePair)spinner.getSelectedItem()).getKey();
    }

    void setEventSpinner(){
        KeyValuePairAdapter adapter = new KeyValuePairAdapter(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテムを追加します
        Event_Selector eventSelector = orma.selectFromEvent().orderBy("date");
        for(Event event:eventSelector){
            String eventName = event.getName();
            Date eventDate = event.getDate();
            if(event != null){
                String eventDateStr = CommonUtil.getStrDate(eventDate);
                eventName = eventDateStr + "  " + eventName;
            }
            adapter.add(new KeyValuePair((int)event.getId(),eventName));
        }
        Spinner spinner = (Spinner) findViewById(R.id.eventSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                Spinner spinner = (Spinner) parentView;
                KeyValuePair item = (KeyValuePair)spinner.getItemAtPosition(position);
                // 選択されたアイテムのテキストを取得
                Event_Selector eventSelector = orma.selectFromEvent().idEq(item.getKey());
                Event event = eventSelector.get(0);
                EditText priceEditText = ((EditText)findViewById(R.id.sellPriceText));
                if(ticketId == 0){
                    priceEditText.setText(String.valueOf(event.getPrice()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        // アダプターを設定します
        spinner.setAdapter(adapter);
        eventAdapter = adapter;
    }

    /**
     * サイト一覧を設定する
     */
    void setSite(){
        KeyValuePairAdapter adapter = new KeyValuePairAdapter(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテムを追加します
        SparseArray sparseArray = SiteUtil.getSiteWordArray();
        for (int i=0 ; i<sparseArray.size() ; i++){
            adapter.add(new KeyValuePair(sparseArray.keyAt(i),(String)sparseArray.valueAt(i)));
        }
        Spinner spinner = (Spinner) findViewById(R.id.spinnerSite);
        // アダプターを設定します
        spinner.setAdapter(adapter);
        siteAdapter = adapter;
    }

    /**
     * ユーザー一覧を設定する
     */
    void setUsers(){
        KeyValuePairAdapter adapter = new KeyValuePairAdapter(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテムを追加します
        User_Selector userSelector = orma.selectFromUser().orderByIdAsc();
        adapter.add(new KeyValuePair(0,"未定"));
        adapter.add(new KeyValuePair(Constants.USER_ADD_ID,"新規追加"));
        for (User user:userSelector){
            adapter.add(new KeyValuePair((int)user.getId(),user.getName()));
        }
        Spinner spinner = (Spinner) findViewById(R.id.opponentSpinner);
        final EditText newUserName = (EditText)findViewById(R.id.newUserName);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View parentView, int position, long l) {
                Spinner spinner = (Spinner) adapterView;
                KeyValuePair item = (KeyValuePair)spinner.getItemAtPosition(position);
                // 選択されたアイテムのテキストを取得
                int id = item.getKey();
                if(id == Constants.USER_ADD_ID){
                    newUserName.setVisibility(View.VISIBLE);
                }else{
                    newUserName.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // アダプターを設定します
        spinner.setAdapter(adapter);
        opponentAdapter = adapter;
    }

}
