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

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class LoginFragment extends Fragment {
    Firebase myFirebaseRef;
    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        myFirebaseRef= new Firebase(MainActivity.URL_PATH);

        AuthData authData = myFirebaseRef.getAuth();

        if (authData != null) {
            getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new ExpenseListFragment(),
                    "Expense List").commit();
        }

        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        view.findViewById(R.id.buttonCreateNewAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new SignUpFragment(),"Signup").commit();
            }
        });

        view.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            EditText etEmail= (EditText) view.findViewById(R.id.editTextEmail);
            EditText etPassword= (EditText) view.findViewById(R.id.editTextPassword);

            @Override
            public void onClick(View v) {

                myFirebaseRef.authWithPassword(etEmail.getText().toString(),etPassword.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new ExpenseListFragment(),
                                "Expense List").commit();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(getActivity(), firebaseError.getMessage(), Toast.LENGTH_LONG).show();
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
