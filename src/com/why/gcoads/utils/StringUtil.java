package com.why.gcoads.utils;

public class StringUtil {

	public static final String Empty = "";
	
	public static boolean isNullOrEmpty(Object obj) {
		if (Empty.equals(obj) || obj == null) {
			return true;
		}
		return false;
	}
}
