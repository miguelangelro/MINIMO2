package com.example.minimo2DSAMiguel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Repos {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("language")
    @Expose
    private String language;

    public String getName() {
        return this.name;
    }
    public String getLanguage(){return this.language;}
}
