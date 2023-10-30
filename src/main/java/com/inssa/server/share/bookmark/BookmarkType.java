package com.inssa.server.share.bookmark;

import lombok.Getter;

@Getter
public enum BookmarkType {
    POST, COMMENT

    ;

    @Override
    public String toString() {
        return name();
    }
}
