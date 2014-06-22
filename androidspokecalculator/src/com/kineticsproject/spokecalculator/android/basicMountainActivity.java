package com.kineticsproject.spokecalculator.android;
import com.kineticsproject.spokecalculator.calculator.*;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import com.kineticsproject.spokecalculator.android.basicActivity;

public class basicMountainActivity extends basicActivity
{

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

 
}
 
