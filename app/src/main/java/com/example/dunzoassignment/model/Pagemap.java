
package com.example.dunzoassignment.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagemap {

    @SerializedName("cse_thumbnail")
    @Expose
    private List<CseThumbnail> cseThumbnail = null;
    @SerializedName("metatags")
    @Expose
    private List<Metatag> metatags = null;
    @SerializedName("cse_image")
    @Expose
    private List<CseImage> cseImage = null;
    @SerializedName("thumbnail")
    @Expose
    private List<Thumbnail> thumbnail = null;
    @SerializedName("newsarticle")
    @Expose
    private List<Newsarticle> newsarticle = null;
    @SerializedName("imageobject")
    @Expose
    private List<Imageobject> imageobject = null;
    @SerializedName("person")
    @Expose
    private List<Person> person = null;
    @SerializedName("organization")
    @Expose
    private List<Organization> organization = null;

    public List<CseThumbnail> getCseThumbnail() {
        return cseThumbnail;
    }

    public void setCseThumbnail(List<CseThumbnail> cseThumbnail) {
        this.cseThumbnail = cseThumbnail;
    }

    public List<Metatag> getMetatags() {
        return metatags;
    }

    public void setMetatags(List<Metatag> metatags) {
        this.metatags = metatags;
    }

    public List<CseImage> getCseImage() {
        return cseImage;
    }

    public void setCseImage(List<CseImage> cseImage) {
        this.cseImage = cseImage;
    }

    public List<Thumbnail> getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(List<Thumbnail> thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<Newsarticle> getNewsarticle() {
        return newsarticle;
    }

    public void setNewsarticle(List<Newsarticle> newsarticle) {
        this.newsarticle = newsarticle;
    }

    public List<Imageobject> getImageobject() {
        return imageobject;
    }

    public void setImageobject(List<Imageobject> imageobject) {
        this.imageobject = imageobject;
    }

    public List<Person> getPerson() {
        return person;
    }

    public void setPerson(List<Person> person) {
        this.person = person;
    }

    public List<Organization> getOrganization() {
        return organization;
    }

    public void setOrganization(List<Organization> organization) {
        this.organization = organization;
    }

}
