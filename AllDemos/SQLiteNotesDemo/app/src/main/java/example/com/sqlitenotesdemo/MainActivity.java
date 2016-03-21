package example.com.sqlitenotesdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseDataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dm = new DatabaseDataManager(this);

        dm.saveNote(new Note("Note 1","Note 1 text"));
        dm.saveNote(new Note("Note 2","Note 2 text"));
        dm.saveNote(new Note("Note 3","Note 3 text"));

        List<Note> notes = dm.getAllNotes();

        Log.d("Demo", notes.toString());
    }

    @Override
    protected void onDestroy() {
        dm.close();
        super.onDestroy();
    }
}
