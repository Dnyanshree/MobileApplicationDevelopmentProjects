package example.com.inclass12;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddExpenseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AddExpenseFragment extends Fragment {
    Spinner catSpinner;
    String email;
    EditText etName, etAmount,etDate;
    Calendar calendar = Calendar.getInstance();
    private OnFragmentInteractionListener mListener;

    public AddExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_add_expense, container, false);
        final Firebase myFirebaseRef = new Firebase(MainActivity.URL_PATH);

        etName=(EditText)view.findViewById(R.id.editTextExpenseName);
        etAmount=(EditText) view.findViewById(R.id.editTextAmount);
        etDate= (EditText) view.findViewById(R.id.editTextDate);
        etDate.setKeyListener(null);
        email=myFirebaseRef.getAuth().getProviderData().get("email").toString();

        final String[] categories = {"Grocerices","Invoice","Transportaion","Shopping","Rent","Trips","Utilities","Other"};

        catSpinner=(Spinner) view.findViewById(R.id.spinnerCategory);

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,
                categories);
        catSpinner.setAdapter(categoriesAdapter);

        final DatePickerDialog.OnDateSetListener departDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear + 1;
                String month, date;
                if(monthOfYear < 10)
                    month = "0" + monthOfYear;
                else
                    month = "" + monthOfYear;
                if(dayOfMonth < 10)
                    date = "0" + dayOfMonth;
                else
                    date = "" + dayOfMonth;
                etDate.setText("" + month + "/" + date + "/" + year);
            }
        };
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDate.setError(null);
                new DatePickerDialog(getActivity(), departDateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        view.findViewById(R.id.buttonAddExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().isEmpty()||etAmount.getText().toString().isEmpty()||etDate.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Make sure all fields are filled up!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Expense expense= new Expense(etName.getText().toString(),
                            categories[catSpinner.getSelectedItemPosition()],etAmount.getText().toString(),etDate.getText().toString(),email);

                    Firebase expensePush =myFirebaseRef.child("Expenses").push();
                    expensePush.setValue(expense);
                    expense.setKey(expensePush.getKey());
                    getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new ExpenseListFragment(), "ExpenseList").commit();
                }

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
