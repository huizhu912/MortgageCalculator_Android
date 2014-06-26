package com.example.mortgagecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	protected float P;
	protected int I;
	protected double J;
	protected int N = 15;     // the default selection for loan term is 15 years
	protected double T = 0.0; // Taxes and Insurance checkbox is unchecked by default
	protected double M;
	protected int rb_checkedId;
	protected RadioButton rb;
	protected Button bt;
	protected EditText et;
	protected TextView tv;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// set hint message in edit text field
		et = (EditText) findViewById(R.id.etP);
		et.setHint("Amount Borrowed");
		

		// get loan term from radio button
 
			RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
			rb_checkedId = rg.getCheckedRadioButtonId();
			//Toast.makeText(MainActivity.this, "radio button " + ((RadioButton)rg.findViewById(rb_checkedId)).getText().toString() + " is checked", Toast.LENGTH_LONG).show();
			//Toast.makeText(MainActivity.this, "N=  " + N, Toast.LENGTH_LONG).show();
			
			rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					rb = (RadioButton)group.findViewById(checkedId);
					boolean isChecked = rb.isChecked();
					if (isChecked) {
						switch(checkedId){
							case R.id.radio0:
									N=15;
									break;
							case R.id.radio1:
									N=20;
									break;
							case R.id.radio2:
									N=30;
									break;
							default: break;
						}
						//Toast.makeText(MainActivity.this, "radio button " + rb.getText().toString() + " is checked", Toast.LENGTH_LONG).show();
						//Toast.makeText(MainActivity.this, "N=  " + N, Toast.LENGTH_LONG).show();							
					}
				}
			});
			
			
		
		// get interest rate
			final int START = 10;
			SeekBar seekbar = (SeekBar)findViewById(R.id.seekBar);
			seekbar.setProgress(START);
			seekbar.setMax(20);
			tv = (TextView)findViewById(R.id.tvJ);
			tv.setText("interest rate = " + START + ".0%");

			seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

				@Override
				public void onStartTrackingTouch(SeekBar seekbar) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onStopTrackingTouch(SeekBar seekbar) {


				}

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					
					I = progress;
					//Toast.makeText(MainActivity.this, "interest rate= " + J + "%", Toast.LENGTH_SHORT).show();  
					//Log.d("TAG", String.format("%.2f", J/100));
					//String s = String.format("%.2f", I);
					tv = (TextView)findViewById(R.id.tvJ);
					tv.setText("interest rate = " + I + ".0%");

				}

			});
			
			
	
		// get Taxes and Insurance
			CheckBox cb = (CheckBox)findViewById(R.id.cbT);		
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if(isChecked){
						T = 0.001;
					}
					else {
						T = 0.0;
					}
					//Toast.makeText(MainActivity.this, "Taxes= " + T, Toast.LENGTH_SHORT).show();  

				}

			});


			// get monthly payment
			bt = (Button)findViewById(R.id.bt);
			bt.setOnClickListener(new OnClickListener(){
				double M_withInterestRate = 0;
				double M_withoutInterestRate = 0;
				
				@Override
				public void onClick(View v) {
				//get amount borrowed
					//et = (EditText) findViewById(R.id.etP);
					P = Float.valueOf(et.getText().toString());
						
					J = (double)I / 1200;
					M_withoutInterestRate = P/(N*12) + (P * T);
					M_withInterestRate = (P * (J / (1 - Math.pow(1+J,-N*12)))) + (P * T);
					
					if (I > 0) {
						M = M_withInterestRate;
					}
					else if (I == 0){
						M = M_withoutInterestRate;
					}
					
					tv = (TextView)findViewById(R.id.tvN);
					tv.setText("$ " + String.format("%.2f", M));


				}

			}); 
				
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
