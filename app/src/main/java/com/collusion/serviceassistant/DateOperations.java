package com.collusion.serviceassistant;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by Antonio on 8/24/2014.
 */
public class DateOperations {

    public String getdateFile()
    {
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        int currentYear = cal.get(Calendar.YEAR);
        Log.i("INFO", Integer.toString(currentYear));
        if (currentMonth > 9) {
            String CurrentMonthFilePath = Integer.toString(currentMonth) + Integer.toString(currentYear);
            return CurrentMonthFilePath;
        }
        else
        {
            String CurrentMonthFilePath = "0" + Integer.toString(currentMonth) + Integer.toString(currentYear);
            return CurrentMonthFilePath;
        }
    }
}
