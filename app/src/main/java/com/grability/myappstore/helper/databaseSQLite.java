package com.grability.myappstore.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.grability.myappstore.model.author;
import com.grability.myappstore.model.category;
import com.grability.myappstore.model.entry;
import com.grability.myappstore.model.image;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vespa on 14/02/2016.
 */

public class databaseSQLite {
    private databaseHelper sqliteHelper;
    private SQLiteDatabase db;

    /** Constructor de clase */
    public databaseSQLite(Context context)
    {
        sqliteHelper = new databaseHelper( context );
    }

    /** Abre conexion a base de datos */
    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos DB_SE_Purchase"   );
        db = sqliteHelper.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos DB_SE_Purchase" );
        sqliteHelper.close();
    }

    /**
     * Metodo para agregar un nuevo registro
     * @param dto
     * @return BOOLEAN TRUE si tuvo exito FALSE caso contrario
     * */
    public boolean addAuthor(author dto) {
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();

        if( dto.getName().length() > 0 )
        {
            ContentValues values = new ContentValues();
            values.put(sqliteHelper.KEY_AUTHOR_ID, dto.getId()); // full description
            values.put(sqliteHelper.KEY_AUTHOR_NAME, dto.getName()); // full description
            values.put(sqliteHelper.KEY_AUTHOR_URI, dto.getUri());       // Category name
            Log.i(sqliteHelper.TABLE_AUTHOR, "Nuevo author " );
            return ( db.insert( sqliteHelper.TABLE_AUTHOR , null, values ) != -1 )?true:false;
        }
        else
            return false;

    }

    /**
     * Metodo para editar un registro existente
     * @param dto
     * @return BOOLEAN TRUE si tuvo exito FALSE caso contrario
     * */
    public boolean updateAuthor(author dto)
    {
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();
        if( dto.getName().length() > 0 )
        {
            ContentValues values = new ContentValues();
            values.put(sqliteHelper.KEY_AUTHOR_ID, dto.getId()); // full description
            values.put(sqliteHelper.KEY_AUTHOR_NAME, dto.getName());        // Name game
            values.put(sqliteHelper.KEY_AUTHOR_URI, dto.getUri());          // full description
            Log.i(sqliteHelper.TABLE_AUTHOR, "Editar registro " );
            return db.update(sqliteHelper.TABLE_AUTHOR, values, sqliteHelper.KEY_AUTHOR_CODE + "=" + dto.getCode(), null) > 0;
        }
        else
            return false;
    }

    /**
     * @param id del registro a eliminar
     * @return BOOLEAN
     * */
    public boolean deleteAuthor( int id )
    {
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();
        // table , whereClause, whereArgs
        return  (db.delete( sqliteHelper.TABLE_AUTHOR, sqliteHelper.KEY_AUTHOR_CODE + " = " + id ,  null) > 0) ? true:false;
    }
    /**
     * @return BOOLEAN
     * */
    public boolean deleteAllAuthor()
    {
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();
        // table , whereClause, whereArgs
        return  (db.delete( sqliteHelper.TABLE_AUTHOR, sqliteHelper.KEY_AUTHOR_CODE + " <> ''",  null) > 0) ? true:false;
    }

    /**
     *  Obtiene un registro
     * */
    public author getAuthor(int id)
    {
        author dto = null;
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();

        // 2. build query
        Cursor cursor = db.query( sqliteHelper.TABLE_AUTHOR,
                new String[]{
                        sqliteHelper.KEY_AUTHOR_CODE,
                        sqliteHelper.KEY_AUTHOR_ID,
                        sqliteHelper.KEY_AUTHOR_NAME,
                        sqliteHelper.KEY_AUTHOR_URI}, sqliteHelper.KEY_AUTHOR_CODE + " = " + id,
                null, null, null, null);
        // 3. Move to first row
        cursor.moveToFirst();
        Log.i("SQLite", " author ID: " + id);
        if(cursor.getCount() > 0){
            dto = new author(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3));
        }
        cursor.close();
        db.close();
        // return user
        return dto;
    }

    /*
    *   REGISTRO DE CATEGORIAS
    * */
    /**
     * Metodo para agregar un nuevo registro
     * @param dto
     * @return BOOLEAN TRUE si tuvo exito FALSE caso contrario
     * */
    public boolean addCategory(category dto) {
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();

        if( dto.getLabel().length() > 0 )
        {
            ContentValues values = new ContentValues();
            values.put(sqliteHelper.KEY_CATEGORY_ID, dto.getId());               // Name game
            values.put(sqliteHelper.KEY_CATEGORY_LABEL, dto.getLabel()); // full description
            values.put(sqliteHelper.KEY_CATEGORY_SCHEME, dto.getScheme());       // Category name
            values.put(sqliteHelper.KEY_CATEGORY_TERM, dto.getScheme());       // Category name
            Log.i(sqliteHelper.TABLE_CATEGORY, "Nuevo category " );
            return ( db.insert( sqliteHelper.TABLE_CATEGORY , null, values ) != -1 )?true:false;
        }
        else
            return false;
    }

    /* Este método verifica que el items agregar no este en la lista del cart
    *  en caso de estar en la lita, agregar al items en la misma linea
    *  incrementando solo el campo quantity().
    * */
    public boolean addNewCategory(category dto) {
        category result;
        result = getCategory(dto.getId());
        if ( result == null ) {
            // Agrega el items en el carro de compras
            return ( addCategory(dto) );
        } else {
            // De lo contrario, se actualiza los datos del usuario
            return ( updateCategory(dto) );
        }

    }

    /**
     * Metodo para editar un registro existente
     * @param dto
     * @return BOOLEAN TRUE si tuvo exito FALSE caso contrario
     * */
    public boolean updateCategory(category dto)
    {
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();
        if( dto.getLabel().length() > 0 )
        {
            ContentValues values = new ContentValues();
            values.put(sqliteHelper.KEY_CATEGORY_LABEL, dto.getLabel());        // Name game
            values.put(sqliteHelper.KEY_CATEGORY_TERM, dto.getScheme());          // full description
            values.put(sqliteHelper.KEY_CATEGORY_SCHEME, dto.getTerm());            // Category name
            Log.i(sqliteHelper.TABLE_AUTHOR, "Editar registro " );
            return db.update(sqliteHelper.TABLE_CATEGORY, values, sqliteHelper.KEY_CATEGORY_ID + "='" + dto.getId()+"'", null) > 0;
        }
        else
            return false;
    }

    /**
     * @param id del registro a eliminar
     * @return BOOLEAN
     * */
    public boolean deleteCategory( String id )
    {
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();
        // table , whereClause, whereArgs
        return  (db.delete( sqliteHelper.TABLE_AUTHOR, sqliteHelper.KEY_CATEGORY_ID + " = '" + id +"'",  null) > 0) ? true:false;
    }
    /**
     * @return BOOLEAN
     * */
    public boolean deleteAllCategory()
    {
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();
        // table , whereClause, whereArgs
        return  (db.delete( sqliteHelper.TABLE_CATEGORY, sqliteHelper.KEY_CATEGORY_ID + " <> '' ",  null) > 0) ? true:false;
    }

    /**
     * Obtiene un registro
     * */
    public category getCategory(String id)
    {
        category dto = null;
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();

        // 2. build query
        Cursor cursor = db.query( sqliteHelper.TABLE_CATEGORY,
                new String[]{
                        sqliteHelper.KEY_CATEGORY_ID,
                        sqliteHelper.KEY_CATEGORY_TERM,
                        sqliteHelper.KEY_CATEGORY_SCHEME,
                        sqliteHelper.KEY_CATEGORY_LABEL}, sqliteHelper.KEY_CATEGORY_ID + " = '" + id +"'",
                null, null, null, null);
        // 3. Move to first row
        cursor.moveToFirst();
        Log.i("SQLite", " category ID: " + id);
        if(cursor.getCount() > 0){
            dto = new category(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3));
        }
        cursor.close();
        db.close();
        // return user
        return dto;
    }

    /**
     * Obtiene todos los registros de la unica tabla de la base de datos
     * @return Cursor
     * */
    public List<category> getAllCategory()
    {
        List<category> list = new LinkedList<category>();
        category dto = null;
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();
        // 2. sql query
        String sql = "SELECT * FROM "+ sqliteHelper.TABLE_CATEGORY;

        Cursor cursor =  db.rawQuery(sql, null);
        Log.i("SQLite", " get all games");
        // 3. go over each row, build book and add it to list
        if (cursor.moveToFirst()) {
            do {
                dto = new category(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));
                // Add game to Array Games
                list.add(dto);
                Log.i(sqliteHelper.TABLE_CATEGORY, "List all category. name: "+cursor.getString(3) );
            } while (cursor.moveToNext());
        }
        // 4. close
        db.close();
        cursor.close();

        return list;
    }

    /*
    *   REGISTRO DE IMAGENES POR ENTRY **********************************************************************
    * */
    /**
     * Metodo para agregar un nuevo registro
     * @param dto
     * @return BOOLEAN TRUE si tuvo exito FALSE caso contrario
     * */
    public boolean addImage(image dto) {
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();

        if( dto.getLabel().length() > 0 )
        {
            ContentValues values = new ContentValues();
            values.put(sqliteHelper.KEY_IMAGE_ENTRYID, dto.getEntryid());       // full description
            values.put(sqliteHelper.KEY_IMAGE_LABEL, dto.getLabel());           // Category name
            values.put(sqliteHelper.KEY_IMAGE_HEIGHT, dto.getHeight());         // Category name
            Log.i(sqliteHelper.TABLE_IMAGE, "Nueva image Entryid:"+dto.getEntryid()+" URL:"+dto.getLabel() );
            return ( db.insert( sqliteHelper.TABLE_IMAGE , null, values ) != -1 )?true:false;

        }
        else
            return false;

    }

    /* Este método verifica que el items agregar no este en la lista del cart
    *  en caso de estar en la lita, agregar al items en la misma linea
    *  incrementando solo el campo quantity().
    * */
    public boolean addNewImage(image dto) {
        image result;
        result = getImage(dto.getId());
        if ( result == null ) {
            // Agrega el items en el carro de compras
            return ( addImage(dto) );
        } else {
            // De lo contrario, se actualiza los datos del usuario
            return ( updateImage(dto) );
        }

    }

    /**
     * Metodo para editar un registro existente
     * @param dto
     * @return BOOLEAN TRUE si tuvo exito FALSE caso contrario
     * */
    public boolean updateImage(image dto)
    {
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();
        if( dto.getLabel().length() > 0 )
        {
            ContentValues values = new ContentValues();
            values.put(sqliteHelper.KEY_IMAGE_ENTRYID, dto.getEntryid());       // Name game
            values.put(sqliteHelper.KEY_IMAGE_LABEL, dto.getLabel());           // full description
            values.put(sqliteHelper.KEY_IMAGE_HEIGHT, dto.getHeight());         // Category name
            Log.i(sqliteHelper.TABLE_IMAGE, "Update image Entryid:"+dto.getEntryid()+" URL:"+dto.getLabel() );
            return db.update(sqliteHelper.TABLE_IMAGE, values, sqliteHelper.KEY_IMAGE_ID + "=" + dto.getId(), null) > 0;
        }
        else
            return false;
    }

    /**
     * @param id del registro a eliminar
     * @return BOOLEAN
     * */
    public boolean deleteImage( int id )
    {
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();
        // table , whereClause, whereArgs
        return  (db.delete( sqliteHelper.TABLE_IMAGE, sqliteHelper.KEY_IMAGE_ID+ " = " + id,  null) > 0) ? true:false;
    }
    /**
     * @return BOOLEAN
     * */
    public boolean deleteAllImage()
    {
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();
        // table , whereClause, whereArgs
        return  (db.delete( sqliteHelper.TABLE_AUTHOR, sqliteHelper.KEY_IMAGE_ID + " > " + 0,  null) > 0) ? true:false;
    }

    /**
     * Obtiene un registro
     * */
    public image getImage(int id)
    {
        image dto = null;
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();

        // 2. build query
        Cursor cursor = db.query( sqliteHelper.TABLE_IMAGE,
                new String[]{
                        sqliteHelper.KEY_IMAGE_ID,
                        sqliteHelper.KEY_IMAGE_ENTRYID,
                        sqliteHelper.KEY_IMAGE_LABEL,
                        sqliteHelper.KEY_IMAGE_HEIGHT}, sqliteHelper.KEY_IMAGE_ID + " = " + id ,
                null, null, null, null);
        // 3. Move to first row
        cursor.moveToFirst();
        Log.i("SQLite", " category ID: " + id);
        if(cursor.getCount() > 0){
            dto = new image(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3));
        }
        cursor.close();
        db.close();
        // return user
        return dto;
    }

    /**
     * Obtiene todos los registros de las imagenes relacionadas con un registro en la tabla Entry
     * @return Cursor
     * */
    public List<image> getAllImage(String entryid)
    {
        List<image> list = new LinkedList<image>();
        image dto = null;
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();
        // 2. sql query
        String sql = "SELECT * FROM "+ sqliteHelper.TABLE_IMAGE + " WHERE "+ sqliteHelper.KEY_IMAGE_ENTRYID + " = '" + entryid+"'";

        Cursor cursor =  db.rawQuery(sql, null);
        Log.i("SQLite", " get all images EntryId:"+entryid);
        // 3. go over each row, build book and add it to list
        if (cursor.moveToFirst()) {
            do {
                dto = new image(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3));
                // Add game to Array Games
                list.add(dto);
                Log.i(sqliteHelper.TABLE_IMAGE, "List all images: EntryID: "+cursor.getString(1)+" Height: "+cursor.getString(3) );
            } while (cursor.moveToNext());
        }
        // 4. close
        db.close();
        cursor.close();

        return list;
    }

    /*
    *   REGISTRO DE ENTRY TABLA PRINCIPAL   ******************************************************************
    * */
    /**
     * Metodo para agregar un nuevo registro
     * @param dto
     * @return BOOLEAN TRUE si tuvo exito FALSE caso contrario
     * */
    public boolean addEntry(entry dto) {
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();

        if( dto.getImname().length() > 0 )
        {
            ContentValues values = new ContentValues();
            values.put(sqliteHelper.KEY_ENTRY_ID, dto.getId());                 // Name game
            values.put(sqliteHelper.KEY_ENTRY_BUNDLEID, dto.getBundleId());        // full description
            values.put(sqliteHelper.KEY_ENTRY_LABEL, dto.getLabel());           // Category name
            values.put(sqliteHelper.KEY_ENTRY_NAME, dto.getImname());           // Category name
            values.put(sqliteHelper.KEY_ENTRY_TITLE, dto.getTitle());           // Category name
            values.put(sqliteHelper.KEY_ENTRY_RIGHTS, dto.getRights());          // full description
            values.put(sqliteHelper.KEY_ENTRY_ARTIST, dto.getArtist());          // Category name
            values.put(sqliteHelper.KEY_ENTRY_ARTISTHREF, dto.getArtist_href());     // Category name
            values.put(sqliteHelper.KEY_ENTRY_TYPE, dto.getContenttype());            // Category name
            values.put(sqliteHelper.KEY_ENTRY_LABELPRICE, dto.getLabelprice());      // Category name
            values.put(sqliteHelper.KEY_ENTRY_AMOUNT, dto.getAmount());          // full description
            values.put(sqliteHelper.KEY_ENTRY_CURRENCY, dto.getCurrency());        // Category name
            values.put(sqliteHelper.KEY_ENTRY_LINK_REL, dto.getLink_rel());       // Category name
            values.put(sqliteHelper.KEY_ENTRY_LINKTYPE, dto.getLink_type());        // Category name
            values.put(sqliteHelper.KEY_ENTRY_HREF, dto.getLinl_href());            // Category name
            values.put(sqliteHelper.KEY_ENTRY_DATE, dto.getReleaseDate());            // full description
            values.put(sqliteHelper.KEY_ENTRY_AUTHORID, dto.getAuthorid());        // Category name
            values.put(sqliteHelper.KEY_ENTRY_CATEGORYID, dto.getCategoryid());     // Category name
            Log.i(sqliteHelper.TABLE_ENTRY, "Nuevo registro entry: "+ dto.getImname()+" categoryId: "+dto.getCategoryid() );
            return ( db.insert( sqliteHelper.TABLE_ENTRY , null, values ) != -1 )?true:false;
        }
        else
            return false;

    }

    /* Este método verifica que el items agregar no este en la lista del cart
    *  en caso de estar en la lita, agregar al items en la misma linea
    *  incrementando solo el campo quantity().
    * */
    public boolean addNewEntry(entry dto) {
        entry result;
        result = getEntry(dto.getId());
        if ( result == null ) {
            // Agrega el items en el carro de compras
            return ( addEntry(dto) );
        } else {
            // De lo contrario, se actualiza los datos del usuario
            return (updateEntry(dto) );
        }

    }

    /**
     * Metodo para editar un registro existente
     * @param dto
     * @return BOOLEAN TRUE si tuvo exito FALSE caso contrario
     * */
    public boolean updateEntry(entry dto)
    {
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();
        if( dto.getImname().length() > 0 )
        {
            ContentValues values = new ContentValues();
            values.put(sqliteHelper.KEY_ENTRY_BUNDLEID, dto.getBundleId());        // full description
            values.put(sqliteHelper.KEY_ENTRY_LABEL, dto.getLabel());           // Category name
            values.put(sqliteHelper.KEY_ENTRY_NAME, dto.getImname());           // Category name
            values.put(sqliteHelper.KEY_ENTRY_TITLE, dto.getTitle());           // Category name
            values.put(sqliteHelper.KEY_ENTRY_RIGHTS, dto.getRights());          // full description
            values.put(sqliteHelper.KEY_ENTRY_ARTIST, dto.getArtist());          // Category name
            values.put(sqliteHelper.KEY_ENTRY_ARTISTHREF, dto.getArtist_href());     // Category name
            values.put(sqliteHelper.KEY_ENTRY_TYPE, dto.getContenttype());            // Category name
            values.put(sqliteHelper.KEY_ENTRY_LABELPRICE, dto.getLabelprice());      // Category name
            values.put(sqliteHelper.KEY_ENTRY_AMOUNT, dto.getAmount());          // full description
            values.put(sqliteHelper.KEY_ENTRY_CURRENCY, dto.getCurrency());        // Category name
            values.put(sqliteHelper.KEY_ENTRY_LINK_REL, dto.getLink_rel());       // Category name
            values.put(sqliteHelper.KEY_ENTRY_LINKTYPE, dto.getLink_type());        // Category name
            values.put(sqliteHelper.KEY_ENTRY_HREF, dto.getLinl_href());            // Category name
            values.put(sqliteHelper.KEY_ENTRY_DATE, dto.getReleaseDate());            // full description
            values.put(sqliteHelper.KEY_ENTRY_AUTHORID, dto.getAuthorid());        // Category name
            values.put(sqliteHelper.KEY_ENTRY_CATEGORYID, dto.getCategoryid());     // Category name
            Log.i(sqliteHelper.TABLE_ENTRY, "Editar registro " );
            return db.update(sqliteHelper.TABLE_ENTRY, values, sqliteHelper.KEY_ENTRY_ID + "='" + dto.getId()+"'", null) > 0;
        }
        else
            return false;
    }

    /**
     * @param id del registro a eliminar
     * @return BOOLEAN
     * */
    public boolean deleteEntry( String id )
    {
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();
        // table , whereClause, whereArgs
        return  (db.delete( sqliteHelper.TABLE_ENTRY, sqliteHelper.KEY_ENTRY_ID+ " = '" + id+"'",  null) > 0) ? true:false;
    }
    /**
     * @return BOOLEAN
     * */
    public boolean deleteAllEntry()
    {
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();
        // table , whereClause, whereArgs
        return  (db.delete( sqliteHelper.TABLE_ENTRY, sqliteHelper.KEY_ENTRY_ID + " <> ''",  null) > 0) ? true:false;
    }

    /**
     * Obtiene un registro
     * */
    public entry getEntry(String id)
    {
        entry dto = null;
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();

        // 2. build query
        Cursor cursor = db.query( sqliteHelper.TABLE_ENTRY,
                new String[]{
                        sqliteHelper.KEY_ENTRY_ID,
                        sqliteHelper.KEY_ENTRY_BUNDLEID,
                        sqliteHelper.KEY_ENTRY_LABEL,
                        sqliteHelper.KEY_ENTRY_NAME,
                        sqliteHelper.KEY_ENTRY_TITLE,
                        sqliteHelper.KEY_ENTRY_RIGHTS,
                        sqliteHelper.KEY_ENTRY_ARTIST,
                        sqliteHelper.KEY_ENTRY_ARTISTHREF,
                        sqliteHelper.KEY_ENTRY_TYPE,
                        sqliteHelper.KEY_ENTRY_LABELPRICE,
                        sqliteHelper.KEY_ENTRY_AMOUNT,
                        sqliteHelper.KEY_ENTRY_CURRENCY,
                        sqliteHelper.KEY_ENTRY_LINK_REL,
                        sqliteHelper.KEY_ENTRY_LINKTYPE,
                        sqliteHelper.KEY_ENTRY_HREF,
                        sqliteHelper.KEY_ENTRY_DATE,
                        sqliteHelper.KEY_ENTRY_AUTHORID,
                        sqliteHelper.KEY_ENTRY_CATEGORYID}, sqliteHelper.KEY_ENTRY_ID + " = '" + id +"'" ,
                null, null, null, null);
        // 3. Move to first row
        cursor.moveToFirst();
        Log.i("SQLite", " entry ID: " + id);
        if(cursor.getCount() > 0){
            dto = new entry();
            dto.setId(cursor.getString(0));
            dto.setBundleId(cursor.getString(1));
            dto.setLabel(cursor.getString(2));
            dto.setImname(cursor.getString(3));
            dto.setTitle(cursor.getString(4));
            dto.setRights(cursor.getString(5));
            dto.setArtist(cursor.getString(6));
            dto.setArtist_href(cursor.getString(7));
            dto.setContenttype(cursor.getString(8));
            dto.setLabelprice(cursor.getString(9));
            dto.setAmount(cursor.getDouble(10));
            dto.setCurrency(cursor.getString(11));
            dto.setLink_rel(cursor.getString(12));
            dto.setLink_type(cursor.getString(13));
            dto.setLinl_href(cursor.getString(14));
            dto.setReleaseDate(cursor.getString(15));
            dto.setAuthorid(cursor.getInt(16));
            dto.setCategoryid(cursor.getString(17));
        }
        cursor.close();
        db.close();
        // return user
        return dto;
    }

    /**
     * Obtiene todos los registros de la unica tabla de la base de datos
     * @return Cursor
     * */
    public List<entry> getAllEntrybyCategory( int PageSize, int CurrentOffset, String categoryid )
    {
        int Start = 0;
        Start = (PageSize * CurrentOffset)+1;
        List<entry> list = new LinkedList<entry>();
        entry dto = null;
        // 1. get reference to writable DB
        db = sqliteHelper.getWritableDatabase();
        // 2. sql query
        String sql = "SELECT * FROM "+ sqliteHelper.TABLE_ENTRY+ " WHERE " + sqliteHelper.KEY_ENTRY_CATEGORYID + " = '"+categoryid +"'"; // LIMIT "+ Start +" , "+PageSize;

        Cursor cursor =  db.rawQuery(sql, null);
        Log.i("SQLite Entry", sql);
        Log.i("SQLite Entry", " get all entry by category: "+categoryid);
        // 3. go over each row, build book and add it to list
        if (cursor.moveToFirst()) {
            do {
                dto = new entry();
                dto.setId(cursor.getString(0));
                dto.setBundleId(cursor.getString(1));
                dto.setLabel(cursor.getString(2));
                dto.setImname(cursor.getString(3));
                dto.setTitle(cursor.getString(4));
                dto.setRights(cursor.getString(5));
                dto.setArtist(cursor.getString(6));
                dto.setArtist_href(cursor.getString(7));
                dto.setContenttype(cursor.getString(8));
                dto.setLabelprice(cursor.getString(9));
                dto.setAmount(cursor.getDouble(10));
                dto.setCurrency(cursor.getString(11));
                dto.setLink_rel(cursor.getString(12));
                dto.setLink_type(cursor.getString(13));
                dto.setLinl_href(cursor.getString(14));
                dto.setReleaseDate(cursor.getString(15));
                dto.setAuthorid(cursor.getInt(16));
                dto.setCategoryid(cursor.getString(17));
                // Add game to Array Games
                list.add(dto);
                Log.i(sqliteHelper.TABLE_ENTRY, "List all entry. name: "+cursor.getString(3) );
            } while (cursor.moveToNext());
        }
        // 4. close
        db.close();
        cursor.close();

        return list;
    }

    /**
     * Getting count items
     * return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + sqliteHelper.TABLE_ENTRY;
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }

}