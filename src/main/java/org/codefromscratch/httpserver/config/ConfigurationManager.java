package org.codefromscratch.httpserver.config;

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
    public void loadConfigurationFile(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        StringBuffer sb = new StringBuffer();
        int i;
        while( ( i  = fileReader.read()) != -1){
            sb.append((char)i);
        }
        JsonNode conf = Json.parse(sb.toString());
        myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
    }

   /* Method to get the configuration available
   Returns the loaded configuration
   Throws an IO error
    */
    public void getCurrentConfiguration (){

    }
}
