package com.example.kedar.inclass12;

import android.Manifest;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.FirebaseListAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Contacts.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Contacts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contacts extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int flaggedItem;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Contacts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contacts.
     */
    // TODO: Rename and change types and number of parameters
    public static Contacts newInstance(String param1, String param2) {
        Contacts fragment = new Contacts();
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
    void setUserToRemove(int i){
        flaggedItem=i;
        Log.d("Arraylist","Remove:" +i);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_contacts, container, false);
        ListView lvContacts = (ListView)view.findViewById(R.id.listViewContacts);




        MainActivity.contactList=new ArrayList<>();
        //Firebase List adapter used**********************************
        FirebaseListAdapter<User> listAdapter=new FirebaseListAdapter<User>(
                getActivity(),
                User.class,
                R.layout.contacts_layout,
                MainActivity.myFirebaseRef.child("users")
        ) {
            @Override
            protected void populateView(View view, final User user, int i) {

                if(!user.getEmail().equals(MainActivity.loggedInUser.getEmail())) {
                    TextView textView = (TextView) view.findViewById(R.id.textViewFullName);
                    textView.setText(user.getName());
                    ImageView imageViewProfilePic = (ImageView) view.findViewById(R.id.imageViewProfilePic);
                    byte[] decodedString = Base64.decode(user.getProfilePic(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageViewProfilePic.setImageBitmap(decodedByte);


                    view.findViewById(R.id.imageViewCall).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(android.content.Intent.ACTION_CALL, Uri.parse("tel:" + user.getPhone()));
                            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            }
                            getActivity().startActivity(i);
                        }
                    });

                }
                else{
                   view.setLayoutParams(new AbsListView.LayoutParams(-1,1));
                    setUserToRemove(i);
                }
                MainActivity.contactList.add(user);
            }

        };


        lvContacts.setAdapter(listAdapter);

        lvContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                for(User u: MainActivity.contactList)
                {
                    if(u.getName()==MainActivity.contactList.get(position).getName())
                        getFragmentManager().beginTransaction().replace(R.id.FragmentContainer,ViewContact.newInstance(u,""))
                            .addToBackStack(null).commit();
                }

                return false;
            }
        });


        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                        ViewMessage.newInstance(MainActivity.loggedInUser,MainActivity.contactList.get(position)))
                            .addToBackStack(null).commit();


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
