package com.lenovo.trafficclient;

import android.widget.Toast;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String str="([1-9]|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])(.+(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])){3}";
        Pattern pattern=Pattern.compile(str);
        Matcher matcher=pattern.matcher("01.1112221.121.21");
        if (matcher.matches()){
            System.out.println("dadada");
            return;
        }
    }
}