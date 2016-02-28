package example.com.pizzastore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;

/*
* Assignment No. In Class 03
* File name: MainActivity.java
* Full Name: Kedar Kulkarni, Dnyanshree Shengulwar,Marissa McLaughlin
* */


public class MainActivity extends AppCompatActivity {

    private ArrayList<String> toppingsAdded= new ArrayList<String>();
    private String [] toppings_array;
    private double PizzaPrice=6.5;
    private CheckBox checkBoxDelivery;
    final static String PRICE_KEY="Price",DELIVERY_KEY="Delivery",TOPPING_KEY="Toppings";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ProgressBar progressBar= (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        checkBoxDelivery= (CheckBox) findViewById(R.id.checkBoxDelivery);
        toppings_array= getResources().getStringArray(R.array.toppings_array);

        final TableLayout tableLayout= (TableLayout) findViewById(R.id.tableLayout);
        final TableRow tableRow1= new TableRow(this);
        final TableRow tableRow2= new TableRow(this);
        tableLayout.addView(tableRow1);
        tableLayout.addView(tableRow2);
        //TableRow.LayoutParams trParams= new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
        //tableRow1.setLayoutParams(trParams);
        //tableRow2.setLayoutParams(trParams);


        final AlertDialog.Builder toppingAlert= new AlertDialog.Builder(MainActivity.this);
        toppingAlert.setTitle(R.string.chooseTopping)
                .setItems(R.array.toppings_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (toppingsAdded.size() < 10) {
                            toppingsAdded.add(toppings_array[which]);
                            progressBar.incrementProgressBy(1);
                            final ImageView imageView= new ImageView(MainActivity.this);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ImageView imageView=(ImageView) v;
                                    tableRow1.removeView(imageView);
                                    Log.d("Topping","Clicked");
                                }
                            });
                            String topping_added=toppings_array[which];
                            topping_added=topping_added.replaceAll("\\s","");

                            int resID = getResources().getIdentifier( topping_added.toLowerCase(), "drawable", getPackageName());

                            TableRow.LayoutParams layoutParams= new TableRow.LayoutParams(150,150);
                            imageView.setLayoutParams(layoutParams);
                            imageView.setImageResource(resID);
                            if(toppingsAdded.size()<=5){
                                tableRow1.addView(imageView);
                            }
                            else
                            {
                                tableRow2.addView(imageView);
                            }
                            PizzaPrice+=1.5;
                        }
                        else {
                            Toast.makeText(MainActivity.this,R.string.max_cap,Toast.LENGTH_LONG).show();
                        }
                    }
                });

        findViewById(R.id.buttonAddTopping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toppingAlert.show();

            }
        });


        findViewById(R.id.buttonClearPizza).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxDelivery.setChecked(false);
                toppingsAdded.clear();
                tableRow1.removeAllViewsInLayout();
                tableRow2.removeAllViewsInLayout();
                progressBar.setProgress(0);
                PizzaPrice = 0;
            }
        });

        findViewById(R.id.buttonCheckout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxDelivery.isChecked())
                    PizzaPrice+=4.0;
                Intent intent= new Intent(MainActivity.this,OrderActivity.class);
                intent.putExtra(PRICE_KEY,PizzaPrice);
                intent.putExtra(DELIVERY_KEY,checkBoxDelivery.isChecked());
                intent.putExtra(TOPPING_KEY,toppingsAdded);
                startActivity(intent);
            }
        });
    }
}
