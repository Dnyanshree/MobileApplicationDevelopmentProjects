package com.example.kedar.inclass12;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewMessage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ViewMessage extends Fragment {

    private static final String USER = "param1";
    private static final String USER2 = "param2";

    private User current_user,receiver;
    private ListView messagesListView;
    ArrayList<Messages> messagesList ;
    MessagesAdapter adapter;
    private OnFragmentInteractionListener mListener;



    public ViewMessage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewContact.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewMessage newInstance(User param1, User param2) {
        ViewMessage fragment = new ViewMessage();
        Bundle args = new Bundle();
        args.putParcelable(USER, param1);
        args.putParcelable(USER2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            current_user = getArguments().getParcelable(USER);
            receiver = getArguments().getParcelable(USER2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_view_message, container, false);
        messagesList= new ArrayList<>();
        adapter = new MessagesAdapter(getActivity(), R.layout.message_list_row, messagesList);
        messagesListView=(ListView) view.findViewById(R.id.listViewConversation) ;
        messagesListView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        messagesListView.smoothScrollToPosition(adapter.getCount());



        //To display messages in real time in the list
        MainActivity.myFirebaseRef.child("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                messagesList.clear();

                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {

                    Messages msg = messageSnapshot.getValue(Messages.class);
                    msg.setKey(messageSnapshot.getKey());

                    if(msg.getReceiver().equals(current_user.getEmail())&& msg.getSender().equals(receiver.getEmail())) {
                        messagesList.add(msg);
                       // Log.d("Demo",current_user.getEmail()+"---"+receiver.getEmail());
                       // Log.d("Demo2",msg.getSender()+"---"+msg.getReceiver());
                        MainActivity.myFirebaseRef.child("Messages").child(msg.getKey()).child("message_read").setValue("true");
                        adapter.notifyDataSetChanged();
                    }
                    //reverse Condition
                    if(msg.getReceiver().equals(receiver.getEmail())&&msg.getSender().equals(current_user.getEmail())) {
                        messagesList.add(msg);

                        adapter.notifyDataSetChanged();
                    }
                }
                messagesListView.setSelection(messagesListView.getCount() - 1);



            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        final EditText etMessage= (EditText) view.findViewById(R.id.editTextMssage);
        view.findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etMessage.getText().toString().isEmpty()){
                    if(etMessage.getText().toString().length()<140)
                    {
                        final String date = DateFormat.getDateTimeInstance().format(new Date());
                        Messages msg=new Messages(date,"false",etMessage.getText().toString(),receiver.getEmail(),current_user.getEmail());
                        etMessage.setText("");
                        Firebase msgRef=MainActivity.myFirebaseRef.child("Messages").push();
                        msgRef.setValue(msg);
                        msgRef.child("message_read").setValue("false");


                        if(MainActivity.conversationsList.isEmpty()){
                            Conversations newConversation= new Conversations(current_user.getEmail(),receiver.getEmail(),"none","","");
                            Firebase convRef=MainActivity.myFirebaseRef.child("Conversations").push();
                            convRef.setValue(newConversation);
                            convRef.child("isArchived_by_participant1").setValue("false");
                            convRef.child("isArchived_by_participant2").setValue("false");
                            newConversation.setConversationID(convRef.getKey());
                            MainActivity.conversationsList.add(newConversation);

                        }
                        else
                        for(Conversations c: MainActivity.conversationsList){

                            if( (c.getParticipant1().equals(current_user.getEmail()))  &&
                                    !(c.getParticipant2().equals(receiver.getEmail()))){
                                Conversations newConversation= new Conversations(current_user.getEmail(),receiver.getEmail(),"none","","");
                                Firebase convRef=MainActivity.myFirebaseRef.child("Conversations").push();
                                convRef.setValue(newConversation);
                                convRef.child("isArchived_by_participant1").setValue("false");
                                convRef.child("isArchived_by_participant2").setValue("false");
                                newConversation.setConversationID(convRef.getKey());
                                MainActivity.conversationsList.add(newConversation);
                                break;
                            }
                        }
                    }else
                        Toast.makeText(getActivity(), "Message box contains more than 140 Characters!", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getActivity(), "Message box is Empty", Toast.LENGTH_SHORT).show();
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
