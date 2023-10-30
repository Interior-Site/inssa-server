package com.inssa.server.api.bookmark.data;

import com.inssa.server.api.bookmark.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkCustomRepository {
}
