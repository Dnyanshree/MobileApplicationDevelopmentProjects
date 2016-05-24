package example.com.inclass12;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpenseListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ExpenseListFragment extends Fragment{
    Firebase myFirebaseRef;
    ListView listView;
    ArrayList<Expense> expenses=new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    public ExpenseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_expense_list, container, false);
        listView=(ListView) view.findViewById(R.id.listView);

        myFirebaseRef = new Firebase(MainActivity.URL_PATH+"Expenses");

        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                expenses.clear();
                for(DataSnapshot expenseSnapshot: dataSnapshot.getChildren()){
                    Expense expense= expenseSnapshot.getValue(Expense.class);
                    if(expense.email.equals(myFirebaseRef.getAuth().getProviderData().get("email")))
                        expenses.add(expense);
                }
                try{
                ExpenseAdapter adapter = new ExpenseAdapter(getActivity(), R.layout.expense_list_layout, expenses);
                listView.setAdapter(adapter);
                }catch (NullPointerException e)
                {e.printStackTrace();}
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new ExpenseDetailsFragment(), "ExpenseDetails")
                        .addToBackStack(null).commit();
                Firebase detailsRef= new Firebase(MainActivity.URL_PATH+"Expenses");
                detailsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot expenseSnapshot: dataSnapshot.getChildren()){
                            Expense expense= expenseSnapshot.getValue(Expense.class);
                            if(expense.getName().equals(expenses.get(position).getName()) && expense.getEmail().equals(expenses.get(position).getEmail()))
                            {   try{
                                ExpenseDetailsFragment e= (ExpenseDetailsFragment)getFragmentManager().findFragmentByTag("ExpenseDetails");
                                e.setDetails(expense);
                                }catch (Exception e){e.printStackTrace();}
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

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
