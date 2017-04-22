package com.skywalker.courserademo.home;

import com.skywalker.courserademo.api.Course;
import com.skywalker.courserademo.api.Entry;
import com.skywalker.courserademo.api.OnDemandSpecialization;
import com.skywalker.courserademo.api.Partner;
import com.skywalker.courserademo.api.SearchResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemViewModel {
    public String id;
    public String name;
    public String imageUrl;
    public List<String> universityName = new ArrayList<>();
    public Integer numberOfCourses;

    public static List<ItemViewModel> convert(SearchResult result) {
        if (result == null || result.linked == null || result.elements == null || result.elements.isEmpty()) {
            return Collections.emptyList();
        }

        final List<Entry> entries = result.elements.get(0).entries;
        if (entries == null || entries.isEmpty()) {
            return Collections.emptyList();
        }

        final Map<String, Course> coursesMap = new HashMap<>();
        final Map<String, OnDemandSpecialization> demandSpecializationsMap = new HashMap<>();
        final Map<String, Partner> partnerMap = new HashMap<>();

        if (result.linked.partners != null) {
            result.linked.partners.forEach(p -> partnerMap.put(p.id, p));
        }

        if (result.linked.courses != null) {
            result.linked.courses.forEach(c -> coursesMap.put(c.id, c));
        }

        if (result.linked.onDemandSpecializations != null) {
            result.linked.onDemandSpecializations.forEach(d -> demandSpecializationsMap.put(d.id, d));
        }

        final List<ItemViewModel> itemViewModels = new ArrayList<>();

        for (Entry entry : entries) {
            final ItemViewModel item = new ItemViewModel();
            item.id = entry.id;

            if (coursesMap.containsKey(entry.id)) {
                final Course course = coursesMap.get(entry.id);
                item.name = course.name;
                item.imageUrl = course.photoUrl;
                course.partnerIds.forEach(partnerId -> {
                    if (partnerMap.containsKey(partnerId)) {
                        item.universityName.add(partnerMap.get(partnerId).name);
                    }
                });
            }

            if (demandSpecializationsMap.containsKey(entry.id)) {
                final OnDemandSpecialization onDemandSpecialization = demandSpecializationsMap.get(entry.id);
                item.name = onDemandSpecialization.name;
                item.imageUrl = onDemandSpecialization.logo;
                item.numberOfCourses = onDemandSpecialization.courseIds.size();
                onDemandSpecialization.partnerIds.forEach(partnerId -> {
                    if (partnerMap.containsKey(partnerId)) {
                        item.universityName.add(partnerMap.get(partnerId).name);
                    }
                });
            }

            itemViewModels.add(item);
        }

        return itemViewModels;
    }
}
