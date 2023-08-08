package com.inssa.server.share.board;

import java.util.Objects;

public enum BoardStatus {
	VISIBLE, DELETED;

	public boolean isVisible() {
		return Objects.equals(this, VISIBLE);
	}
}
