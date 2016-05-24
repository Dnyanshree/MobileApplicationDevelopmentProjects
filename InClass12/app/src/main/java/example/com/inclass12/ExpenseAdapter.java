package example.com.inclass12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dnyanshree on 4/18/2016.
 */
public class ExpenseAdapter extends ArrayAdapter<Expense> {
    List<Expense> mData;
    Context mContext;
    int mResourceId;

    public ExpenseAdapter(Context context, int resource, List<Expense> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResourceId, parent, false);
        }

        Expense expense = mData.get(position);

        TextView tvExpenseName = (TextView) convertView.findViewById(R.id.textViewExpenseName);
        tvExpenseName.setText(expense.getName());

        TextView tvExpenseHex = (TextView) convertView.findViewById(R.id.textViewValue);
        tvExpenseHex.setText("$"+expense.getAmount());
        //tvExpenseHex.setBackgroundExpense(android.graphics.Expense.parseExpense(Expense.ExpenseHex));

        return convertView;
    }

}
