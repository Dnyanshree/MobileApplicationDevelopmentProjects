package example.com.inclass12;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {
    Firebase myFirebaseRef;
    static final String URL_PATH ="https://group22-inclass11.firebaseio.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);
        myFirebaseRef= new Firebase(URL_PATH);
        getFragmentManager().beginTransaction().add(R.id.FragmentContainer, new LoginFragment(), "Login").commit();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public void onBackPressed() {

        if(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }else{
            myFirebaseRef.unauth();
            getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new LoginFragment(), "Login")
                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AuthData authData=myFirebaseRef.getAuth();
        switch(item.getItemId()){
            case R.id.AddExpense:

                if(authData!=null)
                getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new AddExpenseFragment(), "AddExpense")
                        .addToBackStack(null).commit();
                else
                    Toast.makeText(this,"Please Login to add Expenses!",Toast.LENGTH_LONG).show();
                break;
            case R.id.logout:
                if(authData!=null) {
                    myFirebaseRef.unauth();
                    getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new LoginFragment(), "Login").commit();
                }else
                    Toast.makeText(this,"Please Login to add Expenses!",Toast.LENGTH_LONG).show();
                break;

        }
        return true;
    }
}
