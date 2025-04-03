package com.tradeReportingEngine;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class UtilsTest {

    @ParameterizedTest
    @CsvSource({
            "EMU_BANK,KANMU_EB,true",
            "Listen,Silent,true",
            "Night,Thing,true",
            "Straw,Wars,false",
            "careful,race,false",
            "race,careful,false",
            "Straw,Wars,false",
            "wart,Wars,false",
            "Listens,Silent,false",
            "Listen,Silents,false",
            "a,b,false",
            "'',a,false",
            "a,'',false",
            "a,a,true",
            "A,a,true",
            "A,A,true",
            "a,A,true",
            "'','',true",
    })
    @SneakyThrows
    public void testIsAnAnagram(String str1, String str2, boolean isAnagram){
        Assertions.assertEquals(isAnagram,Utils.isAnAnagram(str1,str2));
    }
}
