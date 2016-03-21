package example.com.weatherapp;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Dnyanshree on 3/19/2016.
 */
public class CitiesTable {
    static final String TABLENAME = "cities";
    static final String COLUMN_CITYKEY = "citykey";
    static final String COLUMN_CITYNAME = "cityname";
    static final String COLUMN_STATE = "state";
    static public void  onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLENAME + " (");
        sb.append(COLUMN_CITYKEY + " integer primary key autoincrement, ");
        sb.append(COLUMN_CITYNAME + " text not null, ");
        sb.append(COLUMN_STATE + " text not null);");
        try{
            db.execSQL(sb.toString());
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME + ";");
        CitiesTable.onCreate(db);
    }
}
