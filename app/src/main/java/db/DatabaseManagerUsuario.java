package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import beans.SpinnerBean;
import beans.UsuarioBean;

public class DatabaseManagerUsuario extends DatabaseManager {

    public static final String NOMBRE_TABLA = "USUARIO";
    public static final String CN_ID = "_ID";
    public static final String CN_NOMBRES = "NOMBRES";
    public static final String CN_APELLIDOS = "APELLIDOS";
    public static final String CN_CORREO = "CORREO";
    public static final String CN_TELEFONO = "TELEFONO";
    public static final String CN_TYPE_CODE = "TYPE_CODE";
    public static final String CN_IAT = "IAT";
    public static final String CN_EXP = "EXP";

    public static final String CREATE_TABLE =  "create table " + NOMBRE_TABLA + " ("
            + CN_ID + " integer PRIMARY KEY,"
            + CN_NOMBRES + " text NULL,"
            + CN_APELLIDOS + " text NULL,"
            + CN_CORREO + " text NULL,"
            + CN_TELEFONO + " text NULL,"
            + CN_TYPE_CODE + " text NULL,"
            + CN_IAT + " text NULL,"
            + CN_EXP + " text NULL"
            + ");";

    public DatabaseManagerUsuario(Context ctx) {
        super(ctx);
    }
    @Override
    public void cerrar(){
        super.getDb().close();
    }

    private ContentValues generarContentValues(UsuarioBean obj){
        ContentValues valores = new ContentValues();
        valores.put(CN_ID,obj.getID());
        valores.put(CN_NOMBRES,obj.getNOMBRES());
        valores.put(CN_APELLIDOS,obj.getAPELLIDOS());
        valores.put(CN_CORREO,obj.getCORREO());
        valores.put(CN_TELEFONO,obj.getTELEFONO());
        valores.put(CN_TYPE_CODE,obj.getTYPE_CODE());
        valores.put(CN_IAT,obj.getIAT());
        valores.put(CN_EXP,obj.getEXP());
        return valores;
    }

    public void insertar(UsuarioBean obj) {
        //super.getDb().execSQL("INSERT INTO ...");
        Log.d(NOMBRE_TABLA + "_insertar",super.getDb().insert(NOMBRE_TABLA,null,generarContentValues(obj))+"");
    }

    public void actualizar(UsuarioBean obj) {
        ContentValues valores = generarContentValues(obj);
        String [] args = new String[]{obj.getID()};
        Log.d(NOMBRE_TABLA + "_actualizar",super.getDb().update(NOMBRE_TABLA,valores, CN_ID+ "=?", args)+"");
    }

    @Override
    public void eliminar(String id) {
        super.getDb().delete(NOMBRE_TABLA,CN_ID+ "=?", new String[]{id});
    }

    @Override
    public void eliminarTodo() {
        super.getDb().execSQL("DELETE FROM " + NOMBRE_TABLA + ";");
        Log.d(NOMBRE_TABLA + "_eliminados","Datos borrados");
    }
    /*public void EliminarRegistro(String id){
        String sql = "UPDATE " + NOMBRE_TABLA + " SET " +
                CN_ESTADO + " = 0, " +
                "WHERE " + CN_ID + " = " + id;
        super.getDb().execSQL(sql);
    }*/
    @Override
    public Cursor cargar() {
        String [] columnas = new String[]
                {CN_ID,CN_NOMBRES,CN_APELLIDOS,CN_CORREO,CN_TELEFONO,CN_TYPE_CODE,CN_IAT,CN_EXP};
        return super.getDb().query(NOMBRE_TABLA, columnas,null,null,null,null,null);
    }

    @Override
    public Cursor cargarById(String id) {
        String [] columnas = new String[]
                {CN_ID,CN_NOMBRES,CN_APELLIDOS,CN_CORREO,CN_TELEFONO,CN_TYPE_CODE,CN_IAT,CN_EXP};
        return super.getDb().query(NOMBRE_TABLA, columnas,CN_ID + "=?", new String[]{id},null,null,null);
    }

    @Override
    public Boolean compruebaRegistro(String id) {
        boolean existe = true;
        Cursor resultSet = super.getDb().rawQuery("Select * from " + NOMBRE_TABLA + " WHERE " + CN_ID + " =" + id, null);

        if (resultSet.getCount() <= 0)
            existe = false;
        else
            existe = true;

        return existe;
    }

    /*public Boolean verificarPorUsuarioPassword(String usuario, String password) {
        boolean existe = true;
        Cursor resultSet = super.getDb().rawQuery("Select * from " + NOMBRE_TABLA + " WHERE " + CN_USUARIO + " =" + usuario + " AND " + CN_CONTRASENA + " =" + password, null);

        if (resultSet.getCount() <= 0)
            existe = false;
        else
            existe = true;

        return existe;
    }*/

    @Override
    public Boolean verificarRegistros() {
        boolean existe = true;
        Cursor resultSet = super.getDb().rawQuery("Select * from " + NOMBRE_TABLA, null);

        if (resultSet.getCount() <= 0)
            existe = false;
        else
            existe = true;

        return existe;
    }


    public Boolean verificarRegistroPorID(String Id) {
        boolean existe = true;
        Cursor resultSet = super.getDb().rawQuery("Select * from " + NOMBRE_TABLA + " WHERE " + CN_ID + " = " + Id, null);

        if (resultSet.getCount() > 0)
            existe = true;
        else
            existe = false;

        return existe;
    }

    @Override
    public UsuarioBean get(String id){
        UsuarioBean bean = null;
        Cursor c = cargarById(id);

        while (c.moveToNext()){
            bean = new UsuarioBean();
            bean.setID(c.getString(0));
            bean.setNOMBRES(c.getString(1));
            bean.setAPELLIDOS(c.getString(2));
            bean.setCORREO(c.getString(3));
            bean.setTELEFONO(c.getString(4));
            bean.setTYPE_CODE(c.getString(5));
            bean.setIAT(c.getString(6));
            bean.setEXP(c.getString(7));
        }
        return bean;
    }

    public List<UsuarioBean> getList(String tipo){
        List<UsuarioBean> list = new ArrayList<>();
        Cursor c = null;
        c = cargar();

        while (c.moveToNext()){
            UsuarioBean bean = new UsuarioBean();
            bean.setID(c.getString(0));
            bean.setNOMBRES(c.getString(1));
            bean.setAPELLIDOS(c.getString(2));
            bean.setCORREO(c.getString(3));
            bean.setTELEFONO(c.getString(4));
            bean.setTYPE_CODE(c.getString(5));
            bean.setIAT(c.getString(6));
            bean.setEXP(c.getString(7));
            list.add(bean);
        }
        return list;
    }


    public List<SpinnerBean> getSpinner(String tipo){
        List<SpinnerBean> list = new ArrayList<>();
        Cursor c = cargar();

        while (c.moveToNext()){
            SpinnerBean bean = new SpinnerBean(c.getInt(0),c.getString(2));
            list.add(bean);
        }
        return list;
    }
}
