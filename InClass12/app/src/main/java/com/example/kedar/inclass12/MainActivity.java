package com.example.kedar.inclass12;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Login.OnFragmentInteractionListener {
    public static String URL_PATH="https://stay-in-touch22.firebaseio.com/";
    public static ArrayList<Conversations> conversationsList;
    public static ArrayList<User> contactList;
    public static Firebase myFirebaseRef;
    private String[] navDrawerOptions = {"Contacts","Conversation","Archived","Settings","Log Out","Exit"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private TextView tvFullName;
    private ImageView imageViewProfilePic;
    public static User loggedInUser;
    private boolean isCalled=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase(URL_PATH);
        tvFullName=(TextView)findViewById(R.id.textViewFullName);
        imageViewProfilePic=(ImageView)findViewById(R.id.imageViewProfilePic);

        tvFullName.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        AuthData authData = myFirebaseRef.getAuth();
        if(!isCalled){
            if (authData != null) {
                setNavigationDrawer();
                isCalled=true;
                Toast.makeText(MainActivity.this,"onResume is Called!",Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction().replace(R.id.FragmentContainer,new Conversation()).commit();
            }
            else
                getFragmentManager().beginTransaction().replace(R.id.FragmentContainer,new Login()).commit();
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()>0)
            getFragmentManager().popBackStack();

    }

    @Override
    public void setNavigationDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_list);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, navDrawerOptions));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        final Firebase UserRef = MainActivity.myFirebaseRef.child("users").child(MainActivity.myFirebaseRef.getAuth().
                getProviderData().get("email").toString().split("@")[0]);

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    MainActivity.loggedInUser = dataSnapshot.getValue(User.class);
                    tvFullName.setText(loggedInUser.getName());
                    tvFullName.setVisibility(View.VISIBLE);
                    byte[] decodedString = Base64.decode(loggedInUser.getProfilePic(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageViewProfilePic.setImageBitmap(decodedByte);


                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position){

        switch(navDrawerOptions[position]){



            case "Contacts":
                getFragmentManager().beginTransaction().replace(R.id.FragmentContainer,new Contacts()).addToBackStack(null).commit();
                break;
            case "Settings":
                getFragmentManager().beginTransaction().replace(R.id.FragmentContainer,new EditProfile()).addToBackStack(null).commit();
                break;
            case "Conversation":
                getFragmentManager().beginTransaction().replace(R.id.FragmentContainer,new Conversation()).commit();
                break;
            case "Log Out": myFirebaseRef.unauth();
                    imageViewProfilePic.setImageBitmap(null);
                    tvFullName.setText(null);
                    getFragmentManager().beginTransaction().replace(R.id.FragmentContainer,new Login()).commit();
                    mDrawerList.setAdapter(null);
                break;
            case "Exit":finish();
        }
        mDrawerList.clearChoices();
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }
    /** Swaps fragments in the main content view
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = new Conversation();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(navDrawerOptions[position]);

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }*/
}
