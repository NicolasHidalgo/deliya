package db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.UnsupportedEncodingException;

public abstract class DatabaseManager {

    private DbHelper helper;
    private SQLiteDatabase db;

    public DatabaseManager(Context ctx){
        helper = new DbHelper(ctx);
        db = helper.getWritableDatabase();
    }

    public void cerrar(){
        db.close();
    }

    abstract public void eliminar(String id);
    abstract public void eliminarTodo();
    abstract public Cursor cargar();
    abstract public Cursor cargarById(String id);
    abstract public Boolean compruebaRegistro(String id);
    abstract public Boolean verificarRegistros();
    abstract public Object get(String id) throws UnsupportedEncodingException;

    public DbHelper getHelper(){
        return  helper;
    }
    public void setHelper(DbHelper helper){
        this.helper = helper;
    }
    public SQLiteDatabase getDb(){
        return db;
    }
    public void setDb(SQLiteDatabase db){
        this.db = db;
    }

}
