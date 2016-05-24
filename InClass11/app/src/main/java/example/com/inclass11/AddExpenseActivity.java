package example.com.inclass11;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {
    Spinner catSpinner;
    String email;
    EditText etName, etAmount,etDate;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        Firebase.setAndroidContext(this);
        final Firebase myFirebaseRef = new Firebase(MainActivity.URL_PATH);

        //Log.d("AuthData",myFirebaseRef.getAuth().getProviderData().get("email").toString());

        etName=(EditText)findViewById(R.id.editTextExpenseName);
        etAmount=(EditText) findViewById(R.id.editTextAmount);
        etDate= (EditText) findViewById(R.id.editTextDate);
        etDate.setKeyListener(null);
        email=myFirebaseRef.getAuth().getProviderData().get("email").toString();

        final String[] categories = {"Grocerices","Invoice","Transportaion","Shopping","Rent","Trips","Utilities","Other"};

        catSpinner=(Spinner) findViewById(R.id.spinnerCategory);

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(AddExpenseActivity.this,R.layout.support_simple_spinner_dropdown_item,
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
                new DatePickerDialog(AddExpenseActivity.this, departDateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        findViewById(R.id.buttonAddExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().isEmpty()||etAmount.getText().toString().isEmpty()||etDate.getText().toString().isEmpty()){
                    Toast.makeText(AddExpenseActivity.this, "Make sure all fields are filled up!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Expense expense= new Expense(etName.getText().toString(),
                            categories[catSpinner.getSelectedItemPosition()],etAmount.getText().toString(),etDate.getText().toString(),email);

                    myFirebaseRef.child("Expenses").push().setValue(expense);
                    finish();
                }
            }
        });


    }
}
