
package com.skywalker.courserademo.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResult {
    @SerializedName("elements")
    @Expose
    public List<Element> elements = null;
    @SerializedName("linked")
    @Expose
    public Linked linked;
    @SerializedName("paging")
    @Expose
    public Paging paging;
}
