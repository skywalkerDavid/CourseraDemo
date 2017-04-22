package com.skywalker.courserademo.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Paging {
    @SerializedName("next")
    @Expose
    public String current;
    @SerializedName("total")
    @Expose
    public int total;
}
