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

	public static void initialise(Logger.LogLevel logLevel)
	{
		LoggerFactory.logLevel = logLevel;
	}

	public static void initialise(Logger.LogLevel logLevel, PrintStream printStream)
	{
		LoggerFactory.logLevel = logLevel;
		LoggerFactory.printStream = printStream;
	}

	public static void initialise(Logger.LogLevel logLevel, PrintStream printStream, DateTimeFormatter dateFormat)
	{
		LoggerFactory.logLevel = logLevel;
		LoggerFactory.printStream = printStream;
		LoggerFactory.dateFormat = dateFormat;
	}

	public static void setLogFile(Path logFilePath) throws FileNotFoundException
	{
		LoggerFactory.printStream = new PrintStream(new FileOutputStream(logFilePath.toFile()));
	}

	public static Logger getLogger(Class<?> clazz)
	{
		return Logger.getLogger(clazz, logLevel, dateFormat, printStream);
	}
}