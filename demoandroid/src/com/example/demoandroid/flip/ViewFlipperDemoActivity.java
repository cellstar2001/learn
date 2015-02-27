package com.example.demoandroid.flip;

 

 
import com.example.demoandroid.R;
import com.example.demoandroid.flip.MyViewFlipper.OnViewFlipperListener;

import android.app.Activity; 
import android.os.Bundle; 
import android.view.LayoutInflater; 
import android.view.View; 
import android.widget.ScrollView; 
import android.widget.TextView; 
 
public class ViewFlipperDemoActivity extends Activity implements OnViewFlipperListener { 
 
    private MyViewFlipper myViewFlipper; 
    private int currentNumber; 
 
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activtiy_flip_main); 
 
        currentNumber = 1; 
        myViewFlipper = (MyViewFlipper) findViewById(R.id.myViewFlipper); 
        myViewFlipper.setOnViewFlipperListener(this); 
        myViewFlipper.addView(creatView(currentNumber)); 
    } 
 
    @Override 
    public View getNextView() { 
        currentNumber = currentNumber == 10 ? 1 : currentNumber + 1; 
        return creatView(currentNumber); 
    } 
 
    @Override 
    public View getPreviousView() { 
        currentNumber = currentNumber == 1 ? 10 : currentNumber - 1; 
        return creatView(currentNumber); 
    } 
 
    private View creatView(int currentNumber) { 
        LayoutInflater layoutInflater = LayoutInflater.from(this); 
        ScrollView resultView = (ScrollView) layoutInflater.inflate(R.layout.activtiy_flip_view, null); 
        ((TextView) resultView.findViewById(R.id.textView)).setText(currentNumber + ""); 
        return resultView; 
    } 
} 
