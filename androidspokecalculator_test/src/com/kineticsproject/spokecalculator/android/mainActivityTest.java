package com.kineticsproject.spokecalculator.android;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.kineticsproject.spokecalculator.android.mainActivityTest \
 * com.kineticsproject.spokecalculator.android.tests/android.test.InstrumentationTestRunner
 */
public class mainActivityTest extends ActivityInstrumentationTestCase2<mainActivity>
{

    public mainActivityTest() 
    {
        super("com.kineticsproject.spokecalculator.android", mainActivity.class);

    }

}
