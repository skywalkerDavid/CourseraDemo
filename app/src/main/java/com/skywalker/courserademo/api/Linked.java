
package com.skywalker.courserademo.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Linked {

    @SerializedName("courses.v1")
    @Expose
    public List<Course> courses = null;
    @SerializedName("partners.v1")
    @Expose
    public List<Partner> partners = null;
    @SerializedName("onDemandSpecializations.v1")
    @Expose
    public List<OnDemandSpecialization> onDemandSpecializations = null;

}
