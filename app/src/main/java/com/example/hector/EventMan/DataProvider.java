package com.example.hector.EventMan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataProvider {
    public static HashMap<String, List<String>> getInfo()
    {
        HashMap<String, List<String>> MoviesDetails = new HashMap<String, List<String>>();

        List<String> Action_Movies = new ArrayList<String>();
        Action_Movies.add("The Expendables 32");
        Action_Movies.add("Guardian of the Galaxy");

        List<String> Romntic_Movies = new ArrayList<String>();
        Romntic_Movies.add("Mean Girls");
        Romntic_Movies.add("The Devil Wears Prada");

        List<String> Horror_Movies= new ArrayList<String>();
        Horror_Movies.add("Sleepy Hollow");
        Horror_Movies.add("Eden lake");

        List<String> Comedy_Movies = new ArrayList<String>();
        Comedy_Movies.add("Ride Along");

        return MoviesDetails;
    }

}

	