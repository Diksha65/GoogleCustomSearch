
package com.example.dunzoassignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {

    @SerializedName("sameas")
    @Expose
    private String sameas;
    @SerializedName("name")
    @Expose
    private String name;

    public String getSameas() {
        return sameas;
    }

    public void setSameas(String sameas) {
        this.sameas = sameas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
