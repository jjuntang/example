package com.example.json02.family;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FamilyTest {

    @Test
    void familyJsonGeneratorUsingStringWriterTest() {
        Writer writer = new StringWriter();
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jg = null;
        try {
            jg = jfactory
                    .createGenerator(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            jg.writeStartArray();
            int i = 0;
            while (i < 6)
            {
                jg.writeStartArray();
                jg.writeObject(i++);
                jg.writeObject(i++);
                jg.writeEndArray();
            }
            jg.writeEndArray();
            jg.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(writer.toString());
    }

    @Test
    void familyJsonGeneratorUsingByteArrayOutputStreamTest() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        JsonGenerator jg = null;
        try {
            JsonFactory jfactory = new JsonFactory();
            jg = jfactory
                    .createGenerator(stream, JsonEncoding.UTF8);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            jg.writeStartObject();
            jg.writeFieldName("address");
            jg.writeStartArray();
            int i = 0;
            while (i < 6)
            {
                jg.writeStartArray();
                jg.writeObject(i++);
                jg.writeObject(i++);
                jg.writeEndArray();
            }
            jg.writeEndArray();
            jg.writeEndObject();
            jg.flush();
            jg.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = null;
        try {
            json = new String(stream.toByteArray(), "UTF-8");
            System.out.println(json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}