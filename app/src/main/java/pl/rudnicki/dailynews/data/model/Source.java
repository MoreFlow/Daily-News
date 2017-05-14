package pl.rudnicki.dailynews.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Szymon on 14.05.2017.
 */

public class Source {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("url")
    @Expose
    public String url;

    @SerializedName("category")
    @Expose
    public String category;

    @SerializedName("language")
    @Expose
    public String language;

    @SerializedName("country")
    @Expose
    public String country;

    @SerializedName("sortBysAvailable")
    @Expose
    public List<String> sortBysAvailable = null;

}