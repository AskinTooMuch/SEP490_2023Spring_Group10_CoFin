package com.example.eims.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StringDealer {
    public String trimMax(String str) {
        return str.trim().replaceAll("\\s+"," ");
    }

    public Date convertToDateAndFormat (String str) {
        Date date;
        try {
            date = new Date(
                    (new SimpleDateFormat("yyyy-MM-dd").parse(str)).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
}
