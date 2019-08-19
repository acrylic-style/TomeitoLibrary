package xyz.acrylicstyle.tomeito_core;

import org.junit.Test;

import xyz.acrylicstyle.tomeito_core.utils.ReflectionUtil;

public class UtilTest {
	private final static String not = "Not anything";
	private final static String itsInt = "12345";
	private final static String itsDouble = "12345.67";
	private final static String itsFloat = "123.4567890";
	private final static String sTrue = "tRuE";
	private final static String sFalse = "fAlSe";

	private void throwNFE() {
		throw new NumberFormatException("It isn't correct format");
	}

	@Test
	public void isInt() {
		if (!ReflectionUtil.isInt(itsInt)) throwNFE();
	}

	@Test(expected=NumberFormatException.class)
	public void isntIntItsDouble() {
		if (!ReflectionUtil.isInt(itsDouble)) throwNFE();
	}

	@Test(expected=NumberFormatException.class)
	public void isntInt() {
		if (!ReflectionUtil.isInt(not)) throwNFE();
	}

	@Test
	public void isDouble() {
		if (!ReflectionUtil.isDouble(itsDouble)) throwNFE();
	}

	@Test
	public void isDoubleButInt() {
		if (!ReflectionUtil.isDouble(itsInt)) throwNFE();
	}

	@Test(expected=NumberFormatException.class)
	public void isntDouble() {
		if (!ReflectionUtil.isDouble(not)) throwNFE();
	}

	@Test
	public void parseBoolean() throws Exception {
		ReflectionUtil.parseBoolean(sTrue);
		ReflectionUtil.parseBoolean(sFalse);
	}

	@Test(expected=Exception.class)
	public void parseBooleanButNot() throws Exception {
		ReflectionUtil.parseBoolean(not);
	}

	@Test
	public void parseFloat() {
		if (!ReflectionUtil.isDouble(itsFloat)) throwNFE();
	}

	@Test(expected=NumberFormatException.class)
	public void parseFloatButNot() {
		if (!ReflectionUtil.isDouble(not)) throwNFE();
	}
}
