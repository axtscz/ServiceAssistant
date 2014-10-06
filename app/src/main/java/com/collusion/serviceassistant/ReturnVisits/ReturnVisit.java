package com.collusion.serviceassistant.ReturnVisits;

import java.util.Comparator;

/**
 * Created by Antonio on 9/16/2014.
 */
public class ReturnVisit implements Comparator<ReturnVisit>, Comparable<ReturnVisit> {
    private String name;
    private String address;
    private String filename;
    private String longitude;
    private String latitude;
    private String latlong;
    private String dayofWeek;
    private String distances;
    private String Notes;

    ReturnVisit(){
    }

    public ReturnVisit(String n, String a, String f, String lon, String lat, String ll, String dow, String d, String notes){
        name = n;
        address = a;
        filename = f;
        longitude = lon;
        latitude = lat;
        latlong = ll;
        dayofWeek = dow;
        distances = d;
        Notes = Notes;

    }


    public String getReturnVisitName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getName(){
        return name;
    }

    public String getFilename(){
        return filename;
    }
    public String getLongitude(){
        return longitude;
    }
    public String getLatitude(){
        return latitude;
    }
    public String getLatlong(){
        return latlong;
    }

    public String getDayofWeek(){
        return dayofWeek;
    }


    public String getDistances(){
        return distances;
    }

    public String getNotes(){
        return Notes;
    }


    // Overriding the compareTo method
    public int compareTo(ReturnVisit d){
        return (this.distances).compareTo(d.distances);
    }

    // Overriding the compare method to sort the age 
    public int compareto(ReturnVisit d){
        return (this.name).compareTo(d.name);
    }

    static Comparator<ReturnVisit> nameSort =  new Comparator<ReturnVisit>() {
        public int compare(ReturnVisit d) {
            return (d.name).compareTo(d.name);
        }

        @Override
        public int compare(ReturnVisit returnVisit, ReturnVisit returnVisit2) {
            return 0;
        }
    };

    @Override
    public int compare(ReturnVisit returnVisit, ReturnVisit returnVisit2) {
        return 0;
    }
}
