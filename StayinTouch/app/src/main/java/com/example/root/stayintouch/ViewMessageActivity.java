package com.example.root.stayintouch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewMessageActivity extends AppCompatActivity {
    EditText etMessage;
    ListView messagesListView;
    public static User user,loggedInUser;
    String currentLogggedInUserFullName, userId;
    ArrayList<Messages> messagesList = new ArrayList<>();
    String sender;
    AuthData authData;
    MessagesAdapter adapter;

    @Override
    public void onBackPressed() {
       ConversationActivity.adapter.notifyDataSetChanged();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);


        final Firebase myUserRef = new Firebase(MainActivity.URL_PATH + "/users");
        etMessage = (EditText) findViewById(R.id.editTextMssage);
        messagesListView = (ListView) findViewById(R.id.listViewConversation);


        Firebase root = new Firebase(MainActivity.URL_PATH);

        if (getIntent() != null) {
            user = getIntent().getParcelableExtra("User");
            user.setHasUnreadMsg("false");
            ConversationActivity.contactsList.set(getIntent().getIntExtra("Position", -1), user);

            //getSupportActionBar().setTitle(user.getFullName());
        }

        authData = root.getAuth();
        if (authData != null) {
            final Firebase loggedInUserUrl = new Firebase(MainActivity.URL_PATH+"/users/" + authData.getProviderData().get("email").toString().split("@")[0]);
            loggedInUserUrl.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    loggedInUser = snapshot.getValue(User.class);

                    currentLogggedInUserFullName = loggedInUser.getName();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });
            userId = authData.getUid();
        }

        //Firebase.setAndroidContext(this);


        final Firebase myFirebaseRef = new Firebase(MainActivity.URL_PATH+"/Messages/");

        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                messagesList.clear();

                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {

                    Messages msg = messageSnapshot.getValue(Messages.class);
                    msg.setKey(messageSnapshot.getKey());
                    if ((msg.getSender().equals(currentLogggedInUserFullName) && msg.getReceiver().equals(user.getName())) ||
                            (msg.getSender().equals(user.getName()) && msg.getReceiver().equals(currentLogggedInUserFullName))){
                        if(msg.getReceiver().equals(currentLogggedInUserFullName) && msg.isMessage_read()==false){
                            Firebase updateMessage  = new Firebase(MainActivity.URL_PATH+"/Messages/"+msg.getKey());
                            msg.setMessage_read(true);
                            updateMessage.setValue(msg);
                        }
                        messagesList.add(msg);
                    }
                }
                adapter.notifyDataSetChanged();
                //msgListView.smoothScrollToPosition(adapter.getCount());
                //messagesListView.setSelection(msgListView.getCount() - 1);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });



        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Messages msg;
                if (etMessage.getText().toString().isEmpty()) {
                    Toast.makeText(ViewMessageActivity.this, "Message can not be blank", Toast.LENGTH_SHORT).show();
                } else {
                    final String date = DateFormat.getDateTimeInstance().format(new Date());
                    msg = new Messages(date, false, etMessage.getText().toString(), user.getName(),currentLogggedInUserFullName);
                    etMessage.setText("");
                    myFirebaseRef.push().setValue(msg, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            System.out.println("done");
                        }
                    });
                }
            }
        });
        adapter = new MessagesAdapter(this, R.layout.messages_list_row, messagesList);
        messagesListView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        adapter.notifyDataSetChanged();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_for_view_msg,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.call_contact:

                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getIntent().getStringExtra("phone")));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return false;
                }

                startActivity(i);
               // Intent i = new Intent(android.content.Intent.ACTION_CALL, Uri.parse("tel:" + mData.get(pos).getPhone()));
                //startActivity(i);
                break;
            case R.id.view_contact:
                Intent intent = new Intent(ViewMessageActivity.this,ViewContactActivity.class);
                intent.putExtra("name", getIntent().getStringExtra("name"));
                startActivity(intent);
                break;
        }
        return true;
    }

}
