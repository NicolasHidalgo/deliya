package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NOMBRE = "deliya.sqlite";
    private static int DB_SCHEME_VERSION = 1;

    public DbHelper(Context context){ //, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NOMBRE, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DatabaseManagerUsuario.CREATE_TABLE);

        }catch(Exception e){
            e.getMessage();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManagerUsuario.NOMBRE_TABLA);
        onCreate(db);
    }
}
