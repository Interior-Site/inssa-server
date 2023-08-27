package com.inssa.server.share.bookmark;

import lombok.Getter;

@Getter
public enum BookmarkType {
    ARTICLE, REVIEW

    ;

    @Override
    public String toString() {
        return name();
    }
}
