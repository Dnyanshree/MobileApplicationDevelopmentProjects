package com.example.kedar.inclass12;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Conversation.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Conversation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Conversation extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView conversationsListView;
    public static ArrayList<Messages> currentlyFetchedMessages; // to be used by Conversation adapter to check if any of the messages has loggedInUser as receiver

    ConversationAdapter adapter;
    private OnFragmentInteractionListener mListener;

    public Conversation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Conversation.
     */
    // TODO: Rename and change types and number of parameters
    public static Conversation newInstance(String param1, String param2) {
        Conversation fragment = new Conversation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        MainActivity.conversationsList = new ArrayList<>();
        adapter = new ConversationAdapter(getActivity(), R.layout.conversation_list_row, MainActivity.conversationsList);
        conversationsListView=(ListView) view.findViewById(R.id.listViewConv) ;
        conversationsListView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        conversationsListView.smoothScrollToPosition(adapter.getCount());



        currentlyFetchedMessages=new ArrayList<>();
        MainActivity.myFirebaseRef.child("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                currentlyFetchedMessages.clear();

                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {

                    Messages msg = messageSnapshot.getValue(Messages.class);
                    msg.setKey(messageSnapshot.getKey());

                    if(msg.getReceiver().equals(MainActivity.loggedInUser.getEmail())) {
                        currentlyFetchedMessages.add(msg);
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });



        //MainActivity.myFirebaseRef.child("Conversations").push().setValue(conv);
        MainActivity.contactList=new ArrayList<>();
        MainActivity.myFirebaseRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    final User user=d.getValue(User.class);

                    MainActivity.contactList.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




        //Getting users with whom logged in user had conversations
        MainActivity.myFirebaseRef.child("Conversations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                MainActivity.conversationsList.clear();

                for (DataSnapshot conversationSnapshot : snapshot.getChildren()) {

                    Conversations conv = conversationSnapshot.getValue(Conversations.class);
                    conv.setConversationID(conversationSnapshot.getKey());
                    //Log.d("Demo",conversationSnapshot.getKey()+"");


                    //Getting only relevant Conversations
                    if((conv.getParticipant1().equals(MainActivity.loggedInUser.getEmail()) ||
                            conv.getParticipant2().equals(MainActivity.loggedInUser.getEmail())) &&
                            !MainActivity.conversationsList.contains(conv)) {
                        try{
                        if(conv.getParticipant1().equals(MainActivity.loggedInUser.getEmail())&&
                                conv.isArchived_by_participant1().equals("false")) {
                            MainActivity.conversationsList.add(conv);
                            adapter.notifyDataSetChanged();
                        }
                        else if(conv.getParticipant2().equals(MainActivity.loggedInUser.getEmail())&&
                                conv.isArchived_by_participant2().equals("false")) {
                            MainActivity.conversationsList.add(conv);
                            adapter.notifyDataSetChanged();
                        }
                        }catch (Exception e){}

                    }


                    //Log.d("Demo",MainActivity.conversationsList.toString());
                    adapter.notifyDataSetChanged();
                }
                conversationsListView.setSelection(conversationsListView.getCount() - 1);


            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        conversationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(User u: MainActivity.contactList){
                    if((u.getEmail().equals(MainActivity.conversationsList.get(position).getParticipant1())
                            ||u.getEmail().equals(MainActivity.conversationsList.get(position).getParticipant2()))
                            && !u.getEmail().equals(MainActivity.loggedInUser.getEmail())) {
                        getFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                                ViewMessage.newInstance(MainActivity.loggedInUser, u))
                                .addToBackStack(null).commit();
                        break;
                    }
                }
            }
        });



        conversationsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                String []choices= {"Archive Conversation", "Delete Conversation"};
                AlertDialog.Builder deleteArchiveAlert = new AlertDialog.Builder(getActivity());
                deleteArchiveAlert.setTitle("Please choose an action").setItems(choices, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Demo",MainActivity.conversationsList.get(position).toString());
                        if(which==0){
                            if(MainActivity.conversationsList.get(position).getParticipant1().equals(MainActivity.loggedInUser.getEmail())) {
                                MainActivity.conversationsList.get(position).setIsArchived_by_participant1("true");
                                MainActivity.myFirebaseRef.child("Conversations").child(MainActivity.conversationsList.get(position).
                                        getConversationID()).child("isArchived_by_participant1").setValue("true");
                            }
                            else {
                                MainActivity.conversationsList.get(position).setIsArchived_by_participant2("true");
                                MainActivity.myFirebaseRef.child("Conversations").child(MainActivity.conversationsList.get(position).
                                        getConversationID()).child("isArchived_by_participant2").setValue("true");
                            }

                        }
                        else{
                            if(MainActivity.conversationsList.get(position).getDeletedBy().equals("none")) {
                                MainActivity.myFirebaseRef.child("Conversations").child(MainActivity.conversationsList.get(position).
                                        getConversationID()).child("deletedBy").setValue(MainActivity.loggedInUser.getEmail());
                                MainActivity.conversationsList.get(position).setDeletedBy(MainActivity.loggedInUser.getEmail());
                            }
                            else if(!MainActivity.conversationsList.get(position).getDeletedBy().equals(MainActivity.loggedInUser.getEmail()))
                            {

                                MainActivity.myFirebaseRef.child("Messages").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for(DataSnapshot d: dataSnapshot.getChildren())
                                            try{
                                                if(d.getValue(Messages.class).getSender().equals(MainActivity.conversationsList.get(position).getParticipant1())
                                                        && d.getValue(Messages.class).getReceiver().equals(MainActivity.conversationsList.get(position).getParticipant2())){
                                                    MainActivity.myFirebaseRef.child("Messages").child(d.getKey()).removeValue();
                                                }else if(d.getValue(Messages.class).getSender().equals(MainActivity.conversationsList.get(position).getParticipant2())
                                                        && d.getValue(Messages.class).getReceiver().equals(MainActivity.conversationsList.get(position).getParticipant1())){
                                                    MainActivity.myFirebaseRef.child("Messages").child(d.getKey()).removeValue();
                                                }
                                            }catch (Exception e){}
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });
                                MainActivity.myFirebaseRef.child("Conversations").child(MainActivity.conversationsList.get(position).
                                        getConversationID()).removeValue();

                            }

                        }
                    }
                }).show();
                return false;
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
