package example.com.ticketreservation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
/*
* Assignment No. HomeWork 02
* File Name: MainActivity.java
* Full Name: Kedar Kulkarni, Dnyanshree Shengulwar, Marissa McLaughlin
* */
public class PrintTicketActivity extends Activity {
    TextView name, source, destination, departureDate, departureTime, returnDate, returnTime, returnLabel;
    Ticket ticket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_ticket);

        name = (TextView) findViewById(R.id.textViewNameDisplay);
        source = (TextView) findViewById(R.id.textViewSourceDisplay);
        destination = (TextView) findViewById(R.id.textViewDestinationDisplay);
        departureDate = (TextView) findViewById(R.id.textViewDepartureDisplay);
        departureTime = (TextView) findViewById(R.id.textViewDepartTimeDisplay);
        returnDate = (TextView) findViewById(R.id.textViewReturnDisplay);
        returnTime = (TextView) findViewById(R.id.textViewReturnTimeDisplay);
        returnLabel = (TextView) findViewById(R.id.textViewReturn);

        ticket = getIntent().getExtras().getParcelable(CreateTicketActivity.PRINT_TICKET_KEY);
        name.setText(ticket.getName());
        source.setText(" " + ticket.getSource());
        destination.setText(" " + ticket.getDestination());
        departureDate.setText(" " + ticket.getDepartureDate() + ", ");
        departureTime.setText(" " + ticket.getDepartureTime());
        if(ticket.getTrip().equalsIgnoreCase("One-Way")){
            returnDate.setVisibility(View.INVISIBLE);
            returnTime.setVisibility(View.INVISIBLE);
            returnLabel.setVisibility(View.INVISIBLE);
        }else if(ticket.getTrip().equalsIgnoreCase("Round-Trip")){
            returnDate.setText("  " + ticket.getReturnDate() + ", ");
            returnTime.setText(" " + ticket.getReturnTime());
        }
        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(PrintTicketActivity.this, MainActivity.class);
               // startActivity(intent);
                finish();
            }
        });
    }
}
