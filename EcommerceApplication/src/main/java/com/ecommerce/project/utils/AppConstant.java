package com.ecommerce.project.utils;

import jakarta.validation.ConstraintViolationException;

public class AppConstant {
	public static final String PAGE_NUMBER = "0";
	public static final String PAGE_SIZE = "10";
	public static final String SORT_CATEGORY_BY = "categoryId";
	public static final String SORT_ORDER = "asc";
	
	public static String convetrSetToCommaSeparatedString(final ConstraintViolationException exception) {
		StringBuilder errorsString = new StringBuilder();
        exception.getConstraintViolations().stream().forEach(fieldError -> {
        	errorsString.append(fieldError.getMessage()).append("|");
        });
        String commaseparatedlist = errorsString.toString();
        if(commaseparatedlist.length()>0) {
        	commaseparatedlist = commaseparatedlist.substring(0,commaseparatedlist.length()-1);
        }
		return commaseparatedlist;
	}
}
