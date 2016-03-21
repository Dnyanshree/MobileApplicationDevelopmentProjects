package example.com.weatherapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dnyanshree on 3/19/2016.
 */
public class CityDAO {
    private SQLiteDatabase db;

    public CityDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(City city){
        ContentValues values = new ContentValues();
        values.put(CitiesTable.COLUMN_CITYNAME, city.getCityname());
        values.put(CitiesTable.COLUMN_STATE, city.getState());
        return db.insert(CitiesTable.TABLENAME, null, values);
    }

    public boolean update(City city){
        ContentValues values = new ContentValues();
        values.put(CitiesTable.COLUMN_CITYNAME, city.getCityname());
        values.put(CitiesTable.COLUMN_STATE, city.getState());
        return db.update(CitiesTable.TABLENAME, values, CitiesTable.COLUMN_CITYKEY + "=?", new String[]{city.getCitykey() + ""})>0;
    }
    public boolean delete(City city){
        return db.delete(CitiesTable.TABLENAME, CitiesTable.COLUMN_CITYNAME + "=? and " + CitiesTable.COLUMN_STATE + "=?",
                new String[]{city.getCityname(), city.getState()}) > 0;
    }
    public boolean deleteAll(){
        return db.delete(CitiesTable.TABLENAME, null, null) > 0;
    }

    public City getCity(long citykey){
        City city = null;
        Cursor c = db.query(true, CitiesTable.TABLENAME, new String[]{CitiesTable.COLUMN_CITYKEY, CitiesTable.COLUMN_CITYNAME,
                        CitiesTable.COLUMN_STATE},CitiesTable.COLUMN_CITYKEY + "=?",
                new String[]{citykey + ""}, null, null, null, null, null);
        if(c != null && c.moveToFirst()){
            city = buildCityFromCursor(c);
            if(!c.isClosed()){
                c.close();
            }
        }
        return city;
    }

    public long getCitykey(String cityName, String state){
        City city = null;
        Cursor c = db.query(true, CitiesTable.TABLENAME, new String[]{CitiesTable.COLUMN_CITYKEY, CitiesTable.COLUMN_CITYNAME,
                        CitiesTable.COLUMN_STATE},CitiesTable.COLUMN_CITYNAME + "=? and " + CitiesTable.COLUMN_STATE + "=?",
                new String[]{cityName, state}, null, null, null, null, null);
        if(c != null && c.moveToFirst()){
            city = buildCityFromCursor(c);
            if(!c.isClosed()){
                c.close();
            }
        }
        return city.getCitykey();
    }
    public ArrayList<City> getAll(){
        ArrayList<City> cities = new ArrayList<City>();
        Cursor c = db.query(CitiesTable.TABLENAME, new String[]{
                CitiesTable.COLUMN_CITYKEY, CitiesTable.COLUMN_CITYNAME,
                CitiesTable.COLUMN_STATE}, null, null, null, null, null);
        if(c != null && c.moveToFirst()){
            do{
                City city = buildCityFromCursor(c);
                if(city != null){
                    cities.add(city);
                }
            }while(c.moveToNext());
        }
        return cities;
    }
    private City buildCityFromCursor(Cursor c){
        City city = null;
        if(c != null){
            city = new City();
            city.setCitykey(c.getLong(0));
            city.setCityname(c.getString(1));
            city.setState(c.getString(2));
        }
        return city;
    }
}
