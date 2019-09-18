package com.siletti.iscs;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class MyFixedWidthParserFootballTest {

    MyFixedWidthParser myFixedWidthParser;
    File footballFile;

    @Before
    public void setUp() throws Exception {
        URL url = this.getClass().getResource("/football.dat");
        footballFile = new File(url.getFile());
        myFixedWidthParser = new MyFixedWidthParserFootball(footballFile);
    }


    @Test
    public void getKeyWithMinIntVal() {
        String result = myFixedWidthParser.getKeyWithMinIntVal();
        assertTrue(result.equals("Aston_Villa"));
    }

    @Test
    public void getTheList() throws IOException {
        List<String[]> result = myFixedWidthParser.getTheList(footballFile);
        assertTrue(result.size() == 21);
    }

    @Test
    public void stringIntegerMap() {
        Map<String, Integer> result = myFixedWidthParser.stringIntegerMap();
        assertTrue(result.size() == 20);
    }
}