package com.inssa.server.api.image.data;

import com.inssa.server.api.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
