package com.kineticsproject.spokecalculator.android;

import com.kineticsproject.spokecalculator.calculator.*;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.view.Gravity;
import java.text.DecimalFormat;


public class basicActivity extends Activity 
{
    protected ArrayAdapter<Rim>   adapterRimModel;
    protected ArrayAdapter<Brand> adapterRimBrand;
    protected ArrayAdapter<Brand> adapter;
     
    protected com.kineticsproject.spokecalculator.android.DatabaseAdapter dbAdapter;

    protected MySpinner hubModelSpinner;
    protected MySpinner hubBrandSpinner;
    protected MySpinner rimBrandSpinner; 
    protected MySpinner rimModelSpinner;
    protected MySpinner spokeCountSpinner;
    protected MySpinner spokeCrossSpinner;
    protected TextView  hubModelTextView;
    protected TextView  hubBrandTextView;
    protected TextView  rimBrandTextView;
    protected TextView  rimModelTextView;
    protected TextView  spokeCountTextView;
    protected TextView  spokeCrossTextView;


    protected TextView  driveSideSpokeLengthTextView;
    protected TextView  nondriveSideSpokeLengthTextView;

    protected Brand[]   hubBrandArray;
    protected Brand[]   rimBrandArray;
    protected Rim[]     rimArray;
    protected Hub[]     hubArray;
    protected Rim       selectedRim;
    protected Hub       selectedHub;
    protected int       selectedCrosses;
    protected int       selectedSpokes;
    protected String[]  spokeCrossArray;
    protected String[]  spokeCountArray;
    protected int       rimModelSelection;
    protected int       hubModelSelection;
    protected int       rimBrandIndex;
    protected int       hubBrandIndex;

    Wheel   wheel;

    protected Button calculateButton;
    final protected String select = "Select";
    final protected int selectID = 255;

    protected void checkEnableCalculateButton()
    {
        boolean result = (hubBrandSpinner.isSelectionValid() && rimBrandSpinner.isSelectionValid()
                       && hubModelSpinner.isSelectionValid() && rimModelSpinner.isSelectionValid()
                       && spokeCountSpinner.isSelectionValid() && spokeCrossSpinner.isSelectionValid());

        //System.out.println("enableCalc: "+hubBrandSpinner.isSelectionValid()+rimBrandSpinner.isSelectionValid()+hubModelSpinner.isSelectionValid()+
         //                 rimModelSpinner.isSelectionValid()+spokeCountSpinner.isSelectionValid()+spokeCrossSpinner.isSelectionValid());

         
      //  calculateButton.setEnabled(result);
      if (result)
      {
         calculateSpokeLength();
      }
      else
      {
          driveSideSpokeLengthTextView.setText("--");
          nondriveSideSpokeLengthTextView.setText("--");
      }

      driveSideSpokeLengthTextView.setEnabled(result);
      nondriveSideSpokeLengthTextView.setEnabled(result);

     
           
    }

    protected static String roundTwoDecimals(double d)
    {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(d);
    }

    protected OnItemSelectedListener spokeCrossSelectedListener = new OnItemSelectedListener()
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

    protected OnItemSelectedListener spokeCountSelectedListener = new OnItemSelectedListener()
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

    protected OnItemSelectedListener hubModelSelectedListener = new OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
        {
            if (null != hubArray)
            {
                if (position < hubArray.length)
                {
                    Hub h = hubArray[position];

                    selectedHub = h;
                    
                    System.out.println("*****************************hub model selected: "+selectedHub.model+","+selectedHub.nondriveSideFlangeDiameter+","+selectedHub.driveSideFlangeDiameter);
                    if (select.equals(h.model))
                    {
                        hubModelSpinner.setSelectionValid(false);
                    }
                    else
                    {
                        hubModelSpinner.setSelectionValid(true);
                        hubModelSelection = position;        
                    }

                    checkEnableCalculateButton();
                }
                else
                {
                    System.out.println("Hub selection exceeds the array!******************************");
                }
            }
            else
            {
                System.out.println("Hub Array is NULL!***********************");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView)
        {
            System.out.println("****************************rim model, nothing selected");
        }
    };

