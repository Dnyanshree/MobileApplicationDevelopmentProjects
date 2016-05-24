package example.com.inclass11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class ExpensesListActivity extends AppCompatActivity {
    ListView listView;
    Firebase myFirebaseRef;
    ArrayList<Expense> expenses=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_list);
        listView=(ListView) findViewById(R.id.listView);


        Firebase.setAndroidContext(this);
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
                ExpenseAdapter adapter = new ExpenseAdapter(ExpensesListActivity.this, R.layout.row_item_layout, expenses);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent= new Intent(ExpensesListActivity.this, DetailsActivity.class);
                intent.putExtra("Name",expenses.get(position).getName());
                intent.putExtra("Email",expenses.get(position).getEmail());
                intent.putExtra("Amount",expenses.get(position).getAmount());
                intent.putExtra("Category",expenses.get(position).getCategory());
                intent.putExtra("Date",expenses.get(position).getDate());

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_expense:
                    Intent intent = new Intent(ExpensesListActivity.this,AddExpenseActivity.class);
                    startActivity(intent);
                break;
            case R.id.logout:
                myFirebaseRef.unauth();
                finish();
                break;

        }
        return true;
    }
}
