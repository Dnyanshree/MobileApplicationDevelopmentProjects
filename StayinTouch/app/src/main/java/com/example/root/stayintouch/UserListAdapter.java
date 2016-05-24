package com.example.root.stayintouch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dnyanshree on 4/16/2016.
 */
public class UserListAdapter extends ArrayAdapter<User> {
    List<User> mData;
    Context mContext;
    int mResourceId;
    ImageView imageViewIsUnread;
    ArrayList<Messages> messagesList = new ArrayList<>();
    final Firebase myFirebaseRef = new Firebase(MainActivity.URL_PATH + "/Messages");
    final Firebase myUserRef= new Firebase(MainActivity.URL_PATH+"/users");


    public UserListAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResourceId = resource;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //if(convertView == null){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(mResourceId, parent, false);
        //}

        final User user = mData.get(position);

        TextView tvName = (TextView) convertView.findViewById(R.id.textViewFullName);
        ImageView imageViewProfilePic = (ImageView) convertView.findViewById(R.id.imageViewProfilePic);
        imageViewIsUnread = (ImageView) convertView.findViewById(R.id.imageViewIsUnread);
        ImageView imageViewCall = (ImageView) convertView.findViewById(R.id.imageViewCall);
        final int pos = position;
        imageViewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(android.content.Intent.ACTION_CALL, Uri.parse("tel:" + mData.get(pos).getPhone()));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                mContext.startActivity(i);
            }
        });

       if(user.getHasUnreadMsg().equals("true"))
            imageViewIsUnread.setVisibility(View.VISIBLE);
        else
            imageViewIsUnread.setVisibility(View.INVISIBLE);


        tvName.setText(mData.get(position).getName());
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ViewMessageActivity.class);
                intent.putExtra("name", mData.get(position).getName());
                intent.putExtra("phone",mData.get(pos).getPhone());
                intent.putExtra("User",mData.get(position));
                intent.putExtra("Position",position);
                mContext.startActivity(intent);
            }
        });



        final String sender =mData.get(position).getName();
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (final DataSnapshot d : dataSnapshot.getChildren()) {
                    if (d.getValue(Messages.class).getSender().equals(sender)) {
                        Firebase userRef = myUserRef.child(myFirebaseRef.getAuth().getProviderData().get("email").toString().split("@")[0]);
                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.getValue(User.class).getName().equals(d.getValue(Messages.class).getReceiver())) {
                                    imageViewIsUnread.setVisibility(View.INVISIBLE);
                                    return;
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        try {
            byte[] decodedString = Base64.decode(mData.get(position).getProfilePic(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageViewProfilePic.setImageBitmap(decodedByte);
        }catch (Exception e){}

        return convertView;
    }
}