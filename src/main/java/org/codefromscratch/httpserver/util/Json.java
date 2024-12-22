package org.codefromscratch.httpserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class Json{

    private static  ObjectMapper myObjectMapper = defaultObjectMapper();

    //Method that create a new Object Mapper
    private static ObjectMapper defaultObjectMapper (){
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //makes the parsing not crash if one object is missing
        return om;
    }

    //Method to parse a json String into a json node
    public static JsonNode parse(String jsonSrc) throws IOException {
        return  myObjectMapper.readTree(jsonSrc);
    }

    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return  myObjectMapper.treeToValue(node, clazz);
    }

    //method to create the configuration.java into a json node, returns a  json node with an object as input
    public static JsonNode toJson(Object obj){
        return  myObjectMapper.valueToTree(obj);
    }

    //method to call
    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }

    public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node, true);
    }

    //way to see the json node in a string format
    private static String generateJson(Object o, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = myObjectMapper.writer();
        if(pretty) {
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }

        return  objectWriter.writeValueAsString(o);
    }
}
