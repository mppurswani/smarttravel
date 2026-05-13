package com.travel.smarttravel.specification;

import com.travel.smarttravel.entity.City;
import com.travel.smarttravel.entity.CityCategory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CitySpecification {

    public static Specification<City> filter(String keyword,
                                         String country,
                                         CityCategory category) {

    return (root, query, cb) -> {

        List<Predicate> predicates = new ArrayList<>();

        // keyword filter
        if (keyword != null && !keyword.isBlank()) {
            String kw = "%" + keyword.toLowerCase() + "%";

            predicates.add(
                cb.or(
                    cb.like(cb.lower(root.get("name")), kw),
                    cb.like(cb.lower(root.get("state")), kw),
                    cb.like(cb.lower(root.get("country")), kw)
                )
            );
        }

        // country filter
        if (country != null && !country.isBlank() && !country.equalsIgnoreCase("ALL")) {
            predicates.add(
                cb.equal(cb.lower(root.get("country")), country.toLowerCase())
            );
        }

        // category filter
        if (category != null) {
            predicates.add(
                cb.equal(root.get("category"), category)
            );
        }

        // IMPORTANT FIX: no filters → return "true condition"
        if (predicates.isEmpty()) {
            return cb.conjunction(); // returns all rows
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    };
}
}