package example.com.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNotesActivity extends AppCompatActivity {
    String city, state, date;
    int position;
    DatabaseDataManager dm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        dm = DatabaseDataManager.getDatabaseDataManagerInstance();
        dm.init(this);
        city = getIntent().getStringExtra("City");
        state = getIntent().getStringExtra("State");
        date = getIntent().getStringExtra("Date");
        position = getIntent().getIntExtra("clickedID",0);


        Button saveNote = (Button) findViewById(R.id.buttonSaveNote);

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etNote = (EditText) findViewById(R.id.editTextNote);

                Note note = new Note();
                //Log.d("AddNotes", etNote.getText().toString());
                if(etNote.getText().toString().isEmpty()) {
                    Toast.makeText(AddNotesActivity.this,"Please enter a note..",Toast.LENGTH_LONG).show();
                    return;
                }
                note.setNote(etNote.getText().toString());
                note.setDate(date);
                note.setCitykey(dm.getCitykey(city, state));
                dm.saveNote(note);
                Intent intent = new Intent();
                intent.putExtra("Flag", position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });



    }
}
