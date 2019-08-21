package xyz.acrylicstyle.tomeito_core;

import org.junit.Test;

import xyz.acrylicstyle.tomeito_core.utils.TypeUtil;

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
		if (!TypeUtil.isInt(itsInt)) throwNFE();
	}

	@Test(expected=NumberFormatException.class)
	public void isntIntItsDouble() {
		if (!TypeUtil.isInt(itsDouble)) throwNFE();
	}

	@Test(expected=NumberFormatException.class)
	public void isntInt() {
		if (!TypeUtil.isInt(not)) throwNFE();
	}

	@Test
	public void isDouble() {
		if (!TypeUtil.isDouble(itsDouble)) throwNFE();
	}

	@Test
	public void isDoubleButInt() {
		if (!TypeUtil.isDouble(itsInt)) throwNFE();
	}

	@Test(expected=NumberFormatException.class)
	public void isntDouble() {
		if (!TypeUtil.isDouble(not)) throwNFE();
	}

	@Test
	public void parseBoolean() throws Exception {
		TypeUtil.parseBoolean(sTrue);
		TypeUtil.parseBoolean(sFalse);
	}

	@Test(expected=Exception.class)
	public void parseBooleanButNot() throws Exception {
		TypeUtil.parseBoolean(not);
	}

	@Test
	public void parseFloat() {
		if (!TypeUtil.isDouble(itsFloat)) throwNFE();
	}

	@Test(expected=NumberFormatException.class)
	public void parseFloatButNot() {
		if (!TypeUtil.isDouble(not)) throwNFE();
	}
}
