package com.productsort.model.utils;

import org.springframework.util.StringUtils;

public final class StringToNumberUtils {

	private StringToNumberUtils() {

	}

	public static final boolean isLong(String string) {
		if (!StringUtils.hasLength(string)) {
			return false;
		}
		try {
			Long.parseLong(string);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

}
