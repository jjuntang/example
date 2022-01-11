package com.example.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

class CarTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void test1() {
        //System.out.println("test1");
        Car car = new Car("red", "sports");
        String ret = null;
        try {
            ret = objectMapper.writeValueAsString(car);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(ret);
        assertEquals("red", car.getColor(),
                "Car 의 color 에러");

//        assertEquals("blue", car.getColor(),
//                "Car 의 color 에러");
    }
}