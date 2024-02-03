package mrp_v2.concreteconversion.server;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mrp_v2.concreteconversion.ConcreteConversionCommon;

public class Config {

    static File file;
    static JsonObject object;

    protected static boolean onlyPlayerThrownItems;
    protected static int conversionCheckDelay;
    protected static int conversionDelay;
    protected static boolean disableVanillaConversionMechanic;
    protected static boolean convertMud;

    public static void init(File fileSrc) {
        onlyPlayerThrownItems = true;
        conversionCheckDelay = 20;
        conversionDelay = 0;
        disableVanillaConversionMechanic = false;
        convertMud = false;

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ConcreteConversionCommon.CONFIG.OPTI, onlyPlayerThrownItems);
        jsonObject.addProperty(ConcreteConversionCommon.CONFIG.CCD, conversionCheckDelay);
        jsonObject.addProperty(ConcreteConversionCommon.CONFIG.CD, conversionDelay);
        jsonObject.addProperty(ConcreteConversionCommon.CONFIG.DVCM, disableVanillaConversionMechanic);
        jsonObject.addProperty(ConcreteConversionCommon.CONFIG.CM, convertMud);

        if(!fileSrc.exists() || fileSrc.length() <= 2)
            save(fileSrc, jsonObject);
        else
            load(fileSrc);

        file = fileSrc;
        object = jsonObject;
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

    public static void load(File fileSrc) {
        try {
            Object obj = JsonParser.parseReader(new FileReader(fileSrc));
            JsonObject jsonObjectRead = (JsonObject) obj;

            onlyPlayerThrownItems = jsonObjectRead.get(ConcreteConversionCommon.CONFIG.OPTI).getAsBoolean();
            conversionCheckDelay = jsonObjectRead.get(ConcreteConversionCommon.CONFIG.CCD).getAsInt();
            conversionDelay = jsonObjectRead.get(ConcreteConversionCommon.CONFIG.CD).getAsInt();
            disableVanillaConversionMechanic = jsonObjectRead.get(ConcreteConversionCommon.CONFIG.DVCM).getAsBoolean();
            convertMud = jsonObjectRead.get(ConcreteConversionCommon.CONFIG.CM).getAsBoolean();

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

    public static File getFile() {
        return file;
    }

    public static JsonObject getObject() {
        return object;
    }
}
