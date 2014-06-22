package com.kineticsproject.spokecalculator.android;

import android.widget.Spinner;
import android.content.Context;
import android.util.AttributeSet;

public class MySpinner extends Spinner
{
    private boolean valid;

    private void init()
    {
        valid = false;
    }

    public MySpinner(Context context)
    {
        super(context);
        init();
    }
    

    public MySpinner(Context context, AttributeSet attr)
    {
        super(context, attr);
        init();
    }

    public MySpinner(Context context, AttributeSet att, int defStyle)
    {
        super(context, att, defStyle);
        init();
    }

    public MySpinner(Context context, AttributeSet att, int defStyle, int mode)
    {
        super(context, att, defStyle, mode);
        init();
    }

    public void setSelectionValid(boolean valid)
    {
        this.valid = valid;
    }

    public boolean isSelectionValid()
    {
        return valid;
    }
}

