package example.com.ticketreservation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import java.util.ArrayList;
/*
* Assignment No. HomeWork 02
* File Name: MainActivity.java
* Full Name: Kedar Kulkarni, Dnyanshree Shengulwar, Marissa McLaughlin
* */
public class DeleteTicketActivity extends Activity {
    ArrayList<Ticket> ticketList;
    int chosenID = -1;
    private EditText etDepartDate, etDepartTime, etReturnDate, etReturnTime;
    private RadioButton rbOneWay, rbRoundTrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_ticket);
        final EditText etName = (EditText) findViewById(R.id.editTextName);
        final EditText etSource = (EditText) findViewById(R.id.editTextSource);
        final EditText etDestination = (EditText) findViewById(R.id.editTextDestination);
        //RadioGroup rgTrip = (RadioGroup) findViewById(R.id.radioGroup);
        etDepartDate = (EditText) findViewById(R.id.editTextDepartureDate);
        etDepartTime = (EditText) findViewById(R.id.editTextDepartureTime);
        etReturnDate = (EditText) findViewById(R.id.editTextReturnDate);
        etReturnTime = (EditText) findViewById(R.id.editTextReturnTime);
        final Button btnSelectTckt = (Button) findViewById(R.id.buttonSelectTicket);
        rbOneWay = (RadioButton) findViewById(R.id.radioButtonOneWay);
        rbRoundTrip = (RadioButton) findViewById(R.id.radioButtonRoundTrip);

        etName.setKeyListener(null);
        etSource.setKeyListener(null);
        etDestination.setKeyListener(null);
        etDepartDate.setKeyListener(null);
        etDepartTime.setKeyListener(null);
        etReturnDate.setKeyListener(null);
        etReturnTime.setKeyListener(null);
        rbOneWay.setClickable(false);
        rbRoundTrip.setClickable(false);

        ticketList = getIntent().getParcelableArrayListExtra(MainActivity.TICKETLIST_KEY);
        final CharSequence[] nameArray = new CharSequence[ticketList.size()];
        int i=0;
        for (Ticket t: ticketList) {
            nameArray[i] = t.getName();
            i++;
        }
        btnSelectTckt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder namesAlert = new AlertDialog.Builder(DeleteTicketActivity.this);
                namesAlert.setTitle(R.string.Pick_Ticket)
                        .setItems(nameArray, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                chosenID = which;
                                Ticket t = ticketList.get(which);
                                Log.d("Demo", ticketList.get(which).getName());
                                etName.setText(t.getName());
                                etSource.setText(t.getSource());
                                etDestination.setText(t.getDestination());
                                if (t.getTrip().equalsIgnoreCase("One-Way")) {
                                    rbOneWay.setChecked(true);
                                    etDepartDate.setText(t.getDepartureDate());
                                    etDepartTime.setText(t.getDepartureTime());
                                } else {
                                    rbRoundTrip.setChecked(true);
                                    etDepartDate.setText(t.getDepartureDate());
                                    etDepartTime.setText(t.getDepartureTime());
                                    etReturnDate.setVisibility(View.VISIBLE);
                                    etReturnTime.setVisibility(View.VISIBLE);
                                    etReturnDate.setText(t.getReturnDate());
                                    etReturnTime.setText(t.getReturnTime());
                                }

                            }
                        });
                namesAlert.show();
                btnSelectTckt.setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.DELETE_TICKET_ID_KEY, chosenID);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
