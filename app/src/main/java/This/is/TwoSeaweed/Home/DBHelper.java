package This.is.TwoSeaweed.Home;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "datadb", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableSql = "Create table tb_data ("+"_id integer primary key autoincrement," +
                " year not null," +
                " month not null," +
                " day not null," +
                " curday not null," +
                " content not null," +
                " state not null)";
        db.execSQL(tableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i1 == 5){
            sqLiteDatabase.execSQL("drop  table tb_data");
            onCreate(sqLiteDatabase);
        }
    }
}
