package main.java.com.epam.lab.web.readers;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVParser{

    public static String[][] parse(String pathToFile){
        String[][] resultArray = {};
        try(BufferedReader reader = new BufferedReader(new FileReader(pathToFile))){
            List<List<String>> resultList = new ArrayList<>();
            String line;
            while((line = reader.readLine()) != null){
                String[] values = line.split(",");
                resultList.add(Arrays.asList(values));
            }
            resultArray = new String[resultList.size()][];
            for(int i = 0; i < resultList.size(); i++){
                resultArray[i] = Arrays.copyOf(resultList.get(i).toArray(), resultList.get(i).size(), String[].class);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return resultArray;
    }
}
