package com.kineticsproject.spokecalculator.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.EditText;
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
import android.view.inputmethod.InputMethodManager;
import android.view.View.OnFocusChangeListener;
import android.content.Context;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.inputmethod.EditorInfo;


public class advancedActivity extends Activity 
{
     
    private com.kineticsproject.spokecalculator.android.DatabaseAdapter dbAdapter;

    private MySpinner spokeCountSpinner;
    private MySpinner spokeCrossSpinner;
    private TextView  spokeCountTextView;
    private TextView  spokeCrossTextView;
    private EditText  rimERDEditText;
    private EditText  drivesideFlangeDiameterEditText;
    private EditText  nondrivesideFlangeDiameterEditText;
    private EditText  centerToDriveFlangeEditText;
    private EditText  centerToNonDriveFlangeEditText;

    private TextView  driveSideSpokeLengthTextView;
    private TextView  nondriveSideSpokeLengthTextView;

    private int       selectedCrosses;
    private int       selectedSpokes;
    private Hub       selectedHub;
    private Rim       selectedRim;
    private String[]  spokeCrossArray;
    private String[]  spokeCountArray;
    private InputMethodManager imm;

    Wheel   wheel;

    final private String select = "Select";
    final private int selectID = 255;

    private Rim getRim()
    {
        Rim result = null;
        Double tmpDouble;

        String tmpString =  rimERDEditText.getText().toString();
       
        if ((null != tmpString) && (!tmpString.isEmpty()))
        {
            tmpDouble = Double.parseDouble(tmpString);

            result = new Rim(tmpDouble.doubleValue(), 0, 0);
        }
        else
        {
            // return null
        }
        

        return result;
    }


    private double getTextData(String tmp)
    {
        Double tmpDouble;
        double result = 0.0; // 0.0 is a non-valid value so it is used as a "magic" false
                
        if ((null != tmp) && (!tmp.isEmpty()))
        {
            tmpDouble = Double.parseDouble(tmp);
            result = tmpDouble.doubleValue();
        }

        return result;
        
    }

    private Hub getHub()
    {
        Hub result = null;  // hub object to return
        String tmpDSFD;     // drive side flange diameter
        String tmpNDSFD;    // non drive side flange diameter
        String tmpCTDF;     // center to drive flange
        String tmpCTNDF;    // center to non drive flange

        tmpDSFD   =  drivesideFlangeDiameterEditText.getText().toString();
        tmpNDSFD  =  nondrivesideFlangeDiameterEditText.getText().toString();
        tmpCTDF   =  centerToDriveFlangeEditText.getText().toString();
        tmpCTNDF  =  centerToNonDriveFlangeEditText.getText().toString();

        double DSFD;    // drive side flange diameter
        double NDSFD;   // non drive side flange diameter
        double CTDF;    // center to drive flange
        double CTNDF;   // center to non drive flange
   
        DSFD    = getTextData(tmpDSFD);
        NDSFD   = getTextData(tmpNDSFD);
        CTDF    = getTextData(tmpCTDF);
        CTNDF   = getTextData(tmpCTNDF);
      
        if ((0.0 != DSFD) && (0.0 != NDSFD) && (0.0 != CTDF) && (0.0 != CTNDF))
        {

                result = new Hub(DSFD, NDSFD, CTDF, CTNDF);

        }            

        return result;
    }


    /* After all the data is entered and we've calculated a spoke value switch from
       wizard mode to edit mode.  The keyboard success button is changed from
       "next" to "done"
    */
    private void switchKeyboardFromNextToDone()
    {
      rimERDEditText.setImeOptions(EditorInfo.IME_ACTION_DONE); 
      drivesideFlangeDiameterEditText.setImeOptions(EditorInfo.IME_ACTION_DONE); 
      nondrivesideFlangeDiameterEditText.setImeOptions(EditorInfo.IME_ACTION_DONE); 
      centerToDriveFlangeEditText.setImeOptions(EditorInfo.IME_ACTION_DONE); 
      centerToNonDriveFlangeEditText.setImeOptions(EditorInfo.IME_ACTION_DONE); 
    }

    private void checkEnableCalculateButton()
    {
        boolean result = (spokeCountSpinner.isSelectionValid() && spokeCrossSpinner.isSelectionValid());

          
         selectedRim = getRim();
         selectedHub = getHub();
   
      if (result && (null != selectedRim) && (null != selectedHub))
      {

         calculateSpokeLength();
         //imm.hideSoftInputFromWindow(rimERDEditText.getWindowToken(), 0);
         switchKeyboardFromNextToDone();
      }
      else
      {
          driveSideSpokeLengthTextView.setText("--");
          nondriveSideSpokeLengthTextView.setText("--");
      }

      driveSideSpokeLengthTextView.setEnabled(result);
      nondriveSideSpokeLengthTextView.setEnabled(result);
     
           
    }

    private static String roundTwoDecimals(double d)
    {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(d);
    }

    

