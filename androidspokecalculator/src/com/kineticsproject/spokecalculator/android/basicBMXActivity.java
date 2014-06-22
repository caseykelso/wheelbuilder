package com.kineticsproject.spokecalculator.android;
import com.kineticsproject.spokecalculator.calculator.*;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import com.kineticsproject.spokecalculator.android.basicActivity;

public class basicBMXActivity extends basicActivity
{

    public void populateRimModelSpinner(int brand)
    {
            
            if (brand != rimBrandIndex)
            {
                try
                {
                rimBrandIndex = brand;

                List<Rim> rimList = dbAdapter.getRimsOfBrand(brand, "bmx");

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



    public void populateHubModelSpinner(int brandIndex)
    {

        if (brandIndex != hubBrandIndex)
        {
            hubBrandIndex = brandIndex;

            try
            {
            List<Hub> hubModelList = dbAdapter.getHubsOfBrand(brandIndex, "bmx");
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

            List<Brand> hubBrandList = dbAdapter.getHubBrands("bmx");
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


}
 
