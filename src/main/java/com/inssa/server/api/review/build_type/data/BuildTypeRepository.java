package com.inssa.server.api.review.build_type.data;

import com.inssa.server.api.review.build_type.model.BuildType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildTypeRepository extends JpaRepository<BuildType, Long> {
    @Cacheable("buildTypeIds")
    List<BuildType> findAll();
}
