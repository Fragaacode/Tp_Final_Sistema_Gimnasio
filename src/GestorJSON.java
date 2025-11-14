import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class GestorJSON {
    public GestorJSON(){}

    ///  ///////////////////////////
    /// DE JSON A ARCHIVOS ///
    /// //////////////////////////

    public void gimnasioAarchivo(String nombreArchivo, GestionGimnasio gym){
        LecturaEscrituraDeArchivo.guardar(nombreArchivo, serializarGimnasio(gym));
    }

    //GUARDADO DE LISTAS//

    public void guardarClientes(String nombreArchivo, GestionGimnasio gym){
        LecturaEscrituraDeArchivo.guardar(nombreArchivo, serializarListaClientes(gym.getClientes()));
    }

    public void guardarAsistentes(String nombreArchivo, GestionGimnasio gym){
        LecturaEscrituraDeArchivo.guardar(nombreArchivo, serializarSetAsistentes(gym.getAsistentes()));
    }

    public void guardarAdministradores(String nombreArchivo, GestionGimnasio gym){
        LecturaEscrituraDeArchivo.guardar(nombreArchivo, serializarMapAdministrador(gym.getAdministradores()));
    }

    /// /////////////////////////////////////////////////////////////////////////////////////////////////////////

    //SOBRE ESCRITURA DE LISTAS

    public void sobreEscribirListaClientes(String nombreArchivo, GestionGimnasio gym){
        LecturaEscrituraDeArchivo.sobreEscribir(nombreArchivo, serializarListaClientes(gym.getClientes()));
    }

    public void sobreEscribirListaAsistentes(String nombreArchivo, GestionGimnasio gym){
        LecturaEscrituraDeArchivo.sobreEscribir(nombreArchivo, serializarSetAsistentes(gym.getAsistentes()));
    }

    public void sobreEscribirListaAdministradores(String nombreArchivo, GestionGimnasio gym){
        LecturaEscrituraDeArchivo.sobreEscribir(nombreArchivo, serializarMapAdministrador(gym.getAdministradores()));
    }

    /// /////////////////////////////////////////////////////////////////////////////////////////////////////////

    //GUARDADO INDIVIDUAL

    public void guardar(String nombreArchivo, Cliente cliente){
        LecturaEscrituraDeArchivo.guardar(nombreArchivo, serializarCliente(cliente));
    }

    public void guardar(String nombreArchivo, Asistente asistente){
        LecturaEscrituraDeArchivo.guardar(nombreArchivo, serializarAsistente(asistente));
    }
    public void guardar(String nombreArchivo, Administrador administrador){
        LecturaEscrituraDeArchivo.guardar(nombreArchivo, serializarAdministrador(administrador));
    }

    /// /////////////////////////////////////////////////////////////////////////////////////////////////////////

    //SOBRE ESCRITURA INDIVIDUAL

    public void sobreEscribir(String nombreArchivo, Cliente cliente){
        LecturaEscrituraDeArchivo.sobreEscribir(nombreArchivo, serializarCliente(cliente));
    }

    public void sobreEscribir(String nombreArchivo, Asistente asistente){
        LecturaEscrituraDeArchivo.sobreEscribir(nombreArchivo, serializarAsistente(asistente));
    }

    public void sobreEscribir(String nombreArchivo, Administrador administrador){
        LecturaEscrituraDeArchivo.sobreEscribir(nombreArchivo, serializarAdministrador(administrador));
    }

    /// /////////////////////////////////////////////////////////////////////////////////////////////////////////


    /*public GestionGimnasio ArchivoAGimnasio(String nombreArchivo) {
        GestionGimnasio gym = new GestionGimnasio();
        JSONTokener tokener = LecturaEscrituraDeArchivo.leer(nombreArchivo);
        try {
            GestionGimnasio listasRecibida = deserializarListaClientes(new JSONArray(tokener));
            lista.setClientes();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }*/




    public JSONObject serializarGimnasio(GestionGimnasio gimnasio){
        JSONObject gym = new JSONObject();
        try {
            JSONArray arrayClientes = serializarListaClientes(gimnasio.getClientes());
            gym.put("clientes", arrayClientes);

            JSONArray mapAdministrador = serializarMapAdministrador(gimnasio.getAdministradores());
            gym.put("administradores", mapAdministrador);

            JSONArray setAsistentes = serializarSetAsistentes(gimnasio.getAsistentes());
            gym.put("asistentes", setAsistentes);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return gym;
    }

    public GestionGimnasio deserializarGimnasio(JSONObject jsonObject){
        GestionGimnasio gimnasio = new GestionGimnasio();

        try {
            JSONArray clientesJSON = (JSONArray) jsonObject.get("clientes");
            ArrayList<Cliente> arrCliente = deserializarListaClientes(clientesJSON);
            gimnasio.setClientes(arrCliente);

            JSONArray adminJSON = (JSONArray) jsonObject.get("administradores");
            HashMap<String, Administrador> mapAdmin = deserializarMapAdministrador(adminJSON);
            gimnasio.setAdministradores(mapAdmin);

            JSONArray asistentesJSON = (JSONArray) jsonObject.get("asistentes");
            HashSet<Asistente> setAsistente = deserializarSetAsistente(asistentesJSON);
            gimnasio.setAsistentes(setAsistente);

        }catch (JSONException e){
            e.printStackTrace();
        }


        return gimnasio;
    }

    public JSONObject serializarPersona(Persona p){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("nombre", p.getNombre());
            jsonObject.put("edad", p.getEdad());
            jsonObject.put("dni", p.getDni());
            jsonObject.put("id", p.getId());
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject serializarCliente(Cliente c){
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject();
            jsonObject = serializarPersona(c);
            jsonObject.put("saldo", c.getSaldo());
            jsonObject.put("tipoCuota", c.geteCuota());
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray serializarListaClientes(ArrayList<Cliente> listaClientes){
        JSONArray jsonArray = null;
        try{
            jsonArray = new JSONArray();
            for (Cliente c : listaClientes){
                jsonArray.put(serializarCliente(c));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONObject serializarAdministrador(Administrador a){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject = serializarPersona(a);
            jsonObject.put("usuario", a.getUsuario());
            jsonObject.put("contrasenia", a.getContraseña());
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray serializarMapAdministrador(HashMap<String, Administrador> ad){
        JSONArray jsonMap = null;
        try {
            jsonMap = new JSONArray();
            for (String key : ad.keySet()){
                Administrador a = ad.get(key);
                jsonMap.put(serializarAdministrador(a));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonMap;
    }

    public JSONObject serializarAsistente(Asistente as){
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject();
            jsonObject = serializarPersona(as);
            jsonObject.put("Turno", as.geteTurno());
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray serializarSetAsistentes(HashSet<Asistente> aSet){
        JSONArray jsonSet = null;
        try {
            jsonSet = new JSONArray();
            for (Asistente as : aSet){
                jsonSet.put(serializarAsistente(as));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonSet;
    }

    public void auxDeserializarPersona(Persona p, JSONObject jObject){
        try{
            if (jObject.has("nombre")){
                p.setNombre(jObject.getString("nombre"));
            }
            if (jObject.has("edad")){
                p.setEdad(jObject.getInt("edad"));
            }
            if (jObject.has("dni")){
                p.setDni(jObject.getString("dni"));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public Cliente deserializarCliente(JSONObject jsonObject){
        Cliente c = new Cliente();
        try{
            auxDeserializarPersona(c, jsonObject);

            c.setSaldo(jsonObject.getDouble("saldo"));
            c.seteCuota(eCuota.valueOf(jsonObject.getString("tipoCuota")));

        }catch (JSONException e){
            e.printStackTrace();
        }
        return c;
    }

    public ArrayList<Cliente> deserializarListaClientes(JSONArray jsonArray){
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        try{
            for (int i = 0; i < jsonArray.length(); i++){
                Cliente c = deserializarCliente(jsonArray.getJSONObject(i));
                listaClientes.add(c);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return listaClientes;
    }

    public Administrador deserializarAdministrador(JSONObject jsonObject){
        Administrador a = new Administrador();
        try {
            auxDeserializarPersona(a,jsonObject);

            a.setusuario(jsonObject.getString("Usuario"));
            a.setcontraseña(jsonObject.getString("Contraseña"));

        }catch (JSONException e){
            e.printStackTrace();
        }
        return a;
    }

    public HashMap<String, Administrador> deserializarMapAdministrador(JSONArray jsonArray){
        HashMap<String, Administrador> a = new HashMap<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++){
                Administrador ad = deserializarAdministrador(jsonArray.getJSONObject(i));
                String key = ad.getDni();
                a.put(key, ad);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return a;
    }

    public Asistente deserializarAsistente(JSONObject jsonObject){
        Asistente a = new Asistente();
        try {
            auxDeserializarPersona(a, jsonObject);

            a.seteTurno(eTurno.valueOf(jsonObject.getString("Turno")));

        }catch (JSONException e){
            e.printStackTrace();
        }
        return a;
    }

    public HashSet<Asistente> deserializarSetAsistente(JSONArray jsonArray){
        HashSet<Asistente> a = new HashSet<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++){
                Asistente ad = deserializarAsistente(jsonArray.getJSONObject(i));
                a.add(ad);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return a;


        }
}
