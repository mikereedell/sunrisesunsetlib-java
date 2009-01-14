package com.luckycatlabs.sunrisesunset.util;

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

    private File testDataDirectory;

    public CSVTestDriver(String testDataDirectoryName) {
        testDataDirectory = new File(testDataDirectoryName);
    }

    public String[] getFileNames() {
        return testDataDirectory.list();
    }

    public List<String[]> getData(String testDataFileName) {
        List<String[]> valueList = new ArrayList<String[]>();
        try {
            FileReader csvFileReader = new FileReader(new File(testDataDirectory + "/" + testDataFileName));
            LineNumberReader reader = new LineNumberReader(csvFileReader);

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    String[] datum = line.split("\\,");
                    valueList.add(datum);
                }
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valueList;
    }
}
