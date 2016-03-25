package com.grability.myappstore.parser;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.grability.myappstore.controller.dbController;
import com.grability.myappstore.model.author;
import com.grability.myappstore.model.category;
import com.grability.myappstore.model.entry;
import com.grability.myappstore.model.image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luis Vespa on 23/01/2016.
 */
public class getGsonParser {
    static String tag_string_req = getGsonParser.class.getSimpleName();

    public boolean getFlujoJson(Activity ctx, String response) {
        List lista = new ArrayList();
        boolean result=false;
        dbController cont = new dbController(ctx);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonFeed = jsonObject.getJSONObject("feed");

            if (jsonFeed!=null) {
                cont.deleteAllDatabase();
                // Now store the user in sqlite
                JSONObject jsonAuthor = jsonFeed.getJSONObject("author");
                JSONArray jsonEntry = jsonFeed.getJSONArray("entry");
                JSONObject jsonID = jsonFeed.getJSONObject("id");

                JSONObject jsonName = jsonAuthor.getJSONObject("name");
                JSONObject jsonUri = jsonAuthor.getJSONObject("uri");

                // Se guarda el author en su tabla
                author objAuthor = new author();
                objAuthor.setName(jsonName.getString("label"));              // Nombre del Autor
                objAuthor.setUri(jsonUri.getString("label"));                // URI del Autor

                for (int i = 0; i < jsonEntry.length(); i++) {
                    JSONObject jsonObjEntry = jsonEntry.getJSONObject(i);
                    if (jsonObjEntry!=null) {
                        String entryid ="";
                        String categoryid="";
                        entry dtoEntry = new entry();
                        JSONObject imName = jsonObjEntry.getJSONObject("im:name");
                        dtoEntry.setImname(imName.getString("label"));

                        JSONObject Summary = jsonObjEntry.getJSONObject("summary");
                        dtoEntry.setLabel(Summary.getString("label"));
                        JSONObject Price = jsonObjEntry.getJSONObject("im:price");
                        dtoEntry.setLabelprice(Price.getString("label"));
                        JSONObject Attributes = Price.getJSONObject("attributes");
                        dtoEntry.setAmount(Double.parseDouble(Attributes.getString("amount")));
                        dtoEntry.setCurrency(Attributes.getString("currency"));

                        JSONObject jsonContentType = jsonObjEntry.getJSONObject("im:contentType");
                        JSONObject jsonContentType2 = jsonContentType.getJSONObject("attributes");
                        dtoEntry.setContenttype(jsonContentType2.getString("term"));
                        JSONObject jsonRights = jsonObjEntry.getJSONObject("rights");
                        dtoEntry.setRights(jsonRights.getString("label"));
                        JSONObject jsonTitle = jsonObjEntry.getJSONObject("title");
                        dtoEntry.setTitle(jsonTitle.getString("label"));
                        JSONObject jsonLink = jsonObjEntry.getJSONObject("link");
                        JSONObject jsonLink2 = jsonLink.getJSONObject("attributes");
                        dtoEntry.setLink_rel(jsonLink2.getString("rel"));
                        dtoEntry.setLink_type(jsonLink2.getString("type"));
                        dtoEntry.setLinl_href(jsonLink2.getString("href"));
                        JSONObject jsonEntryID = jsonObjEntry.getJSONObject("id");
                        jsonEntryID.getString("label");
                        JSONObject attributes2 = jsonEntryID.getJSONObject("attributes");
                        entryid = attributes2.getString("im:id");
                        dtoEntry.setId(attributes2.getString("im:id"));
                        dtoEntry.setBundleId(attributes2.getString("im:bundleId"));

                        JSONObject jsonArtist = jsonObjEntry.getJSONObject("im:artist");
                        dtoEntry.setArtist(jsonArtist.getString("label"));
                        JSONObject jsonCategory = jsonObjEntry.getJSONObject("category");           //
                        JSONObject jsonCategory2 = jsonCategory.getJSONObject("attributes");
                        categoryid = jsonCategory2.getString("im:id");
                        // Se agrega registro en la tabla datanase
                        category cat = new category(jsonCategory2.getString("im:id"),
                                jsonCategory2.getString("term"),
                                jsonCategory2.getString("scheme"),
                                jsonCategory2.getString("label"));
                        // Agregar a database category
                        cont.agregarCategory(cat);

                        JSONObject jsonDate = jsonObjEntry.getJSONObject("im:releaseDate");
                        dtoEntry.setReleaseDate(jsonDate.getString("label"));
                        dtoEntry.setCategoryid(categoryid);
                        // Aqui se guarda el registro en la tabla Entry
                        cont.agregarEntry(dtoEntry);
                        // Agregar a database Entry
                        Log.e(tag_string_req, "SQL New Entry: " + dtoEntry.getId() + ", " + dtoEntry.getTitle() + " CategoryId: " + dtoEntry.getCategoryid() );
                        /************ Array Imagenes ************/
                        JSONArray imImage = jsonObjEntry.getJSONArray("im:image");
                        for (int j = 0; j < imImage.length(); j++) {

                            JSONObject objimage = imImage.getJSONObject(j);
                            JSONObject attributes = objimage.getJSONObject("attributes");
                            image dtoimage = new image(entryid,
                                    objimage.getString("label"),
                                    Integer.parseInt(attributes.getString("height")) );
                            // Agregar a database detalle image
                            cont.agregarImage(dtoimage);
                        }

                    } else {
                        // Error occurred in registration. Get the error
                        String errorMsg = "Listentry played Error Json Parse! ";
                        Log.e(tag_string_req, "Error: " + errorMsg);
                    }
                }
                result = true;
                // Inserting row in users table
                // db.addUser(firstname, lastname, username, uid, image, type, created_at);

            } else {
                // Error occurred in registration. Get the error
                // message
                String errorMsg = "Error Json Parse";
                Toast.makeText(ctx, errorMsg, Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