    protected OnItemSelectedListener rimModelSelectedListener = new OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
        {
            Rim r = rimArray[position];
            System.out.println("*****************************rim model selected: "+r.model);
            selectedRim = r;
            
            if (select.equals(r.model))
            {
                rimModelSpinner.setSelectionValid(false);
                rimModelSelection = position;
            }
            else
            {
                rimModelSpinner.setSelectionValid(true);
            }

            checkEnableCalculateButton();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView)
        {
            System.out.println("****************************rim model, nothing selected");
        }
    };

    protected OnItemSelectedListener rimBrandSelectedListener = new OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
        {
            Brand b = rimBrandArray[position];
           
            if (null != b)
            {
                if (select.equals(b.name))
                {
                    if (null != rimModelSpinner)
                    {
                      rimModelSpinner.setEnabled(false);
                    }

                    if (null != rimBrandSpinner)
                    {
                    rimBrandSpinner.setSelectionValid(false);
                    }
                }
                else
                {
                    populateRimModelSpinner(b.id);
                    rimModelSpinner.setEnabled(true);
                    rimBrandSpinner.setSelectionValid(true);
                }

                checkEnableCalculateButton();
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView)
        {
            System.out.println("****************************rim brand, nothing selected");
            rimModelSpinner.setEnabled(false);
        }
    };


    protected OnItemSelectedListener hubBrandSelectedListener = new OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
        {
            Brand b = hubBrandArray[position];
            System.out.println("*****************************hub brand selected: "+b.name);

            if (select.equals(b.name))
            {
                hubModelSpinner.setEnabled(false);
                hubBrandSpinner.setSelectionValid(false);
            }
            else
            {
                populateHubModelSpinner(b.id);
                hubModelSpinner.setEnabled(true);
                hubBrandSpinner.setSelectionValid(true);
            }

            checkEnableCalculateButton();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView)
        {
            hubModelSpinner.setEnabled(false);
            System.out.println("****************************hub brand, nothing selected");
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
    public void populateHubModelSpinner(int brandIndex)
    {

        if (brandIndex != hubBrandIndex)
        {
            hubBrandIndex = brandIndex;

            try
            {
            List<Hub> hubModelList = dbAdapter.getHubsOfBrand(brandIndex, "mountain");
            hubArray = new Hub[hubModelList.size()+1];
            hubModelList.toArray(hubArray);

            Hub h      = new Hub();
            h.model    = select;
            h.ID       = selectID;

            hubArray[hubModelList.size()] = h;

            System.out.println("*************************************hubmodellist: "+hubArray.length);

           /* Setup the spinner for the hub model */
            hubModelSpinner = (MySpinner) findViewById(R.id.hub_model_spinner);
            hubModelSpinner.setEnabled(false);

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<Hub> hubModelAdapter = new ArrayAdapter<Hub>(this, android.R.layout.simple_spinner_item, hubArray);

            // Specify the layout to use when the list of choices appears
            hubModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            hubModelSpinner.setAdapter(hubModelAdapter);

      
            // set the default to "Select"
            hubModelSpinner.setSelection(hubArray.length-1);

            hubModelSpinner.setOnItemSelectedListener(hubModelSelectedListener);
        }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
            }
        }
    }

    public void populateHubBrandSpinner()
    {
        /* Setup the spinner for the hub brand */
        hubBrandSpinner = (MySpinner) findViewById(R.id.hub_brand_spinner);

        try 
        {

            List<Brand> hubBrandList = dbAdapter.getHubBrands("mountain");
            hubBrandArray = new Brand[hubBrandList.size()+1];
            hubBrandList.toArray(hubBrandArray);

            Brand b = new Brand();
            b.name = select;
            b.id=selectID;
            hubBrandArray[hubBrandArray.length-1] = b;

            adapter = new ArrayAdapter<Brand>(this, android.R.layout.simple_spinner_item, hubBrandArray);

            // Specify the layout to use when the list of choices appears
            hubBrandSpinner.setAdapter(adapter);


            // set the format of the spinner items during selection; select the larger item size for easier selection
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            hubBrandSpinner.setSelection(hubBrandArray.length-1);
            
            hubBrandSpinner.setOnItemSelectedListener(hubBrandSelectedListener);
         
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        } 
    }

    public void populateRimBrandSpinner()
    {
        try
        {
            
            List<Brand> rimBrandList = dbAdapter.getRimBrands("mountain");
            rimBrandArray = new Brand[rimBrandList.size()+1];
            rimBrandList.toArray(rimBrandArray);

            // insert the default "select" option for this spinner
            Brand b = new Brand();
            b.name=select;
            b.id=selectID;
            rimBrandArray[rimBrandArray.length-1] = b;
            
            ArrayAdapter<Brand> adapterRimBrand = new ArrayAdapter<Brand>(this, android.R.layout.simple_spinner_item, rimBrandArray);
     
            /* Setup the rim brand spinner */
            rimBrandSpinner = (MySpinner) findViewById(R.id.rim_brand_spinner);
            // Specify the layout to use when the list of choices appears
            adapterRimBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the rim brand spinner
            rimBrandSpinner.setAdapter(adapterRimBrand);
     
            // set default to "Select"
            rimBrandSpinner.setSelection(rimBrandArray.length-1);

            rimBrandSpinner.setOnItemSelectedListener(rimBrandSelectedListener);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        }
    }

    public void populateRimModelSpinner(int brand)
    {
            
            if (brand != rimBrandIndex)
            {
                try
                {
                rimBrandIndex = brand;

                List<Rim> rimList = dbAdapter.getRimsOfBrand(brand, "mountain");

                if (rimList.size() > 0)
                {
                    rimArray = new Rim[rimList.size()+1];
                    rimList.toArray(rimArray);

                    // insert the default "select" option for this spinner
                    Rim r   = new Rim();
                    r.model = select;
                    r.ID    = selectID;
                    rimArray[rimArray.length-1] = r;


                    adapterRimModel = new ArrayAdapter<Rim>(this, android.R.layout.simple_spinner_item, rimArray);

                    /* Setup the rim model spinner */
                    rimModelSpinner = (MySpinner) findViewById(R.id.rim_model_spinner);

                    // Specify the layout to use when the list of choices appears
                    adapterRimModel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Apply the adapter to the front hub model spinner
                    rimModelSpinner.setAdapter(adapterRimModel);

                    // set the default to "Select"
                    rimModelSpinner.setSelection(rimArray.length-1);

                    rimModelSpinner.setOnItemSelectedListener(rimModelSelectedListener);
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                }
        }
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
        data.putInt("rimmodelselection",rimModelSelection);
        data.putInt("hubmodelselection",hubModelSelection);
        data.putInt("rimbrandindex",rimBrandIndex);
        data.putInt("hubbrandindex",hubBrandIndex);
        System.out.println("**********************************Saving Configuration***********************:"+rimModelSelection+","+hubModelSelection);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        boolean reinit = (null != savedInstanceState);

        if(reinit)
        {
            hubModelSelection = savedInstanceState.getInt("hubmodelselection");
            rimModelSelection = savedInstanceState.getInt("rimmodelselection");
            rimBrandIndex     = savedInstanceState.getInt("rimbrandindex");
            hubBrandIndex     = savedInstanceState.getInt("hubbrandindex");
            System.out.println("*************************Restoring configuration*****************:"+rimModelSelection+","+hubModelSelection);
        }

     
        dbAdapter = new com.kineticsproject.spokecalculator.android.DatabaseAdapter(this);
       
        if (!dbAdapter.open())
        {
            System.out.println("*****************failed to open db\r\n");
        }
        else
        {
            System.out.println("****************opened db successfully\r\n");
        }

        if (true)
        {

         int tmpHubBrandIndex = hubBrandIndex;
         int tmpRimBrandIndex = rimBrandIndex;
         hubBrandIndex=0;
         rimBrandIndex=0;
 
        if (!reinit)
        {
            populateRimModelSpinner(6);
            populateHubModelSpinner(1);
        }
        else
        {
            populateRimModelSpinner(tmpRimBrandIndex);
            populateHubModelSpinner(tmpHubBrandIndex);
        }
        populateHubBrandSpinner();
        populateSpokeCountSpinner();
        populateSpokeCrossSpinner();
        populateRimBrandSpinner();
        spokeCrossArray   = getResources().getStringArray(R.array.spoke_cross_array);
        spokeCountArray   = getResources().getStringArray(R.array.spoke_count_array);

        nondriveSideSpokeLengthTextView  =  (TextView) findViewById(R.id.nondriveside_spokelength_textview);
       // fails on uncle brad's phone with android version 2.3.5 (api does not exist) 
       // nondriveSideSpokeLengthTextView.setTextIsSelectable(false);
        driveSideSpokeLengthTextView     =  (TextView) findViewById(R.id.driveside_spokelength_textview);
        hubModelTextView = (TextView) findViewById(R.id.hub_model_textview);
    
        driveSideSpokeLengthTextView.setEnabled(false);
        nondriveSideSpokeLengthTextView.setEnabled(false);

        }

        if(reinit)
        {

            spokeCrossArray   = getResources().getStringArray(R.array.spoke_cross_array);
            spokeCountArray   = getResources().getStringArray(R.array.spoke_count_array);

            nondriveSideSpokeLengthTextView  =  (TextView) findViewById(R.id.nondriveside_spokelength_textview);
            // fails on uncle brad's phone
            //nondriveSideSpokeLengthTextView.setTextIsSelectable(false);
            driveSideSpokeLengthTextView     =  (TextView) findViewById(R.id.driveside_spokelength_textview);
            hubModelTextView = (TextView) findViewById(R.id.hub_model_textview);
    

            rimModelSpinner.setSelection(rimModelSelection);
            hubModelSpinner.setSelection(hubModelSelection);
            checkEnableCalculateButton();
            System.out.println("REINIT******************************************");
        }



          System.out.println("ENDOFONCREATE*************************");



       } // onCreate

    
}
