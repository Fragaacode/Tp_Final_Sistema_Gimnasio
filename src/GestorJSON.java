import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public class GestorJSON {

    public GestorJSON(){}

    //////////// SERIALIZACION / DESERIALIZACION COMPLETA DEL GIMNASIO ////////////

    public void sobreescribirGimnasioAarchivo(String nombreArchivo, GestionGimnasio gym){
        LecturaEscrituraDeArchivo.sobreEscribir(serializarGimnasio(gym), nombreArchivo);
    }

    public GestionGimnasio ArchivoAGimnasio(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("Archivo no encontrado: se creará uno nuevo.");
            return null;
        }

        try {
            JSONTokener tokener = LecturaEscrituraDeArchivo.leer(nombreArchivo);
            if (tokener == null) return null;
            return deserializarGimnasio(new JSONObject(tokener));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject serializarGimnasio(GestionGimnasio gimnasio){
        JSONObject gym = new JSONObject();
        try {
            gym.put("clientes", serializarListaClientes(gimnasio.getClientes()));
            gym.put("administradores", serializarMapAdministrador(gimnasio.getAdministradores()));
            gym.put("asistentes", serializarSetAsistentes(gimnasio.getAsistentes()));
            gym.put("clientesOrdenadosPorNombre", serializarTreeMapClientes(gimnasio.getClientesOrdenadosPorNombre()));
            gym.put("profesores", serializarSetProfesores(gimnasio.getProfesores()));
            gym.put("mantenimientos", serializarSetMantenimiento(gimnasio.getMantenimientos()));
        } catch (JSONException e){
            e.printStackTrace();
        }
        return gym;
    }

    public GestionGimnasio deserializarGimnasio(JSONObject jsonObject){
        GestionGimnasio gimnasio = new GestionGimnasio();

        try {
            // CLIENTES
            JSONArray clientesJSON = jsonObject.getJSONArray("clientes");
            gimnasio.setClientes(deserializarListaClientes(clientesJSON));

            // ADMINISTRADORES
            if (jsonObject.has("administradores") && jsonObject.get("administradores") instanceof JSONObject) {
                JSONObject adminJSON = jsonObject.getJSONObject("administradores");
                gimnasio.setAdministradores(deserializarMapAdministrador(adminJSON));
            } else {
                gimnasio.setAdministradores(new HashMap<>());
            }

            // ASISTENTES
            JSONArray asistentesJSON = jsonObject.getJSONArray("asistentes");
            gimnasio.setAsistentes(deserializarSetAsistente(asistentesJSON));

            // TREE CLIENTES
            JSONArray clientesOrdenadosJSON = jsonObject.getJSONArray("clientesOrdenadosPorNombre");
            gimnasio.setClientesOrdenadosPorNombre(deserializarTreeMapClientes(clientesOrdenadosJSON));

            // PROFESORES
            JSONArray profesoresJSON = jsonObject.getJSONArray("profesores");
            gimnasio.setProfesores(deserializarSetProfesores(profesoresJSON));

            // MANTENIMIENTO
            JSONArray mantenimientoJSON = jsonObject.getJSONArray("mantenimientos");
            gimnasio.setMantenimientos(deserializarSetMantenimiento(mantenimientoJSON));

        } catch (JSONException e){
            e.printStackTrace();
        }
        return gimnasio;
    }

    //////////// SERIALIZACION / DESERIALIZACION DE PERSONAS ////////////

    public JSONObject serializarPersona(Persona p){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nombre", p.getNombre());
            jsonObject.put("edad", p.getEdad());
            jsonObject.put("dni", p.getDni());
            jsonObject.put("id", p.getId());
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void auxDeserializarPersona(Persona p, JSONObject jObject){
        try{
            if (jObject.has("id")) p.setId(jObject.getInt("id"));
            if (jObject.has("nombre")) p.setNombre(jObject.getString("nombre"));
            if (jObject.has("edad")) p.setEdad(jObject.getInt("edad"));
            if (jObject.has("dni")) p.setDni(jObject.getString("dni"));
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    //////////// CLIENTES ////////////

    public JSONObject serializarCliente(Cliente c){
        JSONObject jsonObject = serializarPersona(c);
        try {
            jsonObject.put("saldo", c.getSaldo());
            jsonObject.put("eCuota", c.geteCuota());
            jsonObject.put("activo", c.isActivo());
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray serializarListaClientes(ArrayList<Cliente> listaClientes){
        JSONArray jsonArray = new JSONArray();
        for (Cliente c : listaClientes){
            jsonArray.put(serializarCliente(c));
        }
        return jsonArray;
    }

    public Cliente deserializarCliente(JSONObject jsonObject){
        Cliente c = new Cliente();
        auxDeserializarPersona(c, jsonObject);
        try {
            c.setSaldo(jsonObject.getDouble("saldo"));
            c.seteCuota(eCuota.valueOf(jsonObject.getString("eCuota").toUpperCase()));
            c.setActivo(jsonObject.getBoolean("activo"));
        } catch (JSONException e){
            e.printStackTrace();
        }
        return c;
    }

    public ArrayList<Cliente> deserializarListaClientes(JSONArray jsonArray){
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            try {
                listaClientes.add(deserializarCliente(jsonArray.getJSONObject(i)));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return listaClientes;
    }

    public JSONArray serializarTreeMapClientes(TreeMap<String, Cliente> treeC){
        JSONArray jsonTree = new JSONArray();
        for (Cliente c : treeC.values()){
            jsonTree.put(serializarCliente(c));
        }
        return jsonTree;
    }

    public TreeMap<String, Cliente> deserializarTreeMapClientes(JSONArray jsonArray){
        TreeMap<String, Cliente> t = new TreeMap<>();
        for (int i = 0; i < jsonArray.length(); i++){
            try {
                Cliente c = deserializarCliente(jsonArray.getJSONObject(i));
                t.put(c.getDni(), c);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return t;
    }

    //////////// ADMINISTRADORES ////////////

    public JSONObject serializarAdministrador(Administrador a){
        JSONObject jsonObject = serializarPersona(a);
        try {
            jsonObject.put("usuario", a.getUsuario());
            jsonObject.put("contraseña", a.getContraseña());
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject serializarMapAdministrador(HashMap<String, Administrador> ad){
        JSONObject jsonMap = new JSONObject();
        for (String key : ad.keySet()){
            jsonMap.put(key, serializarAdministrador(ad.get(key)));
        }
        return jsonMap;
    }

    public Administrador deserializarAdministrador(JSONObject jsonObject){
        Administrador a = new Administrador();
        auxDeserializarPersona(a, jsonObject);
        try {
            a.setUsuario(jsonObject.getString("usuario"));
            a.setContraseña(jsonObject.getString("contraseña"));
        } catch (JSONException e){
            e.printStackTrace();
        }
        return a;
    }

    public HashMap<String, Administrador> deserializarMapAdministrador(JSONObject jsonObject){
        HashMap<String, Administrador> map = new HashMap<>();
        for (String key : jsonObject.keySet()){
            try {
                map.put(key, deserializarAdministrador(jsonObject.getJSONObject(key)));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return map;
    }

    //////////// ASISTENTES ////////////

    public JSONObject serializarAsistente(Asistente as){
        JSONObject jsonObject = serializarPersona(as);
        try {
            jsonObject.put("eTurno", as.geteTurno());
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray serializarSetAsistentes(HashSet<Asistente> setA){
        JSONArray jsonSet = new JSONArray();
        for (Asistente as : setA){
            jsonSet.put(serializarAsistente(as));
        }
        return jsonSet;
    }

    public Asistente deserializarAsistente(JSONObject jsonObject){
        Asistente a = new Asistente();
        auxDeserializarPersona(a, jsonObject);
        try {
            a.seteTurno(eTurno.valueOf(jsonObject.getString("eTurno").toUpperCase()));
        } catch (JSONException e){
            e.printStackTrace();
        }
        return a;
    }

    public HashSet<Asistente> deserializarSetAsistente(JSONArray jsonArray){
        HashSet<Asistente> a = new HashSet<>();
        for (int i = 0; i < jsonArray.length(); i++){
            try {
                a.add(deserializarAsistente(jsonArray.getJSONObject(i)));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return a;
    }

    //////////// PROFESORES ////////////

    public JSONObject serializarProfesor(Profesor profesor){
        JSONObject jsonObject = serializarPersona(profesor);
        try {
            jsonObject.put("especialidad", profesor.getEspecialidad());
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray serializarSetProfesores(HashSet<Profesor> setP){
        JSONArray jsonSet = new JSONArray();
        for (Profesor p : setP){
            jsonSet.put(serializarProfesor(p));
        }
        return jsonSet;
    }

    public Profesor deserializarProfesor(JSONObject jsonObject){
        Profesor p = new Profesor();
        auxDeserializarPersona(p, jsonObject);
        try {
            p.setEspecialidad(jsonObject.getString("especialidad"));
        } catch (JSONException e){
            e.printStackTrace();
        }
        return p;
    }

    public HashSet<Profesor> deserializarSetProfesores(JSONArray jsonArray){
        HashSet<Profesor> p = new HashSet<>();
        for (int i = 0; i < jsonArray.length(); i++){
            try {
                p.add(deserializarProfesor(jsonArray.getJSONObject(i)));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return p;
    }

    //////////// mantenimiento ////////////

    public JSONObject serializarMantenimiento(mantenimiento mant){
        JSONObject jsonObject = serializarPersona(mant);
        try {
            jsonObject.put("sectorAsignado", mant.getSectorAsignado());
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray serializarSetMantenimiento(HashSet<mantenimiento> setM){
        JSONArray jsonSet = new JSONArray();
        for (mantenimiento m : setM){
            jsonSet.put(serializarMantenimiento(m));
        }
        return jsonSet;
    }

    public mantenimiento deserializarMantenimiento(JSONObject jsonObject){
        mantenimiento m = new mantenimiento();
        auxDeserializarPersona(m, jsonObject);
        try {
            m.setSectorAsignado(jsonObject.getString("sectorAsignado"));
        } catch (JSONException e){
            e.printStackTrace();
        }
        return m;
    }

    public HashSet<mantenimiento> deserializarSetMantenimiento(JSONArray jsonArray){
        HashSet<mantenimiento> m = new HashSet<>();
        for (int i = 0; i < jsonArray.length(); i++){
            try {
                m.add(deserializarMantenimiento(jsonArray.getJSONObject(i)));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return m;
    }

    //////////// MÉTODOS AUXILIARES PARA AGREGAR Y GUARDAR DIRECTAMENTE ////////////

    public void agregarClienteYGuardar(String nombreArchivo, Cliente c){
        GestionGimnasio gym = ArchivoAGimnasio(nombreArchivo);
        if (gym == null) gym = new GestionGimnasio();
        gym.agregarCliente(c);
        sobreescribirGimnasioAarchivo(nombreArchivo, gym);
    }

    public void agregarAdministradorYGuardar(String nombreArchivo, Administrador a){
        GestionGimnasio gym = ArchivoAGimnasio(nombreArchivo);
        if (gym == null) gym = new GestionGimnasio();
        gym.agregarAdministrador(a);
        sobreescribirGimnasioAarchivo(nombreArchivo, gym);
    }

    public void agregarAsistenteYGuardar(String nombreArchivo, Asistente as){
        GestionGimnasio gym = ArchivoAGimnasio(nombreArchivo);
        if (gym == null) gym = new GestionGimnasio();
        gym.agregarAsistente(as);
        sobreescribirGimnasioAarchivo(nombreArchivo, gym);
    }

    public void agregarProfesorYGuardar(String nombreArchivo, Profesor pr){
        GestionGimnasio gym = ArchivoAGimnasio(nombreArchivo);
        if (gym == null) gym = new GestionGimnasio();
        gym.agregarProfesor(pr);
        sobreescribirGimnasioAarchivo(nombreArchivo, gym);
    }

    public void agregarMantenimientoYGuardar(String nombreArchivo, mantenimiento m){
        GestionGimnasio gym = ArchivoAGimnasio(nombreArchivo);
        if (gym == null) gym = new GestionGimnasio();
        gym.agregarMantenimiento(m);
        sobreescribirGimnasioAarchivo(nombreArchivo, gym);
    }


}











