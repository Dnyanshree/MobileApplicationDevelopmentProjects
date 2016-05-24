package example.com.inclass12;

import android.content.Context;
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

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SignUpFragment extends Fragment {
    Firebase myFirebaseRef;
    private OnFragmentInteractionListener mListener;
    EditText etEmail, etPassword, etName;
    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFirebaseRef= new Firebase(MainActivity.URL_PATH+"users");
        final View view=inflater.inflate(R.layout.fragment_sign_up, container, false);

        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.FragmentContainer,new LoginFragment(),"Login").commit();
            }
        });

        view.findViewById(R.id.buttonSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmail =(EditText)view.findViewById(R.id.editTextEmail);
                etPassword =(EditText)view.findViewById(R.id.editTextPassword);
                etName =(EditText)view.findViewById(R.id.editTextName);
                final User user=new User(etEmail.getText().toString(),etName.getText().toString(),etPassword.getText().toString());


                myFirebaseRef.createUser(etEmail.getText().toString(),etPassword.getText().toString(), new Firebase.ValueResultHandler<Map<String,Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> stringUserMap) {
                        myFirebaseRef.child(etName.getText().toString()).setValue(user);
                        Toast.makeText(getActivity(), "Signup Successful.", Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new LoginFragment(), "Login").commit();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(getActivity(),firebaseError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
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
