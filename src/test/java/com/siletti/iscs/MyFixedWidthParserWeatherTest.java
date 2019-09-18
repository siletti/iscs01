package com.siletti.iscs;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class MyFixedWidthParserWeatherTest {


    MyFixedWidthParser myFixedWidthParser;
    File weatherFile;

    @Before
    public void setUp() throws Exception {
        URL url = this.getClass().getResource("/weather.dat");
        weatherFile = new File(url.getFile());
        myFixedWidthParser = new MyFixedWidthParserWeather(weatherFile);
    }


    @Test
    public void getKeyWithMinIntVal() {
        String result = myFixedWidthParser.getKeyWithMinIntVal();
        assertTrue(result.equals("14"));
    }

    @Test
    public void getTheList() throws IOException {
        List<String[]> result = myFixedWidthParser.getTheList(weatherFile);
        assertTrue(result.size() == 31);
    }

    @Test
    public void stringIntegerMap() {
        Map<String, Integer> result = myFixedWidthParser.stringIntegerMap();
        assertTrue(result.size() == 30);
    }
}