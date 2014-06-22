package com.kineticsproject.spokecalculator.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView;
import android.graphics.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import com.kineticsproject.spokecalculator.calculator.*;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.view.Gravity;
import java.text.DecimalFormat;

import android.view.View.OnClickListener;
import android.content.Intent;


public class mainActivity extends Activity 
{
    private ArrayAdapter<Rim>   adapterRimModel;
    private Button mountainButton;
    private Button roadButton;
    private Button bmxButton;
    private Button cyclocrossButton;
    private Button advancedButton;

    public void addListenerButton() 
    {

     
            mountainButton   = (Button) findViewById(R.id.mountain_button);
            bmxButton        = (Button) findViewById(R.id.bmx_button);
            roadButton       = (Button) findViewById(R.id.road_button);
            advancedButton   = (Button) findViewById(R.id.advanced_button);
            cyclocrossButton = (Button) findViewById(R.id.cyclocross_button); 

            advancedButton.setOnClickListener(new OnClickListener() {
     
                @Override
                public void onClick(View arg0) {
     
                  Intent advancedIntent = 
                                new Intent(mainActivity.this, advancedActivity.class);
                    startActivity(advancedIntent);
     
                }
     
            });

            cyclocrossButton.setOnClickListener(new OnClickListener() {
     
                @Override
                public void onClick(View arg0) {
     
                  Intent basicIntent = 
                                new Intent(mainActivity.this, basicCyclocrossActivity.class);
                    startActivity(basicIntent);
     
                }
     
            });
 

            bmxButton.setOnClickListener(new OnClickListener() {
     
                @Override
                public void onClick(View arg0) {
     
                  Intent basicIntent = 
                                new Intent(mainActivity.this, basicBMXActivity.class);
                    startActivity(basicIntent);
     
                }
     
            });
 
            roadButton.setOnClickListener(new OnClickListener() {
     
                @Override
                public void onClick(View arg0) {
     
                  Intent basicIntent = 
                                new Intent(mainActivity.this, basicRoadActivity.class);
                    startActivity(basicIntent);
     
                }
     
            });
 
     
            mountainButton.setOnClickListener(new OnClickListener() {
     
                @Override
                public void onClick(View arg0) {
     
                  Intent basicIntent = 
                                new Intent(mainActivity.this, basicMountainActivity.class);
                    startActivity(basicIntent);
     
                }
     
            });
     
	}
 
       
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        addListenerButton();

    }

   
};

