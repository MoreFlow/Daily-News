package pl.rudnicki.dailynews.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Szymon on 14.05.2017.
 */

public class SourcesResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("sources")
    @Expose
    public List<Source> sources = null;
}