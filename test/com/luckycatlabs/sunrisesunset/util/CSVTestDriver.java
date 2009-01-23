/*
 * Copyright 2008-2009 Mike Reedell / LuckyCatLabs.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
