
package com.skywalker.courserademo.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnDemandSpecialization {

    @SerializedName("courseIds")
    @Expose
    public List<String> courseIds = null;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("partnerIds")
    @Expose
    public List<String> partnerIds = null;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("tagline")
    @Expose
    public String tagline;
    @SerializedName("logo")
    @Expose
    public String logo;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("slug")
    @Expose
    public String slug;

}
