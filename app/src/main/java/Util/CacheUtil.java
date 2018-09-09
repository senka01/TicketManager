package Util;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.SparseArray;

import com.example.hayao.ticketter.OrmaDatabaseProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Event;
import Model.Event_Selector;
import Model.OrmaDatabase;
import Model.Ticket;
import Model.User;
import Model.User_Selector;

public class CacheUtil {
    private final static SparseArray<User> userCache = new SparseArray<User>();
    private final static SparseArray<Event> eventCache = new SparseArray<Event>();
    private static List<Ticket> ticketCache = new ArrayList<Ticket>();

    public static int getTicketSumPrice() {
        return ticketSumPrice;
    }

    public static int getUndecidedTicketSumPrice() {
        return undecidedTicketSumPrice;
    }

    private static int ticketSumPrice = 0;
    private static int undecidedTicketSumPrice = 0;
    private CacheUtil(){
    }
    public static void loadUserCache(final Context context){
        userCache.clear();
        new Thread(){
            @Override
            public void run() {
                OrmaDatabase ormaDatabase = OrmaDatabaseProvider.getInstance(context);
                List<User> userSelector = ormaDatabase.selectFromUser().toList();
                for (User user:userSelector) {
                    userCache.put((int)user.getId(),user);
                }
            }
        }.start();
    }
    public static void loadTicketCache(final Context context){
        ticketCache.clear();
        new Thread(){
            @Override
            public void run() {
                OrmaDatabase ormaDatabase = OrmaDatabaseProvider.getInstance(context);
                String whereSql = "";
                Cursor cursor = ormaDatabase.getConnection().rawQuery("select ticket.* from ticket " +"" +
                        " inner join event on ticket.event = event.id " +
                        "" +whereSql +
                        " order by event.date asc ,event.id asc,  seat asc ,sellPrice desc,opponent asc  " );
                while(cursor.moveToNext()){
                    ticketCache.add(ormaDatabase.newTicketFromCursor(cursor));
                }
                ticketSumPrice = 0;
                undecidedTicketSumPrice = 0;
                for (Ticket ticket : ticketCache) {
                    if(ticket.isPaidFlg()){
                        continue;
                    }
                    int ticketPrice = TicketUtil.getPrice(ticket);
                    ticketSumPrice += ticketPrice;
                    if(ticket.getSellSite() == 0 && ticket.opponent.getId() == 0 ){
                        undecidedTicketSumPrice += ticketPrice;
                    }
                }
                Log.d("ticketSumPrice",String.valueOf(ticketSumPrice));
                Log.d("undecidedTicketSumPrice",String.valueOf(undecidedTicketSumPrice));
            }
        }.start();
    }
    public static void loadEventCache(final Context context){
        eventCache.clear();
        new Thread(){
            @Override
            public void run() {
                OrmaDatabase ormaDatabase = OrmaDatabaseProvider.getInstance(context);
                List<Event> eventSelector = ormaDatabase.selectFromEvent().toList();
                for (Event event:eventSelector) {
                    eventCache.put((int)event.getId(),event);
                }
            }
        }.start();
    }

    public static Event getEvent(long eventId){
        return eventCache.get((int)eventId);
    }

    public static User getUser(long userId){
        return userCache.get((int)userId);
    }

    public static Ticket getTicket(long ticketId){
        return ticketCache.get((int)ticketId);
    }

    public static List<Ticket> getTicketCache(){
        return ticketCache;
    }


}
