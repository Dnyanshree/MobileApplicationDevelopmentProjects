package example.com.weatherapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dnyanshree on 3/19/2016.
 */
public class DatabaseDataManager {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private NoteDAO noteDAO;
    private CityDAO cityDAO;
    private static DatabaseDataManager dm;

    public static DatabaseDataManager getDatabaseDataManagerInstance(){
    if(dm==null){
        dm=new DatabaseDataManager();
    }
        return dm;
    }
    private DatabaseDataManager(){}
    public void init(Context mContext) {
        this.mContext = mContext;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        noteDAO = new NoteDAO(db);
        cityDAO = new CityDAO(db);
    }
    public void close(){
        if(db != null){
            db.close();
        }
    }
    public NoteDAO getNoteDAO(){
        return this.noteDAO;
    }
    public long saveNote(Note note){
        return this.noteDAO.save(note);
    }
    public boolean updateNote(Note note){
        return this.noteDAO.update(note);
    }
    public boolean deleteNote(long cityKey,String date){
        return this.noteDAO.delete(cityKey,date);
    }
    public Note getNote(long id){
        return this.noteDAO.get(id);
    }
    public List<Note> getAllNotes(){
        return this.noteDAO.getAll();
    }
    public List<Note> getAllNotes(long citykey){
        return this.noteDAO.getAll(citykey);
    }
    public CityDAO getCityDAO(){
        return this.cityDAO;
    }
    public long saveCity(City city){
        return this.cityDAO.save(city);
    }
    public boolean updateCity(City city){
        return this.cityDAO.update(city);
    }
    public boolean deleteCity(City city){
        return this.cityDAO.delete(city);
    }
    public City getCity(long id){
        return this.cityDAO.getCity(id);
    }
    public long getCitykey(String cityName, String state){
        return this.cityDAO.getCitykey(cityName, state);
    }
    public ArrayList<City> getAllCities(){
        return this.cityDAO.getAll();
    }
    public boolean deleteAllCities(){return this.cityDAO.deleteAll();}
    public boolean hasNote(long cityKey,String date){
        return this.noteDAO.hasNote(cityKey, date);
    }
    public boolean deleteAllNotes(){return this.noteDAO.deleteAll();}
}
