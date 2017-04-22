
package com.skywalker.courserademo.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Element {

    @SerializedName("entries")
    @Expose
    public List<Entry> entries = null;
    @SerializedName("id")
    @Expose
    public String id;

}
