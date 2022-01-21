package com.example.json02;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemSerializerTest {

    @Test
    void itemSerializerTest() {
        Item myItem = new Item(1, "theItem", new User(2, "theUser"));
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(Item.class, new ItemSerializer());
        mapper.registerModule(module);

        String serialized = null;
        try {
            serialized = mapper.writeValueAsString(myItem);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(serialized);
    }


}