package example.com.pizzastore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
/*
* Assignment No. In Class 03
* File name: OrderActivity.java
* Full Name: Kedar Kulkarni, Dnyanshree Shengulwar, Marissa McLaughlin
* */
public class OrderActivity extends AppCompatActivity {

    private ArrayList<String> toppingsAdded= new ArrayList<String>();
    private TextView tvBasePrice, tvToppingsPrice,tvDeliveryPrice,tvTotal,tvToppings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        tvBasePrice=(TextView)findViewById(R.id.textViewBasePrice);
        tvDeliveryPrice=(TextView) findViewById(R.id.textViewDeliveryPrice);
        tvToppingsPrice=(TextView) findViewById(R.id.textViewToppingPrice);
        tvTotal=(TextView) findViewById(R.id.textViewTotalPrice);
        tvToppings=(TextView)findViewById(R.id.textViewToppings);

        Intent intent=getIntent();
        if(intent.getExtras()!= null){
            tvBasePrice.setText("6.5$");
            toppingsAdded = intent.getExtras().getStringArrayList(MainActivity.TOPPING_KEY);
            tvToppingsPrice.setText((toppingsAdded.size()*1.5)+"$");
            tvToppings.setText(toppingsAdded.toString());
            if(intent.getExtras().getBoolean(MainActivity.DELIVERY_KEY)==true)
                tvDeliveryPrice.setText("4.0$");
            else
                tvDeliveryPrice.setText("");

            tvTotal.setText(intent.getExtras().getDouble(MainActivity.PRICE_KEY)+"$");
        }


        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
