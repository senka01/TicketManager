package Util;

import android.app.AlertDialog;
import android.content.Context;

public class AlertUtil {

    public static void showAlert(Context context,String title,String message){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();

    }
    public static void showInputAlert(Context context,String message){
        new AlertDialog.Builder(context)
                .setTitle("入力エラー")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();

    }
}
