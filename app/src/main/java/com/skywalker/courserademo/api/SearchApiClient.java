package com.skywalker.courserademo.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface SearchApiClient {
    // Sample : https://www.coursera.org/api/catalogResults.v2?q=search&query=machine%20learning&start=0&limit=5&fields=courseId,onDemandSpecializationId,courses.v1(name,photoUrl,partnerIds),onDemandSpecializations.v1(name,logo,courseIds,partnerIds),partners.v1(name)&includes=courseId,onDemandSpecializationId,courses.v1(partnerIds)
    @GET("catalogResults.v2?q=search&fields=courseId,onDemandSpecializationId,courses.v1(name,photoUrl,partnerIds),onDemandSpecializations.v1(name,logo,courseIds,partnerIds),partners.v1(name)&includes=courseId,onDemandSpecializationId,courses.v1(partnerIds)")
    Observable<SearchResult> search(@Query("query") String query, @Query("start") int start, @Query("limit") int limit);
}
