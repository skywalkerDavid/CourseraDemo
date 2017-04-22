
package com.skywalker.courserademo.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Course {

    @SerializedName("courseType")
    @Expose
    public String courseType;
    @SerializedName("photoUrl")
    @Expose
    public String photoUrl;
    @SerializedName("partnerIds")
    @Expose
    public List<String> partnerIds = null;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("slug")
    @Expose
    public String slug;

}
