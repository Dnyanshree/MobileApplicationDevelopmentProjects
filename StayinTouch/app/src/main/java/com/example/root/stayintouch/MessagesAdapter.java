package com.example.root.stayintouch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.List;

/**
 * Created by Dnyanshree on 4/17/2016.
 */
public class MessagesAdapter extends ArrayAdapter<Messages> {
        List<Messages> mData;
        Context mContext;
        int mResourceId;

public MessagesAdapter(Context context, int resource, List<Messages> objects) {
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

        TextView tvName=(TextView) convertView.findViewById(R.id.textViewName);
        ImageView imageViewDelete=(ImageView) convertView.findViewById(R.id.imageViewDelete);

        TextView tvMessage=(TextView) convertView.findViewById(R.id.textViewMessage);
        TextView tvTimestamp=(TextView) convertView.findViewById(R.id.textViewTimestamp);

    final Messages msg  = mData.get(position);

    String loggedInUserName = ViewMessageActivity.loggedInUser.getName();

    if(msg.getSender().equals(loggedInUserName)){
        convertView.setBackgroundColor(Color.parseColor("#8d8d8d"));
        imageViewDelete.setVisibility(View.VISIBLE);
    }
    else{
        convertView.setBackgroundColor(Color.WHITE);
        imageViewDelete.setVisibility(View.INVISIBLE);
    }

//if(mData.get(position).getSender().equals() && mData.get(position).getReceiver().equals()){
        tvName.setText(mData.get(position).getSender().toString());
        tvMessage.setText(mData.get(position).getMessage_text().toString());
        tvTimestamp.setText(mData.get(position).getTimestamp().toString());
        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase.setAndroidContext(mContext);
                Firebase msgUrl = new Firebase(MainActivity.URL_PATH+"/Messages/"+ msg.getKey());
                msgUrl.removeValue();
            }
        });
    //}
        return convertView;
    }
}