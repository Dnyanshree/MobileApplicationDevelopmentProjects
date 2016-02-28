package example.com.ticketreservation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

/*
* Assignment No. HomeWork 02
* File Name: MainActivity.java
* Full Name: Kedar Kulkarni, Dnyanshree Shengulwar, Marissa McLaughlin
* */
public class MainActivity extends Activity {

    public ArrayList<Ticket> ticketList = new ArrayList<>();
    final static String CREATE_TICKET_KEY = "CREATE_TICKET", TICKETLIST_KEY = "TICKETLIST", EDIT_TICKET_KEY = "EDIT_TICKET", EDIT_TICKET_ID_KEY = "EDIT_TICKET_ID", DELETE_TICKET_ID_KEY = "DELETE_TICKET_ID", VIEW_TICKET_KEY = "VIEW_TICKET";
    ;
    public static final int CREATE_REQ_CODE = 100, EDIT_REQ_CODE = 200, DELETE_REQ_CODE = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView Callicon = (ImageView) findViewById(R.id.imageViewCallIcon);


        ticketList.add(new Ticket("Person 1", "Greenville, SC", "Las Vegas, NV", "Round-Trip", "02/15/2016", "11:01 PM", "02/21/2016", "10:03 AM"));
        ticketList.add(new Ticket("Person 2", "Los Angeles, CA", "New York, NY", "Round-Trip", "03/15/2016", "01:15 PM", "04/15/2016", "10:39 AM"));
        ticketList.add(new Ticket("Person 3", "Myrtle Beach, SC", "Las Vegas, NV", "Round-Trip", "02/10/2016", "11:15 PM", "12/15/2016", "10:35 AM"));

        findViewById(R.id.buttonCreateTicket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateTicketActivity.class);
                startActivityForResult(intent, CREATE_REQ_CODE);
            }
        });
        findViewById(R.id.buttonEditTicket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditTicketActivity.class);
                intent.putParcelableArrayListExtra(TICKETLIST_KEY, ticketList);
                startActivityForResult(intent, EDIT_REQ_CODE);
            }
        });
        findViewById(R.id.buttonDeleteTicket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteTicketActivity.class);
                intent.putParcelableArrayListExtra(TICKETLIST_KEY, ticketList);
                startActivityForResult(intent, DELETE_REQ_CODE);
            }
        });
        findViewById(R.id.buttonViewTicket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewTicketActivity.class);
                intent.putExtra(VIEW_TICKET_KEY, ticketList);
                startActivity(intent);
            }
        });
        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.textViewCustomerCare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:999999999"));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CREATE_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                Ticket t = data.getExtras().getParcelable(MainActivity.CREATE_TICKET_KEY);
                ticketList.add(t);
                //Log.d("Demo",data.getExtras().getParcelable(TICKET_KEY).toString());
                }
        }
        if(requestCode == EDIT_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                Ticket t = data.getExtras().getParcelable(MainActivity.EDIT_TICKET_KEY);
                int index = data.getIntExtra(MainActivity.EDIT_TICKET_ID_KEY, -1);
                Log.d("Demo", "Edit index:" + index );
                if(index != -1){
                    ticketList.remove(index);
                    ticketList.add(index,t);
                }
            }
        }
        if(requestCode == DELETE_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                int index = data.getIntExtra(MainActivity.DELETE_TICKET_ID_KEY, -1);
                if(index !=-1){
                    Log.d("Demo", "Delete index:" + index );
                    ticketList.remove(index);
                }
             }
        }
    }

}
