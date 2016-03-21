package example.com.weatherapp;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Dnyanshree on 3/19/2016.
 */
public class NotesTable {
    static final String TABLENAME = "notes";
    static final String COLUMN_CITYKEY = "citykey";
    static final String COLUMN_DATE = "date";
    static final String COLUMN_NOTE = "note";
    static public void  onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLENAME + " (");
        sb.append(COLUMN_CITYKEY + " integer, ");
        sb.append(COLUMN_DATE + " text not null, ");
        sb.append(COLUMN_NOTE + " text not null);");
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