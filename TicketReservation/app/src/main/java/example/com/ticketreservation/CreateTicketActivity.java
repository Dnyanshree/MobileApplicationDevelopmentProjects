package example.com.ticketreservation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/*
* Assignment No. HomeWork 02
* File Name: MainActivity.java
* Full Name: Kedar Kulkarni, Dnyanshree Shengulwar, Marissa McLaughlin
* */
public class CreateTicketActivity extends Activity {
    private Calendar calendar = Calendar.getInstance();
    String [] cities_array;
    public static SimpleDateFormat dfDate;
    String today;
    final static String PRINT_TICKET_KEY = "PRINT_TICKET";
    private String name, source, destination, trip="One-Way", departureDate, departureTime, returnDate = null, returnTime = null;
    private EditText etDepartDate, etDepartTime, etReturnDate, etReturnTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);
        dfDate = new SimpleDateFormat("MM/dd/yyyy");
        today = dfDate.format(new Date(System.currentTimeMillis()));

        final EditText etName = (EditText) findViewById(R.id.editTextName);
        final EditText etSource = (EditText) findViewById(R.id.editTextSource);
        final EditText etDestination = (EditText) findViewById(R.id.editTextDestination);
        RadioGroup rgTrip = (RadioGroup) findViewById(R.id.radioGroup);
        etDepartDate = (EditText) findViewById(R.id.editTextDepartureDate);
        etDepartTime = (EditText) findViewById(R.id.editTextDepartureTime);
        etReturnDate = (EditText) findViewById(R.id.editTextReturnDate);
        etReturnTime = (EditText) findViewById(R.id.editTextReturnTime);

        etSource.setKeyListener(null);
        etDestination.setKeyListener(null);
        etDepartDate.setKeyListener(null);
        etDepartTime.setKeyListener(null);
        etReturnDate.setKeyListener(null);
        etReturnTime.setKeyListener(null);

        cities_array = getResources().getStringArray(R.array.cities_array);

        final AlertDialog.Builder sourceAlert = new AlertDialog.Builder(CreateTicketActivity.this);
        sourceAlert.setTitle(R.string.Choose_Source)
                .setItems(R.array.cities_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //final String source = cities_array[which];
                        if (cities_array[which].equalsIgnoreCase(etDestination.getText().toString())) {
                            Toast.makeText(CreateTicketActivity.this, R.string.Error_ToastMsg, Toast.LENGTH_LONG).show();
                        } else {
                            etSource.setText(cities_array[which]);
                        }
                    }
                });
        final AlertDialog.Builder destinationAlert = new AlertDialog.Builder(CreateTicketActivity.this);
        destinationAlert.setTitle(R.string.Choose_Destination)
                .setItems(R.array.cities_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //String destination = cities_array[which];
                        if (cities_array[which].equalsIgnoreCase(etSource.getText().toString())) {
                            Toast.makeText(CreateTicketActivity.this, R.string.Error_ToastMsg, Toast.LENGTH_LONG).show();
                        } else {
                            etDestination.setText(cities_array[which]);
                        }
                    }
                });

        rgTrip.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonRoundTrip) {
                    trip = "Round-Trip";
                    etReturnDate.setVisibility(View.VISIBLE);
                    etReturnTime.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.radioButtonOneWay) {
                    trip = "One-Way";
                    returnDate = null; returnTime = null;
                    etReturnDate.setVisibility(View.INVISIBLE);
                    etReturnTime.setVisibility(View.INVISIBLE);
                }
            }
        });
        etSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSource.setError(null);
                sourceAlert.show();
            }
        });
        etDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDestination.setError(null);
                destinationAlert.show();
            }
        });
        etDepartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDepartDate.setError(null);
                new DatePickerDialog(CreateTicketActivity.this, departDateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        etDepartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDepartTime.setError(null);
                new TimePickerDialog(CreateTicketActivity.this, departTimeListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        });
        etReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etReturnDate.setError(null);
                new DatePickerDialog(CreateTicketActivity.this, returnDateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        etReturnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etReturnTime.setError(null);
                new TimePickerDialog(CreateTicketActivity.this, returnTimeListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        });
        findViewById(R.id.buttonPrintSummary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().length()>0)
                    name = etName.getText().toString();
                else
                {
                    etName.setError("Name cannot be empty!");
                    return;
                }
                if(etSource.getText().length()>0)
                    source = etSource.getText().toString();
                else if(etSource.getText().toString().isEmpty())
                {
                    etSource.setError("Choose a Source!");
                    return;
                }
                if(etDestination.getText().length()>0)
                    destination = etDestination.getText().toString();
                else if(etDestination.getText().toString().isEmpty())
                {
                    etDestination.setError("Choose a Destination!");
                    return;
                }
                if(etDepartDate.getText().toString().isEmpty()){
                    etDepartDate.setError("Choose a Destination Date!");
                    return;
                }
                if(etDepartTime.getText().toString().isEmpty()){
                    etDepartTime.setError("Choose a Destination Time!");
                }
                if(trip.equalsIgnoreCase("Round-Trip") && etReturnDate.getText().toString().isEmpty()){
                    etReturnDate.setError("Choose a Return Date!");
                    return;
                }
                if(trip.equalsIgnoreCase("Round-Trip") && etReturnDate.getText().toString().isEmpty()){
                    etReturnTime.setError("Choose a Return Time!");
                    return;
                }
                Intent intent = new Intent(CreateTicketActivity.this, PrintTicketActivity.class);
                Ticket ticket = new Ticket(name, source, destination, trip, departureDate, departureTime, returnDate, returnTime);
                intent.putExtra(PRINT_TICKET_KEY, ticket);
                Intent intent1 = new Intent();
                intent1.putExtra(MainActivity.CREATE_TICKET_KEY, ticket);
                startActivity(intent);
                setResult(RESULT_OK, intent1);
                finish();
                }

        });
    }
    TimePickerDialog.OnTimeSetListener departTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String hourString, minuteSting, AMPM;
            if(hourOfDay<=12){
                AMPM = " AM";
            }else{
                hourOfDay = hourOfDay - 12;
                AMPM = " PM";
            }
            if (hourOfDay < 10)
                hourString = "0" + hourOfDay;
            else
                hourString = "" +hourOfDay;

            if (minute < 10)
                minuteSting = "0" + minute;
            else
                minuteSting = "" +minute;

            etDepartTime.setText("" + hourString + ":" + minuteSting + AMPM);

            departureTime = etDepartTime.getText().toString();
        }
    };
    TimePickerDialog.OnTimeSetListener returnTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String hourString, minuteSting, AMPM;
            if(hourOfDay<=12){
               AMPM = " AM";
            }else{
                hourOfDay = hourOfDay - 12;
                AMPM = " PM";
            }
            if (hourOfDay < 10)
                hourString = "0" + hourOfDay;
            else
                hourString = "" +hourOfDay;

            if (minute < 10)
                minuteSting = "0" + minute;
            else
                minuteSting = "" +minute;

                etReturnTime.setText("" + hourString + ":" + minuteSting + AMPM);

            returnTime = etReturnTime.getText().toString();
        }
    };
    DatePickerDialog.OnDateSetListener departDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear + 1;
            String month, date;
            if(monthOfYear < 10)
                month = "0" + monthOfYear;
            else
                month = "" + monthOfYear;
            if(dayOfMonth < 10)
                date = "0" + dayOfMonth;
            else
                date = "" + dayOfMonth;
            etDepartDate.setText("" + month + "/" + date + "/" + year);
            departureDate = etDepartDate.getText().toString();
            while(CheckForToday(today, etDepartDate.getText().toString())){
                Toast.makeText(getApplicationContext(), "Departure Date cannot be a date before today!", Toast.LENGTH_LONG).show();
                etDepartDate.setText(null);
                departureDate = null;
            }
            while(trip.equalsIgnoreCase("Round-Trip") && CheckDates(etDepartDate.getText().toString(), etReturnDate.getText().toString())){
                Toast.makeText(getApplicationContext(), "Return Date should be after Departure Date!", Toast.LENGTH_LONG).show();
                etDepartDate.setText(null);
                departureDate = null;
            }
        }
    };
    DatePickerDialog.OnDateSetListener returnDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear + 1;
            String month, date;
            if(monthOfYear < 10)
                month = "0" + monthOfYear;
            else
                month = "" + monthOfYear;
            if(dayOfMonth < 10)
                date = "0" + dayOfMonth;
            else
                date = "" + dayOfMonth;
            etReturnDate.setText("" + month + "/" + date + "/" + year);
            returnDate = etReturnDate.getText().toString();
            while(trip.equalsIgnoreCase("Round-Trip") && CheckDates(etDepartDate.getText().toString(), etReturnDate.getText().toString())){
                Toast.makeText(getApplicationContext(), "Return Date should be after Departure Date!", Toast.LENGTH_LONG).show();
                etReturnDate.setText(null);
                returnDate = null;
            }
        }
    };
    public static boolean CheckDates(String departureDate, String returnDate) {
        boolean b = false;
        try {
            if (dfDate.parse(returnDate).before(dfDate.parse(departureDate))) {
                b = true;  // If depart date is before return date.
            } else if (dfDate.parse(departureDate).equals(dfDate.parse(returnDate))) {
                b = true;  // If two dates are equal.
            } else if (dfDate.parse(returnDate).after(dfDate.parse(departureDate))) {
                b = false; // If depart date is after the return date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
            e.getMessage();
        }
        return b;
    }
    public static boolean CheckForToday(String today, String departureDate) {
        boolean b = false;
        try {
            if (dfDate.parse(departureDate).before(dfDate.parse(today))) {
                b = true;
            } else if (dfDate.parse(today).equals(dfDate.parse(departureDate))) {
                b = false;  // If two dates are equal.
            } else if (dfDate.parse(departureDate).after(dfDate.parse(today))) {
                b = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            e.getMessage();
        }
        return b;
    }
}
