package com.example.hayao.ticketter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.SpannableStringBuilder;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import Util.ShippingUtil;
import Util.SiteUtil;
import Util.TicketUtil;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setShipping();
        setSite();
        setCalculateEvent();
    }

    void setCalculateEvent(){
        findViewById(R.id.calculate).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                calculate();
            }
        });
    }


    void calculate(){
        int siteID = getSpinnerSelectedId(R.id.site);
        int shippingId = getSpinnerSelectedId(R.id.shipping);
        double commissionPercent = SiteUtil.getCommissionPercent(siteID);
        int shippingPrice = ShippingUtil.getPrice(shippingId);

        //枚数
        Spinner appCompatSpinner = (Spinner)findViewById(R.id.numberOfTicket);
        int number = Integer.parseInt(appCompatSpinner.getSelectedItem().toString());
        log("枚数",number);
        //定価
        int listPrice = getNumberFromTextView(R.id.listPrice)* number;
        log("定価",listPrice);
        //売価
        int sellPrice = getNumberFromTextView(R.id.sellingPrice) * number;
        log("売価",sellPrice);
        //手数料
        int commission = TicketUtil.getMultiplyResult(sellPrice,commissionPercent);
        if(siteID == SiteUtil.TICKET_DISTRIBUTION_CENTER_ID){
            if(sellPrice <= 8000){
                commission = 820;
            }
        }
        log("手数料",commission);
        int uketori = sellPrice - commission;
        //利益
        int profits =  uketori - listPrice;
        if(siteID == SiteUtil.TICKET_DISTRIBUTION_CENTER_ID){
            profits -= shippingPrice;
        }
        log("利益",profits);
        TextView textView = (TextView)findViewById(R.id.result);
        String result =
                "\n手数料率:" + (commissionPercent*100) + "%" +
                "\n売価:" + sellPrice +
                "\n手数料:" + commission +
                "\n受取金額:" + uketori +
                "\n送料:" + shippingPrice +
                "\n利益:" + profits ;
        textView.setText(result);
    }

    String getTextFromTextView(int id){
        EditText editText = (EditText)findViewById(id);
        SpannableStringBuilder sp = (SpannableStringBuilder)editText.getText();
        return sp.toString();
    }

    int getNumberFromTextView(int id){
        String text = getTextFromTextView(id);
        try{
            return Integer.parseInt(text);
        }catch (NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }

    int getSpinnerSelectedId(int id){
        Spinner spinner = (Spinner) findViewById(id);
        return ((KeyValuePair)spinner.getSelectedItem()).getKey();
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
        Spinner spinner = (Spinner) findViewById(R.id.site);
        // アダプターを設定します
        spinner.setAdapter(adapter);

    }



    /**
     * 発送方法を設定する
     */
    void setShipping(){
        KeyValuePairAdapter adapter = new KeyValuePairAdapter(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテムを追加します
        SparseArray sparseArray = ShippingUtil.getWordArray();
        for (int i=0 ; i<sparseArray.size() ; i++){
            adapter.add(new KeyValuePair(sparseArray.keyAt(i),(String)sparseArray.valueAt(i)));
        }
        Spinner spinner = (Spinner) findViewById(R.id.shipping);
        // アダプターを設定します
        spinner.setAdapter(adapter);
    }


    void log(String word, int val){
        System.out.println(word + ":"+val);
    }

}
