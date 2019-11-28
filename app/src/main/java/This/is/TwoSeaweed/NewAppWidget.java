package This.is.TwoSeaweed;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import This.is.TwoSeaweed.Home.DBHelper;
import This.is.TwoSeaweed.Home.contents;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    private static final String ACTION_BUTTON1 = "com.example.gyu_won.lunch_for_sunrin.Refresh";
    static StringBuffer list;
    static String year, month, day;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        views.setTextViewText(R.id.appwidget_text, list);

        Intent intentSync = new Intent(context, NewAppWidget.class);
        intentSync.setAction(ACTION_BUTTON1); //You need to specify the action for the intent. Right now that intent is doing nothing for there is no action to be broadcasted.

        PendingIntent pendingSync = PendingIntent.getBroadcast(context, 0, intentSync, PendingIntent.FLAG_UPDATE_CURRENT); //You need to specify a proper flag for the intent. Or else the intent will become deleted.
        views.setOnClickPendingIntent(R.id.button, pendingSync);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        update u = new update(context, appWidgetManager, appWidgetIds);
        u.thread.start();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), NewAppWidget.class.getName());
        int[] appWidgets = appWidgetManager.getAppWidgetIds(thisAppWidget);
//        String action = intent.getAction();
        if (intent.getAction().equals(ACTION_BUTTON1)) {
            onUpdate(context, appWidgetManager, appWidgets);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static String getDate() {
        String curdate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddE");
        Date date = new Date();
        curdate = sdf.format(date);
        return curdate;
    }

    public static class update {
        Context context;
        AppWidgetManager appWidgetManager;
        int[] appWidgetIds;
        String date;

        update(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
            this.context = context;
            this.appWidgetIds = appWidgetIds;
            this.appWidgetManager = appWidgetManager;
            date = NewAppWidget.getDate();
            list= new StringBuffer();
        }

        Handler handler = new Handler();

        Thread thread = new Thread() {
            public void run() {
                list.replace(0,list.length(),"");
                list.append("Reloading");
                for (int appWidgetId : appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId);
                }
                list.replace(0,list.length(),"");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //데이터 가져오기

                        date = getDate();
                        year = date.substring(0, 4);
                        month = date.substring(4, 6);
                        day = date.substring(6, 8);

                        DBHelper dbHelper = new DBHelper(context);
                        SQLiteDatabase db = dbHelper.getReadableDatabase();
                        String inquirySQL = "select * from tb_data where year='" + year + "' AND month='" + month + "' AND day='" + day + "'";
                        //데이터 받아오기
                        Cursor cursor = db.rawQuery(inquirySQL, null);
//                        Log.e("date",date);

                        if (!cursor.moveToNext()) {
                            list.replace(0,list.length(),"");
                            list.append("Nothing to do");
//                            Log.e("qwe","nothing to do");
                        }else{
                            String content = cursor.getString(4);
                            int state = cursor.getInt(5);
                            //텍스트 append
                            if (state != 3) {
                                list.append(content+"\n");
                            }
                        }

                        while (cursor.moveToNext()) {
                            String content = cursor.getString(4);
                            int state = cursor.getInt(5);
                            //텍스트 append
                            if (state != 3) {
                                list.append(content+"\n");
                            }
                        }
                        db.close();
                        for (int appWidgetId : appWidgetIds) {
                            updateAppWidget(context, appWidgetManager, appWidgetId);
                        }
                    }
                });
            }
        };
    }

}

