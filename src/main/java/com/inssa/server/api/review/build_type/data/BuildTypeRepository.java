package com.inssa.server.api.review.build_type.data;

import com.inssa.server.api.review.build_type.model.BuildType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildTypeRepository extends JpaRepository<BuildType, Long> {
}
