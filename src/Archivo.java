import java.io.FileWriter;
import java.io.IOException;

public final class Archivo {

    public static void guardarDato(String nombreArchivo, String contenido){
        try (FileWriter fw = new FileWriter(nombreArchivo)) {
            fw.write(contenido);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}