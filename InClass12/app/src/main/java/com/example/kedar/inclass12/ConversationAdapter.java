package com.example.kedar.inclass12;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dnyanshree on 4/26/2016.
 */
public class ConversationAdapter extends ArrayAdapter<Conversations> {
    List<Conversations> mData;
    Context mContext;
    int mResourceId;

    public ConversationAdapter(Context context, int resource, ArrayList<Conversations> objects) {
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

        TextView tvName=(TextView) convertView.findViewById(R.id.textViewFullName);
        ImageView imageViewProfilePic=(ImageView) convertView.findViewById(R.id.imageViewProfilePic);
        ImageView imageViewIsUnread=(ImageView) convertView.findViewById(R.id.imageViewIsUnread);
        imageViewIsUnread.setVisibility(View.INVISIBLE);
        final Conversations conv = mData.get(position);



        if(conv.getParticipant1().equals(MainActivity.loggedInUser.getEmail())){
            tvName.setText(mData.get(position).getParticipant2());
            for(User u: MainActivity.contactList){
                if((u.getEmail().equals(MainActivity.conversationsList.get(position).getParticipant2()))){
                    byte[] decodedString = Base64.decode(u.getProfilePic(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageViewProfilePic.setImageBitmap(decodedByte);
                }
            }
            for(Messages msg: Conversation.currentlyFetchedMessages){
                //Log.d("Demo","Message:"+msg.toString());
                if(msg.getSender().equals(mData.get(position).getParticipant2()) && msg.isMessage_read().equals("false")){
                    imageViewIsUnread.setVisibility(View.VISIBLE);
                }
            }
        }
        else{
            tvName.setText(mData.get(position).getParticipant1());
            for(User u: MainActivity.contactList){
                if((u.getEmail().equals(MainActivity.conversationsList.get(position).getParticipant1()))){
                    byte[] decodedString = Base64.decode(u.getProfilePic(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageViewProfilePic.setImageBitmap(decodedByte);
                }
            }
            for(Messages msg: Conversation.currentlyFetchedMessages){
                if(msg.getSender().equals(mData.get(position).getParticipant1()) && msg.isMessage_read().equals("false")){
                    imageViewIsUnread.setVisibility(View.VISIBLE);
                }
            }
        }


        return convertView;
    }
}