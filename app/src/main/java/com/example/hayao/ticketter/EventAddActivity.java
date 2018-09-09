package com.example.hayao.ticketter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import Model.Event;
import Model.OrmaDatabase;
import Util.AlertUtil;
import Util.CacheUtil;
import Util.CommonUtil;
import Util.StringUtil;

import static Util.Constants.EVENT_ID_KEY;
import static Util.Constants.TICKET_ID_KEY;

public class EventAddActivity extends AppCompatActivity {
    EditText eventDay;
    long eventId = 0;
    Event event = null;
    OrmaDatabase orma = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);
        orma = OrmaDatabaseProvider.getInstance(this);
        Intent intent = getIntent();
        eventId = (int)intent.getLongExtra(EVENT_ID_KEY,0);
        if(eventId > 0){
            event = orma.selectFromEvent().idEq(eventId).get(0);
        }
        Button button = findViewById(R.id.addEventButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isValidationOk()){
                    return;
                }
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        saveEvent();
                    }
                };
                thread.start();
                finish();
            }
        });
        setDatePicker();

    }

    void setDatePicker(){
        eventDay = (EditText) findViewById(R.id.eventDay);
        if(eventId > 0){
            eventDay.setText(CommonUtil.getStrDate(event.getDate()));
        }else{
            eventDay.setText(CommonUtil.getStrDate(new Date()));
        }

        //EditTextにリスナーをつける
        eventDay.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    //Calendarインスタンスを取得
                    final Calendar date = Calendar.getInstance();

                    //DatePickerDialogインスタンスを取得
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            EventAddActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    //setした日付を取得して表示
                                    eventDay.setText(String.format("%d/%02d/%02d", year, month+1, dayOfMonth));
                                }
                            },
                            date.get(Calendar.YEAR),
                            date.get(Calendar.MONTH),
                            date.get(Calendar.DATE)
                    );

                    //dialogを表示
                    datePickerDialog.show();
                }
                return false;
            }

        });


    }

    boolean isValidationOk(){
        TextView text = (TextView) findViewById(R.id.eventName);
        String eventName = text.getText().toString();
        if(StringUtil.isNullOrBlank(eventName)){
            AlertUtil.showInputAlert(this,"イベント名が未入力です");
            return false;
        }
        TextView eventDayView = (TextView) findViewById(R.id.eventDay);
        String eventDay = eventDayView.getText().toString();
        if(StringUtil.isNullOrBlank(eventDay)){
            AlertUtil.showInputAlert(this,"日付が未入力です");
            return false;
        }
        if(CommonUtil.getDate(eventDay) == null){
            AlertUtil.showInputAlert(this,"日付の形式が不正です");
            return false;
        }

        return true;
    }

    void saveEvent(){
        Event event = new Event();
        TextView text = (TextView) findViewById(R.id.eventName);
        String eventName = text.getText().toString();
        TextView eventDayView = (TextView) findViewById(R.id.eventDay);
        TextView priceTextView = (TextView) findViewById(R.id.price);
        String priceStr = priceTextView.getText().toString();
        int price = 0;
        if(StringUtil.isNotNullOrBlank(priceStr)){
            price = Integer.parseInt(priceStr);
        }

        if(eventId > 0){
            orma.updateEvent().idEq(eventId)
                    .name(eventName)
                    .date(CommonUtil.getDate(eventDayView.getText().toString()))
                    .price(price);
        }else{
            event.setPrice(price);
            event.setName(eventName);
            event.setDate(CommonUtil.getDate(eventDayView.getText().toString()));
            orma.insertIntoEvent(event);
        }
        CacheUtil.loadEventCache(this);

    }
}
