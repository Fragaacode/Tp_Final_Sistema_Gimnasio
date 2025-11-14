import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;


public class GestorJSON {
    public GestorJSON(){}

    ///  ///////////////////////////
    /// DE JSON A ARCHIVOS ///
    /// //////////////////////////

    public void gimnasioAarchivo(String nombreArchivo, GestionGimnasio gym){
        LecturaEscrituraDeArchivo.guardar(nombreArchivo, serializarGimnasio(gym));
    }

    public void sobreescribirGimnasioAarchivo(String nombreArchivo, GestionGimnasio gym){
        LecturaEscrituraDeArchivo.sobreEscribir(nombreArchivo, serializarGimnasio(gym));
    }


    /// /////////////////////////////////////////////////////////////////////////////////////////////////////////

    //GUARDADO INDIVIDUAL

    public void guardar(String nombreArchivo, Cliente cliente){
        GestionGimnasio gim = null;
        JSONTokener tokener = LecturaEscrituraDeArchivo.leer(nombreArchivo);
        try {
            gim = deserializarGimnasio(new JSONObject(tokener));
            gim.agregarCliente(cliente);
            LecturaEscrituraDeArchivo.sobreEscribir(nombreArchivo, serializarGimnasio(gim));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void guardar(String nombreArchivo, Asistente asistente){
        GestionGimnasio gim = null;
        JSONTokener tokener = LecturaEscrituraDeArchivo.leer(nombreArchivo);
        try {
            gim = deserializarGimnasio(new JSONObject(tokener));
            gim.agregarAsistente(asistente);
            LecturaEscrituraDeArchivo.sobreEscribir(nombreArchivo, serializarGimnasio(gim));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public void guardar(String nombreArchivo, Administrador administrador){
        GestionGimnasio gim = null;
        JSONTokener tokener = LecturaEscrituraDeArchivo.leer(nombreArchivo);
        try {
            gim = deserializarGimnasio(new JSONObject(tokener));
            gim.agregarAdministrador(administrador);
            LecturaEscrituraDeArchivo.sobreEscribir(nombreArchivo, serializarGimnasio(gim));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }



    public GestionGimnasio ArchivoAGimnasio(String nombreArchivo) {
        GestionGimnasio gym = null;
        JSONTokener tokener = LecturaEscrituraDeArchivo.leer(nombreArchivo);
        try {
            gym = deserializarGimnasio(new JSONObject(tokener));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gym;
    }


    public JSONObject serializarGimnasio(GestionGimnasio gimnasio){
        JSONObject gym = new JSONObject();
        try {
            JSONArray arrayClientes = serializarListaClientes(gimnasio.getClientes());
            gym.put("clientes", arrayClientes);

            JSONArray mapAdministrador = serializarMapAdministrador(gimnasio.getAdministradores());
            gym.put("administradores", mapAdministrador);

            JSONArray setAsistentes = serializarSetAsistentes(gimnasio.getAsistentes());
            gym.put("asistentes", setAsistentes);

            JSONArray treeClientes = serializarTreeMapClientes(gimnasio.getClientesOrdenadosPorNombre());
            gym.put("clientesOrdenadosPorNombre", treeClientes);
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

            JSONArray clientesOrdenadosJSON = (JSONArray) jsonObject.get("clientesOrdenadosPorNombre");
            TreeMap<String, Cliente> treeClientesOrdenados = deserialiarTreeMapClientes(clientesOrdenadosJSON);
            gimnasio.setClientesOrdenadosPorNombre(treeClientesOrdenados);

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
            jsonObject.put("eCuota", c.getCuota());
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
            jsonObject.put("contraseña", a.getContraseña());
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
            jsonObject.put("eTurno", as.getTurno());
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray serializarSetAsistentes(HashSet<Asistente> setA){
        JSONArray jsonSet = null;
        try {
            jsonSet = new JSONArray();
            for (Asistente as : setA){
                jsonSet.put(serializarAsistente(as));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonSet;
    }

    public JSONArray serializarTreeMapClientes(TreeMap<String, Cliente> treeC){
        JSONArray jsonTree = null;
        try {
            jsonTree = new JSONArray();
            for (String key : treeC.keySet()){
                Cliente c = treeC.get(key);
                jsonTree.put(serializarCliente(c));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonTree;
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
            c.setCuota(eCuota.valueOf(jsonObject.getString("eCuota").toUpperCase()));

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

            a.setUsuario(jsonObject.getString("usuario"));
            a.setContraseña(jsonObject.getString("contraseña"));

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

            a.setTurno(eTurno.valueOf(jsonObject.getString("eTurno").toUpperCase()));

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

    public TreeMap<String, Cliente> deserialiarTreeMapClientes(JSONArray jsonArray){
        TreeMap<String, Cliente> t = new TreeMap<>();
        try{
            for (int i = 0; i < jsonArray.length(); i++){
                Cliente c = deserializarCliente(jsonArray.getJSONObject(i));
                String key = c.getDni();
                t.put(key, c);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return t;
    }
}










