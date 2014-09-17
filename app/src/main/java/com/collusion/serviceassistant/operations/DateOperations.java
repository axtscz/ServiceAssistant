package com.collusion.serviceassistant.operations;

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

    public String getMonth(String month1)
    {
        String TitleMonth = "";
        if (month1.equals("01"))
        {
            TitleMonth = "January";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("02"))
        {
            TitleMonth = "February";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("03"))
        {
            TitleMonth = "March";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("04"))
        {
            TitleMonth = "April";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("05"))
        {
            TitleMonth = "May";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("06"))
        {
            TitleMonth = "June";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("07"))
        {
            TitleMonth = "July";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("08"))
        {
            TitleMonth = "August";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("09"))
        {
            TitleMonth = "September";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("10"))
        {
            TitleMonth = "October";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("11"))
        {
            TitleMonth = "November";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("12"))
        {
            TitleMonth = "December";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else {
            TitleMonth = "December";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
    }
}
