package mrp_v2.concreteconversion.server;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Config {

	public static int conversionCheckDelay;

	public static int conversionDelay;
	
	public static boolean disableVanillaConversionMechanic;

	public static void init(File fileSrc) {

		conversionCheckDelay = 20;
		conversionDelay = 0;
		disableVanillaConversionMechanic = false;

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("conversionCheckDelay", conversionCheckDelay);
		jsonObject.addProperty("conversionDelay", conversionDelay);
		jsonObject.addProperty("disableVanillaConversionMechanic", disableVanillaConversionMechanic);

		if(!fileSrc.exists() || fileSrc.length() <= 2) {
			save(fileSrc, jsonObject);
		} else {
			JsonParser parser = new JsonParser();
			try {
				Object obj = parser.parse(new FileReader(fileSrc));
				JsonObject jsonObjectRead = (JsonObject) obj;;
				conversionCheckDelay = jsonObjectRead.get("conversionCheckDelay").getAsInt();
				conversionDelay = jsonObjectRead.get("conversionDelay").getAsInt();
				disableVanillaConversionMechanic = jsonObjectRead.get("disableVanillaConversionMechanic").getAsBoolean();
				
				if(conversionCheckDelay < 1) {
					conversionCheckDelay = 1;
				}
				
				if(conversionCheckDelay > 200) {
					conversionCheckDelay = 200;
				}
				
				if(conversionDelay < 0) {
					conversionDelay = 0;
				}
				
				if(conversionDelay > 6000) {
					conversionDelay = 6000;
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}
	
	public static void save(File fileSrc, JsonObject object) {
		try {
			FileWriter file = new FileWriter(fileSrc);
			file.write(new GsonBuilder().setPrettyPrinting().create().toJson(object));
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
