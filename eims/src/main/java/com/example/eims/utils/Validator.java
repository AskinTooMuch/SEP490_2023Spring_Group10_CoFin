package com.example.eims.utils;

public class Validator {
    public int isInteger(String s){
        try {
            int i = Integer.parseInt(s.trim());
            return i;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    public Long isLong(String s){
        try {
            Long l = Long.parseLong(s.trim());
            return l;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Double isDouble(String s){
        try {
            Double d = Double.parseDouble(s.trim());
            return d;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String advanceTrim(String s, Boolean required){
        s = s.replaceAll("\\s+", " ");
        s = s.trim();
        if ((required) && (s.length() == 0)) throw new RuntimeException();
        return s;
    }
}
