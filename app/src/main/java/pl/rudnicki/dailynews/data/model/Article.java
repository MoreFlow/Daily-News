package pl.rudnicki.dailynews.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Szymon on 14.05.2017.
 */

public class Article {

    @SerializedName("author")
    @Expose
    public String author;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("url")
    @Expose
    public String url;

    @SerializedName("urlToImage")
    @Expose
    public String urlToImage;

    @SerializedName("publishedAt")
    @Expose
    public String publishedAt;
}