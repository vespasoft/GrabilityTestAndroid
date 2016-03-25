package com.grability.myappstore.model;

import java.io.Serializable;

/**
 * Created by Luis Vespa on 23/03/2016.
 */
public class entry implements Serializable {

    private String id; // Identificador de la Aplicación
    private String bundleId;
    private String label;
    private String imname;
    private String title;
    private String rights;
    private String artist;
    private String artist_href;
    private String contenttype;
    private String labelprice;
    private Double amount;
    private String currency;
    private String link_rel;
    private String link_type;
    private String linl_href;
    private String releaseDate;
    private image[] imimage;
    private int authorid;
    private String categoryid;

    public entry() {
    }

    public entry(String id, String imname, String title, String artist, String cid, String href) {
        this.imname = imname;
        this.title = title;
        this.artist = artist;
        this.categoryid= cid;
        this.id = id;
        this.bundleId = "";
        this.label = "";
        this.rights = "";
        this.artist_href = "";
        this.contenttype = "";
        this.labelprice = "";
        this.amount = 0.0;
        this.currency = "";
        this.link_rel = "";
        this.link_type = "";
        this.linl_href = href;
        this.releaseDate = "";
        this.authorid = 0;
    }

    public entry(String id, String bundleId, String label, String imname, String title, String rights, String artist, String artist_href, String contenttype, String labelprice, Double amount, String currency, String link_rel, String link_type, String linl_href, String releaseDate, image[] imimage, int authorid, String categoryid) {
        this.id = id;
        this.bundleId = bundleId;
        this.label = label;
        this.imname = imname;
        this.title = title;
        this.rights = rights;
        this.artist = artist;
        this.artist_href = artist_href;
        this.contenttype = contenttype;
        this.labelprice = labelprice;
        this.amount = amount;
        this.currency = currency;
        this.link_rel = link_rel;
        this.link_type = link_type;
        this.linl_href = linl_href;
        this.releaseDate = releaseDate;
        this.imimage = imimage;
        this.authorid = authorid;
        this.categoryid = categoryid;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtist_href() {
        return artist_href;
    }

    public void setArtist_href(String artist_href) {
        this.artist_href = artist_href;
    }

    public int getAuthorid() {
        return authorid;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getLink_rel() {
        return link_rel;
    }

    public void setLink_rel(String link_rel) {
        this.link_rel = link_rel;
    }

    public String getLink_type() {
        return link_type;
    }

    public void setLink_type(String link_type) {
        this.link_type = link_type;
    }

    public String getLinl_href() {
        return linl_href;
    }

    public void setLinl_href(String linl_href) {
        this.linl_href = linl_href;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getLabelprice() {
        return labelprice;
    }

    public void setLabelprice(String labelprice) {
        this.labelprice = labelprice;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getImname() {
        return imname;
    }

    public void setImname(String imname) {
        this.imname = imname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public image[] getImimage() {
        return imimage;
    }

    public void setImimage(image[] imimage) {
        this.imimage = imimage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
