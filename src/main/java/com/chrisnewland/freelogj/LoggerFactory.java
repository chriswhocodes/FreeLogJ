// Copyright (c) 2021, Chris Newland (@chriswhocodes)

package com.chrisnewland.freelogj;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

public class LoggerFactory
{
	private static Logger.LogLevel logLevel = Logger.LogLevel.INFO;

	private static PrintStream printStream = System.out;

	private static DateTimeFormatter dateFormat = Logger.DEFAULT_DATE_FORMAT;

	/**
	 * Initialise the LoggerFactory with the given {@link com.chrisnewland.freelogj.Logger.LogLevel}.
	 * All loggers created by {@link #getLogger(Class) getLogger} will be set at this LogLevel.
	 *
	 * @param logLevel The logging level. Messages below this level will not be logged.
	 */
	public static void initialise(Logger.LogLevel logLevel)
	{
		LoggerFactory.logLevel = logLevel;
	}

	/**
	 * Initialise the LoggerFactory with the given {@link com.chrisnewland.freelogj.Logger.LogLevel} and {@link PrintStream}.
	 * All loggers created by {@link #getLogger(Class) getLogger} will be set at this LogLevel and use this PrintStream.
	 *
	 * @param logLevel    Messages below this level will not be logged.
	 * @param printStream Messages will be written to this PrintStream.
	 */
	public static void initialise(Logger.LogLevel logLevel, PrintStream printStream)
	{
		LoggerFactory.logLevel = logLevel;
		LoggerFactory.printStream = printStream;
	}

	/**
	 * Initialise the LoggerFactory with the given {@link com.chrisnewland.freelogj.Logger.LogLevel}, {@link PrintStream}, and {@link DateTimeFormatter}
	 * All loggers created by {@link #getLogger(Class) getLogger} will be set at this LogLevel and use this PrintStream and DateTimeFormatter.
	 *
	 * @param logLevel          Messages below this level will not be logged.
	 * @param printStream       Messages will be written to this PrintStream.
	 * @param dateTimeFormatter Messages timestamps will be formatted with this DateTimeFormatter.
	 */
	public static void initialise(Logger.LogLevel logLevel, PrintStream printStream, DateTimeFormatter dateTimeFormatter)
	{
		LoggerFactory.logLevel = logLevel;
		LoggerFactory.printStream = printStream;
		LoggerFactory.dateFormat = dateTimeFormatter;
	}

	/**
	 * Set the LoggerFactory PrintStream to use a {@link PrintStream} wrapping a {@link FileOutputStream} opened on the given {@link Path}
	 * All loggers created by {@link #getLogger(Class) getLogger} will use this PrintStream.
	 *
	 * @param logFilePath Path to the log file. If the log file cannot be opened, a {@link RuntimeException} is thrown wrapping the {@link FileNotFoundException}.
	 */
	public static void setLogFile(Path logFilePath)
	{
		try
		{
			LoggerFactory.printStream = new PrintStream(new FileOutputStream(logFilePath.toFile()));
		}
		catch (FileNotFoundException fnfe)
		{
			throw new RuntimeException("Could not open output file: " + logFilePath);
		}
	}

	/**
	 * Returns a {@link Logger} for the given Class. The fully qualified class name is printed on each log line.
	 *
	 * @param clazz The class
	 */
	public static Logger getLogger(Class<?> clazz)
	{
		return Logger.getLogger(clazz, logLevel, dateFormat, printStream);
	}
}