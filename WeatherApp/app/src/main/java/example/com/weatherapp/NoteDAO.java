package example.com.weatherapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dnyanshree on 3/19/2016.
 */
public class NoteDAO { private SQLiteDatabase db;

    public NoteDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Note note){
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_CITYKEY, note.getCitykey());
        values.put(NotesTable.COLUMN_DATE, note.getDate());
        values.put(NotesTable.COLUMN_NOTE, note.getNote());
        return db.insert(NotesTable.TABLENAME, null, values);
    }

    public boolean update(Note note){
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_CITYKEY, note.getCitykey());
        values.put(NotesTable.COLUMN_DATE, note.getDate());
        values.put(NotesTable.COLUMN_NOTE, note.getNote());
        return db.update(NotesTable.TABLENAME, values, NotesTable.COLUMN_CITYKEY + "=?", new String[]{note.getCitykey() + ""})>0;
    }
    public boolean delete(long citykey, String date){
        return db.delete(NotesTable.TABLENAME, NotesTable.COLUMN_CITYKEY + "=? and " + NotesTable.COLUMN_DATE + "=?", new String[]{citykey + "",date}) > 0;
    }
    public Note get(long citykey){
        Note note = null;
        Cursor c = db.query(true, NotesTable.TABLENAME, new String[]{NotesTable.COLUMN_CITYKEY, NotesTable.COLUMN_DATE,
                        NotesTable.COLUMN_NOTE},NotesTable.COLUMN_CITYKEY + "=?",
                new String[]{citykey + ""}, null, null, null, null, null);
        if(c != null && c.moveToFirst()){
            note = buildNoteFromCursor(c);
            if(!c.isClosed()){
                c.close();
            }
        }
        return note;
    }

    public boolean hasNote(long cityKey, String date){
        Note note;
        Cursor c = db.query(true, NotesTable.TABLENAME, new String[]{NotesTable.COLUMN_CITYKEY,NotesTable.COLUMN_DATE,
                        NotesTable.COLUMN_NOTE
                        },NotesTable.COLUMN_CITYKEY + "=? and " + NotesTable.COLUMN_DATE + "=?",
                new String[]{cityKey+"", date}, null, null, null, null, null);
        if(c != null && c.moveToFirst() ){
            note = buildNoteFromCursor(c);
            Log.d("HasNOte",note.toString());
            if(!c.isClosed()){
                c.close();
            }
            return true;
        }
        return false;
    }
    public boolean deleteAll(){
        return db.delete(NotesTable.TABLENAME, null, null) > 0;
    }
    public List<Note> getAll(){
        List<Note> notes = new ArrayList<Note>();
        Cursor c = db.query(NotesTable.TABLENAME, new String[]{
                NotesTable.COLUMN_CITYKEY, NotesTable.COLUMN_DATE,
                NotesTable.COLUMN_NOTE}, null, null, null, null, null);
        if(c != null && c.moveToFirst()){
            do{
                Note note = buildNoteFromCursor(c);
                if(note != null){
                    notes.add(note);
                }
            }while(c.moveToNext());
        }
        return notes;
    }
    public List<Note> getAll(long citykey){
        List<Note> notes = new ArrayList<Note>();
        Cursor c = db.query(true, NotesTable.TABLENAME, new String[]{NotesTable.COLUMN_CITYKEY, NotesTable.COLUMN_DATE,
                        NotesTable.COLUMN_NOTE},NotesTable.COLUMN_CITYKEY + "=?",
                new String[]{citykey + ""}, null, null, null, null, null);
        if(c != null && c.moveToFirst()){
            do{
                Note note = buildNoteFromCursor(c);
                if(note != null){
                    notes.add(note);
                }
            }while(c.moveToNext());
        }
        return notes;
    }
    private Note buildNoteFromCursor(Cursor c){
        Note note = null;
        if(c != null){
            note = new Note();
            note.setCitykey(c.getLong(0));
            note.setDate(c.getString(1));
            note.setNote(c.getString(2));
        }
        return note;
    }
}

