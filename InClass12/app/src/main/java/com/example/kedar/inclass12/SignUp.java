
package com.example.kedar.inclass12;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.tubesock.Base64;

import java.io.ByteArrayOutputStream;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUp.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SignUp extends Fragment {
    EditText etEmail,etPassword,etName,etConfirmPassword,etPhone;

    private OnFragmentInteractionListener mListener;

    public SignUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate t
        //he layout for this fragment
        View view=inflater.inflate(R.layout.fragment_sign_up, container, false);

        etEmail =(EditText)view.findViewById(R.id.editTextEmail);
        etPassword =(EditText)view.findViewById(R.id.editTextPassword);
        etName =(EditText)view.findViewById(R.id.editTextName);
        etConfirmPassword=(EditText)view.findViewById(R.id.editTextConfirmPassword);
        etPhone=(EditText) view.findViewById(R.id.editTextPhone);



        view.findViewById(R.id.buttonSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!etEmail.getText().toString().isEmpty()&&!etPassword.getText().toString().isEmpty()&&
                        !etName.getText().toString().isEmpty()&&!etConfirmPassword.getText().toString().isEmpty()&&
                        !etPhone.getText().toString().isEmpty() &&(
                        (etPassword.getText().toString().equals(etConfirmPassword.getText().toString()))))
                    MainActivity.myFirebaseRef.child("users").createUser(etEmail.getText().toString(),etPassword.getText().toString(), new Firebase.ValueResultHandler<Map<String,Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> stringUserMap) {

                            Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.android);
                            ByteArrayOutputStream stream=new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                            final byte[] image=stream.toByteArray();

                            final String img_str = Base64.encodeToString(image, false);
                            final User user=new User(etEmail.getText().toString(),etPassword.getText().toString(),
                                    etName.getText().toString(),etPhone.getText().toString(),img_str);

                            MainActivity.myFirebaseRef.child("users").child(etEmail.getText().toString().split("@")[0]).setValue(user);
                            getFragmentManager().popBackStack();
                            /*myFirebaseRef.authWithPassword(etEmail.getText().toString(), etPassword.getText().toString(), new Firebase.AuthResultHandler() {
                                @Override
                                public void onAuthenticated(AuthData authData) {
                                    Toast.makeText(Signup.this, "Sign up success!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Signup.this, ConversationActivity.class);
                                    startActivity(intent);

                                }

                                @Override
                                public void onAuthenticationError(FirebaseError firebaseError) {
                                    Toast.makeText(Signup.this, firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                                    ;
                                }
                            });*/
                            //finish();

                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            Toast.makeText(getActivity(),firebaseError.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    });
                else
                    Toast.makeText(getActivity(),"Please fill in all the details correctly!", Toast.LENGTH_LONG).show();
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
