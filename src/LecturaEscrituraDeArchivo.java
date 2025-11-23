import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LecturaEscrituraDeArchivo {

    // Guarda un JSONObject (reemplazando el contenido)
    public static void guardar(JSONObject jsonObject, String nombreArchivo){
        try (FileWriter fileWriter = new FileWriter(nombreArchivo)) {
            fileWriter.write(jsonObject.toString(4)); // indentación 4 espacios
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // Guarda un JSONArray (reemplazando el contenido)
    public static void guardar(JSONArray jsonArray, String nombreArchivo){
        try (FileWriter fileWriter = new FileWriter(nombreArchivo)) {
            fileWriter.write(jsonArray.toString(4));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // Sobrescribir es lo mismo que guardar en esta versión
    public static void sobreEscribir(JSONObject jsonObject, String nombreArchivo){
        guardar(jsonObject, nombreArchivo);
    }

    public static void sobreEscribir(JSONArray jsonArray, String nombreArchivo){
        guardar(jsonArray, nombreArchivo);
    }

    // Leer JSON desde archivo
    public static JSONTokener leer(String nombreArchivo) {
        try {
            return new JSONTokener(new FileReader(nombreArchivo));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
