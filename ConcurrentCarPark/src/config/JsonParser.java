package config;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONException;
import org.json.JSONObject;

import jdk.nashorn.internal.parser.JSONParser;

public class JsonParser {
	
	public void readInput(String s){
		try {
			FileReader reader = new FileReader(s);
			System.out.println(reader.toString());
			JSONObject jsonObject = new JSONObject(reader);
			
			System.out.println(jsonObject.length());
			jsonObject.get("num_cars");
		} catch (FileNotFoundException | JSONException e) {
			e.printStackTrace();
		}
	}
}
