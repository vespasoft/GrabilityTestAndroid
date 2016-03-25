package com.grability.myappstore.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vespa on 14/02/2016.
 */
public class databaseHelper extends SQLiteOpenHelper {
    //nombre de la base de datos
    private static final String __DATABASE = "database_appstore";
    //versi�n de la base de datos
    private static final int __VERSION = 2;
    // table name
    public final String TABLE_AUTHOR = "t_author";
    public final String TABLE_CATEGORY = "t_category";
    public final String TABLE_IMAGE = "t_image";
    public final String TABLE_ENTRY = "t_entry";

    // Author Table Columns names
    public final String KEY_AUTHOR_CODE = "code";
    public final String KEY_AUTHOR_ID = "id";
    public final String KEY_AUTHOR_NAME = "name";
    public final String KEY_AUTHOR_URI = "uri";
    // Category Table Columns names
    public final String KEY_CATEGORY_ID = "id";
    public final String KEY_CATEGORY_TERM = "term";
    public final String KEY_CATEGORY_SCHEME = "scheme";
    public final String KEY_CATEGORY_LABEL = "label";
    // Image Table Columns names
    public final String KEY_IMAGE_ID = "id";
    public final String KEY_IMAGE_ENTRYID = "entryid";
    public final String KEY_IMAGE_LABEL = "label";
    public final String KEY_IMAGE_HEIGHT = "height";
    // Entry Table Columns names
    public final String KEY_ENTRY_ID = "id";
    public final String KEY_ENTRY_BUNDLEID = "bundleId";
    public final String KEY_ENTRY_LABEL = "label";
    public final String KEY_ENTRY_NAME = "imname";
    public final String KEY_ENTRY_TITLE = "title";
    public final String KEY_ENTRY_RIGHTS = "rights";
    public final String KEY_ENTRY_ARTIST = "artist";
    public final String KEY_ENTRY_ARTISTHREF = "artist_href";
    public final String KEY_ENTRY_TYPE = "contenttype";
    public final String KEY_ENTRY_LABELPRICE = "labelprice";
    public final String KEY_ENTRY_AMOUNT = "amount";
    public final String KEY_ENTRY_CURRENCY = "currency";
    public final String KEY_ENTRY_LINK_REL = "link_rel";
    public final String KEY_ENTRY_LINKTYPE = "link_type";
    public final String KEY_ENTRY_HREF = "linl_href";
    public final String KEY_ENTRY_DATE = "releaseDate";
    public final String KEY_ENTRY_AUTHORID = "authorid";
    public final String KEY_ENTRY_CATEGORYID = "categoryid";

    // Clase Helper, almacena todas la variables y constantes referentes
    /* a la tables Purchase de sqlite.
    /* La clase Helper tambien tiene la funcion de CREAR y ELIMINAR la Tabla Purchase...
    **/
    private final String sql_author = "CREATE TABLE " + TABLE_AUTHOR + " ( "
            + KEY_AUTHOR_CODE + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + KEY_AUTHOR_ID + " TEXT,"
            + KEY_AUTHOR_NAME + " TEXT,"
            + KEY_AUTHOR_URI + " TEXT )";

    private final String sql_category = "CREATE TABLE " + TABLE_CATEGORY + " ( "
            + KEY_CATEGORY_ID + " TEXT PRIMARY KEY NOT NULL, "
            + KEY_CATEGORY_TERM + " TEXT,"
            + KEY_CATEGORY_SCHEME + " TEXT,"
            + KEY_CATEGORY_LABEL + " TEXT )";

    private final String sql_image = "CREATE TABLE " + TABLE_IMAGE + " ( "
            + KEY_IMAGE_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + KEY_IMAGE_ENTRYID + " TEXT,"
            + KEY_IMAGE_LABEL + " TEXT,"
            + KEY_IMAGE_HEIGHT + " INTEGER )";

    private final String sql_entry = "CREATE TABLE " + TABLE_ENTRY + " ( "
            + KEY_ENTRY_ID+ " TEXT PRIMARY KEY NOT NULL, "
            + KEY_ENTRY_BUNDLEID + " TEXT,"
            + KEY_ENTRY_LABEL + " TEXT,"
            + KEY_ENTRY_NAME + " TEXT,"
            + KEY_ENTRY_TITLE + " TEXT,"
            + KEY_ENTRY_RIGHTS + " TEXT,"
            + KEY_ENTRY_ARTIST + " TEXT,"
            + KEY_ENTRY_ARTISTHREF + " TEXT,"
            + KEY_ENTRY_TYPE + " TEXT,"
            + KEY_ENTRY_LABELPRICE + " TEXT,"
            + KEY_ENTRY_AMOUNT + " DOUBLE,"
            + KEY_ENTRY_CURRENCY + " TEXT,"
            + KEY_ENTRY_LINK_REL + " TEXT,"
            + KEY_ENTRY_LINKTYPE + " TEXT,"
            + KEY_ENTRY_HREF + " TEXT,"
            + KEY_ENTRY_DATE + " TEXT,"
            + KEY_ENTRY_AUTHORID + " INTEGER,"
            + KEY_ENTRY_CATEGORYID + " TEXT )";
    /**
     * Constructor de clase
     * */
    public databaseHelper(Context context) {
        super( context, __DATABASE, null, __VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL( sql_author );
        db.execSQL( sql_category );
        db.execSQL( sql_image );
        db.execSQL( sql_entry );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db,  int oldVersion, int newVersion ) {
        if ( newVersion > oldVersion )
        {
            //elimina tabla
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_AUTHOR );
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_CATEGORY );
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_IMAGE );
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_ENTRY );
            //y luego creamos la nueva tabla
            db.execSQL( sql_author );
            db.execSQL( sql_category );
            db.execSQL( sql_image );
            db.execSQL( sql_entry );
        }
    }

}
