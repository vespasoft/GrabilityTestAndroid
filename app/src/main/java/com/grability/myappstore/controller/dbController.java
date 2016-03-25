package com.grability.myappstore.controller;

import android.app.Activity;
import android.widget.Toast;

import com.grability.myappstore.app.AppConfig;
import com.grability.myappstore.helper.databaseSQLite;
import com.grability.myappstore.model.author;
import com.grability.myappstore.model.category;
import com.grability.myappstore.model.entry;
import com.grability.myappstore.model.image;
import com.grability.myappstore.util.Utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Luis Vespa on 23/03/2016.
 */
public class dbController {
    private static final String TAG = dbController.class.getSimpleName();

    private Activity context;
    private databaseSQLite Datos;

    public dbController(Activity ctx) {
        context = ctx;

        // SQLite database handler
        Datos = new databaseSQLite(ctx);
        Datos.abrir();
    }

    public void deleteAllDatabase() {
        Datos.deleteAllAuthor();
        Datos.deleteAllCategory();
        Datos.deleteAllImage();
        Datos.deleteAllEntry();
    }

    public void deleteAllImages() {
        Datos.deleteAllImage();
    }

    public void agregarCategory(category dto) {
        Datos.addNewCategory(dto);
    }

    public void agregarImage(image dto) {
        Datos.addImage(dto);
    }

    public void agregarEntry(entry dto) {
        Datos.addNewEntry(dto);
    }

    public void agregarAuthor(author dto) {
        Datos.addAuthor(dto);
    }

    // Filtra todos los juegos sin filtro ni paginación
    public category getCategory(String id) {

        return Datos.getCategory(id);
    }

    // Filtra todos los juegos sin filtro ni paginación
    public List<category> getAllCategory() {
        return Datos.getAllCategory();
    }

    // Filtra todos los juegos sin filtro ni paginación
    public List<image> getAllImagebyEntry(String entryid) {

        return Datos.getAllImage(entryid);
    }

    // Filtra los todos juegos paginados con LIMIT
    public List<entry> getAllEntry( int offset , String categoryid) {
        return Datos.getAllEntrybyCategory(AppConfig.PAGESIZE, offset, categoryid);
    }

    public void getCountEntry() {
        Utils.DisplayToastMessage(context, "Numero de registros: " + Datos.getRowCount(), "L");
    }

}
