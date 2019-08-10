
package com.example.dunzoassignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Newsarticle {

    @SerializedName("mainentityofpage")
    @Expose
    private String mainentityofpage;
    @SerializedName("headline")
    @Expose
    private String headline;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("datepublished")
    @Expose
    private String datepublished;
    @SerializedName("datemodified")
    @Expose
    private String datemodified;
    @SerializedName("articlebody")
    @Expose
    private String articlebody;
    @SerializedName("contenturl")
    @Expose
    private String contenturl;

    public String getMainentityofpage() {
        return mainentityofpage;
    }

    public void setMainentityofpage(String mainentityofpage) {
        this.mainentityofpage = mainentityofpage;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatepublished() {
        return datepublished;
    }

    public void setDatepublished(String datepublished) {
        this.datepublished = datepublished;
    }

    public String getDatemodified() {
        return datemodified;
    }

    public void setDatemodified(String datemodified) {
        this.datemodified = datemodified;
    }

    public String getArticlebody() {
        return articlebody;
    }

    public void setArticlebody(String articlebody) {
        this.articlebody = articlebody;
    }

    public String getContenturl() {
        return contenturl;
    }

    public void setContenturl(String contenturl) {
        this.contenturl = contenturl;
    }

}
