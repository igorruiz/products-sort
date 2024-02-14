package com.productsort.model.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringToNumberUtilsTest {
	
	@Test
	public void givenValidNumericString_whenInvokingIsLong_thenReturnTrue() {
		
		// Preparate
		String string = "100";
		
		// Act
		boolean testResult = StringToNumberUtils.isLong(string);
		
		// Assert
		assertTrue(testResult);
	}

	@Test
	public void givenANotNumericString_whenInvokingIsLong_thenReturnFalse() {
		
		// Preparate
		String string = "hello";
		
		// Act
		boolean testResult = StringToNumberUtils.isLong(string);
		
		// Assert
		assertFalse(testResult);
	}

	@Test
	public void givenANullString_whenInvokingIsLong_thenReturnFalse() {
		
		// Preparate
		
		// Act
		boolean testResult = StringToNumberUtils.isLong(null);
		
		// Assert
		assertFalse(testResult);
	}

	@Test
	public void givenAnEmptyString_whenInvokingIsLong_thenReturnFalse() {
		
		// Preparate
		
		// Act
		boolean testResult = StringToNumberUtils.isLong("");
		
		// Assert
		assertFalse(testResult);
	}

}
