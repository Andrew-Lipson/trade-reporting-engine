package com.tradeReportingEngine;

import java.util.HashMap;

public final class Utils {

    public static boolean isAnAnagram(String str1, String str2){
        if (str1.length() != str2.length()) return false;

        HashMap<Character,Integer> freqMap = new HashMap<>();

        for(char c : str1.toLowerCase().toCharArray()){
            int occurrence =  freqMap.getOrDefault(c,0);
            freqMap.put(c,occurrence+1);
        }

        for(char c : str2.toLowerCase().toCharArray()){
            int occurrence =  freqMap.getOrDefault(c,-1);
            if (occurrence<0) return false;
            freqMap.put(c,occurrence-1);
        }

        return true;
    }
}