    private OnItemSelectedListener spokeCrossSelectedListener = new OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
        {
            System.out.println("selected spoke cross: "+spokeCrossArray[position]);
            spokeCrossSpinner.setSelectionValid(0 != position);
            if (0 != position)
            {
                if (0 == spokeCrossArray[position].compareTo("radial"))
                {
                    selectedCrosses = 0;
                }
                else
                {
                    Integer i = Integer.parseInt(spokeCrossArray[position]);
                    selectedCrosses = i.intValue();
                }
            }


            checkEnableCalculateButton();
            
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentview)
        {
        }
    };

    private OnItemSelectedListener spokeCountSelectedListener = new OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
        {
            System.out.println("selected spoke count: "+spokeCountArray[position]);
            spokeCountSpinner.setSelectionValid(0 != position);

            if (0 != position)
            {
                Integer i = Integer.parseInt(spokeCountArray[position]);
                selectedSpokes = i.intValue();
            }

           checkEnableCalculateButton();
        }


        @Override
        public void onNothingSelected(AdapterView<?> parentview)
        {
        }
    };


    public void populateSpokeCountSpinner()
    {
        /* Setup the spinner for spoke count */
        spokeCountSpinner = (MySpinner) findViewById(R.id.spoke_count_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpokeCount = ArrayAdapter.createFromResource(this,
        R.array.spoke_count_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterSpokeCount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spoke count spinner
        spokeCountSpinner.setAdapter(adapterSpokeCount);

        // add listener
        spokeCountSpinner.setOnItemSelectedListener(spokeCountSelectedListener);


    }

    public void populateSpokeCrossSpinner()
    {
        /* Setup the spinner for spoke cross */
        spokeCrossSpinner = (MySpinner) findViewById(R.id.spoke_cross_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpokeCross = ArrayAdapter.createFromResource(this,
        R.array.spoke_cross_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterSpokeCross.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spoke cross spinner
        spokeCrossSpinner.setAdapter(adapterSpokeCross);

        // add listener
        spokeCrossSpinner.setOnItemSelectedListener(spokeCrossSelectedListener);
    }

    public void calculateSpokeLength()
    {
        wheel = new Wheel(selectedRim, selectedHub, selectedCrosses, selectedSpokes);
        
        System.out.println("****************************************calculateSpokeLength("+selectedRim+","+selectedHub+","+selectedCrosses+","+selectedSpokes+")="+wheel.calculateNonDriveSideSpokeLength()+","+wheel.calculateDriveSideSpokeLength());

        nondriveSideSpokeLengthTextView.setText(roundTwoDecimals(wheel.calculateNonDriveSideSpokeLength()));
        driveSideSpokeLengthTextView.setText(roundTwoDecimals(wheel.calculateDriveSideSpokeLength()));


    }

    @Override
    protected void onSaveInstanceState(Bundle data)
    {
        super.onSaveInstanceState(data);
    }

    private void initializeEditText()
    {

        rimERDEditText                     = (EditText) findViewById(R.id.rim_erd_edittext);
        drivesideFlangeDiameterEditText    = (EditText) findViewById(R.id.hub_drive_flange_diameter_edittext);
        nondrivesideFlangeDiameterEditText = (EditText) findViewById(R.id.hub_nondrive_flange_diameter_edittext);
        centerToDriveFlangeEditText        = (EditText) findViewById(R.id.hub_center_to_drive_flange_edittext);
        centerToNonDriveFlangeEditText     = (EditText) findViewById(R.id.hub_center_to_nondrive_flange_edittext);

    }




    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advanced);

        boolean reinit = (null != savedInstanceState);

        imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
    
        initializeEditText(); 

        dbAdapter = new com.kineticsproject.spokecalculator.android.DatabaseAdapter(this);
       
        if (!dbAdapter.open())
        {
            System.out.println("*****************failed to open db\r\n");
        }
        else
        {
            System.out.println("****************opened db successfully\r\n");
        }

        TextWatcher myTextWatcher = new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) 
            {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                checkEnableCalculateButton();
            }

       };

        rimERDEditText.addTextChangedListener(myTextWatcher);
        drivesideFlangeDiameterEditText.addTextChangedListener(myTextWatcher);
        nondrivesideFlangeDiameterEditText.addTextChangedListener(myTextWatcher);
        centerToDriveFlangeEditText.addTextChangedListener(myTextWatcher);
        centerToNonDriveFlangeEditText.addTextChangedListener(myTextWatcher);
         
        populateSpokeCountSpinner();
        populateSpokeCrossSpinner();
        spokeCrossArray   = getResources().getStringArray(R.array.spoke_cross_array);
        spokeCountArray   = getResources().getStringArray(R.array.spoke_count_array);

        nondriveSideSpokeLengthTextView  =  (TextView) findViewById(R.id.nondriveside_spokelength_textview);
       // fails on uncle brad's phone with android version 2.3.5 (api does not exist) 
       // nondriveSideSpokeLengthTextView.setTextIsSelectable(false);
        driveSideSpokeLengthTextView     =  (TextView) findViewById(R.id.driveside_spokelength_textview);
//        hubModelTextView = (TextView) findViewById(R.id.hub_model_textview);
    
        driveSideSpokeLengthTextView.setEnabled(false);
        nondriveSideSpokeLengthTextView.setEnabled(false);

        nondriveSideSpokeLengthTextView.setOnFocusChangeListener(new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus)
            {
                  imm.hideSoftInputFromWindow(nondriveSideSpokeLengthTextView.getWindowToken(), 0);
            }
            else 
            {

            }
           }
        });


        if(reinit)
        {

            spokeCrossArray   = getResources().getStringArray(R.array.spoke_cross_array);
            spokeCountArray   = getResources().getStringArray(R.array.spoke_count_array);

            nondriveSideSpokeLengthTextView  =  (TextView) findViewById(R.id.nondriveside_spokelength_textview);
            // fails on uncle brad's phone
            //nondriveSideSpokeLengthTextView.setTextIsSelectable(false);
            driveSideSpokeLengthTextView     =  (TextView) findViewById(R.id.driveside_spokelength_textview);
 //           hubModelTextView = (TextView) findViewById(R.id.hub_model_textview);
    
            checkEnableCalculateButton();
            System.out.println("REINIT******************************************");
        }

          System.out.println("ENDOFONCREATE*************************");

       } // onCreate

    
}
