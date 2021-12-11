package com.chrisnewland.freelogj;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class LoggerTest
{
	private Logger.LogLevel logLevel;

	private ByteArrayOutputStream baos;

	private PrintStream printStream;

	private void checkContents(String message)
	{
		message = message + "\n";

		String streamString = baos.toString();

		assertEquals(streamString.substring(streamString.length() - message.length()), message);

		baos.reset();
	}

	@Before
	public void setup()
	{
		logLevel = Logger.LogLevel.INFO;

		baos = new ByteArrayOutputStream();

		printStream = new PrintStream(baos);
	}

	@Test
	public void testParamAtStart()
	{
		Logger logger = Logger.getLogger(LoggerTest.class, logLevel, printStream);

		logger.info("{} open source maintainers!", "Pay");

		checkContents("Pay open source maintainers!");
	}

	@Test
	public void testParamAtEnd()
	{
		Logger logger = Logger.getLogger(LoggerTest.class, logLevel, printStream);

		logger.info("Hello {}", "Chris");

		checkContents("Hello Chris");
	}

	@Test
	public void testParamInMiddle()
	{
		Logger logger = Logger.getLogger(LoggerTest.class, logLevel, printStream);

		logger.info("I am a {} logger", "simple");

		checkContents("I am a simple logger");
	}

	@Test
	public void testLotOfParams()
	{
		Logger logger = Logger.getLogger(LoggerTest.class, logLevel, printStream);

		logger.info("Name:{} Age:{} Location:{}", "Chris Newland", 999, "QueingForCompilation");

		checkContents("Name:Chris Newland Age:999 Location:QueingForCompilation");
	}

	@Test
	public void testNoParams()
	{
		Logger logger = Logger.getLogger(LoggerTest.class, logLevel, printStream);

		logger.info("This is a simple message");

		checkContents("This is a simple message");
	}

	@Test
	public void testNullParams()
	{
		Logger logger = Logger.getLogger(LoggerTest.class, logLevel, printStream);

		logger.info("value:{}", null);

		checkContents("value:{}");
	}

	@Test
	public void testMissingParams()
	{
		Logger logger = Logger.getLogger(LoggerTest.class, logLevel, printStream);

		logger.info("Name:{} Age:{} Location:{}", "Chris Newland", 999);

		checkContents("Name:Chris Newland Age:999 Location:{}");
	}

	@Test
	public void testNullMessage()
	{
		Logger logger = Logger.getLogger(LoggerTest.class, logLevel, printStream);

		logger.info(null);

		checkContents("null");
	}

	@Test
	public void testLogLevelTrace()
	{
		logLevel = Logger.LogLevel.TRACE;

		Logger logger = Logger.getLogger(LoggerTest.class, logLevel, printStream);

		logger.trace("trace should be logged");

		checkContents("trace should be logged");

		assertTrue(logger.isTraceEnabled());
		assertTrue(logger.isDebugEnabled());
		assertTrue(logger.isInfoEnabled());
		assertTrue(logger.isWarnEnabled());
		assertTrue(logger.isErrorEnabled());
		assertTrue(logger.isFatalEnabled());
	}

	@Test
	public void testLogLevelDebug()
	{
		logLevel = Logger.LogLevel.DEBUG;

		Logger logger = Logger.getLogger(LoggerTest.class, logLevel, printStream);

		logger.trace("trace should not be logged");

		assertEquals(0, baos.toByteArray().length);

		logger.debug("debug should be logged");

		checkContents("debug should be logged");

		assertFalse(logger.isTraceEnabled());
		assertTrue(logger.isDebugEnabled());
		assertTrue(logger.isInfoEnabled());
		assertTrue(logger.isWarnEnabled());
		assertTrue(logger.isErrorEnabled());
		assertTrue(logger.isFatalEnabled());
	}

	@Test
	public void testLogLevelInfo()
	{
		logLevel = Logger.LogLevel.INFO;

		Logger logger = Logger.getLogger(LoggerTest.class, logLevel, printStream);

		logger.debug("debug should not be logged");

		assertEquals(0, baos.toByteArray().length);

		logger.info("info should be logged");

		checkContents("info should be logged");

		assertFalse(logger.isTraceEnabled());
		assertFalse(logger.isDebugEnabled());
		assertTrue(logger.isInfoEnabled());
		assertTrue(logger.isWarnEnabled());
		assertTrue(logger.isErrorEnabled());
		assertTrue(logger.isFatalEnabled());
	}

	@Test
	public void testLogLevelWarn()
	{
		logLevel = Logger.LogLevel.WARN;

		Logger logger = Logger.getLogger(LoggerTest.class, logLevel, printStream);

		logger.info("info should not be logged");

		assertEquals(0, baos.toByteArray().length);

		logger.warn("warn should be logged");

		checkContents("warn should be logged");

		assertFalse(logger.isTraceEnabled());
		assertFalse(logger.isDebugEnabled());
		assertFalse(logger.isInfoEnabled());
		assertTrue(logger.isWarnEnabled());
		assertTrue(logger.isErrorEnabled());
		assertTrue(logger.isFatalEnabled());
	}

	@Test
	public void testLogLevelError()
	{
		logLevel = Logger.LogLevel.ERROR;

		Logger logger = Logger.getLogger(LoggerTest.class, logLevel, printStream);

		logger.warn("warn should not be logged");

		assertEquals(0, baos.toByteArray().length);

		logger.error("error should be logged");

		checkContents("error should be logged");

		assertFalse(logger.isTraceEnabled());
		assertFalse(logger.isDebugEnabled());
		assertFalse(logger.isInfoEnabled());
		assertFalse(logger.isWarnEnabled());
		assertTrue(logger.isErrorEnabled());
		assertTrue(logger.isFatalEnabled());
	}

	@Test
	public void testLogLevelFatal()
	{
		logLevel = Logger.LogLevel.FATAL;

		Logger logger = Logger.getLogger(LoggerTest.class, logLevel, printStream);

		logger.error("error should not be logged");

		assertEquals(0, baos.toByteArray().length);

		logger.fatal("fatal should be logged");

		checkContents("fatal should be logged");

		assertFalse(logger.isTraceEnabled());
		assertFalse(logger.isDebugEnabled());
		assertFalse(logger.isInfoEnabled());
		assertFalse(logger.isWarnEnabled());
		assertFalse(logger.isErrorEnabled());
		assertTrue(logger.isFatalEnabled());
	}

	@Test
	public void testExceptions()
	{
		Logger logger = Logger.getLogger(LoggerTest.class, logLevel, printStream);

		logger.error("something happened", new RuntimeException("oops"));

		String contents = baos.toString();

		assertTrue(contents.contains("java.lang.RuntimeException: oops\n"));
		assertTrue(contents.contains("at com.chrisnewland.freelogj.LoggerTest.testExceptions(LoggerTest.java:259)\n"));
	}
}