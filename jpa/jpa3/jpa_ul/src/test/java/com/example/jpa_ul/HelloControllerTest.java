package com.example.jpa_ul;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HelloControllerTest {

    @Autowired
    private ChargerRepository chargerRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void 디비에서_시리얼넘버로_읽어오기() {
//        Charger ch = null;
//        try {
//            ch = chargerRepository.findBySerialNumber("TT00000011");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        assertEquals("TT00000011", ch.getSerialNumber(), "실패");
    }
}