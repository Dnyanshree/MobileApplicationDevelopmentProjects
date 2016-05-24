package com.example.kedar.inclass12;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvName;
    private EditText etName,etEmail,etPhone,etPassword;
    private ImageView profilePic;
    private int PICK_IMAGE_REQUEST = 1;
    private String img_str;
    private User loggedInUser;



    private OnFragmentInteractionListener mListener;

    public EditProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfile newInstance(String param1, String param2) {
        EditProfile fragment = new EditProfile();
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
        View view=inflater.inflate(R.layout.fragment_edit_profile, container, false);

        tvName = (TextView) view.findViewById(R.id.textViewFullName);
        etName = (EditText) view.findViewById(R.id.editTextName);
        etEmail = (EditText) view.findViewById(R.id.editTextEmail);
        etPassword = (EditText) view.findViewById(R.id.editTextPassword);
        etPhone = (EditText) view.findViewById(R.id.editTextPhone);
        profilePic = (ImageView) view.findViewById(R.id.imageViewProfilePic);


        final Firebase UserRef = MainActivity.myFirebaseRef.child("users").child(MainActivity.myFirebaseRef.getAuth().
                getProviderData().get("email").toString().split("@")[0]);

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    loggedInUser = dataSnapshot.getValue(User.class);
                    tvName.setText(loggedInUser.getName());
                    etName.setText(loggedInUser.getName());
                    etPhone.setText(loggedInUser.getPhone());
                    etPassword.setText(loggedInUser.getPassword());
                    etEmail.setText(loggedInUser.getEmail());
                    img_str = loggedInUser.getProfilePic();
                    byte[] decodedString = Base64.decode(loggedInUser.getProfilePic(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    profilePic.setImageBitmap(decodedByte);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        //On Update****************************************************************************
        view.findViewById(R.id.buttonUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etEmail.getText().toString().isEmpty()&&!etPassword.getText().toString().isEmpty()&&
                        !etName.getText().toString().isEmpty()&&
                        !etPhone.getText().toString().isEmpty()){
                    final User user=new User(etEmail.getText().toString(),etPassword.getText().toString(),
                            etName.getText().toString(),etPhone.getText().toString(),img_str);

                    if(!user.getEmail().equals(loggedInUser.getEmail())){
                        MainActivity.myFirebaseRef.changeEmail(MainActivity.myFirebaseRef.getAuth().getProviderData().get("email").toString(),
                                etPassword.getText().toString(),etEmail.getText().toString(), new Firebase.ResultHandler() {
                            @Override
                            public void onSuccess() {
                                UserRef.setValue(null);
                                UserRef.removeValue();
                                MainActivity.myFirebaseRef.child("users").child(etEmail.getText().toString().split("@")[0]).setValue(user);


                                getFragmentManager().popBackStack();
                                MainActivity.myFirebaseRef.authWithPassword(user.getEmail(),user.getPassword(),null);
                                updateLoggedInUser(user);
                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {
                                Toast.makeText(getActivity(), firebaseError.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                    }else if(!user.getPassword().equals(loggedInUser.getPassword())){

                        MainActivity.myFirebaseRef.changePassword(user.getEmail(), loggedInUser.getPassword(), user.getPassword(), new Firebase.ResultHandler() {
                            public void onSuccess() {

                                MainActivity.myFirebaseRef.child("users").child(etEmail.getText().toString().split("@")[0]).setValue(user);


                                getFragmentManager().popBackStack();

                                MainActivity.myFirebaseRef.authWithPassword(user.getEmail(),user.getPassword(),null);
                                updateLoggedInUser(user);
                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {
                                Toast.makeText(getActivity(), firebaseError.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                    else{
                        UserRef.setValue(user);
                        getFragmentManager().popBackStack();
                        updateLoggedInUser(user);

                    }

                }else
                    Toast.makeText(getActivity(),"One or more fields are Empty!",Toast.LENGTH_LONG).show();

            }
        });

        //End of Update on click listener*************************************************************************************

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    private void updateLoggedInUser(User user){
        Toast.makeText(getActivity(),"Updates Saved Successfully!",Toast.LENGTH_LONG).show();
        loggedInUser=user;
        MainActivity.loggedInUser=loggedInUser;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                Bitmap bitmap1=bitmap;
                profilePic.setImageBitmap(bitmap1);
                ByteArrayOutputStream os= new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, os);
                byte[] image=os.toByteArray();

                img_str = Base64.encodeToString(image, 1);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
