package example.com.sqlitenotesdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class NoteDAO {
    private SQLiteDatabase db;

    public NoteDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Note note){
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_SUBJECT, note.getSubject());
        values.put(NotesTable.COLUMN_TEXT, note.getText());
        return db.insert(NotesTable.TABLENAME, null, values);
    }

    public boolean update(Note note){
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_SUBJECT, note.getSubject());
        values.put(NotesTable.COLUMN_TEXT, note.getText());
        return db.update(NotesTable.TABLENAME, values, NotesTable.COLUMN_ID + "=?", new String[]{note.get_id() + ""})>0;
    }
    public boolean delete(Note note){
        return db.delete(NotesTable.TABLENAME, NotesTable.COLUMN_ID + "=?", new String[]{note.get_id() + ""}) > 0;
    }
    public Note get(long id){
        Note note = null;
        Cursor c = db.query(true, NotesTable.TABLENAME, new String[]{NotesTable.COLUMN_ID, NotesTable.COLUMN_SUBJECT,
                NotesTable.COLUMN_TEXT},NotesTable.COLUMN_ID + "=?",
                new String[]{id + ""}, null, null, null, null, null);
        if(c != null && c.moveToFirst()){
            note = buildNoteFromCursor(c);
            if(!c.isClosed()){
                c.close();
            }
        }
      return note;
    }
    public List<Note> getAll(){
        List<Note> notes = new ArrayList<Note>();
        Cursor c = db.query(NotesTable.TABLENAME, new String[]{
                NotesTable.COLUMN_ID, NotesTable.COLUMN_SUBJECT,
                NotesTable.COLUMN_TEXT}, null, null, null, null, null);
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
            note.set_id(c.getLong(0));
            note.setSubject(c.getString(1));
            note.setText(c.getString(2));
        }
        return note;
    }
}
