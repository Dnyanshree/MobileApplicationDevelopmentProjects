package example.com.computercalculator;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
/*
Assignment: Homework 01
FileName: ComputerCalculator
Group 22
Team Members: Dnyanshree Shengulwar,
              Kedar Vijay Kulkarni,
              Marissa McLaughlin

 */
public class MainActivity extends AppCompatActivity {
    private TextView tvStatus,tvTip,tvPrice;
    private EditText etBudget;
    private RadioGroup rgMemory, rgStorage;
    private CheckBox cbMouse,cbFlashDrive,cbCoolingPad,cbCarryCase;
    private SeekBar sbTip;
    private int percentTip;
    private Switch swDelivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher); 


        tvStatus=(TextView)findViewById(R.id.textViewStatus);
        etBudget=(EditText) findViewById(R.id.editTextBudgetInput);
        rgMemory=(RadioGroup) findViewById(R.id.radioGroupMemory);
        rgStorage=(RadioGroup) findViewById(R.id.radioGroupStorage);
        cbMouse=(CheckBox) findViewById(R.id.checkBoxMouse);
        cbCarryCase=(CheckBox) findViewById(R.id.checkBoxCarryingCase);
        cbFlashDrive=(CheckBox) findViewById(R.id.checkBoxFlashDrive);
        cbCoolingPad=(CheckBox) findViewById(R.id.checkBoxCoolingPad);
        sbTip =(SeekBar) findViewById(R.id.seekBar);
        tvTip=(TextView)findViewById(R.id.textViewPercentTip);
        swDelivery=(Switch) findViewById(R.id.switchDelivery);
        tvPrice=(TextView) findViewById(R.id.textViewPrice);

        sbTip.setProgress(15);
        percentTip=15;
        sbTip.setMax(25);
        sbTip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percentTip=(Math.round(progress/5))*5;
                sbTip.setProgress(percentTip);
                tvTip.setText(percentTip+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void CalculatePrice(View V)
    {
        Double budget;
        Double price=0.0;
        int noOfAccessories=0;
        if(!etBudget.getText().toString().isEmpty()){
            budget= Double.parseDouble(etBudget.getText().toString());

            int rbid=rgMemory.getCheckedRadioButtonId();
            if(rbid==R.id.radioButton2GB){
                price+=10*2;
            }else if (rbid==R.id.radioButton4GB){
                price+=10*4;
            }else if (rbid==R.id.radioButton8GB)
            {
                price+=10*8;
            }
            else if(rbid==R.id.radioButton16GB){
                price+=10*16;
            }
            rbid=rgStorage.getCheckedRadioButtonId();
            if(rbid==R.id.radioButton250GB){
                price +=.75*250;
            }
            else if(rbid==R.id.radioButton500GB)
            {
                price+=.75*500;
            }
            else if (rbid==R.id.radioButton750GB){
                price+=.75*750;
            }
            else if (rbid==R.id.radioButton1TB){
                price+=.75*1000;
            }

            if(cbMouse.isChecked()){
                noOfAccessories+=1;
            }
            if(cbCarryCase.isChecked()){
                noOfAccessories+=1;
            }
            if(cbCoolingPad.isChecked()){
                noOfAccessories+=1;
            }
            if(cbFlashDrive.isChecked()){
                noOfAccessories+=1;
            }
            price+=20*noOfAccessories;

            price=price+(price*percentTip/100);
            if(swDelivery.isChecked())
                price+=5.95;
            tvPrice.setText("Price: $"+String.valueOf(price));
            if(price<budget){
                tvStatus.setText(R.string.Status_Positive);
                tvStatus.setBackgroundColor(Color.GREEN);
                tvStatus.setTextColor(Color.WHITE);
            }
            else {
                tvStatus.setText(R.string.Status_Negative);
                tvStatus.setBackgroundColor(Color.RED);
                tvStatus.setTextColor(Color.WHITE);
            }
            //price=0.0;
        }
        else{
            Toast.makeText(this,"Please Enter Budget",Toast.LENGTH_LONG).show();
        }
    }

    public void ResetAll(View v){
        rgMemory.clearCheck();
        rgMemory.check(R.id.radioButton2GB);
        rgStorage.clearCheck();
        rgStorage.check(R.id.radioButton250GB);

        cbFlashDrive.setChecked(false);
        cbCoolingPad.setChecked(false);
        cbCarryCase.setChecked(false);
        cbMouse.setChecked(false);

        sbTip.setProgress(15);

        etBudget.setText("");
        etBudget.setHint(R.string.Input_Hint);

        swDelivery.setChecked(true);

        tvStatus.setText("");
        tvPrice.setText(R.string.Price_TextViewLabel);
        tvTip.setText("15%");
    }
}
