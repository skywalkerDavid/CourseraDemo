
package com.skywalker.courserademo.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Entry {

    @SerializedName("score")
    @Expose
    public Double score;
    @SerializedName("resourceName")
    @Expose
    public String resourceName;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("courseId")
    @Expose
    public String courseId;
    @SerializedName("onDemandSpecializationId")
    @Expose
    public String onDemandSpecializationId;

}
