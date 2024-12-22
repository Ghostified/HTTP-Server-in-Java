package org.codefromscratch.httpserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.codefromscratch.httpserver.util.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    //Singleton Pattern
    private static  ConfigurationManager myConfigurationManger;
    private static Configuration  myCurrentConfiguration;

    //Constructor
    private  ConfigurationManager (){

    }
    //get instance
    public static ConfigurationManager getInstance(){
        if (myConfigurationManger == null)
            myConfigurationManger = new ConfigurationManager();
        return myConfigurationManger;
    }

    //method used to load a configuration  file in the path provided
    //throws an IO exception if the path is unavailable
    public void loadConfigurationFile(String filePath){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        try{
            while ((i = fileReader.read()) != -1){
                sb.append((char)i);
            }
        } catch (IOException e){
            throw new HttpConfigurationException(e);
        }
        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the configuration file", e);
        }
        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the configuration file,internal",e);
        }
    }

   /* Method to get the configuration available
   Returns the loaded configuration
   Throws an IO error
    */
    public Configuration getCurrentConfiguration (){
        if (myCurrentConfiguration == null){
            throw new HttpConfigurationException("No current configuration Set");
        }
        return myCurrentConfiguration;
    }
}
