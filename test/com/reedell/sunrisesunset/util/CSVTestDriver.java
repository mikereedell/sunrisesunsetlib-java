package com.reedell.sunrisesunset.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple class to read a CSV file and return a list of arrays of n strings that comprise a line in the CSV
 * file.
 */
public class CSVTestDriver {

    LineNumberReader reader;

    public CSVTestDriver(String csvFileName) {
        try {
            File csvFile = new File(csvFileName);
            FileReader csvFileReader = new FileReader(csvFile);
            reader = new LineNumberReader(csvFileReader);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    public List<String[]> getData() {
        List<String[]> valueList = new ArrayList<String[]>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                // Split the line on the ',' and turn it into an array.
                String[] datum = line.split("\\,");
                valueList.add(datum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valueList;
    }
}
