package example.com.inclass12;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpenseDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ExpenseDetailsFragment extends Fragment {
    TextView tvName, tvAmount, tvCategory, tvDate;
    private OnFragmentInteractionListener mListener;

    public ExpenseDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expense_details, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
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
    public void setDetails(Expense expense){
        tvName= (TextView) getView().findViewById(R.id.textViewName);
        tvAmount= (TextView)getView().findViewById(R.id.textViewAmount);
        tvCategory= (TextView)getView().findViewById(R.id.textViewCategory);
        tvDate= (TextView)getView().findViewById(R.id.textViewDate);

        tvName.setText(expense.getName());
        tvAmount.setText(expense.getAmount());
        tvDate.setText(expense.getDate());
        tvCategory.setText(expense.getCategory());
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
        public void setDetails(Expense expense,Context context);
    }
}
