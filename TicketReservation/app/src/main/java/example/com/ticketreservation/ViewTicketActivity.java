package example.com.ticketreservation;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
/*
* Assignment No. HomeWork 02
* File Name: MainActivity.java
* Full Name: Kedar Kulkarni, Dnyanshree Shengulwar, Marissa McLaughlin
* */
public class ViewTicketActivity extends Activity {
    ArrayList<Ticket> ticketList;
    Ticket ticketNow;
    int chosenID;
    private EditText etName, etSource, etDestination, etDepartDate, etDepartTime, etReturnDate, etReturnTime;
    ImageView first, prev, next, last;
    private RadioButton rbOneWay, rbRoundTrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ticket);

        etName = (EditText) findViewById(R.id.editTextName);
        etSource = (EditText) findViewById(R.id.editTextSource);
        etDestination = (EditText) findViewById(R.id.editTextDestination);
        RadioGroup rgTrip = (RadioGroup) findViewById(R.id.radioGroup);
        etDepartDate = (EditText) findViewById(R.id.editTextDepartureDate);
        etDepartTime = (EditText) findViewById(R.id.editTextDepartureTime);
        etReturnDate = (EditText) findViewById(R.id.editTextReturnDate);
        etReturnTime = (EditText) findViewById(R.id.editTextReturnTime);
        rbOneWay = (RadioButton) findViewById(R.id.radioButtonOneWay);
        rbRoundTrip = (RadioButton) findViewById(R.id.radioButtonRoundTrip);

        first = (ImageView) findViewById(R.id.imageViewFirst);
        prev = (ImageView) findViewById(R.id.imageViewPrevious);
        next = (ImageView) findViewById(R.id.imageViewNext);
        last = (ImageView) findViewById(R.id.imageViewLast);

        etName.setKeyListener(null);
        etSource.setKeyListener(null);
        etDestination.setKeyListener(null);
        etDepartDate.setKeyListener(null);
        etDepartTime.setKeyListener(null);
        etReturnDate.setKeyListener(null);
        etReturnTime.setKeyListener(null);
        rgTrip.setClickable(false);
        rbOneWay.setClickable(false);
        rbRoundTrip.setClickable(false);

        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if(getIntent().getExtras() != null){
            if(getIntent().getExtras().containsKey(MainActivity.VIEW_TICKET_KEY)) {
                ticketList = getIntent().getParcelableArrayListExtra(MainActivity.VIEW_TICKET_KEY);

                if(ticketList==null || ticketList.size()==0){
                    first.setClickable(false);
                    prev.setClickable(false);
                    next.setClickable(false);
                    last.setClickable(false);
                }else if (ticketList != null && !ticketList.isEmpty()){
                    first.setClickable(true);
                    prev.setClickable(true);
                    next.setClickable(true);
                    last.setClickable(true);

                    chosenID = 0;
                    ticketNow = ticketList.get(chosenID);
                    displayContents();
                }

            }
        }

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ticketList!=null && !ticketList.isEmpty()){
                    chosenID = 0;
                    ticketNow = ticketList.get(chosenID);
                    displayContents();
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenID = (chosenID - 1) > 0 ? (chosenID-1) : 0;
                if(ticketList!=null && !ticketList.isEmpty()){
                    ticketNow = ticketList.get(chosenID);
                    displayContents();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenID = (chosenID + 1) < ticketList.size() ? (chosenID + 1) : 0;
                if(ticketList!=null && !ticketList.isEmpty()){
                    ticketNow = ticketList.get(chosenID);
                    displayContents();
                }
            }
        });

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenID = ticketList.size()-1;
                if(ticketList!=null && !ticketList.isEmpty()){
                    ticketNow = ticketList.get(chosenID);
                    displayContents();
                }
            }
        });

    }

    public void displayContents(){
        etName.setText(ticketNow.getName());
        etSource.setText(ticketNow.getSource());
        etDestination.setText(ticketNow.getDestination());
        etDepartDate.setText(ticketNow.getDepartureDate());
        etDepartTime.setText(ticketNow.getDepartureTime());

        if(ticketNow.getTrip().equalsIgnoreCase("Round-Trip")){
            rbRoundTrip.setChecked(true);
            etDepartDate.setText(ticketNow.getDepartureDate());
            etDepartTime.setText(ticketNow.getDepartureTime());
            etReturnDate.setVisibility(View.VISIBLE);
            etReturnTime.setVisibility(View.VISIBLE);
            etReturnDate.setText(ticketNow.getReturnDate());
            etReturnTime.setText(ticketNow.getReturnTime());
        }else{
            rbOneWay.setChecked(true);
            etDepartDate.setText(ticketNow.getDepartureDate());
            etDepartTime.setText(ticketNow.getDepartureTime());
            etReturnDate.setVisibility(View.INVISIBLE);
            etReturnTime.setVisibility(View.INVISIBLE);
        }
    }

}
